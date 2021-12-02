package priv.xin.xd.common.util.excel;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.xin.xd.common.util.ClassUtil;
import priv.xin.xd.common.util.DateUtil;
import priv.xin.xd.common.util.JsonUtil;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author ：lu
 * @date ：2021/11/15 19:13
 */
public class ExcelUtil {

    private final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    private Workbook workBook;
    private HSSFFormulaEvaluator HSSF_FormulaEvaluator;
    private XSSFFormulaEvaluator XSSF_FormulaEvaluator;

    /**
     * 将Excel内容解析到dto
     *
     * @param inputStream Excel文件流
     * @param excelName   Excel文件名，用来判断Excel版本（xls，xlsx）
     * @param clazz       dto
     * @return
     */
    public <T> T getExcelData(InputStream inputStream, String excelName, Class<T> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] sheetNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // 设置属性可访问
            field.setAccessible(true);
            // 判断是否有注解
            if (field.isAnnotationPresent(ExcelAreaValue.class)) {
                String sheetName = field.getAnnotation(ExcelAreaValue.class).sheet();
                sheetNames[i] = sheetName;
            }
        }

        Map<String, Cell[][]> excelData = readExcelFileData(inputStream, excelName, sheetNames);

        ExcelAreaValue excelAreaValue = null;
        String sheet = null;
        int[] rowArea = null;
        int[] columnArea = null;
        // 创建实体
        T obj = null;
        try {
            obj = clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e.toString());
        } catch (IllegalAccessException e) {
            logger.error(e.toString());
        }
        if (obj == null) {
            logger.error("对象[" + clazz.getTypeName() + "]实例化失败");
            return null;
        }

        field:
        for (Field field : fields) {
            // 设置属性可访问
            field.setAccessible(true);
            // 判断是否是注解
            if (field.isAnnotationPresent(ExcelAreaValue.class)) {
                excelAreaValue = field.getAnnotation(ExcelAreaValue.class);
                sheet = excelAreaValue.sheet();
                rowArea = excelAreaValue.rowArea();
                columnArea = excelAreaValue.columnArea();

                Cell[][] cells = excelData.get(sheet);
                if (cells == null) {
                    logger.error("Sheet[" + sheet + "]不存在");
                    continue field;
                }

                int startRow = rowArea[0] == -1 ? 1 : rowArea[0];
                int endRow = rowArea.length == 1 ? startRow : (rowArea[1] == -1 ? cells.length : rowArea[1]);
                int startColumn = columnArea[0] == -1 ? 1 : columnArea[0];
                int endColumn = columnArea.length == 1 ? startColumn : (columnArea[1] == -1 ? cells[startRow - 1].length : columnArea[1]);

                // 减一计算出下标
                if (startRow != -1) startRow--;
                if (endRow != -1) endRow--;
                if (startColumn != -1) startColumn--;
                if (endColumn != -1) endColumn--;

                // 根据注解参数判断数据类型
                String fieldDataType = "";
                if (startRow == endRow && startColumn == endColumn) {
                    // 一个单元格的数据
                    fieldDataType = "Object";
                } else if (startRow == endRow) {
                    // 某一行的全部数据
                    fieldDataType = "Row";
                } else if (startColumn == endColumn) {
                    // 某一列的全部数据
                    fieldDataType = "Column";
                } else if (startRow != endRow && startColumn != endColumn) {
                    // 某一区域的全部数据
                    fieldDataType = "Table";
                }

                logger.info("字段名:[" + field.getName() + "],数据类型:[" + fieldDataType + "],坐标:[" + startRow + "," + endRow + "][" + startColumn + "," + endColumn + "]");

                if ("".equals(fieldDataType)) {
                    logger.error(field.getName() + "：类型错误");
                    continue field;
                }
                try {
                    // 当前字段的Class是否使用@ExcelObject
                    boolean flag = ClassUtil.getFieldClass(field).isAnnotationPresent(ExcelObject.class);

                    if ("Object".equals(fieldDataType)) {
                        Cell cell = null;
                        if (cells.length >= startRow + 1 && cells[startRow].length >= startColumn + 1) {
                            cell = cells[startRow][startColumn];
                            if (!flag) {
                                // 字段为基础类型(String,Object...)
                                field.set(obj, formatExcelValue(cell, field));
                            } else {
                                Class fieldClass = ClassUtil.getFieldClass(field);
                                Object instance = fieldClass.newInstance();

                                Field f = fieldClass.getDeclaredField(field.getName());
                                if (f == null) {
                                    logger.error(fieldClass.getName() + "[" + field.getName() + "]未找到");
                                    continue field;
                                }
                                // 设置属性可访问
                                f.setAccessible(true);
                                f.set(instance, formatExcelValue(cell, f));
                                field.set(obj, instance);
                            }
                        }
                    } else {
                        if (field.getType() == Object.class) {
                            addFieldData_default(startRow, endRow, startColumn, endColumn, cells, field, obj);
                        } else {
                            if (!flag) {
                                addFieldData_object(startRow, endRow, startColumn, endColumn, cells, field, obj, fieldDataType);
                            } else if (flag) {
                                addFieldData_entity(startRow, endRow, startColumn, endColumn, cells, field, obj, fieldDataType);
                            }
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
//                    logger.error("字段[" + field.getName() + "]解析失败: "+e.toString());
                } catch (InstantiationException e) {
                    e.printStackTrace();
//                    logger.error("字段[" + field.getName() + "]解析失败: "+e.toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
//                    logger.error("字段[" + field.getName() + "]解析失败: "+e.toString());
                } catch (Exception e) {
                    e.printStackTrace();
//                    logger.error("字段[" + field.getName() + "]解析失败: "+e.toString());
                }
            }
        }
        return obj;
    }

    /**
     * 装填字段数据
     *
     * @param startRowIndex    开始行index
     * @param endRowIndex      结束行index
     * @param startColumnIndex 开始列index
     * @param endColumnIndex   结束列index
     * @param cells            当前字段所在Sheet的全部单元格
     * @param field            当前字段
     * @param obj              字段所在的实体类实例化的对象
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private void addFieldData_default(final int startRowIndex, final int endRowIndex,
                                      final int startColumnIndex, final int endColumnIndex,
                                      final Cell[][] cells, final Field field, Object obj)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        Object[][] rootData = new Object[endRowIndex - startRowIndex + 1][];
        for (int r = startRowIndex; r <= endRowIndex; r++) {
            Object[] rowData = new Object[endColumnIndex - startColumnIndex + 1];
            if (cells[r] != null && cells[r].length != 0) {
                int maxColumnIndex = cells[r].length - 1;
                if (maxColumnIndex > endColumnIndex) {
                    maxColumnIndex = endColumnIndex;
                }
                for (int c = startColumnIndex; c <= maxColumnIndex; c++) {
                    Cell cell = cells[r][c];
                    rowData[c - startColumnIndex] = formatExcelValue(cell, field);
                }
            }
            rootData[r - startRowIndex] = rowData;
        }
        field.set(obj, rootData);
    }

    /**
     * 装填数组类型的字段
     *
     * @param startRowIndex    开始行index
     * @param endRowIndex      结束行index
     * @param startColumnIndex 开始列index
     * @param endColumnIndex   结束列index
     * @param cells            当前字段所在Sheet的全部单元格
     * @param field            当前字段
     * @param obj              字段所在的实体类实例化的对象
     * @param fieldDataType    根据注解参数判断出的数据类型
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    private void addFieldData_object(final int startRowIndex, final int endRowIndex,
                                     final int startColumnIndex, final int endColumnIndex,
                                     final Cell[][] cells, final Field field, Object obj, String fieldDataType)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        int maxRowIndex = cells.length - 1;
        if (maxRowIndex > endRowIndex) {
            maxRowIndex = endRowIndex;
        }
        int maxColumnIndex = cells[startRowIndex].length - 1;
        if (maxColumnIndex > endColumnIndex) {
            maxColumnIndex = endColumnIndex;
        }
        if ("Column".equals(fieldDataType)) {
            Object[] objArr = new Object[endRowIndex - startRowIndex + 1];
            for (int r = startRowIndex; r <= maxRowIndex; r++) {
                if (cells.length >= r + 1 && cells[r].length >= startColumnIndex + 1) {
                    Cell cell = cells[r][startColumnIndex];
                    objArr[r - startRowIndex] = formatExcelValue(cell, field);
                }
            }
            field.set(obj, Arrays.copyOf(objArr, objArr.length, (Class) field.getType()));
        } else if ("Row".equals(fieldDataType)) {
            Object[] objArr = new Object[endColumnIndex - startColumnIndex + 1];
            if (cells.length >= startRowIndex + 1) {
                for (int c = startColumnIndex; c <= maxColumnIndex; c++) {
                    if (cells[startRowIndex].length >= c + 1) {
                        Cell cell = cells[startRowIndex][c];
                        objArr[c - startColumnIndex] = formatExcelValue(cell, field);
                    }
                }
            }
            field.set(obj, Arrays.copyOf(objArr, objArr.length, (Class) field.getType()));
        } else if ("Table".equals(fieldDataType)) {
            Object[][] objArr = new Object[endRowIndex - startRowIndex + 1][];
            for (int r = startRowIndex; r <= maxRowIndex; r++) {
                if (cells.length >= r + 1) {
                    Object[] objs = new Object[endColumnIndex - startColumnIndex + 1];
                    for (int c = startColumnIndex; c <= maxColumnIndex; c++) {
                        if (cells[r].length >= c + 1) {
                            Cell cell = cells[r][c];
                            objs[c - startColumnIndex] = formatExcelValue(cell, field);
                        }
                    }
                    String className = field.getType().getName();
                    objArr[r - startRowIndex] = Arrays.copyOf(objs, objs.length, (Class) Class.forName(className.substring(1)));
                }
            }
            field.set(obj, Arrays.copyOf(objArr, objArr.length, (Class) field.getType()));
        }
    }

    private void addFieldData_entity(final int startRowIndex, final int endRowIndex,
                                     final int startColumnIndex, final int endColumnIndex,
                                     final Cell[][] cells, final Field field, Object obj, String fieldDataType)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException, NoSuchFieldException, SecurityException {
        int maxRowIndex = cells.length - 1;
        if (maxRowIndex > endRowIndex) {
            maxRowIndex = endRowIndex;
        }
        int maxColumnIndex = cells[startRowIndex].length - 1;
        if (maxColumnIndex > endColumnIndex) {
            maxColumnIndex = endColumnIndex;
        }

        Map<Integer, Field> fieldMap_row = new HashMap<>();
        Map<Integer, Field> fieldMap_column = new HashMap<>();

        Class fieldClass = ClassUtil.getFieldClass(field);
        Field[] fields = fieldClass.getDeclaredFields();
        for (int f = 0; f < fields.length; f++) {
            Field objectField = fields[f];
            // 设置属性可访问
            objectField.setAccessible(true);
            if (objectField.isAnnotationPresent(ExcelObject.class)) {
                ExcelObject excelObject = objectField.getAnnotation(ExcelObject.class);
                int row = excelObject.row();
                int column = excelObject.column();

                if (row != -1) fieldMap_row.put(row - 1, objectField);
                if (column != -1) fieldMap_column.put(column - 1, objectField);
            }
        }

        if ("Column".equals(fieldDataType)) {
            Object columnObject = addFieldData_entity_detail(startColumnIndex, startRowIndex, endRowIndex,
                    cells, field, fieldDataType, fieldMap_row, fieldMap_column);
            field.set(obj, columnObject);

        } else if ("Row".equals(fieldDataType)) {
            Object rowObject = addFieldData_entity_detail(startRowIndex, startColumnIndex, endColumnIndex,
                    cells, field, fieldDataType, fieldMap_row, fieldMap_column);
            field.set(obj, rowObject);

        } else if ("Table".equals(fieldDataType)) {
            if (fieldMap_column.size() > 0) {
                Object[] objArr = new Object[endRowIndex - startRowIndex + 1];
                for (int r = startRowIndex; r <= maxRowIndex; r++) {
                    Object rowObject = addFieldData_entity_detail(r, startColumnIndex, maxColumnIndex,
                            cells, field, "Row", fieldMap_row, fieldMap_column);
                    objArr[r - startRowIndex] = rowObject;
                }
                field.set(obj, Arrays.copyOf(objArr, objArr.length, (Class) field.getType()));
            } else if (fieldMap_row.size() > 0) {
                Object[] objArr = new Object[endColumnIndex - startColumnIndex + 1];
                for (int c = startColumnIndex; c <= endColumnIndex; c++) {
                    Object columnObject = addFieldData_entity_detail(c, startRowIndex, maxRowIndex,
                            cells, field, "Column", fieldMap_row, fieldMap_column);
                    objArr[c - startColumnIndex] = columnObject;
                }
                field.set(obj, Arrays.copyOf(objArr, objArr.length, (Class) field.getType()));
            }
        }
    }

    private Object addFieldData_entity_detail(final int index, final int start, final int end,
                                              final Cell[][] cells, final Field field, final String fieldDataType,
                                              final Map<Integer, Field> fieldMap_row, final Map<Integer, Field> fieldMap_column)
            throws InstantiationException, IllegalAccessException {

        Class fieldClass = ClassUtil.getFieldClass(field);
        Object instance = fieldClass.newInstance();

        if ("Row".equals(fieldDataType)) {
            if (cells.length < index + 1) {
                return null;
            }
            Cell[] thisRow = cells[index];
            for (int c = start; c <= end; c++) {
                // 当前列的字段
                Field f = fieldMap_column.get(c);
                if (f != null && thisRow.length >= c + 1) {
                    Cell cell = thisRow[c];
                    Object value = formatExcelValue(cell, f);
                    f.set(instance, value);
                }
            }
        } else if ("Column".equals(fieldDataType)) {
            if (cells.length < end + 1) {
                return null;
            }
            for (int r = start; r <= end; r++) {
                // 当前行的字段
                Field f = fieldMap_row.get(r);
                if (f != null && cells[r].length >= index + 1) {
                    Cell cell = cells[r][index];
                    Object value = formatExcelValue(cell, f);
                    f.set(instance, value);
                }
            }
        }
        return instance;
    }

    private Object formatExcelValue(Cell cell, Field field) {
        if (cell == null) {
            return null;
        }
        Class<?> fieldType = ClassUtil.getFieldClass(field);

        String stringValue = "";
        Object value = null;

        CellType cellType = cell.getCellType();
        switch (cellType) {
            case STRING:
                stringValue = cell.getStringCellValue();
                value = stringValue;
                break;
            case NUMERIC:
                if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                    double doubleValue = cell.getNumericCellValue();
                    value = org.apache.poi.ss.usermodel.DateUtil.getJavaDate(doubleValue);
                    stringValue = DateUtil.format((Date) value);
                } else {
                    // 这种用BigDecimal包装再获取plainString，可以防止获取到科学计数值
                    stringValue = new BigDecimal(cell.getNumericCellValue()).toPlainString();
                    value = new BigDecimal(stringValue);
                }
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                stringValue = String.valueOf(value);
                break;
            case FORMULA:
                // 计算
                if (HSSF_FormulaEvaluator != null) {
                    CellValue evaluate = HSSF_FormulaEvaluator.evaluate(cell);
                    stringValue = evaluate.formatAsString();
                } else if (XSSF_FormulaEvaluator != null) {
                    CellValue evaluate = XSSF_FormulaEvaluator.evaluate(cell);
                    stringValue = evaluate.formatAsString();
                } else {
                    stringValue = cell.getCellFormula();
                }
                value = stringValue;
                break;
            case BLANK:
                stringValue = "";
                value = stringValue;
                break;
            case ERROR:
                stringValue = "ERROR VALUE";
                value = stringValue;
                break;
            default:
                stringValue = "UNKNOW VALUE";
                value = stringValue;
                break;
        }

        Object objecValue = null;
        if (fieldType == String.class) {
            objecValue = stringValue;
        } else if (fieldType == int.class || fieldType == Integer.class) {
            objecValue = new BigDecimal(stringValue).intValue();
        } else if (fieldType == double.class || fieldType == Double.class) {
            objecValue = new BigDecimal(stringValue).doubleValue();
        } else if (fieldType == long.class || fieldType == Long.class) {
            objecValue = new BigDecimal(stringValue).longValue();
        } else if (fieldType == Date.class) {
            objecValue = value;
        } else if (fieldType == java.sql.Date.class) {
            objecValue = new java.sql.Date(((Date) value).getTime());
        } else if (fieldType == Object.class) {
            objecValue = value;
        }

        return objecValue;

    }

    /**
     * 根据sheet的名字读取Excel的内容
     *
     * @param inputStream
     * @param excelName
     * @param sheetNames  为空时读取全部sheet
     * @return
     */
    private Map<String, Cell[][]> readExcelFileData(InputStream inputStream, String excelName, String[] sheetNames) {
        workBook = getWorkbook(inputStream, excelName);

        if (workBook != null) {
            if (sheetNames == null || sheetNames.length == 0) {
                // 获取所有的工作表的的数量
                int numOfSheet = workBook.getNumberOfSheets();
                Sheet[] sheets = new Sheet[numOfSheet];
                for (int i = 0; i < numOfSheet; i++) {
                    sheets[i] = workBook.getSheetAt(i);
                }
                return readSheetData(sheets);
            } else {
                Sheet[] sheets = new Sheet[sheetNames.length];
                for (int i = 0; i < sheetNames.length; i++) {
                    Sheet sheet = workBook.getSheet(sheetNames[i]);
                    sheets[i] = sheet;
                }
                return readSheetData(sheets);
            }
        }
        return null;
    }

    /**
     * 获取workbook
     *
     * @param inputStream excel文件流
     * @param excelName   excel名称
     * @return
     */
    private Workbook getWorkbook(InputStream inputStream, String excelName) {
        Workbook workbook = null;
        try {
            // excel后缀
            String extension = FilenameUtils.getExtension(excelName);
            /**
             * 然后再读取文件的时候，应该excel文件的后缀名在不同的版本中对应的解析类不一样
             * 要对fileName进行后缀的解析
             */
            if ("xls".equals(extension)) {
                workbook = new HSSFWorkbook(inputStream);
                // 拿到计算公式eval
                HSSF_FormulaEvaluator = new HSSFFormulaEvaluator((HSSFWorkbook) workbook);
            } else if ("xlsx".equals(extension)) {
                workbook = new XSSFWorkbook(inputStream);
                // 拿到计算公式eval
                XSSF_FormulaEvaluator = new XSSFFormulaEvaluator((XSSFWorkbook) workbook);
            }

            // 获取所有的工作表的的数量
            int numOfSheet = workbook.getNumberOfSheets();
            logger.info("读取Excel[" + excelName + "],Sheet个数:" + numOfSheet);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return workbook;
    }

    private Map<String, Cell[][]> readSheetData(Sheet[] sheets) {
        Map<String, Cell[][]> resultMap = new HashMap<>();
        // 遍历Sheet
        sheet:
        for (Sheet sheet : sheets) {
            if (sheet == null) {
                continue sheet;
            }
            String sheetName = sheet.getSheetName();

            // sheet中一行数据都没有则返回-1，只有第一行有数据则返回0，最后有数据的行是第n行则返回 n-1
            int maxRowNum = sheet.getLastRowNum();
            Cell[][] rowArr = new Cell[maxRowNum + 1][];

            // 遍历sheet每一行数据
            row:
            for (int rowNum = 0; rowNum < maxRowNum + 1; rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    rowArr[rowNum] = new Cell[0];
                    continue row;
                }
                // row中一列数据都没有则返回-1，只有第一列有数据则返回1，最后有数据的列是第n列则返回 n
                short lastCellNum = row.getLastCellNum();
                if (lastCellNum == -1) {
                    rowArr[rowNum] = new Cell[0];
                    continue row;
                } else {
                    rowArr[rowNum] = new Cell[lastCellNum];
                }

                // 遍历每行的单元格
                cell:
                for (int columnNum = 0; columnNum < lastCellNum; columnNum++) {
                    Cell cell = row.getCell(columnNum);
                    rowArr[rowNum][columnNum] = cell;
                }
            }
            resultMap.put(sheetName, rowArr);
        }
        return resultMap;
    }

    /**
     * 根据模板导出excel文件
     *
     * @param outputStream 导出的文件流
     * @param templatePath 模板文件路径
     * @param excelData    添加到模板文件的数据
     */
    public void exportExcelFile(OutputStream outputStream, String templatePath, Object excelData) {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);
        workBook = copyWorkBook(FilenameUtils.getName(templatePath), inputStream);

        setExcelData(workBook, excelData);

        try {
            workBook.write(outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制Sheet
     *
     * @param wb
     * @param copyTarget   要复制的Sheet名字
     * @param newSheetName 复制后的Sheet名字
     * @return
     */
    public Sheet copySheet(Workbook wb, String copyTarget, String newSheetName) {
        int copySheetIndex = wb.getSheetIndex(copyTarget);
        if (copySheetIndex < 0) {
            return null;
        }
        wb.setSheetHidden(copySheetIndex, true);
        Sheet newSheet = wb.cloneSheet(copySheetIndex);
        int newSheetIndex = wb.getSheetIndex(newSheet);
        wb.setSheetName(newSheetIndex, newSheetName);
        return wb.getSheetAt(newSheetIndex);
    }

    /**
     * 将excelData中的数据保存到Excel中
     *
     * @param workBook
     * @param excelData
     */
    public void setExcelData(Workbook workBook, Object excelData) {
        setExcelData(workBook, excelData, null);
    }

    /**
     * 将excelData中的数据保存到Excel中
     *
     * @param wb
     * @param excelData
     * @param copySheet
     */
    public void setExcelData(Workbook wb, Object excelData, Sheet copySheet) {
        workBook = wb;
        Field[] fields = excelData.getClass().getDeclaredFields();
        field:
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // aa 设置属性可访问
            field.setAccessible(true);
            // aa 判断是否有注解
            if (field.isAnnotationPresent(ExcelAreaValue.class)) {
                ExcelAreaValue excelAreaValue = field.getAnnotation(ExcelAreaValue.class);

                Sheet sheet = null;
                if (copySheet == null) {
                    String sheetName = excelAreaValue.sheet();
                    if (sheetName == null || "".equals(sheetName)) {
                        logger.error("请填写Sheet名称");
                        continue field;
                    } else {
                        sheet = workBook.getSheet(sheetName);
                    }
                } else {
                    sheet = copySheet;
                }

                // excel设置公式自动计算
                sheet.setForceFormulaRecalculation(true);

                Object value = null;
                try {
                    value = field.get(excelData);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (value == null) {
                    logger.info(field.getName() + "-未设定值");
                    continue field;
                }

                int[] rowArea = excelAreaValue.rowArea();
                int[] columnArea = excelAreaValue.columnArea();

                int startRow = rowArea[0];
                int endRow = rowArea.length == 1 ? startRow : rowArea[1];
                int startColumn = columnArea[0];
                int endColumn = columnArea.length == 1 ? startColumn : columnArea[1];

                // 是否是多行
                boolean isMultirow = true;
                // 是否是多列
                boolean isMulticolumn = true;
                if (startRow == endRow && startRow != -1) {
                    isMultirow = false;
                }
                if (startColumn == endColumn && startColumn != -1) {
                    isMulticolumn = false;
                }

                String fieldDataType = "";
                if (!isMultirow && !isMulticolumn) {
                    fieldDataType = "Object";
                } else if (!isMultirow) {
                    fieldDataType = "Row";
                } else if (!isMulticolumn) {
                    fieldDataType = "Column";
                } else if (isMultirow && isMulticolumn) {
                    fieldDataType = "Table";
                }

                // 计算下标
                startRow = startRow == -1 ? 0 : (startRow - 1);
                endRow = endRow == -1 ? -1 : (endRow - 1);
                startColumn = startColumn == -1 ? 0 : (startColumn - 1);
                endColumn = endColumn == -1 ? -1 : (endColumn - 1);

                logger.info("字段名:[" + field.getName() + "],数据类型:[" + fieldDataType + "],坐标:[" + startRow + "," + endRow + "][" + startColumn + "," + endColumn + "]");

                Class rootClass = ClassUtil.getRootClass(value.getClass());
                if (List.class.isAssignableFrom(rootClass)) {
                    rootClass = ((List) value).get(0).getClass();
                }
                // 是否使用@ExcelObject
                boolean flag = rootClass.isAnnotationPresent(ExcelObject.class);
                if (!flag) {
                    // 字段为基础类型(String,Object...)
                    setCellValue_object(startRow, endRow, startColumn, endColumn, field, value, fieldDataType, sheet);
                } else {
                    setCellValue_entity(startRow, endRow, startColumn, endColumn, field, value, fieldDataType, sheet);
                }
            }
        }
    }

    /**
     * 将实体例的字段装填到excel单元格中
     *
     * @param startRowIndex
     * @param endRowIndex
     * @param startColumnIndex
     * @param endColumnIndex
     * @param field
     * @param entity
     * @param fieldDataType
     * @param sheet
     */
    private void setCellValue_entity(final int startRowIndex, final int endRowIndex,
                                     final int startColumnIndex, final int endColumnIndex,
                                     final Field field, final Object entity,
                                     final String fieldDataType, Sheet sheet) {
        Class fieldClass = ClassUtil.getFieldClass(field);
        CellStyle cellStyle = CellUtil.getCellStyle(workBook, field);
        Boolean isFormula = isFormula(field);

        if ("Object".equals(fieldDataType)) {
            try {
                Field f = fieldClass.getDeclaredField(field.getName());
                if (f == null) {
                    logger.error(fieldClass.getName() + "[" + field.getName() + "]未找到");
                    return;
                }
                // 设置属性可访问
                f.setAccessible(true);
                Object value = f.get(entity);

                // 获取单元格样式
                CellStyle style = CellUtil.getCellStyle(workBook, f);
                if (style == null) {
                    style = cellStyle;
                }

                // 判断是否是公式
                Boolean formula = isFormula(f);
                if (formula == null) {
                    formula = isFormula;
                }

                setValue(sheet, startRowIndex, startColumnIndex, value, style, formula);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        } else {
            Map<Integer, Field> fieldMap_row = new HashMap<>();
            Map<Integer, Field> fieldMap_column = new HashMap<>();

            Field[] fields = fieldClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                Field objectField = fields[i];
                // 设置属性可访问
                objectField.setAccessible(true);
                if (objectField.isAnnotationPresent(ExcelObject.class)) {
                    ExcelObject excelObject = objectField.getAnnotation(ExcelObject.class);
                    int row = excelObject.row();
                    int column = excelObject.column();

                    if (row != -1) fieldMap_row.put(row - 1, objectField);
                    if (column != -1) fieldMap_column.put(column - 1, objectField);
                }
            }

            if ("Column".equals(fieldDataType)) {
                Object value = null;
                if (entity.getClass().isArray()) {
                    value = ((Object[]) entity)[0];
                } else if (List.class.isAssignableFrom(entity.getClass())) {
                    value = ((List) entity).get(0);
                } else {
                    value = entity;
                }
                setCellValue_entity_detail(startColumnIndex, startRowIndex, endRowIndex, fieldDataType, fieldMap_row, value, sheet, field);
            } else if ("Row".equals(fieldDataType)) {
                Object value = null;
                if (entity.getClass().isArray()) {
                    value = ((Object[]) entity)[0];
                } else if (List.class.isAssignableFrom(entity.getClass())) {
                    value = ((List) entity).get(0);
                } else {
                    value = entity;
                }
                setCellValue_entity_detail(startRowIndex, startColumnIndex, endColumnIndex, fieldDataType, fieldMap_column, value, sheet, field);
            } else if ("Table".equals(fieldDataType)) {
                Object[] entityArr = null;
                if (List.class.isAssignableFrom(entity.getClass())) {
                    entityArr = ((List) entity).toArray();
                } else {
                    entityArr = (Object[]) entity;
                }
                if (fieldMap_column.size() > 0) {
                    int maxRow = endRowIndex;
                    if (maxRow == -1) {
                        maxRow = startRowIndex + entityArr.length - 1;
                    }
                    for (int r = startRowIndex; r <= maxRow; r++) {
                        Object value = entityArr[r - startRowIndex];
                        setCellValue_entity_detail(r, startColumnIndex, endColumnIndex, "Row", fieldMap_column, value, sheet, field);
                    }
                }
                if (fieldMap_row.size() > 0) {
                    int maxColumn = endColumnIndex;
                    if (maxColumn == -1) {
                        maxColumn = startColumnIndex + entityArr.length - 1;
                    }
                    for (int c = startColumnIndex; c <= maxColumn; c++) {
                        Object value = entityArr[c - startColumnIndex];
                        setCellValue_entity_detail(c, startRowIndex, endRowIndex, "Column", fieldMap_row, value, sheet, field);
                    }
                }
            }
        }
    }

    /**
     * 将实体例的字段装填到excel单元格中
     *
     * @param index
     * @param start
     * @param end
     * @param fieldDataType
     * @param fieldMap
     * @param value
     * @param sheet
     * @param field
     */
    private void setCellValue_entity_detail(final int index, final int start, final int end,
                                            final String fieldDataType, final Map<Integer, Field> fieldMap,
                                            final Object value, Sheet sheet, final Field field) {
        try {
            CellStyle cellStyle = CellUtil.getCellStyle(workBook, field);
            Boolean isFormula = isFormula(field);

            for (Map.Entry<Integer, Field> entry : fieldMap.entrySet()) {
                Integer key = entry.getKey();
                Field f = entry.getValue();
                Object o = f.get(value);

                // 获取单元格样式
                CellStyle style = CellUtil.getCellStyle(workBook, f);
                if (style == null) {
                    style = cellStyle;
                }

                // 判断是否是公式
                Boolean formula = isFormula(f);
                if (formula == null) {
                    formula = isFormula;
                }

                if ("Row".equals(fieldDataType)) {
                    setValue(sheet, index, key, o, style, formula);
                } else if ("Column".equals(fieldDataType)) {
                    setValue(sheet, key, index, o, style, formula);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 装填数组类型的字段
     *
     * @param startRowIndex    开始行index
     * @param endRowIndex      结束行index
     * @param startColumnIndex 开始列index
     * @param endColumnIndex   结束列index
     * @param field
     * @param value            要装填的值
     * @param fieldDataType    根据注解参数判断出的数据类型
     * @param sheet            sheet名称
     */
    private void setCellValue_object(final int startRowIndex, final int endRowIndex,
                                     final int startColumnIndex, final int endColumnIndex,
                                     final Field field, final Object value,
                                     final String fieldDataType, Sheet sheet) {
        CellStyle cellStyle = CellUtil.getCellStyle(workBook, field);
        Boolean isFormula = isFormula(field);
        if (isFormula == null) {
            isFormula = false;
        }

        if ("Object".equals(fieldDataType)) {
            setValue(sheet, startRowIndex, startColumnIndex, value, cellStyle, isFormula);
        } else if ("Row".equals(fieldDataType)) {
            Object[] objArr = (Object[]) value;
            int maxColumn = endColumnIndex - startColumnIndex;
            if (endColumnIndex == -1) {
                maxColumn = objArr.length;
            }
            for (int c = 0; c <= maxColumn; c++) {
                if (objArr.length < c + 1) {
                    break;
                }
                setValue(sheet, startRowIndex, startColumnIndex + c, objArr[c], cellStyle, isFormula);
            }
        } else if ("Column".equals(fieldDataType)) {
            Object[] objArr = (Object[]) value;
            int maxRow = endRowIndex - startRowIndex;
            if (endRowIndex == -1) {
                maxRow = objArr.length - 1;
            }
            for (int r = 0; r <= maxRow; r++) {
                if (objArr.length < r + 1) {
                    break;
                }
                setValue(sheet, startRowIndex + r, startColumnIndex, objArr[r], cellStyle, isFormula);
            }
        } else if ("Table".equals(fieldDataType)) {
            Object[][] objArr = (Object[][]) value;
            int maxRow = endRowIndex - startRowIndex;
            if (endRowIndex == -1) {
                maxRow = objArr.length - 1;
            }
            row:
            for (int r = 0; r <= maxRow; r++) {
                if (objArr.length < r + 1) {
                    break row;
                }
                Object[] rowData = objArr[r];
                int maxColumn = endColumnIndex - startColumnIndex;
                if (endColumnIndex == -1) {
                    maxColumn = rowData.length;
                }
                column:
                for (int c = 0; c <= maxColumn; c++) {
                    if (rowData.length < c + 1) {
                        break column;
                    }
                    setValue(sheet, startRowIndex + r, startColumnIndex + c, rowData[c], cellStyle, isFormula);
                }
            }
        }
    }

    /**
     * 给单元格设值
     *
     * @param sheet
     * @param rowIndex
     * @param columnIndex
     * @param objectValue 值
     * @param cellStyle   单元格样式
     * @param isFormula   是否是公式
     */
    private void setValue(Sheet sheet, final int rowIndex, final int columnIndex, final Object objectValue, final CellStyle cellStyle, Boolean isFormula) {
        Row row = sheet.getRow(rowIndex);
        if (row == null) {
            row = sheet.createRow(rowIndex);
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            cell = row.createCell(columnIndex);
        }

        // 设置单元格样式
        if (cellStyle != null) {
            cell.setCellStyle(cellStyle);
        }
        if (objectValue == null) {
            return;
        }

        Class c = objectValue.getClass();
        if (c == String.class) {
            String s = String.valueOf(objectValue);
            if (isFormula != null && isFormula == true) {
                cell.setCellFormula(s);
            } else {
                cell.setCellValue(s);
            }
        } else if (c == int.class || c == Integer.class) {
            cell.setCellValue(new BigDecimal(String.valueOf(objectValue)).intValue());
        } else if (c == double.class || c == Double.class) {
            cell.setCellValue(new BigDecimal(String.valueOf(objectValue)).doubleValue());
        } else if (c == long.class || c == Long.class) {
            cell.setCellValue(new BigDecimal(String.valueOf(objectValue)).longValue());
        } else if (c == Date.class) {
            cell.setCellValue(DateUtil.format(String.valueOf(objectValue)));
        } else if (c == java.sql.Date.class) {
            cell.setCellValue(DateUtil.format(String.valueOf(objectValue)));
        } else if (c == boolean.class || c == Boolean.class) {
            cell.setCellValue(Boolean.parseBoolean(String.valueOf(objectValue)));
        } else if (c == Object.class) {
        }
    }

    /**
     * 复制excel模板的workbook
     *
     * @param templateName        excel模板名称
     * @param templateInputStream excel模板的文件流
     * @return
     */
    public Workbook copyWorkBook(String templateName, InputStream templateInputStream) {
        Workbook templateWorkbook = getWorkbook(templateInputStream, templateName);

        Workbook w = null;
        // 把模板复制到新建的Excel
        if (templateWorkbook instanceof HSSFWorkbook) {
            w = new HSSFWorkbook();
        } else if (templateWorkbook instanceof XSSFWorkbook) {
            w = new XSSFWorkbook();
        }
        w = templateWorkbook;
        try {
            templateInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w;
    }

    /**
     * 是否为公式
     *
     * @param field
     * @return boolean的包装类，请先判断 b == null
     */
    private Boolean isFormula(Field field) {
        if (field.isAnnotationPresent(Formula.class)) {
            Formula annotation = field.getAnnotation(Formula.class);
            return annotation.value();
        }
        return null;
    }

}

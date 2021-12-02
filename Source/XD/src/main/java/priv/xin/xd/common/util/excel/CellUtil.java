package priv.xin.xd.common.util.excel;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.lang.reflect.Field;

/**
 * @author ：lu
 * @date ：2021/11/15 18:37
 */
public class CellUtil {

    /**
     * 获取单元格样式
     *
     * @param workbook
     * @param typeCode
     * @return
     */
    public static CellStyle getCellStyle(Workbook workbook, String typeCode) {
        CellStyle cellStyle = workbook.createCellStyle();

        switch (typeCode) {
            case "1":

                break;
            default:
                // 上边框
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setTopBorderColor(IndexedColors.BLACK.index);

                // 下边框
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBottomBorderColor(IndexedColors.BLACK.index);

                // 左边框
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setLeftBorderColor(IndexedColors.BLACK.index);

                // 右边框
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setRightBorderColor(IndexedColors.BLACK.index);
                break;
        }

        return cellStyle;
    }

    /**
     * 获取单元格样式
     *
     * @param workbook
     * @param sheetName
     * @param row
     * @param column
     * @return
     */
    public static CellStyle getCellStyle(Workbook workbook, String sheetName, int row, int column) {
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet.getRow(row).getCell(column).getCellStyle();
    }

    /**
     * 获取单元格样式
     *
     * @param workbook
     * @param field
     * @return
     */
    public static CellStyle getCellStyle(Workbook workbook, Field field) {
        // 单元格样式
        CellStyle cellStyle = null;
        if (field.isAnnotationPresent(CellStyleCode.class)) {
            CellStyleCode cellStyleCode = field.getAnnotation(CellStyleCode.class);
            String typeCode = cellStyleCode.typeCode();
            cellStyle = getCellStyle(workbook, typeCode);
        }
        return cellStyle;
    }

}

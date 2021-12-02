package priv.xin.xd.expansionService.excel.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import priv.xin.xd.common.properties.ExcelProperties;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.excel.ExcelUtil;
import priv.xin.xd.expansionService.excel.service.ExcelService;
import priv.xin.xd.expansionService.excel.dao.ExcelMapper;
import priv.xin.xd.expansionService.excel.entity.TableInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author ：lu
 * @date ：2021/11/13 15:50
 */
@Service
public class ExcelServiceImpl implements ExcelService {

    @Resource
    private ExcelProperties excelProperties;

    @Resource
    private ExcelMapper excelMapper;

    @Override
    public void exportDBTableInfo(HttpServletResponse response) {

        ExcelUtil excelUtil = new ExcelUtil();

        TableInfo[] tableInfos = excelMapper.getTableInfo();
        if (ListUtil.isEmpty(tableInfos)) {
            return;
        }

        String templatePath = excelProperties.getDbTableTemplate();
        String fileName = FilenameUtils.getName(templatePath);
        try {
            OutputStream outputStream = response.getOutputStream();
            response.reset();
            response.setHeader("content-disposition",
                    "attachment;filename=" + new String((fileName).getBytes("gb2312"), "ISO8859-1"));
            response.setContentType("APPLICATION/msexcel");

            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(templatePath);
            Workbook workBook = excelUtil.copyWorkBook(FilenameUtils.getName(templatePath), inputStream);

            for (TableInfo tableInfo : tableInfos) {
                String tableName = tableInfo.getTableName();
                Sheet sheet = excelUtil.copySheet(workBook, "template", tableName);
                excelUtil.setExcelData(workBook, tableInfo, sheet);
            }

            try {
                workBook.write(outputStream);
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

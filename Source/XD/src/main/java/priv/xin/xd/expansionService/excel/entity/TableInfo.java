package priv.xin.xd.expansionService.excel.entity;

import priv.xin.xd.common.util.excel.CellStyleCode;
import priv.xin.xd.common.util.excel.ExcelAreaValue;

import java.util.List;

/**
 * @author ：lu
 * @date ：2021/11/13 16:00
 */
public class TableInfo {

    @ExcelAreaValue(rowArea = 1, columnArea = 4)
    private String tableRemark;

    @ExcelAreaValue(rowArea = 1, columnArea = 7)
    private String tableName;

    //    @ExcelAreaValue(rowArea = {5, -1}, columnArea = {2, 5})
    @ExcelAreaValue(rowArea = {5, -1}, columnArea = {2, -1})
    @CellStyleCode
    private List<DbField> fieldsInfo;

    public String getTableRemark() {
        return tableRemark;
    }

    public void setTableRemark(String tableRemark) {
        this.tableRemark = tableRemark;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<DbField> getFieldsInfo() {
        return fieldsInfo;
    }

    public void setFieldsInfo(List<DbField> fieldsInfo) {
        this.fieldsInfo = fieldsInfo;
    }
}

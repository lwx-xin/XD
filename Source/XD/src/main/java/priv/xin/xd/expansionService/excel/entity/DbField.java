package priv.xin.xd.expansionService.excel.entity;

import priv.xin.xd.common.util.excel.ExcelObject;
import priv.xin.xd.common.util.excel.Formula;

/**
 * @author ：lu
 * @date ：2021/11/13 16:08
 */
@ExcelObject
public class DbField {

    @ExcelObject(column = 2)
    @Formula()
    private String fieldNum = "ROW()-4";

    @ExcelObject(column = 3)
    private String fieldRemark;

    @ExcelObject(column = 4)
    private String fieldName;

    @ExcelObject(column = 5)
    private String fieldType;

    @ExcelObject(column = 6)
    private boolean allowNull;

    @ExcelObject(column = 7)
    private String defaultValue;

    @ExcelObject(column = 8)
    private boolean primaryKey;

    public String getFieldNum() {
        return fieldNum;
    }

    public void setFieldNum(String fieldNum) {
        this.fieldNum = fieldNum;
    }

    public String getFieldRemark() {
        return fieldRemark;
    }

    public void setFieldRemark(String fieldRemark) {
        this.fieldRemark = fieldRemark;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public boolean isAllowNull() {
        return allowNull;
    }

    public void setAllowNull(boolean allowNull) {
        this.allowNull = allowNull;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }
}

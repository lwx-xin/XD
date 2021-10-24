package priv.xin.xd.core.entity;

import java.util.Date;

public class Code {
    private String codeId;

    private String codeGroup;

    private String codeName;

    private String codeValue;

    private String codeOrder;

    private Date codeCreateTime;

    private String codeCreateUser;

    private Date codeModifyTime;

    private String codeModifyUser;

    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId == null ? null : codeId.trim();
    }

    public String getCodeGroup() {
        return codeGroup;
    }

    public void setCodeGroup(String codeGroup) {
        this.codeGroup = codeGroup == null ? null : codeGroup.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder == null ? null : codeOrder.trim();
    }

    public Date getCodeCreateTime() {
        return codeCreateTime;
    }

    public void setCodeCreateTime(Date codeCreateTime) {
        this.codeCreateTime = codeCreateTime;
    }

    public String getCodeCreateUser() {
        return codeCreateUser;
    }

    public void setCodeCreateUser(String codeCreateUser) {
        this.codeCreateUser = codeCreateUser == null ? null : codeCreateUser.trim();
    }

    public Date getCodeModifyTime() {
        return codeModifyTime;
    }

    public void setCodeModifyTime(Date codeModifyTime) {
        this.codeModifyTime = codeModifyTime;
    }

    public String getCodeModifyUser() {
        return codeModifyUser;
    }

    public void setCodeModifyUser(String codeModifyUser) {
        this.codeModifyUser = codeModifyUser == null ? null : codeModifyUser.trim();
    }
}
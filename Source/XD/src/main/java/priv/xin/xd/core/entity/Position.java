package priv.xin.xd.core.entity;

import java.util.Date;

public class Position {
    private String positionId;

    private String positionName;

    private String positionDepartmentId;

    private String positionDelFlag;

    private Date positionCreateTime;

    private String positionCreateUser;

    private Date positionModifyTime;

    private String positionModifyUser;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public String getPositionDepartmentId() {
        return positionDepartmentId;
    }

    public void setPositionDepartmentId(String positionDepartmentId) {
        this.positionDepartmentId = positionDepartmentId == null ? null : positionDepartmentId.trim();
    }

    public String getPositionDelFlag() {
        return positionDelFlag;
    }

    public void setPositionDelFlag(String positionDelFlag) {
        this.positionDelFlag = positionDelFlag == null ? null : positionDelFlag.trim();
    }

    public Date getPositionCreateTime() {
        return positionCreateTime;
    }

    public void setPositionCreateTime(Date positionCreateTime) {
        this.positionCreateTime = positionCreateTime;
    }

    public String getPositionCreateUser() {
        return positionCreateUser;
    }

    public void setPositionCreateUser(String positionCreateUser) {
        this.positionCreateUser = positionCreateUser == null ? null : positionCreateUser.trim();
    }

    public Date getPositionModifyTime() {
        return positionModifyTime;
    }

    public void setPositionModifyTime(Date positionModifyTime) {
        this.positionModifyTime = positionModifyTime;
    }

    public String getPositionModifyUser() {
        return positionModifyUser;
    }

    public void setPositionModifyUser(String positionModifyUser) {
        this.positionModifyUser = positionModifyUser == null ? null : positionModifyUser.trim();
    }
}
package priv.xin.xd.core.entity;

import java.util.Date;

public class UserPosition {
    private String userPositionId;

    private String userId;

    private String positionId;

    private String userPositionDelFlag;

    private Date userPositionCreateTime;

    private String userPositionCreateUser;

    private Date userPositionModifyTime;

    private String userPositionModifyUser;

    public String getUserPositionId() {
        return userPositionId;
    }

    public void setUserPositionId(String userPositionId) {
        this.userPositionId = userPositionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getUserPositionDelFlag() {
        return userPositionDelFlag;
    }

    public void setUserPositionDelFlag(String userPositionDelFlag) {
        this.userPositionDelFlag = userPositionDelFlag;
    }

    public Date getUserPositionCreateTime() {
        return userPositionCreateTime;
    }

    public void setUserPositionCreateTime(Date userPositionCreateTime) {
        this.userPositionCreateTime = userPositionCreateTime;
    }

    public String getUserPositionCreateUser() {
        return userPositionCreateUser;
    }

    public void setUserPositionCreateUser(String userPositionCreateUser) {
        this.userPositionCreateUser = userPositionCreateUser;
    }

    public Date getUserPositionModifyTime() {
        return userPositionModifyTime;
    }

    public void setUserPositionModifyTime(Date userPositionModifyTime) {
        this.userPositionModifyTime = userPositionModifyTime;
    }

    public String getUserPositionModifyUser() {
        return userPositionModifyUser;
    }

    public void setUserPositionModifyUser(String userPositionModifyUser) {
        this.userPositionModifyUser = userPositionModifyUser;
    }
}
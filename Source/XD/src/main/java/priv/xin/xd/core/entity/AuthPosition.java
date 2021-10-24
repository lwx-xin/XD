package priv.xin.xd.core.entity;

import java.util.Date;

public class AuthPosition {
    private String authPositionId;

    private String authId;

    private String positionId;

    private String authPositionDelFlag;

    private Date authPositionCreateTime;

    private String authPositionCreateUser;

    private Date authPositionModifyTime;

    private String authPositionModifyUser;

    public String getAuthPositionId() {
        return authPositionId;
    }

    public void setAuthPositionId(String authPositionId) {
        this.authPositionId = authPositionId;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getAuthPositionDelFlag() {
        return authPositionDelFlag;
    }

    public void setAuthPositionDelFlag(String authPositionDelFlag) {
        this.authPositionDelFlag = authPositionDelFlag;
    }

    public Date getAuthPositionCreateTime() {
        return authPositionCreateTime;
    }

    public void setAuthPositionCreateTime(Date authPositionCreateTime) {
        this.authPositionCreateTime = authPositionCreateTime;
    }

    public String getAuthPositionCreateUser() {
        return authPositionCreateUser;
    }

    public void setAuthPositionCreateUser(String authPositionCreateUser) {
        this.authPositionCreateUser = authPositionCreateUser;
    }

    public Date getAuthPositionModifyTime() {
        return authPositionModifyTime;
    }

    public void setAuthPositionModifyTime(Date authPositionModifyTime) {
        this.authPositionModifyTime = authPositionModifyTime;
    }

    public String getAuthPositionModifyUser() {
        return authPositionModifyUser;
    }

    public void setAuthPositionModifyUser(String authPositionModifyUser) {
        this.authPositionModifyUser = authPositionModifyUser;
    }
}
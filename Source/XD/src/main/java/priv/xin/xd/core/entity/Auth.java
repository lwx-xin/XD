package priv.xin.xd.core.entity;

import java.io.Serializable;
import java.util.Date;

public class Auth implements Serializable {
    private String authId;

    private String authName;

    private String authDelFlag;

    private Date authCreateTime;

    private String authCreateUser;

    private Date authModifyTime;

    private String authModifyUser;

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getAuthName() {
        return authName;
    }

    public void setAuthName(String authName) {
        this.authName = authName == null ? null : authName.trim();
    }

    public String getAuthDelFlag() {
        return authDelFlag;
    }

    public void setAuthDelFlag(String authDelFlag) {
        this.authDelFlag = authDelFlag == null ? null : authDelFlag.trim();
    }

    public Date getAuthCreateTime() {
        return authCreateTime;
    }

    public void setAuthCreateTime(Date authCreateTime) {
        this.authCreateTime = authCreateTime;
    }

    public String getAuthCreateUser() {
        return authCreateUser;
    }

    public void setAuthCreateUser(String authCreateUser) {
        this.authCreateUser = authCreateUser == null ? null : authCreateUser.trim();
    }

    public Date getAuthModifyTime() {
        return authModifyTime;
    }

    public void setAuthModifyTime(Date authModifyTime) {
        this.authModifyTime = authModifyTime;
    }

    public String getAuthModifyUser() {
        return authModifyUser;
    }

    public void setAuthModifyUser(String authModifyUser) {
        this.authModifyUser = authModifyUser == null ? null : authModifyUser.trim();
    }
}
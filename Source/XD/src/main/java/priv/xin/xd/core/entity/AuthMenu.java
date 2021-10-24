package priv.xin.xd.core.entity;

import java.util.Date;

public class AuthMenu {
    private String authMenuId;

    private String authId;

    private String menuId;

    private String authMenuDelFlag;

    private Date authMenuCreateTime;

    private String authMenuCreateUser;

    private Date authMenuModifyTime;

    private String authMenuModifyUser;

    public String getAuthMenuId() {
        return authMenuId;
    }

    public void setAuthMenuId(String authMenuId) {
        this.authMenuId = authMenuId == null ? null : authMenuId.trim();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId == null ? null : menuId.trim();
    }

    public String getAuthMenuDelFlag() {
        return authMenuDelFlag;
    }

    public void setAuthMenuDelFlag(String authMenuDelFlag) {
        this.authMenuDelFlag = authMenuDelFlag == null ? null : authMenuDelFlag.trim();
    }

    public Date getAuthMenuCreateTime() {
        return authMenuCreateTime;
    }

    public void setAuthMenuCreateTime(Date authMenuCreateTime) {
        this.authMenuCreateTime = authMenuCreateTime;
    }

    public String getAuthMenuCreateUser() {
        return authMenuCreateUser;
    }

    public void setAuthMenuCreateUser(String authMenuCreateUser) {
        this.authMenuCreateUser = authMenuCreateUser == null ? null : authMenuCreateUser.trim();
    }

    public Date getAuthMenuModifyTime() {
        return authMenuModifyTime;
    }

    public void setAuthMenuModifyTime(Date authMenuModifyTime) {
        this.authMenuModifyTime = authMenuModifyTime;
    }

    public String getAuthMenuModifyUser() {
        return authMenuModifyUser;
    }

    public void setAuthMenuModifyUser(String authMenuModifyUser) {
        this.authMenuModifyUser = authMenuModifyUser == null ? null : authMenuModifyUser.trim();
    }
}
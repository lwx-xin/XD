package priv.xin.xd.core.entity;

import java.util.Date;

public class AuthUrl {
    private String authUrlId;

    private String authId;

    private String urlId;

    private String authUrlDelFlag;

    private Date authUrlCreateTime;

    private String authUrlCreateUser;

    private Date authUrlModifyTime;

    private String authUrlModifyUser;

    public String getAuthUrlId() {
        return authUrlId;
    }

    public void setAuthUrlId(String authUrlId) {
        this.authUrlId = authUrlId == null ? null : authUrlId.trim();
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId == null ? null : authId.trim();
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId == null ? null : urlId.trim();
    }

    public String getAuthUrlDelFlag() {
        return authUrlDelFlag;
    }

    public void setAuthUrlDelFlag(String authUrlDelFlag) {
        this.authUrlDelFlag = authUrlDelFlag == null ? null : authUrlDelFlag.trim();
    }

    public Date getAuthUrlCreateTime() {
        return authUrlCreateTime;
    }

    public void setAuthUrlCreateTime(Date authUrlCreateTime) {
        this.authUrlCreateTime = authUrlCreateTime;
    }

    public String getAuthUrlCreateUser() {
        return authUrlCreateUser;
    }

    public void setAuthUrlCreateUser(String authUrlCreateUser) {
        this.authUrlCreateUser = authUrlCreateUser == null ? null : authUrlCreateUser.trim();
    }

    public Date getAuthUrlModifyTime() {
        return authUrlModifyTime;
    }

    public void setAuthUrlModifyTime(Date authUrlModifyTime) {
        this.authUrlModifyTime = authUrlModifyTime;
    }

    public String getAuthUrlModifyUser() {
        return authUrlModifyUser;
    }

    public void setAuthUrlModifyUser(String authUrlModifyUser) {
        this.authUrlModifyUser = authUrlModifyUser == null ? null : authUrlModifyUser.trim();
    }
}
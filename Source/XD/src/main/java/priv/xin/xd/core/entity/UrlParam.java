package priv.xin.xd.core.entity;

import java.io.Serializable;
import java.util.Date;

public class UrlParam implements Serializable {

    private static final long serialVersionUID = 126030705671841057L;

    private String urlParamId;

    private String urlId;

    private String urlParamName;

    private String urlParamValue;

    private String urlParamRequired;

    private String urlParamRemark;

    private String urlParamErrHint;

    private String urlParamDelFlag;

    private Date urlParamCreateTime;

    private String urlParamCreateUser;

    private Date urlParamModifyTime;

    private String urlParamModifyUser;

    public String getUrlParamId() {
        return urlParamId;
    }

    public void setUrlParamId(String urlParamId) {
        this.urlParamId = urlParamId == null ? null : urlParamId.trim();
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId == null ? null : urlId.trim();
    }

    public String getUrlParamName() {
        return urlParamName;
    }

    public void setUrlParamName(String urlParamName) {
        this.urlParamName = urlParamName == null ? null : urlParamName.trim();
    }

    public String getUrlParamValue() {
        return urlParamValue;
    }

    public void setUrlParamValue(String urlParamValue) {
        this.urlParamValue = urlParamValue == null ? null : urlParamValue.trim();
    }

    public String getUrlParamRequired() {
        return urlParamRequired;
    }

    public void setUrlParamRequired(String urlParamRequired) {
        this.urlParamRequired = urlParamRequired == null ? null : urlParamRequired.trim();
    }

    public String getUrlParamRemark() {
        return urlParamRemark;
    }

    public void setUrlParamRemark(String urlParamRemark) {
        this.urlParamRemark = urlParamRemark == null ? null : urlParamRemark.trim();
    }

    public String getUrlParamErrHint() {
        return urlParamErrHint;
    }

    public void setUrlParamErrHint(String urlParamErrHint) {
        this.urlParamErrHint = urlParamErrHint == null ? null : urlParamErrHint.trim();
    }

    public String getUrlParamDelFlag() {
        return urlParamDelFlag;
    }

    public void setUrlParamDelFlag(String urlParamDelFlag) {
        this.urlParamDelFlag = urlParamDelFlag == null ? null : urlParamDelFlag.trim();
    }

    public Date getUrlParamCreateTime() {
        return urlParamCreateTime;
    }

    public void setUrlParamCreateTime(Date urlParamCreateTime) {
        this.urlParamCreateTime = urlParamCreateTime;
    }

    public String getUrlParamCreateUser() {
        return urlParamCreateUser;
    }

    public void setUrlParamCreateUser(String urlParamCreateUser) {
        this.urlParamCreateUser = urlParamCreateUser == null ? null : urlParamCreateUser.trim();
    }

    public Date getUrlParamModifyTime() {
        return urlParamModifyTime;
    }

    public void setUrlParamModifyTime(Date urlParamModifyTime) {
        this.urlParamModifyTime = urlParamModifyTime;
    }

    public String getUrlParamModifyUser() {
        return urlParamModifyUser;
    }

    public void setUrlParamModifyUser(String urlParamModifyUser) {
        this.urlParamModifyUser = urlParamModifyUser == null ? null : urlParamModifyUser.trim();
    }
}
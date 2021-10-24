package priv.xin.xd.core.entity;

import java.io.Serializable;
import java.util.Date;

public class Url implements Serializable {

    private static final long serialVersionUID = 2402439958396577742L;

    private String urlId;

    private String urlPath;

    private String urlType;

    private String urlPlatform;

    private String urlRemarks;

    private String urlDelFlag;

    private Date urlCreateTime;

    private String urlCreateUser;

    private Date urlModifyTime;

    private String urlModifyUser;

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getUrlPlatform() {
        return urlPlatform;
    }

    public void setUrlPlatform(String urlPlatform) {
        this.urlPlatform = urlPlatform;
    }

    public String getUrlRemarks() {
        return urlRemarks;
    }

    public void setUrlRemarks(String urlRemarks) {
        this.urlRemarks = urlRemarks;
    }

    public String getUrlDelFlag() {
        return urlDelFlag;
    }

    public void setUrlDelFlag(String urlDelFlag) {
        this.urlDelFlag = urlDelFlag;
    }

    public Date getUrlCreateTime() {
        return urlCreateTime;
    }

    public void setUrlCreateTime(Date urlCreateTime) {
        this.urlCreateTime = urlCreateTime;
    }

    public String getUrlCreateUser() {
        return urlCreateUser;
    }

    public void setUrlCreateUser(String urlCreateUser) {
        this.urlCreateUser = urlCreateUser;
    }

    public Date getUrlModifyTime() {
        return urlModifyTime;
    }

    public void setUrlModifyTime(Date urlModifyTime) {
        this.urlModifyTime = urlModifyTime;
    }

    public String getUrlModifyUser() {
        return urlModifyUser;
    }

    public void setUrlModifyUser(String urlModifyUser) {
        this.urlModifyUser = urlModifyUser;
    }

    @Override
    public String toString() {
        return "Url{" +
                "urlId='" + urlId + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", urlType='" + urlType + '\'' +
                ", urlPlatform='" + urlPlatform + '\'' +
                ", urlRemarks='" + urlRemarks + '\'' +
                ", urlDelFlag='" + urlDelFlag + '\'' +
                ", urlCreateTime=" + urlCreateTime +
                ", urlCreateUser='" + urlCreateUser + '\'' +
                ", urlModifyTime=" + urlModifyTime +
                ", urlModifyUser='" + urlModifyUser + '\'' +
                '}';
    }
}
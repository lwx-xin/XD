package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.UrlParam;

import java.util.List;

/**
 * @author ：lu
 * @date ：2021/8/14 12:12
 */
public class UrlEx extends Url {

    private String urlTypeName;

    private String urlPlatformName;

    private List<Auth> authList;

    private List<UrlParam> urlParamList;

    private int menuCount;

    public String getUrlTypeName() {
        return urlTypeName;
    }

    public void setUrlTypeName(String urlTypeName) {
        this.urlTypeName = urlTypeName;
    }

    public String getUrlPlatformName() {
        return urlPlatformName;
    }

    public void setUrlPlatformName(String urlPlatformName) {
        this.urlPlatformName = urlPlatformName;
    }

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }

    public List<UrlParam> getUrlParamList() {
        return urlParamList;
    }

    public void setUrlParamList(List<UrlParam> urlParamList) {
        this.urlParamList = urlParamList;
    }

    public int getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(int menuCount) {
        this.menuCount = menuCount;
    }
}

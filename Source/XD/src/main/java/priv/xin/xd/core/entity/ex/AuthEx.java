package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Auth;

/**
 * @author ：lu
 * @date ：2021/8/31 19:13
 */
public class AuthEx extends Auth {

    /**
     * 使用当前权限的请求个数
     */
    private int urlCount;

    /**
     * 使用当前权限的菜单个数
     */
    private int menuCount;

    /**
     * 使用当前权限的职位个数
     */
    private int positionCount;

    public int getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(int urlCount) {
        this.urlCount = urlCount;
    }

    public int getMenuCount() {
        return menuCount;
    }

    public void setMenuCount(int menuCount) {
        this.menuCount = menuCount;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(int positionCount) {
        this.positionCount = positionCount;
    }
}

package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.Url;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/2 15:32
 */
public class MenuEx extends Menu implements Serializable {

    private static final long serialVersionUID = -2906945650818103142L;

    /**
     * 子菜单
     */
    private List<MenuEx> childrens;

    /**
     * 菜单请求
     */
    private Url menuUrlDetail;

    /**
     * 菜单权限
     */
    private List<Auth> authList;

    public List<MenuEx> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<MenuEx> childrens) {
        this.childrens = childrens;
    }

    public Url getMenuUrlDetail() {
        return menuUrlDetail;
    }

    public void setMenuUrlDetail(Url menuUrlDetail) {
        this.menuUrlDetail = menuUrlDetail;
    }

    public List<Auth> getAuthList() {
        return authList;
    }

    public void setAuthList(List<Auth> authList) {
        this.authList = authList;
    }
}

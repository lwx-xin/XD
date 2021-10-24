package priv.xin.xd.core.service;

import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;

import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/21 14:18
 */
public interface ISysMenuService {

    /**
     *
     * @param menu
     * @return data: menuTree
     */
    public Result queryTreeList(Menu menu);

    /**
     * 获取菜单的树状结构数据
     * @param menuList
     * @return
     */
    public List<MenuEx> getMenuTree(List<Menu> menuList);

    /**
     *
     * @param menuId
     * @return data: menuDetail
     */
    public Result queryDetail(String menuId);

    public Result updateMenuDetail(String menuId, MenuEx menuEx);

    public Result insertMenuDetail(MenuEx menuEx);

    public Result deleteByMenuId(String menuId);
}

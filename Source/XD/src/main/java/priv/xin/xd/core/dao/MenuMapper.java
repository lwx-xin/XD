package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(Menu record);

    Menu selectByPrimaryKey(String menuId);

    int updateByPrimaryKey(Menu record);

    /**************************************************************************************************/
    /**
     * 获取用户的菜单列表
     *
     * @param userId 用户id
     * @return
     */
    List<Menu> queryByUser(String userId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param menu 实例对象
     * @return 对象列表
     */
    List<Menu> queryAll(Menu menu);

    MenuEx queryDetail(String menuId);
}
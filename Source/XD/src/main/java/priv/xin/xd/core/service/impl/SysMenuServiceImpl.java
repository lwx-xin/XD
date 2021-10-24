package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysMenuService;
import priv.xin.xd.core.dao.AuthMapper;
import priv.xin.xd.core.dao.AuthMenuMapper;
import priv.xin.xd.core.dao.MenuMapper;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.AuthMenu;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author ：lu
 * @date ：2021/7/21 14:18
 */
@Service
public class SysMenuServiceImpl implements ISysMenuService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private AuthMenuMapper authMenuMapper;

    @Resource
    private AuthMapper authMapper;

    @Override
    public Result queryTreeList(Menu menu) {
        List<Menu> menuList = menuMapper.queryAll(menu);
        List<MenuEx> menuTree = getMenuTree(menuList);
        return new Result(true).data("menuTree", menuTree);
    }

    @Override
    public List<MenuEx> getMenuTree(List<Menu> menuList) {
        List<MenuEx> topMenu = new ArrayList<>();
        Map<String, MenuEx> menuMap = new TreeMap<>();

        if (ListUtil.isEmpty(menuList)) {
            return topMenu;
        }

        List<MenuEx> menuExeList = JsonUtil.toList(menuList, MenuEx.class);
        for (MenuEx menuEx : menuExeList) {
            // 上级菜单id
            String menuParent = menuEx.getMenuParent();
            if (StrUtil.isEmpty(menuParent)) {
                // 顶级菜单
                topMenu.add(menuEx);
            } else {
                Integer menuOrder = menuEx.getMenuOrder();
                menuMap.put(menuParent + "_" + menuOrder, menuEx);
            }
        }

        for (MenuEx menuex : topMenu) {
            menuTree(menuMap, menuex);
        }

        return topMenu;
    }

    private void menuTree(Map<String, MenuEx> menuMap, MenuEx menu) {
        List<MenuEx> childrenList = menu.getChildrens();
        if (childrenList == null) {
            childrenList = new ArrayList<>();
        }

        for (Map.Entry<String, MenuEx> entry : menuMap.entrySet()) {
            String key = entry.getKey();
            MenuEx value = entry.getValue();

            String menuId = menu.getMenuId();
            if (key.startsWith(menuId)) {
                menuTree(menuMap, value);
                childrenList.add(value);
            }
        }
        menu.setChildrens(childrenList);
    }

    @Override
    public Result queryDetail(String menuId) {
        if (StrUtil.isEmpty(menuId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.MENU_UNKNOWN);
        }
        MenuEx menuDetail = menuMapper.queryDetail(menuId);
        return new Result(true).data("menuDetail", menuDetail);
    }

    @Override
    public Result updateMenuDetail(String menuId, MenuEx menuEx) {
        if (StrUtil.isEmpty(menuId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.MENU_UNKNOWN);
        }
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        Menu updateMenu = new Menu();
        updateMenu.setMenuId(menuId);
        updateMenu.setMenuText(menuEx.getMenuText());
        updateMenu.setMenuGroup(menuEx.getMenuGroup());
        updateMenu.setMenuUrl(menuEx.getMenuUrl());
        updateMenu.setMenuOrder(menuEx.getMenuOrder());
        updateMenu.setMenuIcon(menuEx.getMenuIcon());
        updateMenu.setMenuModifyUser(operator);

        int updateMenuStatus = menuMapper.updateByPrimaryKey(updateMenu);
        if (updateMenuStatus == 0) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        authMenuMapper.deleteByMenuId(menuId);

        return saveAuthMenu(menuEx, operator, menuId);
    }

    @Override
    public Result insertMenuDetail(MenuEx menuEx) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        String menuId = StrUtil.getUUID();

        Menu insertMenu = new Menu();
        insertMenu.setMenuId(menuId);
        insertMenu.setMenuText(menuEx.getMenuText());
        insertMenu.setMenuParent(menuEx.getMenuParent());
        insertMenu.setMenuGroup(menuEx.getMenuGroup());
        insertMenu.setMenuUrl(menuEx.getMenuUrl());
        insertMenu.setMenuOrder(menuEx.getMenuOrder());
        insertMenu.setMenuIcon(menuEx.getMenuIcon());
        insertMenu.setMenuDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        insertMenu.setMenuCreateUser(operator);
        insertMenu.setMenuModifyUser(operator);

        int insert = menuMapper.insert(insertMenu);
        if (insert == 0) {
            // 回滚数据
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new Result(false);
        }

        return saveAuthMenu(menuEx, operator, menuId);
    }

    @Override
    public Result deleteByMenuId(String menuId) {
        if (StrUtil.isEmpty(menuId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.MENU_UNKNOWN);
        }

        // check
        Result checkResult = new Result(true);
        Menu menuQuery = new Menu();
        menuQuery.setMenuParent(menuId);
        List<Menu> menuList = menuMapper.queryAll(menuQuery);
        if (ListUtil.isNotEmpty(menuList)) {
            checkResult.status(false).message(MessageLevel.ERROR, "请先删除子菜单");
        }

        if (!checkResult.getStatus()) {
            return checkResult;
        }

        int i = menuMapper.deleteByPrimaryKey(menuId);
        if (i == 0) {
            return new Result(false);
        }

        // 删除菜单对应的权限
        authMenuMapper.deleteByMenuId(menuId);
        return new Result(true);
    }

    /**
     * 添加菜单权限信息
     *
     * @param menuEx
     * @param operator
     * @param menuId
     * @return
     */
    private Result saveAuthMenu(MenuEx menuEx, String operator, String menuId) {
        List<Auth> authList = menuEx.getAuthList();
        if (ListUtil.isNotEmpty(authList)) {
            for (Auth auth : authList) {
                String authId = auth.getAuthId();
                if (authMapper.selectByPrimaryKey(authId) == null) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false).message(MessageLevel.ERROR, Message.AUTH_UNKNOWN);
                }

                AuthMenu authMenu = new AuthMenu();
                authMenu.setAuthMenuId(StrUtil.getUUID());
                authMenu.setAuthId(authId);
                authMenu.setMenuId(menuId);
                authMenu.setAuthMenuDelFlag(CodeEnum.DEL_FLAG_1.getValue());
                authMenu.setAuthMenuModifyUser(operator);
                authMenu.setAuthMenuCreateUser(operator);

                if (authMenuMapper.insert(authMenu) == 0) {
                    // 回滚数据
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new Result(false).message(MessageLevel.ERROR, Message.MENU_AUTH_EDIT_ERROR);
                }
            }
        }
        return new Result(true);
    }
}

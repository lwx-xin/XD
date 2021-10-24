package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.AuthMenu;

public interface AuthMenuMapper {
    int deleteByPrimaryKey(String authMenuId);

    int insert(AuthMenu record);

    AuthMenu selectByPrimaryKey(String authMenuId);

    int updateByPrimaryKey(AuthMenu record);

    int deleteByMenuId(String menuId);

}
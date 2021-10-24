package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.AuthUrl;

import java.util.List;

public interface AuthUrlMapper {
    int deleteByPrimaryKey(String authUrlId);

    int insert(AuthUrl record);

    AuthUrl selectByPrimaryKey(String authUrlId);

    int updateByPrimaryKey(AuthUrl record);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param authUrl 实例对象
     * @return 对象列表
     */
    List<AuthUrl> queryAll(AuthUrl authUrl);

    /**
     * 删除当前请求的全部权限
     * @param urlId
     * @return
     */
    int deleteByUrlId(String urlId);
}
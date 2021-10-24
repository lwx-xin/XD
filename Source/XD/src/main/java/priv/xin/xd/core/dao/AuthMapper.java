package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.ex.AuthEx;

import java.util.List;

public interface AuthMapper {
    int deleteByPrimaryKey(String authId);

    int insert(Auth record);

    Auth selectByPrimaryKey(String authId);

    int updateByPrimaryKey(Auth record);

    /**
     * 根据用户ID查询用户拥有的权限
     *
     * @param userId 用户ID
     * @return 权限ID
     */
    List<Auth> queryAuthByUser(String userId);
    /**
     * 通过实体作为筛选条件查询
     *
     * @param auth 实例对象
     * @return 对象列表
     */
    List<Auth> queryAll(Auth auth);

    /**
     * 通过实体作为筛选条件查询(包含使用该权限的请求，菜单，职位个数)
     *
     * @param authEx 实例对象
     * @return 对象列表
     */
    List<AuthEx> queryListLimit(@Param("authEx") AuthEx authEx,@Param("page") Page page);
    int queryListLimitCount(@Param("authEx") AuthEx authEx);

    /**
     * 当前权限被使用的次数（菜单，请求，职位）
     * @param authId
     * @return
     */
    AuthEx queryAuthUsedCount(String authId);
}
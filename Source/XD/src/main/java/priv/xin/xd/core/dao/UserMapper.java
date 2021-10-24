package priv.xin.xd.core.dao;

import org.apache.ibatis.annotations.Param;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.core.entity.User;
import priv.xin.xd.core.entity.ex.UserEx;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String userId);

    int insert(User record);

    User selectByPrimaryKey(String userId);

    int updateByPrimaryKey(User record);

    /**********************************************************************************/
    /**
     * 带条件的分页查询
     *
     * @param userEx 查询条件
     * @param page
     * @return
     */
    public List<UserEx> queryListLimit(@Param("userEx") UserEx userEx, @Param("page") Page page);

    /**
     * 查询复核条件的数据条数
     *
     * @param userEx 查询条件
     * @return
     */
    public int queryListLimitCount(@Param("userEx") UserEx userEx);

    /**
     * 查询用户详细信息(包括职位信息)
     *
     * @param userId
     * @return
     */
    public UserEx queryDetail(@Param("userId") String userId);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param user 实例对象
     * @return 对象列表
     */
    List<User> queryAll(User user);
}
package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.UserPosition;

import java.util.List;

public interface UserPositionMapper {
    int deleteByPrimaryKey(String userPositionId);

    int insert(UserPosition record);

    UserPosition selectByPrimaryKey(String userPositionId);

    int updateByPrimaryKey(UserPosition record);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param userPosition 实例对象
     * @return 对象列表
     */
    List<UserPosition> queryAll(UserPosition userPosition);

    /**
     * 删除用户端全部职位
     * @param userId
     * @return
     */
    int deleteByUserId(String userId);
}
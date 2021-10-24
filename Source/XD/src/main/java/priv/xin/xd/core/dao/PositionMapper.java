package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Position;
import priv.xin.xd.core.entity.ex.PositionEx;

import java.util.List;

public interface PositionMapper {
    int deleteByPrimaryKey(String positionId);

    int insert(Position record);

    Position selectByPrimaryKey(String positionId);

    int updateByPrimaryKey(Position record);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param position 实例对象
     * @return 对象列表
     */
    List<Position> queryAll(Position position);

    PositionEx queryPositionUsedCount(String positionId);
}
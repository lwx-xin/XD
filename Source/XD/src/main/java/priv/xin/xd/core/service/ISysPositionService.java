package priv.xin.xd.core.service;

import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.Position;

/**
 * @author ：lu
 * @date ：2021/7/7 13:15
 */
public interface ISysPositionService {

    /**
     *
     * @param position
     * @return data: positionList
     */
    public Result queryAll(Position position);

    /**
     *
     * @param positionId
     * @return data: positionDetail
     */
    public Result queryDetail(String positionId);

    public Result updatePositionDetail(String positionId, Position position);

    public Result insertPositionDetail(Position position);

    public Result deleteByPositionId(String positionId);
}

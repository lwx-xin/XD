package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysPositionService;
import priv.xin.xd.core.dao.AuthPositionMapper;
import priv.xin.xd.core.dao.PositionMapper;
import priv.xin.xd.core.entity.Position;
import priv.xin.xd.core.entity.ex.PositionEx;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/7 13:16
 */
@Service
public class SysPositionServiceImpl implements ISysPositionService {

    @Resource
    private PositionMapper positionMapper;

    @Resource
    private AuthPositionMapper authPositionMapper;

    @Override
    public Result queryAll(Position position) {
        List<Position> positionList = positionMapper.queryAll(position);
        return new Result(true).data("positionList", positionList);
    }

    @Override
    public Result queryDetail(String positionId) {
        if (StrUtil.isEmpty(positionId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.POSITION_UNKNOWN);
        }
        Position positionDetail = positionMapper.selectByPrimaryKey(positionId);
        return new Result(true).data("positionDetail", positionDetail);
    }

    @Override
    public Result updatePositionDetail(String positionId, Position position) {
        if (StrUtil.isEmpty(positionId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.POSITION_UNKNOWN);
        }
        Position positionUpdate = new Position();
        positionUpdate.setPositionId(positionId);
        positionUpdate.setPositionName(position.getPositionName());
        positionUpdate.setPositionModifyUser(ShiroUtil.getUserId());

        int i = positionMapper.updateByPrimaryKey(positionUpdate);
        if (i == 0) {
            position = positionMapper.selectByPrimaryKey(positionId);
            Result result = new Result(false);
            if (position == null) {
                result.message(MessageLevel.ERROR, Message.POSITION_UNKNOWN);
            }
            return result;
        }
        return new Result(true);
    }

    @Override
    public Result insertPositionDetail(Position position) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        Position positionInsert = new Position();
        positionInsert.setPositionId(StrUtil.getUUID());
        positionInsert.setPositionName(position.getPositionName());
        positionInsert.setPositionDepartmentId(position.getPositionDepartmentId());
        positionInsert.setPositionDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        positionInsert.setPositionCreateUser(operator);
        positionInsert.setPositionModifyUser(operator);

        int i = positionMapper.insert(positionInsert);
        if (i == 0) {
            return new Result(false);
        }
        return new Result(true);
    }

    @Override
    public Result deleteByPositionId(String positionId) {
        if (StrUtil.isEmpty(positionId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.POSITION_UNKNOWN);
        }

        // check
        Result checkResult = new Result(true);
        PositionEx positionUsedCount = positionMapper.queryPositionUsedCount(positionId);
        int userCount = positionUsedCount.getUserCount();
        // 是否有用户使用过该职位
        if (userCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "该职位已被" + userCount + "个用户使用");
        }

        if (!checkResult.getStatus()) {
            return checkResult;
        }

        int i = positionMapper.deleteByPrimaryKey(positionId);
        if (i == 0) {
            return new Result(false).message(MessageLevel.ERROR, "职位信息删除失败");
        }

        // 删除该职位的权限数据
        authPositionMapper.deleteByPositionId(positionId);

        return new Result(true);
    }
}

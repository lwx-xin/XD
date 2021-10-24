package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysPositionService;
import priv.xin.xd.core.entity.Position;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/7 13:14
 */
@RestController
@RequestMapping("sys/position")
public class PositionController {

    @Resource
    private ISysPositionService sysPositionService;

    /**
     * @param position 查询条件+
     * @param page     页码
     * @param pageSize 每页条数
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(Position position, Integer page, Integer pageSize) {

        return null;
    }

    /**
     * @param position 查询条件+
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result list(Position position) {
        Result result = sysPositionService.queryAll(position);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.POSITION_QUERY_ERROR);
        }
        Object positionList = result.getData("positionList");
        return result.clearData()
                .data("positionList", positionList)
                .message(MessageLevel.INFO, Message.POSITION_QUERY_ERROR);
    }

    @RequestMapping(value = "/{positionId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("positionId") String positionId) {
        Result result = sysPositionService.queryDetail(positionId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.POSITION_QUERY_ERROR);
        }
        Object position = result.getData("positionDetail");
        return result.clearData()
                .data("positionDetail", position)
                .message(MessageLevel.INFO, Message.POSITION_QUERY_ERROR);
    }

    @RequestMapping(value = "/{positionId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("positionId") String positionId, @RequestBody Position position) {
        Result result = sysPositionService.updatePositionDetail(positionId, position);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.POSITION_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.POSITION_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(@RequestBody Position position) {
        Result result = sysPositionService.insertPositionDetail(position);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.POSITION_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.POSITION_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{positionId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("positionId") String positionId) {
        Result result = sysPositionService.deleteByPositionId(positionId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.POSITION_DELETE_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.POSITION_DELETE_SUCCESS);
    }
}

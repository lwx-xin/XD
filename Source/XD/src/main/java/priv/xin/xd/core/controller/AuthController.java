package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysAuthService;
import priv.xin.xd.core.entity.Auth;
import priv.xin.xd.core.entity.ex.AuthEx;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/8/19 9:56
 */
@RestController
@RequestMapping("sys/auth")
public class AuthController {

    @Resource
    private ISysAuthService sysAuthService;

    /**
     * @param authEx 查询条件+
     * @param page   分页/排序信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(AuthEx authEx, Page page) {
        Result result = sysAuthService.queryListLimit(authEx, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.AUTH_QUERY_ERROR);
        }

        Object authList = result.getData("authList");
        //总条数
        Object totalNumber = result.getData("count");
        return result.clearData()
                .data("authList", authList)
                .data("totalNumber", totalNumber)
                .message(MessageLevel.INFO, Message.AUTH_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("authId") String authId) {
        Result result = sysAuthService.queryDetail(authId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.AUTH_QUERY_ERROR);
        }
        Object authDetail = result.getData("authDetail");
        return result.clearData()
                .data("authDetail", authDetail)
                .message(MessageLevel.INFO, Message.AUTH_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("authId") String authId, @RequestBody Auth auth) {
        Result result = sysAuthService.updateAuthDetail(authId, auth);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.AUTH_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.AUTH_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(@RequestBody Auth auth) {
        Result result = sysAuthService.insertAuthDetail(auth);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.AUTH_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.AUTH_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{authId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("authId") String authId) {
        Result result = sysAuthService.deleteByAuthId(authId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.AUTH_DELETE_ERROR);
        }
        return result.clearData().message(MessageLevel.INFO, Message.AUTH_DELETE_SUCCESS);
    }
}

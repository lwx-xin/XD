package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.core.service.ISysUserService;
import priv.xin.xd.core.entity.ex.UserEx;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/2 17:29
 */
@RestController
@RequestMapping("sys/user")
public class UserController {

    @Resource
    private ISysUserService sysUserService;

    /**
     * @param userEx 查询条件+
     * @param page   分页/排序信息
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Result list(UserEx userEx, Page page) {
        Result result = sysUserService.queryListLimit(userEx, page);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_QUERY_ERROR);
        }
        Object userList = result.getData("userList");
        //总条数
        Object totalNumber = result.getData("count");
        return result.clearData()
                .data("userList", userList)
                .data("totalNumber", totalNumber)
                .message(MessageLevel.INFO, Message.USER_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("userId") String userId) {
        Result result = sysUserService.queryDetail(userId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_QUERY_ERROR);
        }
        Object userDetail = result.getData("userDetail");
        return result.clearData()
                .data("userDetail", userDetail)
                .message(MessageLevel.INFO, Message.USER_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("userId") String userId, UserEx userEx, MultipartFile files) {
        Result result = sysUserService.updateUserDetail(userId, userEx, files);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_EDIT_ERROR);
        }
        if (userId.equals(ShiroUtil.getUserId())) {
            // 修改了当前登录用户
            result.message(MessageLevel.INFO, Message.USER_INFO_CHANGE_LOGIN_AGAIN);
        }
        return result.message(MessageLevel.INFO, Message.USER_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(UserEx userEx, MultipartFile files) {
        Result result = sysUserService.insertUserDetail(userEx, files);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.USER_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("userId") String userId) {
        Result result = sysUserService.deleteByUserId(userId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_EDIT_ERROR);
        }
        if (userId.equals(ShiroUtil.getUserId())) {
            // 修改了当前登录用户
            result.message(MessageLevel.INFO, Message.USER_INFO_CHANGE_LOGIN_AGAIN);
        }
        return result.message(MessageLevel.INFO, Message.USER_EDIT_SUCCESS);
    }

}

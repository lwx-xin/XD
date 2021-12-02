package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.core.service.ISysUserService;
import priv.xin.xd.core.entity.User;
import priv.xin.xd.core.service.ISysCommonService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ：lu
 * @date ：2021/5/31 18:05
 */
@RestController
@RequestMapping("sys")
public class CommonController {

    @Resource
    private ISysCommonService sysCommonService;

    @Resource
    private ISysUserService sysUserService;

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@RequestBody User user, HttpServletRequest request) {
        Result result = sysCommonService.login(user.getUserNumber(), user.getUserPwd(), request);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.LOGIN_ERROR);
        } else {
            // 登录成功后判断账号平台
        }
        return result.message(MessageLevel.INFO, Message.LOGIN_SUCCESS);
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public Result logout(HttpServletRequest request) {
        Result result = sysCommonService.logout(request);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.LOGOUT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.LOGOUT_SUCCESS);
    }

    /**
     * 获取用戶的菜單列表
     * 没有设置权限的菜单，任何用户都可以访问
     *
     * @return
     */
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    public Result menu() {
        String userId = ShiroUtil.getUserId();
        Result result = sysCommonService.getUserMenu(userId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.MENU_QUERY_ERROR);
        }
        Object userMenu = result.getData("userMenu");
        return result.clearData()
                .data("menuList", userMenu)
                .message(MessageLevel.INFO, Message.MENU_QUERY_SUCCESS);
    }

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    public Result loginUser() {
        String userId = ShiroUtil.getUserId();
        Result result = sysUserService.queryDetail(userId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.USER_QUERY_ERROR);
        }
        Object userEx = result.getData("userDetail");
        return result.clearData()
                .data("userDetail", userEx)
                .message(MessageLevel.INFO, Message.USER_QUERY_SUCCESS);
    }

}

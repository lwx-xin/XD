package priv.xin.xd.core.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ListUtil;
import priv.xin.xd.common.util.PasswordUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.common.util.jwt.JwtUtil;
import priv.xin.xd.core.dao.UserMapper;
import priv.xin.xd.core.entity.User;
import priv.xin.xd.core.service.ISysMenuService;
import priv.xin.xd.core.dao.MenuMapper;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;
import priv.xin.xd.core.service.ISysCommonService;
import priv.xin.xd.expansionService.redis.service.JwtTokenRedis;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @author ：lu
 * @date ：2021/5/31 18:18
 */
@Service
public class SysCommonServiceImpl implements ISysCommonService {

    @Resource
    private MenuMapper menuMapper;

    @Resource
    private ISysMenuService sysMenuService;

    @Resource
    private JwtTokenRedis jwtTokenRedis;

    @Resource
    private UserMapper userMapper;

    @Override
    public Result login(String userNumber, String userPwd, HttpServletRequest request) {
        User userQuery = new User();
        userQuery.setUserNumber(userNumber);
        List<User> users = userMapper.queryAll(userQuery);
        if (ListUtil.isEmpty(users)) {
            // 账号不存在
            return new Result(false).message(MessageLevel.ERROR, Message.ACCOUNT_UNKNOWN);
        }

        User user = users.get(0);

        // 验证用户密码
        String encrypt = "";
        try {
            encrypt = PasswordUtil.encrypt(userNumber, userPwd);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!encrypt.equals(user.getUserPwd())) {
            // 密码错误
            return new Result(false).message(MessageLevel.ERROR, Message.PASSWORD_ERROR);
        }

        // 用户锁定状态
        String userDelFlag = user.getUserDelFlag();
        if (userDelFlag != null && CodeEnum.DEL_FLAG_0.getValue().equals(userDelFlag)) {
            // 账号已被锁定
            return new Result(false).message(MessageLevel.ERROR, Message.ACCOUNT_IS_LOCK);
        }

        // 生成令牌
        String jwtToken = JwtUtil.createToken(user.getUserId());

        return new Result(true).data("jwtToken", jwtToken);
    }

    @Override
    public Result logout(HttpServletRequest request) {
        String token = JwtUtil.getToken(request);
        if (StrUtil.isEmpty(token)) {
            return new Result(false).message(MessageLevel.ERROR, Message.TOKEN_ERROR);
        }
        String userId = JwtUtil.getUserId(token);
        if (StrUtil.isEmpty(userId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.TOKEN_ERROR);
        }
        jwtTokenRedis.removeToken(userId);
        return new Result(true);
    }

    @Override
    public Result getUserMenu(String userId) {
        if (StrUtil.isEmpty(userId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.USER_NOT_LOGIN);
        }
        List<Menu> menuList = menuMapper.queryByUser(userId);
        List<MenuEx> userMenu = sysMenuService.getMenuTree(menuList);
        ;
        return new Result(true).data("userMenu", userMenu);
    }
}

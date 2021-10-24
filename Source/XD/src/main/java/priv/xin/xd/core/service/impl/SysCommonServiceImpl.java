package priv.xin.xd.core.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysMenuService;
import priv.xin.xd.core.dao.MenuMapper;
import priv.xin.xd.core.entity.Menu;
import priv.xin.xd.core.entity.ex.MenuEx;
import priv.xin.xd.core.service.ISysCommonService;

import javax.annotation.Resource;
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

    @Override
    public Result login(String userNumber, String userPwd) {
        /**
         * Shiro登录
         */
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 封装用户信息
        UsernamePasswordToken token = new UsernamePasswordToken(userNumber, userPwd);
        // 登录
        try {
            /**
             * login()方法会去执行 ShiroRealm 的认证方法 (doGetAuthenticationInfo)
             * 没有抛出异常代表登录成功
             */
            subject.login(token);
            return new Result(true);
        } catch (UnknownAccountException e) {
            // 账号不存在
            token.clear();
            return new Result(false).message(MessageLevel.ERROR, Message.ACCOUNT_UNKNOWN);
        } catch (IncorrectCredentialsException e) {
            // 密码错误
            token.clear();
            return new Result(false).message(MessageLevel.ERROR, Message.PASSWORD_ERROR);
        } catch (AccountException e) {
            // 其他错误
            token.clear();
            return new Result(false).message(MessageLevel.ERROR, e.getMessage());
        } catch (Exception e) {
            // 其他错误
            token.clear();
            return new Result(false).message(MessageLevel.ERROR, Message.ERROR);
        }
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

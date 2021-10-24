/**
 * MIT License
 * Copyright (c) 2018 yadong.zhang
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package priv.xin.xd.expansionService.shiro;

import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.springframework.context.annotation.Configuration;
import priv.xin.xd.common.util.DateUtil;
import priv.xin.xd.expansionService.redis.service.ShiroLogin;

import javax.annotation.Resource;

/**
 * Shiro-密码输入错误的状态下重试次数的匹配管理
 */
@Configuration
public class RetryLimitCredentialsMatcher extends CredentialsMatcher {

    @Resource
    private ShiroLogin shiroLogin;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String userId = (String) info.getPrincipals().getPrimaryPrincipal();

        // 判断账号是否被禁用
        Long lockTime = shiroLogin.isLock(userId);
        if (lockTime.compareTo(0L) > 0) {
            String time = DateUtil.format(lockTime);
            throw new ExcessiveAttemptsException("帐号已被锁定,解锁时间:" + time);
        }

        // 执行登录校验
        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            shiroLogin.loginSuccess(userId);
        } else {
            int count = shiroLogin.loginError(userId);
            if (count <= 0) {
                lockTime = shiroLogin.isLock(userId);
                if (lockTime.compareTo(0L) > 0) {
                    String time = DateUtil.format(lockTime);
                    throw new ExcessiveAttemptsException("帐号已被锁定,解锁时间:" + time);
                }
            } else {
                String msg = "您还剩" + count + "次重试的机会";
                throw new AccountException("帐号或密码不正确！" + msg);
            }
        }

        return true;
    }

}

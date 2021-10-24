package priv.xin.xd.expansionService.shiro.filter;

import org.apache.shiro.subject.Subject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * @author ：lu
 * @date ：2021/6/4 12:38
 */
public class LogoutFilter extends org.apache.shiro.web.filter.authc.LogoutFilter {
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        Subject subject = getSubject(request,response);
        //登出
        subject.logout();
        //获取登出后重定向到的地址
        String redirectUrl = getRedirectUrl(request,response,subject);
        //重定向
        issueRedirect(request,response,redirectUrl);
        return false;
    }
}

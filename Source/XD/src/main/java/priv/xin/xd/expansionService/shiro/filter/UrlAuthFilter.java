package priv.xin.xd.expansionService.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.dao.AuthUrlMapper;
import priv.xin.xd.core.dao.UrlMapper;
import priv.xin.xd.core.dao.UrlParamMapper;
import priv.xin.xd.core.entity.AuthUrl;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.UrlParam;
import priv.xin.xd.expansionService.redis.service.ServiceLogger;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author ：lu
 * @date ：2021/6/2 18:32
 */
public class UrlAuthFilter extends AccessControlFilter {

    private UrlMapper urlMapper;
    private AuthUrlMapper authUrlMapper;
    private UrlParamMapper urlParamMapper;
    private ServiceLogger serviceLogger;

    private void loadBean() {
        if (urlMapper == null) {
            urlMapper = SpringUtil.getBean(UrlMapper.class);
        }
        if (authUrlMapper == null) {
            authUrlMapper = SpringUtil.getBean(AuthUrlMapper.class);
        }
        if (urlParamMapper == null) {
            urlParamMapper = SpringUtil.getBean(UrlParamMapper.class);
        }
        if (serviceLogger == null) {
            serviceLogger = SpringUtil.getBean(ServiceLogger.class);
        }
    }

    /**
     * 该方法用来判断是否允许访问
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return 允许访问返回true，否则false；
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        // 加载bean
        loadBean();

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 参数集合
        Map<String, String[]> requestParameters = httpServletRequest.getParameterMap();
        // 请求路径
        String requestUrl = httpServletRequest.getServletPath();
        // 请求方法-post，get
        String requestMethod = httpServletRequest.getMethod();
        String requestMethodCode = CodeEnum.getValueByName(CodeEnum.REQUEST_METHOD_ALL.getGroup(), requestMethod);

        LoggerUtil.error("拦截的请求:" + requestUrl, getClass());

        // 是否登录 true:登录 ,false:未登录
        boolean loginStatus = false;
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            loginStatus = true;
        }
        if (!loginStatus) {
            return toLogin(request, response);
        }

        // 查询当前请求url的相关信息
        Url urlQuery = new Url();
        urlQuery.setUrlPath(requestUrl);
        urlQuery.setUrlType(requestMethodCode);
        List<Url> urlList = urlMapper.queryAllRegexp(urlQuery);

        // 当前请求url在数据库中没有记录(请求不存在)
        if (ListUtil.isEmpty(urlList)) {
            return toNoUrl(request, response);
        }

//        if (ListUtil.isEmpty(urlList)) {
//            if (loginStatus) {
//                return toNoUrl(request, response);
//            } else {
//                return toLogin(request, response);
//            }
//        }

        // 查询当前请求需要的权限
        List<AuthUrl> authUrlList = new ArrayList<>();
        for (Url url : urlList) {
            AuthUrl authUrlQuery = new AuthUrl();
            authUrlQuery.setUrlId(url.getUrlId());
            authUrlList.addAll(authUrlMapper.queryAll(authUrlQuery));
        }

        // 验证是否拥有访问该请求的权限
        if (ListUtil.isEmpty(authUrlList)) {
            // 不需要权限的请求url不需要登录也可以访问
//            return true;
        } else {
            for (AuthUrl authUrl : authUrlList) {
                String authId = authUrl.getAuthId();
                // 是否拥有该权限
                if (!subject.isPermitted(authId)) {
                    return toNoAuth(request, response);
                }
            }
        }

        // 获取当前请求需要验证的参数
        List<UrlParam> urlParamList = new ArrayList<>();
        for (Url url : urlList) {
            UrlParam urlParamQuery = new UrlParam();
            urlParamQuery.setUrlId(url.getUrlId());
            urlParamList.addAll(urlParamMapper.queryAll(urlParamQuery));
        }
        // 验证参数
        if (ListUtil.isNotEmpty(urlParamList)) {

            List<String> fieldList = new ArrayList<>();
            List<String> messageList = new ArrayList<>();
            boolean status = true;

            for (UrlParam urlParam : urlParamList) {
                String urlParamName = urlParam.getUrlParamName();
                String urlParamValue = urlParam.getUrlParamValue();
                String urlParamRequired = urlParam.getUrlParamRequired();
                String urlParamErrHint = urlParam.getUrlParamErrHint();

                String[] values = requestParameters.get(urlParamName);

                // 当前参数为必须参数
                if (CodeEnum.YES.getValue().equals(urlParamRequired) && (ListUtil.isEmpty(values) || StrUtil.isEmpty(values[0]))) {
                    fieldList.add(urlParamName);
                    messageList.add(urlParamErrHint);
                    status = false;
                }

                if (ListUtil.isNotEmpty(values)) {
                    for (String value : values) {
                        if (StrUtil.isNotEmpty(value) && !Pattern.matches(urlParamValue, value)) {
                            fieldList.add(urlParamName);
                            messageList.add(urlParamErrHint);
                            status = false;
                        }
                    }
                }
            }

            if (!status) {
                return toErrorParam(request, response, fieldList, messageList);
            }
        }

        //是否拥有该角色
//        subject.hasRole("");

        // 记录日志
        String userId = (String) subject.getPrincipal();
        String log = "请求时间:" + DateUtil.now() +
                "\n请求地址:" + requestUrl +
                "\n参数列表:" + JsonUtil.toJson(requestParameters);
        serviceLogger.addLog(userId, log);

        return true;
    }

    /**
     * 当访问被拒绝时(isAccessAllowed返回false时)执行该方法
     *
     * @param request
     * @param response
     * @return 返回true表示还需要继续处理，返回false表示已经处理完毕
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 获取请求平台
     *
     * @param request
     * @return
     */
    public CodeEnum getPlatform(ServletRequest request) {
        return CodeEnum.PLATFORM_PC;
    }

    /**
     * 返回登录页面
     *
     * @param request
     * @param response
     */
    private boolean toLogin(ServletRequest request, ServletResponse response) throws IOException {
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    /**
     * 返回没有权限的页面
     *
     * @param request
     * @param response
     */
    private boolean toNoAuth(ServletRequest request, ServletResponse response) throws IOException {
//        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
//        httpServletRequest.getHeader("");

        LoggerUtil.error(WebUtils.toHttp(request).getServletPath(), getClass());
        LoggerUtil.error("返回没有权限的页面", getClass());
        saveRequest(request);

//        WebUtils.issueRedirect(request, response, CommonConst.URL_ERR_AUTH);
        setResponse(request, response, null, Arrays.asList("权限不足"), null);
        return false;
    }

    /**
     * 返回未知的请求页面
     *
     * @param request
     * @param response
     * @return
     */
    private boolean toNoUrl(ServletRequest request, ServletResponse response) throws IOException {

        LoggerUtil.error(WebUtils.toHttp(request).getServletPath(), getClass());
        LoggerUtil.error("返回未知的请求页面", getClass());
        saveRequest(request);

//        WebUtils.issueRedirect(request, response, CommonConst.URL_ERR_UNKNOWN_URL);
        setResponse(request, response, null, Arrays.asList("未知的请求"), null);
        return false;
    }

    /**
     * 返回参数错误的请求页面
     *
     * @param request
     * @param response
     * @return
     */
    private boolean toErrorParam(ServletRequest request, ServletResponse response, List<String> fields, List<String> messages) throws IOException {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        String url = httpServletRequest.getServletPath();

        LoggerUtil.error(url, getClass());
        LoggerUtil.error("返回参数错误的请求页面", getClass());

        setResponse(request, response, null, messages, fields);

        saveRequest(request);
//        WebUtils.issueRedirect(request, httpServletResponse, CommonConst.URL_ERR_PARAM);
        return false;
    }

    private void setResponse(ServletRequest request, ServletResponse response, String redirectUrl, List<String> messages, List<String> fields) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        httpServletResponse.setStatus(500);

        String url = httpServletRequest.getServletPath();

        redirectUrl = StrUtil.toUTF8(redirectUrl);

        Result result = new Result(false);
        String message = "";
        if (ListUtil.isNotEmpty(messages)) {
            for (String msg : messages) {
                result.message(MessageLevel.ERROR, msg);
            }
            message = StrUtil.toUTF8(JsonUtil.toJson(result.getMessages()));
        }

        String field = "";
        if (ListUtil.isNotEmpty(fields)) {
            field = StrUtil.toUTF8(JsonUtil.toJson(fields));
        }
        url = StrUtil.toUTF8(url);

        if (StrUtil.ajaxRequest(httpServletRequest)) {
            if (StrUtil.isNotEmpty(redirectUrl)) {
                httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_REDIRECT, redirectUrl);
            }
            if (StrUtil.isNotEmpty(message)) {
                httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_MESSAGES, message);
            }
            if (StrUtil.isNotEmpty(field)) {
                httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_PARAM, field);
            }
            httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_URL, url);
            httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_MESSAGE_TITLE, StrUtil.toUTF8("异常"));
            httpServletResponse.addHeader(CommonConst.SYSTEM_ERROR_MESSAGE_TYPE, StrUtil.toUTF8("error"));
        } else {
            if (StrUtil.isNotEmpty(redirectUrl)) {
                CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_REDIRECT, redirectUrl, httpServletResponse);
            }
            if (StrUtil.isNotEmpty(message)) {
                CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGES, message, httpServletResponse);
            }
            if (StrUtil.isNotEmpty(field)) {
                CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_PARAM, field, httpServletResponse);
            }
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_URL, url, httpServletResponse);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TITLE, StrUtil.toUTF8("异常"), httpServletResponse);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TYPE, StrUtil.toUTF8("error"), httpServletResponse);
        }
    }

}

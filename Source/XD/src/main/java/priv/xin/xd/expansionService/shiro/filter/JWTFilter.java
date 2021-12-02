package priv.xin.xd.expansionService.shiro.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.common.util.jwt.JwtToken;
import priv.xin.xd.common.util.jwt.JwtUtil;
import priv.xin.xd.core.dao.AuthUrlMapper;
import priv.xin.xd.core.dao.UrlMapper;
import priv.xin.xd.core.dao.UrlParamMapper;
import priv.xin.xd.core.entity.AuthUrl;
import priv.xin.xd.core.entity.Url;
import priv.xin.xd.core.entity.UrlParam;
import priv.xin.xd.expansionService.redis.service.LoggerRedis;

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
 * @date ：2021/11/25 11:18
 */
public class JWTFilter extends BasicHttpAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JWTFilter.class);
    private UrlMapper urlMapper;
    private AuthUrlMapper authUrlMapper;
    private UrlParamMapper urlParamMapper;

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
    }

    /**
     * 判断是否登录
     * 在登录的情况下会走此方法
     * 此方法返回true直接访问控制器，此方法返回false就继续执行onAccessDenied()
     *
     * @param request
     * @param response
     * @param mappedValue
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        /**
         * 进入该方法的请求全部需要token
         */
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // 参数集合
        Map<String, String[]> requestParameters = httpServletRequest.getParameterMap();
        // 请求路径
        String requestUrl = httpServletRequest.getServletPath();
        // 请求方法-post，get
        String requestMethod = httpServletRequest.getMethod();
        String requestMethodCode = CodeEnum.getValueByName(CodeEnum.REQUEST_METHOD_ALL.getGroup(), requestMethod);

        // 访问HTML无需权限
        if (requestUrl.endsWith(".html") && CodeEnum.REQUEST_METHOD_GET.getValue().equals(requestMethodCode)) {
            return true;
        }

        logger.info("拦截的请求:" + requestUrl);

        loadBean();

        logger.info("======= 身份认证开始 =======");
        // 验证Token
        boolean verifyToken = false;
        List<String> toLoginMessage = new ArrayList<>();
        try {
            verifyToken = verifyToken(request, response);
            if (!verifyToken) {
                toLoginMessage.add("请先登录后再操作");
            }
        } catch (Exception e) {
            verifyToken = false;
            toLoginMessage.add(e.getCause().getMessage());
        }
        if (!verifyToken) {
            logger.info("======= 身份认证结束(error) =======");
            return toLogin(request, response, toLoginMessage);
        }
        logger.info("======= 身份认证结束(success) =======");

        logger.info("======= 请求认证开始 =======");
        // 验证请求信息
        Url url = verifyUrl(request, response);
        if (url == null) {
            logger.info("======= 请求认证结束(error) =======");
            return toNoUrl(request, response);
        }
        logger.info("======= 请求认证结束(success) =======");

        logger.info("======= 请求权限认证开始 =======");
        // 验证权限--判断该用户是否有权限访问该请求
        boolean verifyAuth = verifyAuth(request, response, url);
        if (!verifyAuth) {
            logger.info("======= 请求权限认证结束(error) =======");
            return toNoAuth(request, response);
        }
        logger.info("======= 请求权限认证结束(success) =======");

        logger.info("======= 请求参数认证开始 =======");
        // 验证请求参数
        List<String> fieldList = new ArrayList<>();
        List<String> messageList = new ArrayList<>();
        boolean verifyParam = verifyParam(request, response, url, fieldList, messageList);
        if (!verifyParam) {
            logger.info("======= 请求参数认证结束(error) =======");
            return toErrorParam(request, response, fieldList, messageList);
        }
        logger.info("======= 请求参数认证结束(success) =======");

        return true;
    }

    /**
     * 是否是拒绝登录
     * 没有登录的情况下会走此方法
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        return false;
    }

    /**
     * 对跨域提供支持
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 验证请求信息
     *
     * @param request
     * @param response
     * @return
     */
    private Url verifyUrl(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);

        // 请求路径
        String requestUrl = httpServletRequest.getServletPath();
        // 请求方法-post，get
        String requestMethod = httpServletRequest.getMethod();
        String requestMethodCode = CodeEnum.getValueByName(CodeEnum.REQUEST_METHOD_ALL.getGroup(), requestMethod);

        // 查询当前请求url的相关信息
        Url urlQuery = new Url();
        urlQuery.setUrlPath(requestUrl);
        urlQuery.setUrlType(requestMethodCode);
        List<Url> urlList = urlMapper.queryAllRegexp(urlQuery);

        // 当前请求url在数据库中没有记录(请求不存在)
        if (ListUtil.isEmpty(urlList)) {
            return null;
        }
        return urlList.get(0);
    }

    /**
     * 验证token
     *
     * @param request
     * @param response
     * @return
     */
    private boolean verifyToken(ServletRequest request, ServletResponse response)
            throws AuthenticationException, JWTVerificationException {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        // token信息
        String token = JwtUtil.getToken(httpServletRequest);
        if (StrUtil.isNotEmpty(token)) {
            // 验证token
            JwtToken jwtToken = new JwtToken(token);
            // 提交给realm进行登入，如果错误它会抛出异常并被捕获
            Subject subject = getSubject(request, response);
            subject.login(jwtToken);

            return true;
        }
        return false;
    }

    /**
     * 验证权限--判断该用户是否有权限访问该请求
     *
     * @param request
     * @param response
     * @param url
     * @return
     */
    private boolean verifyAuth(ServletRequest request, ServletResponse response, Url url) {
        AuthUrl authUrlQuery = new AuthUrl();
        authUrlQuery.setUrlId(url.getUrlId());
        List<AuthUrl> authUrlList = authUrlMapper.queryAll(authUrlQuery);
        if (ListUtil.isNotEmpty(authUrlList)) {
            for (AuthUrl authUrl : authUrlList) {
                String authId = authUrl.getAuthId();
                // 是否拥有该权限
                Subject subject = getSubject(request, response);
                if (!subject.isPermitted(authId)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 验证请求参数
     *
     * @param request
     * @param response
     * @param url
     * @param fieldList
     * @param messageList
     * @return
     */
    private boolean verifyParam(ServletRequest request, ServletResponse response,
                                Url url, List<String> fieldList, List<String> messageList) {
        UrlParam urlParamQuery = new UrlParam();
        urlParamQuery.setUrlId(url.getUrlId());
        List<UrlParam> urlParams = urlParamMapper.queryAll(urlParamQuery);
        if (ListUtil.isNotEmpty(urlParams)) {

            HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
            // 参数集合
            Map<String, String[]> requestParameters = httpServletRequest.getParameterMap();

            boolean status = true;
            for (UrlParam urlParam : urlParams) {
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
            return status;
        }
        return true;
    }

    /**
     * 返回登录页面
     *
     * @param request
     * @param response
     */
    private boolean toLogin(ServletRequest request, ServletResponse response, List<String> messages) {
        setResponse(request, response, getLoginUrl(), messages, null);
        logger.error("返回登录页面");
//        try {
//            saveRequestAndRedirectToLogin(request, response);
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        }
        return false;
    }

    /**
     * 返回没有权限的页面
     *
     * @param request
     * @param response
     */
    private boolean toNoAuth(ServletRequest request, ServletResponse response) {

        LoggerUtil.error(WebUtils.toHttp(request).getServletPath(), getClass());
        LoggerUtil.error("返回没有权限的页面", getClass());

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
    private boolean toNoUrl(ServletRequest request, ServletResponse response) {

        LoggerUtil.error(WebUtils.toHttp(request).getServletPath(), getClass());
        LoggerUtil.error("返回未知的请求页面", getClass());

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
    private boolean toErrorParam(ServletRequest request, ServletResponse response, List<String> fields, List<String> messages) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        String url = httpServletRequest.getServletPath();

        LoggerUtil.error(url, getClass());
        LoggerUtil.error("返回参数错误的请求页面", getClass());

        setResponse(request, response, null, messages, fields);

        return false;
    }

    /**
     * 设置response的数据
     *
     * @param request
     * @param response
     * @param redirectUrl 重定向地址
     * @param messages    消息列表
     * @param fields      错误的字段名列表
     */
    private void setResponse(ServletRequest request, ServletResponse response,
                             String redirectUrl, List<String> messages, List<String> fields) {
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

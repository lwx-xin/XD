package priv.xin.xd.expansionService.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.common.properties.PathProperties;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.*;
import priv.xin.xd.core.dao.FileMapper;
import priv.xin.xd.core.entity.File;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/6/2 18:32
 */
public class FileAuthFilter extends AccessControlFilter {

    private PathProperties pathProperties;
    private FileMapper fileMapper;

    private void loadBean() {
        if (pathProperties == null) {
            pathProperties = SpringUtil.getBean(PathProperties.class);
        }
        if (fileMapper == null) {
            fileMapper = SpringUtil.getBean(FileMapper.class);
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

        String userId = (String) SecurityUtils.getSubject().getPrincipal();
        if (StrUtil.isEmpty(userId)) {
            return toLogin(request, response);
        }

        String filePath = requestUrl.replace(pathProperties.getFileSaveVirtualPath().replace("**", ""), "");

        if (StrUtil.isNotEmpty(filePath)) {
            File fileQuery = new File();
            fileQuery.setFilePath(filePath);
            List<File> files = fileMapper.queryAll(fileQuery);
            if (ListUtil.isNotEmpty(files)) {
                String fileOwner = files.get(0).getFileOwner();
                if (!fileOwner.equals(userId)) {
                    // 文件所有者错误
                    return toFileNotExist(request, response);
                }
            } else {
                // 文件不存在
                return toFileNotExist(request, response);
            }
        } else {
            // 文件不存在
            return toFileNotExist(request, response);
        }

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
     * 文件不存在
     *
     * @param request
     * @param response
     */
    private boolean toFileNotExist(ServletRequest request, ServletResponse response) throws IOException {

        LoggerUtil.error(WebUtils.toHttp(request).getServletPath(), getClass());
        LoggerUtil.error("文件不存在", getClass());
        saveRequest(request);

        WebUtils.issueRedirect(request, response, CommonConst.URL_FILE_NOT_EXIST);
        return false;
    }

    /**
     * 返回登录页面
     *
     * @param request
     * @param response
     */
    private boolean toLogin(ServletRequest request, ServletResponse response) throws IOException {
        setResponse(request, response, Arrays.asList("请先登录后再访问"));
        saveRequestAndRedirectToLogin(request, response);
        return false;
    }

    private void setResponse(ServletRequest request, ServletResponse response, List<String> messages) {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);

        String url = httpServletRequest.getServletPath();

        Result result = new Result(false);
        String message = "";
        if (ListUtil.isNotEmpty(messages)) {
            for (String msg : messages) {
                result.message(MessageLevel.ERROR, msg);
            }
            message = StrUtil.toUTF8(JsonUtil.toJson(result.getMessages()));
        }

        if (StrUtil.isNotEmpty(message)) {
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGES, message, httpServletResponse);
        }
        CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_URL, url, httpServletResponse);
        CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TITLE, StrUtil.toUTF8("异常"), httpServletResponse);
        CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TYPE, StrUtil.toUTF8("error"), httpServletResponse);
    }

}

package priv.xin.xd.expansionService.logger;

import org.apache.commons.collections4.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.common.util.jwt.JwtUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 向MDC中添加用户，请求信息的过滤器
 *
 * @author ：lu
 * @date ：2021/11/21 19:04
 */
public class UrlMdcFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(UrlMdcFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // 参数集合
        Map<String, String[]> requestParameters = httpServletRequest.getParameterMap();
        // 请求路径
        String requestUrl = httpServletRequest.getServletPath();
        // 请求方法-post，get
        String requestMethod = httpServletRequest.getMethod();
        // 开始时间
        long start = System.currentTimeMillis();
        // token信息
        String token = JwtUtil.getToken(httpServletRequest);
        if (StrUtil.isNotEmpty(token)) {
            String userId = JwtUtil.getUserId(token);
            MDC.put("userId", userId);
        }

        MDC.put("url", requestUrl);
        MDC.put("method", requestMethod);
        if (MapUtils.isNotEmpty(requestParameters)) {
            MDC.put("params", JsonUtil.toJson(requestParameters));
        }

        chain.doFilter(request, response);

        // 结束时间
        long end = System.currentTimeMillis();
        // 请求执行时间
        long time = end - start;
        int status = ((HttpServletResponse) response).getStatus();
        logger.info("status:" + status + ", 执行时间(秒):" + time / 1000.000);

        MDC.remove("userId");
        MDC.remove("url");
        MDC.remove("method");
        if (MapUtils.isNotEmpty(requestParameters)) {
            MDC.remove("params");
        }
    }

    @Override
    public void destroy() {

    }

}

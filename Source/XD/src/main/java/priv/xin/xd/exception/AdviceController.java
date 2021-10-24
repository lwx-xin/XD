package priv.xin.xd.exception;

import io.lettuce.core.RedisCommandTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.common.entity.CommonConst;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.CookieUtil;
import priv.xin.xd.common.util.JsonUtil;
import priv.xin.xd.common.util.StrUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @author ：lu
 * @date ：2021/6/1 17:49
 */
@ControllerAdvice
public class AdviceController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(value = RedisCommandTimeoutException.class)
    public void redisExceptionHandler(RedisCommandTimeoutException ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        //redis连接异常
        // 请求路径
        String url = StrUtil.toUTF8(request.getServletPath());
        // 错误消息
        Result result = new Result(false);
        result.message(MessageLevel.ERROR,ex.getMessage());
        String errMessages = StrUtil.toUTF8(JsonUtil.toJson(result.getMessages()));

        saveErrLog(errMessages, url, ex, request, response);
    }

    @ExceptionHandler(value = Exception.class)
    public void exceptionHandler(Exception ex, HttpServletRequest request, HttpServletResponse response) {
        logger.error(ex.getMessage());
        ex.printStackTrace();
        // 请求路径
        String url = StrUtil.toUTF8(request.getServletPath());
        // 错误消息
        Result result = new Result(false);
        result.message(MessageLevel.ERROR,ex.getMessage());
        String errMessages = StrUtil.toUTF8(JsonUtil.toJson(result.getMessages()));

        saveErrLog(errMessages, url, ex, request, response);
    }

    private void saveErrLog(String errMessages, String url, Exception ex, HttpServletRequest request, HttpServletResponse response) {

        response.setStatus(500);

        if (StrUtil.ajaxRequest(request)) {
            response.addHeader(CommonConst.SYSTEM_ERROR_REDIRECT, StrUtil.toUTF8(CommonConst.URL_ERR_500));
            response.addHeader(CommonConst.SYSTEM_ERROR_MESSAGES, errMessages);
            response.addHeader(CommonConst.SYSTEM_ERROR_URL, url);
            response.addHeader(CommonConst.SYSTEM_ERROR_MESSAGE_TITLE, "异常");
            response.addHeader(CommonConst.SYSTEM_ERROR_MESSAGE_TYPE, "error");
        } else {
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_REDIRECT, StrUtil.toUTF8(CommonConst.URL_ERR_500), response);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGES, errMessages, response);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_URL, url, response);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TITLE, "异常", response);
            CookieUtil.createCookie(CommonConst.SYSTEM_ERROR_MESSAGE_TYPE, "error", response);
        }
        // 记录日志文件
    }
}

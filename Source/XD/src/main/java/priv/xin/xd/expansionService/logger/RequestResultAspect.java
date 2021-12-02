package priv.xin.xd.expansionService.logger;

import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.JsonUtil;

/**
 * 打印接口返回数据的切面
 *
 * @author ：lu
 * @date ：2021/11/19 10:17
 */
@Aspect
@Configuration
public class RequestResultAspect {

    private Logger logger = LoggerFactory.getLogger(RequestResultAspect.class);

    private static final String POINTCUT_EXPRESSION = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";

    @Pointcut(POINTCUT_EXPRESSION)
    private void pointCut() {
    }

    @AfterReturning(pointcut = "pointCut()", returning = "ret")
    public void afterReturning(Object ret) throws Throwable {
        if (ret instanceof Result) {
            Result result = (Result) ret;
            logger.info(JsonUtil.toJson(result));
        } else {
//            logger.info(JsonUtil.toJson(ret));
        }
    }

}

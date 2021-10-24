package priv.xin.xd.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author ：lu
 * @date ：2021/6/1 14:50
 */
public class LoggerUtil {

    public static void info(String message, Class c){
        Logger logger = LoggerFactory.getLogger(c);
        logger.info(message);
    }

    public static void info(Exception e, Class c){
        info(e.getMessage(), c);
    }

    public static void error(String message, Class c){
        Logger logger = LoggerFactory.getLogger(c);
        logger.error(message);
    }

    public static void error(Exception e, Class c){
        error(e.getMessage(), c);
    }

}

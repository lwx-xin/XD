package priv.xin.xd.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：lu
 * @date ：2021/9/26 14:55
 */
public class DateUtil {

    /**
     * 将时间戳转换成字符串
     *
     * @param time
     * @return
     */
    public static String format(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String now() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    /**
     * 将时间戳转换成字符串
     *
     * @param time
     * @param formatStr
     * @return
     */
    public static String format(long time, String formatStr) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

    /**
     * 获取当前时间
     *
     * @param formatStr
     * @return
     */
    public static String format(String formatStr) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        return format.format(date);
    }

}

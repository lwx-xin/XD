package priv.xin.xd.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author ：lu
 * @date ：2021/9/26 14:55
 */
public class DateUtil {

    /**
     * 时间格式
     */
    public static final String[] dateFormat = new String[]{
            "yyyy/MM/dd HH:mm:ss",
            "yyyy-MM-dd HH:mm:ss",
            "yyyy年MM月dd日 HH:mm:ss",
            "yyyy/MM/dd",
            "yyyy-MM-dd",
            "yyyy年MM月dd日",
            "MM/dd",
            "MM-dd",
            "MM月dd日"
    };

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
     * @return
     */
    public static String format(long time) {
        Date date = new Date(time);
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
     * 字符串转换成时间
     *
     * @param dateStr
     * @return
     */
    public static Date format(String dateStr) {
        Date date = null;
        for (int i = 0; i < dateFormat.length; i++) {
            date = format(dateStr, dateFormat[i]);
            if (date != null) {
                break;
            }
        }
        return date;
    }

    /**
     * 时间转换字符串
     * @param date
     * @return
     */
    public static String format(Date date) {
        String dateStr = "";
        for (int i = 0; i < dateFormat.length; i++) {
            dateStr = format(date, dateFormat[i]);
            if (dateStr != null && !"".equals(dateStr)) {
                break;
            }
        }
        return dateStr;
    }

    /**
     * 时间转换字符串
     * @param date
     * @param formatStr
     * @return
     */
    public static String format(Date date,String formatStr) {
        String dataStr = "";
        try {
            formatStr = formatStr.replace(" ", "");
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            dataStr = format.format(date);
        } catch (Exception e) {
            System.err.println(e);
        }
        return dataStr;
    }

    /**
     * 字符串转换成时间
     *
     * @param dateStr
     * @return
     */
    public static Date format(String dateStr, String formatStr) {

        dateStr = dateStr.replace(" ", "");
        formatStr = formatStr.replace(" ", "");

        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        try {
            date = format.parse(dateStr);
        } catch (ParseException e) {
            System.err.println(e);
        }
        return date;
    }

}


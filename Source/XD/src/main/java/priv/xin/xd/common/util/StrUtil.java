package priv.xin.xd.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import priv.xin.xd.common.code.CodeEnum;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

public class StrUtil {

    private static final Logger logger = LoggerFactory.getLogger(StrUtil.class);

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID() {
//        return UUID.randomUUID().toString().replaceAll("-", "");
        return UUID.randomUUID().toString();
    }

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str) || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isEmpty(Object obj) {
        if (obj == null || "".equals(obj) || "".equals(format(obj).trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 判断字符串是否相等
     *
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null || b == null) {
            return false;
        } else {
            return a.equals(b);
        }
    }

    /**
     * 转换成String
     *
     * @param strObject
     * @return
     */
    public static String format(Object strObject) {
        if (strObject != null) {
            return strObject.toString();
        }
        return "";
    }

    /**
     * 转换编码格式，前端使用toUTF8（）还原编码
     *
     * @param str
     * @return
     */
    public static String toUTF8(String str) {
        if (isEmpty(str)) {
            return "";
        }
        str = str.replaceAll("[\\t\\n\\r]", "");
        try {
            return URLEncoder.encode(toAscii(str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
            return "";
        }
    }

    /**
     * 转换编码格式，前端使用fromCharCode()还原编码
     *
     * @param value
     * @return
     */
    private static String toAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 是否是Ajax请求
     *
     * @param request
     * @return true:ajax
     */
    public static boolean ajaxRequest(HttpServletRequest request) {
        return "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
    }

}

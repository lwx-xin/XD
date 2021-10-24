package priv.xin.xd.common.entity;

/**
 * @author ：lu
 * @date ：2021/5/31 19:11
 */
public class CommonConst {

    /**
     * 登录页面(首页)
     */
    public static final String URL_LOGIN = "/html/login.html";
    /**
     * 错误页面--没有权限
     */
    public static final String URL_ERR_AUTH = "/html/error/err-noAuth.html";
    /**
     * 错误页面--参数错误
     */
    public static final String URL_ERR_PARAM = "/html/error/err-paramsErr.html";
    /**
     * 错误页面--未知的请求
     */
    public static final String URL_ERR_UNKNOWN_URL = "/html/error/err-unknownUrl.html";
    /**
     * 错误页面--未知的请求
     */
//    public static final String URL_ERR_500 = "/html/error/err-unknownUrl.html";
    public static final String URL_ERR_500 = "";
    /**
     * 错误页面--文件不存在
     */
    public static final String URL_FILE_NOT_EXIST = "/html/error/err-file-not-exist.html";

    /**
     * 盐-shiro密码加密使用
     */
    public static final String SHIRO_SALT = "xd-xin-dream";

    /**
     * User 的 session key;
     */
    public static final String USER_SESSION_KEY = "user";

    /**
     * 错误消息-消息内容
     */
    public static final String SYSTEM_ERROR_MESSAGES = "SYSTEM_ERROR_MESSAGES";
    /**
     * 错误消息-标题
     */
    public static final String SYSTEM_ERROR_MESSAGE_TITLE = "SYSTEM_ERROR_MESSAGE_TITLE";
    /**
     * 错误消息-类型
     */
    public static final String SYSTEM_ERROR_MESSAGE_TYPE = "SYSTEM_ERROR_MESSAGE_TYPE";
    /**
     * 错误消息-请求地址
     */
    public static final String SYSTEM_ERROR_URL = "SYSTEM_ERROR_URL";
    /**
     * 错误消息-重定向地址
     */
    public static final String SYSTEM_ERROR_REDIRECT = "SYSTEM_ERROR_REDIRECT";
    /**
     * 错误消息-错误的参数名
     */
    public static final String SYSTEM_ERROR_PARAM = "SYSTEM_ERROR_PARAM";

    /**
     * 略缩图后缀
     */
    public static final String IMG_EXT = "_thumbnails";

}

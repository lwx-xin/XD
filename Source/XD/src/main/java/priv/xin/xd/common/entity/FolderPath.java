package priv.xin.xd.common.entity;

/**
 * @author ：lu
 * @date ：2021/9/10 16:54
 */
public class FolderPath {

    /**
     * 系统资源
     */
    public static final String SYSTEM_RESOURCE_FOLDER_NAME = "系统文件";
    public static final String SYSTEM_RESOURCE_FOLDER_PATH = "/%s/" + SYSTEM_RESOURCE_FOLDER_NAME;

    /**
     * 用户头像
     */
    public static final String USER_HEAD_FOLDER_NAME = "头像";
    public static final String USER_HEAD_FOLDER_PATH = SYSTEM_RESOURCE_FOLDER_PATH + "/" + USER_HEAD_FOLDER_NAME;

    /**
     * 用户日志
     */
    public static final String USER_LOG_FOLDER_NAME = "日志";
    public static final String USER_LOG_FOLDER_PATH = SYSTEM_RESOURCE_FOLDER_PATH + "/" + USER_LOG_FOLDER_NAME;

    /**
     * 自定义资源
     */
    public static final String CUSTOM_RESOURCE_FOLDER_NAME = "用户文件";
    public static final String CUSTOM_RESOURCE_FOLDER_PATH = "/%s/" + CUSTOM_RESOURCE_FOLDER_NAME;

}

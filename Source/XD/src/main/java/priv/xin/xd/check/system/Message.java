package priv.xin.xd.check.system;

import priv.xin.xd.check.MessageLevel;

/**
 * @author ：lu
 * @date ：2021/9/18 16:40
 */
public enum Message{

    /** 未知的错误 */
    ERROR("","未知的错误"),

    /** 登录成功 */
    LOGIN_SUCCESS("","登录成功"),
    /** 登录失败 */
    LOGIN_ERROR("","登录失败"),

    /** 登出成功 */
    LOGOUT_SUCCESS("","登出成功"),
    /** 登出失败 */
    LOGOUT_ERROR("","登出失败"),

    /** 未知的账号 */
    ACCOUNT_UNKNOWN("","未知的账号"),
    /** 账号已被锁定 */
    ACCOUNT_IS_LOCK("","账号已被锁定"),
    /** 密码错误 */
    PASSWORD_ERROR("","密码错误"),

    /** 令牌异常 */
    TOKEN_ERROR("","令牌异常"),

    /** 用户查询失败 */
    USER_QUERY_ERROR("", "用户查询失败"),
    /** 用户查询成功 */
    USER_QUERY_SUCCESS("", "用户查询成功"),
    /** 用户添加失败 */
    USER_INSERT_ERROR("", "用户添加失败"),
    /** 用户添加成功 */
    USER_INSERT_SUCCESS("", "用户添加成功"),
    /** 用户编辑失败 */
    USER_EDIT_ERROR("", "用户编辑失败"),
    /** 用户编辑成功 */
    USER_EDIT_SUCCESS("", "用户编辑成功"),
    /** 用户删除失败 */
    USER_DELETE_ERROR("", "用户删除失败"),
    /** 用户删除成功 */
    USER_DELETE_SUCCESS("", "用户删除成功"),
    /** 未知的用户 */
    USER_UNKNOWN("","未知的用户"),

    /** 获取用户头像文件夹失败 */
    USER_HEAD_FOLDER_ERROR("","获取用户头像文件夹失败"),
    /** 修改用户头像失败 */
    USER_HEAD_EDIT_ERROR("","修改用户头像失败"),
    /** 添加用户头像失败 */
    USER_HEAD_INSERT_ERROR("","添加用户头像失败"),
    /** 加密密码失败 */
    USER_ENCRYPTION_PASSWORD_ERROR("","加密密码失败"),

    /** 请登录后再操作 */
    USER_NOT_LOGIN("","请登录后再操作"),
    /** 请登录后再操作 */
    USER_INFO_CHANGE_LOGIN_AGAIN("","当前登录用户被修改，请重新登陆"),

    /** 权限查询失败 */
    AUTH_QUERY_ERROR("", "权限查询失败"),
    /** 权限查询成功 */
    AUTH_QUERY_SUCCESS("", "权限查询成功"),
    /** 权限添加失败 */
    AUTH_INSERT_ERROR("", "权限添加失败"),
    /** 权限添加成功 */
    AUTH_INSERT_SUCCESS("", "权限添加成功"),
    /** 权限编辑失败 */
    AUTH_EDIT_ERROR("", "权限编辑失败"),
    /** 权限编辑成功 */
    AUTH_EDIT_SUCCESS("", "权限编辑成功"),
    /** 权限删除失败 */
    AUTH_DELETE_ERROR("", "权限删除失败"),
    /** 权限删除成功 */
    AUTH_DELETE_SUCCESS("", "权限删除成功"),
    /** 未知的权限 */
    AUTH_UNKNOWN("","未知的权限"),

    /** 部门查询失败 */
    DEPARTMENT_QUERY_ERROR("", "部门查询失败"),
    /** 部门查询成功 */
    DEPARTMENT_QUERY_SUCCESS("", "部门查询成功"),
    /** 部门添加失败 */
    DEPARTMENT_INSERT_ERROR("", "部门添加失败"),
    /** 部门添加成功 */
    DEPARTMENT_INSERT_SUCCESS("", "部门添加成功"),
    /** 部门编辑失败 */
    DEPARTMENT_EDIT_ERROR("", "部门编辑失败"),
    /** 部门编辑成功 */
    DEPARTMENT_EDIT_SUCCESS("", "部门编辑成功"),
    /** 部门删除失败 */
    DEPARTMENT_DELETE_ERROR("", "部门删除失败"),
    /** 部门删除成功 */
    DEPARTMENT_DELETE_SUCCESS("", "部门删除成功"),
    /** 未知的部门 */
    DEPARTMENT_UNKNOWN("","未知的部门"),

    /** 菜单查询失败 */
    MENU_QUERY_ERROR("", "菜单查询失败"),
    /** 菜单查询成功 */
    MENU_QUERY_SUCCESS("", "菜单查询成功"),
    /** 菜单添加失败 */
    MENU_INSERT_ERROR("", "菜单添加失败"),
    /** 菜单添加成功 */
    MENU_INSERT_SUCCESS("", "菜单添加成功"),
    /** 菜单编辑失败 */
    MENU_EDIT_ERROR("", "菜单编辑失败"),
    /** 菜单编辑成功 */
    MENU_EDIT_SUCCESS("", "菜单编辑成功"),
    /** 菜单删除失败 */
    MENU_DELETE_ERROR("", "菜单删除失败"),
    /** 菜单删除成功 */
    MENU_DELETE_SUCCESS("", "菜单删除成功"),
    /** 未知的菜单 */
    MENU_UNKNOWN("","未知的菜单"),

    /** 菜单权限编辑失败 */
    MENU_AUTH_EDIT_ERROR("", "菜单权限编辑失败"),

    /** 职位查询失败 */
    POSITION_QUERY_ERROR("", "职位查询失败"),
    /** 职位查询成功 */
    POSITION_QUERY_SUCCESS("", "职位查询成功"),
    /** 职位添加失败 */
    POSITION_INSERT_ERROR("", "职位添加失败"),
    /** 职位添加成功 */
    POSITION_INSERT_SUCCESS("", "职位添加成功"),
    /** 职位编辑失败 */
    POSITION_EDIT_ERROR("", "职位编辑失败"),
    /** 职位编辑成功 */
    POSITION_EDIT_SUCCESS("", "职位编辑成功"),
    /** 职位删除失败 */
    POSITION_DELETE_ERROR("", "职位删除失败"),
    /** 职位删除成功 */
    POSITION_DELETE_SUCCESS("", "职位删除成功"),
    /** 未知的职位 */
    POSITION_UNKNOWN("","未知的职位"),

    /** 请求查询失败 */
    URL_QUERY_ERROR("", "请求查询失败"),
    /** 请求查询成功 */
    URL_QUERY_SUCCESS("", "请求查询成功"),
    /** 请求添加失败 */
    URL_INSERT_ERROR("", "请求添加失败"),
    /** 请求添加成功 */
    URL_INSERT_SUCCESS("", "请求添加成功"),
    /** 请求编辑失败 */
    URL_EDIT_ERROR("", "请求编辑失败"),
    /** 请求编辑成功 */
    URL_EDIT_SUCCESS("", "请求编辑成功"),
    /** 请求删除失败 */
    URL_DELETE_ERROR("", "请求删除失败"),
    /** 请求删除成功 */
    URL_DELETE_SUCCESS("", "请求删除成功"),
    /** 未知的请求 */
    URL_UNKNOWN("","未知的请求"),

    /** 请求权限编辑失败 */
    URL_AUTH_EDIT_ERROR("", "请求权限编辑失败"),
    /** 请求参数查询失败 */
    URL_PARAM_QUERY_ERROR("", "请求参数查询失败"),
    /** 请求参数查询成功 */
    URL_PARAM_QUERY_SUCCESS("", "请求参数查询成功"),
    /** 请求参数编辑失败 */
    URL_PARAM_EDIT_ERROR("", "请求参数编辑失败"),

    /** 文件查询成功 */
    FILE_QUERY_SUCCESS("","文件查询成功"),
    /** 文件查询失败 */
    FILE_QUERY_ERROR("","文件查询失败"),
    /** 文件添加失败 */
    FILE_INSERT_ERROR("","文件添加失败"),
    /** 文件上传失败 */
    FILE_UPLOAD_ERROR("","文件上传失败"),
    /** 文件上传成功 */
    FILE_UPLOAD_SUCCESS("","文件上传成功"),
    /** 文件信息编辑失败 */
    FILE_EDIT_ERROR("", "文件信息编辑失败"),
    /** 文件信息编辑成功 */
    FILE_EDIT_SUCCESS("", "文件信息编辑成功"),
    /** 文件信息删除失败 */
    FILE_DELETE_ERROR("", "文件信息删除失败"),
    /** 文件信息删除成功 */
    FILE_DELETE_SUCCESS("", "文件信息删除成功"),
    /** 请选择要上传的文件 */
    FILE_UPLOAD_NO_FILE("","请选择要上传的文件"),
    /** 暂无文件 */
    FILE_LIST_NULL("","暂无文件"),
    /** 未知的文件 */
    FILE_UNKNOWN("","未知的文件"),

    /** 文件类型查询成功 */
    FILE_TYPE_QUERY_SUCCESS("","文件类型查询成功"),
    /** 文件类型查询失败 */
    FILE_TYPE_QUERY_ERROR("","文件类型查询失败"),
    /** 文件类型编辑成功 */
    FILE_TYPE_EDIT_SUCCESS("","文件类型编辑成功"),
    /** 文件类型编辑失败 */
    FILE_TYPE_EDIT_ERROR("","文件类型编辑失败"),
    /** 未知的格式 */
    FILE_TYPE_UNKNOWN("","未知的格式"),

    /** 未知的文件夹 */
    FOLDER_UNKNOWN("","未知的文件夹"),
    /** 文件夹资源类别异常 */
    FOLDER_RESOURCE_TYPE_ERROR("","文件夹资源类别异常"),
    /** 初始化目录失败 */
    FOLDER_INIT_ERROR("","初始化目录失败"),
    /** 文件夹异常 */
    FOLDER_ERROR("","文件夹异常"),

    /** 资源查询成功 */
    RESOURCE_QUERY_SUCCESS("","资源查询成功"),
    /** 资源查询失败 */
    RESOURCE_QUERY_ERROR("","资源查询失败"),

    /** 设定参数查询失败 */
    CONFIG_QUERY_ERROR("","设定参数查询失败"),
    /** 设定参数查询成功 */
    CONFIG_QUERY_SUCCESS("","设定参数查询成功"),

    /** 未知的Code */
    CODE_GROUP_UNKNOWN("","未知的Code"),
    /** Code查询成功 */
    CODE_QUERY_SUCCESS("","Code查询成功"),
    /** Code查询失败 */
    CODE_QUERY_ERROR("","Code查询失败");

    private String code;
    private String message;

    private Message(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

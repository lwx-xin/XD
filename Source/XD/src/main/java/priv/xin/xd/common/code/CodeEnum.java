package priv.xin.xd.common.code;

import java.util.EnumSet;

public enum CodeEnum {

    /** 删除标志-禁用 */
    DEL_FLAG_0("del_flag", "禁用", "0"),
    /** 删除标志-启用 */
    DEL_FLAG_1("del_flag", "启用", "1"),

    /** 平台- 全部 */
    PLATFORM_ALL("platform", "全部", "0"),
    /** 平台-PC端 */
    PLATFORM_PC("platform", "PC端", "1"),
    /** 平台-移动端 */
    PLATFORM_MOBILE("platform", "移动端", "2"),
    /** 平台-其他*/
    PLATFORM_OTHER("platform", "其他", "3"),

    /** 请求方式-全部 */
    REQUEST_METHOD_ALL("request_method", "all method", "0"),
    /** 请求方式-GET */
    REQUEST_METHOD_GET("request_method", "get", "1"),
    /** 请求方式-POST */
    REQUEST_METHOD_POST("request_method", "post", "2"),
    /** 请求方式-DELETE */
    REQUEST_METHOD_DELETE("request_method", "delete", "3"),
    /** 请求方式-PUT */
    REQUEST_METHOD_PUT("request_method", "put", "4"),

    /** 否-false */
    NO("yes_no", "否", "0"),
    /** 是-true */
    YES("yes_no", "是", "1"),

    /** 提示框-不显示 */
    PROMPT_BOX_TYPE_NONE("prompt_box_type","不显示", "-1"),
    /** 提示框-layer */
    PROMPT_BOX_TYPE_LAYER("prompt_box_type","layer", "0"),
    /** 提示框-Toastr */
    PROMPT_BOX_TYPE_TOASTR("prompt_box_type", "Toastr", "1"),
    /** 提示框-SmallPop */
    PROMPT_BOX_TYPE_SMALLPOP("prompt_box_type", "SmallPop", "2"),

    /** 文件类型-其它*/
    FILE_TYPE_OTHER("file_type", "其它", "0"),
    /** 文件类型-音频*/
    FILE_TYPE_AUDIO("file_type", "音频", "1"),
    /** 文件类型-视频*/
    FILE_TYPE_VIDEO("file_type", "视频", "2"),
    /** 文件类型-文本*/
    FILE_TYPE_TXT("file_type", "文本", "3"),
    /** 文件类型-日志*/
    FILE_TYPE_LOG("file_type", "日志", "4"),
    /** 文件类型-图片*/
    FILE_TYPE_PICTURE("file_type", "图片", "5"),

    /** 资源类别-系统资源 */
    RESOURCE_TYPE_SYSTEM("resource_type","系统资源","0"),
    /** 资源类别-自定义资源 */
    RESOURCE_TYPE_CUSTOM("resource_type","自定义资源","1");

    private String group;
    private String name;
    private String value;
    private CodeEnum(String group, String name, String value){
        this.group = group;
        this.name = name;
        this.value = value;
    }

    public String getGroup(){
        return group;
    }

    public String getName(){
        return name;
    }

    public String getValue(){
        return value;
    }

    /**
     * 获取code的value
     * @param group
     * @param name 不区分大小写
     * @return
     */
    public static String getValueByName(String group, String name){
        EnumSet<CodeEnum> codes = EnumSet.allOf(CodeEnum.class);
        for (CodeEnum code : codes) {
            if (code.group.equals(group) && code.name.equalsIgnoreCase(name)) {
                return code.value;
            }
        }
        return null;
    }

}

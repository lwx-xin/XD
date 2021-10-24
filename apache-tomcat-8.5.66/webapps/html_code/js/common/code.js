const Code = {
	/** 删除标志-禁用 */
	"DEL_FLG_0": {"group":"del_flg", "name":"禁用", "value":"0"},
	/** 删除标志-启用 */
	"DEL_FLG_1": {"group":"del_flg", "name":"启用", "value":"1"},
	
	/** 平台- 全部*/
	"PLATFORM_WEB_ALL": {"group":"platform", "name":"All", "value":"0"},
	/** 平台-Window */
	"PLATFORM_WEB_WINDOW": {"group":"platform", "name":"Window", "value":"1"},
	/** 平台-Mac */
	"PLATFORM_APP_MAC": {"group":"platform", "name":"Mac", "value":"2"},
	/** 平台-Android */
	"PLATFORM_APP_ANDROID": {"group":"platform", "name":"Android", "value":"3"},
	/** 平台-IOS */
	"PLATFORM_APP_IOS": {"group":"platform", "name":"IOS", "value":"4"},
	
	/** 请求方式-全部 */
	"REQUEST_METHOD_ALL": {"group":"request_method", "name":"all method", "value":"0"},
	/** 请求方式-get */
	"REQUEST_METHOD_GET": {"group":"request_method", "name":"get", "value":"1"},
	/** 请求方式-post */
	"REQUEST_METHOD_POST": {"group":"request_method", "name":"post", "value":"2"},
	/** 请求方式-delete */
	"REQUEST_METHOD_DELETE": {"group":"request_method", "name":"delete", "value":"3"},
	/** 请求方式-put */
	"REQUEST_METHOD_PUT": {"group":"request_method", "name":"put", "value":"4"},
	
	/** 否-false */
	"NO": {"group":"yes_no", "name":"否", "value":"0"},
	/** 是-true */
	"YES": {"group":"yes_no", "name":"是", "value":"1"},
	
	/** 禁用 */
	"disable": {"group":"use_type", "name":"禁用", "value":"0"},
	/** 启用 */
	"enable": {"group":"use_type", "name":"启用", "value":"1"},
	
	/** 文件类型-文件夹*/
	"FILE_TYPE_FOLDER": {"group":"file_type", "name":"folder", "value":"-1"},
	/** 文件类型-其它*/
	"FILE_TYPE_OTHER": {"group":"file_type", "name":"other", "value":"0"},
	/** 文件类型-音频*/
	"FILE_TYPE_AUDIO": {"group":"file_type", "name":"audio", "value":"1"},
	/** 文件类型-视频*/
	"FILE_TYPE_VIDEO": {"group":"file_type", "name":"video", "value":"2"},
	/** 文件类型-文本*/
	"FILE_TYPE_TXT": {"group":"file_type", "name":"txt", "value":"3"},
	/** 文件类型-日志*/
	"FILE_TYPE_LOG": {"group":"file_type", "name":"log", "value":"4"},
	/** 文件类型-图片*/
	"FILE_TYPE_PICTURE": {"group":"file_type", "name":"picture", "value":"5"},
	
	/** 资源类别-系统资源 */
	"RESOURCE_TYPE_SYSTEM": {"group":"resource_type", "name":"系统资源", "value":"-1"},
	/** 资源类别-用户根目录 */
	"RESOURCE_TYPE_ROOT": {"group":"resource_type", "name":"用户根目录", "value":"0"},
	/** 资源类别-自定义资源 */
	"RESOURCE_TYPE_CUSTOM": {"group":"resource_type", "name":"自定义资源", "value":"1"},
}
$.deepFreeze(Code);
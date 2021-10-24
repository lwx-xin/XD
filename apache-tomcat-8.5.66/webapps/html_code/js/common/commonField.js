const commonField = {
	// 是否使用toastr
	"sys_config_toastr_using": "toastrUsing",
	// toastr持续显示时间（单位:毫秒）
	"sys_config_toastr_timeOut": "toastrTimeOut",
	
	// 是否使用SmallPop
	"sys_config_smallpop_using": "smallpopUsing",
	// SmallPop持续显示时间（单位:毫秒）
	"sys_config_smallpop_timeOut": "smallpopTimeOut",
	
	// 后端返回的异常信息
	"sys_err_msg": "systemErrMsg",
	"sys_err_redirect": "systemErrRedirect",
	"sys_err_source_path": "systemErrSourcePath",
	
	// ajax参数验证所需的json数据
	"ajax_check_json": "ajaxCheckJson"
}
$.deepFreeze(commonField);
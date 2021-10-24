const RequestPath = {
	// PC端登录
	login:{"url":"/sys/login", "type":"post"},
	// PC端登出
	logout:{"url":"/sys/logout", "type":"get"},
	//用户的菜单列表
	menu:{"url":"/sys/menu", "type":"get"},
	//登录用户的详细信息
	user:{"url":"/sys/loginUser", "type":"get"},
	
	// 获取系统设置参数
	configList:{"url":"/sys/config", "type":"get"},
	
	//用户列表
	userList:{"url":"/sys/user", "type":"get"},
	userDetail:{"url":"/sys/user/{userId}", "type":"get"},
	userInsert:{"url":"/sys/user", "type":"post"},
	userUpdate:{"url":"/sys/user/{userId}", "type":"put"},
	userDelete:{"url":"/sys/user/{userId}", "type":"delete"},
	
	codeList:{"url":"/sys/code", "type":"get"},
	
	departmentTree:{"url":"/sys/department/tree", "type":"get"},
	departmentAll:{"url":"/sys/department/all", "type":"get"},
	departmentDetail:{"url":"/sys/department/{departmentId}", "type":"get"},
	departmentInsert:{"url":"/sys/department", "type":"post"},
	departmentUpdate:{"url":"/sys/department/{departmentId}", "type":"put"},
	departmentDelete:{"url":"/sys/department/{departmentId}", "type":"delete"},
	
	positionList:{"url":"/sys/position", "type":"get"},
	positionAll:{"url":"/sys/position/all", "type":"get"},
	positionDetail:{"url":"/sys/position/{positionId}", "type":"get"},
	positionInsert:{"url":"/sys/position", "type":"post"},
	positionUpdate:{"url":"/sys/position/{positionId}", "type":"put"},
	positionDelete:{"url":"/sys/position/{positionId}", "type":"delete"},
	
	menuTree:{"url":"/sys/menu/tree", "type":"get"},
	menuDetail:{"url":"/sys/menu/{menuId}", "type":"get"},
	menuInsert:{"url":"/sys/menu", "type":"post"},
	menuUpdate:{"url":"/sys/menu/{menuId}", "type":"put"},
	menuDelete:{"url":"/sys/menu/{menuId}", "type":"delete"},
	
	//请求列表
	urlList:{"url":"/sys/url", "type":"get"},
	urlDetail:{"url":"/sys/url/{urlId}", "type":"get"},
	urlInsert:{"url":"/sys/url", "type":"post"},
	urlUpdate:{"url":"/sys/url/{urlId}", "type":"put"},
	urlDelete:{"url":"/sys/url/{urlId}", "type":"delete"},
	
	//请求参数列表
	urlParamList:{"url":"/sys/url/param/all", "type":"get"},
	
	//权限列表
	authList:{"url":"/sys/auth", "type":"get"},
	authDetail:{"url":"/sys/auth/{authId}", "type":"get"},
	authInsert:{"url":"/sys/auth", "type":"post"},
	authUpdate:{"url":"/sys/auth/{authId}", "type":"put"},
	authDelete:{"url":"/sys/auth/{authId}", "type":"delete"},
	
	// 文件列表
	fileList:{"url":"/sys/file", "type":"get"},
	fileDetail:{"url":"/sys/file/{fileId}", "type":"get"},
	fileUpload:{"url":"/sys/file/upload", "type":"post"},
	fileUpdate:{"url":"/sys/file/{fileId}", "type":"put"},
	fileDelete:{"url":"/sys/file/{fileId}", "type":"delete"},
	fileDownload:{"url":"/sys/file/download/{fileId}", "type":"get"},
	fileImage:{"url":"/sys/file/image/content/{fileId}", "type":"get"},
	fileVideo:{"url":"/sys/file/video/content/{fileId}", "type":"get"},
	
	resourceList:{"url":"/sys/folder/resources", "type":"get"},
	
	// 文件格式列表
	fileTypeList:{"url":"/sys/fileType", "type":"get"},
	fileTypeUpdate:{"url":"/sys/fileType/{fileSuffix}", "type":"put"},
	
}
$.deepFreeze(RequestPath);
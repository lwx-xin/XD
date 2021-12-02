
$(function(){
	ajaxShowMessage("showCookieMessage")
	
	// 取消默认右键菜单
	document.oncontextmenu = function(){
	　　return false;
	}
});

/**
 * 获取页面间传递的参数
 */
function getPageParam(){
	var objs = new Object();
	var strs = window.location.href.split("?");
	if(isNull(strs) || strs.length==1){
		return objs;
	}
	var params = strs[1].split("&");
	for(var i=0;i<params.length;i++){
		var key = params[i].split("=")[0];
		var value = params[i].split("=")[1];
		objs[key]=value;
	}
	return objs;
}

/**
 * 加载code列表
 * @param {Array} paramData [{group: "分组名称", element: "控件名", nullSelect: "是否有[--请选择---]", default:"默认选中"}]
 */
function loadCode(paramData){
	var groups = new Array();
	$.each(paramData, function(i, data){
		groups.push(data.group);
	});
	
	var param = new Object();
	param.groups = JSON.stringify(groups);
	ajax({
		url: RequestPath.codeList.url,
		type: RequestPath.codeList.type,
		data: param,
		success: function(data){
			var codes = data.codes;
			$.each(paramData, function(i, data){
				var codeList = codes[data.group];
				setCodeHtml(data, codeList);
			});
		}
	}, null, false);
}

/**
 * 加载图标选择器
 */
function initIconPicker(){
	var iconArr = new Array();
	$.each(Icon, function(k, iconObj){
		var value = iconObj.value;
		if(value!=""){
			iconArr.push(value);
		}
	});
	$(".icon-picker").iconPicker({
		"icons": iconArr
	});
}

/**
 * 填充code列表HTML
 * @param {String} setting 
 * @param {List} codeList code列表数据
 */
function setCodeHtml(setting,codeList){
	if(isNull(codeList)){
		return false;
	}
	var ele = $("[name='"+setting.element+"']");
	if(setting.nullSelect == null){
		setting.nullSelect = true;
	}
	
	if(ele.is("select")){
		var html = "";
		if(setting.nullSelect){
			html += "<option value=''>--请选择--</option>"
		}
		for(var i=0;i<codeList.length;i++){
			var codeValue = codeList[i].codeValue;
			var codeName = codeList[i].codeName;
			if(isNotNull(setting.default) && setting.default == codeValue){
				html += "<option value='"+codeValue+"' selected>"+codeName+"</option>";
			} else {
				html += "<option value='"+codeValue+"'>"+codeName+"</option>";
			}
		}
		ele.html(html);
	} else if(ele.is("input") && ele.attr("type")=="radio"){
		
	} else if(ele.is("input") && ele.attr("type")=="checkbox"){
		
	}
}

/**
 * 打开弹窗
 */
function openPopup(){
	
}

/**
 * 页面跳转
 * @param {String} url 页面地址
 */
function openWin(url){
	var version_suffix = "v="+new Date().getTime();
	if(url.split("?").length > 1){
		url = url + "&" + version_suffix;
	} else {
		url = url + "?" + version_suffix;
	}
	top.window.location.href = url;
}

/**
 * 登出
 */
function logout(){
	//openWin(RequestPath.logout.url);
	ajax({
		url: RequestPath.logout.url,
		type: RequestPath.logout.type,
		success: function(data){
			removeToken();
			openWin("../login.html");
		}
	}, null, true);
}

/**
 * 登录
 * @param {String} userNumber 账号
 * @param {String} userPassword 密码
 */
function login(userNumber, userPassword){
	if(isNull(userNumber)){
		showMessage("提示", "请输入账号", "error");
	} else if(isNull(userPassword)){
		showMessage("提示", "请输入密码", "error");
	} else {
		ajax({
			url: RequestPath.login.url,
			data: {"userNumber": userNumber,"userPwd": userPassword},
			type: RequestPath.login.type,
			success: function(data){
				// 存储 JWT Token
				saveToken(data.datas.jwtToken);
				openWin("system/home.html");
			}
		}, null, true);
	}
}

/**
 * @param {Map} dataParam 请求参数
 * @param {Map} header 请求头
 * @param {Boolean} isRedirect 成功后是否会跳转页面
 */
function ajax(dataParam, header, isRedirect){
	
	// 事件无法被外部调用方修改
	var eventArray = ["beforeSend","success","error","complete"];
	var contentType = "application/x-www-form-urlencoded";//"application/json";
	
	// 设置请求头
	var headersMap = header;
	if(header == null){
		headersMap = new Object();
	}
	// 设置令牌
	var token = getToken();
	if(isNotNull(token)){
		headersMap["jwtToken"] = token;
	}
	// 设置ajax标识
	headersMap["req-flag"] = "ajax";
	
	var param = {
		dataType: "json",
		contentType: contentType,
		async: false, // false,同步；true,异步
		headers: headersMap, // 请求头
		beforeSend: function (request) {
			loading();
			//发送请求前
			if(dataParam.beforeSend != null){
				dataParam.beforeSend();
			}
			$(".has-error").removeClass("has-error");
		},
		success: function(data){
			var messageTitle="";
			var messageLevel="";
			if(data.status){
				messageTitle = "提示";
				messageLevel = "info";
			} else {
				isRedirect = false;
				messageTitle = "异常";
				messageLevel = "error";
			}
			
			if(data.messages != null){
				var msgObject = new Object();
				msgObject["title"] = messageTitle;
				msgObject["messages"] = JSON.stringify(data.messages);
				// msgObject["redirect"] = "";
				msgObject["url"] = dataParam.url;
				msgObject["type"] = messageLevel;
				if(isRedirect){
					ajaxShowMessage("saveToCookie", msgObject);
				} else {
					ajaxShowMessage("showMessage", msgObject);
				}
			}
			
			if(data.status){
				messageTitle = "提示";
				messageLevel = "info";
				if(dataParam.success != null){
					dataParam.success(data);
				}
			} else {
				isRedirect = false;
				messageTitle = "异常";
				messageLevel = "error";
				if(dataParam.error != null){
					dataParam.error(data);
				}
			}
		},
		error: function(data){
			if(dataParam.error != null){
				dataParam.error(data);
			}
		},
		complete: function(request,status){
			if(dataParam.complete != null){
				dataParam.complete(request,status);
			}
			if(status != "success"){
				var errMessages_response = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_MESSAGES));
				var errUrl_response = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_URL));
				var errRedirect_response = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_REDIRECT));
				var errMsgTitle_response = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_MESSAGE_TITLE));
				var errMsgType_response = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_MESSAGE_TYPE));
				
				var errorParamNames = decodeStr(request.getResponseHeader(Field.SYSTEM_ERROR_PARAM));
				if(isNotNull(errorParamNames)){
					$.each(JSON.parse(errorParamNames), function(i, errorParamName){
						var element = $("#"+errorParamName).length==0 ? $("[name='errorParamName']"):$("#"+errorParamName);
						if($(element).length!=0){
							$(element).parents(".form-group").addClass("has-error");
						}
					});
				}
				
				var msgObject = new Object();
				msgObject["title"] = errMsgTitle_response;
				msgObject["messages"] = errMessages_response;
				msgObject["redirect"] = errRedirect_response;
				msgObject["url"] = dataParam.url;
				msgObject["type"] = errMsgType_response;
				
				if(isNotNull(errRedirect_response)){
					ajaxShowMessage("saveToCookie", msgObject);
					openWin(errRedirect_response);
				} else {
					ajaxShowMessage("showMessage", msgObject);
				}
			}
			removeLoading();
		}
	};
	
	if(dataParam != null){
		
		// 转换url中的 {xxxx} 参数
		if(isNotNull(dataParam.data)){
			$.each(dataParam.data, function(k, v){
				dataParam.url = dataParam.url.replace("{"+k+"}", v);
			});
		}
		
		// 上传文件的参数修改
		if(isNotNull(dataParam.data) && "files" in dataParam.data){
			var formData = new FormData();
			$.each(dataParam.data, function(k, v){
				formData.append(k, v);
			});
			var files = dataParam.data.files;
			if(files != null){
				for(var i=0;i<files.length;i++){
					formData.append("files", files[i]);
				}
			}
			dataParam.processData = false;
			dataParam.contentType = false;
			dataParam.data = formData;
		} else if(dataParam.type == "post" || dataParam.type == "POST" || dataParam.type == "put" || dataParam.type == "PUT"){
			// 将数据转换成JSON传给服务端
			dataParam.data = JSON.stringify(dataParam.data);
			dataParam.contentType = "application/json";
		}
		
		// 将调用方的自定义参数设置到全局参数里面
		$.each(dataParam, function(k, v){
			if($.inArray(k, eventArray) == -1){
				param[k] = v;
			}
		});
	}
	$.ajax(param);
}

/**
 * 设置Token
 * @param {String} token
 */
function saveToken(token){
	// setLocalStorage("jwtToken",token);
	$.cookie("jwtToken", token, { expires: 1, path: '/' });
}

/**
 * 删除Token
 */
function removeToken(){
	// removeLocalStroage("jwtToken");
	$.cookie("jwtToken", "", { expires: 0, path: '/' });
}

/**
 * 获取Token
 */
function getToken(){
	// return getLocalStroage("jwtToken");
	return $.cookie("jwtToken");
}

/**
 * 判断浏览器是否支持html5本地存储
 * @return {Boolean}
 */
function localStorageSupport() {
    return (('localStorage' in window) && window['localStorage'] !== null)
}

/**
 * 设置本地缓存
 * @param {String} key
 * @param {String} value
 */
function setLocalStorage(key,value){
	if(localStorageSupport()){
		localStorage.setItem(key, value);
	}
}

/**
 * 获取本地缓存
 * @param {String} key
 */
function getLocalStroage(key){
	if(localStorageSupport()){
		return localStorage.getItem(key);
	}
	return "";
}

/**
 * 移除本地缓存
 * @param {String} key
 */
function removeLocalStroage(key){
	if(localStorageSupport()){
		localStorage.removeItem(key);
	}
}

/**
 * 清除全部本地缓存
 */
function clearAllLocalStroage(){
	if(localStorageSupport()){
		localStorage.clear();
	}
}

/**
 * @param {String} code = [saveToCookie|showCookieMessage|showMessage]
 * @param {Map} msgObject {"title":"","messages":"","redirect":"","url":"","type":""}
 */
function ajaxShowMessage(code, msgObject){
	if(code == "saveToCookie"){ // 将消息内容保存到cookie中
		// 消息的标题
		var title = msgObject.title;
		//消息主体
		var messages = msgObject.messages;
		//重定向的地址
		var redirect = msgObject.redirect;
		//产生消息的请求
		var url = msgObject.url;
		//消息类型 [error|info|success|warning]
		var type = msgObject.type;
		
		if(isNotNull(title)){
			setCookie(Field.SYSTEM_ERROR_MESSAGE_TITLE, title);
		}
		if(isNotNull(messages)){
			setCookie(Field.SYSTEM_ERROR_MESSAGES, messages);
		}
		if(isNotNull(redirect)){
			setCookie(Field.SYSTEM_ERROR_REDIRECT, redirect);
		}
		if(isNotNull(url)){
			setCookie(Field.SYSTEM_ERROR_URL, url);
		}
		if(isNotNull(type)){
			setCookie(Field.SYSTEM_ERROR_MESSAGE_TYPE, type);
		}
	} else if(code == "showMessage" || code == "showCookieMessage") {
		// 消息的标题
		var title = "";
		//消息主体
		var messages = "";
		//重定向的地址
		var redirect = "";
		//产生消息的请求
		var url = "";
		//消息类型 [error|info|success|warning]
		var type = "";
		
		if(code == "showMessage"){
			title = msgObject.title;
			messages = msgObject.messages;
			redirect = msgObject.redirect;
			url = msgObject.url;
			type = msgObject.type;
		} else {
			title = getCookie(Field.SYSTEM_ERROR_MESSAGE_TITLE);
			messages = getCookie(Field.SYSTEM_ERROR_MESSAGES);
			redirect = getCookie(Field.SYSTEM_ERROR_REDIRECT);
			url = getCookie(Field.SYSTEM_ERROR_URL);
			type = getCookie(Field.SYSTEM_ERROR_MESSAGE_TYPE);
			
			removeCookie(Field.SYSTEM_ERROR_MESSAGE_TITLE);
			removeCookie(Field.SYSTEM_ERROR_MESSAGES);
			removeCookie(Field.SYSTEM_ERROR_REDIRECT);
			removeCookie(Field.SYSTEM_ERROR_URL);
			removeCookie(Field.SYSTEM_ERROR_MESSAGE_TYPE);
		}
		
		var msg = "";
		var b = false;
		if(isNotNull(url) && type == "error"){
			msg += "<div>";
			msg += "<p>请求: "+url+"</p>";
			msg += "</div>";
			b= true;
		}
		if(isNotNull(messages)){
			var messageArr = JSON.parse(messages);
			if(messageArr.length == 1){
				msg += "<div>";
				msg += "<p>["+messageArr[0].code+"] "+messageArr[0].msg+"</p>";
				msg += "</div>";
			} else {
				msg += "<div>";
				for (var i=0;i<messageArr.length;i++) {
					msg += "<p>["+messageArr[i].code+"] "+messageArr[i].msg+"</p>";
				}
				msg += "</div>";
			}
			b= true;
		}
		// if(isNotNull(redirect)){
		// 	msg += "<div>";
		// 	msg += "<p>重定向地址: </p>";
		// 	msg += "<p>"+redirect+"</p>";
		// 	msg += "</div>";
		// 	b= true;
		// }
		if(b){
			showMessage(title, msg, type)
		}
	}
}

/**
 * 显示toastr弹框
 * @param {String} type = [error|info|success|warning]
 * @param {String} title 标题
 * @param {String} messgae 内容
 * @param {Function(Event)} clickFun 点击事件回调函数
 */
function showToastr(type, title, messgae, timeOut, clickFun){
	
	// 持续显示时间（单位:毫秒）
	var timeOut = 0;
	
	toastr.options = {
		"closeButton": true,
		"debug": false,
		"progressBar": true,
		"positionClass": "toast-top-right",
		"onclick": clickFun,
		"showDuration": "400",
		"hideDuration": "1000",
		"timeOut": timeOut,
		"extendedTimeOut": "1000",
		"showEasing": "swing",
		"hideEasing": "linear",
		"showMethod": "fadeIn",
		"hideMethod": "fadeOut"
	}
	
	toastr['info'](messgae, "<strong>"+title+"</strong>");
}

/**
 * 清空Toastr
 */
function closeToastr(){
	toastr.clear();
}

/**
 * 显示SmallPop弹框
 * @param {String} type = [error|info|success|warning]
 * @param {String} title 标题
 * @param {String} messgae 内容
 * @param {Function(Event)} openFun 打开回调函数
 * @param {Function(Event)} closeFun 关闭回调函数
 */
function showSmallPop(type, title, messgae, openFun, closeFun){
	title = "";
	
	if(type == "error"){
		top.smallPop(type, title, messgae, openFun, closeFun);
	} else {
		var ele = $('.spop-container .spop', top.document);
		if(ele==null || ele.length==0){
			ele = $('.spop-container .spop');
		}
		if(ele!=null && ele.length==0){
			top.smallPop(type, title, messgae, openFun, closeFun);
		} else {
			var spopType = ele.eq(0).find("i.spop-icon").attr("class").replace("spop-icon spop-icon--","");
			if(spopType == type){
				var messageEle = $(messgae).find("p");
				var messageArr = new Array();
				messageEle.each(function(i,v){
					messageArr.push($(v).html());
				});
				ele.eq(0).find("div.spop-body div p").each(function(i, v){
					if($.inArray($(v).html(),messageArr) != -1){
						messageArr.splice(jQuery.inArray($(v).html(),messageArr),1);
					}
				});
				$.each(messageArr, function(i, v){
					ele.eq(0).find("div.spop-body div").append("<p>"+v+"</p>");
				});
			} else {
				top.smallPop(type, title, messgae, openFun, closeFun);
			}
		}
	}
}

/**
 * 显示SmallPop弹框
 * @param {String} type = [error|info|success|warning]
 * @param {String} title 标题
 * @param {String} messgae 内容
 * @param {Function(Event)} openFun 打开回调函数
 * @param {Function(Event)} closeFun 关闭回调函数
 */
function smallPop(type, title, messgae, openFun, closeFun){
	// 持续显示时间（单位:毫秒）
	// var timeOut = 1500;
	var timeOut = 0;
	spop({
		template  : '<h4 class="spop-title">'+title+'</h4>'+messgae,// string required. Without it nothing happens!
		style     : type,// error or success
		autoclose : timeOut==0?false:timeOut,// miliseconds
		position  : 'top-right',// top-left top-center bottom-left bottom-center bottom-right
		icon      : true,// or false
		group     : false,// string, add a id reference 
		onOpen    : openFun,
		onClose   : closeFun,
		group: true
	});
}

/**
 * 清空SmallPop
 */
function closeSmallPop(){
	var pops  = $('.spop');
	var popsC = $('.spop-container');

	pops.removeClass('spop--in')
	popsC.remove();
	// setTimeout(function () {
	// 	popsC.remove();
	// }, 300);
}

/**
 * 显示提示信息
 * @param {String} title 标题
 * @param {String} msg 提示内容
 * @param {String} level = [error|info|success|warning]
 */
function showMessage(title, msg, level){
	// getSysConfig(Field.PROMPT_BOX_TYPE,function(type){
	// 	if(type == Code.PROMPT_BOX_TYPE_TOASTR.value){
	// 		showToastr(level, title, msg, null);
	// 	} else if(type == Code.PROMPT_BOX_TYPE_SMALLPOP.value){
	// 		showSmallPop(level, title, msg, null, null);
	// 	} else if(type == Code.PROMPT_BOX_TYPE_LAYER.value){
	// 		layer.msg(msg);
	// 	} else if(type == Code.PROMPT_BOX_TYPE_NONE.value){
	// 		layer.msg(msg);
	// 	}
	// });
	showSmallPop(level, title, msg, null, null);
}

/**
 * 清空提示信息
 */
function clearMessage(){
	closeToastr();//清空toastr
	closeSmallPop();//清空smallpop
}

/**
 * 将图片转换为base64
 * @param {Element} fileEle
 * @param {Function(String))} callBack
 * @example 
 */
function imgToBase64(fileEle, callBack){
	/*图片转Base64 核心代码*/ 
	var file = fileEle.files[0]; 
	//这里我们判断下类型如果不是图片就返回 去掉就可以上传任意文件 
	if (!/image\/\w+/.test(file.type)) { 
		alert("请确保文件为图像类型"); 
		return false; 
	} 
	var reader = new FileReader(); 
	reader.onload = function () {
		callBack(this.result);
	} 
	reader.readAsDataURL(file);
}

/**
 * 获取系统设置参数
 * @param {String} configKey
 * @param {Function} callback
 */
function getSysConfig(configKey, callback){
	$IDB.getConfig(configKey, function(data){
		if(isNotNull(data)){
			callback(data[0].configValue);
		} else {
			ajax({
				url: RequestPath.configList.url,
				type: RequestPath.configList.type,
				success: function(data){
					var configList = data.datas.configList;
					if(isNotNull(configList)){
						$IDB.edit("s_config",configList);
					}
				}
			}, null, false);
			$IDB.getConfig(configKey, function(data){
				if(isNotNull(data)){
					callback(data[0].configValue);
				} else {
					callback(null);
				}
			});
		}
	});
}

/**
 * 编辑系统参数
 * @param {String} configKey
 * @param {String} configValue
 */
function editSysConfig(configKey,configValue){
	$IDB.getConfig(configKey, function(data){
		if(isNotNull(data)){
			data[0].configValue = configValue;
			$IDB.edit("s_config",data[0]).then(function(){
				showMessage("提示","提示框类型修改成功","success");
			});
		} else {
			showMessage("提示","提示框类型修改失败","error");
		}
	});
}

/**
 * 获取code对应的名称
 * @param {String} group code分组
 * @param {String} value code值
 */
function getCodeName(group, value){
	var name = "";
	if(isNotNull(value)){
		$.each(Code, function(k, v){
			if(v.group == group && value == v.value){
				name = v.name;
				return false;
			}
		});
	}
	return name;
}

function loading(){
	//加载动画
	var html = '<div class="loading-wrapper">'+
	'<div class="loading-spinner-box">'+
	'	<div class="loading-configure-border-1">'+
	'		<div class="loading-configure-core"></div>'+
	'	</div>'+
	'	<div class="loading-configure-border-2">'+
	'		<div class="loading-configure-core"></div>'+
	'	</div>'+
	'</div>'+
	'</div>';
	$("body").append(html);
	
	//打开遮罩层
	$("body").append("<div class='mask'></div>");
	$(".mask").fadeIn();
}

function removeLoading(){
	//移除加载动画
	$(".loading-wrapper").remove();
	//移除遮罩层
	$(".mask").fadeOut();
	$(".mask").remove();
}

$(function(){
	
	// 显示cookie中的异常信息
	showCookieErrMsg();
	
	// 取消默认右键菜单
	document.oncontextmenu = function(){
	　　return false;
	}
});

/**
 * 加载图标选择器
 */
function initIconPicker(){
	var iconArr = new Array();
	$.each(commonIcon, function(k, iconObj){
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
 * 加载codelist
 */
function loadCodeList(){
	var groups = new Array();
	$("[code-tag]").each(function(){
		var group = $(this).attr("code-tag");
		if(isNotNull(group)){
			groups.push(group);
		}
	});
	
	var iconIndex = $.inArray("icon", groups);
	if(iconIndex!=-1){
		$.each(commonIcon, function(id, iconData){
			$("select[code-tag='icon']").append("<option value='"+iconData.value+"' class='"+iconData.value+"'> "+iconData.name+"</option>");
		})
		groups.splice(iconIndex, 1);
		$("select[code-tag='icon']").chosen();
	}
	
	if(isNotNull(groups)){
		var codeElements = getCodes(groups);
		if(isNotNull(codeElements)){
			$.each(codeElements, function(group, codeList){
				if(isNotNull(codeList)){
					$.each(codeList, function(i,codeElement){
						$("select[code-tag='"+group+"']").append("<option value='"+codeElement.value+"'>"+codeElement.name+"</option>");
					});
				}
			});
		}
		$(".chosen-select").chosen();
	}
}

/**
 * 显示cookie中的异常信息
 */
function showCookieErrMsg(){
	var systemErrMsg = getCookie(commonField.sys_err_msg);
	var redirectUrl = getCookie(commonField.sys_err_redirect);
	var sourcePath = getCookie(commonField.sys_err_source_path);

	removeCookie(commonField.sys_err_redirect);
	var thisHtmlPath = location.href.split("?")[0].substring(location.href.indexOf("/html"));
	if(isNotNull(redirectUrl) && thisHtmlPath!=redirectUrl){
		if(redirectUrl=="/html/login.html"){
			top.location.href=redirectUrl;
		} else {
			window.location.href=redirectUrl;
		}
	} else {
		removeCookie(commonField.sys_err_msg);
		removeCookie(commonField.sys_err_source_path);
		
		var errInfo = "";
		if(isNotNull(systemErrMsg)){
			errInfo += "异常信息<b>:</b>"+systemErrMsg+"<br/>";
		}
		if(isNotNull(sourcePath)){
			errInfo += "异常资源<b>:</b>"+sourcePath+"<br/>";
		}
		if(isNotNull(errInfo)){
			showInformation("系统提示", errInfo, "info");
		}
	}
}

/**
 * 获取ajax参数验证所需的json数据
 */
function getAjaxCheckJson(){
	var param = new Object();
	param["paramCheck"] = false;
	
	ajax({
		url: requestPath.getAjaxCheckJson.url,
		data: param,
		type: requestPath.getAjaxCheckJson.type,
		success: function(data){
			if(isNotNull(data.data)){
				setLocalStorage(commonField.ajax_check_json,data.data);
			}
		},
		error: function(data){
		}
	});
}

/**
 * 验证ajax请求参数
 * @param {String} url
 * @param {String} type
 * @param {Object} data
 */
function checkAjaxParam(url,type,data){
	var result = new Object();
	var status = true;
	var msg = "";
	var hintMap = new Object();
	
	var checkJson = getLocalStroage(commonField.ajax_check_json);
	if(isNotNull(checkJson) && isNotNull(data)){
		var checkMap = JSON.parse(checkJson);
		$.each(checkMap, function(k, v){
			if(url.match(k)!=null){
				var paramMap = v[type];
				if(paramMap!=null){
					$.each(paramMap, function(paramName, urlParamEntityEx){
						var paramValue = urlParamEntityEx.urlParamValue;
						var paramRequired = urlParamEntityEx.urlParamRequired;
						var paramNull = urlParamEntityEx.urlParamNull;
						var paramHint = urlParamEntityEx.urlParamErrHint;
						
						var value = data[paramName];
						
						if((value==null && paramRequired == Code.YES.value)
							|| (value=="" && paramNull == Code.NO.value)
							|| (value.match(paramValue) == null)){
							status = false;
							hintMap[paramName] = paramHint;
						}
					});
				}
			}
		});
	}
	
	hideInformation();
	$(".has-error").removeClass("has-error");
	if(!status){
		$.each(hintMap, function(paramName, hintMsg){
			showInformation("参数异常", hintMsg, "warning");
			$("#"+paramName).parents(".form-group").addClass("has-error");
		});
	}
	
	result["status"] = status;
	result["errHint"] = hintMap;
	return result;
}

/**
 * ajax
 * @param {Object} param
 */
function ajax(param){
	
	var isAsync = false;
	if(isNotNull(param.async)){
		isAsync = param.async;
	}
	
	var dataType = "json";
	if(isNotNull(param.dataType)){
		dataType = param.dataType;
	}
	
	var data;
	var formData;
	var processData = true;
	var contentType = "application/x-www-form-urlencoded";
	var traditional = false;
	if(isNotNull(param.data)){
		$.each(param.data, function(k, v){
			param.url = param.url.replace("{"+k+"}", v);
		});
		if(param.data.files != null){
			formData = new FormData();
			$.each(param.data, function(k, v){
				formData.append(k, v);
			});
			var files = param.data.files;
			for(var i=0;i<files.length;i++){
				formData.append("files", files[i]);
			}
			processData = false;
			contentType = false;
			data = formData;
		} else {
			data = param.data;
		}
		if(param.traditional!=null){
			traditional = param.traditional;
		}
	}
	
	if(isNull(param.paramCheck) || param.paramCheck == true){
		var checkResult = checkAjaxParam(param.url, param.type, data);
		if(checkResult.status == false){
			if(param.error!=null){
				param.error(checkResult);
			}
			return false;
		}
	}
	// console.log(data);
	// console.log(param);
	
	$.ajax({
		url: param.url,
		data: data,
		dataType: param.dataType,
		type: param.type,
		async: isAsync,//false,同步；true,异步
		processData: processData, 
		contentType: contentType,
		traditional: traditional,
		headers: {  
			"req-flg": "ajax"  
		},
		beforeSend: function (request) {
			//发送请求前
			if(param.beforeSend!=null){
				param.beforeSend();
			}
		},
		success: function(data){
			//console.log(data)
			
			if(isNotNull(data.message)){
				showInformation("提示", data.message, "info");
			}
			
			if(param.success!=null){
				param.success(data);
			}
			
			if(data.errCode=="-1"){
				alert("请重新登录!");
			}
		},
		error: function(data){
			layer.msg("ajax error");
			if(param.error!=null){
				param.error(data);
			}
		},
		complete: function(request,status){
			var sysErrMessage = request.getResponseHeader(commonField.sys_err_msg);
			var redirectUrl = request.getResponseHeader(commonField.sys_err_redirect);
			var sourcePath = request.getResponseHeader(commonField.sys_err_source_path);
			
			if(isNotNull(sysErrMessage)){ console.log("错误信息:"+decodeStr(sysErrMessage)); }
			if(isNotNull(redirectUrl)){ console.log("重定向页面:"+decodeStr(redirectUrl)); }
			if(isNotNull(sourcePath)){ console.log("异常资源:"+decodeStr(sourcePath)); }
			
			var errInfo = "";
			if(isNotNull(sysErrMessage)){
				if(isNull(redirectUrl)){
					errInfo += "异常信息<b>:</b>"+decodeStr(sysErrMessage)+"<br/>";
				} else {
					setCookie(commonField.sys_err_msg, decodeStr(sysErrMessage))
				}
			}
			if(isNotNull(sourcePath)){
				if(isNull(redirectUrl)){
					errInfo += "异常资源<b>:</b>"+decodeStr(sourcePath)+"<br/>";
				} else {
					setCookie(commonField.sys_err_source_path, decodeStr(sourcePath))
				}
			}
			
			if(isNotNull(errInfo)){
				showInformation("系统提示", errInfo, "info");
			}
			
			if(isNotNull(redirectUrl)){
				redirectUrl = decodeStr(redirectUrl);
				if(redirectUrl=="/html/login.html"){
					top.location.href=redirectUrl;
				} else {
					window.location.href=redirectUrl;
				}
			}
			
			if(status=="error"){
				
			} else if(status=="timeout"){
				layer.msg("服务器连接超时，请稍后再试");
			} else if(status=="success"){
				
			}
		}
	});
}

/**
 * 显示toastr弹框
 * @param {String} type = [error|info|success|warning]
 * @param {String} title 标题
 * @param {String} messgae 内容
 * @param {Function(Event)} clickFun 点击事件回调函数
 */
function showToastr(type, title, messgae, clickFun){
	
	// 持续显示时间（单位:毫秒）
	var timeOut = toNumber(getLocalStroage(commonField.sys_config_toastr_timeOut));
	
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
	
	// 持续显示时间（单位:毫秒）
	var timeOut = toNumber(getLocalStroage(commonField.sys_config_smallpop_timeOut));
	
	spop({
		template  : '<h4 class="spop-title">'+title+'</h4>'+messgae,// string required. Without it nothing happens!
		style     : type,// error or success
		autoclose : timeOut==0?false:timeOut,// miliseconds
		position  : 'top-right',// top-left top-center bottom-left bottom-center bottom-right
		icon      : true,// or false
		group     : false,// string, add a id reference 
		onOpen    : openFun,
		onClose   : closeFun
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
 * 将字符串转换为数字
 * @param {Object} str
 */
function toNumber(str){
	if(isNull(str)){
		return 0;
	}
	return Number(str);
}

/**
 * 如果是null,undefined就返回空字符串
 * @param {Object} obj
 */
function formatStr(obj){
	if(isNull(obj)){
		return "";
	} else {
		return obj;
	}
}

/**
 * 判断值是否为空值,空值返回true
 * @param {Object} obj
 * @return {Boolean}
 */
function isNull(obj){
	if(obj==null || obj=="" || (typeof obj=="String" && obj.trim()=="") || JSON.stringify(obj)=="{}" || JSON.stringify(obj)=="[]"){
		return true;
	}
	return false;
}

/**
 * 判断值是否为空值,空值返回false
 * @param {Object} obj
 * @return {Boolean}
 */
function isNotNull(obj){
	return !isNull(obj);
}

/**
 * 设置cookie
 * @param {String} key
 * @param {String} value
 */
function setCookie(key, value){
	$.cookie(key, encodeURI(toAscll(value)), { expires: 1, path: '/' });
}

/**
 * 获取cookie
 * @param {String} key
 */
function getCookie(key){
	return decodeStr($.cookie(key));
}

/**
 * 移除cookie
 * @param {String} key
 */
function removeCookie(key){
	$.cookie(key, "", { expires: 0, path: '/' });
}

/**
 * 清除所有cookie
 */
function clearAllCookie() {  
	var keys = document.cookie.match(/[^ =;]+(?=\=)/g);  
	if(keys) {  
		for(var i = keys.length; i--;)  
			$.cookie(keys[i], "", { expires: 0, path: '/' });
	}  
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
 * 获取分组下的code列表
 * @param {Array} codeGroups code分组
 * @return {Array}
 */
function getCodes(codeGroups){
	var codeElements;
	var param = new Object();
	param["codeGroup"] = JSON.stringify(codeGroups);
	ajax({
		url: requestPath.code.url,
		data: param,
		type: requestPath.code.type,
		success: function(data){
			if(data.status){
				codeElements = data.data;
			}
		},
		error: function(data){
			layer.msg("err");
		}
	});
	return codeElements;
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

/**
 * 退出登录
 */
function logout(){
	setCookie(commonField.sys_err_msg,"退出登录");
	//跳转登录页面
	top.location.href= "../login.html";
}

/**
 * 编码
 * @param {String} str
 */
function encodeStr(str){
	var encode_str = "";
	if(isNotNull(str)){
		var ascllStr = toAscll(str);
		encode_str = encodeURIComponent(ascllStr);
	}
	return encode_str;
}

/**
 * 解码
 * @param {String} str
 */
function decodeStr(str){
	var ascll_str = ""
	if(isNotNull(str)){
		// 带","的ascll编码
		var s = decodeURIComponent(str);
		if(isNotNull(s)){
			var asclls = s.split(",");
			for(var i=0;i<asclls.length;i++){
				ascll_str += String.fromCharCode(asclls[i]);
			}
		}
	}
	return ascll_str;
}

/**
 * 显示提示信息
 * @param {String} title 标题
 * @param {String} msg 提示内容
 * @param {String} level = [error|info|success|warning]
 */
function showInformation(title, msg, level){
	//是否使用toastr
	var toastrUsing_localStorage = getLocalStroage(commonField.sys_config_toastr_using);
	//是否使用SmallPop
	var smallpopUsing_localStorage = getLocalStroage(commonField.sys_config_smallpop_using);
	
	if(Code.enable.value == toastrUsing_localStorage){
		showToastr(level, title, msg, null);
	} else if(Code.enable.value == smallpopUsing_localStorage){
		showSmallPop(level, title, msg, null, null);
	} else {
		layer.msg(msg);
	}
}

/**
 * 清空提示信息
 */
function hideInformation(){
	closeToastr();//清空toastr
	closeSmallPop();//清空smallpop
}

/**
 * 将字符串转换成ascll(","分隔)
 * @param {String} str
 */
function toAscll(str){
	var ascll_arr = new Array();
	if(isNotNull(str)){
		for(var i=0;i<str.length;i++){
			ascll_arr.push(str[i].charCodeAt());
		}
	}
	return ascll_arr.join();
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
 * 修改checkbox选中状态
 * @param {Object} switchElement
 * @param {Booleam} checkedBool
 * @example var ele = new Switchery(document.getElementById("urlParamRequired"), {color: '#1AB394'}); <br> setSwitchery(ele, false);
 */
function setSwitchery(switchElement, checkedBool) {
    if((checkedBool && !switchElement.isChecked()) || (!checkedBool && switchElement.isChecked())) {
        switchElement.setPosition(true);
        switchElement.handleOnchange(true);
    }
}

/**
 * 文件单位转换
 * @param {String} fileSize KB
 */
function formatFileSize(fileSize){
	var size = toNumber(fileSize);
	var units = ["KB","MB","GB","TB"];
	var num = 0;
	var result = "";
	for(var i=0; i<=units.length; i++){
		num = Math.pow(1024, i+1);
		if(fileSize < num){
			result = (fileSize/Math.pow(1024, i)).toFixed(2) + " " + units[i];
			break;
		}
	}
	return result;
}
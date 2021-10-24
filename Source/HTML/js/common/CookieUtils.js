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
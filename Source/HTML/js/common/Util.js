/**
 * 日期转字符串
 * @param {String} fmt = [yyyy-MM-dd HH:mm:ss|yyyy-MM-dd HH:mm|yyyy-MM-dd] 格式
 * @example new Date().toStr("yyyy-MM-dd HH:mm:ss")
 */
Date.prototype.toStr = function(fmt){
    if(this == "" || this == null){
        return "";
    }
    var o = {
        "M+": this.getMonth()+1,
        "d+": this.getDate(),
        "H+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth()+3)/3),
        "S": this.getMilliseconds()
    }
    if(/(y+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4-RegExp.$1.length))
    }
    for(var k in o)
        if(new RegExp("("+k+")").test(fmt))fmt =
            fmt.replace(RegExp.$1, (RegExp.$1.length==1)?(o[k]):(("00"+o[k]).substr((""+o[k]).length)));
    return fmt;
}

/**
 * 字符串转时间
 * @example "2020-11-30 11:05:51".toDate()
 */
String.prototype.toDate = function(){
    if(this == "" || this == null){
        return "";
    }
    var ymd = this.split(" ")[0];
    var hms = this.split(" ")[1];

    var ymd_arr = ymd.split("/");
    if(ymd_arr!=null && ymd_arr[0]==ymd){
        ymd_arr = ymd.split("-");
    }
    if(hms!=null){
        var hms_arr = hms.split(":");
        return new Date(ymd_arr[0]==null?0:ymd_arr[0], ymd_arr[1]-1==null?1:ymd_arr[1]-1, ymd_arr[2]==null?1:ymd_arr[2], hms_arr[0]==null?0:hms_arr[0], hms_arr[1]==null?0:hms_arr[1], hms_arr[2]==null?0:hms_arr[2]);
    }
    return new Date(ymd_arr[0]==null?0:ymd_arr[0], ymd_arr[1]-1==null?1:ymd_arr[1]-1, ymd_arr[2]==null?1:ymd_arr[2]);
}

jQuery.extend({
	/**
	 * 深层冻结对象(对象不允许添加属性，修改属性，删除属性)
	 * @param {Object} o
	 * @example $.deepFreeze(obj);
	 */
	deepFreeze: function(o){
		var prop, propKey;
		Object.freeze(o); // 首先冻结第一层对象
		for (propKey in o) {
			prop = o[propKey];
			if (!o.hasOwnProperty(propKey) || !(typeof prop === "object") || Object.isFrozen(prop)) {
				// 跳过原型链上的属性、基本类型和已冻结的对象.
				continue;
			}
			$.deepFreeze(prop); //递归调用.
		}
	}
});

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
 * 将字符串转换为数字
 * @param {Object} str
 */
function toNumber(str){
	if(isNull(str)){
		return 0;
	}
	return Number(str);
}
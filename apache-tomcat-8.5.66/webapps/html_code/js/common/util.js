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


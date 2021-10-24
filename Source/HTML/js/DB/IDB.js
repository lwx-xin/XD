(function( window ) {
	var dbObject={};
	dbObject.name = "XD";
	dbObject.version = 0.1;
	dbObject.db = null
	
	
	dbObject.init = async () => {
		dbObject.db = new Dexie(dbObject.name);
		await dbObject.db.version(dbObject.version).stores({
			s_config: '&configId,configKey',
			s_url_param: "++urlParamId,urlPath,urlType",
		});
	}
	
	dbObject.edit = async (tableName, data) => {
		if(dbObject.db == null){
			dbObject.init();
		}
		await dbObject.db.open().then((db) => {
			if(Object.prototype.toString.call(data) == "[object Array]"){
				db[tableName].bulkPut(data);
			} else if(Object.prototype.toString.call(data) == "[object Object]"){
				db[tableName].put(data);
			}
		});
	}
	
	dbObject.getConfig = function(configKey, callback){
		if(dbObject.db == null){
			dbObject.init().then(function(){
				dbObject.getConfig(configKey, callback);
			});
		} else {
			dbObject.db.open().then((db) => {
				return db["s_config"].where("configKey").equals(configKey).toArray();
			}).then((data) => {
				callback(data);
			})
		}
	}
	
	dbObject.getUrlParam = async (urlPath, urlType) => {
		if(dbObject.db == null){
			dbObject.init().then(function(){
				dbObject.getUrlParam(urlPath, urlType);
			});
		} else {
			return await dbObject.db.open().then((db) => {
				// return db["s_url_param"].where("urlType").equals(urlType).filter((urlParam) => {
				// 	return regexp(urlParam.urlPath, urlPath);
				// }).toArray();
				return db["s_url_param"].where("urlType").equals(urlType).filter((urlParam)=> regexp(urlParam.urlPath, urlPath)).toArray();
			})
		}
	}
	
	function regexp(regexp, str){
		return RegExp(regexp, "g").exec(str)==str;
	}

  window.$IDB = dbObject;
})(window);


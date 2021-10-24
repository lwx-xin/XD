package priv.xin.xd.common.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonUtil {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

	/**
	 * 字符串转实体 / Map
	 *
	 * @param <T>
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> T toEntity(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseObject(jsonStr, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * Map转实体
	 *
	 * @param <T>
	 * @param map
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static <T> T toEntity(Map map, Class<T> clazz) {
		try {
			return JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 实体转实体
	 *
	 * @param <T>
	 * @param entity
	 * @param clazz
	 * @return
	 */
	public static <T> T toEntity(Object entity, Class<T> clazz) {
		try {
			String json = toJson(entity);
			return toEntity(json, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * 字符串转List
	 *
	 * @param <T>
	 *
	 * @param jsonStr
	 * @param clazz
	 * @return
	 */
	public static <T> List<T> toList(String jsonStr, Class<T> clazz) {
		try {
			return JSON.parseArray(jsonStr, clazz);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * List转List
	 *
	 * @param <T>
	 * @param list
	 * @param clazz
	 * @return
	 */
    public static <T> List<T> toList(List<?> list, Class<T> clazz) {
        try {
            return JSON.parseArray(JsonUtil.toJson(list), clazz);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static <T> List<T> toList(List<?> list, Class<T> clazz, PropertyPreFilter filter) {
        try {
            return JSON.parseArray(JsonUtil.toJson(list,filter), clazz);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

	/**
	 * 实体对象转Map
	 *
	 * @param obj
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Map toMap(Object obj) {
		try {
			return JSON.parseObject(JSON.toJSONString(obj));
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * list转json字符串
	 *
	 * @param arr
	 * @return
	 */
	public static String toJson(Object[] arr) {
		try {
			return JSON.toJSONString(arr);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

    /**
     * list转json字符串
     *
     * @param list
     * @return
     */
    public static String toJson(List list) {
        try {
            return JSON.toJSONString(list);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    /**
     * list转json字符串(只转换制定的字段)
     *
     * @param list
     * @return
     */
    public static String toJson(List list, PropertyPreFilter filter) {
        try {
            return JSON.toJSONString(list, filter);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

	/**
	 * 实体转json字符串
	 *
	 * @param obj
	 * @return
	 */
	public static String toJson(Object obj) {
		try {
			return JSON.toJSONString(obj);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}


}

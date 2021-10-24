package priv.xin.xd.common.util;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {

	// 设置cookie有效路径
	private static final String contextPath = "/";

	// 设置有效时间,单位秒
	private static final Integer maxAge = -1;

	/**
	 * 往cookie中添加数据<br/>
	 * 有效时间：永久
	 * 
	 * @param cookieName  名称
	 * @param cookieValue 值
	 * @param response
	 */
	public static void createCookie(String cookieName, String cookieValue, HttpServletResponse response) {
		cookieValue = cookieValue.replaceAll(",", " ");
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath(contextPath);
//		cookie.setDomain("localhost");
		response.addCookie(cookie);
	}

	/**
	 * 往cookie中添加数据
	 * 
	 * @param cookieName  名称
	 * @param cookieValue 值
	 * @param maxAge      有效时间
	 * @param response
	 */
	public static void createCookie(String cookieName, String cookieValue, Integer maxAge,
			HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(maxAge);
		cookie.setPath(contextPath);
//		cookie.setDomain("localhost");
		response.addCookie(cookie);
	}

	/**
	 * 移除cookie中的数据
	 * 
	 * @param cookieName
	 * @param response
	 */
	public static void removeCookie(String cookieName, HttpServletResponse response) {
		Cookie cookie = new Cookie(cookieName, null);// cookie名字要相同
		cookie.setMaxAge(0);
		cookie.setPath(contextPath);
//		cookie.setDomain("localhost");
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie中的数据
	 * 
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public static String getCookie(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return "";
	}

//	/**
//	 * 在cookie中保存用户信息
//	 * @param userId 用户ID
//	 * @param authIdList 权限列表(jwt加密后的字符串)
//	 * @param response
//	 */
//	public static void saveUserInfoToken(String userId, List<String> authIdList, HttpServletResponse response) {
//
//		UserToken token = new UserToken();
//		if (StrUtil.isNotEmpty(userId)) {
//			token.setUserId(userId);
//		}
//		if (ListUtil.isNotEmpty(authIdList)) {
//			token.setAuthIdList(authIdList);
//		}
//
//		Integer ExpiryDate = 24;
//		Integer unit = Calendar.HOUR;
//
//		// 令牌有效期24h
//		String tokenStr = JwtUtil.createToken(token, unit, ExpiryDate);
//
//		createCookie(CommonField.SYSTEM_USER_INFO, tokenStr, ExpiryDate * 60 * 60, response);
//	}
//
//	/**
//	 * 获取cookie中的用户信息
//	 *
//	 * @param request
//	 * @return jwt加密后的字符串
//	 */
//	public static String getUserInfoToken(HttpServletRequest request) {
//		return getCookie(CommonField.SYSTEM_USER_INFO, request);
//	}
//
//	public static void clearCookie(HttpServletResponse response) {
//		removeCookie(CommonField.SYSTEM_USER_INFO, response);
//	}
	
}

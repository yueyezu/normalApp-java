package org.litu.util.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie工具类 
 * 功能:添加cookie,获取cookie的值，删除cookie
 */
public class CookieUtil {

	/**
	 * 添加cookie
	 * 
	 * @param response  回复
	 * @param key
	 *            cookie主键
	 * @param value
	 *            cookie值
	 */
	public static void addCookie(HttpServletResponse response, String key, String value, int time, String path) {
		Cookie cookie = new Cookie(key, value);
		cookie.setPath(path);
		cookie.setMaxAge(time);
		response.addCookie(cookie);
	}

	/**
	 * 删除cookie
	 * 
	 * @param request  请求
	 * @param response  回复
	 * @param key
	 *            cookie主键
	 */
	public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String key) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(key)) {
					Cookie cookie = new Cookie(key, null);
					cookie.setPath("/");// 设置成跟写入cookies一样的
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
	}

	/**
	 * 取得cookie的值
	 * 
	 * @param request  请求
	 * @param key
	 *            cookie主键
	 */
	public static String getCookieValue(HttpServletRequest request, String key) throws UnsupportedEncodingException {
		for (Cookie cookie : request.getCookies()) {
			if (cookie.getName().equals(key)) {
				return URLDecoder.decode(cookie.getValue(), "UTF-8");
			}
		}
		return null;
	}
}
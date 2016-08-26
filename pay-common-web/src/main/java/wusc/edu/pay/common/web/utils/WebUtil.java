package wusc.edu.pay.common.web.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/**
 * Web操作工具类.
 * 
 * @author WuShuicheng.
 * @version 1.0, 2013-3-23,上午12:22:12.
 */
public final class WebUtil {
	
	/**
	 * 私有构造方法.
	 */
	private WebUtil() {}
	
	/**
	 * 获取客户端的真实IP地址.<br/>
	 * 如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，那么取X-Forwarded-For中第一个非unknown的有效IP字符串。
	 * @param request .
	 * @return IP.
	 */
	public static String getIpAddr(HttpServletRequest request) {
	       String ip = request.getHeader("x-forwarded-for");
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("Proxy-Client-IP");
	       }
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getHeader("WL-Proxy-Client-IP");
	       }
	       if(StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
	           ip = request.getRemoteAddr();
	       }
	       
	       if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip) && ip.contains(",")){
	    	   ip = ip.substring(0, ip.indexOf(",")); // 截取第一个
	       }
	       return ip;
	}
	
	/**
	 * 根据request和sessionKey获取session（如果调用处能提供request时则可调用此方法，性能高）.
	 * @param request .
	 * @param sessionKey .
	 * @return sessionValue .
	 */
	public static Object getSession(HttpServletRequest request, String sessionKey){
		return request.getSession().getAttribute(sessionKey);
	}
	
	/**
	 * 保存Session值（如果调用处能提供request时则可调用此方法，性能高）.
	 * @param request .
	 * @param sessionKey .
	 * @param sessionValue .
	 */
	public static void putSession(HttpServletRequest request, String sessionKey, Object sessionValue){
		request.getSession().setAttribute(sessionKey, sessionValue);
	}
	
	
	/**
	 * 根据session名称删除session值.
	 * @param request .
	 * @param sessionKey .
	 */
	public static void removeSession(HttpServletRequest request, String sessionKey){
		request.getSession().removeAttribute(sessionKey);
	}

	/**
	 * 添加Cookie值（切记，为防止XSS劫持Cookie攻击，在向客户端返回Cookie值时记得设置HttpOnly）.
	 * @param response .
	 * @param name cookie的名称 .
	 * @param value cookie的值 .
	 * @param maxAge cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0, cookie将随浏览器关闭而清除).
	 */
	public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0) {
			cookie.setMaxAge(maxAge);
		}
		response.addCookie(cookie);
	}

	/**
	 * 根据某一Cookie名获取Cookie的值.
	 * @param request .
	 * @param name Cookie的名称 .
	 * @return Cookie值.
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = WebUtil.readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	/**
	 * 从request中读取所有Cookie值,放入Map中.
	 * @param request .
	 * @return cookieMap.
	 */
	private static Map<String, Cookie> readCookieMap(HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int num = 0; num < cookies.length; num++) {
				cookieMap.put(cookies[num].getName(), cookies[num]);
			}
		}
		return cookieMap;
	}
}

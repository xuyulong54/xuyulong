package wusc.edu.pay.webservice.merchant.utils;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

/**
 * 类描述：session监听器
 * 
 * @author: huangbin
 * @date： 日期：2014-1-12 时间：上午11:05:41
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class MySessionContext {

	private static HashMap mymap = new HashMap();

	public static synchronized void AddSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}

	public static synchronized void DelSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}

	public static synchronized HttpSession getSession(String session_id) {
		if (session_id == null)
			return null;
		return (HttpSession) mymap.get(session_id);
	}

}

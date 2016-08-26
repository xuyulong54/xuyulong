package wusc.edu.pay.web.portal.listener;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import wusc.edu.pay.common.utils.string.StringUtil;


/**
 * 自定义的session上下文类.<br/>
 * 
 * @author WuShuicheng.
 * 
 */
@SuppressWarnings("rawtypes")
public class GzzYzzSessionContext {
	/**
	 * 实例.
	 */
	private static GzzYzzSessionContext instance;
	
	/**
	 * 商户session集合map
	 */
	private HashMap mymap;

	
	private GzzYzzSessionContext() {
		mymap = new HashMap();
	}

	/**
	 * 获取单例对象.
	 * @return
	 */
	public static GzzYzzSessionContext getInstance() {
		if (instance == null) {
			instance = new GzzYzzSessionContext();
		}
		return instance;
	}

	/**
	 * 往集合中添加session对象.
	 * @param session
	 */
	@SuppressWarnings("unchecked")
	public synchronized void addSession(HttpSession session) {
		if (session != null) {
			mymap.put(session.getId(), session);
		}
	}

	/**
	 * 移除session.
	 * @param session
	 */
	public synchronized void delSession(HttpSession session) {
		if (session != null) {
			mymap.remove(session.getId());
		}
	}
	
	/**
	 * 根据sessionid移除session.
	 * @param session
	 */
	public synchronized void delSessionById(String sessionid) {
		if (StringUtil.isNotBlank(sessionid) && mymap.containsKey(sessionid)) {
			mymap.remove(sessionid);
		}
	}

	/**
	 * 根据sessionid获取session对象.
	 * @param sessionid 会话ID.
	 * @return session对象.
	 */
	public synchronized HttpSession getSession(String sessionid) {
		if (StringUtil.isBlank(sessionid)) {
			return null;
		}
		return (HttpSession) mymap.get(sessionid);
	}

	public HashMap getMymap() {
		return mymap;
	}

}
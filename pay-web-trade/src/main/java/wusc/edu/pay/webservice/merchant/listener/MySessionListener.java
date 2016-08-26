package wusc.edu.pay.webservice.merchant.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import wusc.edu.pay.webservice.merchant.utils.MySessionContext;


/**
 * 类描述：Session监听器
 * 
 * @author: huangbin
 * @date： 日期：2014-1-12 时间：上午11:07:03
 * @version 1.0
 */
public class MySessionListener implements HttpSessionListener {
	
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		MySessionContext.AddSession(httpSessionEvent.getSession());
	}

	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		HttpSession session = httpSessionEvent.getSession();
		MySessionContext.DelSession(session);
	}
}

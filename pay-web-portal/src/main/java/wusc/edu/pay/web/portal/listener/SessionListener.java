package wusc.edu.pay.web.portal.listener;

import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.web.portal.base.BaseConsts;


/**
 * 会话监听器.<br/>
 * 考虑到会话结束的话，那么对应的登录用户也应该相应的注销登录。<br/>
 * 我们可以写一个Session监听器，监听sessioon销毁的时候，我们将登录的用户注销掉，<br/>
 * 也就是从application中移除。表示该用户已经下线了。
 * 
 * @author WuShuicheng.
 * @CreateTime 2014-02-25.
 * 
 */
public class SessionListener implements HttpSessionListener {

	private Logger log = Logger.getLogger(this.getClass());

	private GzzYzzSessionContext mySessionMap = GzzYzzSessionContext.getInstance();

	/**
	 * 监听session的创建.
	 */
	@Override
	public void sessionCreated(HttpSessionEvent event) {
		// 往自定义会话管理类中添加会话
		HttpSession session = event.getSession();
		log.info("== sessionCreated:" + session.getId());

		mySessionMap.addSession(session);
		log.info("== onlineCount:" + mySessionMap.getMymap().size());
	}

	/**
	 * 监听session的销毁.
	 */
	@SuppressWarnings("unchecked")
	public void sessionDestroyed(HttpSessionEvent event) {
		HttpSession session = event.getSession();
		log.info("== sessionDestroyed:" + session.getId());

		// 在定义会话管理类中移除会话
		mySessionMap.delSession(session);
		log.info("== onlineCount:" + mySessionMap.getMymap().size());

		// 在session销毁的时候 把applicationOperatorMap中保存的键值对清除

		// 如果是商户
		// Object merchant =
		// event.getSession().getAttribute(BaseConsts.MERCHANT_SESSION_KEY);
		// if(merchant != null){
		// MerchantOperator merchantOperator = (MerchantOperator) merchant;
		// log.info("== sessionDestroyed merchant:" +
		// merchantOperator.getLoginName());
		// @SuppressWarnings("unchecked")
		// Map<String, String> merchantApp = (Map<String,
		// String>)event.getSession().getServletContext().getAttribute(BaseConsts.MERCHANT_APPLICATION_KEY);
		// merchantApp.remove(merchantOperator.getLoginName());
		// event.getSession().getServletContext().setAttribute(BaseConsts.MERCHANT_APPLICATION_KEY,
		// merchantApp);
		// }
		// // 如果是会员
		// Object member =
		// event.getSession().getAttribute(BaseConsts.MEMBER_SESSION_KEY);
		// if (member != null){
		// MemberInfo memberInfo = (MemberInfo) member;
		// log.info("== sessionDestroyed member:" + memberInfo.getLoginName());
		// @SuppressWarnings("unchecked")
		// Map<String, String> memberApp = (Map<String,
		// String>)event.getSession().getServletContext().getAttribute(BaseConsts.MEMBER_APPLICATION_KEY);
		// memberApp.remove(memberInfo.getLoginName());
		// event.getSession().getServletContext().setAttribute(BaseConsts.MEMBER_APPLICATION_KEY,
		// memberApp);
		// }

		UserInfo user = (UserInfo) event.getSession().getAttribute(BaseConsts.CURRENT_USER);
		if (user != null) {
			if (user.getUserType() == UserTypeEnum.MERCHANT.getValue() || user.getUserType() == UserTypeEnum.POSAGENT.getValue() ) {
				Map<String, String> merchantApp = (Map<String, String>) event.getSession().getServletContext().getAttribute(BaseConsts.MERCHANT_APPLICATION_KEY);
				merchantApp.remove(user.getLoginName());
				event.getSession().getServletContext().setAttribute(BaseConsts.MERCHANT_APPLICATION_KEY, merchantApp);
			} else if (user.getUserType() == UserTypeEnum.CUSTOMER.getValue()) {
				Map<String, String> memberApp = (Map<String, String>) event.getSession().getServletContext().getAttribute(BaseConsts.MEMBER_APPLICATION_KEY);
				memberApp.remove(user.getLoginName());
				event.getSession().getServletContext().setAttribute(BaseConsts.MEMBER_APPLICATION_KEY, memberApp);
			}
		}
	}
}
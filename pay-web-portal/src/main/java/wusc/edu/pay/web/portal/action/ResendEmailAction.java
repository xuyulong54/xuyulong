package wusc.edu.pay.web.portal.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.biz.MailBiz;


public class ResendEmailAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(ResendEmailAction.class);

	@Autowired
	private MailBiz mailBiz;

	/**
	 * 重送发送邮件
	 * 
	 * @return
	 */
	public void resendEmail() {
		try {

			String userNo = getString("userNo");
			String userType = getString("userType");
			String loginName = getString("loginName");
			String toMail = getString("toMail");
			String mailType = getString("mailType");
			mailBiz.sendEmail(userNo, Integer.parseInt(userType), loginName, toMail, Integer.parseInt(mailType));
		} catch (Exception e) {
			LOG.error(" resendEmail fail:", e);
		}
	}
}

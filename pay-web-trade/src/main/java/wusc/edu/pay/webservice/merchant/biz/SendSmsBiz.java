package wusc.edu.pay.webservice.merchant.biz;

import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;
import org.springframework.ui.velocity.VelocityEngineUtils;

import wusc.edu.pay.common.param.SmsParam;
import wusc.edu.pay.facade.notify.util.NotifyUtil;


/**
 * 
 * @描述: 短息发送业务类.
 * @作者: WuShuicheng.
 * @创建: 2014-9-26,下午12:44:56
 * @版本: V1.0
 * 
 */
@Component("sendSmsBiz")
public class SendSmsBiz {

	private static final Log LOG = LogFactory.getLog(SendSmsBiz.class);

	@Autowired
	private VelocityEngine velocityEngine; // Velocity 模板引擎（spring配置中定义）

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	/**
	 * 短信内容模板解释.
	 * 
	 * @param smsTemplatePath
	 *            短信模板路径.
	 * @param paramModel
	 *            模板中的参数变量.
	 * @return content
	 * @throws Exception
	 */
	public String mergeSmsTemplate(String smsTemplatePath, Map<String, Object> paramModel) throws Exception {
		try {
			return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, smsTemplatePath, "UTF-8", paramModel);
		} catch (Exception e) {
			LOG.error("==>merge " + smsTemplatePath + " exception:", e);
			throw e;
		}
	}

	/**
	 * 发送短信.
	 * 
	 * @param phone
	 *            手机号.
	 * @param content
	 *            短信内容.
	 */
	public void sendSms(String phone, String content) {
		final SmsParam smsParam = new SmsParam(phone, content);

		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatSms(smsParam));
			}
		});
	}
}

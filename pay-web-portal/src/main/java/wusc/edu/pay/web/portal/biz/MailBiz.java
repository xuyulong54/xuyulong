package wusc.edu.pay.web.portal.biz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.constant.PublicStatus;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.param.MailParam;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.token.TokenProductFactory;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.boss.service.EmailFacade;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.util.PropertiesUtil;


/**
 * 
 * @描述: 邮件发送的业务逻辑类.
 * @作者: LiLiqiong,WuShuicheng.
 * @创建: 2014-6-4,下午4:18:32
 * @版本: V1.0
 * 
 */
@Component("mailBiz")
public class MailBiz {

	private static final Log LOG = LogFactory.getLog(MailBiz.class);

	@Autowired
	private JmsTemplate notifyJmsTemplate;
	@Autowired
	private VelocityEngine velocityEngine;// spring配置中定义
	@Autowired
	private EmailFacade emailFacade;

	/**
	 * 发送邮件
	 * 
	 * @param userID
	 *            用户ID
	 * @param userType
	 *            用户类型
	 * @param loginName
	 *            登录名
	 * @param toMail
	 *            收件邮箱
	 * @param mailType
	 *            邮件类型
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String sendEmail(String userNo, int userType, String loginName, String toMail, int mailType) {
		Properties properties = PropertiesUtil.getPropertiesValue("email.properties");
		String mailTypeDesc = PublicTemplateEnum.getName(mailType);
		String templateName = properties.getProperty(mailTypeDesc + "_VM");// 模板
		String subject = properties.getProperty(mailTypeDesc + "_SUB");// 主题
		String validTime = mailTypeDesc + "_TIME";// 有效时间
		String url = mailTypeDesc + "_URL";// URL

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		map.put("userType", userType);
		map.put("type", mailType);
		map.put("status", PublicStatus.INACTIVE);
		List<EmailVerify> list = emailFacade.listModelByCondition(map);
		EmailVerify emailVerify = null;
		if (!ValidateUtils.isEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				emailVerify = list.get(i);
				emailVerify.setStatus(PublicStatus.ACTIVE);
				emailFacade.updateEmailVerify(emailVerify);
			}
		}
		Date validTime1 = DateUtils.addMinute(new Date(), Integer.parseInt(properties.getProperty(validTime)));
		// 创建实体
		String token = productToken();
		LOG.info("===>send mail token:" + token);
		emailVerify = new EmailVerify();
		emailVerify.setValidTime(validTime1);
		emailVerify.setToken(token);
		emailVerify.setType(mailType);
		emailVerify.setStatus(PublicStatus.INACTIVE);
		emailVerify.setUserNo(userNo);
		emailVerify.setUserType(userType);
		emailVerify.setToEmail(toMail);
		emailFacade.createEmailVerify(emailVerify);

		// 封装邮件发送
		Map<String, Object> mapModel = new HashMap<String, Object>();
		mapModel.put("loginName", loginName);
		mapModel.put("validTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(emailVerify.getValidTime()));
		mapModel.put("la", PublicConfig.PROTAL_URL + properties.getProperty(url) + token);
		mapModel.put("COMPANY_FOR", PublicConfig.COMPANY_FOR);


		// 邮件内容
		String content = "";
		try {
			content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "UTF-8", mapModel);
		} catch (Exception e) {
			LOG.error("==>mergeTemplateIntoString exception:", e);
		}

		sendMail(toMail, subject, content);// 发送邮件
		return PublicConfig.PROTAL_URL + properties.getProperty(url) + token;
	}

	/***
	 * 验证用户的token是否正确
	 * 
	 * @param token
	 * @param emailVerify
	 * @param emailFacade
	 * @return
	 */
	/*
	 * public Map<String, String> validateToken(EmailVerify emailVerify) {
	 * Map<String, String> msgMap = new HashMap<String, String>(); String
	 * errorMsg = BaseConsts.PAGE_ERROR_MSG; if
	 * (ValidateUtils.isEmpty(emailVerify)) { msgMap.put(errorMsg, "链接无效");
	 * return msgMap; } Long validTime = emailVerify.getValidTime().getTime();
	 * Long nowTime = new Date().getTime(); if (nowTime >= validTime) {
	 * msgMap.put(errorMsg, "链接超时"); return msgMap; } if
	 * (emailVerify.getStatus().intValue() == PublicStatus.ACTIVE) {
	 * msgMap.put(errorMsg, "链接无效"); return msgMap; } Map<String, Object> param
	 * = new HashMap<String, Object>(); param.put("type",
	 * emailVerify.getType()); param.put("userNo", emailVerify.getUserNo());
	 * param.put("userType", emailVerify.getUserType()); param.put("token",
	 * emailVerify.getToken());// 不等于些token的数据 param.put("status",
	 * PublicStatus.ACTIVE); emailFacade.updateStatus(param); return msgMap; }
	 */

	/**
	 * 生成加密TOKEN(系统标示+UUID)
	 * 
	 * @return
	 */
	public String productToken() {
		return TokenProductFactory.getInstallBase64().encrypt(BaseConsts.SYSTEM_NAME + "," + StrUtil.get32UUID());
	}

	/**
	 * 验证TOKEN
	 * 
	 * @param mtoken
	 * @return
	 * @throws ParseException
	 */
	public Map<String, Object> validateToken(String mtoken) {
		Map<String, Object> msgMap = new HashMap<String, Object>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		// 判断密文TOKEN
		if (ValidateUtils.isEmpty(mtoken)) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}
		// 解密后的TOKEN
		String token = TokenProductFactory.getInstallBase64().decrypt(mtoken);
		LOG.info("===>decrypt token" + token);
		if (ValidateUtils.isEmpty(token)) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}
		String[] paramArr = token.split(",");
		if (paramArr == null || paramArr.length != 2) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}
		if (!BaseConsts.SYSTEM_NAME.equals(paramArr[0])) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}

		EmailVerify emailVerify = emailFacade.getEmailVerifyByToken(mtoken);
		if (ValidateUtils.isEmpty(emailVerify)) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}
		Long validTime = emailVerify.getValidTime().getTime();
		Long nowTime = new Date().getTime();
		if (nowTime >= validTime) {
			msgMap.put(errorMsg, "链接超时");
			return msgMap;
		}
		if (emailVerify.getStatus().intValue() == PublicStatus.ACTIVE) {
			msgMap.put(errorMsg, "链接无效");
			return msgMap;
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", emailVerify.getType());
		param.put("userNo", emailVerify.getUserNo());
		param.put("userType", emailVerify.getUserType());
		param.put("token", emailVerify.getToken());// 不等于些token的数据
		param.put("status", PublicStatus.ACTIVE);
		emailFacade.updateStatus(param);

		msgMap.put(BaseConsts.EMAILVERIFY_MAP_KEY, emailVerify);
		return msgMap;
	}

	/**
	 * 发送邮件
	 * 
	 * @param templateName
	 *            模板名称
	 * @param toMail
	 *            发送人
	 * @param subject
	 *            主题
	 * @param content
	 *            邮件内容
	 * @param mapModel
	 *            模板参数
	 */
	public void sendMail(String toMail, String subject, String content) {
		final MailParam mailParam = new MailParam(toMail, subject, content);
	        notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatMail(mailParam));
			}
		});
	}

	/**
	 * 将邮箱验证记录置为无效.
	 * 
	 * @param token
	 */
	public void updateEmailVerifyStatus(String token) {
		EmailVerify emailVerify = emailFacade.getEmailVerifyByToken(token);
		emailVerify.setStatus(PublicStatus.INACTIVE);
		emailFacade.updateEmailVerify(emailVerify);
	}
}

package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MemberLogonAction;
import wusc.edu.pay.web.portal.biz.MailBiz;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title:会员注册</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-4
 */

@SuppressWarnings("serial")
public class RegisterAction extends MemberLogonAction {
	private static Log LOG = LogFactory.getLog(RegisterAction.class);
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserManagementFacade userManagementFacade;

	/**
	 * 注册:进入验证账户名页面
	 * 
	 * @return
	 */
	public String checkLoginNameUI() {
		return "checkLoginNameUI";
	}

	/**
	 * 注册:验证账户名
	 * 
	 * @return
	 */
	public String checkLoginName() {
		String loginName = StringTools.stringToTrim(getString("loginName"));// 用户名
		String randomCode = StringTools.stringToTrim(getString("randomCode"));// 验证码

		// 1.验证页面参数
		Map<String, String> msgMap = validateCheckLoginName(getHttpRequest(), loginName, randomCode);
		if (!msgMap.isEmpty()) {
			this.pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		// 2.判断是手机还是邮箱注册
		if (ValidateUtils.isEmail(loginName)) {// 邮箱注册
			String url = mailBiz.sendEmail(loginName, UserTypeEnum.CUSTOMER.getValue(), loginName, loginName, PublicTemplateEnum.MAIL_MEMBER_RES_MA.getValue());
			putData("userNo", loginName);
			putData("userType", UserTypeEnum.CUSTOMER.getValue());
			putData("loginName", loginName);
			putData("toMail", loginName);
			putData("mailType", PublicTemplateEnum.MAIL_MEMBER_RES_MA.getValue());
			putData("url", url);
			LOG.info("会员注册流程--》 1.验证用户名，发送验证邮件成功,登录名[" + loginName + "] . ");
			return "checkLoginName";
		} else {// 手机注册
			putData("showPhone", StringTools.phoneChange(loginName));
			putData("token", DigestUtils.sha1Hex(loginName + UserTypeEnum.CUSTOMER.getValue()));// 签名
			putData("loginName", loginName);
			LOG.info("会员注册流程--》 1.验证用户名，去验证手机号页面,登录名[" + loginName + "] . ");
			return "mobileVerificationCode";
		}
	}

	/**
	 * 会员手机注册，手机动态码验证
	 * 
	 * @return
	 */
	public String checkPhoneCode() {
		String token = StringTools.stringToTrim(getString("token"));
		String loginName = StringTools.stringToTrim(getString("loginName"));
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));

		// 1.验证页面参数
		Map<String, String> msgMap = validatePhoneCode(loginName, phoneCode, token);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2.设置相关信息到页面
		putData("token", token);
		putData("loginName", loginName);
		putData("questions", SecurityQuestionEnum.values());// 安全问题
		putData("registerType", "phone");// 标示是手机注册
		LOG.info("会员注册流程--》 2.手机验证码验证成功,登录名[" + loginName + "] . ");
		return "setIdentityInfoUI";
	}

	/**
	 * 会员点击验证邮箱地址后的操作 会员注册:进入设置身份信息页面
	 * 
	 * @return
	 */
	public String setIdentityInfoUI() {
		String token = getString("token");
		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2.设置相关信息到页面
		putData("token", token);
		putData("questions", SecurityQuestionEnum.values());// 安全问题
		LOG.info("会员注册流程--》 2.邮箱链接验证成功,登录名[" + ((EmailVerify) obj).getUserNo() + "] . ");
		return "setIdentityInfoUI";
	}

	/**
	 * 注册:设置身份信息
	 * 
	 * @return
	 */
	public String setIdentityInfo() {
		String token = getString("token");
		String registerType = getString("registerType");// 注册类型（phone:手机注册）
		String loginName = StringTools.stringToTrim(getString("loginName"));// 登录名

		// 1.验证TOKEN
		Map<String, Object> msgMap = new HashMap<String, Object>();
		if (!"phone".equals(registerType)) {// 邮箱注册
			msgMap = mailBiz.validateToken(token);
			Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
			if (!msgMap.isEmpty()) {
				pushData(msgMap);
				return BaseConsts.ACTION_ERROR_ADD;
			}
			loginName = ((EmailVerify) obj).getUserNo();
			// 更新TOKEN状态,改为“已验证”
			mailBiz.updateEmailVerifyStatus(token);
		} else {// 手机注册
			if (!token.equals(DigestUtils.sha1Hex(loginName + UserTypeEnum.CUSTOMER.getValue()))) {
				msgMap.put(BaseConsts.PAGE_ERROR_MSG, "参数丢失:TOKEN");
				pushData(msgMap);
				return BaseConsts.ACTION_ERROR_ADD;
			}
		}

		String loginPwd = StringTools.stringToTrim(getString("loginPwd"));// 登录密码
		String reLoginPwd = StringTools.stringToTrim(getString("reLoginPwd"));// 确认登录密码
		//注册不用密码控件
		String tradePwd = StringTools.stringToTrim(getString("tradePwd"));//super.getPwd("tradePwd");// 支付密码
		String reTradePwd = StringTools.stringToTrim(getString("reTradePwd"));// 确认支付密码
		String question = StringTools.stringToTrim(getString("question"));// 安全问题
		String answer = StringTools.stringToTrim(getString("answer"));// 回答
		String greeting = StringTools.stringToTrim(getString("greeting"));// 预留信息
		String realName = StringTools.stringToTrim(getString("realName"));// 真实姓名
		String cardNo = StringTools.stringToTrim(getString("cardNo"));// 身份证号

		// 2.验证页面参数
		Map<String, String> msgMap1 = validateSetIdentityInfo(loginPwd, reLoginPwd, tradePwd, reTradePwd, question, answer, realName, cardNo, greeting);
		if (!msgMap1.isEmpty()) {
			pushData(msgMap1);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 3.创建相关信息（用户表，用户操作员表，会员表，账户表，账户密码表，账户安全表）
		userManagementFacade.registerMember(loginName, DigestUtils.sha1Hex(reLoginPwd), DigestUtils.sha1Hex(tradePwd), question, answer, greeting, realName, cardNo, "", "", "", "");

		// 4.设置相关信息到页面
		putData("loginName", loginName);
		LOG.info("会员注册流程--》 3.注册会员成功,商户登录名[" + loginName + "] . ");
		return "registerSuccess";
	}

	// ************************************验证***************************************//
	private Map<String, String> validatePhoneCode(String loginName, String phoneCode, String token) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;
		// token:DigestUtils.sha1Hex(用户名+会员类型)
		if (ValidateUtils.isEmpty(token) || !token.equals(DigestUtils.sha1Hex(loginName + UserTypeEnum.CUSTOMER.getValue()))) {
			msgMap.put(msgErrorKey, "参数错误");
			return msgMap;
		}
		// 验证码：必填，与正确验证码相同，且不区分大小写
		if (!ValidateUtil.isValidatePhoneCode(loginName, phoneCode, getCurrentPhoneCode())) {
			msgMap.put(msgErrorKey, "验证码有误");
		}
		return msgMap;
	}

	private Map<String, String> validateCheckLoginName(HttpServletRequest req, String loginName, String randomCode) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;
		// 用户名：必填，邮箱（不可为雅虎邮箱）或者手机号
		if (ValidateUtils.isEmpty(loginName) || (!ValidateUtils.isEmail(loginName) && !ValidateUtils.isMobile(loginName))) {
			msgMap.put("loginNameMsg", "请正确输入手机号码或者邮箱地址");
			return msgMap;
		}
		
		// 用户名：唯一性
		UserInfo userInfo = super.getUserInfoByLoginName(loginName);
		if (!ValidateUtils.isEmpty(userInfo)) {
			msgMap.put(msgErrorKey, "该用户名已存在");
			return msgMap;
		}
		// 验证码：必填，与正确验证码相同，且不区分大小写
		if (ValidateUtil.isValidateCode(req, randomCode)) {
			msgMap.put(msgErrorKey, "请输入正确的验证码");
			return msgMap;
		}
		return msgMap;
	}

	private Map<String, String> validateSetIdentityInfo(String loginPwd, String reLoginPwd, String tradePwd, String reTradePwd, String question, String answer, String realName, String cardNo, String greeting) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String msgErrorKey = BaseConsts.PAGE_ERROR_MSG;
		// 登录密码：必填，8~20位不连续的数字、字母和特殊符号组合
		String pwdMsg = ValidateUtil.isValidatePwd(loginPwd);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(msgErrorKey, pwdMsg);
			return msgMap;
		}
		// 再次输入登录密码:必填，上次密码一致
		if (!loginPwd.equals(reLoginPwd)) {
			msgMap.put(msgErrorKey, "两次输入密码不一致");
			return msgMap;
		}
		// 支付密码： 必填，8~20位不连续的数字、字母和特殊符号组合
		pwdMsg = ValidateUtil.isValidatePwd(tradePwd);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(msgErrorKey, pwdMsg);
			return msgMap;
		}
		// 再次输入支付密码:必填，上次密码一致
		if (!tradePwd.equals(reTradePwd)) {
			msgMap.put(msgErrorKey, "两次输入密码不一致");
			return msgMap;
		}
		// 登录支付密码：不可相同
		if (loginPwd.equals(tradePwd)) {
			msgMap.put(msgErrorKey, "支付密码不能与登录密码一样");
			return msgMap;
		}
		// 安全问题：必选
		if (ValidateUtils.isEmpty(question)) {
			msgMap.put(msgErrorKey, "请选择安全问题");
			return msgMap;
		}
		// 安全问题回答：必填，2~16个字
		if (ValidateUtils.isEmpty(answer) || answer.length() < 2 || answer.length() > 16) {
			msgMap.put(msgErrorKey, "请输入长度为2~16的字");
			return msgMap;
		}
		// 预留信息：必填，5~20个字
		if (ValidateUtils.isEmpty(greeting) || greeting.length() < 5 || answer.length() > 20) {
			msgMap.put(msgErrorKey, "请输入长度为5~20的字");
			return msgMap;
		}
		// 真实姓名：2-10位的中文
		if (ValidateUtils.isEmpty(realName) || realName.length() < 2 || realName.length() > 10 || !ValidateUtils.isChinese(realName)) {
			msgMap.put(msgErrorKey, "请输入2-10位的中文");
			return msgMap;
		}
		// 身份证号：18位正确格式的身份证号
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo)) {
			msgMap.put(msgErrorKey, "请输入18位正确格式的身份证号");
			return msgMap;
		}
		return msgMap;
	}
}

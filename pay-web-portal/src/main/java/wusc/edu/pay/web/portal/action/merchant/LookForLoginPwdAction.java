package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.biz.MailBiz;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * 用户找回登录密码
 * 
 * @author liliqiong
 * @date 2013-12-11
 * @version 1.0
 */
public class LookForLoginPwdAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;

	/**
	 * 登录密码:去验证帐户名页面
	 * 
	 * @return
	 */
	public String loginPwdCheckLoginNameUI() {
		return "loginPwdCheckLoginNameUI";
	}

	/**
	 * 登录密码:验证帐户名
	 * 
	 * @return
	 */
	public String loginPwdCheckLoginName() {
		String loginName = StringTools.stringToTrim(getString("loginName"));// 用户名
		String randomCode = StringTools.stringToTrim(getString("randomCode"));// 验证码
		// 验证参数
		Map<String, String> msgMap = validateLoginPwdCheckLoginName(getHttpRequest(), loginName, randomCode);
		String userNo = msgMap.remove("userNo");
		if (!msgMap.isEmpty()) {
			return BaseConsts.ACTION_ERROR_ADD;
		}
		// 签名：sha1Hex(loginName + userNo)
		putData("token", DigestUtils.sha1Hex(loginName + userNo));
		putData("loginName", loginName);
		return "loginPwdCheckLoginName";
	}

	/**
	 * 登录密码：发送邮件进行验证
	 * 
	 * @return
	 */
	public String loginPwdSendEmailCheck() {

		String loginName = StringTools.stringToTrim(getString("loginName"));
		String token = StringTools.stringToTrim(getString("token"));

		// 判断传递参数是否被改动（loginName,token）
		UserInfo userInfo = validatePassParam(loginName, token);

		// 判断是否绑定邮件
		Map<String, String> msgMap = validateAccountSecurity(userInfo, "email");
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		int mailType;
		if (userInfo.getUserType() == 3) {//该用户为会员
			 mailType = PublicTemplateEnum.MAIL_MEMBER_FIND_CODE_LOGIN_MA.getValue();
		}
		else {
			 mailType = PublicTemplateEnum.MAIL_MERCHANT_FIND_CODE_LOGIN_MA.getValue();
		}
		// 发送邮件
		String userNo = userInfo.getUserNo();
		String toMail = userInfo.getBindEmail();
		String url = mailBiz.sendEmail(userNo, userInfo.getUserType(), loginName, toMail, mailType);
		putData("email", StringTools.phoneChange(toMail));
		putData("userNo", userNo);
		putData("userType", userInfo.getUserType());
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", mailType);
		putData("url", url);
		return "loginPwdSendEmailCheck";
	}

	/**
	 * 登录密码：进入手机验证页面
	 * 
	 * @return
	 */
	public String loginPwdSendPhoneCodeCheckUI() {
		String loginName = StringTools.stringToTrim(getString("loginName"));
		String token = StringTools.stringToTrim(getString("token"));

		// 判断传递参数是否被改动（loginName,token）
		UserInfo userInfo = validatePassParam(loginName, token);

		// 判断是否绑定手机
		Map<String, String> msgMap = validateAccountSecurity(userInfo, "phone");
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		putData("loginName", loginName);
		putData("token", token);
		return "loginPwdSendPhoneCodeCheckUI";
	}

	/**
	 * 登录密码：通过手机动态码进行验证
	 * 
	 * @return
	 */
	public String loginPwdSendPhoneCodeCheck() {
		String bindingPhone = StringTools.stringToTrim(getString("bindingPhone"));
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));

		String loginName = StringTools.stringToTrim(getString("loginName"));
		String token = StringTools.stringToTrim(getString("token"));
		// 判断传递参数是否被改动（loginName,token）
		UserInfo userInfo = validatePassParam(loginName, token);

		Map<String, String> msgMap = validateLoginPwdSendPhoneCodeCheck(loginName, bindingPhone, phoneCode, userInfo);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		putData("loginName", loginName);
		putData("token", token);
		putData("lookForType", "phone");
		return "editLoginPwdUI";
	}

	/**
	 * 点击邮件链接，去修改登录密码页面
	 * 
	 * @return
	 */
	public String editLoginPwdUI() {

		String token = StringTools.stringToTrim(getString("token"));

		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		UserInfo userInfo = super.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
		putData("loginName", userInfo.getLoginName());
		putData("token", token);
		putData("lookForType", "email");
		return "editLoginPwdUI";
	}

	/**
	 * 登录密码：修改登录密码
	 * 
	 * @return
	 */
	public String editLoginPwd() {
		String lookForType = getString("lookForType");// 找回方式：phone:手机找回;email:邮箱找回
		String token = StringTools.stringToTrim(getString("token"));
		String loginName = StringTools.stringToTrim(getString("loginName"));
		String lgnewPwd1 = StringTools.stringToTrim(getString("lgnewPwd1"));
		String lgnewPwd2 = StringTools.stringToTrim(getString("lgnewPwd2"));

		// 1.验证token
		UserInfo userInfo = null;
		if ("phone".equals(lookForType)) {// 手机找回密码方式
			// 判断传递参数是否被改动（loginName,token）
			userInfo = validatePassParam(loginName, token);
		} else if ("email".equals(lookForType)) {// 邮件找回密码方式
			Map<String, Object> msgMap = mailBiz.validateToken(token);
			Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
			if (!msgMap.isEmpty()) {
				pushData(msgMap);
				return BaseConsts.ACTION_ERROR_ADD;
			}

			userInfo = super.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
		} else {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}

		// 2.验证页面参数
		Map<String, String> msgMap = validateEditPwd(userInfo, lgnewPwd1, lgnewPwd2);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 3.更新商户密码
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(userInfo.getLoginName());
		userManagementFacade.changeOperatorLoginPwd(userInfo.getLoginName(), userOperator.getLoginPwd(), DigestUtils.sha1Hex(lgnewPwd1), 1);
		if ("email".equals(lookForType)) {
			mailBiz.updateEmailVerifyStatus(token);
		}
		return "editLoginPwd";
	}

	/**
	 * 登录密码：人工服务
	 * 
	 * @return
	 */
	public String humanServicesUI() {
		return "humanServicesUI";
	}

	// ///////////////////////////验证///////////////////////////////
	public Map<String, String> validateLoginPwdCheckLoginName(HttpServletRequest req, String loginName, String randomCode) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(loginName)) {
			msgMap.put(errorMsg, "请输入正确的用户名");
			return msgMap;
		}
		UserInfo userInfo = super.getUserInfoByLoginName(loginName);
		if (ValidateUtils.isEmpty(userInfo)) {
			msgMap.put(errorMsg, "该用户不存在");
			return msgMap;
		}
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
		if (ValidateUtils.isEmpty(userOperator)) {
			msgMap.put(errorMsg, "该用户不存在");
			return msgMap;
		}
		if (userOperator.getType().intValue() != UserOperatorTypeEnum.ADMIN.getValue()) {
			msgMap.put(errorMsg, "操作员不提供找回密码功能，请联系管理员重置密码");
			return msgMap;
		}
		if (userOperator.getStatus().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
			msgMap.put(errorMsg, "该用戶为不可用状态");
			return msgMap;
		}
		if (ValidateUtil.isValidateCode(req, randomCode)) {
			msgMap.put(errorMsg, "请输入正确的验证码");
			return msgMap;
		}
		msgMap.put("userNo", userInfo.getUserNo());
		return msgMap;
	}

	public Map<String, String> validateLoginPwdSendPhoneCodeCheck(String loginName, String bindingPhone, String phoneCode, UserInfo userInfo) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(bindingPhone)) {
			msgMap.put(errorType, "请输入正确的手机号码");
			return msgMap;
		}
		// 验证手机号是否为绑定手机
		if (!bindingPhone.equals(userInfo.getBindMobileNo())) {
			msgMap.put(errorType, "请输入正确的手机号码");
			return msgMap;
		}
		// 手机动态码验证
		if (!ValidateUtil.isValidatePhoneCode(loginName, phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorType, "请输入正确的验证码");
		}
		return msgMap;
	}

	public Map<String, String> validateEditPwd(UserInfo userInfo, String newPwd1, String newPwd2) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		
		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorType, pwdMsg);
			return msgMap;
		}
		if (!newPwd1.equals(newPwd2)) {
			msgMap.put(errorType, "两次输入密码不一致");
			return msgMap;
		}
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(userInfo.getLoginName());
		if (userOperator.getLoginPwd().equals(DigestUtils.sha1Hex(newPwd1))) {
			msgMap.put(errorType, "新密码不可与原密码一样");
			return msgMap;
		}
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(userInfo.getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userTradePwd.getTradePwd())) {
			msgMap.put(errorType, "登录密码不可与支付密码一致");
		}
		return msgMap;
	}

	/**
	 * 验证整个流程的两个传递参数的正确性（token:sha1Hex(loginName + id),loginName）
	 * 
	 * @param loginName
	 * @param token
	 * @return
	 */
	public UserInfo validatePassParam(String loginName, String token) {
		if (ValidateUtils.isEmpty(loginName)) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		UserInfo userInfo = super.getUserInfoByLoginName(loginName);
		if (ValidateUtils.isEmpty(userInfo)) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		if (ValidateUtils.isEmpty(token) || !token.equals(DigestUtils.sha1Hex(loginName + userInfo.getUserNo()))) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		return userInfo;
	}

	/**
	 * 判断帐户安全（是否判断手机或邮箱）
	 * 
	 * @param type
	 * @return
	 */
	private Map<String, String> validateAccountSecurity(UserInfo userInfo, String type) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		//AccountSecurity accountSecurity = accountQueryFacade.getAccountSecurityByAccountNo(userInfo.getAccountNo());
		if ("phone".equals(type)) {
			if (userInfo.getIsMobileAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				msgMap.put(errorMsg, "未绑定手机");
			}
		} else if ("email".equals(type)) {
			if (userInfo.getIsEmailAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				msgMap.put(errorMsg, "未绑定邮箱");
			}
		}
		return msgMap;
	}
}
package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;
import wusc.edu.pay.web.portal.biz.MailBiz;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * 找回支付密码
 * 
 * @author liliqiong
 * @date 2013-11-6
 * @version 1.0
 */
public class LookForTradePwdAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;
	
	private static final Log LOG = LogFactory.getLog(LookForTradePwdAction.class);

	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 选择找回支付密码方式
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdListWay() {
		int merchantType = 0;
		if (getCurrentUserInfo().getUserType().intValue() == UserTypeEnum.MERCHANT.getValue()) {
			merchantType = getMerchantOnline().getMerchantType();
		} 
		putData("merchantType", merchantType);
		return "tradePwdListWay";
	}

	/**
	 * 支付密码：去验证手机页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendPhoneCodeCheckUI() {
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		putData("currentUserInfo", userInfo);
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		if (userInfo.getIsMobileAuth().intValue() == PublicStatusEnum.ACTIVE.getValue()) {
			String bindMobileNo = getCurrentUserInfo().getBindMobileNo();
			putData("showBindMobileNo", StringTools.phoneChange(ValidateUtils.isEmpty(bindMobileNo) ? "" : bindMobileNo));
		}
		return "tradePwdSendPhoneCodeCheckUI";
	}

	/**
	 * 支付密码：验证手机,修改支付密码
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendPhoneCodeCheck() {
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		String cardNo = StringTools.stringToTrim(getString("cardNo"));
		String trnewPwd1 = StringTools.stringToTrim(getString("trnewPwd1"));
		String trnewPwd2 = StringTools.stringToTrim(getString("trnewPwd2"));

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		String loginName = userInfo.getLoginName();
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(loginName);
		Map<String, String> msgMap = validateTradePwdSendPhoneCodeCheck(phoneCode, cardNo, trnewPwd1, trnewPwd2, userTradePwd.getTradePwd());
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "（通过手机验证）找回支付密码");
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		userTradePwdFacade.changePwd(loginName, userTradePwd.getTradePwd(), DigestUtils.sha1Hex(trnewPwd1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "（通过手机验证）找回支付密码");
		return "tradePwdSendPhoneCodeCheck";
	}

	/**
	 * 支付密码：去验证邮箱页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendMailCheckUI() {
		UserInfo userInfo = getCurrentUserInfo();
		putData("currentUserInfo", userInfo);
		if (userInfo.getIsEmailAuth().intValue() == PublicStatusEnum.ACTIVE.getValue()) {
			String bindEmail = getCurrentUserInfo().getBindEmail();
			putData("bindEmail", StringTools.phoneChange(ValidateUtils.isEmpty(bindEmail) ? "" : bindEmail));
		}

		return "tradePwdSendMailCheckUI";
	}

	/**
	 * 支付密码：邮件+安全问题
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendMailCheckUI1() {

		UserInfo userInfo = getCurrentUserInfo();
		putData("currentUserInfo", userInfo);

		if (userInfo.getIsEmailAuth().intValue() == PublicStatusEnum.ACTIVE.getValue()) {
			String bindEmail = getCurrentUserInfo().getBindEmail();
			putData("bindEmail", StringTools.phoneChange(ValidateUtils.isEmpty(bindEmail) ? "" : bindEmail));
			putData("securityQuestionList", SecurityQuestionEnum.values());
		}
		return "tradePwdSendMailCheckUI1";
	}

	/**
	 * 支付密码：验证邮箱，发送邮件
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendMailCheck() {
		String cardNo = StringTools.stringToTrim(getString("cardNo"));
		UserInfo userInfo = getCurrentUserInfo();
		if (userInfo.getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未实名认证");
		} else if (userInfo.getIsEmailAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未绑定邮箱");
		}

		Map<String, String> msgMap = validateTradePwdSendMailCheck(cardNo);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "（通过邮箱验证）找回支付密码");
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		String userNo = userInfo.getUserNo();
		String toMail = userInfo.getBindEmail();
		String loginName = userInfo.getLoginName();
		Integer type = PublicTemplateEnum.MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA.getValue();
		String url = mailBiz.sendEmail(userNo, userInfo.getUserType(), loginName, toMail, type);
		putData("url", url);
		putData("userNo", userNo);
		putData("userType", userInfo.getUserType());
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", type);
		putData("merchantBusType", userInfo.getUserType());
		this.addUserLog(OpeStatusEnum.SUCCESS, "（通过邮箱验证）找回支付密码");
		return "tradePwdSendMailCheck";
	}

	/**
	 * 支付密码：验证邮箱，发送邮件(邮箱+安全问题)
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String tradePwdSendMailCheck1() {
		String answer = StringTools.stringToTrim(getString("answer"));
		UserInfo userInfo = getCurrentUserInfo();
		if (userInfo.getIsEmailAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未绑定邮箱");
		}

		Map<String, String> msgMap = validateTradePwdSendMailCheck1(answer);
		if (!msgMap.isEmpty()) {
			putData("currentUserInfo", userInfo);
			putData("answer", answer);
			String bindEmail = userInfo.getBindEmail();
			putData("bindEmail", StringTools.phoneChange(ValidateUtils.isEmpty(bindEmail) ? "" : bindEmail));
			pushData(msgMap);
			return "tradePwdSendMailCheckUI1";
		}

		String userNo = userInfo.getUserNo();
		String toMail = userInfo.getBindEmail();
		String loginName = userInfo.getLoginName();
		Integer type = PublicTemplateEnum.MAIL_MERCHANT_LOOKFOR_TRADEPWD_MA.getValue();

		String url = mailBiz.sendEmail(userNo, userInfo.getUserType(), loginName, toMail, type);
		putData("url", url);
		putData("userNo", userNo);
		putData("userType", userInfo.getUserType());
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", type);

		return "tradePwdSendMailCheck";
	}

	/**
	 * 支付密码：去修改支付密码页面
	 * 
	 * @return
	 */
	public String editTradePwdUI() {
		String token = StringTools.stringToTrim(getString("token"));
		LOG.info("===> email token:" + token);
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		UserInfo userInfo = super.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
		putData("loginName", userInfo.getLoginName());
		putData("token", token);
		return "editTradePwdUI";
	}

	/**
	 * 支付密码：修改支付密码页面
	 * 
	 * @return
	 */
	public String editTradePwd() {
		String token = StringTools.stringToTrim(getString("token"));
		String trnewPwd1 = StringTools.stringToTrim(getString("trnewPwd1"));
		String trnewPwd2 = StringTools.stringToTrim(getString("trnewPwd2"));
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		UserInfo userInfo = super.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
		String loginName = userInfo.getLoginName();
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(loginName);
		Map<String, String> msgMap2 = validateEditPwd(userOperator.getLoginPwd(), userTradePwd.getTradePwd(), trnewPwd1, trnewPwd2);
		if (!msgMap2.isEmpty()) {
			putData("accountNo", userInfo.getAccountNo());
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			putData("token", token);
			pushData(msgMap2);
			return "editTradePwdUI";
		}
		// 修改交易密码
		userTradePwdFacade.changePwd(loginName, userTradePwd.getTradePwd(), DigestUtils.sha1Hex(trnewPwd1));
		// 更新token状态
		mailBiz.updateEmailVerifyStatus(token);
		return "editTradePwd";
	}

	// //////////////////////////验证//////////////////////////
	public Map<String, String> validateTradePwdSendPhoneCodeCheck(String phoneCode, String cardNo, String newPwd1, String newPwd2, String oldTradePwd) {
		// AccountSecurity accountSecurity =
		// accountQueryFacade.getAccountSecurityByAccountNo(getCurrentUser().getAccountNo());
		UserInfo userInfo = getCurrentUserInfo();
		if (userInfo.getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未实名认证");
		} else if (userInfo.getIsMobileAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未绑定手机");
		}
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorMsg, "请输入正确的验证码");
			return msgMap;
		}
		// 身份证号验证
		if (!ValidateUtils.isIdCard(cardNo) || !cardNo.toUpperCase().equals(getMerchantOnline().getCardNo().toUpperCase())) {
			msgMap.put(errorMsg, "请输入正确的身份证号");
			return msgMap;
		}
		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorMsg, pwdMsg);
			return msgMap;
		}
		if (!newPwd1.equals(newPwd2)) {
			msgMap.put(errorMsg, "两次输入密码不一致");
			return msgMap;
		}
		if (DigestUtils.sha1Hex(newPwd1).equals(oldTradePwd)) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		if (DigestUtils.sha1Hex(newPwd1).equals(getCurrentUserOperator().getLoginPwd())) {
			msgMap.put(errorMsg, "新密码不能与登录密码一致");
		}
		return msgMap;
	}

	public Map<String, String> validateTradePwdSendMailCheck(String cardNo) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(cardNo) || !ValidateUtils.isIdCard(cardNo) || !cardNo.toUpperCase().equals(getCurrentUserInfo().getCardNo().toUpperCase())) {
			msgMap.put(errorMsg, "请输入正确的身份证号");
			return msgMap;
		}
		return msgMap;
	}

	public Map<String, String> validateTradePwdSendMailCheck1(String answer) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(answer) || !answer.equals(getCurrentUserInfo().getAnswer())) {
			msgMap.put(errorMsg, "请输入正确的安全问题答案");
		}
		return msgMap;
	}

	/**
	 * 
	 * @param loginPwd
	 *            登录密码串.
	 * @param oldTradePwd
	 *            旧交易密码串.
	 * @param newPwd1
	 *            新交易密码.
	 * @param newPwd2
	 *            确认新密码.
	 * @return
	 */
	public Map<String, String> validateEditPwd(String loginPwd, String oldTradePwd, String newPwd1, String newPwd2) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(newPwd1) || newPwd1.length() < 6 || newPwd1.length() > 20) {
			msgMap.put(errorMsg, "请输入由6~20位不连续的数字、字母组合的新密码");
			return msgMap;
		}
		if (!newPwd1.equals(newPwd2)) {
			msgMap.put(errorMsg, "两次输入密码不一致");
			return msgMap;
		}
		if (DigestUtils.sha1Hex(newPwd1).equals(oldTradePwd)) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		if (DigestUtils.sha1Hex(newPwd1).equals(loginPwd)) {
			msgMap.put(errorMsg, "新密码不能与登录密码一致");
		}
		return msgMap;
	}
}

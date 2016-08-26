package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
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
public class LookForTradePwdAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;
	/**
	 * 选择找回支付密码方式
	 * 
	 * @return
	 */
	public String tradePwdListWay() {
		UserInfo userInfo= getCurrentUserInfo();
		putData("userInfo", userInfo);
		return "tradePwdListWay";
	}

	/**
	 * 支付密码：去验证手机页面
	 * 
	 * @return
	 */
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
	public String tradePwdSendPhoneCodeCheck() {
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		String cardNo = StringTools.stringToTrim(getString("cardNo"));
		String trnewPwd1 = StringTools.stringToTrim(getString("trnewPwd1"));
		String trnewPwd2 = StringTools.stringToTrim(getString("trnewPwd2"));
		// 1.验证页面参数
		Map<String, String> msgMap = validateTradePwdSendPhoneCodeCheck( phoneCode, cardNo, trnewPwd1, trnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "（通过手机验证）找回支付密码");
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		// 2.修改支付密码
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
		userTradePwdFacade.changePwd(getCurrentUserInfo().getLoginName(), userTradePwd.getTradePwd(), DigestUtils.sha1Hex(trnewPwd1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "（通过手机验证）找回支付密码");
		return "tradePwdSendPhoneCodeCheck";
	}

	/**
	 * 支付密码：去验证邮箱页面
	 * 
	 * @return
	 */
	public String tradePwdSendMailCheckUI() {
		UserInfo userInfo = getCurrentUserInfo();
		String bindEmail = userInfo.getBindEmail();
		putData("bindEmail", StringTools.phoneChange(ValidateUtils.isEmpty(bindEmail) ? "" : bindEmail));
		return "tradePwdSendMailCheckUI";
	}

	/**
	 * 支付密码：验证邮箱，发送邮件
	 * 
	 * @return
	 */
	public String tradePwdSendMailCheck() {
		String cardNo = StringTools.stringToTrim(getString("cardNo"));
		UserInfo userInfo = getCurrentUserInfo();
		// 1.验证页面参数
		Map<String, String> msgMap = validateTradePwdSendMailCheck(cardNo);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "（通过邮箱验证）找回支付密码");
			putData("cardNo", cardNo);
			String bindEmail = userInfo.getBindEmail();
			putData("bindEmail", StringTools.phoneChange(ValidateUtils.isEmpty(bindEmail) ? "" : bindEmail));
			pushData(msgMap);
			return "tradePwdSendMailCheckUI";
		}
		String userNo = userInfo.getUserNo();
		String toMail = userInfo.getBindEmail();
		Integer userType = UserTypeEnum.CUSTOMER.getValue();
		String loginName = userInfo.getLoginName();
		Integer type = PublicTemplateEnum.MAIL_MEMBER_LOOKFOR_TRADEPWD_MA.getValue();

		// 2.发送邮件
		String url = mailBiz.sendEmail(userNo, userType, loginName, toMail, type);
		putData("userNo", userNo);
		putData("userType", userType);
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", type);
		putData("url", url);
		this.addUserLog(OpeStatusEnum.SUCCESS, "（通过邮箱验证）找回支付密码");
		return "tradePwdSendMailCheck";
	}

	/**
	 * 支付密码：去修改支付密码页面
	 * 
	 * @return
	 */
	public String editTradePwdUI() {
		String token = StringTools.stringToTrim(getString("token"));
		// 1.验证页面参数
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return "editTradePwdError";
		}

		// 2.设置相关信息到页面
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
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
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return "editTradePwdError";
		}
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(((EmailVerify) obj).getUserNo());
		String trnewPwd1 = StringTools.stringToTrim(getString("trnewPwd1"));
		String trnewPwd2 = StringTools.stringToTrim(getString("trnewPwd2"));

		Map<String, String> msgMap2 = validateEditPwd(userInfo, trnewPwd1, trnewPwd2);
		if (!msgMap2.isEmpty()) {
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			pushData(msgMap2);
			putData("token", token);
			return "editTradePwdUI";
		}
		// 修改交易密码
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(userInfo.getLoginName());
		userTradePwdFacade.changePwd(userInfo.getLoginName(), userTradePwd.getTradePwd(), DigestUtils.sha1Hex(trnewPwd1));
		mailBiz.updateEmailVerifyStatus(token);
		return "editTradePwd";
	}

	// ********************************验证******************************//
	public Map<String, String> validateTradePwdSendPhoneCodeCheck( String phoneCode, String cardNo, String newPwd1, String trnewPwd2) {
		if (getCurrentUserInfo().getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未实名认证");
		} else if (getCurrentUserInfo().getIsMobileAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未绑定手机");
		}
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		UserInfo userInfo = getCurrentUserInfo();
		if (!ValidateUtil.isValidatePhoneCode(userInfo.getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorMsg, "请输入正确的验证码");
			return msgMap;
		}
		if (!ValidateUtils.isIdCard(cardNo) || !cardNo.toUpperCase().equals(getCurrentUserInfo().getCardNo().toUpperCase())) {
			msgMap.put(errorMsg, "请输入正确的身份证号");
			return msgMap;
		}
		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorMsg, pwdMsg);
			return msgMap;
		}
		if (!newPwd1.equals(trnewPwd2)) {
			msgMap.put(errorMsg, "两次输入密码不一致");
			return msgMap;
		}
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(userInfo.getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userTradePwd.getTradePwd())) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(userInfo.getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userOperator.getLoginPwd())) {
			msgMap.put(errorMsg, "新密码不能与登录密码一致");
		}
		return msgMap;
	}

	public Map<String, String> validateTradePwdSendMailCheck(String cardNo) {
		if (getCurrentUserInfo().getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未实名认证");
		} else if (getCurrentUserInfo().getIsEmailAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：未绑定邮箱");
		}
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (!ValidateUtils.isIdCard(cardNo) || !cardNo.toUpperCase().equals(getCurrentUserInfo().getCardNo().toUpperCase())) {
			msgMap.put(errorMsg, "请输入正确的身份证号");
		}
		return msgMap;
	}

	public Map<String, String> validateEditPwd(UserInfo userInfo, String newPwd1, String trnewPwd2) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		// 密码验证
		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorMsg, pwdMsg);
			return msgMap;
		}
		if (!newPwd1.equals(trnewPwd2)) {
			msgMap.put(errorMsg, "两次输入密码不一致");
			return msgMap;
		}
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(userInfo.getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userTradePwd.getTradePwd())) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(userInfo.getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userOperator.getLoginPwd())) {
			msgMap.put(errorMsg, "新密码不能与登录密码一致");
		}
		return msgMap;
	}
}

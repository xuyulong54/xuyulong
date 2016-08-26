package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title: 会员： 手机绑定,解绑</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-5
 */
public class MobileBindAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserManagementFacade userManagementFacade;
	/**
	 * 手机:去绑定邮箱页面
	 * 
	 * @return
	 */
	public String bindingMobileUI() {
		return "bindingMobileUI";

	}

	/**
	 * 绑定手机，并激活
	 * 
	 * @return
	 */
	public String bindingMobile() {
		String bindingPhone = StringTools.stringToTrim(getString("bindingPhone"));
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		String tradePwd = super.getPwd("tradePwd");
		String messageMobile = StringTools.stringToTrim(getString("messageMobile"));

		// 1.参数验证
		Map<String, String> msgMap = validateBindingPhone(bindingPhone, phoneCode, tradePwd, messageMobile);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "绑定手机.");
			putData("bindingPhone", bindingPhone);
			putData("phoneCode", phoneCode);
			putData("tradePwd", tradePwd);
			putData("messageMobile", messageMobile);
			pushData(msgMap);
			return "bindingMobileUI";
		}
		
		// 2.绑定手机号
		userManagementFacade.bindMobileNo(getCurrentUserInfo().getUserNo(), bindingPhone);

		// 3.更新SESSION中的当前用户
		UserInfo userInfo = this.getCurrentUserInfo();
		userInfo.setIsMobileAuth(PublicStatusEnum.ACTIVE.getValue());
		userInfo.setBindMobileNo(bindingPhone);
		setCurrentUserInfo(userInfo);
		this.addUserLog(OpeStatusEnum.SUCCESS, "绑定手机.");
		return "bindingMobile";
	}

	/**
	 * 去解绑手机页面
	 * 
	 * @return
	 */
	public String unbundlingMobileUI() {
		UserInfo userInfo = getCurrentUserInfo();
		putData("showBindingPhone", StringTools.phoneChange(userInfo.getBindMobileNo()));
		putData("bindingPhone", userInfo.getBindMobileNo());
		putData("loginName", userInfo.getLoginName());
		return "unbundlingMobileUI";
	}

	/**
	 * 解绑手机
	 * 
	 * @return
	 */
	public String unbundlingMobile() {
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		String tradePwd = super.getPwd("tradePwd");

		// 1.验证页面参数
		Map<String, String> msgMap = validateUnbundlingPhone(tradePwd, phoneCode);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "解绑手机.");
			UserInfo userInfo = getCurrentUserInfo();
			putData("phoneCode", phoneCode);
			putData("showBindingPhone", StringTools.phoneChange(userInfo.getBindMobileNo()));
			putData("bindingPhone", userInfo.getBindMobileNo());
			putData("loginName", userInfo.getLoginName());
			pushData(msgMap);
			return "unbundlingMobileUI";
		}
		
		// 2.解绑手机号
		userManagementFacade.unBindMobileNo(getCurrentUserInfo().getUserNo());

		// 3.更新SESSION中的当前用户
		UserInfo userInfo = getCurrentUserInfo();
		userInfo.setBindMobileNo(null);
		userInfo.setIsMobileAuth(PublicStatusEnum.INACTIVE.getValue());
		setCurrentUserInfo(userInfo);
		this.addUserLog(OpeStatusEnum.SUCCESS, "解绑手机.");
		return "unbundlingMobile";
	}

	// ////////////////////验证////////////////////////
	public Map<String, String> validateBindingPhone(String bindingPhone, String phoneCode, String tradePwd, String messageMobile) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		// 绑定手机的合法性
		if (ValidateUtils.isEmpty(bindingPhone) || !ValidateUtils.isMobile(bindingPhone)) {
			msgMap.put(errorType, "请输入正确的手机号码");
			return msgMap;
		}
		if (bindingPhone.equals(getCurrentUserInfo().getBindMobileNo())) {
			msgMap.put(errorType, "用绑定手机不可与原手机号一致");
			return msgMap;
		}
		if (!bindingPhone.equals(messageMobile)) {
			msgMap.put(errorType, "验证码无效，请输入正确的验证码");
			return msgMap;
		}
		// 手机动态码验证
		if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorType, "验证码无效，请输入正确的验证码");
			return msgMap;
		}
		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtils.isEmpty(tradePwdMsg)) {
			msgMap.put(errorType, tradePwdMsg);
		}
		return msgMap;
	}

	private Map<String, String> validateUnbundlingPhone(String tradePwd, String phoneCode) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorType, "验证码无效，请输入正确的验证码");
			return msgMap;
		}
		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtils.isEmpty(tradePwdMsg)) {
			msgMap.put(errorType, tradePwdMsg);
		}
		return msgMap;
	}

}

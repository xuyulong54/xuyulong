package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.biz.MailBiz;


/**
 * 邮箱绑定，解绑
 * 
 * @author liliqiong
 * @date 2013-11-5
 * @version 1.0
 */
public class EmailBindAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 绑定：去 验证邮箱页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Binding")
	public String bindingEmailUI() {
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		// 需查询出最新的数据，如果取session中的数据会出现当解绑邮箱后，再绑定原先绑定的邮箱，页面提示“不能与原绑定邮箱一致”的问题
		putData("bindEmail", userInfo.getBindEmail());
		return "bindingEmailUI";
	}

	/**
	 * 
	 * 绑定：验证邮箱
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Binding")
	public String bindingEmail() {

		String bindingEmail = StringTools.stringToTrim(getString("bindingEmail"));
		String tradePwd = super.getPwd("tradePwd");

		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		Map<String, String> msgMap = validateBindingEmail(bindingEmail, tradePwd, userInfo);
		if (!msgMap.isEmpty()) {
		this.addUserLog(OpeStatusEnum.FAIL, "修改绑定邮箱.");
			// 校验不通过，回显提示信息和表单数据
			putData("bindingEmail", bindingEmail);
			putData("tradePwd", tradePwd);
			pushData(msgMap);
			return "bindingEmailUI";
		}
		String userNo = userInfo.getUserNo();
		String loginName = userInfo.getLoginName();
		Integer mailType = PublicTemplateEnum.MAIL_MERCHANT_BANG_MA.getValue();
		// 发送邮件
		String returnUrl = mailBiz.sendEmail(userNo, userInfo.getUserType(), loginName, bindingEmail, mailType);
		putData("userNo", userNo);
		putData("userType", userInfo.getUserType());
		putData("loginName", loginName);
		putData("toMail", bindingEmail);
		putData("mailType", mailType);
		putData("returnUrl", returnUrl);

		this.addUserLog(OpeStatusEnum.SUCCESS, "修改绑定邮箱.");

		return "bindingEmail";
	}

	/**
	 * 邮箱：激活
	 * 
	 * @return
	 */
	public String bindingActivationEmail() {
		String token = StringTools.stringToTrim(getString("token"));
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2.更新用户表绑定邮箱，绑定邮箱状态字段
		userManagementFacade.bindEmail(((EmailVerify) obj).getUserNo(), ((EmailVerify) obj).getToEmail());

		// 3.更新TOKEN状态
		mailBiz.updateEmailVerifyStatus(token);
		return "bindingActivationEmail";
	}

	/**
	 * 解绑：去解绑邮箱页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Binding")
	public String unbundlingEmailUI() {
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		putData("bindingEmail", StringTools.phoneChange(userInfo.getBindEmail()));
		return "unbundlingEmailUI";
	}

	/**
	 * 解绑：解绑邮箱
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Binding")
	public String unbundlingEmail() {
		String tradePwd = super.getPwd("tradePwd");
		// 发送邮箱
		Map<String, String> msgMap = validateUnbundlingEmail(tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "解绑邮箱");
			putData("bindingEmail", StringTools.phoneChange(getCurrentUserInfo().getBindEmail()));
			pushData(msgMap);
			return "unbundlingEmailUI";
		}
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		String loginName = userInfo.getLoginName();
		String toMail = userInfo.getBindEmail();
		Integer type = PublicTemplateEnum.MAIL_MERCHANT_UNBANG_MA.getValue();
		// 发送邮件
		String returnUrl = mailBiz.sendEmail(userInfo.getUserNo(), userInfo.getUserType(), loginName, toMail, type);
		putData("userNo", userInfo.getUserNo());
		putData("userType", userInfo.getUserType());
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", type);
		putData("returnUrl", returnUrl);
		this.addUserLog(OpeStatusEnum.SUCCESS, "解绑邮箱");
		return "unbundlingEmail";
	}

	/**
	 * 解绑：更新状态
	 * 
	 * @return
	 */
	public String unbundlingActivationEmail() {
		String token = StringTools.stringToTrim(getString("token"));
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}

		// 2.解除邮箱绑定
		userManagementFacade.unBindEmail(((EmailVerify) obj).getUserNo());

		// 3.更新TOKEN状态
		mailBiz.updateEmailVerifyStatus(token);
		return "unbundlingActivationEmail";
	}

	// ///////////////////验证///////////////////////////////////////

	public Map<String, String> validateBindingEmail(String bindingEmail, String tradePwd, UserInfo userInfo) {
		Map<String, String> msgMap = new HashMap<String, String>();
		// 邮箱合法性验证
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(bindingEmail) || !ValidateUtils.isEmail(bindingEmail) || bindingEmail.length() < 8 || bindingEmail.length() > 30) {
			msgMap.put(errorType, "请输入正确的邮箱");
			return msgMap;
		}
		if (bindingEmail.equals(userInfo.getBindEmail())) {
			msgMap.put(errorType, "不能与原绑定邮箱一致");
			return msgMap;
		}
		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtil.isEmpty(tradePwdMsg)) {
			msgMap.put(errorType, tradePwdMsg);
		}

		return msgMap;
	}

	private Map<String, String> validateUnbundlingEmail(String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtil.isEmpty(tradePwdMsg)) {
			msgMap.put(errorType, tradePwdMsg);
		}
		return msgMap;
	}
}

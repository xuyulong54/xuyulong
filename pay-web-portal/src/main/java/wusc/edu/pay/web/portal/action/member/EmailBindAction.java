package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicTemplateEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.boss.entity.EmailVerify;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.biz.MailBiz;


/**
 * <ul>
 * <li>Title:邮箱绑定，解绑</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-5
 */
public class EmailBindAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private MailBiz mailBiz;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 绑定：去 验证邮箱页面
	 * 
	 * @return
	 */
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
	public String bindingEmail() {
		String bindingEmail = StringTools.stringToTrim(getString("bindingEmail"));
		String tradePwd = super.getPwd("tradePwd");
		
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		// 1.验证页面参数
		Map<String, String> msgMap = validateBindingEmail(bindingEmail, tradePwd,userInfo);
		if (!msgMap.isEmpty()) {
			// 校验不通过，回显提示信息和表单数据
			this.addUserLog(OpeStatusEnum.FAIL, "绑定邮箱");
			putData("bindingEmail", bindingEmail);
			putData("tradePwd", tradePwd);
			pushData(msgMap);
			return "bindingEmailUI";
		}
		
		String userNo = userInfo.getUserNo();
		Integer userType = UserTypeEnum.CUSTOMER.getValue();
		String loginName = userInfo.getLoginName();
		Integer mailType = PublicTemplateEnum.MAIL_MEMBER_BANG_MA.getValue();
		// 2. 发送邮件
		String url = mailBiz.sendEmail(userNo, userType, loginName, bindingEmail, mailType);
		putData("userNo", userNo);
		putData("userType", userType);
		putData("loginName", loginName);
		putData("toMail", bindingEmail);
		putData("mailType", mailType);
		putData("url", url);
		this.addUserLog(OpeStatusEnum.SUCCESS, "绑定邮箱");
		return "bindingEmail";
	}

	/**
	 * 邮箱：激活
	 * 
	 * @return
	 */
	public String bindingActivationEmail() {
		String token = StringTools.stringToTrim(getString("token"));

		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		// 2.绑定邮箱
		userManagementFacade.bindEmail(((EmailVerify) obj).getUserNo(), ((EmailVerify) obj).getToEmail());

		// 3.更新TOKEN状态
		mailBiz.updateEmailVerifyStatus(token);

		return "bindingActivationEmail";
	}

	/**
	 * 去解绑邮箱页面
	 * 
	 * @return
	 */
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
	public String unbundlingEmail() {
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		String tradePwd = super.getPwd("tradePwd");
		putData("bindingEmail", StringTools.phoneChange(userInfo.getBindEmail()));

		// 1. 验证页面参数
		Map<String, String> msgMap = validateUnbundlingEmail(tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "解绑邮箱");
			pushData(msgMap);
			return "unbundlingEmailUI";
		}
		String userNo = userInfo.getUserNo();
		String loginName = userInfo.getBindEmail();
		Integer userType = UserTypeEnum.CUSTOMER.getValue();
		String toMail = userInfo.getBindEmail();
		Integer type = PublicTemplateEnum.MAIL_MEMBER_UNBANG_MA.getValue();

		// 2. 发送验证邮件
		String url = mailBiz.sendEmail(userNo, userType, loginName, toMail, type);
		putData("userNo", userNo);
		putData("userType", userType);
		putData("loginName", loginName);
		putData("toMail", toMail);
		putData("mailType", type);
		putData("url", url);
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
		// 1.验证TOKEN
		Map<String, Object> msgMap = mailBiz.validateToken(token);
		Object obj = msgMap.remove(BaseConsts.EMAILVERIFY_MAP_KEY);
		if (!msgMap.isEmpty()) {
			pushData(msgMap);
			return "unbundlingEmailError";
		}

		// 2.解除邮箱绑定
		userManagementFacade.unBindEmail(((EmailVerify) obj).getUserNo());

		// 3.更新TOKEN状态
		mailBiz.updateEmailVerifyStatus(token);

		return "unbundlingActivationEmail";
	}

	// ///////////////////验证///////////////////////////////////////

	public Map<String, String> validateBindingEmail(String bindingEmail, String tradePwd,UserInfo userInfo ) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		// 邮箱合法性验证
		if (ValidateUtils.isEmpty(bindingEmail) || !ValidateUtils.isEmail(bindingEmail)) {
			msgMap.put(errorType, "请输入正确的邮箱");
			return msgMap;
		}
		if (bindingEmail.equals(userInfo.getBindEmail())) {
			msgMap.put(errorType, "新绑定邮箱不可与原绑定邮箱一致");
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

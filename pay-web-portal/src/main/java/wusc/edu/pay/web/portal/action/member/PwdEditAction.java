package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * <ul>
 * <li>Title: 密码修改(登录密码、支付密码)</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-5
 */
public class PwdEditAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;

	/**
	 * 去修改登录页面
	 * 
	 * @return
	 */
	public String editLoginPwdUI() {
		return "editLoginPwdUI";
	}

	/**
	 * 修改登录密码
	 * 
	 * @return
	 */
	public String editLoginPwd() {
		String troldPwd = getString("lgoldPwd");
		String lgnewPwd1 = getString("lgnewPwd1");
		String lgnewPwd2 = getString("lgnewPwd2");

		// 1.验证参数
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(getCurrentUserInfo().getLoginName());
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
		Map<String, String> msgMap = validateEditLoginPwd(troldPwd, lgnewPwd1, lgnewPwd2, userOperator.getLoginPwd(), userTradePwd.getTradePwd());
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改登陆密码");
			putData("troldPwd", troldPwd);
			putData("lgnewPwd1", lgnewPwd1);
			putData("lgnewPwd2", lgnewPwd2);
			pushData(msgMap);
			return "editLoginPwdUI";
		}

		// 2.修改登录密码
		userManagementFacade.changeOperatorLoginPwd(userOperator.getLoginName(), DigestUtils.sha1Hex(troldPwd), DigestUtils.sha1Hex(lgnewPwd1), 1);
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改登陆密码");
		// 3.清除Session，以强制重新登录
		getSessionMap().clear();
		return "editLoginPwd";
	}

	/**
	 * 去修改交易密码页面
	 * 
	 * @return
	 */
	public String editTradePwdUI() {
		return "editTradePwdUI";
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 */
	public String editTradePwd() {
		String troldPwd = getString("troldPwd");
		String trnewPwd1 = getString("trnewPwd1");
		String trnewPwd2 = getString("trnewPwd2");
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(getCurrentUserInfo().getLoginName());
		// 1.验证参数
		Map<String, String> msgMap = validateEditTradePwd(troldPwd, trnewPwd1, trnewPwd2, userOperator.getLoginPwd());
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改支付密码");
			putData("troldPwd", troldPwd);
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			pushData(msgMap);
			return "editTradePwdUI";
		}
		// 2.修改密码
		userTradePwdFacade.changePwd(getCurrentUserInfo().getLoginName(), DigestUtils.sha1Hex(troldPwd), DigestUtils.sha1Hex(trnewPwd1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改支付密码");
		return "editTradePwd";
	}

	// *****************************验证**********************************//
	/**
	 * 修改登录密码校验.
	 * @param oldPwd
	 * @param newPwd1
	 * @param newPwd2
	 * @param loginPwd 登录密码(加密串)
	 * @param tradePwd 交易密码(加密串)
	 * @return
	 */
	private Map<String, String> validateEditLoginPwd(String oldPwd, String newPwd1, String newPwd2, 
			String loginPwd, String tradePwd) {
		
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(oldPwd) || !(loginPwd).equals(DigestUtils.sha1Hex(oldPwd))) {
			msgMap.put(errorMsg, "原始密码有误");
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
		if (oldPwd.equals(newPwd1)) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		
		if (DigestUtils.sha1Hex(newPwd1).equals(tradePwd)) {
			msgMap.put(errorMsg, "登录密码不能与支付密码一致");
		}
		return msgMap;
	}

	/**
	 * 校验修改支付密码.
	 * @param oldPwd 
	 * @param newPwd1
	 * @param newPwd2
	 * @param loginPwd 登录密码(数据库取出的加密串)
	 * @return
	 */
	private Map<String, String> validateEditTradePwd(String oldPwd, String newPwd1, String newPwd2, String loginPwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		String tradePwdMsg = validateTradePwd(oldPwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put(errorMsg, tradePwdMsg);
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
		if (oldPwd.equals(newPwd1)) {
			msgMap.put(errorMsg, "新密码不能与原密码一致");
			return msgMap;
		}
		
		if (DigestUtils.sha1Hex(newPwd1).equals(loginPwd)) {
			msgMap.put(errorMsg, "支付密码不能与登录密码一致");
		}
		return msgMap;
	}
}

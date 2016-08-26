package wusc.edu.pay.web.portal.action.merchant;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * 商户：密码修改(登录密码、支付密码)
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class PwdEditAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;

	/**
	 * 去修改登录密码页面(首次)
	 * 
	 * @return
	 */
	public String firstEditLoginPwdUI() {
		return "firstEditLoginPwdUI";
	}

	/**
	 * 修改登录密码(首次)
	 * 
	 * @return
	 */
	public String firstEditLoginPwd() {
		String lgoldPwd = getString("lgoldPwd");
		String lgnewPwd1 = getString("lgnewPwd1");
		String lgnewPwd2 = getString("lgnewPwd2");
		// 1.验证参数
		Map<String, String> msgMap = validateEditLoginPwd(lgoldPwd, lgnewPwd1, lgnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改登陆密码.");
			putData("lgoldPwd", lgoldPwd);
			putData("lgnewPwd1", lgnewPwd1);
			putData("lgnewPwd2", lgnewPwd2);
			pushData(msgMap);
			return "editLoginPwdUI";
		}
		UserOperator userOperator = getCurrentUserOperator();
		if (userOperator.getIsChangedPwd() == 1) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		userOperator.setLoginPwd(DigestUtils.sha1Hex(lgnewPwd1));
		userOperator.setIsChangedPwd(1);// 设置为更改过密码
		userOperator.setLastAlertPwdTime(new Date());
		userOperatorFacade.updateUserOperator(userOperator);
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改登陆密码.");
		// 3.清除Session
		getSessionMap().clear();

		return "editLoginPwdFirst";
	}

	/**
	 * 去修改支付，登录密码页面(首次)
	 * 
	 * @return
	 */
	public String firstEditLoginTradePwdUI() {
		return "firstEditLoginTradePwdUI";
	}

	/**
	 * 修改支付，登录密码页面(首次)
	 * 
	 * @return
	 */
	public String firstEditLoginTradePwd() {
		String lgoldPwd = getString("lgoldPwd");
		String lgnewPwd1 = getString("lgnewPwd1");
		String lgnewPwd2 = getString("lgnewPwd2");
		String troldPwd = getString("troldPwd");
		String trnewPwd1 = getString("trnewPwd1");
		String trnewPwd2 = getString("trnewPwd2");
		// 1.验证登录密码参数
		Map<String, String> msgMap = validateEditLoginPwd(lgoldPwd, lgnewPwd1, lgnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改初始登陆密码和支付密码.");
			putData("lgoldPwd", lgoldPwd);
			putData("lgnewPwd1", lgnewPwd1);
			putData("lgnewPwd2", lgnewPwd2);
			putData("troldPwd", troldPwd);
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			pushData(msgMap);
			return "firstEditLoginTradePwdUI";
		}
		// 2.验证支付密码参数
		
		msgMap = validateEditTradePwd(troldPwd, trnewPwd1, trnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改初始登陆密码和支付密码.");
			putData("lgoldPwd", lgoldPwd);
			putData("lgnewPwd1", lgnewPwd1);
			putData("lgnewPwd2", lgnewPwd2);
			putData("troldPwd", troldPwd);
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			pushData(msgMap);
			return "firstEditLoginTradePwdUI";
		}
		// 修改登录密码，及是否更改过密码状态
		UserOperator userOperator = getCurrentUserOperator();
		if (userOperator.getIsChangedPwd() == 1) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改初始登陆密码和支付密码.");
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		// 修改支付密码，及是否更改过密码状态
		String loginName = getCurrentUserInfo().getLoginName();
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(loginName);

		if (!ValidateUtils.isEmpty(userTradePwd.getIsInitialPwd()) && userTradePwd.getIsInitialPwd().intValue() != 1) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改初始登陆密码和支付密码.");
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误");
		}
		
		userOperator.setLoginPwd(DigestUtils.sha1Hex(lgnewPwd1));
		userOperator.setIsChangedPwd(1);// 设置为更改过密码
		userOperator.setLastAlertPwdTime(new Date());
		userOperatorFacade.updateUserOperator(userOperator);
		
		userTradePwdFacade.changePwd(loginName, userTradePwd.getTradePwd(), DigestUtils.sha1Hex(trnewPwd1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改初始登陆密码和支付密码.");
		// 3.清除Session
		getSessionMap().clear();
		return "firstEditLoginTradePwd";
	}

	/**
	 * 去修改密码页面
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
		String lgoldPwd = getString("lgoldPwd");
		String lgnewPwd1 = getString("lgnewPwd1");
		String lgnewPwd2 = getString("lgnewPwd2");
		// 1.验证参数
		Map<String, String> msgMap = validateEditLoginPwd(lgoldPwd, lgnewPwd1, lgnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改登录密码");
			putData("lgoldPwd", lgoldPwd);
			putData("lgnewPwd1", lgnewPwd1);
			putData("lgnewPwd2", lgnewPwd2);
			pushData(msgMap);
			return "editLoginPwdUI";
		}
		UserOperator userOperator = getCurrentUserOperator();
		userOperator.setLoginPwd(DigestUtils.sha1Hex(lgnewPwd1));
		userOperator.setIsChangedPwd(1);// 设置为更改过密码
		userOperator.setLastAlertPwdTime(new Date());
		userOperatorFacade.updateUserOperator(userOperator);
		
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改登录密码");
		// 3.清除Session
		getSessionMap().clear();

		
		return "editLoginPwd";
	}

	/**
	 * 去修改交易密码页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String editTradePwdUI() {
		return "editTradePwdUI";
	}

	/**
	 * 修改交易密码
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:TradePassword")
	public String editTradePwd() {
		String troldPwd = getString("troldPwd");
		String trnewPwd1 = getString("trnewPwd1");
		String trnewPwd2 = getString("trnewPwd2");
		// 1.验证参数
		Map<String, String> msgMap = validateEditTradePwd(troldPwd, trnewPwd1, trnewPwd2);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改支付密码");
			putData("troldPwd", troldPwd);
			putData("trnewPwd1", trnewPwd1);
			putData("trnewPwd2", trnewPwd2);
			pushData(msgMap);
			return "editTradePwdUI";
		}
		// 2.修改密码
		String loginName = getCurrentUserInfo().getLoginName();
		userTradePwdFacade.changePwd(loginName, DigestUtils.sha1Hex(troldPwd), DigestUtils.sha1Hex(trnewPwd1));
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改支付密码");
		return "editTradePwd";
	}

	// //////////////////////验证///////////////////////
	public Map<String, String> validateEditLoginPwd(String oldPwd, String newPwd1, String newPwd2) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(oldPwd) || !(getCurrentUserOperator().getLoginPwd()).equals(DigestUtils.sha1Hex(oldPwd))) {
			msgMap.put(errorType, "原始密码有误");
			return msgMap;
		}

		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorType, pwdMsg);
			return msgMap;
		}

		if (!newPwd1.equals(newPwd2)) {
			msgMap.put(errorType, "两次输入密码不一致");
			return msgMap;
		}
		if (oldPwd.equals(newPwd1)) {
			msgMap.put(errorType, "新密码不能与原密码一致");
			return msgMap;
		}
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
		if (DigestUtils.sha1Hex(newPwd1).equals(userTradePwd.getTradePwd())) {
			msgMap.put(errorType, "登录密码不能与支付密码一致");
		}
		return msgMap;
	}

	/***
	 * 验证旧密码是否正确
	 * 
	 * @param oldPwd
	 * @param newPwd1
	 * @param newPwd2
	 * @return
	 */
	public Map<String, String> validateEditTradePwd(String oldPwd, String newPwd1, String newPwd2) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorType = BaseConsts.PAGE_ERROR_MSG;
		// AccountPwd accountPwd =
		// accountQueryFacade.getAccountPwdEntityByAccountNo(getCurrentUser().getAccountNo());
		String tradePwdMsg = validateTradePwd(oldPwd);
		if (!ValidateUtils.isEmpty(tradePwdMsg)) {
			msgMap.put(errorType, tradePwdMsg);
			return msgMap;
		}
		String pwdMsg = ValidateUtil.isValidatePwd(newPwd1);
		if (!ValidateUtils.isEmpty(pwdMsg)) {
			msgMap.put(errorType, pwdMsg);
			return msgMap;
		}
		if (!newPwd1.equals(newPwd2)) {
			msgMap.put(errorType, "两次输入密码不一致");
			return msgMap;
		}
		if (oldPwd.equals(newPwd1)) {
			msgMap.put(errorType, "新密码不能与原密码一致");
			return msgMap;
		}
		if (DigestUtils.sha1Hex(newPwd1).equals(getCurrentUserOperator().getLoginPwd())) {
			msgMap.put(errorType, "支付密码不能与登录密码一致");
		}
		return msgMap;
	}
}

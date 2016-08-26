/**
 * wusc.edu.pay.web.portal.base.BaseAction.java
 */
package wusc.edu.pay.web.portal.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.boss.entity.Score;
import wusc.edu.pay.facade.boss.service.ScoreFacade;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantAction;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserCheckRealNameStatusEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.MerchantActionFacade;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.MerchantRoleActionFacade;
import wusc.edu.pay.facade.user.service.MerchantRoleOperatorFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserOperatorLogFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;


/**
 * <ul>
 * <li>Title: 控制器基类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
public class BaseAction extends Struts2ActionSupport {

	@Autowired
	private ScoreFacade scoreFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	/*
	 * @Autowired private MerchantPosFacade merchantPosFacade;
	 */
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private MerchantRoleOperatorFacade merchantRoleOperatorFacade;
	@Autowired
	private MerchantRoleActionFacade merchantRoleActionFacade;
	@Autowired
	private MerchantActionFacade merchantActionFacade;
	@Autowired
	private UserOperatorLogFacade userOperatorLogFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;
	@Autowired
	private UserManagementFacade userManagementFacade;

	/**
	 * 存放常用的枚举值
	 */
	public void putEnums() {
		super.putData("UserAuditStatusEnum", UserAuditStatusEnum.toMap());
		super.putData("UserCheckRealNameStatusEnum", UserCheckRealNameStatusEnum.toMap());
	}

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3913863823025110156L;

	/**
	 * 设置当前用户手机动态验证码(放入会话中).<br/>
	 * 
	 * @param phoneCode
	 *            .
	 */
	public void setCurrentPhoneCode(String phoneCode) {
		this.getSessionMap().put(BaseConsts.CURRENT_USER_PHONE_CODE, phoneCode);
	}

	/**
	 * 获取当前用户手机动态验证码(从会话中获取).<br/>
	 * 
	 * @return PhoneCode.
	 */
	public String getCurrentPhoneCode() {
		return (String) getSessionMap().get(BaseConsts.CURRENT_USER_PHONE_CODE);
	}

	/**
	 * 获取SESSION的用户信息.<br/>
	 * 
	 * @return
	 */
	public UserInfo getCurrentUserInfo() {
		return (UserInfo) this.getSessionMap().get(BaseConsts.CURRENT_USER);
	}

	/**
	 * 设置SESSION值
	 * 
	 * @param userVo
	 */
	public void setCurrentUserInfo(UserInfo userInfo) {
		this.getSessionMap().put(BaseConsts.CURRENT_USER, userInfo);
	}

	/**
	 * 根据当前登录用户的账户编号获取当前用户的账户信息(账户编号从session中获取).<br/>
	 * 
	 * @return Account.
	 */
	public Account getAccount() {
		return accountQueryFacade.getAccountByAccountNo(getCurrentUserInfo().getAccountNo());
	}

	/**
	 * 根据当前登录用户的账户编号获取在线商户信息(用户编号从session中获取).<br/>
	 * 
	 * @return
	 */
	public MerchantOnline getMerchantOnline() {
		return merchantOnlineFacade.getMerchantOnlineByMerchantNo(getCurrentUserInfo().getUserNo());
	}

	/**
	 * 获取在线商户
	 * 
	 * @return
	 */
	public MerchantOnline getMerchantOnline(String userNo) {
		return merchantOnlineFacade.getMerchantOnlineByMerchantNo(userNo);
	}

	/*	*//**
	 * 根据当前登录用户的账户编号获取POS商户信息(用户编号从session中获取).<br/>
	 * 
	 * @return MerchantPos.
	 */
	/*
	 * public MerchantPos getMerchantPos() { return
	 * merchantPosFacade.getMerchantByMerchantNo
	 * (getCurrentUserInfo().getUserNo()); }
	 */

	/**
	 * 获取POS商户信息.<br/>
	 * 
	 * @return MerchantPos .
	 */
	/*
	 * public MerchantPos getMerchantPos(String userNo) { return
	 * merchantPosFacade.getMerchantByMerchantNo(userNo); }
	 */

	/**
	 * 获取会员基本信息
	 * 
	 * @return
	 */
	public MemberInfo getMemberInfo() {
		return memberInfoFacade.getMemberByUserNo(getCurrentUserInfo().getUserNo());
	}

	/**
	 * 获取会员基本信息
	 * 
	 * @return
	 */
	public MemberInfo getMemberInfo(String userNo) {
		return memberInfoFacade.getMemberByUserNo(userNo);
	}

	/**
	 * 获取当前用户的操作员
	 * 
	 * @return
	 */
	public UserOperator getCurrentUserOperator() {
		return (UserOperator) this.getSessionMap().get(BaseConsts.CURRENT_USER_OPERATOR);
	}

	/**
	 * 设置用户操作员基本信息
	 * 
	 * @param userOperator
	 */
	public void setCurrentUserOperator(UserOperator userOperator) {
		this.getSessionMap().put(BaseConsts.CURRENT_USER_OPERATOR, userOperator);
	}

	/**
	 * 根据用户登录名获取操作员.<br/>
	 * 
	 * @param loginName
	 *            用户登录名.<br/>
	 * @return UserOperator.
	 */
	public UserOperator getUserOperator(String loginName) {
		return userOperatorFacade.getUserOperatorByLoginName(loginName);
	}

	/**
	 * 得到以权限（Action）为key，以Action为Value的指定用户的权限Map
	 * 
	 * @param bossOperator
	 * @return
	 */
	public List<String> getMerchantActions(long userOperatorId) {
		// 根据用户ID得到该用户的所有角色拼成的字符串
		String roleIds = merchantRoleOperatorFacade.getRoleStrByOperatorId(userOperatorId);
		// 根据角色ID字符串得到该用户的所有权限拼成的字符串
		String actionIds = "";
		if (!"".equals(roleIds)) {
			actionIds = merchantRoleActionFacade.getMRActionStrByRoleIds(roleIds);
		}
		// 根据权限ID字符串得到权限列表
		List<MerchantAction> merchantActionList = new ArrayList<MerchantAction>();
		if (!"".equals(actionIds)) {
			merchantActionList = merchantActionFacade.listMerchantActionByIdStr(actionIds);
		}
		// 将权限放入HashMap中，其中key为权限（action），值为权限对象
		List<String> actionList = new ArrayList<String>();
		for (int i = 0; i < merchantActionList.size(); i++) {
			MerchantAction MerchantAction = merchantActionList.get(i);
			actionList.add(MerchantAction.getAction());
		}
		return actionList;
	}

	/**
	 * 验证支付密码
	 * 
	 * @param pwd
	 * @return
	 */
	public String validateTradePwd(String tradePwd) {
		String msg = null; // 提示信息
		int lockMinute = PublicConfig.PWD_ERROR_LIMIT_TIME; // 错误时间范围
		int errMaxTimes = PublicConfig.PWD_ERROR_LIMIT_TIMES; // 错误最大次数
		try {
			userTradePwdFacade.validTradePwd(getCurrentUserInfo().getLoginName(), DigestUtils.sha1Hex(tradePwd), lockMinute, errMaxTimes);
		} catch (UserBizException e) {
			msg = e.getMsg();
		}
		return msg;
	}

	/**
	 * 获取客户端的IP
	 */
	public String getIpAddr() {
		String ip = getHttpRequest().getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getHttpRequest().getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getHttpRequest().getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = getHttpRequest().getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 记录操作日志
	 * 
	 * @param opeStatusEnum
	 *            操作状态
	 * @param content
	 *            操作日志
	 */
	public void addUserLog(OpeStatusEnum opeStatusEnum, String content) {
		userOperatorLogFacade.createUserOperatorLog(getCurrentUserOperator().getLoginName(), getCurrentUserInfo().getUserNo(),
				opeStatusEnum, getIpAddr(), content);
	}

	/**
	 * 取当前用户积分
	 * 
	 * @return
	 */
	public Score getScore() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("accountNo", this.getCurrentUserInfo().getAccountNo());
		return scoreFacade.getBy(map);
	}

	/**
	 * 设置用户的预留信息
	 * 
	 * @param greeting
	 */
	public void setUserGreeting(String greeting) {
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(this.getCurrentUserInfo().getUserNo());
		userInfo.setGreeting(greeting);
		userManagementFacade.updateUserInfo(userInfo);

		this.setCurrentUserInfo(userInfo);
	}

	/**
	 * 包括是否启用密码键盘
	 * 
	 * @param key
	 */
	public String getPwd(String key) {
		return StringTools.stringToTrim(getString(key));
	}

	/**
	 * 登录出错次数 + 1
	 */
	public void setLoginTimesReach() {
		int times = 0;
		if (getSessionMap().get("LOGIN_TIMES_REACH") != "" && getSessionMap().get("LOGIN_TIMES_REACH") != null) {
			times = Integer.parseInt(getSessionMap().get("LOGIN_TIMES_REACH") + "");
		}
		super.getSessionMap().put("LOGIN_TIMES_REACH", times + 1);
	}

	/**
	 * 获得登录出错次数
	 * 
	 * @return
	 */
	public Integer getLoginTimesReach() {
		return Integer.parseInt(getSessionMap().get("LOGIN_TIMES_REACH") + "");
	}

	// /***
	// * 修改用户的临时绑定邮箱
	// *
	// * @param tempBindEmail
	// * 临时绑定邮箱
	// */
	// public void updateUserInfoTempBindEmail(String tempBindEmail) {
	// UserInfo userInfo =
	// userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
	// userInfo.setTempBindEmail(tempBindEmail);
	// userManagementFacade.updateUserInfo(userInfo);
	// }
}

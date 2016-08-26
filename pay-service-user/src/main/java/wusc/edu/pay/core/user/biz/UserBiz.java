package wusc.edu.pay.core.user.biz;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.OperatorStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.core.agent.dao.AgentMerchantRelationDao;
import wusc.edu.pay.core.agent.dao.AgentRelationDao;
import wusc.edu.pay.core.pms.dao.PmsOperatorDao;
import wusc.edu.pay.core.user.dao.MemberInfoDao;
import wusc.edu.pay.core.user.dao.MerchantFileDao;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.UserAuditRecordCloseDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.core.user.dao.UserOperatorLogDao;
import wusc.edu.pay.facade.account.enums.AccountInitiatorEnum;
import wusc.edu.pay.facade.account.enums.AccountOperationTypeEnum;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.entity.AgentRelation;
import wusc.edu.pay.facade.pms.entity.PmsOperator;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantFile;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserOperatorLog;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserOperatorLogTypeEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * 用户信息Biz接口
 * 
 * @author huangbin
 * 
 */
@Component("userBiz")
public class UserBiz extends UserRegBiz {
	private static final Log log = LogFactory.getLog(UserBiz.class);
	@Autowired
	private UserInfoDao userInfoDao; // 用户信息接口
	@Autowired
	private MerchantOnlineDao merchantOnlineDao; // 在线商户服务
	@Autowired
	private UserOperatorDao userOperatorDao; // 用户操作员服务
	@Autowired
	private PmsOperatorDao pmsOperatorDao;
	@Autowired
	private AccountManagementFacade accountManagementFacade;
	@Autowired
	private UserOperatorLogDao userOperatorLogDao; // 用户操作日志表
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private AgentMerchantRelationDao agentMerchantRelationDao;
	@Autowired
	private MerchantFileDao merchantFileDao;
	@Autowired
	private SettManagementFacade settManagementFacade;
	@Autowired
	private AgentRelationDao agentRelationDao;
	@Autowired
	private UserBankAccountBiz userBankAccountBiz; 
	@Autowired
	private UserAuditRecordCloseDao userAuditRecordCloseDao;
	/*******************************************************************************/
	//
	/*******************************************************************************/

	/**
	 * 商户登录.
	 * @param loginName 登录名.
	 * @param loginPwd 登录密码(密文)
	 * @param pwdErrMaxTimes 密码错误次数限制.
	 * @param validMinute 密码多次输错后的账户锁定时间.
	 * @return userInfo 用户信息.
	 */
	public UserInfo merchantLogin(String loginName, String loginPwd, Integer pwdErrMaxTimes, Integer validMinute) {

		// 1. 根据登录名判断是否存在此操作员
		UserOperator userOperator = userOperatorDao.getByLoginName(loginName);
		if(userOperator == null){
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误");
		}
		//用户销户记录
		UserAuditRecordClose userAuditRecordClose = 
						userAuditRecordCloseDao.getByUserNo_auditStatus(userOperator.getUserNo(), UserAuditStatusEnum.AGREE.getValue());
		if (ValidateUtils.isEmpty(userOperator)) {
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误");
		}

		// 2.判断商户的状态
		if (OperatorStatusEnum.ACTIVE.getValue() == userOperator.getStatus()) {
			// 3.判断是否存在此商户
			UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userOperator.getUserNo());
			if (userInfo == null) {
				throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误");
			}
			int userType = userInfo.getUserType().intValue();
			if (userType != UserTypeEnum.MERCHANT.getValue() && userType != UserTypeEnum.POSAGENT.getValue()) {
				throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "商户用户名或密码错误");
			}

			// 4.判断登录密码是否正确
			validLoginPwd(userOperator, loginPwd, pwdErrMaxTimes, validMinute);

			return userInfo;
		} 	else if (userAuditRecordClose!=null) {
			throw new UserBizException(UserBizException.LOGIN_OPERATORSTATUS_CANCELLATION, "该用户已被销户，不能登陆");
		}
			else {
			throw new UserBizException(UserBizException.LOGIN_OPERATORSTATUS_INACTIVE, "用户冻结状态，不能登录");
		}
	}

	/**
	 * 会员登录.
	 * @param loginName 登录名.
	 * @param passWord 登录密码(密文)
	 * @param pwdErrMaxTimes 密码错误次数限制.
	 * @param validMinute 密码多次输错后的账户锁定时间.
	 * @return userInfo 用户信息.
	 */
	public UserInfo memberLogin(String loginName, String passWord, Integer pwdErrMaxTimes, Integer validMinute) {

		UserOperator userOperator = userOperatorDao.getByLoginName(loginName);
		// 1.判断操作员是否存在、可用
		if (ValidateUtils.isEmpty(userOperator)) {
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误");
		}
		
		//用户销户记录
		UserAuditRecordClose userAuditRecordClose = 
				userAuditRecordCloseDao.getByUserNo_auditStatus(userOperator.getUserNo(), UserAuditStatusEnum.AGREE.getValue());
		
		if (OperatorStatusEnum.ACTIVE.getValue() == userOperator.getStatus()) {

			// 2.判断是否存关联的userInfo
			UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userOperator.getUserNo());
			if (userInfo == null) {
				throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户名或密码错误");
			} else if (userInfo.getUserType().intValue() != UserTypeEnum.CUSTOMER.getValue()) {
				throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "会员用户名或密码错误");
			}
			// 3.判断登录密码是否正确
			validLoginPwd(userOperator, passWord, pwdErrMaxTimes, validMinute);

			return userInfo;
		}  else if (userAuditRecordClose!=null) {
			throw new UserBizException(UserBizException.LOGIN_OPERATORSTATUS_CANCELLATION, "该用户已被销户，不能登陆");
		}
		   else {
			throw new UserBizException(UserBizException.LOGIN_OPERATORSTATUS_INACTIVE, "用户冻结状态，不能登录");
		}
	}

	/**
	 * 验证登录密码是否正确
	 * 
	 * @param userOperator
	 * @param pwd
	 * @param errMaxTimes
	 * @param lockMinute
	 * @return
	 */
	private void validLoginPwd(UserOperator userOperator, String pwd, int errMaxTimes, int lockMinute) {

		if (userOperator.getPwdErrorLastTime() == null) {
			userOperator.setPwdErrorLastTime(new Date());
		}

		if (new Date().before(DateUtils.addMinute(userOperator.getPwdErrorLastTime(), lockMinute))) {
			if (userOperator.getPwdErrorTimes() >= errMaxTimes) {
				throw new UserBizException(UserBizException.LOGIN_PWDERRORTIMES_OVERRUN, "密码错误次数超限");
			}
		} else {
			userOperator.setPwdErrorTimes(0);
		}

		if (userOperator.getLoginPwd().equals(pwd)) {
			userOperator.setPwdErrorTimes(0);
			userOperator.setLastLoginTime(new Date());
			userOperatorDao.update(userOperator);
		} else {
			// 只记录第一次错误时间
			if (userOperator.getPwdErrorTimes() == 0) {
				userOperator.setPwdErrorLastTime(new Date());
			}
			userOperator.setPwdErrorTimes(userOperator.getPwdErrorTimes() + 1);
			userOperatorDao.update(userOperator);
			if (errMaxTimes - userOperator.getPwdErrorTimes() == 0) {
				throw new UserBizException(UserBizException.LOGIN_PWD_ERROR, "登录密码错误，已被锁定");
			} else {
				throw new UserBizException(UserBizException.LOGIN_PWD_ERROR, "登录密码错误,还有%s次机会.", errMaxTimes - userOperator.getPwdErrorTimes());
			}
		}
	}

	/**
	 * 验证会员是否可销户
	 * 
	 * @param accountNo
	 * @return
	 */
	/*
	 * private Boolean validMemCancelAccount(String accountNo) { // 1年内无交易
	 * String endDate = DateUtils.getShortDateStr(); String beginDate =
	 * DateUtils.getReqDate(DateUtils.getDate(-365)); Map<String, Object>
	 * paramMap = new HashMap<String, Object>(); paramMap.put("accountNo",
	 * accountNo); paramMap.put("beginDate", beginDate);// 创建时间
	 * paramMap.put("endDate", endDate); PageBean pageBean =
	 * accountQueryFacade.queryAccountHistoryListPage(new PageParam(0, 10),
	 * paramMap); if (pageBean != null && pageBean.getTotalCount() > 0) { throw
	 * new UserBizException(UserBizException.CELL_ORDE_IS_EXIST,
	 * "不可销户，一年内存在交易"); } // 2）账户无余额 Account account =
	 * accountQueryFacade.getAccountByAccountNo(accountNo); if
	 * (AmountUtil.bigger(account.getBalance(), 0)) { throw new
	 * UserBizException(UserBizException.CELL_BALANCE_MORETHAN_ONE,
	 * "不可销户，账户有余额"); } return true; }
	 */

	/*******************************************************************************/
	// 重置密码，冻结用户
	/*******************************************************************************/

	/***
	 * 用户冻结/或者激活
	 * 
	 * @param LoginName
	 *            (操作员)
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean frozenUser(String loginName, String type) {
		UserOperator userOperator = userOperatorDao.getByLoginName(loginName);
		if (ValidateUtils.isEmpty(userOperator)) {
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "该用户不存在");
		}
		if ("0".equals(type) && "1".equals(userOperator.getType())) {
			throw new UserBizException(UserBizException.USERINFO_NOT_EDIT_AUTHORITY, "你没有修改超级管理员的权限");
		}
		if (userOperator.getStatus().intValue() == OperatorStatusEnum.ACTIVE.getValue()) {
			userOperator.setStatus(OperatorStatusEnum.INACTIVE.getValue());
			if (userOperatorDao.update(userOperator) > 0) {
				return true;
			}
		} else {
			userOperator.setStatus(OperatorStatusEnum.ACTIVE.getValue());
			userOperator.setPwdErrorTimes(0);
			if (userOperatorDao.update(userOperator) > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 商户审核通过
	 * 
	 * @param loginName
	 */
	@Transactional(rollbackFor = Exception.class)
	public void merchantAudit(String loginName) {
		UserOperator userOperator = userOperatorDao.getByLoginName(loginName);
		if (ValidateUtils.isEmpty(userOperator)) {
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户不存在");
		}

		// 激活账户
		accountManagementFacade.changeAccountStatus(userOperator.getUserNo(), AccountOperationTypeEnum.UNFREEZE_ACCOUNT, AccountInitiatorEnum.BOSS_SYS, "商户审核通过");
	}

	/**
	 * 修改用户信息 .
	 * @param userInfo 用户信息 .
	 * @throws UserBizException
	 */
	public long updateUserInfo(UserInfo userInfo) {
		return userInfoDao.update(userInfo);
	}

	/**
	 * 修改登录密码
	 * 
	 * @param loginName
	 *            操作员登录名
	 * @param oldPwd
	 *            原密码（加密后）
	 * @param newPwd
	 *            新密码（加密后）
	 * @param isChangedPwd
	 *            是否更改过密码(0:否,1:是)
	 */
	public void changeOperatorLoginPwd(String loginName, String oldPwd, String newPwd, Integer isChangedPwd) {
		UserOperator userOperator = userOperatorDao.getByLoginName(loginName);
		if (userOperator == null) {
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户不存在");
		}
		if (!userOperator.getLoginPwd().equals(oldPwd)) {
			throw new UserBizException(UserBizException.LOGPWD_ERROR, "登录密码错误.");
		}
		userOperator.setLoginPwd(newPwd);
		userOperator.setIsChangedPwd(isChangedPwd);
		userOperator.setLastAlertPwdTime(new Date());
		userOperator.setPwdErrorTimes(0);
		userOperatorDao.update(userOperator);
	}

	/***
	 * 更新商户和用户信息
	 * 
	 * @param merchant
	 *            商户信息表
	 * @param userInfo
	 *            用户信息表
	 */
	@Transactional(rollbackFor = Exception.class)
	public int updateMerchantAndUser(MerchantOnline merchant, UserInfo userInfo) {
		merchantOnlineDao.update(merchant);
		userInfoDao.update(userInfo);
		return 1;
	}
	/**
	 * 根据用户编号更新绑定邮箱.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param bindEmail
	 *            要更新的绑定邮箱.<br/>
	 */
	public long bindEmail(String userNo, String bindEmail) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		userInfo.setBindEmail(bindEmail);
		userInfo.setIsEmailAuth(PublicStatusEnum.ACTIVE.getValue());
		return userInfoDao.update(userInfo);
	}

	/**
	 * 根据用户编号解除邮箱绑定.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 */
	public long unBindEmail(String userNo) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		userInfo.setBindEmail(null);
		userInfo.setIsEmailAuth(PublicStatusEnum.INACTIVE.getValue());
		return userInfoDao.update(userInfo);
	}

	/**
	 * 根据用户编号更新绑定手机号.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param bindMobileNo
	 *            要更新的绑定手机号.<br/>
	 */
	public long bindMobileNo(String userNo, String bindMobileNo) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		userInfo.setBindMobileNo(bindMobileNo);
		userInfo.setIsMobileAuth(PublicStatusEnum.ACTIVE.getValue());
		return userInfoDao.update(userInfo);
	}

	/**
	 * 根据用户编号解除手机号绑定.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 */
	public long unBindMobileNo(String userNo) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		userInfo.setBindMobileNo(null);
		userInfo.setIsMobileAuth(PublicStatusEnum.INACTIVE.getValue());
		return userInfoDao.update(userInfo);
	}

	/**
	 * 实名验证： 1、绑定身份证号、真实姓名 2、修改是否实名验证状态 3、激活账户
	 * 
	 * @param userNo
	 * @param cardNo
	 */
	public void bindCardNo(String userNo, String cardNo, String realName) {
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		userInfo.setCardNo(cardNo);
		userInfo.setIsRealNameAuth(PublicStatusEnum.ACTIVE.getValue());
		userInfo.setRealName(realName);
		userInfoDao.update(userInfo);

	}

	/***
	 * 更新用户状态,并标记用户为已实名认证
	 * 
	 * @param userNo
	 * @param changeStatus
	 * @return
	 */
	public int updateUserInfoStatusAndIsRealName(String userNo, int changeStatus) {
		return userInfoDao.updateUserInfoStatusAndIsRealName(userNo, changeStatus, PublicStatusEnum.ACTIVE.getValue());
	}

	/***
	 * 冻结用户不能登录
	 * 
	 * @param userNo
	 *            商户编号
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public boolean freezeuserOperator(String userNo, String ip) {
		log.info(String.format("===========> 风控系统调用用户服务, 更新商户状态, 使其不能登录 , 用户编号：%s<========", userNo));
		// 根据商户编号查询登录名
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		if (userInfo == null)
			throw new UserBizException(UserBizException.LOGIN_LOGNAME_NOT_EXIST, "用户信息不存在!");
		
		// 冻结商户下所有操作员不能登录
		userOperatorDao.updateUserOperatorStatusByUserNo(userNo, UserStatusEnum.INACTIVE.getValue());
		log.info(String.format("========> 冻结操作员成功，用户编号为：%s", userNo));
		// 冻结用户信息
		userInfoDao.updateUserInfoStatusAndIsRealName(userNo, UserStatusEnum.INACTIVE.getValue(), PublicStatusEnum.ACTIVE.getValue());
		log.info(String.format("========> 冻结用户成功，用户编号为：%s", userNo));
		if(userInfo.getUserType() == UserTypeEnum.MERCHANT.getValue()){
			// 冻结在线商户信息
			MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(userNo);
			merchantOnline.setStatus(MerchantStatusEnum.INACTIVE.getValue());
			merchantOnlineDao.update(merchantOnline);
			log.info(String.format("========> 更新在线商户表成功，用户编号为：%s", userNo));
		} else if (userInfo.getUserType() == UserTypeEnum.CUSTOMER.getValue()){
			// 冻结会员信息
			MemberInfo memberInfo = memberInfoDao.getMemberInfoByMemberNo(userNo);
			memberInfo.setStatus(MerchantStatusEnum.INACTIVE.getValue());
			memberInfoDao.update(memberInfo);
		}

		// 记录冻结操作员的操作日志
		UserOperatorLog entity = new UserOperatorLog();
		entity.setContent("冻结操作员，用户编号[" + userNo + "]");
		entity.setLoginName(userInfo.getLoginName());
		entity.setOperateStatus(1);
		entity.setIp(ip);
		entity.setOperType(UserOperatorLogTypeEnum.OPER_MOTIFY.getValue());
		entity.setUserNo(userNo);
		userOperatorLogDao.insert(entity);

		return true;
	}

	

	/***
	 * 更新代理商系统的商户信息，以及关联的结算账户，结算方式，用户信息，代理商商户关系表，商户资质信息
	 * @param userInfo
	 * @param merchant
	 * @param agentMerchantRelation
	 * @param MerchantFile				商户资质文件
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateAgentMerchantInfo(UserInfo userInfo, MerchantOnline merchant, SettRule settRule ,
			UserBankAccount userBankAccount, AgentMerchantRelation agentMerchantRelation, MerchantFile merchantFile) {
		
		log.info("========> 进入修改商户信息的biz方法。");
		
		merchantOnlineDao.update(merchant);
		log.info("========> 修改商户表的信息成功。");
		
		// 修改操作员真实姓名
		UserOperator operator = userOperatorDao.getByLoginName(userInfo.getLoginName());
		if(operator != null){
			operator.setRealName(merchant.getFullName()); 
			userOperatorDao.update(operator);
		}
		
		userInfoDao.update(userInfo);
		log.info("========> 修改用户表的信息成功。");
		
		merchantFileDao.update(merchantFile);
		log.info("========> 修改商户资质文件表信息成功。");
		
		userBankAccountBiz.updateUserBankAccount(userBankAccount);
		log.info("========> 修改用户银行卡账户信息成功。");
		
		agentMerchantRelationDao.update(agentMerchantRelation);
		log.info("========> 修改代理商商户关联表信息成功。");
		
		settManagementFacade.updateSettRule(settRule);
		log.info("========> 修改代理商商户结算规则信息成功。");
		
		log.info("========> 退出修改商户信息的biz方法，信息修改成功。");
	}
	/**
	 * 修改代理商系统的代理商信息
	 * @param userInfo
	 * @param merchant
	 * @param settRule
	 * @param userBankAccount
	 * @param agentRelation
	 */
	@Transactional(rollbackFor = Exception.class)
	public void updateAgentInfo(UserInfo userInfo, MerchantOnline merchant, SettRule settRule, UserBankAccount userBankAccount, AgentRelation agentRelation) {
		log.info("========> 进入修改商户信息的biz方法。");

		merchantOnlineDao.update(merchant);
		log.info("========> 修改商户表的信息成功。");
		
		// 修改代理商的操作员信息
		PmsOperator pmsOperator = pmsOperatorDao.getByLoginName(userInfo.getLoginName());
		if(pmsOperator != null){
			pmsOperator.setRealName(merchant.getFullName());
			pmsOperatorDao.update(pmsOperator);
		}
		
		userInfoDao.update(userInfo);
		log.info("========> 修改用户表的信息成功。");

		agentRelationDao.update(agentRelation);
		log.info("========> 修改代理商关联表信息成功。");
		
		userBankAccountBiz.updateUserBankAccount(userBankAccount);
		log.info("========> 修改结算账户信息成功。");

		settManagementFacade.updateSettRule(settRule);
		log.info("========> 修改结算规则信息成功。");
		
		log.info("========> 退出修改商户信息的biz方法，信息修改成功。");

	}
}

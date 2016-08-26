package wusc.edu.pay.core.user.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.constant.PublicStatus;
import wusc.edu.pay.common.enums.OperatorStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.agent.dao.AgentMerchantRelationDao;
import wusc.edu.pay.core.agent.dao.AgentRelationDao;
import wusc.edu.pay.core.pms.dao.PmsOperatorDao;
import wusc.edu.pay.core.user.dao.MemberInfoDao;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.UserAuditRecordCloseDao;
import wusc.edu.pay.core.user.dao.UserAuditRecordRealNameDao;
import wusc.edu.pay.core.user.dao.UserAuditRecordStatusDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.core.user.dao.UserOperatorDao;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountInitiatorEnum;
import wusc.edu.pay.facade.account.enums.AccountOperationTypeEnum;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.account.service.AccountManagementFacade;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.agent.entity.AgentMerchantRelation;
import wusc.edu.pay.facade.agent.entity.AgentRelation;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.enums.MemberStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.UserAuditStatusEnum;
import wusc.edu.pay.facade.user.enums.UserCheckRealNameStatusEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/***
 * 
 * @描述: .
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午5:38:28 .
 * @版本: V1.0.
 * 
 */
@Component("userAuditBiz")
public class UserAuditBiz {

	private static final Log log = LogFactory.getLog(UserAuditBiz.class);

	@Autowired
	private UserAuditRecordCloseDao userAuditRecordCloseDao;
	@Autowired
	private UserAuditRecordRealNameDao userAuditRecordRealNameDao;
	@Autowired
	private UserAuditRecordStatusDao userAuditRecordStatusDao;
	@Autowired
	private UserBiz userBiz;
	@Autowired
	private MerchantOnlineDao merchantOnlineDao;
	@Autowired
	private MemberInfoDao memberInfoDao;
	@Autowired
	private AccountManagementFacade accountManagementFacade;
	@Autowired
	private UserInfoDao userInfoDao;
	@Autowired
	private UserOperatorDao userOperatorDao;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private AgentRelationDao agentRelationDao;
	@Autowired
	private PmsOperatorDao pmsOperatorDao;
	@Autowired
	private AgentMerchantRelationDao agentMerchantRelationDao;

	/***
	 * 通过id查找用户实名验证审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordRealName getUserAuditRecordRealNameById(long id) {
		return userAuditRecordRealNameDao.getById(id);
	}

	/***
	 * 分页查询用户实名验证审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordRealNameListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditRecordRealNameDao.listPage(pageParam, paramMap);
	}

	/***
	 * 更加id查找用户销户审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordClose getUserAuditRecordCloseById(long id) {
		return userAuditRecordCloseDao.getById(id);
	}

	/**
	 * 分页查询用户销户审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordCloseListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditRecordCloseDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据id查找用户状态审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordStatus getUserAuditRecordStatusById(long id) {
		return userAuditRecordStatusDao.getById(id);
	}

	/**
	 * 分页查询用户状态审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordStatusListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditRecordStatusDao.listPage(pageParam, paramMap);
	}

	/**
	 * 用户实名验证通过 .<br/>
	 * 1、更新实名验证记录表 .<br/>
	 * 2、更新用户表的CardNo、RealName、IsRealNameAuth .<br/>
	 * 3、更新会员表的RealName.<br/>
	 * 4、修改会员的账户状态 .<br/>
	 * 
	 * @param id
	 *            用户实名证认证审核记录ID.
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void auditPass_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {

		UserAuditRecordRealName entity = getUserAuditRecordRealNameById(id);
		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_REALNAME_IS_NULL, "实名验证的审核记录不存在");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.AGREE.getValue());
		userAuditRecordRealNameDao.update(entity);

		// 更新用户表的
		userBiz.bindCardNo(entity.getUserNo(), entity.getCardNo(), entity.getRealName());

		// 更新会员信息表
		MemberInfo memberInfo = memberInfoDao.getMemberInfoByMemberNo(entity.getUserNo());
		if (memberInfo != null) {
			memberInfo.setRealName(entity.getRealName());
			memberInfo.setCardNo(entity.getCardNo());
			memberInfoDao.update(memberInfo);
		}

		// 修改会员的账户状态
		accountManagementFacade.changeAccountStatus(entity.getUserNo(), AccountOperationTypeEnum.UNFREEZE_ACCOUNT,
				AccountInitiatorEnum.BOSS_SYS, "实名谁审核通过");

	}

	/**
	 * 用户实名验证不通过.<br/>
	 * 1、更新实名验证记录表
	 * 
	 * @param id
	 *            用户实名验认证审核记录ID .
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 */
	public void auditRefuse_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {

		UserAuditRecordRealName entity = getUserAuditRecordRealNameById(id);
		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_REALNAME_IS_NULL, "实名验证的审核记录不存在");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.REFUSE.getValue());
		userAuditRecordRealNameDao.update(entity);
	}

	/**
	 * 用户状态审核通过.<br/>
	 * 1、修改用户状态变更记录 .<br/>
	 * 2、更新用户信息表的Status、IsRealName.<br/>
	 * 3、修改商户或者会员信息表的Status.<br/>
	 * 4、激活账户.<br/>
	 * 
	 * @param id
	 *            用户状态变更审核记录ID.
	 * @param userNo
	 *            用户编号.
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 * @param changeStatus
	 *            申请变更的状态.
	 * @param currentStatus
	 *            当前状态.
	 */
	@Transactional(rollbackFor = Exception.class)
	public void auditPass_Status(long id, String userNo, String auditOperatorLoginName, String auditOperatorName, String applyDesc,
			String auditDesc, Integer changeStatus, Integer currentStatus) {

		if (id == 0) {
			id = createUserAuditRecordStatus(userNo, auditOperatorLoginName, auditOperatorName, applyDesc, changeStatus, currentStatus);
		}

		UserAuditRecordStatus entity = getUserAuditRecordStatusById(id);

		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_STATUS_IS_NULL, "用户状态的审核记录不存在");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.AGREE.getValue());
		entity.setActualChangeStatus(changeStatus);

		userAuditRecordStatusDao.update(entity);
		// 更新用户状态,并标记用户为已实名认证
		userBiz.updateUserInfoStatusAndIsRealName(userNo, changeStatus);

		// 如果是商户(在线，pos，代理商)就修改商户信息
		if (entity.getUserType().intValue() == UserTypeEnum.MERCHANT.getValue()
				|| entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {
			MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(userNo);
			merchantOnline.setStatus(changeStatus);
			merchantOnlineDao.update(merchantOnline);

			if (entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {
				AgentRelation AR = agentRelationDao.getByAgentNo(userNo, null, null);
				AR.setAgentStatus(changeStatus);
				agentRelationDao.update(AR);
			}

		}

		// 如果是pos代理商要修改代理商层级关系的状态
		if (entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {

		}

		// 如果是会员就修改会员信息
		if (entity.getUserType().intValue() == UserTypeEnum.CUSTOMER.getValue()) {

			MemberInfo memberInfo = memberInfoDao.getMemberInfoByMemberNo(userNo);
			memberInfo.setStatus(changeStatus);
			memberInfoDao.update(memberInfo);

		}

		// 如果是激活动作要把操作员状态改为可登陆(激活)
		if (changeStatus.intValue() == UserStatusEnum.ACTIVE.getValue()) {
			// 如果是POS代理商更新POS代理商操作员表
			if (entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {
				pmsOperatorDao.updateUserOperatorStatusByUserNo(userNo, OperatorStatusEnum.ACTIVE.getValue());
			} else {
				userOperatorDao.updateUserOperatorStatusByUserNo(userNo, OperatorStatusEnum.ACTIVE.getValue());
			}
			// 激活账户
			accountManagementFacade.changeAccountStatus(userNo, AccountOperationTypeEnum.UNFREEZE_ACCOUNT, AccountInitiatorEnum.BOSS_SYS,
					"");
			//如果是POS商户，当状态从初审通过变为激活时， 则需要更新开通时间
			if (currentStatus==MerchantStatusEnum.FIRSTPASS.getValue()&&entity.getUserType().intValue() == UserTypeEnum.MERCHANT.getValue()) {
				Map<String, Object> paramMap=new HashMap<String, Object>();
				paramMap.put("merchantNo", entity.getUserNo());
				AgentMerchantRelation agentMerchantRelation=agentMerchantRelationDao.getBy(paramMap);
				if(agentMerchantRelation!=null){
					agentMerchantRelation.setOpenDate(new Date());
					agentMerchantRelationDao.update(agentMerchantRelation);
				}
			}
		}
		// 如果是冻结动作要把操作员状态改为不可登陆(冻结)
		// 还要修改账户状态
		if (changeStatus.intValue() == UserStatusEnum.INACTIVE.getValue()) {
			// 如果是POS代理商更新POS代理商操作员表
			if (entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {
				pmsOperatorDao.updateUserOperatorStatusByUserNo(userNo, OperatorStatusEnum.INACTIVE.getValue());
			} else {
				userOperatorDao.updateUserOperatorStatusByUserNo(userNo, OperatorStatusEnum.INACTIVE.getValue());
			}
			// 冻结账户
			accountManagementFacade.changeAccountStatus(userNo, AccountOperationTypeEnum.FREEZE_ACCOUNT, AccountInitiatorEnum.BOSS_SYS, "");
		}

	}

	/**
	 * 用户状态审核不通过.<br/>
	 * 1、修改用户状态变更记录.<br/>
	 * 
	 * @param id
	 *            用户状态变更审核记录ID.
	 * @param userNo
	 *            用户编号.
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 * @param changeStatus
	 *            申请变更的状态.
	 * @param currentStatus
	 *            当前状态.
	 */
	public void auditRefuse_Status(long id, String userNo, String auditOperatorLoginName, String auditOperatorName, String applyDesc,
			String auditDesc, Integer changeStatus, Integer currentStatus) {

		if (id == 0) {
			id = createUserAuditRecordStatus(userNo, auditOperatorLoginName, auditOperatorName, applyDesc, changeStatus, currentStatus);
		}

		UserAuditRecordStatus entity = getUserAuditRecordStatusById(id);

		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_STATUS_IS_NULL, "用户状态的审核记录不存在");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.REFUSE.getValue());
		entity.setActualChangeStatus(changeStatus);

		userAuditRecordStatusDao.update(entity);

		// 如果是商户就修改商户信息(将状态改为审核不通过)
		if ((entity.getUserType().intValue() == UserTypeEnum.MERCHANT.getValue()
				|| entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue())
				&& (currentStatus == MerchantStatusEnum.CREATED.getValue() || currentStatus == MerchantStatusEnum.FIRSTPASS.getValue())) {

			MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(userNo);
			merchantOnline.setStatus(MerchantStatusEnum.NOPASS.getValue());
			merchantOnlineDao.update(merchantOnline);

			if (entity.getUserType().intValue() == UserTypeEnum.POSAGENT.getValue()) {
				AgentRelation AR = agentRelationDao.getByAgentNo(userNo, null, null);
				AR.setAgentStatus(MerchantStatusEnum.NOPASS.getValue());
				agentRelationDao.update(AR);
			}
		}
	}

	/**
	 * 用户销户的审核通过.<br/>
	 * 1、修改用户销户申请记录 .<br/>
	 * 2、修改商户、会员信息表的Status .<br/>
	 * 3、调用结算服务.<br/>
	 * 
	 * @param id
	 *            销户审核记录ID.
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 * @param isLogin
	 *            是否可以登陆.
	 * @throws Exception
	 * @throws UserBizException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void auditPass_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc, Integer isLogin)
			throws AccountBizException, UserBizException, Exception {

		UserAuditRecordClose entity = getUserAuditRecordCloseById(id);
		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_CLOSE_IS_NULL, "用户销户的审核记录不存在");
		}
		String userNo = entity.getUserNo();
		// 账户无余额
		Account account = accountQueryFacade.getAccountByUserNo(userNo);
		if (account.getBalance() > 0) {
			log.error("[" + userNo + "]用户销户的审核记录不存在");
			throw new UserBizException(UserBizException.CELL_BALANCE_MORETHAN_ONE, "不可销户，账户有余额");
		}

		// 1年内无交易
		String endDate = DateUtils.getShortDateStr();
		String beginDate = DateUtils.getReqDate(DateUtils.getDate(-365));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", account.getAccountNo());
		paramMap.put("beginDate", beginDate);// 创建时间
		paramMap.put("endDate", endDate);
		PageBean pageBean = accountQueryFacade.queryAccountHistoryListPage(new PageParam(1, 10), paramMap);
		if (pageBean.getRecordList() != null && pageBean.getTotalCount() > 0) {
			log.error("[" + userNo + "] 不可销户，一年内存在交易");
			throw new UserBizException(UserBizException.CELL_ORDE_IS_EXIST, "不可销户，一年内存在交易");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.AGREE.getValue());
		entity.setIsLogin(isLogin); // 是否可以登陆

		userAuditRecordCloseDao.update(entity);

		// 如果是商户就修改商户信息
		if (entity.getUserType() == UserTypeEnum.MERCHANT.getValue()) {

			MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(userNo);
			merchantOnline.setStatus(MerchantStatusEnum.CLOSE.getValue());
			merchantOnlineDao.update(merchantOnline);
			log.info("[" + userNo + "]注销在线商户");
		}
		// 如果是会员就修改会员信息
		if (entity.getUserType() == UserTypeEnum.CUSTOMER.getValue()) {

			MemberInfo memberInfo = memberInfoDao.getMemberInfoByMemberNo(userNo);
			memberInfo.setStatus(MemberStatusEnum.CLOSE.getValue());
			memberInfoDao.update(memberInfo);
			log.info("[" + userNo + "]注销会员");
		}

		// 用户信息表
		userInfoDao.updateUserInfoStatusAndIsRealName(userNo, UserStatusEnum.INACTIVE.getValue(), PublicStatusEnum.INACTIVE.getValue());
		log.info("[" + userNo + "]修改用户表状态为冻结。。");
		// 账户信息表
		accountManagementFacade.changeAccountStatus(userNo, AccountOperationTypeEnum.CANCEL_ACCOUNT, AccountInitiatorEnum.BOSS_SYS, "销户操作");
		log.info("[" + userNo + "]修改账户表状态为销户。。。");
		// 操作员信息表
		userOperatorDao.updateUserOperatorStatusByUserNo(userNo, OperatorStatusEnum.INACTIVE.getValue());
		log.info("[" + userNo + "]修改操作员表状态为冻结。。");
		// 调用结算服
		// accountSettleFacade.closeAccountSettleOneMerchant(entity.getUserNo(),
		// entity.getUserType());

	}

	/**
	 * 用户销户审核不通过.<br/>
	 * 1、修改用户销户申请记录
	 * 
	 * @param id
	 *            销户审核记录ID.
	 * @param auditOperatorLoginName
	 *            审核人登录名.
	 * @param auditOperatorName
	 *            审核人真实姓名.
	 * @param auditDesc
	 *            审核描述.
	 * @throws UserBizException
	 */
	@Transactional(rollbackFor = Exception.class)
	public void auditRefuse_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {

		UserAuditRecordClose entity = getUserAuditRecordCloseById(id);

		if (entity == null) {
			throw new UserBizException(UserBizException.USER_AUDIT_RECORD_CLOSE_IS_NULL, "用户销户的审核记录不存在");
		}

		entity.setAuditDesc(auditDesc);
		entity.setAuditOperatorLoginName(auditOperatorLoginName);
		entity.setAuditOperatorName(auditOperatorName);
		entity.setDealTime(new Date());
		entity.setAuditStatus(UserAuditStatusEnum.REFUSE.getValue());

		userAuditRecordCloseDao.update(entity);

		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(entity.getUserNo());
		if (userInfo != null) {
			// 恢复用户，账户
			auditPass_Status(0, entity.getUserNo(), entity.getLoginName(), entity.getLoginName(), "(销户审核不通过)系统自动触发申请",
					"(销户审核不通过)系统自动促发审核通过", UserStatusEnum.ACTIVE.getValue(), userInfo.getStatus());
		}
	}

	/**
	 * 新增用户状态变更审核记录 .<br/>
	 * 1、判断是否有未完成的审核记录 .<br/>
	 * 2、判断用户是否存在 .<br/>
	 * 3、创建一条用户状态变更审核记录 .<br/>
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param applyOperatorLoginName
	 *            申请人登录名.
	 * @param applyOperatorName
	 *            申请人真实姓名 .
	 * @param applyDesc
	 *            申请描述.
	 * @param changeStatus
	 *            申请变更的状态.
	 * @param currentStatus
	 *            当前状态.
	 */
	public long createUserAuditRecordStatus(String userNo, String applyOperatorLoginName, String applyOperatorName, String applyDesc,
			Integer changeStatus, Integer currentStatus) {

		UserAuditRecordStatus userAuditRecordStatus = userAuditRecordStatusDao.getByUserNo_auditStatus(userNo,
				UserAuditStatusEnum.WAIT.getValue());

		if (userAuditRecordStatus != null) {
			throw new UserBizException(UserBizException.USER_LAST_USERAUDIT_IS_NOT_FINISHED, "上次审核未处理完成");
		}

		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);

		if (userInfo == null) {
			throw new UserBizException(UserBizException.USERINFO_NOT_IS_EXIST, "用户不存在");
		}
		userAuditRecordStatus = new UserAuditRecordStatus();

		userAuditRecordStatus.setApplyDesc(applyDesc);
		userAuditRecordStatus.setApplyOperatorLoginName(applyOperatorLoginName);
		userAuditRecordStatus.setApplyOperatorName(applyOperatorName);
		userAuditRecordStatus.setAuditDesc("");
		userAuditRecordStatus.setAuditOperatorLoginName("");
		userAuditRecordStatus.setAuditOperatorName("");
		userAuditRecordStatus.setAuditStatus(UserAuditStatusEnum.WAIT.getValue());
		userAuditRecordStatus.setChangeStatus(changeStatus);
		userAuditRecordStatus.setCurrentStatus(currentStatus);
		userAuditRecordStatus.setFullName(userInfo.getRealName());
		userAuditRecordStatus.setLoginName(userInfo.getLoginName());
		userAuditRecordStatus.setUserNo(userNo);
		userAuditRecordStatus.setUserType(userInfo.getUserType());
		// userAuditRecordStatus.setAccountChangeStatus(accountChangeStatus);

		return userAuditRecordStatusDao.insert(userAuditRecordStatus);
	}

	/**
	 * 增加用户实名验证审核记录.<br/>
	 * 1、判断用户是否存在 .<br/>
	 * 2、判断用户是否已实名验证 .<br/>
	 * 3、判断用户是否有未完成的记录 .<br/>
	 * 4、创建用户实名验证审核记录 .<br/>
	 * 
	 * @param cardNo
	 *            身份证号
	 * @param cardFrontPath
	 *            身份证正面
	 * @param cardBackPath
	 *            身份证反面
	 * @param cardNoValid
	 *            身份证到期日期
	 * @param userOperator
	 *            会员（操作员）
	 * @param realName
	 *            真实姓名
	 * @param country
	 *            国籍
	 * @param profession
	 *            职业
	 */
	public long createUserAuditRecordRealName(String cardNo, String cardFrontPath, String cardBackPath, Date cardNoValid,
			UserOperator userOperator, String realName, String country, String profession) {

		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userOperator.getUserNo());

		if (userInfo == null) {
			throw new UserBizException(UserBizException.USERINFO_NOT_IS_EXIST, "用户不存在");
		}

		if (userInfo.getIsRealNameAuth().intValue() == PublicStatus.ACTIVE) {
			throw new UserBizException(UserBizException.USER_REALNAMEED_IS_CHECKED, "用户已经实名验证");
		}

		// 更新会员的国籍和职业
		MemberInfo memberInfo = memberInfoDao.getMemberInfoByMemberNo(userOperator.getUserNo());
		if (memberInfo == null) {
			throw new UserBizException(UserBizException.MEMBER_INFO_IS_NULL, "会员信息为空");
		}
		memberInfo.setCountry(country); // 国籍
		memberInfo.setProfession(profession); // 职业
		memberInfoDao.update(memberInfo);

		UserAuditRecordRealName userAuditRecordRealName = userAuditRecordRealNameDao.getByUserNo_auditStatus(userOperator.getUserNo(),
				UserAuditStatusEnum.WAIT.getValue());

		if (userAuditRecordRealName != null) {
			throw new UserBizException(UserBizException.USER_LAST_USERAUDIT_IS_NOT_FINISHED, "上次审核未处理完成");
		}

		userAuditRecordRealName = new UserAuditRecordRealName();
		userAuditRecordRealName.setApplyDesc("系统备注：实名验证申请");
		userAuditRecordRealName.setApplyOperatorLoginName(userOperator.getLoginName());
		userAuditRecordRealName.setApplyOperatorName(userOperator.getRealName());
		userAuditRecordRealName.setAuditDesc("");
		userAuditRecordRealName.setAuditOperatorLoginName("");
		userAuditRecordRealName.setAuditOperatorName("");
		userAuditRecordRealName.setAuditStatus(UserCheckRealNameStatusEnum.WAIT.getValue());
		userAuditRecordRealName.setCardBackPath(cardBackPath);
		userAuditRecordRealName.setCardFrontPath(cardFrontPath);
		userAuditRecordRealName.setCardNo(cardNo);
		userAuditRecordRealName.setCardNoValid(cardNoValid);
		userAuditRecordRealName.setFullName(userOperator.getRealName());
		userAuditRecordRealName.setLoginName(userOperator.getLoginName());
		userAuditRecordRealName.setRealName(realName);
		userAuditRecordRealName.setUserNo(userOperator.getUserNo());
		userAuditRecordRealName.setUserType(userInfo.getUserType());

		return userAuditRecordRealNameDao.insert(userAuditRecordRealName);
	}

	/**
	 * 创建用户销户审核记录.<br/>
	 * 1、判断用户是否存在 .<br/>
	 * 2、判断用户是否有未完成的记录 .<br/>
	 * 3、增加用户销户审核记录 .<br/>
	 * 
	 * @param userNo
	 *            用户编号.
	 * @param applyOperatorLoginName
	 *            申请人登录名.
	 * @param applyOperatorName
	 *            申请人真实姓名.
	 * @param applyDesc
	 *            申请描述.
	 */
	public void createUserAuditRecordClose(String userNo, String applyOperatorLoginName, String applyOperatorName, String applyDesc) {

		// 用户是否存在
		UserInfo userInfo = userInfoDao.getUserInfoByUserNo(userNo);
		if (userInfo == null) {
			throw new UserBizException(UserBizException.USERINFO_NOT_IS_EXIST, "用户不存在");
		}

		// 账户无余额
		Account account = accountQueryFacade.getAccountByUserNo(userNo);
		if (account.getBalance() > 0) {
			throw new UserBizException(UserBizException.CELL_BALANCE_MORETHAN_ONE, "不可销户，账户有余额");
		}

		// 1年内无交易
		String endDate = DateUtils.getShortDateStr();
		String beginDate = DateUtils.getReqDate(DateUtils.getDate(-365));
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", userInfo.getAccountNo());
		paramMap.put("beginDate", beginDate);// 创建时间
		paramMap.put("endDate", endDate);
		PageBean pageBean = accountQueryFacade.queryAccountHistoryListPage(new PageParam(1, 10), paramMap);
		if (pageBean != null && pageBean.getTotalCount() > 0) {
			throw new UserBizException(UserBizException.CELL_ORDE_IS_EXIST, "不可销户，一年内存在交易");
		}

		UserAuditRecordClose userAuditRecordClose = userAuditRecordCloseDao.getByUserNo_auditStatus(userNo,
				UserAuditStatusEnum.WAIT.getValue());
		if (userAuditRecordClose != null) {
			throw new UserBizException(UserBizException.USER_LAST_USERAUDIT_IS_NOT_FINISHED, "上次审核未处理完成");
		}

		userAuditRecordClose = new UserAuditRecordClose();
		userAuditRecordClose.setApplyDesc(applyDesc);
		userAuditRecordClose.setApplyOperatorLoginName(applyOperatorLoginName);
		userAuditRecordClose.setApplyOperatorName(applyOperatorName);
		userAuditRecordClose.setAuditDesc("");
		userAuditRecordClose.setAuditOperatorLoginName("");
		userAuditRecordClose.setAuditOperatorName("");
		userAuditRecordClose.setAuditStatus(UserAuditStatusEnum.WAIT.getValue());
		userAuditRecordClose.setUserNo(userNo);
		userAuditRecordClose.setUserType(userInfo.getUserType());
		userAuditRecordClose.setFullName(userInfo.getRealName());
		userAuditRecordClose.setLoginName(userInfo.getLoginName());
		userAuditRecordCloseDao.insert(userAuditRecordClose);

		// 冻结用户
		auditPass_Status(0, userNo, applyOperatorLoginName, applyOperatorLoginName, "(创建用户销户审核记录)系统自动触发申请", "(创建用户销户审核记录)系统自动促发审核通过",
				UserStatusEnum.INACTIVE.getValue(), userInfo.getStatus());

	}

	/**
	 * 根据用户编号、审核状态查找实名验证申请记录.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param auditStatus
	 *            审核状态.<br/>
	 * @return userAuditRecordRealName
	 */
	public UserAuditRecordRealName getByUserNo_auditStatus(String userNo, Integer auditStatus) {
		return userAuditRecordRealNameDao.getByUserNo_auditStatus(userNo, auditStatus);
	}

	/**
	 * 根据用户编号、审核状态查找用户状态变更审核记录.<br/>
	 * 
	 * @param userNo
	 *            用户编号.<br/>
	 * @param auditStatus
	 *            审核状态.<br/>
	 * @return userAuditRecordStatus.
	 */
	public UserAuditRecordStatus getUserAuditRecordStatusByUserNo_auditStatus(String userNo, Integer auditStatus) {
		return userAuditRecordStatusDao.getByUserNo_auditStatus(userNo, auditStatus);
	}

	/***
	 * 根据map里面的参数查询UserAuditRecordStatus数据
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<UserAuditRecordStatus> userAuditRecordStatusListByMap(Map<String, Object> paramMap) {
		return userAuditRecordStatusDao.listBy(paramMap);
	}
}

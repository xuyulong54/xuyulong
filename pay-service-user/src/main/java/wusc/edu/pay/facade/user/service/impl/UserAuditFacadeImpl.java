package wusc.edu.pay.facade.user.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.biz.UserAuditBiz;
import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserAuditFacade;


@Component("userAuditFacade")
public class UserAuditFacadeImpl implements UserAuditFacade {

	@Autowired
	private UserAuditBiz userAuditBiz;

	public UserAuditRecordRealName getUserAuditRecordRealNameById(long id) {
		return userAuditBiz.getUserAuditRecordRealNameById(id);
	}

	public PageBean queryUserAuditRecordRealNameListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditBiz.queryUserAuditRecordRealNameListPage(pageParam, paramMap);
	}

	public UserAuditRecordClose getUserAuditRecordCloseById(long id) {
		return userAuditBiz.getUserAuditRecordCloseById(id);
	}

	public PageBean queryUserAuditRecordCloseListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditBiz.queryUserAuditRecordCloseListPage(pageParam, paramMap);
	}

	public UserAuditRecordStatus getUserAuditRecordStatusById(long id) {
		return userAuditBiz.getUserAuditRecordStatusById(id);
	}

	public PageBean queryUserAuditRecordStatusListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return userAuditBiz.queryUserAuditRecordStatusListPage(pageParam, paramMap);
	}

	/**
	 * 用户实名验证通过 .<br/>
	 * 1、更新实名验证记录表  .<br/>
	 * 2、更新用户表的CardNo、RealName、IsRealNameAuth .<br/>
	 * 3、更新会员表的RealName.<br/>
	 * 4、修改会员的账户状态 .<br/>
	 * 
	 * @param id 用户实名证认证审核记录ID.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 */
	public void auditPass_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {
		userAuditBiz.auditPass_RealName(id, auditOperatorLoginName, auditOperatorName, auditDesc);
	}

	/**
	 * 用户实名验证不通过.<br/>
	 * 1、更新实名验证记录表
	 * @param id 用户实名验认证审核记录ID .
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 */
	public void auditRefuse_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {
		userAuditBiz.auditRefuse_RealName(id, auditOperatorLoginName, auditOperatorName, auditDesc);
	}

	/**
	 * 用户状态审核通过.<br/>
	 * 1、修改用户状态变更记录 .<br/>
	 * 2、更新用户信息表的Status、IsRealName.<br/>
	 * 3、修改商户或者会员信息表的Status.<br/>
	 * 4、激活账户.<br/>
	 * 
	 * @param id 用户状态变更审核记录ID.
	 * @param userNo 用户编号.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 * @param changeStatus 申请变更的状态.
	 * @param currentStatus 当前状态.
	 */
	public void auditPass_Status(long id, String userNo, String auditOperatorLoginName, String auditOperatorName
			, String applyDesc, String auditDesc, Integer changeStatus, Integer currentStatus) {
		userAuditBiz.auditPass_Status(id, userNo, auditOperatorLoginName, auditOperatorName, applyDesc, auditDesc, changeStatus,
				currentStatus);
	}

	/**
	 * 用户状态审核不通过.<br/>
	 * 1、修改用户状态变更记录.<br/>
	 * 
	 * @param id 用户状态变更审核记录ID.
	 * @param userNo 用户编号.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 * @param changeStatus 申请变更的状态.
	 * @param currentStatus 当前状态.
	 */
	public void auditRefuse_Status(long id, String userNo, String auditOperatorLoginName, String auditOperatorName,
			String applyDesc, String auditDesc, Integer changeStatus, Integer currentStatus) {
		userAuditBiz.auditRefuse_Status(id, userNo, auditOperatorLoginName, auditOperatorName, applyDesc, auditDesc, changeStatus,
				currentStatus);
	}

	/**
	 * 用户销户的审核通过.<br/>
	 * 1、修改用户销户申请记录 .<br/>
	 * 2、修改商户、会员信息表的Status .<br/>
	 * 3、调用结算服务.<br/>
	 * 
	 * @param id 销户审核记录ID.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 * @param isLogin 是否可以登陆.
	 * @throws Exception
	 * @throws UserBizException
	 */
	public void auditPass_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc,
			Integer isLogin) throws AccountBizException, UserBizException, Exception {
		userAuditBiz.auditPass_Close(id, auditOperatorLoginName, auditOperatorName, auditDesc, isLogin);
	}

	/**
	 * 用户销户审核不通过.<br/>
	 * 1、修改用户销户申请记录
	 * 
	 * @param id 销户审核记录ID.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 * @throws UserBizException
	 */
	public void auditRefuse_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc) {
		userAuditBiz.auditRefuse_Close(id, auditOperatorLoginName, auditOperatorName, auditDesc);
	}

	/**
	 * 新增用户状态变更审核记录 .<br/>
	 * 1、判断是否有未完成的审核记录  .<br/>
	 * 2、判断用户是否存在  .<br/>
	 * 3、创建一条用户状态变更审核记录 .<br/>
	 * 
	 * @param userNo 用户编号.
	 * @param applyOperatorLoginName 申请人登录名.
	 * @param applyOperatorName 申请人真实姓名 .
	 * @param applyDesc 申请描述.
	 * @param changeStatus 申请变更的状态.
	 * @param currentStatus 当前状态.
	 */
	public long createUserAuditRecordStatus(String userNo, String applyOperatorLoginName, String applyOperatorName,
			String applyDesc, Integer changeStatus, Integer currentStatus) throws UserBizException {
		return userAuditBiz.createUserAuditRecordStatus(userNo, applyOperatorLoginName, applyOperatorName, applyDesc,
				changeStatus, currentStatus);
	}

	/**
	 * 增加用户实名验证审核记录.<br/>
	 * 1、判断用户是否存在 .<br/>
	 * 2、判断用户是否已实名验证  .<br/>
	 * 3、判断用户是否有未完成的记录  .<br/>
	 * 4、创建用户实名验证审核记录 .<br/>
	 * 
	 * @param cardNo 身份证号
	 * @param cardFrontPath 身份证正面
	 * @param cardBackPath 身份证反面
	 * @param cardNoValid 身份证到期日期
	 * @param userOperator 会员（操作员）
	 * @param realName 真实姓名
	 * @param country 国籍
	 * @param profession 职业
	 */
	public long createUserAuditRecordRealName(String cardNo, String cardFrontPath, String cardBackPath,
			Date cardNoValid, UserOperator userOperator, String realName, String country, String profession) {
		return userAuditBiz.createUserAuditRecordRealName(cardNo, cardFrontPath, cardBackPath, cardNoValid, userOperator,
				realName, country, profession);
	}

	/**
	 * 创建用户销户审核记录.<br/>
	 * 1、判断用户是否存在 .<br/>
	 * 2、判断用户是否有未完成的记录 .<br/>
	 * 3、增加用户销户审核记录 .<br/>
	 * 
	 * @param userNo 用户编号.
	 * @param applyOperatorLoginName 申请人登录名.
	 * @param applyOperatorName 申请人真实姓名.
	 * @param applyDesc 申请描述.
	 * @throws UserBizException
	 */
	public void createUserAuditRecordClose(String userNo, String applyOperatorLoginName, String applyOperatorName,
			String applyDesc) {
		userAuditBiz.createUserAuditRecordClose(userNo, applyOperatorLoginName, applyOperatorName, applyDesc);
	}

	/**
	 * 根据用户编号、审核状态查找实名验证申请记录.<br/>
	 * 
	 * @param userNo 用户编号.<br/>
	 * @param auditStatus 审核状态.<br/>
	 * @return userAuditRecordRealName 
	 */
	public UserAuditRecordRealName getByUserNo_auditStatus(String userNo, Integer auditStatus) {
		return userAuditBiz.getByUserNo_auditStatus(userNo, auditStatus);
	}

	public List<UserAuditRecordStatus> userAuditRecordStatusListByMap(Map<String, Object> paramMap) {
		return userAuditBiz.userAuditRecordStatusListByMap(paramMap);
	}

	/**
	 * 根据用户编号、审核状态查找用户状态变更审核记录.<br/>
	 * 
	 * @param userNo 用户编号.<br/>
	 * @param auditStatus 审核状态.<br/>
	 * @return userAuditRecordStatus.
	 */
	public UserAuditRecordStatus getUserAuditRecordStatusByUserNo_auditStatus(String userNo, Integer auditStatus) {
		return userAuditBiz.getUserAuditRecordStatusByUserNo_auditStatus(userNo, auditStatus);
	}

}

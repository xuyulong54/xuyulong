package wusc.edu.pay.facade.user.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.UserAuditRecordClose;
import wusc.edu.pay.facade.user.entity.UserAuditRecordRealName;
import wusc.edu.pay.facade.user.entity.UserAuditRecordStatus;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 
 * @描述: 用户审核(实名验证审核，销户审核，状态变更审核)接口.
 * @作者: Lanzy.
 * @创建时间: 2014-6-17, 上午11:30:53 .
 * @版本: V1.0.
 * 
 */
@Component("userAuditFacade")
public interface UserAuditFacade {

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
			Date cardNoValid, UserOperator userOperator, String realName, String country, String profession);

	/**
	 * 通过id查找用户实名验证审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordRealName getUserAuditRecordRealNameById(long id) throws UserBizException;

	/***
	 * 分页查询用户实名验证审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordRealNameListPage(PageParam pageParam, Map<String, Object> paramMap)
			throws UserBizException;

	/***
	 * 更加id查找用户销户审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordClose getUserAuditRecordCloseById(long id) throws UserBizException;

	/**
	 * 分页查询用户销户审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordCloseListPage(PageParam pageParam, Map<String, Object> paramMap)
			throws UserBizException;

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
			String applyDesc, Integer changeStatus, Integer currentStatus) throws UserBizException;

	/**
	 * 根据id查找用户状态审核记录
	 * 
	 * @param id
	 * @return
	 */
	public UserAuditRecordStatus getUserAuditRecordStatusById(long id) throws UserBizException;

	/**
	 * 分页查询用户状态审核记录
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryUserAuditRecordStatusListPage(PageParam pageParam, Map<String, Object> paramMap)
			throws UserBizException;

	/**
	 * 用户实名验证通过 .<br/>
	 * 1、更新实名验证记录表  .<br/>
	 * 2、更新用户表的CardNo、RealName、IsRealNameAuth .<br/>
	 * 3、更新会员表的RealName.<br/>
	 * 4、修改会员的账户状态 .<br/>
	 * 
	 * @param id 用户实名验认证审核记录ID.
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 */
	public void auditPass_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc)
			throws UserBizException;

	/**
	 * 用户实名验证不通过.<br/>
	 * 1、更新实名验证记录表
	 * @param id 用户实名验认证审核记录ID .
	 * @param auditOperatorLoginName 审核人登录名.
	 * @param auditOperatorName 审核人真实姓名.
	 * @param auditDesc 审核描述.
	 */
	public void auditRefuse_RealName(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc)
			throws UserBizException;

	/**
	 * 用户状态变更审核通过.<br/>
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
	public void auditPass_Status(long id, String userNo, String auditOperatorLoginName, String auditOperatorName,
			String applyDesc, String auditDesc, Integer changeStatus, Integer currentStatus) throws UserBizException;

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
			String applyDesc, String auditDesc, Integer changeStatus, Integer currentStatus)
			throws UserBizException;
	
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
			String applyDesc) throws UserBizException;

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
	 * @param isLogin 是否可以登录.
	 * @throws Exception
	 * @throws UserBizException
	 */
	public void auditPass_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc,
			Integer isLogin) throws UserBizException, Exception;

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
	public void auditRefuse_Close(long id, String auditOperatorLoginName, String auditOperatorName, String auditDesc)
			throws UserBizException;


	/**
	 * 根据用户编号、审核状态查找实名验证申请记录.<br/>
	 * 
	 * @param userNo 用户编号.<br/>
	 * @param auditStatus 审核状态.<br/>
	 * @return userAuditRecordRealName 
	 */
	public UserAuditRecordRealName getByUserNo_auditStatus(String userNo, Integer auditStatus);

	/**
	 * 根据用户编号、审核状态查找用户状态变更审核记录.<br/>
	 * 
	 * @param userNo 用户编号.<br/>
	 * @param auditStatus 审核状态.<br/>
	 * @return userAuditRecordStatus.
	 */
	public UserAuditRecordStatus getUserAuditRecordStatusByUserNo_auditStatus(String userNo, Integer auditStatus);

	/**
	 * 根据map参数查询UserAuditRecordStatus的数据
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<UserAuditRecordStatus> userAuditRecordStatusListByMap(Map<String, Object> paramMap);
}

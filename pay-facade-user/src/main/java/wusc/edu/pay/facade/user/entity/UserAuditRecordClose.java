package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/***
 * 
 * @描述: 用户注销审核表（实体）.
 * @作者: Lanzy.
 * @创建时间: 2014-6-16, 下午3:57:12 .
 * @版本: V1.0.
 * 
 */
public class UserAuditRecordClose extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3132434670663923671L;

	/** 用户编号 **/
	private String userNo;
	/** 公司全称 */
	private String fullName;
	/** 登录名 **/
	private String loginName;
	/** 用户类型：会员、在线商户、pos商户 **/
	private Integer userType;
	/** 审核状态：审核通过，审核不通过，未审核 **/
	private Integer auditStatus;
	/** 申请描述 **/
	private String applyDesc;
	/** 审核描述 **/
	private String auditDesc;
	/** 审核操作员的登陆名 **/
	private String auditOperatorLoginName;
	/** 什么操作员的名字 **/
	private String auditOperatorName;
	/** 处理时间 **/
	private Date dealTime;
	/** 申请操作员的登陆名 **/
	private String applyOperatorLoginName;
	/** 申请操作员的名字 **/
	private String applyOperatorName;
	
	private Integer isLogin; // 是否可以登陆，1-可以登陆，2-不可登陆
	
	private Integer timeInterval; // 时间间隔，如果大于7，商户审核列表才给审核按钮

	public Integer getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(Integer timeInterval) {
		this.timeInterval = timeInterval;
	}

	public Integer getIsLogin() {
		return isLogin;
	}

	public void setIsLogin(Integer isLogin) {
		this.isLogin = isLogin;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getApplyDesc() {
		return applyDesc;
	}

	public void setApplyDesc(String applyDesc) {
		this.applyDesc = applyDesc;
	}

	public String getAuditDesc() {
		return auditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		this.auditDesc = auditDesc;
	}

	public String getAuditOperatorLoginName() {
		return auditOperatorLoginName;
	}

	public void setAuditOperatorLoginName(String auditOperatorLoginName) {
		this.auditOperatorLoginName = auditOperatorLoginName;
	}

	public String getAuditOperatorName() {
		return auditOperatorName;
	}

	public void setAuditOperatorName(String auditOperatorName) {
		this.auditOperatorName = auditOperatorName;
	}

	public Date getDealTime() {
		return dealTime;
	}

	public void setDealTime(Date dealTime) {
		this.dealTime = dealTime;
	}

	public String getApplyOperatorLoginName() {
		return applyOperatorLoginName;
	}

	public void setApplyOperatorLoginName(String applyOperatorLoginName) {
		this.applyOperatorLoginName = applyOperatorLoginName;
	}

	public String getApplyOperatorName() {
		return applyOperatorName;
	}

	public void setApplyOperatorName(String applyOperatorName) {
		this.applyOperatorName = applyOperatorName;
	}

}

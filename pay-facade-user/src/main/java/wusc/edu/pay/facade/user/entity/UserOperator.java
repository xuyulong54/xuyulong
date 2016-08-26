package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 权限管理-用户操作员
 * 
 * @author WuShuicheng.
 * 
 */
public class UserOperator extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 用户编号(商户编号、会员编号)*/
	private String userNo; 
	/** 登录名 */
	private String loginName;
	/** 登录密码 */
	private String loginPwd;
	/** 姓名 */
	private String realName;
	/** 手机号 */
	private String mobileNo;
	/** 状态 */
	private Integer status;
	/** 操作员类型 */
	private Integer type;
	/** 是否更改过密码(0:否,1:是) **/
	private Integer isChangedPwd;
	/** 登录密码错误次数 **/
	private Integer pwdErrorTimes;
	/** 最后登录时间 **/
	private Date lastLoginTime;
	/** 最后一次登录密码错误时间 **/
	private Date pwdErrorLastTime;
	/** 最后一次修改时间 **/
	private Date lastAlertPwdTime;
	
	/**
	 * 用户编号(商户编号、会员编号)
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}
	/**
	 * 用户编号(商户编号、会员编号)
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 * 登录名
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * 登录名
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 *  登录密码
	 * @return
	 */
	public String getLoginPwd() {
		return loginPwd;
	}
	/**
	 *  登录密码
	 * @param loginPwd
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	/**
	 * 姓名
	 * @return
	 */
	public String getRealName() {
		return realName;
	}
	/**
	 * 姓名
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}
	/**
	 * 手机号 
	 * @return
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * 手机号 
	 * @param mobileNo
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * 状态
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}
	/**
	 * 状态
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}
	/**
	 * 操作员类型
	 * @return
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 操作员类型
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 是否更改过密码(0:否,1:是)
	 * @return
	 */
	public Integer getIsChangedPwd() {
		return isChangedPwd;
	}
	/**
	 * 是否更改过密码(0:否,1:是)
	 * @param isChangedPwd
	 */
	public void setIsChangedPwd(Integer isChangedPwd) {
		this.isChangedPwd = isChangedPwd;
	}
	/**
	 * 登录密码错误次数
	 * @return
	 */
	public Integer getPwdErrorTimes() {
		return pwdErrorTimes;
	}
	/**
	 * 登录密码错误次数
	 * @param pwdErrorTimes
	 */
	public void setPwdErrorTimes(Integer pwdErrorTimes) {
		this.pwdErrorTimes = pwdErrorTimes;
	}
	/**
	 * 最后登录时间
	 * @return
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	/**
	 * 最后登录时间
	 * @param lastLoginTime
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	/**
	 * 最后一次登录密码错误时间
	 * @return
	 */
	public Date getPwdErrorLastTime() {
		return pwdErrorLastTime;
	}
	/**
	 * 最后一次登录密码错误时间
	 * @param pwdErrorLastTime
	 */
	public void setPwdErrorLastTime(Date pwdErrorLastTime) {
		this.pwdErrorLastTime = pwdErrorLastTime;
	}
	/**
	 * 最后一次修改时间
	 * @return
	 */
	public Date getLastAlertPwdTime() {
		return lastAlertPwdTime;
	}
	/**
	 * 最后一次修改时间
	 * @param lastAlertPwdTime
	 */
	public void setLastAlertPwdTime(Date lastAlertPwdTime) {
		this.lastAlertPwdTime = lastAlertPwdTime;
	}
	
}

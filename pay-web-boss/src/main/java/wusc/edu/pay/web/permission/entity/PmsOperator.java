package wusc.edu.pay.web.permission.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 权限管理-操作员
 * 
 * @author xiehui
 * 
 */
public class PmsOperator extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String loginName;// 登录名
	private String loginPwd; // 登录密码
	private String remark; // 描述
	private String realName; // 姓名
	private String mobileNo; // 手机号
	private Integer status; // 状态
	private String type; // 操作员类型（1:超级管理员，0:普通操作员），超级管理员由系统初始化时添加，不能删除
	private Date lastLoginTime;// 最后登录时间
	private Boolean isChangedPwd;// 是否更改过密码
	private Integer pwdErrorCount; // 连续输错密码次数（连续5次输错就冻结帐号）
	private Date pwdErrorTime; // 最后输错密码的时间

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * 登录名
	 * 
	 * @return
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public String getLoginPwd() {
		return loginPwd;
	}

	/**
	 * 登录密码
	 * 
	 * @return
	 */
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 姓名
	 * 
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 姓名
	 * 
	 * @return
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * 手机号
	 * 
	 * @return
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * 状态
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态
	 * 
	 * @return
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 操作员类型
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 操作员类型
	 * 
	 * @return
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 最后登录时间
	 * 
	 * @return
	 */
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/**
	 * 最后登录时间
	 * 
	 * @return
	 */
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/**
	 * 是否更改过密码
	 * 
	 * @return
	 */
	public Boolean getIsChangedPwd() {
		return isChangedPwd;
	}

	/**
	 * 是否更改过密码
	 * 
	 * @return
	 */
	public void setIsChangedPwd(Boolean isChangedPwd) {
		this.isChangedPwd = isChangedPwd;
	}

	public Integer getPwdErrorCount() {
		return pwdErrorCount;
	}

	public void setPwdErrorCount(Integer pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}

	public Date getPwdErrorTime() {
		return pwdErrorTime;
	}

	public void setPwdErrorTime(Date pwdErrorTime) {
		this.pwdErrorTime = pwdErrorTime;
	}

	public PmsOperator() {

	}

}

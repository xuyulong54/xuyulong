package wusc.edu.pay.facade.pms.entity;

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
	
	/** 登录名 **/
	private String loginName;
	
	/** 登录密码 **/
	private String loginPwd;
	
	/** 备注（描述） **/
	private String remark;
	
	/** 姓名 **/
	private String realName;
	
	/** 手机号 **/
	private String mobileNo;
	
	/** 状态（100:可用,101:不可用，参考PublicStatusEnum） **/
	private Integer status;
	
	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	private String type;
	
	/** 最后登录时间 **/
	private Date lastLoginTime;
	
	/** 是否更改过密码 **/
	private Boolean isChangedPwd;
	
	/** 连续输错密码次数（如：连续5次输错就冻结帐号） **/
	private Integer pwdErrorCount;
	
	/** 最后输错密码的时间 **/
	private Date pwdErrorTime;
	
	/** 用户编号（可以是商户编号、代理商编号） **/
	private String userNo;
	
	
	
	public PmsOperator() {
	}
	
	

	/** 登录名 **/
	public String getLoginName() {
		return loginName;
	}

	/** 登录名 **/
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/** 登录密码 **/
	public String getLoginPwd() {
		return loginPwd;
	}

	/** 登录密码 **/
	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}
	
	/** 备注（描述） **/
	public String getRemark() {
		return remark;
	}

	/** 备注（描述） **/
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/** 姓名 **/
	public String getRealName() {
		return realName;
	}

	/** 姓名 **/
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/** 手机号 **/
	public String getMobileNo() {
		return mobileNo;
	}

	/** 手机号 **/
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/** 状态（100:可用,101:不可用，参考PublicStatusEnum） **/
	public Integer getStatus() {
		return status;
	}

	/** 状态（100:可用,101:不可用，参考PublicStatusEnum） **/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	public String getType() {
		return type;
	}

	/** 操作员类型（1:超级管理员,2:普通管理员,3:默认操作员,4:普通操作员，参考枚举PmsOperatorTypeEnum） **/
	public void setType(String type) {
		this.type = type;
	}

	/** 最后登录时间 **/
	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	/** 最后登录时间 **/
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	/** 是否更改过密码 **/
	public Boolean getIsChangedPwd() {
		return isChangedPwd;
	}

	/** 是否更改过密码 **/
	public void setIsChangedPwd(Boolean isChangedPwd) {
		this.isChangedPwd = isChangedPwd;
	}

	/** 连续输错密码次数（如：连续5次输错就冻结帐号） **/
	public Integer getPwdErrorCount() {
		return pwdErrorCount;
	}

	/** 连续输错密码次数（如：连续5次输错就冻结帐号） **/
	public void setPwdErrorCount(Integer pwdErrorCount) {
		this.pwdErrorCount = pwdErrorCount;
	}

	/** 最后输错密码的时间 **/
	public Date getPwdErrorTime() {
		return pwdErrorTime;
	}

	/** 最后输错密码的时间 **/
	public void setPwdErrorTime(Date pwdErrorTime) {
		this.pwdErrorTime = pwdErrorTime;
	}

	/** 用户编号（可以是商户编号、代理商编号） **/
	public String getUserNo() {
		return userNo;
	}

	/** 用户编号（可以是商户编号、代理商编号） **/
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	

}

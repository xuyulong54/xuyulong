package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 用户验证码
 * 
 * @author huqian.
 * 
 */
public class UserVerificationCode extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/** 用户编号(商户编号、会员编号)*/
	private String userNo; 
	/** 登录名 */
	private String loginName;
	/** 验证码类型  参考枚举UserVerificationCodeTypeEnum*/
	private Integer verificationType;
	/** 状态  参考枚举UserVerificationCodeStatusEnum*/
	private Integer status;
	/** 验证码 **/
	private String verificationCode;
	/** 验证码生成时间 **/
	private Date generateTime;
	/** 验证码到期时间 **/
	private Date maturityTime;
	/** 备注 **/
	private String remark;
	
	/**
	 * 用户编号
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}
	/**
	 * 用户编号
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	/**
	 *  登录名
	 * @return
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 *  登录名
	 * @param loginName
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * 验证码类型
	 * @return
	 */
	public Integer getVerificationType() {
		return verificationType;
	}
	/**
	 * 验证码类型
	 * @param verificationType
	 */
	public void setVerificationType(Integer verificationType) {
		this.verificationType = verificationType;
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
	 * 验证码 
	 * @return
	 */
	public String getVerificationCode() {
		return verificationCode;
	}
	/**
	 * 验证码 
	 * @param verificationCode
	 */
	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}
	/**
	 * 验证码生成时间
	 * @return
	 */
	public Date getGenerateTime() {
		return generateTime;
	}
	/**
	 * 验证码生成时间
	 * @param generateTime
	 */
	public void setGenerateTime(Date generateTime) {
		this.generateTime = generateTime;
	}
	/**
	 * 验证码到期时间
	 * @return
	 */
	public Date getMaturityTime() {
		return maturityTime;
	}
	/**
	 * 验证码到期时间
	 * @param maturityTime
	 */
	public void setMaturityTime(Date maturityTime) {
		this.maturityTime = maturityTime;
	}
	/**
	 * 备注
	 * @return
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 备注
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

}

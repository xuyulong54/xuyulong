package wusc.edu.pay.facade.boss.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 邮箱验证记录.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-29,下午3:03:12 .
 * @版本: 1.0 .
 */
public class EmailVerify extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private Date validTime; // 有效时间
	private String token; // 令牌（32位UUID值）
	private Integer type; // 类型 PublicTemplateEnum
	private Integer status; // 状态，100:已验证，101:未验证
	private Integer userType; // 用户类型（对应用户类型枚举值）
	private String userNo;// 用户编号
	/** 接收邮箱 **/
	private String toEmail;

	/**
	 * 有效时间
	 * 
	 * @return
	 */
	public Date getValidTime() {
		return validTime;
	}

	/**
	 * 有效时间
	 * 
	 * @param validTime
	 */
	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	/**
	 * 令牌（32位UUID值）
	 * 
	 * @return
	 */
	public String getToken() {
		return token;
	}

	/**
	 * 令牌（32位UUID值）
	 * 
	 * @param token
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * 类型
	 * 
	 * @return
	 */
	public Integer getType() {
		return type;
	}

	/**
	 * 类型
	 * 
	 * @param type
	 */
	public void setType(Integer type) {
		this.type = type;
	}

	/**
	 * 状态，100:已验证，101:未验证
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态，100:已验证，101:未验证
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 用户类型
	 * 
	 * @return
	 */
	public Integer getUserType() {
		return userType;
	}

	/**
	 * 用户类型
	 * 
	 * @param userType
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	/** 接收邮箱 **/
	public String getToEmail() {
		return toEmail;
	}

	/** 接收邮箱 **/
	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

}

package wusc.edu.pay.facade.user.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 会员信息实体.
 * @desc
 * @author shenjialong
 * @date 2013-9-27,上午11:28:07
 */
public class MemberInfo extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
	/** 会员编号 */
	private String memberNo;
	/** 账户编号 */
	private String accountNo;
	/** 状态(100:激活,101:冻结,102:已销户) */
	private Integer status;
	/** 真实姓名 */
	private String realName;
	private String cardNo; // 身份证号
	/** 联系地址 */
	private String address;
	/** 固定电话 */
	private String telNo;
	/** 传真号 */
	private String fax;
	/** QQ号 */
	private String qq;
	
	private String profession; // 职业
	
	private String country; // 国籍
	
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	/** 实名认证检查(非数据库映射字段，只用于传值) */
	private Integer realNameCheckStatus;
	
	

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}


	/**
	 * 会员编号
	 * @return
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * 会员编号
	 * @param memberNo
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	/**
	 * 状态(100:激活,101:冻结,102:已销户)
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 状态(100:激活,101:冻结,102:已销户)
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 真实姓名
	 * @return
	 */
	public String getRealName() {
		return realName;
	}

	/**
	 * 真实姓名
	 * @param realName
	 */
	public void setRealName(String realName) {
		this.realName = realName;
	}

	/**
	 * 联系地址
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 联系地址
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 固定电话
	 * @return
	 */
	public String getTelNo() {
		return telNo;
	}

	/**
	 * 固定电话
	 * @param telNo
	 */
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}

	/**
	 * 传真号
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 传真号
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 *  QQ号 
	 * @return
	 */
	public String getQq() {
		return qq;
	}

	/**
	 *  QQ号 
	 * @param qq
	 */
	public void setQq(String qq) {
		this.qq = qq;
	}

	/**
	 * 实名认证检查(非数据库映射字段，只用于传值)
	 * @return
	 */
	public Integer getRealNameCheckStatus() {
		return realNameCheckStatus;
	}

	/**
	 * 实名认证检查(非数据库映射字段，只用于传值)
	 * @param realNameCheckStatus
	 */
	public void setRealNameCheckStatus(Integer realNameCheckStatus) {
		this.realNameCheckStatus = realNameCheckStatus;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	
}

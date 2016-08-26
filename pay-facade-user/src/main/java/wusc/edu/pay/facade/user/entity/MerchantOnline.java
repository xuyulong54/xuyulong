package wusc.edu.pay.facade.user.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 
 * @描述: 在线商户信息.
 * @作者: WuShuicheng.
 * @创建: 2014-5-27,上午10:18:42
 * @版本: V1.0
 * 
 */
public class MerchantOnline extends BaseEntity {

	private static final long serialVersionUID = 1L;
	/** 商户编号 */
	private String merchantNo;
	/** 账户编号 */
	private String accountNo;
	/** 商户状态(激活:100, 冻结:101, 用户已创建:102, 审核不通过:103, 注册中:104, 已注销:105) */
	private Integer status;
	/** 商户类型(10:个人,11:个体工商,12:企业) */
	private Integer merchantType;
	/** 商户密钥 */
	private String merchantKey;
	/** 公司简称 */
	private String shortName;
	/** 公司全称 */
	private String fullName;
	/** 公司地址 */
	private String address;
	/** 公司网址 */
	private String url;
	/** ICP证备案号 */
	private String icp;
	/** 法人代表姓名 */
	private String legalPerson;
	/** 法人身份证号 */
	private String cardNo;
	/** 法人证件有效期 */
	private String certificateExpiry;
	/** 经营范围 */
	private String scope;
	/** 营业执照号 */
	private String licenseNo;
	/** 营业执照有效期 */
	private String licenseExpiry;
	/** 营业执照号到期日期 */
	private Date licenseNoValid;
	/** 组织机构代码 */
	private String orgCode;
	/** 税务登记证(国税) */
	private String nationalTax;
	/** 税务登记证(地税) */
	private String landTax;
	/** 签约时间 */
	private Date signTime;
	/** 合同到期日 */
	private Date contractValid;
	/** 业务联系人 */
	private String busiContactName;
	/** 业务联系人手机号码 */
	private String busiContactMobileNo;
	/** 业务联系人邮箱 */
	private String busiContactEmail;
	/** 业务联系人QQ */
	private String busiContactQq;
	/** 技术联系人 */
	private String techContactName;
	/** 技术联系人手机号 */
	private String techContactMobileNo;
	/** 技术联系人邮箱 */
	private String techContactEmail;
	/** 技术联系人QQ */
	private String techContactQq;
	/** 传真号 */
	private String fax;
	/** IP段 */
	private String ipSeg;
	/**
	 * 行业类型
	 */
	private String mcc;
	/**
	 * 用户类型(参考枚举:UserTypeEnum)
	 */
	private Integer userType;
	/**
	 * 注册资本(万元)
	 */
	private BigDecimal registerMoney;
	/**
	 * 是否开通预授权
	 */
	private Integer preauthorization;
	private BigDecimal deposit; // 保证金
	/*
	 * 机构编码（只用于展示，不用于数据库的存储）
	 */
	private String parentUserNo;

	/** 邮编 */
	private String zipCode;

	/** 固定电话 */
	private String telephoneNum;
	/** 经营面积 */
	private Integer businessArea;
	/** 员工数量 */
	private Integer empNum;

	/**
	 * 身份证地址
	 */
	private String cardnoAddress; 
	/**
	 * 营业执照开始日期
	 */
	private String licenseExpiryForm;

	/**
	 * 商户编号
	 * 
	 * @return
	 */
	public String getMerchantNo() {
		return merchantNo;
	}

	/**
	 * 商户编号
	 * 
	 * @param merchantNo
	 */
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	/**
	 * 商户状态(激活:100, 冻结:101, 用户已创建:102, 审核不通过:103, 注册中:104, 已注销:105)
	 * 
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 商户状态(激活:100, 冻结:101, 用户已创建:102, 审核不通过:103, 注册中:104, 已注销:105)
	 * 
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 商户类型(10:个人,11:个体工商,12:企业)
	 * 
	 * @return
	 */
	public Integer getMerchantType() {
		return merchantType;
	}

	/**
	 * 商户类型(10:个人,11:个体工商,12:企业)
	 * 
	 * @param merchantType
	 */
	public void setMerchantType(Integer merchantType) {
		this.merchantType = merchantType;
	}

	/**
	 * 商户密钥
	 * 
	 * @return
	 */
	public String getMerchantKey() {
		return merchantKey;
	}

	/**
	 * 商户密钥
	 * 
	 * @param merchantKey
	 */
	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	/**
	 * 公司简称
	 * 
	 * @return
	 */
	public String getShortName() {
		return shortName;
	}

	/**
	 * 公司简称
	 * 
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	/**
	 * 公司全称
	 * 
	 * @return
	 */
	public String getFullName() {
		return fullName;
	}

	/**
	 * 公司全称
	 * 
	 * @param fullName
	 */
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	/**
	 * 公司地址
	 * 
	 * @return
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 公司地址
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 公司网址
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 公司网址
	 * 
	 * @param url
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * ICP证备案号
	 * 
	 * @return
	 */
	public String getIcp() {
		return icp;
	}

	/**
	 * ICP证备案号
	 * 
	 * @param icp
	 */
	public void setIcp(String icp) {
		this.icp = icp;
	}

	/**
	 * 法人代表姓名
	 * 
	 * @return
	 */
	public String getLegalPerson() {
		return legalPerson;
	}

	/**
	 * 法人代表姓名
	 * 
	 * @param legalPerson
	 */
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}

	/**
	 * 法人身份证号
	 * 
	 * @return
	 */
	public String getCardNo() {
		return cardNo;
	}

	/**
	 * 法人身份证号
	 * 
	 * @param cardNo
	 */
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	/**
	 * 法人证件有效期
	 * 
	 * @return
	 */
	public String getCertificateExpiry() {
		return certificateExpiry;
	}

	/**
	 * 法人证件有效期
	 * 
	 * @param certificateExpiry
	 */
	public void setCertificateExpiry(String certificateExpiry) {
		this.certificateExpiry = certificateExpiry;
	}

	/**
	 * 经营范围
	 * 
	 * @return
	 */
	public String getScope() {
		return scope;
	}

	/**
	 * 经营范围
	 * 
	 * @param scope
	 */
	public void setScope(String scope) {
		this.scope = scope;
	}

	/**
	 * 营业执照号
	 * 
	 * @return
	 */
	public String getLicenseNo() {
		return licenseNo;
	}

	/**
	 * 营业执照号
	 * 
	 * @param licenseNo
	 */
	public void setLicenseNo(String licenseNo) {
		this.licenseNo = licenseNo;
	}

	/**
	 * 营业执照有效期
	 * 
	 * @return
	 */
	public String getLicenseExpiry() {
		return licenseExpiry;
	}

	/**
	 * 营业执照有效期
	 * 
	 * @param licenseExpiry
	 */
	public void setLicenseExpiry(String licenseExpiry) {
		this.licenseExpiry = licenseExpiry;
	}

	/**
	 * 营业执照号到期日期
	 * 
	 * @return
	 */
	public Date getLicenseNoValid() {
		return licenseNoValid;
	}

	/**
	 * 营业执照号到期日期
	 * 
	 * @param licenseNoValid
	 */
	public void setLicenseNoValid(Date licenseNoValid) {
		this.licenseNoValid = licenseNoValid;
	}

	/**
	 * 组织机构代码
	 * 
	 * @return
	 */
	public String getOrgCode() {
		return orgCode;
	}

	/**
	 * 组织机构代码
	 * 
	 * @param orgCode
	 */
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	/**
	 * 税务登记证(国税)
	 * 
	 * @return
	 */
	public String getNationalTax() {
		return nationalTax;
	}

	/**
	 * 税务登记证(国税)
	 * 
	 * @param nationalTax
	 */
	public void setNationalTax(String nationalTax) {
		this.nationalTax = nationalTax;
	}

	/**
	 * 税务登记证(地税)
	 * 
	 * @return
	 */
	public String getLandTax() {
		return landTax;
	}

	/**
	 * 税务登记证(地税)
	 * 
	 * @param landTax
	 */
	public void setLandTax(String landTax) {
		this.landTax = landTax;
	}

	/**
	 * 签约时间
	 * 
	 * @return
	 */
	public Date getSignTime() {
		return signTime;
	}

	/**
	 * 签约时间
	 * 
	 * @param signTime
	 */
	public void setSignTime(Date signTime) {
		this.signTime = signTime;
	}

	/**
	 * 合同到期日
	 * 
	 * @return
	 */
	public Date getContractValid() {
		return contractValid;
	}

	/**
	 * 合同到期日
	 * 
	 * @param contractValid
	 */
	public void setContractValid(Date contractValid) {
		this.contractValid = contractValid;
	}

	/**
	 * 业务联系人
	 * 
	 * @return
	 */
	public String getBusiContactName() {
		return busiContactName;
	}

	/**
	 * 业务联系人
	 * 
	 * @param busiContactName
	 */
	public void setBusiContactName(String busiContactName) {
		this.busiContactName = busiContactName;
	}

	/**
	 * 业务联系人手机号码
	 * 
	 * @return
	 */
	public String getBusiContactMobileNo() {
		return busiContactMobileNo;
	}

	/**
	 * 业务联系人手机号码
	 * 
	 * @param busiContactMobileNo
	 */
	public void setBusiContactMobileNo(String busiContactMobileNo) {
		this.busiContactMobileNo = busiContactMobileNo;
	}

	/**
	 * 业务联系人邮箱
	 * 
	 * @return
	 */
	public String getBusiContactEmail() {
		return busiContactEmail;
	}

	/**
	 * 业务联系人邮箱
	 * 
	 * @param busiContactEmail
	 */
	public void setBusiContactEmail(String busiContactEmail) {
		this.busiContactEmail = busiContactEmail;
	}

	/**
	 * 业务联系人QQ
	 * 
	 * @return
	 */
	public String getBusiContactQq() {
		return busiContactQq;
	}

	/**
	 * 业务联系人QQ
	 * 
	 * @param busiContactQq
	 */
	public void setBusiContactQq(String busiContactQq) {
		this.busiContactQq = busiContactQq;
	}

	/**
	 * 技术联系人
	 * 
	 * @return
	 */
	public String getTechContactName() {
		return techContactName;
	}

	/**
	 * 技术联系人
	 * 
	 * @param techContactName
	 */
	public void setTechContactName(String techContactName) {
		this.techContactName = techContactName;
	}

	/**
	 * 技术联系人手机号
	 * 
	 * @return
	 */
	public String getTechContactMobileNo() {
		return techContactMobileNo;
	}

	/**
	 * 技术联系人手机号
	 * 
	 * @param techContactMobileNo
	 */
	public void setTechContactMobileNo(String techContactMobileNo) {
		this.techContactMobileNo = techContactMobileNo;
	}

	/**
	 * 技术联系人邮箱
	 * 
	 * @return
	 */
	public String getTechContactEmail() {
		return techContactEmail;
	}

	/**
	 * 技术联系人邮箱
	 * 
	 * @param techContactEmail
	 */
	public void setTechContactEmail(String techContactEmail) {
		this.techContactEmail = techContactEmail;
	}

	/**
	 * 技术联系人QQ
	 * 
	 * @return
	 */
	public String getTechContactQq() {
		return techContactQq;
	}

	/**
	 * 技术联系人QQ
	 * 
	 * @param techContactQq
	 */
	public void setTechContactQq(String techContactQq) {
		this.techContactQq = techContactQq;
	}

	/**
	 * 传真号
	 * 
	 * @return
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * 传真号
	 * 
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * IP段
	 * 
	 * @return
	 */
	public String getIpSeg() {
		return ipSeg;
	}

	/**
	 * IP段
	 * 
	 * @param ipSeg
	 */
	public void setIpSeg(String ipSeg) {
		this.ipSeg = ipSeg;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public BigDecimal getRegisterMoney() {
		return registerMoney;
	}

	public void setRegisterMoney(BigDecimal registerMoney) {
		this.registerMoney = registerMoney;
	}

	public Integer getPreauthorization() {
		return preauthorization;
	}

	public void setPreauthorization(Integer preauthorization) {
		this.preauthorization = preauthorization;
	}

	public BigDecimal getDeposit() {
		return deposit;
	}

	public void setDeposit(BigDecimal deposit) {
		this.deposit = deposit;
	}

	public String getParentUserNo() {
		return parentUserNo;
	}

	public void setParentUserNo(String parentUserNo) {
		this.parentUserNo = parentUserNo;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getTelephoneNum() {
		return telephoneNum;
	}

	public void setTelephoneNum(String telephoneNum) {
		this.telephoneNum = telephoneNum;
	}

	public Integer getBusinessArea() {
		return businessArea;
	}

	public void setBusinessArea(Integer businessArea) {
		this.businessArea = businessArea;
	}

	public Integer getEmpNum() {
		return empNum;
	}

	public void setEmpNum(Integer empNum) {
		this.empNum = empNum;
	}

	public String getCardnoAddress() {
		return cardnoAddress;
	}

	public void setCardnoAddress(String cardnoAddress) {
		this.cardnoAddress = cardnoAddress;
	}

	public String getLicenseExpiryForm() {
		return licenseExpiryForm;
	}

	public void setLicenseExpiryForm(String licenseExpiryForm) {
		this.licenseExpiryForm = licenseExpiryForm;
	}

}

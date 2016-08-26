package wusc.edu.pay.facade.user.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.utils.string.StringUtil;


/***
 * 
 * @描述: 用户银行账户.
 * @作者: Lanzy.
 * @创建时间: 2014-6-11, 下午6:34:10 .
 * @版本: V1.0.
 * 
 */
public class UserBankAccount extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String loginName;// 用户登录名
	private String userNo;// 用户编号
	private String bankName;// 银行名称
	private String bankCode;// 银行编码
	private String bankAccountName;// 开户名称(持卡人)
	private String bankAccountNo;// 开户账号
	private String contractNo;// 协议号
	private Integer bankAccountType;// 银行卡类型
	private Integer isAuth;// 是否已验证(100:是,101:否,默认值为101)
	private Date authTime;// 验证时间
	private Integer cardType;// 证件类型
	private String cardNo;// 证件号
	private String mobileNo;// 手机号
	private Date lastTime = new Date();// 最后修改时间
	private Integer isDelete;// 是否已删除(100:是,101:否,默认值为101)
	private Integer isDefault;// 是否默认(100:是,101:否,默认值为101)
	private String province ;//开户行所在省份
	private String city;//城市
	private String bankAccountAddress;//开户行地址
	private String bankChannelNo;//银行行号
	private String remark;//描述
	
	public String getBankChannelNo() {
		return bankChannelNo;
	}

	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	public Integer getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	public Integer getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Integer isDefault) {
		this.isDefault = isDefault;
	}
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}


	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankAccountName() {
		return bankAccountName;
	}

	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}

	public String getBankAccountNo() {
		return bankAccountNo;
	}
	
	/**
	 * 中间使用*代替了的银行卡账号
	 * @return
	 */
	public String gettempBankAccountNo() {
		if(StringUtil.isBlank(bankAccountNo)){
			return null;
		}else{
			return StringUtil.replaceStringByChar(4,4,'*',bankAccountNo);
		}
	}
	
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public Integer getBankAccountType() {
		return bankAccountType;
	}

	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}


	public Date getAuthTime() {
		return authTime;
	}

	public void setAuthTime(Date authTime) {
		this.authTime = authTime;
	}

	public Integer getCardType() {
		return cardType;
	}

	public void setCardType(Integer cardType) {
		this.cardType = cardType;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBankAccountAddress() {
		return bankAccountAddress;
	}

	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress;
	}

}

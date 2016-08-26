package wusc.edu.pay.facade.settlement.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.settlement.enums.SettRecordStatusEnum;


/**
 * 结算记录
 * 
 * @author pc-Along
 * 
 */
public class SettRecord extends BaseEntity {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -507346932227359104L;

	/** 结算批次号 **/
	private String batchNo;

	/** 打款请求号 **/
	private String remitNo;

	/** 结算发起方式(参考 SettModeTypeEnum) **/
	private Integer settMode;

	/** 账户编号 **/
	private String accountNo;

	/**
	 * 用户类型
	 */
	private Integer userType;

	/** 用户编号 **/
	private String userNo;

	/** 用户姓名 **/
	private String userName;

	/** 结算日期 **/
	private Date settDate;

	/** 结算账户币种(参考CurrencyTypeEnum) **/
	private Integer currencyType;

	/** 银行编码 **/
	private String bankCode;

	/** 银行行号 **/
	private String bankChannelNo;

	/** 开户名 **/
	private String bankAccountName;

	/** 开户账户 **/
	private String bankAccountNo;

	/** 银行卡类型(参考枚举:BankAccountTypeEnum) **/
	private Integer bankAccountType;

	/** 开户行所在国家 **/
	private String country;

	/** 开户行所在省份 **/
	private String province;

	/** 开户行所在城市 **/
	private String city;

	/** 开户行全称 **/
	private String bankAccountAddress;

	/** 结算金额 **/
	private BigDecimal settAmount = BigDecimal.ZERO;

	/** 结算手续费 **/
	private BigDecimal settFee = BigDecimal.ZERO;

	/** 结算打款金额 **/
	private BigDecimal remitAmount = BigDecimal.ZERO;

	/** 交易笔数 **/
	private Integer tradeNum = 0;

	/** 交易金额 **/
	private BigDecimal tradeAmount = BigDecimal.ZERO;

	/** 交易手续费 **/
	private BigDecimal tradeFee = BigDecimal.ZERO;

	/** 退款笔数 **/
	private Integer refundNum = 0;

	/** 退款金额 **/
	private BigDecimal refundAmount = BigDecimal.ZERO;

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	private Integer settStatus;

	/** 退款手续费 **/
	private BigDecimal refundFee = BigDecimal.ZERO;

	/** 结算失败退回手续费处理方式(参考枚举:SettleReturnFeeTypeEnum) **/
	private Integer returnFeeType;

	/** 结算退回金额 **/
	private BigDecimal returnAmount = BigDecimal.ZERO;

	/** 结算开始日期 **/
	private Date beginDate;

	/** 结算截止日期 **/
	private Date endDate;

	/** 打款发送时间 **/
	private Date remitRequestTime;

	/** 打款确认时间 **/
	private Date remitConfirmTime;

	/** 描述 **/
	private String remark;

	/** 打款备注 **/
	private String remitRemark;

	/** 操作员登录名 **/
	private String operatorLoginname;

	/** 操作员姓名 **/
	private String operatorRealname;

	/***************** 传值 ******************/
	private List<FeeCalculateResultDTO> calculateResultDTOList;
	private List<SettDailyCollect> dailys;
	
	public List<SettDailyCollect> getDailys() {
		return dailys;
	}

	public void setDailys(List<SettDailyCollect> dailys) {
		this.dailys = dailys;
	}

	public List<FeeCalculateResultDTO> getCalculateResultDTOList() {
		return calculateResultDTOList;
	}
	
	public void setCalculateResultDTOList(List<FeeCalculateResultDTO> calculateResultDTOList) {
		this.calculateResultDTOList = calculateResultDTOList;
	}
	
	/************************************/

	/**
	 * 结算批次号
	 * 
	 * @return
	 */
	public String getBatchNo() {
		return batchNo;
	}

	/**
	 * 结算批次号
	 * 
	 * @param batchNo
	 */
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo == null ? null : batchNo.trim();
	}

	/**
	 * 打款请求号
	 * 
	 * @return
	 */
	public String getRemitNo() {
		return remitNo;
	}

	/**
	 * 打款请求号
	 * 
	 * @param remitNo
	 */
	public void setRemitNo(String remitNo) {
		this.remitNo = remitNo == null ? null : remitNo.trim();
	}

	/**
	 * 结算发起方式(参考SettModeTypeEnum)
	 * 
	 * @return
	 */
	public Integer getSettMode() {
		return settMode;
	}

	/**
	 * 结算发起方式(参考SettModeTypeEnum)
	 */
	public void setSettMode(Integer settMode) {
		this.settMode = settMode;
	}

	/**
	 * 账户编号
	 * 
	 * @return
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 账户编号
	 * 
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo == null ? null : accountNo.trim();
	}

	/**
	 * userType
	 * 
	 * @return the userType
	 * @since 1.0
	 */

	public Integer getUserType() {
		return userType;
	}

	/**
	 * @param userType
	 *            the userType to set
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	/**
	 * 用户编号
	 * 
	 * @return
	 */
	public String getUserNo() {
		return userNo;
	}

	/**
	 * 用户编号
	 * 
	 * @param userNo
	 */
	public void setUserNo(String userNo) {
		this.userNo = userNo == null ? null : userNo.trim();
	}

	/**
	 * 用户姓名
	 * 
	 * @return
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 用户姓名
	 * 
	 * @param userName
	 */
	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	/**
	 * 结算日期
	 * 
	 * @return
	 */
	public Date getSettDate() {
		return settDate;
	}

	/**
	 * 结算日期
	 * 
	 * @param settDate
	 */
	public void setSettDate(Date settDate) {
		this.settDate = settDate;
	}

	/**
	 * 结算账户币种(参考CurrencyTypeEnum)
	 * 
	 * @return
	 */
	public Integer getCurrencyType() {
		return currencyType;
	}

	/**
	 * 结算账户币种(参考CurrencyTypeEnum)
	 * 
	 * @param currencyType
	 */
	public void setCurrencyType(Integer currencyType) {
		this.currencyType = currencyType;
	}

	/**
	 * 银行编码
	 * 
	 * @return
	 */
	public String getBankCode() {
		return bankCode;
	}

	/** 银行行号 **/
	public String getBankChannelNo() {
		return bankChannelNo;
	}

	/** 银行行号 **/
	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}

	/**
	 * 银行编码
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode == null ? null : bankCode.trim();
	}

	/**
	 * 开户名
	 * 
	 * @return
	 */
	public String getBankAccountName() {
		return bankAccountName;
	}

	/**
	 * 开户名
	 * 
	 * @param bankAccountName
	 */
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName == null ? null : bankAccountName.trim();
	}

	/**
	 * 开户账户
	 * 
	 * @return
	 */
	public String getBankAccountNo() {
		return bankAccountNo;
	}

	/**
	 * 开户账户
	 * 
	 * @param bankAccountNo
	 */
	public void setBankAccountNo(String bankAccountNo) {
		this.bankAccountNo = bankAccountNo == null ? null : bankAccountNo.trim();
	}

	/**
	 * 银行卡类型(参考枚举:BankAccountTypeEnum)
	 * 
	 * @return
	 */
	public Integer getBankAccountType() {
		return bankAccountType;
	}

	/**
	 * 银行卡类型(参考枚举:BankAccountTypeEnum)
	 * 
	 * @param bankAccountType
	 */
	public void setBankAccountType(Integer bankAccountType) {
		this.bankAccountType = bankAccountType;
	}

	/**
	 * 开户行所在国家
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * 开户行所在国家
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country == null ? null : country.trim();
	}

	/**
	 * 开户行所在省份
	 * 
	 * @return
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * 开户行所在省份
	 * 
	 * @param province
	 */
	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	/**
	 * 开户行所在城市
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}

	/**
	 * 开户行所在城市
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	/**
	 * 开户行全称
	 * 
	 * @return
	 */
	public String getBankAccountAddress() {
		return bankAccountAddress;
	}

	/**
	 * 开户行全称
	 * 
	 * @param bankAccountAddress
	 */
	public void setBankAccountAddress(String bankAccountAddress) {
		this.bankAccountAddress = bankAccountAddress == null ? null : bankAccountAddress.trim();
	}

	/**
	 * 结算金额
	 * 
	 * @return
	 */
	public BigDecimal getSettAmount() {
		return settAmount;
	}

	/**
	 * 结算金额
	 * 
	 * @param settAmount
	 */
	public void setSettAmount(BigDecimal settAmount) {
		this.settAmount = settAmount;
	}

	/**
	 * 结算手续费
	 * 
	 * @return
	 */
	public BigDecimal getSettFee() {
		return settFee;
	}

	/**
	 * 结算手续费
	 * 
	 * @param settFee
	 */
	public void setSettFee(BigDecimal settFee) {
		this.settFee = settFee;
	}

	/**
	 * 结算打款金额
	 * 
	 * @return
	 */
	public BigDecimal getRemitAmount() {
		return remitAmount;
	}

	/**
	 * 结算打款金额
	 * 
	 * @param remitAmount
	 */
	public void setRemitAmount(BigDecimal remitAmount) {
		this.remitAmount = remitAmount;
	}

	/**
	 * 交易笔数
	 * 
	 * @return
	 */
	public Integer getTradeNum() {
		return tradeNum;
	}

	/**
	 * 交易笔数
	 * 
	 * @param tradeNum
	 */
	public void setTradeNum(Integer tradeNum) {
		this.tradeNum = tradeNum;
	}

	/**
	 * 交易金额
	 * 
	 * @return
	 */
	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	/**
	 * 交易金额
	 * 
	 * @param tradeAmount
	 */
	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	/**
	 * 交易手续费
	 * 
	 * @return
	 */
	public BigDecimal getTradeFee() {
		return tradeFee;
	}

	/**
	 * 交易手续费
	 * 
	 * @param tradeFee
	 */
	public void setTradeFee(BigDecimal tradeFee) {
		this.tradeFee = tradeFee;
	}

	/**
	 * 退款笔数
	 * 
	 * @return
	 */
	public Integer getRefundNum() {
		return refundNum;
	}

	/**
	 * 退款笔数
	 * 
	 * @param refundNum
	 */
	public void setRefundNum(Integer refundNum) {
		this.refundNum = refundNum;
	}

	/**
	 * 退款金额
	 * 
	 * @return
	 */
	public BigDecimal getRefundAmount() {
		return refundAmount;
	}

	/**
	 * 退款金额
	 * 
	 * @param refundAmount
	 */
	public void setRefundAmount(BigDecimal refundAmount) {
		this.refundAmount = refundAmount;
	}

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	public Integer getSettStatus() {
		return settStatus;
	}

	/** 结算状态(参考枚举:SettRecordStatusEnum) **/
	public void setSettStatus(Integer settStatus) {
		this.settStatus = settStatus;
	}

	/**
	 * 退款手续费
	 * 
	 * @return
	 */
	public BigDecimal getRefundFee() {
		return refundFee;
	}

	/**
	 * 退款手续费
	 * 
	 * @param refundFee
	 */
	public void setRefundFee(BigDecimal refundFee) {
		this.refundFee = refundFee;
	}

	/**
	 * 结算失败退回手续费处理方式(参考枚举:SettleReturnFeeTypeEnum)
	 * 
	 * @return
	 */
	public Integer getReturnFeeType() {
		return returnFeeType;
	}

	/**
	 * 结算失败退回手续费处理方式(参考枚举:SettleReturnFeeTypeEnum)
	 * 
	 * @param returnFeeType
	 */
	public void setReturnFeeType(Integer returnFeeType) {
		this.returnFeeType = returnFeeType;
	}

	/**
	 * 结算退回金额
	 * 
	 * @return
	 */
	public BigDecimal getReturnAmount() {
		return returnAmount;
	}

	/**
	 * 结算退回金额
	 * 
	 * @param returnAmount
	 */
	public void setReturnAmount(BigDecimal returnAmount) {
		this.returnAmount = returnAmount;
	}

	/**
	 * 结算开始日期
	 * 
	 * @return
	 */
	public Date getBeginDate() {
		return beginDate;
	}

	/**
	 * 结算开始日期
	 * 
	 * @param beginDate
	 */
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 结算截止日期
	 * 
	 * @return
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * 结算截止日期
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * 打款发送时间
	 * 
	 * @return
	 */
	public Date getRemitRequestTime() {
		return remitRequestTime;
	}

	/**
	 * 打款发送时间
	 * 
	 * @param remitRequestTime
	 */
	public void setRemitRequestTime(Date remitRequestTime) {
		this.remitRequestTime = remitRequestTime;
	}

	/**
	 * 打款确认时间
	 * 
	 * @return
	 */
	public Date getRemitConfirmTime() {
		return remitConfirmTime;
	}

	/**
	 * 打款确认时间
	 * 
	 * @param remitConfirmTime
	 */
	public void setRemitConfirmTime(Date remitConfirmTime) {
		this.remitConfirmTime = remitConfirmTime;
	}

	/**
	 * 描述
	 * 
	 * @return
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 描述
	 * 
	 * @param remark
	 */
	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	/**
	 * 打款备注
	 * 
	 * @return
	 */
	public String getRemitRemark() {
		return remitRemark;
	}

	/**
	 * 打款备注
	 * 
	 * @param remitRemark
	 */
	public void setRemitRemark(String remitRemark) {
		this.remitRemark = remitRemark == null ? null : remitRemark.trim();
	}

	/**
	 * 操作员登录名
	 * 
	 * @return
	 */
	public String getOperatorLoginname() {
		return operatorLoginname;
	}

	/**
	 * 操作员登录名
	 * 
	 * @param operatorLoginname
	 */
	public void setOperatorLoginname(String operatorLoginname) {
		this.operatorLoginname = operatorLoginname == null ? null : operatorLoginname.trim();
	}

	/**
	 * 操作员姓名
	 * 
	 * @return
	 */
	public String getOperatorRealname() {
		return operatorRealname;
	}

	/**
	 * 操作员姓名
	 * 
	 * @param operatorRealname
	 */
	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname == null ? null : operatorRealname.trim();
	}

	/**
	 * 增加交易总金额
	 * 
	 * @param amount
	 * @param count
	 */
	public void addTradeAmount(BigDecimal amount, Integer count) {
		this.settAmount = this.settAmount.add(amount);
		this.tradeAmount = this.tradeAmount.add(amount);
		this.tradeNum = this.tradeNum + count;
	}

	/**
	 * 是否能进行确认处理
	 */
	public boolean isCanConfirmProcess() {
		if (this.settStatus == SettRecordStatusEnum.WAIT_CONFIRM.getValue())
			return true;
		return false;
	}

	/**
	 * 是否能进行撤销处理
	 */
	public boolean isCanCancelProcess() {
		if (this.settStatus == SettRecordStatusEnum.WAIT_CONFIRM.getValue())
			return true;
		return false;
	}

	/**
	 * 是否能进行重新出款
	 */
	public boolean isCanResendRemit() {
		if (this.settStatus == SettRecordStatusEnum.FAIL_RETURN.getValue())
			return true;
		return false;
	}

	/**
	 * 是否能进行重新出款
	 */
	public boolean isCanConfirmStatus() {
		if (this.settStatus == SettRecordStatusEnum.WAIT_CONFIRM.getValue() || this.settStatus == SettRecordStatusEnum.CONFIRMED.getValue()
				|| this.settStatus == SettRecordStatusEnum.CANCEL.getValue()
				|| this.settStatus == SettRecordStatusEnum.CONFIRMED.getValue())
			return false;
		return true;
	}
}
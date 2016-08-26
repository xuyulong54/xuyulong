package wusc.edu.pay.facade.cost.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 计费成本订单实体
 * 
 */
public class CalCostOrder extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String merchantNo; // 商户编号

	private String merchantName; // 商户名称

	private String fromSystem; // 来源系统

	private Long feeWayId; // 计费约束ID

	private String calInterface; // 银行计费接口

	private String merchantOrderNo; // 商户订单号

	private String trxNo; // 交易流水号

	private String bankOrderNo; // 银行订单号

	private String calExpression; // 计算表达式

	private BigDecimal amount = BigDecimal.ZERO; // 交易金额

	private BigDecimal fee = BigDecimal.ZERO; // 费用

	private Date trxTime; // 交易时间

	private Integer costItem; // 交易类型

	private Integer status; // 状态

	private String failedReason; // 失败原因

	private Date calEndTime; // 计费完成时间

	private Integer calOrderType; // 订单类型

	private String mcc; // MCC码

	private String orgTrxNo; // 原支付流水号

	private CalFeeFlow feeFlow; // 计费流量

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
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	/**
	 * 商户名称
	 * 
	 * @return
	 */
	public String getMerchantName() {
		return merchantName;
	}

	/**
	 * 商户名称
	 * 
	 * @param merchantName
	 */
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	/**
	 * 来源系统
	 * 
	 * @return
	 */
	public String getFromSystem() {
		return fromSystem;
	}

	/**
	 * 来源系统
	 * 
	 * @param fromSystem
	 */
	public void setFromSystem(String fromSystem) {
		this.fromSystem = fromSystem == null ? null : fromSystem.trim();
	}

	/**
	 * 计费约束ID
	 * 
	 * @return
	 */
	public Long getFeeWayId() {
		return feeWayId;
	}

	/**
	 * 计费约束ID
	 * 
	 * @param feeWayId
	 */
	public void setFeeWayId(Long feeWayId) {
		this.feeWayId = feeWayId;
	}

	/**
	 * 银行计费接口
	 * 
	 * @return
	 */
	public String getCalInterface() {
		return calInterface;
	}

	/**
	 * 银行计费接口
	 * 
	 * @param calInterface
	 */
	public void setCalInterface(String calInterface) {
		this.calInterface = calInterface == null ? null : calInterface.trim();
	}

	/**
	 * 商户订单号
	 * 
	 * @return
	 */
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/**
	 * 商户订单号
	 * 
	 * @param merchantOrderNo
	 */
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo;
	}

	/**
	 * 交易流水号
	 * 
	 * @return
	 */
	public String getTrxNo() {
		return trxNo;
	}

	/**
	 * 交易流水号
	 * 
	 * @param trxNo
	 */
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	/**
	 * 银行订单号
	 * 
	 * @return
	 */
	public String getBankOrderNo() {
		return bankOrderNo;
	}

	/**
	 * 银行订单号
	 * 
	 * @param bankOrderNo
	 */
	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
	}

	/**
	 * 计算表达式
	 * 
	 * @return
	 */
	public String getCalExpression() {
		return calExpression;
	}

	/**
	 * 计算表达式
	 * 
	 * @param calExpression
	 */
	public void setCalExpression(String calExpression) {
		this.calExpression = calExpression == null ? null : calExpression.trim();
	}

	/**
	 * 交易金额
	 * 
	 * @return
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 交易金额
	 * 
	 * @param amount
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 费用
	 * 
	 * @return
	 */
	public BigDecimal getFee() {
		return fee;
	}

	/**
	 * 费用
	 * 
	 * @param fee
	 */
	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	/**
	 * 交易时间
	 * 
	 * @return
	 */
	public Date getTrxTime() {
		return trxTime;
	}

	/**
	 * 交易时间
	 * 
	 * @param trxTime
	 */
	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}

	/**
	 * 交易类型
	 * 
	 * @return
	 */
	public Integer getCostItem() {
		return costItem;
	}

	/**
	 * 交易类型
	 * 
	 * @param trxType
	 */
	public void setCostItem(Integer costItem) {
		this.costItem = costItem;
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
	 * @param status
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 失败原因
	 * 
	 * @return
	 */
	public String getFailedReason() {
		return failedReason;
	}

	/**
	 * 失败原因
	 * 
	 * @param failedReason
	 */
	public void setFailedReason(String failedReason) {
		this.failedReason = failedReason == null ? null : failedReason.trim();
	}

	/**
	 * 计费完成时间
	 * 
	 * @return
	 */
	public Date getCalEndTime() {
		return calEndTime;
	}

	/**
	 * 计费完成时间
	 * 
	 * @param calEndTime
	 */
	public void setCalEndTime(Date calEndTime) {
		this.calEndTime = calEndTime;
	}

	/**
	 * 订单类型
	 * 
	 * @return
	 */
	public Integer getCalOrderType() {
		return calOrderType;
	}

	/**
	 * 订单类型
	 * 
	 * @param calOrderType
	 */
	public void setCalOrderType(Integer calOrderType) {
		this.calOrderType = calOrderType;
	}

	/**
	 * MCC码
	 * 
	 * @return
	 */
	public String getMcc() {
		return mcc;
	}

	/**
	 * MCC码
	 * 
	 * @param mcc
	 */
	public void setMcc(String mcc) {
		this.mcc = mcc == null ? null : mcc.trim();
	}

	/**
	 * 原支付流水号
	 * 
	 * @return
	 */
	public String getOrgTrxNo() {
		return orgTrxNo;
	}

	/**
	 * 原支付流水号
	 * 
	 * @param orgTrxNo
	 */
	public void setOrgTrxNo(String orgTrxNo) {
		this.orgTrxNo = orgTrxNo;
	}

	/**
	 * 获取计费流量信息
	 * 
	 * @return
	 */
	public CalFeeFlow getFeeFlow() {
		return feeFlow;
	}

	/**
	 * 设置计费流量信息
	 * 
	 * @param feeFlow
	 */
	public void setFeeFlow(CalFeeFlow feeFlow) {
		this.feeFlow = feeFlow;
	}

}
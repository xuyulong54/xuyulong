package wusc.edu.pay.facade.trade.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 分账记录 TBL_TRADE_SPLIT_RECORD
 * 
 * @author chenjh
 * 
 */
public class SplitRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5316167469319565715L;

	/** 创建日期 **/
	private Date createDate;


	/** 最后修改时间 **/
	private Date modifyTime;

	/** 商户订单号 **/
	private String merchantOrderNo;

	/** 订单总金额 **/
	private BigDecimal orderAmount = BigDecimal.ZERO;

	/** 支付总金额 **/
	private BigDecimal payAmount = BigDecimal.ZERO;

	/** 商户编号 **/
	private String merchantNo;

	/** 商户名称 **/
	private String merchantName;

	/** 分账商户编号 **/
	private String splitMerchantNo;

	/** 分账商户名称 **/
	private String splitMerchantName;

	/** 分账商户登录名 **/
	private String splitLoginName;

	/** 分账金额 **/
	private BigDecimal splitAmount = BigDecimal.ZERO;

	/** 分账成功日期 **/
	private Date splitSuccessDate;

	/** 分账成功时间 **/
	private Date splitSuccessTime;

	/** 分账状态 SplitStatusEnum **/
	private Integer splitStatus;

	/** 交易类型 TrxModelEnum **/
	private Integer trxModel;

	/** 成功退款金额 **/
	private BigDecimal successRefundAmount = BigDecimal.ZERO;

	/** 是否退款 **/
	private Integer isRefund = 101;

	/** 创建日期 **/
	public Date getCreateDate() {
		return createDate;
	}

	/** 创建日期 **/
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/** 最后修改时间 **/
	public Date getModifyTime() {
		return modifyTime;
	}

	/** 最后修改时间 **/
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	/** 商户订单号 **/
	public String getMerchantOrderNo() {
		return merchantOrderNo;
	}

	/** 商户订单号 **/
	public void setMerchantOrderNo(String merchantOrderNo) {
		this.merchantOrderNo = merchantOrderNo == null ? null : merchantOrderNo.trim();
	}

	/** 商户编号 **/
	public String getMerchantNo() {
		return merchantNo;
	}

	/** 商户编号 **/
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo == null ? null : merchantNo.trim();
	}

	/** 商户名称 **/
	public String getMerchantName() {
		return merchantName;
	}

	/** 商户名称 **/
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName == null ? null : merchantName.trim();
	}

	public String getSplitMerchantNo() {
		return splitMerchantNo;
	}

	public void setSplitMerchantNo(String splitMerchantNo) {
		this.splitMerchantNo = splitMerchantNo;
	}

	public String getSplitMerchantName() {
		return splitMerchantName;
	}

	public void setSplitMerchantName(String splitMerchantName) {
		this.splitMerchantName = splitMerchantName;
	}

	public BigDecimal getSplitAmount() {
		return splitAmount;
	}

	public void setSplitAmount(BigDecimal splitAmount) {
		this.splitAmount = splitAmount;
	}

	public Date getSplitSuccessDate() {
		return splitSuccessDate;
	}

	public void setSplitSuccessDate(Date splitSuccessDate) {
		this.splitSuccessDate = splitSuccessDate;
	}

	public Date getSplitSuccessTime() {
		return splitSuccessTime;
	}

	public void setSplitSuccessTime(Date splitSuccessTime) {
		this.splitSuccessTime = splitSuccessTime;
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	public Integer getSplitStatus() {
		return splitStatus;
	}

	public void setSplitStatus(Integer splitStatus) {
		this.splitStatus = splitStatus;
	}

	public Integer getTrxModel() {
		return trxModel;
	}

	public void setTrxModel(Integer trxModel) {
		this.trxModel = trxModel;
	}

	public String getSplitLoginName() {
		return splitLoginName;
	}

	public void setSplitLoginName(String splitLoginName) {
		this.splitLoginName = splitLoginName;
	}

	public BigDecimal getSuccessRefundAmount() {
		return successRefundAmount;
	}

	public void setSuccessRefundAmount(BigDecimal successRefundAmount) {
		this.successRefundAmount = successRefundAmount;
	}

	public Integer getIsRefund() {
		return isRefund;
	}

	public void setIsRefund(Integer isRefund) {
		this.isRefund = isRefund;
	}

}

package wusc.edu.pay.facade.trade.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * @描述: 转账记录表（内部账户）.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-8,下午11:12:17 .
 * @版本: 1.0 .
 */
public class TransferRecord extends BaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2752860535462158800L;
	private String trxNo; // 交易流水号，生成规则延用交易记录表trxno生成函数
	private Double amount = 0D; // 转账金额
	private Double sourceRate = 0D; // 收款方手续费率
	private Double targetRate = 0D; // 付款方手续费率
	private String sourceUserNo; // 收款方用户编号
	private String targetUserNo; // 付款方用户编号
	private Double targetFee = 0D; // 付款方手续费
	private Double sourceFee = 0D; // 收款方手续费
	private Double platFee = 0D; // 平台收取的手续费
	private Integer status; // 交易状态，100:交易成功 101:交易失败 102:订单已创建 103:订单已取消
	private String remark; // 付款说明
	private String sourceMobileNo; // 收款人手机号，手工输入手机号,可选
	private String sourceLoginName;// 收款人登陆名
	private String sourceName;// 收款人姓名
	private String targetLoginName;// 付款人登陆名
	private String targetName;// 付款人姓名
	private Integer sourceUserType; // 收款方用户类型
	private Integer targetUserType; // 付款方用户类型

	/**
	 * 交易流水号
	 * 
	 * @return the trxNo
	 */
	public String getTrxNo() {
		return trxNo;
	}

	/**
	 * 交易流水号
	 * 
	 * @param trxNo
	 *            the trxNo to set
	 */
	public void setTrxNo(String trxNo) {
		this.trxNo = trxNo;
	}

	/**
	 * 转账金额
	 * 
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 转账金额
	 * 
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 收款方手续费率
	 * 
	 * @return the sourceReate
	 */
	public Double getSourceRate() {
		return sourceRate;
	}

	/**
	 * 收款方手续费率
	 * 
	 * @param sourceReate
	 *            the sourceReate to set
	 */
	public void setSourceRate(Double sourceRate) {
		this.sourceRate = sourceRate;
	}

	/**
	 * 付款方手续费率
	 * 
	 * @return the targetRate
	 */
	public Double getTargetRate() {
		return targetRate;
	}

	/**
	 * 付款方手续费率
	 * 
	 * @param targetRate
	 *            the targetRate to set
	 */
	public void setTargetRate(Double targetRate) {
		this.targetRate = targetRate;
	}

	public String getSourceUserNo() {
		return sourceUserNo;
	}

	public void setSourceUserNo(String sourceUserNo) {
		this.sourceUserNo = sourceUserNo;
	}

	public String getTargetUserNo() {
		return targetUserNo;
	}

	public void setTargetUserNo(String targetUserNo) {
		this.targetUserNo = targetUserNo;
	}

	/**
	 * 付款方手续费
	 * 
	 * @return the targetFee
	 */
	public Double getTargetFee() {
		return targetFee;
	}

	/**
	 * 付款方手续费
	 * 
	 * @param targetFee
	 *            the targetFee to set
	 */
	public void setTargetFee(Double targetFee) {
		this.targetFee = targetFee;
	}

	/**
	 * 收款方手续费
	 * 
	 * @return the sourceFee
	 */
	public Double getSourceFee() {
		return sourceFee;
	}

	/**
	 * 收款方手续费
	 * 
	 * @param sourceFee
	 *            the sourceFee to set
	 */
	public void setSourceFee(Double sourceFee) {
		this.sourceFee = sourceFee;
	}

	/**
	 * 平台收取的手续费
	 * 
	 * @return the platFee
	 */
	public Double getPlatFee() {
		return platFee;
	}

	/**
	 * 平台收取的手续费
	 * 
	 * @param platFee
	 *            the platFee to set
	 */
	public void setPlatFee(Double platFee) {
		this.platFee = platFee;
	}

	/**
	 * 交易状态
	 * 
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * 交易状态
	 * 
	 * @param status
	 *            the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 收款人手机号
	 * 
	 * @return the sourceMobileNo
	 */
	public String getSourceMobileNo() {
		return sourceMobileNo;
	}

	/**
	 * 收款人手机号
	 * 
	 * @param sourceMobileNo
	 *            the sourceMobileNo to set
	 */
	public void setSourceMobileNo(String sourceMobileNo) {
		this.sourceMobileNo = sourceMobileNo;
	}

	/**
	 * sourceLoginName.
	 * 
	 * @return the sourceLoginName 收款人登陆名
	 */
	public String getSourceLoginName() {
		return sourceLoginName;
	}

	/**
	 * @param sourceLoginName
	 *            the sourceLoginName to set
	 */
	public void setSourceLoginName(String sourceLoginName) {
		this.sourceLoginName = sourceLoginName;
	}

	/**
	 * sourceName.
	 * 
	 * @return the sourceName 收款人姓名
	 */
	public String getSourceName() {
		return sourceName;
	}

	/**
	 * @param sourceName
	 *            the sourceName to set
	 */
	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	/**
	 * targetLoginName.
	 * 
	 * @return the targetLoginName付款人登陆名
	 */
	public String getTargetLoginName() {
		return targetLoginName;
	}

	/**
	 * @param targetLoginName
	 *            the targetLoginName to set付款人登陆名
	 */
	public void setTargetLoginName(String targetLoginName) {
		this.targetLoginName = targetLoginName;
	}

	/**
	 * targetName.
	 * 
	 * @return the targetName 付款人姓名
	 */
	public String getTargetName() {
		return targetName;
	}

	/**
	 * @param targetName
	 *            the targetName to set 付款人姓名
	 */
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	/**
	 * 收款方用户类型
	 * 
	 * @return
	 */
	public Integer getSourceUserType() {
		return sourceUserType;
	}

	/**
	 * 收款方用户类型
	 * 
	 * @param sourceUserType
	 */
	public void setSourceUserType(Integer sourceUserType) {
		this.sourceUserType = sourceUserType;
	}

	/**
	 * 付款方用户类型
	 * 
	 * @return
	 */
	public Integer getTargetUserType() {
		return targetUserType;
	}

	/**
	 * 付款方用户类型
	 * 
	 * @param targetUserType
	 */
	public void setTargetUserType(Integer targetUserType) {
		this.targetUserType = targetUserType;
	}
}

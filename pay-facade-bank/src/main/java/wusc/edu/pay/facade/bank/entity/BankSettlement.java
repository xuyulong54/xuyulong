package wusc.edu.pay.facade.bank.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 
 * @描述: 银行结算信息参数实体.
 * @作者: WuShuicheng .
 * @创建时间: 2014-4-15, 下午5:54:03
 */
public class BankSettlement extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	
	/** 银行渠道编码  */
	private String bankChannelCode;
	
	/** 结算周期：T+X */
	private Integer settleCycle;
	
	/** 手续费账户：关联银行账户表 */
	private String chargeAccount;
	
	/** 保证金账户：关联银行账户表 */
	private String marginAccount;
	
	/** 手续费扣收方式：1：内扣、2：外扣 */
	private Integer chargeDeductWay;
	
	/** 手续费扣收周期：1：实时、2：包年 */
	private Integer chargeDeductCycle;
	
	/** 手续费支付方式：1：自动扣帐、2：人工转账 */
	private Integer chargePayWay;
	
	/** 退款方式：1：内扣、2：外扣 */
	private Integer refoundType;
	
	/** 退款扣收方式：1：接口、2：网银、3：传真、4：邮件、5：邮寄 */
	private Integer refoundDeductWay;
	
	/** 退款有效期：（X天内允许退款） */
	private Integer refoundValidity;
	
	/** 是否退回手续费：1：是 、 2：否 */
	private Integer isReturnCharge;
	
	/** 部分退款是否退回部分手续费：对于支持部分退款的情况 ：1：是 、 2 ：否 */
	private Integer isReturnPartFee;
	
	/** 退款到账时间（X天后到帐） */
	private Integer refundAccountTime;
	
	/** 退款限额 */
	private Double refundLimit;
	
	/** 是否非工作日到账 ，1:是，2:否 */
	private Integer isNonWorkdayAccount;
	
	/** 备注 */
	private String comments;
	

	/** 银行渠道名称(只用来显示用，对应数据库没有实际字段)*/
	private String bankChannelName;
	/** 银行渠道名称(只用来显示用，对应数据库没有实际字段)*/
	public String getBankChannelName() {
		return bankChannelName;
	}
	/** 银行渠道名称(只用来显示用，对应数据库没有实际字段)*/
	public void setBankChannelName(String bankChannelName) {
		this.bankChannelName = bankChannelName;
	}
	
	
	/**
	 * 结算周期：T+X
	 * @return
	 */
	public Integer getSettleCycle() {
		return settleCycle;
	}

	/**
	 * 结算周期：T+X
	 * @param settleCycle
	 */
	public void setSettleCycle(Integer settleCycle) {
		this.settleCycle = settleCycle;
	}

	/**
	 * 手续费账户：关联银行账户表
	 * @return
	 */
	public String getChargeAccount() {
		return chargeAccount;
	}

	/**
	 * 手续费账户：关联银行账户表
	 * @param chargeAccount
	 */
	public void setChargeAccount(String chargeAccount) {
		this.chargeAccount = chargeAccount;
	}

	/**
	 * 保证金账户：关联银行账户表
	 * @return
	 */
	public String getMarginAccount() {
		return marginAccount;
	}

	/**
	 * 保证金账户：关联银行账户表
	 * @param marginAccount
	 */
	public void setMarginAccount(String marginAccount) {
		this.marginAccount = marginAccount;
	}

	/**
	 * 手续费扣收方式：1：内扣、2：外扣 
	 * @return
	 */
	public Integer getChargeDeductWay() {
		return chargeDeductWay;
	}

	/**
	 * 手续费扣收方式：1：内扣、2：外扣 
	 * @param chargeDeductWay
	 */
	public void setChargeDeductWay(Integer chargeDeductWay) {
		this.chargeDeductWay = chargeDeductWay;
	}

	/**
	 * 手续费扣收周期：1：实时、2：包年 
	 * @return
	 */
	public Integer getChargeDeductCycle() {
		return chargeDeductCycle;
	}

	/**
	 * 手续费扣收周期：1：实时、2：包年 
	 * @param chargeDeductCycle
	 */
	public void setChargeDeductCycle(Integer chargeDeductCycle) {
		this.chargeDeductCycle = chargeDeductCycle;
	}

	/**
	 * 手续费支付方式：1：自动扣帐、2：人工转账
	 * @return
	 */
	public Integer getChargePayWay() {
		return chargePayWay;
	}

	/**
	 * 手续费支付方式：1：自动扣帐、2：人工转账
	 * @param chargePayWay
	 */
	public void setChargePayWay(Integer chargePayWay) {
		this.chargePayWay = chargePayWay;
	}

	/**
	 * 退款方式：1：内扣、2：外扣
	 * @return
	 */
	public Integer getRefoundType() {
		return refoundType;
	}

	/**
	 * 退款方式：1：内扣、2：外扣
	 * @param refoundType
	 */
	public void setRefoundType(Integer refoundType) {
		this.refoundType = refoundType;
	}

	/**
	 * 退款扣收方式：1：接口、2：网银、3：传真、4：邮件、5：邮寄
	 * @return
	 */
	public Integer getRefoundDeductWay() {
		return refoundDeductWay;
	}

	/**
	 * 退款扣收方式：1：接口、2：网银、3：传真、4：邮件、5：邮寄
	 * @param refoundDeductWay
	 */
	public void setRefoundDeductWay(Integer refoundDeductWay) {
		this.refoundDeductWay = refoundDeductWay;
	}

	/**
	 * 退款有效期：（X天内允许退款）
	 * @return
	 */
	public Integer getRefoundValidity() {
		return refoundValidity;
	}

	/**
	 * 退款有效期：（X天内允许退款）
	 * @param refoundValidity
	 */
	public void setRefoundValidity(Integer refoundValidity) {
		this.refoundValidity = refoundValidity;
	}

	/**
	 * 是否退回手续费：1：是 、 2：否
	 * @return
	 */
	public Integer getIsReturnCharge() {
		return isReturnCharge;
	}

	/**
	 * 是否退回手续费：1：是 、 2：否
	 * @param isReturnCharge
	 */
	public void setIsReturnCharge(Integer isReturnCharge) {
		this.isReturnCharge = isReturnCharge;
	}

	/**
	 * 部分退款是否退回部分手续费：对于支持部分退款的情况 ：1：是 、 2 ：否
	 * @return
	 */
	public Integer getIsReturnPartFee() {
		return isReturnPartFee;
	}

	/**
	 * 部分退款是否退回部分手续费：对于支持部分退款的情况 ：1：是 、 2 ：否
	 * @param isReturnPartFee
	 */
	public void setIsReturnPartFee(Integer isReturnPartFee) {
		this.isReturnPartFee = isReturnPartFee;
	}

	/**
	 * 退款到账时间（X天后到帐）
	 * @return
	 */
	public Integer getRefundAccountTime() {
		return refundAccountTime;
	}

	/**
	 * 退款到账时间（X天后到帐）
	 * @param refundAccountTime
	 */
	public void setRefundAccountTime(Integer refundAccountTime) {
		this.refundAccountTime = refundAccountTime;
	}

	/**
	 * 退款限额
	 * @return
	 */
	public Double getRefundLimit() {
		return refundLimit;
	}

	/**
	 * 退款限额
	 * @param refundLimit
	 */
	public void setRefundLimit(Double refundLimit) {
		this.refundLimit = refundLimit;
	}

	/**
	 * 是否非工作日到账 ，1:是，2:否
	 * @return
	 */
	public Integer getIsNonWorkdayAccount() {
		return isNonWorkdayAccount;
	}

	/**
	 * 是否非工作日到账 ，1:是，2:否
	 * @param isNonWorkdayAccount
	 */
	public void setIsNonWorkdayAccount(Integer isNonWorkdayAccount) {
		this.isNonWorkdayAccount = isNonWorkdayAccount;
	}

	/**
	 * 备注
	 * @return
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * 备注
	 * @param comments
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * 银行渠道编码
	 * @return
	 */
	public String getBankChannelCode() {
		return bankChannelCode;
	}

	/**
	 * 银行渠道编码
	 * @param bankChannelCode
	 */
	public void setBankChannelCode(String bankChannelCode) {
		this.bankChannelCode = bankChannelCode;
	}
	
}

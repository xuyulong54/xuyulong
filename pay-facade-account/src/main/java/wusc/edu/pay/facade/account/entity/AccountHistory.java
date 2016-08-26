package wusc.edu.pay.facade.account.entity;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;


/**
 * 账户历史实体
 * 
 * @author Hill
 * 
 */
public class AccountHistory extends BaseEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 请求号
	 */
	private String requestNo;
	/**
	 * 账户编号
	 */
	private String accountNo;
	/**
	 * 变动金额
	 */
	private Double amount = 0D;
	/**
	 * 手续费
	 */
	private Double fee = 0D;
	/**
	 * 余额
	 */
	private Double balance = 0D;
	/**
	 * 描述
	 */
	private String remark;
	/**
	 * 资金变动方向
	 */
	private Integer fundDirection;
	/**
	 * 是否允许结算
	 */
	private Integer isAllowSett;
	/**
	 * 是否完成结算
	 */
	private Integer isCompleteSett;
	/**
	 * 交易类型（枚举AccountTradeTypeEnum）
	 */
	private Integer trxType;

	/**
	 * 风险预存期天数
	 */
	private Integer riskDay;

	/**
	 * 余额
	 * 
	 * @return
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * 余额
	 * 
	 * @return
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
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
	 * @return
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 变动金额
	 * 
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}

	/**
	 * 变动金额
	 * 
	 * @return
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/**
	 * 手续费
	 * 
	 * @return
	 */
	public Double getFee() {
		return fee;
	}

	/**
	 * 手续费
	 * 
	 * @return
	 */
	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getIsAllowSett() {
		return isAllowSett;
	}

	public void setIsAllowSett(Integer isAllowSett) {
		this.isAllowSett = isAllowSett;
	}

	public Integer getIsCompleteSett() {
		return isCompleteSett;
	}

	public void setIsCompleteSett(Integer isCompleteSett) {
		this.isCompleteSett = isCompleteSett;
	}

	/**
	 * 资金变动方向
	 * 
	 * @return
	 */

	public Integer getFundDirection() {
		return fundDirection;
	}

	/**
	 * 资金变动方向
	 * 
	 * @return
	 */
	public void setFundDirection(Integer fundDirection) {
		this.fundDirection = fundDirection;
	}

	/**
	 * 是否允许结算
	 * 
	 * @return
	 */
	public Integer isAllowSett() {
		return isAllowSett;
	}

	/**
	 * 是否允许结算
	 * 
	 * @return
	 */
	public void setAllowSett(Integer isAllowSett) {
		this.isAllowSett = isAllowSett;
	}

	/**
	 * 是否完成结算
	 * 
	 * @return
	 */
	public Integer isCompleteSett() {
		return isCompleteSett;
	}

	/**
	 * 是否完成结算
	 * 
	 * @return
	 */
	public void setCompleteSett(Integer isCompleteSett) {
		this.isCompleteSett = isCompleteSett;
	}

	/**
	 * 交易类型
	 * 
	 * @return
	 */
	public Integer getTrxType() {
		return trxType;
	}

	/**
	 * 交易类型
	 * 
	 * @return
	 */
	public void setTrxType(Integer trxType) {
		this.trxType = trxType;
	}

	/**
	 * 请求号
	 * 
	 * @return
	 */
	public String getRequestNo() {
		return requestNo;
	}

	/**
	 * 请求号
	 * 
	 * @return
	 */
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	/**
	 * 交易类型中文描述
	 * 
	 * @return
	 */
	public String getFormatTrxTypeEnumDesc() {
		try {
			return AccountTradeTypeEnum.getEnum(this.trxType).getDesc();
		} catch (Exception e) {
			return "";
		}
	}

	public Integer getRiskDay() {
		return riskDay;
	}

	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}

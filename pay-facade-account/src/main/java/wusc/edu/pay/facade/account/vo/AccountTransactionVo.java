package wusc.edu.pay.facade.account.vo;

import java.io.Serializable;

import wusc.edu.pay.facade.account.enums.AccountFundDirectionEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;


/**
 * 交易命令参数vo
 * 
 * @author Administrator
 * 
 */
public class AccountTransactionVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2451289258390618916L;

	/**
	 * 资金方向
	 */
	private AccountFundDirectionEnum accountFundDirection;

	/**
	 * 交易码
	 */
	private AccountTradeTypeEnum tradeType;

	/**
	 * 用户编号
	 */
	private String userNo;

	/**
	 * 手续费
	 */
	private double fee = 0D;

	/**
	 * 发生金额
	 */
	private double amount = 0D;

	/**
	 * 冻结金额，可不填
	 */
	private double frezonAmount = 0D;

	/**
	 * 解冻金额，可不填
	 */
	private double unFrezonAmount = 0D;

	/**
	 * 业务请求标识号,
	 */
	private String requestNo;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 风险预存期天数
	 */
	private Integer riskDay;

	public AccountFundDirectionEnum getAccountFundDirection() {
		return accountFundDirection;
	}

	public void setAccountFundDirection(AccountFundDirectionEnum accountFundDirection) {
		this.accountFundDirection = accountFundDirection;
	}

	public AccountTradeTypeEnum getTradeType() {
		return tradeType;
	}

	public void setTradeType(AccountTradeTypeEnum tradeType) {
		this.tradeType = tradeType;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public double getFee() {
		return fee;
	}

	public void setFee(double fee) {
		this.fee = fee;
	}

	/**
	 * 解冻金额
	 */
	public double getUnFrezonAmount() {
		return unFrezonAmount;
	}

	/**
	 * 解冻金额，可不填
	 */
	public void setUnFrezonAmount(double unFrezonAmount) {
		this.unFrezonAmount = unFrezonAmount;
	}

	/**
	 * 冻结金额
	 */
	public double getFrezonAmount() {
		return frezonAmount;
	}

	/**
	 * 冻结金额，可不填
	 */
	public void setFrezonAmount(double frezonAmount) {
		this.frezonAmount = frezonAmount;
	}

	/**
	 * 风险预存期天数
	 */
	public Integer getRiskDay() {
		return riskDay;
	}

	/**
	 * 风险预存期天数，可不填
	 */
	public void setRiskDay(Integer riskDay) {
		this.riskDay = riskDay;
	}

}

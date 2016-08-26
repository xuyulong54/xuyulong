package wusc.edu.pay.facade.settlement.DTO;

import java.io.Serializable;
import java.util.Date;

import wusc.edu.pay.facade.settlement.entity.SettRule;


/**
 * @title 发起结算参数的 值对象
 * @company 中益智正(gzzyzz)
 * @author jialong.shen
 * @since 2014-8-13
 */
public class LaunchSettleVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 结算账户
	 */
	private String accountNo;

	/**
	 * 结算银行账户信息
	 */
	private UserBankAccountVo bankAccount;

	/**
	 * 发起结算的当前日期
	 */
	private Date currentDate;

	/**
	 * 结算截至日期
	 */
	private Date settleEndDate;

	/**
	 * 结算类型
	 */
	private Integer settType;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 结算规则
	 */
	private SettRule settRule;

	/**
	 * 临时结算规则
	 */
	private TempSettleRuleParamsDTO tempSettleRule;

	/**
	 * 操作员登录名
	 */
	private String operatorLoginname;

	/**
	 * 操作人名称
	 */
	private String operatorRealname;

	public LaunchSettleVo() {
		currentDate = new Date();
	}

	public Date getCurrentDate() {
		return currentDate;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public SettRule getSettRule() {
		return settRule;
	}

	public void setSettRule(SettRule settRule) {
		this.settRule = settRule;
	}

	public UserBankAccountVo getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(UserBankAccountVo bankAccount) {
		this.bankAccount = bankAccount;
	}

	public Date getSettleEndDate() {
		return settleEndDate;
	}

	public void setSettleEndDate(Date settleEndDate) {
		this.settleEndDate = settleEndDate;
	}

	public TempSettleRuleParamsDTO getTempSettleRule() {
		return tempSettleRule;
	}

	public void setTempSettleRule(TempSettleRuleParamsDTO tempSettleRule) {
		this.tempSettleRule = tempSettleRule;
	}

	public Integer getSettType() {
		return settType;
	}

	public void setSettType(Integer settType) {
		this.settType = settType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOperatorLoginname() {
		return operatorLoginname;
	}

	public void setOperatorLoginname(String operatorLoginname) {
		this.operatorLoginname = operatorLoginname;
	}

	public String getOperatorRealname() {
		return operatorRealname;
	}

	public void setOperatorRealname(String operatorRealname) {
		this.operatorRealname = operatorRealname;
	}

}

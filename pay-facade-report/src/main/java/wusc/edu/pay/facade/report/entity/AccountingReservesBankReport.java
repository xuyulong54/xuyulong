package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * @描述: .备付金银行帐户统计.
 * @作者: Liliqiong.
 * @创建时间: 2014-4-28, 下午4:28:12 .
 * @版本: V1.0.
 * 
 */
public class AccountingReservesBankReport extends BaseEntity {
	private static final long serialVersionUID = 7321551922742899533L;
	private String openBankName;// 备付金银行账户名称
	private String accountNature; // 账户性质(1:收付 2:汇缴)
	private String registDetailType; // 明细类型
	private Date journalDate; // 入账日期
	private Double lastBalance;// 上期余额
	private Double changeAmount; // 发生额
	private Double balance; // 余额
	private Date acountDate;// 统计日期
	private long bankjnRegistId;//银行流水登记ID
	

	public long getBankjnRegistId() {
		return bankjnRegistId;
	}

	public void setBankjnRegistId(long bankjnRegistId) {
		this.bankjnRegistId = bankjnRegistId;
	}


	public String getOpenBankName() {
		return openBankName;
	}

	public void setOpenBankName(String openBankName) {
		this.openBankName = openBankName;
	}

	public String getAccountNature() {
		return accountNature;
	}

	public void setAccountNature(String accountNature) {
		this.accountNature = accountNature;
	}

	public String getRegistDetailType() {
		return registDetailType;
	}

	public void setRegistDetailType(String registDetailType) {
		this.registDetailType = registDetailType;
	}

	public Date getJournalDate() {
		return journalDate;
	}

	public void setJournalDate(Date journalDate) {
		this.journalDate = journalDate;
	}

	public Double getLastBalance() {
		return lastBalance;
	}

	public void setLastBalance(Double lastBalance) {
		this.lastBalance = lastBalance;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Date getAcountDate() {
		return acountDate;
	}

	public void setAcountDate(Date acountDate) {
		this.acountDate = acountDate;
	}
}

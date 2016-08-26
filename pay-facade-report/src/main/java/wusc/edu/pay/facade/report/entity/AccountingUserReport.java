package wusc.edu.pay.facade.report.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * @描述: 客户账户统计.
 * @作者: Liliqiong.
 * @创建时间: 2014-4-28, 下午3:09:26 .
 * @版本: V1.0.
 * 
 */
public class AccountingUserReport extends BaseEntity {
	private static final long serialVersionUID = 5992555196857977935L;
	private Integer userType;// 用户类型（0：商户 1：会员）
	private String loginName;// 登陆名
	private String showName;// 会员真实姓名，商户签约名
	private Double accountBalance;// 账户余额
	private Double availableAmount;// 可用金额
	private Double notAvailableAmount;// 不可用金额
	private Double canTransferAmount;// 平台可转出金额
	private Double outToPlayAmount;// 平台转出待打款金额
	private Double transferredOutAmount;// 累计已转出金额
	private Date acountDate;// 统计日期


	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getShowName() {
		return showName;
	}

	public void setShowName(String showName) {
		this.showName = showName;
	}

	public Double getAccountBalance() {
		return accountBalance;
	}

	public void setAccountBalance(Double accountBalance) {
		this.accountBalance = accountBalance;
	}

	public Double getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(Double availableAmount) {
		this.availableAmount = availableAmount;
	}

	public Double getNotAvailableAmount() {
		return notAvailableAmount;
	}

	public void setNotAvailableAmount(Double notAvailableAmount) {
		this.notAvailableAmount = notAvailableAmount;
	}

	public Double getCanTransferAmount() {
		return canTransferAmount;
	}

	public void setCanTransferAmount(Double canTransferAmount) {
		this.canTransferAmount = canTransferAmount;
	}

	public Double getOutToPlayAmount() {
		return outToPlayAmount;
	}

	public void setOutToPlayAmount(Double outToPlayAmount) {
		this.outToPlayAmount = outToPlayAmount;
	}

	public Double getTransferredOutAmount() {
		return transferredOutAmount;
	}

	public void setTransferredOutAmount(Double transferredOutAmount) {
		this.transferredOutAmount = transferredOutAmount;
	}

	public Date getAcountDate() {
		return acountDate;
	}

	public void setAcountDate(Date acountDate) {
		this.acountDate = acountDate;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}
}

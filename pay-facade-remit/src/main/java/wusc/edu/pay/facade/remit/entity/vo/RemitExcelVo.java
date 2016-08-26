/**
 * className：RemitExcelVo.java
 * @version：1.0
 * date: 2014-9-19-下午5:01:23
 * Copyright (c)  2014中益智正公司-版权所有
 */
package wusc.edu.pay.facade.remit.entity.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * className：RemitExcelVo
 * Function：对 账 时，来自Excel的数据封装实体 VO
 * date: 2014-9-19-下午5:01:23
 * @author laich 
 */
public class RemitExcelVo implements Serializable {
	
	private static final long serialVersionUID = 2134513066697816811L;
	
	private Date requestTime ;
	/**打款流水号*/
	private String requestNo ;
	/**银行流水号**/
	private String orderId ;
	/**交易结果*/
	private String result ;
	/**失改原因*/
	private String failDesc ;
	private String amount ;
	/**发卡行行号*/
	private String bankChannelNo ;
	/**发卡行名称*/
	private String bankName ;
	/**收款户名*/
	private String accountName ;
	/**收款账号 收款人银行卡号*/
	private String accountNo ;
	/**操作员*/
	private String operator; 
	/**交易结果 布尔值 初始值为false 上面 result 是字符 */
	private boolean isSuccess = false ;
	/**付款帐号*/
	private String srcAccountNum;
	
	public Date getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	public String getRequestNo() {
		return requestNo;
	}
	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getFailDesc() {
		return failDesc;
	}
	public void setFailDesc(String failDesc) {
		this.failDesc = failDesc;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBankChannelNo() {
		return bankChannelNo;
	}
	public void setBankChannelNo(String bankChannelNo) {
		this.bankChannelNo = bankChannelNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	};
	/**
	 * @return 付款帐号
	 */
	public String getSrcAccountNum() {
		return srcAccountNum;
	}
	public void setSrcAccountNum(String srcAccountNum) {
		this.srcAccountNum = srcAccountNum;
	}
	
}

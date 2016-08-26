/**
 * wusc.edu.pay.facade.remit.entity.RemitError.java
 */
package wusc.edu.pay.facade.remit.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * <ul>
 * <li>Title:差错处理</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-8-6
 */
public class RemitError extends BaseEntity {
	private static final long serialVersionUID = 1L;
	/**
	 * 差错类型 1-长款(多交易) 2-短款(少交易)
	 */
	private Integer type;
	/**
	 * 打款请求号
	 */
	private String requestNo;

	/**
	 * 银行订单号
	 */
	private String bankOrderNo;
	/**
	 * 收款户名
	 */
	private String accountName;

	/**
	 * 收款帐户
	 */
	private String accountNo;

	/**
	 * 开户行行号
	 */
	private String bankChannelNo;
	/**
	 * 开户行名称
	 */
	private String bankName;
	/**
	 * 状态 '101-待处理,100-已处理'
	 */
	private Integer status;
	/**
	 * 收款金额
	 */
	private BigDecimal amount;
	/**
	 * 用户编号
	 */
	private String userNo;
	/**
	 * 创建人
	 */
	private String creator;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 银行备注信息
	 */
	private String bankRemark;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getRequestNo() {
		return requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getBankOrderNo() {
		return bankOrderNo;
	}

	public void setBankOrderNo(String bankOrderNo) {
		this.bankOrderNo = bankOrderNo;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBankRemark() {
		return bankRemark;
	}

	public void setBankRemark(String bankRemark) {
		this.bankRemark = bankRemark;
	}
}

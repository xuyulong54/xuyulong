package wusc.edu.pay.facade.payrule.entity;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.common.enums.BankCode;

/**
 * 支付渠道
 * @author huqian
 *
 */

public class Frp extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String frpCode; // 支付渠道编号
	private String bankName; // 银行名称
	private BankCode bankCode; // 银行编号
	private Integer status; // 状态
	private String busType;// 业务类型，B2C,B2B，快捷
	private Integer payType; //付款类型

	private Long	payWayId;
	
	public String getBusType() {
		return busType;
	}

	public void setBusType(String busType) {
		this.busType = busType;
	}
	
	/**
	 * 支付渠道编号
	 * @return
	 */
	public String getFrpCode() {
		return frpCode;
	}
	
	/**
	 * 支付渠道编号
	 * @return
	 */
	public void setFrpCode(String frpCode) {
		this.frpCode = frpCode;
	}
	
	/**
	 * 银行名称
	 * @return
	 */
	public String getBankName() {
		return bankName;
	}
	
	/**
	 * 银行名称
	 * @return
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	/**
	 * 银行编号
	 * @return
	 */
	public BankCode getBankCode() {
		return bankCode;
	}
	
	/**
	 * 银行编号
	 * @return
	 */
	public void setBankCode(BankCode bankCode) {
		this.bankCode = bankCode;
	}
	
	/**
	 * 状态
	 * @return
	 */
	
	public Integer getStatus() {
		return status;
	}
	
	/**
	 * 状态
	 * @return
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	
	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Long getPayWayId() {
		return payWayId;
	}

	public void setPayWayId(Long payWayId) {
		this.payWayId = payWayId;
	}
	
}

package wusc.edu.pay.facade.limit.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CumulateTradeAmountParamDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6568997456130931154L;
	
	private String bizFunction;//业务功能
	
	private String payProduct;//支付产品
	
	private String payWay;//支付方式
	
	private String cardType;//支付卡种
	
	private BigDecimal tradeAmount;//交易金额
	
	private String merchantNo;//商户编号
	
	private Date orderDate;//订单时间

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getBizFunction() {
		return bizFunction;
	}

	public void setBizFunction(String bizFunction) {
		this.bizFunction = bizFunction;
	}

	public String getPayProduct() {
		return payProduct;
	}

	public void setPayProduct(String payProduct) {
		this.payProduct = payProduct;
	}

	public String getPayWay() {
		return payWay;
	}

	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public BigDecimal getTradeAmount() {
		return tradeAmount;
	}

	public void setTradeAmount(BigDecimal tradeAmount) {
		this.tradeAmount = tradeAmount;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
}

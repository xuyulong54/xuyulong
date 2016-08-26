package wusc.edu.pay.core.limit.biz;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 过滤条件参数
 * 
 * @author：zh
 */
public class AmountLimitCondition {

	/**
	 * 业务功能
	 */
	private String bizFunction;

	/**
	 * 支付产品
	 */
	private String payProduct;

	/**
	 * 支付方式
	 */
	private String payWay;

	/**
	 * 支付卡种
	 */
	private String cardType;

	public AmountLimitCondition() {
		super();
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}

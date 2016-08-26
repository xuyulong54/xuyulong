/**
 * wusc.edu.pay.facade.limit.params.AmountLimit.java
 */
package wusc.edu.pay.facade.limit.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.limit.enums.CardKindEnum;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;


/**
 * 
 * 
 * <ul>
 * <li>Title: 交易金额限制实体</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-8
 */
public class AmountLimit extends BaseEntity {

	private static final long serialVersionUID = 2665882700573107698L;

	/**
	 * 金额限制ID
	 * 
	 * <pre>
	 * 关联“金额限制实体类”
	 * </pre>
	 */
	private Long amountLimitPackId;

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

	/**
	 * 限制类型
	 */
	private LimitTypeEnum limitType;

	/**
	 * 最小金额
	 */
	private BigDecimal minAmount;

	/**
	 * 最大金额
	 */
	private BigDecimal maxAmount;

	/**
	 * 自定义限制表达式
	 */
	private String expression;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime;


	public String getCardTypeDesc() {
		
		return (this.getCardType() == null || "".equals(this.getCardType())) ? "" : CardKindEnum.getEnum(new Integer(this.getCardType())).getDesc();
	}

	public String getLimitTypeDesc() {
		return this.getLimitType() == null ? "" : this.getLimitType().getLabel();
	}

	public String getBizFunctionDesc() {
		return this.getBizFunction() == null ? "" : LimitTrxTypeEnum.valueOf(this.getBizFunction()).getDesc();
	}

	public Long getAmountLimitPackId() {
		return amountLimitPackId;
	}

	public void setAmountLimitPackId(Long amountLimitPackId) {
		this.amountLimitPackId = amountLimitPackId;
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

	public LimitTypeEnum getLimitType() {
		return limitType;
	}

	public void setLimitType(LimitTypeEnum limitType) {
		this.limitType = limitType;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(Date lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

}

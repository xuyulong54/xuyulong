package wusc.edu.pay.core.limit.util;

import java.math.BigDecimal;

import wusc.edu.pay.facade.limit.entity.AmountLimit;



/**
 * 交易金额限制验证器
 * 
 * @author：zh
 */
public interface AmountLimitValidator {

	/**
	 * 验证
	 * 
	 * @param amountLimitEntity
	 *            交易金额限制规则实体
	 * @param tradeAmount
	 *            交易金额
	 */
	public void validate(String merchantNo,AmountLimit amountLimit, BigDecimal tradeAmount);
}

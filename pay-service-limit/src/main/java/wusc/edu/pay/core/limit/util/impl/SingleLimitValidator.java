package wusc.edu.pay.core.limit.util.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.util.AmountLimitValidator;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.exptions.AmountOverLimitException;
import wusc.edu.pay.facade.limit.exptions.SwitchLimitException;



/**
 * 交易金额限制类型为“单笔”的交易限制验证器
 * 
 * @author：zh
 */
@Component(value = "singleLimitValidator")
public class SingleLimitValidator implements AmountLimitValidator {

	@Override
	public void validate(String merchantNo, AmountLimit amountLimit, BigDecimal tradeAmount) {
		if (amountLimit.getMinAmount() != null) {
			// 如果交易金额小于单笔最小金额，超额
			if (tradeAmount.compareTo(amountLimit.getMinAmount())<0) {
				DecimalFormat df = new DecimalFormat("#.00");
				throw new SwitchLimitException(AmountOverLimitException.SINGLE_LESS_THAN_MIN, 
						"交易金额小于单笔最小金额[交易金额="
								+ tradeAmount + "; 最小金额="
								+ df.format(amountLimit.getMinAmount())
								+ "]");
			}
		}
		if (amountLimit.getMaxAmount() != null) {
			// 如果交易金额大于单笔最大金额，超额
			if (tradeAmount.compareTo(amountLimit.getMaxAmount())>0) {
				DecimalFormat df = new DecimalFormat("#.00");
				throw new SwitchLimitException(AmountOverLimitException.SINGLE_LARGER_THAN_MAX, 
						"交易金额大于单笔最大金额[交易金额="
								+ tradeAmount + "; 最大金额="
								+ df.format(amountLimit.getMaxAmount())
								+ "]");
			}
		}
	}

}

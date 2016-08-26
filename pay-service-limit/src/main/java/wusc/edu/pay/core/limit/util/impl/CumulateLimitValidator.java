package wusc.edu.pay.core.limit.util.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.dao.AmountCumulateDao;
import wusc.edu.pay.core.limit.util.AmountLimitValidator;
import wusc.edu.pay.facade.limit.entity.AmountCumulate;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;
import wusc.edu.pay.facade.limit.exptions.AmountOverLimitException;
import wusc.edu.pay.facade.limit.exptions.SwitchLimitException;



/**
 * 交易金额限制类型为“累计”（日累计，月累计等）的交易限制验证器
 * 
 * @author：zh
 */
@Component(value = "cumulateLimitValidator")
public class CumulateLimitValidator implements AmountLimitValidator {

	@Autowired
	private AmountCumulateDao amountCumulateDao;

	@Override
	public void validate(String merchantNo,AmountLimit amountLimit, BigDecimal tradeAmount) {
		// 获取交易金额累计记录
		AmountCumulate amountCumulate = amountCumulateDao
				.getAmountCumulateByLimitIdAndDateAndMerchantNo(merchantNo,amountLimit.getId(), new Date());

		if (amountCumulate == null) {
			amountCumulate = new AmountCumulate();
			amountCumulate
					.setCumulateAmount(new BigDecimal(0));
		}

		// 判断是否超限
		this.judge(amountLimit, tradeAmount, amountCumulate);
	}

	/**
	 * 判断是否超限
	 */
	private void judge(AmountLimit amountLimit, BigDecimal tradeAmount,
			AmountCumulate tradeAmountCumulate) {
		BigDecimal simulateAmount = tradeAmount.add(tradeAmountCumulate
				.getCumulateAmount());
		
		if (simulateAmount.compareTo(amountLimit.getMaxAmount())>0){
			DecimalFormat df = new DecimalFormat("#.00");
			if (LimitTypeEnum.CUMULATE_DAILY.equals(amountLimit
					.getLimitType())) {
				
				throw new SwitchLimitException(AmountOverLimitException.CUMULATE_DAILY_LARGER_THAN_MAX, 
						"累计超限[累计类型limitType="
						+ amountLimit.getLimitType()
						+ "; 当前累计cumulateAmount="
						+ df.format(tradeAmountCumulate.getCumulateAmount())
						+ "; 交易金额tradeAmount=" + tradeAmount
						+ "; 最大累计maxAmount="
						+ df.format(amountLimit.getMaxAmount()) + "]");
				
			} else if (LimitTypeEnum.CUMULATE_PER_MONTH
					.equals(amountLimit.getLimitType())) {
				
				throw new SwitchLimitException(AmountOverLimitException.CUMULATE_PER_MONTH_LARGER_THAN_MAX, 
						"累计超限[累计类型limitType="
						+ amountLimit.getLimitType()
						+ "; 当前累计cumulateAmount="
						+ df.format(tradeAmountCumulate.getCumulateAmount())
						+ "; 交易金额tradeAmount=" + tradeAmount
						+ "; 最大累计maxAmount="
						+ df.format(amountLimit.getMaxAmount()) + "]");
			}
		}
	}

}

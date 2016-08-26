package wusc.edu.pay.core.limit.periodstrategy.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.periodstrategy.CreateCumulatePeriod;
import wusc.edu.pay.core.limit.periodstrategy.Period;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;


/**
 * 生成累计周期策略
 * 
 * @author：zh
 */
@Component(value = "createPeriodStrategy")
public class CreatePeriodStrategy {

	/**
	 * 生成累计周期
	 * 
	 * @param limitType
	 *            限制类型
	 * @param date
	 *            累计周期所在时间段内日期
	 * @return
	 */
	public Period createPeriod(LimitTypeEnum limitType, Date date) {
		// 选择策略生成器：日累计，月累计...
		CreateCumulatePeriod createCumulatePeriod = chooseCreator(limitType);
		if (createCumulatePeriod != null) {
			return createCumulatePeriod.createPeriod(date);
		}
		throw new RuntimeException("该限制类型没有找到与之匹配的策略生成器[limitType=" + limitType
				+ "]");
	}

	/**
	 * 选择策略生成器
	 */
	private CreateCumulatePeriod chooseCreator(LimitTypeEnum limitType) {
		CreateCumulatePeriod createCumulatePeriod = null;
		if (LimitTypeEnum.CUMULATE_DAILY.equals(limitType)) {
			createCumulatePeriod = new CreateCumulateDailyPeriod();
		} else if (LimitTypeEnum.CUMULATE_PER_MONTH.equals(limitType)) {
			createCumulatePeriod = new CreateCumulatePerMonthPeriod();
		}
		return createCumulatePeriod;
	}

}

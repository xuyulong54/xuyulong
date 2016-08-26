package wusc.edu.pay.core.limit.periodstrategy.impl;

import java.util.Date;

import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.limit.periodstrategy.CreateCumulatePeriod;
import wusc.edu.pay.core.limit.periodstrategy.Period;



/**
 * 日累计周期生成器
 * 
 * @author：zh
 */
@Component(value = "createCumulateDailyPeriod")
public class CreateCumulateDailyPeriod implements CreateCumulatePeriod {

	@Override
	public Period createPeriod(Date date) {
		Date dayStart = DateUtils.getDayStart(date);
		Date dayEnd = DateUtils.getDayEnd(date);
		return new Period(dayStart, dayEnd);
	}

}

package wusc.edu.pay.core.limit.periodstrategy.impl;
import java.util.Date;

import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.limit.periodstrategy.CreateCumulatePeriod;
import wusc.edu.pay.core.limit.periodstrategy.Period;



/**
 * 月累计周期生成器
 * 
 * @author：zh
 */
@Component(value = "createCumulatePerMonthPeriod")
public class CreateCumulatePerMonthPeriod implements CreateCumulatePeriod {

	@Override
	public Period createPeriod(Date date) {
		Date firstDateOfMonth = DateUtils.getFirstDateOfMonth(date);
		Date lastDateOfMonth = DateUtils.getLastDateOfMonth(date);
		Date dayStart = DateUtils.getDayStart(firstDateOfMonth);
		Date dayEnd = DateUtils.getDayEnd(lastDateOfMonth);
		return new Period(dayStart, dayEnd);
	}

}

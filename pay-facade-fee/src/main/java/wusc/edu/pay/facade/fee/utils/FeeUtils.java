package wusc.edu.pay.facade.fee.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.enums.LadderCycleTypeEnum;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;


/**
 * 计算util
 * 
 * @desc
 * @author shenjialong
 * @date 2014-7-1,下午3:01:34
 */
public class FeeUtils {

	/**
	 * 流量累加时间区间计算
	 * 
	 * @param integer
	 *            阶梯周期类型（周，月，季度，自定义）
	 * @param integer2
	 *            自定义周期类型(月 周)
	 * @param day
	 *            自定义周期日
	 * @param transferDate
	 *            当前时间
	 * @return
	 */
	public static Map<String, Date> findAccTimeInterval(Integer ladderCycleType, Integer customizeCycleType, String day, Date transferDate) {
		Map<String, Date> date = new HashMap<String, Date>();
		Date beginDate = null;
		Date endDate = null;

		if (ladderCycleType == LadderCycleTypeEnum.YEAR.getValue()) {
			// 年
			beginDate = DateUtils.getYearStart(transferDate);
			endDate = DateUtils.getYearEnd(transferDate);
		} else if (ladderCycleType == LadderCycleTypeEnum.QUARTER.getValue()) {
			// 季度
			beginDate = DateUtils.getSeasonStart(transferDate);
			endDate = DateUtils.getSeasonEnd(transferDate);
		} else if (ladderCycleType == LadderCycleTypeEnum.MONTH.getValue()) {
			// 月
			beginDate = DateUtils.getMonthStart(transferDate);
			endDate = DateUtils.getMonthEnd(transferDate);
		} else if (ladderCycleType == LadderCycleTypeEnum.WEEK.getValue()) {
			// 周
			beginDate = DateUtils.getWeekStart(transferDate);
			endDate = DateUtils.getWeekEnd(transferDate);
		} else if (ladderCycleType.equals(LadderCycleTypeEnum.CUSTOMIZE.getValue())) {
			// 自定义周期
			if (customizeCycleType == null) {
				throw new FeeBizException(FeeBizException.CAL_CYCLE_DATE_ERROR, "未设置自定义周期的起始日期");
			}
			if (StringUtil.isBlank(day)) {
				throw new FeeBizException(FeeBizException.CAL_CYCLE_DATE_ERROR, "未设置自定义周期的大小");
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(transferDate);
			int customizeDay = Integer.parseInt(day);// 自定义周期日

			// 自定义月
			if (customizeCycleType.equals(LadderCycleTypeEnum.MONTH.getValue())) {
				calendar.set(Calendar.DAY_OF_MONTH, customizeDay);
				if (transferDate.before(FeeDateUtils.getDayStart(calendar.getTime()))) {
					endDate = FeeDateUtils.getDayEnd(calendar.getTime());
				} else {
					calendar.add(Calendar.MONTH, +1);
					endDate = FeeDateUtils.getDayEnd(calendar.getTime());

				}
				calendar.add(Calendar.MONTH, -1);
				beginDate = FeeDateUtils.getDayStart(calendar.getTime());
			}

			// 自定义周
			else if (customizeCycleType.equals(LadderCycleTypeEnum.WEEK.getValue())) {
				calendar.set(Calendar.DAY_OF_WEEK, customizeDay);
				if (transferDate.before(FeeDateUtils.getDayStart(calendar.getTime()))) {
					endDate = FeeDateUtils.getDayEnd(calendar.getTime());
				} else {
					calendar.add(Calendar.WEEK_OF_MONTH, +1);
					endDate = FeeDateUtils.getDayEnd(calendar.getTime());
				}
				calendar.add(Calendar.WEEK_OF_MONTH, -1);
				beginDate = FeeDateUtils.getDayStart(calendar.getTime());
			}
		}

		date.put("start", beginDate);
		date.put("end", endDate);
		return date;
	}

	/**
	 * 手续费的分摊 feeRole 承担角色
	 */
	public static void distributionFee(Integer feeRole, FeeCalculateResultDTO calculateResult) {
		// 承担方为收款方
		if (feeRole == FeeRoleTypeEnum.PAYEE.getValue()) {
			calculateResult.setPayeeFee(calculateResult.getPayFee());
		}
		// 承担方为付款方
		else {
			calculateResult.setPayerFee(calculateResult.getPayFee());
		}
	}
}

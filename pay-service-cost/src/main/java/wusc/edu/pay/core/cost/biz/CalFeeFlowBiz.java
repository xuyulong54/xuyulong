package wusc.edu.pay.core.cost.biz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.cost.dao.CalFeeFlowDao;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.BillingCycleEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 计费流量表业务实现类 .
 * @作者: 李安国 .
 * @创建时间: 2014-4-15, 下午2:28:52
 */
@Component("calFeeFlowBiz")
public class CalFeeFlowBiz extends BaseBizImpl<CalFeeFlow> {

	@Autowired
	private CalFeeFlowDao calFeeFlowDao;/**
	
	 * log4j日志记录
	 */
	private Log logger = LogFactory.getLog(CalCostOrderBiz.class);
	/**
	 * 日期比较
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	protected BaseDao<CalFeeFlow> getDao() {
		return calFeeFlowDao;
	}
	
	/**
	 * 根据费率约束ID获取费率流量信息
	 * @param feeWayId 费率约束ID
	 * @return
	 */
	public List<CalFeeFlow> getNumOfDataByFeeWayId(long feeWayId) {
		return calFeeFlowDao.getNumOfDataByFeeWayId(feeWayId);
	}

	/**
	 * 获取特定日期所在的周期的截止日期
	 * @param calFeeWay
	 * @param date
	 * @return
	 */
	public Date fetchCycleEndDate(CalFeeWay calFeeWay, Date date) throws CostBizException{
		int cycleType = calFeeWay.getCycleType().intValue();
		if(cycleType == BillingCycleEnum.YEAR.getValue()){
			//年
			return DateUtils.getYearEnd(date);
		}else if(cycleType == BillingCycleEnum.QUARTER.getValue()){
			//季度
			return DateUtils.getSeasonEnd(date);
		}else if(cycleType == BillingCycleEnum.MONTH.getValue()){
			//月
			return DateUtils.getMonthEnd(date);
		}else if(cycleType == BillingCycleEnum.WEEK.getValue()){
			//周
			return DateUtils.getWeekEnd(date);
		}else{
			//自定义周期
			if(calFeeWay.getCustomizeDay() == null){
				throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("未设置自定义周期的起始日期");
			}
			if(calFeeWay.getCusCycleType() == null){
				throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("未设置自定义周期的大小");
			}
			long rootTime = calFeeWay.getCustomizeDay().getTime();
			long timeBetween = date.getTime() - rootTime;
			long customDayTime = calFeeWay.getCusCycleType().intValue() * 86400000;
			long endTime = (customDayTime + 86400000) * timeBetween / customDayTime;
			return new Date(rootTime + endTime);
		}
	}
	/**
	 * 获取特定日期所在的周期的起始日期
	 * @param calFeeWay
	 * @param date
	 * @return
	 */
	public Date fetchCycleBeginDate(CalFeeWay calFeeWay, Date date) throws CostBizException{
		int cycleType = calFeeWay.getCycleType().intValue();
		if(cycleType == BillingCycleEnum.YEAR.getValue()){
			//年
			return DateUtils.getYearStart(date);
		}else if(cycleType == BillingCycleEnum.QUARTER.getValue()){
			//季度
			return DateUtils.getSeasonStart(date);
		}else if(cycleType == BillingCycleEnum.MONTH.getValue()){
			//月
			return DateUtils.getMonthStart(date);
		}else if(cycleType == BillingCycleEnum.WEEK.getValue()){
			//周
			return DateUtils.getWeekStart(date);
		}else{
			//自定义周期
			if(calFeeWay.getCustomizeDay() == null){
				throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("未设置自定义周期的起始日期");
			}
			if(calFeeWay.getCusCycleType() == null){
				throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("未设置自定义周期的大小");
			}
			long rootTime = calFeeWay.getCustomizeDay().getTime();
			long timeBetween = date.getTime() - rootTime;
			long customDayTime = calFeeWay.getCusCycleType().intValue() * 86400000;
			long startTime = customDayTime * timeBetween / customDayTime;
			return new Date(rootTime + startTime);
		}
	}
	/**
	 * 根据计费约束获取当前周期的流量信息
	 * @param calFeeWay 计费约束信息
	 * @param dateCycle 当前周期的起始日期和截止日期
	 * @return
	 */
	public CalFeeFlow fetchCalFeeFlow(CalFeeWay calFeeWay, Date cycleBeginDate, Date cycleEndDate){
		List<CalFeeFlow> calFeeFlows = this.getNumOfDataByFeeWayId(calFeeWay.getId());
		int calFeeFlowCount = (calFeeFlows == null) ? 0 : calFeeFlows.size();
		logger.info(String.format("根据计费约束编号[%d]查找到[%d]条流量信息", calFeeWay.getId(), calFeeFlowCount));
		String startDateCycle = dateFormat.format(cycleBeginDate);
		String endDateCycle = dateFormat.format(cycleEndDate);
		for(int i = 0; i < calFeeFlowCount; i++){
			CalFeeFlow calFeeFlow = calFeeFlows.get(i);
			String startDate = dateFormat.format(calFeeFlow.getBeginDate());
			String endDate = dateFormat.format(calFeeFlow.getEndDate());
			if(!startDateCycle.equals(startDate)){
				continue;
			}
			if(!endDateCycle.equals(endDate)){
				continue;
			}
			return calFeeFlow;
		}
		return null;
	}

}
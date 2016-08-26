package wusc.edu.pay.core.settlement.dao;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;


public interface SettDailyCollectDao extends BaseDao<SettDailyCollect> {

	/**
	 * 根据账户编号获取结算日期区间内的所有未结算日汇总数据.<br/>
	 * 
	 * @param accountNo
	 *            账户编号.<br/>
	 * @param beginCollectDate
	 *            汇总日期(开始).<br/>
	 * @param endCollectDate
	 *            汇总日期(结束).<br/>
	 * @return
	 */
	List<SettDailyCollect> listUnSettDailyCollectByAccountNo(String accountNo, String beginCollectDate, String endCollectDate);

	/**
	 * 根据结束时间统计每日汇总记录获取结算金额
	 * 
	 * @param accountNo
	 * @param endDate
	 */
	Double getSettAmount(String accountNo, String beginCollectDate, String endCollectDate);

	/**
	 * 根据批次号，更新每日汇总状态为已结算
	 * 
	 * @param batchNo
	 */
	void updateDailyCollectStatus(String batchNo, int value);

	/**
	 * 删除：未结算遗留汇总
	 */
	void deleteByBatchNoAndDailyType(String batchNo, int settDailyCollectType);

}

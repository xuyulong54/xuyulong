package wusc.edu.pay.core.settlement.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettDailyCollectDao;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectStatusEnum;


@Repository("settDailyCollectDao")
public class SettDailyCollectDaoImpl extends BaseDaoImpl<SettDailyCollect> implements SettDailyCollectDao {

	/**
	 * 根据账户编号获取结算日期区间内的所有未结算日汇总数据.<br/>
	 * @param accountNo 账户编号.<br/>
	 * @param beginCollectDate 汇总日期(开始).<br/>
	 * @param endCollectDate 汇总日期(结束).<br/>
	 * @return
	 */
	@Override
	public List<SettDailyCollect> listUnSettDailyCollectByAccountNo(String accountNo, String beginCollectDate, String endCollectDate) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		paramMap.put("endCollectDate", endCollectDate);
		paramMap.put("settStatus", SettDailyCollectStatusEnum.UNSETTLE.getValue());
		
		return super.listBy(paramMap);
	}

	/**
	 * 根据结束时间统计每日汇总记录获取结算金额
	 * @param accountNo
	 * @param endDate
	 */
	public Double getSettAmount(String accountNo,String beginCollectDate,String endCollectDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", accountNo);
		paramMap.put("beginDate", beginCollectDate);
		paramMap.put("endDate", endCollectDate);
		paramMap.put("settStatus", SettDailyCollectStatusEnum.UNSETTLE.getValue());
		
		return this.getSessionTemplate().selectOne(getStatement("getSettAmount"),paramMap);
	}
	
	/**
	 * 根据批次号，更新每日汇总状态为已结算
	 * @param batchNo
	 */
	public void updateDailyCollectStatus(String batchNo, int status){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		paramMap.put("status", status);
		
		this.getSessionTemplate().update(getStatement("updateDailyCollectStatus"),paramMap);
	}

	/**
	 * 删除：未结算遗留汇总
	 */
	public void deleteByBatchNoAndDailyType(String batchNo, int settDailyCollectType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("batchNo", batchNo);
		paramMap.put("collectType", settDailyCollectType);
		this.getSessionTemplate().delete(getStatement("deleteByBatchNoAndDailyType"),paramMap);
		
	}
	
	


}

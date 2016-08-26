package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByMerch;



public interface TerminalSummaryByMerchDao extends BaseDao<TerminalSummaryByMerch>{
	
	/**
	 * 根据商户来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	int createTermSummaryByMerch(Date dealDate);
}

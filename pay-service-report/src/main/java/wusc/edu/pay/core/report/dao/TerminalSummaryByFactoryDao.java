package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByFactory;



public interface TerminalSummaryByFactoryDao extends BaseDao<TerminalSummaryByFactory>{
	
	/**
	 * 根据终端厂家和型号统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	int createTermSummaryByVendor(Date dealDate);
}

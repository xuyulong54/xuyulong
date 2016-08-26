package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.MerchantAuditSummary;



public interface MerchantAuditSummaryDao extends BaseDao<MerchantAuditSummary>{
	
	/**
	 *  根据审核次数统计初审和终审情况
	 * @param dealDate
	 * @return
	 */
	int createAuditSummary(Date dealDate);
}

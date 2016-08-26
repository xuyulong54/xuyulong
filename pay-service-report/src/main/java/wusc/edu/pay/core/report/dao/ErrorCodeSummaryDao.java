package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.ErrorCodeSummary;



public interface ErrorCodeSummaryDao extends BaseDao<ErrorCodeSummary>{

	int createErrorCodeSummary(Date dealDate);
}

package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.TimeSummary;



public interface TimeSummaryDao extends BaseDao<TimeSummary>{

	int createTimeSummary(Date dealDate);
}

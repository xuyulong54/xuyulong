package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.AreaSummary;



public interface AreaSummaryDao extends BaseDao<AreaSummary>{

	int createAreaSummary(Date dealDate);
}

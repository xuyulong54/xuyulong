package wusc.edu.pay.core.report.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByAgent;



public interface TerminalSummaryByAgentDao extends BaseDao<TerminalSummaryByAgent>{
	
	/**
	 * 根据一级代理商来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	int createTermSummaryByAgent(Date dealDate);
}

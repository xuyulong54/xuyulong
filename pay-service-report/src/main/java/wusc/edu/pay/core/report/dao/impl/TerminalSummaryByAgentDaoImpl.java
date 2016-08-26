package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.TerminalSummaryByAgentDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByAgent;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;

@Repository("terminalSummaryByAgentDao")
public class TerminalSummaryByAgentDaoImpl extends BaseDaoImpl<TerminalSummaryByAgent> implements TerminalSummaryByAgentDao{


	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 根据一级代理商来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	@Override
	public int createTermSummaryByAgent(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.TIMINAL_REPORT_BY_AGENT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", dateFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createTermSummaryByAgent"), param);
	}

}

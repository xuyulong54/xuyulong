package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.TerminalSummaryByAgentDao;


@Component("terminalSummaryByAgentBiz")
public class TerminalSummaryByAgentBiz {

	@Autowired
	private TerminalSummaryByAgentDao terminalSummaryByAgentDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByAgentDao.listPage(pageParam, paramMap);
	}
	
	public int createTermSummaryByAgent(Date dealDate){
		return terminalSummaryByAgentDao.createTermSummaryByAgent(dealDate);
	}

}

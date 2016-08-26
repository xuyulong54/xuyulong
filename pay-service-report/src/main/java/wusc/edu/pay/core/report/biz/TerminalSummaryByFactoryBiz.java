package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.TerminalSummaryByFactoryDao;


@Component("terminalSummaryByFactoryBiz")
public class TerminalSummaryByFactoryBiz {

	@Autowired
	private TerminalSummaryByFactoryDao terminalSummaryByFactoryDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByFactoryDao.listPage(pageParam, paramMap);
	}
	
	public int createTermSummaryByVendor(Date dealDate){
		return terminalSummaryByFactoryDao.createTermSummaryByVendor(dealDate);
	}

}

package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.TerminalSummaryByMerchDao;


@Component("terminalSummaryByMerchBiz")
public class TerminalSummaryByMerchBiz {

	@Autowired
	private TerminalSummaryByMerchDao terminalSummaryByMerchDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return terminalSummaryByMerchDao.listPage(pageParam, paramMap);
	}
	
	public int createTermSummaryByMerch(Date dealDate){
		return terminalSummaryByMerchDao.createTermSummaryByMerch(dealDate);
	}

}

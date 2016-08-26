package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.ErrorCodeSummaryDao;


@Component("errorCodeSummaryBiz")
public class ErrorCodeSummaryBiz {

	@Autowired
	private ErrorCodeSummaryDao errorCodeSummaryDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return errorCodeSummaryDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 创建卡bin统计数据
	 * @param dealDate
	 * @return
	 */
	public int createErrorCodeSummary(Date dealDate){
		return errorCodeSummaryDao.createErrorCodeSummary(dealDate);
	}

}

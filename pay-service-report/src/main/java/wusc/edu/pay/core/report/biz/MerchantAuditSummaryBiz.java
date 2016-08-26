package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.MerchantAuditSummaryDao;


@Component("merchantAuditSummaryBiz")
public class MerchantAuditSummaryBiz {

	@Autowired
	private MerchantAuditSummaryDao merchantAuditSummaryDao;

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantAuditSummaryDao.listPage(pageParam, paramMap);
	}

	public int createAuditSummary(Date dealDate){
		return merchantAuditSummaryDao.createAuditSummary(dealDate);
	}

}

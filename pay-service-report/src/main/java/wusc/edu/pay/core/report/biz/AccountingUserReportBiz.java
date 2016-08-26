package wusc.edu.pay.core.report.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.AccountingUserReportDao;
import wusc.edu.pay.facade.report.entity.AccountingUserReport;


/***
 * 
 * @描述: 客户账户统计.
 * @作者: Lanzy.
 * @创建时间: 2014-4-28, 下午3:56:50 .
 * @版本: V1.0.
 *
 */
@Component("accountingUserReportBiz")
public class AccountingUserReportBiz {
	
	@Autowired
	private AccountingUserReportDao accountingUserReportDao;
	
	public long create(AccountingUserReport entity) {
		return accountingUserReportDao.insert(entity);
	}

	public AccountingUserReport getById(long id) {
		return accountingUserReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return accountingUserReportDao.listPage(pageParam, paramMap);
	}

}

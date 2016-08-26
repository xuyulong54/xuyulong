package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.AccountingReservesBankReportDao;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;

@Component("accountingReservesBankReport")
public class AccountingReservesBankReportBiz {
	
	@Autowired
	private AccountingReservesBankReportDao accountingReservesBankReportDao;
	
	public long create(AccountingReservesBankReport entity) {
		return accountingReservesBankReportDao.insert(entity);
	}

	public AccountingReservesBankReport getById(long id) {
		return accountingReservesBankReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return accountingReservesBankReportDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 报表展现数据
	 * @param paramMap
	 * @return
	 */
	public List<AccountingReservesBankReport> listByDate(Map<String, Object> paramMap){
		return accountingReservesBankReportDao.listByDate(paramMap);
		
	}

	public List<AccountingReservesBankReport> listDetail(
			Map<String, Object> paramMap) {
		return accountingReservesBankReportDao.listDetail(paramMap);
	};
}

package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.AccountingReservesBankReportBiz;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;
import wusc.edu.pay.facade.report.service.AccountingReservesBankReportFacade;


/***
 * 
 * @描述: 帐户对账差错统计(业务对账，财务对账).
 * @作者: Lanzy.
 * @创建时间: 2014-4-28, 下午4:06:10 .
 * @版本: V1.0.
 * 
 */
@Component("accountingReservesBankReportFacade")
public class AccountingReservesBankReportFacadeImpl implements AccountingReservesBankReportFacade {

	@Autowired
	private AccountingReservesBankReportBiz accountingReservesBankReportBiz;

	public long create(AccountingReservesBankReport entity) throws BizException {
		return accountingReservesBankReportBiz.create(entity);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws BizException {
		return accountingReservesBankReportBiz.listPage(pageParam, paramMap);
	}
	@Override
	public AccountingReservesBankReport getById(long id) throws BizException {
		return accountingReservesBankReportBiz.getById(id);
	}
	
	public List<AccountingReservesBankReport> listByDate(Map<String, Object> paramMap) {
		return accountingReservesBankReportBiz.listByDate(paramMap);
	}

	@Override
	public List<AccountingReservesBankReport> listDetail(
			Map<String, Object> paramMap) {
		return accountingReservesBankReportBiz.listDetail(paramMap);
	}
}

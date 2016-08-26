package wusc.edu.pay.facade.report.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.core.report.biz.AccountingUserReportBiz;
import wusc.edu.pay.facade.report.entity.AccountingUserReport;
import wusc.edu.pay.facade.report.service.AccountingUserReportFacade;


/***
 * 
 * @描述: 客户账户统计.
 * @作者: Lanzy.
 * @创建时间: 2014-4-28, 下午3:53:05 .
 * @版本: V1.0.
 *
 */
@Component("accountingUserReportFacade")
public class AccountingUserReportFacadeImpl implements AccountingUserReportFacade{
	
	@Autowired
	private AccountingUserReportBiz accountingUserReportBiz;

	public long create(AccountingUserReport entity) throws BizException {
		AccountingUserReport accountingUserReport = new AccountingUserReport();
		BeanUtils.copyProperties(entity,accountingUserReport);
		return accountingUserReportBiz.create(accountingUserReport);
	}

	public AccountingUserReport getById(long id) throws BizException {
		AccountingUserReport accountingUserReport = accountingUserReportBiz.getById(id);
		AccountingUserReport AccountingUserReport = new AccountingUserReport();
		BeanUtils.copyProperties(accountingUserReport, AccountingUserReport);
		return AccountingUserReport;
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap)
			throws BizException {
		return accountingUserReportBiz.listPage(pageParam, paramMap);
	}

}

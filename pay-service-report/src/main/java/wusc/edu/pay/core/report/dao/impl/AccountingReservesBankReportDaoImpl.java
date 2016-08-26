package wusc.edu.pay.core.report.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.AccountingReservesBankReportDao;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;


/***
 * 
 * @描述: 备付金银行帐户统计.
 * @作者: Lanzy.
 * @创建时间: 2014-4-28, 下午8:08:19 .
 * @版本: V1.0.
 * 
 */
@Repository("accountingReservesBankReportDao")
public class AccountingReservesBankReportDaoImpl extends
		BaseDaoImpl<AccountingReservesBankReport> implements
		AccountingReservesBankReportDao {
	@Override
	public List<AccountingReservesBankReport> listByDate(
			Map<String, Object> paramMap) {
		return this.getSessionTemplate().selectList(getStatement("listByDate"), paramMap);
	}

	@Override
	public List<AccountingReservesBankReport> listDetail(
			Map<String, Object> paramMap) {
		return this.getSessionTemplate().selectList(getStatement("listDetail"), paramMap);
	}
}

package wusc.edu.pay.core.report.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.AccountingReservesBankReport;

/***
 * 
 * @描述: 备付金银行帐户统计.
 * @作者: Lanzy.
 * @创建时间: 2014-4-28, 下午8:07:32 .
 * @版本: V1.0.
 *
 */
public interface AccountingReservesBankReportDao extends BaseDao<AccountingReservesBankReport>{

	List<AccountingReservesBankReport> listByDate(Map<String, Object> paramMap);

	List<AccountingReservesBankReport> listDetail(Map<String, Object> paramMap);
}

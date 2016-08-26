package wusc.edu.pay.core.report.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.OnlineBankReportDao;
import wusc.edu.pay.facade.report.entity.OnlineBankReport;

/***
 * 
 * @描述: 在线银行报表数据.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午6:45:29 .
 * @版本: V1.0.
 *
 */
@Repository("onlineBankReportDao")
public class OnlineBankReportDaoImpl extends BaseDaoImpl<OnlineBankReport> implements OnlineBankReportDao{

}

package wusc.edu.pay.core.report.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.OnlineMerchantReportDao;
import wusc.edu.pay.facade.report.entity.OnlineMerchantReport;

/***
 * 
 * @描述: 在线商户报表数据（统计商户金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午6:48:27 .
 * @版本: V1.0.
 *
 */
@Repository("onlineMerchantReportDao")
public class OnlineMerchantReportDaoImpl extends BaseDaoImpl<OnlineMerchantReport> implements OnlineMerchantReportDao{

}

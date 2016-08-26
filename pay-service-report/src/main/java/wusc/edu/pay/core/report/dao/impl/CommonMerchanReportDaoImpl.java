package wusc.edu.pay.core.report.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.CommonMerchanReportDao;
import wusc.edu.pay.facade.report.entity.CommonMerchanReport;

/***
 * 
 * @描述: 商户报表数据(统计商户非金钱的数据).
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午6:39:27 .
 * @版本: V1.0.
 *
 */
@Repository("commonMerchanReportDao")
public class CommonMerchanReportDaoImpl extends BaseDaoImpl<CommonMerchanReport> implements CommonMerchanReportDao{

}

package wusc.edu.pay.core.report.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.OnlineMemberReportDao;
import wusc.edu.pay.facade.report.entity.OnlineMemberReport;

/***
 * 
 * @描述: 在线会员报表数据（统计会员金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午6:47:09 .
 * @版本: V1.0.
 *
 */
@Repository("onlineMemberReportDao")
public class OnlineMemberReportDaoImpl extends BaseDaoImpl<OnlineMemberReport> implements OnlineMemberReportDao{

}

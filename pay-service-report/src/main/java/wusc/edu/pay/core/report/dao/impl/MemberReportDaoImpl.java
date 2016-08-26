package wusc.edu.pay.core.report.dao.impl;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.MemberReportDao;
import wusc.edu.pay.facade.report.entity.MemberReport;

/***
 * 
 * @描述: 会员报表数据（统计会员非金钱的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午6:44:03 .
 * @版本: V1.0.
 *
 */
@Repository("memberReportDao")
public class MemberReportDaoImpl extends BaseDaoImpl<MemberReport> implements MemberReportDao{

}

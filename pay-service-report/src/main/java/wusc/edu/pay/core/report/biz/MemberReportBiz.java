package wusc.edu.pay.core.report.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.MemberReportDao;
import wusc.edu.pay.facade.report.entity.MemberReport;


/***
 * 
 * @描述: 会员报表数据（统计会员非金钱的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:42:00 .
 * @版本: V1.0.
 *
 */
@Component("memberReportBiz")
public class MemberReportBiz {
	
	@Autowired
	private MemberReportDao memberReportDao;

	public long create(MemberReport entity) {
		return memberReportDao.insert(entity);
	}

	public MemberReport getById(long id) {
		return memberReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return memberReportDao.listPage(pageParam, paramMap);
	}
	
}

package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.OnlineMemberReportDao;
import wusc.edu.pay.facade.report.entity.OnlineMemberReport;


/***
 * 
 * @描述: 在线会员报表数据（统计会员金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:48:19 .
 * @版本: V1.0.
 *
 */
@Component("onlineMemberReportBiz")
public class OnlineMemberReportBiz {
	
	@Autowired
	private OnlineMemberReportDao onlineMemberReportDao;
	
	public long create(OnlineMemberReport entity) {
		return onlineMemberReportDao.insert(entity);
	}

	public OnlineMemberReport getById(long id) {
		return onlineMemberReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return onlineMemberReportDao.listPage(pageParam, paramMap);
	}

	public List<OnlineMemberReport> listBy(Map<String, Object> onlineMemberMap) {
		return onlineMemberReportDao.listBy(onlineMemberMap);
	}
}

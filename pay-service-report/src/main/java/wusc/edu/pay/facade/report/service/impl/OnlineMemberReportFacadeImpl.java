package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.OnlineMemberReportBiz;
import wusc.edu.pay.facade.report.entity.OnlineMemberReport;
import wusc.edu.pay.facade.report.service.OnlineMemberReportFacade;


/***
 * 
 * @描述: 在线会员报表数据表（统计会员金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("onlineMemberReportFacade")
public class OnlineMemberReportFacadeImpl implements OnlineMemberReportFacade{

	@Autowired
	private OnlineMemberReportBiz onlineMemberReportBiz;
	
	public long create(OnlineMemberReport entity) {
		return onlineMemberReportBiz.create(entity);
	}

	public OnlineMemberReport getById(long id) {
		return onlineMemberReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return onlineMemberReportBiz.listPage(pageParam, paramMap);
	}

	public List<OnlineMemberReport> listBy(Map<String, Object> onlineMemberMap) {
		return onlineMemberReportBiz.listBy(onlineMemberMap);
	}

}

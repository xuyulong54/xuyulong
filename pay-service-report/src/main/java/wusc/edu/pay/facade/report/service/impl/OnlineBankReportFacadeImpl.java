package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.OnlineBankReportBiz;
import wusc.edu.pay.facade.report.entity.OnlineBankReport;
import wusc.edu.pay.facade.report.service.OnlineBankReportFacade;


/***
 * 
 * @描述: 在线银行报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("onlineBankReportFacade")
public class OnlineBankReportFacadeImpl implements OnlineBankReportFacade{

	@Autowired
	private OnlineBankReportBiz onlineBankReportBiz;
	
	public long create(OnlineBankReport entity) {
		return onlineBankReportBiz.create(entity);
	}

	public OnlineBankReport getById(long id) {
		return onlineBankReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return onlineBankReportBiz.listPage(pageParam, paramMap);
	}

	public List<OnlineBankReport> listBy(Map<String, Object> onlineBankMap) {
		return onlineBankReportBiz.listBy(onlineBankMap);
	}

}

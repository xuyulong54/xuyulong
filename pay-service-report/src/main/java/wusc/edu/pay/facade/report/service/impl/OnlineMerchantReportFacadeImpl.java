package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.OnlineMerchantReportBiz;
import wusc.edu.pay.facade.report.entity.OnlineMerchantReport;
import wusc.edu.pay.facade.report.service.OnlineMerchantReportFacade;


/***
 * 
 * @描述: 在线商户报表数据表（统计商户金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("onlineMerchantReportFacade")
public class OnlineMerchantReportFacadeImpl implements OnlineMerchantReportFacade{

	@Autowired
	private OnlineMerchantReportBiz onlineMerchantReportBiz;
	
	public long create(OnlineMerchantReport entity) {
		return onlineMerchantReportBiz.create(entity);
	}

	public OnlineMerchantReport getById(long id) {
		return onlineMerchantReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return onlineMerchantReportBiz.listPage(pageParam, paramMap);
	}

	public List<OnlineMerchantReport> listBy(Map<String, Object> onlineMerchantMap) {
		return onlineMerchantReportBiz.listBy(onlineMerchantMap);
	}

}

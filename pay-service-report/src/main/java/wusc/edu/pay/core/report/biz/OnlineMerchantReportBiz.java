package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.OnlineMerchantReportDao;
import wusc.edu.pay.facade.report.entity.OnlineMerchantReport;


/***
 * 
 * @描述: 在线商户报表数据（统计商户金钱有关的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:50:39 .
 * @版本: V1.0.
 *
 */
@Component("onlineMerchantReportBiz")
public class OnlineMerchantReportBiz {
	
	@Autowired
	private OnlineMerchantReportDao onlineMerchantReportDao;

	public long create(OnlineMerchantReport entity) {
		return onlineMerchantReportDao.insert(entity);
	}

	public OnlineMerchantReport getById(long id) {
		return onlineMerchantReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return onlineMerchantReportDao.listPage(pageParam, paramMap);
	}

	public List<OnlineMerchantReport> listBy(Map<String, Object> onlineMerchantMap) {
		return onlineMerchantReportDao.listBy(onlineMerchantMap);
	}
}

package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.OnlineBankReportDao;
import wusc.edu.pay.facade.report.entity.OnlineBankReport;


/***
 * 
 * @描述: 在线银行报表数据.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:45:03 .
 * @版本: V1.0.
 *
 */
@Component("onlineBankReportBiz")
public class OnlineBankReportBiz {
	
	@Autowired
	private OnlineBankReportDao onlineBankReportDao;
	
	public long create(OnlineBankReport entity) {
		return onlineBankReportDao.insert(entity);
	}

	public OnlineBankReport getById(long id) {
		return onlineBankReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return onlineBankReportDao.listPage(pageParam, paramMap);
	}

	public List<OnlineBankReport> listBy(Map<String, Object> onlineBankMap) {
		return onlineBankReportDao.listBy(onlineBankMap);
	}

}

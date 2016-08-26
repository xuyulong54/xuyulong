package wusc.edu.pay.core.report.biz;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.PosMerchantReportDao;
import wusc.edu.pay.facade.report.entity.PosMerchantReport;

/***
 * 
 * @描述: POS商户报表数据.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:54:26 .
 * @版本: V1.0.
 *
 */
@Component("posMerchantReportBiz")
public class PosMerchantReportBiz {

	@Autowired
	private PosMerchantReportDao posMerchantReportDao;
	
	public long create(PosMerchantReport entity) {
		return posMerchantReportDao.insert(entity);
	}

	public PosMerchantReport getById(long id) {
		return posMerchantReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return posMerchantReportDao.listPage(pageParam, paramMap);
	}

	public List<PosMerchantReport> listByDate(Map<String, Object> posMerchantMap) {
		return posMerchantReportDao.listBy(posMerchantMap);
	}
}

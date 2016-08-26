package wusc.edu.pay.core.report.biz;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.CommonMerchanReportDao;
import wusc.edu.pay.facade.report.entity.CommonMerchanReport;


/***
 * 
 * @描述: 商户报表数据(统计商户非金钱的数据).
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午7:35:32 .
 * @版本: V1.0.
 *
 */
@Component("commonMerchanReportBiz")
public class CommonMerchanReportBiz {
	
	@Autowired
	private CommonMerchanReportDao commonMerchanReportDao;
	
	public long create(CommonMerchanReport entity) {
		return commonMerchanReportDao.insert(entity);
	}

	public CommonMerchanReport getById(long id) {
		return commonMerchanReportDao.getById(id);
	}
	
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return commonMerchanReportDao.listPage(pageParam, paramMap);
	}

}

package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.PosMerchantReportBiz;
import wusc.edu.pay.facade.report.entity.PosMerchantReport;
import wusc.edu.pay.facade.report.service.PosMerchantReportFacade;


/***
 * 
 * @描述: POS商户报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 * 
 */
@Component("posMerchantReportFacade")
public class PosMerchantReportFacadeImpl implements PosMerchantReportFacade {

	@Autowired
	private PosMerchantReportBiz posMerchantReportBiz;

	public long create(PosMerchantReport entity) {
		return posMerchantReportBiz.create(entity);
	}

	public PosMerchantReport getById(long id) {
		return posMerchantReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return posMerchantReportBiz.listPage(pageParam, paramMap);
	}

	public List<PosMerchantReport> listByDate(Map<String, Object> posMerchantMap) {
		return posMerchantReportBiz.listByDate(posMerchantMap);
	}

}

package wusc.edu.pay.facade.report.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.CommonMerchanReportBiz;
import wusc.edu.pay.facade.report.entity.CommonMerchanReport;
import wusc.edu.pay.facade.report.service.CommonMerchanReportFacade;


/***
 * 
 * @描述: 商户报表数据表(统计商户非金钱的数据).
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("commonMerchanReportFacade")
public class CommonMerchanReportFacadeImpl implements CommonMerchanReportFacade{

	@Autowired
	private CommonMerchanReportBiz commonMerchanReportBiz;
	
	public long create(CommonMerchanReport entity) {
		return commonMerchanReportBiz.create(entity);
	}

	public CommonMerchanReport getById(long id) {
		return commonMerchanReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return commonMerchanReportBiz.listPage(pageParam, paramMap);
	}

}

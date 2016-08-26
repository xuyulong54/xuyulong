package wusc.edu.pay.facade.report.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.PosBankReportBiz;
import wusc.edu.pay.facade.report.entity.PosBankReport;
import wusc.edu.pay.facade.report.service.PosBankReportFacade;


/***
 * 
 * @描述: POS银行报表数据表.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("posBankReportFacade")
public class PosBankReportFacadeImpl implements PosBankReportFacade{

	@Autowired
	private PosBankReportBiz posBankReportBiz;
	
	public long create(PosBankReport entity) {
		return posBankReportBiz.create(entity);
	}

	public PosBankReport getById(long id) {
		return posBankReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return posBankReportBiz.listPage(pageParam, paramMap);
	}

	public List<PosBankReport> listByDate(Map<String, Object> posBankMap) {
		return posBankReportBiz.listByDate(posBankMap);
	}

}

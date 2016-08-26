package wusc.edu.pay.facade.report.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.MemberReportBiz;
import wusc.edu.pay.facade.report.entity.MemberReport;
import wusc.edu.pay.facade.report.service.MemberReportFacade;


/***
 * 
 * @描述: 会员报表数据表（统计会员非金钱的数据）.
 * @作者: Lanzy.
 * @创建时间: 2014-4-20, 下午9:10:39 .
 * @版本: V1.0.
 *
 */
@Component("memberReportFacade")
public class MemberReportFacadeImpl implements MemberReportFacade{

	@Autowired
	private MemberReportBiz memberReportBiz;
	
	public long create(MemberReport entity) {
		return memberReportBiz.create(entity);
	}

	public MemberReport getById(long id) {
		return memberReportBiz.getById(id);
	}

	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap){
		return memberReportBiz.listPage(pageParam, paramMap);
	}

}

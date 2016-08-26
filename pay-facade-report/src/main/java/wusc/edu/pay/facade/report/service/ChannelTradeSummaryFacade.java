package wusc.edu.pay.facade.report.service;

import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.report.entity.ChannelTradeSummary;


/**
 * 
 * @描述:渠道信息接口.
 * @作者: zhangguoqing.
 * @创建时间: 2014-12-18 上午9:09:13
 * @版本: V1.0.
 * 
 */
public interface ChannelTradeSummaryFacade {
	
	public PageBean listPageChannelTrans(PageParam pageParam, Map<String, Object> paramMap);

	public ChannelTradeSummary countTableConditions(Map<String, Object> paramMap);
	
}
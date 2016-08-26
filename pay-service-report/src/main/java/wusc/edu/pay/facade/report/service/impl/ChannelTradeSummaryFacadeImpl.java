package wusc.edu.pay.facade.report.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.biz.ChannelTradeSummaryBiz;
import wusc.edu.pay.facade.report.entity.ChannelTradeSummary;
import wusc.edu.pay.facade.report.service.ChannelTradeSummaryFacade;


@Component("channelTradeSummaryFacade")
public class ChannelTradeSummaryFacadeImpl implements ChannelTradeSummaryFacade{
	@Autowired
	private ChannelTradeSummaryBiz channelTradeSummaryBiz;

	@Override
	public PageBean listPageChannelTrans(PageParam pageParam,
			Map<String, Object> paramMap) {
		String startDate = (String) paramMap.get("startDate");
		String endDate = (String) paramMap.get("endDate");
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		return channelTradeSummaryBiz.listPageChannelTrans(pageParam, paramMap);
	}
	/**
	 * 页面合计信息统计
	 * @param paramMap
	 * @return
	 */
	@Override
	public ChannelTradeSummary countTableConditions(Map<String, Object> paramMap) {
		return channelTradeSummaryBiz.countTableConditions(paramMap);
	}
}
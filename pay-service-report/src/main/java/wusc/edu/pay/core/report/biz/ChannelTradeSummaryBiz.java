package wusc.edu.pay.core.report.biz;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.report.dao.ChannelTradeSummaryDao;
import wusc.edu.pay.facade.report.entity.ChannelTradeSummary;


/**
 * 
 * @描述:.
 * @作者: zhangguoqing.
 * @创建时间: 2014-12-18
 * @版本: V1.0.
 * 
 */
@Component("channelTradeSummaryBiz")
public class ChannelTradeSummaryBiz {

	@Autowired
	private ChannelTradeSummaryDao channelTradeSummaryDao;

	public PageBean listPageChannelTrans(PageParam pageParam,
			Map<String, Object> paramMap) {
		return channelTradeSummaryDao.listPage(pageParam, paramMap);
	}
	
	/**
	 * 创建渠道汇总明细数据
	 * @param dealDate
	 * @return
	 */
	public int createChannelTransSummary(Date dealDate){
		return channelTradeSummaryDao.createChannelTransSummary(dealDate);
	}
	
	/**
	 * 页面合计信息统计
	 * @param paramMap
	 * @return
	 */
	public ChannelTradeSummary countTableConditions(Map<String, Object> paramMap) {
		return channelTradeSummaryDao.countTableConditions(paramMap);
	}


}
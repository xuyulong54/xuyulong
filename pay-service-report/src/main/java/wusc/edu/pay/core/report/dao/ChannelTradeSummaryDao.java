package wusc.edu.pay.core.report.dao;

import java.util.Date;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.report.entity.ChannelTradeSummary;


/**
 * 
 * @描述:.
 * @作者: zhangguoqing.
 * @创建时间: 2014-12-18
 * @版本: V1.0.
 * 
 */
public interface ChannelTradeSummaryDao extends BaseDao<ChannelTradeSummary> {
	
	/**
	 * 创建渠道交易明细数据
	 * @param dealDate
	 * @return
	 */
	int createChannelTransSummary(Date dealDate);

	/**
	 * 页面合计统计
	 * @param paramMap
	 * @return
	 */
	ChannelTradeSummary countTableConditions(Map<String, Object> paramMap);
	
}
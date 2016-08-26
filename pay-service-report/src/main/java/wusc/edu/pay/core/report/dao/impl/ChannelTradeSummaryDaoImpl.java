package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.ChannelTradeSummaryDao;
import wusc.edu.pay.facade.report.entity.ChannelTradeSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


/**
 * 
 * @描述:.
 * @作者: zhangguoqing.
 * @创建时间: 2014-12-18
 * @版本: V1.0.
 * 
 */
@Repository(value = "channelTradeSummaryDao")
public class ChannelTradeSummaryDaoImpl extends BaseDaoImpl<ChannelTradeSummary> implements ChannelTradeSummaryDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int createChannelTransSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.CHANNEL_TRADE_SUMMARY_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createChannelTransSummary"), param);
	}

	@Override
	public ChannelTradeSummary countTableConditions(Map<String, Object> paramMap) {
		return super.getSessionTemplate().selectOne("countTableConditions", paramMap);
	}

}
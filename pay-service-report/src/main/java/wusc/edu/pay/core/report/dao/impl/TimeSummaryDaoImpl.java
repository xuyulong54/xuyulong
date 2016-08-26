package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.TimeSummaryDao;
import wusc.edu.pay.facade.report.entity.TimeSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


@Repository("timeSummaryDao")
public class TimeSummaryDaoImpl extends BaseDaoImpl<TimeSummary> implements TimeSummaryDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int createTimeSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.TIME_SCOPE_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createTimeSummary"), param);
	}

}

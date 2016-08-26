package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.ErrorCodeSummaryDao;
import wusc.edu.pay.facade.report.entity.ErrorCodeSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


@Repository("errorCodeSummaryDao")
public class ErrorCodeSummaryDaoImpl extends BaseDaoImpl<ErrorCodeSummary> implements ErrorCodeSummaryDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int createErrorCodeSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.ERROR_MSG_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createErrorCodeSummary"), param);
	}

}

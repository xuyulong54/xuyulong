package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.AreaSummaryDao;
import wusc.edu.pay.facade.report.entity.AreaSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


@Repository("areaSummaryDao")
public class AreaSummaryDaoImpl extends BaseDaoImpl<AreaSummary> implements AreaSummaryDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int createAreaSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.AREA_SCOPE_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createAreaSummary"), param);
	}

}

package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.TerminalSummaryByFactoryDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByFactory;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;

@Repository("terminalSummaryByFactoryDao")
public class TerminalSummaryByFactoryDaoImpl extends BaseDaoImpl<TerminalSummaryByFactory> implements TerminalSummaryByFactoryDao{

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 根据终端厂家和型号统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	@Override
	public int createTermSummaryByVendor(Date dealDate){
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.TIMINAL_REPORT_BY_FACTORY.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", dateFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createTermSummaryByVendor"), param);
	}
}

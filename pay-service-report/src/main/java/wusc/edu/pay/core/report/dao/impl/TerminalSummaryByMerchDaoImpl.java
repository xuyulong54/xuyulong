package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.TerminalSummaryByMerchDao;
import wusc.edu.pay.facade.report.entity.TerminalSummaryByMerch;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;

@Repository("terminalSummaryByMerchDao")
public class TerminalSummaryByMerchDaoImpl extends BaseDaoImpl<TerminalSummaryByMerch> implements TerminalSummaryByMerchDao{

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 根据商户来统计机具库存情况
	 * 
	 * @param dealDate
	 * @return
	 */
	@Override
	public int createTermSummaryByMerch(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.TIMINAL_REPORT_BY_MERCHANT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", dateFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createTermSummaryByMerch"), param);
	}

}

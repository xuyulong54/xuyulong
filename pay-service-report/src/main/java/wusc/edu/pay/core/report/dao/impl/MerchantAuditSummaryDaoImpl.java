package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.MerchantAuditSummaryDao;
import wusc.edu.pay.facade.report.entity.MerchantAuditSummary;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;

@Repository("merchantAuditSummaryDao")
public class MerchantAuditSummaryDaoImpl extends BaseDaoImpl<MerchantAuditSummary> implements MerchantAuditSummaryDao{


	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 *  根据审核次数统计初审和终审情况
	 */
	@Override
	public int createAuditSummary(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.MERCHANT_AUDIT_STATUS_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", dateFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createAuditSummary"), param);
	}

}

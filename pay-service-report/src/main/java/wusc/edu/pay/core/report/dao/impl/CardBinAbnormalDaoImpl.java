package wusc.edu.pay.core.report.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.report.dao.CardBinAbnormalDao;
import wusc.edu.pay.facade.report.entity.CardBinAbnormal;
import wusc.edu.pay.facade.report.enums.PosReportTypeEnum;


@Repository("cardBinAbnormalDao")
public class CardBinAbnormalDaoImpl extends BaseDaoImpl<CardBinAbnormal> implements CardBinAbnormalDao {

	private SimpleDateFormat datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private SimpleDateFormat fieldFormat = new SimpleDateFormat("yyyyMMdd");

	@Override
	public int createCardBinAbnormal(Date dealDate) {
		Date date = new Date();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dateTime", datetimeFormat.format(date));
		param.put("reportType", PosReportTypeEnum.CARDBIN_ERROR_MSG_REPORT.getValue());
		param.put("statDate", dateFormat.format(date));
		param.put("transDate", dateFormat.format(dealDate));
		param.put("fieldDate", fieldFormat.format(dealDate));
		return super.getSessionTemplate().insert(getStatement("createCardBinAbnormal"), param);
	}

}

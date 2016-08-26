package wusc.edu.pay.core.limit.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.AmountCumulateDao;
import wusc.edu.pay.facade.limit.entity.AmountCumulate;


@Repository(value="amountCumulateDao")
public class AmountCumulateDaoImpl extends BaseDaoImpl<AmountCumulate> implements AmountCumulateDao{
	/**
	 * 根据金额限制、时间查询交易金额累计
	 * @param amountLimitId 交易金额限制ID
	 * @param date	时间
	 * @return
	 */
	public AmountCumulate getAmountCumulateByLimitIdAndDate(Long amountLimitId, Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("amountLimitId", amountLimitId);
		params.put("date", date);
		return this.getBy(params);
	}
	
	public AmountCumulate getAmountCumulateByLimitIdAndDateAndMerchantNo(String merchantNo, Long amountLimitId, Date date){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("amountLimitId", amountLimitId);
		params.put("date", date);
		return this.getBy(params);
	}
}

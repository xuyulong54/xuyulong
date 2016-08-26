package wusc.edu.pay.core.limit.dao;

import java.util.Date;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.AmountCumulate;


public interface AmountCumulateDao extends BaseDao<AmountCumulate>{
	
	/**
	 * 根据金额限制、时间查询交易金额累计
	 * @param amountLimitId 交易金额限制ID
	 * @param date	时间
	 * @return
	 */
	public AmountCumulate getAmountCumulateByLimitIdAndDate(Long amountLimitId, Date date);
	
	/**
	 * 根据金额限制、时间查询交易金额累计
	 * @param amountLimitId 交易金额限制ID
	 * @param date	时间
	 * @param merchantNo 商户编号
	 * @return
	 */
	public AmountCumulate getAmountCumulateByLimitIdAndDateAndMerchantNo(String merchantNo, Long amountLimitId, Date date);
}

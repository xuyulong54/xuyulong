package wusc.edu.pay.core.limit.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.limit.entity.AmountLimit;


public interface AmountLimitDao extends BaseDao<AmountLimit>{

	/**
	 * 根据商户编号查询交易金额限制
	 * @param merchantNo
	 * @return
	 */
	List<AmountLimit> getAmountLimitByMerchantNo(String merchantNo);

}

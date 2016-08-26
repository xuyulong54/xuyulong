package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.AmountLimitDao;
import wusc.edu.pay.facade.limit.entity.AmountLimit;


@Repository(value="amountLimitDao")
public class AmountLimitDaoImpl extends BaseDaoImpl<AmountLimit> implements AmountLimitDao{
	/**
	 * 根据商户编号查询交易金额限制
	 * @param merchantNo
	 * @return
	 */
	public List<AmountLimit> getAmountLimitByMerchantNo(String merchantNo){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		return super.getSqlSession().selectList(this.getStatement("getAmountLimitByMerchantNo"),params);
	}
}

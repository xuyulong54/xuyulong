package wusc.edu.pay.core.boss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.boss.dao.MerchantSalesDao;
import wusc.edu.pay.facade.boss.entity.MerchantSales;


@Repository("merchantSalesDao")
public class MerchantSalesDaoImpl extends BaseDaoImpl<MerchantSales> implements MerchantSalesDao {

	
	public MerchantSales getByMerchantNo(String merchantNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		return super.getBy(map);
	}

	@Override
	public List<MerchantSales> listByCondition(Map<String, Object> paramMap) {
		return this.listBy(paramMap);
	}
	
}

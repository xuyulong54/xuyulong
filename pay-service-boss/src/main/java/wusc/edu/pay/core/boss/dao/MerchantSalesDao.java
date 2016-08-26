package wusc.edu.pay.core.boss.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.boss.entity.MerchantSales;


public interface MerchantSalesDao extends BaseDao<MerchantSales>{

	MerchantSales getByMerchantNo(String merchantNo);

	List<MerchantSales> listByCondition(Map<String, Object> paramMap);
	
}

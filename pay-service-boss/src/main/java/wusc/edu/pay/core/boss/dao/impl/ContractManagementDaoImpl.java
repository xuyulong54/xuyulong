package wusc.edu.pay.core.boss.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.boss.dao.ContractManagementDao;
import wusc.edu.pay.facade.boss.entity.ContractManagement;

@Repository("contractManagementDao")
public class ContractManagementDaoImpl extends BaseDaoImpl<ContractManagement> implements ContractManagementDao{

	/**
	 * 根据ID查找对象.
	 * 
	 * @param merchantId
	 *            
	 * @return ContractManagement.
	 */
	@Override
	public List<ContractManagement> getByMerchantId(String merchantId) {
		return super.getSessionTemplate().selectList(getStatement("getByrelationId"), merchantId);
	}

	@Override
	public ContractManagement getByUserNo(String merchantNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", merchantNo);
		return super.getBy(map);
	}
	
	
}

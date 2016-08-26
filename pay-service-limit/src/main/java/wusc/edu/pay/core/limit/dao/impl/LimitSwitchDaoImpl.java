package wusc.edu.pay.core.limit.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.limit.dao.LimitSwitchDao;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;


@Repository(value="limitSwitchDao")
public class LimitSwitchDaoImpl extends BaseDaoImpl<LimitSwitch> implements LimitSwitchDao{
	
	/***
	 * 查询所有有效的支付产品列表
	 * @return
	 */
	public List<LimitSwitch> listAllProduct() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", 100);
		
		return this.getSqlSession().selectList(this.getStatement("getAllPayProduct"), paramMap);
	}
	
	/**
	 * 根据支付产品编号查找支付方式
	 * @param payProductCode
	 * @return
	 */
	public List<LimitSwitch> findPayWayByPayProductCode(String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payProductCode", payProductCode);
		return this.getSqlSession().selectList(this.getStatement("getPayWayByPayProduct"), paramMap);
	}
	
}

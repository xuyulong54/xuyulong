package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.PayProductSwitchDao;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;


/**
 * ClassName: PayProductSwitchDaoImpl <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:22:13 <br/>
 * 
 * @author laich
 */
@Repository(value="payProductSwitchDao")
public class PayProductSwitchDaoImpl extends BaseDaoImpl<PayProductSwitch> implements PayProductSwitchDao {

	/**
	 * 根据规则 ID 查出 PayProductSwitch 集合
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> findPayProductSwitchByRuleId(Long payRuleId) {
		Map<String ,Object> paramMap  = new HashMap<String ,Object> ();
		paramMap.put("payRuleId", payRuleId);
		return  super.listBy(paramMap);
	}

	public List<PayProductSwitch> findPayProductSwitchByPayProductCode(String payProductCode) {
		Map<String ,Object> paramMap  = new HashMap<String ,Object> ();
		paramMap.put("payProductCode", payProductCode);
		return  super.listBy(paramMap);
	}

	/***
	 * 根据规则ID和产品编号查询支付产品开关表
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	public PayProductSwitch getProductSwitchByRuleIdAndProductCode(Long ruleId, String productCode) {
		Map<String ,Object> paramMap  = new HashMap<String ,Object> ();
		paramMap.put("payProductCode", productCode);
		paramMap.put("payRuleId", ruleId);
		return super.getBy(paramMap);
	}
	
}

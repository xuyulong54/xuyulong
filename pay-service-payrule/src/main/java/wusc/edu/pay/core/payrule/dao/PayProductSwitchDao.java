package wusc.edu.pay.core.payrule.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;


public interface PayProductSwitchDao extends BaseDao<PayProductSwitch>{

	List<PayProductSwitch> findPayProductSwitchByRuleId(Long payRuleId);

	List<PayProductSwitch> findPayProductSwitchByPayProductCode(String payProductCode);

	/***
	 * 根据规则ID和产品编号查询支付产品开关表
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	PayProductSwitch getProductSwitchByRuleIdAndProductCode(Long ruleId, String productCode);

}

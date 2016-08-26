package wusc.edu.pay.core.settlement.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.settlement.entity.SettRule;


public interface SettRuleDao extends BaseDao<SettRule> {

	/**
	 * 根据账户编号查询结算规则
	 * @param accountNo
	 * @return
	 */
	SettRule getSettRuleByAccountNo(String accountNo);


}

package wusc.edu.pay.core.settlement.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.settlement.dao.SettRuleDao;
import wusc.edu.pay.facade.settlement.entity.SettRule;


@Repository("settRuleDao")
public class SettRuleDaoImpl extends BaseDaoImpl<SettRule> implements SettRuleDao {

	/**
	 * 根据账户编号查询结算规则
	 * @param accountNo
	 * @return
	 */
	public SettRule getSettRuleByAccountNo(String accountNo){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("accountNo", accountNo);
		return this.getBy(param);
	}
}

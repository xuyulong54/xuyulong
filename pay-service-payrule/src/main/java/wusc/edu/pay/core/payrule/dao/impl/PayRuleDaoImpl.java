package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.PayRuleDao;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;

/**
 * ClassName: PayRuleDaoImpl <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:23:23 <br/>
 * 
 * @author laich
 */
@Repository(value="payRuleDao")
public class PayRuleDaoImpl extends BaseDaoImpl<PayRule> implements PayRuleDao {

	/**
	 * 根据支付规则名称获取支付规则.<br/>
	 * @param ruleName 支付规则名称.<br/>
	 * @return PayRule.
	 */
	public PayRule getByRuleName(String ruleName) {
		return super.getSessionTemplate().selectOne(getStatement("getByRuleName"), ruleName);
	}

	/**
	 * 根据支付规则名称获取支付规则,并且支付规则的ID不等于ruleId.<br/>
	 * @param ruleName 支付规则名称.<br/>
	 * @param ruleId 规则ID.<br/>
	 * @return PayRule or null .
	 */
	public PayRule getByRuleNameNotEqualsRuleId(String ruleName, Long ruleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleName", ruleName);
		paramMap.put("ruleId", ruleId);
		return super.getSessionTemplate().selectOne(getStatement("getByRuleNameNotEqualsRuleId"), paramMap);
	}

	/***
	 * 查询支付规则下绑定的支付产品
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> listProductByRuleId(Long ruleId) {
		return super.getSessionTemplate().selectList("listProductByRuleId", ruleId);
	}


}

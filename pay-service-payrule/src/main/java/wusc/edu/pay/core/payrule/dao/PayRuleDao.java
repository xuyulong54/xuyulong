package wusc.edu.pay.core.payrule.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;


/**
 * 
 * @描述: 支付规则表数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-6-30,下午1:56:15
 * @版本: V1.0
 *
 */
public interface PayRuleDao extends BaseDao<PayRule>{

	/**
	 * 根据支付规则名称获取支付规则.<br/>
	 * @param ruleName 支付规则名称.<br/>
	 * @return PayRule or null.
	 */
	PayRule getByRuleName(String ruleName);

	/**
	 * 根据支付规则名称获取支付规则,并且支付规则的ID不等于ruleId.<br/>
	 * @param ruleName 支付规则名称.<br/>
	 * @param ruleId 规则ID.<br/>
	 * @return PayRule or null .
	 */
	PayRule getByRuleNameNotEqualsRuleId(String ruleName, Long ruleId);

	/***
	 * 查询支付规则下绑定的支付产品
	 * @param ruleId
	 * @return
	 */
	List<BindSwitchVo> listProductByRuleId(Long ruleId);

}

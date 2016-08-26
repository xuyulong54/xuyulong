package wusc.edu.pay.core.payrule.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;



public interface UserPayRuleSettingDao extends BaseDao<UserPayRuleSetting>{

	/***
	 * 根据支付规则ID和用户编号查询用户支付规则关联表
	 * @param payRuleId
	 * @param userNo
	 * @return
	 */
	UserPayRuleSetting getRuleSetByRuleAndUserNo(Long payRuleId, String userNo);

}

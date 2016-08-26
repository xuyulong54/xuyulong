package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.UserPayRuleSettingDao;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;

/**
 * ClassName: UserPayRuleSettingDaoImpl <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:25:15 <br/>
 * 
 * @author laich
 */
@Repository(value="userPayRuleSettingDao")
public class UserPayRuleSettingDaoImpl extends BaseDaoImpl<UserPayRuleSetting> implements UserPayRuleSettingDao {

	/***
	 * 根据支付规则ID和用户编号查询用户支付规则关联表
	 * @param payRuleId
	 * @param userNo
	 * @return
	 */
	public UserPayRuleSetting getRuleSetByRuleAndUserNo(Long payRuleId, String userNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payRuleId", payRuleId);
		paramMap.put("userNo", userNo);
		return super.getBy(paramMap);
	}
	
	
	
}

package wusc.edu.pay.facade.payrule.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.payrule.biz.PayRuleBiz;
import wusc.edu.pay.core.payrule.dao.PayRuleDao;
import wusc.edu.pay.core.payrule.dao.UserPayRuleSettingDao;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.service.PayRuleFacade;


@Component("payRuleFacade")
public class PayRuleFacadeImpl implements PayRuleFacade {
	
	@Autowired
	private PayRuleDao payRuleDao;
	@Autowired
	private UserPayRuleSettingDao userPayRuleSettingDao;
	@Autowired
	private PayRuleBiz payRuleBiz;

	/**
	 * 查询并分页列表支付规则信息.<br/>
	 * @param pageParam 分页参数.<br/>
	 * @param ruleName 规则名称.<br/>
	 * @param ruleType 规则类型.<br/>
	 * @return pageBean .
	 */
	@Override
	public PageBean listPayRuleForPage(PageParam pageParam, String ruleName, Integer ruleType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleName", ruleName);
		paramMap.put("ruleType", ruleType);
		return payRuleDao.listPage(pageParam, paramMap);
	}

	/**
	 * 新增支付规则.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回数据库主键自增的ID（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	@Override
	public String createPayRule(String ruleName, Integer ruleType, String description) {
		return payRuleBiz.createPayRule(ruleName, ruleType, description);
	}

	/**
	 * 根据支付规则ID获取支付规则信息.<br/>
	 * @param ruleId 支付规则ID.<br/>
	 * @return PayRule or null;
	 */
	@Override
	public PayRule getPayRuleById(Long ruleId) {
		return payRuleDao.getById(ruleId);
	}

	/**
	 * 修改支付规则.<br/>
	 * @param ruleId 支付规则ID（不能为空）.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回更新结果（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	@Override
	public String updatePayRule(Long ruleId, String ruleName, Integer ruleType, String description) {
		return payRuleBiz.updatePayRule(ruleId, ruleName, ruleType, description);
	}

	/**
	 * 查出所有的支付规则 
	 * @return List<PayRule> 
	 */
	public List<PayRule> listPayRules() {
		return payRuleBiz.listPayRules();
	}
	
	/***
	 * 查询支付规则下绑定的支付产品
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> listProductByRuleId(Long ruleId) {
		return payRuleBiz.listProductByRuleId(ruleId);
	}

	/***
	 * 新增用户支付规则关联表
	 * @param setting
	 * @return
	 */
	public long insert(UserPayRuleSetting setting) {
		return userPayRuleSettingDao.insert(setting);
	}

	/***
	 * 根据支付规则ID和用户编号查询用户支付规则关联表
	 * @param payRuleId
	 * @param userNo
	 * @return
	 */
	public UserPayRuleSetting getRuleSetByRuleAndUserNo(Long payRuleId, String userNo) {
		return userPayRuleSettingDao.getRuleSetByRuleAndUserNo(payRuleId, userNo);
	}

	/***
	 * 更新用户支付规则关联表
	 * @return
	 */
	public long updateRuleSet(UserPayRuleSetting setting) {
		return userPayRuleSettingDao.update(setting);
	}


}

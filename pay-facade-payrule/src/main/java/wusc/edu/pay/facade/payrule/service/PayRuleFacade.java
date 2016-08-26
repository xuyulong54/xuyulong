package wusc.edu.pay.facade.payrule.service;

import java.util.List;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * ClassName: PayRuleFacade  支付规则 接口 包括 UserPayRuleSetting PayRule <br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:06:46 <br/>
 * 
 * @author laich
 */
public interface PayRuleFacade {

	/**
	 * 查询并分页列表支付规则信息.<br/>
	 * @param pageParam 分页参数.<br/>
	 * @param ruleName 规则名称.<br/>
	 * @param ruleType 规则类型.<br/>
	 * @return pageBean .
	 */
	public PageBean listPayRuleForPage(PageParam pageParam, String ruleName, Integer ruleType) throws PayruleBizException;;

	/**
	 * 新增支付规则.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回数据库主键自增的ID（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	public String createPayRule(String ruleName, Integer ruleType, String description) throws PayruleBizException;;

	/**
	 * 根据支付规则ID获取支付规则信息.<br/>
	 * @param ruleId 支付规则ID.<br/>
	 * @return PayRule or null;
	 */
	public PayRule getPayRuleById(Long ruleId) throws PayruleBizException;;

	/**
	 * 修改支付规则.<br/>
	 * @param ruleId 支付规则ID（不能为空）.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回更新结果（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	public String updatePayRule(Long ruleId, String ruleName, Integer ruleType, String description) throws PayruleBizException;;
	
	/**
	 * 查出所有的支付规则 
	 * @return
	 */
	public List<PayRule>  listPayRules() throws PayruleBizException;;
	
	
	/***
	 * 查询支付规则下绑定的支付产品
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> listProductByRuleId(Long ruleId);

	/***
	 * 新增用户支付规则关联表
	 * @param setting
	 * @return
	 */
	public long insert(UserPayRuleSetting setting) throws PayruleBizException;

	/***
	 * 根据支付规则ID和用户编号查询用户支付规则关联表
	 * @param payRuleId
	 * @param userNo
	 * @return
	 */
	public UserPayRuleSetting getRuleSetByRuleAndUserNo(Long payRuleId, String userNo);

	/***
	 * 更新用户支付规则关联表
	 * @param setting
	 * @return
	 */
	public long updateRuleSet(UserPayRuleSetting setting) throws PayruleBizException;
}

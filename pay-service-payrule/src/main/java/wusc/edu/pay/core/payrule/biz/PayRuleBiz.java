package wusc.edu.pay.core.payrule.biz;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.payrule.dao.PayRuleDao;
import wusc.edu.pay.core.payrule.dao.UserPayRuleSettingDao;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.enums.PayRuleTypeEnum;


/**
 * ClassName: PayRuleBiz 对应 接口 PayRuleFacade包括 UserPayRuleSetting PayRule<br/>
 * Function:  <br/>
 * date: 2014-6-27 上午9:32:36 <br/>
 * 
 * @author laich
 */
@Transactional(rollbackFor = Exception.class)
@Component("payRuleBiz")
public class PayRuleBiz {

	@Autowired
	private PayRuleDao payRuleDao;
	@Autowired
	private UserPayRuleSettingDao  userPayRuleSettingDao;
	
	/**
	 * 新增支付规则.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回数据库主键自增的ID（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public String createPayRule(String ruleName, Integer ruleType, String description) {
		String result = "";
		// 校验规则名称
		if (StringUtil.isBlank(ruleName)){
			result += "规则名称不能为空,";
		}else if (StrUtil.strLengthCn(ruleName) > 100){
			result += "规则名称长度不能多于100字符,";
		}
		// 校验规则类型
		if (ruleType == null){
			result += "规则类型不能为空,";
		}else if (!(ruleType.intValue() == PayRuleTypeEnum.SYSTEM.getValue() || ruleType.intValue() == PayRuleTypeEnum.CUSTOM.getValue())){
			result += "规则类型值不正确,";
		}
		// 校验规则长度
		if (StringUtil.isNotBlank(description) && StrUtil.strLengthCn(description) > 300){
			result += "规则描述长度不能多于300字符,";
		}
		
		// 校验规则名称是否存在
		PayRule checkRule = payRuleDao.getByRuleName(ruleName);
		if (checkRule != null){
			result += "规则名称已存在,";
		}
		
		// 参数校验通过，保存数据，返回ID值
		if (StringUtil.isBlank(result)){
			PayRule rule = new PayRule();
			rule.setRuleName(ruleName);
			rule.setRuleType(ruleType);
			rule.setDescription(description);
			long id = payRuleDao.insert(rule);
			result = String.valueOf(id);
		}
		
		return result;
	}
	
	/**
	 * 修改支付规则.<br/>
	 * @param ruleId 支付规则ID（不能为空）.<br/>
	 * @param ruleName 规则名称（不能为空，最大字符长度为100）.<br/>
	 * @param ruleType 规则类型（不能为空，取PayRuleTypeEnum枚举类中的值）.<br/>
	 * @param description 规则描述（可以为空，最大字符长度为300）.<br/>
	 * @return 添加成功:返回更新结果（纯数字符串）,添加失败:返回错误信息（非纯数字符串）.
	 */
	public String updatePayRule(Long ruleId, String ruleName, Integer ruleType, String description) {
		String result = "";
		
		PayRule rule = null;
		
		if (ruleId == null){
			result += "规则ID不能为空,";
		}else{
			rule = payRuleDao.getById(ruleId);
			if (rule == null){
				result += "无法获取要修改的规则信息,";
			}
		}

		// 校验规则名称
		if (StringUtil.isBlank(ruleName)){
			result += "规则名称不能为空,";
		}else if (StrUtil.strLengthCn(ruleName) > 100){
			result += "规则名称长度不能多于100字符,";
		}
		// 校验规则类型
		if (ruleType == null){
			result += "规则类型不能为空,";
		}else if (!(ruleType.intValue() == PayRuleTypeEnum.SYSTEM.getValue() || ruleType.intValue() == PayRuleTypeEnum.CUSTOM.getValue())){
			result += "规则类型值不正确,";
		}
		// 校验规则长度
		if (StringUtil.isNotBlank(description) && StrUtil.strLengthCn(description) > 300){
			result += "规则描述长度不能多于300字符,";
		}
		
		// 校验规则名称是否存在(修改后不要跟别的名称冲突)
		PayRule checkRule = payRuleDao.getByRuleNameNotEqualsRuleId(ruleName, ruleId);
		if (checkRule != null){
			result += "规则名称已存在,";
		}
		
		// 参数校验通过，保存数据，返回ID值
		if (StringUtil.isBlank(result)){
			rule.setRuleName(ruleName);
			rule.setRuleType(ruleType);
			rule.setDescription(description);
			long id = payRuleDao.update(rule);
			result = String.valueOf(id);
		}
		
		return result;
	}
	
	/**
	 * 新增用户与规则设置记录
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createUserPayRuleSetting(UserPayRuleSetting entity){
		return userPayRuleSettingDao.insert(entity);
	}

	public List<PayRule> listPayRules() {
		return payRuleDao.listBy(null);
	}

	/***
	 * 查询支付规则下绑定的支付产品 ,支付方式以（x,x2,x3）形式输出
	 * @param ruleId
	 * @return
	 */
	@SuppressWarnings("unused")
	public List<BindSwitchVo> listProductByRuleId(Long ruleId) {
		
		List<BindSwitchVo>  bsvos = payRuleDao.listProductByRuleId(ruleId);
		List<BindSwitchVo>  vos = new ArrayList<BindSwitchVo>();
		//group by  payProductCode
		for (int i = 0; i < bsvos.size(); i++) {
			BindSwitchVo bvo = (BindSwitchVo) bsvos.get(i);
			
			//定义一个变量 isAdd，boolean
			boolean isAdd = true;
			for (Iterator iterator = vos.iterator(); iterator.hasNext();) {
				BindSwitchVo bindSwitchVo = (BindSwitchVo) iterator.next();
				
				//如果是同一个支付产品，则拼接支付方式字符串
				if(bindSwitchVo.getPayProductCode().equals(bvo.getPayProductCode())){
					if(!bindSwitchVo.getPayWayCode().contains(bvo.getPayWayCode())){
						bindSwitchVo.setPayWayCode(bindSwitchVo.getPayWayCode()+","+bvo.getPayWayCode());
					}
					isAdd = false;
					break;
				}
			}
			
			if(isAdd){
				vos.add(bvo);
			}
			
		}
		return vos;
	}
}

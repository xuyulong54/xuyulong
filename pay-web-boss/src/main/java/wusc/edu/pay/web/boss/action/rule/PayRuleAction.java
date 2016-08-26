package wusc.edu.pay.web.boss.action.rule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.entity.PayRule;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.UserPayRuleSetting;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.enums.PayProductStatusEnum;
import wusc.edu.pay.facade.payrule.enums.PayRuleTypeEnum;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.payrule.service.PayRuleFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * @描述: 支付规则.
 * @作者: WuShuicheng.
 * @创建: 2014-6-30,上午10:39:12
 * @版本: V1.0
 * 
 */
public class PayRuleAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4428404312490122533L;
	private static Log log = LogFactory.getLog(PayRuleAction.class);
	@Autowired
	private PayRuleFacade payRuleFacade;
	@Autowired
	private PayProductFacade payProductFacade;
	@Autowired
	private PayWayFacade payWayFacade;

	/**
	 * 查询并分页列表支付规则.<br/>
	 * 
	 * @return listPayRule.
	 */
	@Permission("payrule:payrule:view")
	public String listPayRule() {
		String ruleName = getString("ruleName"); // 规则名称
		Integer ruleType = getInteger("ruleType"); // 规则类型

		super.pageBean = payRuleFacade.listPayRuleForPage(getPageParam(),
				ruleName, ruleType);
		this.pushData(pageBean);

		// 回显查询参数
		this.putData("ruleName", ruleName);
		this.putData("ruleType", ruleType);
		this.putData("PayRuleTypeEnum", PayRuleTypeEnum.toMap()); // 支付规则类型枚举

		return "listPayRule";
	}

	/**
	 * 进入添加支付规则页面.
	 * 
	 * @return addPayRule.
	 */
	@Permission("payrule:payrule:add")
	public String addPayRuleUI() {
		this.putData("PayRuleTypeEnum", PayRuleTypeEnum.toList()); // 支付规则类型枚举
		return "addPayRule";
	}

	/**
	 * 添加支付规则.
	 * 
	 * @return addPayRule.
	 */
	@Permission("payrule:payrule:add")
	public String addPayRule() {
		String ruleName = getString("ruleName"); // 规则名称
		Integer ruleType = getInteger("ruleType"); // 规则类型
		String description = getString("description"); // 规则描述

		String result = payRuleFacade.createPayRule(ruleName, ruleType,
				description);
		this.logSave("添加支付规则，规则名称："+ruleName);
		// 判断是否添加成功
		if (StringUtils.isNumeric(result)) {
			return operateSuccess("添加成功");
		} else {
			return operateError(result); // 返回错误提示
		}
	}

	/***
	 * 商户绑定支付规则
	 * 
	 * @return
	 */
	public String bindPayRuleUI() {
		Long merchantNo = getLong("merchantNo");
		if (merchantNo == null)
			return operateError("商户信息为空！");
		this.putData("merchantNo", merchantNo);
		// 查询所有激活的支付规则
		List<PayRule> payRuleList = payRuleFacade.listPayRules();
		this.putData("payRuleList", payRuleList);

		// 根据支付规则ID和用户编号查询用户支付规则关联表
		UserPayRuleSetting setting = payRuleFacade.getRuleSetByRuleAndUserNo(null, String.valueOf(merchantNo));
		if (setting != null) {
			Long payRuleId = setting.getPayRuleId();
			List<BindSwitchVo> payProductList = payRuleFacade.listProductByRuleId(payRuleId);
			StringBuffer printOutMsg = new StringBuffer();
			for (BindSwitchVo vo : payProductList) {
				String[] str = vo.getPayWayCode().split(",");
				StringBuffer buffer = new StringBuffer();
				for(int i=0; i<str.length; i++){
					if(i+1==str.length){
						buffer.append(str[i]);
					}else{
						buffer.append(str[i]).append(",");
					}
					if((i+1) % 4 == 0){
						buffer.append("<br/>");
					}
					
				}
				printOutMsg.append(vo.getPayProductName() + "：" + buffer).append("<br/>");
			}
			this.putData("printOutMsg", printOutMsg);
			this.putData("payRuleId", payRuleId);
		}

		return "bindPayRule";
	}

	/***
	 * 商户绑定支付规则
	 * 
	 * @return
	 */
	public String bindPayRule() {
		Long payRuleId = getLong("payRuleId");
		String userNo = getString("userNo");
		if (payRuleId == null || StringUtils.isEmpty(userNo)) {
			return operateError("商户编号为空，或者未选择支付规则！");
		}
		// 根据支付规则ID和用户编号查询用户支付规则关联表
		UserPayRuleSetting setting = payRuleFacade.getRuleSetByRuleAndUserNo(
				null, userNo);
		if (setting == null) {
			setting = new UserPayRuleSetting();
			setting.setPayRuleId(payRuleId);
			setting.setUserNo(userNo);
			long settingId = payRuleFacade.insert(setting);
			if (settingId > 0) {
				this.logSave("为商户【"+userNo+"】绑定支付规则，支付规则ID："+payRuleId);
				return operateSuccess();
			} else {
				return operateError("新增失败！");
			}
		} else {
			setting.setPayRuleId(payRuleId);
			setting.setUserNo(userNo);
			setting.setModifyTime(new Date());
			long rows = payRuleFacade.updateRuleSet(setting);
			this.logSave("修改商户【"+userNo+"】绑定的支付规则，支付规则ID："+payRuleId);
			if (rows > 0) {
				return operateSuccess();
			} else {
				return operateError("新增失败！");
			}
		}
	}

	/***
	 * 根据支付规则ID查询下面的支付产品和支付方式
	 */
	public void getPayProductInfo() {
		Long ruleId = getLong("ruleId");
		if (ruleId == null) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请选择支付规则！");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}

		/*Map<String, Object> putPageMap = new HashMap<String, Object>();
		List<BindSwitchVo> payProductList = payRuleFacade.listProductByRuleId(ruleId);
		StringBuffer printOutMsg = new StringBuffer();
		for (BindSwitchVo vo : payProductList) {
			String[] str = vo.getPayWayCode().split(",");
			StringBuffer buffer = new StringBuffer();
			for(int i=0; i<str.length; i++){
				if(i+1==str.length){
					buffer.append(str[i]);
				}else{
					buffer.append(str[i]).append(",");
				}
				if((i+1) % 4 == 0){
					buffer.append("<br/>");
				}
			}
			printOutMsg.append(vo.getPayProductName() + "：" + buffer).append("<br/>");
		}
		putPageMap.put("printOutMsg", printOutMsg.toString());*/
		getOutputMsg().put("STATE", "SUCCESS");
		getOutputMsg().put("MSG", getPayProduct(ruleId));
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

	}
	
	//支付产品和支付方式
	public Map<String, Object> getPayProduct(Long ruleId){
		Map<String, Object> putPageMap = new HashMap<String, Object>();
		List<BindSwitchVo> payProductList = payRuleFacade.listProductByRuleId(ruleId);
		StringBuffer printOutMsg = new StringBuffer();
		for (BindSwitchVo vo : payProductList) {
			String[] str = vo.getPayWayCode().split(",");
			StringBuffer buffer = new StringBuffer();
			for(int i=0; i<str.length; i++){
				if(i+1==str.length){
					buffer.append(str[i]);
				}else{
					buffer.append(str[i]).append(",");
				}
				if((i+1) % 4 == 0){
					buffer.append("<br/>");
				}
			}
			printOutMsg.append(vo.getPayProductName() + "：" + buffer).append("<br/>");
		}
		putPageMap.put("printOutMsg", printOutMsg.toString());
		return putPageMap;
	}

	/**
	 * 进入支付规则修改页面.
	 * 
	 * @return addPayRule.
	 */
	@Permission("payrule:payrule:edit")
	public String editPayRuleUI() {

		Long ruleId = getLong("id");
		if (ruleId == null) {
			return operateError("ID不能为空");
		}

		PayRule rule = payRuleFacade.getPayRuleById(ruleId);
		if (rule == null) {
			return operateError("无法获取要修改的支付规则信息");
		}

		this.putData("rule", rule);
		this.putData("PayRuleTypeEnum", PayRuleTypeEnum.toList()); // 支付规则类型枚举

		return "editPayRule";
	}

	/**
	 * 添加支付规则.
	 * 
	 * @return addPayRule.
	 */
	@Permission("payrule:payrule:edit")
	public String editPayRule() {
		Long payRuleId = getLong("payRuleId"); // 规则ID
		String ruleName = getString("ruleName"); // 规则名称
		Integer ruleType = getInteger("ruleType"); // 规则类型
		String description = getString("description"); // 规则描述

		String result = payRuleFacade.updatePayRule(payRuleId, ruleName,
				ruleType, description);
		// 判断是否修改成功
		if (StringUtils.isNumeric(result)) {
			this.logEdit("修改支付规则，规则名称："+ruleName);
			return operateSuccess("修改成功");
		} else {
			this.logEditError("修改支付规则，规则名称："+ruleName);
			return operateError(result); // 返回错误提示
		}
	}

	/***
	 * 支付规则绑定支付产品
	 * 
	 * @return
	 */
	public void addProductAndRuleSwitch() {
		String productCode = getString("productCode");
		Long ruleId = getLong("ruleId");
		if (ruleId == null) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "支付规则为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (StringUtils.isEmpty(productCode)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "支付产品为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		try {
			PayProductSwitch productSwitch = payProductFacade
					.getProductSwitchByRuleIdAndProductCode(ruleId, productCode);
			if (productSwitch == null) { // 新增
				productSwitch = new PayProductSwitch();
				productSwitch.setPayProductCode(productCode);
				productSwitch.setPayRuleId(ruleId);
				productSwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());
				payWayFacade.createPayProductSwitch(productSwitch);

				this.logSave("将支付规则（ID）【"+ruleId+"】绑定到支付产品（编号）【"+productCode+"】");
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "添加成功!");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
			} else {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "该支付规则以及绑定了该支付产品，不能重复绑定！");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
			}
		} catch (BossBizException e) {
			this.logSaveError("将支付规则（ID）【"+ruleId+"】绑定到支付产品（编号）【"+productCode+"】");
			log.error("支付规则绑定支付产品失败！", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", e.getMsg() + "，异常编号：" + e.getCode());
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		} catch (Exception e) {
			this.logSaveError("将支付规则（ID）【"+ruleId+"】绑定到支付产品（编号）【"+productCode+"】");
			log.error("支付规则绑定支付产品失败！", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请联系管理员");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
	}

	/***
	 * 取消规则绑定支付产品
	 */
	public void deleteProductAndRuleSwitch() {
		String productCode = getString("productCode");
		Long ruleId = getLong("ruleId");
		if (ruleId == null) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "支付规则为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if (StringUtils.isEmpty(productCode)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "支付产品为空");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		try {
			PayProductSwitch productSwitch = payProductFacade
					.getProductSwitchByRuleIdAndProductCode(ruleId, productCode);
			if (productSwitch == null) { // 新增
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG","该支付规则没有绑定产品编号为[" + productCode + "]的产品!");
				outPrint(getHttpResponse(),JSONObject.fromObject(getOutputMsg()));
				return;
			} else {
				payProductFacade.deteleProductSwitch(productSwitch);
				this.logEdit("将支付产品（编号）【"+productCode+"】的支付规则（ID）【"+ruleId+"】取消绑定");
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "取消成功!");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
			}
		} catch (BossBizException e) {
			log.error("支付规则绑定支付产品失败！", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", e.getMsg() + "，异常编号：" + e.getCode());
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		} catch (Exception e) {
			log.error("支付规则绑定支付产品失败！", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请联系管理员");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
	}

	/***
	 * 支付规则下绑定支付产品
	 */
	public void addPayWaySwitch() {
		try {
			Long ruleId = getLong("ruleId");
			String productCode = getString("productCode");
			String payWayCode = getString("payWayCode");

			if (ruleId == null || StringUtils.isEmpty(productCode)
					|| StringUtils.isEmpty(payWayCode)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "绑定失败，请稍后再试！");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
				return;
			}

			long payWayId = payWayFacade.createPayWaySwitch(ruleId,
					productCode, payWayCode);
			this.logSave("将支付规则（ID）【"+ruleId+"】绑定到支付产品（编号）【"+productCode+"】");
			if (payWayId > 0) {
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "绑定成功！");
			} else {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "绑定失败，请稍后再试！");
			}
		} catch (BossBizException e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "服务异常，请稍后再试！");
		} catch (Exception e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 支付规则下删除支付产品
	 */
	public void deletePayWaySwitch() {
		try {
			Long ruleId = getLong("ruleId");
			String productCode = getString("productCode");
			String payWayCode = getString("payWayCode");

			if (ruleId == null || StringUtils.isEmpty(productCode)
					|| StringUtils.isEmpty(payWayCode)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "解绑失败，请稍后再试！");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
				return;
			}
			// 查询支付方式开关表是否存在该数据
			PayWaySwitch payWaySwitch = payWayFacade
					.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId,
							productCode, payWayCode);
			if (payWaySwitch == null) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "没有绑定该支付方式，无法取消绑定");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
				return;
			}
			// 删除绑定
			long updRow = payWayFacade.deletePayWaySwitch(payWaySwitch.getId());
			if (updRow > 0) {
				this.logDelete("解绑支付规则（ID）【"+ruleId+"】下的支付产品（编号）【"+productCode+"】");
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "解绑成功！");
			} else {
				this.logDeleteError("解绑支付规则（ID）【"+ruleId+"】下的支付产品（编号）【"+productCode+"】");
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "解绑失败，请稍后再试！");
			}
		} catch (BossBizException e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "服务异常，请稍后再试！");
		} catch (Exception e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 批量绑定支付方式
	 */
	public void addPayWaySwitchList() {
		try {
			String payWayCodes = getString("payWayCodes");
			if (StringUtils.isEmpty(payWayCodes)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "绑定失败，请选择需要绑定的支付方式！");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
				return;
			}
			// 批量创建支付规则下绑定支付产品和支付方式
			long result = payWayFacade
					.createPayWaySwitchAndPayProductSwitch(payWayCodes);
			if (result > 0) {
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "绑定成功！");
			} else {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "绑定失败，请稍后再试！");
			}
		} catch (BossBizException e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", e.getMessage());
		} catch (Exception e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 批量解绑支付方式
	 */
	public void deletePayWaySwitchList() {
		try {
			String payWayCodes = getString("payWayCodes");
			if (StringUtils.isEmpty(payWayCodes)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "解绑失败，请选择需要解绑的支付方式！");
				outPrint(getHttpResponse(),
						JSONObject.fromObject(getOutputMsg()));
				return;
			}
			// 批量删除支付规则下绑定的支付产品和支付方式
			long rowNum = payWayFacade
					.deletePayWaySwitchAndPayProductSwitch(payWayCodes);
			if (rowNum > 0) {
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "解绑成功！");
			} else {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "解绑失败，请稍后再试！");
			}
		} catch (BossBizException e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", e.getMessage());
		} catch (Exception e) {
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 支付绑定支付产品和支付方式
	 * 
	 * @return
	 */
	public String bindAllSwitchUI() {
		Long ruleId = getLong("ruleId");
		if (ruleId == null)
			return operateError("该支付规则不存在，请刷新页面重试！");
		this.putData("ruleId", ruleId);
		// 查询出该支付方式下所有的支付产品，并且区分哪些已经绑定
		List<BindSwitchVo> bindSwitchVoList = payWayFacade
				.findBindSwitchList(ruleId);

		this.putData("bindSwitchVoList", bindSwitchVoList);

		return "bindAllSwitchUI";
	}

}

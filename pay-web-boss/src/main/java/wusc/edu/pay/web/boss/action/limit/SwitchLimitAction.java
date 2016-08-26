package wusc.edu.pay.web.boss.action.limit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;
import wusc.edu.pay.facade.limit.service.BizFunctionSwitchFacade;
import wusc.edu.pay.facade.limit.service.LimitSwitchFacade;
import wusc.edu.pay.facade.limit.service.PayProductSwitchFacade;
import wusc.edu.pay.facade.limit.service.PayWaySwitchFacade;
import wusc.edu.pay.facade.limit.service.SwitchLimitPackFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * @描述: 开关限制包Action类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-7, 上午11:06:27 .
 * @版本: V1.0 .
 */
public class SwitchLimitAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
 
	private static Log log = LogFactory.getLog(SwitchLimitAction.class);
	@Autowired
	private SwitchLimitPackFacade switchLimitPackFacade;

	@Autowired
	private LimitSwitchFacade limitSwitchFacade;

	@Autowired
	private BizFunctionSwitchFacade bizFunctionSwitchFacade;

	@Autowired
	private PayProductSwitchFacade payProductSwitchFacade;

	@Autowired
	private PayWaySwitchFacade payWaySwitchFacade;

	/**
	 * 查询开关限制
	 * 
	 * @return
	 */
	public String listSwitchLimit() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String name = getString("name");
		paramMap.put("name", name);
		super.pageBean = switchLimitPackFacade.listPage(getPageParam(), paramMap);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "switchLimitList";
	}

	/**
	 * 添加开关限制页面
	 * 
	 * @return
	 */
	@Permission("limit:switch:edit")
	public String addSwitchLimitUI() {
		return "switchLimitAdd";
	}

	/**
	 * 添加开关限制
	 * 
	 * @return
	 */
	@Permission("limit:switch:edit")
	public String addSwitchLimit() {
		SwitchLimitPack switchLimitPack = new SwitchLimitPack();
		String name = getString("name");
		String description = getString("description");
		SwitchLimitPack existSwitchLimitPack = switchLimitPackFacade.getByName(name);
		if (existSwitchLimitPack != null) {
			return this.operateError("已存在同名的限制包！");
		}
		switchLimitPack.setDescription(description);
		switchLimitPack.setName(name);
		switchLimitPackFacade.saveSwitchLimitPack(switchLimitPack);
		this.logSave("添加开关限制成功:" + switchLimitPack.getName());
		return this.operateSuccess("添加成功！");
	}

	/**
	 * 修改开关限制页面
	 * 
	 * @return
	 */
	public String editSwitchLimitUI() {
		Long id = getLong("id");
		SwitchLimitPack switchLimitPack = switchLimitPackFacade.getById(id);

		this.pushData(switchLimitPack);
		return "switchLimitEdit";
	}

	/**
	 * 修改开关限制
	 * 
	 * @return
	 */
	@Permission("limit:switch:edit")
	public String editSwitchLimit() {
		Long id = getLong("id");
		SwitchLimitPack switchLimitPack = switchLimitPackFacade.getById(id);
		String name = getString("name");
		String description = getString("description");
		switchLimitPack.setDescription(description);
		switchLimitPack.setName(name);
		switchLimitPackFacade.updateSwitchLimitPack(switchLimitPack);
		this.logEdit("修改开关限制成功:" + switchLimitPack.getName());
		return this.operateSuccess("修改成功！");
	}

	/**
	 * 删除开关限制
	 */
	@Permission("limit:switch:edit")
	public void deleteSwitchLimit() {
		try {
			Long id = getLong("id");
			switchLimitPackFacade.deleteSwitchLimitPack(id);
			this.getOutputMsg().put("STATE", "SUCCESS");
		} catch (Exception e) {
			this.getOutputMsg().put("STATE", "ERROR");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));

	}

	/**
	 * 添加业务功能页面
	 * 
	 * @return
	 */
	public String openBizFunction() {
		Long switchLimitPackId = getLong("switchLimitPackId");
		List<LimitSwitch> bizFunctionList = limitSwitchFacade.findBizFunctionList(switchLimitPackId);
		this.putData("onlinelimitTrxTypeEnumList", LimitTrxTypeEnum.toListSwitchLimit());
		//this.putData("onlinelimitTrxTypeEnumList", LimitTrxTypeEnum.toListForOnlineSwitchLimit());
		this.putData("poslimitTrxTypeEnumList", LimitTrxTypeEnum.toListForPosSwitchLimit());
		this.putData("bizFunctionList", bizFunctionList);
		this.putData("switchLimitPackId", switchLimitPackId);
		return "openBizFunction";
	}

	/**
	 * 添加开关限制页面
	 * 
	 * @return
	 */
	public String bindAllSwitchUI() {
		Long switchLimitPackId = getLong("switchLimitPackId");
		// List<LimitSwitch> bizFunctionList =
		// limitSwitchFacade.findBizFunctionList(switchLimitPackId);
		List<LimitSwitch> payProductList = limitSwitchFacade.findLimitSwitchList(switchLimitPackId);
		// this.putData("bizFunctionList", bizFunctionList);
		this.putData("payProductList", payProductList);
		this.putData("switchLimitPackId", switchLimitPackId);
		return "bindAllSwitchUI";
	}

	/**
	 * 添加业务功能开关
	 */
	public void addBizFunctionSwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String bizFunction = getString("bizFunction");

			if (switchLimitPackId == null || StringUtils.isEmpty(bizFunction)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}
			
			// 如果选择的是POS_PREAUTH(POS预授权),则自动选择（POS预授权，预授权完成撤销，消费撤销，POS充值，预授权完成）
			if (bizFunction.equals(LimitTrxTypeEnum.POS_PREAUTH.name())) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = LimitTrxTypeEnum.toListForPosPreauth();
				for (Map<String, String> map : list) {
					BizFunctionSwitch functionSwitch = new BizFunctionSwitch();
					functionSwitch.setBizFunction(map.get("name"));
					functionSwitch.setSwitchLimitPackId(switchLimitPackId);
					functionSwitch.setStatus(SwitchLimitStatusEnum.ON);
					bizFunctionSwitchFacade.saveBizFunctionSwitch(functionSwitch);
					this.logSave("添加业务功能开关成功：开关限制ID:" + switchLimitPackId +",业务："+functionSwitch.getBizFunction());
				}
			}
			else if (bizFunction.equals(LimitTrxTypeEnum.POS_PAY.name())) {// 如果选择的是POS_PAY(POS消费),则自动选择（POS消费，消费撤销）
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = LimitTrxTypeEnum.toListForPosPay();
				for (Map<String, String> map : list) {
					BizFunctionSwitch functionSwitch = new BizFunctionSwitch();
					functionSwitch.setBizFunction(map.get("name"));
					functionSwitch.setSwitchLimitPackId(switchLimitPackId);
					functionSwitch.setStatus(SwitchLimitStatusEnum.ON);
					bizFunctionSwitchFacade.saveBizFunctionSwitch(functionSwitch);
					this.logSave("添加业务功能开关成功：开关限制ID:" + functionSwitch.getSwitchLimitPackId() +",业务："+functionSwitch.getBizFunction());
				}
			}
			else if (bizFunction.equals(LimitTrxTypeEnum.SETTLEMENT.name())) {// 如果选择的是结算,则自动选择（结算，结算T+0）
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = LimitTrxTypeEnum.toListForSettlement();
				for (Map<String, String> map : list) {
					BizFunctionSwitch functionSwitch = new BizFunctionSwitch();
					functionSwitch.setBizFunction(map.get("name"));
					functionSwitch.setSwitchLimitPackId(switchLimitPackId);
					functionSwitch.setStatus(SwitchLimitStatusEnum.ON);
					bizFunctionSwitchFacade.saveBizFunctionSwitch(functionSwitch);
					this.logSave("添加业务功能开关成功：开关限制ID:" + functionSwitch.getSwitchLimitPackId() +",业务："+functionSwitch.getBizFunction());
				}
			}
			else {
					bizFunctionSwitchFacade.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, bizFunction);
					BizFunctionSwitch functionSwitch = new BizFunctionSwitch();
					functionSwitch.setBizFunction(bizFunction);
					functionSwitch.setSwitchLimitPackId(switchLimitPackId);
					functionSwitch.setStatus(SwitchLimitStatusEnum.ON);
					bizFunctionSwitchFacade.saveBizFunctionSwitch(functionSwitch);
					this.logSave("添加业务功能开关成功：开关限制ID:" + functionSwitch.getSwitchLimitPackId() +",业务："+functionSwitch.getBizFunction());
				}
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "操作成功！");
		} catch (Exception e) {
			this.logSaveError("添加业务功能开关失败，开关限制ID:" + getLong("switchLimitPackId") + ",业务：" +getString("bizFunction"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

	/**
	 * 删除业务功能开关
	 */
	public void deleteBizFunctionSwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String bizFunction = getString("bizFunction");

			if (switchLimitPackId == null || StringUtils.isEmpty(bizFunction)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}
			// 如果取消的是POS_PREAUTH(POS预授权),则自动取消（POS预授权，预授权完成撤销，消费撤销，POS充值，预授权完成）
			/*if (bizFunction.equals(LimitTrxTypeEnum.POS_PREAUTH.name())) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> posPreauthEnumList = LimitTrxTypeEnum.toListForPosPreauth();
				for (Map<String, String> map : posPreauthEnumList) {
					bizFunctionSwitchFacade.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, map.get("name"));
				}
			} else*/ if (bizFunction.equals(LimitTrxTypeEnum.POS_PAY.name())) {// 如果取消的是POS_PAY(POS消费),则取消选择（POS消费，消费撤销）
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = LimitTrxTypeEnum.toListForPosPay();
				for (Map<String, String> map : list) {
					bizFunctionSwitchFacade.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, map.get("name"));
				}
			} 
			else if (bizFunction.equals(LimitTrxTypeEnum.SETTLEMENT.name())) {
				@SuppressWarnings("unchecked")
				List<Map<String, String>> settlementEnumList = LimitTrxTypeEnum.toListForSettlement();
				for (Map<String, String> map : settlementEnumList) {
					bizFunctionSwitchFacade.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, map.get("name"));
				}
			}
			else {
				bizFunctionSwitchFacade.deleteBizFunctionSwitchByPackIdAndFunction(switchLimitPackId, bizFunction);
				this.logDelete("删除业务功能开关成功，开关限制ID:" + getLong("switchLimitPackId") + ",业务：" +getString("bizFunction"));
			}
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "操作成功！");
		} catch (Exception e) {
			this.logSaveError("删除业务功能开关失败，开关限制ID:" + getLong("switchLimitPackId") + ",业务：" +getString("bizFunction"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

	/**
	 * 添加支付产品开关
	 */
	public void addPayProductSwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String payProduct = getString("payProduct");

			if (switchLimitPackId == null || StringUtils.isEmpty(payProduct)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}

			PayProductSwitch entity = new PayProductSwitch();
			entity.setPayProduct(payProduct);
			entity.setSwitchLimitPackId(switchLimitPackId);
			entity.setStatus(SwitchLimitStatusEnum.ON);

			long result = payProductSwitchFacade.savePayProductSwitch(entity);

			if (result > 0) {
				this.logSave("添加支付产品开关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "操作成功！");
			} else {
				this.logSaveError("添加支付产品开关失败，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
			}

		} catch (Exception e) {
			this.logSaveError("添加支付产品开关失败，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

	/**
	 * 删除支付产品开关
	 */
	public void deletePayProductSwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String payProduct = getString("payProduct");

			if (switchLimitPackId == null || StringUtils.isEmpty(payProduct)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}

			long result = payProductSwitchFacade.deletePayProductSwitchByPackIdAndProduct(switchLimitPackId, payProduct);

			if (result > 0) {
				this.logDelete("删除支付产品开关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "操作成功！");
			} else {
				this.logDeleteError("删除支付产品开关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
			}
		} catch (Exception e) {
			this.logDeleteError("删除支付产品开关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付产品：" +getString("payProduct"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

	/**
	 * 添加支付方式开关
	 */
	public void addPayWaySwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String payProduct = getString("payProduct");
			String payWay = getString("payWay");

			if (switchLimitPackId == null || StringUtils.isEmpty(payProduct) || StringUtils.isEmpty(payWay)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}

			PayWaySwitch entity = new PayWaySwitch();
			entity.setPayProduct(payProduct);
			entity.setSwitchLimitPackId(switchLimitPackId);
			entity.setStatus(SwitchLimitStatusEnum.ON);
			entity.setPayWay(payWay);

			long result = payWaySwitchFacade.savePayWaySwitch(entity);

			if (result > 0) {
				this.logSave("添加支付方式关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "操作成功！");
			} else {
				this.logSaveError("添加支付方式关失败，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
			}

		} catch (Exception e) {
			this.logSaveError("添加支付方式关失败，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

	/**
	 * 删除支付方式开关
	 */
	public void deletePayWaySwitch() {
		try {
			Long switchLimitPackId = getLong("switchLimitPackId");
			String payProduct = getString("payProduct");
			String payWay = getString("payWay");

			if (switchLimitPackId == null || StringUtils.isEmpty(payProduct)) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}

			long result = payWaySwitchFacade.deletePayProductSwitchByPackIdAndProductAndWay(switchLimitPackId, payProduct, payWay);

			if (result > 0) {
				this.logDelete("删除支付方式关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", "操作成功！");
			} else {
				this.logDeleteError("删除支付方式关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "操作失败，请稍后再试！");
			}
		} catch (Exception e) {
			this.logDeleteError("删除支付方式关成功，开关限制ID:" + getLong("switchLimitPackId") + ",支付方式：" +getString("payWay"));
			log.error("系统异常", e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "系统异常，请稍后再试！");
		} finally {
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}

	}

}

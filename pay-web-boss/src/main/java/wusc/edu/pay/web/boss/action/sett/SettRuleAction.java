package wusc.edu.pay.web.boss.action.sett;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettRuleCycleTypeEnum;
import wusc.edu.pay.facade.settlement.enums.SettRuleStatusEnum;
import wusc.edu.pay.facade.settlement.enums.SettTypeEnum;
import wusc.edu.pay.facade.settlement.service.SettManagementFacade;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/*
 * 结算规则管理
 */
public class SettRuleAction extends BossBaseAction {
	private static final long serialVersionUID = 2759712929164416036L;

	@Autowired
	private SettQueryFacade settQueryFacade;

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private SettManagementFacade settManagementFacade;

	/**
	 * 分页查询结算规则
	 * 
	 * @return
	 */
	@Permission("sett:rule:view")
	public String listSettRule() {

		Integer settType = getInteger("settType");
		String userName = getString("userName");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settType", settType);
		map.put("userName", userName);

		super.pageBean = settQueryFacade.querySettRuleListPage(this.getPageParam(), map);
		for (Object obj : pageBean.getRecordList()) {
			SettRule rule = (SettRule) obj;
			Integer settCycleType = rule.getSettCycleType();
			Integer settTypeS = rule.getSettType();
			String settInterval = rule.getSettCycleData();
			String showSettInterval = "";
			if (settTypeS == SettTypeEnum.AUTO_SETTLEMENT.getValue() && settCycleType == SettRuleCycleTypeEnum.WEEK.getValue()) {
				showSettInterval = "周";
				if (!"".equals(settInterval)) {
					if (settInterval.indexOf("2") >= 0) {
						showSettInterval += "一、";
					}
					if (settInterval.indexOf("3") >= 0) {
						showSettInterval += "二、";
					}
					if (settInterval.indexOf("4") >= 0) {
						showSettInterval += "三、";
					}
					if (settInterval.indexOf("5") >= 0) {
						showSettInterval += "四、";
					}
					if (settInterval.indexOf("6") >= 0) {
						showSettInterval += "五、";
					}
					if (settInterval.indexOf("7") >= 0) {
						showSettInterval += "六、";
					}
					if (settInterval.indexOf("1") >= 0) {
						showSettInterval += "日、";
					}
					showSettInterval = showSettInterval.substring(0, showSettInterval.length() - 1);
				}
			} else if (settTypeS == SettTypeEnum.AUTO_SETTLEMENT.getValue() && settCycleType == SettRuleCycleTypeEnum.MONTH.getValue()) {

				String[] settCycleDataArr = settInterval.split("\\,");
				int len = 0;
				if (settCycleDataArr != null) {
					len = settCycleDataArr.length;
					for (int i = 0; i < len; i++) {
						showSettInterval += settCycleDataArr[i] + "、";
						if (len % 10 == 0) {
							showSettInterval += "<br>";
						}
					}
				}

				if (showSettInterval.length() > 0) {
					showSettInterval = "每月：" + showSettInterval.substring(0, showSettInterval.length() - 1) + "日";
				}
			}
			rule.setSettCycleData(showSettInterval);
		}
		
		this.pushData(pageBean);
		this.putData("SettTypeEnum", SettTypeEnum.toList());
		this.putData("SettRuleCycleTypeEnum", SettRuleCycleTypeEnum.toList());
		this.putData("PublicStatusEnum", PublicStatusEnum.toIsOrNotList());
		this.putData("SettRuleStatusEnum", SettRuleStatusEnum.toList());
		this.pushData(map);
		return "listSettRule";
	}

	/**
	 * 根据结算账户编号查询该规则
	 * 
	 * @return
	 */
	@Permission("sett:rule:view")
	public String getSettRuleByAccountNo() {
		String accountNo = getString("accountNo");
		SettRule settRule = settQueryFacade.getSettRuleByAccountNo(accountNo);
		this.pushData(settRule);
		this.putData("SettTypeEnum", SettTypeEnum.toList());
		this.putData("SettRuleCycleTypeEnum", SettRuleCycleTypeEnum.toList());
		this.putData("PublicStatusEnum", PublicStatusEnum.toIsOrNotList());
		this.putData("SettRuleStatusEnum", SettRuleStatusEnum.toList());
		return "settRuleDetail";
	}

	/**
	 * 添加结算规则UI
	 * 
	 * @return
	 */
	public String addSettRuleUI() {
		this.putData("settTypeEnums)", SettTypeEnum.toList());
		this.putData("settRuleCycleTypeEnums)", SettRuleCycleTypeEnum.toList());
		this.putData("settRuleStatusEnums)", SettRuleStatusEnum.toList());
		return "addSettRuleUI";
	}

	/**
	 * 添加结算规则
	 * 
	 * @return
	 */
	public void addSettRule() {
		Long merchantId = this.getLong("merchantIdTwo");
		Integer settType = this.getInteger("settType");
		Integer settCycleType = this.getInteger("settCycleType");
		Integer riskDay = this.getInteger("riskDay");
		String remark = this.getString("remark");

		String settCycleData = "";
		// 如果是自动结算才有结算周期
		if (settType == SettTypeEnum.AUTO_SETTLEMENT.getValue()) {

			String[] settCycleDataArr = getStringArr("settCycleData"); // 结算周期
			if (settCycleType == SettRuleCycleTypeEnum.MONTH.getValue()) {
				settCycleDataArr = getStringArr("settCycleDataM");
			}
			if (settCycleDataArr == null) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "请选择结算日期");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			}
			settCycleData = settCycleDataArr[0];
			String temp = ",";
			for (int i = 1; i < settCycleDataArr.length; i++) {
				settCycleData = settCycleData + temp + settCycleDataArr[i];
			}
		}
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		SettRule entity = new SettRule();
		entity.setAccountNo(merchant.getAccountNo());
		entity.setCurrentSettStatus(SettRuleStatusEnum.SETTLEABLE.getValue());
		entity.setIsSett(PublicStatusEnum.ACTIVE.getValue());
		entity.setLastBatchNo("");
		entity.setLastModifyTime(new Date());
		entity.setLastProcessDate(new Date());
		entity.setOperatorLoginname(this.getLoginedOperator().getLoginName());
		entity.setOperatorRealname(this.getLoginedOperator().getRealName());
		entity.setRemark(remark);
		entity.setRiskDay(riskDay);
		entity.setSettCycleData(settCycleData);
		entity.setSettCycleType(settCycleType);
		entity.setSettType(settType);
		entity.setUserName(merchant.getFullName());
		entity.setUserNo(merchant.getMerchantNo());

		Long settRuleId = settManagementFacade.createSettlementRule(entity);
		if (StringUtil.isNotNull(settRuleId)) {
			this.logSave("添加结算规则，用户编号："+merchant.getMerchantNo());
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", merchantId);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		} else {
			this.logSaveError("添加结算规则，用户编号："+merchant.getMerchantNo());
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "创建失败，服务异常，请稍后重试!");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/**
	 * 修改结算规则
	 */
	public String editSettRule() {
		String merchantNo = getString("merchantNoForSettRule");
		Integer settType = this.getInteger("settType");
		Integer settCycleType = this.getInteger("settCycleType");

		String settCycleData = "";
		// 如果是自动结算才有结算周期
		if (settType == SettTypeEnum.AUTO_SETTLEMENT.getValue()) {

			String[] settCycleDataArr = getStringArr("settCycleData");
			if (settCycleType == SettRuleCycleTypeEnum.MONTH.getValue()) {
				settCycleDataArr = getStringArr("settCycleDataM");
			}
			if (settCycleDataArr == null) {
				return operateError("请选择结算日期");
			}
			settCycleData = settCycleDataArr[0];
			String temp = ",";
			for (int i = 1; i < settCycleDataArr.length; i++) {
				settCycleData = settCycleData + temp + settCycleDataArr[i];
			}
		}
		Integer riskDay = this.getInteger("riskDay");
		String remark = this.getString("remark");

		SettRule rule = settQueryFacade.getSettlRuleByMerchantNo(merchantNo);
		MerchantOnline merchant = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);
		if (rule == null) {
			SettRule entity = new SettRule();

			entity.setAccountNo(merchant.getAccountNo());
			entity.setCurrentSettStatus(SettRuleStatusEnum.SETTLEABLE.getValue());
			entity.setIsSett(PublicStatusEnum.ACTIVE.getValue());
			entity.setLastBatchNo("");
			entity.setLastModifyTime(new Date());
			entity.setLastProcessDate(new Date());
			entity.setOperatorLoginname(this.getLoginedOperator().getLoginName());
			entity.setOperatorRealname(this.getLoginedOperator().getRealName());
			entity.setRemark(remark);
			entity.setRiskDay(riskDay);
			entity.setSettCycleData(settCycleData);
			entity.setSettCycleType(settCycleType);
			entity.setSettType(settType);
			entity.setUserName(merchant.getFullName());
			entity.setUserNo(merchant.getMerchantNo());
			settManagementFacade.createSettlementRule(entity);
			this.logSave("添加结算规则，用户编号："+merchantNo);
		} else {
			rule.setSettType(settType);
			rule.setSettCycleType(settCycleType);
			rule.setSettCycleData(settCycleData);
			rule.setRiskDay(riskDay);
			rule.setRemark(remark);
			// ===========不到动下面代码======by Along =======
			if (settType != null && settType == SettTypeEnum.AUTO_SETTLEMENT.getValue()) {
				rule.setNextProcessDate(DateUtils.addDay(new Date(), -1));
				rule.setNextProcessDate(rule.calculateNextSettleDay(DateUtils.addDay(new Date(), -1)));
			}
			// =============================================
			settManagementFacade.updateSettRule(rule);
			this.logEdit("修改结算规则，用户编号："+merchantNo);
		}
		// 如果商户状态为审核不通过时，修改结算规则要修改商户状态
		if (merchant != null && merchant.getStatus() == MerchantStatusEnum.NOPASS.getValue()) {
			merchant.setStatus(MerchantStatusEnum.CREATED.getValue());
			merchantOnlineFacade.update(merchant);
		}
		return operateSuccess("修改结算规则成功");
	}

	/**
	 * 根据结算账户编号查询该规则并且返回到修改结算规则页面
	 * 
	 * @return
	 */
	public String getSettRuleToEditSettRule() {
		String accountNo = getString("accountNo");
		SettRule settRule = settQueryFacade.getSettRuleByAccountNo(accountNo);
		this.putData("settRule", settRule);
		this.putData("isView", getString("isView"));
		return "editSettRule";
	}
}

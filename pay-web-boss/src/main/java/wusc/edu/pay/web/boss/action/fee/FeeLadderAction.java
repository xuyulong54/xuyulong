package wusc.edu.pay.web.boss.action.fee;
/*package wusc.edu.pay.web.boss.action.fee;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.facade.boss.rule.service.PayWayFacade;
import wusc.edu.pay.facade.fee.entity.FeeLadderInfo;
import wusc.edu.pay.facade.fee.enums.LadderCycleTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

public class FeeLadderAction extends BossBaseAction {

	private static final long serialVersionUID = 3189877606135992617L;

	@Autowired
	private FeeQueryFacade feeQueryFacade;
	@Autowired
	private FeeManagerFacade feeManagerFacade;
	@Autowired
	private PayWayFacade payWayFacade;

	*//**
	 * 显示阶梯信息
	 * 
	 * @return
	 *//*
	public String showLadderInfo() {
		Long id = getLong("id");// 支付方式ID
		FeeLadderInfo feeLadderInfo = feeQueryFacade.queryFeeLadderInfoByWayId(id);
		putData("ladderInfo", feeLadderInfo);
		putData("wayId", id);
		putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toMap());
		return "showLadderInfo";
	}

	*//**
	 * 新增或保存
	 * 
	 * @return
	 *//*
	public String editLadderInfo() {
		Long wayId = getLong("wayId");// 支付方式ID
		String ladderName = getString("ladderName");// 阶梯名称
		Integer ladderCycleType = getInteger("ladderCycleType");// 阶梯周期类型
		Integer custLadderCycleType = getInteger("custLadderCycleType");// 自定义周期类型
		String custLadderCycleDay = getString("custLadderCycleDay");// 自定义周期日
		// 验证自定义时间
		String errorMsg = verifyCustLadderCycleDay(custLadderCycleType, custLadderCycleDay);
		if (!errorMsg.equals("")) {
			return operateError(errorMsg);
		}
		//
		FeeLadderInfo feeLadderInfo = feeQueryFacade.queryFeeLadderInfoByWayId(wayId);
		if (feeLadderInfo != null) {// 不为空则为更新
			if (ladderCycleType != null && ladderCycleType == LadderCycleTypeEnum.CUSTOMIZE.getValue()) {
				feeLadderInfo.setCustomizeCycleType(custLadderCycleType);
				feeLadderInfo.setCustomizeDay(custLadderCycleDay);
			}
			feeLadderInfo.setLadderCycleType(ladderCycleType);
			feeLadderInfo.setLadderName(ladderName);
			feeManagerFacade.updateFeeLadderInfo(feeLadderInfo);
		} else {// 为空则为新增对象
			feeLadderInfo = new FeeLadderInfo();
			if (ladderCycleType != null && ladderCycleType == LadderCycleTypeEnum.CUSTOMIZE.getValue()) {
				feeLadderInfo.setCustomizeCycleType(custLadderCycleType);
				feeLadderInfo.setCustomizeDay(custLadderCycleDay);
			}
			feeLadderInfo.setCalculateWayId(wayId);
			feeLadderInfo.setLadderCycleType(ladderCycleType);
			feeLadderInfo.setLadderName(ladderName);
			feeManagerFacade.createFeeLadderInfo(feeLadderInfo);
		}
		return operateSuccess();
	}

	*//**
	 * 验证自定义阶梯周期日
	 * 
	 * @param custLadderCycleType
	 *            类型
	 * @param custLadderCycleDay
	 *            哪一天
	 * @return
	 *//*
	private String verifyCustLadderCycleDay(Integer custLadderCycleType, String custLadderCycleDay) {
		String errorMsg = "";
		
		if (custLadderCycleType == null || custLadderCycleDay == null) {
			return errorMsg;
		}

		Integer day = Integer.valueOf(custLadderCycleDay);

		if (LadderCycleTypeEnum.WEEK.getValue() == custLadderCycleType) {// 如果类型为周，那只能是1-7
			if (!(day >= 1 && day <= 7)) {
				errorMsg = "自定义周期日只能为1到7之间";
			}
		} else if (LadderCycleTypeEnum.MONTH.getValue() == custLadderCycleType) {// 如果为月，则只能为1-31
			if (!(day >= 1 && day <= 31)) {
				errorMsg = "自定义周期日只能为1到31之间";
			}
		}
		return errorMsg;
	}
}
*/
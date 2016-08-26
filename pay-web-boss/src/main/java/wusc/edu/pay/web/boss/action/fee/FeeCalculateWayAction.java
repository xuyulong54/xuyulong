package wusc.edu.pay.web.boss.action.fee;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.fee.dto.FeeCalWayAndDimensionDTO;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.enums.CalApproximationEnum;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeChargeTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.enums.LadderCycleTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeCalculateWayFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/*
 * 计费方式管理
 */
public class FeeCalculateWayAction extends BossBaseAction {
	private static final long serialVersionUID = 2759712929164416036L;
	@Autowired
	private FeeCalculateWayFacade feeCalculateWayFacade;
	@Autowired
	private FeeQueryFacade feeQueryFacade;

	/*
	 * 分页查询（根据节点ID）
	 */
	 @Permission("fee:calculateWay:view")
	public String feeCalculateWayByNodeIdList() {
		Long feeNodeId = getLong("feeNodeId");
		List<FeeCalWayAndDimensionDTO> FeeCalculateWayVoList = feeQueryFacade.queryFeeCalculateWayListByNodeId(feeNodeId);
		this.putData("feeNodeId", feeNodeId);
		this.putData("FeeCalculateWayVoList", FeeCalculateWayVoList);
		this.putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.toList());
		this.putData("FeeRoleTypeEnum", FeeRoleTypeEnum.toList());
		this.putData("FeeChargeTypeEnum", FeeChargeTypeEnum.toList());
		this.putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toList());
		this.putData("CustomCycleTypeEnum", LadderCycleTypeEnum.toCustomList());
		return "feeCalculateWayList";
	}

	/**
	 * 查询所有计费方式
	 * 
	 * @return
	 */
	 @Permission("fee:calculateWay:view")
	public String feeCalculateWayAllList() {
		String feeNodeName = getString("feeNodeName");
		Integer isRefundFee = getInteger("isRefundFee");// 是否退还手续费：1是，0否
		Integer calculateType = getInteger("calculateType");
		Integer chargeType = getInteger("chargeType");
		// Integer feeRole = getInteger("feeRole");
		Integer isDelete = getInteger("isDelete");
		Long feeDimensionId = getLong("dimension.id");
		String payProductName = getString("dimension.payProductName");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isRefundFee", isRefundFee);
		map.put("calculateType", calculateType);
		map.put("chargeType", chargeType);
		// map.put("feeRole", feeRole);
		map.put("isDelete", isDelete);
		map.put("feeDimensionId", feeDimensionId);
		map.put("nodeName", feeNodeName);
		super.pageBean = feeCalculateWayFacade.listDimensionAndCalWay(this.getPageParam(), map);
		this.pushData(pageBean);
		// this.putData("feeRole", feeRole);
		this.putData("feeNodeName", feeNodeName);
		this.putData("chargeType", chargeType);
		this.putData("calculateType", calculateType);
		this.putData("isRefundFee", isRefundFee);
		this.putData("isDelete", isDelete);
		this.putData("feeDimensionId", feeDimensionId);
		this.putData("payProductName", payProductName);
		this.putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.toList());
		this.putData("FeeRoleTypeEnum", FeeRoleTypeEnum.toList());
		this.putData("FeeChargeTypeEnum", FeeChargeTypeEnum.toList());
		this.putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toList());
		this.putData("CustomCycleTypeEnum", LadderCycleTypeEnum.toCustomList());
		return "feeCalculateWayAllList";
	}

	/**
	 * 判断是否绑定支付产品
	 * 
	 * @return
	 */

	@Permission("feenode:productswitch:add")
	public void judgeCalculateWayByDimensionId() {
		Long feeNodeId = getLong("feeNodeId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("feeNodeId", feeNodeId);
		map.put("status", PublicStatusEnum.ACTIVE.getValue());
		List<Object> dimensionList = feeQueryFacade.queryFeeDimensionListPage(getPageParam(), map).getRecordList();
		if (dimensionList == null || dimensionList.size() == 0) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "未绑定支付产品！");
		} else {
			getOutputMsg().put("STATE", "SUCCESS");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * 根据ID
	 */
	@Permission("feenode:productswitch:edit")
	public String addCalculateWayByDimensionIdUI() {
		Long feeNodeId = getLong("feeNodeId");
		List<FeeDimension> dimensionList = feeQueryFacade.queryDimensionByNodeId(feeNodeId);
		String dimensionIds = "";
		String temp = "-";
		for (int i = 0; i < dimensionList.size(); i++) {
			dimensionIds = dimensionList.get(i).getId() + temp + dimensionIds;
		}
		this.putData("dimensionIds", dimensionIds);
		this.putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.toList());
		// this.putData("FeeRoleTypeEnum", FeeRoleTypeEnum.toList());
		this.putData("FeeChargeTypeEnum", FeeChargeTypeEnum.toList());
		this.putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toList());
		this.putData("CustomCycleTypeEnum", LadderCycleTypeEnum.toCustomList());
		this.putData("isRoundEnums", CalApproximationEnum.toList());
		return "addFeeCalculateWayUI";
	}

	/**
	 * 选择费率维度添加计费方式UI
	 * 
	 * @return
	 */
	@Permission("fee:calculateWay:add")
	public String addCalculateWaySelectDimensionUI() {
		Long feeNodeId = getLong("feeNodeId");
		this.putData("feeNodeId", feeNodeId);
		this.putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.toList());
		this.putData("FeeRoleTypeEnum", FeeRoleTypeEnum.toList());
		this.putData("FeeChargeTypeEnum", FeeChargeTypeEnum.toList());
		this.putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toList());
		this.putData("CustomCycleTypeEnum", LadderCycleTypeEnum.toCustomList());
		this.putData("isRoundEnums", CalApproximationEnum.toList());
		return "feeCalculateWayAddByDimension";
	}

	/**
	 * 选择费率维度添加计费方式
	 * 
	 * @return
	 */
	public String addCalculateWaySelectDimension() {
		Long feeDimensionId = getLong("dimensionId");
		Integer isRefundFee = getInteger("isRefundFee");// 是否退还手续费：1是，0否
		Integer calculateType = getInteger("calculateType");
		Integer chargeType = getInteger("chargeType");
		Integer feeRole = getInteger("feeRole");// 计费角色
		Integer isRound = getInteger("isRound");// 是否四舍五入
		double feeFreeAmount = getDouble("feeFreeAmount");
		if (feeFreeAmount < 0) {
			return this.operateError("免计费金额不能小于0！");
		}
		String effectiveDateStart = getString("effectiveDateStart");
		String effectiveDateEnd = getString("effectiveDateEnd");
		Integer ladderCycleType = getInteger("ladderCycleType");
		Integer customizeCycleType = getInteger("customizeCycleType");
		String customizeDay = getString("customizeDay");

		FeeCalculateWay feeCalculateWay = new FeeCalculateWay();
		feeCalculateWay.setFeeRole(feeRole);
		feeCalculateWay.setLadderCycleType(ladderCycleType);
		feeCalculateWay.setCalculateType(calculateType);
		feeCalculateWay.setChargeType(chargeType);
		feeCalculateWay.setCustomizeCycleType(customizeCycleType);
		feeCalculateWay.setCustomizeDay(customizeDay);
		feeCalculateWay.setEffectiveDateStart(DateUtils.getDateByStr(effectiveDateStart));
		feeCalculateWay.setEffectiveDateEnd(DateUtils.getDateByStr(effectiveDateEnd));
		feeCalculateWay.setFeeDimensionId(feeDimensionId);
		feeCalculateWay.setFeeFreeAmount(feeFreeAmount);
		feeCalculateWay.setIsRound(isRound);
		feeCalculateWay.setFeeRole(FeeRoleTypeEnum.PAYEE.getValue());// 目前是针对收款方的计费
		feeCalculateWay.setModifyTime(new Date());
		feeCalculateWay.setIsRefundFee(isRefundFee == 1 ? true : false);
		// 做唯一判断
		boolean flag = feeCalculateWayFacade.checkUnique(feeCalculateWay);
		if (flag) {
			feeCalculateWayFacade.createFeeCalculateWay(feeCalculateWay);
			this.logSave("添加计费方式，费率维度Id："+feeDimensionId);
			return this.operateSuccess("添加计费方式成功！");
		} else {
			return this.operateError("已经存在该种计费方式！");
		}

	}

	/**
	 * 删除或还原
	 */
	@Permission("fee:calculateWay:freeze")
	public void isDel() {
		if (getInteger("flag") == 1)// 删除还原
		{
			FeeCalculateWay calculateWay = feeCalculateWayFacade.getById(getLong("id"));
			calculateWay.setIsDelete(false);
			feeCalculateWayFacade.updateFeeCalculateWay(calculateWay);
		} else if (getInteger("flag") == 0) {// 删除
			FeeCalculateWay calculateWay = feeCalculateWayFacade.getById(getLong("id"));
			calculateWay.setIsDelete(true);
			feeCalculateWayFacade.updateFeeCalculateWay(calculateWay);
		}
	}

	/**
	 * 
	 * feeCalculateWayView()
	 * @return 
	 * @since  1.0
	 */
	public String feeCalculateWayView() {
		FeeCalculateWay calculateWay = feeCalculateWayFacade.getById(getLong("id"));
		this.putData("FeeCalculateTypeEnum", FeeCalculateTypeEnum.toList());
		this.putData("FeeRoleTypeEnum", FeeRoleTypeEnum.toList());
		this.putData("FeeChargeTypeEnum", FeeChargeTypeEnum.toList());
		
		this.putData("LadderCycleTypeEnum", LadderCycleTypeEnum.toList());
		this.putData("CustomCycleTypeEnum", LadderCycleTypeEnum.toCustomList());
		this.putData("isRoundEnums", CalApproximationEnum.toList());
		pushData(calculateWay);
		return "feeCalculateWayView";
	}
}

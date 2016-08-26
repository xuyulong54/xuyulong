package wusc.edu.pay.web.boss.action.fee;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeFormulaTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeFormulaeFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/*
 * 计费公式管理
 */
public class FeeFormulaeAction extends BossBaseAction {
	private static final long serialVersionUID = 2759712929164416036L;
	@Autowired
	private FeeFormulaeFacade feeFormulaeFacade;

	/*
	 * 计费方式下所有计算公式
	 */
	@Permission("fee:formula:view")
	public String feeFormulaeList() {
		Long calculateWayId = getLong("id");// 计费方式ID
		Integer formulaType = getInteger("formulaType");
		Integer calculateType = getInteger("calculateType");
		Integer status = getInteger("status");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("calculateWayId", calculateWayId);
		map.put("formulaType", formulaType);
		map.put("status", status);
		PageBean feeFormulaePage = feeFormulaeFacade.listPage(
				this.getPageParam(), map);
		this.pushData(feeFormulaePage);
		this.putData("formulaType", formulaType);
		this.putData("calculateTypeName",
				FeeCalculateTypeEnum.getEnum(calculateType).getDesc());
		this.putData("FeeFormulaTypeEnum", FeeFormulaTypeEnum.toList());
		this.putData("StatusEnum", PublicStatusEnum.toCalPayWayList());
		this.putData("status", status);
		this.putData("calculateType", calculateType);
		this.putData("calculateWayId", calculateWayId);
		return "feeFormulaeList";
	}

	/**
	 * 新增计费公式--ui
	 * 
	 * @return
	 */
	@Permission("fee:formula:add")
	public String addFeeFormulaeUI() {
		Long calculateWayId = getLong("calculateWayId");
		// Integer formulaType = getInteger("formulaType");
		Integer calculateType = getInteger("calculateType");
		// this.putData("formulaType", formulaType);
		this.putData("calculateWayId", calculateWayId);
		this.putData("calculateTypeName",
				FeeCalculateTypeEnum.getEnum(calculateType).getDesc());
		this.putData("calculateType", calculateType);
		this.putData("FeeFormulaTypeEnum", FeeFormulaTypeEnum.toList());
		return "addFeeFormulaeUI";
	}

	/**
	 * 新增计费公式--功能
	 * 
	 * @return
	 */
	@Permission("fee:formula:add")
	public String addFeeFormulae() {

		Long calculateWayId = getLong("calculateWayId");
		Integer calculateType = getInteger("calculateType");
		Integer formulaType = getInteger("formulaType");
		FeeFormula feeFormula = new FeeFormula();
		feeFormula.setCalculateWayId(calculateWayId);
		feeFormula.setFormulaType(formulaType);
		if (formulaType == 1) {
			if (getDouble("fixedFee") == null || getDouble("fixedFee") < 0   ) {
				return this.operateError("数值不能为空，且不能小于0");
			} else {
				feeFormula.setFixedFee(getDouble("fixedFee"));
			}

		} else if (formulaType == 2) {
			if(getDouble("percentFee") == null || getDouble("percentFee") < 0 || getDouble("percentFee")>100){
				return this.operateError("数值不能为空，且比例介于0~100。");
			}else
			if (getDouble("singleMinFee") == null || getDouble("singleMaxFee") == null || getDouble("singleMaxFee") < 0
					|| getDouble("singleMinFee") < 0) {
				return this.operateError("数值不能为空，且不能小于0！");
			} else if (getDouble("singleMaxFee") <= getDouble("singleMinFee")) {
				return this.operateError("上限必须大于下限！");
			} else {
				feeFormula.setPercentFee(getDouble("percentFee") * 0.01);
				feeFormula.setSingleMaxFee(getDouble("singleMaxFee"));
				feeFormula.setSingleMinFee(getDouble("singleMinFee"));
			}
		}
		if (calculateType == 2) {
			if (getDouble("minAmount") == null || getDouble("maxAmount")==null || getDouble("minAmount") < 0 || getDouble("maxAmount") < 0) {
				return this.operateError("数值不能为空，且不能小于0！");
			} else if (getDouble("maxAmount") <= getDouble("minAmount")) {
				return this.operateError("上限必须大于下限！");
			} else {
				feeFormula.setMinAmount(getDouble("minAmount"));
				feeFormula.setMaxAmount(getDouble("maxAmount"));
			}
		}
		if (calculateType == 3 || calculateType == 4) {
			if (getDouble("maxLadderAmount") == null || getDouble("minLadderAmount")==null || getDouble("maxLadderAmount") < 0
					|| getDouble("minLadderAmount") < 0) {
				return this.operateError("数值不能为空，且不能小于0！");
			} else if (getDouble("maxLadderAmount") <= getDouble("minLadderAmount")) {
				return this.operateError("上限必须大于下限！");
			} else {
				feeFormula.setMaxLadderAmount(getDouble("maxLadderAmount"));
				feeFormula.setMinLadderAmount(getDouble("minLadderAmount"));
			}

		}
		boolean flag = feeFormulaeFacade.checkUnique(feeFormula);
		if (flag) {
			feeFormula.setStatus(PublicStatusEnum.ACTIVE.getValue());
			feeFormulaeFacade.createFeeFormulae(feeFormula);
			this.logSave("新增计费公式，计费方式信息ID："+ calculateWayId);
			return this.operateSuccess("添加计费公式成功！");
		} else {
			return this.operateError("已存在该种计费公式！");
		}
	}

	/**
	 * 修改状态
	 */
	@Permission("fee:formula:add")
	public void changeStatus() {
		Integer status = getInteger("status");
		Long id = getLong("id");
		FeeFormula feeFormula = feeFormulaeFacade.getById(id);
		if (status == 100) {
			feeFormula.setStatus(101);
			feeFormulaeFacade.updateFeeFormulae(feeFormula);
			this.logEdit("修改计费公式状态（有效），计费方式信息ID："+ feeFormula.getCalculateWayId());
			getOutputMsg().put("STATE", "SUCCESS");
		} else if (status == 101) {
			feeFormula.setStatus(100);
			feeFormulaeFacade.updateFeeFormulae(feeFormula);
			this.logEdit("修改计费公式状态（无效），计费方式信息ID："+ feeFormula.getCalculateWayId());
			getOutputMsg().put("STATE", "SUCCESS");
		} else {
			getOutputMsg().put("STATE", "FAIL");
		}
		this.outPrint(this.getHttpResponse(),JSONObject.fromObject(this.getOutputMsg()));
	}

}

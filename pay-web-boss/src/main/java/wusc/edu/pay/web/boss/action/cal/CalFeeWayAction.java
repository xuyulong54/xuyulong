package wusc.edu.pay.web.boss.action.cal;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.BillingCycleEnum;
import wusc.edu.pay.facade.cost.enums.CalApproximationEnum;
import wusc.edu.pay.facade.cost.enums.CalPeriodeEnum;
import wusc.edu.pay.facade.cost.enums.CalRoleEnum;
import wusc.edu.pay.facade.cost.enums.CalTypeEnum;
import wusc.edu.pay.facade.cost.enums.CostInterfacePolicyEnum;
import wusc.edu.pay.facade.cost.service.CalDimensionFacade;
import wusc.edu.pay.facade.cost.service.CalFeeWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费方式
 */
public class CalFeeWayAction extends BossBaseAction {

	private static final long serialVersionUID = -338184033324010331L;

	private static Log log = LogFactory.getLog(CalFeeWayAction.class);

	@Autowired
	private CalFeeWayFacade calFeeWayFacade;

	@Autowired
	private CalDimensionFacade calDimensionFacade;

	/**
	 * 查询计费约束并分页.
	 * 
	 * @return
	 */
	@Permission("boss:calFeeWay:view")
	public String listCalFeeWay() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dimensionId", getString("dimensionId"));
		paramMap.put("wayName", getString("wayName"));
		paramMap.put("createTime", getString("createTime"));
		paramMap.put("feeFreeAmount", getString("feeFreeAmount"));
		paramMap.put("status", getString("status"));
		paramMap.put("cycleType", getString("cycleType"));
		paramMap.put("cusCycleType", getString("cusCycleType"));
		paramMap.put("customizeDay", getString("customizeDay"));
		paramMap.put("calType", getInteger("calType"));
		paramMap.put("calPeriod", getInteger("calPeriod"));
		paramMap.put("calRole", getInteger("calRole"));
		paramMap.put("beginDate", getString("beginDate"));
		paramMap.put("endDate", getString("endDate"));
		paramMap.put("mcc", getString("mcc"));
		List<CalDimension> calDimensionList = calDimensionFacade.listAll();
		super.pageBean = calFeeWayFacade.listPage(getPageParam(), paramMap);
		this.putData("calDimensionList", calDimensionList);
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		this.putData("isOrNotList", PublicStatusEnum.toIsOrNotList());
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "CalFeeWayList";
	}

	/**
	 * 查看某一维度下的计费约束
	 */
	@Permission("boss:calFeeWay:view")
	public String calFeeWayInfo() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("dimensionId", getString("dimensionId"));
		List<CalDimension> calDimensionList = calDimensionFacade.listAll();
		super.pageBean = calFeeWayFacade.listPage(getPageParam(), paramMap);
		this.putData("calDimensionList", calDimensionList);
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		this.putData("isOrNotList", PublicStatusEnum.toIsOrNotList());
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "CalFeeWayInfo";
	}

	/**
	 * 进入添加计费约束的页面 .<br/>
	 * 
	 * @return addTerminalUI .
	 */
	@Permission("boss:calFeeWay:add")
	public String addCalFeeWayUI() {
		List<CalDimension> calDimensionList = calDimensionFacade.listAll();
		this.putData("calDimensionList", calDimensionList);
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		//this.putData("isOrNotList", PublicStatusEnum.toIsOrNotList());
		this.putData("calApproximationList", CalApproximationEnum.toList()); //近似值枚举
		//this.putData("mccTypeList", mccFacade.getFirstMccType());
		return "CalFeeWayAdd";
	}

	/**
	 * 添加计费约束
	 */
	@Permission("boss:calFeeWay:add")
	public String addCalFeeWay() {
		try {
			CalFeeWay calFeeWayParam = new CalFeeWay();
			if (getLong("dimensionId") != null) {
				calFeeWayParam.setDimensionId(getLong("dimensionId"));
			}
			if (getString("wayName") != null) {
				calFeeWayParam.setWayName(getString("wayName"));
			}
			calFeeWayParam.setCreateTime(new Date());
			if (getString("feeFreeAmount") != null) {
				calFeeWayParam.setFeeFreeAmount(new BigDecimal(getString("feeFreeAmount")));
			}
			if (getInteger("status") != null) {
				calFeeWayParam.setStatus(getInteger("status"));
			}
			if (getInteger("cycleType") != null) {
				calFeeWayParam.setCycleType(getInteger("cycleType"));
			}
			calFeeWayParam.setCycleType(getInteger("cycleType"));
			calFeeWayParam.setCusCycleType(getInteger("cusCycleType"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (getString("customizeDay") != null) {
				calFeeWayParam.setCustomizeDay(format.parse(getString("customizeDay")));
			}
			calFeeWayParam.setCalType(getInteger("calType"));
			calFeeWayParam.setCalPeriod(getInteger("calPeriod"));
			calFeeWayParam.setCalRole(getInteger("calRole"));
			calFeeWayParam.setIsRound(getInteger("isRound"));
			calFeeWayParam.setBeginDate(format.parse(getString("beginDate")));
			calFeeWayParam.setEndDate(format.parse(getString("endDate")));
			calFeeWayParam.setMcc(getString("mcc"));
			calFeeWayFacade.create(calFeeWayParam);
			this.logSave("添加计费方式，计费方式名称："+calFeeWayParam.getWayName());
			return operateSuccess();
		} catch (Exception e) {
			this.logSaveError("添加计费约束，计费约束名称："+getString("wayName"));
			log.error("== addCalFeeWay exception:", e);
			return operateError("该计费约束已存在");
		}
	}

	/**
	 * 进入修改计费约束的页面 .<br/>
	 * 
	 * @return editTerminalUI .
	 */
	@Permission("boss:calFeeWay:edit")
	public String editCalFeeWayUI() {
		List<CalDimension> calDimensionList = calDimensionFacade.listAll();
		this.putData("calDimensionList", calDimensionList);
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalStatusList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		//this.putData("isOrNotList", PublicStatusEnum.toIsOrNotList());
		this.putData("calApproximationList", CalApproximationEnum.toList()); //近似值枚举
		Long id = getLong("id");
		if (null != id) {
			CalFeeWay calFeeWay = calFeeWayFacade.getById(id);
			this.pushData(calFeeWay);
		}
		//this.putData("mccTypeList", mccFacade.getFirstMccType());
		return "CalFeeWayEdit";
	}

	/**
	 * 修改计费约束
	 */
	@Permission("boss:calFeeWay:edit")
	public String editCalFeeWay() {
		try {
			CalFeeWay calFeeWayParam = calFeeWayFacade.getById(getLong("id"));
			calFeeWayParam.setDimensionId(getLong("dimensionId"));
			calFeeWayParam.setWayName(getString("wayName"));
			calFeeWayParam.setFeeFreeAmount(new BigDecimal(getString("feeFreeAmount")));
			calFeeWayParam.setStatus(getInteger("status"));
			calFeeWayParam.setCycleType(getInteger("cycleType"));
			calFeeWayParam.setCusCycleType(getInteger("cusCycleType"));
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			if (getString("customizeDay") != null) {
				calFeeWayParam.setCustomizeDay(format.parse(getString("customizeDay")));
			}
			calFeeWayParam.setCalType(getInteger("calType"));
			calFeeWayParam.setCalPeriod(getInteger("calPeriod"));
			calFeeWayParam.setCalRole(getInteger("calRole"));
			calFeeWayParam.setIsRound(getInteger("isRound"));
			calFeeWayParam.setBeginDate(format.parse(getString("beginDate")));
			calFeeWayParam.setEndDate(format.parse(getString("endDate")));
			calFeeWayParam.setMcc(getString("mcc"));

			calFeeWayFacade.update(calFeeWayParam);
			// 记录系统操作日志
			super.logEdit("修改计费约束.计费约束名称[" + calFeeWayParam.getWayName() + "]");
			return operateSuccess();
		} catch (Exception e) {
			super.logEditError("修改计费约束.计费约束名称[" + getString("wayName") + "]");
			log.error("== editCalFeeWay exception:", e);
			return operateError("操作失败");
		}
	}

	/**
	 * 修改约束状态
	 */
	@Permission("boss:calFeeWay:edit")
	public void changeFeeWayStatus() {
		try {
			Long id = getLong("id");
			Integer status = getInteger("status");
			CalFeeWay calFeeWayParam = calFeeWayFacade.getById(id);
			calFeeWayParam.setStatus(status);
			calFeeWayFacade.update(calFeeWayParam);
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", "修改状态成功");
			super.logEdit("修改计费约束状态.计费约束名称[" + calFeeWayParam.getWayName() + "]");
		} catch (Exception e) {
			log.error(e);
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "修改状态失败");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	/**
	 * 
	 * @描述: 查看计费方式详情.
	 * @作者: Along.shen .
	 * @创建时间:2015-1-5,上午10:11:01.
	 * @版本:
	 */
	public String calFeeWayView() {
		long feeWayId = getLong("feeWayId");
		CalFeeWay calFeeWay = calFeeWayFacade.getById(feeWayId);
		//MccType mccType = mccFacade.getMccTypeByNumber(calFeeWay.getMcc());
		List<CalDimension> calDimensionList = calDimensionFacade.listAll();
		this.putData("calDimensionList", calDimensionList);
		this.putData("publicStatusEnumList", PublicStatusEnum.toCalPayWayList());
		this.putData("calPeriodeEnumList", CalPeriodeEnum.toList());
		this.putData("billingCycleEnumList", BillingCycleEnum.toList());
		this.putData("calRoleEnumList", CalRoleEnum.toList());
		this.putData("calTypeEnumList", CalTypeEnum.toList());
		this.putData("costInterfacePolicyEnumList", CostInterfacePolicyEnum.toList());
		//this.putData("isOrNotList", PublicStatusEnum.toIsOrNotList());
		this.putData("calApproximationList", CalApproximationEnum.toList()); //近似值枚举
		this.pushData(calFeeWay);
		//this.putData("mccName", mccType==null?"":mccType.getFullName());
		return "CalFeeWayView";
	}

}

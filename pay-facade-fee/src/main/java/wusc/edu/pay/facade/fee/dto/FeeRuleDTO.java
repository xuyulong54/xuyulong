package wusc.edu.pay.facade.fee.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import wusc.edu.pay.facade.fee.entity.FeeFormula;


/**
 * 费率规则DTO
 */
@SuppressWarnings("serial")
public class FeeRuleDTO implements Serializable {
	/**
	 * 计费方式Id
	 */
	private Long id;
	/**
	 * 收费方式（实时收、后收）<参考  FeeChargeTypeEnum >
	 * （必须）
	 */
	private Integer chargeType;
	/**
	 * 计费角色< 参考 FeeRoleTypeEnum>
	 * (必须)
	 */
	private Integer feeRole;
	
	/**
	 * 是否四舍五入
	 * 100--四舍五入
	 * 101--不做四舍五入
	 */
	private Integer isRound;
	/**
	 * 计费类型（阶梯、直接）《参考 FeeCalculateTypeEnum》
	 * （必须）
	 */
	private Integer calculateType;
	
	
	
	/**
	 * 账单周期类型 《参考  LadderCycleTypeEnum》
	 */
	private Integer billCycleType;
	/**
	 * 自定义账单周期类型 《参考  LadderCycleTypeEnum》
	 */
	private Integer customizeBillCycleType;
	/**
	 * 自定义账单日
	 */
	private String customizeBillDay;
	
	/**
	 * 公式生效日期起
	 * （必须）
	 */
	private Date effectiveStart;
	/**
	 * 公式生效日期止
	 * （必须）
	 */
	private Date effectiveEnd;
	
	/**
	 * 免计费金额
	 */
	private Double freeFeeAmount;
	/**
	 * 创建日期
	 */
	private Date createTime = new Date();
	/**
	 * 退款是否退手续费
	 * (默认为不退手续费false)
	 */
	private boolean isRefundFee;
	

	/**
	 * 规则名称、套餐名称
	 */
	private String name;
	
	//=================================
	/**
	 * 阶梯周期类型  
	 * （必须）
	 */
	private Integer ladderCycleType;
	/**
	 * 自定义周期类型 《参考 LadderCycleTypeEnum 》
	 */
	private Integer customizeCycleType;
	/**
	 * 自定义周期日（月，周）
	 */
	private String customizeDay;
	
	/**
	 * 包量信息DTO
	 */
//	private FeePrepaidFlowInfoDTO feePrepaidFlowInfoDto;
	
	/**
	 * 费率公式组
	 * （必须）
	 */
	private List<FeeFormula> feeFormulaList;
	
	
	/**
	 * 判断费率公式组是否不为空
	 * @return
	 */
	public boolean isValid4FeeFormulas(){
		if(null != feeFormulaList && feeFormulaList.size() > 0){
			return true;
		}
		else{
			return false;
		}
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Integer getChargeType() {
		return chargeType;
	}


	public void setChargeType(Integer chargeType) {
		this.chargeType = chargeType;
	}


	public Integer getFeeRole() {
		return feeRole;
	}


	public void setFeeRole(Integer feeRole) {
		this.feeRole = feeRole;
	}


	public Integer getIsRound() {
		return isRound;
	}


	public void setIsRound(Integer isRound) {
		this.isRound = isRound;
	}


	public Integer getCalculateType() {
		return calculateType;
	}


	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
	}


	public Integer getBillCycleType() {
		return billCycleType;
	}


	public void setBillCycleType(Integer billCycleType) {
		this.billCycleType = billCycleType;
	}


	public Integer getCustomizeBillCycleType() {
		return customizeBillCycleType;
	}


	public void setCustomizeBillCycleType(Integer customizeBillCycleType) {
		this.customizeBillCycleType = customizeBillCycleType;
	}


	public String getCustomizeBillDay() {
		return customizeBillDay;
	}


	public void setCustomizeBillDay(String customizeBillDay) {
		this.customizeBillDay = customizeBillDay;
	}


	public Date getEffectiveStart() {
		return effectiveStart;
	}


	public void setEffectiveStart(Date effectiveStart) {
		this.effectiveStart = effectiveStart;
	}


	public Date getEffectiveEnd() {
		return effectiveEnd;
	}


	public void setEffectiveEnd(Date effectiveEnd) {
		this.effectiveEnd = effectiveEnd;
	}


	public Double getFreeFeeAmount() {
		return freeFeeAmount;
	}


	public void setFreeFeeAmount(Double freeFeeAmount) {
		this.freeFeeAmount = freeFeeAmount;
	}


	public Date getCreateTime() {
		return createTime;
	}


	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


	public boolean isRefundFee() {
		return isRefundFee;
	}


	public void setRefundFee(boolean isRefundFee) {
		this.isRefundFee = isRefundFee;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getLadderCycleType() {
		return ladderCycleType;
	}


	public void setLadderCycleType(Integer ladderCycleType) {
		this.ladderCycleType = ladderCycleType;
	}


	public Integer getCustomizeCycleType() {
		return customizeCycleType;
	}


	public void setCustomizeCycleType(Integer customizeCycleType) {
		this.customizeCycleType = customizeCycleType;
	}


	public String getCustomizeDay() {
		return customizeDay;
	}


	public void setCustomizeDay(String customizeDay) {
		this.customizeDay = customizeDay;
	}


	public List<FeeFormula> getFeeFormulaList() {
		return feeFormulaList;
	}


	public void setFeeFormulaList(List<FeeFormula> feeFormulaList) {
		this.feeFormulaList = feeFormulaList;
	}

	
}

package wusc.edu.pay.facade.fee.entity;

import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;
import wusc.edu.pay.facade.fee.utils.FeeDateUtils;
import wusc.edu.pay.facade.fee.utils.ReadFeeConfig;


/**
 * 费率计费方式实体
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-26,下午4:01:35
 */
public class FeeCalculateWay extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 费率维度Id
	 */
	private Long feeDimensionId;

	/**
	 * 退款是否退手续费
	 */
	private boolean isRefundFee;

	/**
	 * 计费类型（阶梯、直接） FeeCalculateTypeEnum
	 */
	private Integer calculateType;
	/**
	 * 收费方式（实时收、后收）《参考 FeeChargeTypeEnum 枚举 》
	 */
	private Integer chargeType;
	/**
	 * 计费角色 《参考 FeeRoleTypeEnum 枚举 》
	 */
	private Integer feeRole;
	
	/**
	 * 是否四舍五入
	 * 100--四舍五入
	 * 101--不做四舍五入
	 */
	private Integer isRound;
	/**
	 * 免计费金额
	 */
	private Double feeFreeAmount = 0.0d;
	/**
	 * 公式生效日期起
	 */
	private Date effectiveDateStart;
	/**
	 * 公式生效日期止
	 */
	private Date effectiveDateEnd;
	/**
	 * 账单周期类型
	 */
	private Integer billCycleType;
	/**
	 * 自定义账单周期类型
	 */
	private Integer customizeBillCycleType;
	/**
	 * 自定义账单日
	 */
	private String customizeBillDay;

	/**
	 * 是否已被删除
	 */
	private boolean isDelete;
	/**
	 * 修改日期
	 */
	private Date modifyTime;

	// =========================================来自原来的阶梯周期信息===============================================

	/**
	 * 阶梯周期类型 LadderCycleTypeEnum
	 */
	private Integer ladderCycleType;

	/**
	 * 自定义阶梯周期类型 LadderCycleTypeEnum
	 */
	private Integer customizeCycleType;

	/**
	 * 自定义阶梯周期日
	 */
	private String customizeDay;

	/**
	 * 阶梯名称
	 */
	private String ladderName;
	
	/**
	 * 该维度的产品名称
	 */
	private String productName;


	public Long getFeeDimensionId() {
		return feeDimensionId;
	}

	public void setFeeDimensionId(Long feeDimensionId) {
		this.feeDimensionId = feeDimensionId;
	}

	public boolean getIsRefundFee() {
		return isRefundFee;
	}

	public void setIsRefundFee(boolean isRefundFee) {
		this.isRefundFee = isRefundFee;
	}

	public Integer getCalculateType() {
		return calculateType;
	}

	public void setCalculateType(Integer calculateType) {
		this.calculateType = calculateType;
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

	public Double getFeeFreeAmount() {
		return feeFreeAmount;
	}

	public void setFeeFreeAmount(Double feeFreeAmount) {
		this.feeFreeAmount = feeFreeAmount;
	}

	public Date getEffectiveDateStart() {
		return effectiveDateStart;
	}

	public void setEffectiveDateStart(Date effectiveDateStart) {
		this.effectiveDateStart = effectiveDateStart;
	}

	public Date getEffectiveDateEnd() {
		return effectiveDateEnd;
	}

	public void setEffectiveDateEnd(Date effectiveDateEnd) {
		this.effectiveDateEnd = effectiveDateEnd;
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

	public boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
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

	public String getLadderName() {
		return ladderName;
	}

	public void setLadderName(String ladderName) {
		this.ladderName = ladderName;
	}

	/**
	 * 更新生效时间起的值
	 * 
	 * @param target
	 */
	public void updateEffectiveDateStart(Date target) {
		String amount = ReadFeeConfig.getText("AFTER_SECONDS");
		this.effectiveDateStart = FeeDateUtils.calculateEffectiveDate(target, Integer.valueOf(amount));
	}

	/**
	 * 验证修改时间是否在生效时间区间内
	 * 
	 * @return
	 */
	public boolean checkDateBetween(Date target) {
		if (null == target)
			throw new IllegalArgumentException("IllegalArgumentException.");
		if (FeeDateUtils.isTimeInInterval(effectiveDateStart, effectiveDateEnd, target))
			return true;
		else
			return false;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}

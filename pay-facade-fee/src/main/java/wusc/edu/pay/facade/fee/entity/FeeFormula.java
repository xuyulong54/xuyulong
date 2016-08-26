package wusc.edu.pay.facade.fee.entity;

import wusc.edu.pay.common.entity.BaseEntity;

/**
 * 费率公式实体类
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,上午10:01:35
 */
public class FeeFormula extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 计费方式信息ID
	 */
	private Long calculateWayId;

	/**
	 * 公式类型（固定、比例） FeeFormulaTypeEnum
	 */
	private Integer formulaType;
	/**
	 * 固定金额费率基数
	 */
	private Double fixedFee;
	/**
	 * 百分比费率基数
	 */
	private Double percentFee;
	/**
	 * 单笔金额下限
	 */
	private Double minAmount = 0D;
	/**
	 * 单笔金额上限
	 */
	private Double maxAmount;
	/**
	 * 阶梯金额下限
	 */
	private Double minLadderAmount = 0D;
	/**
	 * 阶梯金额上限
	 */
	private Double maxLadderAmount;

	/**
	 * 单笔最低手续费
	 */
	private Double singleMinFee = 0D;
	/**
	 * 单笔最高手续费
	 */
	private Double singleMaxFee;
	/**
	 * 是否有效状态
	 */
	private Integer status;
	
	public Long getCalculateWayId() {
		return calculateWayId;
	}

	public void setCalculateWayId(Long calculateWayId) {
		this.calculateWayId = calculateWayId;
	}

	public Integer getFormulaType() {
		return formulaType;
	}

	public void setFormulaType(Integer formulaType) {
		this.formulaType = formulaType;
	}

	public Double getFixedFee() {
		return fixedFee;
	}

	public void setFixedFee(Double fixedFee) {
		this.fixedFee = fixedFee;
	}

	public Double getPercentFee() {
		return percentFee;
	}

	public void setPercentFee(Double percentFee) {
		this.percentFee = percentFee;
	}

	public Double getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}

	public Double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Double getMinLadderAmount() {
		return minLadderAmount;
	}

	public void setMinLadderAmount(Double minLadderAmount) {
		this.minLadderAmount = minLadderAmount;
	}

	public Double getMaxLadderAmount() {
		return maxLadderAmount;
	}

	public void setMaxLadderAmount(Double maxLadderAmount) {
		this.maxLadderAmount = maxLadderAmount;
	}

	public Double getSingleMinFee() {
		return singleMinFee;
	}

	public void setSingleMinFee(Double singleMinFee) {
		this.singleMinFee = singleMinFee;
	}

	public Double getSingleMaxFee() {
		return singleMaxFee;
	}

	public void setSingleMaxFee(Double singleMaxFee) {
		this.singleMaxFee = singleMaxFee;
	}

	/**
	 * 百分比费率基数和固定费率基数不能同时大于零
	 * 
	 * @return
	 */
	public boolean isValidFeeBase() {
		if (null == this.percentFee && null == this.fixedFee) {
			return false;
		} else {
			return true;
		}
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}

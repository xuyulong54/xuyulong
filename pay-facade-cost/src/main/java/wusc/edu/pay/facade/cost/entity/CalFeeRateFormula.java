package wusc.edu.pay.facade.cost.entity;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.common.entity.BaseEntity;


/**
 * 计费公式实体
 */
public class CalFeeRateFormula extends BaseEntity {
	private static final long serialVersionUID = -5639457974721376139L;

	private Date createDate = new Date(); // 创建时间

	private Long feeWayId; // 计费约束ID

	private BigDecimal fixedFee = BigDecimal.ZERO; // 固定手续费

	private BigDecimal percentFee = BigDecimal.ZERO; // 比例手续费

	private BigDecimal minAmount = BigDecimal.ZERO; // 单笔区间下限

	private BigDecimal maxAmount = BigDecimal.ZERO; // 单笔区间上限

	private BigDecimal minLadderAmount = BigDecimal.ZERO; // 阶梯下限

	private BigDecimal maxLadderAmount = BigDecimal.ZERO; // 阶梯上限

	private BigDecimal singleMinFee = BigDecimal.ZERO; // 单笔最低手续费

	private BigDecimal singleMaxFee = BigDecimal.ZERO; // 单笔最高手续费

	private Integer model; // 计费模式

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * 计费约束ID
	 * 
	 * @return
	 */
	public Long getFeeWayId() {
		return feeWayId;
	}

	/**
	 * 计费约束ID
	 * 
	 * @param feeWayId
	 */
	public void setFeeWayId(Long feeWayId) {
		this.feeWayId = feeWayId;
	}

	/**
	 * /固定手续费
	 * 
	 * @return
	 */
	public BigDecimal getFixedFee() {
		return fixedFee;
	}

	/**
	 * /固定手续费
	 * 
	 * @param fixedFee
	 */
	public void setFixedFee(BigDecimal fixedFee) {
		this.fixedFee = fixedFee;
	}

	/**
	 * 比例手续费
	 * 
	 * @return
	 */
	public BigDecimal getPercentFee() {
		return percentFee;
	}

	/**
	 * 比例手续费
	 * 
	 * @param percentFee
	 */
	public void setPercentFee(BigDecimal percentFee) {
		this.percentFee = percentFee;
	}

	/**
	 * 单笔区间下限
	 * 
	 * @return
	 */
	public BigDecimal getMinAmount() {
		return minAmount;
	}

	/**
	 * 单笔区间下限
	 * 
	 * @param minAmount
	 */
	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	/**
	 * 单笔区间上限
	 * 
	 * @return
	 */
	public BigDecimal getMaxAmount() {
		return maxAmount;
	}

	/**
	 * 单笔区间上限
	 * 
	 * @param maxAmount
	 */
	public void setMaxAmount(BigDecimal maxAmount) {
		this.maxAmount = maxAmount;
	}

	/**
	 * 阶梯下限
	 * 
	 * @return
	 */
	public BigDecimal getMinLadderAmount() {
		return minLadderAmount;
	}

	/**
	 * 阶梯下限
	 * 
	 * @param minLadderAmount
	 */
	public void setMinLadderAmount(BigDecimal minLadderAmount) {
		this.minLadderAmount = minLadderAmount;
	}

	/**
	 * 阶梯上限
	 * 
	 * @return
	 */
	public BigDecimal getMaxLadderAmount() {
		return maxLadderAmount;
	}

	/**
	 * 阶梯上限
	 * 
	 * @param maxLadderAmount
	 */
	public void setMaxLadderAmount(BigDecimal maxLadderAmount) {
		this.maxLadderAmount = maxLadderAmount;
	}

	/**
	 * 单笔最低手续费
	 * 
	 * @return
	 */
	public BigDecimal getSingleMinFee() {
		return singleMinFee;
	}

	/**
	 * 单笔最低手续费
	 * 
	 * @param singleMinFee
	 */
	public void setSingleMinFee(BigDecimal singleMinFee) {
		this.singleMinFee = singleMinFee;
	}

	/**
	 * 单笔最高手续费
	 * 
	 * @return
	 */
	public BigDecimal getSingleMaxFee() {
		return singleMaxFee;
	}

	/**
	 * 单笔最高手续费
	 * 
	 * @param singleMaxFee
	 */
	public void setSingleMaxFee(BigDecimal singleMaxFee) {
		this.singleMaxFee = singleMaxFee;
	}

	/**
	 * 计费模式
	 * 
	 * @return
	 */
	public Integer getModel() {
		return model;
	}

	/**
	 * 计费模式
	 * 
	 * @param model
	 */
	public void setModel(Integer model) {
		this.model = model;
	}
}
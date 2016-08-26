package wusc.edu.pay.facade.fee.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.enums.CalApproximationEnum;


/**
 * 费率计算结果DTO
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,下午5:06:14
 */
@SuppressWarnings("serial")
public class FeeCalculateResultDTO implements Serializable {
	/**
	 * 手续费费用
	 */
	public Double payFee = 0D;
	/**
	 * 付款人应付费用
	 */
	public Double payerFee = 0D;
	/**
	 * 收款人应付费用
	 */
	public Double payeeFee = 0D;

	/**
	 * 计费基数，比如0.2%
	 */
	public String feeBase;

	/**
	 * 退款手续费承担角色 FeeRoleTypeEnum
	 */
	public Integer roleType;

	/**
	 * 计费订单
	 */
	public FeeOrder feeOrder;

	/**
	 * 流量累加
	 */
	public FeeFlowAccumulator calFeeFlow;

	public FeeCalculateResultDTO() {
		super();
	}

	public FeeCalculateResultDTO(Double payFee, String feeBase, Integer isRound,Integer roleType) {
		BigDecimal fee = BigDecimal.valueOf(payFee);
		if (isRound == CalApproximationEnum.NONE.getValue()) { // NONE 不做任何操作
			this.payFee = payFee;
		} else if (isRound == CalApproximationEnum.LAST_ROUND.getValue()) {// 舍尾法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		} else if (isRound == CalApproximationEnum.INTO_LAW.getValue()) {// 进一法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_UP).doubleValue();
		} else {// 四舍五入法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		this.feeBase = feeBase;
		this.roleType = roleType;
	}

	public Double getPayFee() {
		return payFee;
	}

	public void setPayFee(Double payFee, Integer isRound) {
		
		BigDecimal fee = BigDecimal.valueOf(payFee);
		if (isRound == CalApproximationEnum.NONE.getValue()) { // NONE 不做任何操作
			this.payFee = payFee;
		} else if (isRound == CalApproximationEnum.LAST_ROUND.getValue()) {// 舍尾法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
		} else if (isRound == CalApproximationEnum.INTO_LAW.getValue()) {// 进一法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_UP).doubleValue();
		} else {// 四舍五入法
			this.payFee = fee.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
	}

	public String getFeeBase() {
		return feeBase;
	}

	public void setFeeBase(String feeBase) {
		this.feeBase = feeBase;
	}

	/**
	 * 付款人应付费用
	 */
	public Double getPayerFee() {
		return payerFee;
	}

	/**
	 * 付款人应付费用
	 */
	public void setPayerFee(Double payerFee) {
		this.payerFee = payerFee;
	}

	/**
	 * 收款人应付费用
	 */
	public Double getPayeeFee() {
		return payeeFee;
	}

	/**
	 * 收款人应付费用
	 */
	public void setPayeeFee(Double payeeFee) {
		this.payeeFee = payeeFee;
	}

	/**
	 * 退款承担角色 FeeRoleTypeEnum
	 * 
	 * @return
	 */
	public Integer getRoleType() {
		return roleType;
	}

	/**
	 * 退款承担角色 FeeRoleTypeEnum
	 * 
	 * @param roleType
	 */
	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	/**
	 * 计费订单
	 * 
	 * @return
	 */
	public FeeOrder getFeeOrder() {
		return feeOrder;
	}

	/**
	 * 计费订单
	 * 
	 * @param feeOrder
	 */
	public void setFeeOrder(FeeOrder feeOrder) {
		this.feeOrder = feeOrder;
	}

	/**
	 * 流量累加
	 * 
	 * @return
	 */
	public FeeFlowAccumulator getCalFeeFlow() {
		return calFeeFlow;
	}

	/**
	 * 流量累加
	 * 
	 * @return
	 */
	public void setCalFeeFlow(FeeFlowAccumulator calFeeFlow) {
		this.calFeeFlow = calFeeFlow;
	}

}

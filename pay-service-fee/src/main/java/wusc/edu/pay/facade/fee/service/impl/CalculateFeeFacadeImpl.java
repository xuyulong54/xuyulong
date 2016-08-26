package wusc.edu.pay.facade.fee.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.CheckUtils;
import wusc.edu.pay.core.fee.biz.CalculateFeeBiz;
import wusc.edu.pay.core.fee.biz.CalculateRefundFeeBiz;
import wusc.edu.pay.core.fee.biz.DealWithFeeOrderBiz;
import wusc.edu.pay.core.fee.biz.FeeFlowAccumulatorBiz;
import wusc.edu.pay.core.fee.biz.FeeOrderBiz;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;


/**
 * 计费Facade实现类
 * 
 * @desc
 * @author shenjialong
 * @date 2014-6-27,下午5:22:07
 */
@Component("calculateFeeFacade")
public class CalculateFeeFacadeImpl implements CalculateFeeFacade {

	@Autowired
	public CalculateFeeBiz calculateFeeBiz; // 计费Biz
	@Autowired
	public FeeQueryFacadeImpl feeQueryFacadeImpl;
	@Autowired
	public FeeOrderBiz feeOrderBiz;
	@Autowired
	public FeeFlowAccumulatorBiz feeFlowAccumulatorBiz;
	@Autowired
	public CalculateRefundFeeBiz calculateRefundFeeBiz;
	@Autowired
	public DealWithFeeOrderBiz dealWithFeeOrderBiz;

//	/**
//	 * 费率计算接口 计算收款方（payee）以及付款方（payer）的费率，分别保存于计费结果DTO的不同属性中
//	 * 
//	 * @param userNo
//	 *            费率调用者编号
//	 * @param userType
//	 *            用户类型
//	 * @param calFeeItem
//	 *            计费项 CalculateFeeItemEnum
//	 * @param payProduct
//	 *            支付产品编号
//	 * @param frpCode
//	 *            支付方式编号
//	 * @param amount
//	 *            待计费金额
//	 * @param transferDate
//	 *            交易发起时间
//	 * @param rateBearerFilter
//	 *            分摊比例 T:1$S:0
//	 * @param merchantName
//	 *            商户名称
//	 * @param orderNo
//	 *            商户订单号
//	 * @param trxNo
//	 *            交易流水号
//	 */
//	public FeeCalculateResultDTO caculateFee(String userNo, Integer userType, Integer calFeeItem, String payProduct, String frpCode, Double amount,Date transferDate, String merchantName, String orderNo, String trxNo) {
//		CheckUtils.notEmpty(userNo, "userNo");
//		CheckUtils.notNull(userType, "userType");
//		CheckUtils.notNull(payProduct, "payProduct");
//		CheckUtils.notNull(frpCode, "frpCode");
//		CheckUtils.notNull(amount, "amount");
//		CheckUtils.notNull(transferDate, "transferDate");
//		
//		/** 查询计费订单是否存在（存在表示已经预算过） **/
//		FeeOrder feeOrder = feeQueryFacadeImpl.getFeeOrderByTrxFlowNo(trxNo);
//		if (feeOrder != null) {
//			/** 直接从计费订单中拿取数据 **/
//			FeeCalculateResultDTO result = new FeeCalculateResultDTO();
//			result.setFeeBase(feeOrder.getFeeBase());
//			result.setPayeeFee(feeOrder.getPayeeFee());
//			result.setPayerFee(feeOrder.getPayerFee());
//			result.setPayFee(AmountUtil.add(feeOrder.getPayeeFee(), feeOrder.getPayerFee()));
//			/** 修改计费订单的状态为已付费 **/
//			feeOrder.setStatus(FeeOrderStatusEnum.TAX_PAID.getValue());// 已付费
//			feeOrderBiz.updateFeeOrder(feeOrder);
//			
//			/** 流量累计 **/
//			/**如果是阶梯类型，则进行流量的累计**/
//			if(FeeCalculateTypeEnum.isLadderAcc(feeOrder.getChargePeriodic())){
//				/** 根据商户编号，计费方式id，创建时间，查询出流量统计**/
//				FeeFlowAccumulator calFeeFlow = feeFlowAccumulatorBiz.getFlowAccumulatorByWayId_UserNo_Time(feeOrder.getCalculateWayId(), userNo ,feeOrder.getCreateTime());
//				calFeeFlow.setThisAmount(amount);
//				calFeeFlow.setTotalAmount(AmountUtil.add(calFeeFlow.getTotalAmount(),amount));
//				calFeeFlow.setModifyDate(feeOrder.getCreateTime());
//				feeFlowAccumulatorBiz.update(calFeeFlow);
//			}
//			return result;
//		} else {
//			return calculateFeeBiz.caculateFee(userNo, userType,calFeeItem, payProduct, frpCode, amount, transferDate, null, merchantName, orderNo, trxNo);
//		}
//	}

	/**
	 * 费率预算接口：
	 * 计算收款方（payee）以及付款方（payer）的费率，分别保存于计费结果DTO的不同属性中
	 * 
	 * @param userNo  费率调用者编号
	 * @param userType	用户类型
	 * @param calFeeItem 计费项 CalculateFeeItemEnum
	 * @param payProduct  支付产品编号
	 * @param frpCode 支付方式编号
	 * @param amount 待计费金额
	 * @param transferDate 交易发起时间
	 * @param rateBearerFilter 分摊比例 S:0$T:1  S:付款方  T:收款方  默认全部由收款方承担设置为 S:0$T:1
	 * @param merchantName 商户名称 
	 * @param orderNo 商户订单号
	 * @param trxNo 交易流水号
	 * @return
	 */
	public FeeCalculateResultDTO preCaculateFee(String userNo, Integer userType, Integer calFeeItem, String payProduct, String frpCode, Double amount, Date transferDate,  String merchantName, String orderNo, String trxNo) {
		CheckUtils.notEmpty(userNo, "userNo");
		CheckUtils.notNull(userType, "userType");
		CheckUtils.notEmpty(calFeeItem, "calFeeItem");
		CheckUtils.notNull(payProduct, "payProduct");
		CheckUtils.notNull(frpCode, "frpCode");
		CheckUtils.notNull(amount, "amount");
		CheckUtils.notNull(transferDate, "transferDate");
		CheckUtils.notNull(orderNo, "orderNo");
		CheckUtils.notNull(trxNo, "trxNo");

		return calculateFeeBiz.caculateFee(userNo, userType, calFeeItem, payProduct, frpCode, amount, transferDate , merchantName, orderNo, trxNo);
	}
	
	/**
	 * 退款退手续费专用接口
	 * @param refundOrderNo
	 * @param oldTrxNo
	 * @param refundAmount
	 * @return
	 * @throws FeeBizException
	 */
	public FeeCalculateResultDTO preCaculateFee(String refundOrderNo, String oldTrxNo ,String newTrxNo, Double refundAmount){
		
		return calculateRefundFeeBiz.caculateRefundFee(refundOrderNo, oldTrxNo ,newTrxNo, refundAmount);
	}
	
	/**
	 * 处理计费订单
	 * @param order
	 */
	public void dealWithFeeOrder(FeeCalculateResultDTO calculateResult){
		dealWithFeeOrderBiz.dealWithFeeOrder(calculateResult);
	}
	
	

}

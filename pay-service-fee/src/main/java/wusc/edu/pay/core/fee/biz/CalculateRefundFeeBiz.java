package wusc.edu.pay.core.fee.biz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.dto.FeeRuleDTO;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeChargeTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeOrderStatusEnum;
import wusc.edu.pay.facade.fee.enums.FeeOrderTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.utils.FeeUtils;


/**
 * 计费Biz接口定义
 * 
 */
@Component("calculateRefundFeeBiz")
public class CalculateRefundFeeBiz {
	private static final Log log = LogFactory.getLog(CalculateRefundFeeBiz.class);

	@Autowired
	private FeeCalculateWayBiz feeCalculateWayBiz;
	@Autowired
	private FeeOrderBiz feeOrderBiz;
	@Autowired
	private FeeFlowAccumulatorBiz feeFlowAccumulatorBiz;

	/**
	 * 退款退手续费专用接口
	 * 
	 * @param oldTrxNo
	 * @param oldTrxNo
	 * @param refundAmount
	 * @return
	 * @throws FeeBizException
	 */
	public FeeCalculateResultDTO caculateRefundFee(String refundOrderNo, String oldTrxNo, String newTrxNo,Double refundAmount) {
		/** 根据 oldTrxNo查询计费订单 **/
		FeeOrder feeOrder = feeOrderBiz.getFeeOrderByTrxNo(oldTrxNo);
		Date transferDate = new Date();
		if (feeOrder == null) {
			throw new FeeBizException(FeeBizException.FEEORDER_IS_NOT_EXIT, "根据流水号查询计费订单不存在");
		}
		// 得到订单金额
		Double orderAmount = feeOrder.getAmount();
		Double payAllFee = feeOrder.getPayAllFee();
		//得到计费方式id
		Long calculateWayId = feeOrder.getCalculateWayId();
		//查询计费方式
		FeeCalculateWay calculateWay = feeCalculateWayBiz.getById(calculateWayId);
		
		if(calculateWay == null){
			throw new FeeBizException(FeeBizException.CALCULATEWAY_IS_NOT_EXIT,String.format("方式Id为{1}的计费方式不存在", calculateWayId));
		}
		
		//是否退款
		Boolean isRefund = calculateWay.getIsRefundFee();
		
		//退款应该退还的手续费
		Double rebackAmount = AmountUtil.div(AmountUtil.mul(refundAmount, payAllFee), orderAmount,6);
		
		//创建FeeOrder
		FeeCalculateResultDTO feeDTO = createFeeCalculateResult(refundOrderNo, isRefund, rebackAmount,  feeOrder ,newTrxNo ,transferDate ,refundAmount);
		
		//减少流量（暂时不减）
//		getCalFeeFlow(calculateWay, transferDate , feeOrder.getMerchantNo(), refundAmount );
		
		return feeDTO;
	}
	
	/**
	 * 创建  费率计算结果DTO
	 * @param isRefund  是否退款
	 * @param rebankAmount  退款应该退还的手续费
	 * @param feeOrder  原计费订单
	 * @return
	 */
	private FeeCalculateResultDTO createFeeCalculateResult(String refundOrderNo, Boolean isRefund,Double rebackAmount, FeeOrder oldFeeOrder,String newTrxNo,Date transferDate,Double refundAmount){
		Integer role = FeeRoleTypeEnum.PLATFORM.getValue();
		Integer oldRole = oldFeeOrder.getFeeRole();
		FeeCalculateResultDTO result = new FeeCalculateResultDTO(rebackAmount,oldFeeOrder.getAmount()+":"+rebackAmount,PublicStatusEnum.INACTIVE.getValue(),FeeRoleTypeEnum.PLATFORM.getValue());
		FeeOrder newOrder = new FeeOrder();
		
		newOrder.setAmount(refundAmount);// 退款金额
		newOrder.setBankChannelCode("");// 支付接口
		newOrder.setCalculateDate(transferDate);// 计费时间
		newOrder.setCalculateFeeItem(CalculateFeeItemEnum.REFUND_ACQUIRING.getValue());// 计费项
		newOrder.setCalculateType(FeeCalculateTypeEnum.SINGLE.getValue());// 计费类型
		newOrder.setCalculateWayId(oldFeeOrder.getCalculateWayId());// 计费方式id
		newOrder.setChargePeriodic(oldFeeOrder.getChargePeriodic());// 阶梯周期类型
		newOrder.setChargeType(FeeChargeTypeEnum.REAL_TIME.getValue());// 收费方式(默认设置为实收)
		newOrder.setFeeBase(refundAmount+":"+BigDecimal.valueOf(rebackAmount).toPlainString());// 费率
		newOrder.setFeeRole(role);// 计费角色
		newOrder.setFrpCode("");// 支付方式
		newOrder.setMerchantName(oldFeeOrder.getMerchantName());// 商户名称
		newOrder.setMerchantNo(oldFeeOrder.getMerchantNo());// 商户编号
		newOrder.setMerchantOrderNo(refundOrderNo);// 退款订单号
		newOrder.setOlFrpCode(oldFeeOrder.getFrpCode());// 原支付方式
		newOrder.setOlPayProduct(oldFeeOrder.getPayProduct());// 原支付产品
		newOrder.setOrderType(FeeOrderTypeEnum.REFUND.getValue());// 订单类型 ==退款
		newOrder.setParentFlowNo(oldFeeOrder.getTrxFlowNo());// 父交易流水号

		newOrder.setPayProduct("");// 支付产品
		newOrder.setRemark("退款计费订单");// 备注
		newOrder.setTrxDate(oldFeeOrder.getTrxDate());// 交易时间
		newOrder.setTrxFlowNo(newTrxNo);// 交易流水号
		newOrder.setUserType(oldFeeOrder.getUserType());// 用户类型
		newOrder.setPayAllFee(rebackAmount);// 总的手续费
		
		//总手续费大于0且退款     （不退款平台自己承担）
		if(AmountUtil.bigger(oldFeeOrder.getPayAllFee(),0D) && !isRefund){
			role = oldRole;
			FeeUtils.distributionFee(role, result);
			newOrder.setPayeeFee(result.getPayeeFee());// 收款方应承担手续费
			newOrder.setPayeeUnBackFee(result.getPayeeFee());// 收款方未退金额
			newOrder.setPayerFee(result.getPayerFee());// 付款方应承担手续费
			newOrder.setPayerUnBackFee(result.getPayeeFee());// 付款方未退金额(手续费做了四舍五入)
		}
		//不退平台自己承担
		else{
			newOrder.setPayeeFee(result.getPayeeFee());// 收款方应承担手续费
			newOrder.setPayeeUnBackFee(result.getPayeeFee());// 收款方未退金额
			newOrder.setPayerFee(result.getPayerFee());// 付款方应承担手续费
			newOrder.setPayerUnBackFee(result.getPayeeFee());// 付款方未退金额(手续费做了四舍五入)
			
		}
		newOrder.setConfirmDate(null);// 实收时间
		newOrder.setStatus(FeeOrderStatusEnum.TAX_CALCULATE.getValue());// 已计费
		
		result.setRoleType(role);
		result.setFeeOrder(newOrder);
		return result;
	}
	
	
	/**
	 * 退款流量减少
	 * 
	 * @param feeRule
	 * @return
	 */
	private FeeFlowAccumulator getCalFeeFlow(FeeCalculateWay calculateWay, Date transferDate, String userNo, Double amount) {
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		// 获取当前交易的累加项
		Map<String, Date> dates = FeeUtils.findAccTimeInterval(calculateWay.getLadderCycleType(), calculateWay.getCustomizeCycleType(), calculateWay.getCustomizeDay(), transferDate);

		// 根据用户编号，方式id查找流量累加
		List<FeeFlowAccumulator> calFeeFlows = feeFlowAccumulatorBiz.getFlowAccumulator(calculateWay.getId(), userNo);
		int calFeeFlowCount = (calFeeFlows == null) ? 0 : calFeeFlows.size();
		log.info(String.format("根据计费约束编号[%d]查找到[%d]条流量信息", calculateWay.getId(), calFeeFlowCount));
		String startDateCycle = dateFormat.format(dates.get("start"));
		String endDateCycle = dateFormat.format(dates.get("end"));
		FeeFlowAccumulator calFeeFlow = null;
		for (int i = 0; i < calFeeFlowCount; i++) {
			calFeeFlow = calFeeFlows.get(i);
			// 流量累计区间起
			String startDate = dateFormat.format(calFeeFlow.getAccIntervalStart());
			// 流量累计区间止
			String endDate = dateFormat.format(calFeeFlow.getAccIntervalEnd());
			if (!startDateCycle.equals(startDate)) {
				continue;
			}
			if (!endDateCycle.equals(endDate)) {
				continue;
			}
			calFeeFlow.setTotalAmount(AmountUtil.sub(calFeeFlow.getTotalAmount(), amount));
			return calFeeFlow;
		}

		// 创建流量记录
		calFeeFlow = new FeeFlowAccumulator();
		calFeeFlow.setAccIntervalEnd(dates.get("end"));
		calFeeFlow.setAccIntervalStart(dates.get("start"));
		calFeeFlow.setCalculateWayId(calculateWay.getId());
		calFeeFlow.setMerchantNo(userNo);
		calFeeFlow.setModifyDate(new Date());
		calFeeFlow.setThisAmount(0D);
		calFeeFlow.setTotalAmount(-amount);
		return calFeeFlow;
	}

}

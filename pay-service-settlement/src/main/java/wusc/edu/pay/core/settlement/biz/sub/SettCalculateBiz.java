package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.core.settlement.dao.SettRecordDao;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;
import wusc.edu.pay.facade.settlement.entity.SettDailyCollect;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.enums.SettDailyCollectTypeEnum;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;


/**
 * 
 * @描述: 自动结算核心计算biz.
 * @作者: Along.shen .
 * @创建时间:2015-3-11,下午5:41:23.
 * @版本:
 */
@Component("settCalculateBiz")
public class SettCalculateBiz {
	private static final Log log = LogFactory.getLog(SettCalculateBiz.class);
	@Autowired
	private CalculateFeeFacade calculateFeeFacade;

	@Autowired
	private SettRecordDao settRecordDao;

	/**
	 * @描述: 结算处理，计算手续费.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-4,下午2:03:50.
	 * @版本: 计算方式： <br/>
	 *      1.根据每条汇总中的riskDay值去计算每条汇总的手续费 <br/>
	 *      2.把每条汇总的手续费 统计保存到settRecord中（多条计费订单）<br/>
	 */
	public void doProcessSettle(List<SettDailyCollect> dailys, SettRecord settRecord, SettRule settRule) {
		log.info("[账户编号："+settRecord.getAccountNo()+"总的每日汇总条数:"+dailys.size()+"]");
		// 初始化费率计算结果DTO的list
		List<FeeCalculateResultDTO> calculateResultDTOList = new ArrayList<FeeCalculateResultDTO>();
		// 初始化结算手续费
		double settFee = 0D;

		// 把汇总中为负值的和riskDay=null为正的对冲
		BigDecimal subHedging = dailyMinuSumHedging(dailys);

		// 计费订单流水号后面补位值
		int i = 0;

		// 控制选择出款的汇总返回可汇总
		List<SettDailyCollect> resultDailys = null;

		// 判断subHedging
		// 如果为正(>0.01)，则负值已经全部被处理
		// 如果为负(<0.01)，则要去对冲这批汇总中riskDay不为null的汇总
		if (subHedging.compareTo(BigDecimal.valueOf(0.01)) == 1) {
			i++;
			int calculateFeeItem = CalculateFeeItemEnum.SETTLEMENT_ACQUIRING.getValue();
			Double calAmount = Double.valueOf(subHedging.toString());
			// 计算商户结算手续费
			final FeeCalculateResultDTO settFeeDto = calculateFeeFacade.preCaculateFee(settRecord.getUserNo(), UserTypeEnum.MERCHANT.getValue(), calculateFeeItem, "SETTLEMENT_ACQUIRING", "SETTLEMENT_ACQUIRING", calAmount, new Date(), settRecord.getUserName(), settRecord.getBatchNo() + "_" + i, settRecord.getBatchNo() + "_" + i);
			// 累加结算手续费
			settFee = settFee + settFeeDto.getPayeeFee();
			settFeeDto.calFeeFlow = null;
			calculateResultDTOList.add(settFeeDto);
			resultDailys = dailys;
		} else if (subHedging.compareTo(BigDecimal.valueOf(-0.01)) == -1) {
			// 用非常规汇总对冲subHedging
			resultDailys = dailySubHedging(subHedging, dailys);
		}else{
			resultDailys = dailys;
		}
		
		
		if (resultDailys == null) {
			log.info("[账户编号："+settRecord.getAccountNo()+"剩下每日汇总条数:0]");
			throw new SettBizException(SettBizException.SETT_IS_IN_LIMIT, "没有满足可结算条件的汇总。");
		}
		log.info("[账户编号："+settRecord.getAccountNo()+"剩下每日汇总条数:"+resultDailys.size()+"]");
		
		for (SettDailyCollect daily : resultDailys) {
			Integer riskDay = daily.getRiskDay();

			// 如果是遗留资金汇总，不用计算手续费
			if (daily.getCollectType() != null && daily.getCollectType() == SettDailyCollectTypeEnum.LEAVE.getValue()) {
				continue;
			}

			// 如果riskDay==null
			if (riskDay == null || daily.getTotalAmount().compareTo(BigDecimal.ZERO) < 1) {
				continue;
			}

			i++;
			// 得到计算手续费的值
			BigDecimal calAmountDecimal = daily.getTotalAmount();
			Double calAmount = Double.valueOf(calAmountDecimal.toString());
			// 初始化结算计费项
			CalculateFeeItemEnum calculateFeeItem = null;
			// 根据每日汇总里面的riskDay判断--计费项
			// T+1
			if (riskDay == 1) {
				calculateFeeItem = CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_1;
			}
			// T+5
			else if (riskDay == 5) {
				calculateFeeItem = CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_5;
			}
			// 其他情况也常规处理（T+n）
			else {
				calculateFeeItem = CalculateFeeItemEnum.SETTLEMENT_ACQUIRING;
			}
			// 计算商户结算手续费
			final FeeCalculateResultDTO settFeeDto = calculateFeeFacade.preCaculateFee(settRecord.getUserNo(), UserTypeEnum.MERCHANT.getValue(), calculateFeeItem.getValue(), calculateFeeItem.name(), calculateFeeItem.name(), calAmount, new Date(), settRecord.getUserName(), settRecord.getBatchNo() + "_" + i, settRecord.getBatchNo() + "_" + i);
			// 累加结算手续费
			settFee = settFee + settFeeDto.getPayeeFee();
			settFeeDto.calFeeFlow = null;
			calculateResultDTOList.add(settFeeDto);

		}

		// settRecord中保存calculateResultDTOList
		settRecord.setCalculateResultDTOList(calculateResultDTOList);

		Double settAmount = Double.valueOf(settRecord.getSettAmount().toString());

		// 得到打款金额(没有做到分的取舍，结算记录审核时做处理)
		double remitAmount = AmountUtil.sub(settAmount, settFee); // 打款金额
		settRecord.setSettFee(BigDecimal.valueOf(settFee)); // 结算手续费
		settRecord.setRemitAmount(BigDecimal.valueOf(remitAmount)); // 结算打款金额

		// 增加判断 =====by shenjialong
		// 1.判断结算金额是否小于0.01 如果不足一分 不生成结算记录
		// 2.判断打款金额（结算金额-手续费）是否大于0.01 若小于0.01 则不生成结算记录
		// tip：1中的判断在2中已经包含 所以只要添加2判断即可。
		// 结算金额-手续费
		BigDecimal subtract = settRecord.getSettAmount().subtract(settRecord.getSettFee());
		if (subtract.compareTo(BigDecimal.valueOf(0.01D)) == -1) {
			throw new SettBizException(SettBizException.SETTLE_AMOUNT_ACCOUNT_CHECKERR, "扣除手续费后,打款金额不足0.01元,不生成结算记录！");
		}

		log.info("settleAmount after check:" + settAmount + " ,结算手续费:" + settFee);

		log.info("create:" + settRecord);
		Long id = settRecordDao.insert(settRecord);
		settRecord.setId(id);
		settRecord.setDailys(dailys);
	}

	/**
	 * 
	 * @return
	 * @描述: 控制选择出款的汇总算法.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-6,上午10:38:44.
	 * @subHedging 为负值
	 * @dailys 所有的待出款每日汇总
	 */
	private List<SettDailyCollect> dailySubHedging(BigDecimal subHedging, List<SettDailyCollect> dailys) {
		// 取出subHedging的绝对值
		BigDecimal single = subHedging.abs();

		// 把遗留资金用非常规对冲
		List<SettDailyCollect> hedgingDaily = hedgingDaily(dailys, single);
		return hedgingDaily;
	}

	/**
	 * @描述: 把遗留资金用非常规对冲.
	 * @创建时间:2015-3-11,下午5:13:07.
	 * @版本:
	 */
	private List<SettDailyCollect> hedgingDaily(List<SettDailyCollect> oldDailys, BigDecimal single) {
		
		List<SettDailyCollect> dailys = new ArrayList<SettDailyCollect>();
		for(SettDailyCollect daily : oldDailys) {
			dailys.add(daily);
		}
		
		// 得到这批汇总中最大riskDay
		int maxRiskDay = 0;
		for (SettDailyCollect daily : dailys) {
			if (daily.getRiskDay() != null && daily.getRiskDay() > maxRiskDay) {
				maxRiskDay = daily.getRiskDay();
			}
		}

		// 得到最大riskDay的金额值
		BigDecimal tempAmount = BigDecimal.ZERO;
		for (SettDailyCollect daily : dailys) {
			if (daily.getRiskDay() != null && daily.getRiskDay() == maxRiskDay) {
				tempAmount = tempAmount.add(daily.getTotalAmount());
			}
		}
		// 对冲maxRiskDay之后 还有余留 继续对冲
		if (single.compareTo(tempAmount) == 1) {
			List<SettDailyCollect> leaveDaily = new ArrayList<SettDailyCollect>();
			// 对冲之后遗留金额
			BigDecimal leaveAmount = tempAmount.subtract(single);
			// 移除所有maxRiskDay记录
			for (SettDailyCollect daily : dailys) {
				if (daily.getRiskDay() == null || daily.getRiskDay() != maxRiskDay) {
					leaveDaily.add(daily);
				}
			}

			// 迭代处理
			return hedgingDaily(leaveDaily, leaveAmount);

		} else {
			List<SettDailyCollect> leaveDaily = new ArrayList<SettDailyCollect>();
			SettDailyCollect temp = new SettDailyCollect();
			// 对冲之后遗留金额
			BigDecimal leaveAmount = tempAmount.subtract(single);
			// 移除所有maxRiskDay记录
			for (SettDailyCollect daily : dailys) {
				if (daily.getRiskDay() == null || daily.getRiskDay() != maxRiskDay) {
					leaveDaily.add(daily);
				}
			}
			// 添加一条maxRiskDay的汇总金额为：leaveAmount
			temp.setTotalAmount(leaveAmount);
			temp.setRiskDay(maxRiskDay);
			leaveDaily.add(temp);
			return leaveDaily;
		}
	}

	/**
	 * @return
	 * @描述: //把汇总中为负值的和riskDay=null为正的对冲.
	 * @作者: Along.shen .
	 * @创建时间:2015-2-6,上午9:51:53.
	 * @return 汇总中为负值+riskDay=null的正值:
	 */
	private BigDecimal dailyMinuSumHedging(List<SettDailyCollect> dailys) {
		// 汇总中为负的和
		BigDecimal minuSum = BigDecimal.ZERO;

		// 汇总总为正的和且riskDay 为null
		BigDecimal plusSum = BigDecimal.ZERO;
		// 遍历每日汇总
		for (SettDailyCollect daily : dailys) {
			// 如果汇总的金额为负
			if (daily.getTotalAmount().compareTo(BigDecimal.ZERO) == -1) {
				// 把所有的为负的金额累加
				minuSum = minuSum.add(daily.getTotalAmount());
				continue;
			}

			Integer riskDay = daily.getRiskDay();
			if (riskDay == null && daily.getTotalAmount().compareTo(BigDecimal.ZERO) == 1) {
				plusSum = plusSum.add(daily.getTotalAmount());
			}
		}
		BigDecimal leaveAmount = plusSum.add(minuSum);
		log.info("[常规对冲遗留金额："+leaveAmount+"]");
		return leaveAmount;
	}

}

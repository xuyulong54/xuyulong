package wusc.edu.pay.core.cost.biz.cal;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.core.cost.biz.CalFeeFlowBiz;
import wusc.edu.pay.core.cost.biz.cal.abs.AbstractBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.AfterBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.FreeBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.LadderMultipleBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.LadderSingleBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.SectionBankCost;
import wusc.edu.pay.core.cost.biz.cal.impl.SingleBankCost;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.CalPeriodeEnum;
import wusc.edu.pay.facade.cost.enums.CalTypeEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;

/**
 * 银行成本计费的工厂类
 * @author yzyue
 *
 */
public class BankCostFactory {

	protected static Log logger = LogFactory.getLog(BankCostFactory.class);
	
	/**
	 * 根据不同的计费方式获取银行成本的计费规则
	 * @param order 客户端上送的成本订单信息
	 * @param calFeeFlowBiz 计费流量的操作接口
	 * @param calFeeWay 计费约束信息
	 * @param date 当前的时间
	 * @return
	 */
	public static AbstractBankCost newInstance(CalCostOrder order, CalFeeFlowBiz calFeeFlowBiz, CalFeeWay calFeeWay, Date date) throws CostBizException{
		logger.info(String.format("判断该笔订单的计费方式[%d](实收-1, 后收-2)", calFeeWay.getCalPeriod()));
		order.setCalOrderType(calFeeWay.getCalPeriod());//计费方式
		if (calFeeWay.getCalPeriod().intValue() == CalPeriodeEnum.AFTER.getValue()) {
			//事后
			logger.info("该笔订单不需要实时计算手续费");
			return new AfterBankCost(order);
		}
		logger.info(String.format("判断该笔订单是否属于免计费订单(当前免计费金额: %s, 当前交易金额:%s)", calFeeWay.getFeeFreeAmount(), order.getAmount()));
		if (calFeeWay.getFeeFreeAmount() != null && order.getAmount().compareTo(calFeeWay.getFeeFreeAmount()) <= 0) {//免计费金额为包含关系
			// 免计费
			logger.info("该笔订单属于免计费订单,手续费为0");
			return new FreeBankCost(order);
		}
		int calType = calFeeWay.getCalType().intValue(); 
		if(calType == CalTypeEnum.SIMPLE.getValue()){
			//单笔
			logger.info("该笔订单属于单笔计费订单");
			return new SingleBankCost(order);
		}else if(calType == CalTypeEnum.INTERVAL.getValue()){
			//区间
			logger.info("该笔订单属于区间计费订单");
			return new SectionBankCost(order);
		}else if(calType == CalTypeEnum.LADDER_SINGLE.getValue()){
			//单阶梯
			logger.info("该笔订单属于单阶梯计费订单");
			return new LadderSingleBankCost(order, calFeeFlowBiz, calFeeWay, date);
		}else if(calType == CalTypeEnum.LADDER_MULTIPLE.getValue()){
			//多阶梯
			logger.info("该笔订单属于多阶梯计费订单");
			return new LadderMultipleBankCost(order, calFeeFlowBiz, calFeeWay, date);
		}else{
			throw CostBizException.CAL_FEE_ERROR.newInstance("未知的计费方式[%s]", calType);
		}
	} 

}

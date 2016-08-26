package wusc.edu.pay.core.cost.biz.cal;

import java.util.List;

import wusc.edu.pay.core.cost.biz.CalFeeRateFormulaBiz;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 计费成本
 * 
 * @author yzyue
 * 
 */
public interface BankCost {
	
	/**
	 * <pre>
	 * 	根据计费约束获取计费公式
	 * 		由于免计费订单、包年和事后计费订单均不需要获取计费公式，
	 * 	而单笔、区间和阶梯类型的订单需要查询计费公式，故在此接口中定
	 * 	义此方法。
	 * </pre>
	 * @param formulaBiz 计费公式的业务处理类 
	 * @param calFeeWay 计费约束
	 * @return
	 */
	public List<CalFeeRateFormula> getFormula(CalFeeRateFormulaBiz formulaBiz, CalFeeWay calFeeWay) throws CostBizException;

	/**
	 * 根据计费公式计算成本
	 * @param formulas 计费公式
	 * 
	 * @return
	 */
	public boolean calculate(List<CalFeeRateFormula> formulas)
			throws CostBizException;
	
	/**
	 * <pre>
	 * 	保存计费流量信息
	 * 		只有阶梯计费类型的订单才涉及到计费流量，才需要保存计费流量。
	 * </pre>
	 * @return
	 * @throws CostBizException
	 */
	public boolean saveFlowInfo() throws CostBizException;

}

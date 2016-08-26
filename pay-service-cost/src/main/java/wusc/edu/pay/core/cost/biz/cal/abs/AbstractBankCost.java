package wusc.edu.pay.core.cost.biz.cal.abs;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.core.cost.biz.CalFeeRateFormulaBiz;
import wusc.edu.pay.core.cost.biz.cal.BankCost;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.CalModelEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;



public abstract class AbstractBankCost implements BankCost {
	/**
	 * 成本的手续费
	 */
	protected BigDecimal fee;
	/**
	 * 客户端上送的订单
	 */
	protected CalCostOrder order;
	/**
	 * 客户端上送的交易金额
	 */
	protected BigDecimal amount;
	
	protected Log logger = LogFactory.getLog(AbstractBankCost.class);
			
	/**
	 * 该构造方式用于单笔、区间和免计费等0手续费的计费方式
	 * @param order
	 */
	public AbstractBankCost(CalCostOrder order) {
		super();
		this.order = order;
		this.amount = order.getAmount();
	}

	/**
	 * 根据约束信息获取公式信息
	 * @param formulaBiz
	 * @param calFeeWay
	 * @return
	 */
	@Override
	public List<CalFeeRateFormula> getFormula(CalFeeRateFormulaBiz formulaBiz, CalFeeWay calFeeWay) {
		logger.info(String.format("根据费率约束信息[%s/%s]获取费率公式信息", calFeeWay.getId(), calFeeWay.getWayName()));
		List<CalFeeRateFormula> formulas = formulaBiz.getRateFormulaByFeeWayId(calFeeWay.getId());
		int formulaCount = (formulas == null) ? 0 : formulas.size();
		logger.info("找到了[" + formulaCount + "]条公式信息");
		return formulas;
	}



	/**
	 * 保存计费流量信息
	 * @return
	 * @throws CostBizException
	 */
	@Override
	public boolean saveFlowInfo() throws CostBizException{
		return true;
	}

	/**
	 * 获取成本手续费
	 * @return
	 */
	public BigDecimal getFee(){
		return this.fee;
	}

}

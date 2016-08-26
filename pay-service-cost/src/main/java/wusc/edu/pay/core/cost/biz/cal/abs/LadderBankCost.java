package wusc.edu.pay.core.cost.biz.cal.abs;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.core.cost.biz.CalFeeFlowBiz;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


public abstract class LadderBankCost extends AbstractBankCost {
	/**
	 * 当前流量的起始日期
	 */
	protected Date beginDate;
	/**
	 * 当前流量的截止日期
	 */
	protected Date endDate;
	/**
	 * 计费流量的业务操作
	 */
	protected CalFeeFlowBiz calFeeFlowBiz;
	/**
	 * 计费约束实体
	 */
	protected CalFeeWay calFeeWay;
	/**
	 * 当前的日期时间
	 */
	protected Date date;
	/**
	 * 当前的计费流量信息
	 */
	protected CalFeeFlow calFeeFlow;

	protected LadderBankCost(CalCostOrder order) {
		super(order);
	}

	/**
	 * 用于阶梯类型的计费
	 * 
	 * @param order
	 * @param calFeeFlowBiz
	 * @param calFeeWay
	 * @param date
	 */
	public LadderBankCost(CalCostOrder order, CalFeeFlowBiz calFeeFlowBiz, CalFeeWay calFeeWay, Date date) {
		this(order);
		this.calFeeFlowBiz = calFeeFlowBiz;
		this.calFeeWay = calFeeWay;
		this.date = date;
	}

	/**
	 * 初始化计费周期、获取当前已对应的计费流量
	 * 
	 * @throws CostBizException
	 */
	protected void inital() throws CostBizException {
		if (calFeeWay.getCycleType() == null) {
			throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("计费周期类型未设置");
		}
		beginDate = calFeeFlowBiz.fetchCycleBeginDate(calFeeWay, date);
		endDate = calFeeFlowBiz.fetchCycleEndDate(calFeeWay, date);
		this.calFeeFlow = calFeeFlowBiz.fetchCalFeeFlow(calFeeWay, beginDate, endDate);
	}

	/**
	 * <pre>
	 * 保存计费流量信息
	 * 	如果不存在，则创建计费流量信息 ；
	 * 	如果已存在，此时，更新修改日期、交易总金额和本次交易金额信息
	 * </pre>
	 * 
	 * @return
	 * @throws CostBizException
	 */
	@Override
	public boolean saveFlowInfo() throws CostBizException {
		if (calFeeFlow == null) {
			if (this.beginDate == null || this.endDate == null) {
				logger.error(String.format("创建流量信息出现异常,计费周期异常,beginDate=[%s], endDate=[%s]", this.beginDate, this.endDate));
				return false;
			}
			calFeeFlow = new CalFeeFlow();
			calFeeFlow.setBeginDate(this.beginDate);
			calFeeFlow.setEndDate(this.endDate);
			calFeeFlow.setCreateTime(date);
			calFeeFlow.setTotalAmount(amount);
			calFeeFlow.setModifyTime(date);
			calFeeFlow.setThisAmount(amount);
			calFeeFlow.setFeeWayId(this.calFeeWay.getId());
			// calFeeFlowBiz.create(calFeeFlow);
			order.setFeeFlow(calFeeFlow);
		} else {
			if (calFeeFlow.getTotalAmount() == null) {
				logger.error("修改流量信息出现异常,交易总金额不能为空");
				return false;
			}
			BigDecimal totalAmount = calFeeFlow.getTotalAmount().add(amount);
			calFeeFlow.setThisAmount(amount);// 本次交易金额
			calFeeFlow.setTotalAmount(totalAmount);// 流量总金额
			calFeeFlow.setModifyTime(date);
			order.setFeeFlow(calFeeFlow);
			// calFeeFlowBiz.update(calFeeFlow);
		}
		return true;
	}
}

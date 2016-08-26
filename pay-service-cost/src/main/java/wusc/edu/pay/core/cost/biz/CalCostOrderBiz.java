package wusc.edu.pay.core.cost.biz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.CheckUtils;
import wusc.edu.pay.core.cost.biz.cal.BankCostFactory;
import wusc.edu.pay.core.cost.biz.cal.abs.AbstractBankCost;
import wusc.edu.pay.core.cost.dao.CalCostOrderDao;
import wusc.edu.pay.facade.cost.entity.CalCostInterface;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.cost.entity.CalDimension;
import wusc.edu.pay.facade.cost.entity.CalFeeFlow;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.CalApproximationEnum;
import wusc.edu.pay.facade.cost.enums.CalTypeEnum;
import wusc.edu.pay.facade.cost.enums.CostInterfacePolicyEnum;
import wusc.edu.pay.facade.cost.enums.CostItemEnum;
import wusc.edu.pay.facade.cost.enums.SystemResourceTypeEnum;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;


/**
 * 
 * @描述: 成本订单信息表业务实现类 .
 * @作者: huqian .
 * @创建时间: 2014-7-1, 上午11:35:52
 */
@Component("calCostOrderBiz")
public class CalCostOrderBiz extends BaseBizImpl<CalCostOrder> {

	@Autowired
	private CalCostOrderDao calCostOrderDao;

	@Autowired
	private CalDimensionBiz calDimensionBiz;
	@Autowired
	private CalFeeFlowBiz calFeeFlowBiz;
	@Autowired
	private CalFeeWayBiz calFeeWayBiz;
	@Autowired
	private CalFeeRateFormulaBiz calFeeRateFormulaBiz;
	@Autowired
	private CalCostInterfaceBiz calCostInterfaceBiz;

	/**
	 * log4j日志记录
	 */
	private Log logger = LogFactory.getLog(CalCostOrderBiz.class);

	protected BaseDao<CalCostOrder> getDao() {
		return calCostOrderDao;
	}

	/**
	 * 根据银行订单号查询
	 * 
	 * @param bankOrderNo
	 * @return
	 */
	public CalCostOrder getBybankOrderNo(String bankOrderNo) {
		return calCostOrderDao.getBybankOrderNo(bankOrderNo);
	}

	/**
	 * 保存订单和流量信息
	 * 
	 * @param order
	 *            订单信息
	 * @param feeFlow
	 *            流量信息
	 * @throws CostBizException
	 */
	@Transactional(rollbackFor = Exception.class, readOnly = false)
	public void create(CalCostOrder order, CalFeeFlow feeFlow) throws CostBizException {
		/** 判断交易流水号是否重复 **/
		if (this.isDoubleOrder(order)) {
			throw CostBizException.CAL_FEE_ERROR.newInstance("交易流水号[%s]已经存在", order.getTrxNo());
		}
		try {
			/** 验证客户端上送的信息是否完整 **/
			logger.info("验证消息队列上送的成本订单信息");
			if (this.createInfoValidate(order)) {
				logger.info(String.format("成本订单验证通过,银行接口[%s],交易金额[%s],交易流水[%s],成本计费项[%s]", order.getCalInterface(), order.getAmount(),
						order.getTrxNo(), order.getCostItem()));
			} else {
				throw CostBizException.COST_ORDER_INVALID;
			}
		} catch (Exception e) {
			order.setStatus(PublicStatusEnum.INACTIVE.getValue());
			order.setFailedReason(e.getMessage());
			logger.error("银行成本保存失败", e);
			throw CostBizException.CAL_FEE_ERROR.newInstance("银行成本保存失败, %s", e.getMessage());
		} finally {
			/** 保存银行成本信息 **/
			order.setCalEndTime(new Date());
			/** 保存订单信息和流量信息 **/
			logger.info("保存成本订单信息");
			this.create(order);
			if (feeFlow != null) {
				if (feeFlow.getId() != null && feeFlow.getId() > 0) {
					calFeeFlowBiz.update(feeFlow);
				} else {
					calFeeFlowBiz.create(feeFlow);
				}
			}
			logger.info("银行成本保存完成");
		}
	}

	/**
	 * <pre>
	 * 	根据系统来源和交易流水号获取成本订单信息
	 * 	* 由于（系统来源 + 交易流水号）在数据库的成本订单表中是唯一的，故只能获取到一个值
	 * </pre>
	 * 
	 * @param fromSystem
	 *            系统来源
	 * @param trxno
	 *            交易流水号
	 * @return
	 */
	public CalCostOrder getByTrxno(String fromSystem, String trxno) {
		return calCostOrderDao.getByTrxno(fromSystem, trxno);
	}

	/**
	 * <pre>
	 * 	验证客户端上送的成本订单信息
	 * 	验证的信息有：
	 * 		1、银行接口		-	用于获取计费维度、计费约束
	 * 		2、交易金额		-	用于从计费公式中获取有效的规则
	 * 		3、交易流水号	-	用于判定重复交易和获取原交易
	 * 		4、交易类型		-	用于判定是否存在原交易
	 * </pre>
	 * 
	 * @param order
	 *            成本订单信息
	 * @return
	 */
	private boolean createInfoValidate(CalCostOrder order) {
		if (order == null) {
			logger.error("客户端上送的订单信息不能为空");
			return false;
		}
		if (CheckUtils.isEmpty(order.getCalInterface())) {
			logger.error("银行接口信息不能为空");
			return false;
		}
		if (CheckUtils.isEmpty(order.getAmount())) {
			logger.error("交易金额不能为空");
			return false;
		}
		if (CheckUtils.isEmpty(order.getFromSystem())) {
			logger.error("系统来源不能为空");
			return false;
		}
		if (CheckUtils.isEmpty(order.getTrxNo())) {
			logger.error("交易流水号不能为空");
			return false;
		}
		if (order.getAmount().compareTo(BigDecimal.valueOf(0)) <= 0) {
			logger.error(String.format("交易金额[%s]有误", order.getAmount()));
			return false;
		}
		if (CheckUtils.isEmpty(order.getCostItem()) || CostItemEnum.getEnum(order.getCostItem()) == null) {
			logger.error(String.format("成本计费项[%s]有误", order.getCostItem()));
			return false;
		}
		return true;
	}

	/**
	 * <pre>
	 * 	根据交易类型判断该交易是否存在原交易
	 * 	需要计算手续费的成本订单存在原交易的判定依据：
	 * 		1、所有的退款（或退货）交易
	 * 		2、所有的撤销交易
	 * 		3、所有的冲正交易
	 * </pre>
	 * 
	 * @param order
	 *            客户端上送的成本订单信息
	 * @return
	 */
	private boolean existOriginalInfo(CalCostOrder order) {
		return !CheckUtils.isEmpty(order.getOrgTrxNo());
	}

	/**
	 * <pre>
	 * 	判断是否属于重复交易
	 * 	注意事项
	 * 		* 由于在成本订单表中，交易流水号是唯一的，故根据交易流水号来判定。
	 * 		* 在此设置成本订单的创建时间
	 * </pre>
	 * 
	 * @param order
	 *            客户端上送的成本订单信息
	 * @return
	 */
	private boolean isDoubleOrder(CalCostOrder order) {
		if (order == null || CheckUtils.isEmpty(order.getTrxNo())) {
			return false;
		}
		order.setCreateTime(new Date());
		return this.getByTrxno(order.getFromSystem(), order.getTrxNo()) != null;
	}

	/**
	 * 根据原交易流水号获取手续费
	 * 
	 * @param amount
	 *            本次退款(货)交易的退款金额
	 * @param fromSystem
	 *            系统来源
	 * @param orgTrxNo
	 *            原交易流水号
	 * @param orgOrder
	 *            原交易订单信息
	 * @return
	 */
	private BigDecimal getOrgOrderFee(BigDecimal amount, CalCostOrder orgOrder) throws CostBizException {
		logger.info("判断是否属于全部退款(货)");
		if (amount.compareTo(orgOrder.getAmount()) == 0) {
			// 全部退款(货)
			logger.info(String.format("该交易属于全部退款(货),手续费[%s]将全额退", orgOrder.getFee()));
			return orgOrder.getFee();
		}
		logger.info(String.format("根据系统来源[%s]和原交易流水号[%s]获取已退款(货)的交易信息", orgOrder.getFromSystem(), orgOrder.getTrxNo()));
		BigDecimal hadRefundAmount = BigDecimal.ZERO;
		BigDecimal hadRefundFee = BigDecimal.ZERO;
		List<CalCostOrder> orgOrders = calCostOrderDao.listByOrgTrxNo(orgOrder.getFromSystem(), orgOrder.getTrxNo());
		int size = (orgOrders == null) ? 0 : orgOrders.size();
		logger.info(String.format("找到了[%d]笔已退款的信息", size));
		if (size > 0) {
			for (int i = 0; i < size; i++) {
				CalCostOrder order = orgOrders.get(i);
				if (order == null)
					continue;
				logger.info(String.format("已退款订单[%d/%d],交易金额[%s],手续费[%s],交易状态[%s]", i + 1, size, order.getAmount(), order.getFee(),
						order.getStatus()));
				if (order.getAmount() == null)
					continue;
				if (order.getFee() == null)
					continue;
				if (order.getStatus().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
					continue;
				}
				hadRefundAmount = hadRefundAmount.add(order.getAmount());
				hadRefundFee = hadRefundFee.add(order.getFee());
			}
		}
		logger.info(String.format("已退款(货)的交易金额为[%s], 手续费为[%s]", hadRefundAmount, hadRefundFee));
		int flag = hadRefundAmount.add(amount).compareTo(orgOrder.getAmount());
		if (flag == 0) {
			// 最后一次退款
			BigDecimal leftFee = orgOrder.getFee().subtract(hadRefundFee);
			logger.info(String.format("该交易属于最后一次退款，则将剩余的手续费[%s]全部退还", leftFee));
			return leftFee.setScale(2, BigDecimal.ROUND_HALF_UP);
		} else if (flag == 1) {
			throw CostBizException.CAL_FEE_ERROR.newInstance("退款的总金额[%s]超过了原交易金额[%s]", hadRefundAmount.add(amount), orgOrder.getAmount());
		} else {
			BigDecimal fee = orgOrder.getFee().multiply(amount).divide(orgOrder.getAmount(), 6, BigDecimal.ROUND_HALF_UP);
			logger.info(String.format("该交易将按原交易的手续费[%s]比例退还手续费[%s]", orgOrder.getFee(), fee));
			return fee.setScale(2, BigDecimal.ROUND_HALF_UP);
		}
	}

	/**
	 * <pre>
	 * 	处理带有原交易的订单信息
	 * 	前提：
	 * 		成本订单必须带有原交易
	 * 	流程:
	 * 		1、验证客户端有没有上送原交易流水号
	 * 		2、根据原交易流水号查找原交易订单信息
	 * 			2.1 如果找不到则抛出异常
	 * 			2.2 如果找到了则继续下一步
	 * 		3、将原交易的信息设置到当前交易中
	 * 			需要设置的属性有：手续费、计费表达式、订单类型、约束编号、订单状态；
	 * 			创建时间和计费截止时间取当前时间。
	 * 		4、如果原交易没有成功，则抛出异常
	 * </pre>
	 * 
	 * @param order
	 *            客户端上送的成本订单信息
	 * @param date
	 *            当前的时间
	 * @throws CostBizException
	 */
	private void processOrigOrder(CalCostOrder order, Date date) throws CostBizException {
		logger.info("该订单需要验证原交易信息");
		CalCostOrder origOrder = this.getByTrxno(order.getFromSystem(), order.getOrgTrxNo());
		if (origOrder == null) {
			throw CostBizException.CAL_FEE_ERROR.newInstance("找不到原交易流水号[%s]", order.getOrgTrxNo());
		} else {
			logger.info(String.format("原交易: 成本计费项=[%s],原交易时间=[%s],原交易流水号=[%s],原交易金额=[%s],原交易手续费=[%s]", origOrder.getCostItem(),
					origOrder.getTrxTime(), origOrder.getTrxNo(), origOrder.getAmount(), origOrder.getFee()));
			order.setFee(this.getOrgOrderFee(order.getAmount(), origOrder));
			order.setCalExpression(origOrder.getCalExpression());
			order.setCalOrderType(origOrder.getCalOrderType());
			order.setFeeWayId(origOrder.getFeeWayId());
			order.setStatus(origOrder.getStatus());
			order.setCreateTime(date);
			order.setCalEndTime(date);
			if (order.getStatus().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				order.setFailedReason("原交易没有计费成功");
				throw CostBizException.CAL_FEE_ERROR.newInstance("原交易没有计费成功[%s]", origOrder.getFailedReason());
			} else {
				CalFeeWay calFeeWay = calFeeWayBiz.getById(origOrder.getFeeWayId());
				if (calFeeWay == null) {
					order.setFailedReason("找不到原交易的计费约束");
					throw CostBizException.CAL_FEE_ERROR.newInstance("找不到原交易的计费约束[%s]", origOrder.getFeeWayId());
				}
				int calType = calFeeWay.getCalType().intValue();
				if (calType == CalTypeEnum.LADDER_SINGLE.getValue() || calType == CalTypeEnum.LADDER_MULTIPLE.getValue()) {
					logger.info("原交易属于阶梯类型的订单，故需要在流量表中做相应调整");
					if (calFeeWay.getCycleType() == null) {
						throw CostBizException.CAL_CYCLE_DATE_ERROR.newInstance("计费周期类型未设置");
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date beginDate = calFeeFlowBiz.fetchCycleBeginDate(calFeeWay, origOrder.getCreateTime());
					Date endDate = calFeeFlowBiz.fetchCycleEndDate(calFeeWay, origOrder.getCreateTime());
					if (endDate.before(date)) {
						throw CostBizException.CAL_FEE_ERROR.newInstance("原交易的计费周期[%s => %s]已过期", sdf.format(beginDate),
								sdf.format(endDate));
					}
					logger.info(String.format("根据原交易的计费周期获取原交易的计费流量", sdf.format(beginDate), sdf.format(endDate)));
					CalFeeFlow calFeeFlow = calFeeFlowBiz.fetchCalFeeFlow(calFeeWay, beginDate, endDate);
					if (calFeeFlow == null) {
						throw CostBizException.CAL_FEE_ERROR.newInstance("找不到原交易的计费周期[%s => %s]", sdf.format(beginDate),
								sdf.format(endDate));
					}
					calFeeFlow.setThisAmount(order.getAmount());
					logger.info(String.format("计费流量更新前的总金额：%s", calFeeFlow.getTotalAmount()));

					// TODO 佳龙 成本订单增加->正向交易还是退款，类似商户计费
					calFeeFlow.setTotalAmount(calFeeFlow.getTotalAmount().add(order.getAmount()));

					calFeeFlow.setModifyTime(date);
					logger.info(String.format("计费流量更新后的总金额：%s", calFeeFlow.getTotalAmount()));
					order.setFeeFlow(calFeeFlow);
				}
			}
		}
	}

	/**
	 * <pre>
	 * 	根据订单信息实时计算成本信息
	 * 	计算流程：
	 * 		1、判断交易流水号是否重复
	 * 			1.1  如果重复，则直接抛出异常给客户端（该类型的交易不需要入库）
	 * 			1.2 如果不重复，则继续下一步
	 * 		2、验证客户端上送的信息是否完整
	 * 			2.1 如果不完整，则抛出异常，然后保存银行成本。
	 * 			2.2 如果完整，则继续下一步
	 * 		3、判断该交易是否存在原交易
	 * 			3.1 如果存在原交易，则获取原交易信息并进行处理，然后保存银行成本。
	 * 			3.2 如果不存在原交易，则继续下一步
	 * 		4、计算银行手续费，并且设置到当前订单信息中
	 * 			4.1 如果计算失败，则抛出异常（提示找不到计费规则），然后保存银行成本。
	 * 			4.2 如果计算成功,则继续下一步
	 * 		5、保存银行成本信息
	 * 		6、返回手续费给客户端
	 * </pre>
	 * 
	 * @param order
	 *            订单信息
	 * @return
	 * @throws CostBizException
	 */
	public CalCostOrder calulateBankCost(CalCostOrder order, CostItemEnum costItemEnum, SystemResourceTypeEnum systemResourceTypeEnum) {
		try {
			logger.info(String.format("接收到计费请求：\r\n\t计费接口=[%s],计费金额=[%s],计费项=[%s],原交易流水=[%s],系统来源=[%s]", order.getCalInterface(),
					order.getAmount(), costItemEnum, order.getOrgTrxNo(), systemResourceTypeEnum));
			if (CheckUtils.isEmpty(order.getCalInterface())) {
				throw CostBizException.CAL_INTERFACE_NOEXIST;
			}
			if (order.getAmount() == null || order.getAmount().compareTo(BigDecimal.valueOf(0)) < 1) {
				throw CostBizException.CAL_FEE_ERROR.newInstance("交易金额[%s]有误", order.getAmount());
			}
			if (costItemEnum == null) {
				throw CostBizException.CAL_FEE_ERROR.newInstance("计费项[%s]有误", order.getCostItem());
			} else {
				order.setCostItem(costItemEnum.getValue());
			}
			if (order.getCostItem() == null || order.getCostItem().intValue() <= 0) {
				throw CostBizException.CAL_FEE_ERROR.newInstance("找不到计费项[%s]", order.getCostItem());
			}
			if (systemResourceTypeEnum != null) {
				order.setFromSystem(Integer.toString(systemResourceTypeEnum.getValue()));
			}
			String mccTypeCode = null;
			if (order.getMcc() != null && !order.getMcc().trim().equals("")) {
				// 在线交易，直接把mcc赋给mccTypeCode
				mccTypeCode = order.getMcc();
			}
			Date date = new Date();
			/** 判断该订单是否属于包年订单 **/
			CalCostInterface calCostInterface = calCostInterfaceBiz.getByInterfaceCode(order.getCalInterface());
			if (calCostInterface != null && calCostInterface.getPolicy().intValue() == CostInterfacePolicyEnum.YEAR.getValue()) {
				/** 处理包年订单 **/
				order.setCalExpression("包年:0");
				order.setFee(BigDecimal.ZERO);
				order.setStatus(PublicStatusEnum.ACTIVE.getValue());
			} else {
				/** 判断该交易是否存在原交易 **/
				if (this.existOriginalInfo(order)) {
					/** 处理原交易订单 **/
					this.processOrigOrder(order, date);
				} else {
					/** 计算银行手续费，并且设置到当前订单信息中 **/
					this.calculateBankFee(order, date, mccTypeCode);
					order.setStatus(PublicStatusEnum.ACTIVE.getValue());
				}
			}
			return order;
		} catch (Exception e) {
			order.setStatus(PublicStatusEnum.INACTIVE.getValue());
			order.setFailedReason(e.getMessage());
			logger.error("银行成本预算失败", e);
			throw CostBizException.CAL_FEE_ERROR.newInstance("银行成本预算失败, %s", e.getMessage());
		} finally {
			logger.info("银行成本预算完成");
		}
	}

	/**
	 * <pre>
	 * 	计算银行手续费，并且设置到当前订单信息中
	 * 	计算流程：
	 * 		1、根据计费接口查找计费维度
	 * 			1.1 如果找不到计费维度，则抛出异常
	 * 			1.2 如果找到了计费维度，则继续下一步
	 * 		2、轮循查找到的计费维度，根据计费维度编号查询计费约束
	 * 			2.1 如果找不到计费约束，则继续循环
	 * 			2.2如果找到了计费约束，则继续下一步
	 * 		3、轮循查找到的计费约束，验证计费约束是否有效
	 * 			3.1 如果无效，则继续循环
	 * 			3.2 如果有效，则继续下一步
	 * 		4、根据计费约束查找计费公式
	 * 			4.1 如果计费公式适合当前的交易金额（或者交易总金额），则根据此金额计算手续费并返回结果
	 * 			4.2 如果计费公式不适合当前的交易金额（或者交易总金额），则继续循环
	 * 		5、如果一直找不到有效的计费公式，则抛出异常
	 * </pre>
	 * 
	 * @param order
	 *            客户端上送的订单信息
	 * @param date
	 *            当前的时间
	 * @return
	 */
	private void calculateBankFee(CalCostOrder order, Date date, String mccTypeCode) throws CostBizException {
		/** 根据计费接口查找计费维度 **/
		logger.info(String.format("根据计费接口[%s]查询计费维度", order.getCalInterface()));
		List<CalDimension> dims = calDimensionBiz.listByBankInterface(order.getCalInterface());
		int dimCount = (dims == null) ? 0 : dims.size();
		if (dimCount == 0) {
			throw CostBizException.CAL_FEE_ERROR.newInstance("找不到计费接口[%s]对应的计费维度", order.getCalInterface());
		} else {
			logger.info(String.format("计费维度查询成功,找到[%d]个计费维度", dimCount));
		}
		for (int i = 0; i < dimCount; i++) {
			CalDimension dim = dims.get(i);
			/** 根据计费维度查找计费约束 **/
			logger.info(String.format("根据计费维度[产品名称: %s; 银行接口: %s]查询计费约束[%d/%d]", dim.getCalProduct(), dim.getCalCostInterfaceCode(), i + 1,
					dimCount));
			List<CalFeeWay> constraints = calFeeWayBiz.listByDimensionId(dim.getId());
			int constraintCount = (constraints == null) ? 0 : constraints.size();
			logger.info(String.format("查询成功,查询到[%d]条计费约束", constraintCount));
			for (int j = 0; j < constraintCount; j++) {
				CalFeeWay constraint = constraints.get(j);
				/** 根据计费约束查找计费公式 **/
				logger.info(String.format("[%d/%d]验证计费约束[约束名称: %s]", j + 1, constraintCount, constraint.getWayName()));
				if (calFeeWayBiz.validate(constraint, mccTypeCode)) {
					logger.info(String.format("[%d/%d]计费约束[约束名称: %s]验证成功", j + 1, constraintCount, constraint.getWayName()));
				} else {
					logger.info(String.format("[%d/%d]计费约束[约束名称: %s]验证拒绝", j + 1, constraintCount, constraint.getWayName()));
					continue;
				}
				logger.info(String.format("[%d/%d]根据计费约束[约束名称: %s]查找计费公式", j + 1, constraintCount, constraint.getWayName()));
				AbstractBankCost bankCost = BankCostFactory.newInstance(order, calFeeFlowBiz, constraint, date);
				List<CalFeeRateFormula> formulas = bankCost.getFormula(calFeeRateFormulaBiz, constraint);
				if (bankCost.calculate(formulas)) {
					BigDecimal fee = BigDecimal.ZERO;
					
//					if (constraint.getIsRound() != null && constraint.getIsRound() == PublicStatusEnum.ACTIVE.getValue()) { // 鍒ゆ柇鏄惁瑕佽繘琛屽洓鑸嶄簲鍏�
//						fee = bankCost.getFee().setScale(2, BigDecimal.ROUND_HALF_UP);
//					} else {
//						fee = bankCost.getFee();
//					}
					
					if (constraint.getIsRound() == CalApproximationEnum.NONE.getValue()) { // NONE 不做任何操作
						fee = bankCost.getFee();
					} else if (constraint.getIsRound() == CalApproximationEnum.LAST_ROUND.getValue()) {// 舍尾法
						fee = bankCost.getFee().setScale(2, BigDecimal.ROUND_DOWN);
					} else if (constraint.getIsRound() == CalApproximationEnum.INTO_LAW.getValue()) {// 进一法
						fee = bankCost.getFee().setScale(2, BigDecimal.ROUND_UP);
					} else {// 四舍五入法
						fee = bankCost.getFee().setScale(2, BigDecimal.ROUND_HALF_UP);
					}
					
					order.setFee(fee);
					order.setFeeWayId(constraint.getId());
					bankCost.saveFlowInfo();
					logger.info(String.format("银行成本计算成功,手续费[%s]", order.getFee()));
					return;
				} else {
					logger.warn(String.format("计费约束[约束名称: %s]找不到有效的计费公式[%d/%d]", constraint.getWayName(), j + 1, constraintCount));
					continue;
				}
			}
		}
		throw CostBizException.CAL_FEE_ERROR.newInstance("找不到有效的计费规则");
	}
	

	/**
	 * 根据计费项和支付流水号查询成本订单
	 * 
	 * @param trxNo
	 * @param costItem
	 * @return
	 */
	public CalCostOrder getByPayTrxNoAndCostItem(String trxNo, Integer costItem) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxNo", trxNo);
		paramMap.put("costItem", costItem);
		return calCostOrderDao.getBy(paramMap);
	}

}
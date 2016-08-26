package wusc.edu.pay.core.fee.biz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import wusc.edu.pay.facade.fee.entity.FeeFlowAccumulator;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.entity.FeeOrder;
import wusc.edu.pay.facade.fee.entity.UserFeeSetting;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeChargeTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.fee.utils.FeeAmountUtils;
import wusc.edu.pay.facade.fee.utils.FeeFormulaCreateTimeComparator;
import wusc.edu.pay.facade.fee.utils.FeeUtils;
import wusc.edu.pay.facade.fee.utils.FormulaComparator;
import wusc.edu.pay.facade.fee.vo.CalculateFeeAmountVO;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


/**
 * 计费Biz接口定义
 * 
 */
@Component("calculateFeeBiz")
public class CalculateFeeBiz {
	private static final Log log = LogFactory.getLog(CalculateFeeBiz.class);

	/**
	 * 日期比较
	 */
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	@Autowired
	private FeeDimensionBiz feeDimensionBiz;
	@Autowired
	private UserFeeSettingBiz userFeeSettingBiz;
	@Autowired
	private FeeFlowAccumulatorBiz feeFlowAccumulatorBiz;
	@Autowired
	private FeeOrderBiz feeOrderBiz;

	/**
	 * 费率预算Biz方法： 计算收款方（payee）以及付款方（payer）的费率，分别保存于计费结果DTO的不同属性中
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param payProduct
	 *            支付产品编号
	 * @param frpCode
	 *            支付方式编号
	 * @param amount
	 *            待计费金额
	 * @param transferDate
	 *            交易发起时间
	 * @param rateBearerFilter
	 *            分摊比例 S:0$T:1 S:付款方 T:收款方 默认全部由收款方承担设置为 S:0$T:1
	 * @param merchantName
	 *            商户名称
	 * @param orderNo
	 *            商户订单号
	 * @param trxNo
	 *            交易流水号
	 * @return
	 */
	public FeeCalculateResultDTO caculateFee(String userNo, Integer userType, Integer calFeeItem, String payProduct, String frpCode, Double amount, Date transferDate, String merchantName, String orderNo, String trxNo) {
		if (calFeeItem == CalculateFeeItemEnum.POS_ACQUIRING.getValue()) {
			throw new FeeBizException(FeeBizException.MCC_IS_ERROR, "MCC码对应的MccFee不存在");
		}

		// 获取该费率调用方的费率节点
		List<UserFeeSetting> userSetList = userFeeSettingBiz.getUserFeeSettingList(userNo, userType, calFeeItem);
		log.info("用户编号为:" + userNo + "用户类型为：" + userType + "计费项" + CalculateFeeItemEnum.getEnum(calFeeItem).getDesc() + "与之对应的节点数：" + userSetList.size());
		if (userSetList == null || userSetList.isEmpty()) {
			throw new FeeBizException(FeeBizException.FEE_USERSETTING_NOT_EXIST, "费率用户设置不存在(请给商户挂计费节点)");
		}

		// 获取所有维度VO(节点-维度表关联)
		List<FeeDimensionVO> dimensions = feeDimensionBiz.findDimensionVos(transferDate, calFeeItem);

		if (dimensions == null || dimensions.isEmpty()) {
			throw new FeeBizException(FeeBizException.NO_SUITABLE_FORMULA_FOR_AMOUNT, "没有满足的公式");
		}

		// 获取适合的维度
		FeeDimensionVO dimension = feeDimensionBiz.getFeeDimensionVo(userSetList, dimensions, payProduct, frpCode, transferDate);

		// 获取相应费率调用方的费率规则
		List<FeeRuleDTO> feeRules = dimension.getFeeRuleList();

		// 得到 费率计算结果
		FeeCalculateResultDTO calculateResult = this.calculate(feeRules.get(0), amount, transferDate, userNo);

		// 创建计费订单
		FeeOrder feeOrder = feeOrderBiz.createFeeOrder(userNo, userType, dimension, feeRules.get(0), calculateResult, amount, transferDate, merchantName, orderNo, trxNo);

		// 把feeOrder放入FeeCalculateResultDTO中
		calculateResult.setFeeOrder(feeOrder);

		return calculateResult;

	}

	/**
	 * 计算最终费用
	 * 
	 * @param contextAcc
	 * @param dimension
	 * @param formulas
	 * @param amount
	 * @param transferDate
	 * @return
	 * @throws Exception
	 */
	private FeeCalculateResultDTO calculate(FeeRuleDTO feeRule, Double amount, Date transferDate, String userNo) {
		FeeCalculateResultDTO result = new FeeCalculateResultDTO(0.0, null, feeRule.getIsRound(), FeeRoleTypeEnum.PLATFORM.getValue());

		// 得到计费公式集合
		List<FeeFormula> formulas = feeRule.getFeeFormulaList();
		List<FeeFormula> tempFormula = new ArrayList<FeeFormula>();
		// 过滤无效的公式
		for (int i = 0; i < formulas.size(); i++) {
			if (formulas.get(i).getStatus() == PublicStatusEnum.ACTIVE.getValue()) {
				tempFormula.add(formulas.get(i));
			}
		}
		formulas = tempFormula;
		// 待计费金额小于等于免计费金额、或者等于零，则直接返回
		if (amount.compareTo(0D) == 1 && AmountUtil.greaterThanOrEqualTo(feeRule.getFreeFeeAmount(), amount)) {
			return result;
		}
		// 判断是实时还是事后计算，如果是后收，直接返回
		if (feeRule.getChargeType() == FeeChargeTypeEnum.UN_REAL_TIME.getValue()) {
			return result;
		}
		// 待计费金额(订单金额 减去 免计费金额)
		// Double temp = AmountUtil.sub(amount, feeRule.getFreeFeeAmount());
		// 流量阶梯计费
		if (FeeCalculateTypeEnum.isLadderAcc(feeRule.getCalculateType())) {

			// 查询流量累计
			FeeFlowAccumulator calFeeFlow = getCalFeeFlow(feeRule, transferDate, userNo, amount);

			result.setCalFeeFlow(calFeeFlow);
			// 得到流量统计数据
			Double addUpAmount = calFeeFlow.getTotalAmount();

			// B阶梯计费 -周期内处于不同区间的累加总量周期结束时使用最终所处区间费率、费额
			if (FeeCalculateTypeEnum.isLadderB(feeRule.getCalculateType())) {

				// 判断符合B阶梯类型的规则
				FeeFormula feeFormula = calculateFeeLadderB(amount, addUpAmount, formulas);

				Map<String, Object> map = new HashMap<String, Object>();
				Double payFee = 0D;
				map = calculateFee(feeFormula, amount);

				payFee = AmountUtil.add(payFee, (Double) map.get("payFee"));
				result.setPayFee(payFee, feeRule.getIsRound());
				// 计费基数设置
				StringBuffer base = new StringBuffer();
				// 装配FeeBase
				assemblyFeeBase(base, amount, feeFormula);
				result.setFeeBase(base.toString());

			} else {
				// A的流量阶梯计费
				result = calculateFeeFlowLadder(feeRule, amount, AmountUtil.sub(addUpAmount, amount), formulas, result);
			}
		}
		// 非阶梯计费
		else {
			// result = calculateFeeCommon(temp, formulas);
			result = calculateFeeCommon(feeRule, amount, formulas);
		}
		return result;
	}

	/**
	 * 根据规则得到流量累加数据
	 * 
	 * @param feeRule
	 * @return
	 */
	private FeeFlowAccumulator getCalFeeFlow(FeeRuleDTO feeRule, Date transferDate, String userNo, Double amount) {
		// 获取当前交易的累加项
		Map<String, Date> dates = FeeUtils.findAccTimeInterval(feeRule.getLadderCycleType(), feeRule.getCustomizeCycleType(), feeRule.getCustomizeDay(), transferDate);

		// 根据用户编号，方式id查找流量累加
		List<FeeFlowAccumulator> calFeeFlows = feeFlowAccumulatorBiz.getFlowAccumulator(feeRule.getId(), userNo);
		int calFeeFlowCount = (calFeeFlows == null) ? 0 : calFeeFlows.size();
		log.info(String.format("根据计费约束编号[%d]查找到[%d]条流量信息", feeRule.getId(), calFeeFlowCount));
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
			calFeeFlow.setThisAmount(amount);
			calFeeFlow.setTotalAmount(AmountUtil.add(calFeeFlow.getTotalAmount(), amount));
			// 返回流量累加中的总金额
			return calFeeFlow;
		}

		// 创建流量记录(如果是预算就)
		calFeeFlow = new FeeFlowAccumulator();
		calFeeFlow.setAccIntervalEnd(dates.get("end"));
		calFeeFlow.setAccIntervalStart(dates.get("start"));
		calFeeFlow.setCalculateWayId(feeRule.getId());
		calFeeFlow.setMerchantNo(userNo);
		calFeeFlow.setModifyDate(new Date());
		calFeeFlow.setThisAmount(amount);
		calFeeFlow.setTotalAmount(amount);
		return calFeeFlow;
	}

	/**
	 * 判断B阶梯这种类型符合的规则
	 * 
	 * @param amount
	 * @param addUpAmount
	 * @param formulas
	 */
	private FeeFormula calculateFeeLadderB(Double amount, Double addUpAmount, List<FeeFormula> formulas) {
		Double curTotal = addUpAmount;// 得到总的统计值
		FeeFormula feeFormula = findFeeFormulaVO(formulas, curTotal);
		// 计费金额无计费区间满足
		if (null == feeFormula) {
			throw new FeeBizException(FeeBizException.NO_SUITABLE_FORMULA_FOR_AMOUNT, "待计费金额无满足计费公式！");
		}
		return feeFormula;
	}

	/**
	 * 计算阶梯流量计费
	 * 
	 * @param feeRule
	 * 
	 * @param amount
	 * @param findCurTotal
	 * @param formulas
	 * @param resultDTO
	 * @return
	 * @throws Exception
	 */
	private FeeCalculateResultDTO calculateFeeFlowLadder(FeeRuleDTO feeRule, Double amount, Double accAmount, List<FeeFormula> formulas, FeeCalculateResultDTO resultDTO) {
		Double curTotal = AmountUtil.add(amount, accAmount);
		// 计费金额无计费区间满足
		if (null == findFeeFormulaVO(formulas, curTotal)) {
			throw new FeeBizException(FeeBizException.NO_SUITABLE_FORMULA_FOR_AMOUNT, "待计费金额无满足计费公式！");
		}

		// 流量累加待计费金额拆分算法
		// 得到每个阶段的金额和相对应的维度
		List<CalculateFeeAmountVO> vos = splitFeeAmount(formulas, accAmount, amount);

		Double payFeeTotal = 0D;

		StringBuffer base = new StringBuffer();
		for (CalculateFeeAmountVO vo : vos) {
			Map<String, Object> result = new HashMap<String, Object>();
			// 费率计算
			result = calculateFee(vo.getFormula(), vo.getAmount());
			payFeeTotal = AmountUtil.add(payFeeTotal, (Double) result.get("payFee"));
			assemblyFeeBase(base, vo.getAmount(), vo.getFormula());
		}
		resultDTO.setPayFee(payFeeTotal, feeRule.getIsRound());
		resultDTO.setFeeBase(base.toString());
		return resultDTO;
	}

	/**
	 * 非阶梯计费(单笔或者单笔区间)
	 * 
	 * @param feeRule
	 * 
	 * @param temp
	 *            金额（amount - 免计费金额）
	 * @param formulas
	 * @return
	 */
	private FeeCalculateResultDTO calculateFeeCommon(FeeRuleDTO feeRule, Double temp, List<FeeFormula> formulas) {
		// 根据金额所在区域范围，寻找合适公式
		formulas = filterFeeFormulaVOsCommon(formulas, temp);
		// 计费金额符合多个计费区间或者无计费区间满足
		if (formulas != null && formulas.isEmpty()) {
			throw new FeeBizException(FeeBizException.NO_SUITABLE_FORMULA_FOR_AMOUNT, String.format("待计费金额{0}，无满足的计费公式！", temp));
		}
		Map<String, Object> result = new HashMap<String, Object>();
		Double payFee = 0D;
		StringBuffer base = new StringBuffer();

		FeeFormula formula = formulas.get(0);
		result = calculateFee(formula, temp);
		payFee = AmountUtil.add(payFee, (Double) result.get("payFee"));

		assemblyFeeBase(base, temp, formula);
		return new FeeCalculateResultDTO(payFee.doubleValue(), base.toString(), feeRule.getIsRound(), feeRule.getFeeRole());
	}

	/**
	 * 根据金额所在区域范围，寻找合适公式
	 * 
	 * @param formulas
	 * @param amount
	 */
	private List<FeeFormula> filterFeeFormulaVOsCommon(List<FeeFormula> formulas, Double amount) {
		List<FeeFormula> formulaList = new ArrayList<FeeFormula>();
		// 根据金额所在区域范围，寻找合适公式
		Iterator<FeeFormula> iter = formulas.iterator();
		while (iter.hasNext()) {
			FeeFormula formula = iter.next();
			if (AmountUtil.bigger(amount, formula.getMinAmount()) && formula.getMaxAmount() == null) {
				formulaList.add(formula);
			} else if (formula.getMaxAmount() != null && FeeAmountUtils.isBetween(Double.valueOf(formula.getMinAmount()), Double.valueOf(formula.getMaxAmount()), amount)) {
				formulaList.add(formula);
			}

		}

		// 根据创建时间排序
		FeeFormulaCreateTimeComparator calWayComp = new FeeFormulaCreateTimeComparator();
		Collections.sort(formulaList, calWayComp);
		return formulaList;
	}

	/**
	 * 装配feeBase
	 * 
	 * @param base
	 * @param amount
	 *            金额
	 * @param feeFormula
	 *            公式
	 */
	private void assemblyFeeBase(StringBuffer base, Double amount, FeeFormula feeFormula) {
		// 如果公式实体中的--固定金额费率基数--不为空且大于等于0
		if (null != feeFormula.getFixedFee() && Double.valueOf(feeFormula.getFixedFee()).compareTo(0D) != -1) {
			base.append(" " + amount + ":").append(BigDecimal.valueOf(feeFormula.getFixedFee()).toPlainString());
		}
		// 如果不是固定金额费率则使用百分比费率基数
		else {
			base.append(" " + amount + "*").append(BigDecimal.valueOf(feeFormula.getPercentFee()).toPlainString());
			// 如果是百分比，判断单笔的上下限
			if (null != feeFormula.getSingleMaxFee()) {
				base.append("(").append(BigDecimal.valueOf(feeFormula.getSingleMinFee())).append(", ").append(BigDecimal.valueOf(feeFormula.getSingleMaxFee()).toPlainString()).append(")");
			}
		}

	}

	/**
	 * 计算手续费
	 * 
	 * @param feeFormula
	 * @param amount
	 * @return
	 */
	private Map<String, Object> calculateFee(FeeFormula feeFormula, Double amount) {
		Map<String, Object> result = new HashMap<String, Object>();
		Double payFee = 0D;
		if (null != feeFormula.getFixedFee() && feeFormula.getFixedFee().compareTo(0d) != -1) {
			// 固定值
			payFee = Double.valueOf(feeFormula.getFixedFee());
		} else if (null != feeFormula.getPercentFee() && feeFormula.getPercentFee().compareTo(0d) != -1) {
			// 百分比
			payFee = AmountUtil.mul(amount, feeFormula.getPercentFee());
		}
		// 判断是否符合最小（大手续费）
		payFee = checkFee(payFee, feeFormula);
		result.put("payFee", payFee);

		return result;
	}

	/**
	 * 判断计算出来的手续费，是否达到设置的最小（大）手续费
	 * 
	 * @param amount
	 * @param feeFormula
	 * @return
	 */
	private Double checkFee(Double amount, FeeFormula feeFormula) {
		if (null != feeFormula.getSingleMinFee() && FeeAmountUtils.isThan(amount, Double.valueOf(feeFormula.getSingleMinFee()))) {
			return amount = Double.valueOf(feeFormula.getSingleMinFee());
		} else if (null != feeFormula.getSingleMaxFee() && FeeAmountUtils.isThan(Double.valueOf(feeFormula.getSingleMaxFee()), amount)) {
			return amount = Double.valueOf(feeFormula.getSingleMaxFee());
		}
		return amount;
	}

	/**
	 * 判断是否跨区间
	 * 
	 * @param formulas
	 * @param target
	 * @return
	 */
	public static FeeFormula findFeeFormulaVO(List<FeeFormula> formulas, Double target) {
		List<FeeFormula> listFormula = new ArrayList<FeeFormula>();
		for (FeeFormula formula : formulas) {
			if (target.equals(0d)) {
				if (formula.getMinLadderAmount().compareTo(target) == 0) {
					return formula;
				}
			} else {
				if (FeeAmountUtils.isBetween(formula.getMinLadderAmount(), formula.getMaxLadderAmount(), target) || (AmountUtil.bigger(target, formula.getMinLadderAmount()) && formula.getMaxLadderAmount() == null)) {
					listFormula.add(formula);
				}
			}
		}
		// 根据创建时间排序
		FeeFormulaCreateTimeComparator calWayComp = new FeeFormulaCreateTimeComparator();
		Collections.sort(listFormula, calWayComp);
		return listFormula.get(0);
	}

	public static List<CalculateFeeAmountVO> splitFeeAmount(List<FeeFormula> formulas, Double total, Double target) {
		if (null == formulas || formulas.isEmpty()) {
			throw new IllegalArgumentException("IllegalArgumentException, empty formulas.");
		}
		List<CalculateFeeAmountVO> amounts = new ArrayList<CalculateFeeAmountVO>();

		// 未跨区间
		if (findFeeFormulaVO(formulas, total).equals(findFeeFormulaVO(formulas, AmountUtil.add(total, target)))) {
			amounts.add(new CalculateFeeAmountVO(target, findFeeFormulaVO(formulas, AmountUtil.add(total, target))));
			return amounts;
		}
		// 跨区间
		else {
			// 根据阶梯金额下限大小排序
			Collections.sort(formulas, new FormulaComparator());
			List<FeeFormula> results = new ArrayList<FeeFormula>();
			Double curTotal = AmountUtil.add(target, total);

			int size = 1, le = 0, ri = 0;
			for (FeeFormula formula : formulas) {
				Double left = formula.getMinLadderAmount();
				Double right = formula.getMaxLadderAmount();
				// 没有加上target所在区域
				if (FeeAmountUtils.isBetween(left, right, total)) {
					le = size;
				}
				// 加上Target所在的区域。（和未加所在区域相隔有可能>=1）
				else if (FeeAmountUtils.isBetween(left, right, curTotal)) {
					ri = size;
					break;
				}
				size++;
			}

			results.addAll(formulas.subList(le - 1, ri));
			int flag = results.size();
			while (!results.isEmpty()) {
				for (Iterator<FeeFormula> iter = results.iterator(); iter.hasNext();) {
					FeeFormula formula = iter.next();
					if (flag == 1) {
						if (curTotal.compareTo(total) == 1) {
							amounts.add(new CalculateFeeAmountVO(AmountUtil.sub(curTotal, total), formula));
						}
						iter.remove();
					} else if (FeeAmountUtils.isBetween(formula.getMinLadderAmount(), formula.getMaxLadderAmount(), curTotal)) {
						amounts.add(new CalculateFeeAmountVO(AmountUtil.sub(curTotal, formula.getMinLadderAmount()), formula));
						curTotal = formula.getMinLadderAmount();
						iter.remove();
						flag--;
					}
				}
			}
		}
		return amounts;
	}
}

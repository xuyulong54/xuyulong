package wusc.edu.pay.core.fee.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.json.JSONUtils;
import wusc.edu.pay.core.fee.dao.FeeCalculateWayDao;
import wusc.edu.pay.core.fee.dao.FeeFormulaDao;
import wusc.edu.pay.core.fee.dao.FeePrepaidFlowInfoDao;
import wusc.edu.pay.core.fee.dao.FeeRuleHistoryDao;
import wusc.edu.pay.facade.fee.dto.FeeModelDTO;
import wusc.edu.pay.facade.fee.dto.FeePrepaidFlowInfoDTO;
import wusc.edu.pay.facade.fee.dto.FeeRuleDTO;
import wusc.edu.pay.facade.fee.entity.FeeCalculateWay;
import wusc.edu.pay.facade.fee.entity.FeeFormula;
import wusc.edu.pay.facade.fee.entity.FeePrepaidFlowInfo;
import wusc.edu.pay.facade.fee.entity.FeeRuleHistory;
import wusc.edu.pay.facade.fee.enums.FeeCalculateTypeEnum;
import wusc.edu.pay.facade.fee.enums.FeeChargeTypeEnum;
import wusc.edu.pay.facade.fee.enums.LadderCycleTypeEnum;
import wusc.edu.pay.facade.fee.utils.FeeCalculateWayComparator;
import wusc.edu.pay.facade.fee.utils.FeeFormulaComparator;
import wusc.edu.pay.facade.fee.vo.FeeDimensionVO;


/**
 * 计费公式biz定义 含费率计费方式、计算公式、阶梯周期信息的增删改查
 */
@Component("feeFormulaBiz")
public class FeeFormulaBiz {

	private static final Log log = LogFactory.getLog(FeeFormulaBiz.class);
	@Autowired
	private FeeFormulaDao feeFormulaDao;
	@Autowired
	private FeeRuleHistoryDao feeRuleHistoryDao;
	@Autowired
	private FeeCalculateWayDao feeCalculateWayDao;
	@Autowired
	private FeePrepaidFlowInfoDao feePrepaidFlowInfoDao;

	/**
	 * 查询费率模型DTO
	 * 
	 * @param userNo
	 *            费率调用者编号
	 * @param userType
	 *            用户类型
	 * @param calculateFeeItem
	 *            计费项 CalculateFeeItemEnum
	 * @param payProduct
	 *            计费产品编号
	 * @param frpCode
	 *            支付方式编号
	 * @param transferDate
	 *            交易发起时间
	 * @return
	 */
	private FeeModelDTO findFeeModelDTO(String userNo, Integer userType, Integer calculateFeeItem, String payProduct, String frpCode, Date transferDate) {
		// 根据开始时间等参数查询费率规则历史记录
		List<FeeRuleHistory> historys = feeRuleHistoryDao.queryFeeRuleHistory(userNo, userType, calculateFeeItem, payProduct, frpCode, transferDate);

		FeeRuleHistory result = null;
		// 如果费率规则历史查询结果不为空且数量等于1，那么取这个历史规则
		if (historys !=null && !historys.isEmpty() && historys.size() == 1) {
			result = historys.get(0);
		}
		// 如果历史计费规则存在，那么取出计费规则转为FeeModelDTO并返回
		if (result != null) {
			return JSONUtils.jsonToBean(result.getFeeRule(), FeeModelDTO.class);
		}
		return null;
	}

	/**
	 * 根据维度去查找计费公式
	 * 
	 * @param dimension
	 * @return
	 */
	public List<FeeRuleDTO> getFormulaInfo(FeeDimensionVO dimension, Date transferDate) {
		// 定义返回集合
		List<FeeRuleDTO> rules = new ArrayList<FeeRuleDTO>();

		// 费率计费方式信息查询
		List<FeeCalculateWay> calWays = feeCalculateWayDao.queryCalculateWay(dimension.getDimensionId(), transferDate);
		
		if(calWays == null  || calWays.isEmpty()){
			log.info(String.format("维度id：{1}的维度,没有符合条件的方式", dimension.getDimensionId()));
			return  rules;
		}

		// 当前生效费率默认放置于List中的第一项( EffectiveDateStart最大)
		sortFeeCalculateWayList(calWays);

		for (FeeCalculateWay way : calWays) {

			// 验证计费方式的费率阶梯信息(如果是阶梯，一定要有阶梯周期信息)
			if (FeeCalculateTypeEnum.isLadderAcc(way.getCalculateType())) {
				if (way.getLadderCycleType() == null) {
					log.error("计费方式ID：" + way.getId() + ",计费类型为阶梯，但是没有设置阶梯周期信息。");
					continue;
				} else {
					// 如果是自定义周期，自定义周期类型和自定义周期日必须不能为空
					if (way.getLadderCycleType() == LadderCycleTypeEnum.CUSTOMIZE.getValue()) {
						if (way.getCustomizeCycleType() == null || !(way.getCustomizeCycleType() == LadderCycleTypeEnum.WEEK.getValue() || way.getCustomizeCycleType() == LadderCycleTypeEnum.MONTH.getValue())) {
							log.error("计费方式ID：" + way.getId() + ",计费类型为阶梯，阶梯周期为自定义,但是自定义周期不符合条件。");
							continue;
						}
					}
				}
			}
			// 判断收费方式是否合法
			if (way.getChargeType() == null || !(way.getChargeType() == FeeChargeTypeEnum.REAL_TIME.getValue() || way.getChargeType() == FeeChargeTypeEnum.UN_REAL_TIME.getValue() || way.getChargeType() == FeeChargeTypeEnum.PREPAID.getValue())) {
				log.error("计费方式ID：" + way.getId() + ",计费类型为阶梯，收费方式不符合条件。");
				continue;
			}

			// 根据计费方式去找计费公式
			List<FeeFormula> formulaList = queryFeeFormulas(way);

			// 如果该方式下面没有公式，此规则不记录
			if (formulaList==null || formulaList.isEmpty()) {
				continue;
			}

			FeeRuleDTO rule = new FeeRuleDTO();
			// 给费率规则填值
			assemblyFeeRuleDTO(way, rule);

			rule.setFeeFormulaList(formulaList);
			rules.add(rule);
		}
		return rules;
	}

	/**
	 * 费率支付方式排序，list中第一项默认放置当前生效费率 EffectiveDateStart最大
	 * 
	 * @param list
	 */
	private void sortFeeCalculateWayList(List<FeeCalculateWay> list) {
		FeeCalculateWayComparator calWayComp = new FeeCalculateWayComparator();
		Collections.sort(list, calWayComp);
	}

	/**
	 * 组装费率规则DTO
	 * 
	 * @param calWay
	 *            计费方式实体
	 * @param formulaDTOList
	 *            费率公式DTO List
	 * @return
	 */
	private void assemblyFeeRuleDTO(FeeCalculateWay calWay, FeeRuleDTO rule) {
		rule.setCalculateType(calWay.getCalculateType());
		rule.setChargeType(calWay.getChargeType());
		rule.setEffectiveEnd(calWay.getEffectiveDateEnd());
		rule.setEffectiveStart(calWay.getEffectiveDateStart());
		rule.setFreeFeeAmount(calWay.getFeeFreeAmount());
		rule.setCalculateType(calWay.getCalculateType());
		rule.setId(calWay.getId());
		rule.setFeeRole(calWay.getFeeRole());
		rule.setBillCycleType(calWay.getBillCycleType());
		rule.setCustomizeBillCycleType(calWay.getCustomizeBillCycleType());
		rule.setCustomizeBillDay(calWay.getCustomizeBillDay());
		rule.setCreateTime(calWay.getCreateTime());
		rule.setRefundFee(calWay.getIsRefundFee());
		rule.setLadderCycleType(calWay.getLadderCycleType());
		rule.setCustomizeCycleType(calWay.getCustomizeCycleType());
		rule.setCustomizeDay(calWay.getCustomizeDay());
		rule.setIsRound(calWay.getIsRound());
	}

	/**
	 * 查询费率公式
	 * 
	 * @param calWay
	 * @return
	 */
	public List<FeeFormula> queryFeeFormulas(FeeCalculateWay calWay) {
		// 获取费率公式
		List<FeeFormula> formulas = feeFormulaDao.queryFeeFormulaByCalculateWayId(calWay.getId());
		if (formulas !=null && formulas.isEmpty()) {
			return null;
		}
		// 费率公式按照金额区间升序排列
		sortFeeFormulaList(formulas);
		return formulas;
	}

	/**
	 * 费率公式排序， list中每项按照区间升序排列
	 * 
	 * @param list
	 */
	private void sortFeeFormulaList(List<FeeFormula> list) {
		FeeFormulaComparator formulaComp = new FeeFormulaComparator();
		Collections.sort(list, formulaComp);
	}

	/**
	 * 查询费率阶梯信息
	 * 
	 * @param wayId
	 * @param rule
	 */
	/*
	 * private void getFeeLadderInfo(Long wayId, FeeRuleDTO rule) {
	 * //FeeLadderInfo ladder = feeLadderInfoDao.getFeeLadderInfoByWayId(wayId);
	 * rule.setLadderCycleType(ladder.getLadderCycleType());
	 * rule.setName(ladder.getLadderName());
	 * rule.setCustomizeCycleType(ladder.getCustomizeCycleType());
	 * rule.setCustomizeDay(ladder.getCustomizeDay()); }
	 */

	/**
	 * 得到费率包量包笔信息
	 * 
	 * @param wayId
	 * @param rule
	 */
	private void getPrepaidFlowInfo(Long wayId, FeeRuleDTO rule) {
		FeePrepaidFlowInfo info = feePrepaidFlowInfoDao.getFlowInfoByWayId(wayId);
		if (info != null) {
			FeePrepaidFlowInfoDTO dto = new FeePrepaidFlowInfoDTO();

			dto.setCustomizeFlowCycleType(info.getCustomizeCycleType());
			dto.setCustomizeFlowDay(info.getCustomizeDay());
			dto.setFlowAmount(info.getAmount());
			dto.setFlowCycleType(info.getCycleType());
			dto.setFlowQuantity(info.getQuantity());
			dto.setFlowType(info.getFlowType());
			dto.setPrepaidAmount(info.getPrepaidAmount());

			// rule.setFeePrepaidFlowInfoDto(dto);
		}
	}

	/**
	 * 根据维度ID查询计费方式
	 * 
	 * @param id
	 * @param dayStart
	 * @return
	 */
	public List<FeeCalculateWay> queryFeeCalculateWaysByDimensionId(Long id, Date dayStart) {
		return feeCalculateWayDao.queryCalculateWay(id, dayStart);
	}

	/**
	 * 根据条件查询所有计费公式
	 * 
	 * @param pageParam
	 * @param map
	 * @return
	 */
	public PageBean listPage(PageParam pageParam, Map<String, Object> map) {
		return feeFormulaDao.listPage(pageParam, map);
	}

	/**
	 * 新增
	 * 
	 * @param feeFormula
	 */
	public void createFeeFormulae(FeeFormula feeFormula) {
			feeFormulaDao.insert(feeFormula);
	}

	/**
	 * 根据ID查找计费公式
	 * 
	 * @param id
	 * @return
	 */
	public FeeFormula getById(Long id) {
		return feeFormulaDao.getById(id);
	}

	/**
	 * 更新
	 * 
	 * @param feeFormula
	 */
	public void updateFeeFormulae(FeeFormula feeFormula) {
		feeFormulaDao.update(feeFormula);
	}

	/**
	 * 根据计费方式列出所有计费公式
	 * 
	 * @param calWayId
	 * @return
	 */
	public List<FeeFormula> listByCalWayId(Long calWayId) {
		return feeFormulaDao.listByCalWayId(calWayId);
	}

	/**
	 * 检查计费公式是否唯一
	 * @param feeFormula
	 * @return
	 */
	public boolean checkUnique(FeeFormula feeFormula) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("calculateWayId", feeFormula.getCalculateWayId());
		paramMap.put("fixedFee", feeFormula.getFixedFee());
		paramMap.put("formulaType", feeFormula.getFormulaType());
		paramMap.put("maxAmount", feeFormula.getMaxAmount());
		paramMap.put("maxLadderAmount", feeFormula.getMaxLadderAmount());
		paramMap.put("minAmount", feeFormula.getMinAmount());
		paramMap.put("minLadderAmount", feeFormula.getMinLadderAmount());
		paramMap.put("percentFee", feeFormula.getPercentFee());
		paramMap.put("singleMaxFee", feeFormula.getSingleMaxFee());
		paramMap.put("singleMinFee", feeFormula.getSingleMinFee());
		return feeFormulaDao.checkUnique(paramMap);
	}
}

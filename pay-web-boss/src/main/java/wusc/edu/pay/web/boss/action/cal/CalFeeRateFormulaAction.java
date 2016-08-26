package wusc.edu.pay.web.boss.action.cal;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.cost.entity.CalFeeRateFormula;
import wusc.edu.pay.facade.cost.entity.CalFeeWay;
import wusc.edu.pay.facade.cost.enums.CalModelEnum;
import wusc.edu.pay.facade.cost.enums.CalTypeEnum;
import wusc.edu.pay.facade.cost.service.CalFeeRateFormulaFacade;
import wusc.edu.pay.facade.cost.service.CalFeeWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ActionContext;

/**
 * 计费公式
 */
public class CalFeeRateFormulaAction extends BossBaseAction {

	private static final long serialVersionUID = 7533657271450032698L;
	private static final Log log = LogFactory.getLog(CalFeeRateFormulaAction.class);
	@Autowired
	private CalFeeRateFormulaFacade calFeeRateFormulaFacade ;
	@Autowired
	private CalFeeWayFacade calFeeWayFacade;
	/**
	 * 计费公式列表查询
	 * @return
	 */
	@Permission("boss:calFeeRateFormula:view")
	public String calFeeRateFormulaList(){
		try{
			Map<String, Object> calFeeRateFormulaMap = new HashMap<String, Object>();
			calFeeRateFormulaMap.put("feeWayId", getString("feeWayId"));
			List<CalFeeWay> calFeeWayList = calFeeWayFacade.listAll();
			this.putData("calType", getString("calType"));
			super.pageBean =calFeeRateFormulaFacade.listPage(this.getPageParam(), calFeeRateFormulaMap);
			this.pushData(pageBean);
			this.putData("calModelEnum", CalModelEnum.toList());
			this.putData("calTypeEnum", CalTypeEnum.toList());
			this.putData("calTypeMap", CalTypeEnum.toMap());
			this.putData("calFeeWayList", calFeeWayList);
			this.pushData(calFeeRateFormulaMap);
		}catch(Exception e){
			log.error("==calFeeRateFormulaList Exception",e);
		}
		return "calFeeRateFormulaList";
	}

	/**
	 * 跳转至计费公式添加页面
	 * @return
	 */
	@Permission("boss:calFeeRateFormula:add")
	public String addCalFeeRateFormulaUI(){
		ActionContext.getContext().put("calModelList", CalModelEnum.toList());
		this.putData("feeWayId", getString("feeWayId"));
		this.putData("calType", getString("calType"));
		return "calFeeRateFormulaAdd";
	}
	
	@Permission("boss:calFeeRateFormula:add")
	public String addCalFeeRateFormula(){
		Map<String , Object> map = validateData(getLong("feeWayId"), getDouble("maxAmount"), getDouble("maxLadderAmount"), getDouble("minAmount"),
				getDouble("minLadderAmount"), getInteger("model"), getDouble("percentFee"), getDouble("singleMaxFee"), getDouble("singleMinFee"), getDouble("fixedFee"), getInteger("calType"));
		String errMsg = String.valueOf(map.get("errMsg"));
		if(!StringUtils.isBlank(errMsg)){
			return operateError(errMsg);
		}else{
			Object obj = map.get("calFeeRateFormula");
			if(obj != null ){
				CalFeeRateFormula calFeeRateFormula = (CalFeeRateFormula)obj;
				calFeeRateFormula.setFeeWayId(getLong("feeWayId"));
				calFeeRateFormula.setModel(getInteger("model"));
				calFeeRateFormulaFacade.create(calFeeRateFormula);
				this.logSave("添加计费公式，计费方式ID："+getLong("feeWayId"));
				return operateSuccess();
			}else{
				this.logSaveError("添加计费公式，计费方式ID："+getLong("feeWayId"));
				return operateError("创建计费公式失败");
			}
		}
	}
	
	/**
	 * 验证计费公式参数  （还需要再优化，节俭代码）
	 * @param feeWayId 约束id
	 * @param maxAmount 单笔区间上限
	 * @param maxLadderAmount 阶梯上限
	 * @param minAmount 单笔区间下限
	 * @param minLadderAmount 阶梯下限
	 * @param model 计费模式
	 * @param percentFee 比例手续费
	 * @param singleMaxFee 单笔最高手续费
	 * @param singleMinFee 单笔最低手续费
	 * @param fixedFee 固定手续费
	 * @param calType 计费类型
	 * @return
	 */
	private  Map<String , Object> validateData(long feeWayId ,Double maxAmount,Double maxLadderAmount,Double minAmount,Double minLadderAmount,Integer model,Double percentFee,Double singleMaxFee,Double singleMinFee,Double fixedFee, Integer calType){
		CalFeeRateFormula calFeeRateFormula = null;
		String errMsg = "";
		Map<String , Object> returnMap = new HashMap<String , Object>();
		if(calType == null){ 
			errMsg += "计费类型有误,<br/>";
		}else if(model == null){
			errMsg += "计费模式有误,<br/>";
		}else if(model == 1){
			/*按比例的 
			 *  单笔： 需验证  比例手续费 单笔最低手续费 单笔最高手续费
			 *  区间： 需验证  比例手续费  单笔最低手续费 单笔最高手续费                      单笔区间下限 单笔区间上限
			 *  阶梯： 需验证  比例手续费  单笔最低手续费 单笔最高手续费                       单笔阶梯下限 单笔阶梯上限
			 * */
			if(isErrData(percentFee)){
				errMsg += "比例手续费有误,<br/>";
			}else{
				percentFee = percentFee == 0 ? 0 : percentFee/100;
				if(percentFee > 1){
					errMsg += "比例手续费不能大于100%,<br/>";
				}else{
					if(isErrData(singleMinFee)){
						errMsg += "单笔最低手续费有误,<br/>";
					}else{
						 if(isErrData(singleMaxFee)){
								errMsg += "单笔最高手续费有误,<br/>";
							}else{
								if(AmountUtil.greaterThanOrEqualTo(singleMinFee,singleMaxFee)){
									errMsg += "单笔最高手续费不能小于或等于单笔最低手续费,<br/>";
								}else{
									if(calType == CalTypeEnum.SIMPLE.getValue()){//单笔
										calFeeRateFormula = new CalFeeRateFormula();
										calFeeRateFormula.setPercentFee(BigDecimal.valueOf(percentFee));
										calFeeRateFormula.setSingleMinFee(BigDecimal.valueOf(singleMinFee));
										calFeeRateFormula.setSingleMaxFee(BigDecimal.valueOf(singleMaxFee));
								}else if(calType == CalTypeEnum.INTERVAL.getValue()){//区间
									if(isErrData(minAmount)){
										errMsg += "单笔区间下限有误,<br/>";
									}else if(isErrData(maxAmount)){
										errMsg += "单笔区间上限有误,<br/>";
									}else{
										if(AmountUtil.greaterThanOrEqualTo(minAmount,maxAmount)){
											errMsg += "单笔区间上限不能小于单笔区间下限,<br/>";
										}else{
											calFeeRateFormula = new CalFeeRateFormula();
											calFeeRateFormula.setPercentFee(BigDecimal.valueOf(percentFee));
											calFeeRateFormula.setSingleMinFee(BigDecimal.valueOf(singleMinFee));
											calFeeRateFormula.setSingleMaxFee(BigDecimal.valueOf(singleMaxFee));
											calFeeRateFormula.setMaxAmount(BigDecimal.valueOf(maxAmount));
											calFeeRateFormula.setMinAmount(BigDecimal.valueOf(minAmount));
										}
									}
								}else if(calType == CalTypeEnum.LADDER_SINGLE.getValue()||calType == CalTypeEnum.LADDER_MULTIPLE.getValue()){//阶梯
									if(isErrData(minLadderAmount)){
										errMsg += "单笔阶梯下限有误,<br/>";
									}else if(isErrData(maxLadderAmount)){
										errMsg += "单笔阶梯上限有误,<br/>";
									}else{
										if(AmountUtil.greaterThanOrEqualTo(minLadderAmount, maxLadderAmount)){
											errMsg += "单笔阶梯上限不能小于单笔阶梯下限,<br/>";
										}else{
											calFeeRateFormula = new CalFeeRateFormula();
											calFeeRateFormula.setPercentFee(BigDecimal.valueOf(percentFee));
											calFeeRateFormula.setSingleMinFee(BigDecimal.valueOf(singleMinFee));
											calFeeRateFormula.setSingleMaxFee(BigDecimal.valueOf(singleMaxFee));
											calFeeRateFormula.setMaxLadderAmount(BigDecimal.valueOf(maxLadderAmount));
											calFeeRateFormula.setMinLadderAmount(BigDecimal.valueOf(minLadderAmount));
										}
									}
								}
							}
						}
					}
				}
			}
		}else if(model == 2){
			/*
			 * 按固定的
			 *     单笔： 固定手续费
			 *     区间： 固定手续费 单笔区间下限 单笔区间上限
			 *     阶梯：固定手续费 单笔阶梯下限 单笔阶梯上限
			 * */
			if(isErrData(fixedFee)){
				errMsg += "固定手续费有误,<br/>";
			}else{
				if(calType == CalTypeEnum.SIMPLE.getValue()){//单笔
					calFeeRateFormula = new CalFeeRateFormula();
					calFeeRateFormula.setFixedFee(BigDecimal.valueOf(fixedFee));
				}else if(calType == CalTypeEnum.INTERVAL.getValue()){
					if(isErrData(minAmount)){//区间
						errMsg += "单笔区间下限有误,<br/>";
					}else if(isErrData(maxAmount)){
						errMsg += "单笔区间上限有误,<br/>";
					}else{
						if(AmountUtil.greaterThanOrEqualTo(minAmount, maxAmount)){
							errMsg += "单笔区间上限不能小于单笔区间下限,<br/>";
						}else{
							calFeeRateFormula = new CalFeeRateFormula();
							calFeeRateFormula.setFixedFee(BigDecimal.valueOf(fixedFee));
							calFeeRateFormula.setMaxAmount(BigDecimal.valueOf(maxAmount));
							calFeeRateFormula.setMinAmount(BigDecimal.valueOf(minAmount));
						}
					}
				}else if(calType == CalTypeEnum.LADDER_SINGLE.getValue()||calType == CalTypeEnum.LADDER_MULTIPLE.getValue()){//阶梯
					if(isErrData(minLadderAmount)){
						errMsg += "单笔阶梯下限有误,<br/>";
					}else if(isErrData(maxLadderAmount)){
						errMsg += "单笔阶梯上限有误,<br/>";
					}else{
						if(AmountUtil.greaterThanOrEqualTo(minLadderAmount,maxLadderAmount)){
							errMsg += "单笔阶梯上限不能小于单笔阶梯下限,<br/>";
						}else{
							calFeeRateFormula = new CalFeeRateFormula();
							calFeeRateFormula.setFixedFee(BigDecimal.valueOf(fixedFee));
							calFeeRateFormula.setMaxLadderAmount(BigDecimal.valueOf(maxLadderAmount));
							calFeeRateFormula.setMinLadderAmount(BigDecimal.valueOf(minLadderAmount));
						}
					}
				}
			}
		}
		returnMap.put("errMsg", errMsg);
		returnMap.put("calFeeRateFormula", calFeeRateFormula);
		return returnMap;
	}
	
	/**
	 * 如果传入值为空，或者小于0 返回false
	 * @param src
	 * @return
	 */
	private boolean isErrData(Double src){
		if(src == null ){
			return true;
		}else if(src.doubleValue() < 0){
			return true;
		}else{
			return false;
		}
	}
}

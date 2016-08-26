package wusc.edu.pay.core.limit.util.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.util.AmountLimitValidator;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;


/**
 * 交易金额限制验证器工厂
 * 
 * @author：zh
 */
@Component(value = "amountLimitValidatorFactory")
public class AmountLimitValidatorFactory {

	private Map<String, AmountLimitValidator> amountLimitValidatorMap = null;
	
	@Autowired
	private SingleLimitValidator singleLimitValidator;
	
	@Autowired
	private CumulateLimitValidator cumulateLimitValidator;

	private void initMap(){
		Map<String, AmountLimitValidator> initMap = new HashMap<String, AmountLimitValidator>();
		initMap.put("SINGLE", singleLimitValidator);
		initMap.put("CUMULATE_DAILY", cumulateLimitValidator);
		initMap.put("CUMULATE_PER_MONTH", cumulateLimitValidator);
		setAmountLimitValidatorMap(initMap);
	}

	/**
	 * 获取交易金额限制验证器
	 * 
	 * @param limitTypeEnum
	 *            交易金额限制类型
	 * @return
	 */
	public AmountLimitValidator getAmountLimitValidator(
			LimitTypeEnum limitTypeEnum) {
		if(amountLimitValidatorMap == null){
			amountLimitValidatorMap = new HashMap<String, AmountLimitValidator>();
			initMap();
		}
		
		if (amountLimitValidatorMap.containsKey(limitTypeEnum.toString())) {
			return amountLimitValidatorMap.get(limitTypeEnum.toString());
		}
		throw new RuntimeException("根据限制类型未找到对应的交易金额限制验证器[limitTypeEnum="
				+ limitTypeEnum + "]");
	}

	public void setAmountLimitValidatorMap(
			Map<String, AmountLimitValidator> amountLimitValidatorMap) {
		this.amountLimitValidatorMap = amountLimitValidatorMap;
	}

}

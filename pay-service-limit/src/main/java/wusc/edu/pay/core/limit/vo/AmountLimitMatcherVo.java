package wusc.edu.pay.core.limit.vo;

import wusc.edu.pay.core.limit.biz.AmountLimitCondition;
import wusc.edu.pay.facade.limit.entity.AmountLimit;


/**
 * 金额限制匹配器
 * 
 * @author：zh
 */
public class AmountLimitMatcherVo {

	/**
	 * 匹配
	 * 
	 * <pre>
	 * 依次匹配业务功能，支付产品，支付方式，卡种
	 * </pre>
	 * 
	 * @param condition
	 *            金额限制条件入参实体
	 * @param amountLimit
	 *            交易金额限制实体
	 * @return
	 */
	public static boolean match(AmountLimitCondition condition,
			AmountLimit amountLimit) {
		if (condition == null || amountLimit == null) {
			throw new RuntimeException("限制条件过滤入参不合法[condition=" + condition
					+ "; amountLimit=" + amountLimit + "]");
		}

		// 开始匹配
		if (match(condition.getBizFunction(),
				amountLimit.getBizFunction())
				&& match(condition.getPayProduct(),
						amountLimit.getPayProduct())
				&& match(condition.getPayWay(), amountLimit.getPayWay())
				&& match(condition.getCardType(),
						amountLimit.getCardType())) {
			return true;
		}

		return false;
	}

	/**
	 * 匹配
	 * 
	 * <pre>
	 * 如果数据库表中的字段（业务功能）为空
	 *     表示不管入参（业务功能）是什么都通过过滤器
	 * 如果数据库表中的字段（业务功能）不为空
	 *     表示只有入参和它相同（equals）才能通过过滤器
	 * </pre>
	 * 
	 * @param conditionStr
	 *            入参变量
	 * @param ruleStr
	 *            数据库表中字段
	 * @return true匹配上（通过过滤器）/false未匹配上（未通过过滤器）
	 */
	private static boolean match(String conditionStr, String ruleStr) {
		if ("".equals(ruleStr) || ruleStr == null || ruleStr.equals(conditionStr)) {
			return true;
		}
		return false;
	}

}

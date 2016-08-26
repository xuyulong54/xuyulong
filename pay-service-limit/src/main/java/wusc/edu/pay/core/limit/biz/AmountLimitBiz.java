package wusc.edu.pay.core.limit.biz;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.limit.dao.AmountLimitDao;
import wusc.edu.pay.core.limit.util.AmountLimitValidator;
import wusc.edu.pay.core.limit.util.impl.AmountLimitValidatorFactory;
import wusc.edu.pay.core.limit.vo.AmountLimitMatcherVo;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;


/**
 * 交易金额限制业务服务实现
 * 
 * @author：zh
 */
@Component(value = "amountLimitBiz")
public class AmountLimitBiz {

	@Autowired
	private AmountLimitDao amountLimitDao;

	@Autowired
	private AmountLimitValidatorFactory amountLimitValidatorFactory;

	@Autowired
	private AmountCumulateBiz amountCumulateBiz;


	/**
	 * 验证交易金额
	 * @param bizFunction 业务功能
	 * @param payProduct 支付产品
	 * @param payWay 支付方式
	 * @param cardType 卡种类型
	 * @param tradeAmount 交易金额
	 * @param merchantNo 商户编号
	 */
	public void validateTradeAmount(String bizFunction, String payProduct,
			String payWay, String cardType, BigDecimal tradeAmount, String merchantNo) {
		// 验证参数合法性
		validateMatcherFactorValid(bizFunction, payProduct, payWay, cardType);
		validateTradeAmountValid(tradeAmount);

		// 根据商户号查询该商户所有对应的交易金额限制规则
		List<AmountLimit> amountLimitlist = amountLimitDao.getAmountLimitByMerchantNo(merchantNo);

		// 组装条件参数
		AmountLimitCondition condition = buildCondition(bizFunction, payProduct, payWay, cardType);

		// 循环过滤规则
		if (amountLimitlist != null && amountLimitlist.size() > 0) {
			for (AmountLimit amountLimit : amountLimitlist) {
				// 过滤限制规则
				if (AmountLimitMatcherVo.match(condition, amountLimit)) {
					// 根据限制类型获取相应验证器
					AmountLimitValidator amountLimitValidator = amountLimitValidatorFactory.getAmountLimitValidator(amountLimit.getLimitType());
					// 开始金额限制验证
					amountLimitValidator.validate(merchantNo,amountLimit, tradeAmount);
				}
			}
		}
	}

	/**
	 * 累计金额
	 * @param bizFunction 业务功能
	 * @param payProduct 支付产品
	 * @param payWay 支付方式
	 * @param cardType 卡种类型
	 * @param tradeAmount 交易金额
	 * @param merchantNo 商户编号
	 * @param orderDate 订单日期
	 */
	public void cumulateTradeAmount(String bizFunction, String payProduct, String payWay, String cardType, BigDecimal tradeAmount, String merchantNo, Date orderDate) {
		// 验证参数合法性
		validateMatcherFactorValid(bizFunction, payProduct, payWay, cardType);
		validateTradeAmountValid(tradeAmount);

		// 获取该商户号对应的所有金额限制实体
		List<AmountLimit> amountLimitlist = amountLimitDao.getAmountLimitByMerchantNo(merchantNo);

		// 组装条件参数
		AmountLimitCondition condition = buildCondition(bizFunction, payProduct, payWay, cardType);

		// 循环匹配规则
		if (amountLimitlist != null && amountLimitlist.size() > 0) {
			for (AmountLimit amountLimit : amountLimitlist) {
				if (canAddTradeFlow(amountLimit, condition)) {
					// 开始金额累计
					amountCumulateBiz.accumulateFlow(merchantNo,bizFunction, amountLimit, tradeAmount, orderDate);
					
				}
			}
		}

	}

	/**
	 * 是否能够进行累计金额
	 */
	private boolean canAddTradeFlow(AmountLimit amountLimit, AmountLimitCondition condition) {
		// 只有限制类型不是单笔的并且能匹配上的才能进行金额累计
		return !LimitTypeEnum.SINGLE.equals(amountLimit.getLimitType()) && AmountLimitMatcherVo.match(condition, amountLimit);
	}



	/**
	 * 组装条件参数对象
	 * 
	 * @param bizFunction
	 *            业务功能
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @param cardType
	 *            支付卡种
	 * @return
	 */
	private AmountLimitCondition buildCondition(String bizFunction, String payProduct, String payWay, String cardType) {
		AmountLimitCondition condition = new AmountLimitCondition();
		condition.setBizFunction(bizFunction);
		condition.setPayProduct(payProduct);
		condition.setPayWay(payWay);
		condition.setCardType(cardType);
		return condition;
	}

	/**
	 * 验证匹配因子
	 */
	private static void validateMatcherFactorValid(String bizFunction, String payProduct, String payWay, String cardType) {
		/**只检查业务功能不为空即可  修改人@laich*/
		if(StringUtils.isEmpty(bizFunction)){
			throw new IllegalArgumentException("业务功能不能为空![bizFunction=" + bizFunction + "; payProduct=" + payProduct + "; payWay=" + payWay + "; cardType=" + cardType + "]");
		}
		/*if ((bizFunction == null || bizFunction.trim().equals("")) && (payProduct == null || payProduct.trim().equals("")) && (payWay == null || payWay.trim().equals("")) && (cardType == null || cardType.trim().equals(""))) {
			throw new IllegalArgumentException("业务功能，支付产品，支付接口，支付卡种不能同时为空![bizFunction=" + bizFunction + "; payProduct=" + payProduct + "; payWay=" + payWay + "; cardType=" + cardType + "]");
		}*/
	}

	/**
	 * 验证交易金额
	 * 
	 * @param amount
	 */
	private static void validateTradeAmountValid(BigDecimal amount) {
		if (amount == null ) {
			throw new IllegalArgumentException("交易金额为空或不合法!");
		}
	}

	/**
	 * 分页查询金额限制
	 * 
	 * @param pageParam
	 * @param params
	 * @return
	 */
	public PageBean queryAmountLimitPage(PageParam pageParam, Map<String, Object> params) {
		return amountLimitDao.listPage(pageParam, params);
	}

	/**
	 * 新增金额限制
	 * 
	 * @param amountLimit
	 */
	public void addAmountLimit(AmountLimit amountLimit) {
		amountLimitDao.insert(amountLimit);
	}

	/**
	 * 修改金额限制
	 * 
	 * @param amountLimit
	 */
	public void updateAmountLimit(AmountLimit amountLimit) {
		amountLimitDao.update(amountLimit);
	}

	/**
	 * 根据主键加载金额限制
	 * @param id
	 * @return
	 */
	public AmountLimit getAmountLimitById(Long id) {
		return amountLimitDao.getById(id);
	}

	/**
	 * 根据金额包查询列表
	 * @param amountLimitPackId
	 * @return
	 */
	public List<AmountLimit> queryAmountLimit(Long amountLimitPackId) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("amountLimitPackId", amountLimitPackId);
		return amountLimitDao.listBy(params) !=null && amountLimitDao.listBy(params).size()>0?amountLimitDao.listBy(params):null;
	}

}

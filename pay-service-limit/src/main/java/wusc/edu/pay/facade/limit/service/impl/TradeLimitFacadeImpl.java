package wusc.edu.pay.facade.limit.service.impl;

import java.math.BigDecimal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.biz.AmountLimitBiz;
import wusc.edu.pay.core.limit.biz.SwitchLimitBiz;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.exptions.SwitchLimitException;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.limit.util.TradeParamValidator;


/**
 * 交易限制门面服务实现
 * 
 * @author：zh
 */
@Component("tradeLimitFacade")
public class TradeLimitFacadeImpl implements TradeLimitFacade {
	// 日志
	private Logger logger = LoggerFactory.getLogger(TradeLimitFacadeImpl.class);

	// 开关限制
	@Autowired
	private SwitchLimitBiz switchLimitBiz;

	// 金额限制
	@Autowired
	private AmountLimitBiz amountLimitBiz;

	/**
	 * 验证交易金额
	 * @param bizFunction 业务功能
	 * @param payProduct 支付产品
	 * @param payWay 支付方式
	 * @param cardType 卡种类型
	 * @param tradeAmount 交易金额
	 * @param merchantNo 商户编号
	 */
	public void validateTradeAmount(LimitTrxTypeEnum bizFunction, String payProduct, String payWay, String cardType, BigDecimal tradeAmount, String merchantNo) {

		logger.info("验证交易金额限制");
		amountLimitBiz.validateTradeAmount(bizFunction.name(), payProduct, payWay, cardType, tradeAmount, merchantNo);
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
	public void cumulateTradeAmount(LimitTrxTypeEnum bizFunction, String payProduct, String payWay, String cardType, BigDecimal tradeAmount, String merchantNo, Date orderDate) {

		logger.info("交易流量累计");

		// 验证参数合法性
		// TradeParamValidator.checkObjNotNull(cumulateTradeAmountParamDTO,
		// "交易流量累计DTO不能为空");
		// BeanValidator.validate(cumulateTradeAmountParamDTO);

		amountLimitBiz.cumulateTradeAmount(bizFunction.name(), payProduct, payWay, cardType, tradeAmount, merchantNo, orderDate);
	}

	/**
	 * 开关限制接口
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @param bizFunction
	 *            业务功能
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @return
	 */
	public void validateSwitchAvailable(String merchantNo, LimitTrxTypeEnum bizFunction, String payProduct, String payWay) {
		if (logger.isInfoEnabled()) {
			logger.info("验证开关有效性[merchantNo=" + merchantNo + ";bizFunction=" + bizFunction + ";payProduct=" + payProduct + ";payPay=" + payWay + "]");
		}

		// 验证参数合法性
		TradeParamValidator.checkStrNotNull(merchantNo, "商户号不能为空");
		TradeParamValidator.checkStrNotNull(bizFunction.name(), "业务功能不能为空");

		// SwitchValidateResultDTO result = new SwitchValidateResultDTO();

		// 判断业务功能是否开通
		if (switchLimitBiz.isBizFunctionAvailable(merchantNo, bizFunction.name())) {

			// 验证支付产品是否限制
			if (payProduct != null) {
				boolean payProductAvailable = switchLimitBiz.isPayProductAvailable(merchantNo, bizFunction.name(), payProduct);

				// 如果为真 就未限制
				// result.setPayProductAvailable(payProductAvailable);

				if (!payProductAvailable) {
					throw new SwitchLimitException(SwitchLimitException.MERCHANT_NOT_OPEN_THIS_PAY_PRODUCT, "商户被限制了[" + payProduct + "]支付产品");
				}

			}
			// 验证支付方式是否限制
			if (payWay != null) {
				boolean payWayAvailable = switchLimitBiz.isPayWayAvailable(merchantNo, payProduct, payWay);

				// 如果为真 就未限制
				// result.setPayWayAvailable(payWayAvailable);

				if (!payWayAvailable) {
					throw new SwitchLimitException(SwitchLimitException.MERCHANT_NOT_OPEN_THIS_PAY_WAY, "商户被限制了[" + payWay + "]支付方式");
				}

			}
		} else {
			throw new SwitchLimitException(SwitchLimitException.MERCHANT_NOT_OPEN_THIS_BIZ_FUNCTION, "商户未开通[" + bizFunction.getDesc() + "]业务功能");
		}
	}

	/**
	 * 验证业务功能
	 */
	@Override
	public void validateBizFunctionAvailable(String merchantNo, LimitTrxTypeEnum bizFunction) throws SwitchLimitException {
		if (logger.isInfoEnabled()) {
			logger.info("验证开关有效性[merchantNo=" + merchantNo + ";bizFunction=" + bizFunction);
		}

		// 判断业务功能是否开通
		if (!switchLimitBiz.isBizFunctionAvailable(merchantNo, bizFunction.name())) {
			throw new SwitchLimitException(SwitchLimitException.MERCHANT_NOT_OPEN_THIS_BIZ_FUNCTION, "商户未开通[" + bizFunction.getDesc() + "]业务功能");
		}
	}
	
	/**
	 * 验证支付方式是否被限制
	 * @param merchantNo 商户编号
	 * @param payInterface 支付接口
	 * @param payWay 支付方式
	 * return true 被限制  false 无限制
	 */
	public boolean validatePayInterfaceAvailable(String merchantNo, String payWay, String payInterface){
		if (logger.isInfoEnabled()) {
			logger.info("验证支付方式有效性[merchantNo=" + merchantNo + ";payWay=" + payWay + ";payInterface=" + payInterface);
		}
		
		// 判断支付接口是否开通
		return switchLimitBiz.isPayInterfaceAvailable(merchantNo, payWay, payInterface);		
//		if (switchLimitBiz.isPayInterfaceAvailable(merchantNo, payWay, payInterface)) {
//			throw new SwitchLimitException(SwitchLimitException.MERCHANT_NOT_OPEN_THIS_PAY_INTERFACE, "商户未开通[" + payInterface + "]支付接口");
//		}
	}
	
	/**
	 * 该商户是否开通该业务
	 * @param merchantNo 商户编号
	 * @param limitTrxTypeEnum 业务功能
	 * @return true为开通该业务，false为没开通
	 * @throws SwitchLimitException
	 */
	@Override
	public boolean validateIsBizFunctionAvailable(String merchantNo,
			LimitTrxTypeEnum limitTrxTypeEnum) throws SwitchLimitException {
		return switchLimitBiz.isBizFunctionAvailable(merchantNo,limitTrxTypeEnum);
	}
}

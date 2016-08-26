package wusc.edu.pay.facade.limit.service;

import java.math.BigDecimal;
import java.util.Date;

import wusc.edu.pay.facade.limit.dto.CumulateTradeAmountParamDTO;
import wusc.edu.pay.facade.limit.dto.ValidateTradeAmountParamDTO;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.exptions.AmountOverLimitException;
import wusc.edu.pay.facade.limit.exptions.SwitchLimitException;


/**
 * 交易限制门面服务
 * 
 * @author：zh
 */
public interface TradeLimitFacade {

	/**
	 * 验证交易金额
	 * @param LimitTrxTypeEnum 业务功能
	 * @param payProduct 支付产品
	 * @param payWay 支付方式
	 * @param cardType 卡种类型
	 * @param tradeAmount 交易金额
	 * @param merchantNo 商户编号
	 */
	public void validateTradeAmount(LimitTrxTypeEnum limitTrxTypeEnum, String payProduct, String payWay, String cardType, BigDecimal tradeAmount,  String merchantNo) throws AmountOverLimitException;

	/**
	 * 累计金额
	 * @param LimitTrxTypeEnum 业务功能
	 * @param payProduct 支付产品
	 * @param payWay 支付方式
	 * @param cardType 卡种类型
	 * @param tradeAmount 交易金额
	 * @param merchantNo 商户编号
	 * @param orderDate 订单日期
	 */
	public void cumulateTradeAmount(LimitTrxTypeEnum limitTrxTypeEnum, String payProduct, String payWay, String cardType, BigDecimal tradeAmount, String merchantNo, Date orderDate) throws AmountOverLimitException;

	/**
	 * 开关限制接口
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @param LimitTrxTypeEnum
	 *            业务功能
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @return
	 */
	public void validateSwitchAvailable(String merchantNo, LimitTrxTypeEnum limitTrxTypeEnum, String payProduct, String payWay) throws SwitchLimitException;

	/**
	 * 验证业务功能是否打开
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @param LimitTrxTypeEnum 
	 *            业务功能
	 * @throws SwitchLimitException
	 */
	public void validateBizFunctionAvailable(String merchantNo, LimitTrxTypeEnum limitTrxTypeEnum) throws SwitchLimitException;
	
	
	/**
	 * 验证支付接口是否被限制
	 * @param merchantNo 商户编号
	 * @param payInterface 支付接口
	 * @param payWay 支付方式
	 * return true 被限制  false 无限制
	 */
	public boolean validatePayInterfaceAvailable(String merchantNo, String payWay, String payInterface) throws SwitchLimitException;
	
	/**
	 * 该商户是否开通该业务
	 * @param merchantNo 商户编号
	 * @param limitTrxTypeEnum 业务功能
	 * @return true为开通该业务，false为没开通
	 * @throws SwitchLimitException
	 */
	public boolean validateIsBizFunctionAvailable(String merchantNo,LimitTrxTypeEnum limitTrxTypeEnum) throws SwitchLimitException;

}

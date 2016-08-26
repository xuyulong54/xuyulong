package wusc.edu.pay.facade.trade.service;

import java.math.BigDecimal;

import wusc.edu.pay.facade.account.exception.AccountBizException;
import wusc.edu.pay.facade.cost.exceptions.CostBizException;
import wusc.edu.pay.facade.fee.exceptions.FeeBizException;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;

import com.alibaba.dubbo.rpc.RpcException;

/**
 * 
 * @描述: 订单（创建、更新）对外服务接口 .
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-12,下午4:26:57 .
 * @版本: 1.0 .
 */
public interface PaymentFacade {

	/**
	 * 创建支付订单
	 * 
	 * @param paymentOrder
	 */
	void createPaymentOrder(PaymentOrderVo paymentOrderVo) throws TradeBizException;

	/**
	 * 创建充值的支付订单表.<br/>
	 * 
	 * @param vo
	 *            RechargeOrderVo.
	 */
	void createRechargePaymentOrder(RechargeOrderVo vo) throws TradeBizException;

	/**
	 * 交易撤销
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param cancelReason
	 * @throws TradeBizException
	 */
	void cancelPaymentOrder(String merchantNo, String merchantOrderNo, String cancelReason) throws TradeBizException;

	/**
	 * 创建支付记录.同时创建银行订单记录
	 * 
	 * @param entity
	 *            .
	 * @return ID .
	 */
	void createPaymentRecord(PaymentRecordVo paymentRecordVo) throws TradeBizException;

	/**
	 * 支付完成处理
	 * 
	 * @param bankOrderNo
	 *            银行订单号
	 * @param bankTrxNo
	 *            银行流水号
	 * @param amount
	 * @param status
	 * @return
	 * @throws TradeBizException
	 */
	PaymentRecord completePayment(String bankOrderNo, String bankTrxNo, double amount, PaymentRecordStatusEnum status)
			throws TradeBizException, AccountBizException,  CostBizException, FeeBizException, RpcException;

	/**
	 * 余额支付成功处理
	 * 
	 * @param paymentRecord
	 * @return
	 */
	void successAccountBalancePay(PaymentRecordVo paymentRecordVo) throws TradeBizException, AccountBizException, 
			FeeBizException, RpcException;

	/**
	 * 生成支付订单号
	 * 
	 * @return
	 */
	public String buildPaymentOrderNo() throws TradeBizException;

	/**
	 * 生成支付流水号
	 * 
	 * @return
	 * @throws TradeBizException
	 */
	public String buildPaymentTrxNo() throws TradeBizException;

	/**
	 * 生成转账流水号
	 * 
	 * @param bizType
	 * @return
	 */
	public String buildTransferTrxNo() throws TradeBizException;

	/**
	 * 生成银行订单号
	 * 
	 * @return
	 */
	public String buildBankOrderNo() throws TradeBizException;

	/**
	 * 根据用户编号、银行卡账号，更改协议状态，及生成通知URL
	 * 
	 * @param userNo
	 * @param bankAccountNo
	 * @return
	 */
	public String completeBankAgreement(String userNo, String bankAccountNo, String contractNo, int status) throws TradeBizException;

}

package wusc.edu.pay.facade.trade.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.trade.biz.PaymentBiz;
import wusc.edu.pay.core.trade.dao.PaymentRecordDao;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;


/**
 * 订单（创建、更新）对外服务接口实现类.
 * 
 * @描述: .
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-12,下午6:29:57 .
 * @版本: 1.0 .
 */
@Component("paymentFacade")
public class PaymentFacadeImpl extends PaymentQueryFacadeImpl implements PaymentFacade {

	@Autowired
	private PaymentBiz paymentBiz;

	@Autowired
	private PaymentRecordDao paymentRecordDao;

	/**
	 * 创建支付订单
	 * 
	 * @param paymentOrder
	 */
	public void createPaymentOrder(PaymentOrderVo paymentOrderVo) {
		paymentBiz.createPaymentOrder(paymentOrderVo, TradeBizTypeEnum.ONLINE_ACQUIRING);
	}

	/**
	 * 创建充值的支付订单表.<br/>
	 * 
	 * @param vo
	 *            RechargeOrderVo.
	 */
	@Override
	public void createRechargePaymentOrder(RechargeOrderVo rechargePaymentOrder) {
		paymentBiz.createRechargePaymentOrder(rechargePaymentOrder);
	}

	/**
	 * 交易撤销
	 */
	public void cancelPaymentOrder(String merchantNo, String merchantOrderNo, String cancelReason) {
		paymentBiz.cancelPaymentOrder(merchantNo, merchantOrderNo, cancelReason);
	}

	/**
	 * 创建支付记录（充值记录）.同时创建银行订单记录
	 * 
	 * @param entity
	 *            .
	 * @return ID .
	 */
	public void createPaymentRecord(PaymentRecordVo paymentRecordVo) {
		paymentBiz.createPaymentRecord(paymentRecordVo);
	}

	/**
	 * 余额支付完成处理
	 * 
	 * @param entity
	 *            .
	 * @return ID .
	 */
	public void successAccountBalancePay(PaymentRecordVo paymentRecordVo) {
		paymentBiz.successAccountBalancePay(paymentRecordVo);
	}

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
	 */
	public PaymentRecord completePayment(String bankOrderNo, String bankTrxNo, double amount, PaymentRecordStatusEnum status) {
		return paymentBiz.completePayment(bankOrderNo, bankTrxNo, amount, status);
	}

	/**
	 * 生成支付订单号
	 * 
	 * @return
	 */
	public String buildPaymentOrderNo() {
		return paymentRecordDao.buildPaymentOrderNo();
	}

	/**
	 * 生成支付流水号
	 * 
	 * @return
	 */
	public String buildPaymentTrxNo() {
		return paymentRecordDao.buildPaymentTrxNo();
	}

	/**
	 * 生成转账流水号
	 * 
	 * @param bizType
	 * @return
	 */
	public String buildTransferTrxNo() {
		return paymentRecordDao.buildTransferTrxNo();
	}

	/**
	 * 生成银行订单号
	 * 
	 * @return
	 */
	public String buildBankOrderNo() {
		return paymentRecordDao.buildBankOrderNo();
	}

	@Override
	public String completeBankAgreement(String userNo, String bankAccountNo, String contractNo, int status) {
		return paymentBiz.completeBankAgreement(userNo, bankAccountNo, contractNo, status);
	}

}

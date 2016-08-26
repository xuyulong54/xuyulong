package wusc.edu.pay.facade.banklink.netpay.service;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.facade.banklink.netpay.exceptions.BankLinkBizException;
import wusc.edu.pay.facade.banklink.netpay.vo.FileDownResult;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyParam;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentResult;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundNotifyResult;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundParam;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundResult;


/**
 * 该接口是用来对外开放的银行相关业务接口
 * 
 * @author
 * 
 */
public interface BankFacade {

	/**
	 * 预支付请求
	 * 
	 * @param prePaymentParam
	 * @return
	 */
	PrePaymentResult preparePay(PrePaymentParam prePaymentParam) throws BankLinkBizException;

	/**
	 * 批量支付订单查询
	 * 
	 * @param payInterface
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<PaymentQueryResult> periodQuery(String payInterface, Date startTime, Date endTime) throws BankLinkBizException;

	/**
	 * 单笔订单查询
	 * 
	 * @param payInterface
	 * @param bankOrderNo
	 * @param orderTime
	 * @return
	 */
	PaymentQueryResult query(String payInterface, String bankOrderNo, Date orderTime) throws BankLinkBizException;

	/**
	 * 对账文件下载
	 * 
	 * @param payInterface
	 * @param fileDate
	 * @return
	 */
	FileDownResult fileDown(String payInterface, Date fileDate) throws BankLinkBizException;

	/**
	 * 验证支付通知结果
	 * 
	 * @param param
	 * @return
	 */
	NotifyResult verify(NotifyParam param) throws BankLinkBizException;

	/**
	 * 验证退款通知结果
	 * 
	 * @param param
	 * @return
	 */
	RefundNotifyResult refundVerify(NotifyParam param) throws BankLinkBizException;
	
	/**
	 * 支付
	 * 
	 * @param paymentParam
	 *            支付参数对象
	 * 
	 * @return 支付结果
	 */
	public PaymentResult pay(PaymentParam paymentParam) throws BankLinkBizException;
}

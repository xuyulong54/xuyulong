package wusc.edu.pay.core.banklink.netpay.biz;

import wusc.edu.pay.facade.banklink.netpay.vo.PaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentResult;

/**
 * 支付
 * 
 * @author Administrator
 * 
 */
public interface Payment {

	/**
	 * 支付
	 * 
	 * @param paymentParam
	 *            支付参数对象
	 * 
	 * @return 支付结果
	 */
	public PaymentResult pay(PaymentParam paymentParam);

}

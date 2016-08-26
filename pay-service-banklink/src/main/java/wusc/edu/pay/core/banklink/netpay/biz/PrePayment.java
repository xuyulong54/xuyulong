package wusc.edu.pay.core.banklink.netpay.biz;

import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentResult;


/**
 * 预支付
 * 
 * @author Administrator
 * 
 */
public interface PrePayment {

	/**
	 * 预备支付
	 * 
	 * @param preparePayParam
	 *            预备支付参数对象
	 * 
	 * @return 预备支付结果
	 */
	public PrePaymentResult preparePay(PrePaymentParam prePaymentParam);

}

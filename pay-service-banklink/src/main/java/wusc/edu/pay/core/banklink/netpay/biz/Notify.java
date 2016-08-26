package wusc.edu.pay.core.banklink.netpay.biz;

import wusc.edu.pay.facade.banklink.netpay.vo.NotifyParam;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyResult;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundNotifyResult;

/**
 * 验证
 * 
 * @author Administrator
 * 
 */
public interface Notify {

	/**
	 * 验证支付结果
	 * 
	 * @param param
	 * @return
	 */
	public NotifyResult verify(NotifyParam param);

	/**
	 * 验证退款结果
	 * 
	 * @param param
	 * @return
	 */
	public RefundNotifyResult refundVerify(NotifyParam param);

}

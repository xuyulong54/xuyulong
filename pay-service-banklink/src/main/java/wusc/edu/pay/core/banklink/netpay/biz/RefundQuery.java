package wusc.edu.pay.core.banklink.netpay.biz;

import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.vo.RefundResult;


/**
 * 单笔退款查询
 * 
 * @author Administrator
 * 
 */
public interface RefundQuery {

	/**
	 * 单笔退款查询
	 * 
	 * @param payInterface
	 *            支付接口
	 * @param bankRefundOrderNo
	 *            银行退款订单号
	 * @param orgBankOrderNo
	 *            原银行订单号
	 * @param refundProcessTime
	 *            退款处理时间（退款受理成功的处理时间）
	 * 
	 * @return 退款查询结果
	 */
	public RefundResult refundQuery(String payInterface, String bankRefundOrderNo, String orgBankOrderNo, Date refundProcessTime);

}

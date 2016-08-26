package wusc.edu.pay.core.banklink.netpay.biz;

import java.util.Date;

import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;


/**
 * 支付单笔查询
 * 
 * @author Administrator
 * 
 */
public interface PaymentQuery {

	/**
	 * 单笔查询银行交易
	 * 
	 * @param payInterface
	 *            银行接口
	 * @param bankOrderNo
	 *            银行订单号
	 * @param orderTime
	 *            订单日期
	 * @return 银行订单查询结果
	 */
	PaymentQueryResult query(String payInterface, String bankOrderNo, Date orderTime);

}

package wusc.edu.pay.core.banklink.netpay.biz;

import java.util.Date;
import java.util.List;

import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;


/**
 * 支付批量查询
 * 
 * @author Administrator
 * 
 */
public interface PaymentQueryM {

	/**
	 * 查询一段时间内已支付的银行订单
	 * 
	 * @param payInterface
	 *            银行接口
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<PaymentQueryResult> periodQuery(String payInterface, Date startTime, Date endTime);
}

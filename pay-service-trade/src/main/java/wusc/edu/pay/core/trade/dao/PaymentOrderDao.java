package wusc.edu.pay.core.trade.dao;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;


/**
 * 
 * @描述: 支付订单表数据访问层接口 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-15,下午4:40:32
 * @版本: V1.0
 * 
 */
public interface PaymentOrderDao extends BaseDao<PaymentOrder> {

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder getBy_merchantNo_orderNo_status(String merchantNo, String merchantOrderNo, Integer status);

	/**
	 * 根据付款方_商户订单号
	 * 
	 * @param targetUserNo
	 * @param merchantOrderNo
	 * @return
	 */
	public PaymentOrder getByTargetUserNo_merchantOrderNo(String targetUserNo, String merchantOrderNo);

	/**
	 * 根据支付流水号查支付订单
	 * 
	 * @param trxNo
	 * @return
	 */
	public PaymentOrder getPaymentOrderBySuccessTrxNo(String trxNo);
}

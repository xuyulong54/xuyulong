package wusc.edu.pay.core.trade.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.trade.dao.PaymentOrderDao;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;


/**
 * 
 * @描述: 支付订单表数据访问层接口实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-7-15,下午4:44:38
 * @版本: V1.0
 * 
 */
@Repository("paymentOrderDao")
public class PaymentOrderDaoImpl extends BaseDaoImpl<PaymentOrder> implements PaymentOrderDao {

	/**
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder getBy_merchantNo_orderNo_status(String merchantNo, String merchantOrderNo, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", merchantOrderNo);
		params.put("status", status);
		return super.getBy(params);
	}

	/**
	 * 根据付款方_商户订单号
	 * 
	 * @param targetUserNo
	 * @param merchantOrderNo
	 * @return
	 */
	public PaymentOrder getByTargetUserNo_merchantOrderNo(String targetUserNo, String merchantOrderNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("payerUserNo", targetUserNo);
		params.put("merchantOrderNo", merchantOrderNo);
		return super.getBy(params);
	}

	/**
	 * 根据支付流水号查支付订单
	 */
	@Override
	public PaymentOrder getPaymentOrderBySuccessTrxNo(String trxNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("successTrxNo", trxNo);
		return super.getBy(params);
	}
}

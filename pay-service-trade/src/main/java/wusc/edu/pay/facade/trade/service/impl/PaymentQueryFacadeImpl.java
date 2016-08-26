package wusc.edu.pay.facade.trade.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.trade.dao.PaymentOrderDao;
import wusc.edu.pay.core.trade.dao.PaymentRecordDao;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;


/**
 * 
 * @描述: 订单查询对外服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-12,下午6:53:36 .
 * @版本: 1.0 .
 */
@Component("paymentQueryFacade")
public class PaymentQueryFacadeImpl implements PaymentQueryFacade {

	@Autowired
	private PaymentRecordDao paymentRecordDao;

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	/**
	 * 根据付款方_商户订单号
	 * 
	 * @param sourceUserNo
	 * @param merchantOrderNo
	 * @return
	 */
	public PaymentOrder getByTargetUserNo_merchantOrderNo(String targetUserNo, String merchantOrderNo) {
		return paymentOrderDao.getByTargetUserNo_merchantOrderNo(targetUserNo, merchantOrderNo);
	}

	/**
	 * 参数可传null
	 * 
	 * @param merchantNo
	 * @param orderNo
	 * @param trxNo
	 * @param bankOrderNo
	 * @param status
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(String merchantNo, String orderNo, String trxNo,
			String bankOrderNo, Integer status) {
		return paymentRecordDao.getBy_merchantNo_orderNo_trxNo_bankOrderNo_status(merchantNo, orderNo, trxNo, bankOrderNo, status);
	}

	/**
	 * 分页查询支付记录.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询能数 .
	 * @return PaymentRecordList.
	 */
	public PageBean queryPaymentRecordListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return paymentRecordDao.listPage(pageParam, paramMap);
	}

	/**
	 * 根据orderNo，merchantId，status查找
	 * 
	 * @param orderNo
	 * @param merchantNo
	 * @return
	 */
	public List<?> listPaymentRecordByOrder_MerchantNo_Status(String orderNo, String merchantNo, Integer status) {
		return paymentRecordDao.listBy_orderNo_merchantNo_status(orderNo, merchantNo, status);
	}

	/**
	 * 根据oneDayTime，bankChannelCode查找 oneDayTime=创建时间 用于业务对账</br>
	 * 
	 * @param oneDayTime
	 *            创建时间 查出这一天的记录 </br>
	 * @param bankChannelCode
	 *            银行渠道编号
	 * @return
	 */
	public List listByCreateTimeAndBankChannelCode(String oneDayTime, String bankChannelCode) {
		return paymentRecordDao.listBy_createTime_bankChannelCode(oneDayTime, bankChannelCode);
	}

	/**
	 * 根据银行订单号和支付时间查找
	 * 
	 * @param bankOrderNo
	 * @param paymentTime
	 * @return
	 */
	public PaymentRecord getPaymentRecordByBankOrderNo_paymentTime(String bankOrderNo, String paymentTime) {
		return paymentRecordDao.getBy_bankOrderNo_paymentTime(bankOrderNo, paymentTime);
	}

	/**
	 * 查询支付订单
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder getPaymentOrderBy_merchantNo_orderNo_status(String merchantNo, String merchantOrderNo, Integer status) {
		return paymentOrderDao.getBy_merchantNo_orderNo_status(merchantNo, merchantOrderNo, status);
	}

	/**
	 * 支付订单分页查询接口.<br/>
	 * 
	 * @param pageParam
	 *            分页参数.<br/>
	 * @param paramMap
	 *            查询参数集合.<br/>
	 * @return PageBean.
	 */
	public PageBean queryPaymentOrderListForPage(PageParam pageParam, Map<String, Object> paramMap) throws TradeBizException {
		return paymentOrderDao.listPage(pageParam, paramMap);
	}

	/**
	 * 支付交易记录查询
	 * 
	 * @param merchantNo
	 * @param trxNo
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNoAndTrxNo(String merchantNo, String trxNo) {
		return paymentRecordDao.getPaymentRecordByMerchantNoAndTrxNo(merchantNo, trxNo);
	}

	/**
	 * 根据支付流水号查支付订单
	 */
	@Override
	public PaymentOrder getPaymentOrderBySuccessTrxNo(String trxNo) {
		return paymentOrderDao.getPaymentOrderBySuccessTrxNo(trxNo);
	}

}

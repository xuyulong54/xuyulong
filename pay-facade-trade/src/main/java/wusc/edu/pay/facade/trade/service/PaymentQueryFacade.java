package wusc.edu.pay.facade.trade.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.exception.TradeBizException;


/**
 * 
 * @描述: 订单查询对外服务接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-12,下午4:27:33 .
 * @版本: 1.0 .
 */
public interface PaymentQueryFacade {

	/**
	 * 查询支付订单
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder getPaymentOrderBy_merchantNo_orderNo_status(String merchantNo, String merchantOrderNo, Integer status);

	/**
	 * 根据付款方_商户订单号
	 * 
	 * @param sourceUserNo
	 * @param merchantOrderNo
	 * @return
	 */
	public PaymentOrder getByTargetUserNo_merchantOrderNo(String targetUserNo, String merchantOrderNo);

	/**
	 * 支付交易记录查询
	 * 
	 * @param merchantNo
	 * @param trxNo
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNoAndTrxNo(String merchantNo, String trxNo);

	/**
	 * 参数可传null
	 * 
	 * @param merchantNo
	 *            商户编号
	 * @param orderNo
	 *            商户订单号(商户生成)
	 * @param trxNo
	 *            交易流水号(平台生成)
	 * @param bankOrderNo
	 *            银行订单号(平台生成传给银行的)
	 * @param status
	 *            订单状态
	 * @return
	 * @throws TradeBizException
	 */
	public PaymentRecord getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(String merchantNo, String orderNo, String trxNo,
			String bankOrderNo, Integer status) throws TradeBizException;

	/**
	 * 分页查询支付记录.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询能数 .
	 * @return PaymentRecordList.
	 */
	PageBean queryPaymentRecordListPage(PageParam pageParam, Map<String, Object> paramMap) throws TradeBizException;

	/**
	 * 根据orderNo，merchantNo，status查找
	 * 
	 * @param orderNo
	 *            商户订单号
	 * @param merchantNo
	 *            商户编号
	 * @param status
	 *            订单状态
	 * @return
	 */
	public List<?> listPaymentRecordByOrder_MerchantNo_Status(String orderNo, String merchantNo, Integer status) throws TradeBizException;

	/**
	 * 根据oneDayTime，bankChannelCode查找 oneDayTime=创建时间 用于业务对账</br>
	 * 
	 * @param oneDayTime
	 *            创建时间 查出这一天的记录 </br>
	 * @param bankChannelCode
	 *            银行渠道编号
	 * @return
	 */
	public List listByCreateTimeAndBankChannelCode(String oneDayTime, String bankChannelCode) throws TradeBizException;

	/**
	 * 根据银行订单号和支付时间查找
	 * 
	 * @param bankOrderNo
	 *            银行订单号
	 * @param paymentTime
	 *            支付时间
	 * @return
	 * @throws TradeBizException
	 */
	public PaymentRecord getPaymentRecordByBankOrderNo_paymentTime(String bankOrderNo, String paymentTime);

	/**
	 * 支付订单分页查询接口.<br/>
	 * 
	 * @param pageParam
	 *            分页参数.<br/>
	 * @param paramMap
	 *            查询参数集合.<br/>
	 * @return PageBean.
	 */
	public PageBean queryPaymentOrderListForPage(PageParam pageParam, Map<String, Object> paramMap) throws TradeBizException;

	/**
	 * 根据支付流水号查支付订单
	 * 
	 * @param trxNo
	 * @return
	 */
	public PaymentOrder getPaymentOrderBySuccessTrxNo(String trxNo);

}

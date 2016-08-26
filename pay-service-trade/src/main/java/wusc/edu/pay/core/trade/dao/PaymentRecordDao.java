package wusc.edu.pay.core.trade.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;


/**
 * 
 * @描述: 支付记录表数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-5-8,上午10:33:00
 * @版本: V1.0
 * 
 */
public interface PaymentRecordDao extends BaseDao<PaymentRecord> {

	/**
	 * 根据银行订单号查
	 * 
	 * @param bankOrderNo
	 * @param isPessimist
	 * @return
	 */
	public PaymentRecord getByBankOrderNo_IsPessimist(String bankOrderNo, Boolean isPessimist);

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
	public PaymentRecord getBy_merchantNo_orderNo_trxNo_bankOrderNo_status(String merchantNo, String orderNo, String trxNo,
			String bankOrderNo, Integer status);

	/**
	 * 支付交易记录查询
	 * 
	 * @param merchantNo
	 * @param trxNo
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNoAndTrxNo(String merchantNo, String trxNo);

	/**
	 * 根据orderNo，merchantNo，status查找
	 * 
	 * @param orderNo
	 * @param merchantNo
	 * @param status
	 * @return
	 */
	public List<?> listBy_orderNo_merchantNo_status(String orderNo, String merchantNo, Integer status);

	/**
	 * 根据oneDayTime，bankChannelCode查找 oneDayTime=创建时间 用于业务对账
	 * 
	 * @param oneDayTime
	 *            创建时间 查出这一天的记录
	 * @param bankChannelCode
	 *            银行渠道编号
	 * @return
	 */
	public List listBy_createTime_bankChannelCode(String oneDayTime, String bankChannelCode);

	/**
	 * 根据银行订单号和支付时间查找
	 * 
	 * @param bankOrderNo
	 * @param paymentTime
	 * @return
	 */
	public PaymentRecord getBy_bankOrderNo_paymentTime(String bankOrderNo, String paymentTime);

	// /////////////////// 编号生成方法 Begin///////////////////////

	/**
	 * 生成支付订单号
	 * 
	 * @return
	 */
	public String buildPaymentOrderNo();

	/**
	 * 生成支付流水号
	 * 
	 * @return
	 */
	public String buildPaymentTrxNo();

	/**
	 * 生成转账流水号
	 * 
	 * @return
	 */
	public String buildTransferTrxNo();

	/**
	 * 生成银行订单号
	 * 
	 * @return
	 */
	public String buildBankOrderNo();

	/**
	 * 根据商户编号和商户订单号查支付记录
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @return
	 */
	public List<PaymentRecord> getPaymentRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo);

	// /////////////////// 编号生成方法 End///////////////////////

}

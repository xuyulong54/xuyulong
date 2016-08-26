package wusc.edu.pay.core.trade.dao.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.core.trade.dao.PaymentRecordDao;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.SeqTypeEnum;


/**
 * 
 * @描述: 支付记录表数据访问层接口实现类.
 * @作者: WuShuicheng.
 * @创建: 2014-5-8,上午10:32:33
 * @版本: V1.0
 * 
 */
@Repository("paymentRecordDao")
public class PaymentRecordDaoImpl extends BaseDaoImpl<PaymentRecord> implements PaymentRecordDao {

	/**
	 * 根据银行订单号查
	 * 
	 * @param bankOrderNo
	 * @param isPessimist
	 * @return
	 */
	public PaymentRecord getByBankOrderNo_IsPessimist(String bankOrderNo, Boolean isPessimist) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankOrderNo", bankOrderNo);
		params.put("isPessimist", isPessimist);

		return super.getBy(params);
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
	public PaymentRecord getBy_merchantNo_orderNo_trxNo_bankOrderNo_status(String merchantNo, String orderNo, String trxNo,
			String bankOrderNo, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("merchantOrderNo", orderNo);
		params.put("trxNo", trxNo);
		params.put("bankOrderNo", bankOrderNo);
		params.put("status", status);

		return super.getBy(params);
	}

	/**
	 * 根据orderNo，merchantNo，status查找
	 * 
	 * @param orderNo
	 * @param merchantNo
	 * @param status
	 * @return
	 */
	public List<?> listBy_orderNo_merchantNo_status(String orderNo, String merchantNo, Integer status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantOrderNo", orderNo);
		params.put("merchantNo", merchantNo);
		params.put("status", status);
		return super.listBy(params);
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
	public List listBy_createTime_bankChannelCode(String oneDayTime, String bankChannelCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("beginDate", oneDayTime);
		params.put("endDate", oneDayTime);
		params.put("payInterfaceCode", bankChannelCode);
		return super.listBy(params);
	}

	/**
	 * 根据银行订单号和支付时间查找
	 * 
	 */
	public PaymentRecord getBy_bankOrderNo_paymentTime(String bankOrderNo, String paymentTime) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("bankOrderNo", bankOrderNo);
		params.put("paySuccessTime", paymentTime);
		return super.getBy(params);
	}

	// /////////////////// 编号生成方法 Begin///////////////////////

	/**
	 * 生成支付订单号
	 * 
	 * @return
	 */
	@Override
	public String buildPaymentOrderNo() {
		return SeqTypeEnum.PAYMENT_ORDER_NO.getValue() + DateUtils.toString(new Date(), "yyMMdd")
				+ super.getSeqNextValue("PAYMENT_ORDERNO_SEQ");
	}

	/**
	 * 生成支付流水号
	 * 
	 * @return
	 */
	@Override
	public String buildPaymentTrxNo() {
		return SeqTypeEnum.PAYMENT_TRX_NO.getValue() + DateUtils.toString(new Date(), "yyMMdd")
				+ super.getSeqNextValue("PAYMENT_TRXNO_SEQ");
	}

	/**
	 * 生成转账流水号
	 * 
	 * @return
	 */
	@Override
	public String buildTransferTrxNo() {
		return SeqTypeEnum.TRANSFER_TRX_NO.getValue() + DateUtils.toString(new Date(), "yyMMdd")
				+ super.getSeqNextValue("TRANSFER_TRXNO_SEQ");
	}

	/**
	 * 生成银行订单号
	 * 
	 * @return
	 */
	@Override
	public String buildBankOrderNo() {
		return SeqTypeEnum.BANK_ORDER_NO.getValue() + DateUtils.toString(new Date(), "yyMMdd") + super.getSeqNextValue("BANK_ORDERNO_SEQ");
	}

	/**
	 * 支付交易记录查询
	 * 
	 * @param merchantNo
	 * @param trxNo
	 * @return
	 */
	@Override
	public PaymentRecord getPaymentRecordByMerchantNoAndTrxNo(String merchantNo, String trxNo) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("merchantNo", merchantNo);
		params.put("trxNo", trxNo);

		return super.getBy(params);
	}

	/**
	 * 根据商户编号和商户订单号查支付记录
	 */
	@Override
	public List<PaymentRecord> getPaymentRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("merchantNo", merchantNo);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		return super.listBy(paramMap);
	}

}

package wusc.edu.pay.gateway.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.gateway.exceptions.GateWayException;


@Component("orderBiz")
public class OrderBiz {

	@Autowired
	private PaymentFacade paymentFacade;

	private static final Log log = LogFactory.getLog(OrderBiz.class);

	/**
	 * 保存支付记录，并创建支付成功统计记录
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	public void completeBalancePayment(PaymentRecordVo paymentRecord) throws GateWayException {

		paymentFacade.successAccountBalancePay(paymentRecord);

		log.info("余额支付,处理成功.");
	}

	public void createPaymentOrder(PaymentOrderVo paymentOrderVo) throws GateWayException {
		paymentOrderVo.setTrxModel(TrxModelEnum.IMMEDIATELY.getValue());
		paymentFacade.createPaymentOrder(paymentOrderVo);
		log.info("保存支付记录,银行订单成功.");
	}

	/**
	 * 保存支付记录（充值记录）并创建银行订单
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	public void createPaymentRecord(PaymentRecordVo paymentRecord) throws GateWayException {
		paymentFacade.createPaymentRecord(paymentRecord);

		log.info("保存支付记录,银行订单成功.");
	}

	/**
	 * 设置银行订单号
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	public PaymentRecordVo setBankOrderNo(PaymentRecordVo paymentRecord) throws GateWayException {

		// paymentRecord.setBankOrderNo("OTC" + System.currentTimeMillis());
		paymentRecord.setBankOrderNo(paymentFacade.buildBankOrderNo());
		log.info(String.format("获取唯一银行订单号:%s", paymentRecord.getBankOrderNo()));
		return paymentRecord;
	}

	/**
	 * 设置支付流水号
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	public PaymentRecordVo setPaymentTrxNo(PaymentRecordVo paymentRecord) throws GateWayException {
		paymentRecord.setTrxNo(paymentFacade.buildPaymentTrxNo());
		log.info(String.format("获取唯一支付流水号:%s", paymentRecord.getTrxNo()));
		return paymentRecord;
	}

}

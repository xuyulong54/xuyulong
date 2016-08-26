package wusc.edu.pay.facade.banklink.netpay.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.banklink.factory.BanklinkBizFactory;
import wusc.edu.pay.core.banklink.netpay.biz.FileDown;
import wusc.edu.pay.core.banklink.netpay.biz.Notify;
import wusc.edu.pay.core.banklink.netpay.biz.Payment;
import wusc.edu.pay.core.banklink.netpay.biz.PaymentQuery;
import wusc.edu.pay.core.banklink.netpay.biz.PaymentQueryM;
import wusc.edu.pay.core.banklink.netpay.biz.PrePayment;
import wusc.edu.pay.facade.banklink.netpay.exceptions.BankLinkBizException;
import wusc.edu.pay.facade.banklink.netpay.service.BankFacade;
import wusc.edu.pay.facade.banklink.netpay.vo.FileDownResult;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyParam;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentResult;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundNotifyResult;


/**
 * 
 * @author Administrator
 * 
 */
@Component("bankFacade")
public class BankFacadeImpl implements BankFacade {

	@Autowired
	private BanklinkBizFactory banklinkBizFactory;

	private static final Log log = LogFactory.getLog(BankFacadeImpl.class);



	/**
	 * 预支付请求
	 * 
	 * @param prePaymentParam
	 * @return
	 */
	public PrePaymentResult preparePay(PrePaymentParam prePaymentParam) {

		prePaymentParam.checkValue();

		PrePayment prePayment = (PrePayment) banklinkBizFactory.getService(prePaymentParam.getPayInterface());

		return prePayment.preparePay(prePaymentParam);
	}

	/**
	 * 批量支付订单查询
	 * 
	 * @param payInterface
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public List<PaymentQueryResult> periodQuery(String payInterface, Date startTime, Date endTime) {

		PaymentQueryM paymentQueryM = (PaymentQueryM) banklinkBizFactory.getService(payInterface);

		return paymentQueryM.periodQuery(payInterface, startTime, endTime);

	}

	/**
	 * 单笔订单查询
	 * 
	 * @param payInterface
	 * @param bankOrderNo
	 * @param orderTime
	 * @return
	 */
	public PaymentQueryResult query(String payInterface, String bankOrderNo, Date orderTime) {

		PaymentQuery paymentQuery = (PaymentQuery) banklinkBizFactory.getService(payInterface);

		return paymentQuery.query(payInterface, bankOrderNo, orderTime);

	}

	/**
	 * 对账文件下载
	 * 
	 * @param payInterface
	 * @param fileDate
	 * @return
	 */
	public FileDownResult fileDown(String payInterface, Date fileDate) {

		FileDown fileDown = (FileDown) banklinkBizFactory.getService(payInterface);

		return fileDown.fileDown(payInterface, fileDate);

	}

	/**
	 * 验证支付通知结果
	 * 
	 * @param param
	 * @return
	 */
	public NotifyResult verify(NotifyParam param) {

		Notify notify = (Notify) banklinkBizFactory.getService(param.getPayInterface());

		return notify.verify(param);
	}

	/**
	 * 验证退款通知结果
	 * 
	 * @param param
	 * @return
	 */
	public RefundNotifyResult refundVerify(NotifyParam param) {

		Notify notify = (Notify) banklinkBizFactory.getService(param.getPayInterface());

		return notify.refundVerify(param);
	}

	@Override
	public PaymentResult pay(PaymentParam paymentParam) throws BankLinkBizException {

		Payment payment = (Payment) banklinkBizFactory.getService(paymentParam.getPayInterface());

		return payment.pay(paymentParam);
	}
}

package wusc.edu.pay.gateway.action;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.util.OrderFacadeUtil;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.gateway.exceptions.GateWayException;


@Scope("prototype")
public class BankPaySuccessAction extends GateWayAction {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(BankPaySuccessAction.class);

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	public String paySuccess() throws GateWayException, UnsupportedEncodingException {
		// 为保证两个页面同一个会话,必须存session
		PaymentOrderVo vo = super.getPaymentOrderVo();
		if (vo == null) {
			throw BizException.SESSION_IS_OUT_TIME; // 抛出会话超时异常
		}
		String orderNo = vo.getMerchantOrderNo();
		String merchantNo = vo.getMerchantNo();
		if (orderNo == null || StringUtil.isBlank(merchantNo)) {
			return "bankPayFalse";
		}

		PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(merchantNo,
				vo.getMerchantOrderNo(), null, null, 100);
		if (paymentRecord == null) {
			return "bankPayFalse";
		}
		String resultUrl = this.getBankToMerchantUrl(paymentRecord);

		this.putData("productName", paymentRecord.getProductName());
		this.putData("merchantName", paymentRecord.getMerchantName());
		this.putData("payAmount", paymentRecord.getOrderAmount());
		this.putData("orderNo", paymentRecord.getMerchantOrderNo());
		this.putData("trxNo", paymentRecord.getTrxNo());
		this.putData("bankOrderNo", paymentRecord.getBankOrderNo());
		this.putData("createTime", paymentRecord.getCreateTime());
		this.putData("paymentTime", paymentRecord.getPaySuccessTime());
		this.putData("resultUrl", resultUrl);
		return "bankPaySuccess";
	}

	private String getBankToMerchantUrl(PaymentRecord paymentRecord) {
		int cmdCode = OrderFacadeUtil.toCmdCodeEnum(paymentRecord.getBizType(), paymentRecord.getPaymentType()).getValue();

		MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(paymentRecord.getMerchantNo());
		String returnUrl = null;
		try {
			returnUrl = OrderFacadeUtil.buildMerchantNotifyUrl(paymentRecord.getReturnUrl(), cmdCode, paymentRecord.getMerchantNo(),
					paymentRecord.getMerchantOrderNo(), paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getCur(),
					paymentRecord.getProductDesc(), paymentRecord.getStatus(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
					paymentRecord.getBankTrxNo(), paymentRecord.getPaySuccessTime(), paymentRecord.getCompleteTime(),
					paymentRecord.getPayWayCode(), merchantOnline.getMerchantKey());
		} catch (Exception e) {
			log.error("生成返回商户地址错误" + e);
		}

		return returnUrl;
	}

}

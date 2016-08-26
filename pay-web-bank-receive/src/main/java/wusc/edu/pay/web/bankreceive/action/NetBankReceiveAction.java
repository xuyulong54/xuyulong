package wusc.edu.pay.web.bankreceive.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.util.ByteArrayBuffer;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;
import wusc.edu.pay.facade.banklink.netpay.service.BankFacade;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyParam;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyResult;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.util.OrderFacadeUtil;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;

import com.alibaba.fastjson.JSONObject;

public class NetBankReceiveAction extends Struts2ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6667580313192178807L;

	private static final Log log = LogFactory.getLog(NetBankReceiveAction.class);

	@Autowired
	private BankFacade bankFacade;
	@Autowired
	private PaymentFacade paymentFacade;
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	private String payInterface;

	/**
	 * 页面通知
	 * 
	 * @return
	 * @throws Exception
	 */
	public String page() throws Exception {

		log.debug(this.getHttpRequest().getRequestURL().toString() + "?" + this.getHttpRequest().getQueryString());
		log.debug(this.getParamMap_NullStr().toString());

		String encode = getHttpRequest().getParameter("encode");

		NotifyParam param = new NotifyParam();
		param.setPayInterface(payInterface);
		if (StringUtil.isBlank(encode)) {
			param.setParamMap(this.getParamMap_NullStr());
		} else {
			param.setParamMap(this.getParamMap_GBK());
		}
		param.setUrl(this.getHttpRequest().getRequestURL().toString() + "?" + this.getHttpRequest().getQueryString());
		NotifyResult result = bankFacade.verify(param);

		if (result == null) {
			log.debug("NotifyResult is null");
			return ERROR;
		}

		log.info("回调验签结果:" + JSONObject.toJSONString(result));

		if (result.isVerify()) {

			// 支付记录是否已处理
			PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null,
					null, result.getBankOrderNo(), null);

			if (paymentRecord.getStatus().equals(PaymentRecordStatusEnum.CREATED.getValue())) {
				if (result.getStatus().equals(BankTradeStatusEnum.SUCCESS)) {
					paymentRecord = paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount()
							.doubleValue(), PaymentRecordStatusEnum.SUCCESS);
				} else if (result.getStatus().equals(BankTradeStatusEnum.FAILED)) {
					paymentRecord = paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount()
							.doubleValue(), PaymentRecordStatusEnum.FAILED);
				}
			}

			String backToMerchantUrl = this.getBankToMerchantUrl(paymentRecord);

			if (paymentRecord.getStatus().equals(PaymentRecordStatusEnum.FAILED.getValue())) {
				super.putData("backToMerchantUrl", backToMerchantUrl);
				return ERROR;
			}

			super.putData("backToMerchantUrl", backToMerchantUrl);
			super.putData("productName", paymentRecord.getProductName());
			super.putData("merchantName", paymentRecord.getMerchantName());
			super.putData("payAmount", paymentRecord.getOrderAmount());
			super.putData("orderNo", paymentRecord.getMerchantOrderNo());
			super.putData("trxNo", paymentRecord.getTrxNo());
			super.putData("bankOrderNo", paymentRecord.getBankOrderNo());
			super.putData("createTime", paymentRecord.getCreateTime());
			super.putData("paymentTime", paymentRecord.getPaySuccessTime());

			return SUCCESS;

		} else {
			log.debug("签名失败.");
			return ERROR;
		}

	}

	private String getBankToMerchantUrl(PaymentRecord paymentRecord) throws Exception {
		int cmdCode = OrderFacadeUtil.toCmdCodeEnum(paymentRecord.getBizType(), paymentRecord.getPaymentType()).getValue();

		MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(paymentRecord.getMerchantNo());
		String returnUrl = OrderFacadeUtil.buildMerchantNotifyUrl(paymentRecord.getReturnUrl(), cmdCode, paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getCur(),
				paymentRecord.getProductDesc(), paymentRecord.getStatus(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
				paymentRecord.getBankTrxNo(), paymentRecord.getPaySuccessTime(), paymentRecord.getCompleteTime(),
				paymentRecord.getPayWayCode(), merchantOnline.getMerchantKey());

		return returnUrl;
	}

	/**
	 * 后台通知
	 * 
	 * @return
	 */
	public String server() {
		log.info(payInterface);
		log.info(this.getHttpRequest().getRequestURL().toString() + "?" + this.getHttpRequest().getQueryString());
		log.info(this.getParamMap_NullStr().toString());

		String encode = getHttpRequest().getParameter("encode");

		NotifyParam param = new NotifyParam();
		param.setPayInterface(payInterface);
		if (StringUtil.isBlank(encode)) {
			param.setParamMap(this.getParamMap_NullStr());
		} else {
			param.setParamMap(this.getParamMap_GBK());
		}
		NotifyResult result = bankFacade.verify(param);

		if (result == null) {
			log.debug("NotifyResult is null");
			return null;
		}

		log.debug("回调验签结果:" + JSONObject.toJSONString(result));

		if (result.isVerify()) {

			// 支付记录是否已处理
			PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null,
					null, result.getBankOrderNo(), PaymentRecordStatusEnum.CREATED.getValue());

			if (paymentRecord != null) {
				if (result.getStatus().equals(BankTradeStatusEnum.SUCCESS)) {
					paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount().doubleValue(),
							PaymentRecordStatusEnum.SUCCESS);
				} else if (result.getStatus().equals(BankTradeStatusEnum.FAILED)) {
					paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount().doubleValue(),
							PaymentRecordStatusEnum.FAILED);
				}
			}

			// 回写成功字符串
			this.write(result.getResponseStr());

		} else {
			log.debug("签名失败.");
		}

		return null;
	}
	
	
	/**
	 * 用来接收银行端，以流的方式传送通知结果
	 * @return
	 */
	public String serverStream() {
		
		String response = "";
		try {
			ServletInputStream in = this.getHttpRequest().getInputStream();
			 response = readStringFromInputStream(in);
			log.info("response:" + response);
		} catch (IOException e) {
			log.error("IOException:",e);
		}

		NotifyParam param = new NotifyParam();
		param.setPayInterface(payInterface);
		Map<String, Object> map = new HashMap<String , Object>();
		map.put("param", response);
		
		NotifyResult result = bankFacade.verify(param);

		if (result == null) {
			log.debug("NotifyResult is null");
			return null;
		}

		log.debug("回调验签结果:" + JSONObject.toJSONString(result));

		if (result.isVerify()) {

			// 支付记录是否已处理
			PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null,
					null, result.getBankOrderNo(), PaymentRecordStatusEnum.CREATED.getValue());

			if (paymentRecord != null) {
				if (result.getStatus().equals(BankTradeStatusEnum.SUCCESS)) {
					paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount().doubleValue(),
							PaymentRecordStatusEnum.SUCCESS);
				} else if (result.getStatus().equals(BankTradeStatusEnum.FAILED)) {
					paymentFacade.completePayment(result.getBankOrderNo(), result.getBankTrxNo(), result.getPayAmount().doubleValue(),
							PaymentRecordStatusEnum.FAILED);
				}
			}

			// 回写成功字符串
			this.write(result.getResponseStr());

		} else {
			log.debug("签名失败.");
		}

		return null;
	}
	
	
	private String readStringFromInputStream(InputStream is) throws IOException {
		byte[] buf = new byte[4096];
		int len = 0;
		ByteArrayBuffer bytes = new ByteArrayBuffer(4096);

		while (true) {
			len = is.read(buf);
			if (len >= 0) {
				bytes.append(buf, 0, len);
			} else {
				break;
			}
		}
		return new String(bytes.toByteArray(), "UTF-8");
	}

	public String getPayInterface() {
		return payInterface;
	}

	public void setPayInterface(String payInterface) {
		this.payInterface = payInterface;
	}

}

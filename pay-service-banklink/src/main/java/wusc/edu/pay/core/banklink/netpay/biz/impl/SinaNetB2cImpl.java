package wusc.edu.pay.core.banklink.netpay.biz.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpUtils;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.rsa.MD5;
import wusc.edu.pay.core.banklink.common.util.BankTransUtils;
import wusc.edu.pay.core.banklink.netpay.biz.FileDown;
import wusc.edu.pay.core.banklink.netpay.biz.Notify;
import wusc.edu.pay.core.banklink.netpay.biz.PaymentQuery;
import wusc.edu.pay.core.banklink.netpay.biz.PrePayment;
import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;
import wusc.edu.pay.facade.banklink.netpay.vo.FileDownResult;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyParam;
import wusc.edu.pay.facade.banklink.netpay.vo.NotifyResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentResult;
import wusc.edu.pay.facade.banklink.netpay.vo.RefundNotifyResult;


public class SinaNetB2cImpl implements PrePayment, PaymentQuery, Notify, FileDown {

	private static final Log log = LogFactory.getLog(SinaNetB2cImpl.class);

	private String bankId;
	
	private String merchantNo;
	private String sellerEmail;
	private String requestUrl;
	private String notifyURL;
	private String returnURL;
	private String method;
	private String queryUrl;
	private String refundUrl;
	private String publicCertPath;
	private String privateCertPath;
	private String privatePassword;
	private String payUrl;// https://testgate.pay.sina.com.cn/acquire-order-channel/gateway/receiveOrderLoading.htm
	private String key;
	private String inputCharset;//
	private String merchantAcctId;
	private String signType;// 加密方式 1 为md5

	@SuppressWarnings("rawtypes")
	public FileDownResult fileDown(String payInterface, Date fileDate) {

		String startTime = DateUtils.toString(fileDate, "yyyyMMdd") + "000000";// 开始时间
		String endTime = DateUtils.toString(fileDate, "yyyyMMdd") + "235959";// 结束时间
		String version = "weibopay_query_api_1";// 版本
		String pageNo = "0";// 查询页码
		String pageSize = "3000";// 默认为30，目前不可超过3000

		StringBuffer url = new StringBuffer();
		url.append("inputCharset=").append(inputCharset);
		url.append("&version=").append(version);
		url.append("&pid=").append(merchantNo);
		url.append("&startTime=").append(startTime);
		url.append("&endTime=").append(endTime);
		url.append("&pageNo=").append(pageNo);
		url.append("&pageSize=").append(pageSize);
		url.append("&signType=").append(signType);

		String signMsg = MD5.getMD5Str(url.toString() + "&key=" + key).toLowerCase(); // 签名字符串

		String send_url = queryUrl + "?" + url.toString() + "&signMsg=" + signMsg.toString();

		String responseStr = SimpleHttpUtils.httpGet(send_url, new HashMap());

		FileDownResult result = new FileDownResult();
		result.setFileContent(responseStr);
		return result;
	}

	@SuppressWarnings("rawtypes")
	public PaymentQueryResult query(String payInterface, String bankOrderNo, Date orderTime) {

		String version = "weibopay_query_api_1";// 版本

		StringBuffer url = new StringBuffer();
		url.append("inputCharset=").append(inputCharset);
		url.append("&version=").append(version);
		url.append("&pid=").append(merchantNo);
		url.append("&orderId=").append(bankOrderNo);
		url.append("&signType=").append(signType);

		String signMsg = MD5.getMD5Str(url.toString() + "&key=" + key).toLowerCase();

		String send_url = queryUrl + "?" + url.toString() + "&signMsg=" + signMsg;

		String str = SimpleHttpUtils.httpGet(send_url, new HashMap());

		PaymentQueryResult result = new PaymentQueryResult();

		try {
			Document document = DocumentHelper.parseText(str);
			List profileList = document.selectNodes("//queryResult//recordList//orderDetail");
			for (int i = 0; i < profileList.size(); i++) {
				Element element = (Element) profileList.get(i);
				result.setBankOrderNo(element.elementText("orderId"));
				result.setBankStatus("10");// 原始状态成功的都为：10
				result.setBankTrxNo(element.elementText("dealId"));
				result.setPayAmount(BigDecimal.valueOf(Double.valueOf(AmountUtil.roundDown(AmountUtil.mul(Double.valueOf(element.elementText("payAmount")),0.01d)))));
				result.setStatus(BankTradeStatusEnum.SUCCESS);
				try {
					result.setBankSuccessTime(DateUtils.parseDate(element.elementText("dealTime"), "yyyyMMddhhmmss"));
					// 卡类型result.setCardType(cardType)
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			return result;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public PrePaymentResult preparePay(PrePaymentParam prePaymentParam) {

		StringBuffer sign_url = new StringBuffer();

		String bgUrl = notifyURL;// 回调通知
		String version = "v2.3";// 版本
		String language = "1";
		String signType = "1";
		// String payerId = "";// 付款人标识 可空
		String orderId = prePaymentParam.getBankOrderNo();// 商户订单号
		String orderAmount = BankTransUtils.formatAmountToFen(prePaymentParam.getPayAmount());// 为整数
																								// 分为单位商户订单金额
		String orderTime = DateUtils.getStrFormTime("yyyyMMddHHmmss", prePaymentParam.getOrderDate());// 商户订单提交时间
																										// 格式：20071117020101
		String productName = prePaymentParam.getProductName();
		// String ip = "";//可空不用传
		String pid = merchantNo;// 合作伙伴在新浪支付的用户编号
		String bankId = switchBank(prePaymentParam.getBankCode());
		sign_url.append("inputCharset=").append(inputCharset);
		sign_url.append("&bgUrl=").append(bgUrl);
		sign_url.append("&version=").append(version);
		sign_url.append("&language=").append(language);
		sign_url.append("&signType=").append(signType);
		sign_url.append("&merchantAcctId=").append(merchantAcctId);
		sign_url.append("&orderId=").append(orderId);
		sign_url.append("&orderAmount=").append(orderAmount);
		sign_url.append("&orderTime=").append(orderTime);
		sign_url.append("&productName=").append(productName);
		sign_url.append("&bankId=").append(bankId).append("&pid=").append(pid);

		String signMsg = MD5.getMD5Str(sign_url.toString() + "&key=" + key).toLowerCase();
		log.info("接收参数：" + sign_url.toString());

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("inputCharset", inputCharset);
		map.put("bgUrl", bgUrl);
		map.put("productName", productName);
		map.put("version", version);
		map.put("language", language);
		map.put("signType", signType);
		map.put("merchantAcctId", merchantAcctId);
		map.put("orderId", orderId);
		map.put("orderAmount", orderAmount);
		map.put("orderTime", orderTime);
		map.put("bankId", bankId);
		map.put("pid", pid);
		map.put("signMsg", signMsg);

		PrePaymentResult result = new PrePaymentResult();
		result.setMethod(method);
		result.setParamMap(map);
		result.setUrl(requestUrl);
		return result;
	}

	/**
	 * 通知
	 * 
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public NotifyResult verify(NotifyParam param) {

		NotifyResult result = new NotifyResult();
		result.setResponseStr("<result>1</result>"); // 设置成功接收标识

		StringBuffer sb = new StringBuffer();
		/** 获得参数 **/
		merchantAcctId = param.getParamMap().get("merchantAcctId") + "";
		String version = param.getParamMap().get("version") + "";
		String language = param.getParamMap().get("language") + "";
		String signType = param.getParamMap().get("signType") + "";
		String payType = param.getParamMap().get("payType") + "";// 可为空要判断
		String bankId = param.getParamMap().get("bankId") + "";// 可为空要判断
		String orderId = param.getParamMap().get("orderId") + "";
		String orderTime = param.getParamMap().get("orderTime") + "";// 商户订单提交时间
		String orderAmount = param.getParamMap().get("orderAmount") + "";// 商户订单金额,以分为单位。比方10元，提交时金额应为1000
		String dealId = param.getParamMap().get("dealId") + "";// 新浪支付交易号
		String bankDealId = param.getParamMap().get("bankDealId") + "";// 如果不是通过银行卡支付，则为空
		String dealTime = param.getParamMap().get("dealTime") + "";// 用户在新浪支付支付成功的时间,格式为：年[4位]月[2位]日[2位]时[2位]分[2位]秒[2位]
		String payAmount = param.getParamMap().get("payAmount") + "";// 订单实际支付金额,以分为单位。比方10元，提交时金额应为1000
		String fee = param.getParamMap().get("fee") + "";// 新浪支付收取商户的手续费，单位为分
		String payResult = param.getParamMap().get("payResult") + "";// 10：支付成功,11：支付失败
		String errCode = param.getParamMap().get("errCode") + "";// 失败时返回的错误代码，可以为空。
		String signMsg = param.getParamMap().get("signMsg") + "";// 签名字符串
		String payIp = param.getParamMap().get("payIp") + "";// 支付时 IP

		sb.append("merchantAcctId=").append(merchantAcctId).append("&version=").append(version).append("&language=").append(language)
				.append("&signType=").append(signType);

		if (StringUtils.isNotBlank(payType) && !"null".equals(payType)) {
			sb.append("&payType=").append(payType);
		}
		if (StringUtils.isNotBlank(bankId) && !"null".equals(payIp)) {
			sb.append("&bankId=").append(bankId);
		}

		sb.append("&orderId=").append(orderId).append("&orderTime=").append(orderTime).append("&orderAmount=").append(orderAmount)
				.append("&dealId=").append(dealId);

		if (StringUtils.isNotBlank(bankDealId) && !"null".equals(bankDealId)) {
			sb.append("&bankDealId=").append(bankDealId);
		}
		sb.append("&dealTime=" + dealTime + "&payAmount=" + payAmount + "&fee=" + fee + "&payResult=" + payResult);
		if (StringUtils.isNotBlank(payIp) && !"null".equals(payIp)) {
			sb.append("&payIp=").append(payIp);
		}
		if (StringUtils.isNotBlank(errCode) && !"null".equals(errCode)) {
			sb.append("&errCode=").append(errCode);
		}
		log.info("接收参数：" + sb.toString());
		String product_signMsg = MD5.getMD5Str(sb.toString() + "&key=" + key).toLowerCase();

		if (product_signMsg.equals(signMsg)) {
			log.info("签名验证成功");
			result.setVerify(true);
		} else {
			log.info("签名验证操失败:errCode=" + errCode + "product_signMsg=" + product_signMsg);
			result.setVerify(false);
		}

		result.setPayAmount(BigDecimal.valueOf(Double.valueOf(payAmount)).multiply(BigDecimal.valueOf(0.01)));
		result.setBankOrderNo(orderId);
		result.setBankTrxNo(dealId);// 新浪支付交易号
		result.setBankStatus(payResult);
		try {
			result.setBankSuccessTime(DateUtils.parseDate(dealTime, "yyyyMMddhhMMss"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if ("10".equals(payResult)) {// 交易成功
			result.setStatus(BankTradeStatusEnum.SUCCESS);
		} else if ("11".equals(payResult)) {
			result.setStatus(BankTradeStatusEnum.FAILED);
		} else {
			result.setStatus(BankTradeStatusEnum.UNKNOWN);
		}
		log.info("交易状态:" + payResult + " 银行订单号：" + orderId);
		return result;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getReturnURL() {
		return returnURL;
	}

	public void setReturnURL(String returnURL) {
		this.returnURL = returnURL;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getQueryUrl() {
		return queryUrl;
	}

	public void setQueryUrl(String queryUrl) {
		this.queryUrl = queryUrl;
	}

	public String getPublicCertPath() {
		return publicCertPath;
	}

	public void setPublicCertPath(String publicCertPath) {
		this.publicCertPath = publicCertPath;
	}

	public String getPrivateCertPath() {
		return privateCertPath;
	}

	public void setPrivateCertPath(String privateCertPath) {
		this.privateCertPath = privateCertPath;
	}

	public String getRefundUrl() {
		return refundUrl;
	}

	public void setRefundUrl(String refundUrl) {
		this.refundUrl = refundUrl;
	}

	public String getPrivatePassword() {
		return privatePassword;
	}

	public void setPrivatePassword(String privatePassword) {
		this.privatePassword = privatePassword;
	}

	public String getSellerEmail() {
		return sellerEmail;
	}

	public void setSellerEmail(String sellerEmail) {
		this.sellerEmail = sellerEmail;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getInputCharset() {
		return inputCharset;
	}

	public void setInputCharset(String inputCharset) {
		this.inputCharset = inputCharset;
	}

	public String getMerchantAcctId() {
		return merchantAcctId;
	}

	public void setMerchantAcctId(String merchantAcctId) {
		this.merchantAcctId = merchantAcctId;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}
	
	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	@Override
	public RefundNotifyResult refundVerify(NotifyParam param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/*****************************银行id选择，用于直连跳转到具体银行网管***********************************/
	private  String switchBank(String bankCode){
		String bankId = "TESTBANK";
		if(!StringUtils.isBlank(bankCode)){
			if(BankCode.ICBC.toString().equals(bankCode)){ //工商银行
				bankId = "ICBC";
			}else if(BankCode.CMBCHINA.toString().equals(bankCode)){ //招商银行
				bankId = "CMB";
			}else if(BankCode.ABC.toString().equals(bankCode)){ //中国农业银行
				bankId = "ABC";
			}else if(BankCode.CCB.toString().equals(bankCode)){ //建设银行
				bankId = "CCB";
			}else if(BankCode.BCCB.toString().equals(bankCode)){ //北京银行
				bankId = "BCCB";
			}else if(BankCode.BOCO.toString().equals(bankCode)){ //交通银行
				bankId = "COMM";
			}else if(BankCode.CIB.toString().equals(bankCode)){ //兴业银行
				bankId = "CIB";
			}else if(BankCode.NJCB.toString().equals(bankCode)){ //南京银行
				bankId = "NJCB";
			}else if(BankCode.CMBC.toString().equals(bankCode)){ //中国民生银行
				bankId = "CMBC";
			}else if(BankCode.CEB.toString().equals(bankCode)){ //光大银行
				bankId = "CEB";
			}else if(BankCode.BOC.toString().equals(bankCode)){ //中国银行
				bankId = "BOC";
			}else if(BankCode.PINGANBANK.toString().equals(bankCode)){ //平安银行
				bankId = "SZPAB";
			}else if(BankCode.CBHB.toString().equals(bankCode)){ //渤海银行
				bankId = "CBHB";
			}else if(BankCode.NBCB.toString().equals(bankCode)){ //宁波银行
				bankId = "NBCB";
			}else if(BankCode.ECITIC.toString().equals(bankCode)){ //中信银行
				bankId = "CITIC";
			}else if(BankCode.CGB.toString().equals(bankCode)){ //广发银行
				bankId = "GDB";
			}else if(BankCode.SHB.toString().equals(bankCode)){ //上海银行
				bankId = "BOS";
			}else if(BankCode.SPDB.toString().equals(bankCode)){ //上海浦东发展银行
				bankId = "SPDB";
			}else if(BankCode.POST.toString().equals(bankCode)){ //中国邮政
				bankId = "PSBC";
			}else if(BankCode.BJRCB.toString().equals(bankCode)){ //北京农村商业银行
				bankId = "BJRCB";
			}else if(BankCode.HXB.toString().equals(bankCode)){ //华夏银行
				bankId = "HXB";
			}else if(BankCode.CZ.toString().equals(bankCode)){ //浙商银行
				bankId = "CZB";
			}else if(BankCode.HZBANK.toString().equals(bankCode)){ //杭州银行
				bankId = "HCCB";
			}
		}
		return bankId;
	}

	public static void main(String[] args) {
		System.out.println(AmountUtil.roundDown(Double.valueOf("12398")));
		System.out.println(BigDecimal.valueOf(Double.valueOf("12398")).multiply(BigDecimal.valueOf(0.01d)));
		DecimalFormat df = new DecimalFormat("#.00");
		
		System.out.println(AmountUtil.roundDown(AmountUtil.mul(Double.valueOf("12398"),0.01d)));
		
		System.out.println(BigDecimal.valueOf(Double.valueOf(AmountUtil.roundDown(AmountUtil.mul(Double.valueOf("12399"),0.01d)))));
	}
	
}

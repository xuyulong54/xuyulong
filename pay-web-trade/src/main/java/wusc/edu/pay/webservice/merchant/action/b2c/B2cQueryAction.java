package wusc.edu.pay.webservice.merchant.action.b2c;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.webservice.merchant.base.BaseAction;
import wusc.edu.pay.webservice.merchant.exception.WebTradeBizException;
import wusc.edu.pay.webservice.merchant.utils.Context;
import wusc.edu.pay.webservice.merchant.utils.ValidateParam;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

public class B2cQueryAction extends BaseAction {

	private static final long serialVersionUID = 1036856342445456900L;
	private static final Log logger = LogFactory.getLog(B2cQueryAction.class);

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;

	/**
	 * 支付订单查询
	 * 
	 * @throws JSONException
	 */
	public void queryOrder() throws JSONException {

		String p1_MerchantNo = super.getString_UrlDecode_UTF8("p1_MerchantNo");// 商户编号
		String p2_OrderNo = super.getString_UrlDecode_UTF8("p2_OrderNo");// 订单号

		// 验证参数
		if (!this.valideParam()) {
			return;
		}

		// 验证签名
		if (!this.valideSign()) {
			return;
		}

		Map<String, Object> returnMap = new HashMap<String, Object>();
		try {
			PaymentOrder paymentOrder = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(p1_MerchantNo, p2_OrderNo, null);

			if (paymentOrder == null) {
				returnMap.put("r1_MerchantNo", p1_MerchantNo);
				returnMap.put("r2_OrderNo", p2_OrderNo);
				returnMap.put("r3_Amount", "");
				returnMap.put("r4_ProductName", "");
				returnMap.put("r5_TrxNo", "");
				returnMap.put("ra_Status", "");
				returnMap.put("rb_Code", WebTradeBizException.MERCHANTAPI_ORDER_IS_NULL.getCode());
				returnMap.put("rc_CodeMsg", WebTradeBizException.MERCHANTAPI_ORDER_IS_NULL.getMsg());
				returnMap.put("hmac", this.signData(p1_MerchantNo, returnMap));
			} else {
				returnMap.put("r1_MerchantNo", p1_MerchantNo);
				returnMap.put("r2_OrderNo", p2_OrderNo);
				returnMap.put("r3_Amount", paymentOrder.getOrderAmount());
				returnMap.put("r4_ProductName", paymentOrder.getProductName());
				returnMap.put("r5_TrxNo", paymentOrder.getSuccessTrxNo() == null ? "" : paymentOrder.getSuccessTrxNo());
				returnMap.put("ra_Status", paymentOrder.getStatus());
				returnMap.put("rb_Code", Context.SUCCESS);
				returnMap.put("rc_CodeMsg", "");
				returnMap.put("hmac", this.signData(p1_MerchantNo, returnMap));
			}
		} catch (BizException e) {
			returnMap.put("r1_MerchantNo", p1_MerchantNo);
			returnMap.put("r2_OrderNo", p2_OrderNo);
			returnMap.put("r3_Amount", "");
			returnMap.put("r4_ProductName", "");
			returnMap.put("r5_TrxNo", "");
			returnMap.put("ra_Status", "");
			returnMap.put("rb_Code", e.getCode());
			returnMap.put("rc_CodeMsg", e.getMsg());
			returnMap.put("hmac", this.signData(p1_MerchantNo, returnMap));
		}

		this.write(JSONObject.toJSONString(returnMap));
	}

	/**
	 * 验证签名
	 * 
	 * @param paramMap
	 * @return
	 * @throws JSONException
	 */
	private boolean valideSign() throws JSONException {
		String p1_MerchantNo = super.getString_UrlDecode_UTF8("p1_MerchantNo");// 商户编号
		String p2_OrderNo = super.getString_UrlDecode_UTF8("p2_OrderNo");// 订单号
		String hmac = super.getString_UrlDecode_UTF8("hmac");// 签名数据
		String sign = "";
		try {
			sign = super.signData(p1_MerchantNo, super.getParamMap_Utf8());
		} catch (BizException e) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("r1_MerchantNo", p1_MerchantNo);
			returnMap.put("r2_OrderNo", p2_OrderNo);
			returnMap.put("r3_Amount", "");
			returnMap.put("r4_ProductName", "");
			returnMap.put("r5_TrxNo", "");
			returnMap.put("ra_Status", "");
			returnMap.put("rb_Code", e.getCode());
			returnMap.put("rc_CodeMsg", e.getMsg());
			logger.error("退款接口发起退款签名异常：" + e.getMessage());
			if (e.getCode() == WebTradeBizException.MERCHANT_IS_NOT_EXIST.getCode()) {
				returnMap.put("hmac", "");
				this.write(JSONObject.toJSONString(returnMap));
				return false;
			} else {
				returnMap.put("hmac", sign);
				this.write(JSONObject.toJSONString(returnMap));
				return false;
			}
		}

		if (!sign.toUpperCase().equals(hmac.toUpperCase())) {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			returnMap.put("r1_MerchantNo", p1_MerchantNo);
			returnMap.put("r2_OrderNo", p2_OrderNo);
			returnMap.put("r3_Amount", "");
			returnMap.put("r4_ProductName", "");
			returnMap.put("r5_TrxNo", "");
			returnMap.put("ra_Status", "");
			returnMap.put("rb_Code", WebTradeBizException.MERCHANTAPI_VERIFYHMAC_LOSE.getCode());
			returnMap.put("rc_CodeMsg", WebTradeBizException.MERCHANTAPI_VERIFYHMAC_LOSE.getMessage());
			returnMap.put("hmac", this.signData(p1_MerchantNo, returnMap));
			this.write(JSONObject.toJSONString(returnMap));
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 校验请求数据
	 * 
	 * @param paramMap
	 * @return
	 * @throws JSONException
	 */
	private boolean valideParam() throws JSONException {

		String p1_MerchantNo = super.getString_UrlDecode_UTF8("p1_MerchantNo");// 商户编号
		String p2_OrderNo = super.getString_UrlDecode_UTF8("p2_OrderNo");// 订单号
		String hmac = super.getString_UrlDecode_UTF8("hmac");

		Map<String, Object> returnMap = new HashMap<String, Object>();
		boolean flag = false;
		WebTradeBizException webTradeBizException = null;
		webTradeBizException = ValidateParam.validateString(webTradeBizException, "p1_MerchantNo", p1_MerchantNo, 15, false);
		webTradeBizException = ValidateParam.validateString(webTradeBizException, "p2_OrderNo", p2_OrderNo, 30, false);
		webTradeBizException = ValidateParam.validateString(webTradeBizException, "hmac", hmac, 100, false);
		if (webTradeBizException == null) {
			flag = true;
		}

		if (!flag) {
			returnMap.put("r1_MerchantNo", p1_MerchantNo);
			returnMap.put("r2_OrderNo", p2_OrderNo);
			returnMap.put("r3_Amount", "");
			returnMap.put("r4_ProductName", "");
			returnMap.put("r5_TrxNo", "");
			returnMap.put("ra_Status", "");
			returnMap.put("rb_Code", webTradeBizException.getCode());
			returnMap.put("rc_CodeMsg", webTradeBizException.getMsg());
			returnMap.put("hmac", "");
			String sign = "";
			try {
				sign = super.signData(p1_MerchantNo, returnMap);
				returnMap.put("hmac", sign);
			} catch (BizException e) {
				Map<String, Object> returnMaps = new HashMap<String, Object>();
				returnMap.put("r1_MerchantNo", p1_MerchantNo);
				returnMap.put("r2_OrderNo", p2_OrderNo);
				returnMap.put("r3_Amount", "");
				returnMap.put("r4_ProductName", "");
				returnMap.put("r5_TrxNo", "");
				returnMap.put("ra_Status", "");
				returnMap.put("rb_Code", e.getCode());
				returnMap.put("rc_CodeMsg", e.getMsg());
				if (e.getCode() == WebTradeBizException.MERCHANT_IS_NOT_EXIST.getCode()) {
					returnMaps.put("hmac", "");
				} else {
					returnMaps.put("hmac", sign);
				}
				logger.error("退款接口发起退款签名异常：" + e.getMessage());
			}
			this.write(JSONObject.toJSONString(returnMap));
		}

		return flag;
	}

}

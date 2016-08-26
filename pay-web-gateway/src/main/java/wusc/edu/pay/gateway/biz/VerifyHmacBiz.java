package wusc.edu.pay.gateway.biz;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.gateway.exceptions.GateWayException;


@Component("verifyHmacBiz")
public class VerifyHmacBiz {

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	private static final Log log = LogFactory.getLog(VerifyHmacBiz.class);

	/**
	 * 验证支付签名
	 * 
	 * @param vo
	 * @throws GateWayException
	 */
	public void verifyNetPay(PaymentOrderVo vo, String hmac) throws GateWayException {

		MerchantOnline merchant = merchantOnlineFacade.getMerchantOnlineByMerchantNo(vo.getMerchantNo());
		if (merchant == null) {
			throw new GateWayException(GateWayException.GATEWAY_MERCHANT_NOT_EXIST, "商户编号%s, 商户不存在.", vo.getMerchantNo());
		}

		if (!merchant.getStatus().equals(UserStatusEnum.ACTIVE.getValue())) {
			throw new GateWayException(GateWayException.GATEWAY_MERCHANT_STATUS_IS_INACTIVE, "商户未激活.");
		}

		vo.setMerchantNo(merchant.getMerchantNo());
		vo.setMerchantName(merchant.getFullName());

		Map<String, String> map = new HashMap<String, String>();
		map.put("p1_MerchantNo", vo.getMerchantNo());
		map.put("p2_OrderNo", vo.getMerchantOrderNo());
		map.put("p3_Amount", vo.getOrderAmount());
		map.put("p4_Cur", vo.getCur().toString());
		map.put("p5_ProductName", vo.getProductName());
		map.put("p6_Mp", vo.getCallbackPara());
		map.put("p7_ReturnUrl", vo.getReturnUrl());
		map.put("p8_NotifyUrl", vo.getNotifyUrl());
		map.put("p9_FrpCode", vo.getPayWayCode());
		map.put("pa_OrderPeriod", vo.getOrderPeriod() == null ? "" : vo.getOrderPeriod().toString());
		map.put("pb_PayerLoginName", vo.getPayerLoginName());
		map.put("hmac", hmac);

		String sign = this.signData(merchant, map);

		if (!sign.toUpperCase().equals(hmac.toUpperCase())) {
			throw new GateWayException(GateWayException.GATEWAY_VALIDATE_HMAC_IS_FAILED, "验证签名失败.");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String signData(MerchantOnline merchant, Map<String, String> map) throws GateWayException {

		Properties properties = new Properties();
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String value = map.get(name) == null ? "" : map.get(name).toString();
			// if (StringUtils.isBlank(name) || StringUtils.isBlank(value) ||
			// StringUtils.equalsIgnoreCase("hmac", name)) {
			// continue;
			// }
			if (StringUtils.equalsIgnoreCase("hmac", name)) {
				continue;
			}
			properties.setProperty(name, StringUtils.trim(value));
		}

		StringBuffer content = new StringBuffer();
		List keys = new ArrayList(properties.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = properties.getProperty(key);
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}
		log.info("============签名字符串"+content.toString());
		return DigestUtils.md5Hex(content.toString() + merchant.getMerchantKey()).toUpperCase();
	}
}

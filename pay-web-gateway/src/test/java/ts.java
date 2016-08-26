//import java.io.UnsupportedEncodingException;
//import java.net.URLEncoder;
//
//import wusc.edu.pay.common.utils.DigestUtil;

//public class ts {

	/*public static void main(String[] args) throws UnsupportedEncodingException {
		String p1_MerchantNo = "1210000016"; // 得到商户编号
		String p2_OrderNo = String.valueOf(System.currentTimeMillis()); // 得到商户订单号
		String p3_Amount = "abc"; // 得到订单金额
		String p4_Cur = "CNY"; // 交易币种（固定值 CNY）数据库中保存的是int类型。
		String p5_ProductName = "jipaio"; // 商品名称
		String p6_Mp = ""; // 回调消息
		String p7_ReturnUrl = "http://www.gzzyzz.com/back.aciton"; // 返回url
		String p8_NotifyUrl = "http://www.gzzyzz.com/back.aciton"; // 消息通知Url
		String p9_FrpCode = ""; // 银行编码

		StringBuffer signStr = new StringBuffer();
		signStr.append(p1_MerchantNo);
		signStr.append(p2_OrderNo);
		signStr.append(p3_Amount);
		signStr.append(p4_Cur);
		signStr.append(p5_ProductName);
		signStr.append(p6_Mp);
		signStr.append(p7_ReturnUrl);
		signStr.append(p8_NotifyUrl);
		signStr.append(p9_FrpCode);

		String hmac = DigestUtil.hmacSign(signStr.toString(), "e9e24fb30f2f4a66ab6c5e77c4bf80fe");

		String url = "http://127.0.0.1:8080/pay-web-gateway/gateway_init.action";
		url += "?p1_MerchantNo=" + p1_MerchantNo;
		url += "&p2_OrderNo=" + p2_OrderNo;
		url += "&p3_Amount=" + p3_Amount;
		url += "&p4_Cur=" + p4_Cur;
		url += "&p5_ProductName=" + URLEncoder.encode(p5_ProductName, "utf-8");
		url += "&p6_Mp=" + p6_Mp;
		url += "&p7_ReturnUrl=" + URLEncoder.encode(p7_ReturnUrl, "utf-8");
		url += "&p8_NotifyUrl=" + URLEncoder.encode(p8_NotifyUrl, "utf-8");
		url += "&p9_FrpCode=" + p9_FrpCode;
		url += "&hmac=" + hmac;

		System.out.println(url);
	}*/
//}

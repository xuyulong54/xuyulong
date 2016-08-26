<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedHashMap"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.lang.*"%>
<%@ page import="wusc.edu.pay.api.merchant.Pay"%>
<%@ page import="wusc.edu.pay.api.merchant.utils.Context"%>
<%@ page import="wusc.edu.pay.common.utils.number.AmountUtil"%>
<%@ page import="wusc.edu.pay.common.config.PublicConfig"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付网关接口</title>
</head>
<%
	String merchantNo = "888000000000000";
	String merchantKey = "1234qwer";

	String p2_OrderNo = new String(request.getParameter("p2_OrderNo").getBytes("ISO-8859-1"), "UTF-8");
	String amount = new String(request.getParameter("amount").getBytes("ISO-8859-1"), "UTF-8");
	String price = new String(request.getParameter("price").getBytes("ISO-8859-1"), "UTF-8");

	double amount1 = Double.valueOf(amount);
	double price1 = Double.valueOf(price);
	double price2 = AmountUtil.mul(amount1, price1);
	double amount2 = AmountUtil.div(price2, 1, 2);
	String amountStr = amount2 + "";
	String p3_Amount = new String(amountStr.getBytes("ISO-8859-1"), "UTF-8");

	String p4_Cur = "1";//1表示人民币
	String p5_ProductName = new String(request.getParameter("p5_ProductName").getBytes("ISO-8859-1"), "UTF-8");
	String p6_Mp = new String(request.getParameter("p6_Mp").getBytes("ISO-8859-1"), "UTF-8");
	String p7_ReturnUrl = Context.RETURN_URL;
	String p8_NotifyUrl = Context.NOTIFY_URL;
	String p9_FrpCode = "";
	String pa_OrderPeriod ="0";
	String pb_PayerLoginName ="";
	
	String pay_url = PublicConfig.B2CPAY_URL;

	//把请求参数打包成数组
	Map<String, String> map = new LinkedHashMap<String, String>();
	map.put("p1_MerchantNo", merchantNo);
	map.put("p2_OrderNo", p2_OrderNo);
	map.put("p3_Amount", p3_Amount);
	map.put("p4_Cur", p4_Cur);
	map.put("p5_ProductName", p5_ProductName);
	map.put("p6_Mp", p6_Mp);
	map.put("p7_ReturnUrl", p7_ReturnUrl);
	map.put("p8_NotifyUrl", p8_NotifyUrl);
	map.put("p9_FrpCode", p9_FrpCode);
	map.put("pa_OrderPeriod", pa_OrderPeriod);
	map.put("pb_PayerLoginName", pb_PayerLoginName);
	
	map.put("pay_url", pay_url);

	// 创建支付请求html
	String html = new Pay(merchantNo, merchantKey).buildPayUrlPost(map);
	out.println(html);
%>
<script type="text/javascript">
	window.toPay.submit();
</script>
<body>
</body>
</html>

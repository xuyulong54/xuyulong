<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.lang.*"%>
<%@ page import="wusc.edu.pay.api.merchant.Pay"%>
<%@ page import="wusc.edu.pay.api.merchant.utils.Context"%>
<%@ page import="wusc.edu.pay.common.utils.number.AmountUtil"%>
<%@ page import="wusc.edu.pay.common.config.PublicConfig"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>在线支付</title> 
</head>
<%
	String p1_MerchantNo = new String(request.getParameter("p1_MerchantNo").getBytes("ISO-8859-1"), "UTF-8");
	String p2_OrderNo = new String(request.getParameter("p2_OrderNo").getBytes("ISO-8859-1"), "UTF-8");
	String p3_Amount = new String(request.getParameter("p3_Amount").getBytes("ISO-8859-1"), "UTF-8");
	String p4_Cur = new String(request.getParameter("p4_Cur").getBytes("ISO-8859-1"), "UTF-8");
	String p5_ProductName = new String(request.getParameter("p5_ProductName").getBytes("ISO-8859-1"), "UTF-8");
	String p6_Mp = new String(request.getParameter("p6_Mp").getBytes("ISO-8859-1"), "UTF-8");
	String p7_ReturnUrl = new String(request.getParameter("p7_ReturnUrl").getBytes("ISO-8859-1"), "UTF-8");
	String p8_NotifyUrl = new String(request.getParameter("p8_NotifyUrl").getBytes("ISO-8859-1"), "UTF-8");
	String p9_FrpCode = new String(request.getParameter("p9_FrpCode").getBytes("ISO-8859-1"), "UTF-8");
	String pa_OrderPeriod ="0";
	String pb_PayerLoginName ="";
	
	String key = new String(request.getParameter("key").getBytes("ISO-8859-1"), "UTF-8");

	//把请求参数打包成数组
	Map<String, String> map = new HashMap<String, String>();
	map.put("p1_MerchantNo", p1_MerchantNo);
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
	
	String pay_url = PublicConfig.B2CPAY_URL;
	map.put("pay_url", pay_url);

	// 创建支付请求html  
	String html = new Pay(p1_MerchantNo, key).buildPayUrlPost(map);
	out.println(html);
 %>
 <body>
</body>
</html>
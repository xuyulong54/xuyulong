<%@page import="wusc.edu.pay.api.merchant.OrderQuery"%>
<%@page import="java.util.HashMap"%>
<%@ page contentType="text/html; charset=utf-8" language="java" errorPage="" %>
<%@ page import="java.util.Map"%>
<%@ page import="java.lang.*"%>
<%@ page import="wusc.edu.pay.api.merchant.OrderQuery"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单查询</title>
</head>
<body>
<%
	String p1_MerchantNo = new String(request.getParameter("p1_MerchantNo").getBytes("ISO-8859-1"), "UTF-8");
	String p2_OrderNo = new String(request.getParameter("p2_OrderNo").getBytes("ISO-8859-1"), "UTF-8");
	
	String key = new String(request.getParameter("key").getBytes("ISO-8859-1"), "UTF-8");

	//把请求参数打包成数组
	Map<String, String> map = new HashMap<String, String>();
	map.put("p1_MerchantNo", p1_MerchantNo);
	map.put("p2_OrderNo", p2_OrderNo);

	// 创建支付请求html
	String html = new OrderQuery(p1_MerchantNo, key).doQuery(map);
	out.println(html);
 %>
</body>
</html>
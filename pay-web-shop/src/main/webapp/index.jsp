<%@ page contentType="text/html; charset=utf-8" language="java" errorPage=""%>
<%@ page import="wusc.edu.pay.api.merchant.utils.Context"%>
<%@ page import="wusc.edu.pay.common.config.PublicConfig"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>模拟商城</title>
</head>
<body>
	<h2>在线支付</h2>
	<form id="form1" name="form1" method="post" action="pay.jsp" target="_blank">
		<table>
			<tr>
				<td align="right">商户编号：</td>
				<td align="left"><input name="p1_MerchantNo" type="text" value="888000000000000" size="30" /></td>
			</tr>
			<tr>
				<td align="right">商户密钥：</td>
				<td align="left"><input name="key" type="text" value="1234qwer" size="50" /></td>
			</tr>
			<tr>
				<td align="right">订单号：</td>
				<td align="left"><input name="p2_OrderNo" type="text" size="30" value="<%=System.currentTimeMillis()%>" /></td>
			</tr>
			<tr>
				<td align="right">订单金额：</td>
				<td align="left"><input name="p3_Amount" type="text" value="28.8" size="30" /></td>
			</tr>
			<tr>
				<td align="right">币种：</td>
				<td align="left"><input name="p4_Cur" type="text" value="1" size="30" /></td>
			</tr>
			<tr>
				<td align="right">商品名称：</td>
				<td align="left"><input name="p5_ProductName" type="text" value="Dubbo视频教程" size="30" /></td>
			</tr>
			<tr>
				<td align="right">原样返回：</td>
				<td align="left"><input name="p6_Mp" type="text" size="30" /></td>
			</tr>
			<tr>
				<td align="right">页面返回URL：</td>
				<td align="left"><input name="p7_ReturnUrl" type="text" value="<%=Context.RETURN_URL%>" size="50" /></td>
			</tr>
			<tr>
				<td align="right">后台通知URL：</td>
				<td align="left"><input name="p8_NotifyUrl" type="text" value="<%=Context.NOTIFY_URL%>" size="50" /></td>
			</tr>
			<tr>
				<td align="right">银行编码：</td>
				<td align="left"><input name="p9_FrpCode" type="text" size="30" /></td>
			</tr>
			<tr>
				<td align="right">订单有效期：</td>
				<td align="left"><input name="pa_OrderPeriod" value="30" type="text" size="30" /></td>
			</tr>
			<tr>
				<td align="right">付款人平台登录账号：</td>
				<td align="left"><input name="pb_PayerLoginName" type="text" size="30" /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td align="left"><input type="submit" name="Submit" value="提交" /></td>
			</tr>
		</table>
		
	</form>
	
 	<br />
 	<br />
 	
	<h2>订单查询</h2>
	<form id="form2" name="form2" method="post" action="orderQuery.jsp" target="_blank">
		<table>
			<tr>
				<td align="right">商户编号：</td>
				<td align="left"><input name="p1_MerchantNo" type="text" value="888000000000000" size="30" /></td>
			</tr>
			<tr>
				<td align="right">商户密钥：</td>
				<td align="left"><input name="key" type="text" value="1234qwer" size="45" /></td>
			</tr>
			<tr>
				<td align="right">商户订单号：</td>
				<td align="left"><input name="p2_OrderNo" type="text" size="30" value="" /></td>
			</tr>
			<tr>
				<td align="right"></td>
				<td align="left"><input type="submit" name="Submit" value="提交" /></td>
			</tr>
		</table>
	</form>

</body>
</html>
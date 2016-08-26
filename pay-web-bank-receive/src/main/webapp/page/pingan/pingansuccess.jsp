<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<<jsp:include page="header.jsp"></jsp:include>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>支付成功</title>
    
	<link href="<%=basePath%>/static/css/global.css" type="text/css" rel="stylesheet" />
	<link href="<%=basePath%>/static/css/style.css" type="text/css" rel="stylesheet" />
  </head>
  <body>
    <h1>
  	订单支付成功 
  	</h1>
  	<div>
  		<table>
  			<tr>
  				<td>
  					订单号
  				</td>
  				<td>
  					${requestScope.OrderNo } 
  				</td>
  			</tr>
  			<tr>
  				<td>
  					金额
  				</td>
  				<td>
  					${requestScope.PayAmount }
  				</td>
  			</tr>
  		</table>
  	</div>
  </body>
</html>

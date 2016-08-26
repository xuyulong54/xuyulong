<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/page/inc/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta name="keywords" />
<meta name="description" />
<title>支付成功提示</title>
<!–[if lte IE]>
<![endif]–>
<%@include file="/page/inc/headScript.jsp"%>
<link rel="Stylesheet" href="statics/css/GatewayExceptionError.css" />

<script type="text/javascript">
	function countDown(secs, surl) {
		//alert(surl);     
		var jumpTo = document.getElementById('jumpTo');
		jumpTo.innerHTML = secs;
		if (--secs > 0) {
			setTimeout("countDown(" + secs + ",'" + surl + "')", 1000);
		} else {
			location.href = surl;
		}
	}
</script>

</head>
<body>
	<%@include file="/page/inc/head_msg.jsp"%>
	<div class="PayState">
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">支付成功！感谢您使用${COMPANY_FOR }支付平台</li>
				<li class="linkTitle" id="backMerchant"><span id="jumpTo">5</span>秒自动跳转返回<a href="${backToMerchantUrl}">商户页面</a> 
				<script type="text/javascript">countDown(5, '${backToMerchantUrl}');</script>
				</li>
			</ul>
		</div>

		<div class="title">商品信息</div>
		<div class="boxProduct">

			<div style="font-size: 14px">
				<ul>
					<li>商户名称： ${merchantName}</li>
					<li>商品名称： ${productName}</li>
					<li>支付金额： <span><fmt:formatNumber value="${payAmount}" pattern="#0.00"></fmt:formatNumber> </span> 元</li>
				</ul>
			</div>
		</div>

		<div class="title">订单信息</div>
		<table class="form" style=" float:left; width:900px;">
			<tr class="form_head">
				<td>商户订单号</td>
				<td>交易流水号</td>
				<td>银行订单号</td>
				<td>创建时间</td>
				<td>支付时间</td>
			</tr>
			<tr>
				<td>${orderNo}</td>
				<td>${trxNo}</td>
				<td>${bankOrderNo}</td>
				<td><s:date name="createTime" format="yyyy/MM/dd HH:mm:ss" />
				</td>
				<td><s:date name="paymentTime" format="yyyy/MM/dd HH:mm:ss" />
				</td>
			</tr>
		</table>
	</div>


	<div class="ht1"></div>

	<jsp:include page="/page/inc/foot.jsp" />

</body>
</html>

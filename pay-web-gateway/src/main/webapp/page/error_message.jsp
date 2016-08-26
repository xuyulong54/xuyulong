<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/page/inc/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>异常信息页面</title>

<%@include file="/page/inc/headScript.jsp"%>
<link rel="Stylesheet" href="<%=path%>/statics/css/GatewayExceptionError.css" />
<link rel="Stylesheet" href="<%=path%>/statics/iconfots/iconfont.css" />
</head>

<body>

	<div id="FakeCryptoAgent"></div>
	<div class='warmWrap' id="tips">
		<img src="<%=path%>/statics/images/closeImg.gif" class='closeImg' onclick="closeTips()" />
		<p class='warminfos'>${COMPANY_FOR }支付平台提醒您：在购买商品和支付时，请注意核对网址，收款商户、商品名称、支付金额等信息，警惕各种形式的网络欺骗行为！</p>
	</div>
	<%@include file="/page/head.jsp"%>
	<div class="container">
		<div class="bd-container">

			<div class="tipFalse">
				<div class="leftbox">
					<i class="iconfont">&#xe609;</i>

				</div>
				<div class="rightbox">
					<h3>${errMsg }!</h3>
					<p class="less">订单可能已撤销或已完成支付，请核对后再试试</p>
				</div>
				<div class="clear"></div>
			</div>

		</div>
		<jsp:include page="foot.jsp" />
</body>
</html>
<
<script>
	function closeTips() {
		//不要动这个function代码  by shenjialong   
		var tips = $("#tips");
		tips.attr("style", "display: none");
	}
</script>

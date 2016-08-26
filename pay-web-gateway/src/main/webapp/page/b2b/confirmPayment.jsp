<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/page/inc/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>会员订单确认支付页</title>

<%@include file="/page/inc/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/js/Popup.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/b2bgateway.js"></script>
<%-- <script type="text/javascript" src="<%=path%>/statics/js/password.js"></script> --%>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
</head>

<c:choose>
	<c:when test="${USE_SECURITYCENTER }">
		<body onload="getCurrentUserCA('${CURRENT_SN_LIST}')">
	</c:when>
	<c:otherwise>
		<body>
	</c:otherwise>
</c:choose>

<div id="FakeCryptoAgent"></div>
<!-- 证书签名信息-->
<p class="clearfix">
	<!-- 签名原文 -->
	<input type="hidden" id="textareaSignedContent" />
	<!-- 签名结果 -->
	<input type="hidden" name="textareaSignature" id="textareaSignature" />
</p>
<div class='warmWrap' id='tips'>
	<img src="<%=path%>/statics/images/closeImg.gif" class='closeImg' />
	<p class='warminfos' >${COMPANY_FOR }支付平台提醒您：在购买商品和支付时，请注意核对网址，收款商户、商品名称、支付金额等信息，警惕各种形式的网络欺骗行为！</p>
</div>
<%@ include file="/page/head.jsp"%>
<div class="containerGagteway auto">


	<div class="pageContent">
		<form action="b2bbalancePay_balancePay.action" id="infoForm"
			class="form" method="post">
			<input type="hidden" id="orderNo" name="orderNo"
				value="${PaymentOrderVo.merchantOrderNo}" /> <input type="hidden"
				id="merchantNo" name="merchantNo"
				value="${PaymentOrderVo.merchantNo}" /> <input type="hidden"
				id="amount" name="amount" value="${PaymentOrderVo.orderAmount}" />
			<input type="hidden" id=payTime name="payTime" value="${payTime}" />
			<input type="hidden" id="payProductCode" name="payProductCode"
				value="${payProductCode}" />
			<!--订单信息-->

			<div class="showOrder">
				<div>
					<table class="tb" cellpadding="0" cellspacing="0">
						<tr>
							<th>商户订单号</th>
							<th>商家名称</th>
							<th>商品名称</th>
							<th>订单金额(元)</th>
							<th>下单时间</th>
						</tr>
						<tr>
							<td>${PaymentOrderVo.merchantOrderNo}</td>
							<td>${PaymentOrderVo.merchantName}</td>
							<td>${PaymentOrderVo.productName}</td>
							<td class="numRed"><fmt:formatNumber
									value="${PaymentOrderVo.orderAmount}" pattern="#0.00"></fmt:formatNumber>
							</td>
							<td>
								<%
									java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
											"yyyy/MM/dd HH:mm:ss");
									java.util.Date currentTime_1 = new java.util.Date();
									out.print(formatter.format(currentTime_1));
								%>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!--订单信息  end-->


			<div class="orderContent">
				<ul class="block" style="margin-top: 20px;">

					<li>
						<div class="orderInfo">
							<div class="ada-tips">
								温馨提示：使用会员余额支付，直接扣减您${COMPANY_FOR }账户的余额，即可完成支付&nbsp&nbsp&nbsp&nbsp<a
									href="b2bbalancePay_changeBalanceAccount.action">使用其他账户支付</a>
							</div>
							</br>
							<div class="infobox">
								<table class="rest" cellpadding="0" cellspacing="0"
									style="width:90%; margin: 10px auto;">

									<tr>
										<th class="left_td">支付账户：</th>
										<td class="mid_td">${sessionScope.currentUser.loginName}</td>

										<th class="left_td">订单金额：</th>
										<td class="mid_td"><span><fmt:formatNumber
													value="${sessionScope.PaymentOrderVo.orderAmount}"
													pattern="#0.00"></fmt:formatNumber> </span>&nbsp;元</td>


										<th class="left_td">账户余额：</th>
										<td class="mid_td"><span><fmt:formatNumber
													value="${balance}" pattern="#0.00"></fmt:formatNumber> </span>&nbsp;元</td>
									</tr>
								</table>
							</div>
							<!-- 余额不足 -->
							<c:if test="${balance<sessionScope.PaymentOrderVo.orderAmount}">

								<ul>
									<li><img src="<%=path%>/statics/images/tips.png"
										style="float:left;"></li>
									<li><span class="markRed flLt">您当前的账户余额不足，请登录门户后台充值后再支付
											&nbsp&nbsp您还可以通过选择 <a href="javascript:history.go(-1);">网银支付</a>完成支付
											，有卡就能付。</span> <!--class="REbtn  ml5"  --></li>
								</ul>
							</c:if>
							<c:if test="${balance>=sessionScope.PaymentOrderVo.orderAmount}">
								<table class="rest" cellpadding="0" cellspacing="0"
									style="margin: 20px 0px 0px 46px;">
									<tr>
										<th class="left_td">支付密码：</th>
										<td><c:choose>
												<c:when test="${USE_KEYBOARD }">
													<input type="hidden" name="tradePwd" id="password"
														class="password" maxlength="20" />
													<script type="text/javascript">
															writePassObject("powerpass", {
																"width" : 173,
																"height" : 35,
																" margin-top" : 10,
																"accepts" : "[:graph:]+",
																"x" : -50,
																"maxlength" : 20
															});
														</script>
												</c:when>
												<c:otherwise>
													<input type="password" name="tradePwd" id="password"
														class="password" maxlength="20" />
												</c:otherwise>
											</c:choose><span id="pwd" style="color:red"></span> <br /> <span
											id="errorMsg">${msg }</span></td>
									</tr>
									<tr>
										<th class="left_td">
										</td>
										<td>

											<div id="payDivId">
												<span class="commonBtn"
													style="margin-top: 20px;margin-left: -18px;" id="paySpanId">
													<span class="btn_lfRed"> <input id="Button1"
														class="btn_rtRed" type="button" onClick="submitChck()"
														value="确认支付" /> </span> </span>
											</div>
										</td>
									</tr>
								</table>

							</c:if>
						</div>
			</div>
			<!-- <div id="payDivId2" class="caTips"></div> -->

			</li>


			</ul>
	</div>
	</body>
</html>
<script>
var ts = "<%=System.currentTimeMillis()%>";

	function closeTips() {
		//不要动这个function代码  by shenjialong   
		var tips = $("#tips");
		tips.attr("style", "display: none");
	}


	function submitChck() {
		<c:choose>
		<c:when test="${USE_KEYBOARD }">
		var password = getPassInput("powerpass", ts, "errorMsg", "密码输入错误：");
		$("#password").val(password);
		</c:when>
		<c:otherwise>
		//获取密码密文
		var password = $("#password").val();
		</c:otherwise>
		</c:choose>
		if (password == null) {
			alert("支付密码不能为空");
			return;
		}
		// $("#tradePwd").val(tradePwd);
		///是否为数字证书用户
		if ('${IS_SN_USER}' == 'false') {
			submitorder();
		} else {
			var amount = $("#amount").val();//转账金额
			var merchantNo = $("#merchantNo").val();//
			//设置签名信息
			setTransferMessage(amount, merchantNo, null);
			//对数据签名
			if (SignPKCS7()) {
				submitorder();
			} else {
				alert("PIN码有误!");
			}
			//----转账数字证书------失败
		}
	}
</script>

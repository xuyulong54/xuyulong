<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/inc/taglib.jsp"%>
<%@include file="/page/inc/headScript.jsp"%>
<%@ page import="java.lang.*"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="pragma"
	CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
<META HTTP-EQUIV="Cache-Control"
	CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
<META HTTP-EQUIV="expires" CONTENT="-1">
<script type="text/javascript" src="<%=path%>/statics/js/Popup.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/b2bgateway.js"></script>
<title>支付网关登录页</title>

</head>

<script type="text/javascript">
	//页面全局变量
	var ts = "<%=System.currentTimeMillis()%>";
	$(function(){
    $('.closeImg').bind('click',function(){
    $('.warmWrap').hide();

    });
    })
</script>

<body>
	<div class='warmWrap'>
		<img src="<%=path%>/statics/images/closeImg.gif" class='closeImg' />
		<p class='warminfos'>${COMPANY_FOR }支付平台提醒您：在购买商品和支付时，请注意核对网址，收款商户、商品名称、支付金额等信息，警惕各种形式的网络欺骗行为！</p>
	</div>
<%@ include file="/page/head.jsp"%>
	<div class="containerGagteway auto">


		<div class="pageContent">
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
								<% java.text.SimpleDateFormat formatter=new java.text.SimpleDateFormat( "yyyy/MM/dd HH:mm:ss"); java.util.Date currentTime_1=new java.util.Date(); out.print(formatter.format(currentTime_1)); %>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<!--订单信息  end-->

			<!--tag选择栏-->
			<div class="tag">
				<span class="cirArrow"></span>
				<div class="tagCon">
					<ul id="tag">
						<li><span></span><a href="javascript:history.go(-1);">网银支付</a>
						</li>
						<li class="current firstLi"><span></span><a
							href="javascript:void(0)">${COMPANY_FOR }账户支付</a>
						</li>
					</ul>
				</div>
				<span class="cirArrow arg"></span>
			</div>
			<!--tag end-->


			<!---网银支付 end-->

			<!---${COMPANY_FOR }会员支付 start-->
			<li class="bankContainer" id="change">
				<div id="ObligateInfoDiv" style="display: none">
					<div class="tipsMsg"></div>
				</div>
				<ul class="block">
					<li>
						<form action="b2bbalancePay_validateLogin.action" method="post"
							id="payForm">
							<table class="tbCheckInfo" cellpadding="5" cellspacing="0"
								border="0">

								<tr>
									<td class="left_td leftW">登录名：</td>
									<td class="mid_td"><input class="input" type="text"
										name="username" id="username" /> <span id="name"
										style="color:red"></span> <span id="errorMsg"
										style="display: none"></span></td>
									<td style="width:100px;"><a class="linkImg"
										href="${PROTAL_URL }merchantRegister_checkLoginNameUI.action"
										target="_blank" title="如果没有账户请注册"> 注册用户 </a></td>
								</tr>
								<tr>
									<td class="left_td leftW">登录密码：</td>
									<td class="mid_td"><c:choose>
											<c:when test="${USE_KEYBOARD }">
												<input type="hidden" id="keyboard" value="1" />
												<input type="hidden" name="tradePwd" id="password"
													class="input" maxlength="20" />
												<script type="text/javascript">
													writePassObject("powerpass", {
														"width" : 173,
														"height" : 35,
														" margin-top" : 10,
														"accepts" : "[:graph:]+",
														"x" : -50,
														"maxlength":20
													});
												</script>
											</c:when>
											<c:otherwise>
												<input type="password" name="tradePwd" id="password"
													class="input" maxlength="20" />
											</c:otherwise>
										</c:choose> <span id="pwd" style="color:red"></span>
									</td>
									<td style="width:100px;"><a class="linkImg"
										href="${PROTAL_URL }merchantLookForLoginPwd_loginPwdCheckLoginNameUI.action"
										target="_blank" title="请点击修改支付密码登录个人中心,去安全中心获取支付密码"
										class="forgetpwd"> 忘记密码 </a></td>
								</tr>
								<tr>
									<td class="left_td leftW"></td>
									<td colspan="2" height="5px"><span id="message"
										style="color:red">${msg }</span>
									</td>
								</tr>
								<tr>
									<td class="left_td"></td>
									<td class="mid_td">
										<div id="autoD">
											<span class="commonBtn"><span class="btn_lfRed">
													<input id="gwPaySubmit" class="btn_rtRed" type="button"
													value="登录" /> </span> </span>
										</div>
									</td>
									<td class="right_td"></td>
								</tr>
							</table>
						</form>
					</li>
					<%--确定是余额支付--%>
					<input type="hidden" id="frpcode" name="frpCode" value="BALANCE" />
					<!-- 是否是数字证书用户 -->
					<input type="hidden" id="autoCA" name="autoCA"
						value="${IS_SN_USER}" />
				</ul> <span class="clear"></span>
			</li>
			</ul>
			<div id="helpmemo"></div>
			<!-- 弹出层开始 -->
			<div id="fade" class="black_overlay"></div>

			<div id="MyDiv" class="mydiv">
				<div class="topRed">
					<span>网银支付提示</span> <a href="#" class="btnClostRed"
						style="display:none;" onclick="CloseDiv('MyDiv','fade')"></a>
				</div>

				<div class="cont">
					<p>
						<strong> &nbsp; </strong> <font class="font">支付完成前，请不要关闭此支付验证窗口</font>
					</p>
					<p>
						<strong> &nbsp; </strong> <font class="font">支付完成后，请根据支付结果选择如下按钮</font>
					</p>



					<div class="btnDiv">
						<strong> &nbsp; </strong>
						<form action="bankPaySuccess_paySuccess.action" method="post">
							<input type="hidden" id="orderNo" name="orderNo"
								value="${PaymentOrderVo.merchantOrderNo}" /> <input
								type="hidden" id="merchantNo" name="merchantNo"
								value="${PaymentOrderVo.merchantNo}" /> <input
								class="submitBtn" id="Button4" type="submit" value="支付完成"
								style="cursor:pointer;" />
						</form>


						<form action="bankPaySuccess_paySuccess.action" method="post">
							<input type="hidden" id="orderNo" name="orderNo"
								value="${PaymentOrderVo.merchantOrderNo}" /> <input
								type="hidden" id="merchantNo" name="merchantNo"
								value="${PaymentOrderVo.merchantNo}" /> <input
								style="margin-left: 15px;" class="submitBtn" id="Button5"
								type="submit" value="支付出现问题" style="cursor:pointer;" />
						</form>

					</div>
					<div class="clear"></div>
				</div>
			</div>
			<!-- 弹出层结束 -->

			<div style="clear:both"></div>

		</div>
	</div>
	<!-- 弹出层 -->

	<jsp:include page="../foot.jsp" />

</body>

</html>

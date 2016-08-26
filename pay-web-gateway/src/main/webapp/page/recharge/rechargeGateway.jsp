<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/page/inc/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值支付网关页面</title>
<%@include file="/page/inc/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/js/Popup.js"></script>
</head>

<div class="reg_top100">
	<div class="topGateway">
		<ul>
			<li><img src="<%=path%>${COMPANY_LOGO }" /></li>
			<li><span>|</span></li>
			<li class="title">充值平台</li>

		</ul>
	</div>
</div>
<div class="containerGagteway auto">

	<div class="pageContent">

		<form action="rechargeBankPay_execute.action" id="info" class="form" method="post" target="_blank">
			<!--订单信息-->
			<div class="showOrder">
				<div>
					<table class="tb" cellpadding="0" cellspacing="0">
						<tr>
							<th>业务名称</th>
							<th>充值金额(元)</th>
							<th>充值时间</th>
						</tr>
						<tr>
							<td>会员充值</td>
							<td class="numRed">
								<fmt:formatNumber value="${RechargeOrderVo.orderAmount}" pattern="#0.00"></fmt:formatNumber>
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


			<div class="tag">
				<span class="cirArrow"></span>
				<div class="tagCon">
					<ul id="tag">
						<li class="current firstLi">
							<span style="margin-right: -4px;"></span>
							<a href="javascript:void(0)">网银支付</a>
						</li>
					</ul>
				</div>
				<span class="cirArrow arg"></span>
			</div>

			<ul class="tagContext">
				<li>
					<div class="bankContainer">
						<h3>请选择付款银行：</h3>
						<ul class="bank_list bank" id="Bank">
							<s:iterator value="payWays" var="payWay">
								<li>
									<input type="hidden" id="${payWayCode }" name="${payWayCode }" value="${payProductCode}" />
									<!-- 单击事件 gateway.js loadBankQuotaMsg() -->
									<input type="radio" name="frpCode" value="${payWayCode }" id="${bankCode}" />
									<label for="${bankCode}" class="${bankCode}"></label>
								</li>
							</s:iterator>
						</ul>
						<span class="clear"></span>
						<div class="moreBank">
							<span class="moreBank"><em>&or;</em> 更多银行</span>
						</div>
						<input id="frpPaySubmit" class="Pay_btn" type="button" value="确认支付" />
					</div>
					<div id="bankInfo"></div>
				</li>
			</ul>
		</form>
		<div id="helpmemo"></div>
	</div>

	<!-- 弹出层开始 -->
	<div id="fade" class="black_overlay"></div>
	<div id="MyDiv" class="mydiv">
		<div class="topRed">
			<span>网银支付提示</span> 
			<%--<a href="#" class="btnClostRed" style="display:none;" onclick="CloseDiv('MyDiv','fade')"></a>--%>
			
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
				<form action="rechargeBankPaySuccess_paySuccess.action" method="post">
					<input type="hidden" id="merchantOrderNo" name="merchantOrderNo"
						value="${PaymentOrderVo.merchantOrderNo}" />
					<input type="hidden" id="merchantNo" name="merchantNo"
						value="${PaymentOrderVo.merchantNo}" /> <input class="submitBtn"
						id="Button4" type="submit" value="支付成功" style="cursor:pointer;" />
				</form>

				<form action="rechargeBankPaySuccess_paySuccess.action" method="post">
							<input type="hidden" id="merchantOrderNo" name="merchantOrderNo"
								value="${PaymentOrderVo.merchantOrderNo}" /> <input type="hidden"
								id="merchantNo" name="merchantNo"
								value="${PaymentOrderVo.merchantNo}" /> <input
								style="margin-left: 15px;" class="submitBtn" id="Button5"
								type="submit" value="支付出现问题" style="cursor:pointer;" />
								<a style="margin-left: 10px;" href="#" onclick="CloseDiv('MyDiv','fade')"><span>重新选择银行</span></a>
						</form>
			</div>

			<div class="clear"></div>
		</div>
	</div>
	<!-- 弹出层结束 -->
	<div class="clear"></div>
</div>
<!--页脚-->
<jsp:include page="../foot.jsp" />
<!--页脚 end-->
</html>

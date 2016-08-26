<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/page/inc/taglib.jsp"%>
<%@ page import="java.lang.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<META HTTP-EQUIV="pragma"
	CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
<META HTTP-EQUIV="Cache-Control"
	CONTENT="private, no-cache, no-store, proxy-revalidate, no-transform">
<META HTTP-EQUIV="expires" CONTENT="-1">
<title>支付网关页面</title>
<%@include file="/page/inc/headScript.jsp"%>
<%-- <script type="text/javascript" src="<%=path%>/statics/js/writeObject.js"></script> --%>
<script type="text/javascript" src="<%=path%>/statics/js/Popup.js"></script>
<script type="text/javascript" src="<%=path%>/statics/js/b2bgateway.js"></script>
</head>

<script type="text/javascript">
	//页面全局变量
	var ts = "<%=System.currentTimeMillis()%>";
</script>


<body  oncontextmenu="return false" onselectstart="return false" ondragstart="return false">
	<div class="containerGagteway auto">

		<%@ include file="/page/head.jsp"%>

		<div class="pageContent">
			<form action="b2bbankPay_execute.action" id="info" class="form"
				method="post" target="_blank">
				<input type="hidden" id="orderNo" name="orderNo"
					value="${paymentOrderVo.merchantOrderNo}" /> <input type="hidden"
					id="merchantNo" name="merchantNo" value="${paymentOrderVo.merchantNo}" />
				<input type="hidden" id="amount" name="amount"
					value="${paymentOrderVo.orderAmount}" />
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
								<td>${paymentOrderVo.merchantOrderNo}</td>
								<td style="text-align:center">${paymentOrderVo.merchantName}</td>
								<td>${paymentOrderVo.productName}</td>
								<td class="numRed"><fmt:formatNumber
										value="${paymentOrderVo.orderAmount}" pattern="#0.00"></fmt:formatNumber></td>
								<td>
									<%java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
								java.util.Date currentTime_1=new java.util.Date();
								out.print(formatter.format(currentTime_1));%>
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
							<li class="current firstLi">
								<span style="margin-right: -4px;"></span>
								<a href="javascript:void(0)">网银支付</a>
							</li>
							<li>
								<span></span>
								<a href="b2bbalancePay_loginPayment.action">${COMPANY_FOR }账户支付</a>
							</li>
							<!-- <li><span></span><a href="javascript:void(0)">其他支付方式</a>
                                </li> -->
						</ul>
					</div>
					<span class="cirArrow arg"></span>
				</div>
				<!--tag end-->

				<!--选择银行-->
				<ul class="tagContext">
					<!---网银支付 start-->
					<li >
						<div class="bankContainer">
							<h3>请选择付款银行：</h3>
							<ul class="bank_list bank" id="Bank">
								<!-- 动态列出所有的银行 -->
								<s:iterator value="payWays" var="frp">
									<li>
									 <input type="hidden" id="${payWayCode }" name="${payWayCode }" value="${payProductCode}" />
									 <input type="hidden" name="${payWayCode }_bankCode" value="${bankCode}" />
										<!-- 单击事件 gateway.js loadBankQuotaMsg() --> <input
										type="radio" name="frpCode" id="${bankCode}" value="${payWayCode }" /> <label for="${bankCode}"
										class="${bankCode}"></label></li>
								</s:iterator>
							</ul> <span class="clear"></span>
							<div class="moreBank">
								<span class="moreBank"><em>&or;</em> 更多银行</span>
							</div> <span class="commonBtn" style=" margin-left: 20px;"><span
								class="btn_lfRed"> <input id="frpPaySubmit"
									class="btn_rtRed" type="button" value="确认支付" />
							</span> </span>
						</div>
						<div id="bankInfo"></div>
					</li>
					<!---网银支付 end-->

					<!---${COMPANY_FOR }会员支付 start-->
					<li class="bankContainer divNone" id="change">
					<div id="ObligateInfoDiv" style="display: none;">
					<div class="tipsMsg"></div></div>
						<ul class="block">
							<li>
								<table class="tbCheckInfo" cellpadding="5" cellspacing="0"
									border="0"><%--
									<tr id="ObligateInfoDiv"
										style="display: none;">

										<td class="left_td" colspan="3">
											<div class="tipsMsg"></div>
										</td>
									</tr>
									--%><tr>
										<td class="left_td">登录名：</td>
										<td class="mid_td"><input class="input" type="text"
											name="username" id="username" /><span id="name"
											style="color:red"></span><span id="errorMsg"
											style="display: none"></span></td>
										<td>&nbsp;&nbsp;  <a class="linkImg" href="${registerUrl}" target="_blank" title="如果没有账户请注册">
												注册用户
										</a>

										</td>
									</tr>
									<tr>
										<td class="left_td">支付密码：</td>
										<td class="mid_td">
											<input class="input" name="tradePwd" id="tradePwd" type="hidden" id="password" />
											<span id="pwd"style="color:red"></span>
											<script type="text/javascript">
                                                writePassObject("powerpass", {
                                                    "width": 204,
                                                    "height": 30,
                                                    " margin-top": 5,
                                                    "accepts": "[:graph:]+",
                                                    "x": -50
                                                });
                                            </script>
                                        </td>
										<td>&nbsp;&nbsp; <a class="linkImg" href="${pwdFind}" target="_blank" title="请点击修改支付密码登录个人中心,去安全中心获取支付密码" class="forgetpwd">
												忘记密码
										</a>
										</td>
									</tr>
									<tr>

										<td colspan="3" height="5px"><span id="message"
											style="color:red"></span></td>
									</tr>
									<tr>
										<td class="left_td"></td>
										<td class="mid_td"><span class="commonBtn"><span
												class="btn_lfRed"> <input id="gwPaySubmit" class="btn_rtRed"
													type="button" value="确认支付" />
											</span> </span></td>
										<td class="right_td"></td>
									</tr>
								</table>
							</li>
							<%--确定是余额支付--%>
							<input type="hidden" id="frpcode" name="frpCode" value="BALANCE" />
						</ul> <span class="clear"></span></li>
					<!---${COMPANY_FOR }会员支付 end-->

					<!---其它支付方式 start-->
					<%--
	    <li class="bankContainer divNone">
    		<ul class="bank_list name_list" id="Bank">
	    				<li>
			              <input type="radio" name="frpCode"  value="1001099"  />
			              xxx支付账户
			            </li>
	        </ul>
	        <span class="clear"></span>
	        <div class="btn"><a href="####" class="org_btn" id="otherFrp_submit"><span>确认支付</span></a></div>
    	</li>
    	--%>
					<!---其它支付方式 end-->

				</ul>
			</form>


			<div id="helpmemo"></div>


			<!-- 弹出层开始 -->
			<div id="fade" class="black_overlay"></div>

			<div id="MyDiv" class="mydiv">
				<div class="topRed">
					<span>网银支付提示</span> 
					<%--<a href="#" class="btnClostRed" style="display:none;" onclick="CloseDiv('MyDiv','fade')"></a>--%>
					<a href="#" onclick="CloseDiv('MyDiv','fade')"><span>重新选择银行</span></a>
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
								value="${PaymentOrderVo.merchantOrderNo}" /> <input type="hidden"
								id="merchantNo" name="merchantNo"
								value="${PaymentOrderVo.merchantNo}" /> <input class="submitBtn"
								id="Button4" type="submit" value="支付完成" style="cursor:pointer;" />
						</form>


						<form action="bankPaySuccess_paySuccess.action" method="post">
							<input type="hidden" id="orderNo" name="orderNo"
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
	</div>

	<!--页脚-->
	<jsp:include page="../foot.jsp" />
	<!--页脚 end-->
</body>
</html>

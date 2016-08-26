<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>

		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>支付记录查询</title>
			<%@include file="/page/include/headScript.jsp" %>
			<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
			<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
				<script type="text/javascript">

				/*页面分类*/
				 $(document).ready(function() { setPageType('.mer-receivable', '.mer-receivable-payment '); })
				//时间控制
			//	$(function(){selectDate('${timeType}');})
					 // 退款处理
					function refundOrder() {
						var trxNos = "-1";
						if (orderNo == "-1") {
							var items = document.getElementsByName("checkC");

							var len = items.length;
							for (var i = 0; i < len; i++) {
								if (items.checked) {
									trxNos = trxNos + "&" + items[i].value;
								}
							}
						} else {
							trxNos = trxNo;
						}
						if (trxNos != "-1") {
							document.getElementById("trxNos").value = trxNos;
							document.forms[0].action = "merchantReceiveRefund_refundRecord.action?trxNos=" + trxNos;
							document.forms[0].submit();
						} else {
							alert("请选择数据");
						}
					}

					function refundSS() {
						var trxNos = "";
						var items = document.getElementsByName("checkC");
						var len = items.length;
						for (var i = 0; i < len; i++) {
							if (items[i].checked) {
								trxNos = trxNos + "&" + items[i].value;
							}
						}
						if (trxNos == "") {
							$("#msg").html("请选择数据");
							$("#msg").css('display', 'inline-block');
							return false;
						}
						document.forms[0].action = "merchantReceiveRefund_refundRecord.action?trxNos=" + trxNos;
						document.forms[0].submit();
					}

					 //补单处理
					function replacementOrder() {
						var trxNos = "";
						var items = document.getElementsByName("checkC");
						var len = items.length;
						for (var i = 0; i < len; i++) {
							if (items[i].checked) {
								trxNos = trxNos + "&" + items[i].value;
							}
						}
						if (trxNos == "") {
							$("#msg").html("请选择数据");
							$("#msg").css('display', 'inline-block');
							return false;
						}
						$.post("merchantReceivePaymentOrder_replacementOrder.action", {
							params: trxNos
						}, function (data) {
							var obj=eval(data);
							var alertStr = "";
							if (obj.status == 0) {
								alertStr = "流水号为：" + data[0].noNotifyTrxNo + "的交易记录不需要补单"
							} else {
								var alertStr = "发送消息成功";
							}
							$("#msg").html(alertStr);
							$("#msg").css('display', 'inline-block');
						}, "json");
						return true;
					}
					 //撤消订单
					function cancelPaymentRecord(merchantOrderNo) {
						$.post("merchantReceivePaymentOrder_cancelPaymentRecord.action", {
							'merchantOrderNo': merchantOrderNo
						}, function (data) {
							$("#msg").html(data.message);
							$("#msg").css('display', 'inline-block');
							if(data.status=="SUCC"){
								$("#" + merchantOrderNo).html(data.paystatus);
								$("#a" + merchantOrderNo).hide();
							}
						}, "json");
					 }
					function checkAll() {
						var items = document.getElementsByName("checkC");
						var itemall = document.getElementById("all");
						var len = items.length;
						for (var i = 0; i < len; i++) {
							items[i].checked = itemall.checked;
						}
					}
				</script>
		</head>

		<body >
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>

			 <div class="container">


			<form action="merchantReceivePaymentOrder_listReceivePaymentOrder.action" id="form" method="post">

		<div class="query-head mtop10">
			<div class="title">支付记录查询</div>
		</div>
		 <div class="query-conditions">
			  <div class="frm-horizontal">
			   <div class="frm-group">
					<label></label>
					<span  style="display:none;" class="ada-wronginfos" id="msg"></span>
				</div>
					<div class="frm-group">
					 <div class="content-left">
			    <jsp:include page="/page/include/tradingTime.jsp"></jsp:include></div>

					 <label>
                       商户订单号：</label><input name="merchantOrderNo" type="text" value="${merchantOrderNo}"></div>

 			<div class="frm-group">
			    <div class="content-left">
			    	<label>支付方式类型：</label>
			    	<div class="select_border">
                    	<div class="select_cont">
							 <select name="paymentType" class="select input-w170">
								<option value="">所有</option>
								<c:forEach items="${tradePaymentTypeList}" var="tradePaymentType">
									<option value="${tradePaymentType.value}"
										<c:if test="${paymentType ne null and paymentType eq tradePaymentType.value}">selected="selected"</c:if>>${tradePaymentType.desc}</option>
								</c:forEach>
							</select>
						</div>
					</div>
			    </div>
			    <label>交易状态：</label>
			    <div class="select_border">
                            <div class="select_cont">
				<select name="status" class="select input-w170">
					<option value="">所有</option>
					<c:forEach items="${orderStatusList}" var="orderStatus">
						<option value="${orderStatus.value}"
							<c:if test="${status==orderStatus.value}">selected="selected"</c:if>>${orderStatus.desc}</option>
					</c:forEach>
				</select>
				</div></div>
			    </div>

					 <div class="frm-group">
                    <label>
                       </label>
                       <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
                       </div>


					<div class="clear"></div>
				</div>
			</div>

		<div class="search-top">
			<a id="exportExecl" href="merchantReceivePaymentOrder_exportReceivePaymentOrderToExcel.action?123456&orderNo=${orderNo }&trxNo=${trxNo }&beginDate=${beginDate}&endDate=${endDate}&status=${status }&paymentType=${paymentType }"
						onclick="return exportExcel();">导出EXCEL</a>
		</div>

<div class="search-sesult">
        <img class="png" src="<%=path%>/statics/themes/default/images/ico_total.png"/>
        <strong class="fl">合计结果：</strong>
        <p>订单金额合计：<span class="text-warning"> <fmt:formatNumber value="${empty countResultMap.payerAmountSum?0.00:countResultMap.payerAmountSum}" pattern="#0.00"></fmt:formatNumber></span> 元，
         实际交易金额：<span class="text-success"> <fmt:formatNumber value="${empty countResultMap.payerAmountSum?0.00:countResultMap.payerAmountSum-countResultMap.receiverFeeSum}" pattern="#0.00"></fmt:formatNumber></span> 元，
         手续费合计：<span class=" text-primary"> <fmt:formatNumber value="${empty countResultMap.receiverFeeSum?0.00:countResultMap.receiverFeeSum }" pattern="#0.00"></fmt:formatNumber></span> 元</p>
        </div>
			</form>

			<div class="search_list">
				<table class="table table-hover" cellpadding="0" cellpadding="0">
				 <thead>
					<tr>
						<th>
							<input type="checkBox" name="all" id="all" onClick="checkAll()" />
							<input type="hidden" name="trxNos" id="trxNos" />
						</th>
						<th>创建时间</th>
						<th>支付时间</th>
						<th>支付方式类型</th>
						<th>商户订单号</th>
						<th>订单金额(元)</th>
						<th>手续费(元)</th>
						<th>实收金额(元)</th>
						<th>交易状态</th>
						<th>是否退款</th>
						<th>操作</th>
					</tr>
					</thead>
					<tbody>
					<s:iterator value="recordList" status="st">
						<tr>
							<td>
								<input id="checkC" type="checkbox" name="checkC" value="${successTrxNo},${status},${merchantOrderNo},${merchantNo}" />
							</td>
							<td>
								<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<fmt:formatDate value="${paySuccessTime}" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
							<td>
								<c:forEach items="${tradePaymentTypeList}" var="v">
									<c:if test="${v.value == paymentType }">${v.desc}</c:if>
								</c:forEach>
							</td>
							<td>${merchantOrderNo}</td>
							<td class="text-warning">
								<fmt:formatNumber value="${orderAmount}" pattern="#0.00"></fmt:formatNumber>
							</td>
							<td class="text-primary">
								<fmt:formatNumber value="${receiverFee}" pattern="#0.00"></fmt:formatNumber>
							</td>
							<td class="text-success">
								<fmt:formatNumber value="${orderAmount - receiverFee}" pattern="#0.00"></fmt:formatNumber>
							</td>
							<td id="${merchantOrderNo}">
								<c:forEach items="${orderStatusList}" var="orderStatus">
									<c:if test="${orderStatus.value==status }">${orderStatus.desc}</c:if>
								</c:forEach>
							</td>
							<td>${isRefund==100?'已退':'未退 '}</td>
							<td class="text-primary">
								<div align="left">
									<a class="link-color" href="merchantReceivePaymentOrder_viewReceivePaymentOrder.action?merchantOrderNo=${merchantOrderNo}" style=" font-size: 12px;">详情</a>
									<c:if test="${bizType !=TradeBizTypeEnum.POS_ACQUIRING.value }">
									<c:if test="${orderAmount > successRefundAmount && status == 100}">
										<a class="link-color" href="merchantReceiveRefund_viewReceiveRefund.action?merchantOrderNo=${merchantOrderNo}" style="font-size: 12px;">退款</a>
									</c:if>
									<div id="a${merchantOrderNo}">
										<c:if test="${status == 102}">
											<!-- 订单已创建 -->
											<a class="link-color"  href="javascript:;" style=" font-size: 12px;" onclick="return cancelPaymentRecord('${merchantOrderNo}')">撤销</a>
										</c:if>
									</div>
									</c:if>
								</div>
							</td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
				<c:if test="${pageBean.totalCount>0}">
					<div class="pageCla">
						<z:page pageBean="${pageBean }" url="merchantReceivePaymentOrder_listReceivePaymentOrder.action" currentPage="${pageNum }" parameter="&merchantOrderNo=${merchantOrderNo }&beginDate=${beginDate}&endDate=${endDate}&status=${status }&paymentType=${paymentType}&timeType=${timeType}"></z:page>
					</div>
				</c:if>
					<input id="Button2"  class="btn btn-primary" type="button" onclick="return replacementOrder();" value="商户自助补单" />
				<br style="clear: both;" />
			</div>
			<div class="ht"></div>
			</div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>

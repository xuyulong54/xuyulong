<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>交易记录详情页面</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
<div class="bd-container">
		
			<div class="headline">
	<div class="title">交易详情</div>
</div>

			
			
				<table class="table table-border mtop10">
				<thead>
					<tr>
						<th>收款商户名称</th>
						<td>${paymentOrder.merchantName}</td>
						<th>商户订单号</th>
						<td>${paymentOrder.merchantOrderNo}</td>
					</tr>
					<tr>
						<th>交易渠道</th>
						<td><c:if test="${paymentOrder.payWayName eq ''}">余额支付</c:if>
							<c:if test="${paymentOrder.payWayName ne null}">${paymentOrder.payWayName}</c:if>
						</td>
						<th>订单金额</th>
						<td><fmt:formatNumber value="${paymentOrder.orderAmount}"
								pattern="#0.00"></fmt:formatNumber>
						</td>
					</tr>
					<tr>
						<th>支付方式类型</th>
						<td>${paymentOrder.formatPaymentTypeEnumDesc}</td>
						<th>手续费</th>
						<td><fmt:formatNumber value="${paymentOrder.payerFee}"
								pattern="#0.00"></fmt:formatNumber></td>
					</tr>
					<tr>
						<th>订单状态</th>
						<td>${paymentOrder.formatStatusEnumDesc}</td>
						<th>实付金额</th>
						<td><fmt:formatNumber value="${paymentOrder.payerPayAmount}"
								pattern="#0.00" />
						</td>
					</tr>
					<tr>
						<th>创建时间</th>
						<td><fmt:formatDate value="${paymentOrder.createTime}"
								pattern="yyyy-MM-dd HH:mm:ss" /></td>
						<th>支付时间</th>
						<td><fmt:formatDate value="${paymentOrder.paySuccessTime}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
					</tr>
					</thead>
				</table>
			
		
			<div class="headline mtop20">
	<div class="title">商品信息</div>
</div>

				<table class="table table-border mtop10">
				<thead>
					<tr>
						<th>商家名称：</th>
						<td>${paymentOrder.merchantName}</td>
						<th>交易金额：
						</td>
						<td>${paymentOrder.orderAmount}</td>
					</tr>
					<tr>
						<th>商品名称：</th>
						<td>${paymentOrder.productName }</td>
						<th></th>
						<td></td>
					</tr>
					</thead>
				</table>
			
		
						<div class="headline mtop20">
	<div class="title">退款详情</div>
</div>

			
				<table class="table table-border mtop10">
				<thead>
					<tr>
						<th>退款金额</th>
						<th>退款原因</th>
						<th>退款申请提交时间</th>
						<th>退款状态</th>
					</tr>
					</thead>
					<tbody>
					<s:iterator value="refundRecordList" status="st">
						<tr>
							<td><s:property value="refundAmount" /></td>
							<td><s:property value="refundReason" /></td>
							<td><fmt:formatDate value="${createTime}"
									pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td><c:forEach items="${refundStatusList}"
									var="refundStatusEnum">
									<c:if test="${ refundStatus==refundStatusEnum.value}">${refundStatusEnum.desc}</c:if>
								</c:forEach></td>
						</tr>
					</s:iterator>
					</tbody>
				</table>
			

		
		<br style="clear: both;" />

	</div></div>
	<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />

</body>
</html>

<script type="text/javascript">

/*页面分类*/
 $(document).ready(function() {setPageType('.mer-pay', '.mer-pay-payment');  })
 </script>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员交易记录详情</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>

<body>
<div class="container">
<div class="bd-container">

	
				
		<div class="headline">
	<div class="title">订单信息</div>
</div>
				
				
				
	<table class="table table-border mtop10 mbt10">
	<thead>
		<tr>
			<th>商户订单号</th>
			<td >${paymentRecord.merchantOrderNo}</td>
			<th>银行名称</th>
			<td >${paymentRecord.payWayName}222</td>
		</tr>
		
		<tr>
			<th>支付方式类型</th>
			<td >${paymentRecord.formatPaymentTypeEnumDesc}</td>
			<th>支付状态</th>
			<td >${paymentRecord.formatStatusEnumDesc}</td>
		</tr>

		<tr>
			<th>创建时间</th>
			<td><fmt:formatDate value="${paymentRecord.createTime}"
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
			<th>支付时间</th>
			<td ><fmt:formatDate value="${paymentRecord.paySuccessDate}"
					pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		</thead>
	</table>
	
	
	
	
	
				<div class="headline">
	<div class="title">商品信息</div>
</div>

	<table class="table table-border mtop10 mbt10">
	<thead>
		<tr>
			<th>商家名称</th>
			<td >${paymentRecord.merchantName}</td>
			<th>交易金额</td>
			<td >${paymentRecord.orderAmount}</td>
		</tr>
		<tr>
			<th >商品名称</th>
			<td >${b2cOrder.productName }</td>
			<th></th>
			<td></td>
		</tr> 
		</thead>
	</table>
	

	


				
				<div class="headline">
	<div class="title">退款详情</div>
</div>
				
				
				
	<table class="table table-border mtop10 mbt10">
	<thead>
		<tr >
			<th>退款金额</th>
			<th>退款原因</th>
			<th>退款申请提交时间</th>
			<th>退款状态</th>
		</tr>
		</thead>
		<tbody>
		<s:iterator value="list" status="st">
			<tr>
				<td>${refundAmount }</td>
				<td>${refundReason}</td>
				<td> <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:choose>
					<c:when test="${refundStatus==100 }">
					退款成功
					</c:when>
					<c:when test="${refundStatus==101 }">
					退款失败
					</c:when>
					<c:when test="${refundStatus==102 }">
					订单已创建
					</c:when>
					<c:otherwise>
					
					</c:otherwise>
					</c:choose>
				</td> 
			</tr>
		</s:iterator>
		</tbody>
		
	</table>
	
	
	</div>
	<br style="clear: both;"/>
	</div>
	<div class="ht1"></div>
	 <jsp:include page="../../foot.jsp" />
</body>
</html>
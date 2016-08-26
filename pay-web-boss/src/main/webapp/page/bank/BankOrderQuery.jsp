<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bankOrder_queryBankOrder.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行订单号：<input type="text" id="bankOrderNo" name="bankOrderNo" value="${bankOrderNo}" class="required" size="30" alt="精确搜索"/>
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><button type="submit">查询</button></li>
						</ul>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
</div>
<!--  -->
<div id="MsgDiv"  style="background-color:rgb(235, 240, 245); text-align:center; margin:auto; margin-top:50px; margin-left:250px; margin-right:250px; margin-bottom:100px;  border:1px ;<c:if test="${isDisplay != 1 }">display: none;</c:if> ">

	<div class="unit">
				<label>商&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;&nbsp;名&nbsp;&nbsp;称：</label>
				<input type="text" size="30" readonly="readonly" value='${paymentRecord.merchantName }'>
				<label>订&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;&nbsp;日&nbsp;&nbsp;期：</label>
				<input type="text" size="30" readonly="readonly" value='<fmt:formatDate value="${paymentRecord.paySuccessDate }" pattern="yyyy-MM-dd"/>'>
	</div>
	<div class="unit">
				<label>银&nbsp;行&nbsp;&nbsp;订&nbsp;&nbsp;单&nbsp;号：</label>
				<input type="text" size="30" readonly="readonly" value='${queryResult.bankOrderNo }'>
				<label>商&nbsp;户&nbsp;&nbsp;订&nbsp;&nbsp;单&nbsp;&nbsp;号：</label>
				<input type="text" size="30" readonly="readonly" value='${paymentRecord.merchantOrderNo }'>
	</div>
	<div class="unit">
				<label>&nbsp;&nbsp;&nbsp;银行订单状态：</label>
				<c:forEach items="${BankTradeStatusEnum }" var="model">
							<c:if test="${model.value eq queryResult.status.value }"><input type="text" size="30" readonly="readonly" value='${model.desc }'></c:if>
				</c:forEach>
				<label>&nbsp;&nbsp;&nbsp;支付记录状态：</label>
				<c:forEach items="${PaymentRecordStatusEnum }" var="model">
							<c:if test="${model.value eq paymentRecord.status }"><input type="text" size="30" readonly="readonly" value='${model.desc }'></c:if>
				</c:forEach>
				
	</div>
	<div class="unit">
				<label>&nbsp;&nbsp;&nbsp;银行订单金额：</label>
				<input type="text" size="30" readonly="readonly" value='${queryResult.payAmount }'>
				<label>&nbsp;&nbsp;&nbsp;支付记录金额：</label>
				<input type="text" size="30" readonly="readonly" value='${paymentRecord.orderAmount }'>
	</div>
	<div  class="unit" <c:if test="${sucStatus != queryResult.status ||   sucTradeStatus == paymentRecord.status }">style="display: none;"</c:if> >
				<a href="bankOrder_notifyMerchant.action?bankOrderNo=${queryResult.bankOrderNo }&orderAccount=${queryResult.payAmount}&bankTradeId=${bankTradeId}" target="ajaxTodo"><input type="button" value="补发通知"></a>
	</div>
</div>
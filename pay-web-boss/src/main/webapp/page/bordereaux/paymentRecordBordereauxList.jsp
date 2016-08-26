<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bordereaux_paymentRecordBordereaux.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>选择统计时间段： <input type="text" name="beginDate"
						value="${fn:substring(beginDate,0,10)}" id="beginDate"
						class="date" size="9" readonly="true" />- <input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="9" readonly="true" />
					</td>
					<td>商户全称：
						<input type="text" name="merchantName" value="${merchantName}" size="15">
					</td>
					<td>交易类型：
						<select name="paymentType" id="paymentType" >
							<option value="">请选择</option>
							<c:forEach items="${TradePaymentTypeEnum }" var="model">
								<option value="${model.value }" <c:if test="${paymentType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<span style="float:left; margin-top: 5px;">计费接口编号：</span>
						<input size="15" class="readonly" id="payInterfaceCode" name="payInterfaceCode" type="text"  value="${payInterfaceCode}" style="float:left"/>
						<a class="btnLook" href="bordereaux_calCostInterfaceListLookupList.action" lookupGroup="frp">搜索</a>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确定导出这些记录吗?" class="icon" target="dwzExport" targettype="navTab"
					href="bordereaux_paymentRecordBordereauxExportExecl.action?beginDate=${beginDate }&endDate=${endDate}&paymentType
						=${paymentType}&merchantName=${merchantName}&payInterfaceCode=${payInterfaceCode}"> <span>导出EXCEL</span> </a></li>
			<li>
			<span>
				支付记录成功总数：<c:if test="${countResultMap.successAll eq null}">
					<font color="red">0</font>条</c:if> <c:if
					test="${countResultMap.successAll ne null}">
					<font color="red">${countResultMap.successAll}</font>条</c:if>
				付款方支付金额：<c:if test="${countResultMap.payerAmountSum eq null}">
					<font color="red">0</font>元</c:if> <c:if
					test="${countResultMap.payerAmountSum ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.payerAmountSum}" pattern="#0.00" /></font>元</c:if>
				付款方手续费：<c:if test="${countResultMap.payerFeeSum eq null}">
					<font color="red">0</font>元</c:if> <c:if
					test="${countResultMap.payerFeeSum ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.payerFeeSum}" pattern="#0.00" /></font>元</c:if>
				收款方手续费：<c:if test="${countResultMap.receiverFeeSum eq null}">
					<font color="red">0</font>元</c:if> <c:if
					test="${countResultMap.receiverFeeSum ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.receiverFeeSum}" pattern="#0.00" /></font>元</c:if>
				订单总金额（成功）：<c:if test="${countResultMap.orderSum eq null}">
					<font color="red">0</font>元</c:if> <c:if
					test="${countResultMap.orderSum ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.orderSum}" pattern="#0.00" /></font>元</c:if>
			</span>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="131">
		<thead>
			<tr>
				<th>序号</th>
				<th>支付时间</th>
				<th>商户全称 </th>
				<th>计费接口编号</th>
				<th>交易类型</th>
				<th>交易流水号</th>
				<th>商户订单号</th>
				<th>交易状态</th>
				<th>订单金额</th>
				<th>支付金额</th>
				<th>付款方手续费</th>
				<th>收款方手续费</th>
				<th>平台收入</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td>
						<fmt:formatDate value="${paySuccessDate}" pattern="yyyy-MM-dd"/>
						<fmt:formatDate value="${paySuccessTime}" pattern="HH:mm:ss"/>
					</td>
					<td>${merchantName}</td>
					<td>${payInterfaceCode}</td>
					<td>
					<c:forEach items="${TradePaymentTypeEnum}" var="trx">
							<c:if test="${trx.value eq paymentType }">${trx.desc}</c:if>
						</c:forEach>
					</td>
					<td>${trxNo}</td>
					<td>${merchantOrderNo}</td>
					<td>
						<c:forEach items="${paymentRecordStatus}" var="prStatus">
							<c:if test="${prStatus.value eq status}">${prStatus.desc}</c:if>
						</c:forEach>
					</td>
					<td><fmt:formatNumber value="${orderAmount}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${payerPayAmount}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${payerFee}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${receiverFee}" pattern="0.00"></fmt:formatNumber></td>
					<td><fmt:formatNumber value="${platIncome}" pattern="0.00"></fmt:formatNumber></td>
				 	</tr>
				</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
$.extend({
	bringBackSuggest : function(args) {
		$("input[id='payInterfaceCode']").val(args["interfaceCode"]);
	},
});
</script>
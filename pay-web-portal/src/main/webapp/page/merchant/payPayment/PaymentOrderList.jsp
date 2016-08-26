<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>我的付款</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() {  setPageType('.mer-pay', '.mer-pay-payment '); })
//时间控制
$(function(){selectDate('${timeType}');})
</script>
</head>
<body onload="getNowDay()">
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<div class="container">

	<form action="merchantPayPaymentOrder_listPaymentOrder.action" id="form"
		method="post">

	 	<div class="query-head mtop10">
      <div class="title">支付记录查询</div>
    </div>

	  <div class="query-conditions">
		  <div class="frm-horizontal">
	  		<div class="frm-group">
					<label>&nbsp;</label>
					<span style="display:none;" class="ada-wronginfos" id="msg"></span>
					<div class="clear"></div>
				</div>
				<div class="frm-group">
			    <div class="content-left">
			    	<jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
			    </div>
			     <label>商户订单号：</label>
			     <input name="merchantOrderNo" type="text" value="${merchantOrderNo}" />
			  </div>
				<div class="frm-group clearfix">
			    <div class="content-left">
			    	<label>支付方式类型：</label>
			    	<div class="select_border">
	            <div class="select_cont">
								<select name="paymentType" class="select input-w170">
									<option value="">所有</option>
									<c:forEach items="${tradePaymentTypeList}" var="tradePaymentType">
										<option value="${tradePaymentType.value}"
											<c:if test="${paymentType ne null and paymentType eq tradePaymentType.value}">selected="selected"</c:if>>${tradePaymentType.desc}
										</option>
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
						</div>
					</div>
				</div>

				<div class="frm-group clearfix">
	        <label>&nbsp;</label>
	        <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
	      </div>
			  <div class="clear"></div>
			</div>
		</div>

	  <div class="search-top">
	   <a id="exportExecl"
				href="merchantPayPaymentOrder_exportPaymentOrderToExcel.action?123456&merchantOrderNo=${merchantOrderNo }&beginDate=${beginDate}&endDate=${endDate}&status=${status }&paymentType=${paymentType }"
				onclick="return exportExcel();">导出EXCEL</a>
	  </div>
		<div class="search-sesult">
	    <img class="png" src="<%=path%>/statics/themes/default/images/ico_total.png"/>
	    <strong class="fl">合计结果：</strong>
	    <p>订单金额合计：<span class="text-warning"> <fmt:formatNumber
				value="${empty countResultMap.orderSum?0.00:countResultMap.orderSum}"
				pattern="#0.00"></fmt:formatNumber></span> 元
			</p>
	  </div>
	</form>

	<div class="search_list">
		<table class="table table-hover" cellpadding="0" cellpadding="0">
		 <thead>
			<tr>
				<th>创建时间</th>
				<th>支付时间</th>
				<th>支付方式类型</th>
				<th>商户订单号</th>
				<th>订单金额(元)</th>
				<th>实付金额(元)</th>
				<th>交易状态</th>
				<th>操作</th>
			</tr>
			</thead>
			<tbody>
			<s:iterator value="recordList" status="st">
				<tr>
					<td>
						<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<fmt:formatDate value="${paySuccessTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<c:forEach items="${tradePaymentTypeList}" var="pType">
							<c:if test="${pType.value == paymentType }">${pType.desc}</c:if>
						</c:forEach>
					</td>
					<td>${merchantOrderNo}</td>
					<td class="text-warning">
						<fmt:formatNumber value="${orderAmount}" pattern="#0.00"></fmt:formatNumber>
					</td>
					<td class="text-success">
						<fmt:formatNumber value="${payerPayAmount}" pattern="#0.00"></fmt:formatNumber>
					</td>
					<td id="${id}">
						<c:forEach items="${orderStatusList}" var="orderStatus">
							<c:if test="${orderStatus.value==status }">${orderStatus.desc}</c:if>
						</c:forEach>
					</td>
					<td class="text-primary">
						<div align="left">
								<a class="link-color" href="merchantPayPaymentOrder_viewPaymentOrder.action?merchantOrderNo=${merchantOrderNo }"
									style="color: Blue; font-size: 12px;">详情</a>
						</div>
					</td>
				</tr>
			</s:iterator>
			</tbody>
		</table>
		<c:if test="${pageBean.totalCount>0}">
			<div class="pageCla">
				<z:page pageBean="${pageBean }"
					url="merchantPayPaymentOrder_listPaymentOrder.action"
					currentPage="${pageNum }"
					parameter="&merchantOrderNo=${merchantOrderNo }&beginDate=${beginDate}&endDate=${endDate}&status=${status }&paymentType=${paymentType }&timeType=${timeType}"></z:page>
			</div>
		</c:if>
		<br style="clear: both;" />
	</div>
	<div class="ht"></div>
</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

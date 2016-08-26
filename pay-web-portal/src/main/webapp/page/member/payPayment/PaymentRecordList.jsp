<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>


<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>交易记录</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() { setPageType('.men-pay', '.men-pay-payment '); })
	//时间控制
	$(function(){selectDate('${timeType}');})
	</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<form action="memberPayPaymentOrder_listPaymentOrder.action" method="post" id="form">
				<div class="query-head mtop10">
					 <div class="title">支付记录查询</div>
				</div>
				<div class="query-conditions">
			    <div class="frm-horizontal">
			      <div class="frm-group">
			        <span style="display:none;" class="ada-wronginfos" id="msg"></span>
			      </div>
			      <div class="frm-group">
			        <div class="content-left">
								<jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
						  </div>
				      <label>交易状态：</label>
		          <div class="select_border">
		            <div class="select_cont">
			            <select name="status" class="select input-w170">
										<option value="">所有</option>
										<c:forEach items="${orderStatusList}" var="orderStatus">
											<option value="${orderStatus.value}"
												<c:if test="${proMap.status==orderStatus.value}">selected="selected"</c:if>>${orderStatus.desc}</option>
										</c:forEach>
								  </select>
							  </div>
							</div>
			      </div>
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
	            <label>商户订单号：</label> <input name="merchantOrderNo" type="text" value="${merchantOrderNo}" />
	          </div>
	          <div class="frm-group">
	            <label>&nbsp;</label>
	            <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
	          </div>
			    </div>
		    </div>
		    <div class="search-top">
			    <a id="exportExecl"
						href="memberPayPaymentOrder_exportPaymentOrderToExcel.action?beginDate=${beginDate }&endDate=${endDate }&merchantOrderNo=${merchantOrderNo }&status=${status }&paymentType=${paymentType}"
						onclick="return exportExcel();">导出EXCEL
				  </a>
		    </div>

			  <div class="search_list">
					<table class="table table-hover" cellpadding="0" cellpadding="0">
						<thead>
							<tr>
								<th>商户订单号</th>
								<th>创建时间</th>
								<th>交易类型</th>
								<th>交易状态</th>
								<th>商户名称</th>
								<th>订单金额(元)</th>
								<th>实际交易金额(元)</th>
								<th>是否退款</th>
								<th>操作</th>
							</tr>
						</thead>
						<tbody>
						<s:iterator value="recordList" status="st">
							<tr>
								<td>${merchantOrderNo}</td>
								<td><fmt:formatDate value="${createTime}"
										pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><c:forEach items="${tradePaymentTypeList}" var="pType">
										<c:if test="${pType.value== paymentType }">${pType.desc}</c:if>
									</c:forEach></td>
								<td><c:forEach items="${orderStatusList}" var="orderStatus">
										<c:if test="${orderStatus.value==status }">${orderStatus.desc}</c:if>
									</c:forEach></td>
								<td>${merchantName}</td>
								<td class='text-warning'><fmt:formatNumber pattern="#0.00" value="${orderAmount}"></fmt:formatNumber>
								<td class='text-primary'><fmt:formatNumber pattern="#0.00" value="${payerPayAmount}"></fmt:formatNumber>
								</td>
								<td>${isRefund==100?'已退':'未退' }</td>
								<td><a class="link-color" href="memberPayPaymentOrder_viewPaymentOrder.action?merchantOrderNo=${merchantOrderNo}">详情</a>
								</td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
					<c:if test="${pageBean.totalCount>0}">
						<div class="pageCla">
							<z:page pageBean="${pageBean }"
								url="memberPayPaymentOrder_listPaymentOrder.action"
								currentPage="${pageNum }"
								parameter="&beginDate=${beginDate }&endDate=${endDate }&merchantOrderNo=${merchantOrderNo }&status=${status }&paymentType=${paymentType }&timeType=${timeType}"></z:page>
						</div>
					</c:if>
					<br style="clear: both;" />
				</div>
	  	</form>
		</div>
  </div>
	<jsp:include page="../../foot.jsp" />
 </body>
</html>

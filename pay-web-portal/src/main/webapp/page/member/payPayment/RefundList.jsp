<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>退款记录查询</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">
	/*页面分类*/
	$(document).ready(function() {setPageType('.men-pay', '.men-pay-refund '); })
	//时间控制
	$(function(){selectDate('${timeType}');})
	</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	  <div class="container">
		  <div class="bd-container">
				<form action="memberPayRefund_listRefund.action" method="post" id="form">
					<div class="query-head mtop10">
						<div class="title">退款记录查询</div>
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
								<label> 交易流水号：</label><input name="orgTrxNo" type="text" value="${orgTrxNo}">
							</div>
							<div class="frm-group">
								<div class="content-left">
									<label> 金额范围：</label>
									<input name="beginAmount" style="width:62px" type="text" value="${beginAmount}" /> -
									<input name="endAmount" style="width:62px" type="text" value="${endAmount}" />
								</div>
				        <label> 退款状态：</label>
				        <div class="select_border">
		              <div class="select_cont">
						        <select name="refundStatus" class="select input-w170">
											<option value="">所有</option>
											<c:forEach items="${RefundStatusEnum }" var="refundStatusEnum">
												<option value="${refundStatusEnum.value}"
													<c:if test="${refundStatus==refundStatusEnum.value}">selected="selected"</c:if>>${refundStatusEnum.desc}
												</option>
											</c:forEach>
								    </select>
						   		</div>
								</div>
							</div>
						  <div class="frm-group">
						    <div class="content-left">
								  <label>商户订单号：</label>
								  <input  name="orgMerchantOrderNo" type="text" value="${orgMerchantOrderNo}">
						    </div>
						    <label>商户退款订单号：</label>
						    <input name="refundOrderNo" type="text" value="${refundOrderNo }">
						  </div>
							<div class="frm-group">
			          <label>&nbsp; </label>
			          <input class="btn btn-primary"  type="button" onclick="checkData('form');" value="查 询" />
			        </div>
						</div>
					</div>

					<div class="search-top">
				     <a id="exportExecl"
									href="memberPayRefund_refundExportExcel.action?beginDate=${beginDate}&endDate=${endDate}&orgTrxNo=${orgTrxNo}&beginAmount=${beginAmount}&endAmount=${endAmount}&refundStatus=${refundStatus}&orgMerchantOrderNo=${orgMerchantOrderNo}&refundOrderNo=${refundOrderNo} "
									onclick="return exportExcel();" >导出EXCEL</a>
		      </div>
		  	</form>
		    <div class="search_list">
					<table class="table table-hover" cellpadding="0" cellpadding="0">
						<thead>
							<tr>
								<th>创建时间</th>
								<th>商户订单号</th>
								<th>交易流水号</th>
								<th>商户退款订单号</th>
								<th>商户名称</th>
								<th>退款金额(元)</th>
								<th>退款状态</th>
								<th>退款原因</th>
							</tr>
						</thead>
						<s:iterator value="recordList" status="st">
							<tr>
								<td><fmt:formatDate value="${createTime}"
										pattern="yyyy-MM-dd HH:dd:ss" />
								</td>
								<td>${orgMerchantOrderNo}</td>
								<td>${orgTrxNo}</td>
								<td>${refundOrderNo}</td>

								<td>${merchantName}</td>
								<td class='text-warning'><fmt:formatNumber value="${refundAmount}" pattern="#0.00"></fmt:formatNumber>
								</td>
								<td><c:forEach items="${RefundStatusEnum}"
										var="refundStatusEnum">
										<c:if test="${refundStatus eq refundStatusEnum.value}">${refundStatusEnum.desc}</c:if>
									</c:forEach>
								</td>
								<td>${refundReason}</td>
							</tr>
						</s:iterator>
					</table>
						<c:if test="${pageBean.totalCount>0}">
						<div class="pageCla">
							<z:page pageBean="${pageBean }"
								url="memberPayRefund_listRefund.action"
								currentPage="${pageNum }"
								parameter="&beginDate=${beginDate }&endDate=${endDate }&refundStatus=${refundStatus }&orgMerchantOrderNo=${orgMerchantOrderNo }&orgTrxNo=${orgTrxNo }&createTime=${createTime }&refundOrderNo=${refundOrderNo }&refundStatus=${refundStatus }&endAmount=${endAmount }&timeType=${timeType}"></z:page>
						</div>
						</c:if>
					<br style="clear: both;" />
				</div>
			</div>
	</div>
<jsp:include page="../../foot.jsp" />
</body>
</html>

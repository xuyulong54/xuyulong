<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>转账记录查询</title>
		<%@include file="/page/include/headScript.jsp" %>
		<script type="text/javascript" src="<%=path%>/statics/js/common.js"></script>
		<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js">
		</script>
			<script type="text/javascript">
			/*页面分类*/
			 $(document).ready(function() { setPageType('.mer-pay', '.mer-pay-transfer '); })
			//时间控制
			$(function(){selectDate('${timeType}');})
			</script>
	</head>

	<body onload="getNowDay();">
		<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
		<div class="container">
			<div class="bd-container">
				<form action="merchantTransferRecord_listTransfer.action" method="post" id="form">
					<div class="query-head mtop10">
						<div class="title">转账记录查询</div>
					</div>
					<div class="query-conditions">
						<div class="frm-horizontal">
							<div class="frm-group">
								<label>&nbsp;</label>
								<span style="display:none;" class="ada-wronginfos" id="msg"></span>
							</div>
							<div class="frm-group">
							  <div class="content-left">
					    	<jsp:include page="/page/include/tradingTime.jsp"></jsp:include></div>
							  <label>交易流水号：</label><input type="text" name="trxNo" value="${trxNo}">
		          </div>
						  <div class="frm-group">
		            <label>&nbsp;</label>
		            <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
		          </div>
							<div class="clear"></div>
						</div>
					</div>
					<div class="search-top">
	  				<a id="exportExecl" href="merchantTransferRecord_transferExportExcel.action?beginDate=${beginDate}&endDate=${endDate}&trxNo=${trxNo}" onclick="return exportExcel();">导出EXCEL
	  				</a>
	   			</div>
		    	<div class="search-sesult">
		        <img class="png" src="<%=path%>/statics/themes/default/images/ico_total.png"/>
		        <strong class="fl">合计结果：</strong>
		        <p>转账金额合计：<span class="text-warning"> <fmt:formatNumber value="${empty countResultMap.successamount?0.00:countResultMap.successamount}" pattern="#0.00"></fmt:formatNumber></span> 元
		      </div>
				</form>

				<div class="search_list">
					<table class="table table-hover" cellpadding="0" cellpadding="0">
					 <thead>
						<tr>
							<th>创建时间</th>
							<th>交易流水号</th>
							<th>对方账号</th>
							<th>转账金额(元)</th>
							<th>手续费(元)</th>
							<th>交易状态</th>
							<th>转账说明</th>
						</tr>
						</thead>
						<tbody>
						<s:iterator value="recordList" status="st">
							<tr>
								<td>
									<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${trxNo}</td>
								<td>${sourceLoginName }</td>
								<td class="text-warning">
									<fmt:formatNumber value="${amount}" pattern="#0.00"></fmt:formatNumber>
								</td>
								<td class="text-primary">
									<fmt:formatNumber value="${targetFee }" pattern="#0.00"></fmt:formatNumber>
								</td>
								<td>
									<c:forEach items="${orderStatusList}" var="orderStatusEnum">
										<c:if test="${orderStatusEnum.value==status }">${orderStatusEnum.desc}</c:if>
									</c:forEach>
								</td>
								<td>${remark}</td>
							</tr>
						</s:iterator>
						</tbody>
					</table>
					<c:if test="${pageBean.totalCount>0}">
						<div class="pageCla">
							<z:page pageBean="${pageBean}" url="merchantTransferRecord_listTransfer.action" currentPage="${pageNum }" parameter="&beginDate=${beginDate }&endDate=${endDate }&sttype=${sttype}&trxNo=${trxNo}&timeType=${timeType}"></z:page>
						</div>
					</c:if>
					<br style="clear: both;" />
				</div>
				<div class="clear"></div>
			</div>
		</div>
			<jsp:include page="../../foot.jsp" />
	</body>

</html>

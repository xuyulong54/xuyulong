<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>会员转账记录查询</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">

	/*页面分类*/
	$(document).ready(function() {
		setPageType('.men-receivable', '.men-receivable-transfer ');})
	//时间控制
	$(function(){selectDate('${timeType}');})

	</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
		  <form action="memberReceiveTransfer_listReceiveTransfer.action" method="post" id="form">
				<div class="query-head mtop10">
					<div class="title">转账记录查询</div>
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
							<label> 交易流水号：</label><input type="text" name="trxNo" value="${trxNo}">
						</div>
						<div class="frm-group">
	            <label>&nbsp;</label>
	            <input class="btn btn-primary" type="button" onclick="checkData('form');" value="查 询" />
	          </div>
					</div>
				</div>
				<div class="search-top">
	        <a id="exportExecl"
					  href="memberReceiveTransfer_exportReceiveTransferToExcel.action?beginDate=${beginDate }&endDate=${endDate }&trxNo=${trxNo}"
								onclick="return exportExcel();" >导出EXCEL
					</a>
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
							<th>交易状态</th>
							<th>转账说明</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="recordList" status="st">
							<tr>
								<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>${trxNo}</td>
								<td>${memberAccountId eq
									targetAccountId?targetLoginName:sourceLoginName}</td>
								<td class='text-warning'><fmt:formatNumber
										value="${amount}" pattern="#0.00"></fmt:formatNumber>
								</td>
								<td><c:forEach items="${orderStatusList}"
										var="orderStatusEnum">
										<c:if test="${orderStatusEnum.value==status }">${orderStatusEnum.desc}</c:if>
									</c:forEach></td>
								<td>${remark}</td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
				<c:if test="${pageBean.totalCount>0}">
					<div class="pageCla">
						<z:page pageBean="${pageBean }"
							url="memberReceiveTransfer_listReceiveTransfer.action"
							currentPage="${pageNum }"
							parameter="&beginDate=${beginDate }&endDate=${endDate }&trxNo=${trxNo}&timeType=${timeType}"></z:page>
					</div>
				</c:if>
				<br style="clear: both;" />
			</div>
		</div>
	</div>
	<div class="clear"></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

  <%--   <!-- 开始 -->
	<div class="pageContent">
		<form action="memberReceiveTransfer_listRecieveTransfer.action" method="post" id="form">
			<div class="column columnW980">
				<div class="title">转账记录查询</div>
			</div>
			<div class="queryConditions">
				<table class="resaullist_head">
					<p class="ada-wronginfop">
						<span style="display:none;" class="ada-wronginfos" id="msg"></span>
					</p>
					<p class="clearfix">
						<strong>创建时间：</strong>
						<input class="timeinput" name="beginDate" value="${beginDate }" type="text" class="timeinput" onclick="calendar(this)" id="beginDate" />
						至 <input name="endDate" value="${endDate}" type="text" class="timeinput" onclick="calendar(this)" id="endDate" /> &nbsp; &nbsp; &nbsp;
						<a href="#" id="a_Date_1" onclick="selectDate('toDay',this)">今天</a>&nbsp;
						<a href="#" id="a_Date_3" onclick="selectDate('currentMonth',this)" class="Fcurrent">本月</a>&nbsp;
						<a href="#" id="a_Date_2" onclick="selectDate('lastMonth',this)">上月</a>
					</p>
					<p class="clearfix">
						<strong>交易流水号：</strong> <input type="text" name="trxNo"
							value="${trxNo}">
					</p>
					<p class="clearfix">
						<strong> &nbsp; </strong> <span class="commonBtn"><span
							class="btn_lfRed"> <input class="btn_rtRed" type="button"
								onclick="checkData();" value="查 询" /> </span>
						</span> <a id="exportExecl"
							href="memberReceiveTransfer_exportReceiveTransferToExcel.action?beginDate=${beginDate }&endDate=${endDate }&trxNo=${trxNo}"
							onclick="return exportExcel();" class="exportBtn">导出EXCEL</a>
					</p>
				</table>
				<div class="clear"></div>
			</div>
		</form>
		<div class="h10"></div>
		<div class="search_list">
			<table class="tbl" cellpadding="0" cellpadding="0">
				<tr>
					<th>交易流水号</th>
					<th>创建时间</th>
					<th>对方账号</th>
					<th>转账金额</th>
					<th>交易状态</th>
					<th>转账说明</th>
				</tr>
				<s:iterator value="recordList" status="st">
					<tr>
						<td>${trxNo}</td>
						<td><fmt:formatDate value="${createTime}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>${memberAccountId eq
							targetAccountId?targetLoginName:sourceLoginName}</td>
						<td>${memberAccountId eq targetAccountId?'-':'+'}<fmt:formatNumber
								value="${amount}" pattern="#0.00"></fmt:formatNumber>
						</td>
						<td><c:forEach items="${orderStatusList}"
								var="orderStatusEnum">
								<c:if test="${orderStatusEnum.value==status }">${orderStatusEnum.desc}</c:if>
							</c:forEach></td>
						<td>${remark}</td>
					</tr>
				</s:iterator>
			</table>
			<c:if test="${pageBean.totalCount>0}">
				<div class="pageCla">
					<z:page pageBean="${pageBean }"
						url="memberReceiveTransfer_listRecieveTransfer.action"
						currentPage="${pageNum }"
						parameter="&beginDate=${beginDate }&endDate=${endDate }&trxNo=${trxNo}"></z:page>
				</div>
			</c:if>
			<br style="clear: both;" />
		</div>
		<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html> --%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>提现记录查询</title>
	<%@include file="/page/include/headScript.jsp"%>
	<script type="text/javascript" src="<%=path%>/statics/themes/default/js/searchList.js"></script>
	<script type="text/javascript">

	/*页面分类*/
	$(document).ready(function() {  setPageType('.men-receivable', '.men-receivable-withdraw '); })
	//时间控制
	$(function(){selectDate('${timeType}');})
	</script>
</head>
<body onload="getNowDay();">
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
		  <form action="withdraw_listRemittanceRecord.action" id="remittanceForm" method="post">
				<div class="query-head mtop10">
					<div class="title"> 提现记录查询</div>
				</div>
				<div class="query-conditions">
					<div class="frm-horizontal">
					  <div class="frm-group">
					    <span style="display:none;" class="ada-wronginfos" id="msg"></span>
				    </div>

				    <div class="frm-group clearfix">
							<div class="content-left">
							  <jsp:include page="/page/include/tradingTime.jsp"></jsp:include>
							</div>
						</div>

						<div class="frm-group">
	            <label>&nbsp;</label>
	            <input class="btn btn-primary" type="button" onclick="checkData('remittanceForm');" value="查 询"  />
	          </div>
					</div>
				</div>
				<div class="search-top">
	        <a  id="exportExecl" href="withdraw_exportExcel.action?&beginDate=${beginDate }&endDate=${endDate}" onclick="return exportExcel();">导出EXCEL</a>
	      </div>

	      <div class="search_list">
					<table class="table table-hover" cellpadding="0" cellpadding="0">
						<thead>
							<tr >
								<th>创建时间</th>
								<th>打款批次号</th>
								<th>提现金额(元)</th>
								<th>提现银行卡</th>
								<th>提现卡所属银行</th>
								<th>交易状态</th>
								<th>备注</th>
							</tr>
						</thead>
						<tbody>
							<s:iterator value="recordList" status="st">
								<tr>
									<td ><fmt:formatDate
											value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
									<td>${batchNo }</td>
									<td class='text-warning'> <fmt:formatNumber pattern="#0.00" value="${remitAmount}"></fmt:formatNumber>
									</td>
									<td >
										<script type="text/javascript">
											var bankNoLength = '${bankAccountNo}'.length;
											window.document.write('${bankAccountNo}'.substring(0,4));
											window.document.write("****" + '${bankAccountNo}'.substring(bankNoLength-4,bankNoLength));
										</script>
									</td>
									<td >
										<c:forEach items="${RemitBankTypeList }" var="RemitBankTypeList">
										<c:if test="${bankCode == RemitBankTypeList.bankCode }">${RemitBankTypeList.typeName }</c:if>
										</c:forEach>
									</td>
									<td >
										<c:forEach items="${SettRecordStatusEnum}" var="enums">
										<c:if test="${settStatus eq enums.value}">${enums.desc}</c:if>
										</c:forEach>
									</td>
									<td >${remark }</td>
								</tr>
							</s:iterator>
						</tbody>
					</table>
					<c:if test="${pageBean.totalCount>0}">
					<div class="pageCla">
						<z:page pageBean="${pageBean }" currentPage="${pageNum }"
							url="withdraw_listRemittanceRecord.action"
							parameter="&beginDate=${beginDate }&endDate=${endDate}&timeType=${timeType}"></z:page>
					</div>
					</c:if>
				</div>
			</form>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>


<%-- <div class="pageContent">
	<form action="withdraw_listRemittanceRecord.action" id="remittanceForm"
		method="post">

			 <div class="column columnW980">
                <div class="title">
                   提现记录查询</div>
            </div>

				<div class="queryConditions">
					<p class="ada-wronginfop">
			   <span style="display:none;" class="ada-wronginfos" id="msg"></span>
               </p>

					<p class="clearfix">
						<strong>提现日期：</strong> <input name="beginDate"
							value="${beginDate}" class="timeinput"
							type="text"  onclick="calendar(this)"
							id="beginDate" /> 至 <input name="endDate"
							value="${endDate}" class="timeinput"
							type="text"  onclick="calendar(this)"
							id="endDate" /> &nbsp; &nbsp; &nbsp; <a href="#" id="a_Date_1"
							onclick="selectDate('toDay',this)">今天</a> &nbsp; <a href="#"
							id="a_Date_3" onclick="selectDate('currentMonth',this)"
							class="Fcurrent">本月</a> &nbsp; <a href="#" id="a_Date_2"
							onclick="selectDate('lastMonth',this)">上月</a>

					</p>
					<p class="clearfix">
						<strong> &nbsp; </strong>
				<span class="commonBtn"><span class="btn_lfRed">
				 <input class="btn_rtRed" type="button" onclick="checkData();" value="查 询" />
				 </span></span>
						<a  id="exportExecl" href="withdraw_exportExcel.action?&beginDate=${beginDate }&endDate=${endDate}" onclick="return exportExcel();" class="exportBtn">导出EXCEL</a>
					</p>
  <div class="clear"></div>
				</div>

		<div class="h10"></div>
	<div class="search_list">
		<table class="tbl" cellpadding="0" cellpadding="0">
				<tr >
					<th>打款批次号</th>
					<th>提现时间</th>
					<th>提现金额(元)</th>
					<th>提现银行卡</th>
					<th>提现卡所属银行</th>
					<th>交易状态</th>
					<th>备注</th>
				</tr>
				<s:iterator value="recordList" status="st">
					<tr>
						<td>${batchNo }</td>
						<td class="Form_Content"><fmt:formatDate
								value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td class="Form_Content"> <fmt:formatNumber pattern="#0.00" value="${transferAmount}"></fmt:formatNumber></td>
						<td class="Form_Content">
						<script type="text/javascript">
												var bankNoLength = '${sourceAccountNo}'.length;
												window.document.write('${sourceAccountNo}'.substring(0,4));
												window.document.write("****" + '${sourceAccountNo}'.substring(bankNoLength-4,bankNoLength));
						</script>
						</td>

						<td class="Form_Content">${sourceBankName }</td>
						<td class="Form_Content">
						<c:forEach items="${WithdrawRemittanceRecordEnum}" var="WithdrawRemittanceRecordEnum">
						<c:if test="${transferStatus==WithdrawRemittanceRecordEnum.value}">${WithdrawRemittanceRecordEnum.desc}</c:if>
						</c:forEach>
						</td>
						<td class="Form_Content">${remark }</td>
					</tr>
				</s:iterator>
			</table>
			<c:if test="${pageBean.totalCount>0}">
			<div class="pageCla">
				<z:page pageBean="${pageBean }" currentPage="${pageNum }"
					url="withdraw_listRemittanceRecord.action"
					parameter="&beginDate=${beginDate }&endDate=${endDate}"></z:page>
			</div>
			</c:if>
			</div>
	</form>
	<br style="clear: both;" />

	<div class="clear"></div>
</div>

	<jsp:include page="../../foot.jsp" />
</body>
</html> --%>

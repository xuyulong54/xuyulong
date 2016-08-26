<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript">
function channelTransSummaryExportExecl() {
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var ylmerchantno = $("#ylmerchantno").val();
	var channelMerchName = $("#channelMerchName").val();
	var channelCode = $("#channelCode").val();
	
	location.href="channelTrans_channelTransSummaryExportExecl.action?startDate="+startDate+"&endDate="+endDate+"&ylmerchantno="+ylmerchantno+"&channelCode="+channelCode+"&channelMerchName="+encodeURI(encodeURI(channelMerchName));
    //       location.href="channelTrans_channelTransExportExecl.action?startDate="+startDate+"&endDate="+endDate+"&ylmerchantno="+ylmerchantno+"&channelMerchName="+encodeURI(encodeURI(channelMerchName));
}
</script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="channelTrans_toChannelTransSummary.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>清算日期： <input type="text" name="startDate"
						value="${fn:substring(startDate,0,10)}" id="startDate"
						class="date" size="10" readonly="readonly" />-<input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="10" readonly="readonly" />
					</td>
					<td>
						<span>渠道商户编号：</span>
						<input type="text" id="channelMerchNo" name="channelMerchNo" value="${channelMerchNo}" size="20"  alt="精确搜索"/>
					</td>
					<td>
						<span>渠道商户名称：</span>
						<input type="text" id="channelMerchName" name="channelMerchName" value="${channelMerchName}" size="20"  alt="模糊搜索"/>
					</td>
					</tr><tr>
					<td colspan="2">
						<span>渠道名称：</span>
						<select name="channelCode" id="channelCode">
							<option value="">请选择</option>
							<c:forEach items="${channelInfoList }" var="v">
								<option value="${v.channelCode }" <c:if test="${v.channelCode eq  channelCode}">selected="selected"</c:if> >${v.channelName}</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="button" onclick="channelTransSummaryExportExecl();">报表导出</button>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<div class="panelBar" style="height: 38px;">
		<ul class="toolBar" >
			<table style="width: 80%; padding-left: 10px;">
				<tr>
					<td height="20">总笔数：</td>
					<td><b style="color:red;">${cts.totalCount}</b>（笔）</td>
					<td>借记总笔数：</td>
					<td><b style="color:red;">${cts.debitCount}</b>（笔）</td>
					<td>贷记总笔数：</td>
					<td><b style="color:red;">${cts.creditCount}</b>（笔）</td>
					<td>总手续费：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.totalFee }" type="currency" /></b></td>
					<td>平台利润：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.platProfit }" type="currency" /></b></td>
				</tr>
				<tr>
					<td>总金额：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.totalAmount }" type="currency" /></b></td>
					<td>借记总金额：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.debitAmount }" type="currency" /></b></td>
					<td>贷记总金额：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.creditAmount }" type="currency" /></b></td>
					<td>渠道成本：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.channelCost }" type="currency" /></b></td>
					<td>代理商利润：</td>
					<td><b style="color:red;"><fmt:formatNumber value="${cts.agentProfit }" type="currency" /></b></td>
				</tr>
			</table>
		</ul>
	</div>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="173">
		<thead>
			<tr>
				<th>序号</th>
				<th>清算日期</th>
				<th>渠道</th>
				<th>渠道商户</th>
				<th>借记总笔数</th>
				<th>借记总金额</th>
				<th>贷记总笔数</th>
				<th>贷记总金额</th>
				<th>总笔数</th>  
				<th>总金额</th> 
				<th>渠道成本</th>
				<th>平台利润</th>
				<th>代理商利润</th>
				<th>总手续费</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user">
					<td align="center">${st.index+1}</td>
					<td align="center">${trxDate}</td>
					<td align="center">
						${channelCode}<br/>
						${channelName}
					</td>
					<td>
						${channelMerchNo}<br/>
						${channelMerchName}
					</td>
					<td align="right">${debitCount }</td>
					<td align="right"><fmt:formatNumber value="${debitAmount }" type="currency" /></td>
					<td align="right">${creditCount }</td>
					<td align="right"><fmt:formatNumber value="${creditAmount }" type="currency" /></td>
					<td align="right">${totalCount }</td>
					<td align="right"><fmt:formatNumber value="${totalAmount }" type="currency" /></td>
					<td align="right"><fmt:formatNumber value="${channelCost }" type="currency" /></td>
					<td align="right"><fmt:formatNumber value="${platProfit }" type="currency" /></td>
					<td align="right"><fmt:formatNumber value="${agentProfit }" type="currency" /></td>
					<td align="right"><fmt:formatNumber value="${totalFee }" type="currency" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

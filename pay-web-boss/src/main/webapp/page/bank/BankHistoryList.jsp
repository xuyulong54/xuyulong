<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="bank_listAccountHistory.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					银行账户：<input type="text" name="bankName" value="${bankName}" size="30" alt="模糊查询"  />
				</td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
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
			<li></li>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="112">
		<thead>
			<tr>
				<th>序号</th>
				<th>银行账户ID</th>
				<th>银行协议ID</th>
				<th>变动余额</th>
				<th>手续费</th>
				<th>余额</th>
				<th>资金变动方向</th>
				<th>类型</th>
				<th>批次号</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${bankAccountId }</td>
					<td>${bankAgreementId }</td>
					<td>${amount }</td>
					<td>${fee }</td>
					<td>${balance }</td>
					<td>${fundDirection }</td>
					<td>${type }</td>
					<td>${batchCode }</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    
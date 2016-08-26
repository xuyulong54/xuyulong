<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="contract_onlineMerchantLookupList.action">
<%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="contract_onlineMerchantLookupList.action" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						商户编号：<input type="text" name="merchantNo" value="${merchantNo }" size="30" alt="精确搜索" />
					</td>
					<td>
						商户全称：
						<input type="text" name="fullName" value="${fullName }" size="28" alt="精确搜索" />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="100">
		<thead>
			<tr>
				<th>序号</th>
				<th>商户编号</th>
				<th>公司全称</th>
				<th>商户状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}"
					onclick="$.bringBack({merchantName:'${fullName}', merchantId:'${id}', merchantNo:'${merchantNo }'})" >
					<td>${st.index+1}</td>
					<td>${merchantNo }</td>
				    <td>${fullName}</td>
					<td>
						<c:forEach items="${MerchantStatusEnum }" var="m">
							<c:if test="${status eq m.value }">${m.desc }</c:if>
						</c:forEach>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
   <%@include file="/page/inc/pageBarLookup.jsp"%>
</div>
    
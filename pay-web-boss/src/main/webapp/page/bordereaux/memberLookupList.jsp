<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="bordereaux_memberLookupList.action">
<%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="bordereaux_memberLookupList.action" method="post">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					会员编号：<input type="text" name="memberNo" value="${memberNo }" size="30" alt="精确搜索" />
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
				<th>会员编号</th>
				<th>真实姓名</th>
				<th>会员状态</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}" ondblclick="$.bringBack({memberNo:'${memberNo }', memberId:'${id}'})" >
					<td>${st.index+1}</td>
					<td>${memberNo}</td>
					<td>${realName}</td>
					<td>
						<s:if test="status=='101'"><font color="#C5260A">冻结</font></s:if>
						<s:if test="status=='102'"><font color="#C5260A">注销</font></s:if>
						<s:elseif test="status=='100'">已激活</s:elseif>
						<s:else>--</s:else>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBarLookup.jsp"%>
</div>
    
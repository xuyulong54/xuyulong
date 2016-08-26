<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="FeeDimension_dimensionLookupList.action">
	<%@include file="/page/inc/pageForm.jsp"%>
</form>
<div class="pageHeader">
	<form rel="pagerForm" onsubmit="return dwzSearch(this, 'dialog');" action="FeeDimension_dimensionLookupList.action" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>产品名称：<input type="text" name="payProductName" size="30" alt="精确搜索" value="${payProductName }" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div></li>
							</ul>
						</div>
					</td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="103" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th>节点名称</th>
				<th>产品编号</th>
				<th>产品名称</th>
				<th>方式编号</th>
				<th>操作</th>
				<!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}" ondblclick="$.bringBack({id:'${dimensionId}', payProductName:'${payProductName}'});">
					<td>${st.index+1}</td>
					<td>${nodeName}</td>
					<td>${payProduct }</td>
					<td>${payProductName}</td>
					<td>${frpCode}<br>【${bankChannelCode}】</td>
					<td><a class="btnSelect" href="javascript:$.bringBack({id:'${dimensionId}', payProductName:'${payProductName}'})" title="查找带回">选择</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBarLookup.jsp"%>
</div>

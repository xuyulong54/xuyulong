<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<form id="pagerForm" method="post" action="sales_listSalesBy.action?salesId=${salesId}">
	<!-- 分页表单参数 -->
	<%@include file="/page/inc/pageForm.jsp"%>
</form>
<input type="hidden" name="navTabId" value="list">
<input type="hidden" name="callbackType" value="closeCurrent">
<input type="hidden" name="forwardUrl" value="">
<div class="pageHeader">
	<form onsubmit="return dwzSearch(this, 'dialog');" action="sales_listSalesBy.action?salesId=${salesId}" method="post">
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>销售人员编号：<input type="text" name="salesNo" value="${salesNo }" size="15" alt="精确搜索" /></td>
					<td>销售人员姓名：<input type="text" name="salesName" value="${salesName }" size="15" alt="精确搜索" /></td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="100">
		<thead>
			<tr>
				<th>序号</th>
				<th>销售人员编号</th>
				<th>销售人员姓名</th>
				<th>联系手机</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}" 
				ondblclick="$.bringBack({backSalesId:'${id}', backSalesName:'${salesName }', backSalesNo:'${salesNo }'});">
					<td >${st.index+1}</td>
					<td>${salesNo }</td>
					<td>${salesName }</td>
					<td>${mobile }</td>
					<td >
						<c:if test="${status == 100 }">激活</c:if>
						<c:if test="${status == 101 }">冻结</c:if>
					</td>
					<td>
						<a class="btnSelect" href="javascript:$.bringBack({backSalesId:'${id}', backSalesName:'${salesName }', backSalesNo:'${salesNo }'})" title="查找带回">选择</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBarLookup.jsp"%>
</div>

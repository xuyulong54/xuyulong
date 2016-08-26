<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="sales_listSales.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					销售人员编号：<input type="text" name="salesNo" value="${salesNo }" size="15" alt="精确搜索" />
				</td>
				<td>
					销售人员姓名：<input type="text" name="salesName" value="${salesName }" size="15" alt="精确搜索" />
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
			<z:permission value="sales:member:add">
			<li><a class="add" href="sales_toAddUI.action" target="dialog" rel="input"  title="添加销售人员"><span>添加销售人员</span></a></li>
			</z:permission>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>销售人员编号</th>
				<th>销售人员姓名</th>
				<th>联系手机</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>备注</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td >${st.index+1}</td>
					<td>${salesNo }</td>
					<td>${salesName }</td>
					<td>${mobile }</td>
					<td >
						<c:if test="${status == 100 }">激活</c:if>
						<c:if test="${status == 101 }">冻结</c:if>
					</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${description}</td>
					<td>
						<z:permission value="sales:member:edit">
							<a href="sales_toEditUI.action?id=${id}" title="修改" target="dialog" style="color:blue">修改</a>
						</z:permission>
						<z:permission value="sales:member:moveMerchant">
							<a href="sales_moveMerchantUI.action?id=${id}" rel="newSalesPage" title="移交该销售的所有客户" target="dialog" style="color:blue">移交客户</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
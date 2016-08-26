<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="notifyRecordLog_getNotifyRecordLogList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					交易流水号：<input type="text" name="trxNo" value="${trxNo}" size="30" alt="精确查询"  />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="112">
		<thead>
			<tr>
				<th>序号</th>
				<th>ID</th>
				<th>通知记录ID</th>
				<th>创建时间</th>
				<th>请求信息</th>
				<th>返回信息</th>
				<th>商户ID</th>
				<th>交易流水号</th>
				<th>HTTP状态</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td >${st.index+1}</td>
					<td >${id }</td>
					<td>${notifyId }</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${request }</td>
					<td><s:property value="response"/></td>
					<td>${merchantId }</td>
					<td>${trxNo }</td>
					<td>${http_status }</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
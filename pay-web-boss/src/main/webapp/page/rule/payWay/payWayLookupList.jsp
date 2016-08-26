<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="payProduct_list.action" method="post">

		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>支付方式名称：<input type="text" name="payWayName" value="${payWayName }" size="40" alt="精确搜索" />
					</td>
					<td>支付产品编号：<input type="text" name="payProductCode" value="${payProductCode }" size="40" alt="精确搜索" />
					</td>
				</tr>
				<tr>
					<td>支付渠道编号 ：<input type="text" name="payWayCode" value="${payWayCode }" size="40" alt="精确搜索" />
					</td>
					<td></td>
				</tr>
				<tr>

					<td></td>
					<td>
						<div class="subBar" style="float: right;">
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

	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="payProduct:info:add">
				<li><a class="add" href="payWay_addView.action" target="dialog" title="新建支付方式"><span>新建支付方式</span></a></li>
			</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<th style="width: 40px;">序号</th>
				<th>支付方式名称</th>
				<th>支付产品编号</th>
				<th>支付渠道编号</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>新增/修改支付方式</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${payWayName}</td>
					<td>${payProductCode}</td>
					<td>${payWayCode}</td>
					<td>${status }</td>
					<td>${createTime}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><a class="btnSelect" href="javascript:$.bringBack({payWayName:'${payWayName}',payWayCode:'${payWayCode}',bankChannelCode:'${defaultPayInterface}'})" title="查找带回">选择</a></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

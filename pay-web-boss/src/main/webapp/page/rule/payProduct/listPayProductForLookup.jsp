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
					<td>支付产品名称：<input type="text" name="payProductName" value="${payProductName }" size="40" alt="精确搜索" />
					</td>
					<td>支付产品编号：<input type="text" name="payProductCode" value="${payProductCode }" size="40" alt="精确搜索" />
					</td>
					<td>
						<%-- 	商户类型：
					<select name="merchantType" id="merchantType">
						<option value="">请选择</option>
						<c:forEach items="${merchantTypeList }" var="model">
							<option value="${model.value }" <c:if test="${merchantType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select> --%>
					</td>

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
			<z:permission value="payrule:product:add">
				<li><a class="add" href="payProduct_addView.action" target="dialog" title="新建支付产品"><span>新建支付产品</span></a></li>
			</z:permission>
			<z:permission value="payrule:product:edit">
				<li><a class="edit" href="payProduct_editView.action?id={sid_user}" target="dialog" title="修改支付产品"><span>修改支付产品</span></a></li>
			</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<th style="width: 40px;">序号</th>
				<th>支付产品编号</th>
				<th>支付产品名称</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>查看/修改支付方式</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${payProductCode}</td>
					<td>${payProductName }</td>
					<td><c:if test="${status == PayProductStatusEnum.ACTIVITY.value }">${PayProductStatusEnum.ACTIVITY.desc }</c:if> <c:if test="${status == PayProductStatusEnum.INACTIVITY.value }">${PayProductStatusEnum.INACTIVITY.desc }</c:if></td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><z:permission value="payrule:payway:view">
							<a href="payWay_editUI.action?payProductCode=${payProductCode}&payProductName=${payProductName}" rel="payWayList" title="${payProductName }支付方式列表" target="navTab">查看/修改</a>
						</z:permission></td>
					<td>
						<!-- &nbsp;&nbsp;修改 -->
						<a class="btnSelect" href="javascript:$.bringBack({payProductCode:'${payProductCode}', payProductName:'${payProductName}'})" title="查找带回">选择</a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

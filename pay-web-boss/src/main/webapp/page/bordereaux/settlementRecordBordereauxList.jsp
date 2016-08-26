<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bordereaux_merchantBordereaux.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<%--<td>状态： <select name="status" id="status">
							<option value="">全部</option>
							<c:forEach var="status" items="${merchantStatusList}">
								<option value="${status.value}" <c:if test="${status.value eq status}">selected="selected"</c:if>>${status.desc}</option>
							</c:forEach>
					</select>
					</td>
					--%><td>选择统计时间段： <input type="text" name="beginDate"
						value="${fn:substring(beginDate,0,10)}" id="beginDate"
						class="date" size="10" readonly="true" />- <input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="10" readonly="true" /></td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li>商户总数：<font color="red"><s:if test="${countResultMap.ALL eq null}">0</s:if>${countResultMap.ALL}<s:else></s:else></font>个</li>
			<li>冻结商户数：<font color="red">${countResultMap.INACTIVE}</font>个</li>
			<li>激活商户数：<font color="red">${countResultMap.ACTIVE}</font>个</li>
			<li>审核不通过商户数：<font color="red">${countResultMap.NOPASS}</font>个</li>
			<li>注册中商户数：<font color="red">${countResultMap.REGING}</font>个</li>
			<li>创建中商户数：<font color="red">${countResultMap.MERCHANTS}</font>个</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>账户名称</th>
				<th>商户编号</th>
				<th>商户类型</th>
				<th>商户简称</th>
				<th>签约名</th>
				<th>商户状态</th>
				<th>注册日期</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${loginName }</td>
					<td>${merchantNo}</td>
					<td>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value eq merchantType }">${model.desc }</c:if>
						</c:forEach>
					</td>
				    <td>${shortName}</td>
					<td>
						${signName}
					</td>
					<td>
						<c:forEach items="${merchantStatusList }" var="model">
							<c:if test="${model.value eq status }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="merchant_merchantRemindList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					到期提醒：
					<select name="remindType" id="remindType">
						<option value="1" <c:if test="${remindType eq 1 }">selected="selected"</c:if> selected="selected">合同到期</option>
						<option value="2" <c:if test="${remindType eq 2 }">selected="selected"</c:if> >身份证到期</option>
						<option value="3" <c:if test="${remindType eq 3 }">selected="selected"</c:if> >营业执照到期</option>
					</select>
				</td>
				<td>
					商户全称：<input type="text" name="fullName" value="${fullName }" size="15"  alt="精确搜索"/>
				</td>
				<td>
					商户类型：
					<select name="merchantType" id="merchantType">
						<option value="">请选择</option>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value != 13 }">
							<option value="${model.value }" <c:if test="${merchantType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<td>
					<div class="subBar" style="float: right;">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="103" >
		<thead>
			<tr>
				<th style="width: 40px;">序号</th>
				<th>商户全称</th>
				<th>商户编号</th>
				<th>商户类型</th>
				<th>身份证到期日期</th>
				<th>营业执照到期日期</th>
				<th>商户状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${fullName }</td>
					<td>${merchantNo}</td>
					<td>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value eq merchantType }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						${certificateExpiry}
					</td>
					<td>
						<s:date name="licenseNoValid" format="yyyy-MM-dd" />
					</td>
					<td>
						<c:forEach items="${merchantStatusList }" var="model">
							<c:if test="${model.value eq status }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<z:permission value="merchant:info:remindList">
							<a href="merchant_viewMerchant.action?id=${id}" title="查看商户详细信息" target="navTab" style="color:blue">查看</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
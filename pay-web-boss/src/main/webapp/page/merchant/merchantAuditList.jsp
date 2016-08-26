<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="merchant_listAuditMerchant.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
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
				<td >
					商户编号：<input type="text" name="merchantNo" value="${merchantNo }" size="15"  alt="精确搜索"/>
				</td>
				<td>
					商户简称：<input type="text" name="shortName" value="${shortName }" size="15"  alt="精确搜索"/>
				</td>
				<td>
					注册时间：
					<input type="text" name="beginDate" value="${beginDate}" id="beginDate" class="date" size="8" readonly="readonly" />-
					<input type="text" name="endDate" value="${endDate}" id="endDate" class="date" size="8" readonly="readonly" />
				</td>
				<td>
					<div class="subBar" style="float: right;">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="auditMerchantBt">查询</button></div></div></li>
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
				<th>序号</th>
				<th>机构编号</th>
				<th>商户编号</th>
				<th>商户类型</th>
				<th>商户简称</th>
				<th>商户全称</th>
				<th>商户状态</th>
				<th>注册时间</th>
				<th>操作</th> <!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${parentUserNo}</td>
					<td>${merchantNo}</td>
					<td>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value eq merchantType }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>${shortName}</td>
					<td>${fullName}</td>
					<td>
						<c:forEach items="${merchantStatusList }" var="model">
							<c:if test="${model.value eq status }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<z:permission value="merchant:info:auditMerchantOpt">
							<a href="merchant_viewMerchant.action?id=${id}&isAudit=1" title="审核商户详细信息" rel="addMerchant" target="navTab" style="color:blue">审核</a>&nbsp;
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
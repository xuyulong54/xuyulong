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
					<td>选择统计时间段： <input type="text" name="beginDate"
						value="${fn:substring(beginDate,0,10)}" id="beginDate"
						class="date" size="10" readonly="true" />- <input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="10" readonly="true" /></td>
					<td>商户状态： <select name="status" id="status">
							<option value="">全部</option>
							<c:forEach var="merchantStatus" items="${merchantStatusList}">
								<option value="${merchantStatus.value}"
									<c:if test="${merchantStatus.value eq status}">selected="selected"</c:if>>${merchantStatus.desc}</option>
							</c:forEach>
					</select>
					</td>
					<td>商户类型： <select name="merchantType" id="merchantType">
							<option value="">全部</option>
							<c:forEach var="type" items="${merchantTypeList}">
								<option value="${type.value}"
									<c:if test="${type.value eq merchantType}">selected="selected"</c:if>>${type.desc}</option>
							</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>商户简称：<input type="text" name="shortName" value="${shortName}" alt="精确查询"> </td>
					<td>商户编号：<input type="text" name="merchantNo" value="${merchantNo}" alt="精确查询"> </td>
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
			<z:permission value="bordereaux:merchant:view">
			<li><a title="确定导出这些记录吗?" class="icon" href="bordereaux_merchantBordereauxExportExecl.action?beginDate=${fn:substring(beginDate,0,10) }&endDate=${fn:substring(endDate,0,10)}&status
							=${status}&merchantType=${merchantType}&shortName=${shortNames}&merchantNo=${merchantNo}" target="dwzExport" targettype="navTab"> <span>导出EXCEL</span> </a></li>
			</z:permission>
			<li>
				<span>
					商户总数：<c:if test="${countResultMap.alls eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.alls ne null}">
						<font color="red">${countResultMap.alls}</font>个</c:if>
					冻结商户数：<c:if test="${countResultMap.inActive eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.inActive ne null}">
						<font color="red">${countResultMap.inActive}</font>个</c:if>
				        激活商户数：<c:if test="${countResultMap.active eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.active ne null}">
						<font color="red">${countResultMap.active}</font>个</c:if>
				        审核不通过商户数：<c:if test="${countResultMap.noPass eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.noPass ne null}">
						<font color="red">${countResultMap.noPass}</font>个</c:if>
				        注册中商户数：<c:if test="${countResultMap.reging eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.reging ne null}">
						<font color="red">${countResultMap.reging}</font>个</c:if>
				        创建中商户数：<c:if test="${countResultMap.merchants eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.merchants ne null}">
						<font color="red">${countResultMap.merchants}</font>个</c:if>
				        已注销商户数：<c:if test="${countResultMap.cell eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.cell ne null}">
						<font color="red">${countResultMap.cell}</font>个</c:if>
				</span>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th>注册时间</th>
				<th>商户编号</th>
				<th>商户类型</th>
				<th>商户简称</th>
				<th>商户状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /> </td>
					<td>${merchantNo}</td>
					<td><c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value eq merchantType }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>${shortName}</td>
					<td><c:forEach items="${merchantStatusList }" var="model">
							<c:if test="${model.value eq status }">${model.desc }</c:if>
						</c:forEach>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

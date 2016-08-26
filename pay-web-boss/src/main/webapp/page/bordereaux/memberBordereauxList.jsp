<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bordereaux_memberBordereaux.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>选择统计时间段： <input type="text" name="startDate"
						value="${fn:substring(startDate,0,10)}" id="startDate"
						class="date" size="10" readonly="true" />- <input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="10" readonly="true" />
					</td>
					<td>
						<span>真实姓名：</span>
						<input type="text" id="realName" name="realName" value="${realName}" size="20"  alt="模糊搜索"/>
					</td>
					<td>
					<span>状态：</span>
						<select name="status" id="status">
							<option value="">--请选择--</option>
							<c:forEach items="${memberStatusList }" var="model">
								<option value="${model.value }"
								<c:if test="${status eq model.value}">selected="selected"</c:if>>${model.desc }</option>
							</c:forEach>
						</select>
					</td>
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
			<li><a title="确定导出这些记录吗?" class="icon" target="dwzExport" targettype="navTab"
					href="bordereaux_memberBordereauxExportExecl.action?startDate=${fn:substring(startDate,0,10) }&endDate=${fn:substring(endDate,0,10)}
							&realName=${realName}&status=${status}"> <span>导出EXCEL</span> </a></li>
			<li>
				<span>
					会员总数：<c:if test="${countResultMap.alls eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.alls ne null}">
						<font color="red">${countResultMap.alls}</font>个</c:if>
					冻结会员数：<c:if test="${countResultMap.inactive eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.inactive ne null}">
						<font color="red">${countResultMap.inactive}</font>个</c:if>
					激活会员数：<c:if test="${countResultMap.active eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.active ne null}">
						<font color="red">${countResultMap.active}</font>个</c:if>
					注册中会员数：<c:if test="${countResultMap.signing eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.signing ne null}">
						<font color="red">${countResultMap.signing}</font>个</c:if>
					已销户会员数：<c:if test="${countResultMap.cell eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.cell ne null}">
						<font color="red">${countResultMap.cell}</font>个</c:if>
					实名认证审核不通过数：<c:if test="${countResultMap.realnopass eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.realnopass ne null}">
						<font color="red">${countResultMap.realnopass}</font>个</c:if>
					认证审核通过数：<c:if test="${countResultMap.realpass eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.realpass ne null}">
						<font color="red">${countResultMap.realpass}</font>个</c:if>
					认证审核中数：<c:if test="${countResultMap.realreging eq null}">
						<font color="red">0</font>个</c:if> <c:if
						test="${countResultMap.realreging ne null}">
						<font color="red">${countResultMap.realreging}</font>个</c:if>
				</span>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>注册时间</th>
				<th>会员编号</th>
				<th>真实姓名</th>
				<th>身份证号</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${memberNo}</td>
					<td>${realName}</td>
					<td>${cardNo}</td>
					<td>
						<c:forEach items="${memberStatusList}" var="statu">
							<c:if test="${status eq statu.value}">${statu.desc }</c:if>
						</c:forEach>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

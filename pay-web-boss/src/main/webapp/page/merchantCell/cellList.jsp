<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="merchantCell_list.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					登录名：
					<input type="text" name="loginName" id="loginName" value="${loginName }" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td >
					商户编号：
					<input type="text" name="userNo" id="userNo" value="${userNo }" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					审核状态：
					<select name="auditStatus" id="auditStatus">
						<option value="">请选择</option>
						<c:forEach items="${userAuditStatusEnum }" var="model">
							<option value="${model.value }" <c:if test="${auditStatus eq model.value }">selected="selected"</c:if> >${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<div class="subBar" >
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="103">
		<thead>
			<tr>
				<th>序号</th>
				<th>登录名</th>
				<th>商户编号</th>
				<th>创建时间</th>
				<th>申请变更状态</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${loginName}</td>
				    <td>${userNo}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						注销
					</td>
					<td>
						<c:forEach items="${userAuditStatusEnum }" var="model">
							<c:if test="${auditStatus eq model.value }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:if test="${auditStatus eq waitEnum }">
							<z:permission value="merchant:cell:audit">
								<a title="商户注销审核" href="merchantCell_view.action?id=${id }" style="color:blue" target="dialog" >审核</a>&nbsp;
							</z:permission>
						</c:if>
						<z:permission value="merchant:cell:view">
							<a title="查看商户注销详情" href="merchantCell_view.action?id=${id }&isView=1" style="color:blue" target="dialog" >查看</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
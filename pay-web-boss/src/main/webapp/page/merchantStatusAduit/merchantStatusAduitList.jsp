<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="userAuditRecordStatus_list.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					商户全称：
					<input type="text" name="fullName" id="fullName" value="${fullName }" size="30" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td >
					商户编号：
					<input type="text" name="userNo" id="userNo" value="${userNo }" size="30" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					审核状态：
					<select name="auditStatus" id="auditStatus" >
						<option value="1">请选择</option>
						<c:forEach items="${UserAuditStatusEnumList }" var="model">
							<option value="${model.value }" <c:if test="${auditStatus eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					审核类型：
					<select name="currentStatus" id="currentStatus" >
						<option value="">请选择</option>
						<option value="${UserStatusEnum.CREATED.value }" <c:if test="${currentStatus eq UserStatusEnum.CREATED.value }">selected="selected"</c:if>>信息审核</option>
						<option value="${UserStatusEnum.ACTIVE.value }" <c:if test="${currentStatus eq UserStatusEnum.ACTIVE.value }">selected="selected"</c:if>>状态审核</option>
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
				<th>商户全称</th>
				<th>商户编号</th>
				<th>创建时间</th>
				<th>当前状态</th>
				<th>申请变更状态</th>
				<th>处理状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${fullName}</td>
				    <td>${userNo}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:forEach items="${UserStatusEnumList }" var="model">
							<c:if test="${model.value eq currentStatus }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${UserStatusEnumList }" var="model">
							<c:if test="${model.value eq changeStatus }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${UserAuditStatusEnumList }" var="model">
							<c:if test="${model.value eq auditStatus }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">
							<z:permission value="merchant:status:audit">
								<a title="商户状态变更审核" href="userAuditRecordStatus_view.action?id=${id }" style="color:blue" target="dialog" >审核</a>&nbsp;
							</z:permission>
						</c:if>
						<c:if test="${auditStatus ne UserAuditStatusEnum.WAIT.value }">
							<z:permission value="merchant:status:view">
								<a title="查看商户状态变更" href="userAuditRecordStatus_view.action?id=${id }&isView=1" style="color:blue" target="dialog" >查看</a>
							</z:permission>
						</c:if>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
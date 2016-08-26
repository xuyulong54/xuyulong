<%-- 权限模块:角色管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pms_listPmsRole.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					角色名称：<input type="text" name="roleName" value="${roleName}" size="30" alt="模糊查询"  />
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

	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="pms:role:add">
				<li><a class="add" href="pms_addPmsRoleUI.action" target="dialog" width="550" height="300" rel="input" title="添加角色"><span>添加角色</span></a></li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="131">
		<thead>
			<tr>
				<th>序号</th>
				<th>角色名称</th>
				<th>角色类型</th>
				<th>描述</th>
				<th>创建时间</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
		    	<%-- 普通操作员看不到超级管理员角色 --%>
		    	<c:if test="${(roleType eq RoleTypeEnum.ADMIN.value && type eq OperatorTypeEnum.ADMIN.value) || (roleType eq RoleTypeEnum.USER.value)}">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${roleName }</td>
					<td>
						<c:forEach items="${RoleTypeEnumList}" var="roleTypeEnum">
							<c:if test="${roleType ne null and roleType eq roleTypeEnum.value}">${roleTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>${remark}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<z:permission value="pms:role:assignpermission">
							[<a href="pms_assignPermissionUI.action?roleId=${id}" title="为角色【${roleName}】分配权限" target="dialog" width="950" style="color:blue">分配权限</a>]
						</z:permission>
						<z:permission value="pms:role:edit">
							&nbsp;[<a href="pms_editPmsRoleUI.action?roleId=${id}" title="修改角色【${roleName}】" target="dialog" width="550" height="300" rel="input" style="color:blue">修改</a>]
						</z:permission>
						<z:permission value="pms:role:delete">
							<c:if test="${roleType eq RoleTypeEnum.USER.value}">
							&nbsp;[<a href="pms_deletePmsRole.action?roleId=${id}" title="删除角色【${roleName}】" target="ajaxTodo" style="color:blue">删除</a>]
							</c:if>
						</z:permission>
					</td>
				</tr>
				</c:if>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
    
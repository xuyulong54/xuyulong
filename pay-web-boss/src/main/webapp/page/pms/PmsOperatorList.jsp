<%-- 权限模块:操作员管理:分页查看页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pms_listPmsOperator.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					操作员登录名：<input type="text" name="loginName" value="${loginName}" size="30" alt="精确查询"  />
				</td>
				<td>
					操作员姓名：<input type="text" name="realName" value="${realName}" size="30" alt="模糊查询"  />
				</td>
				<td>状态：</td>
				<td>
					<select name="status" class="combox">
						<option value="">-全部-</option>
						<c:forEach items="${OperatorStatusEnumList}" var="operatorStatus">
						<option value="${operatorStatus.value}"
							<c:if test="${status ne null and status eq operatorStatus.value}">selected="selected"</c:if>>
							${operatorStatus.desc}
						</option>
					</c:forEach>
					</select>
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
			<z:permission value="pms:operator:add">
				<li><a class="add" href="pms_addPmsOperatorUI.action" target="dialog" rel="input" title="添加操作员"><span>添加操作员</span></a></li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>操作员登录名</th>
				<th>操作员姓名</th>
				<th>手机号码</th>
				<th>状态</th>
				<th>类型</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
		    	<%-- 普通操作员看不到超级管理员信息 --%>
		    	<c:if test="${(type eq OperatorTypeEnum.ADMIN.value && type eq OperatorTypeEnum.ADMIN.value) || (type eq OperatorTypeEnum.USER.value)}">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${loginName }</td>
					<td>${realName }</td>
					<td>${mobileNo }</td>
					<td>
						<c:forEach items="${OperatorStatusEnumList}" var="operatorStatus">
							<c:if test="${status ne null and status eq operatorStatus.value}">${operatorStatus.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${OperatorTypeEnumList}" var="operatorType">
							<c:if test="${type ne null and type eq operatorType.value}">${operatorType.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<z:permission value="pms:operator:view">
							[<a href="pms_viewPmsOperatorUI.action?id=${id}" title="查看【${loginName }】详情" target="dialog" style="color:blue">查看</a>]
						</z:permission>
						<c:if test="${type eq OperatorTypeEnum.USER.value }">
							<z:permission value="pms:operator:edit">
								&nbsp;[<a href="pms_editPmsOperatorUI.action?id=${id}" title="修改【${loginName }】" target="dialog" rel="operatorUpdate" style="color:blue">修改</a>]
							</z:permission>
							<z:permission value="pms:operator:resetpwd">
								&nbsp;[<a href="pms_resetOperatorPwdUI.action?id=${id}" title="重置【${loginName }】的密码" target="dialog" width="550" height="300" style="color:blue">重置密码</a>]
							</z:permission>
							<z:permission value="pms:operator:changestatus">
								<c:if test="${type eq OperatorTypeEnum.USER.value && status==OperatorStatusEnum.ACTIVE.value}">
								&nbsp;[<a href="pms_changeOperatorStatus.action?id=${id}" title="冻结【${loginName }】" target="ajaxTodo" style="color:blue">冻结</a>]
								</c:if>
								<c:if test="${type eq OperatorTypeEnum.USER.value &&status==OperatorStatusEnum.INACTIVE.value}">
								&nbsp;[<a href="pms_changeOperatorStatus.action?id=${id}" title="激活【${loginName }】" target="ajaxTodo" style="color:blue">激活</a>]
								</c:if>
							</z:permission>
							<z:permission value="pms:operator:delete">
								<c:if test="${type eq OperatorTypeEnum.USER.value }">
								&nbsp;[<a href="pms_deleteOperatorStatus.action?id=${id}" target="ajaxTodo" title="确定要删除吗？" style="color:blue">删除</a>]
								</c:if>
							</z:permission>
						</c:if>
					</td>
				</tr>
				</c:if>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
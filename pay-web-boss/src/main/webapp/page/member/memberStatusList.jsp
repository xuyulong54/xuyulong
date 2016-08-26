<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="memberInfo_listMemberStatus.action" method="post">
	<!-- 分页表单参数 -->
    	<%@include file="/page/inc/pageForm.jsp"%>
    	<input type="hidden" name="navTabId" value="list">
		<input type="hidden" name="callbackType" value="">
		<input type="hidden" name="forwardUrl" value="">
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					真实姓名：
					<input type="text" name="fullName" id="fullName" value="${fullName }" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td >
					用户编号：
					<input type="text" name="userNo" id="userNo" value="${userNo }" alt="精确搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					审核状态：
					<select name="auditStatus" id="auditStatus">
						<option value="">请选择</option>
						<option value="${UserAuditStatusEnum.AGREE.value }" <c:if test="${auditStatus eq UserAuditStatusEnum.AGREE.value }">selected="selected"</c:if> >审核通过</option>
						<option value="${UserAuditStatusEnum.WAIT.value }" <c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">selected="selected"</c:if> >申请中</option>
						<option value="${UserAuditStatusEnum.REFUSE.value }" <c:if test="${auditStatus eq UserAuditStatusEnum.REFUSE.value }">selected="selected"</c:if> >审核不通过</option>
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
				<th>真实姓名</th>
				<th>用户编号</th>
				<th>创建时间</th>
				<th>当前状态</th>
				<th>申请变更状态</th>
				<th>状态</th>
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
					<td>${currentStatus == UserStatusEnum.ACTIVE.value ? '激活' : '冻结' }</td>
					<td>${changeStatus == UserStatusEnum.ACTIVE.value ? '激活' : '冻结' }</td>
					<td>
						<c:if test="${auditStatus eq UserAuditStatusEnum.AGREE.value }">
							审核通过
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">
							申请中
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.REFUSE.value }">
							审核不通过
						</c:if>
					</td>
					<td>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">
							<z:permission value="member:status:audit">
								<a title="会员状态变更审核" href="memberInfo_toAudit.action?id=${id }&changeStatus=${changeStatus}" style="color:blue" target="dialog" rel="list" >审核</a>&nbsp;
							</z:permission>
						</c:if>
						<z:permission value="member:status:view">
							<a title="查看会员状态变更" href="memberInfo_toAudit.action?id=${id }&isView=1" style="color:blue" target="dialog" >查看</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
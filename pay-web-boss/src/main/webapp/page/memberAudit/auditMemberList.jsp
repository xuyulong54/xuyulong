<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="memberAudit_listMemberAudit.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					登录名：<input type="text" id="loginName" name="loginName" value="${loginName }" size="20" alt="模糊搜索"/>
				</td>
				<td>
					用户编号：<input type="text" id="userNoInput" name="userNo" value="${userNo }" size="20" alt="精确搜索"/>
				</td>
				<td >
					真实姓名：<input type="text" id="realName" name="realName" value="${realName}" size="20" alt="模糊搜索"/>
				</td>
			</tr>
			<tr>
				<td>
					身份证号：<input type="text" id="cardNo" name="cardNo" value="${cardNo}" size="20" alt="精确搜索"/>
				</td>
				<td>
					实名认证状态
					<select name="auditStatus" id="auditStatus">
						<option value="">请选择</option>
						<option value="${UserAuditStatusEnum.AGREE.value}" <c:if test="${auditStatus == UserAuditStatusEnum.AGREE.value }">selected="selected"</c:if> >审核通过</option>
						<option value="${UserAuditStatusEnum.WAIT.value }" <c:if test="${auditStatus == UserAuditStatusEnum.WAIT.value }">selected="selected"</c:if> >申请中</option>
						<option value="${UserAuditStatusEnum.REFUSE.value }" <c:if test="${auditStatus == UserAuditStatusEnum.REFUSE.value }">selected="selected"</c:if> >审核不通过</option>
					</select>
				</td>
				<td>
					注册时间：
					<input type="text" name="beginDate" value="${beginDate }" id="startDate" class="date"  size="10" readonly="true" />-
					<input type="text" name="endDate" value="${endDate }" id="endDate" class="date" size="10" readonly="true" />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>登录名</th>
				<th>用户编号</th>
				<th>真实姓名</th>
				<th>身份证号</th>
				<th>注册时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}" oncontextmenu="return false">
					<td>${st.index+1}</td>
					<td>${loginName}</td>
					<td>${userNo}</td>
				    <td>${realName}</td>
				    <td>${cardNo}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:if test="${auditStatus eq UserAuditStatusEnum.AGREE.value }">
							${UserAuditStatusEnum.AGREE.desc }
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.REFUSE.value }">
							${UserAuditStatusEnum.REFUSE.desc }
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">
							${UserAuditStatusEnum.WAIT.desc }
						</c:if>
					</td>
					<td>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value}">
							<z:permission value="member:audit:audit">
								<a title="会员实名认证审核" href="memberAudit_auditMemberUI.action?id=${id }" style="color:blue" target="dialog" >审核</a>&nbsp;
							</z:permission>
						</c:if>
						<z:permission value="member:audit:view">
							<a title="查看会员实名认证" href="memberAudit_auditMemberUI.action?id=${id }&isView=1" style="color:blue" target="dialog" >查看</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

<script>
	//onkeydown = function(event){
	 // if(event.keyCode == 17) {
		 // return false;
	 //     if(event.keyCode == 67){
	 //   	 return false;
	 //     }
	  //}
	//}
</script>

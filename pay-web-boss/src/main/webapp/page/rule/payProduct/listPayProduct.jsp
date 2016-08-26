<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="payProduct_listPayProduct.action" method="post">
	
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					支付产品名称：<input type="text" name="payProductName" value="${payProductName }" size="40" alt="精确搜索"/>
				</td>
				<td>
					支付产品编号：<input type="text" name="payProductCode" value="${payProductCode }" size="40" alt="精确搜索" />
				</td>
				<td>
					<div class="subBar" style="float: right;">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
						</ul>
					</div>
				</td>
			<%-- 	<td>
					商户类型：
					<select name="merchantType" id="merchantType">
						<option value="">请选择</option>
						<c:forEach items="${merchantTypeList }" var="model">
							<option value="${model.value }" <c:if test="${merchantType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select> 
				</td> --%>
				
			</tr>
		</table>
	</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<z:permission value="payrule:product:add">
				<li><a class="add" href="payProduct_addView.action" target="dialog" width="600" height="350" title="新建支付产品"><span>新建支付产品</span></a></li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="130" >
		<thead>
			<tr>
				<th>序号</th>
				<th>支付产品编号</th>
				<th>支付产品名称</th>
				<th>状态</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${payProductCode}</td>
					<td>
						${payProductName }
					</td>
					<td>
						<c:if test="${status == PayProductStatusEnum.ACTIVITY.value }">${PayProductStatusEnum.ACTIVITY.desc }</c:if>
						<c:if test="${status == PayProductStatusEnum.INACTIVITY.value }">${PayProductStatusEnum.INACTIVITY.desc }</c:if> 
					</td>
				    <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<z:permission value="payrule:payway:func">
							<c:if test="${status == PayProductStatusEnum.ACTIVITY.value }">
								[<a href="payProduct_updateStatus.action?id=${id}&status=${status}" target="ajaxTodo" 
								title="确认要关闭【${payProductName }】？" style="color:blue">关闭</a>]&nbsp;
							</c:if>
							<c:if test="${status == PayProductStatusEnum.INACTIVITY.value }">
								[<a href="payProduct_updateStatus.action?id=${id}&status=${status}" target="ajaxTodo" 
								title="确认要打开【${payProductName }】？" style="color:blue">打开</a>]&nbsp;
							</c:if>
						</z:permission>
						<z:permission value="payrule:payway:bind">
							[<a href="payWay_editUI.action?payProductCode=${payProductCode}&payProductName=${payProductName}" 
							rel="payWayList"  title="${payProductName }-绑定支付方式" target="dialog"
							style="color:blue">绑定支付方式</a>]&nbsp;
						</z:permission>
						<%-- <z:permission value="payrule:product:edit">
							[<a class="edit" href="payProduct_editView.action?id=${id }" target="dialog" 
							width="600" height="350" title="修改支付产品" style="color:blue">修改</a>]
						</z:permission> --%>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="SettControl_listSettControl.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>结算控制方式： <select name="settType">
							<option value="">请选择</option>
							<c:forEach items="${SettModeTypeEnum }" var="enums">
								<option value="${enums.value }" <c:if test="${settType == enums.value }"> selected="selected" </c:if>>${enums.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
					<div class="subBar">
						<ul>
							<li>
								<div class="buttonActive">
									<z:permission value="sett:control:view">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</z:permission>	
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
		<z:permission value="sett:control:add">
			<ul class="toolBar">
				<li>
					<a class="add" href="SettControl_addSettControlUI.action" target="dialog" rel="input" title="添加结算控制"><span>添加结算控制</span></a>
				</li>
			</ul>
		</z:permission>	
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>结算控制方式</th>
				<th>开始时间</th>
				<th>结束时间</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><c:forEach items="${SettModeTypeEnum }" var="enums">
							<c:if test="${settModeType eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
					<td><fmt:formatDate value="${beginTime }" pattern="HH:mm:ss" /></td>
					<td><fmt:formatDate value="${endTime }" pattern="HH:mm:ss" /></td>
					<td><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<z:permission value="sett:control:edit">
							<a href="SettControl_updateSettControlUI.action?settModeType=${settModeType }" target="dialog" title="修改" style="color:blue">修改</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>


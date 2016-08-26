<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="SettRule_listSettRule.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>结算类型： <select name="settType">
							<option value="">请选择</option>
							<c:forEach items="${SettTypeEnum }" var="enums">
								<option value="${enums.value }" <c:if test="${settType eq enums.value }">selected="selected"</c:if>>${enums.desc }</option>
							</c:forEach>
						</select>

					</td>
					<td>用户名称：<input name="userName" type="text" value="${userName}" alt="精确查询">
					
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<z:permission value="sett:rule:view">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="103" nowrapTD="false">
		<thead>
			<tr>
				<th>序号</th>
				<th>账户编号</th>
				<th>用户名称</th>
				<th>结算类型</th>
				<th>风险预存期</th>
				<th>是否可结算</th>
				<th>结算状态</th>
				<th>结算周期</th>
				<th>描述</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${accountNo }</td>
					<td>${userName }</td>
					<td><c:forEach items="${SettTypeEnum }" var="enums">
							<c:if test="${settType eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
					<td>${riskDay }</td>
					<td><c:forEach items="${PublicStatusEnum }" var="enums">
							<c:if test="${isSett eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
					<td><c:forEach items="${SettRuleStatusEnum }" var="enums">
							<c:if test="${currentSettStatus eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
					<td>${settCycleData}</td>
					<td>${remark}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" name="memberForm" onsubmit="return navTabSearch(this);" action="FeeDimension_feeDimensionAllList.action" method="post">
		<input type="hidden" value="${feeNode.id }" name="feeNodeId" />
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>产品编号： <input name="payProductCode" type="text" value="${payProduct }" alt="精确查询">
					</td>
					<td>方式编号：<input name="payWayCode" type="text" value="${payWayCode }" alt="精确查询">
					</td>
					<td>节点名称：<input name="feeNodeName" type="text" value="${feeNodeName }" alt="精确查询">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="103">
		<thead>
			<tr>
				<th>序号</th>
				<th>节点名称</th>
				<th>计费项</th>
				<th>产品编号/名称</th>
				<th>支付方式/接口通道</th>
				<th>创建时间</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${nodeName}</td>
					<td><c:forEach items="${CalculateFeeItemEnum}" var="enums">
							<c:if test="${enums.value eq calculateFeeItem}"> ${enums.desc}</c:if>
						</c:forEach></td>
					<td>${payProduct} (${payProductName})</td>
					<td>${frpCode} (${bankChannelCode})</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
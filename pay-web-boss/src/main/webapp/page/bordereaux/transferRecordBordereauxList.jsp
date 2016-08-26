<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bordereaux_transferRecordBordereaux.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>选择统计时间段： <input type="text" name="beginDate"
						value="${fn:substring(beginDate,0,10)}" id="beginDate"
						class="date" size="10" readonly="true" />- <input type="text"
						name="endDate" value="${fn:substring(endDate,0,10)}" id="endDate"
						class="date" size="10" readonly="true" />
					</td>
					<td>付款人账户：
						<input name="targetLoginName" type="text" value="${targetLoginName}" size="30" alt="精确搜索"/>
					</td>
					<td>收款人账户：
						<input name="sourceLoginName" type="text" value="${sourceLoginName}" size="30" alt="精确搜索"/>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li><a title="确定导出这些记录吗?" class="icon" target="dwzExport" targettype="navTab"
					href="bordereaux_transferRecordBordereauxExportExecl.action?beginDate=${fn:substring(beginDate,0,10) }&endDate=${fn:substring(endDate,0,10)}
							&targetLoginName=${targetLoginName}&sourceLoginName=${sourceLoginName}"> <span>导出EXCEL</span> </a></li>
			<li><span>
				转账订单总数：<c:if test="${countResultMap.alls eq null}"><font color="red">0</font>个</c:if>
				<c:if test="${countResultMap.alls ne null}"><font color="red">${countResultMap.alls}</font>个</c:if>
				转账成功订单数：<c:if test="${countResultMap.success eq null}"><font color="red">0</font>个</c:if>
				<c:if test="${countResultMap.success ne null}"><font color="red">${countResultMap.success}</font>个</c:if>
				转账失败订单总数：<c:if test="${countResultMap.failed eq null}"><font color="red">0</font>个</c:if>
				<c:if test="${countResultMap.failed ne null}"><font color="red">${countResultMap.failed}</font>个</c:if>
				转账成功订单总金额：<c:if test="${countResultMap.successamount eq null}"><font color="red">0</font>元</c:if>
				<c:if test="${countResultMap.successamount ne null}">
				<font color="red"><fmt:formatNumber value="${countResultMap.successamount}" pattern="#0.00" /></font>元</c:if>
				手续费总金额：<c:if test="${countResultMap.platfee eq null}"><font color="red">0</font>元</c:if>
				<c:if test="${countResultMap.platfee ne null}">
				<font color="red"><fmt:formatNumber value="${countResultMap.platfee}" pattern="#0.00" /></font>元</c:if>
			</span></li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>交易流水号</th>
				<th>付款人账户</th>
				<th>收款人账户</th>
				<th>订单金额</th>
				<th>手续费</th>
				<th>转账说明</th>
				<th>交易状态</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td><fmt:formatDate value="${createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${trxNo}</td>
					<td>${targetLoginName}</td>
					<td>${sourceLoginName}</td>
					<td><fmt:formatNumber value="${amount}" pattern="0.00"></fmt:formatNumber></td>
					 <td><fmt:formatNumber value="${platFee }" pattern="0.00"></fmt:formatNumber></td>
					 <td >${remark}</td>
					<td>
						<c:forEach items="${paymentRecordStatus }" var="v">
							<c:if test="${status eq v.value }">${v.desc}</c:if>
						</c:forEach>
				 	</tr>
				</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

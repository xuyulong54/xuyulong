<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bordereaux_refundRecordBordereaux.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>选择统计时间段： <input type="text" name="beginDate"
						value="${beginDate}" id="beginDate"
						class="date" size="10" readonly="true" />- <input type="text"
						name="endDate" value="${endDate}" id="endDate"
						class="date" size="10" readonly="true" /></td>
						
					<td>
					<td>
						原支付接口编号：
						<input type="text" name="orgPayInterfaceCode" value="${orgPayInterfaceCode}" size="20"/>
					</td>
					<td>
						商户全称：
						<input type="text" name="merchantName" value="${merchantName}">
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
					href="bordereaux_refundRecordBordereauxExportExecl.action?beginDate=${beginDate }&endDate=${endDate}&orgPayInterfaceCode
						=${orgPayInterfaceCode}&merchantName=${merchantName}"> <span>导出EXCEL</span> </a></li>
			<li>
				<span>
					订单总数：<c:if test="${countResultMap.alls eq null}"><font color="red">0</font>个</c:if>
					<c:if test="${countResultMap.alls ne null}"><font color="red">${countResultMap.alls}</font>个</c:if>
					成功订单数：<c:if test="${countResultMap.success eq null}"><font color="red">0</font>个</c:if>
					<c:if test="${countResultMap.success ne null}"><font color="red">${countResultMap.success}</font>个</c:if>
					失败订单总数：<c:if test="${countResultMap.failed eq null}"><font color="red">0</font>个</c:if>
					<c:if test="${countResultMap.failed ne null}"><font color="red">${countResultMap.failed}</font>个</c:if>
					成功订单总金额：<c:if test="${countResultMap.successamount eq null}"><font color="red">0</font>元</c:if>
					<c:if test="${countResultMap.successamount ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.successamount}" pattern="#0.00" /></font>元</c:if>
					成功总金额：<c:if test="${countResultMap.successrefundamount eq null}"><font color="red">0</font>元</c:if>
					<c:if test="${countResultMap.successrefundamount ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.successrefundamount}" pattern="#0.00" /></font>元</c:if>
					银行退回总手续费：<c:if test="${countResultMap.successbankbackamount eq null}"><font color="red">0</font>元</c:if>
					<c:if test="${countResultMap.successbankbackamount ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.successbankbackamount}" pattern="#0.00" /></font>元</c:if>
					平台退回总手续费：<c:if test="${countResultMap.successmerchantbackamount eq null}"><font color="red">0</font>元</c:if>
					<c:if test="${countResultMap.successmerchantbackamount ne null}">
					<font color="red"><fmt:formatNumber value="${countResultMap.successmerchantbackamount}" pattern="#0.00" /></font>元</c:if>
				</span>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>交易流水号</th>
				<th>商户退款订单号</th>
				<th>原支付接口名称</th>
				<th>处理时间</th>
				<th>退款状态</th>
				<th>商户全称</th>
				<th>银行退回手续费</th>
				<th>平台退回手续费</th>
				<th>退款金额</th>
				</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${orgMerchantOrderNo}</td>
					<td>${orgTrxNo}</td>
					<td>${refundOrderNo}</td>
					<td>${orgPayInterfaceCode}</td>
					<td><fmt:formatDate value="${refundCompleteTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					
					<td>
						<c:forEach items="${RefundStatusEnum }" var="model">
							<c:if test="${model.value eq refundStatus }">${model.desc }</c:if>
						</c:forEach>
					</td>
					
					<td>${merchantName}</td>
					<td><fmt:formatNumber pattern="#0.00" value="${bankBackFee}"/></td>
					<td><fmt:formatNumber pattern="#0.00" value="${platBackFee}"/></td>
					<td><fmt:formatNumber pattern="#0.00" value="${refundAmount}"/></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>

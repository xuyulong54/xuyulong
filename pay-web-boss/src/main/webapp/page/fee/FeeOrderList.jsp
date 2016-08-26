<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageHeader">
	<form id="pagerForm" name="memberForm" onsubmit="return navTabSearch(this);" action="FeeOrder_listOrder.action" method="post">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>计费时间：</label>
						<input type="text" name="beginDate" value="${beginDate}"  class="date" size="10" readonly="readonly" />-
						<input type="text" name="endDate" value="${endDate}" class="date" size="10" readonly="readonly" />
					</td>
					<td>
						<label>商户名称 ：</label>
						<input type="text" name="merchantName" value="${merchantName}" />
					</td>
					<td>
						<label>商户编号：</label>
						<input type="text" name="merchantNo" value="${merchantNo}" />
					</td>
					<td>
						<label>用户类型：</label>
						<select name="userType">
							<option value="">--请选择--</option>
							<c:forEach items="${UserTypeEnum }" var="item">
								<option value="${item.value }" <c:if test="${item.value eq userType}">selected="selected"</c:if>>${item.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>计费项：</label>
						<select name="calculateFeeItem">
							<option value="">--请选择--</option>
							<c:forEach items="${CalculateFeeItemEnum }" var="item">
								<option value="${item.value }" <c:if test="${item.value eq calculateFeeItem}">selected="selected"</c:if>>${item.desc }</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<label>交易流水号：</label>
						<input type="text" name="trxFlowNo" value="${trxFlowNo}" />
					</td>
					<td>
						<label>订单状态：</label>
						<select name="status">
							<option value="">--请选择--</option>
							<c:forEach items="${FeeOrderStatusEnum }" var="item">
								<option value="${item.value }" <c:if test="${item.value eq status}">selected="selected"</c:if>>${item.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<label>订单类型：</label>
						<select name="orderType">
							<option value="">--请选择--</option>
							<c:forEach items="${FeeOrderTypeEnum }" var="item">
								<option value="${item.value }" <c:if test="${item.value eq orderType}">selected="selected"</c:if>>${item.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td>
						<div class="subBar" style="float: right;">
							<ul>
								<li>
									<div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
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
		<ul class="toolBar">
			<li><a title="确定导出这些记录吗?" class="icon" href="FeeOrder_feeOrderExportExecl.action?beginDate=${fn:substring(beginDate,0,10) }&endDate=${fn:substring(endDate,0,10)}&status
						=${status}&merchantName=${merchantName}&userType=${userType}&calculateFeeItem=${calculateFeeItem}&trxFlowNo=${trxFlowNo}&merchantNo=${merchantNo }&orderType=${orderType }" 
						target="dwzExport" targettype="navTab"> <span>导出EXCEL</span> </a></li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th>计费时间</th>
				<th>交易流水号</th>
				<th>商户名称</th>
				<th>商户编号</th>
				<th>订单编号</th>
				<th>用户类型</th>
				<th>计费项</th>
				<th>费率基数</th>
				<th>交易金额</th>
				<th>收款方手续费</th>
				<th>订单类型</th>
				<th>订单状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_order" rel="${id}">
					<td>${st.index+1}</td>
					<td>
						<s:date name="calculateDate" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${trxFlowNo}</td>
					<td>${merchantName}</td>
					<td>${merchantNo}</td>
					<td>${merchantOrderNo}</td>
					<td>
						<c:forEach items="${UserTypeEnum }" var="item">
							<c:if test="${item.value eq userType }">${item.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${CalculateFeeItemEnum }" var="item">
							<c:if test="${item.value eq calculateFeeItem }">${item.desc }</c:if>
						</c:forEach>
					</td>
					<td>${feeBase}</td>
					<td>
						<fmt:formatNumber value="${amount}" pattern="#0.00" />
					</td>
					<%--<td>${payeeFee}</td>--%>
					<td><fmt:formatNumber value="${payeeFee}" pattern="#0.00" /></td> 
					<td>
						<c:forEach items="${FeeOrderTypeEnum }" var="item">
							<c:if test="${item.value eq orderType }">${item.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${FeeOrderStatusEnum }" var="item">
							<c:if test="${item.value eq status }">${item.desc }</c:if>
						</c:forEach>
					</td>
					<td nowrap>
						<z:permission value="fee:order:view">
							<a href="FeeOrder_showOrderDetail.action?id=${id }" title="查看明细" rel="feeCalculateWayList" target="dialog" style="color:blue">查看明细</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
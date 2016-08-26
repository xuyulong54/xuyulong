<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="calCostOrder_costOrderList.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					<label>商户编号：</label>
					<input type="text" name="merchantNo" value="${merchantNo }" alt="精确搜索"/>
				</td>
				<td >
					<label>商户名称：</label>
					<input type="text" name="merchantName" value="${merchantName }" alt="模糊搜索"/>
				</td>
				<td>
					<label>交易流水号：</label>
					<input type="text" name="trxNo" value="${trxNo }" alt="精确搜索" />
				</td>
				
			</tr>
			<tr>
				<td>
					<label>成本计费项：</label>
					<select name="costItem" id="costItem">
						<option value="">请选择</option>
						<c:forEach items="${costItemEnumList }" var="costItemEnum">
							<option value="${costItemEnum.value }" <c:if test="${costItem eq costItemEnum.value }">selected="selected"</c:if>>${costItemEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>计费方式：</label>
					<select name="calOrderType" id="calOrderType">
						<option value="">请选择</option>
						<c:forEach items="${calOrderTypeEnumList }" var="calOrderTypeEnum">
							<option value="${calOrderTypeEnum.value }" <c:if test="${calOrderType eq calOrderTypeEnum.value }">selected="selected"</c:if>>${calOrderTypeEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>计费状态：</label>
					<select name="status" id="status">
						<option value="">请选择</option>
						<c:forEach items="${trxStatusEnumList }" var="trxStatusEnum">
							<option value="${trxStatusEnum.value }" <c:if test="${status eq trxStatusEnum.value }">selected="selected"</c:if>>${trxStatusEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					<label>来源系统：</label>
					<select name="fromSystem" id="fromSystem">
						<option value="">请选择</option>
						<c:forEach items="${systemResourceTypeEnumList }" var="systemResourceTypeEnum">
							<option value="${systemResourceTypeEnum.value }" <c:if test="${fromSystem eq systemResourceTypeEnum.value }">selected="selected"</c:if>>${systemResourceTypeEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>交易时间：</label>
					<input name="beginDate" value="${beginDate }" 
					dateFmt="yyyy-MM-dd"
						type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate" value="${endDate }" 
						dateFmt="yyyy-MM-dd"
						type="text" class="date" id="endDate" style="width: 77px;" />
				</td>
				<td >
					<label>银行接口：</label>
					<input type="text" name="calInterface" value="${calInterface }" alt="模糊搜索"/>
				</td>
				<td>
					<div class="subBar" style="float: left;">
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
			<li><a title="确定导出这些记录吗?" class="icon" href="calCostOrder_calCostOrderExportExecl.action?beginDate=${fn:substring(beginDate,0,10) }&endDate=${fn:substring(endDate,0,10)}&status
						=${status}&merchantName=${merchantName}&trxNo=${trxNo}&costItem=${costItem}&calOrderType=${calOrderType}&merchantNo=${merchantNo }
						&calInterface=${calInterface }&fromSystem=${fromSystem }" target="dwzExport" targettype="navTab"> <span>导出EXCEL</span> </a></li>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="191" >
		<thead>
			<tr>
				<th>序号</th>
				<th>交易时间</th>
				<th>计费方式</th>
				<th>系统来源</th>
				<th>银行接口</th>
				<th>成本计费项</th>
				<th>商户编号</th>
				<th>商户名称</th>
				<!-- <th>MCC</th> -->
				<th>交易流水号</th>
				<!-- <th>商户订单号</th> -->
				<th>订单金额</th>
				<th>计费金额</th>
				<th>计费状态</th>
				<!-- <th>计费完成时间</th> -->
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td align="center"><s:date name="trxTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:forEach items="${calOrderTypeEnumList }" var="calOrderTypeEnum">
							<c:if test="${calOrderTypeEnum.value eq calOrderType }">${calOrderTypeEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${systemResourceTypeEnumList }" var="systemResourceTypeEnum">
							<c:if test="${systemResourceTypeEnum.value eq fromSystem }">${systemResourceTypeEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${calInterface}</td>
					<td>
						<c:forEach items="${costItemEnumList }" var="costItemEnum">
							<c:if test="${costItemEnum.value eq costItem }">${costItemEnum.desc }</c:if>
						</c:forEach>
					</td>
					<td>${merchantNo}</td>
					<td>${merchantName}</td>
					<td>${trxNo}</td>
					<td align="right">
						<fmt:formatNumber value="${amount}" pattern="0.00"></fmt:formatNumber>
					</td>
					<td align="right">
						<fmt:formatNumber value="${fee}" pattern="0.000000"></fmt:formatNumber>
					</td>
					
					<td>
						<c:forEach items="${trxStatusEnumList }" var="trxStatusEnum">
							<c:if test="${trxStatusEnum.value eq status }">${trxStatusEnum.desc }</c:if>
						</c:forEach>
					</td>
					<z:permission value="boss:calCostOrder:view">
					<td><a href="calCostOrder_costOrderInfo.action?id=${id}"  title="查看详细信息" target="dialog"  style="color:blue">查看明细</a></td>
					</z:permission>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>


<script>


</script>

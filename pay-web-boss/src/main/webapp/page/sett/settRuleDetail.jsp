<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<table class="searchContent" border="1">
		<tr style="height: 30px;">
			<td>账户编号： <input value="${accountNo }" readonly="readonly" /></td>
			<td>用户编号：<input value="${userNo }" readonly="readonly" /></td>
			<td>用户名称：<input value="${userName }" readonly="readonly" /></td>
		</tr>
		<tr style="height: 30px;">
			<td>结算类型：<c:forEach items="${SettTypeEnum }" var="enums">
							<c:if test="${settType eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
			<td>结算周期类型：<c:forEach items="${SettRuleCycleTypeEnum }" var="enums">
							<c:if test="${settCycleType eq enums.value }">${enums.desc }</c:if>
						</c:forEach>
			</td>
			<td>结算周期：<input value="${settCycleData }" readonly="readonly" />
			</td>
		</tr>
		<tr style="height: 30px;">
			<td>风险预存期：<input value="${riskDay }" readonly="readonly" />
			</td>
			<td>是否可结算：<c:forEach items="${PublicStatusEnum }" var="enums">
							<c:if test="${isSett eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
			<td>当前结算状态：<c:forEach items="${SettRuleStatusEnum }" var="enums">
							<c:if test="${currentSettStatus eq enums.value }">${enums.desc }</c:if>
						</c:forEach></td>
		</tr>
		<tr style="height: 30px;">
			<td>上次汇总日期：<input value="<fmt:formatDate value='${lastSumDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
			<td>上次结算截止日期：<input value="<fmt:formatDate value='${lastSettDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
			<td>上次结算处理日期：<input value="<fmt:formatDate value='${lastProcessDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
		</tr>
		<tr style="height: 30px;">
			<td>下次结算处理日期：<input value="<fmt:formatDate value='${nextProcessDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
			<td>上次结算批次号：<input value="${lastBatchNo }" readonly="readonly" /></td>
			<td>最后修改时间：<input value="<fmt:formatDate value='${lastModifyTime }' pattern='yyyy-MM-dd HH:mm:ss' />" readonly="readonly" /></td>
		</tr>
		<tr style="height: 30px;">
			<td>操作员登录名：<input value="${operatorLoginname }" readonly="readonly" /></td>
			<td>操作员姓名：<input value="${operatorRealname }" readonly="readonly" /></td>
			<td>描述 ：<input value="${remark }" readonly="readonly" /></td>
		</tr>

	</table>
</div>

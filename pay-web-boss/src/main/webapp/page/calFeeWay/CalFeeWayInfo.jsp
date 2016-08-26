<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="73">
		<thead>
			<tr>
				<th >序号</th>
				<th >计费维度</th>
				<th >计费方式名称</th>
				<th >创建时间</th>
				<th >免计费金额(包含)</th>
				<th >计费周期</th>
				<th >是否包年</th>
				<th >计费类型</th>
				<th >收费方式</th>
				<th >计费角色</th>
				<th >是否四舍五入</th>
				<th >生效日期</th>
				<th >失效日期</th>
				<th >MCC</th>
				<th >状态</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>
						<c:forEach items="${calDimensionList }" var="v">
							<c:if test="${dimensionId eq v.id}">${v.calProduct} ${ v.calCostInterfaceCode}</c:if>
						</c:forEach>
					</td>
					<td>${wayName}</td>
					<td align="center"><s:date name="createTime" format="yyyy-MM-dd" /></td>
				    <td align="right">${feeFreeAmount}</td>
					<td>
						<c:forEach items="${billingCycleEnumList }" var="billingCycleEnum">
							<c:if test="${cycleType eq billingCycleEnum.value}">${billingCycleEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${costInterfacePolicyEnumList }" var="costInterfacePolicyEnum">
							<c:if test="${packYears eq costInterfacePolicyEnum.value}">${costInterfacePolicyEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
							<c:if test="${calType eq calTypeEnum.value}">${calTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum">
							<c:if test="${calPeriod eq calPeriodeEnum.value}">${calPeriodeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${calRoleEnumList }" var="calRoleEnum">
							<c:if test="${calRole eq calRoleEnum.value}">${calRoleEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${isOrNotList }" var="isOrNot">
							<c:if test="${isRound eq isOrNot.value}">${isOrNot.desc}</c:if>
						</c:forEach>
					</td>
					<td align="center"><s:date name="beginDate" format="yyyy-MM-dd" /></td>
					<td align="center"><s:date name="endDate" format="yyyy-MM-dd" /></td>
					<td >${mcc }</td>
					<td >
						<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum">
							<c:if test="${status eq publicStatusEnum.value}">${publicStatusEnum.desc}</c:if>
						</c:forEach>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>

</div>

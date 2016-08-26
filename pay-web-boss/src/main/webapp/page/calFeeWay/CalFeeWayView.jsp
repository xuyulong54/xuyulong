<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script type="text/javascript" src="js/commonDate.js"></script>
<div class="pageContent">
	<div class="pageFormContent">
		<p>
			<label>创建时间：</label> <input type="text" value="<fmt:formatDate value='${createTime }' type="both"/>" readonly="true" />
		</p>

		<p>
			<label>计费维度：</label>
			<c:forEach items="${calDimensionList }" var="v">
				<c:if test="${dimensionId eq v.id}">
					<input type="text" value='${v.calProduct}(${v.calCostInterfaceCode})' readonly="true" />
				</c:if>
			</c:forEach>
		</p>
		<p>
			<label>计费方式名称：</label> <input type="text" value="${wayName}" readonly="true" />
		</p>
		<p>
			<label>免计费金额(包含)：</label> <input type="text" value="${feeFreeAmount}" readonly="true" />
		</p>
		<p>
			<label>计费周期：</label>
			<c:forEach items="${billingCycleEnumList }" var="billingCycleEnum">
				<c:if test="${cycleType eq billingCycleEnum.value}">
					<input type="text" value="${billingCycleEnum.desc}" readonly="true" />
				</c:if>
			</c:forEach>
			<c:if test="${empty cycleType}">
				<input type="text" value="" readonly="true" />
			</c:if>
		</p>

		<p>
			<label>计费类型：</label>
			<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
				<c:if test="${calType eq calTypeEnum.value}">
					<input type="text" value="${calTypeEnum.desc}" readonly="true" />
				</c:if>
			</c:forEach>

		</p>
		<p>
			<label>收费方式：</label>
			<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum">
				<c:if test="${calPeriod eq calPeriodeEnum.value}">
					<input type="text" value="${calPeriodeEnum.desc}" readonly="true" />
				</c:if>
			</c:forEach>
		</p>
		<p>
			<label>计费角色：</label>
			<c:forEach items="${calRoleEnumList }" var="calRoleEnum">
				<c:if test="${calRole eq calRoleEnum.value}">
					<input type="text" value="${calRoleEnum.desc}" readonly="true" />
				</c:if>
			</c:forEach>
		</p>
		<p>
			<label>近似值：</label> 
			<c:forEach items="${calApproximationList }" var="app" varStatus="index">
			 	<c:if test="${isRound == app.value }">${app.desc }</c:if>
			</c:forEach>
			<%--
			<c:forEach items="${isOrNotList }" var="isOrNot">
				<c:if test="${isRound eq isOrNot.value}">
					<input type="text" value="${isOrNot.desc}" readonly="true" />
				</c:if>
			</c:forEach>
 			--%>
		</p>
		<p>
			<label>费率类别：</label> <input type="text" value="${mccName}" readonly="true" />
		</p>
		<p>
			<label>生效日期：</label>
			<s:date name="beginDate" format="yyyy-MM-dd" />
		</p>
		<p>
			<label>失效日期：</label>
			<s:date name="endDate" format="yyyy-MM-dd" />
		</p>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="button">
					<div class="buttonContent">
						<button type="button" class="close">取消</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script type="text/javascript" src="js/commonDate.js"></script>
<div class="pageContent">
	<div class="pageFormContent">
		<p>
			<label>生效日期:</label>
			<s:date name="effectiveDateStart" format="yyyy-MM-dd" />
		</p>
		<p>
			<label>失效日期:</label>
			<s:date name="effectiveDateEnd" format="yyyy-MM-dd" />
		</p>
		<p>
			<label>计费类型:</label>
			<c:forEach items="${FeeCalculateTypeEnum }" var="models">
				<c:if test="${calculateType eq models.value }">
					<input value="${models.desc }" name="calculateType"
						readonly="readonly" />
				</c:if>
			</c:forEach>
		</p>
		<p>
			<label>收费方式:</label>
			<c:forEach items="${FeeChargeTypeEnum }" var="model">
				<c:if test="${chargeType eq model.value }">
					<input value="${model.desc }" name="chargeType" readonly="readonly" />
				</c:if>
			</c:forEach>
		</p>

		<p>
			<label>是否退还手续费:</label>
			<c:if test="${isRefundFee}">
				<input value="是" name="isRefundFee" readonly="readonly" />
			</c:if>
			<c:if test="${!isRefundFee}">
				<input value="否" name="isRefundFee" readonly="readonly" />
			</c:if>
		</p>

		<p>
			<label>是否有效:</label>
			<c:if test="${!isDelete}">
				<input value="是" name="isDelete" readonly="readonly" />
			</c:if>
			<c:if test="${isDelete}">
				<input value="否" name="isDelete" readonly="readonly" />
			</c:if>
		</p>
		<p>
			<label>计费角色:</label>
			<c:forEach items="${FeeRoleTypeEnum }" var="model">
				<c:if test="${feeRole eq model.value }">
					<input value="${model.desc }" name="feeRole" readonly="readonly" />
				</c:if>
			</c:forEach>
		</p>
		<p>
			<label>免计费金额(包含):</label>
			<input name="feeFreeAmount" readonly="readonly"
				value="${feeFreeAmount}" />
		</p>

		<p id="ladderCycleTypePP" type="hidden">
			<label>阶梯周期类型:</label>
			<c:if test="${ladderCycleType eq null}">
				<input name="ladderCycleType" readonly="readonly" value="" />
			</c:if>

			<c:forEach items="${LadderCycleTypeEnum }" var="model">
				<c:if test="${ladderCycleType eq model.value }">
					<input name="ladderCycleType" readonly="readonly"
						value="${model.desc}" />
				</c:if>
			</c:forEach>
		</p>
		<p id="customizeCycleType" type="hidden">
			<label>自定义阶梯周期类型:</label>
			<c:if test="${customizeCycleType eq null}">
				<input name="ladderCycleType" readonly="readonly" value="" />
			</c:if>
			<c:forEach items="${CustomCycleTypeEnum }" var="model">
				<c:if test="${customizeCycleType eq model.value }">
					<input name="customizeCycleType" readonly="readonly"
						value="${model.desc}" />
				</c:if>
			</c:forEach>

		</p>
		<p id="customizeDay" type="hidden">
			<label>自定义计费日:</label>
			<input name="customizeDay" value="${customizeDay}"
				readonly="readonly" />
		</p>
		
		<p id="isRound" type="hidden">
				<label>近似值:</label>
				<select name="isRound" disabled="disabled">
					<option value="">请选择</option>
					<c:forEach items="${isRoundEnums }" var="model">
						<option value="${model.value }" <c:if test="${isRound eq model.value }">selected="selected"</c:if>>${model.desc}</option>
					</c:forEach>
				</select>
			</p>
	</div>
	<div class="formBar" >
		<ul>
			<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
		</ul>
	</div>
</div>

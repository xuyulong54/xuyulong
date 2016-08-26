<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<table class="searchContent">
		<tr>
			<td>创建时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${feeOrder.createTime }' type="both"/>" readonly="true" /></td>
			<td>交易流水号：</td>
			<td><input type="text" value="${feeOrder.trxFlowNo}" readonly="true" /></td>
			<td>费率订单状态：</td>
			<td><c:forEach items="${FeeOrderStatusEnum }" var="item">
					<c:if test="${item.value eq feeOrder.status }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>父交易流水号：</td>
			<td><input type="text" value="${feeOrder.parentFlowNo}" readonly="true" /></td>
			<td>交易发生日期：</td>
			<td><input type="text" value="<fmt:formatDate value='${feeOrder.trxDate }' type="both"/>" readonly="true" /></td>
			<td>用户编号：</td>
			<td><input type="text" value="${feeOrder.merchantNo}" readonly="true" /></td>
		</tr>
		<tr>
			<td>用户名称：</td>
			<td><input type="text" value="${feeOrder.merchantName}" readonly="true" /></td>
			<td>用户订单编号：</td>
			<td><input type="text" value="${feeOrder.merchantOrderNo}" readonly="true" /></td>
			<td>用户类型：</td>
			<td><c:forEach items="${UserTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.userType }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<%-- <td>业务类型：</td>
			<td><c:forEach items="${TrxTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.trxType }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td> --%>

			<td>支付产品：</td>
			<td><input type="text" value="${payProduct.payProductName}" readonly="true" /></td>
			<td>支付方式：</td>
			<td><input type="text" value="${feeOrder.frpCode}" readonly="true" /></td>
			<td>支付接口：</td>
			<td><input type="text" value="${feeOrder.bankChannelCode}" readonly="true" /></td>
		</tr>
		<tr>
			<td>计费项：</td>
			<td><c:forEach items="${CalculateFeeItemEnum }" var="item">
					<c:if test="${item.value eq feeOrder.calculateFeeItem }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>计费时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${feeOrder.calculateDate }' type="both"/>" readonly="true" /></td>
			<td>实收时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${feeOrder.confirmDate }' type="both"/>" readonly="true" /></td>
		</tr>
		<tr>
			<%-- <td>计费角色：</td>
			<td><c:forEach items="${FeeRoleTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.feeRole }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td> --%>
				<td>收费类型：</td>
			<td><c:forEach items="${FeeChargeTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.chargeType }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>费率计算方式：</td>
			<td><c:forEach items="${FeeCalculateTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.calculateType }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
				<td>费率基数：</td>
			<td><input type="text" value="${feeOrder.feeBase}" readonly="true" /></td>
		</tr>
		<tr>
			<td>交易金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${feeOrder.amount}" pattern="#0.00" />" readonly="true" /></td>
			<td>收款方手续费：</td>
			<td><input type="text" value="<fmt:formatNumber value="${feeOrder.payeeFee}" pattern="#0.00" />" readonly="true" /></td>
			<td>收款方实收金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${feeOrder.payeeUnBackFee}" pattern="#0.00" />" readonly="true" /></td>
		</tr>
	<%-- 	<tr>
			<td>付款方实际手续费：</td>
			<td><input type="text" value="<fmt:formatNumber value="${feeOrder.payerFee}" pattern="#0.00" />" readonly="true" /></td>
			<td>付款方应收手续费：</td>
			<td><input type="text" value="<fmt:formatNumber value="${feeOrder.payerUnBackFee}" pattern="#0.00" />" readonly="true" /></td> 
		</tr> --%>
		<tr>
			<td>收费周期：</td>
			<td><c:forEach items="${LadderCycleTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.chargePeriodic }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>订单类型：</td>
			<td><c:forEach items="${FeeOrderTypeEnum }" var="item">
					<c:if test="${item.value eq feeOrder.orderType }">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
				<td>备注：</td>
			<td><input type="text" value="${feeOrder.remark}" readonly="true" /></td>
		</tr>
	</table>
</div>
<br />
<h4>计费方式</h4>
<br />
<div class="pageContent">
	<table class="table" asc="asc" desc="desc" width="100%">
		<thead>
			<tr>
				<th>计费类型</th>
				<th>收费方式</th>
				<th>计费角色</th>
				<th>免计费金额(包含)</th>
				<th>生效日期</th>
				<th>失效日期</th>
				<th>账单周期类型</th>
				<th>自定义账单周期类型</th>
				<th>自定义账单日</th>
				<th>退款是否退手续费</th>
			</tr>
		</thead>
		<tbody>
			<tr target="sid_user" rel="${feeCalculateWay.id}">
				<td><c:forEach items="${FeeCalculateTypeEnum }" var="item">
						<c:if test="${item.value eq feeCalculateWay.calculateType }">${item.desc }</c:if>
					</c:forEach></td>
				<td><c:forEach items="${FeeChargeTypeEnum }" var="item">
						<c:if test="${item.value eq feeCalculateWay.chargeType }">${item.desc }</c:if>
					</c:forEach></td>
				<td><c:forEach items="${FeeRoleTypeEnum }" var="item">
						<c:if test="${item.value eq feeCalculateWay.feeRole }">${item.desc }</c:if>
					</c:forEach></td>
				<td>${feeCalculateWay.feeFreeAmount }</td>
				<td><fmt:formatDate value="${feeCalculateWay.effectiveDateStart }" type="both" pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${feeCalculateWay.effectiveDateEnd }" type="both" pattern="yyyy-MM-dd" /></td>
				<td><c:forEach items="${LadderCycleTypeEnum }" var="item">
						<c:if test="${item.value eq feeCalculateWay.billCycleType }">${item.desc }</c:if>
					</c:forEach></td>
				<td><c:forEach items="${LadderCycleTypeEnum }" var="item">
						<c:if test="${item.value eq feeCalculateWay.customizeBillCycleType }">${item.desc }</c:if>
					</c:forEach></td>
				<td>${feeCalculateWay.customizeBillDay }</td>
				<td><c:if test="${feeCalculateWay.isRefundFee eq true }">是</c:if> <c:if test="${feeCalculateWay.isRefundFee eq false }">否</c:if></td>
			</tr>
		</tbody>
	</table>
</div>
<br />
<h4>计费公式</h4>
<br />
<div class="pageContent">
	<table class="table" asc="asc" desc="desc" width="100%">
		<thead>
			<tr>
				<th>公式类型</th>
				<th>固定金额费率基数</th>
				<th>百分比费率基数</th>
				<th>单笔金额下限</th>
				<th>单笔金额上限</th>
				<th>阶梯金额下限</th>
				<th>阶梯金额上限</th>
				<th>单笔最低手续费</th>
				<th>单笔最高手续费</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${listFeeFormulas }" var="formula">
				<tr target="sid_user" rel="${calFeeRateFormula.id}">
					<td><c:forEach items="${FeeFormulaTypeEnum }" var="item">
							<c:if test="${item.value eq formula.formulaType }">${item.desc }</c:if>
						</c:forEach></td>
					<td><fmt:formatNumber pattern="#0.000000#">${formula.fixedFee }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.000000#">${formula.percentFee }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.minAmount }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.maxAmount }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.minLadderAmount }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.maxLadderAmount }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.singleMinFee }</fmt:formatNumber></td>
					<td><fmt:formatNumber pattern="#0.00#">${formula.singleMaxFee }</fmt:formatNumber></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>


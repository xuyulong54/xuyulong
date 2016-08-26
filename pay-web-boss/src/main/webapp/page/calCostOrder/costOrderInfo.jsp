<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" layoutH="50">
		<fieldset style="width:99%">
			<legend>成本订单详情</legend>
				<p><label>交易时间：</label><fmt:formatDate value="${calCostOrder.trxTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
				<p ><label>计费方式：</label>
					<c:forEach items='${calOrderTypeEnumList }' var='calOrderTypeEnum'>
						<c:if test='${calOrderTypeEnum.value eq calCostOrder.calOrderType }'>${calOrderTypeEnum.desc }</c:if>
					</c:forEach>
				</p>
				<p ><label>系统来源：</label>
					<c:forEach items='${systemResourceTypeEnumList }' var='systemResourceTypeEnum'>
						<c:if test='${systemResourceTypeEnum.value eq calCostOrder.fromSystem }'>${systemResourceTypeEnum.desc }</c:if>
					</c:forEach>
				</p>
				<p><label>银行接口名称：</label>${calCostOrder.calInterface}</p>
				<p><label>成本计费项：</label>
					<c:forEach items="${costItemEnumList }" var="costItemEnum">
						<c:if test="${costItemEnum.value eq calCostOrder.costItem }">${costItemEnum.desc }</c:if>
					</c:forEach>
				</p>
				<p><label>MCC/渠道：</label>${calCostOrder.mcc}</p>
				<p><label>交易流水号：</label>${calCostOrder.trxNo}</p>
				<p><label>商户订单号：</label>${calCostOrder.merchantOrderNo}</p>
				<p><label>银行订单号：</label>${calCostOrder.bankOrderNo}</p>
				<p><label>订单金额：</label>${calCostOrder.amount}</p>
				<p><label>计费金额：</label>${calCostOrder.fee}</p>
				<p><label>计费描述：</label>${calCostOrder.calExpression}</p>
				<p><label>计费状态：</label>
					<c:forEach items='${trxStatusEnumList }' var='trxStatusEnum'>
						<c:if test='${trxStatusEnum.value eq calCostOrder.status }'>${trxStatusEnum.desc }</c:if>
					</c:forEach>
				</p>
				<p><label>商户编号：</label>${calCostOrder.merchantNo}</p>
				<p><label>商户名称：</label>${calCostOrder.merchantName}</p>
				<p><label>失败原因：</label>${calCostOrder.failedReason}</p>
				<p><label>计费完成时间：</label><fmt:formatDate value="${calCostOrder.calEndTime}" pattern="yyyy-MM-dd HH:mm:ss"/></p>
			
		</fieldset>
	
		<fieldset style="width:99%">
			<legend>
				计费方式
			</legend>
			<table class="table"  asc="asc" desc="desc" width="100%">
				<thead>
					<tr>
						<th >计费维度</th>
						<th >计费方式名称</th>
						<th >免计费金额</th>
						<th >计费周期</th>
						<th >计费类型</th>
						<th >计费方式</th>
						<th >计费角色</th>
					</tr>
				</thead>
				<tbody>
					<tr target="sid_user" rel="${calFeeWay.id}">
					    <td>
							<%-- <c:forEach items="${calDimensionList }" var="v">
								<c:if test="${calFeeWay.dimensionId eq v.id}">${v.calProduct}</c:if>
							</c:forEach> --%>
							${calDimension.calProduct }
						</td>
						<td>${calFeeWay.wayName}</td>
					    <td align="right">${calFeeWay.feeFreeAmount}</td>
						<td>
							<c:forEach items="${billingCycleEnumList }" var="billingCycleEnum">
								<c:if test="${calFeeWay.cycleType eq billingCycleEnum.value}">${billingCycleEnum.desc}</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach items="${calTypeEnumList }" var="calTypeEnum">
								<c:if test="${calFeeWay.calType eq calTypeEnum.value}">${calTypeEnum.desc}</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach items="${calPeriodeEnumList }" var="calPeriodeEnum">
								<c:if test="${calFeeWay.calPeriod eq calPeriodeEnum.value}">${calPeriodeEnum.desc}</c:if>
							</c:forEach>
						</td>
						<td>
							<c:forEach items="${calRoleEnumList }" var="calRoleEnum">
								<c:if test="${calFeeWay.calRole eq calRoleEnum.value}">${calRoleEnum.desc}</c:if>
							</c:forEach>
						</td>
					</tr>
				</tbody>
			</table>
		</fieldset>
	</div>
	
	<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	
</div>
<%-- <h4>计费公式</h4><br/>
<div class="pageContent" >
	<table class="table"  asc="asc" desc="desc" width="100%" >
		<thead>
			<tr>
				<th >计费约束</th>
				<th >计费模式</th>
				<th >固定手续费</th>
				<th >比例手续费</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${calFeeRateFormulaList }" var="calFeeRateFormula">
				<tr target="sid_user" rel="${calFeeRateFormula.id}">
				    <td>${calFeeWay.wayName}</td>
				    <td>
				    	<c:forEach items="${calModelEnumList }" var="calModelEnum">
							<c:if test="${calModelEnum.value eq calFeeRateFormula.model }">${calModelEnum.desc }</c:if>
						</c:forEach>
				    </td>
				    <td>${calFeeRateFormula.fixedFee}</td>
					<td>${calFeeRateFormula.percentFee }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div> --%>


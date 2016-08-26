<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<div class="pageFormContent" >
		<table class="searchContent" style="width: 99%;">
			<tr style="height: 30px;">
				<td>账户编号： </td>
				<td><input value="${accountNo }" readonly="readonly" /></td>
				<td>用户编号：</td>
				<td><input value="${userNo }" readonly="readonly" /></td>
				<td>用户名称：</td>
				<td><input value="${userName }" readonly="readonly" /></td>
			</tr>
			<tr style="height: 30px;">
				<td>结算批次号：</td><td> <input value="${batchNo }" readonly="readonly" /></td>
				<td>打款请求号：</td><td><input value="${remitNo }" readonly="readonly" /></td>
				<td>发起方式：</td><td> <c:forEach items="${SettModeTypeEnum }" var="enums">
						<c:if test="${settMode eq enums.value }">
							<input value="${enums.desc }" readonly="readonly" />
						</c:if>
					</c:forEach>
				</td>
			</tr>
			<tr style="height: 30px;">
				<td>结算日期：</td><td><input value="<fmt:formatDate value="${settDate }" pattern="yyyy-MM-dd" />" readonly="readonly" />
				</td>
				<td>账户币种：</td>
				<td>
					<c:forEach items="${CurrencyTypeEnum }" var="enums">
						<c:if test="${currencyType eq enums.value }">${enums.desc }</c:if>
					</c:forEach>
				</td>
				<td>银行编码：</td><td><input value="${bankCode }" type="text" readonly="readonly" />
				</td>
			</tr>
			<tr style="height: 30px;">
				<td>开户名：</td><td><input value="${bankAccountName }" readonly="readonly" />
				</td>
				<td>开户账号：</td><td><input type="text" value="${bankAccountNo }" readonly="readonly" /></td>
				<td>银行卡类型：</td><td><c:forEach items="${BankAccountTypeEnum }" var="enums">
						<c:if test="${bankAccountType eq enums.value }">${enums.desc }</c:if>
					</c:forEach></td>
			</tr>
			<tr style="height:30px;">
				<%-- <td>开户行国家：</td><td><input type="text" value="${country }" readonly="readonly" /></td> --%>
				<td>开户行省市：</td><td><input type="text" value="${province }" readonly="readonly" /></td>
				<td>开户行城市：</td><td><input type="text" value="${city }" readonly="readonly" /></td>
				<td>开户行全称：</td><td><input type="text" value="${bankAccountAddress }" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>结算金额：</td><td><input type="text" value="${settAmount }" readonly="readonly" /></td>
				<td>结算手续费：</td><td><input type="text" value="${settFee }" readonly="readonly" /></td>
				<td>结算打款金额：</td><td><input type="text" value="${remitAmount }" readonly="readonly" /></td>
			</tr>
			
			<%-- <tr>
				<td>交易手续费：</td><td><input type="text" value="${tradeFee }" readonly="readonly" /></td>
				<td>退款笔数：</td><td><input type="text" value="${refundNum }" readonly="readonly" /></td>
				<td>退款金额：</td><td><input type="text" value="${refundAmount }" readonly="readonly" /></td>
			</tr> --%>
			<%-- <tr style="height: 30px;">
				<td>退款手续费：</td><td><input value="${refundFee }" readonly="readonly" />
				</td>
				<td>结算退回金额：</td><td><input type="text" value="${returnAmount }" readonly="readonly" /></td>
				<td>手续费退回处理：</td><td>
					<c:forEach items="${SettleReturnFeeTypeEnum}" var="enums">
						<c:if test="${returnFeeType eq enums.value }">${enums.desc }</c:if>
					</c:forEach></td>
			</tr> --%>
			<tr>
				<td>结算状态：</td><td><c:forEach items="${SettRecordStatusEnum }" var="enums">
						<c:if test="${settStatus eq enums.value }">${enums.desc }</c:if>
					</c:forEach></td>
				<td>结算开始日期：</td><td><input value="<fmt:formatDate value='${beginDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
				<td>结算截止日期：</td><td><input value="<fmt:formatDate value='${endDate }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
			</tr>
			
			<tr style="height: 30px;">
				<td>打款发送时间：</td><td><input value="<fmt:formatDate value='${remitRequestTime }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
				<td>打款确认时间：</td><td><input value="<fmt:formatDate value='${remitConfirmTime }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
				<td>打款备注：</td><td><input value="${remitRemark }" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>交易笔数：</td><td><input type="text" value="${tradeNum }" readonly="readonly" /></td>
				<td>交易金额：</td><td><input type="text" value="${tradeAmount }" readonly="readonly" /></td>
				<td>创建时间：</td><td><input value="<fmt:formatDate value='${createTime }' pattern='yyyy-MM-dd' />" readonly="readonly" /></td>
			</tr>
			<tr style="height: 30px;">
				<td>操作员登录名：</td><td><input value="${operatorLoginname }" readonly="readonly" /></td>
				<td>操作员姓名：</td><td><input value="${operatorRealname }" readonly="readonly" /></td>
			</tr>
			<tr>
				<td>描述 ：</td><td colspan="3"><input value="${remark }" readonly="readonly" size="60" /></td>
			</tr>
		</table>
	</div>
</div>
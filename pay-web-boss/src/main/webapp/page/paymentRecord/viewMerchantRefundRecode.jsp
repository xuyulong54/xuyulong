<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%@taglib prefix="s" uri="/struts-tags"%>

<div class=pageContent>
	<fieldset style="width:99%">
		<legend>
			退款记录详情<font color="red">*</font>
		</legend>
			<table class="searchContent">
		<tr>
			<td>创建时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.createTime }' type="both"/>" readonly="true" /></td>
			<td>原商户订单号：</td>
			<td><input type="text" value="${refundRecord.orgMerchantOrderNo}" readonly="true" /></td>
			<td>原支付流水号：</td>
			<td><input type="text" value="${refundRecord.orgTrxNo}" readonly="true" /></td>
		</tr>
		<tr>
			<td>原银行订单号：</td>
			<td><input type="text" value="${refundRecord.orgBankOrderNo}" readonly="true" /></td>
			<td>原银行流水号：</td>
			<td><input type="text" value="${refundRecord.orgBankTrxNo }" readonly="true" /></td>
			<td>商户编号：</td>
			<td><input type="text" value="${refundRecord.merchantNo}" readonly="true" /></td>
		</tr>
		<tr>
			<td>商户名称：</td>
			<td><input type="text" value="${refundRecord.merchantName}" readonly="true" /></td>
			<td>原收款方账户类型：</td>
			<td><c:forEach items="${AccountTypeEnum }" var="item">
					<c:if test="${refundRecord.orgReceiverAccountType eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>原付款人编号：</td>
			<td><input type="text" value="${refundRecord.orgPayerUserNo}" readonly="true" /></td>
		</tr>
		<tr>
			<td>原付款人名称：</td>
			<td><input type="text" value="${refundRecord.orgPayerName}" readonly="true" /></td>
			<td>原付款方账户类型：</td>
			<td><c:forEach items="${AccountTypeEnum }" var="item">
					<c:if test="${refundRecord.orgPayerAccountType eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>原订单金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgOrderAmount}" pattern="#0.00" />" readonly="true" /></td>
		</tr>
		<tr>
			<td>原付款方手续费：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgPayerFee}" pattern="#0.00" />" readonly="true" /></td>
			<td>原付款方支付总金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgPayerPayAmount}" pattern="#0.00" />" readonly="true" /></td>
			<td>原收款方手续费：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgReceiverFee}" pattern="#0.00" />" readonly="true" /></td>
		</tr>
		<tr>
			<td>原收款方实收金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgReceiverPostAmount}" pattern="#0.00" />" readonly="true" /></td>
			<td>原平台收入：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.orgPlatIncome}" pattern="#0.00" />" readonly="true" /></td>
			<td>原商品名称：</td>
			<td><input type="text" value="${refundRecord.orgProductName }" readonly="true" /></td>
		</tr>
		<tr>
			<td>原支付成功时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.orgPaySuccessTime }' type="both"/>" readonly="true" /></td>
			<td>原支付方式编码：</td>
			<td><input type="text" value="${refundRecord.orgPayWayCode }" readonly="true" /></td>
			<td>原支付方式类型：</td>
			<td><c:forEach items="${TradePaymentTypeEnum }" var="item">
					<c:if test="${refundRecord.orgPaymentType eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>原支付接口编号：</td>
			<td><input type="text" value="${refundRecord.orgPayInterfaceCode}" readonly="true" /></td>
			<td>原支付接口名称：</td>
			<td><input type="text" value="${refundRecord.orgPayInterfaceName }" readonly="true" /></td>
			<td>退款总金额：</td>
			<td><input type="text" value="<fmt:formatNumber value="${refundRecord.refundAmount}" pattern="#0.00" />" readonly="true" /></td>
		</tr>
		<tr>
			<td>退款流水号：</td>
			<td><input type="text" value="${refundRecord.refundTrxNo}" readonly="true" /></td>
			<td>退款订单号：</td>
			<td><input type="text" value="${refundRecord.refundOrderNo }" readonly="true" /></td>
			<td>银行退款订单号：</td>
			<td><input type="text" value="${refundRecord.bankRefundOrderNo}" readonly="true" /></td>
		</tr>
		<tr>
			<td>银行退款流水号：</td>
			<td><input type="text" value="${refundRecord.bankRefundTrxNo}" readonly="true" /></td>
			<td>结果通知URL：</td>
			<td><input type="text" value="${refundRecord.resultNotifyUrl }" readonly="true" /></td>
			<td>退款状态：</td>
			<td><c:forEach items="${RefundStatusEnum }" var="item">
					<c:if test="${refundRecord.refundStatus eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>退款来源：</td>
			<td>
				<c:forEach items="${RefundFromEnum }" var="item">
					<c:if test="${refundRecord.refundFrom eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach>
			</td>
			<td>退款方式：</td>
			<td><c:forEach items="${RefundWayEnum }" var="item">
					<c:if test="${refundRecord.refundWay eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
			<td>风险类型：</td>
			<td><c:forEach items="${RefundRiskyTypeEnum }" var="item">
					<c:if test="${refundRecord.riskyType eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
		<tr>
			<td>退款请求时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.refundRequestTime }' type="both"/>" readonly="true" /></td>
			<td>退款成功时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.refundSuccessTime }' type="both"/>" readonly="true" /></td>
			<td>退款完成时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.refundCompleteTime }' type="both"/>" readonly="true" /></td>
		</tr>
		<tr>
			<td>退款接口编号：</td>
			<td><input type="text" value="${refundRecord.refundInterfaceCode}" readonly="true" /></td>
			<td>退款接口名称：</td>
			<td><input type="text" value="${refundRecord.refundInterfaceName}" readonly="true" /></td>
			<td>退款申请人登录名：</td>
			<td><input type="text" value="${refundRecord.requestApplyLoginName }" readonly="true" /></td>
		</tr>
		<tr>
			<td>最后修改时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.lastModifyTime }' type="both"/>" readonly="true" /></td>
			<td>退款申请人姓名：</td>
			<td><input type="text" value="${refundRecord.requestApplyName}" readonly="true" /></td>
			<td>退款原因：</td>
			<td><input type="text" value="${refundRecord.refundReason}" readonly="true" /></td>
		</tr>
		<tr>
			<td>操作员登录名：</td>
			<td><input type="text" value="${refundRecord.operatorAuditLoginName }" readonly="true" /></td>
			<td>操作员姓名：</td>
			<td><input type="text" value="${refundRecord.operatorAuditName}" readonly="true" /></td>
			<td>操作员审核时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.operatorAuditTime }' type="both"/>" readonly="true" /></td>
		</tr>
		<tr>
			<td>操作员审核意见：</td>
			<td><input type="text" value="${refundRecord.operatorAuditOpinion }" readonly="true" /></td>
			<td>平台审核员登录名：</td>
			<td><input type="text" value="${refundRecord.platAuditLoginName}" readonly="true" /></td>
			<td>平台审核员姓名：</td>
			<td><input type="text" value="${refundRecord.platAuditName}" readonly="true" /></td>
		</tr>
		<tr>
			<td>平台审核时间：</td>
			<td><input type="text" value="<fmt:formatDate value='${refundRecord.platAuditTime }' type="both"/>" readonly="true" /></td>
			<td>平台审核意见：</td>
			<td><input type="text" value="${refundRecord.platAuditOpinion}" readonly="true" /></td>
			<td>审核状态：</td>
			<td><c:forEach items="${RefundAuditStatusEnum }" var="item">
					<c:if test="${refundRecord.requestAuditStatus eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach></td>
		</tr>
	</table>
	</fieldset>
	<br>
</div>
<div class=pageContent>
		<fieldset style="width:99%">
		<legend>
			退款处理详情<font color="red">*</font>
		</legend>
		<table class="table" width="100%" layoutH="300">
		<thead>
			<tr>
				<th>序号</th>
				<th>处理时间</th>
				<th>退款流水号</th>
				<th>第几次处理</th>
				<th>失败原因</th>
				<th>退款状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}"
							pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${refundTrxNo}</td>
					<td>${refundTimes}</td>
					<td>${failReason}</td>
					<td><c:forEach items="${RefundStatusEnum }" var="item">
							<c:if test="${item.value eq refundProcessStatus }">${item.desc }</c:if>
						</c:forEach>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</fieldset>
	</div>
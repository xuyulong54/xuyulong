<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="paymentRecord_editPaymentRecord.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> <input type="hidden" name="callbackType" value="closeCurrent"> <input type="hidden" name="forwardUrl" value="">
			<s:hidden name="id"></s:hidden>
			<fieldset style="width:99%">
				<legend>
					订单详情<font color="red">*</font>
				</legend>
				<p>
					<label>收款方名称：</label>
					<s:textfield name="merchantName" readonly="true" id="merchantName" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>付款方名称：</label>
					<s:textfield name="payerName" readonly="true" id="userName" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>银行订单号：</label>
					<s:textfield name="bankOrderNo" readonly="true" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>商户订单号：</label>
					<s:textfield name="merchantOrderNo" readonly="true" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>交易渠道：</label>
					<s:textfield name="payWayName" readonly="true" id="payWayName"></s:textfield>
				</p>
				<p>
					<label>交易流水号：</label>
					<s:textfield name="trxNo" readonly="true" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>订单金额：</label>
					<%-- <s:textfield name="orderAmount" readonly="true"  minlength="1" maxlength="25" size="30" /> --%>
					<fmt:formatNumber value="${orderAmount }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>交易类型：</label>
					<s:textfield name="trxType" readonly="true" value="%{#trxTypeDesc}"></s:textfield>
				</p>
				<p>
					<label>手续费：</label>
					<%-- <s:textfield name="sourceFee" readonly="true"  minlength="1" maxlength="25" size="30" /> --%>
					<fmt:formatNumber value="${receiverFee }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>交易状态：</label>
					<s:select name="status" disabled="true" value="%{status}" list="#{100:'交易成功', 101:'交易失败', 102:'订单已创建', 103:'订单已取消'}" />
				</p>
				<p>
					<label>实收金额：</label>
					<fmt:formatNumber value="${orderAmount-sourceFee }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>创建时间：</label>
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
				</p>
				<p>
					<label>退款金额：</label>
					<fmt:formatNumber value="${successRefundAmount }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>支付时间：</label>
					<s:date name="paySuccessTime" format="yyyy-MM-dd HH:mm:ss" />
				</p>
				<p>
					<label>通知状态：</label> <select name="notifyStatus" disabled="disabled">
						<option <c:if test="${notifyStatus eq 100 }">selected="selected"</c:if>>通知成功</option>
						<option <c:if test="${notifyStatus eq 101 || empty notifyStatus }">selected="selected"</c:if>>通知失败</option>
					</select>
				</p>
			</fieldset>
			<fieldset style="width:99%">
				<legend>
					退款详情<font color="red">*</font>
				</legend>
				<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false">
					<thead>
						<tr>
							<th>序号</th>
							<th>退款金额</th>
							<th>退款原因</th>
							<th>退款时间</th>
							<th>退款状态</th>
						</tr>
					</thead>
					<tbody>
						<s:iterator value="list" status="st">
							<tr target="sid" rel="${Id}">
								<td>${st.index+1}</td>
								<td>${refundAmount}</td>
								<td>${refundReason}</td>
								<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
								<td><c:forEach items="${RefundStatusEnum }" var="item">
										<c:if test="${refundStatus eq item.value }">${item.desc }</c:if>
									</c:forEach></td>
							</tr>
						</s:iterator>
					</tbody>
				</table>
			</fieldset>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${empty view }">
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="submitForm();">保存</button>
							</div>
						</div></li>
				</c:if>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		$("#ref_merchantId").val($("#merchantId").val());
		$("#ref_merchantName").val($("#merchantName").val());
	});
	function submitForm() {
		$("#merchantId").val($("#ref_merchantId").val());
		$("#merchantName").val($("#ref_merchantName").val());
		$("#form").submit();
	}
	function addBankName(bankCodeDomObj) {
		$("#bankName").val($(bankCodeDomObj).find("option:selected").text());
	}
</script>
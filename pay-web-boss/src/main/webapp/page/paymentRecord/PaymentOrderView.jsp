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
					<label>商户编号：</label>
					<s:textfield name="merchantNo" readonly="true" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>付款方名称：</label>
					<s:textfield name="payerName" readonly="true" id="userName" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>商户订单号：</label>
					<s:textfield name="merchantOrderNo" readonly="true" minlength="1" maxlength="25" size="30" />
				</p>
				<p>
					<label>订单金额：</label>
					<fmt:formatNumber value="${orderAmount }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>交易类型：</label>
					<c:forEach items="${tradeBizTypeEnums }" var="item">
					<c:if test="${bizType eq  item.value}">
						<input type="text" value="${item.desc }" readonly="true" />
					</c:if>
				</c:forEach>
				</p>
				<p>
					<label>付款方手续费：</label>
					<fmt:formatNumber value="${payerFee }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>收款方手续费：</label>
					<fmt:formatNumber value="${receiverFee }" pattern="0.00"></fmt:formatNumber>
				</p>
				<p>
					<label>交易状态：</label>
					<s:select name="status" disabled="true" value="%{status}" list="#{100:'交易成功', 101:'交易失败', 102:'订单已创建', 103:'订单已取消'}" />
				</p>
				
				<p>
					<label>创建时间：</label>
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
				</p>
				<p>
					<label>通知状态：</label> <select name="notifyStatus" disabled="disabled">
						<option <c:if test="${notifyStatus eq 100 }">selected="selected"</c:if>>通知成功</option>
						<option <c:if test="${notifyStatus eq 101 || empty notifyStatus }">selected="selected"</c:if>>通知失败</option>
					</select>
				</p>
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
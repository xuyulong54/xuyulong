<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="bank_addBankChannel.action" class="pageForm required-validate"
	 onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id }" id="id">
			<input type="hidden" name="bankchannelCode" value="${bankChannelCode }" id="bankchannelCode">
			<input type="hidden" name="bankAgreementID" value="${bankAgreementID }" id="bankAgreementID">
			<div class="unit">
				<label>银行渠道编号：</label>
				<s:textfield name="bankChannelCode" readonly="true" cssClass="required" minlength="1" maxlength="30" size="45" />
			</div>
			<div class="unit">
				<label>银行渠道名称：</label>
				<s:textfield name="bankChannelName" readonly="true" cssClass="required" minlength="1" maxlength="100" size="45" />
			</div>
			<div class="unit">
				<label>银行名称：</label>
				<s:textfield name="bankName" readonly="true" cssClass="required" minlength="1" maxlength="25" size="45" />
<%-- 				<s:select headerValue="请选择" headerKey="" list="#bankDictList" 
				name="bankCode" listKey="key" listValue="value" cssClass="required" disabled="true" /> --%>
			</div>
			<div class="unit">
				<label>银行账户名称：</label>
				<s:textfield name="bankAccountName" readonly="true" cssClass="required" minlength="1" maxlength="100" size="45" />
			</div>
			<div class="unit">
				<label>状态：</label>
				<select name="status" class="combox">
					<option value="101" <c:if test="${status eq 101 }">selected="selected"</c:if> >无效</option>
					<option value="100" <c:if test="${status eq 100 }">selected="selected"</c:if> >有效</option>
				</select>
			</div>
			<div class="unit">
				<label>落地行名称：</label>
				<s:textfield name="landingBankName" readonly="true" cssClass="required" minlength="1" maxlength="90" size="45" />
			</div>
			<div class="unit">
				<label>描述：</label>
				<s:textarea name="remark" maxlength="200" rows="3" cols="40"></s:textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		var id = $("#id").val();
		if (id.length > 0) {
			var url = "bank_editBankChannel.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}

</script>

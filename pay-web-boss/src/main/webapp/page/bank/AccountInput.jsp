<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
<h2><s:if test="code==null">新建</s:if><s:else>修改</s:else>资金账户信息</h2>
</div>
<div class="pageContent">
	<form id="form" method="post" action="bank_addAccount.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${bankAccount }" id="id">

			
			<p style="width:99%">
				<label>开户账户：</label>
				<s:textfield name="bankAccount" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p style="width:99%">
				<label>账户名称：</label>
				<s:textfield name="bankAccountname" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p style="width:99%">
				<label>账户余额：</label>
				<s:textfield name="balance" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<s:select list="#{101:'无效',100:'有效'}" listKey="key" name="status"
				listValue="value"  headerKey="" cssClass="required" /> 
			</p>
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
			var url = "bank_editAccount.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}

</script>

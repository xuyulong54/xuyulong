<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="bank_editBankBusiSetting.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="bankAgreementId" value="${bankAgreementId }" id="bankAgreementId">

			<p style="width:99%">
				<!-- <label>是否支持部分退款：</label> -->
				<input type="checkbox" name="isPartRefund" id="isPartRefund" value="100" title="勾选表示支持部分退款" <c:if test="${isPartRefund eq 100 }"> checked="checked" </c:if> />部分退款
				<!--<s:select list="#{100:'是',101:'否'}" id="isPartRefund" value="%{isPartRefund}" name="isPartRefund" 
				 headerKey="-1" headerValue="请选择" cssClass="required"/> -->
				 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				 <input type="checkbox" name="refundIsBackFee" title="勾选表示支持退还支付手续费"  id="refundIsBackFee" value="100" <c:if test="${refundIsBackFee eq 100 }">checked="checked" </c:if> />退还支付手续费
			</p>
			
			<p style="width:99%">
				<!-- <label>退款时是否退回支付手续费：</label>
				<s:select list="#{100:'是',101:'否'}" id="refundIsBackFee" value="%{refundIsBackFee}" name="refundIsBackFee" 
				 headerKey="-1" headerValue="请选择" cssClass="required"/>  -->
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
		var id = $("#bankAgreementId").val();
		if (id.length > 0) {
			var url = "bank_editBankBusiSetting.action";
			$("#form").attr("action", url);
		}
		var isPartRefund = $("#isPartRefund").val();
		var refundIsBackFee = $("#refundIsBackFee").val();
		
		$("#form").submit();
	}

</script>

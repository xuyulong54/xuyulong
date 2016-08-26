<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="saveAuditMerchant" method="post"
		action="merchant_auditMerchantInfo.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="id" value="${merchantId }" id="id" />
			<input type="hidden" name="status" id="status" value="101" />
			<div class="unit">
				<label>审核意见：</label>
				<label><s:textarea rows="5" cols="30" maxlength="1000" name="checkResult" cssClass="required"></s:textarea></label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="saveAuditMerchant()">保存</button>
						</div>
					</div>
				</li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>
<script>
function saveAuditMerchant(obj){
	$("#saveAuditMerchant").submit();
	// 刷新商户审核列表页面
	$("#auditMerchantBt").click();
	navTab.closeCurrentTab();
}
</script>

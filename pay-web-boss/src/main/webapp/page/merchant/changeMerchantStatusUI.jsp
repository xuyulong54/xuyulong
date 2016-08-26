<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="userAuditRecordStatus_changeMerchantStatus.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="merchantNo" value="${merchantNo }" id="id" />
			<input type="hidden" name="status" id="status" value="${status }" />
			<input type="hidden" name="currentStatus" id="currentStatus" value="${currentStatus }" />
			
			<div class="unit">
				<label>商户编号：</label>
				<label>${merchantNo }</label>
			</div>
			<div class="unit">
				<label>变更前状态：</label>
				<label>${status == 101 ? '激活' : '冻结' }</label>
			</div>
			<div class="unit">
				<label>变更后状态：</label>
				<label>${status == 101 ? '冻结' : '激活' }</label>
			</div>
			<div class="unit">
				<label>变更原因：</label>
				<label><s:textarea rows="5" cols="30" maxlength="1000" name="requestDesc" cssClass="required"></s:textarea></label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
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

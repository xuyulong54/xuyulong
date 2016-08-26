<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"
		action="merchant_merchantCell.action"
		class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="id" value="${merchantId }" id="id" />
			<div class="unit">
				<label>商户编号：</label>
				<label>${merchantNo }</label>
			</div>
			<div class="unit">
				<label>商户全称：</label>
				<label style="width: 300px;">${fullName }</label>
			</div>
			<div class="unit">
				<label>注销原因：</label>
				<label><s:textarea rows="5" cols="30" maxlength="1000" name="requestDesc" cssClass="required"></s:textarea></label>
			</div>
			<div class="unit"></div>
			<div class="unit"></div>
			<div class="unit"></div>
			<div class="unit" style="color:red;">
				提醒：提交申请后，将关闭该商户的业务功能，只保留退款功能，请谨慎操作！
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="submit">保存</button>
						</div>
					</div>
				</li>
				<li>
					<div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div>
				</li>
			</ul>
		</div>
	</form>
</div>

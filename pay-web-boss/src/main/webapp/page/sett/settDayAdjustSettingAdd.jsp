<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="SettDayAdjust_addSettDayAdjustSetting.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listSettDayAdjustSetting">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<div class="unit">
				<label>结算原始日：</label>
				<input type="text" name="oldDate" required="required" value="${fn:substring(oldDate,0,10)}"  class="date" size="25" readonly="true" />
			</div>
			<div class="unit">
				<label>结算调整日：</label>
				<input type="text" name="newDate"  required="required" value="${fn:substring(newDate,0,10)}"  class="date" size="25" readonly="true" />
			</div>
			<div class="unit">
				<label>描述：</label>
				<textarea rows="10" cols="23"  name="remark"  minlength="1" maxlength="1000" required="required" >${remark}</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

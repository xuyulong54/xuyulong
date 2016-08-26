<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="sales_doAdd.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${model.id}" />
			<div class="unit">
				<label>销售人员姓名：</label>
				<input type="text" value="${model.salesName }"  size="30" name="salesName"  class="required" minlength="6" maxlength="1000" />
			</div>
			<div class="unit">
				<label>联系手机：</label>
				<input type="text" value="${model.mobile }"  size="30" name="mobile"  class="required " minlength="11" maxlength="11" />
			</div>
			<div class="unit">
				<label>状态：</label>
				<select name="status" class="required ">
					<option value="">请选择</option>
					<option value="100" <c:if test="${model.status == 100 }">selected="selected"</c:if> >激活</option>
					<option value="101" <c:if test="${model.status == 101 }">selected="selected"</c:if> >冻结</option>
				</select>
			</div>
			<div class="unit">
				<label>备注：</label>
				<textarea rows="10" cols="23"  name="description"  minlength="6" maxlength="1000">${model.description}</textarea>
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

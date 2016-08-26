<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageContent">
	<form id="form" method="post" action="article_doAdd.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<div class="unit">
				<label>文章类型：</label>
				<s:select list="articleTypeList" name="type" cssClass="request" listValue="desc" listKey="value" disabled="true" ></s:select>
			</div>
			<div class="unit">
				<label>文章标题：</label>
				<s:textfield name="title" id="title" cssClass="required" readonly="true" maxlength="300" size="30" />
			</div>
			<div class="unit">
				<label>状态：</label>
				<select name="status" disabled="true">
					<option value="100" <c:if test="${status == 100 }">selected="selected"</c:if> >启用</option>
					<option value="101" <c:if test="${status == 101 }">selected="selected"</c:if> >停用</option>
				</select>
			</div>
			<div class="unit">
				<label>内容：</label>
				<textarea id="elm1" tools="mini" name="content" class="editor" readonly="readonly" style="width:600px;height:320px;visibility:hidden;">${content }</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

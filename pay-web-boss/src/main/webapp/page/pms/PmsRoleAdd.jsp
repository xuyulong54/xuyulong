<%-- 权限模块:角色管理:添加或修改页面 --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="pms_addPmsRole.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="listPmsRole">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
						
			<p>
				<label>角色名称：</label>
				<input type="text" name="roleName" class="required" minlength="3" maxlength="90" size="30" >
			</p>
			<p>
				<label>角色类型：</label>
				<input type="text" value="普通操作员角色" readonly="true" size="30" >
			</p>
			<p style="height:50px;">
				<label>角色描述：</label>
				<textarea rows="5" cols="27" name="desc" class="required" minlength="3" maxlength="300"></textarea>
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
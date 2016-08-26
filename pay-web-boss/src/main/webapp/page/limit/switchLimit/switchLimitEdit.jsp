<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<form method="post"  class="pageForm" name="riskTypeAdd" 
		action="switchLimit_editSwitchLimit.action" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<!-- 基本信息 -->
		<div class="pageFormContent" layoutH="58">
  			<input type="hidden" name="navTabId" value="listSwitchLimit">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}">
			<p>
				<label>名称：</label>
				<s:textfield name="name" minlength="1" maxlength="50" cssClass="required" size="20" />
			</p>
			<div class="unit">
					<label>描述：</label> 
					<s:textarea cols="50" rows="4"
					name="description" cssClass="required" maxlength="200"></s:textarea>
					
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

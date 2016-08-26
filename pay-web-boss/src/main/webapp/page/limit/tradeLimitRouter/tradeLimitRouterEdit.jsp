<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<form method="post"  class="pageForm" name="riskTypeAdd" 
		action="tradeLimitRouter_editTradeLimitRouter.action" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<!-- 基本信息 -->
		<div class="pageFormContent" layoutH="58">
  			<input type="hidden" name="navTabId" value="listTradeLimitRouter">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}">
			<p>
				<label>商户编号：</label>
				<s:textfield name="merchantNo" readonly="true" minlength="1" maxlength="100" cssClass="required" size="20" />
			</p>
			<p>
				<label>开关限制包：</label>
					<select name="switchLimitPackId">		
						<option value="">--请选择--</option>				
						<c:forEach items="${switchLimitPacksList}" var="model">
						<option value="${model.id}"						
						<c:if test="${switchLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>								
					</select>				
			</p>
			<p>
				<label>金额限制包：</label>
					<select name="amountLimitPackId">	
						<option value="">--请选择--</option>					
						<c:forEach items="${amountLimitPackList}" var="model">
						<option value="${model.id}"						
						<c:if test="${amountLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>								
					</select>				
			</p>				
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

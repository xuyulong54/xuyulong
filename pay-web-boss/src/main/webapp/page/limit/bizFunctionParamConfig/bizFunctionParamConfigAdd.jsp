<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<form method="post"  class="pageForm" name="riskTypeAdd" 
		action="bizFunctionSwitch_addBizFunctionSwitch.action" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<!-- 基本信息 -->
		<div class="pageFormContent" layoutH="58">
  			<input type="hidden" name="navTabId" value="listBizFunctionSwitch">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<p>
				<label>开关限制包：</label>
					<select name=switchLimitPackId>
						<option value="">请选择</option>
						<c:forEach items="${switchLimitPacksList}" var="model">
							<option value="${model.id}">${model.name}</option>
						</c:forEach>
				    </select>				
			</p>
			<p>
				<label>业务功能：</label>				
				<select name=bizFunction>
						<option value="">请选择</option>
						<c:forEach items="${trxTypeEnumList}" var="model">
							<option value="${model.value}">${model.desc}</option>
						</c:forEach>
				    </select>		
			</p>	
			<p>
				<label>状态：</label>
				<select name=status>
						<option value="">请选择</option>
						<c:forEach items="${SwitchLimitStatusEnumList}" var="model">
							<option value="${model.value}">${model.label}</option>
						</c:forEach>
				</select>							
			</p>			
		</div>	
		<div class="formBar">
			<ul>
					<li>
						<div class="buttonActive">
							<div class="buttonContent">
								<button type="submit">添加</button>
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

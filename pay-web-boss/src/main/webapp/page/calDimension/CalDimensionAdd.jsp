<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="calDimension_calDimensionSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="calDimension">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60" >
		
			<div class="unit">
				<label>计费产品编号：</label>
				<input type="text" id="calProduct" value="${calProduct}"  size="30" name="calProduct" />
			</div>
		
			<div class="unit">
				<label>计费接口：</label>
				<select name="calCostInterfaceCode" id="calCostInterfaceCode">
					<option value="">请选择</option>
					<c:forEach items="${calCostInterfaceList }" var="calCostInterface">
						<option value="${calCostInterface.interfaceCode }" >${calCostInterface.interfaceName}</option>
					</c:forEach>
				</select>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>


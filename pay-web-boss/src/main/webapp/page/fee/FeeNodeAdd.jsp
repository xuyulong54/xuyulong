<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="pageForm" method="post" action="FeeNode_addNode.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listNode">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">

			<p>
				<label>节点名称：</label>
				<s:textfield name="feeNodeName" id="feeNodeName" cssClass="required" minlength="1" maxlength="50" size="30" />
			</p>
			<p>
				<label>节点类型：</label>
				<select name="feeNodeType" class="required">
					<option value="">请选择</option>
					<c:forEach items="${feeModelTypeEnums}" var="feeModelType">
						<option value="${feeModelType.value}">${feeModelType.desc }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>计费项：</label>
				<select name="calculateFeeItem" class="required">
					<option value="">请选择</option>
					<c:forEach items="${CalculateFeeItemEnums}" var="CalculateFeeItemE">
						<option value="${CalculateFeeItemE.value}">${CalculateFeeItemE.desc }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>描述：</label>
				<s:textarea cols="25" rows="3" name="desc" cssClass="required" maxlength="300"></s:textarea>
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
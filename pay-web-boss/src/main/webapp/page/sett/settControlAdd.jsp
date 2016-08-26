<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="js/commonDate.js"></script>
<div class="pageContent">
	<form name="addSettControlForm" method="post" action="SettControl_addSettControl.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listSettControl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<div class="unit">
				<label>结算控制方式：</label>
				<select name="settModeType" class="required" >
							<option value="">请选择</option>
							<c:forEach items="${SettModeTypeEnum }" var="enums">
								<option value="${enums.value }" <c:if test="${settModeType eq enums.value }"> selected="selected" </c:if>>${enums.desc }</option>
							</c:forEach>
						</select>
			</div>
			<div class="unit">
				<label>结算控制开始时间：</label>
				<input type="text" id="beginTime" name="beginTime" required="required" value="${fn:substring(beginTime,0,10)}" class="date" dateFmt="HH:mm:ss" size="25" readonly="true" />
			</div>
			<div class="unit">
				<label>结算控制结束时间：</label>
				<input type="text" id="endTime" name="endTime" required="required" value="${fn:substring(endTime,0,10)}" class="date" dateFmt="HH:mm:ss" size="25" readonly="true" />
			</div>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="checkDateAndForm('addSettControlForm','beginTime','endTime');">保存</button>
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

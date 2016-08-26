<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="js/commonDate.js"></script>
<div class="pageContent">
	<form name="updateSettControlForm" method="post" action="SettControl_updateSettControl.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listSettControl">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="settModeType" value="${settModeType }">
			<div class="unit">
				<label>结算控制方式：</label>
				<c:forEach items="${SettModeTypeEnum }" var="enums">
					<c:if test="${settModeType eq enums.value }">
						<input type="text" value="${enums.desc }" readonly="readonly" />
					</c:if>
				</c:forEach>
			</div>
			<div class="unit">
				<label>结算控制开始时间：</label>
				<input type="text" id="beginTime" name="beginTime" required="required" value="<fmt:formatDate value="${beginTime}" pattern="HH:mm:ss" />" class="date" dateFmt="HH:mm:ss" readonly="readonly" />
			</div>
			<div class="unit">
				<label>结算控制结束时间：</label>
				<input type="text" id="endTime" name="endTime" required="required" value="<fmt:formatDate value="${endTime}" pattern="HH:mm:ss" />" class="date" dateFmt="HH:mm:ss" readonly="readonly" />
			</div>

		</div>
		<div class="formBar">
			<ul>
				<li>
					<div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="checkDateAndForm('updateSettControlForm','beginTime','endTime');">保存</button>
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

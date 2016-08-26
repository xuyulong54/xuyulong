<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
		<form id="form" method="post" action="globalSet_editGlobalSet.action" class="pageForm required-validate" 
			onsubmit="return validateCallback(this, dialogAjaxDone);">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}">
		<div class="part" layoutH="58">
			<table>
				<tr>
					<td class="n_td">设置键名</td>
					<td><input type="text" id="setKey" name="setKey" readonly="readonly" value="${setKey}" size="76"/></td>
				</tr>
				<tr>
					<td class="t_td">设置内容：</td>
					<td style="text-align: left"><textarea id="setContent" name="setContent"
							style="width: 480px; height: 80px" maxlength="3000">${setContent}</textarea></td>
				</tr>
				<tr>
					<td class="n_td">描述：</td>
					<td><input id="description" name="description" type="text" value="${description}" size="76"/></td>
				</tr>
			</table>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${empty view }">
					<li><div class="buttonActive">
							<div class="buttonContent">
								<button type="button" onclick="submitForm();">保存</button>
							</div>
						</div>
					</li>
				</c:if>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function submitForm(){
	$("#form").submit();
}
</script>
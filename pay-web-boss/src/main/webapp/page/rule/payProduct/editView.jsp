<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
 <%@include file="/page/inc/pageForm.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="payProduct_editPayProduct.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="listPayProduct">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id }">
			<p>
				<label>支付产品编号：</label>
				<s:textfield name="payProductCode"  id="payProductCode" cssClass="required" minlength="1" maxlength="25" size="30" readonly="true"  />
			</p>
			<p>
				<label>支付产品名称：</label>
				<s:textfield name="payProductName" id="payProductName" cssClass="required" minlength="1" maxlength="25" size="30" readonly="true" />
			</p>
			<p>
				<label>状态：</label>
				<select name="status" >
					<c:forEach var="item" items="${statusList }">
						<option value="${item.value }" <c:if test="${status == item.value}">selected ='selected'</c:if>>${item.desc} </option>
					</c:forEach>
				</select>
			
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="submit" >保存</button>
						</div>
					</div></li>
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
/* 	var payProductName=$("#payProductName").val();
	if(payProductName ==null || '' == payProductName){
		alert("支付产品称名不能为空!");
		return ;
	} */
	$("#form").submit();
}
</script>
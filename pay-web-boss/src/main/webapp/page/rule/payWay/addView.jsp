<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="payWay_payWaySave.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="payWayList">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="payProductCode" value="${payProductCode }">
			<p>
				<label>支付方式编号：</label>
				<input type="text" name="payWayCode" value="${payWayCode }" size="35" id="payWayCode" />
			</p>
			<p>
				<label>支付方式名称：</label>
				<input type="text" name="payWayName" value="${payWayName }" size="35" id="payWayName"/>
			</p>
			<p>
				<label>支付渠道：</label>
			<select name="defaultPayInterface">
					<c:forEach items="${frps }" var="item" >
						<option value="${item.frpCode }">${item.bankName }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>排序值：</label>
				<input type="text" name="sort" value="${sort }" size="10"  alt="1000" />
			</p>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm();">保存</button>
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
	var payWayCode=$("#payWayCode").val();
	var payWayName=$("#payWayName").val();
	if(payWayCode == null||''==payWayCode){
		alert("支付方式编号不能为空!");
		return ;
	}
	if(payWayName ==null || '' == payWayName){
		alert("支付方式称名不能为空!");
		return ;
	}
	$("#form").submit();
}
</script>
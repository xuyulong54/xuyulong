<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="payWayForm" method="post" action="payWay_payWayEdit.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="payWayList">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${payWay.id}">
			<p>
				<label>支付方式编号：</label>
				<input type="text" name="payWayCode" value="${payWay.payWayCode }" id="payWayCode" size="35" readonly="readonly" />
			</p>
			<p>
				<label>支付方式名称：</label>
				<input type="text" name="payWayName" value="${payWay.payWayName }" id="payWayName" size="35" readonly="readonly"  />
			</p>
			<c:if test="${not empty bankBranch}">
				 <p>
					<label>默认支付接口：</label>
					<%-- <input type="text" name="defaultPayInterface" value="${payWay.defaultPayInterface }" id="defaultPayInterface" size="35" readonly="readonly"  /> --%>
					<select name="defaultPayInterface" id="defaultPayInterface">
						<!-- <option value="">请选择</option> -->
						<option value="${bankBranch.defaultBankChannelCode}" <c:if test="${payWay.defaultPayInterface eq bankBranch.defaultBankChannelCode}">selected="selected"</c:if>>${bankBranch.defaultBankChannelCode}</option>
						<option value="${bankBranch.spareBankChannelCode}" <c:if test="${payWay.defaultPayInterface eq bankBranch.spareBankChannelCode}">selected="selected"</c:if>>${bankBranch.spareBankChannelCode}</option>
					</select>
				</p> 
			</c:if>
			<p>
				<label>排序值：</label>
				<input type="text" name="sort" value="${payWay.sorts }" size="10"  alt="1000" />
			</p>
		</div>
		
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm();">修改</button>
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
	$("#payWayForm").submit();
}
</script>
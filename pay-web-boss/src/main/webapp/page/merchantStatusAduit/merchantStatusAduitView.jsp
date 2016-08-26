<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="userAuditRecordStatus_auditRefuseStatus.action" class="pageForm required-validate subform" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			
			<input type="hidden" name="changeStatus" id="changeStatus" value="${changeStatus }"  />
  			<input type="hidden" name="id" id="id" value="${id}"  />
  			<input type="hidden" name="userNo" id="userNo" value="${userNo }"  />
  			
			<div class="unit">
				<label>待审核商户登录名：</label>
				<input type="text" name="loginName" id="loginName" value="${loginName }"  readonly="true"/>
			</div>
			<c:if test="${not empty isView }">
			<div class="unit">
				<label>审核操作员：</label>
				<input type="text" name="auditOperatorLoginName" id="auditOperatorLoginName" value="${auditOperatorLoginName }" readonly="true"  />
			</div>
			</c:if>
			<div class="unit">
				<label>当前状态：</label>
				<c:if test="${currentStatus eq MerchantStatusEnums.ACTIVE.value }">${MerchantStatusEnums.ACTIVE.desc }</c:if>
				<c:if test="${currentStatus eq MerchantStatusEnums.INACTIVE.value }">${MerchantStatusEnums.INACTIVE.desc }</c:if>
				<c:if test="${currentStatus eq MerchantStatusEnums.CREATED.value }">${MerchantStatusEnums.CREATED.desc }</c:if>
				<c:if test="${currentStatus eq MerchantStatusEnums.NOPASS.value }">${MerchantStatusEnums.NOPASS.desc }</c:if>
			</div>
			<div class="unit">
				<label>申请变更状态：</label>
				<c:if test="${changeStatus eq MerchantStatusEnums.ACTIVE.value }">${MerchantStatusEnums.ACTIVE.desc }</c:if>
				<c:if test="${changeStatus eq MerchantStatusEnums.INACTIVE.value }">${MerchantStatusEnums.INACTIVE.desc }</c:if>
			</div>
			<div class="unit">
				<label>申请描述：</label>
				<s:textarea name="applyDesc" cols="35" rows="5" readonly="true" ></s:textarea>
			</div>
			<div class="unit">
				<label>审核描述：</label>
				<s:textarea name="auditDesc" cols="35" rows="5" cssClass="required"></s:textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${ empty isView }">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" class='pass' >审核通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >审核不通过</button></div></div></li>
				</c:if>
				<c:if test="${not empty isView }">
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</c:if>
			</ul>
		</div>
	</form>
</div>

<script>
	$(function(){
		$('.pass').bind('click',function(){	
			$('.subform').attr('action','userAuditRecordStatus_auditPassStatus.action');
			$('#form').submit();
		});
	})
</script>
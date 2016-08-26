<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"  class="pageForm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			
			<input type="hidden" name="changeStatus" id="changeStatus" value="${status }"  />
  			<input type="hidden" name="id" id="id" value="${id}"  />
			<div class="unit">
				<label>登录名：</label>
				${loginName }
			</div>
			<div class="unit">
				<label>商户编号：</label>
				<input type="hidden" name="userNo" id="userNo" value="${userNo}"  />
				${userNo }
			</div>
			
			<div class="unit">
				<label>申请变更状态：</label>
				<label>注销</label>
			</div>
			<div class="unit">
				<label>申请描述：</label>
				<s:textarea name="applyDesc" cols="35" rows="5" readonly="true" ></s:textarea>
			</div>
			<c:if test="${not empty isView }">
				<div class="unit">
					<label>审核状态：</label>
					<c:forEach items="${userAuditStatusEnum }" var="model">
						<c:if test="${auditStatus eq model.value }">${model.desc }</c:if>
					</c:forEach>
				</div>
			</c:if>
			<div class="unit">
				<label>审核描述：</label>
				<textarea name="auditDesc" cols="35" rows="5" class="required" <c:if test="${not empty isView }">readonly="readonly"</c:if>>${auditDesc }</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${ empty isView }">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMerchant('100');" >审核通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMerchant('101');" >审核不通过</button></div></div></li>
				</c:if>
				<c:if test="${not empty isView }">
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</c:if>
			</ul>
		</div>
	</form>
</div>

<script>
	function auditMerchant(status){
		$("#form").attr("action", "merchantCell_audit.action?status="+status);
		$("#form").submit();
	}
	
	// 隐藏保留功能的DIV
	function hideOptionDiv(obj){
		if(obj == 1){
			$("#optionDiv").show();
		}else{
			$("#optionDiv").hide();
		}
	}
</script>
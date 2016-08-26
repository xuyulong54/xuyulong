<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"  class="pageForm" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			
  			<input type="hidden" name="id" id="id" value="${cancel.id }"  />
			<div class="unit">
				<label>登录名：</label>
				<input type="text" readonly="readonly" name="loginName" value="${cancel.loginName }" />
			</div>
			<div class="unit">
				<label>真实姓名：</label>
				<input type="text" readonly="readonly" name="realName" value="${cancel.fullName }" />
			</div>
			<div class="unit">
				<label>发起时间：</label>
				<input type="text" readonly="readonly" name="createTime" value="<fmt:formatDate value="${cancel.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>" />
			</div>
			<div class="unit">
				<label>销户原因：</label>
				<textarea rows="5" cols="40" name="cancellationReason" readonly="readonly">${cancel.applyDesc }</textarea>
			</div>
			<c:if test="${not empty isView }">
				<div class="unit">
					<label>审核状态：</label>
					<label>
						<c:if test="${cancel.auditStatus eq UserAuditStatusEnum.AGREE.value}">
							审核通过
						</c:if>
						<c:if test="${cancel.auditStatus eq UserAuditStatusEnum.WAIT.value }">
							申请中
						</c:if>
						<c:if test="${cancel.auditStatus eq UserAuditStatusEnum.REFUSE.value }">
							审核不通过
						</c:if>
					</label>
				</div>
			</c:if>
			<div class="unit">
				<label>审核描述：</label>
				<label><textarea name="auditDesc" id="auditDesc" cols="35" rows="5" class="required" <c:if test="${not empty isView }">readonly="readonly"</c:if>>${cancel.auditDesc }</textarea>
				</label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${empty isView }">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMemberCancel('${UserAuditStatusEnum.AGREE.value}');" >审核通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMemberCancel('${UserAuditStatusEnum.REFUSE.value}');" style="width: 90px;">审核不通过</button></div></div></li>
				</c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script>
	function auditMemberCancel(status){
		$("#form").attr("action", "memberCancel_doAuditMemberCancel.action?auditStatus="+status);
		$("#form").submit();
	}
</script>
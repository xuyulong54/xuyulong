<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"  class="pageForm" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			<input type="hidden" name="userNo" id="userNo" value="${userNo }"  />
  			<input type="hidden" name="auditId" id="auditId" value="${id}"  />
  			<input type="hidden" name="changeStatus" id="changeStatus" value="${changeStatus}"  />
  			<input type="hidden" name="currentStatus" id="currentStatus" value="${currentStatus}"  />
  			
			<div class="unit">
				<label>用户名：</label>
				<input  type="text" name="loginName" readonly="readonly" value="${loginName }" />
			</div>
			<div class="unit">
				<label>申请变更状态：</label>
				<label>${changeStatus == 101 ? '冻结' : '激活' }</label>
			</div>
			<div class="unit">
				<label>申请描述：</label>
				<s:textarea name="applyDesc" cols="35" rows="5" readonly="true" ></s:textarea>
			</div>
			<c:if test="${not empty isView }">
				<div class="unit">
					<label>申请状态：</label>
					<c:if test="${auditStatus eq UserAuditStatusEnum.AGREE.value }">
						审核通过
					</c:if>
					<c:if test="${auditStatus eq UserAuditStatusEnum.REFUSE.value }">
						申请中
					</c:if>
					<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value  }">
						审核不通过
					</c:if>
				</div>
			</c:if>
			<div class="unit">
				<label>审核描述：</label>
				<textarea  cols="35" rows="5" class="required" <c:if test="${not empty isView }">readonly="readonly"</c:if> name="auditDesc">${auditDesc }</textarea>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${ empty isView }">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMerchant('${UserAuditStatusEnum.AGREE.value}');" >审核通过</button></div></div></li>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMerchant('${UserAuditStatusEnum.REFUSE.value}');" style="width:90px;" >审核不通过</button></div></div></li>
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
		$("#form").attr("action", "memberInfo_doAduit.action?status="+status);
		$("#form").submit();
	}
</script>
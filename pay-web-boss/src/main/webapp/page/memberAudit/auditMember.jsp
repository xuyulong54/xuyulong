<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post"  class="pageForm" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			
  			<input type="hidden" name="id" id="id" value="${id}"  />
  			<input type="hidden" name="userNo" id="userNo" value="${userNo}"  />
			<div class="unit">
				<label>账户名：</label>
				<input  type="text" name="loginName" readonly="readonly" value="${loginName }"/>
			</div>
			<div class="unit">
				<label>真实姓名：</label>
				<input  type="text" name="realName" readonly="readonly" value="${realName }"/>
			</div>
			<div class="unit">
				<label>身份证号码：</label>
				<input  type="text" name="cardNo" readonly="readonly" value="${cardNo }"/>
			</div>
			<div class="unit">
				<label>身份证有效期：</label>
				<input  type="text" name="cardNoValid" readonly="readonly" value="<fmt:formatDate value="${cardNoValid }" pattern="yyyy-MM-dd" />"/>
			</div>
			<div class="unit">
				<label>国籍：</label>
				<input  type="text" name="country" readonly="readonly" value="${memberInfo.country }"/>
			</div>
			<div class="unit">
				<label>职业：</label>
				<input  type="text" name="profession" readonly="readonly" value="${memberInfo.profession }"/>
			</div>
			<div class="unit">
				<label>申请时间：</label>
				<input  type="text" name="createTime" readonly="readonly" value="<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" />"/>
			</div>
			<c:if test="${not empty isView }">
				<div class="unit">
					<label>申请状态：</label>
					<c:if test="${auditStatus eq UserAuditStatusEnum.AGREE.value }">
							审核通过
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.WAIT.value }">
							申请中
						</c:if>
						<c:if test="${auditStatus eq UserAuditStatusEnum.REFUSE.value }">
							审核不通过
						</c:if>
				</div>
			</c:if>
			<div class="unit">
				<label>身份证正面照：</label>
				<label>
					<img src="${cardFrontPath }" width="400" height="200" />
				</label>
			</div>
			<div class="unit">
				<label>身份证反面照：</label>
				<label>
					<img src="${cardBackPath }" width="400" height="200" />
				</label>
			</div>
			
			<div class="unit">
				<label>审核描述：</label>
				<label>
					<textarea name="auditDesc" <c:if test="${isView eq 1 }">readonly="readonly"</c:if> id="auditDesc" cols="35" rows="5" cssClass="required" class='auditDesc'>${auditDesc }</textarea>
				</label>
			</div>
		</div>
		<div class="formBar">
			<ul>
				<c:if test="${ empty isView }">
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMember('100');" >审核通过</button></div></div></li>
					<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMember('102');" style="width: 90px;">审核不通过</button></div></div></li>
				</c:if>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script>
	function auditMember(status){
		var auditDesc = $("#auditDesc").val();
		if(auditDesc == null || auditDesc == ""){
			alertMsg.error("请输入审核描述");
			return ;
		}
		$("#form").attr("action", "memberAudit_doAuditMember.action?status="+status);
		$("#form").submit();
	}
</script>
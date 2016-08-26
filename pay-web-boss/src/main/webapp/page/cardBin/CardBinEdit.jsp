<%@ page language="java" contentType="text/html; charset=UTF-8" 
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" 
		action="cardBin_editCardBin.action" 
		class="pageForm required-validate" 
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		<input type="hidden" name="navTabId" value="list">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${id}" />
			 <p>
				<label>卡bin：</label>
				<input type="text" value="${cardBin}"  size="30" name="cardBin"  cssClass="required " minlength="1" maxlength="10" />
			</p>
			
			<p>
				<label>发卡行代码：</label>
				<input type="text" value="${bankCode}"  size="30" name="bankCode"  cssClass="required " minlength="1" maxlength="25" />
			</p>
			<p>
				<label>发卡行名称：</label>
				<input type="text" value="${bankName}"  size="30" name="bankName"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>卡名：</label>
				<input type="text" value="${cardName}"  size="30" name="cardName"  cssClass="required " minlength="1" maxlength="50" />
			</p>
			<p>
				<label>卡种：</label>
				<select name="cardKind" style="width:131px">
						<c:forEach items="${cardKindEnums }" var="v">
							<option value="${v.value }" <c:if test="${cardKind eq v.value }">selected="selected"</c:if>>${v.desc}</option>
						</c:forEach>
				</select>
			</p>
			<p>
				<label>卡片长度：</label>
				<input type="text" value="${cardLength}"  size="30" name="cardLength"  cssClass="required " minlength="1" maxlength="1000" />
			</p>
			<p>
				<label>状态：</label>
				<select name="status" style="width:131px" id="status">
					<option value="${cardBinStatusEnums.YES.value }" <c:if test="${status eq cardBinStatusEnums.YES.value}">selected="selected"</c:if>>${cardBinStatusEnums.YES.desc }</option>
					<option value="${cardBinStatusEnums.NO.value }" <c:if test="${status eq cardBinStatusEnums.NO.value}">selected="selected"</c:if>>${cardBinStatusEnums.NO.desc }</option>
				</select>
			</p>
			
			</div>
		
			<div class="formBar">
				<ul>
					<li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
	</form>
</div>
<script type="text/javascript">
ty($("#terminalType"));
function ty(terminalTypeId){
		if(terminalTypeId.val()==2){
			$("#phone1").css("display", "none");
			$("#phone2").css("display", "none");
			$("#phone3").css("display", "none");
			$("#sim").css("display", "");
		}else if(terminalTypeId.val()==3){
			$("#sim").css("display", "none");
			$("#phone1").css("display", "");
			$("#phone2").css("display", "");
			$("#phone3").css("display", "");
		}else{
			$("#phone1").css("display", "none");
			$("#phone2").css("display", "none");
			$("#phone3").css("display", "none");
			$("#sim").css("display", "none");
		}
	}
</script>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
<h2><s:if test="code==null">新建</s:if><s:else>修改</s:else>字典信息</h2>
</div>
<div class="pageContent">
	<form id="form" method="post" action="bank_saveDict.action" class="pageForm required-validate" onsubmit="return validateCallback(this, navTabAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${code }" id="id">

			
			<p style="width:99%">
				<label>银行编号：</label>
				<s:select headerValue="请选择" headerKey="" list="#codeMap" 
				name="code" listKey="key" listValue="key" cssClass="required"/>
			</p>
			<p style="width:99%">
				<label>银行名称：</label>
				<s:textfield name="name" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="submitForm()">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	function submitForm() {
		var id = $("#id").val();
		if (id.length > 0) {
			var url = "bank_saveEditDict.action";
			$("#form").attr("action", url);
		}

		$("#form").submit();
	}

</script>

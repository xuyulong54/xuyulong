<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="pagerForm22" onsubmit="return iframeCallback(this);" enctype="multipart/form-data" action="remitBankInfo_remitBankInfoUploadSave.action" method="post" class="pageForm required-validate">
		<div class="pageFormContent" layoutH="58">
			<p>
				<label>文件上传：</label>
				<input type="file" name="remitBankInfoFile"/>
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

<script>
	function submitForm(){
		$("#pagerForm22").submit();	
		$.pdialog.closeCurrent();
	}
</script>

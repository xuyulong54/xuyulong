<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="pagerForm22" onsubmit="return iframeCallback(this);" enctype="multipart/form-data" 
				action="contract_addContractFile.action" method="post" 
				class="pageForm required-validate">
		<div class="pageFormContent" layoutH="58">
  			
  			<p>
				<label>文件类型：</label>
				<select name="contractType" id="contractType">
					<option value="">--请选择--</option>
					<c:forEach items="${contractTypeList }" var="model">
						<option value="${model.value }"
						<c:if test="${contractType eq model.value}">selected="selected"</c:if>>${model.desc }</option>
					</c:forEach>
				</select>
			</p>
			<p>
				<label>文件性质：</label>
				<select name="fileProperties" id="fileProperties">
					<c:forEach items="${filePropertiesList }" var="model">
						<option value="${model.value }"
						<c:if test="${fileProperties eq model.value}">selected="selected"</c:if>>${model.desc }</option>
					</c:forEach>
				</select>
			</p>
			<p id="tipsMsgDiv1">
				<label>商户名称：</label>
				<input type="hidden"  id="merchantId" name="merchantId"/>
				<input type="hidden"  id="merchantNo" name="merchantNo"/>
				<input id="merchantName" type="text" readonly="true" size="30" name="merchantName"/>
				<a class="btnLook" id="findBtn" href="contract_onlineMerchantLookupList.action" lookupGroup="merchantListDiv">搜索</a>
			</p>
			<p id="tipsMsgDiv2">
				<label>银行协议：</label>
				<input type="hidden" name="bankNo"  id="bankNo"/>
				<input type="hidden" name="bankId"  id="bankId"/>
				<input id="bankName" type="text" readonly="true" size="30" name="bankName"/>
				<a class="btnLook" id="findBtn" href="contract_listBankAgreement.action" lookupGroup="bankListDiv">搜索</a>
			</p>
			<p>
				<label>文件扫描件：</label>
				<input id="cardFront1" type="file" name="file"/>
				<div id="message"></div>
			</p>
			<div class="unit" id="sign">
				<label>签约日期：</label>
				<input type="text" name="signTime" id="signTime" maxDate="{%y}-%M-{%d}" value="<fmt:formatDate value="${signTime }" pattern="yyyy-MM-dd" />" class="date" size="30" readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit" id="contract">
				<label>合同到期日：</label>
				<input type="text" name="contractValid" id="contractValid" minDate="{%y}-%M-{%d}" value="<fmt:formatDate value="${contractValid }" pattern="yyyy-MM-dd" />" class="date" size="30"
					readonly="readonly" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<p>
				<label>描述：</label>
				<textarea rows="3" cols="30" name="desc"></textarea>
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
$.extend({
	bringBackSuggest : function(args) {
		$("input[id='merchantId']").val(args["merchantId"]);
		$("input[id='merchantNo']").val(args["merchantNo"]);
		$("input[id='merchantName']").val(args["merchantName"]);
		$("input[id='bankNo']").val(args["bankNo"]);
		$("input[id='bankId']").val(args["bankId"]);
		$("input[id='bankName']").val(args["bankName"]);
	},
});

function submitForm(){
	$("#pagerForm22").submit();	
	$.pdialog.closeCurrent();
}

$(document).ready(function(){
	$("#tipsMsgDiv1").hide();
	$("#tipsMsgDiv2").hide();
	$("#sign").hide();
	$("#contract").hide();
$("select[name='contractType']").each(function(){
		$(this).bind("click",function(){
			if($(this).attr("value")=="1"){
				$("#tipsMsgDiv1").show();
				$("#tipsMsgDiv2").hide();
				$("#sign").show();
				$("#contract").show();
			}else if($(this).attr("value")=="2"){
				$("#tipsMsgDiv2").show();
				$("#tipsMsgDiv1").hide();
				$("#sign").hide();
				$("#contract").hide();
			}else{
				$("#tipsMsgDiv1").hide();
				$("#tipsMsgDiv2").hide();
			}
		});
	});

});
</script>

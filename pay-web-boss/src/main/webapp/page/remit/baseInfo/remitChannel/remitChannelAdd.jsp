<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="remitChannel_remitChannelSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="remitChannel">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<div class="pageFormContent" layoutH="60" >
			<p>
				<label>打款通道编号：</label>
				<input type="text" id="channelCode" value="${channelCode}" class="required"  size="30" name="channelCode" />
			</p>
			
			<p>
				<label>打款通道名称：</label>
				<input type="text" id="channelName" value="${channelName}"  class="required" size="30" name="channelName" />
			</p>
			
			
			
			<p>
				<label>所属银行：</label>
				<select name="bankTypeCode" id="bankTypeCodeAdd" class="required">
					<option value="">请选择</option>
					<c:forEach items="${remitBankTypeList }" var="remitBankType">
						<option value="${remitBankType.typeCode}" >${remitBankType.typeName}</option>
					</c:forEach>
				</select>
			</p>
			
			<p>
				<label>总笔数限制：</label>
				<input type="text" id="limitNum"  class="digits required" min="1" max="1000" value="50" size="30" name="limitNum" />
			</p>
			
			<p>
				<label>状态：</label>
				<c:forEach items="${publicStatusEnumList }" var="publicStatusEnum" varStatus="index">
					<input type="radio" name="status" value="${publicStatusEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${publicStatusEnum.desc}
				</c:forEach>
			</p>
			
			<p>
				<label>最小额：</label>
				<input type="text" id="minAmount" value="${minAmount}"  class="number required" size="30" name="minAmount" />
			</p>
			
			<p>
				<label>最大额：</label>
				<input type="text" id="maxAmount" value="${maxAmount}"  class="number required" size="30" name="maxAmount" />
			</p>
			
			<p>
				<label>单个批次最小额：</label>
				<input type="text" id="batchMinAmount" value="${batchMinAmount}"  class="number required" size="30" name="batchMinAmount" />
			</p>
			
			<p>
				<label>单个批次最大额：</label>
				<input type="text" id="batchMaxAmount" value="${batchMaxAmount}"  class="number required" size="30" name="batchMaxAmount" />
			</p>
			
			<%-- <p>
				<label>是否支持加急：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index" >
					<input type="radio" name="isUrgent" value="${isOrNotEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</p> --%>
			
			<%-- <p>
				<label>是否支持打款确认：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isConfirm" value="${isOrNotEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</p> --%>
			
			<p>
				<label>是否支持自动打款：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isAutoRemit" value="${isOrNotEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</p>
			
			<p>
				<label>付款账户类型：</label>
			
				<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum" varStatus="index">
					<input type="radio" name="accountType" value="${bankAccountTypeEnum.value}" 
					<c:if test="${index.index eq 1 }">checked="checked"</c:if> 
					 />${bankAccountTypeEnum.desc}
				</c:forEach>
			</p>
			
			<p>
				<label>付款帐号：</label>
				<input type="text" id="srcAccountNum" value="${srcAccountNum}" size="30" name="srcAccountTag.srcAccountNum" class="required" readonly/>
				<a class="btnLook" id="btnLookUp" href="remitChannel_remitSrcAccountInfo.action"
							lookupGroup="srcAccountTag" >搜索</a> <span class="info">搜索</span>
			</p>
			
			<p>
				<label>付款帐号户名：</label>
				<input type="text" id="srcAccountName" value="${srcAccountName}" size="30" name="srcAccountTag.srcAccountName" class="required" readonly/>
			</p>
			
			<p>
				<label>付款账号开户行名称：</label>
				<input type="text" id="srcBankName" value="${srcBankName}" size="30" name="srcAccountTag.srcBankName" class="required" readonly/>
			</p>
			
			<p>
				<label>付款账号开户行行号：</label>
				<input type="text" id="srcBankChannelNo" value="${srcBankChannelNo}" size="30" name="srcAccountTag.srcBankChannelNo" class="required" readonly/>
			</p>
			
			
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="addSave" >添加</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$("#bankTypeCodeAdd").change(function(){
			var val = $(this).children('option:selected').text();
			val = encodeURI(encodeURI(val));
			$("#btnLookUp").attr("href","remitChannel_remitSrcAccountInfo.action?openBank="+val);
		});
	});
	
	
	
$.extend({
	bringBackSuggest : function(args) {
		$("input[name='srcAccountTag.srcAccountNum']").val(args["srcAccountNum"]);
		$("input[name='srcAccountTag.srcAccountName']").val(args["srcAccountName"]);
		$("input[name='srcAccountTag.srcBankName']").val(args["srcBankName"]);
		$("input[name='srcAccountTag.srcBankChannelNo']").val(args["srcBankChannelNo"]);
	},
	bringBack : function(args) {
		$.bringBackSuggest(args);
		$.pdialog.closeCurrent();
	}
});
</script>

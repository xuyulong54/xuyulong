<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="remitBankInfo_remitBankInfoUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="remitBankInfo">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60" >
			<p>
				<label>银行行号：</label>
				<input type="text" id="bankChannelNo" value="${bankChannelNo}" class="required digits" minlength="12" maxlength="12"  size="30" name="bankChannelNo" />
			</p>
			
			<p>
				<label>银行名称：</label>
				<input type="text" id="bankName" value="${bankName}" class="required"  size="30" name="bankName" />
			</p>
			
			<p>
				<label>类别：</label>
				<c:forEach items="${bankTypeEnumList }" var="bankTypeEnum" varStatus="index">
					<input type="radio" name="bankType" value="${bankTypeEnum.value}" 
					<c:if test="${bankTypeEnum.value eq bankType }">checked="checked"</c:if> 
					 />${bankTypeEnum.desc}
				</c:forEach>
				
			</p>
			
			<p>
				<label>清算行号：</label>
				<input type="text" id="clearBankChannelNo" value="${clearBankChannelNo}" class="required digits" minlength="12" maxlength="12"   size="30" name="clearBankChannelNo" />
			</p>
			
			<p>
				<label>省份：</label>
				<select name="province" id="bankInfoProvince" class="required">
					<option value="">请选择</option>
					<c:forEach items="${provinceList }" var="provinceT">
						<option value="${provinceT.province }" <c:if test="${province == provinceT.province }">selected="selected"</c:if>  >${provinceT.province} </option>
					</c:forEach>
				</select>
			</p>
			
			<p>
				<label>城市：</label>
				<select name="city" id="bankInfoCity" class="required">
					<option value="">请选择</option>
					<c:forEach items="${cityList }" var="cityT">
						<option value="${cityT.city }" <c:if test="${city eq cityT.city }">selected="selected"</c:if>  >${cityT.city}</option>
					</c:forEach>
				</select>
			</p>
			
			<p>
				<label>省内/省外：</label>
				<c:forEach items="${isInProvinceEnumList }" var="isInProvinceEnum" varStatus="index">
					<input type="radio" name="isInProvince" value="${isInProvinceEnum.value}" 
					<c:if test="${isInProvinceEnum.value eq isInProvince }">checked="checked"</c:if> 
					 />${isInProvinceEnum.desc}
				</c:forEach>
			</p>
			
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit" id="editSave" >保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>

<script type="text/javascript">
	$(function(){
		$("#bankInfoProvince").change(function(){
			$.post("remitOrder_getCityByProvince.action",{province:$(this).children('option:selected').val()},function(result){
				$("#bankInfoCity").empty();
				$("#bankInfoCity").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#bankInfoCity").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
		});
	});
</script>

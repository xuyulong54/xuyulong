<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="remitOrder_remitOrderUpdate.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="remitOrder">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		<input type="hidden" name="id" value="${id}" />
		<div class="pageFormContent" layoutH="60" >
			<div class="unit">
				<label>收款户名：</label>
				<input type="text" id="orderAccountNameEdit" value="${accountName}"   size="30" name="accountName" />
				<a class="btnLook" id="orderAccountInfoLookUp" href="remitOrder_remitOrderAccountInfo.action"
							lookupGroup="orderAccountInfoTag" width="900" >搜索</a> <span class="info">搜索</span>
			</div>
			
			<div class="unit">
				<label>收款账号：</label>
				<input type="text" id="orderAccountNameEdit" value="${accountNo}" class="required number"   size="30" name="accountNo" maxlength="20"  />
			</div>
			
			<%-- <div class="unit">
				<label>用户编号：</label>
				<input type="text" id="orderUserNoEdit" value="${userNo}"  size="30" name="userNo" />
			</div> --%>
			
			<div class="unit">
				<label>账户类型：</label>
				<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum" varStatus="index">
					<input type="radio" name="accountType" value="${bankAccountTypeEnum.value}" 
					<c:if test="${accountType eq bankAccountTypeEnum.value }">checked="checked"</c:if> 
					 />${bankAccountTypeEnum.desc}
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>收款人所在地区:</label>
				<select name="province" id="orderProvinceEdit">
					<option value="">请选择</option>
					<c:forEach items="${provinceList }" var="provinceT">
						<option value="${provinceT.province }" <c:if test="${province eq provinceT.province }">selected="selected"</c:if> >${provinceT.province}</option>
					</c:forEach>
				</select>
				<select name="city" id="orderCityEdit">
					<option value="">请选择</option>
					<c:forEach items="${cityList }" var="cityT">
						<option value="${cityT.city }" <c:if test="${city eq cityT.city}">selected="selected"</c:if> >${cityT.city}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>行别：</label>
				<select  id="orderBankTypeEdit">
					<option value="">请选择</option>
					<c:forEach items="${remitBankTypeList }" var="remitBankType">
						<option value="${remitBankType.typeCode }" <c:if test="${bankType eq remitBankType.typeCode}">selected="selected"</c:if> >${remitBankType.typeName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>开户行：</label>
				<select name="bankChannelNo" id="orderBankChannelNoEdit">
					<option value="">请选择</option>
					<c:forEach items="${remitBankInfoList }" var="remitBankInfo">
						<option value="${remitBankInfo.bankChannelNo }" <c:if test="${bankChannelNo eq remitBankInfo.bankChannelNo }">selected="selected"</c:if> >${remitBankInfo.bankName}</option>
					</c:forEach>
				</select>
			</div>
			<div class="unit">
				<label>开户行行号：</label>
				<input type="text"  size="30" id="orderAccountBankChannelNoEdit" value="${remitBankInfo.bankChannelNo }" readonly/>
			</div>
			<div class="unit">
				<label>清算行行号：</label>
				<input type="text"  size="30" id="orderClearBankChannelNoEdit" value="${remitBankInfo.clearBankChannelNo}" readonly/>
			</div>
			
			<div class="unit">
				<label>收款金额 ：</label>
				<input type="text" id="orderAmountEdit" value="${amount}" min="0"  size="30" name="amount" />
			</div>
			
			<div class="unit">
				<label>是否加急：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isUrgent" value="${isOrNotEnum.value}" 
					<c:if test="${isUrgent eq isOrNotEnum.value }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</div>
			
			<%-- <div class="unit">
				<label>是否自动处理：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isAutoProcess" value="${isOrNotEnum.value}" 
					<c:if test="${isAutoProcess eq isOrNotEnum.value  }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</div> --%>
			
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
		$("#orderProvinceEdit").change(function(){
			$.post("remitOrder_getCityByProvince.action",{province:$(this).children('option:selected').val()},function(result){
				$("#orderCityEdit").empty();
				$("#orderCityEdit").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#orderCityEdit").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
			var bankTypeCode = $("#orderBankTypeEdit").children('option:selected').val();
			var province = $("#orderProvinceEdit").children('option:selected').val();
			var city = $("#orderCityEdit").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankTypeEdit").children('option:selected').val(),
					province:$("#orderProvinceEdit").children('option:selected').val(),
					city:$("#orderCityEdit").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNoEdit").empty();
						$("#orderBankChannelNoEdit").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNoEdit").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNoEdit").val("");
				$("#orderClearBankChannelNoEdit").val("");
			}
			
		});
		
		$("#orderBankTypeEdit").change(function(){
			var bankTypeCode = $("#orderBankTypeEdit").children('option:selected').val();
			var province = $("#orderProvinceEdit").children('option:selected').val();
			var city = $("#orderCityEdit").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankTypeEdit").children('option:selected').val(),
					province:$("#orderProvinceEdit").children('option:selected').val(),
					city:$("#orderCityEdit").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNoEdit").empty();
						$("#orderBankChannelNoEdit").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNoEdit").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNoEdit").val("");
				$("#orderClearBankChannelNoEdit").val("");
			}
		});
		
		$("#orderCityEdit").change(function(){
			var bankTypeCode = $("#orderBankTypeEdit").children('option:selected').val();
			var province = $("#orderProvinceEdit").children('option:selected').val();
			var city = $("#orderCityEdit").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankTypeEdit").children('option:selected').val(),
					province:$("#orderProvinceEdit").children('option:selected').val(),
					city:$("#orderCityEdit").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNoEdit").empty();
						$("#orderBankChannelNoEdit").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNoEdit").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNoEdit").val("");
				$("#orderClearBankChannelNoEdit").val("");
			}
		});
		
		$("#orderBankChannelNoEdit").change(function(){
			$.post("remitOrder_getRemitBankInfoByBankChannelNo.action",{bankChannelNo:$(this).children('option:selected').val()},function(result){
				if(result.FAIL=="FAIL"){
					alertMsg.error("请选择开户行!");
				}else{
					$("#orderAccountBankChannelNoEdit").val(result.remitBankInfo.bankChannelNo);
					$("#orderClearBankChannelNoEdit").val(result.remitBankInfo.clearBankChannelNo);
				}
			},"json");
		});
		
		//查找带回数据
		$.extend({
			bringBackSuggest : function(args) {
			 	$("#orderAccountNameEdit").val(args["accountName"]);
				$("#orderAccountNoEdit").val(args["accountNo"]);
				$("#orderUserNoEdit").val(args["userNo"]);
				$("#orderAccountTypeEdit").find("input[value='"+args["accountType"]+"']").attr("checked","checked");
				$("#orderProvinceEdit").find("option[value='"+args["province"]+"']").attr("selected",true);
				$.post("remitOrder_getCityByProvince.action",{province:$("#orderProvinceEdit").children('option:selected').val()},function(result){
					$("#orderCityEdit").empty();
					$("#orderCityEdit").append("<option value=''>请选择</option>");
					for(var i=0;i<result.cityList.length;i++){
						$("#orderCityEdit").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
					}
					$("#orderCityEdit").find("option[value='"+args["city"]+"']").attr("selected",true);
					$("#orderBankTypeEdit").find("option[value='"+args["bankChannelNo"].substring(0,3)+"']").attr("selected",true);
					$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
						bankTypeCode:$("#orderBankTypeEdit").children('option:selected').val(),
						province:$("#orderProvinceEdit").children('option:selected').val(),
						city:$("#orderCityEdit").children('option:selected').val()
						},function(result){
							$("#orderBankChannelNoEdit").empty();
							$("#orderBankChannelNoEdit").append("<option value=''>请选择</option>");
							for(var i=0;i<result.remitBankInfoList.length;i++){
								$("#orderBankChannelNoEdit").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
							}
							$("#orderBankChannelNoEdit").find("option[value='"+args["bankChannelNo"]+"']").attr("selected",true);
							$("#orderBankChannelNoEdit").change();
						}
					,"json");
				
				},"json"); 
			},
			bringBack : function(args) {
				$.bringBackSuggest(args);
				$.pdialog.closeCurrent();
			}
		});
		
		
	});
</script>

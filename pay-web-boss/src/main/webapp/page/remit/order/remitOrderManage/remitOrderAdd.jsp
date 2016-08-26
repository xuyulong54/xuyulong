<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script src="js/provincesCity.js" type="text/javascript"></script>
<div class="pageContent">
	<form id="form" method="post" action="remitOrder_remitOrderSave.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		
		<input type="hidden" name="navTabId" value="remitOrder">
		<input type="hidden" name="callbackType" value="closeCurrent">
		<input type="hidden" name="forwardUrl" value="">
		
		<div class="pageFormContent" layoutH="60" >
			<div class="unit">
				<label>收款户名：</label>
				<input type="text" id="orderAccountName" value="${accountName}"  class="required" size="30" name="accountName" />
				<!-- <a class="btnLook" id="orderAccountInfoLookUp" href="remitOrder_remitOrderAccountInfo.action"
							lookupGroup="orderAccountInfoTag" width="900" >搜索</a> <span class="info">搜索</span> -->
			</div>
			
			<div class="unit">
				<label>收款账号：</label>
				<input type="text" id="orderAccountNo" value="${accountNo}" class="required number"  size="30" name="accountNo" maxlength="20" />
			</div>
			
			<%-- <div class="unit">
				<label>用户编号：</label>
				<input type="text" id="orderUserNo" value="${userNo}"  size="30" name="userNo" />
			</div> --%>
			
			<div class="unit">
				<label>账户类型：</label>
				<%-- <select name="accountType" id="accountType" value="${accountType}" >
					<option value="">请选择</option>
					<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum">
						<option value="${bankAccountTypeEnum.value }" <c:if test="${accountType eq bankAccountTypeEnum.value }">selected="selected"</c:if> >${bankAccountTypeEnum.desc}</option>
					</c:forEach>
				</select> --%>
				<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum" varStatus="index">
					<input type="radio" name="accountType" value="${bankAccountTypeEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${bankAccountTypeEnum.desc}
				</c:forEach>
			</div>
			
			<div class="unit">
				<label>收款人所在地区:</label>
				<select name="province" id="orderProvince" class="required">
					<option value="">请选择</option>
					<c:forEach items="${provinceList }" var="province">
						<option value="${province.province }">${province.province}</option>
					</c:forEach>
				</select>
				<select name="city" id="orderCity" class="required">
					<option value="">请选择</option>
				</select>
			</div>
			
			<div class="unit">
				<label>行别：</label>
				<select  id="orderBankType" class="required">
					<option value="">请选择</option>
					<c:forEach items="${remitBankTypeList }" var="remitBankType">
						<option value="${remitBankType.typeCode }" >${remitBankType.typeName}</option>
					</c:forEach>
				</select>
			</div>
			
			<div class="unit">
				<label>开户行：<!-- (<a href="#" id="searchWay" style="color:blue">切换查询</a>) --></label>
				<select name="bankChannelNo" id="orderBankChannelNo">
					<option value="">请选择</option>
					
				</select>
				<!-- <input type="text" name="bankChannelNo" id="bankChannelNoInput"  size="30"/> 
				<button type="button" id="searchButton">搜索</button> -->
			</div>
			<div class="unit">
				<label>开户行行号：</label>
				<input type="text"  size="30" id="orderAccountBankChannelNo" class="required" readonly/>
			</div>
			<div class="unit">
				<label>清算行行号：</label>
				<input type="text"  size="30" id="orderClearBankChannelNo" class="required" readonly/>
			</div>
			
			<div class="unit">
				<label>收款金额 ：</label>
				<input type="text" id="orderAmount" value="${amount}" class="required number" min="0"  size="30" name="amount" />
			</div>
			
			<%-- <div class="unit">
				<label>业务类型 ：</label>
				<select  id="orderBusinessType" name="businessType" class="required">
					<option value="">请选择</option>
					<c:forEach items="${tradeTypeEnumList }" var="tradeTypeEnum">
						<option value="${tradeTypeEnum.value }" >${tradeTypeEnum.desc}</option>
					</c:forEach>
				</select>
			</div> --%>
			
			<%-- <div class="unit">
				<label>是否加急：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isUrgent" value="${isOrNotEnum.value}" 
					<c:if test="${index.index eq 1 }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</div>
			
			<%-- <div class="unit">
				<label>是否自动处理：</label>
				<c:forEach items="${isOrNotEnumList }" var="isOrNotEnum" varStatus="index">
					<input type="radio" name="isAutoProcess" value="${isOrNotEnum.value}" 
					<c:if test="${index.index eq 0 }">checked="checked"</c:if> 
					 />${isOrNotEnum.desc}
				</c:forEach>
			</div> --%>
			
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
		/* $("#bankChannelNoInput").hide();
		$("#searchButton").hide(); */
		$("#orderProvince").change(function(){
			$.post("remitOrder_getCityByProvince.action",{province:$(this).children('option:selected').val()},function(result){
				$("#orderCity").empty();
				$("#orderCity").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#orderCity").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
			var bankTypeCode = $("#orderBankType").children('option:selected').val();
			var province = $("#orderProvince").children('option:selected').val();
			var city = $("#orderCity").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankType").children('option:selected').val(),
					province:$("#orderProvince").children('option:selected').val(),
					city:$("#orderCity").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNo").empty();
						$("#orderBankChannelNo").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNo").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNo").val("");
				$("#orderClearBankChannelNo").val("");
			}
			
		});
		
		$("#orderBankType").change(function(){
			var bankTypeCode = $("#orderBankType").children('option:selected').val();
			var province = $("#orderProvince").children('option:selected').val();
			var city = $("#orderCity").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankType").children('option:selected').val(),
					province:$("#orderProvince").children('option:selected').val(),
					city:$("#orderCity").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNo").empty();
						$("#orderBankChannelNo").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNo").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNo").val("");
				$("#orderClearBankChannelNo").val("");
			}
		});
		
		$("#orderCity").change(function(){
			var bankTypeCode = $("#orderBankType").children('option:selected').val();
			var province = $("#orderProvince").children('option:selected').val();
			var city = $("#orderCity").children('option:selected').val();
			if(bankTypeCode!=""&&province!=""&&city!=""){
				$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
					bankTypeCode:$("#orderBankType").children('option:selected').val(),
					province:$("#orderProvince").children('option:selected').val(),
					city:$("#orderCity").children('option:selected').val()
					},function(result){
						$("#orderBankChannelNo").empty();
						$("#orderBankChannelNo").append("<option value=''>请选择</option>");
						for(var i=0;i<result.remitBankInfoList.length;i++){
							$("#orderBankChannelNo").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#orderAccountBankChannelNo").val("");
				$("#orderClearBankChannelNo").val("");
			}
		});
		
		$("#orderBankChannelNo").change(function(){
			$.post("remitOrder_getRemitBankInfoByBankChannelNo.action",{bankChannelNo:$(this).children('option:selected').val()},function(result){
				if(result.FAIL=="FAIL"){
					alertMsg.error("银行行号有误，请确认！");
				}else{
					$("#orderAccountBankChannelNo").val(result.remitBankInfo.bankChannelNo);
					$("#orderClearBankChannelNo").val(result.remitBankInfo.clearBankChannelNo);
				}
			},"json");
		});	
		
		//查找带回数据
		$.extend({
			bringBackSuggest : function(args) {
			 	$("#orderAccountName").val(args["accountName"]);
				$("#orderAccountNo").val(args["accountNo"]);
				$("#orderUserNo").val(args["userNo"]);
				//$("#orderAccountType").find("input[value='"+args["accountType"]+"']").attr("checked","checked");
				$("input[name='accountType'][value='"+args["accountType"]+"']").attr("checked","checked");
				$("#orderProvince").find("option[value='"+args["province"]+"']").attr("selected",true);
				$.post("remitOrder_getCityByProvince.action",{province:$("#orderProvince").children('option:selected').val()},function(result){
					$("#orderCity").empty();
					$("#orderCity").append("<option value=''>请选择</option>");
					for(var i=0;i<result.cityList.length;i++){
						$("#orderCity").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
					}
					$("#orderCity").find("option[value='"+args["city"]+"']").attr("selected",true);
					$("#orderBankType").find("option[value='"+args["bankChannelNo"].substring(0,3)+"']").attr("selected",true);
					$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
						bankTypeCode:$("#orderBankType").children('option:selected').val(),
						province:$("#orderProvince").children('option:selected').val(),
						city:$("#orderCity").children('option:selected').val()
						},function(result){
							$("#orderBankChannelNo").empty();
							$("#orderBankChannelNo").append("<option value=''>请选择</option>");
							for(var i=0;i<result.remitBankInfoList.length;i++){
								$("#orderBankChannelNo").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
							}
							$("#orderBankChannelNo").find("option[value='"+args["bankChannelNo"]+"']").attr("selected",true);
							$("#orderBankChannelNo").change();
						}
					,"json");
				
				},"json"); 
			},
			bringBack : function(args) {
				$.bringBackSuggest(args);
				$.pdialog.closeCurrent();
			}
		});
		
	/* 	$("#searchWay").click(function(){
			$("#bankChannelNoInput").toggle();
			$("#orderBankChannelNo").toggle();
			$("#searchButton").toggle();
		});
		
		$("#searchButton").click(function(){
			var bankName = $("#bankChannelNoInput").val();
			$.post("remitOrder_getRemitBankInfoByBankName.action",{bankName:bankName},function(result){
				$("#orderProvince").find("option[value='"+result.remitBankArea.province+"']").attr("selected",true);
				$("#orderCity").empty();
				$("#orderCity").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#orderCity").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
				$("#orderCity").find("option[value='"+result.remitBankArea.city+"']").attr("selected",true);
				$("#orderBankType").find("option[value='"+result.remitBankType.typeCode+"']").attr("selected",true);
				
				$("#bankChannelNoInput").val(result.remitBankInfo.bankName);
				$("#orderAccountBankChannelNo").val(result.remitBankInfo.bankChannelNo);
				$("#orderClearBankChannelNo").val(result.remitBankInfo.clearBankChannelNo);
				$("#orderBankType").change();
			},"json");
		}); */
		
	});
</script>

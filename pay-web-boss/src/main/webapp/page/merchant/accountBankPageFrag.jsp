<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<script>
	//调用插件  
	$(function() {
		// 查看权限
		var isView = "${isView}"; // 查看权限
		if(isView != null && isView != ""){
			$("#provinceMerchantInfo").attr("disabled", "true");	
			$("#cityMerchantInfo").attr("disabled", "true");
			$("#bankType").attr("disabled", "true");
			$("#bankChannelName").attr("disabled", "true");
			$("#bankChannelNameSel").attr("disabled", "true");
			$("input[name='bankAccountType']").attr("disabled", "true");
			$("#bankName").attr("disabled", "true");
			$("#bankAccountName").attr("disabled", "true");
			$("#bankAccountNo").attr("disabled", "true");
		}else{
			$("#provinceMerchantInfo").removeAttr("disabled");	
			$("#cityMerchantInfo").removeAttr("disabled");
			$("#bankType").removeAttr("disabled");
			$("#bankChannelName").removeAttr("disabled");
			$("#bankChannelNameSel").removeAttr("disabled");
			$("input[name='bankAccountType']").removeAttr("disabled");
			$("#bankName").removeAttr("disabled");	
			$("#bankAccountName").removeAttr("disabled");
			$("#bankAccountNo").removeAttr("disabled");
		}
	});
	
</script>

<!-- 商户ID，添加成功时由ajax返回 -->
<input type="hidden" id="id" name="id" value="${id }" />
<div class="unit">
	<label>银行账户名：</label>
	<s:textfield name="bankAccountName" id="bankAccountName" cssClass="required" maxlength="45" size="45" />
</div>
<div class="unit">
	<label>银行账号：</label>
	<s:textfield name="bankAccountNo" id="bankAccountNo" cssClass="required number" maxlength="20" size="45" onkeyup="this.value=this.value.replace(/[^0-9-]/g,'')" />
</div>
<div class="unit">
	<label>账户类型：</label>
	<c:forEach items="${bankAccountTypeEnumList }" var="bankAccountTypeEnum" varStatus="index">
		<label>
			<input type="radio" name="bankAccountType" id="bankAccountType" value="${bankAccountTypeEnum.value}" 
				<c:if test="${bankAccountType eq bankAccountTypeEnum.value || bankAccountType eq null}">checked="checked"</c:if> />
			${bankAccountTypeEnum.desc}
		</label>
	</c:forEach>
</div>

<div class="unit">
	<label>收款人所在地区:</label>
	<select name="provinceMerchantInfo" id="provinceMerchantInfo" class="required">
		<option value="">请选择</option>
		<c:forEach items="${provinceList }" var="pro">
			<option value="${pro.province }" <c:if test="${provinceUserBank == pro.province }">selected="selected"</c:if> >${pro.province}</option>
		</c:forEach>
	</select>
	<select name="cityMerchantInfo" id="cityMerchantInfo" class="required">
		<option value="">请选择</option>
	</select>
</div>

<div class="unit">
	<label>行别：</label>
	<select name="bankName"  id="bankType" class="required">
		<option value="">请选择</option>
		<c:forEach items="${remitBankTypeList }" var="remitBankType">
			<option value="${remitBankType.bankCode },${remitBankType.typeName},${remitBankType.typeCode}" <c:if test="${bankTypeCode == remitBankType.typeCode }">selected="selected"</c:if>>${remitBankType.typeName}</option>
		</c:forEach>
	</select>
</div>

<div class="unit">
	<label>开户行：</label>
	<select name="bankChannelName" id="bankChannelNameSel">
		<option value="">请选择</option>
		
	</select>
</div>
<div class="unit">
	<label>开户行行号：</label>
	<input type="text" size="30" name="bankChannelNo" id="bankChannelNoValue" value="${bankChannelNo }" readonly ="readonly" class="required"/>
</div>



<script>
	
$(function() {
	// 加载初始化数据  start
	var provinces = "${provinceUserBank}";
	var citys = "${cityUserBank}";
	var bankTypeCode = "${bankTypeCode}"; // 银行行别
	
	$.post("remitOrder_getCityByProvince.action",{"province":provinces},function(result){
		$("#cityMerchantInfo").empty();
		$("#cityMerchantInfo").append("<option value=''>请选择</option>");
		for(var i=0;i<result.cityList.length;i++){
			if(citys == result.cityList[i].city){
				$("#cityMerchantInfo").append("<option value='"+result.cityList[i].city+"' selected='selected'>"+result.cityList[i].city+"</option>");
			}else{
				$("#cityMerchantInfo").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
			}
		}
	},"json");
	
	
	// 选中开户行
	$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
		'bankTypeCode':bankTypeCode,
		'province':provinces,
		'city':citys
		},function(result){
			$("#bankChannelNameSel").empty();
			$("#bankChannelNameSel").append("<option value=''>请选择</option>");
			for(var i=0;i<result.remitBankInfoList.length;i++){
				var bankAccountAddress = "${bankAccountAddress}"; // 开户行地址
				var bankNames = result.remitBankInfoList[i].bankName; // 开户行名称
				var bankChannelNos = result.remitBankInfoList[i].bankChannelNo; // 开户行编号
				if(bankAccountAddress == bankNames){
					$("#bankChannelNameSel").append("<option value='"+bankNames+","+bankChannelNos+"' selected='selected'>"+bankNames+"</option>");
				}else{
					$("#bankChannelNameSel").append("<option value='"+bankNames+","+bankChannelNos+"'>"+bankNames+"</option>");
				}
			}
		},"json");
	$("#accountBankChannelNo").val("");
	$("#clearBankChannelNo").val("");
	// 加载初始化数据  end
	
	
	
	
	// 商户基本基本信息的省市区下拉框
	$("#provinceMerchantInfo").change(function(){
		var provinceValue = $(this).children('option:selected').val();
		$.post("remitOrder_getCityByProvince.action",{"province":provinceValue},function(result){
			$("#cityMerchantInfo").empty();
			$("#cityMerchantInfo").append("<option value=''>请选择</option>");
			for(var i=0;i<result.cityList.length;i++){
				$("#cityMerchantInfo").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
			}
		},"json");
	});
	
	// 行别的下拉框改变事件
	$("#bankType").change(function(){
		var bankTypeCode = $("#bankType").children('option:selected').val();
		var tempBankCode = bankTypeCode.split(",");
		var typeCode = tempBankCode[2];
		var province = $("#provinceMerchantInfo").children('option:selected').val();
		var city = $("#cityMerchantInfo").children('option:selected').val();
		if(bankTypeCode!=""&&province!=""&&city!=""){
			$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
				'bankTypeCode':typeCode,
				'province':province,
				'city':city
				},function(result){
					$("#bankChannelNameSel").empty();
					$("#bankChannelNameSel").append("<option value=''>请选择</option>");
					for(var i=0;i<result.remitBankInfoList.length;i++){
						var bankChannelNos = result.remitBankInfoList[i].bankChannelNo;
						var bankNames = result.remitBankInfoList[i].bankName;
						$("#bankChannelNameSel").append("<option value='"+bankNames+","+bankChannelNos+"'>"+bankNames+"</option>");
					}
				},"json");
			$("#accountBankChannelNo").val("");
			$("#clearBankChannelNo").val("");
		}
	});
	
	// 市的下拉框改变事件
	$("#cityMerchantInfo").change(function(){
		var bankTypeCode = $("#bankType").children('option:selected').val();
		var tempBankCode = bankTypeCode.split(",");
		var typeCode = tempBankCode[2];
		var province = $("#provinceMerchantInfo").children('option:selected').val();
		var city = $("#cityMerchantInfo").children('option:selected').val();
		if(bankTypeCode!=""&&province!=""&&city!=""){
			$.post("remitOrder_getBankInfoListByBankTypeCodeAndArea.action",{
				'bankTypeCode':typeCode,
				'province':province,
				'city':city
				},function(result){
					$("#bankChannelNameSel").empty();
					$("#bankChannelNameSel").append("<option value=''>请选择</option>");
					for(var i=0;i<result.remitBankInfoList.length;i++){
						$("#bankChannelNameSel").append("<option value='"+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
					}
				},"json");
			$("#accountBankChannelNo").val("");
			$("#clearBankChannelNo").val("");
		}
	});
	
	// 开户行下拉框改变事件
	$("#bankChannelNameSel").change(function(){
		var bankCodeValue=$(this).children('option:selected').val();
		var tempBankCode;
		var bankCodeChannelNo;
		if(bankCodeValue.indexOf(",") > 0){
			tempBankCode= bankCodeValue.split(",");
			bankCodeChannelNo = tempBankCode[1]; // 行别
		}else{
			bankCodeChannelNo = bankCodeValue;
		}
		$.post("remitOrder_getRemitBankInfoByBankChannelNo.action",{'bankChannelNo': bankCodeChannelNo},function(result){
			$("#bankChannelNoValue").val(result.remitBankInfo.bankChannelNo);
		},"json");
	});
	
});
	
</script>


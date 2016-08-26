<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<div class="pageContent">
	<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li id="merchantBaseInfoTab">
						<a href="javascript:;"><span>企业基本信息</span> </a>
					</li>
					<li id="settAccountTab">
						<a href="javascript:;"><span>企业结算账户</span> </a>
					</li>
					<li id="settTypeTab">
						<a href="javascript:;"><span>结算方式</span> </a>
					</li>
					<li id="salesStaffTab">
						<a href="javascript:;"><span>销售人员</span> </a>
					</li>
				</ul>
			</div>
		</div>

		<!-- 修改时需要用到的商户ID -->
		<input type="hidden" name="merchantId" id="merchantId" value="${id }" />

		<div class="tabsContent">
			<!-- 企业基本信息 -->
			<form id="merchantBaseInfoForm" method="post" class="pageForm required-validate">
				<div class="pageFormContent" layoutH="90">
					<p>
						<label>商户类型：</label>
						<select name="merchantType" class=" combox required " onchange="changeMerchantType(this.value);">
							<option value="">--请选择--</option>
							<option value="12">企业</option>
							<option value="10">个人</option>
							<option value="11">个体工商户</option>
						</select>
					</p>
					<p>
						<label>登录名：</label>
						<s:textfield name="loginName" id="regLoginName" cssClass="required email" minlength="3" maxlength="90" size="30" />
					</p>
					<p>
						<label>商户全称：</label>
						<s:textfield name="fullName" id="fullName" cssClass="required" maxlength="100" size="30" />
					</p>
					<p>
						<label>商户简称：</label>
						<s:textfield name="shortName" cssClass="required" minlength="2" maxlength="30" size="30" />
					</p>
					<p id="licenseNoDiv11">
						<label>营业执照号：</label>
						<s:textfield name="licenseNo" cssClass="required" maxlength="15" size="30" />
					</p>
					<p id="licenseNoDiv22">
						<label>营业执照有效期：</label>
						<s:textfield name="licenseNoValid" id="licenseNoValid" maxlength="10" minDate="{%y}-%M-{%d}" cssClass="date" />
						<a class="inputDateButton" id="licenseBtn" href="javascript:;">选择</a>
					</p>
					<p>
						<label>法人姓名：</label>
						<s:textfield name="legalPerson" cssClass="required" maxlength="50" size="30" />
					</p>
					<p>
						<label>法人身份证号：</label>
						<s:textfield onBlur="isCardID();" name="cardNo" id="cardNo" cssClass="required" maxlength="18" minlength="15" size="30" />
					</p>
					<p id="orgCode">
						<label>组织机构代码：</label>
						<s:textfield name="orgCode" minlength="9" maxlength="10" size="30" cssClass="required"/>
					</p>
					<p>
						<label>身份证有效期：</label>
						<input name="certificateExpiry" id="certificateExpiry" minDate="{%y}-%M-{%d}" class="date" size="10"/>
						（空则默认为长期有效）
					</p>
					<p>
						<label>绑定手机：</label>
						<s:textfield name="bindMobileNo" id="bindMobileNo" cssClass="required mobile" maxlength="11" size="30" />
					</p>
					<p>
						<label>ICP证备案号：</label>
						<s:textfield name="icp" maxlength="40" size="30" />
					</p>
					<p>
						<label>业务联系人：</label>
						<s:textfield name="busiContactName" cssClass="required" minlength="2" maxlength="20" size="30" />
					</p>
					<p>
						<label>联系人手机：</label>
						<s:textfield name="busiContactMobileNo" cssClass="required mobile" maxlength="11" size="30" />
					</p>
					<p>
						<label>商城网址：</label>
						<s:textfield name="url" cssClass="url" maxlength="300" size="30" value="http://" />
					</p>
					<p style="height:50px;">
						<label>经营范围：</label>
						<s:textarea cols="25" rows="2" name="scope" cssClass="required" maxlength="300"></s:textarea>
					</p>
					<div style="width:100%;clear:both;height:50px;padding-top:10px;width:600px;">
						<label style="float:left;">IP段：</label>
						<div style="float:left;">
							<s:textarea style="width:400px;" rows="2" name="ipSeg"
								id="ipSeg" maxlength="30"></s:textarea>
						</div>
						<div style="clear:both;height:0px;overflow:hidden"></div>
					</div>

					<div class="unit">
						<label>所在地区：</label>
						<div>
							<select name="province" id="provinceMerchant" class="required">
								<option value="">请选择</option>
								<c:forEach items="${provinceList }" var="province">
									<option value="${province.province }">${province.province}</option>
								</c:forEach>
							</select> <select name="city" id="cityMerchant" class="required">
								<option value="">请选择</option>
							</select>
						</div>
						<div style="width:100%;clear:both;height:50px;padding-top:10px;width:600px;">
							<label style="float:left;">详细地址：</label>
							<div style="float:left;">
								<s:textarea style="width:400px;" rows="2" name="address"
									id="address" cssClass="required" minlength="2" maxlength="500"></s:textarea>
							</div>
							<div style="clear:both;height:0px;overflow:hidden"></div>
						</div>
					</div>
					
					<div style="width:851px;">
						<p>
							<label>MCC码：</label> 
							<input id="mccma" name="mccFeeLookUp.mccma" type="text" class="textInput readonly" /> 
							<a class="btnLook"
								href="mccFee_listMccFeeLookUp.action" lookupgroup="mccFeeLookUp"
								rel="mccFeeLookUp">MCC费率</a>
						</p>
						<p>
							<label>MCC费率(%)：</label><input id="mccRate" class="textInput readonly" name="mccFeeLookUp.rate"/>
						</p>
						<div style="width:380px; float:left;">
							<label style="float:left;">MCC经营范围：</label>
							<label id="buiScope" style="width:200px;"></label>
						</div>
						<div style="clear:both;height:0px;overflow:hidden;"></div>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="merchantBaseInfoForm()">下一步</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>

			<!-- 企业结算账户 -->
			<form id="accountBankInfoAddForm" method="post">
				<div class="pageFormContent" layoutH="90">
					<input type="hidden" id="merchantIdOne" name="merchantIdOne" />
					<input type="hidden" id="accountBankId" name="accountBankId" />
					<input type="hidden" id="loginNameValue" name="loginNameValue" />

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
								<input type="radio" name="bankAccountType" value="${bankAccountTypeEnum.value}" <c:if test="${index.index eq 0 }">checked="checked"</c:if> />
								${bankAccountTypeEnum.desc}
							</label>
						</c:forEach>
					</div>

					<div class="unit">
						<label>收款人所在地区：</label>
						<select name="provinceMerchantInfo" id="provinceMerchantInfo" class="required">
							<option value="">请选择</option>
							<c:forEach items="${provinceList }" var="province">
								<option value="${province.province }">${province.province}</option>
							</c:forEach>
						</select> <select name="cityMerchantInfo" id="cityMerchantInfo" class="required">
							<option value="">请选择</option>
						</select>
					</div>

					<div class="unit">
						<label>行别：</label>
						<select name="bankName" id="bankType" class="required">
							<option value="">请选择</option>
							<c:forEach items="${remitBankTypeList }" var="remitBankType">
								<option value="${remitBankType.bankCode },${remitBankType.typeName},${remitBankType.typeCode}">${remitBankType.typeName}</option>
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
						<input type="text" size="30" name="bankChannelNo" id="bankChannelNo" class="required" readonly />
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="accountBankInfoForm()">下一步</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>

			<!-- 结算方式 -->
			<form id="settTypeForm" method="post">
				<input type="hidden" id="merchantIdTwo" name="merchantIdTwo" />
				<div class="pageFormContent" layoutH="90">
					<div class="unit">
						<label>结算方式：</label>
						<select name="settType" id="status" onchange="changeType(this)" class="required ">
							<option value="1" <c:if test="${settType eq 1 }">selected="selected"</c:if>>自动结算</option>
							<option value="2" <c:if test="${settType eq 2 }">selected="selected"</c:if>>手工结算</option>
						</select>
					</div>
					<div class="unit">
						<div id="autoType" <c:if test="${settType eq 2 }">style="display: none;" </c:if>>
							<label>结算周期：</label>
							<select name="settCycleType" id="settCycleStatus" onchange="changeCycleType(this)">
								<option value="2" selected="selected">周结</option>
								<option value="1">月结</option> 
							</select>
						</div>
					</div>

			<div id="autoType1" <c:if test="${settType eq 2 }">style="display: none;" </c:if>>
					<div class="unit" id="autoType2" >
						<label> 结算周期(周) </label>
						<input type="checkbox" value="1" id="settCycleData1" name="settCycleData" />
						周日&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="2" id="settCycleData2" name="settCycleData" />
						周一&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="3" id="settCycleData3" name="settCycleData" />
						周二&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="4" id="settCycleData4" name="settCycleData" />
						周三&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="5" id="settCycleData5" name="settCycleData" />
						周四&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="6" id="settCycleData6" name="settCycleData" />
						周五&nbsp;&nbsp;&nbsp;&nbsp;
						<input type="checkbox" value="7" id="settCycleData7" name="settCycleData" />
						周六&nbsp;&nbsp;&nbsp;&nbsp;

					</div>
					
					<div class="unit" id="autoType3" style="display: none;">
						<label> 结算周期(月) </label>
						<c:forEach begin="1" end="31" step="1" var="settData">
						<input type="checkbox" value="${settData}" id="settCycleData${settData}" name="settCycleDataM" />
						${settData}日&nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${settData eq 16 }">
								<br/>
							</c:if>
						</c:forEach>
					</div>
			</div>
					<div class="unit">
						<label>风险预存期：</label>
						<input type="text" name="riskDay" id="riskTimelimitDay" class="required number" maxlength="3" size="30" value="1" />
						天
					</div>
					<div class="unit">
						<label>描述：</label>
						<textarea rows="6" cols="28" name="remark" minlength="1" maxlength="800" class="required" >${remark}</textarea>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="settTypeForm()">下一步</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>

			<!-- 销售人员 -->
			<form id="salesForm" action="merchant_addMerchantSales.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="list" />
				<input type="hidden" name="callbackType" value="" />
				<input type="hidden" name="forwardUrl" value="" />

				<input type="hidden" name="xiaoshouMerchantId" id="xiaoshouMerchantId" />
				<div class="pageFormContent" layoutH="90">
					<div class="unit">
						<label>销售人员：</label>
						<input type="hidden" name="backSalesId" id="backSalesId" value="${salesParam.id }" />
						<input class="required readonly" id="backSalesName" name="backSalesName" value="${salesParam.salesName }" type="text" size="25" readonly />
						<a class="btnLook" href="sales_listSalesBy.action" lookupGroup="salesNameTag">搜索</a> <span class="info">搜索</span>
					</div>
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="button" onclick="submitForm();">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button id="salesCloseBtn" type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>

<script type="text/javascript">

	// 选项卡切换（上一步）
	function merchantBaseInfoTab(){
		$("#regLoginName").attr("disabled", "true");
		$("#merchantBaseInfoTab").click();
	}
	// 企业结算账户
	function settAccountTab(){
		// 把企业信息的签约名替换到结算账户的银行账户名
		$("#loginNameValue").val($("#regLoginName").val());
		$("#settAccountTab").click();
	}
	
	// 结算方式
	function settTypeTab(){
		$("#settTypeTab").click();
	}
	
	// 销售人员
	function salesStaffTab(){
		$("#salesStaffTab").click();
	}
	// merchant表单提交
	function merchantBaseInfoForm() {
		var merchantId = $("#merchantId").val();
		if (merchantId==''){
			$.post("merchant_addMerchant.action", $('#merchantBaseInfoForm').serialize(), function(res){
				if(res.STATE == "SUCCESS"){
					var merchantId = res.MSG;
					var fullNameValue = res.FULLNAMEVALUE;
					if(merchantId != '' && merchantId != null){
						$("#merchantId").val(merchantId);
						$("#xiaoshouId").val(merchantId);
						$("#merchantIdOne").val(merchantId); // 企业结算账户需要用到的商户ID
						$("#merchantIdTwo").val(merchantId); // 结算方式需要用到的商户ID
						$("#xiaoshouMerchantId").val(merchantId);
						$("#bankAccountName").val(fullNameValue);
						settAccountTab();
					}else{
						alertMsg.error(res.MSG);
					}
				}else if(res.STATE == "FAIL"){
					alertMsg.error(res.MSG);	
				}else{
					alertMsg.error("添加失败，请稍后重试，或者联系管理员！");
				}
			},"json");
		}else{
			var url = "merchant_editMerchant.action?id="+merchantId+"&add=1";
			$.post(url, $('#merchantBaseInfoForm').serialize(), function(res){
				if(res.STATE == "SUCCESS"){
					var result = res.MSG;
					if(result != '' && result != null){
						settAccountTab();
					}else{
						alertMsg.error(res.MSG);
					}
				}else{
					alertMsg.error(res.MSG);
				}
			},"json");
		}
	}
	
	// 验证密码
	function isRegisterUserName(s){
		var patrn=/^[\w\-~!@#$%^&*()+{}[ \]:]{8,20}$/;
		if (!patrn.test(s)) {
			return false;
		}else{
			return true;
		}
	}
	
	//accountBank表单提交 企业结算账户表单
	function accountBankInfoForm() {
		var accountBankId = $("#accountBankId").val();
		if(accountBankId == null || accountBankId == ""){
			$.post("accountBank_addAccountBank.action", $("#accountBankInfoAddForm").serialize(), function(res){
				if(res.STATE == "SUCCESS"){
					var result = res.MSG;
					if(result != null && result != ''){
						// 拿到银行账户ID和商户ID
						$("#accountBankIdValue").val(result); // 银行账户ID
						settTypeTab();
					}else{
						alertMsg.error(res.MSG);
					}
				}else{
					alertMsg.error(res.MSG);
				}
			},"json");
		}else{
			var url = "accountBank_editAccountBank.action?accountids="+accountBankId+"&ajax=1";
			$.post(url , $('#accountBankInfoForm').serialize(), function(res){
				if(res.STATE == "SUCCESS"){
					var result = res.MSG;
					if(result != '' && result != null){
						settTypeTab();
					}else{
						alertMsg.error(res.MSG);
					}
				}else{
					alertMsg.error(res.MSG);
				}
			},"json");
		}
	}
		
	// 结算方式表单
	function settTypeForm() {
		var merchantId = $("#merchantIdTwo").val();
		if(merchantId==null||merchantId==""){
			alertMsg.error("商户信息为空");
			return;
		}
		var riskTimelimitDay = $("#riskTimelimitDay").val();
		if(riskTimelimitDay <= 0){
			alertMsg.error("风险预存期必须大于等于0");
			return;
		}
		
		$.post("SettRule_addSettRule.action", $('#settTypeForm').serialize(), function(res){
			if(res.STATE == "SUCCESS"){
				$("#salesStaffTab").click();
			}else{
				alertMsg.error(res.MSG);
			}
		},"json");
	}

	// 验证身份证号是否存在
	function checkCardNo(obj){
		if(obj == null || obj == ""){
			return ;
		}
		var url="merchant_checkCardNo.action";
		
		$.post(url, {'cardNo': obj}, function(res){
			if(res.STATE == "FAIL"){
				alertMsg.error(res.MSG);
			}
		},"json");
	}
	
	// 选择不同的商户类型
	function changeMerchantType(obj){
		// 10-个人，11个体工商户，12-企业
		if(obj == 10){
			$("#licenseNoDiv11").hide();// 隐藏营业执照
			$("#licenseNoDiv22").hide();// 隐藏营业执照
			$("#orgCode").hide();// 隐藏组织机构代码
			$("#privateList").show(); // 对私账户
			$("#publicList").hide();
		}else if (obj == 12 || obj == 11){
			$("#licenseNoDiv11").show();// 显示营业执照
			$("#licenseNoDiv22").show();// 显示营业执照
			$("#orgCode").show();// 隐藏组织机构代码
			$("#publicList").show();
			$("#privateList").hide();
		}
	}
	
	// 改变结算方式
	function changeType(obj){
		if(obj != null){
			if(obj.value == "1"){
				$("#autoType").show();
				$("#autoType1").show();
			}else{
				$("#autoType").hide();
				$("#autoType1").hide();
			}
		}
	}
	//改变结算周期
	function changeCycleType(obj){
		if(obj != null){
			if(obj.value == "1"){
				$("#autoType3").show();
				$("#autoType2").hide();
			}else{
				$("#autoType3").hide();
				$("#autoType2").show();
			}
		}
	}
	
	// 安全保护问题如果没选择则 不能填充
	function showAnswerInput(obj){
		if(obj != null && obj != ""){
			$("#answer").removeAttr("disabled");
		}else{
			$("#answer").val('');
			$("#answer").attr("disabled", "true");
		}
	}
	
	
	// 校验转账按钮是否选中
	function zhuanzhangClick(){
		if($("#zhuanzhang").is(":checked")==false){
			$("#zhanghuxiane").attr("disabled", "true");
			$("#zhanghuxiane").attr("checked", "checked");
			$("#zhanghuxiane2").attr("checked", "");
		}else{
			$("#zhanghuxiane").removeAttr("disabled");
		}
	}

	// 交易收款按钮是否选中
	function jiaoyiClick(){
		if($("#jiaoyi").is(":checked")==false){ //未选中
			$("#jiaoyixiane").attr("disabled", "true");
			$("#jiaoyixiane").attr("checked", "checked");
			$("#jiaoyixiane2").attr("checked", "");
		}else{
			$("#jiaoyixiane").removeAttr("disabled");
		}
	}
	
	
	// 添加企业结算账户时用到
	$(function(){
		// 商户基本基本信息的省市区下拉框
		$("#provinceMerchant").change(function(){
			var provinceValue = $(this).children('option:selected').val();
			$.post("remitOrder_getCityByProvince.action",{"province":provinceValue},function(result){
				$("#cityMerchant").empty();
				$("#cityMerchant").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#cityMerchant").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
		});
		
		// 企业结算账户的省市区下拉框
		$("#provinceMerchantInfo").change(function(){
			$.post("remitOrder_getCityByProvince.action",{province:$(this).children('option:selected').val()},function(result){
				$("#cityMerchantInfo").empty();
				$("#cityMerchantInfo").append("<option value=''>请选择</option>");
				for(var i=0;i<result.cityList.length;i++){
					$("#cityMerchantInfo").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			},"json");
		});
		
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
							$("#bankChannelNameSel").append("<option value='"+result.remitBankInfoList[i].bankName+","+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#bankChannelNo").val("");
				//$("#accountBankChannelNo").val("");
				//$("#clearBankChannelNo").val("");
			}
		});
		
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
							$("#bankChannelNameSel").append("<option value='"+result.remitBankInfoList[i].bankName+","+result.remitBankInfoList[i].bankChannelNo+"'>"+result.remitBankInfoList[i].bankName+"</option>");
						}
					},"json");
				$("#bankChannelNo").val("");
				//$("#accountBankChannelNo").val("");
				//$("#clearBankChannelNo").val("");
			}
		});
		
		$("#bankChannelNameSel").change(function(){
			$("#bankChannelNo").val($(this).children('option:selected').val().split(",")[1]);
		});
	});
	
	$.extend({
		bringBackSuggest : function(args) {
			$("input[name='backSalesId']").val(args["backSalesId"]);
			$("input[name='backSalesName']").val(args["backSalesName"]);
			
			// mcc
			$("input[name='mccFeeLookUp.mccma']").val(args["mccma"]);
			$("input[name='mccFeeLookUp.rate']").val(args["rate"]);
			$("#buiScope").html(args["buiScope"]);
			
		},
		bringBack : function(args) {
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});
	
	function submitForm(){
		$("#salesForm").submit();	
		$("#salesCloseBtn").click();
	}
	
	function isCardID(){ 
		var sId=$("#cardNo").val();
		var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"} 
		var iSum=0 ;
		var info="" ;
		if(!/^\d{17}(\d|x)$/i.test(sId)) {alertMsg.error("您输入的身份证长度或格式错误"); return false;}
		sId=sId.replace(/x$/i,"a"); 
		if(aCity[parseInt(sId.substr(0,2))]==null) {alertMsg.error("您的身份证地区非法");  return false; }
		sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2)); 
		var d=new Date(sBirthday.replace(/-/g,"/")) ;
		if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate())) {alertMsg.error("身份证上的出生日期非法"); return false; }
		for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;
		if(iSum%11!=1) {alertMsg.error("您输入的身份证号非法"); return false; }
		return true;
	} 
	
</script>
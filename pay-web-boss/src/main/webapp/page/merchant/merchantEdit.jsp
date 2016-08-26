<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<script src="js/provincesCity.js" type="text/javascript"></script>

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
						<a href="javascript:settAccountTab('${id }');"><span>企业结算账户</span> </a>
					</li>
					<li id="settTypeTab">
						<a href="javascript:settTypeTab('${accountNo}');"><span>结算方式</span> </a>
					</li>
					<li id="sellPersonTab">
						<a href="javascript:;"><span>销售人员</span> </a>
					</li>
				</ul>
			</div>
		</div>
		<div class="tabsContent">
			<form id="form" method="post" action="merchant_editMerchant.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<div class="pageFormContent" layoutH="88">
					<input type="hidden" name="navTabId" value="merchantList" />
					<input type="hidden" name="callbackType" value="" />
					<input type="hidden" name="forwardUrl" value="" />

					<s:hidden id="merchantId" name="merchantId" />
					<input type="hidden" name="id" name="id" value="${id }" />

					<p>
						<label>账户名：</label>
						<s:textfield name="loginName" id="regLoginName" readonly="true" minlength="3" maxlength="90" size="30" />
					</p>
					<p>
						<label>商户类型：</label>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${merchantType eq model.value }">
								<input type="text" name="merchantType" id="merchantType" value="${model.desc}" disabled="disabled" />
							</c:if>
						</c:forEach>
					</p>
					<p>
						<label>商户全称：</label>
						<s:textfield name="fullName" readonly="true" id="fullName" maxlength="100" size="30" />
					</p>
					<p>
						<label>商户简称：</label>
						<s:textfield name="shortName" readonly="true" minlength="2" maxlength="30" size="30" />
					</p>
					<c:if test="${merchantType ne merchantTypeEnum.MEMBER.value }">
						<p id="licenseNoDiv11">
							<label>营业执照号：</label>
							<s:textfield name="licenseNo" readonly="true" maxlength="15" size="30" />
						</p>
						<p id="licenseNoDiv22">
							<label>营业执照有效期：</label>
							<input type="text" name="licenseNoValid" id="licenseNoValid" minDate="{%y}-%M-{%d}" value="<fmt:formatDate value="${licenseNoValid }" pattern="yyyy-MM-dd" />" maxlength="10" class="date" />
							<a class="inputDateButton" href="javascript:;">选择</a>
						</p>
					</c:if>
					<p>
						<label>法人姓名：</label>
						<s:textfield name="legalPerson" maxlength="50" size="30" readonly="true" />
					</p>
					<p>
						<label>法人身份证号：</label>
						<s:textfield name="cardNo" id="cardNo" size="30" readonly="true"/>
					</p>
					<p>
						<label>身份证有效期：</label>
						<s:textfield name="certificateExpiry" id="certificateExpiry" maxlength="10" minDate="{%y}-%M-{%d}" cssClass="date" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</p>
					<p>
						<label>绑定手机：</label>
						<s:textfield name="bindMobileNo" id="bindMobileNo" maxlength="11" size="30" readonly="true" />
					</p>
					<p>
						<label>ICP证备案号：</label>
						<s:textfield name="icp" maxlength="40" size="30"  />
					</p>
					<p>
						<label>业务联系人：</label>
						<s:textfield name="busiContactName" cssClass="required" minlength="2" maxlength="20" size="30" />
					</p>
					<p>
						<label>联系人手机：</label>
						<s:textfield name="busiContactMobileNo" cssClass="required mobile" maxlength="15" size="30" />
					</p>
					<p>
						<label>商城网址：</label>
						<s:textfield name="url" cssClass="url" maxlength="255" size="30" />
					</p>
					<p>
						<label>组织机构代码：</label> 
						<input id="orgCode" name="orgCode" type="text" readonly="readonly" value="${orgCode }"/>
					</p>
					
					<p>
						<label>MCC码：</label> 
						<input id="mccma" name="mccma" type="text" readonly="readonly" value="${mcc }"/>
					</p>
					<div style="width:100%;clear:both;height:30px;padding-top:10px;width:800px;">
						<label style="float:left;">经营范围：</label>
						<s:textarea style="width:400px;" rows="2" name="scope" cssClass="required" maxlength="300"></s:textarea>
					</div>
					<div style="width:100%;clear:both;height:30px;padding-top:10px;width:600px;">
						<label style="float:left;">IP段：</label>
						<div>
							<s:textfield style="width:400px;" name="ipSeg" maxlength="200"/>
						</div>
					</div>
					
					<div class="unit">
						<label>所在地区：</label>
						<div>
							<select name="province" id="provinceMerchant" class="required">
								<option value="" >请选择</option>
								<c:forEach items="${provinceList }" var="pro">
									<option value="${pro.province }" <c:if test="${province == pro.province }">selected='selected'</c:if> >${pro.province}</option>
								</c:forEach>
							</select>
							<select name="city" id="cityMerchant" class="required">
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
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>



			<!-- 企业结算账户 -->
			<form id="accountBankInfoForm" method="post" action="accountBank_editAccountBank.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="">
				<input type="hidden" name="callbackType" value="">
				<input type="hidden" name="forwardUrl" value="">
				<!-- 企业基本信息ID -->
				<input type="hidden" name="bankMerchantId" id="bankMerchantId" value="${id }" />

				<!-- bankAccountDiv 里面的数据是动态添加的 -->
				<div class="pageFormContent" layoutH="90" id="bankAccountDiv"></div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit" rel="list">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>

				</div>
			</form>

			<!-- 结算方式 -->
			<form id="settTypeForm" method="post" action="SettRule_editSettRule.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="">
				<input type="hidden" name="callbackType" value="">
				<input type="hidden" name="forwardUrl" value="">
				<input type="hidden" name="merchantNoForSettRule" value="${merchantNo}">
				
				<div class="pageFormContent" layoutH="90" id="settTypeDiv">
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>

			<!-- 销售人员 -->
			<form action="merchant_addMerchantSales.action" method="post" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="">
				<input type="hidden" name="callbackType" value="">
				<input type="hidden" name="forwardUrl" value="">

				<input type="hidden" name="merchantSalesId" id="merchantSalesId" value="${merchantSales.id }" />
				<input type="hidden" name="xiaoshouMerchantId" id="xiaoshouMerchantId" value="${id }" />
				<div class="pageFormContent" layoutH="90">
					<div class="unit">
						<label>销售人员：</label>
						<input type="hidden" name="backSalesId" id="backSalesId" value="${sales.id }" />
						<input class="required readonly" id="backSalesName" name="backSalesName" value="${sales.salesName }" type="text" size="25" readonly />
						<a class="btnLook" href="sales_listSalesBy.action" lookupGroup="salesNameTag">搜索</a> <span class="info">搜索</span>
					</div>
					<!-- 
					<div class="unit">
						<label>签约日期：</label>
						<input type="text" name="signTime" id="signTime" value="<fmt:formatDate value="${contract.signTime }" pattern="yyyy-MM-dd" />" maxDate="{%y}-%M-{%d}" class="date" size="30" readonly="readonly" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</div>
					<div class="unit">
						<label>合同到期日：</label>
						<input type="text" name="contractValid" id="contractValid" minDate="{%y}-%M-{%d}" value="<fmt:formatDate value="${contract.contractValid }" pattern="yyyy-MM-dd" />" class="date" size="30"
							readonly="readonly" />
						<a class="inputDateButton" href="javascript:;">选择</a>
					</div> -->
				</div>
				<div class="formBar">
					<ul>
						<li>
							<div class="buttonActive">
								<div class="buttonContent">
									<button type="submit">保存</button>
								</div>
							</div>
						</li>
						<li>
							<div class="button">
								<div class="buttonContent">
									<button type="button" class="close">取消</button>
								</div>
							</div>
						</li>
					</ul>
				</div>
			</form>
		</div>
	</div>
</div>

<script>
	// 企业结算账户
	function settAccountTab(merchantId) {
		$("#settAccountTab").click();
		if (merchantId == null || merchantId == "") {
			alertMsg.error("该商户信息不存在!");
			return;
		}
		var url = "merchant_findAccountBankByMerchantId.action";
		$.ajax({
			url : url,
			type : 'post',
			data : {
				'merchantId' : merchantId
			},
			dataType : 'html',
			success : function(data) {
				if (data != null) {
					$("#bankAccountDiv").html(data);
				}
			}
		});
	}

	// 结算方式
	function settTypeTab(accountNo) {
		$("#settTypeTab").click();
		if (accountNo == null || accountNo == "") {
			alertMsg.error("该结算信息不存在!");
			return;
		}
		var url = "SettRule_getSettRuleToEditSettRule.action";
		$.ajax({
			url : url,
			type : 'post',
			data : {
				'accountNo' : accountNo
			},
			success : function(data) {
				if (data != null) {
					$("#settTypeDiv").html(data);
				}
			}
		});
	}

	// 改变结算方式
	function changeType(obj) {
		if (obj != null) {
			if (obj.value == "1") {
				$("#autoType").show();
				$("#autoType2").show();
			} else {
				$("#autoType").hide();
				$("#autoType2").hide();
			}
		}
	}

	$(function() {
		
		// 加载省事数据
		var provinces = "${province}";
		var city = "${city}";
		$.post("remitOrder_getCityByProvince.action",{"province":provinces},function(result){
			$("#cityMerchant").empty();
			$("#cityMerchant").append("<option value=''>请选择</option>");
			for(var i=0;i<result.cityList.length;i++){
				if(city == result.cityList[i].city){
					$("#cityMerchant").append("<option value='"+result.cityList[i].city+"' selected='selected'>"+result.cityList[i].city+"</option>");
				}else{
					$("#cityMerchant").append("<option value='"+result.cityList[i].city+"'>"+result.cityList[i].city+"</option>");
				}
			}
		},"json");
		
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
	});

	// 查找带回的拓展功能
	$.extend({
		bringBackSuggest : function(args) {
			$("input[name='backSalesId']").val(args["backSalesId"]);
			$("input[name='backSalesName']").val(args["backSalesName"]);
		},
		bringBack : function(args) {
			$.bringBackSuggest(args);
			$.pdialog.closeCurrent();
		}
	});

</script>
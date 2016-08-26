<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
%>

<script type="text/javascript" src="js/provincesCity.js"></script>

<style>
.worm{display:block;float:left; margin-left:3px; width:16px; height:16px; background: url('/pay-web-boss/statics/dwz/themes/default/images/alert/alertpanel_icon.png'); background-repeat: no-repeat; background-position:top; }
</style>

<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
<div class="tabs" currentIndex="0" eventType="click">
		<div class="tabsHeader">
			<div class="tabsHeaderContent">
				<ul>
					<li id="merchantBaseInfoTab" ><a href="javascript:;"><span>企业基本信息</span></a></li>
					<li id="settAccountTab"><a href="javascript:settAccountTab('${id }');"><span>企业结算账户</span></a></li>
					<li id="settTypeTab">
						<a href="javascript:settTypeTab('${accountNo}');"><span>结算方式</span> </a>
					</li>
					<li id="sellPersonTab"><a href="javascript:sellPersonTab('${id }');"><span>销售人员</span></a></li>
				</ul>
			</div>
		</div>
	<div class="tabsContent">
		<form id="form" method="post" action="merchant_changeMerchantStatus.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="90">
			    <input type="hidden" name="navTabId" value="list">
				<input type="hidden" name="callbackType" value="closeCurrent">
				<input type="hidden" name="forwardUrl" value="">
				
				<s:hidden id="merchantId" name="merchantId" />
				<input type="hidden" name="id" value="${id }"/>
				<input type="hidden" name="status" id="status" />
				<p>
					<label>登录名：</label>
					<input type="text" value="${loginName }" readonly="readonly" size="30" >
				</p>
				<p>
					<label>账户名：</label>
					<input type="text" value="${accountNo }" readonly="readonly" size="30" >
				</p>
				<p>
					<label>商户类型：</label>
					<select name="merchantType" id="merchantType1" disabled="disabled">
						<option value="">请选择</option>
						<c:forEach items="${merchantTypeList }" var="model">
							<option value="${model.value }" <c:if test="${merchantType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select>
				</p>
				<p>
					<label>商户编号：</label>
					<s:textfield name="merchantNo" readonly="true"  minlength="2" maxlength="90" size="30" />
				</p>
				<p>
					<label>商户全称：</label>
					<s:textfield name="fullName" readonly="true" minlength="2" maxlength="90" size="30" />
				</p>
	   			<p>
					<label>商户简称：</label>
					<s:textfield name="shortName" readonly="true"  minlength="2" maxlength="90" size="30" />
				</p>
				<c:if test="${merchantType eq 11 || merchantType eq 12 }">
					<p id="licenseNoDiv">
						<label>营业执照号：</label>
						<s:textfield name="licenseNo" maxlength="50" size="30" readonly="true" />
					</p>
					<p id="licenseNoDiv2">
						<label>营业执照有效期：</label>
						<input type="text" name="licenseNoValid" id="licenseNoValid1" 
						<c:if test="${not empty licenseTimeOut }">style="color: red;"</c:if> 
						value="<fmt:formatDate value="${licenseNoValid }" pattern="yyyy-MM-dd" />" readonly="readonly" size="30" />
						<c:if test="${licenseTimeOut >= 0 }"><a href="javascript:void(0);" title="营业执照即将到期，请联系该商户!" class="worm"></a></c:if>
						<c:if test="${licenseTimeOut < 0 }"><a href="javascript:void(0);" title="营业执照已经到期，请联系该商户!" class="worm"></a></c:if>
					</p>
				</c:if>
				<p>
					<label>法人姓名：</label>
					<s:textfield name="legalPerson"  maxlength="255" size="30" readonly="true" />
				</p>
				<p>
					<label>法人身份证号：</label>
					<s:textfield name="cardNo" id="cardNo" maxlength="18" minlength="14" size="30" readonly="true" />
				</p>
				<p>
					<label>身份证有效期：</label> <input name="certificateExpiry" id="certificateExpiry"
					readonly="readonly"
						 size="30" minDate="{%y}-%M-{%d}"  value="${certificateExpiry }" />
				</p>
				<p>
					<label>绑定手机：</label>
					<s:textfield name="bindMobileNo"  readonly="true" maxlength="15" size="30" />
				</p>
				
				<p>
					<label>注册日期：</label>
					<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/>
				</p>
				<p>
					<label>ICP证备案号：</label>
					<s:textfield name="icp" readonly="true"   size="30" />
				</p>
				<p>
					<label>业务联系人：</label>
					<s:textfield name="busiContactName"  readonly="true" minlength="2" maxlength="15" size="30" />
				</p>
				<p>
					<label>联系人手机：</label>
					<s:textfield name="busiContactMobileNo"  readonly="true" maxlength="15" size="30" />
				</p>
				<p>
					<label>实名认证状态：</label>
					<c:if test="${empty isRealNameAuth }">
						<input name="isRealNameAuth" id="isRealNameAuth" value="认证未通过"  readonly="readonly" size="30" />
					</c:if>
					<c:if test="${isRealNameAuth eq 100 }">
						<input name="isRealNameAuth" id="isRealNameAuth" value="认证通过"  readonly="readonly" size="30" />
					</c:if>
					<c:if test="${isRealNameAuth eq 101 }">
						<input name="isRealNameAuth" id="isRealNameAuth" value="未认证"  readonly="readonly" size="30" />
					</c:if>
				</p>
				
				<p>
					<label>商城网址：</label>
					<s:textfield name="url" maxlength="255" size="30" readonly="true" />
				</p>
				
				<p style="height:50px;">
					<label>经营范围：</label>
					<s:textarea cols="27" rows="2" name="scope" 
						maxlength="300" readonly="true"></s:textarea>
				</p>
				
				<div class="unit">
					<label>IP段：</label>
						<s:textarea cols="27" rows="2" name="ipSeg" maxlength="300" readonly="true"></s:textarea>
				</div>
				<p>
						<label>组织机构代码：</label> 
						<input id="orgCode" name="orgCode" type="text" readonly="readonly" value="${orgCode }"/>
					</p>
				<div class="unit">
					<label>MCC码：</label> 
					<s:textarea id="mcc" name="mcc" readonly="readonly"></s:textarea>
				</div>
				
				<div class="unit">
					<label>通信地址：</label>
					<div>
						<input type="text" name="province" id="province11" disabled="disabled" width="65px" value="${province }"/>
						<input type="text" name="city" id="city22" disabled="disabled" width="65px" value="${city }"/>
					</div>
					<label><s:textarea cols="27" rows="3" name="address" maxlength="150" readonly="true" ></s:textarea>
					</label>
				</div>
			</div>
			
			<div class="formBar" >
				<ul>
					<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
				</ul>
			</div>
		</form>
			<!-- 企业结算账户 -->
			<form id="accountBankInfoForm" method="post" action="accountBank_editAccountBank.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="list">
				<input type="hidden" name="callbackType" value="closeCurrent">
				<input type="hidden" name="forwardUrl" value="">
				<!-- bankAccountDiv 里面的数据是动态添加的 -->
				<div class="pageFormContent" layoutH="90" id="bankAccountDiv">
					
				</div>
			</form>
			
			
			<!-- 结算方式 -->
			<form id="settTypeForm" method="post" action="settlement_editSettType.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="navTabId" value="list">
				<input type="hidden" name="callbackType" value="closeCurrent">
				<input type="hidden" name="forwardUrl" value="">
				<div class="pageFormContent" layoutH="120" id="settTypeDiv">
				</div>
			</form>
			
			<!-- 销售人员 start -->
			<form action="" method="post" id="auditForm" name="auditForm" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
				<input type="hidden" name="id" value="${id }" id="id" />
				<input type="hidden" name="navTabId" value="list"> 
				<input type="hidden" name="callbackType" value="closeCurrent"> 
				<input type="hidden" name="forwardUrl" value=""> 
				<div class="pageFormContent" layoutH="90">
					<div class="unit">
					<label>销售人员：</label>
						<input type="text" name="salesName" id="salesName" value="${sales.salesName }" readonly="readonly" />
					</div>
					
					<!-- 提示信息 -->
					<div class="unit">
					还没有上传商户合同？
					请到【系统设置-合同管理】上传商户相关合同扫描件。
					</div>
					<!-- 
					<div class="unit">
						<label>签约日期：</label>
						<input type="text" name="signTime" id="signTime" value="<fmt:formatDate value="${contract.signTime }" pattern="yyyy-MM-dd" />" readonly="readonly" />
					</div>
					<div class="unit">
						<label>合同到期日：</label>
						<input type="text" name="contractValid" 
						<c:if test="${not empty contractValidTimeOut }">style="color:red;"</c:if> 
						id="contractValid" value="<fmt:formatDate value="${contract.contractValid }" pattern="yyyy-MM-dd" />" readonly="readonly" />
						<c:if test="${contractValidTimeOut >= 0 }"><a href="javascript:void(0);" title="合同即将到期，请联系该商户!" class="worm"></a></c:if>
						<c:if test="${contractValidTimeOut < 0 }"><a href="javascript:void(0);" title="合同已经到期，请联系该商户!" class="worm"></a></c:if>
					</div>
					 -->
				</div>
				<c:if test="${not empty isAudit }">
				<div class="formBar">
					<ul>
						<c:if test="${status == 102 || status == 103}">
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="auditMerchant('100');" >审核通过</button></div></div></li>
							<li><div class="buttonActive"><div class="buttonContent"><button type="button" target="dialog" onclick="auditNoPass('${id }');" >审核不通过</button></div></div></li>
						</c:if>
					</ul>
				</div>
				</c:if>
			</form>
		</div>
	</div>
</div>

<script>

// 企业结算账户
function settAccountTab(merchantId){
	$("#settAccountTab").click();
	if(merchantId == null || merchantId == ""){
		alertMsg.error("该商户信息不存在!");
		return;
	}
	var url = "merchant_findAccountBankByMerchantId.action";
	$.ajax({
		url:url,
		type:'post',
		data:{'merchantId':merchantId,'isView':'1'},
		success:function(data){
			if(data != null){
				$("#bankAccountDiv").html(data);
			}
		}
	});
}

//加载企业结算账户城市下拉菜单
function getAccountBankAddress(provinceNo) {
	$.post("merchant_loadAddressInfo.action", {
		'provinceNo' : provinceNo
	}, function(objarray) {
		if (provinceNo != 0) {
			$("#cityAccountBankSel").empty();
			document.getElementById("cityAccountBankSel").options.add(new Option("==市==", 0));
			for ( var i = 0; i < objarray.length; i++) {
				document.getElementById("cityAccountBankSel").options.add(new Option(objarray[i].cityName, objarray[i].cityNo));
			}
			$("#cityAccountBankSel").val("${city}");
		} else {
			for ( var i = 0; i < objarray.length; i++) {
				document.getElementById("provinceAccountBankSel").options.add(new Option(objarray[i].provinceName, objarray[i].provinceNo));
			}
			$("#provinceAccountBankSel").val("${province}");
			getAccountBankAddress("${province}");
		}
	}, "json");
}


// 结算方式
function settTypeTab(accountNo){
	$("#settTypeTab").click();
	if (accountNo == null || accountNo == "") {
		alertMsg.error("该结算信息不存在!");
		return;
	}
	var url = "SettRule_getSettRuleToEditSettRule.action?isView=1";
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

// 商户审核状态
function auditMerchant(obj){
	$("#auditForm").attr("action", "merchant_auditMerchantInfo.action?status="+obj);
	$("#auditForm").submit();
	
	// 刷新商户审核列表页面
	$("#auditMerchantBt").click();
	navTab.closeCurrentTab();
}


//销售人员
function sellPersonTab(obj){
	$("#sellPersonTab").click();
}

//商户审核状态
var num = 0;
function auditNoPass(obj){
	num++;
	var url = "merchant_toAuditNoPassUI.action?merchantId="+obj;
	$.pdialog.open(url, "auditMerchantPd"+num, "审核商户信息" ,{width:500,height:300});
}
</script>
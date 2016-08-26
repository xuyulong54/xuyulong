<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="js/provincesCity.js"></script>
<div class="pageContent">
	<form id="form" method="post" action="bank_addBankAgreement.action" class="pageForm required-validate" 
	onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			
			<input type="hidden" name="id" value="${id }" id="id">

			<div class="unit">
				<label >商户编号：</label>
				<s:textfield name="merchantNo" cssClass="required" minlength="1" maxlength="30" size="40" />
			</div>
			<div class="unit">
				<label >合同编号：</label>
				<s:textfield name="agreementNo" cssClass="required" minlength="1" maxlength="50" size="40" />
			</div>
			<div class="unit">
				<label >银行序号：</label>
				<s:textfield name="bankSequence" cssClass="required" minlength="1" maxlength="50" size="40" />
			</div>
			<div class="unit">
				<label >接口名称：</label>
				<s:textfield name="interfaceName" cssClass="required" minlength="1" maxlength="50" size="40" />
			</div>
			<div class="unit">
				<label >上线时间：</label>
				<s:textfield name="onlineTime"   Class="date"  minlength="1" maxlength="50" size="40" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit">
				<label >下线时间：</label>
				<s:textfield name="offlineTime"   Class="date"  minlength="1" maxlength="50" size="40" />
				<a class="inputDateButton" href="javascript:;">选择</a>
			</div>
			<div class="unit">
				<label >合同OA：</label>
				<s:textfield name="contractOANo" cssClass="required" minlength="1" maxlength="50" size="40" />
			</div>
			<div class="unit">
				<label >银行名称：</label>
				<select name="bankCode" class="required combox ">
						<option value="">请选择</option>
						<c:forEach items="${bankCodeWithName }" var="bankName">
							<option value="${bankName.key }">${bankName.value }</option>
						</c:forEach>
					</select>
			</div>
			<div class="unit">
				<label >通信地址：</label>
					<select class="required" style="width:85px" name="province"  id="province" onchange="getAddress(this.value,0)">
								<option value="0">==省份==</option>
							</select>
						<select class="required" style="width:85px" name="city" id="city" onchange="getAddress(0,this.value)">
								<option value="0">==市==</option>
							</select>
						<select class="required" style="width:120px" name="area"  id="area">
								<option value="0">==区/镇==</option>
						</select>
			</div>
			<div class="unit">
				<label >业务类型：</label>
				<label><input type="radio" id="linkType1" name="linkType"  checked="checked" value="1"> B2B</label>
				<label><input type="radio" id="linkType2" name="linkType" value="2"> B2C</label>
				<label ><input type="radio" id="linkType2" name="linkType" value="3"> 快捷支付</label>
			</div>
			<div class="unit">
				<label >卡种：</label>
				<label><input type="checkbox" name="cardType"  checked="checked" value="1"> 信用卡</label>
				<label><input type="checkbox" name="cardType" value="2"> 借记卡</label>
			</div>
			<div class="unit">
				<label >支持的商户类型：</label>
				<label><input type="checkbox" name="merchantType" checked="checked" value="1"> 个人</label>
				<label><input type="checkbox" name="merchantType" value="2"> 企业</label>
				<label><input type="checkbox" name="merchantType" value="3"> 个体工商户</label>
			</div>
			<div class="unit">
				<label >大小额限额点：</label>
				<s:textfield name="amountSystem" cssClass="required number" minlength="1" maxlength="12" size="40" />
			</div>
			<div class="unit">
				<label >通讯方式：</label>
				<label><input type="checkbox" name="communicationMode"  checked="checked" value="1"> HTTP</label>
				<label><input type="checkbox" name="communicationMode" value="2"> HTTPS</label>
				<label><input type="checkbox" name="communicationMode" value="3"> SFTP</label>
			</div>
			<div class="unit">
				<label >通讯地址：</label>
				<s:textfield name="communicationAddress" cssClass="required" minlength="1" maxlength="50" size="40" />
			</div>
			<div class="unit">
				<label >银行联系方式(名称、类型、电话、邮箱)：</label>
				<s:textarea name="linkMan" cssClass="required" maxlength="100" rows="3" cols="45"></s:textarea>
			</div>
			<div class="unit">
				<label >描述：</label>
				<s:textarea name="desc" maxlength="100" rows="3" cols="45"></s:textarea>
			</div>
		</div>
			
		<div class="formBar">
			<ul>
				<li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
				<li><div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
	
	// 加载详细地址
	function getAddress(provinceNo, cityNo) {
		$.post("bank_loadAddressInfo.action", {
			'provinceNo' : provinceNo,
			'cityNo' : cityNo
		}, function(objarray) {
			if (provinceNo != 0) {
				$("#city").empty();
				$("#area").empty();
				document.getElementById("city").options.add(new Option("==市==", 0));
				document.getElementById("area").options.add(new Option("==区/镇==", 0));
				for ( var i = 0; i < objarray.length; i++) {
					document.getElementById("city").options.add(new Option(objarray[i].cityName, objarray[i].cityNo));
				}
			} else if (cityNo != 0) {
				$("#area").empty();
				document.getElementById("area").options.add(new Option("==区/镇==", 0));
				for ( var i = 0; i < objarray.length; i++) {
					document.getElementById("area").options.add(new Option(objarray[i].townName, objarray[i].townNo));
				}
			} else {
				for ( var i = 0; i < objarray.length; i++) {
					document.getElementById("province").options.add(new Option(objarray[i].provinceName, objarray[i].provinceNo));
				}
			}
		}, "json");
	}
	
	$(function() {
		getAddress(0, 0);
	});
	

</script>

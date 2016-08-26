<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent sortDrag" selector="h1" layoutH="42">
		<div class="pageFormContent" layoutH="30">
		    <input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<input type="hidden" name="id" value="${bankChannelCode }" id="id">
			<input type="hidden" name="bankCode" value="${bankCode }" >
			<div class="unit">
				<label style="font-weight: bold;">银行渠道编号：</label>
				<%-- <label style="width: 500px">${bankChannelCode }</label> --%>
				<s:textfield name="bankChannelCode" readonly="true" cssClass="required" minlength="1" maxlength="25" size="45" />
			</div>
			<div class="unit">
				<label style="font-weight: bold;">银行渠道名称：</label>
				<%-- <label>${bankChannelName }</label> --%>
				<s:textfield name="bankChannelName" readonly="true" cssClass="required" minlength="1" maxlength="45" size="45" />
			</div>
			<div class="unit">
				<label style="font-weight: bold;">银行名称：</label>
				<s:textfield name="bankName" readonly="true" cssClass="required" minlength="1" maxlength="25" size="45" />
			</div>
			<div class="unit">
				<label style="font-weight: bold;">银行账户名称：</label>
				<s:textfield name="bankAccountName" readonly="true" cssClass="required" minlength="1" maxlength="45" size="45" />
			</div>
			<div class="unit">
				<label style="font-weight: bold;">状态：</label>
				<select name="status" disabled="disabled">
					<option value="101" <c:if test="${status eq 101 }">selected="selected"</c:if> >无效</option>
					<option value="100" <c:if test="${status eq 100 }">selected="selected"</c:if> >有效</option>
				</select>
			</div>
			<%-- <div class="unit">
				<label style="font-weight: bold;">银行协议ID：</label>
				<s:textfield name="bankAgreementID" readonly="true" cssClass="required" minlength="1" maxlength="25" size="45" />
			</div> --%>
			<div class="unit">
				<label style="font-weight: bold;">落地行名称：</label>
				<s:textfield name="landingBankName" readonly="true" cssClass="required" minlength="1" maxlength="90" size="45" />
			</div>
			<div class="unit">
				<label style="font-weight: bold;">描述：</label>
				<s:textarea name="remark" readonly="true" maxlength="200" rows="3" cols="40"></s:textarea>
			</div>
		</div>
		
</div>

</script>

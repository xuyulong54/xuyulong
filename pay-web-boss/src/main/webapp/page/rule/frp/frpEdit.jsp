<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
 <%@include file="/page/inc/pageForm.jsp"%>
<div class="pageContent">

<label><input type="radio" name="payType" <c:if test="${payType == PayTypeEnum.BANK_CARD.value }">checked="checked"</c:if> disabled="disabled">银行卡</label>&nbsp;&nbsp; 
<label><input type="radio" name="payType" <c:if test="${payType == PayTypeEnum.NO_CARD.value }">checked="checked"</c:if> disabled="disabled">非银行卡</label>

<c:if test="${payType == PayTypeEnum.BANK_CARD.value }">
<div id="CardFrom">
	<form id="form" method="post" action="frp_editFrp.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="60">
			<input type="hidden" name="navTabId" value="listFrp"> 
			<input type="hidden" name="callbackType" value="closeCurrent"> 
			<input type="hidden" name="forwardUrl" value=""> 
			
			<input type="hidden" name="id" value="${id }" id="id">
			<input type="hidden" name="bankCode" value="${bankCode }">
			<p style="width:99%">
				<label>银行名称：</label>
				<input id="bankName" readonly="readonly"  name="bankName" value="${bankName }">
			</p>
			<p style="width:99%">
				<label>业务类型：</label>
				<select name="busType" disabled="disabled"  style="font-size:14px;">
					<option value="0" >请选择</option>
					<c:forEach items="${bankBusTypeEnumList }" var="bankBusType">
						<option value="${bankBusType.value}" <c:if test="${busType eq bankBusType.value }">selected="selected"</c:if>>${bankBusType.desc}</option>
					</c:forEach>
				</select>
			</p>
			<p style="width:99%">
				<label>支付渠道编号：</label>
				<input id="frpCode"  readonly="readonly"  name="frpCode" value="${frpCode }">
			</p>
			<p style="width:99%">
				<label>状态：</label>
				<s:select list="#{100:'有效',101:'无效'}" listKey="key" name="status"
					listValue="value" headerKey="0" cssClass="required"  style="font-size:14px;"/>
			</p>

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
	</c:if>
	<c:if test="${payType == PayTypeEnum.NO_CARD.value }">
	<div id="NoCardFrom">
		<form id="CardFrom" method="post" action="frp_editFrp.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
			<div class="pageFormContent" layoutH="70">
				<input type="hidden" name="navTabId" value="listFrp"> 
				<input type="hidden" name="callbackType" value="closeCurrent"> 
				<input type="hidden" name="forwardUrl" value=""> 
				<input type="hidden" name="payType" value="${PayTypeEnum.NO_CARD.value}" />
				<input type="hidden" name="id" value="${id }" id="id">
				<input type="hidden" name="bankCode" value="${bankCode }">
				<p style="width:99%">
					<label>名称：</label>
					<input type="text" id="bankCode" value="${bankName }" name="bankCode"  readonly="readonly"  cssClass="required" minlength="1" maxlength="60" size="30" />
				<%-- 
				<p style="width:99%">
					<label>业务类型：</label>
					<select id="busType" onchange="splicingChannelByBusType(this.value)" name="busType" style="font-size:14px;">
						<option value="">请选择</option>
						<c:forEach items="${bankBusTypeEnumList }" var="bankBusType">
							<option value="${bankBusType.value}">${bankBusType.desc}</option>
						</c:forEach>
					</select>
				</p> 
				--%>
				<p style="width:99%">
					<label>支付渠道编号：</label>
					<input type="text" id="frpFrpCode" value="${frpCode }" name="frpCode"  readonly="readonly"  cssClass="required" minlength="1" maxlength="60" size="30" >
				</p>
				<p style="width:99%">
					<label>状态：</label>
					<s:select list="#{100:'有效',101:'无效'}" listKey="key" name="status"
					listValue="value" headerKey="0" cssClass="required"  style="font-size:14px;"/>
				</p>
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
	</c:if>
</div>

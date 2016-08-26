<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="paymentRecord_addPaymentRecord.action" class="pageForm required-validate"
		onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list">
			<input type="hidden" name="callbackType" value="closeCurrent">
			<input type="hidden" name="forwardUrl" value="">
			<p>
				<label>商户订单号：</label>
				<s:textfield name="orderNo" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>交易流水号：</label>
				<s:textfield name="trxNo" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行订单号：</label>
				<s:textfield name="bankOrderNo" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行流水号：</label>
				<s:textfield name="bankTrxNo" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>支付金额：</label>
				<s:textfield name="payAmount" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>商户ID：</label>
				<s:hidden name="merchantId" id="merchantId" ></s:hidden>
				<s:textfield name="merchant.merchantId" id="ref_merchantId"  cssClass="required" minlength="1" maxlength="25" size="30" />
			    <a id="findBtn" class="btnLook" href="merchant_merchantLookupList.action" lookupGroup="merchant">搜索</a>
			</p>
			<p>
				<label>商户名称：</label>
				<s:hidden name="merchantName" id="merchantName"></s:hidden>
				<s:textfield name="merchant.merchantName"  id="ref_merchantName" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>客户ID：</label>
				<s:textfield name="customerId" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>客户名称：</label>
				<s:textfield name="customerName" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>商户汇率：</label>
				<s:textfield name="merchantRate" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>收款方手续费率：</label>
				<s:textfield name="sourceRate" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>付款方手续费率：</label>
				<s:textfield name="targetRate" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>是否退款：</label>
				<s:select name="isRefund"  list="#{1:'否',0:'是'}" />
			</p>
			<p>
				<label>退款次数：</label>
				<s:textfield name="refundTime" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>支付状态：</label>
				<s:select name="status"  list="#{100:'交易成功', 101:'交易失败', 102:'订单已创建', 103:'订单已取消'}" />
			</p>
			<p>
				<label>业务类型：</label>
				<s:select list="selectMap" name="trxType" listKey="value" listValue="key" cssClass="required" cssStyle="width:184px" />
			</p>
			<p>
				<label>银行名称：</label>
				<s:hidden name="bankName" id="bankName"></s:hidden>
				<s:select  name="bankCode" list="#mapBankCode" listKey="key" listValue="value" onchange="addBankName(this)"></s:select>
			</p>
			
			<p>
				<label>支付渠道编号：</label>
				<s:textfield name="frpCode" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行渠道编号：</label>
				<s:textfield name="bankChannelCode" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行汇率：</label>
				<s:textfield name="bankRate" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行协议ID：</label>
				<s:textfield name="bankAgreementId" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>成功退款总金额：</label>
				<s:textfield name="successRefundAmount" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>代理商ID：</label>
				<s:textfield name="agentId" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>代理商名称：</label>
				<s:textfield name="agentName" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>代理商汇率：</label>
				<s:textfield name="agentRate" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>订单金额：</label>
				<s:textfield name="orderAmount" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行手续费：</label>
				<s:textfield name="bankFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label> 商户手续费：</label>
				<s:textfield name="merchantFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label> 付款方手续费：</label>
				<s:textfield name="targetFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label> 收款方手续费：</label>
				<s:textfield name="sourceFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label> 代理商手续费：</label>
				<s:textfield name="agentFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>平台收入：</label>
				<s:textfield name="platFee" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>来源账户编号：</label>
				<s:textfield name="source_AccountId" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>目标账户编号：</label>
				<s:textfield name="target_AccountId" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
		</div>
		<div class="formBar">
			<ul>
				<li><div class="buttonActive">
						<div class="buttonContent">
							<button type="button" onclick="submitForm();">保存</button>
						</div>
					</div></li>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">取消</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>
<script type="text/javascript">
function submitForm(){
	$("#merchantId").val($("#ref_merchantId").val());
	$("#merchantName").val($("#ref_merchantName").val());
	$("#form").submit();
}
function addBankName(bankCodeDomObj){
$("#bankName").val($(bankCodeDomObj).find("option:selected").text());
}
</script>

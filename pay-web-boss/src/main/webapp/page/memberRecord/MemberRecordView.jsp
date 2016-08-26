<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<form id="form" method="post" action="" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
		<div class="pageFormContent" layoutH="58">
			<input type="hidden" name="navTabId" value="list"> <input type="hidden" name="callbackType" value="closeCurrent"> <input type="hidden" name="forwardUrl" value="">
			<s:hidden name="id"></s:hidden>
			<p>
				<label>商户订单号：</label>
				<s:textfield name="merchantOrderNo" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>交易流水号：</label>
				<s:textfield name="trxNo" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>银行流水号：</label>
				<s:textfield name="bankTrxNo" cssClass="required" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>交易金额：</label> <input type="text" name="orderAmount"
					value="<c:if test="${trxType==6001 || trxType==6004||trxType==6005 }">-</c:if><c:if test="${trxType==3001 || trxType == 4001|| trxType == 4002|| trxType == 4003|| trxType == 4004|| trxType == 4005 }">+</c:if>${orderAmount }" disabled="disabled" maxlength="25"
					size="30" />
			</p>
			
			<p>
				<label>商户名称：</label>
				<s:textfield name="merchantName" id="ref_merchantName" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>会员登录名：</label>
				<s:textfield name="payerName" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>是否退款：</label>
				<s:select name="isRefund" disabled="true" value="%{isRefund?0:1}" list="#{1:'否',0:'是'}" />
			</p>
			<p>
				<label>退款次数：</label>
				<s:textfield name="refundTimes" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>
			<p>
				<label>订单状态：</label>
				<s:select name="status" disabled="true" value="%{status}" list="#{100:'交易成功', 101:'交易失败', 102:'订单已创建', 103:'订单已取消'}" />
			</p>
			<p>
				<label>成功退款总金额：</label>
				<s:textfield name="successRefundAmount" readonly="true" minlength="1" maxlength="25" size="30" />
			</p>

		</div>
		<div class="formBar">
			<ul>
				<li><div class="button">
						<div class="buttonContent">
							<button type="button" class="close">关闭</button>
						</div>
					</div></li>
			</ul>
		</div>
	</form>
</div>

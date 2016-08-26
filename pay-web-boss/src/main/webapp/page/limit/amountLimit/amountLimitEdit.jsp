<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>
        <div class="pageContent">
            <form id="form" method="post" action="amountLimit_addOrEditAmountLimit.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone);">
                <div class="pageFormContent" layoutH="60">
                    <input type="hidden" name="navTabId" value="listAmountLimit">
                    <input type="hidden" name="callbackType" value="closeCurrent">
                    <input type="hidden" name="forwardUrl" value="">
                    <input type="hidden" name="id" value="${id}" />
                    <p>
                        <label>金额限制包：</label>						
                        <select name="amountLimitPack" disabled="disabled">
                            <c:forEach items="${AmountLimitPackList }" var="v">
                                <option  value=${v.id } <c:if test="${amountLimitPackId eq v.id }">selected="selected"</c:if>>${v.name}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="amountLimitPackId" value="${amountLimitPackId}" />
                    </p> 

                    <p>
                        <label>业务功能：</label>
                        <select name="bizFunction" id="bizFunction" onchange="changeBizFunction(this.value)"  class="required">
                            <c:forEach items="${TrxTypeEnums }" var="v">
                                <option value="${v.name }" <c:if test="${bizFunction eq v.name }">selected="selected"</c:if>>${v.desc }</option>
                            </c:forEach>
                        </select>
                        <font color="red">*</font>
                    </p>
                    <p>
                        <label>支付产品：</label>
                        <select name="payProduct" id="payProduct" onchange="getPayWay(this.value)" >
                        	<option value="">请选择</option>
                            <c:forEach items="${ PayProductList}" var="v">
                                <option value="${v.payProductCode }" <c:if test="${ payProduct eq v.payProductCode }">selected="selected"</c:if>>${v.payProductName }</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label>支付方式：</label>
                        <select name="payWay" id="payWay" >
                        	<option value="">请选择</option>
                            <c:forEach items="${PayWayList }" var="v">
                                <option value="${v.payWayCode }" <c:if test="${payWay eq v.payWayCode }">selected="selected"</c:if>>${v.payWayCode }</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label>支付卡种：</label>
                        <select name="cardType" id="cardType" >
                        	<option value="">请选择</option>
                            <c:forEach items="${CardTypeEnums }" var="v">
                                <option value="${v.value }" <c:if test="${cardType eq v.value }">selected="selected"</c:if>>${v.desc}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label>限制类型：</label>
                        <select name="limitType" id="limitType" onchange="changeLimitType(this.value)">
                            <c:forEach items="${LimitTypeEnums }" var="v">
                                <option value="${v.value }" <c:if test="${limitType eq v }">selected="selected"</c:if>>${v.label}</option>
                            </c:forEach>
                        </select>
                    </p>
                    <p>
                        <label>最小限额：</label>
                        <input type="text" name="minAmount" id="minAmount" />
                    </p>
                    <p>
                        <label>最大限额：</label>
                        <input type="text" name="maxAmount" value="<fmt:formatNumber value="${maxAmount}" pattern="#0.00"/>" class="required"/>
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
        
        
        <script type="text/javascript">
        
     function changeBizFunction(bizFunction){
     	var $payProduct = $('#payProduct');
		var $payWay = $('#payWay');
		var $cardType = $('#cardType');
     	if(bizFunction == "REFUND" || bizFunction == "SETTLEMENT" || bizFunction == "ATM" || bizFunction == "ACCOUNT_TRANSFER"){
     		//$payProduct.attr('disabled', 'disabled').removeAttr('class');
			//$payWay.attr('disabled', 'disabled').removeAttr('class');
			/* if(bizFunction == "ACCOUNT_TRANSFER"){
				$cardType.attr('disabled', 'disabled').removeAttr('class');
			} */
     	}else{
     		//$payProduct.attr('class', 'required').removeAttr('disabled');
			//$payWay.attr('class', 'required').removeAttr('disabled');
			//$cardType.attr('class', 'required').removeAttr('disabled');
     	}
     	
     	var POS_PAY='${LimitTrxTypeEnum.POS_PAY.value}';//POS消费
     	var POS_REFUND='${LimitTrxTypeEnum.POS_REFUND.value}';//退款
     	var POS_PREAUTHED='${LimitTrxTypeEnum.POS_PREAUTHED.value}';//预授权完成
     	
     	if(bizFunction == "POS_PAY" || bizFunction == "POS_REFUND" || bizFunction == "POS_PREAUTH" || bizFunction == "ACCOUNT_TRANSFER"){
     		$payProduct.attr('disabled', 'disabled').removeAttr('class');
			$payWay.attr('disabled', 'disabled').removeAttr('class');
			$cardType.attr('disabled', 'disabled').removeAttr('class');
     	}else{
     		$payProduct.removeAttr('disabled');
			$payWay.removeAttr('disabled');
			$cardType.removeAttr('disabled');
     	}
     }

	// 加载支付方式
	function getPayWay(payProduct) {
		$("#payWay").empty();
		document.getElementById("payWay").options.add(new Option("请选择", ""));
		if(payProduct != ""){
			$.post("amountLimit_loadPayWay.action", {
			'payProduct' : payProduct
			}, function(objarray) {
				
				if(objarray == null || objarray == ""){
					alertMsg.error("该支付产品下未设置支付方式");
				}else{
						for ( var i = 0; i < objarray.length; i++) {
							document.getElementById("payWay").options.add(new Option(objarray[i].payWayCode, objarray[i].payWayCode));
						}
				}
			}, "json");
		}
		
	}
	
	
	$(function(){
		changeBizFunction($('#bizFunction').val());
	});

	function changeLimitType(limitType) {
     	var $minAmount = $('#minAmount');
     	

		if(limitType == "CUMULATE_DAILY" || limitType == "CUMULATE_PER_MONTH") {
			$minAmount.attr('readonly','readonly').removeAttr('class');
			$minAmount.attr('class','textInput readonly valid');
			$minAmount.removeAttr('value');
			
			}
		else{
			$minAmount.removeAttr('readonly').attr('class','required');	
			$minAmount.attr('value','<fmt:formatNumber value="${minAmount}" pattern="#0.00"/>');
		}	
	}
	
	$(function(){
		changeLimitType($('#limitType').val());
	});
</script>
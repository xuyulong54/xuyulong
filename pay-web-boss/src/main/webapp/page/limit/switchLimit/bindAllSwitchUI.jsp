<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>

<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <input type="hidden" name="navTabId" value="">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="switchLimitPackId" id="switchLimitPackId" value="${switchLimitPackId }" />

        

        <fieldset>
            <legend>支付产品限制</legend>
            <div class="unit">
                <div style="float: left; width: 10%;">
                    <label style="width: 90px;"></label>
                </div>
                <c:forEach items="${payProductList }" var="model" varStatus="index">
                    <div style="width: 250px; float: left; border: 1px solid #999999; margin-left: 10px;margin-top: 10px;">
                        <!-- 循环产品名称 -->
                        <label style="width: 240px;background-color: #c3e0f4;" title="产品名称（产品编号）">
                            <input type="checkbox" id="${model.payProductCode }" name="productCodeCk" <c:if test="${model.isSelectPayProductCode eq true }">checked="checked"</c:if>
                            onclick="clickPayProductCk(this);" value="${model.payProductCode }" />${model.payProductName }（${model.payProductCode }）
                            <!-- 显示和隐藏的按钮
									<a href="###" id="showDiv" style="float: right;padding-top: 5px; color:blue;" onclick="hidePayWayDiv('${index.index+1 }');" >显示/隐藏</a>
									 -->
                        </label>

                    </div>

                    <!-- 清除浮动 -->
                    <c:if test="${(index.index+1)%3 == 0 }">
                        <br style="clear:both" />
                    </c:if>

                </c:forEach>
            </div>
        </fieldset>

        <div class="unit">
            <label style="width: 120px;"></label>
        </div>

        <fieldset>
            <legend>支付方式限制</legend>
            <div class="unit">
                <div style="float: left; width: 10%;">
                    <label style="width: 90px;"></label>
                </div>
                <c:forEach items="${payProductList }" var="model" varStatus="index">
                    <div style="width: 250px; float: left; border: 1px solid #999999; margin-left: 10px;margin-top: 10px;">
                        <!-- 循环产品名称 -->
                        <label style="width: 240px;background-color: #c3e0f4;" title="产品名称（产品编号）">
                            ${model.payProductName }（${model.payProductCode }）
                            <!-- 显示和隐藏的按钮
									<a href="###" id="showDiv" style="float: right;padding-top: 5px; color:blue;" onclick="hidePayWayDiv('${index.index+1 }');" >显示/隐藏</a>
									 -->
                        </label>
                        <div id="table_${index.index+1 }">
                            <table class="table" style="width: 100%;">
                                <c:if test="${not empty model.payWayCodeList }">
                                    <c:forEach items="${model.payWayCodeList }" var="payWay" varStatus="index1">
                                        <!-- 循环产品下的支付方式 -->
                                        <tr>
                                            <td style="width: 100%;margin: 0px;">
                                                <label style="width: 5px;">&nbsp;</label>
                                                <label>
                                                    <input type="checkbox" id="${switchLimitPackId }-${model.payProductCode }-${payWay.payWayCode }" name="payWayCode" value="${payWay.payWayCode }" onclick="addPayWaySwitch('${switchLimitPackId }', '${model.payProductCode }', '${payWay.payWayCode }');" <c:if test="${payWay.isSelectPayWayCode eq true }">checked="checked"</c:if>/>${payWay.payWayCode }
                                </label>
                                </td>
                                </tr>
                                </c:forEach>
                                </c:if>
                                <c:if test="${empty model.payWayCodeList }">
                                    <tr>
                                        <td style="width: 100%;margin: 0px; color: red;">
                                            <label style="width: 5px;">&nbsp;</label>
                                            暂无绑定支付方式!
                                        </td>
                                    </tr>
                                </c:if>
                            </table>
                        </div>
                    </div>

                    <!-- 清除浮动 -->
                    <c:if test="${(index.index+1)%3 == 0 }">
                        <br style="clear:both" />
                    </c:if>

                </c:forEach>
            </div>
        </fieldset>

    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">关闭</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
        <script>
            // 点击产品事件
            function addPayWaySwitch(switchLimitPackId, productCode, payWayCode) {
                if (switchLimitPackId == null || productCode == null || payWayCode == null) return;
                var payWayId = switchLimitPackId + "-" + productCode + "-" + payWayCode; // 支付规则ID
                if ($("#" + payWayId).attr("checked") == "checked") { // 选中
                    $.post("switchLimit_addPayWaySwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "payProduct": productCode,
                        "payWay": payWayCode
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                            $("#" + payWayId).removeAttr("checked");
                        }
                    }, "json");
                    //$("#" + productCode).attr("checked", "checked");
                } else {
                    // 取消选中
                    $.post("switchLimit_deletePayWaySwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "payProduct": productCode,
                        "payWay": payWayCode
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                            $("#" + payWayId).removeAttr("checked");
                        }
                    }, "json");
                    //$("#" + productCode).removeAttr("checked");
                }
            }

             // 点击支付产品复选框事件
            function clickPayProductCk(obj) {
                if (obj == null) return;
                var switchLimitPackId = "${switchLimitPackId}"; // 规则ID
                var payProduct = obj.value; // 支付产品编号

                if ($(obj).attr("checked") == "checked") { // 选中
                    // 		选中该下面所有的复选框
                    // 		$("input:checkbox[id^=" + switchLimitPackId + "-" + productCode +"]").attr("checked", "checked");		
                    // 		var payWayCodes = "";
                    // 		$("input:checkbox[id^=" + switchLimitPackId + "-" + productCode +"]").each(function(){
                    // 			if(payWayCodes == ""){
                    // 				payWayCodes += $(this).attr("id") ;
                    // 			} else {
                    // 				payWayCodes += "#" + $(this).attr("id") ;
                    // 			}
                    // 		});

                    // 批量绑定支付方式方法
                    $.post("switchLimit_addPayProductSwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "payProduct": payProduct
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                        }
                    }, "json");
                } else {
                    // 取消选中下面所有的复选框
                    // 		$("input:checkbox[id^=" + switchLimitPackId + "-" + productCode +"]").removeAttr("checked");
                    // 		var payWayCodes = "";
                    // 		$("input:checkbox[id^=" + switchLimitPackId + "-" + productCode +"]").each(function(){
                    // 			if(payWayCodes == ""){
                    // 				payWayCodes += $(this).attr("id") ;
                    // 			} else {
                    // 				payWayCodes += "#" + $(this).attr("id") ;
                    // 			}
                    // 		});
                    // 批量解绑支付方式方法
                    $.post("switchLimit_deletePayProductSwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "payProduct": payProduct
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                        }
                    }, "json");
                }
            }

             // 点击业务功能复选框事件
            function clickBizFunctionCk(obj) {
                if (obj == null) return;
                var switchLimitPackId = "${switchLimitPackId}"; // 规则ID
                var bizFunction = obj.value; // 业务功能编号

                if ($(obj).attr("checked") == "checked") { // 选中
                    // 绑定业务功能开关
                    $.post("switchLimit_addBizFunctionSwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "bizFunction": bizFunction
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                        }
                    }, "json");
                } else {

                    // 解绑业务功能开关 
                    $.post("switchLimit_deleteBizFunctionSwitch.action", {
                        "switchLimitPackId": switchLimitPackId,
                        "bizFunction": bizFunction
                    }, function (res) {
                        if (res.STATE != "SUCCESS") {
                            alertMsg.error(res.MSG);
                        }
                    }, "json");
                }
            }



             // 隐藏支付方式div
            function hidePayWayDiv(objNo) {
                $("#table_" + objNo).toggle("normal");
            }
        </script>

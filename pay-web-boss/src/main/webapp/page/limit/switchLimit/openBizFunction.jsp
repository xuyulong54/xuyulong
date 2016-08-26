<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>

<div class="pageContent">
    <div class="pageFormContent" layoutH="58">
        <input type="hidden" name="navTabId" value="">
        <input type="hidden" name="callbackType" value="closeCurrent">
        <input type="hidden" name="forwardUrl" value="">
        <input type="hidden" name="switchLimitPackId" id="switchLimitPackId" value="${switchLimitPackId }" />

        <fieldset>
           <!--  <legend>在线业务功能</legend> -->
            <div class="unit">
                <div style="float: left; width: 10%;">
                    <label style="width: 90px;"></label>
                </div>
                <!-- 循环业务功能 -->
                <c:forEach items="${onlinelimitTrxTypeEnumList}" var="v">
                <c:forEach items="${bizFunctionList }" var="model" varStatus="index">
                	<c:if test="${v.name eq model.bizFunctionCode }">
                    <div style="width: 250px; float: left; border: 1px solid #999999; margin-left: 10px;margin-top: 10px;">
                        <label style="width: 240px;background-color: #c3e0f4;" title="业务功能编号">
                            <input type="checkbox" id="${model.bizFunctionCode }" name="functionCodeCk" <c:if test="${model.isSelectBizFunctionCode eq true }">checked="checked"</c:if>
                            onclick="clickBizFunctionCk(this);" value="${model.bizFunctionCode }" />${model.bizFunctionName }（${model.bizFunctionCode }）
                        </label>
                    </div>
                    </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
        </fieldset>
        
      <%--   <fieldset>
         <legend>POS业务功能</legend>
            <div class="unit">
                <div style="float: left; width: 10%;">
                    <label style="width: 90px;"></label>
                </div>
                <!-- 循环业务功能 -->
                <c:forEach items="${poslimitTrxTypeEnumList}" var="v">
                <c:forEach items="${bizFunctionList }" var="model" varStatus="index">
                	<c:if test="${v.name eq model.bizFunctionCode }">
                    <div style="width: 270px; float: left; border: 1px solid #999999; margin-left: 10px;margin-top: 10px;">
                        <label style="width: 260px;background-color: #c3e0f4;" title="业务功能编号">
                            <input type="checkbox" id="${model.bizFunctionCode }" name="functionCodeCk" <c:if test="${model.isSelectBizFunctionCode eq true }">checked="checked"</c:if>
                            onclick="clickBizFunctionCk(this);" value="${model.bizFunctionCode }" />${model.bizFunctionName }（${model.bizFunctionCode }）
                        </label>
                    </div>
                    </c:if>
                    </c:forEach>
                </c:forEach>
            </div>
          </fieldset> --%>
    </div>
    <div class="formBar">
        <ul>
            <li>
                <div class="button">
                    <div class="buttonContent">
                        <button type="button" class="close">取消</button>
                    </div>
                </div>
            </li>
        </ul>
    </div>
</div>
        <script>
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

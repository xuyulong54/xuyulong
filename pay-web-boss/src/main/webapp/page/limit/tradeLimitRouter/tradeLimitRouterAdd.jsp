<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>
        <div class="pageContent">
            <form method="post" name="riskTypeAdd" action="tradeLimitRouter_addTradeLimitRouter.action" class="pageForm required-validate" onsubmit="return validateCallback(this, dialogAjaxDone); ">
                <!-- 基本信息 -->
                <div class="pageFormContent">
                    <input type="hidden" name="navTabId" value="merchantList">
                    <input type="hidden" name="callbackType" value="closeCurrent">
                    <input type="hidden" name="forwardUrl" value="">
                    <input type="hidden" name="id" value="${id}">
                    <p>
                        <label>商户编号：</label>
                        	<c:if test="${merchantNo ne  ''}"><s:textfield name="merchantNo" minlength="1" maxlength="20" cssClass="required" size="20"  readonly="true" /></c:if>
                        	<c:if test="${merchantNo eq  ''}"><s:textfield name="merchantNo" minlength="1" maxlength="20" cssClass="required" size="20"   /></c:if>
                    </p>
                    <p>
                        <label>开关限制包：</label>
                        <select name="switchLimitPackId" onchange="ChangeSwitchLimitPack(this.value)">
                            <option value="">--请选择--</option>
                            <c:forEach items="${switchLimitPacksList}" var="model">
						<option value="${model.id}"						
						<c:if test="${switchLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>	
                        </select>
                    </p>
                    <p>
                        <label>金额限制包：</label>
                        <select name="amountLimitPackId" onchange="ChangeAmountLimitPack(this.value)">
                            <option value="">--请选择--</option>
                           <c:forEach items="${amountLimitPackList}" var="model">
								<option value="${model.id}"						
								<c:if test="${amountLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>			
                        </select>
                    </p>


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

                </div>
            </form>
            <!-- 包TABS -->
            <div class="tabs" currentIndex="0">
                <div class="tabsHeader">
                    <div class="tabsHeaderContent">
                        <ul>
                            <li><a href="#"><span>限制开关包</span>
					</a>
                            </li>
                            <li><a href="#"><span>金额限制包</span>
					</a>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="tabsContent" style="height:300px">

                    <!-- 开关限制包 -->
                    <div style="display: block;">

                        <div eventtype="click" currentindex="0" class="tabs">
                            <div class="tabsHeader">
                                <div class="tabsHeaderContent">
                                    <ul>
                                        <li class=""><a href="javascript:;"><span>开通业务功能</span>
								</a>
                                        </li>
                                        <li class="selected"><a href="javascript:;"><span>支付产品限制</span>
								</a>
                                        </li>
                                        <li class="selected"><a href="javascript:;"><span>支付方式限制</span>
								</a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div style="height:250px;" class="tabsContent">

                                <!-- 业务功能 -->
                                <div style="display: none;" inited="1000" id="BizFunctionDiv">

                                </div>
                                <!-- 支付产品 -->
                                <div style="display: block;" inited="1000" id="PayProductDiv">

                                </div>
                                <!-- 支付方式 -->
                                <div style="display: block;" inited="1000" id="PayWayDiv">
                                    <ul id="outer_wrap">


                                    </ul>
                                </div>

                            </div>
                            <div class="tabsFooter">
                                <div class="tabsFooterContent"></div>
                            </div>
                        </div>


                    </div>



                    <!-- 金额限制包 -->
                    <div style="display: none;">
                        <table class="table" >
                            <thead>
                                <tr>
                                    <th style="width: 69px;">业务功能</th>
                                    <th style="width: 69px;">支付产品</th>
                                    <th style="width: 150px;">支付方式</th>
                                    <th style="width: 69px;">卡种</th>
                                    <th style="width: 69px;">限制类型</th>
                                    <th style="width: 69px;">最小限额</th>
                                    <th style="width: 69px;">最大限额</th>
                                    <th style="width: 69px;">创建时间</th>
                                </tr>
                            </thead>
                            <tbody id="AmountLimitTbody">

                            </tbody>
                        </table>

                    </div>

                </div>

                <div class="tabsFooter">
                    <div class="tabsFooterContent"></div>
                </div>

            </div>

        </div>

        <script type="text/javascript">
        
        	//初始化数据
        	$(function(){
        	
        		//初始化开关限制包的数据
        		if("${switchLimitPackId}" !=""){
        			ChangeSwitchLimitPack("${switchLimitPackId}");
        		}
        		
        		
        		//初始化金额限制包的数据
        		if("${amountLimitPackId}" !=""){
        			ChangeAmountLimitPack("${amountLimitPackId}");
        		}
        		
        	});
        	
        	
        		
        
        	
            //开关限制包
            function ChangeSwitchLimitPack(val) {
				
				
				$("#BizFunctionDiv").html("");
				$("#PayProductDiv").html("");
				$("#outer_wrap").html("");
				
                if (val == "") {
                    return;
                }

                //加载数据
                $.post("tradeLimitRouter_queryDataBySwitchLimitPackId.action", {
                    switchLimitPackId: val
                }, function (res) {
                	
                	if(res.resultData!='undefined'){
                
	                    //业务功能
	                    var BizFunctionList = res.resultData.BizFunctionList;
	                    //支付产品
	                    var PayProductList = res.resultData.PayProductList;
	                    //支付方式
	                    var PayWayList = res.resultData.PayWayList;
	                    //获取所有的支付产品
	                    var PayProductAllList = res.resultData.PayProductAllList;
	                    
	                    var PayWayProductList = res.resultData.PayWayAndProductList;
	
	                    //加载业务功能
	                    initBizFunction(BizFunctionList);
	
	                    //加载支付产品
	                    initPayProduct(PayProductList, PayProductAllList);
	
	                    //加载支付方式
	                    initPayWay(PayWayList, PayWayProductList);
                    
                    }

                }, "json");

            }

             //交易限制包
            function ChangeAmountLimitPack(val) {
				
				$("#AmountLimitTbody").html(" ");
				
				if(val==""){
					return;
				}
				
				
                //加载数据
                $.post("tradeLimitRouter_queryDataByAmountLimitPackId.action", {
                    amountLimitPackId: val
                }, function (res) {

                    if (res == null || res.AmountLimitList == null) {
                        return;
                    }

					
					var AmountHTML="";
					
                    for (var k = 0; k < res.AmountLimitList.length; k++) {

                        var amountLimit = res.AmountLimitList[k];
						var minAmount = new Number(amountLimit.minAmount);
						var maxAmount = new Number(amountLimit.maxAmount);
                         AmountHTML += "<tr><td style='width: 69px;'>" + amountLimit.bizFunctionDesc + "</td><td style='width: 69px;'>" + amountLimit.payProduct + "</td>" +
                            "<td style='width: 150px;'>" + amountLimit.payWay + "</td><td style='width: 69px;'>" + amountLimit.cardTypeDesc + "</td>";
                        AmountHTML += "<td style='width: 69px;'>" + amountLimit.limitTypeDesc + "</td><td style='width: 69px;'>" + minAmount.toFixed(2)  + "</td><td style='width: 69px;'>" + maxAmount.toFixed(2) + "</td><td style='width: 69px;'>" + amountLimit.createTime + "</td></tr>";

                       
                    }
                    
                     $("#AmountLimitTbody").html(AmountHTML);

                }, "json");
            }

             //加载业务功能
            function initBizFunction(bizFunctionList) {
                $("#BizFunctionDiv").html("");
                for (i = 0; i < bizFunctionList.length; i++) {
                    $("#BizFunctionDiv").append("<div > <span class='tips' style='display: inline-block; min-width: 104px; width: auto;'>" + bizFunctionList[i].bizFunctionDesc + "</span></div>");
                }
            }

             //加载支付产品
            function initPayProduct(payProductList, payProductAllList) {
                $("#PayProductDiv").html("");
                for (var i = 0; i < payProductList.length; i++) {
                    for (var k = 0; k < payProductAllList.length; k++) {
                        if (payProductList[i].payProduct == payProductAllList[k].payProductCode) {
                            $("#PayProductDiv").append("<div > <span  class='tips' style='display: inline-block; min-width: 104px; width: auto;'>" + payProductAllList[k].payProductName + "</span></div>");
                        }
                    }
                }
            }

             //加载支付方式
            function initPayWay(payWayList, payProductAllList) {

                var PayWayHTML = "<li >";

                for (var i = 0; i < payProductAllList.length; i++) {
                

                    PayWayHTML += "<h2><div > <span class='tips' style='display: inline-block; min-width: 104px; width: auto;'>" + payProductAllList[i].payProductName + "</span></div></h2>";

                    for (var k = 0; k < payWayList.length; k++) {

                        PayWayHTML += "<ul>";

                        if (payWayList[k].payProduct == payProductAllList[i].payProductCode) {

                            PayWayHTML += "<li><div > <span class='tips' style='display: inline-block; min-width: 104px; width: auto;'>" + payWayList[k].payWay + "</span></div></li>";
                        }

                        if (payWayList.length - 1 == k) {
                            PayWayHTML += "<ul>";
                        }
                    }
                }

                PayWayHTML += "</li>";

                $("#outer_wrap").html(PayWayHTML);

            }
        </script>
        

        <style type="text/css">
            .tips {
                background-color: #b8d0d6;
                border: 1px solid #74c802;
                height: 30px;
                line-height: 35px;
                text-indent: 10px;
                padding-right:8px;
            }
            h2,
            h3 {
                margin: 0;
            }
            ul {
                margin: 0;
                padding: 0;
                list-style: none;
            }
            #outer_wrap li {
                padding-left: 30px;
                line-height: 24px;
            }
            .controlSymbol {
                padding: 0 5px;
                border: 1px solid #adff2f;
                cursor: pointer;
            }
        </style>

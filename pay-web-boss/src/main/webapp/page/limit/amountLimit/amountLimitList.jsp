<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@include file="/page/inc/taglib.jsp" %>
        <div class="pageHeader">
            <form id="pagerForm" onsubmit="return navTabSearch(this);" action="amountLimit_listAmountLimit.action" method="post" name="amountLimitForm">
                <%@include file="/page/inc/pageForm.jsp" %>
                        <input type="hidden" name="amountLimitPackId" value="${amountLimitPackId}" />
                    <div class="searchBar">
                        <table class="searchContent">
                            <tr>  
                                <td>
                            	 业务功能：
                            	<select name="bizFunction">
								<option value="">请选择</option>
                          		<c:forEach items="${TrxTypeEnums }" var="v">
                                <option value="${v.value }" <c:if test="${bizFunction eq v.value }">selected="selected"</c:if>>${v.desc }</option>
                            	</c:forEach>
                        		</select>
                                </td>
                                 <td>
                             	   支付产品：
                                 <select name="payProduct">
								<option value="">请选择</option>
                            	 <c:forEach items="${ PayProductList}" var="v">
                                 <option value="${v.payProductCode }" <c:if test="${ payProduct eq v.payProductCode }">selected="selected"</c:if>>${v.payProductName }</option>
                            	 </c:forEach>
                        		 </select>
                                 </td>
                                 <td>
                                 	支付方式：
                                 <select name="payWay">
								<option value="">请选择</option>
                            	 <c:forEach items="${ PayWayList}" var="v">
                                 <option value="${v.frpCode }" <c:if test="${ payWay eq v.frpCode }">selected="selected"</c:if>>${v.frpCode }</option>
                            	 </c:forEach>
                        		 </select>
                                 </td> 
                                 </tr>
                                 <tr>
                                 <td>
                               	    支付卡种：
                                 <select name="cardType">
								<option value="">请选择</option>
                            	 <c:forEach items="${ CardTypeEnums}" var="v">
                                 <option value="${v.value }" <c:if test="${ cardType eq v.value }">selected="selected"</c:if>>${v.desc }</option>
                            	 </c:forEach>
                        		 </select>
                                 </td>
                                  <td>
                               	    限制类型：
                                 <select name="limitType">
								<option value="">请选择</option>
                            	 <c:forEach items="${ LimitTypeEnums}" var="v">
                                 <option value="${v.value }" <c:if test="${ limitType eq v.value }">selected="selected"</c:if>>${v.label }</option>
                            	 </c:forEach>
                        		 </select>
                                 </td>                                                               
                                <td>
                                    <div class="subBar">
                                        <ul>
                                            <li>
                                                <div class="buttonActive">
                                                    <div class="buttonContent">
                                                        <button type="submit">查询</button>
                                                    </div>
                                                </div>
                                            </li>
                                        </ul>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
            </form>
        </div>
        <div class="pageContent">
            <div class="panelBar">
                    <ul class="toolBar">
                        <li><a class="add" href="amountLimit_toEditAmountLimit.action?amountLimitPackId=${amountLimitPackId}" target="dialog" rel="input" title="添加"><span>添加</span></a>
                        </li>
                    </ul>
            </div>
            <table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="162">
                <thead>
                    <tr>
                        <th >序号</th>
                        <th >金额限制包</th>
                        <th >业务功能</th>
                        <th >支付产品</th>
                        <th >支付方式</th>
                        <th >卡种</th>
                        <th >限制类型</th>
                        <th >最小限额</th>
                        <th >最大限额</th>
                        <th >创建时间</th>
                        <th >操作</th>
                    </tr>
                </thead>
                <tbody>
                    <s:iterator value="recordList" status="st">
                        <tr target="sid_user" rel="${id}">
                            <td>${st.index+1}</td>
                            <td>
								<c:forEach var="amountLimitPackList" items="${AmountLimitPackList }">
								<c:if test="${amountLimitPackId eq amountLimitPackList.id }">${amountLimitPackList.name }
								</c:if>
								</c:forEach>						
							</td>
                            <td>
								<c:forEach var="trxTypeEnums" items="${TrxTypeEnums }">
								<c:if test="${bizFunction eq trxTypeEnums.value }">${trxTypeEnums.desc }
								</c:if>
								</c:forEach>						
							</td>
							 <td>
								<c:forEach var="payProductList" items="${PayProductList }">
								<c:if test="${payProduct eq payProductList.payProductCode }">${payProductList.payProductName }
								</c:if>
								</c:forEach>
							</td>						
                            <td>${payWay}</td>
                             <td>
								<c:forEach var="cardTypeEnums" items="${CardTypeEnums }">
								<c:if test="${cardType eq cardTypeEnums.value }">${cardTypeEnums.desc }
								</c:if>
								</c:forEach>						
							</td>
							 <td>
								<c:forEach var="limitTypeEnums" items="${LimitTypeEnums }">
								<c:if test="${limitType eq limitTypeEnums.value }">${limitTypeEnums.label }
								</c:if>
								</c:forEach>						
							</td>
                            <td> <fmt:formatNumber value="${minAmount}" pattern="#0.00"/></td>
                            <td> <fmt:formatNumber value="${maxAmount}" pattern="#0.00"/></td>
                            <td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td>
                                <a href="amountLimit_toEditAmountLimit.action?id=${id}" title="修改" target="dialog" style="color:blue">修改</a>
                            </td>
                        </tr>
                    </s:iterator>
                </tbody>
            </table>
            <!-- 分页条 -->
            <%@include file="/page/inc/pageBar.jsp" %>
        </div>

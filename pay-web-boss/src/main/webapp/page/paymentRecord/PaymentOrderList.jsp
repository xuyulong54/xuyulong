<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader" >
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="paymentRecord_listPaymentOrder.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent" >
			<tr>
				<td>
					创建时间：
					<input name="beginDate" value="${proMap.beginDate }" type="text"  id="beginDate" style="width: 77px;" class="date" readonly="readonly" /> 
					至 <input name="endDate" value="${proMap.endDate}" type="text" class="date" id="endDate" style="width: 77px;" readonly="readonly" />
				</td>
				<td>
					商户订单号：
					<input name="merchantOrderNo" type="text" value="${proMap.merchantOrderNo}" alt="精确搜索">
				</td>
				<td>
					<label>商户编号：</label>
					<input name="merchantNo" type="text" value="${proMap.merchantNo}" alt="精确搜索" />
				</td>
				<td>
					业务类型：
					<select name="bizType">
						<option value="">--请选择--</option>
						<c:forEach items="${tradeBizTypeEnum }" var="model">
							<option value="${model.value }"
							<c:if test="${proMap.bizType eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>
					订单状态：
					<select name="status">
						<option value="">--请选择--</option>
						<c:forEach items="${paymentRecordStatus }" var="model">
							<option value="${model.value }"
							<c:if test="${proMap.status eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					支付流水号：
					<input name="successTrxNo" type="text" value="${proMap.successTrxNo}" alt="精确搜索" />
				</td>
				<td>
					<label>支付方式：</label>
					<select name="paymentType">
						<option value="">--请选择--</option>
						<c:forEach items="${tradePaymentTypeEnum }" var="model">
							<option value="${model.value }"
							<c:if test="${proMap.paymentType eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td colspan="2">
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
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
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<th>序号</th>
				<th width="80">创建时间</th>
				<th>收款方</th>
				<th>付款方</th>
				<th>商户订单号</th>
				<th>订单金额</th>
				<th>商品名称</th>
				<th>订单来源</th>
				<th>订单状态</th>
				<th>支付成功时间</th>
				<th>支付流水号</th>
				<th>支付方式</th>
				<th>收货人手机号</th>
				<th width="30">操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						${merchantNo }<br/>
						${merchantName }
					</td>
					<td>
						${payerUserNo }<br/>
						${payerName }
					</td>
					<td>
						${merchantOrderNo }
					</td>
					<td align="right">
						<fmt:formatNumber value="${orderAmount }" type="currency" />
					</td>
					<td>
						${productName }
					</td>
					<td>
						<c:forEach items="${orderFromTypeEnum }" var="v">
							<c:if test="${orderFrom eq v.value }">${v.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${paymentRecordStatus }" var="v">
							<c:if test="${status eq v.value }">${v.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<fmt:formatDate value="${paySuccessTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						${successTrxNo }
					</td>
					<td>
						<c:forEach items="${tradeBizTypeEnum }" var="v">
							<c:if test="${bizType eq v.value }">${v.desc}</c:if>
						</c:forEach>
						<br/>
						<c:forEach items="${tradePaymentTypeEnum }" var="v">
							<c:if test="${paymentType eq v.value }">${v.desc}</c:if>
						</c:forEach>
						<br/>
						${payWayCode }
					</td>
					<td>
						${consigneeMobile }
					</td>
					<td>
						<z:permission value="merchant:paymentorder:view">
				 			<a href="paymentRecord_viewPaymentOrderUI.action?merchantOrderNo=${merchantOrderNo}&merchantNo=${merchantNo}&view=yes" title="查看" target="dialog" rel="input"   style="color:blue" >查看</a>
				 		</z:permission>
					 </td>
				 	</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script>
	$(document).ready(function(){
			$("#sreachKeys").bind("keyup", function(event){if (event.keyCode==13) searchDatas();});
			$("#sreachNames").bind("keyup", function(event){if (event.keyCode==13) searchDatas();});
	});
	
	function searchDatas(){
		$("#pagerForm").submit();
	}
	
	// 清空表单
	function clearFormPaymentRecord(){
		$(':input',"form[action='paymentRecord_listPaymentRecord.action']")
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected');  
	}
</script>
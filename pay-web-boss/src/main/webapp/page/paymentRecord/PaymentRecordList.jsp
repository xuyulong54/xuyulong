<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="paymentRecord_listPaymentRecord.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>商户订单号：</td>
					<td align="left"><input name="merchantOrderNo" type="text" value="${proMap.merchantOrderNo}" alt="精确搜索"></td>
					<td>商户编号：</td>
					<td align="left"><input name="merchantNo" type="text" value="${proMap.merchantNo}" alt="精确搜索" />
					</td>
					<td>交易状态：</td>
					<td align="left"><select name="status">
							<option value="">--请选择--</option>
							<c:forEach items="${paymentRecordStatus }" var="model">
								<option value="${model.value }" <c:if test="${proMap.status eq model.value}">selected="selected"</c:if>>${model.desc }</option>
							</c:forEach>
					</select>
					</td>
					<td>交易流水号：</td>
					<td align="left"><input name="trxNo" type="text" value="${proMap.trxNo}" alt="精确搜索" />
					</td>
					</td>
					
					</td>
				</tr>
				<tr>
					<td>银行订单号：</td>
					<td align="left"><input name="bankOrderNo" type="text" value="${proMap.bankOrderNo}" alt="精确搜索"></td>
					<td>银行流水号：</td>
					<td align="left"><input name="bankTrxNo" type="text" value="${proMap.bankTrxNo}" alt="精确搜索"></td>
					<td>业务类型：</td>
					<td align="left"><select name="bizType">
							<option value="">--请选择--</option>
							<c:forEach items="${tradeBizTypeList }" var="v">
								<option value="${v.value }" <c:if test="${proMap.bizType eq v.value }">selected="selected"</c:if>>${v.desc}</option>
							</c:forEach>
					</select></td>
					<td>支付方式：</td>
					<td align="left"><select name="paymentType">
							<option value="">--请选择--</option>
							<c:forEach items="${tradePaymentTypeList }" var="v">
								<option value="${v.value }" <c:if test="${proMap.paymentType eq v.value }">selected="selected"</c:if>>${v.desc }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>创建时间：</td>
					<td align="left"><input name="beginDate" value="${proMap.beginDate }" type="text" id="beginDate" style="width: 77px;" class="date" readonly="readonly" /> 至<input name="endDate" value="${proMap.endDate}" type="text" class="date" id="endDate" style="width: 77px;" readonly="readonly" /> 
					<td>银行渠道编号：</td>
					<td align="left"><input name="payInterfaceCode" type="text" value="${proMap.payInterfaceCode}" alt="精确搜索"></td>
					<td colspan="2">
					<div class="subBar">
							<ul>
								<li><div class="buttonActive">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="163">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>交易流水号</th>
				<th>银行订单号/流水号</th>
				<th>订单金额</th>
				<th>收款方</th>
				<th>支付方式</th>
				<th>交易状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${merchantOrderNo}</td>
					<td>${trxNo}</td>
					<td>${bankOrderNo}<br/>${bankTrxNo}</td>
					<td><fmt:formatNumber value="${orderAmount}" pattern="#0.00" />
					<td>${merchantNo}<br />${merchantName }</td>
					<td><c:forEach items="${tradeBizTypeList }" var="v">
							<c:if test="${bizType eq v.value }">${v.desc }</c:if>
						</c:forEach><br /> <c:forEach items="${tradePaymentTypeList }" var="v">
							<c:if test="${paymentType eq v.value }">${v.desc }</c:if>
						</c:forEach><br />${payWayName} (${payInterfaceCode })</td>
					
					<td><c:forEach items="${paymentRecordStatus }" var="v">
							<c:if test="${status eq v.value }">${v.desc}</c:if>
						</c:forEach></td>
					</td>
					<td><z:permission value="merchant:paymentrecord:view">
							<a href="paymentRecord_viewPaymentRecordUI.action?trxNo=${trxNo}&view=yes" title="查看" target="dialog" rel="input" style="color:blue">查看</a>
						</z:permission> <!--  
					 		<z:permission value="merchant:paymentrecord:sendmq">
					 			<c:if test="${status==100 }"><a href="notifyRecord_merchantSenderMQ.action?id=${merchantId}&trxno=${trxNo}" target="ajaxTodo" style="color:blue" >补发</a></c:if>
					 		</z:permission>
					 		 --></td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script>
	$(document).ready(function() {
		$("#sreachKeys").bind("keyup", function(event) {
			if (event.keyCode == 13)
				searchDatas();
		});
		$("#sreachNames").bind("keyup", function(event) {
			if (event.keyCode == 13)
				searchDatas();
		});
	});

	function searchDatas() {
		$("#pagerForm").submit();
	}

	// 清空表单
	function clearFormPaymentRecord() {
		$(':input', "form[action='paymentRecord_listPaymentRecord.action']")
				.not(':button, :submit, :reset, :hidden').val('').removeAttr(
						'checked').removeAttr('selected');
	}
</script>
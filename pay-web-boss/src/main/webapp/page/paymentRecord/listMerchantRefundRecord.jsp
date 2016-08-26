
<%
	//退款 @author laich
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="merchantRefund_listMerchantRefundRecode.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td class="lf">创建时间：</td>
					<td align="left"><input name="beginDate"
						value="${proMap.beginDate}" dateFmt="yyyy-MM-dd" type="text"
						id="beginDateMerchantRefundRecord" readonly="readonly"
						style="width: 77px;" class="date" /> 至 <input name="endDate"
						value="${proMap.endDate}" dateFmt="yyyy-MM-dd" type="text"
						class="date" readonly="readonly" id="endDateMerchantRefundRecord"
						style="width: 77px;" /> <!-- 					<a href="#" id="a_Date_1" style="color:blue;" onclick="selectDateMerchantRefundRecord('toDay',this)">今天</a>
					&nbsp;<a href="#" id="a_Date_3" style="color:blue;" onclick="selectDateMerchantRefundRecord('currentMonth',this)" class="Fcurrent">本月</a>
					&nbsp;<a href="#" id="a_Date_2" style="color:blue;" onclick="selectDateMerchantRefundRecord('lastMonth',this)">上月</a>
					&nbsp;<a href="#" id="clear" style="color:blue;" onclick="clearFormMerchantRefundRecord()">清空条件</a> -->
					</td>
					<td class="lf">商户订单号：</td>
					<td class="rt"><input name="orgMerchantOrderNo" type="text"
						value="${proMap.orgMerchantOrderNo}" alt="精确搜索" /></td>
					<td>退款状态：
					<select name="refundStatus" class="required">
						<option value="">请选择</option>
					<c:forEach items="${RefundStatusEnum }" var="item">
						<c:if test="${item.value!='102'}">
							<option value="${item.value }" <c:if test="${item.value eq proMap.refundStatus}">selected="selected"</c:if>>${item.desc }</option>
						</c:if>
					</c:forEach>
				</select>
					
					
					<td>交易流水号：<input name="orgTrxNo" type="text"
						value="${proMap.orgTrxNo}" alt="精确搜索" />
					</td>
				</tr>
				<tr>
					<td class="lf">金额范围：</td>
					<td class="rt"><input name="beginAmount" type="text"
						value="${proMap.beginAmount}" /> 至<input name="endAmount"
						type="text" value="${proMap.endAmount}" /></td>
					<td class="lf">商户退款订单号：</td>
					<td class="rt"><input name="refundOrderNo" type="text"
						value="${proMap.refundOrderNo }" alt="精确搜索" /></td>
					<td>
						<div class="subBar" style="float:right;">
							<ul>
								<li><div class="buttonActive">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
									</div>
								</li>
							</ul>
						</div></td>
				</tr>
			</table>
		</div>
	</form>
</div>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>交易流水号</th>
				<th>商户退款订单号</th>
				<th>处理时间</th>
				<th>退款状态</th>
				<th>商户名称</th>
				<th>退款金额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>${orgMerchantOrderNo}</td>
					<td>${orgTrxNo}</td>
					<td>${refundOrderNo}</td>
					<td><fmt:formatDate value="${refundCompleteTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					<c:forEach items="${RefundStatusEnum}" var="item">
							<c:if test="${item.value eq refundStatus }">${item.desc }</c:if>
						</c:forEach>
					</td>
					<td>${merchantName}</td>
					<td><fmt:formatNumber value="${refundAmount}" pattern="0.00" />
					</td>
					<td><a
						href="merchantRefund_viewMerchantRefundRecode.action?refundOrderNo=${refundOrderNo}&merchantNo=${merchantNo}"
						title="查看退款详细信息" width="820" height="600" target="dialog" style="color:blue">查看</a></td>
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

	function selectDateMerchantRefundRecord(str, obj) {
		var now = new Date(); //获取当前时间
		var year = now.getFullYear(); //获取当前日期的年份部分
		var month = now.getMonth() + 1 + ""; //获取当前日期的月份部分
		var day = now.getDate() + ""; //获取当前日期的天数部分
		if (day.length == 1) {
			day = "0" + day;
		}
		//获取当前月份包含的天数
		var nowdays = new Date(year, month, 0).getDate();
		var lastMonth = month - 1 + "";
		var lastMonthdays = new Date(year, lastMonth, 0).getDate() + "";
		if (month.length == 1) {
			month = "0" + month;
		}
		if (lastMonth.length == 1) {
			lastMonth = "0" + lastMonth;
		}
		if (lastMonthdays.length == 1) {
			lastMonthdays = "0" + lastMonthdays;
		}
		var startTime = "";
		var endTime = "";
		switch (str) {
		case "toDay":
			startTime = year + "-" + month + "-" + day;
			endTime = year + "-" + month + "-" + day;
			break;
		case "lastMonth":
			if (lastMonth == 0) {
				lastMonth = 12;
				year = year - 1;
			}
			startTime = year + "-" + lastMonth + "-01";
			endTime = year + "-" + lastMonth + "-" + lastMonthdays;
			break;
		case "currentMonth":
			startTime = year + "-" + month + "-01";
			endTime = year + "-" + month + "-" + nowdays;
			break;
		case "lastYear":
			startTime = (year - 1) + "-01-01";
			endTime = (year - 1) + "-12-31";
			break;
		case "currentYear":
			startTime = year + "-01-01";
			endTime = year + "-12-31";
			break;
		}
		$("#beginDateMerchantRefundRecord").val(startTime);
		$("#endDateMerchantRefundRecord").val(endTime);

	}

	// 清空表单
	function clearFormMerchantRefundRecord() {
		$(':input',
				"form[action='merchantRefund_listMerchantRefundRecode.action']")
				.not(':button, :submit, :reset, :hidden').val('').removeAttr(
						'checked').removeAttr('selected');
	}
</script>
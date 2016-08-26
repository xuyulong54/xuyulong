<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="memberRecord_listMemberRecord.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>创建时间：</td>
					<td>
						<input name="beginDate" readonly="readonly" value="${proMap.beginDate }" id="beginDateMemberRecordList" class="date" type="text" style="width: 75px;" />至
						<input name="endDate" readonly="readonly" type="text" value="${proMap.endDate}" id="endDateMemberRecordList" class="date" type="text" style="width: 75px;" />
					</td>
					<td>
					交易流水号：</td>
					<td>
						<input name="trxNo" type="text" value="${proMap.trxNo}" alt="精确搜索" size="27" />
					</td>
					<td>
					付款方名称：</td>
					<td><input name="payerName" type="text" value="${proMap.payerName}" size="27" alt="精确搜索"/>
					</td>
				</tr>
				<tr>
					<td>
					订单状态：</td>
					<td>
						  <select name="status">
							<option value="">请选择</option>
							<c:forEach items="${paymentRecordStatus }" var="v">
								<option value="${v.value }"
									<c:if test="${proMap.status eq v.value }">selected="selected"</c:if>>${v.desc}</option>
							</c:forEach>
						</select>
					</td>
					<td>
					收款方名称：</td>
					<td>
					<input name="merchantName" type="text" value="${proMap.merchantName}" size="27" alt="精确搜索"/>
					</td>
					<td>
						<div class="subBar" style="float:left;">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>商户订单号</th>
				<th>交易流水号</th>
				<th>收款方名称</th>
				<th>付款方名称</th>
				<th>订单状态</th>
				<th>交易金额</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
				<td> <fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${merchantOrderNo}</td>
				<td>${trxNo}</td>
				<td>${merchantName}</td>
				<td>${payerName}</td>
				<td>
					<c:forEach items="${paymentRecordStatus }" var="v">
						<c:if test="${status eq v.value }">${v.desc}</c:if>
					</c:forEach>
				</td> 
				<td>
				<c:if test="${trxType==6001 || trxType==6004||trxType==6005 }">-</c:if>
				<c:if test="${trxType==3001 || trxType == 4001|| trxType == 4002|| trxType == 4003|| trxType == 4004|| trxType == 4005 }">+</c:if>
				<fmt:formatNumber value="${orderAmount}"  pattern="0.00"></fmt:formatNumber></td>
					<td>
					<z:permission value="member:paymentrecord:view">
						<a href="memberRecord_viewMemberRecordUI.action?trxNo=${trxNo}&view=yes"
							title="查看" target="dialog" rel="input" style="color:blue">查看</a>
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
	
	

	function selectDateMemberRecordList(str, obj) {
	    var now = new Date(); //获取当前时间
	    var year = now.getFullYear(); //获取当前日期的年份部分
	    var month = now.getMonth() + 1 +""; //获取当前日期的月份部分
	    var day = now.getDate() +""; //获取当前日期的天数部分
	    if(day.length==1){
	    	day="0"+day;
	    }
	    //获取当前月份包含的天数
	    var nowdays = new Date(year, month, 0).getDate();
	    var lastMonth = month - 1+"";
	    var lastMonthdays = new Date(year, lastMonth, 0).getDate() +"";
	    if(month.length==1){
	    	month="0"+month;
	    }
	    if(lastMonth.length==1){
	    	lastMonth="0"+lastMonth;
	    }
	    if(lastMonthdays.length==1){
	    	lastMonthdays="0"+lastMonthdays;
	    }
	    var startTime = "";
	    var endTime = "";
	    switch (str) {
	        case "toDay":
	        	startTime = year + "-" + month + "-" + day;
	        	endTime = year + "-" + month + "-" + day;
	            break;
	        case "lastMonth":
	            if (lastMonth == 0) { lastMonth = 12; year = year - 1; }
	            startTime = year + "-" + lastMonth + "-01";
	            endTime = year + "-" + lastMonth + "-" + lastMonthdays;
	            break;
	        case "currentMonth":
	            startTime = year + "-" + month + "-01";
	            //endTime = year + "-" + month + "-" + day;
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
	    $("#beginDateMemberRecordList").val(startTime);
	    $("#endDateMemberRecordList").val(endTime);

	}

	// 清空表单
	function clearFormMemberRecordList(){
		$(':input',"form[action='memberRecord_listMemberRecord.action']")  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected');  
	}
</script>
<%
//提现 @author laich
 %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" class = "exp" onsubmit="return navTabSearch(this);"
		action="memberRecord_listMemberWithdrawRecord.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>提现时间： 
						<input type="text" name="startDate" value="${fn:substring(beginTime,0,10)}" id="beginDateWithdrawRecord" class="date" size="10" readonly="readonly" /> 至 
						<input type="text" name="endDate" value="${fn:substring(endTime,0,10)}" id="endDateWithdrawRecord" class="date" size="10" readonly="readonly" />
						<a href="#" id="a_Date_1" style="color:blue;" onclick="selectDateWithdrawRecord('toDay',this)">今天</a>
					&nbsp;<a href="#" id="a_Date_3" style="color:blue;" onclick="selectDateWithdrawRecord('currentMonth',this)" class="Fcurrent">本月</a>
					&nbsp;<a href="#" id="a_Date_2" style="color:blue;" onclick="selectDateWithdrawRecord('lastMonth',this)">上月</a>
					&nbsp;<a href="#" id="clear" style="color:blue;" onclick="clearFormWithdrawRecord()">清空条件</a>
					</td>
				</tr>
				<tr>
					<td >
						收款人名称：<input type="text" name="bankAccountName" value="${bankAccountName}" size="15" alt="模糊搜索" />
					</td>
					<td>
						提现状态：
						<select name="settStatus">
							<option value="">--请选择--</option>
							<c:forEach items="${SettRecordStatusEnum }" var="enums" >
							<option value="${enums.value }" <c:if test="${settStatus eq enums.value }">selected="selected"</c:if> >${enums.desc }</option>
							</c:forEach>
						</select>
					</td>
					<td colspan="2">
						<div class="subBar" style="float: right;">
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>收款人</th>
				<th>收款银行名称</th>
				<th>收款账户开户行名称</th>
				<th>收款银行银行卡号</th>
				<th>提现金额</th>
				<th>提现时间</th>
				<th>提现批次号</th>
				<th>提现状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid" rel="${Id}">
					<td>${st.index+1}</td>
					<td>${bankAccountName}</td>
					<td>
					<c:forEach items="${bankList }" var="bankList" >
					<c:if test="${bankCode eq bankList.bankCode }">${bankList.typeName }</c:if>
					</c:forEach>
					</td>
					<td>${bankAccountAddress}</td>
					<td>${bankAccountNo}</td>
					<td>${remitAmount}</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>${batchNo}</td>
					<td>
					<c:forEach items="${SettRecordStatusEnum }" var="enums" >
						<c:if test="${settStatus eq enums.value }">${enums.desc }</c:if>					
					</c:forEach>
					</td>
					<td>
						<z:permission value="member:withdrawrecord:view">
							<a href="memberRecord_reviewWithDrawRecord.action?batchNo=${batchNo}&accountNo=${accountNo}" title="查看详情" target="dialog" style="color:blue">查看</a>&nbsp;
						<c:if test="${settStatus eq remitFail}">
								<a href="javascript:resent('${batchNo }','${accountNo }');" title="重新提现" style="color:blue">重新提现</a>
								<a href="javascript:confirmFail('${batchNo }','${accountNo }');" title="确认提现失败,提现记录作废" style="color:blue">确认提现失败</a>
						</c:if> 
						</z:permission>
					</td>		
				</tr>
			</s:iterator>
		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>

<script type="text/javascript">
	function resent(batchNo, accountNo) {
		alertMsg.confirm("是否重新发起提现？", {
			okCall : function() {
				$.post("SettRecord_resendSett.action", {
					batchNo : batchNo,
					accountNo : accountNo
				}, function() {
					DWZ.ajaxDone;
					$("form[id='pagerForm'][class = 'exp']").submit();
				}, "json");
			}
		});
	}

	function confirmFail(batchNo, accountNo) {
		alertMsg.confirm("确认提现失败,提现记录作废？", {
			okCall : function() {
				$.post("SettRecord_confirmFail.action", {
					batchNo : batchNo,
					accountNo : accountNo
				}, function() {
					DWZ.ajaxDone;
					$("form[id='pagerForm'][class = 'exp']").submit();
				}, "json");
			}
		});
	}
	
	
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
	
	function selectDateWithdrawRecord(str, obj) {
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
	    $("#beginDateWithdrawRecord").val(startTime);
	    $("#endDateWithdrawRecord").val(endTime);

	}

	// 清空表单
	function clearFormWithdrawRecord(){
		$(':input',"form[action='memberRecord_listMemberWithdrawRecord.action']")  
		 .not(':button, :submit, :reset, :hidden')  
		 .val('')  
		 .removeAttr('checked')  
		 .removeAttr('selected');  
	}
</script>
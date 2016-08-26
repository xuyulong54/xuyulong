<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="pmsOperatorLog_listPmsOperatorLog.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>操作时间：</td>
				<td>
					<input type="text" name="beginDate" value="${beginDate}" title="时间区间前后不能大于6天" id="beginDateOperatorLogList" class="date" size="10" readonly="readonly" />-
					<input type="text" name="endDate" value="${endDate}" title="时间区间前后不能大于6天" id="endDateOperatorLogList" class="date" size="10" readonly="readonly" />
						<a href="#" id="a_Date_1" style="color:blue;" onclick="selectDateOperatorLogList('toDay',this)">今天</a>
				&nbsp;	<a href="#" id="clear" style="color:blue;" onclick="clearFormOperatorLogList()">清空条件</a>
				</td>
				<td>
					操作员：<input type="text" name="operatorName" value="${operatorName}" size="15" alt="精确查询"  />
				</td>
				<td>
					操作状态：
				</td>
				<td>
					<select name="status">
						<option value="">-请选择-</option>
						<c:forEach items="${OperatorLogStatusEnumList}" var="logStatusEnum">
							<option value="${logStatusEnum.value}" <c:if test="${status ne null and status eq logStatusEnum.value}">selected="selected"</c:if>>${logStatusEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td>操作类型：</td>
				<td>
					<select name="operateType" id="operateType">
						<option value="">-请选择-</option>
						<c:forEach items="${OperatorLogTypeEnumList}" var="logTypeEnum">
							<option value="${logTypeEnum.value}" <c:if test="${operateType ne null and operateType eq logTypeEnum.value}">selected="selected"</c:if>>${logTypeEnum.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					IP地址：<input type="text" name="ip" value="${ip}" size="15" alt="精确查询"  />
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>操作员</th>
				<th>操作类型</th>
				<th>操作状态</th>
				<th>IP地址</th>
				<th>操作内容</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
				    <td>
				    	<fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss"/>
				    </td>
					<td>${operatorName }</td>
					<td>
						<c:forEach items="${OperatorLogTypeEnumList}" var="logTypeEnum">
							<c:if test="${operateType eq logTypeEnum.value}">${logTypeEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${OperatorLogStatusEnumList}" var="logStatusEnum">
							<c:if test="${status eq logStatusEnum.value}">${logStatusEnum.desc}</c:if>
						</c:forEach>
					</td>
					<td>${ip }</td>
					<td>
						<c:choose>
							<c:when test="${fn:length(content) > 50}">
								${fn:substring(content, 0, 50) }...
							</c:when>
							<c:otherwise>${content }</c:otherwise>
						</c:choose>
					</td>
					<td>
						<z:permission value="pms:operatorlog:view">
							[<a class="add" href="pmsOperatorLog_viewById.action?id=${id }" target="dialog" rel="input" title="查看操作员操作日志详情" style="color:blue"><span>查看详情</span></a>]
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
function selectDateOperatorLogList(str, obj) {
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
    $("#beginDateOperatorLogList").val(startTime);
    $("#endDateOperatorLogList").val(endTime);

}

// 清空表单
function clearFormOperatorLogList(){
	$(':input',"form[action='pmsOperatorLog_listPmsOperatorLog.action']")
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}

</script>

    
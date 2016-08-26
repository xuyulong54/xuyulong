<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="memberInfo_listMemberInfo.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					<span>会员编号：</span>
					<input type="text" id="memberNo" name="memberNo" value="${memberNo}" size="27" alt="精确搜索"/>
				</td>
				<td>
					<span>真实姓名：</span>
					<input type="text" id="realName" name="realName" value="${realName}" size="20"  alt="模糊搜索"/>
				</td>
				<td>
					<span>身份证号：</span>
					<input type="text" id="cardNo" name="cardNo" value="${cardNo}" size="20" alt="精确搜索"/>
				</td>
			</tr>
			<tr>
				<td>
					<span>注册时间：</span>
					<input type="text" name="startDate" value="${startDate }" id="beginDateMemberList" class="date"  size="10" readonly="true" />-
					<input type="text" name="endDate" value="${endDate }" id="endDateMemberList" class="date" size="10" readonly="true" />
<!-- 					<a href="#" id="a_Date_1" style="color:blue;" onclick="selectDateMemberList('toDay',this)">今天</a>
				&nbsp;	<a href="#" id="a_Date_3" style="color:blue;" onclick="selectDateMemberList('currentMonth',this)"class="Fcurrent">本月</a>
				&nbsp;	<a href="#" id="a_Date_2" style="color:blue;" onclick="selectDateMemberList('lastMonth',this)">上月</a>
				&nbsp;	<a href="#" id="clear" style="color:blue;" onclick="clearFormMemberList()">清空条件</a> -->
				</td>
				<td>
					<span>状态：</span>
					<select name="status" id="status">
						<option value="">--请选择--</option>
						<c:forEach items="${memberStatusList }" var="model">
							<option value="${model.value }"
							<c:if test="${status eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<div class="subBar" >
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="133">
		<thead>
			<tr>
				<th>序号</th>
				<th>会员编号</th>
				<th>真实姓名</th>
				<th>身份证号</th>
				<th>注册时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<td>${memberNo}</td>
				    <td>${realName}</td>
					<td>${cardNo}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:forEach items="${memberStatusList }" var="model">
							<c:if test="${status eq model.value}">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<z:permission value="member:info:view">
							<a href="memberInfo_viewMemberInfo.action?id=${id}" title="查看会员详细信息" target="dialog"   style="color:blue">查看</a>
						</z:permission>
						<c:if test="${status != 103 }">
							<z:permission value="member:info:changestatus">
								<c:if test="${status == 101 }"> | <a href="memberInfo_toChangeMemberStatusUI.action?status=100&id=${id }" title="修改会员状态" target="dialog"  style="color:blue">激活</a></c:if>
								<c:if test="${status == 100 }"> | <a href="memberInfo_toChangeMemberStatusUI.action?status=101&id=${id }" title="修改会员状态" target="dialog" rel="memberStop" style="color:blue">冻结</a></c:if>
							</z:permission>
							<z:permission value="member:info:resetpassword">
								 <c:if test="${status == 100 }"> | <a href="memberInfo_resetPassword.action?id=${id }" title="确定要重置密码吗？" target="ajaxTodo"  style="color:blue">重置密码</a></c:if>
							</z:permission>
						</c:if>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>


<script>
function selectDateMemberList(str, obj) {
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
    $("#beginDateMemberList").val(startTime);
    $("#endDateMemberList").val(endTime);

}

// 清空表单
function clearFormMemberList(){
	$(':input',"form[action='memberInfo_listMemberInfo.action']")
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}

</script>
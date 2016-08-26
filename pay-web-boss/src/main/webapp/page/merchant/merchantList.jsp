<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript" src="js/common.js"></script>
<div class="pageHeader">
	<form id="pagerForm" name="listMerchant" onsubmit="return navTabSearch(this);" action="merchant_listMerchant.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					<label>商户编号：</label>
					<input type="text" name="merchantNo" value="${merchantNo }" size="20" alt="精确搜索"/>
				</td>
				<td>
					<label>商户全称：</label>
					<input type="text" name="fullName" value="${fullName }" size="28" alt="模糊搜索" />
				</td>
				<td>
					<label>商户类型：</label>
					<select name="merchantType" id="merchantType" >
						<option value="">请选择</option>
						<c:forEach items="${merchantTypeList }" var="model">
							<option value="${model.value }" <c:if test="${merchantType eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select>
				</td>
				<!-- <td >
					到期提醒：
					<select name="remindType" id="remindType">
						<option value="">请选择</option>
						<option value="1" <c:if test="${remindType eq 1 }">selected="selected"</c:if> >合同到期</option>
						<option value="2" <c:if test="${remindType eq 2 }">selected="selected"</c:if> >身份证到期</option>
						<option value="3" <c:if test="${remindType eq 3 }">selected="selected"</c:if> >营业执照到期</option>
					</select>
				</td> -->
			</tr>
			<tr>
				<td>
					<label>商户状态：</label>
					<select name="status" id="status">
						<option value="">请选择</option>
						<c:forEach items="${merchantStatusList }" var="model">
								<option value="${model.value }" <c:if test="${status eq model.value }">selected="selected"</c:if>>${model.desc}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<label>注册时间：</label>
					<input type="text" name="beginDate" value="${beginDate}" id="beginDateMerchantList" class="date" size="10" readonly="readonly" />-
					<input type="text" name="endDate" value="${endDate}" id="endDateMerchantList" class="date" size="10" readonly="readonly" />
				</td>
 				<td>
						<a href="#" id="a_Date_1" style="color:blue;" onclick="selectDateMerchantList('toDay',this)">今天</a>
				&nbsp;	<a href="#" id="a_Date_3" style="color:blue;" onclick="selectDateMerchantList('currentMonth',this)"class="Fcurrent">本月</a>
				&nbsp;	<a href="#" id="a_Date_2" style="color:blue;" onclick="selectDateMerchantList('lastMonth',this)">上月</a>
				&nbsp;	<a href="#" id="clear" style="color:blue;" onclick="clearFormMerchantList()">清空条件</a>
				</td>
				<td>
					<div class="subBar" style="float: right;">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							<!-- 
							<li><div class="buttonActive2"><div class="buttonContent"><a href="merchant_doBatchAddMerchantInfo.action" target="ajaxTodo">添加商户</a></div></div></li>
							 -->
							<li>&nbsp;</li>
							<li>&nbsp;</li>
							<li>&nbsp;</li>
							<li>
								<z:permission value="boss:contract:download">
									<a title="文件管理" href="contract_listContract.action" style="color:blue;" target="navTab">下载文件</a>
								</z:permission>
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
			<z:permission value="merchant:info:add">
				<li><a class="add" href="merchant_addMerchantUI.action" target="navTab" rel="addMerchant" title="添加商户"><span>添加</span></a></li>
			</z:permission>
		</ul>
	</div>
	
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160" >
		<thead>
			<tr>
				<th>序号</th>
				<th>机构编号</th>
				<th>商户编号</th>
				<th>商户类型</th>
				<th>商户全称</th>
				<th>商户状态</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>${parentUserNo}</td>
					<td>${merchantNo}</td>
					<td>
						<c:forEach items="${merchantTypeList }" var="model">
							<c:if test="${model.value eq merchantType }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>${fullName}</td>
					<td>
						<c:forEach items="${merchantStatusList }" var="model">
							<c:if test="${model.value eq status }">${model.desc }</c:if>
						</c:forEach>
					</td>
					<td>
						<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						<z:permission value="merchant:info:view">
							<a href="merchant_viewMerchant.action?id=${id}" title="查看商户详细信息" target="navTab" rel="addMerchant" style="color:blue">查看</a>
						</z:permission>
						<!-- 激活 -->
						<c:if test="${status == MerchantStatusEnums.ACTIVE.value || status == MerchantStatusEnums.CREATED.value || status == MerchantStatusEnums.NOPASS.value}">
							<z:permission value="merchant:info:edit">
								| <a href="merchant_editMerchantUI.action?id=${id}" title="修改商户的详细信息" target="navTab" rel="addMerchant" style="color:blue">修改</a>
							</z:permission>
						</c:if>
						<c:if test="${status == MerchantStatusEnums.ACTIVE.value}">	
							<z:permission value="merchant:info:changestatus">
								| <a href="userAuditRecordStatus_toChangeMerchantStatusUI.action?status=101&merchantNo=${merchantNo }" 
									title="修改商户账户状态" target="dialog" style="color:blue" width="450" height="350">冻结</a> 
							</z:permission>
							
							<z:permission value="merchant:info:resetpassword">
								| <a href="merchant_resetPassword.action?merchantNo=${merchantNo}" title="确定要重置登录密码吗？" target="ajaxTodo" style="color:blue">重置密码</a>
							</z:permission>
						</c:if>
						<!-- 冻结 -->
						<c:if test="${status == MerchantStatusEnums.INACTIVE.value}">
							<z:permission value="merchant:info:changestatus">
								| <a href="userAuditRecordStatus_toChangeMerchantStatusUI.action?status=100&merchantNo=${merchantNo }" title="激活商户账户状态" target="dialog"  style="color:blue">激活</a>
							</z:permission>
						</c:if>
						 | <a href="merchantNode_merchantNodeList.action?id=${id}&userNo=${merchantNo}" rel="merchantNodeList" title="设置计费节点" target="navTab" style="color:blue">设置计费节点</a>
						 | <a href="tradeLimitRouter_addTradeLimitRouterUI.action?merchantNo=${merchantNo}" rel="tradeLimitRouter" title="关联限制包" target="dialog" style="color:blue">关联限制包</a>
						 | <a href="merchantCustomPayInterface_addMerchantCustomPayInterfaceUI.action?merchantNo=${merchantNo}" rel="merchantPayInterface" title="设置支付接口路由" target="dialog" style="color:blue">限制支付接口</a>
						<z:permission value="payrule:payrule:merchantBind">
							| <a href="payRule_bindPayRuleUI.action?merchantNo=${merchantNo }" title="设置支付规则" rel="setRuleInfo" target="dialog" style="color:blue">设置支付规则</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
    <!-- 分页 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>


<script>
function selectDateMerchantList(str, obj) {
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
    $("#beginDateMerchantList").val(startTime);
    $("#endDateMerchantList").val(endTime);

}

// 清空表单
function clearFormMerchantList(){
	$(':input',"form[action='merchant_listMerchant.action']")  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}

</script>

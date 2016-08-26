<%-- <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name ="remitProcessUnremitForm" onsubmit="return navTabSearch(this);" action="remitProcessUnremit_remitProcessUnremitList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td> 
						<label>审核时间：</label> 
						<input name="beginDate1" id="beginDate1" value="${beginDate1 }" dateFmt="yyyy-MM-dd" type="text"  style="width: 77px;" class="date"  /> 
						至<input name="endDate1"  value="${endDate1 }" dateFmt="yyyy-MM-dd" type="text" class="date" id="endDate1" style="width: 77px;" />
					</td>
					<td>
						<label>打款请求号：</label>
						<input type="text" name="requestNo" id="requestNo" value="${requestNo }"  alt="精确搜索"/>
					</td>
					<td>
						<label>打款渠道：</label>
						<select name="channelCode" id="channelCode" >
						<option value="">请选择</option>
						<c:forEach items="${remitChannelList }" var="v">
						<option value="${v.channelCode}" <c:if test="${channelCode==v.channelCode }">selected="selected"</c:if> >${v.channelName }</option>
						</c:forEach>
						</select>
					</td>
					<td>
						<label>用户类型：</label>
						<select name="userType" id="userType" >
						<option value="">请选择</option>
						<option value="${UserTypeEnum.MERCHANT.value }" <c:if test="${userType eq UserTypeEnum.MERCHANT.value}" >selected="selected" </c:if>>商户</option>
						<option value="${UserTypeEnum.POSAGENT.value}" <c:if test="${userType eq UserTypeEnum.POSAGENT.value}" >selected="selected" </c:if> >pos代理商</option>
						</select>
					</td>
					</tr>
					<tr>
					<td>
						<label>收款户名：</label>
						<input type="text" name="accountName" id="accountName"  value="${accountName }"  alt="模糊搜索"/>
					</td>
					<td>
						<label>收款账号：</label>
						<input type="text" name="bankChannelNo" id="bankChannelNo" value="${bankChannelNo }" alt="模糊搜索"/>
					</td>
					<td>
						<label>收款发卡行：</label>
						<input type="text" name="bankName" id="bankName" value="${bankName}"  alt="模糊搜索"/>
					</td>
					<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive">
									<div class="buttonContent">
										<button type="submit">查询</button>
									</div>
								</div>
							</li>
							<z:permission value="boss:remitProcessUnremit:expor">
								<li><div class="buttonActive"><div class="buttonContent"><button type="button" id="exportButton" onclick="expleExcel('all');">导出</button></div></div></li>
							</z:permission>
						</ul>
					</div>
					</td>
					</tr>
			</table>
		</div>
	</form>
</div>
<div class="panelBar">
<table>
<tr>
	<td><label>&nbsp;&nbsp;打款渠道：</label>
		<select name="channelCodeEx" id="channelCodeEx" >
		<option value="">请选择</option>
		<c:forEach items="${remitChannelList }" var="v">
		<option value="${v.channelCode}" <c:if test="${channelCode==v.channelCode }">selected="selected"</c:if> >${v.channelName }</option>
		</c:forEach>
		</select></td>
	<td>
		<ul>
			<z:permission value="boss:remitProcessUnremit:expor">
			<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="expleExcel();">导出</button></div></div></li>
			</z:permission>
		</ul>
	</td>
	<td>
		<ul>
			<z:permission value="boss:remitProcessUnremit:expor">
				<li><div class="buttonActive"><div class="buttonContent"><button type="button" onclick="expleExcel('all');">全部导出</button></div></div></li>
			</z:permission>
		</ul>
	</td>
</tr>
</table>
</div>
<div class="pageContent">

	<div class="panelBar">
		<ul class="toolBar">
			<li>
				<span> 打款总笔数：<b style="color:red;">${totalCount }</b> 笔</span>
				<span> 打款总金额：<b style="color:red;"><fmt:formatNumber value="${countResultMap.totalAmount }" type="currency" /></b></span>
			</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="160">
		<thead>
			<tr>
				<!-- <th><input type="checkbox" id="allCheck" onclick="allCheck(this);"></th> -->
				<th>序号</th>
				<th width="80">审核时间</th>
				<th>审核批次号</th>
				<th>打款请求号</th>
				<th>收款人</th>
				<th align="right">收款金额</th>
				<th>打款渠道</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td><input type="checkbox"  name="sigCheck" id="${id}" value="id" class="ada-checkbox"></td>
				   	<td>${st.index+1 }</td>
				    <td><fmt:formatDate value="${confirmDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				    <td>${batchNo }</td>
				    <td>${requestNo}</td>
				    <td>
				    	${accountName }<br/>
				    	${accountNo }<br/>
				    	${bankName}(${bankChannelNo})
				    </td>
				    <td>
				    	<fmt:formatNumber value="${amount }" type="currency" />
				    </td>
				    <td>
				    <c:forEach items="${remitChannelList }" var="v">
				    <c:if test="${v.channelCode==channelCode}">${v.channelName}</c:if>
				    </c:forEach>
				    </td>
				    <td>
				    <z:permission value="boss:remitProcessUnremit:view">
				    	<a  href="remitProcess_remitProcessView.action?id=${id}" title="打款记录详情" target="dialog" rel="input" width="801" height="450" style="color:blue">查看</a>
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
//全选/反选
function allCheck(obj){
	$(":checkbox[name='sigCheck']").attr("checked",obj.checked);
}
//导出功能
function expleExcel(allEx){
	var beginDate1=$("#beginDate1").val();
	var endDate1=$("#endDate1").val();
	var	 requestNo=$("#requestNo").val();
	var channelCode=$("#channelCode").val();
//	var channelCodeEx=$("#channelCodeEx").val();
	var accountName=$("#accountName").val();
	var bankChannelNo= $("#bankChannelNo").val();
	var bankName=$("#bankName").val();
	var userType=$("#userType").val();
	
	if(channelCode==''){
		alertMsg.info("请选择打款渠道!");
		return false;
	}
	
	var params="";
	if(allEx !="all"){
		var str = "";
		 $("input[name='sigCheck']:checked").each(function () {
			str += $(this).attr("id");
			str += ",";
		});
		if(str == ''){
			alertMsg.info("请选择要导出的数据!");
			return false;
		}
		params="&ids="+str+"&channelCodeEx="+channelCodeEx;
	}else{
		params="&beginDate1="+beginDate1
		      +"&endDate1="+endDate1
		      +"&requestNo="+requestNo
		      +"&channelCode="+channelCode
//		      +"&channelCodeEx="+channelCodeEx
		      +"&accountName="+accountName
		      +"&bankChannelNo="+bankChannelNo
		      +"&bankName="+bankName
		      +"&userType="+userType;
	}
	
//	window.location.href="remitProcessUnremit_exportExcel.action?1=1"+params;
	$.post("remitProcessUnremit_remitProcessExist.action","1=1"+params,function(result){
		if(result.STATE=="FAIL"){
			alertMsg.error(result.MSG);
		}else{
			//window.open("remitProcessUnremit_exportExcel.action?1=1"+params);
			$("#exportButton").attr("disabled", true);
			window.location.href="remitProcessUnremit_exportExcel.action?1=1"+params;
			setTimeout(function () { 
				$("form[name='remitProcessUnremitForm']").submit();
		    }, 3000);
		}
	},"json");


}
</script>
 --%>
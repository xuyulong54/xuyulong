<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="settHolidayFrom" onsubmit="return navTabSearch(this);" action="SettHoliday_listSettHolidaySetting.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>节假日日期：
					<input type="text" name="holiday"  value="${fn:substring(holiday ,0,10)}"  class="date" size="25" readonly="true" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
									<z:permission value="sett:holidaysetting:view">
										<div class="buttonContent">
											<button type="submit">查询</button>
										</div>
										</z:permission>
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
		<z:permission value="sett:holidaysetting:add">
			<ul class="toolBar">
				<li>
					<a class="add" href="SettHoliday_addSettHolidayUI.action" target="dialog" rel="input" title="添加节假日"><span>添加节假日</span></a>
				</li>
			</ul>
		</z:permission>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>节假日</th>
				<th >描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${holiday }" pattern="yyyy-MM-dd"/></td>
					<td>${remark }</td>
					<td><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td>
					<z:permission value="sett:holidaysetting:delete">
						<a href="javascript:deleteSettHoliday('${id}')" title="删除" style="color:blue">删除</a>
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
function deleteSettHoliday(id){
	alertMsg.confirm("是否确认删除？",{
		okCall:function(){
			$.post("SettHoliday_deleteSettHoliday.action",{id:id},function(res){
				$("form[name='settHolidayFrom']").submit();
			},"json"); 
		}
	});
	
}
</script>

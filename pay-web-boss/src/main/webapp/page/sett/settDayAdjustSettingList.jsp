<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form name="settDayAdjustForm" onsubmit="return navTabSearch(this);" action="SettDayAdjust_listSettDayAdjustSetting.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>结算日原始日期：
					<input type="text" name="oldDate"  value="${fn:substring(effectiveDateStart,0,10)}"  class="date" size="12" readonly="true" />
					</td>
					<td>结算日调整日期：
					<input type="text" name="newDate"  value="${fn:substring(effectiveDateStart,0,10)}"  class="date" size="12" readonly="true" />
					</td>
					<td>
						<div class="subBar">
							<ul>
								<li>
									<div class="buttonActive">
										<z:permission value="sett:dayadjustsetting:view">
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
	<z:permission value="sett:dayadjustsetting:add">
		<ul class="toolBar">
				<li>
					<a class="add" href="SettDayAdjust_addSettDayAdjustSettingUI.action" target="dialog" rel="input" title="添加结算日调整"><span>添加结算日调整</span></a>
				</li>
		</ul>
	</z:permission>	
</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>结算原始日</th>
				<th>结算调整日</th>
				<th >描述</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td><fmt:formatDate value="${oldDate }" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${newDate }" pattern="yyyy-MM-dd" /></td>
					<td>${remark }</td>
					<td><fmt:formatDate value="${createTime }" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					<z:permission value="sett:dayadjustsetting:delete">
						<a href="javascript:delSettDayAdjustSetting('${id }');" title="删除" style="color:blue" >删除</a>
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
function delSettDayAdjustSetting(id){
	alertMsg.confirm("是否确认删除？",{
		okCall:function(){
			$.post("SettDayAdjust_delSettDayAdjustSetting.action",{id:id},function(res){
				$("form[name='settDayAdjustForm']").submit();
			},"json"); 
		}
	});
	
}
</script>
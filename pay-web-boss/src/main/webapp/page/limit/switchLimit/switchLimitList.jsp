<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="switchLimit_listSwitchLimit.action" method="post"
		name="listSwtichLimit">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>名称：
					<input type="text" name="name" value="${name}" alt="模糊查询"/>
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
			<z:permission value="limit:switch:add">
				<li><a class="add" href="switchLimit_addSwitchLimitUI.action" target="dialog" rel="input">
					<span>添加</span></a>
				</li>
			</z:permission>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>名称</th>
				<th>描述</th>
				<th>修改时间</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>
						${name }
					</td>
					<td>
						${description }
					</td>					
					<td><s:date name="lastModifyTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<z:permission value="limit:switch:openFun">
							<a href="switchLimit_openBizFunction.action?switchLimitPackId=${id}" 
							target="dialog" width="900" height="500" style="color:blue">开通业务功能</a>
						</z:permission>
						
						<z:permission value="limit:switch:bind">
							| <a href="switchLimit_bindAllSwitchUI.action?switchLimitPackId=${id}" 
							target="dialog" width="900" height="500" style="color:blue">绑定限制</a>
						</z:permission>
						
						<z:permission value="limit:switch:edit">
							| <a href="switchLimit_editSwitchLimitUI.action?id=${id}" target="dialog" style="color:blue">修改</a>
	<%--						<a href="javascript:deleteSwitchLimit('${id}');" id="SwtichLimit" style="color:blue">删除</a>--%>
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
	function deleteSwtichLimit(id){
		if(confirm("你确定要删除吗？")){
			$.post("switchLimit_deleteSwitchLimit.action",{id:id},
				function(res){
					if(res.STATE=="SUCCESS"){
						alert("删除成功！");
						$("form[name='listSwitchLimit']").submit();
					}else{
						alert("删除失败！");
					}
			},"json");
		}
	}
</script>

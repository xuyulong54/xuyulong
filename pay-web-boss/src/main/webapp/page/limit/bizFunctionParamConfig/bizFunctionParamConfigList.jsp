<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="bizFunctionParamConfig_listBizFunctionParamConfig.action" method="post"
		name="listBizFunctionParamConfig">
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
				<li><a class="add" href="bizFunctionParamConfig_addBizFunctionParamConfigUI.action" target="dialog" rel="input">
					<span>添加</span></a>
				</li>
		</ul>
	</div>

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>开发限制包</th>
				<th>业务功能</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					</td>
					<td>
						<c:forEach var="switchLimitPacksList" items="${switchLimitPacksList }">
						<c:if test="${switchLimitPackId eq switchLimitPacksList.id }">${switchLimitPacksList.name }
						</c:if>
						</c:forEach>						
					</td>
					<td>
					<c:forEach var="trxTypeEnumList" items="${trxTypeEnumList }">
						<c:if test="${bizFunction eq trxTypeEnumList.value }">${trxTypeEnumList.desc }
						</c:if>
						</c:forEach>											
					</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						${StatusDesc }
					</td>	
					<td>
						<a href="bizFunctionParamConfig_editBizFunctionParamConfigUI.action?id=${id}" target="dialog" style="color:blue">修改</a>
						<a href="javascript:deleteBizFunctionParamConfig('${id}');" id="bizFunctionParamConfig" style="color:blue">删除</a>
					</td>
				</tr>
			</s:iterator>

		</tbody>
	</table>
	<!-- 分页条 -->
	<%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">
	function deleteBizFunctionParamConfig(id){
		if(confirm("你确定要删除吗？")){
			$.post("bizFunctionParamConfig_deleteBizFunctionParamConfig.action",{id:id},
				function(res){
					if(res.STATE=="SUCCESS"){
						alert("删除成功！");
						$("form[name='listBizFunctionParamConfig']").submit();
					}else{
						alert("删除失败！");
					}
			},"json");
		}
	}
</script>

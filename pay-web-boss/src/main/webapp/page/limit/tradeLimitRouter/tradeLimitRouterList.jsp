<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="tradeLimitRouter_listTradeLimitRouter.action" method="post"
		name="listTradeLimitRouter">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>商户编号：
					<input type="text" name="merchantNo" value="${merchantNo}" alt="精确查询"/>
					</td>
					<td>开关限制包：
						<select name= "switchLimitPackId">
						<option value="">--请选择--</option>				
						<c:forEach items="${switchLimitPacksList}" var="model">
						<option value="${model.id}"						
						<c:if test="${switchLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>	
				    </select>							
				    </td>	
					<td>金额限制包：
					<select name="amountLimitPackId">
						<option value="">--请选择--</option>					
						<c:forEach items="${amountLimitPackList}" var="model">
						<option value="${model.id}"						
						<c:if test="${amountLimitPackId eq model.id }">selected="selected"</c:if>>${model.name}</option>
						</c:forEach>
				    </select>								
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

<!-- 	<div class="panelBar"> -->
<!-- 		<ul class="toolBar"> -->
<!-- 				<li><a class="add" href="tradeLimitRouter_addTradeLimitRouterUI.action" target="dialog" rel="input"> -->
<!-- 					<span>添加</span></a> -->
<!-- 				</li> -->
<!-- 		</ul> -->
<!-- 	</div> -->

	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" layoutH="103">
		<thead>
			<tr>
				<th>序号</th>
				<th>商户编号</th>
				<th>开关限制包</th>
				<th>金额限制包</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					</td>
					<td>
						${merchantNo }
					</td>
					<td>
						<c:forEach var="switchLimitPacksList" items="${switchLimitPacksList }">
						<c:if test="${switchLimitPackId eq switchLimitPacksList.id }">${switchLimitPacksList.name }
						</c:if>
						</c:forEach>						
					</td>					
					<td>
						<c:forEach var="amountLimitPackList" items="${amountLimitPackList }">
						<c:if test="${amountLimitPackId eq amountLimitPackList.id }">${amountLimitPackList.name }
						</c:if>
						</c:forEach>						
					</td>						
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<z:permission value="limit:tradeLimit:edit">
							<a href="tradeLimitRouter_addTradeLimitRouterUI.action?id=${id}" target="dialog" style="color:blue">修改</a>
						</z:permission>
						
						<z:permission value="limit:tradeLimit:del">
						 | <a href="javascript:deleteTradeLimitRouter('${id}');" id="TradeLimitRouter" style="color:blue">删除</a>
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
	function deleteTradeLimitRouter(id){
		if(confirm("你确定要删除吗？")){
			$.post("tradeLimitRouter_deleteTradeLimitRouter.action",{id:id},
				function(res){
					if(res.STATE=="SUCCESS"){
						alert("删除成功！");
						$("form[name='listTradeLimitRouter']").submit();
					}else{
						alert("删除失败！");
					}
			},"json");
		}
	}
</script>

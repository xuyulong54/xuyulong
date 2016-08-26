<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);"
		action="merchantCustomPayInterface_listMerchantCustomPayInterface.action" method="post"
		name="listMerchantCustomPayInterface">
		<!-- 分页表单参数 -->
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>商户编号：
					<input type="text" name="merchantNo" value="${merchantNo}" alt="精确查询"/>
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
<!-- 
	<div class="panelBar">
		<ul class="toolBar">
				<li><a class="add" href="merchantCustomPayInterface_addMerchantCustomPayInterfaceUI.action" target="dialog" rel="input">
					<span>添加</span></a>
				</li>
		</ul>
	</div>
 -->
	<table class="table" targetType="navTab" asc="asc" desc="desc"
		width="100%" layoutH="103">
		<thead>
			<tr>
				<th>序号</th>
				<th>商户编号</th>
				<th>支付方式</th>
				<th>支付接口</th>
				<th>创建时间</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
					<td>
						${merchantNo }
					</td>
					<td>
						${payWay }
					</td>	
					<td>
						${payInterface }
					</td>	
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
					<z:permission value="limit:payinteface:edit">
						<a href="merchantCustomPayInterface_editMerchantCustomPayInterfaceUI.action?id=${id}" target="dialog" style="color:blue">修改</a>
					</z:permission>
					<z:permission value="limit:payinteface:del">
						<a href="javascript:deleteMerchantCustomPayInterface('${id}');" id="MerchantCustomPayInterface" style="color:blue">删除</a>
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
	function deleteMerchantCustomPayInterface(id){
		if(confirm("你确定要删除吗？")){
			$.post("merchantCustomPayInterface_deleteMerchantCustomPayInterface.action",{id:id},
				function(res){
					if(res.STATE=="SUCCESS"){
						alert("删除成功！");
						$("form[name='listMerchantCustomPayInterface']").submit();
					}else{
						alert("删除失败！");
					}
			},"json");
		}
	}
</script>

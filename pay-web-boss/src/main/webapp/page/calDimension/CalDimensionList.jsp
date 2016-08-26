<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="calDimension_calDimensionList.action" method="post">
		<%@include file="/page/inc/pageForm.jsp"%>
		<div class="searchBar">
			<table class="searchContent">
				<tr>
					<td>
						<label>计费产品编号：</label>
						<input type="text" name="calProduct" value="${calProduct }" size="25" alt="精确搜索"/>
					</td>
					<td>
						<label>计费接口：</label>
						<select name="calCostInterfaceCode" id="calCostInterfaceCode">
							<option value="">请选择</option>
							<c:forEach items="${calCostInterfaceList }" var="calCostInterface">
								<option value="${calCostInterface.interfaceCode}" <c:if test="${calCostInterfaceCode eq calCostInterface.interfaceCode }">selected="selected"</c:if>>${calCostInterface.interfaceName}</option>
							</c:forEach>
						</select>
					</td>	
					<td>
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
	<div class="panelBar">
	<z:permission value="boss:calDimension:add">
		<ul class="toolBar">
			<li><a class="add" href="javascript:calDimensionAdd('${id }')"   title="添加计费维度"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>计费产品编号</th>
				<th>计费接口名称</th>
				<th>计费接口编号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${calProduct}</td>
				    <td>
						<c:forEach items="${calCostInterfaceList }" var="calCostInterface">
							<c:if test="${calCostInterface.interfaceCode eq calCostInterfaceCode }">${calCostInterface.interfaceName }</c:if>
						</c:forEach>
					</td>
					<td>${calCostInterfaceCode }</td>
					<td>
						<z:permission value="boss:calDimension:edit">
							<a  href="javascript:editCalDimension('${id }')" title="修改计费维度" style="color:blue">修改</a>
							&nbsp;|&nbsp;
						</z:permission>
						<z:permission value="boss:calDimension:view">
						<a href="javascript:calFeeWayInfo('${id }')" title="查看计费约束" style="color:blue">查看计费方式</a>
						&nbsp;|&nbsp;
						</z:permission>
						<%-- <a href="calFeeWay_calFeeWayInfo.action?dimensionId=${id}" rel="input" target="dialog" title="查看计费约束" style="color:blue">查看计费约束</a>
						<z:permission value="boss:calDimension:delete">
							<a href="javascript:deleteCalDimension(${id })"  title="删除计费维度" style="color:blue">删除</a>
						</z:permission> --%>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
  <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
<script type="text/javascript">

	//增加计费维度
	function calDimensionAdd(id) {
		$.pdialog.open("calDimension_calDimensionAdd.action?id="
				+ id, "calDimensionAdd", "增加计费维度", {
			max : false,
			mask : false,
			resizable : true,
			drawable : true,
			maxable : true,
			minable : true,
			fresh : true,
			width : 550,
			height : 320,
			close : function closeLimit() {
				return true;
			}
		});
	}
	
	//查看计费约束
	function calFeeWayInfo(id) {
		$.pdialog.open("calFeeWay_calFeeWayInfo.action?dimensionId="
				+ id, "calFeeWayInfo", "查看计费方式", {
			max : false,
			mask : false,
			resizable : true,
			drawable : true,
			maxable : true,
			minable : true,
			fresh : true,
			width : 1024,
			height : 320,
			close : function closeLimit() {
				return true;
			}
		});
	}

	//修改计费维度
	function editCalDimension(id) {
		$.pdialog.open("calDimension_editCalDimension.action?id="
				+ id, "editCalDimension", "修改计费维度", {
			max : false,
			mask : false,
			resizable : true,
			drawable : true,
			maxable : true,
			minable : true,
			fresh : true,
			width : 550,
			height : 320,
			close : function closeLimit() {
				return true;
			}
		});
	}
	

	function deleteCalDimension(id){
		 if(confirm("确定要删除吗？")){
			 $.post("calDimension_deleteCalDimension.action",{id:id},function(res){
				 if(res.STATE=="SUCCESS"){
					 alert(res.MSG);
					 $("#pagerForm").submit();
				 }
			 },"json");
		 }
	}
</script>
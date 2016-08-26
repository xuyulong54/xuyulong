<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="cardBin_listCardBin.action" method="post">
	<%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					卡Bin：<input type="text" name="cardBin" value="${cardBin }" size="15" alt="模糊搜索" />
				</td>
				<td>
					发卡行名称：<input type="text" name="bankName" value="${bankName }" size="15" alt="模糊搜索" />
				</td>	
				<td>
					卡名：<input type="text" name="cardName" value="${cardName }" size="15" alt="模糊搜索" />
				</td>
			</tr>
			<tr>
				<td>
					卡种：
						<select name=cardKind>
							<option value="">--请选择--</option>
							<c:forEach items="${cardKindEnum }" var="v">
									<option value="${v.value }"
										<c:if test="${cardKind eq v.value }">selected="selected"</c:if>>${v.desc}</option>
							</c:forEach>
						</select>
				</td>
				<td>状态：
				<select name="status" onchange="cbChange();" style="width: 158px; ">
						<option value="">--全部状态--</option>
					    <option value="${cardBinStatusEnums.YES.value }" <c:if test="${status eq cardBinStatusEnums.YES.value}">selected="selected"</c:if>>${cardBinStatusEnums.YES.desc }</option>
						<option value="${cardBinStatusEnums.NO.value }" <c:if test="${status eq cardBinStatusEnums.NO.value}">selected="selected"</c:if>>${cardBinStatusEnums.NO.desc }</option>
				</select></td>
				<td>
					<div class="subBar">
						<ul>
							<li><div class="buttonActive"><div class="buttonContent"><button type="submit">查询</button></div></div></li>
							<li>
								<div class="buttonActive">
									<div class="buttonContent">
										<button type="button" onclick="openExcel()">导入Excel</button>
									</div>
								</div>
							</li>
							<li><a href="statics/mould/cardBin.xls" style="color:blue">下载模板</a></li>
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
	<z:permission value="boss:cardBin:add">
		<ul class="toolBar">
			<li><a class="add" href="cardBin_addCardBinUI.action" target="dialog" rel="input"  title="添加卡Bin"><span>添加</span></a></li>
		</ul>
	</z:permission>
	</div>
<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="160">
		<thead>
			<tr>
				<th >序号</th>
				<th>卡Bin</th>
				<th>发卡行代码</th>
				<th>发卡行名称</th>
				<th>卡名</th>
				<th>卡种</th>
				<th>卡片长度</th>
				<th>状态</th>
				<th>修改人</th>
				<th>修改时间</th>
				<th>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>${cardBin}</td>
				    <td>${bankCode}</td>
				    <td>${bankName}</td>
					<td>${cardName }</td>
					<td>
						<c:forEach items="${cardKindEnum }" var="help">
							<c:if test="${help.value eq cardKind }">${help.desc }</c:if>
						</c:forEach>
					</td>
					<td>${cardLength}</td>
					<td>
						<c:forEach items="${cardBinStatusEnum }" var="sim">
							<c:if test="${sim.value eq status }">${sim.desc }</c:if>
						</c:forEach>
					</td>
					<td>${lastUpdatorName}</td>
					<td><s:date name="lastUpdateTime" format="yyyy-MM-dd" /></td>
					<td>
					<z:permission value="boss:cardBin:edit">
						<a href="cardBin_editCardBinUI.action?id=${id}" title="修改卡Bin" target="dialog" style="color:blue">修改</a>
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
	function cbChange(){
		$("form[name='cbForm']").submit();
	}
	
	function openExcel() {
		$.pdialog.open("cardBin_importExcelUI.action", "improtCardBin","导入卡bin信息", {
				max : false,
				mask : true,
				mixable : false,
				minable : false,
				resizable : false,
				drawable : true,
				fresh : true,
				close : "function"
		});
	}

</script>
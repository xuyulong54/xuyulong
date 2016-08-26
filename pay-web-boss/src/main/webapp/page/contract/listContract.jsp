<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>

<script>
	 function callback(flag, msg){ 
        if(flag=='true'){ 
            alertMsg.correct(msg); 
            navTab.reload('contract_listContract.action'); 
        }else{ 
            alertMsg.error(msg); 
        } 
     } 
</script>

<div class="pageHeader">
	<form id="pagerForm" name="searchForm" onsubmit="return navTabSearch(this);" action="contract_listContract.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					商户或银行接口名称：<input type="text" id="userName" name="userName" value="${userName}" size="30"  alt="模糊搜索"/>
				</td>
				<td>
					文件编号：<input type="text" id="contractNo" name="contractNo" value="${contractNo}" size="15" alt="模糊搜索"/>
				</td>
				<td>
					文件类型：
					<select name="contractType" id="contractType">
						<option value="">-请选择-</option>
						<c:forEach items="${contractTypeList }" var="model">
							<option value="${model.value }"
							<c:if test="${contractType eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					文件性质：
					<select name="fileProperties" id="fileProperties">
						<option value="">-请选择-</option>
						<c:forEach items="${filePropertiesList }" var="model">
							<option value="${model.value }"
							<c:if test="${fileProperties eq model.value}">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<div class="subBar" >
						<ul>
							<z:permission value="boss:contract:view">
								<li>
									<div class="buttonActive">
										<div class="buttonContent"><button type="submit" id="queryContractBtn">查询</button></div>
									</div>
								</li>
							</z:permission>
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
			<z:permission value="boss:contract:add">
				<li><a class="add" href="contract_addContractUI.action" target="dialog" rel="addContractDiv" 
				title="上传文件" width="600" height="400"><span>上传文件</span></a></li>
			</z:permission>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="131">
		<thead>
			<tr>
				<th>序号</th>
				<th>创建时间</th>
				<th>商户名称或银行接口名称</th>
				<th>文件编号</th>
				<th>源文件名</th>
				<th>签约日期</th>
				<th>合同到期日期</th>
				<th>创建人</th>
				<th>文件类型</th>
				<th>文件性质</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
				    <td>${userName}</td>
					<td>${contractNo}</td>
					<td>${fileName }</td>
					<td><s:date name="signTime" format="yyyy-MM-dd" /></td>
					<td><s:date name="contractValid" format="yyyy-MM-dd" /></td>
					<td>${creater}</td>
					<td>
						<c:forEach items="${contractTypeList }" var="v">
							<c:if test="${contractType eq v.value }">${v.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<c:forEach items="${filePropertiesList }" var="v">
							<c:if test="${fileProperties eq v.value }">${v.desc}</c:if>
						</c:forEach>
					</td>
					<td>
						<z:permission value="boss:contract:download">
							<a title="确定要下载该文件吗？" href="download.action?id=${id}" target="dwzExport" style="color:blue;">下载</a>
						</z:permission>
						<z:permission value="boss:contract:view">
              | <a title="详情" href="contract_viewContract.action?id=${id}" style="color:blue;" target="dialog">详情</a>
					   </z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
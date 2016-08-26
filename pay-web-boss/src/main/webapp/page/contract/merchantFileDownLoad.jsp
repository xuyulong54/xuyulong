<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" name="searchForm" onsubmit="return navTabSearch(this);" action="contract_fileDownLoad.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td>
					文件编号：<input type="text" id="contractNo" name="contractNo" value="${contractNo}" size="20" alt="模糊搜索"/>
					<input type="hidden" name="id" value="${relationId}"/>
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
					创建人：<input type="text" id="creater" name="creater" value="${creater}" size="20"  alt="模糊搜索"/>
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
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="103">
		<thead>
			<tr>
				<th>序号</th>
				<th>文件编号</th>
				<th>源文件名</th>
				<th>创建人</th>
				<th>文件类型</th>
				<th>文件性质</th>
				<th>描述</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${Id}">
					<td>${st.index+1}</td>
					<td>${contractNo}</td>
					<td>${fileName }</td>
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
					<td>${remark}</td>
					<td>
						<z:permission value="boss:contract:download">
							<a title="下载文件到本地" href="download.action?id=${id}" style="color:blue;">下载</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
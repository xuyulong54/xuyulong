<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageHeader">
	<form id="pagerForm" onsubmit="return navTabSearch(this);" action="article_list.action" method="post">
	<!-- 分页表单参数 -->
    <%@include file="/page/inc/pageForm.jsp"%>
	<div class="searchBar">
		<table class="searchContent">
			<tr>
				<td >
					文章标题：
					<input type="text" name="title" id="title" value="${title }" alt="模糊搜索"/>&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					类型：
					<select name="type" id="type">
						<option value="">请选择</option>
						<c:forEach items="${articleTypeList }" var="model">
							<option value="${model.value }" <c:if test="${type == model.value }">selected="selected"</c:if>>${model.desc }</option>
						</c:forEach>
					</select>
				</td>
				<td>
					状态：
					<select name="status" id="status">
						<option value="">请选择</option>
						<option value="100" <c:if test="${status eq 100 }">selected="selected"</c:if> >启用</option>
						<option value="101" <c:if test="${status eq 101 }">selected="selected"</c:if> >停用</option>
					</select>
				</td>
				<td>
					<div class="subBar" >
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
		<ul class="toolBar">
			<z:permission value="boss:article:add">
			<li><a class="add" href="article_toAdd.action" target="dialog" rel="addArticle" title="新建文章"><span>新建文章</span></a></li>
			</z:permission>
		</ul>
	</div>
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" nowrapTD="false" layoutH="130">
		<thead>
			<tr>
				<th>序号</th>
				<th>类型</th>
				<th>标题</th>
				<th>创建时间</th>
				<th>状态</th>
				<th>所属系统</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
				<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td>${st.index+1}</td>
				    <td>
				    	<c:if test="${type == 1}">企业动态</c:if>
				    	<c:if test="${type == 2}">招聘信息</c:if>
				    	<c:if test="${type == 3}">常见问题</c:if>
				    	<c:if test="${type == 4}">产品发布</c:if>
				    	<c:if test="${type == 5}">公告/通知</c:if>
				    </td>
				    <td>${title}</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td>
						<c:if test="${status == '100' }">启用</c:if>
						<c:if test="${status == '101' }">停用</c:if>
					</td>
					<td> 
					<c:if test="${articleType==1 }">门户系统</c:if>
					<c:if test="${articleType==2 }">代理商系统</c:if>
					</td>
					<td>
						<z:permission value="boss:article:view">
						<a title="查看" href="article_toView.action?id=${id }&isView=1" style="color:blue" target="dialog" >查看</a>
						</z:permission>
						<z:permission value="boss:article:edit">
						<a title="修改" href="article_toEdit.action?id=${id }&isView=1" style="color:blue" target="dialog" >修改</a>
						</z:permission>
					</td>
				</tr>
				</s:iterator>
		</tbody>
	</table>
    <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
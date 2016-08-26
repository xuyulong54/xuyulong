<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="57">
		<thead>
			<tr>
				<th>序号</th>
				<th nowrap>设置键名</th>
				<th nowrap>设置内容</th>
				<th nowrap>描述</th>
				<th nowrap>操作</th><!-- 图标列不能居中 -->
			</tr>
		</thead>
		<tbody>
		    <s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
				    <td>${st.index+1}</td>
					<td>${setKey}</td>
					<td>${setContent}</td>
					<td>${description}</td>
					<td nowrap>
						<z:permission value="boss:globalset:view">
							<a href="globalSet_viewGlobalSet.action?id=${id}" title="查看" target="dialog" style="color:blue">查看</a>
						</z:permission>
						<z:permission value="boss:globalset:edit">
							<a href="globalSet_editGlobalSetUI.action?id=${id}" title="修改" target="dialog" style="color:blue">修改</a>
						</z:permission>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
     <!-- 分页条 -->
    <%@include file="/page/inc/pageBar.jsp"%>
</div>
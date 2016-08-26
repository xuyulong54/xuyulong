<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="pageContent">
	<table class="table" targetType="navTab" asc="asc" desc="desc" width="100%" layoutH="50" nowrapTD="false" style="table-layout:fixed;">
		<thead>
			<tr>
				<th>创建时间</th>
				<th>请求信息</th>
				<th>返回信息</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="recordList" status="st">
				<tr target="sid_user" rel="${id}">
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm:ss" /></td>
					<td style="word-wrap:break-word;">${request }</td>
					<td style="word-wrap:break-word;">${response }</td>
					<td>${httpStatus}</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
</div>

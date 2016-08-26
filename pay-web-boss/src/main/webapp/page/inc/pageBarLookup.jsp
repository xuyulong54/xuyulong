<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<div class="panelBar">
	<div class="pages">
		<span>查到&nbsp;${totalCount}&nbsp;条记录，每页20条，共&nbsp;${pageCount}&nbsp;页</span>
	</div>
	<div class="pagination" 
		targetType="dialog" 
		totalCount="${totalCount}" 
		numPerPage="${numPerPage}" 
		pageNumShown="5" 
		currentPage="${currentPage}">
	</div>
</div>
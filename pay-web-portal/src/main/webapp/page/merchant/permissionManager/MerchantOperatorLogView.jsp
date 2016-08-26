<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>操作员日志详情</title>
			<%@include file="/page/include/headScript.jsp" %>


		</head>

		<body>
			<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
			<br />

			<div class="showinfo">

		<div class="headline">
			<div class="title">操作日志详情</div>
		</div>

		

					<table class="table table-border mtop10">
					<thead>
						<tr>
							<th>创建时间：</th>
							<td>
								<fmt:formatDate value="${operatorLog.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
							</td>
						</tr>
						<tr>
							<th>操作员名称：</th>
							<td>${operatorLog.loginName }</td>
						</tr>
						<tr>
							<th>操作内容：</th>
							<td>${operatorLog.content }</td>
						</tr>
						<tr>
							<th>操作状态：</th>
							<td>
								<c:choose>
									<c:when test="${operatorLog.operateStatus eq 100}">成功</c:when>
									<c:when test="${operatorLog.operateStatus eq 101}">失败</c:when>
									<c:otherwise>--</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<th>IP地址：</th>
							<td>${operatorLog.ip }</td>
						</tr>
</thead>
					</table>

				
			
			<div class="clear"></div>
			</div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>
			<script type="text/javascript">
		/*页面分类*/
		 $(document).ready(function() { setPageType('.mer-system', '.mer-system-operator '); })
		 </script>
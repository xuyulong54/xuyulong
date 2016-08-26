<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" buffer="64kb"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>验证参数错误</title>
<%@include file="/page/include/headScript.jsp"%>
<meta name="description" content="" />
<meta name="keywords" content="" />
</head>
<body>
	<div class="errorpg">
		<div class="content">
			<ul>
				<li>${PAGE_ERROR_MSG}</li>
			</ul>
		</div>
	</div>
    <div class="ht1"></div>
</body>
</html>

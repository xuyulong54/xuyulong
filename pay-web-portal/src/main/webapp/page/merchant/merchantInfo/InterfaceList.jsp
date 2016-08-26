<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>接口文件下载</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
	<%-- top menu include --%>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<%--预留信息：${bean.greeting } --%>

	<div class="container">
<div class="bd-container">
		<div class="headline">
	<div class="title">商户接口管理</div>
</div>

				<div class="h10"></div>
				<table class="table table-border" >
				<thead>
							<tr>
								<th >接口名称</th>
								<th >接口描述</th>
								<th >备注</th>
							</tr>
							</thead>
							<tbody>
							<tr>
								<td >支付平台商家接口包</td>
								<td >此文档专为支付平台的合作商户提供，要求商户技术人员使用支付服务接口必须按照此文档开发</td>
								<td >

								<z:permission value="CommonInfo:Api:View">
								</z:permission>
								</td>
							</tr>
							</tbody>
						</table>
				<div class="clear"></div>
		</div></div>
				<div class="clear"></div>


	<div class="clear"></div>
</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

/*页面分类*/
 $(document).ready(function() { setPageType('.mer-publicinfo', '.mer-publicinfo-interface '); })
 </script>

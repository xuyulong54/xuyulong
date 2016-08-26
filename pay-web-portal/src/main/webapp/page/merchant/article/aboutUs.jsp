<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<%@include file="/page/include/headScript.jsp" %>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title>走进我们</title>
		</head>

		<body>
			<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
			<div class="aboutUs">
				<div class="content">
					<div class="left">
						<ul class="lefttop ">
							<li>
								<h3 style='line-height:40px;'>走进${COMPANY_FOR }</h3>
							</li>
							<li class="bg2"><a href="article_aboutUs.action">关于我们</a>
							</li>
							<li><a href="article_contactUs.action">联系我们</a>
							</li>
						</ul>
						<div class="leftbottom">
							<h3>客服热线</h3>
						<div class="cont">
							<img src="<%=path%>/statics/themes/default/images/kefu.jpg" />
							<p>客服电话：${COMPANY_TEL }</p>
<p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
							</div>
						</div>
					</div>
					<div class="right">
						<div class="top">
							<img src="<%=path%>/statics/themes/default/images/aboutustitle.jpg" />
						</div>
						<div class="bottom">
							<h3>关于我们</h3>
							<p> 
								${COMPANY_ABOUT_US }
							</p>
						</div>
					</div>
					<div class="clear"></div>
				</div>

			</div>
			<div class="clear"></div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>

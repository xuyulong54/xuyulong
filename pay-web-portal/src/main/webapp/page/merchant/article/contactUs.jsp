<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
			<%@include file="/page/include/taglib.jsp" %>
	<%@include file="/page/include/headScript.jsp" %>

			<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
			<html>

			<head>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title>联系我们</title>
			</head>

			<body>
				<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
				<div class="aboutUs">
					<div class="content">
						<div class="left">
							<ul class="lefttop">
								<li>
									<h3 style='line-height:40px;'>走进${COMPANY_FOR}</h3>
								</li>
								<li class="bg1"><a href="article_aboutUs.action">关于我们</a>
								</li>
								<li class="bg2"><a href="article_contactUs.action">联系我们</a>
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
							<div class="contactbottom">
								<div class="boxleft">
									<h2>${COMPANY_NAME}</h2>
									<p>总机：${COMPANY_TEL }
										<br />传真：${COMPANY_TEL }
										<br />地址：${COMPANY_ADDRESS }
										<br />邮编：510050
									</p>
									<!-- <h3>客服热线</h3>
									<p>客服电话：${COMPANY_TEL }
									<br />客服邮箱：${COMPANY_EMAIL } -->
									</p>
								</div>
								<div class="boxright">
									<img src="<%=path%>/statics/themes/default/images/map.jpg" />
								</div>
								<div class="clear"></div>
						<!-- 		<div class="boxleft areaA">
							<img src="<%=path%>/statics/themes/default/images/pic1.jpg" />
							<div class="box">
								<h3>技术服务</h3>
								<p>电话：${COMPANY_TEL }
									<br />邮箱：${COMPANY_EMAIL }
								</p>
							</div>
						</div>
						<div class="boxleft areaA">
							<img src="<%=path%>/statics/themes/default/images/contactus2.jpg" />
							<div class="box">
								<h3>商户服务</h3>
								<p>电话：${COMPANY_TEL }
									<br />邮箱：${COMPANY_EMAIL }
								</p>
							</div>
						</div>
						<div class="clear"></div> -->
							</div>
						</div>
						<div class="clear"></div>
					</div>

				</div>
				<div class="clear"></div>
				<jsp:include page="../../foot.jsp" />
			</body>

			</html>

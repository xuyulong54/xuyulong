<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/page/include/taglib.jsp" %>
	<%@include file="/page/include/headScript.jsp" %>

				<html>

				<head>
					<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
					<title>商户服务</title>
					<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				</head>

				<body>
					<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
					<div class="gwService">
	 					<ul class="banner">
							<li class="p1">
								<div class="cont">
									<div class="text">	我们为商户提供专业的支付产品，商家接入${COMPANY_FOR }第三方支付
									</div>
								<div class="bannerContent">
									<div class="words">

										<p class="clearfix ">
											<a href="#" class="fl" style="margin-left: 120px;">信用卡支付 </a>
										</p>
										<p class="clearfix">
											<a href="#" class="fl" style="color:#8e8e8e;">网银支付</a>  <a href="#" class="fr">预付支付</a>
										</p>
										<p class="clearfix"><a href="#" class="margin">移动支付</a>
										</p>

										<p class="clearfix">
											<a href="#" class="fl" style="font-size: 18px; margin-left: 20px;color:#8e8e8e;">快捷支付</a>  <a href="#" class="fr" style="font-size: 14px; margin-right: 50px;color:#8e8e8e;">代扣</a>

										</p>
										<p class="clearfix">
											<a href="#" class="fl" style="margin-left: 50px;">POS支付</a>  <a href="#" class="fr">分账支付</a>

										</p>
										<p class="clearfix"><a href="#" class="margin" style="font-size: 18px;color:#8e8e8e;">转账</a>
										</p>
									</div>
								</div>
								</div>
							</li>
							<li class="p2">
							</li>
							<li class="p1">
								<div class="cont">
									<div class="text">	我们为商户提供专业的支付产品，商家接入${COMPANY_FOR }第三方支付
									</div>
									<div class="bannerContent">
										<div class="words">

											<p class="clearfix ">
												<a href="#" class="fl" style="margin-left: 120px;">信用卡支付 </a>
											</p>
											<p class="clearfix">
												<a href="#" class="fl" style="color:#8e8e8e;">网银支付</a>  <a href="#" class="fr">预付支付</a>
											</p>
											<p class="clearfix"><a href="#" class="margin">移动支付</a>
											</p>

											<p class="clearfix">
												<a href="#" class="fl" style="font-size: 18px; margin-left: 20px;color:#8e8e8e;">快捷支付</a>  <a href="#" class="fr" style="font-size: 14px; margin-right: 50px;color:#8e8e8e;">代扣</a>

											</p>
											<p class="clearfix">
												<a href="#" class="fl" style="margin-left: 50px;">POS支付</a>  <a href="#" class="fr">分账支付</a>

											</p>
											<p class="clearfix"><a href="#" class="margin" style="font-size: 18px;color:#8e8e8e;">转账</a>
											</p>
										</div>
									</div>
								</div>
							</li>
							<li class="p2"></li>
						</ul>

						<ul class="bannerdot">
							<li>
								<img src="statics/themes/default/images/bannerdot2.png" />
							</li>
							<li>
								<img src="statics/themes/default/images/bannerdot.png" />
							</li>
							<li>
								<img src="statics/themes/default/images/bannerdot.png" />
							</li>
							<li>
								<img src="statics/themes/default/images/bannerdot.png" />
							</li>
						</ul>
						<div class='bannerContent'>

						</div>
						<div class="content bottom clearfix">
							<div class="boxA">
								<h3>商家接入流程</h3>

								<ul>
									<li>
										<p>注册${COMPANY_FOR}账户 提交企业资料签订合同</p>
									</li>
									<li>
										<p class="p2">申请开通相关产品 协议确定相关费率信息</p>
									</li>
									<li>
										<p class="p3">${COMPANY_FOR}审核商户信息</p>
									</li>
									<li>
										<p class="p4">审核通过，上线运行</p>
									</li>
								</ul>
								<div class="clear"></div>
							</div>
							<div class="boxB">
								<h3>商家接入热线</h3>
								<div class="area">
									<img src="statics/themes/default/images/phoneImg.jpg" />
									<span>${COMPANY_TEL }</span>
									<p>拨打咨询服务热线，营销顾问会根据您的实际情况，提供一站式解决方案</p>
								</div>
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<jsp:include page="../../foot.jsp" />
				</body>

				</html>
				<script type="text/javascript">
					$(function () {
					    setPageType('.navService');//判断页面属于哪类导航项
						var banner = $('.banner');
						banner.timer = null;
						$(".banner li").fadeOut(0).eq(0).fadeIn(0);
						var i = 0;
						banner.timer = setInterval(run, 10000);

						function run() {
							if (i < $(".banner li").length - 1) {
								$(".banner li").eq(i).fadeOut(1000).next("li").fadeIn(1000);
								i++;
								$('.bannerdot img').attr('src', 'statics/themes/default/images/bannerdot.png');
								$('.bannerdot img').eq(i).attr('src', 'statics/themes/default/images/bannerdot2.png');
								$('.bannerdot img').removeClass('activeDot');
								$('.bannerdot img').eq(i).addClass('activeDot');
							} else {
								$(".banner li").eq(i).fadeOut(1000).siblings("li").eq(0).fadeIn(1000);
								i = 0;
								$('.bannerdot img').attr('src', 'statics/themes/default/images/bannerdot.png');
								$('.bannerdot img').eq(0).attr('src', 'statics/themes/default/images/bannerdot2.png');
								$('.bannerdot img').removeClass('activeDot');
								$('.bannerdot img').eq(0).addClass('activeDot');
							}
						}
						$(".bannerdot li").bind('mouseover', function () {
							clearInterval(banner.timer);
						});
						$(".bannerdot li").bind('mouseout', function () {
							banner.timer = setInterval(run, 10000);
						});
						for (n = 0; n < $(".bannerdot li").length; n++) {
							$(".bannerdot li").eq(n).attr('ada-index', n);
							$(".bannerdot li").eq(n).bind('click', function () {
								if (!$(this).children().hasClass('activeDot')) {
									i = $(this).attr('ada-index');
									i = parseInt(i);
									$(".banner li").fadeOut(1000).eq(i).fadeIn(1000);
									i = i - 1;
									run();
								}
							});
						}

					});
				</script>

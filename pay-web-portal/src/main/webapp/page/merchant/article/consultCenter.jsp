<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>客服中心</title>
		</head>

		<body>
			<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
			<div class="content consultCenter">
      <div class="text">
        <p>${COMPANY_FOR }支付平台服务热线</p>
        <p>${COMPANY_TEL }</p>
      </div>
				<img src="statics/themes/default/images/gwProductsTitle.jpg" />
				<div class="wrap">
					<div class="left">
						<div class="boxA">
							<h3>商户快速服务通道</h3>
							<ul>
								<li class="imgA"><a href="merchantemailbind_bindingEmailUI.action">修改绑定邮箱</a>
								</li>
								<li class="imgB"><a href="merchantmobilebind_bindingMobileUI.action">修改绑定手机</a>
								</li>
								<li class="imgD"><a href="merchantlookfortradepwd_tradePwdListWay.action">找回支付密码</a>
								</li>
							</ul>
							<h3>会员快速服务通道</h3>
							<ul>
								<li class="imgA"><a href="memberemailbind_bindingEmailUI.action">修改绑定邮箱</a>
								</li>
								<li class="imgB"><a href="membermobilebind_bindingMobileUI.action">修改绑定手机</a>
								</li>
								<li class="imgD"><a href="memberlookfortradepwd_tradePwdListWay.action">找回支付密码</a>
								</li>
							</ul>
						</div>
						<div class="boxB">
							<ol>
								<li>1.提交建议</li>
								<li>2.处理建议</li>
								<li>3.反馈处理结果</li>
							</ol>
						</div>
					</div>
					<div class="right">
						<div class="clearfix" id="tab">
							<div class="menuBox menuBoxHover">
								<img src="statics/themes/default/images/consultImgPhong.jpg" />
								<h4>人工客服</h4>
								<p>第一时间为您解决困扰。如遇坐席忙，请稍后再拨</p>
							</div>
							<div class=" menuBox">
								<img src="statics/themes/default/images/consultImg3.jpg" />
								<h4>常见问题</h4>
								<p>您可以自助查询，找到解决方法</p>
							</div>
							<div class="menuBox" style="margin-right: 0px;">
								<img src="statics/themes/default/images/consultImg4.jpg" />
								<h4>投诉建议</h4>
								<p>工作人员会在两个工作日内回复您的问题</p>
							</div>
						</div>

						<ul id="tabCont">
							<li class=" block">
                      <div class="service-wrap clearfix">
                        <div class="icon">
                          <i class="iconfont">&#xe60c;</i>
                        </div>
                        <div class="con">
                          <h4>邮件客服</h4>
                          <p class="blue">${COMPANY_EMAIL }</p>
                          <p>服务时间：00:00-24:00</p>
                        </div>
                      </div>
                      <div class="service-wrap clearfix">
                        <div class="icon">
                          <i class="iconfont">&#xe60b;</i>
                        </div>
                        <div class="con">
                          <h4>电话客服</h4>
                          <p class="blue">${COMPANY_TEL }</p>
                          <p>服务时间：9:00-17:00</p>
                        </div>
                      </div>
                    </li>


		<li>
								<span class="contBox">
								您可能遇到以下问题:<br/>&nbsp;
        <h4>安全类常见问题</h4>
        <p class="boxH">
       <img src="statics/themes/default/images/img_SecurityQuestion.jpg" alt="" />
       </p>
        </span>
								<span class="contBox">
								&nbsp;<br/>&nbsp;
        <h4>支付类常见问题</h4>
       <p class="boxH">
       <img src="statics/themes/default/images/img_PayQuetion.jpg" alt="" />
       </p>
        </span>
								<span class="contBox">
									&nbsp;<br/>&nbsp;
        <h4>银行卡常见问题</h4>
         <p class="boxH">
       <img src="statics/themes/default/images/img_BankcardQuestion.jpg" alt="" />
       </p>
        <a href="article_listArticle.action?type=3" class="searchMore">查看更多</a>
        </span>

							</li>

						</ul>
						<div class="clear"></div>
					</div>
				</div>
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>
		<script type="text/javascript">
		    $(function(){
		    setPageType('.navCallcenter');//判断页面属于哪类导航项
		    })
			var tabs = document.getElementById("tab").getElementsByTagName("div");
			var divs = document.getElementById("tabCont").getElementsByTagName("li");
			for (var i = 0; i < tabs.length; i++) {
				tabs[i].onclick = function () {
					change(this);
				}
			}

			function change(obj) {
				for (var i = 0; i < tabs.length; i++) {
					if (tabs[i] == obj) {
						tabs[i].className = "menuBoxHover";
						divs[i].className = "block";
					} else {
						tabs[i].className = "menuBox";
						divs[i].className = "";
					}
				}
			}
		</script>

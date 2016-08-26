<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headUrl.jsp" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="Stylesheet" href="<%=path%>/statics/themes/default/css/login.css" />
	<link rel="stylesheet" href="<%=path %>/statics/themes/default/iconfonts/iconfont.css" />
	<script type="text/javascript" src="<%=path %>/statics/themes/default/js/html5.js"></script>
	<script type="text/javascript" src="<%=path %>/statics/themes/default/js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="<%=path %>/statics/themes/default/js/jquery.pngFix.pack.js"></script>
	<script type="text/javascript" src="<%=path %>/statics/js/common.js"></script>
	<title>${COMPANY_NAME }-首页</title>
	<script type="text/javascript">
		//获取服务器时间戳
		var ts="<%=System.currentTimeMillis()%>";
		function changeCode(code) {
			code.src = 'randomCode.jpg?' + Math.floor(Math.random() * 100);
		}
		$(document).ready(function(){
		    setPageType('.navHome');//判断页面属于哪类导航项
			var userType='${userType}';
			var loginName='${loginName}';
		       $('.ptitle li').eq(0).bind('click',function(){
			   $('.ada-sp').val('请输入邮箱地址/用户名');
			   $('.test').val('');
			  /// document.getElementById("password").val("");

		    });
			$('.ptitle li').eq(1).bind('click',function(){

			  $('.ada-sp').val('请输入邮箱地址/手机号码');
			  $('.test').val('');
			  //document.getElementById("password").val("");
		    });
			$('.ada-sp').bind('focus',function(){
				var value = $(this).val();
				if(value=='请输入邮箱地址/用户名'|| value=='请输入邮箱地址/手机号码'
					|| value.trim() == ''){
					$(this).val('');
				}
			});
			$('.ada-sp').bind('blur',function(){
			    if($(this).val()==''){
					if($('.ptitle li').eq(0).hasClass('now')){
						$(this).val('请输入邮箱地址/用户名');
						}
					else{
						$(this).val('请输入邮箱地址/手机号码');
						}
					}
			   });
	    if(loginName!=''){
	   		$('.ada-sp').val(loginName);}
		});
	</script>
	<c:if test="${USE_KEYBOARD }">
		<script type="text/javascript" src="<%=path %>/statics/js/writeObject.js"></script>
		<!-- 处理IE浏览器下id为powerpass控件的回车事件 -->
		<script language="javascript" for='powerpass' event='EventReturn'>
			//将焦点放在id为login的标签上。
			document.getElementById("randomCode").focus();
		</script>

		<!-- 处理IE浏览器下id为powerpass控件的Tab事件 -->
		<script language="javascript" for='powerpass' event='EventTab'>
			//将焦点放在id为login的标签上。
			document.getElementById("randomCode").focus();
		</script>

		<!-- 处理IE浏览器下id为powerpass控件的密码强弱度事件 -->
		<script language="javascript" for='powerpass' event='EventDegree(arg1)'>
			var degree = "";
			if (arg1 == "W") {
				degree = "弱";
			} else if (arg1 == "M") {
				degree = "中";
			} else if (arg1 == "S") {
				degree = "强";
			}
		</script>
	</c:if>
	<style>
	body{background: #f4f4f4}
	</style>
</head>
<body onselectstart="return false" ondragstart="return false">
	<div class="header">
		<div class="headerContent">
			<div class="notice">
				<a href="article_listArticle.action?type=5" class="noticeTitle">企业公告：</a>
				<marquee direction="infinite"  scrollamount="5" style="width:500px; height:34px; line-height:34px;">
					<c:forEach items="${recordListNotice}" var="notice">
						<a href="article_viewArticle.action?id=${notice.id}" style="color:#B9BABE; text-decoration: none;float:left">${notice.title}&nbsp;&nbsp;</a>
					</c:forEach>
				</marquee>
			</div>
			<div class="links">
				<a href="article_listArticle.action?type=3">常见问题</a>
				<a href="login_loginUI.action" class="noline">登录</a>
				<c:if test="${PORTAL_IS_REGISTER }">
					<a href="merchantRegister_checkLoginNameUI.action">注册</a>
				</c:if>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<div class="navWrap">
		<div class="newNav">
			<div class="logo">
				<%-- <img src="<%=path %>${COMPANY_LOGO }" /> --%>
				<img src="<%=path %>${COMPANY_LOGO }" />
			</div>
			<ul>
				<li class='navHome'><a href="login_loginUI.action">首页</a></li>
				<li class='navService'><a href="article_gwService.action">商户服务</a></li>
				<li class='navProduct'><a href="article_listArticle.action?type=4">产品</a></li>
				<li class='navSecurity'><a href="article_securityCenter.action">安全中心</a></li>
				<li class='lastMenu navCallcenter'><a href="article_consultCenter.action">客服中心</a></li>
			</ul>
		</div>
	</div>

	<div class="newContent">
		<ul class="banner">
			<li class="p1">
				<div class="content">
					<div class="text">	我们为商户提供专业的支付产品
					</div>
				</div>
			</li>
			<li class="p2">
				<div class="content">
				</div>
			</li>
			<li class="p1">
				<div class="content">
					<div class="text">	我们为商户提供专业的支付产品
					</div>
				</div>
			</li>
			<li class="p2">
				<div class="content">
				</div>
			</li>
		</ul>
		<ul class="bannerdot">
			<li><img src="<%=path %>/statics/themes/default/images/bannerdot2.png" />
			</li>
			<li><img src="<%=path %>/statics/themes/default/images/bannerdot.png" />
			</li>
			<li><img src="<%=path %>/statics/themes/default/images/bannerdot.png" />
			</li>
			<li><img src="<%=path %>/statics/themes/default/images/bannerdot.png" />
			</li>
		</ul>
		<div class="contentWrap">
			<div class="bannerContent">
				<div class="contentwrap">
					<div class="loginBox">
						<div class="ptitle">
							<ul class="tabHead">
								<li onclick="change_div('merchant')" class="pt_b <c:if test="${empty userType or userType eq 'merchant'}" >now</c:if>" key="merchant">商户登录</li>
								<li onclick="change_div('member')" class="pt_a <c:if test="${userType eq 'member'}" >now</c:if>" key="member">会员登录</li>
							</ul>
						</div>
						<div class="login opa">
							<form action="login_login.action" method="post" id="loginfrom">
								<input type="hidden" name="userType" id="userType" value="${userType == null ? 'merchant' : userType}">

								<div style="clear: both; color:red; text-indent:20px; font-size:14px; ">
									<span id="errorMsg"></span>
									<c:if test="${!empty loginMsg }">
										<span id="loginMsg"> ${loginMsg} </span>
									</c:if>
								</div>
	 								<div class="userwrap">
										<input type="text" name="loginName" value='请输入邮箱地址/用户名' class="input user ada-sp" />
									</div>
									<div class="passwordwrap">
										<span style='float:left;width:43px;'>&nbsp;&nbsp;&nbsp;</span>
										<c:choose>
										 <c:when test="${USE_KEYBOARD }">
										 <input type="hidden" name="loginPwd" id="password" class="password" maxlength="20"/>
										 <script type="text/javascript">
											writePassObject("powerpass", {
												"width" : 173,
												"height" : 35,
												" margin-top" : 10,
												"accepts" : "[:graph:]+",
												"x" : -50,
												"maxlength":20
											});
										</script>
										</c:when>
										<c:otherwise>
										<input type="password" name="loginPwd" id="password" class="password" maxlength="20"/>
										</c:otherwise>
										</c:choose>

									</div>
									<c:choose>
										 <c:when test="${IS_USE_KAPTCHA ||(PWD_TIMES_USE_KAPTCHA >0 && LOGIN_TIMES_REACH > PWD_TIMES_USE_KAPTCHA)}">
									<div class="yanzhengma">
										<div class="testwrap">
											<input type="text" name="randomCode" id="randomCode" size="8" class="test" />

										</div>
										<img alt="验证码" src="randomCode.jpg" onclick="changeCode(this);" height="35" width="80px">
									</div>
									</c:when>
										<c:otherwise>
											<input type="hidden" name="randomCode" value="false" id="randomCode" />
										</c:otherwise>
									</c:choose>
									<div class="submit" style="*margin-left:10px;">
										<input class="commonBtnLong btn_login" id="Button4" type="button" value="登 录" />

									</div>
							</form>
							<div class="others links">
								<div id="merchantid">
									<a id="shwjmm"
										href="merchantLookForLoginPwd_loginPwdCheckLoginNameUI.action"
										class="forgetpass">忘记密码</a>
										<c:if test="${PORTAL_IS_REGISTER }">
										 <a id="shzhuce"
										href="merchantRegister_checkLoginNameUI.action"
										class="registerfree">注册</a>
										</c:if>
								</div>
								<div id="memberid" style="display: none">
									<a id="hywjmm"
										href="merchantLookForLoginPwd_loginPwdCheckLoginNameUI.action"
										class="forgetpass">忘记密码</a> 
										<c:if test="${PORTAL_IS_REGISTER }">
										<a id="hyzhuce"
										href="memberRegister_checkLoginNameUI.action"
										class="registerfree">注册</a>
										</c:if>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>

    <div class='productWrap'>
	    <h2>特色产品</h2>
	    <div class='areaA'>
		    <div class='box boxA'>
		    <h3 class="starImg"><i class="iconfont" style='color:#f7b82b;padding-right:10px;'>&#xe604;</i>基础服务</h3>
		    <img src="<%=path %>/statics/themes/default/images/zuojiantou.jpg" class="zuojiantou slide1" />
		    <img src="<%=path %>/statics/themes/default/images/youjiantou.jpg" class="youjiantou rightSlide1" />
		    <span  class="youtubiao" ></span>
		    <span  class="zuotubiao" ></span>
		    <ul >
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_Recharge.png" class="securityBoxImg" />
											<div class="description">
												<h4>充值</h4>
												<p>将银行卡的资金充值到用户的支付账户上</p>
											</div>
										</li>
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_Transfer.png" class="securityBoxImg" />
											<div class="description">
												<h4>转账</h4>
												<p>转账到他人的支付帐户<a href="#" class="transferLink">立即转账</a>
												</p>
											</div>
										</li>
										<li class="li3">
											<img src="<%=path %>/statics/themes/default/images/ico_E-bank.png" class="securityBoxImg" />
											<div class="description">
												<h4>网银支付</h4>
												<p>银行卡开通网上银行功能，即可跳转到银行页面进行付款。</p>
											</div>
										</li>
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_credit.png" class="securityBoxImg" />
											<div class="description">
												<h4>信用卡支付</h4>
												<p>只需要输入银行卡信息，即可完成付款。</p>
											</div>
										</li>
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_shortcut.png" class="securityBoxImg" />
											<div class="description">
												<h4>快捷支付</h4>
												<p>随时随地轻松完成支付，具有快捷、方便、高效特点。</p>
											</div>
										</li>
										<div class="clear"></div>
			 </ul>
		    </div>
		    <div class='boxLine'></div>
		    <div class='box boxB'>
		    <h3 class="starImg"><i class="iconfont" style='color:#f7b82b;padding-right:10px;'>&#xe604;</i>支付产品</h3>
		    <img src="<%=path %>/statics/themes/default/images/zuojiantou.jpg" class="zuojiantou slide2" />
		    <img src="<%=path %>/statics/themes/default/images/youjiantou.jpg" class="youjiantou rightSlide2" />
		    <span  class="youtubiao" ></span>
		    <span  class="zuotubiao" ></span>
		    <ul>
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_netpay.png" class="securityBoxImg" />
											<div class="description">
												<h4>互联网支付</h4>
												<p>提供商户间网上支付及清结算服务</p>
											</div>
										</li>
										<li>
											<img src="<%=path %>/statics/themes/default/images/ico_pospay.png" class="securityBoxImg" />
											<div class="description">
												<h4>POS支付</h4>
												<p>消费者使用银行卡在POS机上完成消费付款</p>
											</div>
										</li>
										<li class="li3">
											<img src="<%=path %>/statics/themes/default/images/ico_phonepay.png" class="securityBoxImg" />
											<div class="description">
												<h4>移动支付</h4>
												<p>随时随地轻松完成支付，具有快速、便捷、高效特点。</p>
											</div>
										</li>

										<div class="clear"></div>
									</ul>

                                    </div>
                                    <div class='boxLine'></div>
		    <div class='box boxC'>
		        <h3 class="starImg"><i class="iconfont" style='color:#f7b82b;padding-right:10px;'>&#xe604;</i>行业解决方案</h3>
		        <ul>
										<li>
											<img src="<%=path %>/statics/themes/default/images/procuctImg2.jpg" />
											<div class="description" >
												<h4>大宗商品贸易</h4>

											</div>
											<div class="clear"></div>
										</li>
									</ul>
		    </div>
		    <div class='clear'></div>
	    </div>
    </div>
     <div class='shadow'></div>
    <div class='footerWrap'>
      <div class='clear'></div>
			<jsp:include page="foot.jsp" />
		</div>
</body>

<script type="text/javascript">

	var paths = "<%=path %>";

	$(document).ready(function() {

		$(".ptitle").find("li").bind("click", function() {
			$(this).addClass("now").siblings("li").removeClass("now");
			$("#userType").val($(this).attr("key"));
		});
		$("#memberid").hide();
		$("#merchantid").show();

		/* change_div('merchant');  */
		//登录信息不能为空提示
		$('.btn_login').bind( 'click', function() {
			if ($('.ada-sp').val() == '请输入邮箱地址/用户名' || $('.ada-sp').val() == '请输入邮箱地址/手机号码' || $('.ada-sp').val() == '') {
				$('#errorMsg').html('用户名不能为空');
				$('#errorMsg').show();
				return;
			}
			<c:choose>
			 <c:when test="${USE_KEYBOARD }">
			 	 var password = getPassInput("powerpass", ts, "errorMsg", "密码输入错误：");
			 	 $("#password").val(password);
			</c:when>
			<c:otherwise>
				//获取密码密文
				 var password = $("#password").val();
			</c:otherwise>
			</c:choose>
			//获取密码密文
			// var password = $("#password").val(); //getPassInput("powerpass", ts, "errorMsg", "密码输入错误：");
			if (password == null) {
				$('#errorMsg').html('密码不能为空');
				$('#errorMsg').show();
				return;
			}
			if ($('.test').val() == '') {
				$('#errorMsg').html('验证码不能为空');
				$('#errorMsg').show();
				return;
			}
			$("#loginfrom").submit();
		});
		// 循环更换广告图片
		changeBannerTwo();

		//鼠标移进移入效果
		filter('.youjiantou','mouseover',1);
		filter('.youjiantou','mouseout',0);
		filter('.zuojiantou','mouseover',1);
		filter('.zuojiantou','mouseout',0);

		var moveNum1=0;
		var moveNum2=0;
		var moveLength=$('.boxA ul li').width();
		slide('.slide1','.rightSlide1',2,moveNum1,moveLength);
		slide('.slide2','.rightSlide2',1,moveNum2,moveLength);
	});

	//鼠标移进移入效果
	function filter(selector,action,opacity){
	$(selector).bind(action,function(){
		$(this).animate({opacity:opacity});
		});
	}


	//小图点击切换
    function slide(selector,selector2,num,moveNum,moveLength){
    $(selector).bind('click',function(){
		moveNum++;
		if(moveNum>=num){
		moveNum=num;
		}
		$(this).siblings('ul').animate({'left':'-'+moveLength*moveNum+'px'});
		});
		$(selector2).bind('click',function(){
		moveNum--;
		if(moveNum<0){
		moveNum=0;
		}
		$(this).siblings('ul').animate({'left':'-'+moveLength*moveNum+'px'});
		});
    }



	// 更换大广告图片
	function changeBannerTwo(){
		var banner = $('.banner');
		banner.timer = null;
		$(".banner li").fadeOut(0).eq(0).fadeIn(0);
		var i = 0;
		banner.timer = setInterval(run, 10000);
		function run() {
			if (i < $(".banner li").length - 1) {
				$(".banner li").eq(i).fadeOut(2000).next( "li").fadeIn(2000);
				i++;
				$('.bannerdot img').attr('src', paths+'/statics/themes/default/images/bannerdot.png');
				$('.bannerdot img').eq((i)).attr('src', paths+'/statics/themes/default/images/bannerdot2.png');
				$('.bannerdot img').removeClass('activeDot');
				$('.bannerdot img').eq(i).addClass('activeDot');

			} else {
				$(".banner li").eq(i).fadeOut(2000).siblings("li").eq(0).fadeIn(2000);
				i = 0;
				$('.bannerdot img').attr('src', paths+'/statics/themes/default/images/bannerdot.png');
				$('.bannerdot img').eq(0).attr('src', paths+'/statics/themes/default/images/bannerdot2.png');
				$('.bannerdot img').removeClass('activeDot');
				$('.bannerdot img').eq(0).addClass('activeDot');
			}
		}
		$(".bannerdot li").bind('mouseover', function() {
			clearInterval(banner.timer);
		});
		$(".bannerdot li").bind('mouseout', function() {
			banner.timer = setInterval(run, 10000);
		});
		for (n = 0; n < $(".bannerdot li").length; n++) {
			$(".bannerdot li").eq(n).attr('ada-index', n);
			$(".bannerdot li").eq(n).bind('click', function() {
				if (!$(this).children().hasClass('activeDot')) {
					i = $(this).attr('ada-index');
					i = parseInt(i);
					$(".banner li").fadeOut(2000).eq(i).fadeIn(2000);
					i = i - 1;
					run();
				}
			});
		}
	}

	// 更换商户/会员的提示信息
	function change_div(id) {
		if (id == "member") {
			$("#memberid").show();
			$("#merchantid").hide();
		} else {
			$("#memberid").hide();
			$("#merchantid").show();
		}
		$("#loginMsg").hide();
	}

</script>
</html>

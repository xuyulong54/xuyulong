<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>会员账户管理</title>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>

<script type="text/javascript">

//联系客服
$(function(){
 var $this= $('.scrollBoxWrap');
 $this.hover(function(){
 		timer=setTimeout(function(){
 		$this.animate({'width':238},'500');
 		},500);
  },function(){
  	clearTimeout(timer);
  	$this.animate({'width':29},'300');
  });

  var accountStatus='${account.status}';
  if(accountStatus!=100){
  	$("#transfer").attr("href","javascript:;");
  	$("#withdraw").attr("href","javascript:;");
  }
  ChangeDiv();
  var mobileStatus='${userInfo.isMobileAuth}';

  if(mobileStatus == 100){
  $('.IMG_link2A').addClass('IMG_link2B');
  }
  var mailStatus='${userInfo.isEmailAuth}';
  if(mailStatus==100){
  $('.IMG_link3A').addClass('IMG_link3B');
  }

});
//登录密码修改提示
function logPwdEditHint(){
		window.location.href='memberpwdedit_editLoginPwdUI.action';
}
//弹出隐藏层
function ChangeDiv(message){
        var alertPwdDays ='${alertPwdDays}';
		var pwd_edit_limit_time = '${pwd_edit_limit_time}';
		if (alertPwdDays > pwd_edit_limit_time) {
        var text = "您已经有<font class='markRed'>"+alertPwdDays+"</font>天没有修改密码了，点击确定修改密码";
		myAlert(text,350,250,function(){
		logPwdEditHint();
		});
		}
		if(message!=''&&message!=null&&message!=undefined){
			myAlert(message,350,250);
		}

}

</script>
<script type="text/javascript">
	//根据用户状态是否可结算，提现
	function accountStatus(temp) {
		if ("${account.status}" != 100) {
			var message = "";
			if (temp == 1) {
				message = "转账";
			} else {
				message = "提现";
			}
			message = "账户为冻结状态，不能" + message;
			ChangeDiv(message);
		} else {
			if (temp == 1) {
			window.location.href="memberReceiveTransfer_ransferPage.action";
			} else {
			window.location.href="withdraw_withdrawUI.action";
			}
		}
	}
</script>
</head>
<c:choose>
	<c:when test="${USE_SECURITYCENTER }">
		<body onload="getCurrentUserCA('${CURRENT_SN_LIST}')">
	</c:when>
	<c:otherwise>
		<body>
	</c:otherwise>
</c:choose>

<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
<!-- 切删除改div,用于加载CA	控件 -->
<div id="FakeCryptoAgent" style="padding: 0px; margin: 0px;"></div>
<div class="shell">
	<label> 【公告】</label>
	<div id="scrollbox">
		<c:forEach items="${recordListNotice}" var="notice">
			<a href="article_viewArticle.action?id=${notice.id}">${notice.title}&nbsp;&nbsp;</a>
		</c:forEach>
	</div>
</div>
<div class='scrollBoxWrap'>
	<div class="mainbox">
		<div class='scrollBox'>
			<p>
				<i class="iconfont">&#xe601;</i>联系客服
			</p>
		</div>
		<div class='pWrap'>
			<img src="<%=path%>/statics/themes/default/images/conetionIMG1.png" />
			<div class="cont">
				<p>客服电话：${COMPANY_TEL }</p>
<p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
			</div>
		</div>
	</div>
</div>
<div class="bd-container ">
	<div class="greetview">
		<div class="panelbox clearfix">
			<div class="panelbox-sm panel-special panel-special-blue">
				<div class="title">
					<span class="png">预</span> 留信息
				</div>
				<div class="txt">
					${userInfo.greeting }
					<c:if test="${empty userInfo.greeting }">
						<font color="red"> 您还未设置预留信息哦！</font>
						<a class="link-color" href="memReservationInfo_editMemberGreetingUI.action">进行设置</a>
					</c:if>

				</div>
			</div>
			<div class="panelbox-sm  panel-special panel-special-org mleft15">
				<div class="title">
					<span class="png">账</span> 户余额
				</div>
				<div class="txt">
					<p>
						<label> 可用余额：</label> <span class="num"><fmt:formatNumber
								value="${availableBalance}" pattern="#0.00" /> </span>元

					</p>

					<p>
						<label> 不可用余额：</label><span class="minor"><fmt:formatNumber
								value="${account.unBalance}" pattern="#0.00" /> </span>元
					</p>

				</div>
			</div>
			<div class="panelbox-mid">
				<div class=" account-fn panel-special panel-special-gray">
					<div class="title">
						<span class="png">账</span> 户功能
					</div>
					<div class="txt txtreset">
						<a class="search" href="memberaccount_listAccountHistory.action">账户明细查询</a>
					</div>
					<div class="txt">
						<ul>
							<li><a class="rechage png"
								href="memberReceiveRecharge_rechargePage.action">充值</a></li>
							<c:choose>
								<c:when
									test="${userInfo.status == PublicStatusEnum.ACTIVE.value}">
									<li class="mleft15 png"><a class="transfers"
										href="javascript:;" id="transfer" class="btn_rtRed"
										onclick="accountStatus(1);">转账</a></li>
									<li class="mleft15 png"><a class="withdraw"
										href="javascript:;" id="withdraw" class="btn_rtRed"
										onclick="accountStatus(2);">提现</a></li>
								</c:when>
							</c:choose>
						</ul>
					</div>
					<div class="clearfix"></div>
				</div>
			</div>
			<div class="h15"></div>
			<div class="panelbox-sm minheight panel-special panel-special-red">
				<div class="title">
					<span class="png">积</span>分
				</div>
				<div class="txt">
					<p>
						<span class="minor">${loginScore}</span>分
					</p>
				</div>
			</div>
			<div class="panelbox-big minheight panel-special panel-special-green">
				<div class="title">
					<span class="png">账</span>户安全
				</div>
				<div class="txt">
					<div style='width:260px;float:left;'>
						<p>
							<strong>最后登录时间：</strong>
							<fmt:formatDate value="${lastLoginDate}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</p>
						<p>
							<strong>账户状态：</strong> <span> <c:forEach
									items="${accountStatusList }" var="accountStatusEnum">
									<c:if test="${accountStatusEnum.value== account.status}">${accountStatusEnum.desc}</c:if>
								</c:forEach> </span>
						</p>
					</div>
					<ul class="authen" style='float:left;'>
						<c:choose>
							<c:when
								test="${userInfo.isRealNameAuth == PublicStatusEnum.ACTIVE.value}">
								<li class=" png IMG_link1B"></li>
							</c:when>
							<c:otherwise>
								<li class=" png IMG_link1A"></li>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when
								test="${userInfo.isMobileAuth == PublicStatusEnum.ACTIVE.value}">
								<li class="png IMG_link2B mleft15"></li>
							</c:when>
							<c:when
								test="${userInfo.isMobileAuth == PublicStatusEnum.INACTIVE.value}">
								<li class="png IMG_link2A mleft15"></li>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when
								test="${userInfo.isEmailAuth == PublicStatusEnum.ACTIVE.value}">
								<li class="png IMG_link3B mleft15"></li>
							</c:when>
							<c:when
								test="${userInfo.isEmailAuth == PublicStatusEnum.INACTIVE.value}">
								<li class="png IMG_link3A mleft15"></li>
							</c:when>
						</c:choose>


					</ul>
					<div class="fixedbtn fr mtop15">
						<a class="btn btn-suc"
							href="membersecuritycenter_securityCenter.action">进入安全中心</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

    $(document).ready(function() {
        setPageType('.men-account', '.men-account-info ');
    })


    $('.panel-special').hover(function() {
        var color = $(this).css("border-top-color");
        $(this).css('border-color', color);
        $(this).addClass('panel-special-shadow');
        $(this).animate({
            left: '-5px',
            top: '-5px'
        });

    }, function() {
        var color = $(this).css("border-top-color");
        $(this).css('border-color', '#ddd');
        $(this).css('border-top-color', color);
        $(this).removeClass('panel-special-shadow');
        $(this).animate({
            left: '0px',
            top: '0'
        });

    })


    var c, _ = Function;
    with (o = document.getElementById("scrollbox")) { innerHTML += innerHTML; onmouseover = _("c=1"); onmouseout = _("c=0"); }
    (F = _("if(#%30||!c)#++,#%=o.scrollHeight>>1;setTimeout(F,#%30?10:1500);".replace(/#/g, "o.scrollTop")))();



</script>

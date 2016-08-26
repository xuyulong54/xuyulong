<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headUrl.jsp" %>
<script type="text/javascript">
	$(function() {
	    navSlide();
		$("#dropmeun select").change(function() {
			var url = $(this).val();
			location.href = url;
		});
	});

	//点击导航栏，列出菜单。
    function changePageType(FirstClassName, SeconClassName) {

        if(typeof(SeconClassName)=='undefined'){
          $(FirstClassName).siblings().removeClass('active');
          $(FirstClassName).addClass('active');
        }
        /*一级菜单状态*/
        else{
        $(FirstClassName).siblings().find('a').removeClass('active');
        $(FirstClassName).find('a').addClass('active');


        /*二级菜单状态*/
        $(SeconClassName).siblings().find('a').removeClass('active');

        /*二级菜单显示和隐藏*/
        $(SeconClassName).parents("div").addClass('block');
        $(SeconClassName).parents("div").siblings().removeClass('block');



        /*滑动条状态*/
        $(FirstClassName).siblings().find('a').removeClass('current');
        $(FirstClassName).find('a').addClass('current');

        $(".nav-current").css('display', 'block');
        var itemW = $(".nav").find('.current').innerWidth(); //默认当前位置宽度
        var defLeftW = $(".nav").find('.current').position().left;  //默认当前位置Left值
        //添加默认下划线
        $(".nav-current").css({ width: itemW, left: defLeftW });

    }
    }
</script>
   <div class="header">
    <div class="headerContent">
       <div class="notice"><span class="noticeTitle"> ${currentUser.loginName} 您好！欢迎登录${COMPANY_FOR}支付平台会员版 </span> </div>
        <div class="links">
            <a href="login_loginout.action">退出 </a>
        </div>
        <div class="clear"></div>
   </div>
</div>



 <div class="nav100">
        <div class="nav">
            <ul>
                <!-- <li class="nav-logo"></li> -->
                <li style="width:159px; height:78px; background:url('<%=path %>${COMPANY_LOGO }') no-repeat; background-position:50%;"></li>
                <li class="men-account mleft30"><a onclick="changePageType('.men-account', '.men-account-info ');" class="current">我的账户</a></li>
                <li class="men-receivable"><a onclick="changePageType('.men-receivable', '.men-receivable-transfer ');" >我的收款</a></li>
                <li class="men-pay"><a onclick="changePageType('.men-pay', '.men-pay-payment ');">我的付款</a></li>
                <li class="men-security"><a onclick="changePageType('.men-security', '.men-security-info ');">安全中心</a></li>
                <li class="men-help"><a href="article_listArticle.action?type=3" >帮助中心</a></li>

            </ul>


            <div class="nav-current">
            </div>
        </div>
        <div class="subnav100">
            <div class="subnav block">
                <ul>
                    <li class=" men-account-info "><a href="memberaccount_viewAccount.action">账户信息</a></li>
                      <li class=" men-account-search"><a href="memberaccount_listAccountHistory.action" >账户明细查询</a></li>
                    <li class=" men-account-bsinfo"><a href="memberinfo_viewMember.action">基本信息</a> </li>
                    <li class=" men-account-card"><a href="bankCardBind_viewBankCard.action">银行卡管理</a></li>
                </ul>
            </div>
            <div class="subnav">
                <ul>
                    <li class="men-receivable-transfer"><a href="memberReceiveTransfer_listReceiveTransfer.action">转账记录查询</a></li>
                    <li  class="men-receivable-recharge"><a href="memberReceiveRecharge_listReceiveRechargeRecord.action">充值记录查询</a></li>
                    <li class="men-receivable-withdraw"><a href="withdraw_listRemittanceRecordUI.action">提现记录查询</a></li>
                </ul>
            </div>
            <div class="subnav">
                <ul>
                    <li class="men-pay-payment"><a href="memberPayPaymentOrder_listPaymentOrder.action">支付记录查询</a></li>
                    <li  class="men-pay-transfer"><a href="memberPayTransfer_listTransfer.action">转账记录查询</a></li>
                </ul>
            </div>
            <div class="subnav">
                <ul>
                    <li class="men-security-info"><a href="membersecuritycenter_securityCenter.action">安全中心</a></li>
                </ul>
            </div>
            <div class="subnav">
                <ul>
                    <li class="men-help-faq"><a href="article_listArticle.action?type=3">常见问题</a></li>
                    <li class="men-help-score"><a href="memberscore_viewScore.action">我的积分</a></li>
                </ul>
            </div>

        </div>
    </div>


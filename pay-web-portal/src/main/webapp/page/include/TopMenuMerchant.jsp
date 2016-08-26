<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
	<%@include file="/page/include/headUrl.jsp" %>
		<script type="text/javascript">
			$(function () {
			    navSlide();
				$("#dropmeun select").change(function () {
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
				<div class="notice">
					<span class="noticeTitle">
				${userOperator.loginName}，欢迎登录${COMPANY_FOR}支付平台 </span>
				</div>
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
                <li class="mer-account "><a onclick="changePageType('.mer-account', '.account ');" class="current">我的账户</a></li>
             	<li class="mer-receivable"><a onclick="changePageType('.mer-receivable', '.receivable ');">我的收款</a></li>
              	<li class="mer-pay"><a onclick="changePageType('.mer-pay', '.payment ');" >我的付款</a></li>
                <li class="mer-security"><a onclick="changePageType('.mer-security', '.mer-security-info');">安全中心</a></li>
                <li class="mer-system"><a onclick="changePageType('.mer-system', '.system');" >系统管理</a></li>
                <li class="mer-publicinfo"><a onclick="changePageType('.mer-publicinfo', '.publicinfo');" >公共信息</a></li>
            </ul>
            <div class="nav-current">
            </div>
        </div>
        <div class="subnav100">
         <!-- 我的账户 -->
            <div class="subnav block">
                <ul><li class="account"></li>
                    <li class=" mer-account-info "><a href="merchantaccount_viewAccount.action">账户信息</a></li>
                    <li class=" mer-account-bsinfo"><a href="merchantinfo_viewMerchant.action">基本信息</a> </li>
        <z:permission value="Account:History:List">
				<li class=" mer-account-search "><a href="merchantaccount_listAccountHistory.action" class="ada-transfer" >账户明细查询</a></li>
       </z:permission>
                </ul>
            </div>

            <!-- 我的收款 -->
            <div class="subnav">
                <ul>
              <li class="receivable"></li>
				<z:permission value="Receive:PaymentOrder:List">
						<li class=" mer-receivable-payment ">
							<a href="merchantReceivePaymentOrder_listReceivePaymentOrder.action">支付记录查询</a>
						</li>
				</z:permission>
				<z:permission value="Receive:TransferRecord:List">
						<li class="mer-receivable-transfer"><a
							href="merchantReceiveTransfer_listReceiveTransfer.action">转账记录查询</a>
						</li>
				</z:permission>
				
				<z:permission value="Receive:RechargeRecord:List">
						<li class="mer-receivable-recharge"><a
							href="merchantReceiveRecharge_listReceiveRechargeRecord.action">充值记录查询</a>
						</li>
				</z:permission>
			</ul>
            </div>
            <!-- 我的付款 -->
            <div class="subnav">
                <ul>
               <li class="payment"></li>
					<z:permission value="Pay:PaymentOrder:List">
						<li class="mer-pay-payment"><a
							href="merchantPayPaymentOrder_listPaymentOrder.action">支付记录查询</a>
						</li>
					</z:permission>
					<z:permission value="Pay:TransferRecord:List">
						<li class="mer-pay-transfer">
							<a href="merchantTransferRecord_listTransfer.action">转账记录查询</a>
						</li>
					</z:permission>
			</ul>
            </div>
            <div class="subnav">
                <ul>
                   <li class="mer-security-info"><a href="merchantsecuritycenter_securityCenter.action">安全中心</a>
                </ul>
            </div>
            <div class="subnav">
                <ul>
               <li class="system"></li>
                   <z:permission value="Pms:Operator:View">
					<li class="mer-system-operator"><a href="permission_listMerchantOperator.action">操作员管理</a></li>
					</z:permission>
                </ul>
            </div>
            <div class="subnav">
                <ul>
                <li class="publicinfo"></li>
					<z:permission value="CommonInfo:Key:View">
						<li class="mer-publicinfo-keymanage">
							<a href="merchantKey_showMerchantKeyUI.action">密钥管理</a>
						</li>
					</z:permission>
				</ul>
             </div>
    </div>
    </div>


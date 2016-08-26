<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>安全中心</title>
<script>
$(function(){
setPageType('.navSecurity');//判断页面属于哪类导航项
})
</script>
</head>
<body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
   <div class="content security">
        <span class="text">${COMPANY_FOR }支付平台</span>
        <img src="<%=path%>/statics/themes/default/images/securityBanner.jpg" />
       <div class="wrap">
        <h3>安全产品</h3>
        <div class="box">
                <img src="statics/themes/default/images/ico_SQuestion.png" class="securityBoxImg"/>
                <div class="description">
                <h4>安全保护问题</h4>
                <p>安全保护问题将作为您通用的身份校验，可用于找回登录密码、找回支付密码等。</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index1.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
         <div class="box">
                <img src="<%=path%>/statics/themes/default/images/ico_Dpwd.png" class="securityBoxImg"/>
                <div class="description">
                <h4>双重密码</h4>
                <p>${COMPANY_FOR}支付提供登录密码和支付密码双重密码保护。即使不慎泄露其中一项密码，账户资金依然能够获得保护。</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index2.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
         <div class="box">
                <img src="<%=path%>/statics/themes/default/images/ico_control.png" class="securityBoxImg"/>
                <div class="description">
                <h4>安全控件</h4>
                <p>${COMPANY_FOR}支付提供密码安全控件，该安全控件实现了在SSL加密传输基础上对用户的关键信息进行非对称加密，可以有效防止木马程序截取键盘记录.</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index3.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
         <div class="box">
                <img src="<%=path%>/statics/themes/default/images/ico_Certificate.png" class="securityBoxImg"/>
                <div class="description">
                <h4>数字证书</h4>
                <p>数字证书是用来标识用户身份信息的安全凭证。在证书的保护下，进行付款、提现等重要操作时，系统会验证电脑上是否安装了数字证书，并且验证证书的有效性，确保账户和资金的安全。</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index4.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
         <div class="box">
                <img src="<%=path%>/statics/themes/default/images/ico_SWeb.png" class="securityBoxImg"/>
                <div class="description">
                <h4>网站防钓鱼</h4>
                <p>钓鱼网站是欺诈性的网站，让消费者误以为在正规的网站进行购物，从而盗取消费者的银行卡号及密码信息。为了保障用户的安全，${COMPANY_FOR}支付平台提供设置预留信息验证网站真伪，实现防钓鱼功能。</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index5.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
         <div class="box">
                <img src="<%=path%>/statics/themes/default/images/ico_Monitor.png" class="securityBoxImg"/>
                <div class="description">
                <h4>实时智能监控</h4>
                <p>${COMPANY_FOR}支付依托强大的风险监控中心，7*24小时实时监控系统运行情况，提供快速完备的安全预警机制和事件响应体系流程，来保障您的交易。</p>
                </div>
                <div class="indexImg">
                <span >安全指数：</span>
                <img src="<%=path%>/statics/themes/default/images/index6.jpg"  />
                <div class="clear"></div>
                </div>
                <div class="clear"></div>
        </div>
    </div>
</div>
   <div class="clear"></div>
 <jsp:include page="../../foot.jsp" />
  </body>
</html>

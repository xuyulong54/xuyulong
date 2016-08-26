<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>
<div class="header">
    <div class="headerContent">
        <div class="notice"><a href="article_listArticle.action?type=5" class="noticeTitle">企业公告：</a>
       <marquee direction="infinite"  scrollamount="5" style="width:500px; height:34px; line-height:34px;">
					<c:forEach items="${recordListNotice}" var="notice">
						<a href="article_viewArticle.action?id=${notice.id}" style="color:#B9BABE; text-decoration: none;float:left">${notice.title}&nbsp;&nbsp;</a>
					</c:forEach>
					</marquee>
		</div>
        <div class="links">
            <a href="article_listArticle.action?type=3">常见问题</a>
            <a href="login_loginUI.action" class="noline">登录</a>
            <c:if test="${PORTAL_IS_REGISTER }"><a href="merchantRegister_checkLoginNameUI.action">注册</a></c:if>
        </div>
        <div class="clear"></div>
   </div>
</div>
<div class="clear"></div>
<div class="navWrap">
     <div class="newNav">
     <div class="logo">
     	<%-- <img src="<%=path%>${COMPANY_LOGO }" /> --%>
     	<img src="<%=path%>${COMPANY_LOGO }" />
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

</body>
</html>

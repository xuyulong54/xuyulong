<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>网站地图</title>
  </head>
  <body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
   <div class="content websitemap">
   <img src="<%=path%>/statics/themes/default/images/title2.jpg" />
    <div class="box">
        <h3 class="title">网站地图</h3>
    
        <div class="boxa">
        <h3>首页</h3>
        <p>
        <a href="<%=path%>/login_loginUI.action">登录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
        <a href="<%=path%>/memberRegister_checkLoginNameUI.action">注册</a><br />
        <a href="<%=path%>/article_listArticle.action?type=5">企业公告</a>
        <a href="<%=path%>/article_listArticle.action?type=1">企业动态</a>
        <a href="<%=path%>/article_listArticle.action?type=2">诚聘英才</a>
        <a href="<%=path%>/article_partner.action">合作伙伴</a>
        </p>
        </div>
        <div class="boxa boxb">
        <h3>商户服务</h3>
        <p>
        <a href="<%=path%>/article_gwService.action">商户服务</a>
        </p>
        </div>
        <div class="boxa">
        <h3>${COMPANY_FOR}产品</h3>
        <p>
        <a href="<%=path%>/article_listArticle.action?type=4">推荐产品</a>
        <a href="<%=path%>/article_listArticle.action?type=4">行业解决方案</a><br />
        
        </p>
        </div>
        <div class="boxa boxb">
        <h3>安全中心</h3>
        <p>
        <a href="<%=path%>/article_securityCenter.action">安全中心</a>
        </p>
        </div>
        <div class="boxa">
        <h3>客服中心</h3>
        <p>
        <a href="<%=path%>/article_listArticle.action?type=3"> 常见问题</a>
        <a href="<%=path%>/article_aboutUs.action">  关于我们</a>
        <a href="<%=path%>/article_contactUs.action">  联系我们</a>
        </p>
        
        </div>
        <div class="clear"></div>
    </div>
</div>
<div class="clear"></div>
<jsp:include page="../../foot.jsp" />
  </body>
</html>

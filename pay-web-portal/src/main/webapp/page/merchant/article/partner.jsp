<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>合作伙伴</title>
  </head>
  <body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
    <div class="content partner">
     <img src="<%=path%>/statics/themes/default/images/title2.jpg" />
     <div class="box">
        <h3 class="title">合作银行</h3>
        <div class="boxa">
        <ul>
        <li><img src="<%=path%>/statics/themes/default/images/bank2.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank3.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank4.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank5.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank6.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank7.jpg" /></li>
        <li class="nomargin"><img src="<%=path%>/statics/themes/default/images/bank8.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank9.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank10.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank11.jpg" /></li>
        <li class="none"><img src="<%=path%>/statics/themes/default/images/bank1.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bank12.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/bankgd.jpg" /></li>
        </ul>
        <div class="clear"></div>
        </div>
     </div>
     <c:if test="${COMPANY_FOR != '迅时'}">
          <div class="box">
        <h3 class="title">合作商户</h3>
        <div class="boxa">
        <ul>
        <!-- <li><img src="<%=path%>/statics/themes/default/images/merchant1.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant2.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant3.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant4.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant5.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant6.jpg" /></li>

        <li class="nomargin"><img src="<%=path%>/statics/themes/default/images/merchant7.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant8.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant9.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant10.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/merchant11.jpg" /></li> -->
        <li><img src="<%=path%>/statics/themes/default/images/cw.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/gtwg.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/sxwg.jpg" /></li>
        <li><img src="<%=path%>/statics/themes/default/images/mcwg.jpg" /></li>
        </ul>
        <div class="clear"></div>
        </div>
     </div>
     </c:if>

</div>

<div class="clear"></div>
<jsp:include page="../../foot.jsp" />
  </body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/page/inc/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="keywords" />
    <meta name="description" />
    <title>支付成功提示</title><!–[if lte IE]>
    <![endif]–>
	<%@include file="/page/inc/headScript.jsp"%>
</head>
<body>
    <div class="reg_top100">
        <div class="reg_top2">
            <ul>
                <li class="title">| 信息提示</li>
            </ul>
        </div>
    </div>
      <div class="PayState">
        <table class="tbl">
            <tr>
                <td width="80px">
                    <img src="<%=path%>/statics/images/susess.png" />
                </td>
                <td>
                    <div class="title2">
                        支付成功！感谢您使用${COMPANY_FOR }支付平台</div></br>
                    <div>
                        返回<a href="${returnUrl}">商户页面</a></div>
                </td>
            </tr>
        </table>
        <hr />
        <div class="title">
            商品信息</div>
        <div style="font-size: 14px">
            <ul>
                <li >商户名称： ${merchantName}</li>
                <li >商品名称： ${productName}</li>
                <li >订单金额： <span>${amount}</span> 元</li>
                <li >订单号： ${orderNo}</li>
                <li >下单时间：<s:date name="createTime" format="yyyy-MM-dd HH:mm:ss"/></li>
            </ul>
        </div>
    </div>


    <div class="ht1"></div>

    <jsp:include page="../foot.jsp" />
  </body>
</html>

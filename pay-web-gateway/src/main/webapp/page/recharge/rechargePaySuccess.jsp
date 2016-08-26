<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/page/inc/taglib.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="utf-8">
    <meta name="keywords" />
    <meta name="description" />
    <title>充值成功提示</title><!–[if lte IE]>

    <![endif]–>
	<%@include file="/page/inc/headScript.jsp"%>
	<link rel="Stylesheet" href="<%=path%>/statics/css/GatewayExceptionError.css" />
</head>
<body>
  <%@include file="/page/head_msg.jsp"%>
      <div class="PayState">


		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">充值成功！感谢您使用${COMPANY_FOR}支付平台</li>

			</ul>
		</div>


		<div class="title">
            订单信息</div>
        <div style="font-size: 14px">
            <ul>
                <li >订单号： ${orderNo}</li>
                <li >交易流水号： ${trxNo}</li>
                <li >支付金额： <span><fmt:formatNumber value="${payAmount}" pattern="#0.00"></fmt:formatNumber></span> 元</li>
                <li >银行订单号： ${bankOrderNo}</li>
                <li >充值时间：<s:date name="paySuccessTime" format="yyyy-MM-dd HH:mm:ss"/></li>
            </ul>
        </div>
    </div>


    <div class="ht1"></div>



<jsp:include page="../foot.jsp" />

  </body>
</html>

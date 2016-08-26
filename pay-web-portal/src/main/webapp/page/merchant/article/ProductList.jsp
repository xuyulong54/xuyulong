<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<html>
<head>
  <%@include file="/page/include/headScript.jsp"%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>产品发布管理</title>
</head>
<body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
  <div class="Productbanner"></div>
	<div class="NewsMain">
    <div class="left">
      <div class="conection1">
        <div class="NewsTile">申请流程</div>
        <div class="LCbox" style="margin-top:10px">
          <span>1</span> &nbsp; 注册${COMPANY_FOR}账户<br />
          提交企业资料/签订合同
        </div>
        <div class="LCbox_img"></div>
        <div class="LCbox">
          <span>2</span> &nbsp; 申请开通相关产品<br />
          协议确定相关费率信息
        </div>
        <div class="LCbox_img"></div>
        <div class="LCbox">
          <span>3</span> &nbsp; ${COMPANY_FOR}审核商户信息<br />
        </div>
        <div class="LCbox_img"></div>
        <div class="LCbox">
          <span>4</span> &nbsp; 审核通过，上线运行<br />
        </div>
        <br style="clear: both;" />
      </div>
      <div class="ht"></div>
      <div class="conection1">
        <div class="NewsTile">客服热线</div>
        <div class="conIMG"></div>
        <div class="cont">
          <p>客服电话：${COMPANY_TEL }</p>
          <p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
        </div>
        <br  style="clear:both;"/>
      </div>
      <div class="ht"></div>
      <div class="conection1">
        <div class="NewsTile">联系地址</div>
        <div class="conIMG2"></div>
        <div class="cont">
          <p>地址：${COMPANY_ADDRESS }</p>
          <p>邮编：510050</p>
        </div>
        <br  style="clear:both;"/>
      </div>
      <br style="clear: both" />
    </div>
    <div class="right">
      <div class="NewsTile_bg">
        <div class="NewsTile">产品列表</div>
      </div>
      <div style="height: 10px"></div>
      <div class="article">
      <table id="tbl" cellpadding="0" cellpadding="0" class="tbl">
         <s:iterator value="recordList" status="st">
         <tr>
           <td class="lf_td">
             <a href="article_viewArticle.action?id=${id}">${title} </a>
           </td>
           <td class="rt_td"><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd"/>
           </td>
         </tr>
         </s:iterator>
       </table>
        <br style="clear: both;"/>
      </div>
      <br style="clear: both" />
    </div>
    <br style="clear: both" />
  </div>
	<div class="ht1"></div>
	<jsp:include page="../../foot.jsp" />
  <script>
    $(function(){
     setPageType('.navProduct');//判断页面属于哪类导航项
    })
  </script>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数字证书下载成功</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">数字证书</div>
</div>

      
      
       <div class="memThirdSetpFlow mtop10">
        </div>
        <div class="memFlowTex">
        <ul>
            <li class="green">
               数字证书申请
            </li>
            <li class="green" style="width: 420px;">
                填写申请信息并验证身份
            </li>
           
            <li class="red">
                申请成功
            </li>
            </ul>
        </div>
        <div class="h10"></div>
        
            
	 <div class="tipsBox" style="min-height:100px;"> 	
		<div class="tipsTitle">
			<ul>
				<li class="TipsImg SuccTipsImg"></li>
				<li class="tipTxt markGreen">   恭喜您，数字证书申请成功！</li>

			</ul>
		</div>
		<div class="tipsCont"> 
		<p>    如果您重装系统或在其他电脑上使用账户资金，需要在其他电脑上也安装数字证书。</p>
		<p>   您可以 <a href="merchantsecuritycenter_listCA.action">查看证书 </a>&nbsp &nbsp &nbsp <a href="merchantsecuritycenter_securityCenter.action">安全中心 </a></p>
		</div>
		<div class="clear"></div>
		</div>
		
        <div class="clear"></div>
    </div>
    <div class="clear"></div>
    </div>
<jsp:include page="../../foot.jsp" />

<!-- 成功了！！ -->
</body>
</html>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })
</script>
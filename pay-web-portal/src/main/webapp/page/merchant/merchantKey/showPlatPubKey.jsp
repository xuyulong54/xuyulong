<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/page/include/taglib.jsp" %>
<%@include file="/page/include/headScript.jsp" %>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>密钥管理</title>
</head>
<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { 

 setPageType('.mer-publicinfo', '.mer-publicinfo-keymanage');
 
  })

</script>

<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="clear"></div>
			<div class="headline">
				<div class="title">密钥管理</div>
			</div>

			<div class="ada-wrap frm-comon mtop20" >
				<p class="clearfix">
					<label>	商户编号：</label>
					<span>${currentUser.userNo}</span>
				</p>
				
				<div id="platDiv">
					<p class="clearfix">
					<label> &nbsp; </label>
						<span class="text-warning">温馨提示：请谨记平台公钥，可以使用该公钥对下面的明文及密文进行验签实验</span>
					</p>
					
					<p class="clearfix" id="platformPubKeyP">
						<label>平台公钥：</label>
						<textarea rows="5" cols="40" id="platformPubKeyPText" readonly="readonly" name="platformPubKeyPText" >
							${platformPubKey }
						</textarea>
					</p>
					
					<p class="clearfix" id="platformSourceStr">
						<label>平台签名明文：</label>
						<input type="text" style="width: 300px;" disabled="disabled" name="platformSourceStrText" value="${sourceStr }">
						
					</p>
					
					<p class="clearfix" id="platformSignStr">
						<label>平台签名后密文：</label>
						<textarea rows="5" cols="40" id="platformSignStrText" readonly="readonly" name="platformSignStrText" >
							${signStr }
						</textarea>
					</p>
					
					<p class="clearfix">
						<label> &nbsp; </label>
						<a href="merchantKey_showMerchantKeyUI.action"  style="margin-left: 28px; color: blue ; ">返回</a>
					</p>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

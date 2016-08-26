<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>申请数字证书</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript" src="<%=path%>/statics/cfca/js/onload.js"></script>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/install.js"></script>
<script>
	/*页面分类*/
	$(document).ready(function() {
		setPageType('.mer-security', '.mer-security-info ');
	})
</script>

<body>
	<div id="FakeCryptoAgent"></div>
	<!-- 不能删除这个DIV -->
	<div id="tr_DigestAlgorithm" style="display: none"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<div class="container">
		<div class="bd-container">
			<div class="headline">
				<div class="title">数字证书</div>
			</div>
			<div class="clear"></div>
			<div class="frm-comon mtop10">
				<div class="h10"></div>

				<div class="memFirstSetpFlow"></div>
				<div class="memFlowTex">
					<ul>
						<li class="red">数字证书申请</li>
						<li style="width: 420px;">填写申请信息并验证身份</li>

						<li>申请成功</li>
					</ul>
				</div>
				<div class="h10"></div>


				<div class="accounts-yellow">
					<div class="fl" style="width:140px;">
						<i class="iconfont"
							style="color:#5EB95A;font-size:80px;margin-left: 10px;">
							&#xe606;</i>
					</div>
					<div class="pWrap cawarp">

						<p>
							<i class="iconfont"> &#xe607;</i>
							数字证书是用来标识用户身份信息的安全凭证。在证书的保护下，进行付款、提现等重要操作时，系统会验证电脑上是否安装了数字证书，并且验证证书的有效性，确保账户和资金的安全。
						</p>
						<p>
							<i class="iconfont"> &#xe607;</i> 申请成功后，在浏览器上安装便可使用
						</p>
						<p>
							<i class="iconfont"> &#xe607;</i> 当您换电脑或重装系统时，只需用手机校验即可重新安装数字证书
						</p>
					</div>
					<div class="clear"></div>

				</div>
				<div class="btn" style="margin-left: 140px;">
					<div id="install" style="display: none; line-height: 30px;"></div>
					<div  id="instlch" style="display: none;line-height: 30px;"></div>
					<z:permission value="SecurityCenter:Ca">

						<input id="Button1" type="button" class="btn btn-primary"
							value="申请数字证书"
							onclick="applyCert('merchantca_addCAImInfoUI.action');" />

					</z:permission>
				</div>

				<div class="text-muted"
					style="margin-left: 140px; margin-bottom: 100px;">
					<c:if test="${isMobileAuth == 100}">
											检测到您账户绑定的手机为（${bindMobileNo}），请确保该手机通讯正常。
										</c:if>
					<c:if test="${isMobileAuth == 101}">
											检测到您账户未绑定手机，请先
						<a class="link-color" href="merchantmobilebind_bindingMobileUI.action">绑定手机</a>
					</c:if>
				</div>
			</div>
			<div class="clear"></div>
		</div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>

</html>

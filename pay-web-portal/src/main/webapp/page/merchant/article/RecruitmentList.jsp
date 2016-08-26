<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<%@include file="/page/include/headScript.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
$(function(){
	   $("#tbl tr:odd").hide();
	   $("#tbl tr:even").css("cursor","pointer").css('background','#fafafa');
	   $("#tbl tr:even").bind("click",function(event){
		   $(this).next("tr").toggle();
	   });
});
</script>

<title>人才招聘管理</title>
</head>
<body>
<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
	<div class="RecruitmentBanner"></div>
	<div class="NewsMain">
		<div class="left">
			<div class="conection1">

				<div class="NewsTile">招聘指南</div>
				<div class="conIMG3"></div>
			<div class="cont">
				<div class="ZPTile">招聘简历投递：</div>
				<p>邮件地址：${COMPANY_HR_EMAIL }</p>
				<p>邮件标题：姓名+应聘职位</p>
		<!-- 		<div class="btn">
		<span class="commonBtn"><span class="btn_lfRed">
			<input id="Button1" type="button" class="btn_rtRed" value="发送邮件" />
			</span></span>
		</div> -->
			</div>
				<br style="clear: both;" />
			</div>
			<div class="ht"></div>
	<!-- 		<div class="conection1">
		<div class="NewsTile">客服热线</div>
		<div class="conIMG"></div>
		<div class="cont">
			<p>客服电话：${COMPANY_TEL }</p>
			<p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
		</div>
		<br style="clear:both;" />
	</div>
	<div class="ht"></div> -->
			<div class="conection1">
				<div class="NewsTile">联系地址</div>
				<div class="conIMG2"></div>
				<div class="cont">
					<p>地址：${COMPANY_ADDRESS }</p>
					<p>邮编：510050</p>
				</div>
				<br style="clear:both;" />
			</div>
			<br style="clear: both" />
		</div>
		<div class="right" id="div_1">
			<div class="NewsTile_bg">
				<div class="NewsTile">招聘信息列表</div>
			</div>
			<div style="height: 10px"></div>

			<div class="article">
				<table id="tbl" cellpadding="0" cellpadding="0" class="tbl">


					<s:iterator value="recordList" var="item" status="st">

							<tr>
								<td class="lf_td"><s:property escape="false" value="#item.title"/></td>
							</tr>
							<tr>
								<td><div ><s:property escape="false"  value="#item.content"/></div></td>
							</tr>

					</s:iterator>
                </table>

				<br style="clear: both;" />
			</div>
		</div>

		<br style="clear: both" />
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>

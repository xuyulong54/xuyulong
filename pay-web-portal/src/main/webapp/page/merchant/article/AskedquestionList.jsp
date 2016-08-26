		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
	<%@include file="/page/include/headScript.jsp"%>
		<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<title>常见问题列表</title>
			<script>
			$(function(){
			var userType='${currentUser.userType}';
			if(userType==3&&userType!=''){
		    setPageType('.men-help', '.men-help-faq');
			}
			else if(userType==''){
			setPageType('.navCallcenter');//判断页面属于哪类导航项
			}
			})
			</script>
		</head>

		<body>
			<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
			<div class="h10"></div>
			<div class="NewsMain">
				<div class="left">
					<div class="conection1">
						<div class="NewsTile">
							客服热线
						</div>
						<div class="conIMG">
						</div>
						<div class="cont">
							<p>客服电话：${COMPANY_TEL }</p>
							<p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
						</div>
						<br style="clear:both;" />
					</div>
					<div class="h10">
					</div>
					<div class="conection1">
						<div class="NewsTile">
							联系地址
						</div>
						<div class="conIMG2">
						</div>
						<div class="cont">
							<p>地址：${COMPANY_ADDRESS }</p>
							<p>邮编：510050</p>
						</div>
						<br style="clear:both;" />
					</div>
					<br style="clear: both" />
				</div>
				<div class="right">
					<div class="NewsTile_bg">
						<div class="NewsTile">
							常见问题列表</div>
					</div>
					<div style="height: 10px">
					</div>

					<div class="article">
						<table id="tbl" cellpadding="0" cellpadding="0" class="tbl">

							<s:iterator value="recordList" status="st">
								<tr>
									<td class="lf_td">
										<a href="article_viewArticle.action?id=${id}">${title} </a>
									</td>


								</tr>
							</s:iterator>
						</table>
						<br style="clear: both;" />
					</div>


					<br style="clear: both" />
				</div>
				<br style="clear: both" />
			</div>
			<jsp:include page="../../foot.jsp" />
		</body>

		</html>

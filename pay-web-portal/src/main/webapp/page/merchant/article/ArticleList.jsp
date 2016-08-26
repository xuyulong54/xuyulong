<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>

		<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
		<html>

		<head>
			<%@include file="/page/include/headScript.jsp" %>
				<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				<title>企业动态</title>
<!--
				<script language="javascript" type="text/javascript">
					window.onload = function showtable() {
						var tablename = document.getElementById("tbl");
						var li = tablename.getElementsByTagName("tr");
						for (var i = 0; i <= li.length; i++) {
							if (i % 2 == 0) {
								li[i].style.backgroundColor = "#fafafa";
							} else li[i].style.backgroundColor = "#FFFFFF";
						}
					}
				</script> -->

		</head>

		<body>
			<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
			<div class="Newsbanner">
			</div>
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
					<div class="ht">
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
				<div class="rightNewsNav">
					<ul>
						<li><a class="firstLi" href="article_listArticle.action?type=1#">企业动态</a>
						</li>
						<li><a class="secondLi" href="article_listArticle.action?type=5#">企业公告</a>
						</li>
					</ul>
				</div>
				<div class="rightNews">
				<div style="height: 10px">
				</div>

				<div class="article">
					<table id="tbl" cellpadding="0" cellpadding="0" class="tbl">

						<s:iterator value="recordList" status="st">
							<tr>
								<td class="lf_td">
									<a href="article_viewArticle.action?id=${id}">${title} </a>
								</td>
								<td class="rt_td">
									<fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd" />
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

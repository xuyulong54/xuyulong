<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/headScript.jsp" %>
			<%@include file="/page/include/taglib.jsp" %>
				<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
				<html>
                <script type="text/javascript">
                $(function(){
                 setPageType('.navProduct');//判断页面属于哪类导航项
                })
                </script>
				<head>
					<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
					<title>产品</title>
					<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
				</head>

				<body>
					<jsp:include page="/page/include/TopMenuLogged.jsp"></jsp:include>
					<div class="content gwProcuct">
						<img src="statics/themes/default/images/gwProducts.jpg" />
						<div class='wrap'>
							<div class="left">
								<div class="boxA">
									<h3>申请流程</h3>
									<ol>
										<li class="li1">注册${COMPANY_FOR}账户提交企业资料/签订合同</li>
										<li class="li2">申请开通相关产品 协议确定相关费率信息</li>
										<li class="li3">${COMPANY_FOR}审核商户信息</li>
										<li class="li4">审核通过，上线运行</li>
									</ol>
								</div>
								<div class="boxB">
									<h3>客服热线</h3>
									<img src="statics/themes/default/images/kefu.jpg" />
									<p>客服电话：${COMPANY_TEL }</p>
									<p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
								</div>
								<div class="boxB">
									<h3>联系地址</h3>
									<img src="statics/themes/default/images/addressImg.jpg" />
									<p class="special">地址：${COMPANY_ADDRESS }</p>
									<p>邮编：510050</p>
								</div>
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
						</div>
						<div class="clear"></div>
					</div>
					<div class="clear"></div>
					<jsp:include page="../../foot.jsp" />
				</body>

				</html>

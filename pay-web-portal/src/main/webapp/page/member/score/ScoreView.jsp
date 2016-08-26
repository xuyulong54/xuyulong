<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>积分管理</title>
<script>

/*页面分类*/
$(document).ready(function() { setPageType('.men-help', '.men-help-score ');  })


	$(document)
			.ready(
					function() {

						$('.right .head img')
								.click(
										function() {
											if ($(this).attr('src') == '<%=path%>/statics/themes/default/images/ada-icon1.gif') {
												$(this)
														.attr('src',
																'<%=path%>/statics/themes/default/images/ada-icon2.gif');
											} else if ($(this).attr('src') == '<%=path%>/statics/themes/default/images/ada-icon2.gif') {
												$(this)
														.attr('src',
																'<%=path%>/statics/themes/default/images/ada-icon1.gif');
											}
											$(this).parent().siblings()
													.toggle();
											;
										});

					});
</script>
</head>
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="clear"></div>
	<div class="bd-container">
		<div class="jifen">
			<div class="left">
				<h3>积分信息</h3>
				<div class="info clearfix">
					<i class="iconfont">&#xe60e;</i>
					<span class="num">积分: <strong class="markRed">${loginScore}分</strong> </span>
					<p>${currentUserVo.userName }</p>
				</div>


				<h4>如何获得积分？</h4>
				<ul>
					<li>
						<!-- <p class="imgRegister"></p> -->
						<i class="iconfont">&#xe60f;</i>
						<p align="center">注册</p>
					</li>
					<li>
						<!-- <p class="imgLogin"></p> -->
						<i class="iconfont">&#xe610;</i>
						<p align="center">登录</p>
					</li>
					<div class="clear"></div>
				</ul>
				<div class="clear"></div>
			</div>
			<div class="right">
				<div class="introduce">
					<div class="head">
						<h3>积分介绍</h3>

						<img src="<%=path%>/statics/themes/default/images/ada-icon2.gif" />
						<div class="clear"></div>
					</div>
					<p>
						${COMPANY_FOR}积分，是用来记录用户在${COMPANY_FOR}支付平台操作的记录，用户的操作将获得相应的积分。用户的积分在从0开始累积，获取积分即可累积积分（如注册、签到等）。
					</p>
				</div>
				<div class="rule">
					<div class="head">
						<h3>积分规则</h3>
						<img src="<%=path%>/statics/themes/default/images/ada-icon2.gif" />
						<div class="clear"></div>
					</div>
					<table style=" border-collapse:collapse; text-align:center;"
						cellspacing="0" width="670px" border="1px" bordercolor="#CCCCCC">
						<tr>
							<th>积分发放场景</th>
							<th>具体现场</th>
							<th>分值</th>
						</tr>
						<tr>
							<td>每次登录</td>
							<td>每次登录获得积分</td>
							<td>2</td>
						</tr>
					</table>
					<div class="clear"></div>
				</div>
				<%--
				<div class="details">
					<div class="head">
						<h3>积分详情</h3>
						<img src="<%=path%>/statics/themes/default/images/ada-icon1.gif" />
						<div class="clear"></div>
					</div>

					<div class="more">
						<img src="<%=path%>/statics/themes/default/images/ada-img4.jpg" />
						<p>即将开放，敬请期待</p>
						<div class="clear"></div>
					</div>
				</div>
			--%>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<jsp:include page="../../foot.jsp" />
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>修改权限</title>
		<%@include file="/page/include/headScript.jsp" %>
			
	</head>

	<body>
		<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
		

		<div id="tabs1">
			<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
			<div class="main1box mtop15">
				<div class="main" id="main1">
					<ul class="block">
						<li>
							<form action="permission_editMerchantAction.action" method="post">

								<div class="tab_box frm-comon" style="min-height: 200px;">
									<input type="hidden" name="id" value="${id}" />

									<p class="clearfix">
										<label>权限名称:</label>
										<input type="text" name="actionName" value="${actionName }" />
										<span class="text-warning">${actionNameMsg}</span>
									</p>
									<p class="clearfix">
										<label>权限:</label>
										<input type="text" name="action" value="${action}" /> <span class="message">${actionMsg}</span>
									</p>
									<p class="clearfix">
										<label>描述:</label>

										<textarea style="resize:none;width:300px;height:60px;" name="desc">${remark}</textarea>
										<span class="text-warning">${descMsg}</span>
									</p>

									<p class="clearfix">
										<label> &nbsp; </label>
										
				<input type="submit" value="提 交" class="btn btn-primary" />
				
									</p>
									<div class="clear"></div>
								</div>
							</form>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="ht"></div>
		<jsp:include page="../../foot.jsp" />
	</body>

	</html>
<script type="text/javascript">
/*页面分类*/
 $(document).ready(function() { setPageType('.mer-system', '.mer-system-operator '); })
 </script>
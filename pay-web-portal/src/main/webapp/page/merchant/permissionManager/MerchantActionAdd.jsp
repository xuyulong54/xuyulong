<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

	<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加权限</title>
		<%@include file="/page/include/headScript.jsp" %>

	</head>

	<body>
		<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
		
		<form action="permission_addMerchantAction.action" method="post">
			<div id="tabs1">
				<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>
				<div class="main1box">
				<div class="frm-comon mtop15">
					<div class="main" id="main1">
						<ul class="block">
							<li>
								<div class="tab_box" style="min-height: 200px;">
									<p class="clearfix">
										<label>权限名称：</label>
										<input type="text" name="actionName" value="${merchantAction.actionName}" /> <span class="message">${actionNameMsg}</span>
									</p>
									<p class="clearfix">
										<label>权 限：</label> 
										<input type="text" name="action" value="${merchantAction.action}" /> <span class="message">${actionMsg}</span>
									</p>
									<p class="clearfix">
										<label>描 述：</label>
										<textarea style="resize:none;height:60px;" name="desc">${merchantAction.remark}</textarea>
									</p>
									<p class="clearfix">
										<label> &nbsp; </label>
										
									　<input type="submit" class="btn btn-primary" value="提 交" />
									
									</p>
									<div class="clear"></div>
								</div>
							</li>
						</ul>
					</div>
				</div>
			</div>
			</div>
		</form>
		<div class="ht1"></div>
		<jsp:include page="../../foot.jsp" />
	</body>

	</html>
	
	<script type="text/javascript">
/*页面分类*/
 $(document).ready(function() { setPageType('.mer-system', '.mer-system-operator '); })
 </script>
	
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
	<%@include file="/page/include/taglib.jsp" %>
		<style>
			.menu1box a {
				display: block;
			}
		</style>
		<div class="subNav">
			<div id="subNav" class="subNavLine">

				<z:permission value="Pms:Role:View">
					<a href="permission_listMerchantRole.action" title="查看角色" class="subMenu secondNav1" style="margin-left: 50px;">角色管理</a>
				</z:permission>
				<z:permission value="Pms:Operator:View">
					<a href="permission_listMerchantOperator.action" title="查看操作员" class="subMenu secondNav2"> 操作员管理</a>
				</z:permission>
				<z:permission value="Pms:OperatorLog:View">
					<a href="permission_listMerchantOperateLog.action" title="查看日志" class="subMenu secondNav3"> 日志管理</a>
				</z:permission>
			</div>
		</div>

		<script>
		/*页面分类*/
		 $(document).ready(function() { setPageType('.mer-system', '.mer-system-operator '); })
		 
			(function () {
				//alert(window.location.pathname);
				var path = window.location.pathname;
				var path = path.match(/permission_listMerchant([\w]*)\./)[1];

				switch (path) {
				case "Action":
					$("#subNav  a[title=查看权限]").addClass("subMenuHover");

					break;
				case "Operator":
					$("#subNav  a[title=查看操作员]").addClass("subMenuHover");

					break;
				case "Role":

					$("#subNav  a[title=查看角色]").addClass("subMenuHover");

					break;
				case "OperateLog":

					$("#subNav  a[title=查看日志]").addClass("subMenuHover");

					break;
				default:
					;
				}

			})();
			function setSecondNav(selector){
			$(selector).addClass('subMenuHover');
			$(selector).siblings().removeClass('subMenuHover');
			}
		</script>
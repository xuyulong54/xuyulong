<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript">
	(function() {
		alert("您的账号于${time}在其它地方登录，请及时修改密码！");
		logout();
	})();

	function logout() {
		top.location.href = "login_logout.action";
	}
</script>

<%--
<div class="pageContent">
	<div class="pageFormContent" layoutH="58" align="center">
		<br /> <br /> <br />
		<br /> <br /> <br />
		<h1 style="color:red;">登录超时，请重新登录！</h1>
	</div>
	<div class="formBar">
		<ul>
			<li><div class="buttonActive">
					<div class="buttonContent">
						<button type="button" onclick="logout();">确认</button>
					</div>
				</div>
			</li>
		</ul>
	</div>
</div>
--%>
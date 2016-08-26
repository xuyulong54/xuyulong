<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/headUrl.jsp" %>
<%@include file="/page/include/taglib.jsp"%>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/html5.js"></script>
<%--<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/style.css" />--%>
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/comon.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/base.css" />
<link rel="stylesheet" href="<%=path%>/statics/themes/default/css/usercommon.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/css/customAlert.css" />
<link rel="stylesheet" href="<%=path %>/statics/themes/default/iconfonts/iconfont.css" />
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=path %>/statics/js/jquery-form-2.33.js"></script>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/jquery.pngFix.pack.js"></script>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/dev.js"></script>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/calendar.js"></script>
<script type="text/javascript" src="<%=path %>/statics/js/common.js"></script>
<script type="text/javascript" src="<%=path %>/statics/div-plugin/js/jquery.boxy.js"></script>
<script type="text/javascript" src="<%=path %>/statics/formvalidator/formValidator-4.1.3.js"></script>
<script type="text/javascript" src="<%=path %>/statics/formvalidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/jquery.tabso_yeso.js"></script>
<script type="text/javascript" src="<%=path %>/statics/themes/default/js/customAlert1.js"></script>
 <script type="text/javascript" src="<%=path%>/statics/cfca/js/SignCertManager.js"></script>
 <script type="text/javascript">
	//获取服务器时间戳
	var ts="<%=System.currentTimeMillis()%>";
	//全局函数 获得支付密码
	 var funGetPassword =  function() {
		<c:choose>
		<c:when test="${USE_KEYBOARD }">
		var password = getPassInput("powerpass", ts, "errorMsg", "密码输入错误：");
		$("#password").val(password);
		</c:when>
		<c:otherwise>
		//获取密码密文
		var password = $("#password").val();
		</c:otherwise>
		</c:choose>
		if (password == null || password=='') {
			return null;
		}else{
			return password;
		}
	}
</script>
<!-- 加载密码控件 -->
<c:if test="${USE_KEYBOARD }">
<script type="text/javascript" src="<%=path %>/statics/js/writeObject.js"></script>
<!-- 处理IE浏览器下id为powerpass控件的回车事件 -->
<script language="javascript" for='powerpass' event='EventReturn'>
	//将焦点放在id为login的标签上。
	document.getElementById("randomCode").focus();
</script>

<!-- 处理IE浏览器下id为powerpass控件的Tab事件 -->
<script language="javascript" for='powerpass' event='EventTab'>
	//将焦点放在id为login的标签上。
	document.getElementById("randomCode").focus();
</script>

<!-- 处理IE浏览器下id为powerpass控件的密码强弱度事件 -->
<script language="javascript" for='powerpass' event='EventDegree(arg1)'>
	var degree = "";
	if (arg1 == "W") {
		degree = "弱";
	} else if (arg1 == "M") {
		degree = "中";
	} else if (arg1 == "S") {
		degree = "强";
	}
</script>
</c:if>

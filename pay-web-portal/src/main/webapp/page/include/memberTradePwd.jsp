<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<script type="text/javascript">
	function load()
	{
		if (!isIE())
			//如果是非IE浏览器，则调用此函数，为控件添加事件处理函数。
			doAdd();
	}

	function doAdd()
	{
	    //获取对象
	    var powerpass = document.getElementById("powerpass");
	    //添加Password控件的回车事件，如果收到此事件，则触发OnPassEventReturn()函数
	    addEvent(powerpass, "EventReturn",OnPassEventReturn);
	  	//添加Password控件的Tab事件，如果收到此事件，则触发OnPassEventTab()函数
	    addEvent(powerpass, "EventTab",OnPassEventTab);

	  	//添加Password控件的密码强弱度事件，如果收到此事件，则触发OnEventDegree(arg1)函数
	    addEvent(powerpass, "EventDegree",OnEventDegree);
	}
</script>
</head>
<body>
	<p class="clearfix">
		<label>支付密码：</label>
		<span>
		<c:choose>
			<c:when test="${USE_KEYBOARD }">
				<input type="hidden" name="tradePwd" id="password" class="password"
					maxlength="20" />
				<script type="text/javascript">
					writePassObject("powerpass", {
						"width" : 173,
						"height" : 35,
						" margin-top" : 10,
						"accepts" : "[:graph:]+",
						"x" : -50,
						"maxlength" : 20,
						"wmode": "transparent"
					});
				</script>
			</c:when>
			<c:otherwise>
				<input type="password" name="tradePwd" id="password"
					class="password" maxlength="20" />
			</c:otherwise>
		</c:choose>
		</span>
		<a href="memberlookfortradepwd_tradePwdListWay.action"
			class='mtop10 link-color'> &nbsp;忘记支付密码？</a>
	</p>
	<p>
		<label for="">&nbsp;</label>
		<span class="markRed" id="errorMsg"></span>
	</p>
	<div class="clear"></div>


	<!-- 安装证书提示 -->
	<div id="MyDiv" class="popupBox">
		<input type="hidden" id="id"> <input type="hidden"
			id="operationType"> <input type="hidden" id="status">
		<div class="top">

			<a href="#" class="btnClost" onclick="CloseCADiv('#MyDiv');"></a>
		</div>
		<div class="cont">
			<div class="left-icon">
				<i class="iconfont">&#xe600;</i>
			</div>
			<div class="right-box">
				<h3 id="hint-title">温馨提示：</h3>
				<p id="p-hint">


				<font id="hint-content" class="font txt-color"> <label>
						您没有安装数字证书！</label><a href='memberca_listCA.action' class="link-color"><font>点击申请</font>
				</a> </font>
			</p>
			<p id="p-tradePwd" style="text-align: left;"></p>
			<p id="p-errorMsg">
				<strong> &nbsp; </strong><span id="errorMsg" class="font markRed"></span>
			</p>
         </div>


			<%--<p id="p-hint">


				<font id="hint-content" class="font"> <label>
						您没有安装数字证书！</label><a href='memberca_listCA.action' class="link-color"><font>点击申请</font>
				</a> </font>
			</p>
			--%>

			<div class="btnDiv">
				<input class="btn btn-primary closebtn" id="btn2" type="button"
					value="取  消" onclick="CloseCADiv('#MyDiv');" />
				<div class="clear"></div>
			</div>
			<div class="clear"></div>
		</div>
	</div>

	<div class="popupMask"></div>
	<!-- 安装证书提示 end -->
	<!-- 证书签名信息-->
	<p class="clearfix">
		<!-- 签名原文 -->
		<input type="hidden" id="textareaSignedContent" />
		<!-- 签名结果 -->
		<input type="hidden" name="textareaSignature" id="textareaSignature" />
	</p>
</body>
</html>
<script type="text/javascript">
var autoCA =new AutoCA('${IS_SN_USER}','${CURRENT_SN}','form1',$("#transferAmount").val(),$("#payeeAccountNo").val());

function subForm(){
		var password = funGetPassword();
		if (password == null || password=='') {
			alert("支付密码为空或长度不足");
			return;
		}
		//$("#tradePwd").val(password);
		autoCA.invoke();
}
</script>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改角色</title>
<%@include file="/page/include/headScript.jsp"%>
</head>
<script type="text/javascript">
	$(function() {
		setPageType('.mer-system', '.mer-system-operator ');/*页面分类*/
		setSecondNav('.secondNav1');
		validatorFrom();
		var str = "${owenedActionIds}";
		var array = new Array();
		array = str.split(",");
		for ( var i = 0; i < array.length; i++) {
			$("#" + array[i]).attr("checked", "checked");
		}
	})

	function submitForm() {
		  var str = "";
		  document.getElementById("errorMsg").innerHTML=str;
          $("#tradePwd").val(password);
            var textWarning= document.getElementById("textWarning");
            if (textWarning!=null){
              textWarning .innerHTML=str
            }             
		    $(".ada-checkbox :checkbox:checked").each(function() {
		      str += $(this).attr("id");
		      str += ",";
		    });
		    $("#selectVal").val(str);
		    if (str == '') {
		      $('.wronginfos').text('请至少选择一项权限');
		      $('.wronginfos').show();
		      return;
		    } else {
		      $('.wronginfos').hide();
		    }
		var password = funGetPassword();
		if (password == null) {
			alert("支付密码为空或长度不足");
			return;
		}
		$("#tradePwd").val(password);
	
		$("#form").submit();
	}

	function validatorFrom() {
		$.formValidator.initConfig({
			formID : "form",
			submitOnce : "true",
			theme : "ArrowSolidBox",
			mode : "AutoTip",
			onError : function(msg) {
				alert(msg)
			},
			inIframe : true
		});
		$("#roleName").formValidator({
			onShow : "",
			onFocus : "必填项，请输入2-10个字符作为名称"
		}).inputValidator({
			min : 2,
			max : 10,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "角色名称为2-10个字符"
		});
		//角色描述
		$("#roleDesc").formValidator({
			onShow : "",
			onFocus : "必填项，请输入2-30个字符作为描述"
		}).inputValidator({
			min : 2,
			max : 30,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "角色描述为2-30个字符"
		});
		//$(":checkbox[name='selectAction']").formValidator({onShow:"",onFocus:"请选择权限"}).inputValidator({min:1,onError:"请选择权限"});
	}
	//全选
	function allCheck() {
		var obj = document.getElementsByName("selectAction");
		var obj1 = document.getElementsByName("selectAction1");
		if (document.getElementById("all").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				obj[i].checked = true;
			}
			for ( var i = 0; i < obj1.length; i++) {
				obj1[i].checked = true;
			}

		} else {
			for ( var i = 0; i < obj.length; i++) {
				obj[i].checked = false;
			}
			for ( var i = 0; i < obj1.length; i++) {
				obj1[i].checked = false;
			}
		}
	}

	//所有角色
	function roleCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "pms:";
		if (document.getElementById("role").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}

	//所有操作员
	function operatorCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "pms:operator:";
		if (document.getElementById("operator").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}

	//我的账户
	function acinfoCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "ma:";
		if (document.getElementById("acinfo").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}

	//交易管理
	function tradeCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "trade:";
		if (document.getElementById("trade").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}

	//安全中心
	function scCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "sc:";
		if (document.getElementById("sc").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}

	//公共信息
	function cominfoCheck() {
		var obj = document.getElementsByName("selectAction");
		var str1 = "cominfo:";
		if (document.getElementById("cominfo").checked == true) {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = true;
				}
			}
		} else {
			for ( var i = 0; i < obj.length; i++) {
				var str = obj[i].value;
				var num = str.indexOf(str1);
				if (num >= 0) {
					obj[i].checked = false;
				}
			}
		}
	}
</script>

<body>
<div id="FakeCryptoAgent"></div>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	<br />
	<div class="pageContent">


		<jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>

		<div class="subNavContent">


			<div class="tab_box">
				<form id="form" action="permission_editMerchantRole.action"
					method="post">
					<div class="frm-comon reg-inputW mtop15">
						<input type="hidden" name="selectVal" id="selectVal" /> <input
							type="hidden" name="id" value="${id}" />
						<p class="clearfix">
							<label> &nbsp; </label> <span class="text-warning"
								style=" display:none;"></span>
						</p>
						<p class="clearfix">
							<label>角色名称：</label> <input type="text" name="roleName"
								id="roleName" value="${roleName}" maxlength="10" />

						</p>
						<p class="clearfix">
							<label>角色描述：</label>
							<textarea id="roleDesc" style="resize:none;height:60px;"
								name="desc" maxlength="30">${remark }</textarea>
						</p>
						<p class="clearfix">
							<label style="float:left;">设置角色权限：</label>
						</p>
						<div style="width:600px; margin-left: 125px; margin-top: -10px;"
							id="CheckBoxDiv">
							<input type="checkbox" id="All"
								onclick="OnClickBox(this.id,this.checked)" /><label for="All">全选</label>
							<input type="checkbox" id="Account"
								onclick="OnClickBox(this.id,this.checked)" /><label
								for="Account">我的账户</label>
							<c:if test="${currentUser.userType==1}">
								<input type="checkbox" id="Receive"
									onclick="OnClickBox(this.id,this.checked)" />
								<label for="Receive">我的收款</label>
								<input type="checkbox" id="Pay"
									onclick="OnClickBox(this.id,this.checked)" />
								<label for="Pay">我的付款</label>
							</c:if>
							<c:if test="${currentUser.userType==5}">
								<input type="checkbox" id="PosPay"
									onclick="OnClickBox(this.id,this.checked)" />
								<label for="PosPay">交易查询</label>
								<input type="checkbox" id="PosTerminal"
									onclick="OnClickBox(this.id,this.checked)" />
								<label for="PosTerminal">终端管理</label>
							</c:if>
							<input type="checkbox" id="SecurityCenter"
								onclick="OnClickBox(this.id,this.checked)" /><label
								for="SecurityCenter">安全中心</label> <input type="checkbox"
								id="Pms" onclick="OnClickBox(this.id,this.checked)" /><label
								for="Pms">系统管理</label> <input type="checkbox" id="CommonInfo"
								onclick="OnClickBox(this.id,this.checked)" /><label
								for="CommonInfo">公共信息</label>
						</div>
						<br />
						<div style="width:600px; margin-left: 125px; margin-top: -10px;"
							class='ada-checkbox'>
							<s:iterator value="actionsList" status="st">
								<c:if test="${id!=0}">
									<label style="display: block;width:250px; float:left;">
										<input type="checkbox" name="selectAction" value="${action}"
										id="${id }">${actionName}</label>
								</c:if>
							</s:iterator>
						</div>
						<p class="clear"></p>

						<jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>

						<c:if test="${not empty msg }">
							<p class="TabTips" id="textWarning" style="color:red">
								<label> &nbsp; </label> ${msg}
							</p>
						</c:if>
						<p class="clearfix">
							<strong> &nbsp; </strong> <span class="commonBtn"><span
								class="btn_lfRed"> <input type="button" value="提 交"
									onclick="submitForm();" class="btn_rtRed" /> </span>
							</span>
						</p>
					</div>
				</form>
				<div class="clear"></div>
			</div>


			<div class="clear"></div>
		</div>
		<div class="clear"></div>
	</div>
	<jsp:include page="../../foot.jsp" />
</body>
<script type="text/javascript">
	function OnClickBox(CheckBoxId, Checked) {

		//全选
		if (CheckBoxId == "All" && Checked == true) {
			$(".ada-checkbox").find("input[type='checkbox']").each(function() {
				$(this).attr("checked", "checked");
			});

			$("#CheckBoxDiv").find("input[type='checkbox']").each(function() {
				$(this).attr("checked", "checked");
			});

		} else if (CheckBoxId == "All" && Checked == false) {
			$(".ada-checkbox").find("input[type='checkbox']").each(function() {
				$(this).attr("checked", "");
			});

			$("#CheckBoxDiv").find("input[type='checkbox']").each(function() {
				$(this).attr("checked", "");
			});
		}

		//单选
		if (Checked == true && CheckBoxId != "All") {
			$(".ada-checkbox").find(
					"input[type='checkbox'][value^='" + CheckBoxId + "']")
					.each(function() {
						$(this).attr("checked", "checked");
					});
		} else if (Checked == false && CheckBoxId != "All") {
			$(".ada-checkbox").find(
					"input[type='checkbox'][value^='" + CheckBoxId + "']")
					.each(function() {
						$(this).attr("checked", "");
					});
		}
	}
</script>
</html>
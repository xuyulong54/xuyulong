<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript" src="<%=path %>/statics/js/datepicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=path %>/statics/js//datepicker/skin/WdatePicker.css"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>设置商户身份信息</title>
<script type="text/javascript">
function formValidator(){
	$.formValidator.initConfig({formID:"setMerchantInfoForm",submitOnce:"true",errorFocus:false,theme:"ArrowSolidBox",mode:"AutoTip",onError:function(msg){
		//alert(msg)
		},inIframe:true});
	//公司全称
	$("#fullName").formValidator({onShow:"",onFocus:"请输入2-30个字符"}).inputValidator({min:2,max:30,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符"},onError:"请输入2-30个字符"});
	//商户简称
	$("#shortName").formValidator({onShow:"",onFocus:"请输入2-10的字符"}).inputValidator({min:2,max:10,empty:{leftEmpty:false,rightEmpty:false,emptyError:"输入内容两边不能有空符号"},onError:"不能为空，长度为2-10个字符"});

		//商城网址
		$("#url").formValidator({
			onShow : "",
			onFocus : "请输入正确的商城网址"
		}).inputValidator({
			min : 0,
			max : 255,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "长度为少于255个字符"
		});

		//证备案号
		$("#icp").formValidator({
			onShow : "",
			onFocus : "请输入正确的ICP备案号"
		}).inputValidator({
			min : 0,
			max : 30,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "长度为少于30个字符"
		});

		//经营范围
		$("#scope").formValidator({
			onShow : "",
			onFocus : "请输入2-100个字符"
		}).inputValidator({
			min : 2,
			max : 100,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "请输入2-100个字符"
		}).regexValidator({
			regExp : "ada_test3",
			dataType : "enum",
			onError : "请输入2-100个字符"
		});


		//法人姓名
		$("#legalPerson").formValidator({
			onShow : "",
			onFocus : "必填项，长度为2-10个中文"
		}).inputValidator({
			min : 2,
			max : 10,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为2-10个中文"
		}).regexValidator({
			regExp : "chinese",
			dataType : "enum",
			onError : "法人姓名必须为中文"
		});


		//身份证号
		$("#cardNo").formValidator({
			onShow : "",
			onFocus : "必填项，长度为18个字符"
		}).inputValidator({
			min : 18,
			max : 18,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为18个字符"
		}).functionValidator({
			fun : isCardID
		});


		//联系人姓名
		$("#busiContactName").formValidator({
			onShow : "",
			onFocus : "必填项，长度为2-10个中文"
		}).inputValidator({
			min : 2,
			max : 10,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为2-10个中文"
		}).regexValidator({
			regExp : "chinese",
			dataType : "enum",
			onError : "联系人姓名必须为中文"
		});


		//联系人手机
		$("#busiContactMobileNo").formValidator({
			onShow : "",
			onFocus : "必填项，请输入11位的手机号码"
		}).inputValidator({
			min : 11,
			max : 11,
			onError : "手机号码必须是11位的"
		}).regexValidator({
			regExp : "mobile",
			dataType : "enum",
			onError : "手机号码格式不正确"
		});

		//省
		$("#province").formValidator({
			onShow : "",
			onFocus : "请选择"
		}).functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});

		//市
		$("#city").formValidator({
			onShow : "",
			onFocus : "请选择"
		}).functionValidator({
			fun : function(val, elem) {
				if (val == "" || val == "0") {
					return "请选择"
				} else {
					return true;
				}
			}
		});

		// 通信地址
		$("#address").formValidator({
			onShow : "",
			onFocus : "必填项，长度为2-50个字符"
		}).inputValidator({
			min : 2,
			max : 50,
			empty : {
				leftEmpty : false,
				rightEmpty : false,
				emptyError : "输入内容两边不能有空符号"
			},
			onError : "不能为空，长度为2-50个字符"
		});
		$.formValidator.reloadAutoTip();
	}

	$(function() {
		formValidator();
		$("#province").change(function() {
			var provinceValue = $(this).children('option:selected').val();
			$.post("merchantRegister_getCityByProvince.action", {
				"province" : provinceValue
			}, function(result) {
				$("#city").empty();
				$("#city").append("<option value=''>请选择</option>");
				for ( var i = 0; i < result.cityList.length; i++) {
					$("#city").append("<option value='"+result.cityList[i].city+"'>" + result.cityList[i].city + "</option>");
				}
			}, "json");
		});
	})

	// 打开模态窗口选择MCC费率
	function openSelectMcc() {
		var strName = "选择MCC";
		var width = 1000;
		var height = 600;
		var theWindow = window.open("merchantRegister_selectPosMccList.action", strName, 'height=' + height + ',width=' + width + ',top=' + (screen.height - height) / 2 + ',left='+ (screen.width - width) / 2 + ',toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no');
		if (theWindow.opener == null) {
			theWindow.opener = window;
		}
		if (window.focus) {
			theWindow.focus();
		}
	}
</script>
</head>
<body>
<jsp:include page="../../IndexHead.jsp" />
<div class="container">
<div class="bd-container">
<div class="headline">
	<div class="title">商户注册</div>
</div>
		<div class="merThirdSetpFlow"></div>
		<div class="merFlowTex">
			<ul>
				<li class="green">验证账户名</li>
				<li class="green"  style="width: 220px">设置身份信息</li>
				<li class="red"  style="width: 220px">设置商户信息</li>
				<li>注册成功</li>
			</ul>
		</div>

		<input type="hidden" name="provinceTemp" id="provinceTemp"value="${province}">
		<input type="hidden" name="cityTemp" id="cityTemp" value="${city}">
		<input type="hidden" name="areaTemp" id="areaTemp" value="${area}">

		<div class="frm-comon reg-inputW mtop15">
		<form id="setMerchantInfoForm" name="setMerchantInfoForm" action="merchantRegister_setMerchantInfo.action" method="post">
			<input type="hidden" value="${loginName }" name="loginName" />
			<input type="hidden" value="${userType }" name="userType" />
			<input type="hidden" value="${token }" name="token" />
			<div class="h10"></div>

			<p id="attribute" class="clearfix">
				<label>签约属性：</label>
				<input id="radio1" type="radio"
					class="radio" name="merchantType" checked="checked"  value="${MerchantTypeEnum.MEMBER.value}"/>
				<label for="radio1" class="lblradio">个人</label>

				<input id="radio2" type="radio"
					name="merchantType" value="${MerchantTypeEnum.INDIVIDUAL.value }" class="radio"/>
				<label for="radio2" class="lblradio">个体工商户</label>

				<input id="radio3" type="radio"
					name="merchantType" value="${MerchantTypeEnum.ENTERPRISE.value }" class="radio"/>
				<label for="radio3" class="lblradio">企业</label>
			</p>

			<!-- <p class="clearfix">
				<label>签约属性：</label>
				<input id="radio1" type="radio"   checked="checked"
					class="radio" name="merchantType" value="${MerchantTypeEnum.MEMBER.value}"
					<c:if test="${merchantType==MerchantTypeEnum.MEMBER.value}"> checked="checked"</c:if>
					onclick="dynamicShow(this.value);" />
					<label for="radio1" class="lblradio">个人</label>
					<input id="radio2" type="radio"
					name="merchantType" value="${MerchantTypeEnum.INDIVIDUAL.value }" class="radio"
					<c:if test="${merchantType==MerchantTypeEnum.INDIVIDUAL.value}">checked="checked"</c:if>
					onclick="dynamicShow(this.value);" /> <label for="radio2"
					class="lblradio">个体工商户</label> <input id="radio3" type="radio"
					name="merchantType" value="${MerchantTypeEnum.ENTERPRISE.value }" class="radio"
					<c:if test="${merchantType==MerchantTypeEnum.ENTERPRISE.value}">checked="checked"</c:if>
					onclick="dynamicShow(this.value);" /><label for="radio3"
					class="lblradio">企业</label>
			</p> -->
			<p class="clearfix">
				<label>公司全称：</label>
				<input type="text" name="fullName" id="fullName" value="${fullName}" maxlength="30">
			</p>
			<p class="clearfix">
				<label>商户简称：</label>

				<input type="text" class="ada-input" name="shortName" id="shortName" value="${shortName}" maxlength="10">
			</p>
			<p class="clearfix j-enterprise">
				<label>营业执照号：</label>
				<input type="text" name="licenseNo" id="licenseNo" value="${licenseNo}" maxlength="15">
			</p>
			<p class="clearfix j-enterprise">
				<label>营业执照有效期：</label>
					<input name="licenseValid" value="${licenseValid}" readonly="readonly" type="text" class="timeinput"  onfocus="WdatePicker({minDate:'%y-%M-{%d}'})" id="licenseValid" readonly="readonly" />
<%-- 				<input name="licenseValid" value="${licenseValid}" type="text" class="timeinput" onclick="calendar(this)" id="licenseValid" readonly="readonly" />  --%>
			</p>
			<div id="dynamicShow"></div>
			<p class="clearfix">
				<label>商城网址：</label>
				<input type="text" id="url" name="url" value="${empty url?'http://':url}"  maxlength="255">
			</p>
			<p class="clearfix">
				<label>MCC码：</label>
				<input type="text" name="mccValue" id="mccValue"  />
				<span class="commonBtn" style="margin-left: 5px;"><span class="btn_lfRed">
				<input type="button" name="selectMcc" id="selectMcc" class="btn_rtRed" value="选择MCC..." onclick="openSelectMcc();" />
				</span></span>
			</p>
			<p class="clearfix j-enterprise">
				<label>组织机构代码：</label>
				<input type="text" name="orgCode" id="orgCode" value="${orgCode}" maxlength="10">
			</p>
			<p class="clearfix">
				<label>ICP证备案号：</label>
				<input type="text" name="icp" id="icp" value="${icp}" maxlength="30">
			</p>
			<p class="clearfix">
				<label>企业法人姓名：</label>
				<input type="text" name="legalPerson" id="legalPerson" value="${legalPerson}" maxlength="10">
			</p>
			<p class="clearfix">
				<label>身份证号：</label>
				<input type="text" name="cardNo" id="cardNo" value="${cardNo}" maxlength="18">
			</p>
			<p class="clearfix">
				<label>身份证有效期：</label>
				<input name="cardValid" value="${cardValid}" type="text" class="timeinput"  onfocus="WdatePicker({minDate:'%y-%M-{%d}'})" id="cardValid" readonly="readonly" />
				<%--
				<input name="cardValid" value="${cardValid}" type="text" class="timeinput" onclick="calendar(this)" id="cardValid" readonly="readonly" />
				 --%>
			</p>
			<p class="clearfix">
				<label>联系人姓名：</label>
				<input type="text" class="ada-input" id="busiContactName" name="busiContactName" value="${busiContactName}" maxlength="10">
			</p>
			<p class="clearfix">
				<label>联系人手机：</label>
				<input type="text" class="ada-input" id="busiContactMobileNo" name="busiContactMobileNo" value="${busiContactMobileNo}" maxlength="11">
			</p>
			<p class="clearfix">
				<label>经营范围：</label>
				<textarea class="ada-input" style=" resize:none;" id="scope" rows="3" cols="20" name="scope" maxlength="100">${scope }</textarea>
			</p>
			<p class="clearfix">
				<label>通信地址：</label>
				<label style="width:20px">省</label>
				<span  class="select_border">
                 <span class="select_cont">

				<select class="select" style="width:150px" name="province" id="province">
					<option value="">请选择</option>
					<c:forEach items="${provinceList }" var="v">
									<option value="${v.province }">${v.province}</option>
					</c:forEach>
				</select>
				</span></span>
			</p>
			<p class="clearfix">
				<label> &nbsp; </label>
				<label style="width:20px">市</label>
				<span  class="select_border">
                            <span class="select_cont">
				<select class="select" style="width:150px" name="city" id="city">
						<option value="">请选择</option>
				</select>
				</span></span>
			</p>
			<p class="clearfix">
				<label>具体街道地址：</label>
				<input type="text" name="address" id="address"value="${address}">
			</p>
			<p style="height:10px"></p>
			<p class="clearfix">
				<label> &nbsp; </label>
 				<input class="btn btn-primary" id="Button7" type="submit" value="保 存" onclick="submitForm();" />
			</p>
			<div class="h10"></div>
		</form>
		</div>
		<div class="clear">${PAGE_ERROR_MSG}</div>
	</div>
</div>

<jsp:include page="../../foot.jsp" />

<script>
	$(function(){
	 showCont();
	 $("#attribute input[name=merchantType]").click(function(){
	  showCont();
	 });
	});

	function showCont(){
	 switch($("#attribute input[name=merchantType]:checked").attr("id")){
	  case "radio1":
	   $(".j-enterprise").hide();
	   $("#orgCode").formValidator({
           onShow : "",
           onFocus : "请输入正确的组织机构代码"
         }).inputValidator({
           min : 0,
           max : 100,
           onError : "长度最多为10个字符"
         });
       
         //营业执照号
         $("#licenseNo").formValidator({
             onShow : "",
             onFocus : "请输入正确的营业执照号"
           }).inputValidator({
             min : 0,
             max : 15,
             onError : "长度为15位数字"
           });
	   break;
	   
	  case "radio2":
	    $(".j-enterprise").show();
	  //组织机构代码
	    $("#orgCode").formValidator({
	      onShow : "",
	      onFocus : "请输入正确的组织机构代码"
	    }).inputValidator({
	      min : 9,
	      max : 10,
	      empty : {
	        leftEmpty : false,
	        rightEmpty : false,
	        emptyError : "输入内容两边不能有空符号"
	      },
	      onError : "长度最多为10个字符"
	    });
	  
	    //营业执照号
	    $("#licenseNo").formValidator({
	        onShow : "",
	        onFocus : "请输入正确的营业执照号"
	      }).inputValidator({
	        min : 15,
	        max : 15,
	        empty : {
	          leftEmpty : false,
	          rightEmpty : false,
	          emptyError : "输入内容两边不能有空符号"
	        },
	        onError : "长度为15位数字"
	      }).regexValidator({ regExp: "num", dataType: "enum", onError: "只能是数字" });
	   break;
	   
	   case "radio3":
	   $(".j-enterprise").show();
	   //组织机构代码
	      $("#orgCode").formValidator({
	        onShow : "",
	        onFocus : "请输入正确的组织机构代码"
	      }).inputValidator({
	        min : 9,
	        max : 10,
	        empty : {
	          leftEmpty : false,
	          rightEmpty : false,
	          emptyError : "输入内容两边不能有空符号"
	        },
	        onError : "长度最多为10个字符"
	      });
	    
	      //营业执照号
	      $("#licenseNo").formValidator({
	          onShow : "",
	          onFocus : "请输入正确的营业执照号"
	        }).inputValidator({
	          min : 15,
	          max : 15,
	          empty : {
	            leftEmpty : false,
	            rightEmpty : false,
	            emptyError : "输入内容两边不能有空符号"
	          },
	          onError : "长度为15位数字"
	        }).regexValidator({ regExp: "num", dataType: "enum", onError: "只能是数字" });	   
	   break;
	  default:
	   break;
	 }
	}
	function submitForm(){
		var mccValue=$("#mccValue").val();
		if(/^[1-9]\d*$/.test(mccValue) || mccValue == ""){
			$("#setMerchantInfoForm").submit();
		}else{
			alert("请输入正确的MCC码!");
		}
	}
</script>

</body>
</html>

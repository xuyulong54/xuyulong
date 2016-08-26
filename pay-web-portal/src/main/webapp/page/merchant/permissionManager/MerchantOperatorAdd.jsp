<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>添加操作员</title>
<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript">
        /*页面分类*/
         $(document).ready(function() {
         setPageType('.mer-system', '.mer-system-operator ');
         setSecondNav('.secondNav2');
         var timer=null;
         timer=setInterval(function(){
         if($('#realName').next('div').attr('class')=='onCorrect'){
         $("#loginName").focus();
         window.clearInterval(timer);
         }
         },30);
         })
          function submitForm() {
        	    var str = "";
        	    document.getElementById("errorMsg").innerHTML=str;
              var textWarning= document.getElementById("textWarning");
              if (textWarning!=null){
                textWarning .innerHTML=str
              }                           	 
             $(":checkbox:checked").each(function () {
               str += $(this).attr("id");
               str += ",";
             });
             $("#selectVal").val(str);
             if (str == "") {
               $(".text-warning").text("请至少选择一项角色");
               $(".text-warning").show();
               return;
             } else {
               $(".text-warning").hide();
             }
             
           var password = funGetPassword();
            if (password == null) {
                alert("支付密码为空或者长度不足");
              return;
            }
            //$("#tradePwd").val(password);
           
            $("#form").submit();
          }
          $(function () {
            //表单验证
            validatorFrom();
          });

           //名字输入完之后自动带出简写
          function toPinyinSimple(val) {
            toPinyin(val, function (res) {
              $("input[name='loginName']").val(res.full);
            });
          }

           //表单验证
          function validatorFrom() {
            $.formValidator.initConfig({
              formID: "form",
              submitOnce: "true",
              theme: "ArrowSolidBox",
              mode: "AutoTip",
              onError: function (msg) {
                alert(msg);
              },
              inIframe: true
            });
            //操作人名称
            $("#realName").formValidator({
              onShow: "",
              onFocus: "必填项，请输入操作人姓名（2-10个中文）"
            }).inputValidator({
              min: 2,
              max: 10,
              empty: {
                leftEmpty: false,
                rightEmpty: false,
                emptyError: "输入内容两边不能有空符号"
              },
              onError: "操作人姓名为2-10个中文字符"
            })
              .regexValidator({
                regExp: "chinese",
                dataType: "enum",
                onError: "操作人姓名必须为中文"
              });
            //操作人名称
            $("#loginName").formValidator({
              onShow: "",
              onFocus: "必填项，请输入操作人登录名"
            }).inputValidator({
              min: 3,
              max: 20,
              empty: {
                leftEmpty: false,
                rightEmpty: false,
                emptyError: "输入内容两边不能有空符号"
              },
              onError: "操作人登录名为3-20个字母组合"
            }).regexValidator({
              regExp: "letter",
              dataType: "enum",
              onError: "操作人登录名为3-20个字母组合"
            }).ajaxValidator({
              dataType : "json",
              async : true,
              url : "ajaxvalidate_operatorLoginNameValidate.action",
              success : function(data){
                if( data.STATE!='FAIL') {
                        return true;}
                  return data.MSG;
              },
              onError : "校验未通过，请重新输入",
              onWait : "正在校验，请稍候..."
            });
              //登陆密码
              $("#loginPwd").formValidator({onShow:""})
              .functionValidator({
                        fun: function (val, elem) {
                          var msg=validatePwd(val);
                          if(msg!=''){return msg;}return true;
                        }
                 });
              //确认登录密码
            $("#reLoginPwd").formValidator({onShow:"",onFocus:"必填项，两次输入密码必须一致"})
              .functionValidator({
                        fun: function (val, elem) {
                          var msg=validatePwd(val);
                          if(msg!=''){return msg;}return true;
                        }
                 })
                 .compareValidator({desID:"loginPwd",operateor:"=",onError:"两次输入的密码不一致"});

            // 手机号码
            $("#mobileNo").formValidator({
              onShow: "",
              onFocus: "必填项，请输入11位的手机号码"
            }).inputValidator({
              min: 11,
              max: 11,
              onError: "手机号码必须是11位的"
            }).regexValidator({
              regExp: "mobile",
              dataType: "enum",
              onError: "手机号码格式不正确"
            });
            var str = "${owenedRoleIds}";
            var array = new Array();
            array = str.split(",");
            for (var i = 0; i < array.length; i++) {
              $("#" + array[i]).attr("checked", "checked");
            }
          }
        </script>
</head>

<body>
<div id="FakeCryptoAgent"></div>
  <jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
  <br />
  <div class="pageContent">

    <jsp:include page="/page/merchant/permissionManager/PmsMenu.jsp"></jsp:include>

    <div class="operator-box">

      <form id="form" action="permission_addMerchantOperator.action"
        method="post">
        <div class="tab_box frm-comon">
          <input type="hidden" name="selectVal" id="selectVal" />
          <div class="markRed" style="height:5px; line-height: 20px;"></div>
          <p class="clearfix">
            <label> &nbsp; </label> <span class="text-warning"display:none;"></span>
          </p>
          <p class="clearfix">
            <label>操作员姓名：</label> <input type="text" name="realName"
              id="realName" value="${realName }" maxlength="10"
              onchange="toPinyinSimple(this.value)" />
          </p>
          <p class="clearfix">
            <label>操作员登录名：</label> <input type="text" name="loginName"
              id="loginName" value="${loginName }" />
          </p>
          <p class="clearfix">
            <label>操作员密码：</label> <input type="password" name="loginPwd"
              id="loginPwd" value="${loginPwd }"  maxlength="20"/>
          </p>
            <p class="clearfix">
            <label>确认操作员密码：</label> <input type="password" name="reLoginPwd"
              id="reLoginPwd" value="${reLoginPwd }"  maxlength="20"/>
          </p>
          <p class="clearfix">
            <label>手机号：</label> <input type="text" name="mobileNo"
              id="mobileNo" value="${mobileNo }" maxlength="11" />
          </p>

          <div class="leftbox">选择角色 ：</div>
          <div class="rightcont">
            <s:iterator value="rolesList" status="st">
              <c:if test="${id!=0}">

                <input type="checkbox" name="selectRole" class="check"
                  id="${id }">${roleName}

                  </c:if>
            </s:iterator>
            <c:if test="${fn:length(rolesList) <= 0  }">
            <span style="color: red;">没有可分配的角色，请先
                    <a class="link-color mleft10" href="permission_addMerchantRoleUI.action">添加角色</a></span>
            </c:if>
          </div>

          <p class="clear"></p>

          <jsp:include page="/page/include/merchantTradePwd.jsp"></jsp:include>

          <c:if test="${!empty msg }">
            <p class="clearfix" id="textWarning" style="color:red;">
              <label>&nbsp; </label> <span class="text-warning">${msg}</span>
            </p>
          </c:if>
          <p class="clearfix">
            <label> &nbsp; </label> <input type="button"
              class="btn btn-primary" value="提 交" onclick="submitForm();" />

          </p>
        </div>
      </form>

      <div class="clear"></div>
    </div>


    <div class="clear"></div>
  </div>
  <jsp:include page="../../foot.jsp" />
</body>

</html>

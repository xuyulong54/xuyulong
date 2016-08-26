<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户安全中心</title>
<%@include file="/page/include/headScript.jsp"%>
<link rel="stylesheet" href="statics/themes/default/css/style.css" />
</head>
<body>
	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
<!-- 	<div class="nav_shadow"> -->
<!--     </div> -->


	  <div class="NewsMain">
        <div class="left">
          <div class="conection1">

           <div class="NewsTile">
                  安全贴士
                </div>
                <div class="LCbox" style="margin-top:10px">
                    <span>1</span> &nbsp; 输入密码时不要被他人窥视
                </div>
                <div  class="h10">
                </div>
                <div class="LCbox">
                    <span>2</span> &nbsp; 不要将个人的登录密码告诉他人
                </div>
               <div  class="h10">
                </div>
                <div class="LCbox">
                    <span>3</span> &nbsp; 设定密码时尽量不要使用生日/电话号码
                </div>
                <div  class="h10">
                </div>
                <div class="LCbox">
                    <span>4</span> &nbsp; 在公共场合使用${COMPANY_FOR}网站操作完成后要点击退出
                </div>
                <br style="clear: both;" />
            </div>
            <div class="h10">
            </div>
            <div class="conection1">
                <div class="NewsTile">
                    客服热线
                </div>
                <div class="conIMG">
                </div>
                <div class="cont">
                    <p>客服电话：${COMPANY_TEL }</p>
                    <p class="word-break">客服邮箱：${COMPANY_EMAIL }</p>
                </div>
                    <br  style="clear:both;"/>
            </div>
            <div class="ht">
            </div>

            <br style="clear: both" />
        </div>
        <div class="right">
            <div class="NewsTile_bg">
                <div class="NewsTile">
                   下载地址</div>
            </div>
            <div style="height: 10px">
            </div>

            <div class="article">
                <table id="tbl" cellpadding="0" cellpadding="0" class="tbl">
		    <tr>
				<td class="lf_td"><a href="#">下载地址1</a></td>
			</tr>

			  <tr>
				<td class="lf_td"><a href="#">下载地址2</a></td>
			</tr>

                </table>
                  <br style="clear: both;"/>
            </div>


            <br style="clear: both" />
        </div>
        <br style="clear: both" />
    </div>
	  <jsp:include page="../../foot.jsp" />
</body>
</html>

<script type="text/javascript">

/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })
</script>

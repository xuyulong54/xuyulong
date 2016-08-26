<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" buffer="64kb"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN" "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>抱歉，出错啦!</title>
<%@include file="/page/include/headScript.jsp"%>
<meta name="description" content="" />
<meta name="keywords" content="" />
<script type=""></script>

</head>
<body >

	<div class="errorpg">
	<div class="content">
			<ul>
				<li class="topTitle">抱歉,系统异常！</li>
				<li class="ordinaryTitle">系统异常</li>
				

				<li><span class="imgReturn page fl "></span> <!-- <a class="fl ml5"
					href="javascript:history.go(-1);"> 返回 </a> --> <span
					class="imgReturn home fl" style="margin-left:30px;"> </span><a
					class="fl ml5" href="login_loginUI.action">首页</a></li>
			</ul>
		</div>
	</div>
	

 
	 <%--<div class="errorpg">
        <div class="content">
        <div class="frm" >
        <div class="title">抱歉,系统异常！</div>
            <p>
              <span> 系统异常 。</span></p>
               <p>
                 <span> <a href="javascript:history.go(-1);"> 返回 </a></span>&nbsp;&nbsp;&nbsp;  <span><a href="login_loginUI.action">首页</a></span> &nbsp; &nbsp; &nbsp;<span>
            </p>
            <p>
             <div style="display: none"><%=request.getAttribute("errCode")%></div>
            </p>
            </div>
        </div>
    </div>




--%></body>
</html>

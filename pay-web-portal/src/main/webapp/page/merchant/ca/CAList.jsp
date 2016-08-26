<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数字证书</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/onload.js"></script>
<script type="text/javascript"
	src="<%=path%>/statics/cfca/js/merchantCA.js"></script>
</head>
<script>

/*页面分类*/
$(document).ready(function() { setPageType('.mer-security', '.mer-security-info '); })


</script>
<body onload="javascript:OnLoad();">
<div id="FakeCryptoAgent"></div> <div id="tr_DigestAlgorithm" style="display: none"></div><!-- 不能删除这个DIV -->

	<jsp:include page="/page/include/TopMenuMerchant.jsp"></jsp:include>
	
	<!-- <a href="merchantca_addCAIntro.action">申请</a> -->
	<div class="container">
	
	<div class="headline mtop10">
	<div class="title">数字证书列表</div>
	</div>
	<div class="cabanner mtop10">
	<c:if test="${CURRENT_SN ==null || CURRENT_SN == ''}">
				<a  class="applybtn" href="merchantca_addCAIntro.action">我要申请 </a>
			</c:if>	
	</div>
		  <div class="NewsMain">
        <div class="left">
          <div class="conection1">
           <div class="NewsTile">
                  操作提示
                </div>
                <div class="LCbox" style="margin-top:10px">
                    <span>1</span> &nbsp; 填写申请信息
                </div>
                <div  class="h10">
                </div>
                <div class="LCbox">
                    <span>2</span> &nbsp; 安装成功
                </div>
               <div  class="h10">
                </div>
                <div class="LCbox">
                    <span>3</span> &nbsp; 证书保障账户安全
                </div>
                
                <br style="clear: both;" />
            </div>
            
            <div class="ht">
            </div>
       
            <br style="clear: both" />
        </div>
        <div class="right calist">
         
          
            <table class=" table table-border">
            <thead>
            <tr>
            <th>证书使用地点</th>
            <th>申请时间</th>
				<th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pageBean.recordList }" var="merchantCa">
			<tr id="tr${merchantCa.id }">
				<td>
				<c:forEach items="${addrs }" var="addr">
				<c:if test="${addr.value==merchantCa.addrId }">${addr.desc }</c:if>
				</c:forEach>
				<c:if test="${CURRENT_SN eq merchantCa.sn }"> <font color="red">&nbsp;本机正在使用中...</font></c:if>
				</td>
				<td> <fmt:formatDate value="${merchantCa.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
				
				<td>
				<z:permission value="SecurityCenter:Ca">
				<a class="link-color" href="javascript:del('${merchantCa.id }','${merchantCa.sn  }')">删除</a>
				</z:permission>
				</td>
				
			</tr>
			</c:forEach>
            </tbody>
            </table>
      
            <br style="clear: both" />
        </div>
        <br style="clear: both" />
    </div>
	
	
	</div>
	
	
		

		<%--<c:if test="${pageBean.totalCount>0}">
		<div class="pageCla">
	<z:page pageBean="${pageBean }" url="merchantca_listCA.action" currentPage="${pageNum }"
	parameter="&merchantId=${proMap.merchantId }&userId=${proMap.userId }&userType=${proMap.userType}"></z:page>
	</div>
	</c:if>--%>
		
	
	<div class="ht"></div>
	<jsp:include page="../../foot.jsp" />
</body>
</html>
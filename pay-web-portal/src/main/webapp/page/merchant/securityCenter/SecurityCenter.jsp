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
	  <div class="NewsMain">
      <div class="left">
        <div class="conection1">
          <div class="NewsTile">安全贴士</div>
          <div class="LCbox" style="margin-top:10px">
            <span>1</span> &nbsp; 输入密码时不要被他人窥视
          </div>
          <div class="LCbox">
            <span>2</span> &nbsp; 不要将个人的登录密码告诉他人
          </div>
          <div class="LCbox">
            <span>3</span> &nbsp; 设定密码时尽量不要使用生日/电话号码
          </div>
          <div class="LCbox">
            <span>4</span> &nbsp; 在公共场合使用${COMPANY_FOR}网站操作完成后要点击退出
          </div>
          <br style="clear: both;" />
        </div>
        <div class="h10"></div>
        <div class="conection1">
          <div class="NewsTile">客服热线</div>
          <div class="conIMG"></div>
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
      <div class="right reset-right">
        <div class="NewsTile_bg">
          <div class="NewsTile">安全中心</div>
        </div>

        <div class="security-list">
          <table id="tbl" cellpadding="0" cellpadding="0" class="tbl">
        		<tr>
        		  <th class="w-img ico-auth"></th>
        	    <th class="thw150">实名认证</th>
        				<c:choose>
        					<c:when test="${userInfo.isRealNameAuth == PublicStatusEnum.ACTIVE.value}">
        						<td class="tdw480">您已通过实名认证</td>
        						<td>&nbsp;</td>
        					</c:when>
        					<c:otherwise>
        					<td class="tdw480">验证您的身份，提高服务等级</td>
        					<td >未认证</td>
        					</c:otherwise>
        				</c:choose>
        		</tr>
      			<tr>
      			  <th class="w-img ico-phone"></th>
      				<th class="thw150">绑定手机</th>
      				<c:choose>
      					<c:when test="${userInfo.isMobileAuth == PublicStatusEnum.ACTIVE.value}">
      						<td class="tdw480">您绑定的手机， ${bindMobileNo} </td>
      						<td >
      						<z:permission value="SecurityCenter:Binding">
      						<a href="merchantmobilebind_bindingMobileUI.action">修改</a>&nbsp;&nbsp;
      						</z:permission>
      						<z:permission value="SecurityCenter:Binding">
<%--      						<a href="merchantmobilebind_unbundlingMobileUI.action">解绑</a>--%>
      						</z:permission>
      						</td>
      					</c:when>
        				<c:otherwise>
            			<td class="tdw480">在进行重要操作时验证您的身份</td>
            			<td>
            			<z:permission value="SecurityCenter:Binding">
            			<a href="merchantmobilebind_bindingMobileUI.action">绑定</a>
            			</z:permission>
            			</td>
        				</c:otherwise>
      				</c:choose>
      			</tr>
      			<tr>
      			  <th class="w-img ico-mail"></th>
      				<th class="thw150">绑定邮箱</th>
      				<c:choose>
      					<c:when test="${userInfo.isEmailAuth == PublicStatusEnum.ACTIVE.value}">
      						<td class="tdw480">您绑定的邮箱，${bindEmail }</td>
      						<td>
      						<z:permission value="SecurityCenter:Binding">
      						<a href="merchantemailbind_bindingEmailUI.action">修改</a>&nbsp;&nbsp;
      						</z:permission>
      						<z:permission value="SecurityCenter:Binding">
<%--      						<a href="merchantemailbind_unbundlingEmailUI.action">解绑</a>--%>
      						</z:permission>
      						</td>
      					</c:when>
      					<c:otherwise>
      						<td class="thw480">在进行重要操作时验证您的身份</td>
      						<td>
      						<z:permission value="SecurityCenter:Binding">
      						<a href="merchantemailbind_bindingEmailUI.action">绑定</a>
      						</z:permission>
      						</td>
      					</c:otherwise>
      				</c:choose>
      			</tr>
      			<tr>
      			  <th class="w-img"></th>
      				<th class="thw150 loginpwd">登录密码</th>
      				<td class="thw480">上次登录时间：<fmt:formatDate value="${userOperator.lastLoginTime}" pattern="yyyy-MM-dd HH:mm:ss"/>  </td>
      				<td >
      				  <a href="merchantpwdedit_editLoginPwdUI.action">修改</a>
      				</td>
      			</tr>
      			<tr>
      				<th class="w-img ico-paypwd"></th>
      				<th class="thw150">支付密码</th>
      				<td class="thw480">付款或修改帐户信息时输入，保护账户资金安全</td>
      				<td>
        				<z:permission value="SecurityCenter:TradePassword">
        				<a href="merchantpwdedit_editTradePwdUI.action">修改</a>&nbsp;&nbsp;
        				</z:permission>
        				<z:permission value="SecurityCenter:TradePassword">
        				<a href="merchantlookfortradepwd_tradePwdListWay.action">找回</a>
        				</z:permission>
      				</td>
      			</tr>
      			<tr>
      			  <th class="w-img ico-question"></th>
      				<th class="thw150">安全保护问题</th>
      				<td class="thw480">
      				${empty userInfo.question?'您还未设置安全保护问题' :'您已设置了安全保护问题'}
      				</td>
      				<td>
      				<z:permission value="SecurityCenter:Question:Geeting">
      				<a href="merchantsecurityiquestion_editSecurityiQuestionUI.action">
      				  ${empty userInfo.question?'设置' :'修改'}
      				</a>
      				</z:permission>
      				</td>
      			</tr>
      			<tr>
      			  <th class="w-img ico-msg"></th>
      				<th class="thw150">预留信息</th>
      				<td class="thw480">
        				${empty userInfo.greeting?'您还未设置预留信息' :'您已设置了预留信息'}
        				</td>
        				<td >
        				<z:permission value="SecurityCenter:Question:Geeting">
        				<a href="merreservationInfo_editMerchantGreetingUI.action">
        				${empty userInfo.greeting?'设置' :'修改'}
        				</a>
        				</z:permission>
      				</td>
      			</tr>
      			<c:if test="${USE_SECURITYCENTER }">
      			<tr>
      			  <th class="w-img ico-certificate"></th>
      				<th class="thw150">数字证书</th>
      				<td class="thw480">安装数字证书，保障您的资金安全</td>
      				<td >
      				<z:permission value="SecurityCenter:Ca">
      				<a href="merchantca_listCA.action">数字证书</a>
      				</z:permission>
      				</td>
      			</tr>
      			</c:if>
      			<!-- 必须为激活状态，并为管理员 -->
      			<tr>
      			  <th class="w-img ico-cancel"></th>
      				<th class="thw150">销户</th>
      				<td class="thw480">注销在支付平台的账户，永久删除账号</td>
      				<td>
      				<c:if test="${userInfo.status==PublicStatusEnum.ACTIVE.value && userOperator.type==UserOperatorTypeEnum.ADMIN.value}">
      				<a href="merchantcancelaccount_cancelAgreementUI.action">申请</a>
      				</c:if>
      				</td>
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
 $(document).ready(function() { setPageType('.mer-security', '.mer-security-info ');})
 </script>

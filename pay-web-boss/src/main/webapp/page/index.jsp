<%@page import="wusc.edu.pay.common.config.PublicConfig"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/page/inc/taglib.jsp"%>
<%
	String path = "";
	final String serverName = request.getServerName();
	if(PublicConfig.IS_USE_DOMAIN_NAME){
		path = request.getContextPath();
	}else{
		path = "http://" + serverName +":"+request.getServerPort() + request.getContextPath();
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${COMPANY_FOR}--支付运营平台</title>
<jsp:include page="inc/dwz.jsp" />
</head>
<body scroll="no">
	<div id="layout">
		<div id="header">
		    <!-- navMenu begin -->
			<div class="headerNav">
				<img alt="" src="<%=path %>${COMPANY_LOGO}" height="50" />
				<ul class="nav">
					<li style="color:black;">欢迎您（${realName }）！&nbsp;上次登录：<s:date name="lastLoginTime" format="yyyy年MM月dd日  HH时mm分ss秒" /> </li>
					<li><a href="pms_operatorViewOwnInfo.action" target="dialog" width="500" height="400" style="color:#fff;">帐号信息</a></li>
					<li><a href="pms_operatorChangeOwnPwdUI.action" target="dialog" width="550" height="300" style="color:#fff;">修改密码</a></li>
					<li>
						<a href="login_logoutConfirm.action" title="退出登录确认" target="dialog" width="300" height="200" style="color:#fff;">退出</a>
					</li>
				</ul>
				<ul class="themeList" id="themeList">
					<li theme="default"><div class="selected">蓝色</div>
					</li>
					<li theme="green"><div>绿色</div>
					</li>
					<li theme="purple"><div>紫色</div>
					</li>
					<li theme="silver"><div>银色</div>
					</li>
					<li theme="azure"><div>天蓝</div>
					</li>
				</ul>
			</div>
			<!-- navMenu end -->
		</div>
		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse">
						<div></div>
					</div>
				</div>
			</div>
			<div id="sidebar">
				<!-- <div class="toggleCollapse"><h2>主菜单</h2><div>收缩</div></div>-->
				<div class="accordion" fillSpace="sidebar">${tree }</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent">
						<!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">主页</span>
								</span>
							</a>
							</li>
						</ul>
					</div>
					<div class="tabsLeft">left</div>
					<!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div>
					<!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">主页</a>
					</li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<p>
								<span>${COMPANY_FOR}--运营管理系统</span>
							</p>
						</div>
						<!-- 统计内容  -->
						<div class="pageFormContent" layoutH="80" style="margin-right:230px">
							<h1>商户&nbsp;&nbsp;&nbsp;&nbsp;(<a href="javascript:loadMerchantStatus();" style="color:blue;">刷新</a>)</h1>
							
							<br/>
							<p><a href="posMerchant_agentMerchantOneAuditList.action" title="商户初审" style="color:blue;" id="created" target="navTab">待初审商户(0)</a></p>
							<p><a href="posMerchant_agentMerchantTwoAuditList.action" title="商户终审" style="color:blue;" id="auditing" target="navTab">待终审商户(0)</a></p>
							<p><a href="posMerchant_agentMerchantList.action?status=103" title="pos商户管理" style="color:blue;" id="nopass" target="navTab">审核拒绝商户(0)</a></p>
							<p><a href="posMerchant_agentMerchantList.action?status=100" title="pos商户管理" style="color:blue;" id="active" target="navTab">已开通商户(0)</a></p>
							
							<div class="unit">
								<hr>
							</div>
							<br/>
							<div class="unit">
								<h1>代理商&nbsp;&nbsp;&nbsp;&nbsp;(<a href="javascript:loadAgentStatus();" style="color:blue;">刷新</a>)</h1>
								<br/>
							</div>
							<p><a href="agent_auditAgentList.action" title="代理商审核" style="color:blue;" id="Acreated" target="navTab">待审核代理商(0)</a></p>
							<p><a href="agent_agentList.action?agentStatus=103" title="代理商信息管理" style="color:blue;" id="Anopass" target="navTab">审核拒绝代理商(0)</a></p>
							<p><a href="agent_agentList.action?agentStatus=100" title="代理商信息管理" style="color:blue;" id="Aactive" target="navTab">已开通代理商(0)</a></p>
						
							<div class="unit">
								<hr>
							</div>
							<br/>
							
							<div class="unit">
								<h1>变更申请&nbsp;&nbsp;&nbsp;&nbsp;(<a href="javascript:loadChangeInfoStatus();" style="color:blue;">刷新</a>)</h1>
								<br/>
							</div>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=1&verifyStatus=101" title="商户信息变更" style="color:blue;" id="merchBaseInfoDiv" target="navTab">商户信息变更(0)</a></p>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=2&verifyStatus=101" title="商户费率变更" style="color:blue;" id="merchRateInfoDiv" target="navTab">商户费率变更(0)</a></p>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=3&verifyStatus=101" title="商户结算变更" style="color:blue;" id="merchSettInfoDiv" target="navTab">商户结算变更(0)</a></p>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=4&verifyStatus=101" title="商户机具变更" style="color:blue;" id="merchTermInfoDiv" target="navTab">商户机具变更(0)</a></p>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=5&verifyStatus=101" title="代理商信息变更" style="color:blue;" id="agentBaseInfoDiv" target="navTab">代理商信息变更(0)</a></p>
							<p><a href="merchantRequest_merchantBaseList.action?requestType=6&verifyStatus=101" title="代理商结算变更" style="color:blue;" id="agentSettInfoDiv" target="navTab">代理商结算变更(0)</a></p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="footer">Copyright &copy; ${COMPANY_NET_ICP } ${COMPANY_NAME}  版权所有</div>
	<script type="text/javascript">
		$(function() {
			
			loadStatus();
			setTimeout(function() {
				var isChangePwd = "${isChangePwd}";
				if (isChangePwd == "false") {
					var url = "pms_operatorChangeOwnPwdUI.action";
					$.pdialog.open(url, "changeOperLoginPwd", "修改密码", {
						width : 500,
						height : 300,
						maxable : false
					});
				}
			}, 1000);
			
		});
		
		function loadStatus(){
			loadMerchantStatus(); 	// 加载商户信息
			loadAgentStatus();		// 加载代理商信息
			loadChangeInfoStatus(); 	// 加载代理商和商户的变更信息
		}
		
		// 加载商户信息
		function loadMerchantStatus(){
			// 统计待审核商户个数
			$.post("posMerchant_loadMerchantStatus.action", {}, function(res){
				if(res.STATE == "SUCCESS"){
					var createdNum = 0 ; // 待审核商户
					var auditingNum = 0; // 审核中商户
					var nopassNum = 0; // 审核不通过
					var activeNum = 0; // 激活商户
					var respStr = eval("'"+res.MSG+"'");
					var arryAll = []; 
					if(respStr != null && respStr != ''){
						arryAll = respStr.split(',');
						createdNum = arryAll[0]; // 待初审的商户
						auditingNum = arryAll[1]; // 待终审的的商户
						activeNum = arryAll[2];	// 激活的商户
						nopassNum = arryAll[3]; // 审核不通过的商户
					}
					$("#created").html("待初审商户(<span style='color:red;'>"+createdNum+"</span>)");
					$("#auditing").html("待终审商户(<span style='color:red;'>"+auditingNum+"</span>)");
					$("#nopass").html("审核不通过商户(<span style='color:red;'>"+nopassNum+"</span>)");
					$("#active").html("激活商户(<span style='color:red;'>"+activeNum+"</span>)");
				}
			},"json");
		}
		
		
		// 加载代理商信息
		function loadAgentStatus(){
			// 统计待审核代理商个数
			$.post("agent_loadAgentStatus.action", {}, function(res){
				if(res.STATE == "SUCCESS"){
					var createdNum = 0 ; // 待审核商户
					var nopassNum = 0; // 审核不通过
					var activeNum = 0; // 激活商户
					var respStr = eval("'"+res.MSG+"'");
					var arryAll = []; 
					if(respStr != null && respStr != ''){
						arryAll = respStr.split(',');
						createdNum = arryAll[0]; // 待初审的商户
						activeNum = arryAll[1];	// 激活的商户
						nopassNum = arryAll[2]; // 审核不通过的商户
					}
					$("#Acreated").html("待审核代理商(<span style='color:red;'>"+createdNum+"</span>)");
					$("#Anopass").html("审核不通过代理商(<span style='color:red;'>"+nopassNum+"</span>)");
					$("#Aactive").html("激活代理商(<span style='color:red;'>"+activeNum+"</span>)");
				}
			},"json");
		}
		
		// 加载代理商和商户的变更信息
		function loadChangeInfoStatus(){
			$.post("merchantRequest_loadChangeInfoStatus.action", {}, function(res){
				if(res.STATE == "SUCCESS"){
					var merchBaseInfo = 0;	// 商户基本信息变更
					var merchRateInfo = 0;	// 商户费率信息变更
					var merchSettInfo = 0;	// 商户结算信息变更
					var merchTermInfo = 0;	// 商户机具信息变更
					var agentBaseInfo = 0; 	// 代理商基本信息变更
					var agentSettInfo = 0; 	// 代理商结算信息变更
					var respStr = eval("'"+res.MSG+"'");
					var arryAll = [];
					if(respStr != null && respStr != ''){
						arryAll = respStr.split(',');
						
						merchBaseInfo = arryAll[0];
						merchRateInfo = arryAll[1];
						merchSettInfo = arryAll[2];
						merchTermInfo = arryAll[3];
						agentBaseInfo = arryAll[4];
						agentSettInfo = arryAll[5];
					}
					
					$("#merchBaseInfoDiv").html("商户信息变更(<span style='color:red;'>"+merchBaseInfo+"</span>)");
					$("#merchRateInfoDiv").html("商户费率变更(<span style='color:red;'>"+merchRateInfo+"</span>)");
					$("#merchSettInfoDiv").html("商户结算变更(<span style='color:red;'>"+merchSettInfo+"</span>)");
					$("#merchTermInfoDiv").html("商户机具变更(<span style='color:red;'>"+merchTermInfo+"</span>)");
					$("#agentBaseInfoDiv").html("代理商信息变更(<span style='color:red;'>"+agentBaseInfo+"</span>)");
					$("#agentSettInfoDiv").html("代理商结算变更(<span style='color:red;'>"+agentSettInfo+"</span>)");
				}
			},"json");
		}
		
		
		
	</script>
</body>
</html>
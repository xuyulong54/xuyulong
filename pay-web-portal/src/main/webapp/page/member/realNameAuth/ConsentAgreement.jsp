<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>实名认证</title>

<%@include file="/page/include/headScript.jsp"%>

<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info ');  })

function disabledbut(){
	if($("#ty").attr("checked")==true){
		$('#Button1').removeAttr("disabled");
		$('#Button1').css({'background':'#e60707','border':'1px solid #c70b06'});

	}else{
		$('#Button1').attr('disabled',"true");
		$('#Button1').css({'background':'#959595','border':'1px solid #959595'});
	}
	}
$(function(){
	//点击阅读协议的的同意按钮
	$('.agreenBtn').bind('click',function(){
	$("#ty").attr("checked",true);
	$('#Button1').removeAttr("disabled");
	disabledbut();
	});
	disabledbut();
})

</script>
</head>
<body>
<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>

<c:if test="${isNotWaitRealName}">
<div class="tipsBox infoWrap">
						<div class="tipsTitle">
							<ul>
								<li><i class="iconfont" >&#xe602;</i></li>
								<li class="tipTxt" >实名认证正在审核中，请勿重复申请！</li>
							</ul>
						</div>
						<div class="clear"></div>
						</div>

</c:if>
<c:if test="${!isNotWaitRealName}">
<div class="container">
<div class="bd-container">
<form action="realnameauth_consentAgreement.action" method="post">
<div class="frm-comon mtop10">

		<div class='realNameAuth'>
		<div class="headline">
	<div class="title">实名认证</div>
</div>
		<div class="memFirstSetpFlow setpFlow"></div>
		<div class='memFlowTex'>
			<ul>
			<li class='red'>实名认证说明</li>
			<li class='middle'>填写申请信息</li>
			<li >提交申请，等待审核</li>
			</ul>
		</div>

		<div class="accounts-yellow">
		<h3> <i class="iconfont" style="color:#e60707;font-size:20px;">&#xe600;</i>
		实名认证是一项验证会员身份信息和银行账户信息的服务，通过实名认证可以获得：</h3>

		<p> 使用提现、我要支付等功能时可以确认您的身份；</p>
		<p>  提高会员账户的信用度；</p>
		<p>  提高支付限额</p>

</div>
<p class="clearfix">
  <input type="checkbox" id="ty" onclick="disabledbut();"/> <span>我同意</span>
        <a href="#" class="link-color" onclick="ShowDiv('MyDiv','fade')">实名认证服务协议</a>

</p>

            <input id="Button1"  style="margin-left: 58px;"  class="btn btn-primary"
 type="submit" value="下一步" />



    <div class="clear"></div>

    </div>
    </div>

   </form>

    <div id="fade" class="black_overlay">
    </div>
    <div class="popupMask"></div>
	<div id="MyDiv" class="skipZF">
			<div class="top">
				<div class="skipTit">支付实名认证协议</div>
				<div class="closebtn">
					<img src="<%=path%>/statics/themes/default/images/closebtn2.png"
						onclick="CloseDiv('MyDiv','fade')" />
				</div>
			</div>
			<div class="maincont">
			<div class="content1">
				<div class="tit2">${COMPANY_FOR}支付实名认证协议</div>
				<p style="padding: top">
					为了提高交易的安全性和提高身份的可信度，${COMPANY_FOR}支付向您提供认证服务，本协议由您与广东${COMPANY_FOR}支付有限公司（以下简称本公司）签订。在申请${COMPANY_FOR}支付认证（以下简称认证）时，您已详细阅读了本协议所有内容，您充分理解并同意接受本协议的全部内容。本协议已对与您的权益有或可能具有重大关系的条款，及对本公司具有或可能具有免责或限制责任的条款用粗体字予以标注，请您注意
				</p>
				<p>
				在您申请认证前，您必须先注册成为${COMPANY_FOR}支付用户。本公司有权采取各种必要手段（包括但不限于向第三方确认）对您的身份进行识别。但在目前的技术水平下本公司所能采取的方法有限，且在网络上进行用户身份识别存在一定的困难，因此，本公司对完成认证的用户身份的准确性和绝对真实性不做任何保证。
				</p>
				<p>
				您同意，本公司有权记录并保存您在认证中提供给本公司的信息和本公司向其他合作方获取的信息，亦有权根据本协议的约定向您或第三方提供您是否通过认证以及您的认证信息。
				</p>

        <div class="dvH"></div>
				<strong>一、关于认证服务的理解与认同</strong>
				<p>
				1、认证服务是由${COMPANY_FOR}支付提供的一项身份识别服务。您同意，您有义务按照本公司的要求提供本人的真实身份资料进行注册及认证，并保证诸如电子邮件地址、联系电话、联系地址、邮政编码等信息的有效性，同时也有义务在相关资料发生变更时及时通知本公司进行更新。若因您提供任何错误、不实、过时或不完整资料，或本公司有合理理由怀疑该资料为错误、不实、过时或不完整的，本公司有权暂停或终止对您提供服务，或限制您${COMPANY_FOR}账户的部分或全部功能，本公司对此不承担任何责任。
				</p>
				<p>
				2、${COMPANY_FOR}支付有权单方随时修改或变更本协议内容，并通过${COMPANY_FOR}支付网站公告变更后的协议文本，无需单独通知您。本协议进行任何修改或变更后，您还继续使用${COMPANY_FOR}支付服务和/或认证服务的，即代表您已阅读、了解并同意接受变更后的协议内容；您如果不同意变更后的协议内容，应立即停用${COMPANY_FOR}支付服务和认证服务。
				</p>
				<p>
				3、除非本协议另有约定，一旦您的${COMPANY_FOR}支付账户完成了认证，相关信息和认证结果将不能由您本人进行任何修改；如果您的身份信息等在完成认证后发生了变更，您应按本公司要求提供资料且由本公司审核后进行更新。
                  </p>

        <div class="dvH"></div>
				<strong>二、身份信息识别</strong>
				<p>
				1、中华人民共和国大陆（以下简称大陆）个人${COMPANY_FOR}支付用户提供以下证件用于认证：认证当时处在有效期内的身份证（需要在线上传证件时，必须是彩色原件扫描件/彩色数码拍摄件，第二代身份证需要同时提交正反两面，户籍证明提供日后的有效期在三个月以上，除临时身份证外，有效期三个月以内的证件不予受理）
				</p>
				<p>2、法律规定不具有完全民事行为能力的自然人，${COMPANY_FOR}支付不向其提供认证服务。</p>

				<p>
				3、通过身份信息识别的${COMPANY_FOR}支付用户不能自行修改已经认证的信息，包括但不限于企业名称、姓名以及身份证件号码等。大陆个人${COMPANY_FOR}支付用户认证的有效期与其提供的身份证件有效期一致，但最长自认证完成日起不超过20年，户籍证明从通过审核当日开始起计算，有效期一年。其他个人${COMPANY_FOR}支付用户认证信息的有效期按证件有效期和担保期限两者中较短的有效期计算，但最长自认证完成日起不超过20年。商户类${COMPANY_FOR}支付用户认证信息的有效期一般为一年，若营业期限距离认证通过日少于一年则应以营业期限为准。有效期满后，相应的${COMPANY_FOR}支付账户只能使用原先认证的身份信息或经合法变更后的身份信息进行再次认证。
				</p>

				<p>
				4、如${COMPANY_FOR}支付用户在认证有效期内变更任何身份信息，则应在变更发生后三个工作日内书面通知${COMPANY_FOR}支付变更认证，否则${COMPANY_FOR}支付有权随时单方终止提供${COMPANY_FOR}支付服务，且因此造成的全部后果，由${COMPANY_FOR}支付用户自行承担。
				</p>

				<p>
					5、在${COMPANY_FOR}支付用户对其${COMPANY_FOR}支付账户进行找回密码等操作时，需要按照${COMPANY_FOR}支付的提示出示可确认其持有该账户的个人身份证件，相关证件的要求依照本条前5项约定。
					</p>


<div class="dvH"></div>
				<strong>三、特别声明 </strong>
				<p>
					1、身份认证信息共享：
				</p>
				<p>
				为了使您享有便捷的服务，您经由其它网站向${COMPANY_FOR}支付提交认证申请即表示您同意${COMPANY_FOR}支付为您核对所提交的全部身份信息和银行账户信息，并同意${COMPANY_FOR}支付将是否通过认证的结果及相关身份信息（不包括您的银行账户信息）提供给该网站。
				</p>
				<p>
				2、认证资料的管理：
				</p>
				<p>
				您在认证时提交给${COMPANY_FOR}支付的认证资料，即不可撤销的授权由${COMPANY_FOR}支付保留。${COMPANY_FOR}支付承诺除法定或约定的事由外，不公开或编辑或透露您的认证资料及保存在${COMPANY_FOR}支付的非公开内容用于商业目的，但本条第1项规定以及以下情形除外：
				</p>
				<p>
				1) 您授权${COMPANY_FOR}支付透露的相关信息；
				</p>
				<p>
				2) ${COMPANY_FOR}支付向国家司法及行政机关提供
				</p>
				<p>3) ${COMPANY_FOR}支付向${COMPANY_FOR}支付关联企业提供；</p>
				<p>
				4) 第三方和${COMPANY_FOR}支付一起为用户提供服务时，该第三方向您提供服务所需的相关信息（不包括您的银行账户信息）。
				</p>
				<p>5) 基于解决您与第三方民事纠纷的需要，${COMPANY_FOR}支付有权向该第三方提供您的身份信息。</p>


				<div class="dvH"></div>

				<strong>四、第三方网站的链接</strong>
				<p>
				为实现身份信息审查，${COMPANY_FOR}支付网站(www.gdgwpay.com)上可能包含了指向第三方网站（如网上银行网站）的链接（以下简称“链接网站”）。“链接网站”非${COMPANY_FOR}支付控制，对于任何“链接网站”的内容，包含但不限于“链接网站”内含的任何链接，或“链接网站”的任何改变或更新，${COMPANY_FOR}支付均不予负责。自“链接网站”接收的网络传播或其它形式之传送，${COMPANY_FOR}支付不予负责。
				</p>



<div class="dvH"></div>
<strong>五、不得为非法或禁止的使用 </strong>
					<p>
					接受本协议全部的说明、条款、条件是您申请认证的先决条件。您声明并保证，您不得为任何非法或为本协议、条件及须知所禁止之目的进行认证申请。您不得以任何可能损害、使瘫痪、使过度负荷或损害其他网站或其他网站的服务或${COMPANY_FOR}支付或干扰他人对于${COMPANY_FOR}支付认证申请的使用等方式使用认证服务。您不得经由非${COMPANY_FOR}支付许可提供的任何方式取得或试图取得任何资料或信息。
					</p>

					<div class="dvH"></div>
					<strong>六、有关免责</strong>
					<p>您同意在下列情况时${COMPANY_FOR}支付无需承担任何责任：</p>
					<p>
					1、由于您将${COMPANY_FOR}支付账户密码告知他人或未保管好自己的密码或与他人共享${COMPANY_FOR}支付账户或任何其他非${COMPANY_FOR}支付的过错，导致您的个人资料泄露。
					</p>
					<p>
					2、任何由于黑客攻击、计算机病毒侵入或发作、电信部门技术调整导致之影响、因政府管制而造成的暂时性关闭、由于第三方原因(包括不可抗力，例如国际出口的主干线路及国际出口电信提供商一方出现故障、火灾、水灾、雷击、地震、洪水、台风、龙卷风、火山爆发、瘟疫和传染病流行、罢工、战争或暴力行为或类似事件等)及其他非因${COMPANY_FOR}支付过错而造成的认证信息泄露、丢失、被盗用或被篡改等。
					</p>
					<p>
					3、由于与${COMPANY_FOR}支付链接的其它网站（如网上银行等）所造成的银行账户信息泄露及由此而导致的任何法律争议和后果。
					</p>
					<p>
					4、任何${COMPANY_FOR}支付用户（包括未成年人用户）向${COMPANY_FOR}支付提供错误、不完整、不实信息等造成不能通过认证或遭受任何其他损失，概与${COMPANY_FOR}支付无关。
					</p>

					<div class="dvH"></div>
					<strong>七、协议关系</strong>
					<p>本协议构成《${COMPANY_FOR}支付服务协议》的有效组成部分；本协议未约定的内容，以《${COMPANY_FOR}支付服务协议》的约定为准。</p>
			</div>
			</div>


				<input  style="margin: 10px auto;" class="btn btn-primary agreenBtn" id="Button5" type="button" onclick="CloseDiv('MyDiv','fade')"
						value="已阅读并同意此协议" />



		</div>


    <div class="clear"></div>
    </div>
    </div>
    </c:if>
 <jsp:include page="../../foot.jsp" />
</body>
</html>

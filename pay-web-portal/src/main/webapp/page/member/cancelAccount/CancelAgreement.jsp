<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/page/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注销协议</title>
<%@include file="/page/include/headScript.jsp"%>
<script type="text/javascript">
/*页面分类*/
$(document).ready(function() { setPageType('.men-security', '.men-security-info ');
})


	$(document).ready(function() {
	    $('.nextBtn').attr('disabled',"true");
	    if($('.nextBtn').attr('disabled')==true){
		$('.nextBtn').css('background','#959595');

		}
		else{
		$('.nextBtn').css('background','#D83E24');
		}
		$(".nextBtn").click(function() {
			if ($(".agreement").attr("checked")) {
				$("#form").submit();
			} else {
				 alert("必须同意支付销户协议");
				 return false;
			}
		});

	});
		function disabledbut(){
	if ($(".agreement").attr("checked")) {
		$('.nextBtn').removeAttr("disabled");
		$('.nextBtn').css('background','#D83E24');
	}else{
		$('.nextBtn').attr('disabled',"true");
		$('.nextBtn').css('background','#959595');
	}
	}
</script>
</head>
<hr />
<body>
	<jsp:include page="/page/include/TopMenuMember.jsp"></jsp:include>
	<div class="pageContent">
	<div class="ada-memberinfo">
		<div class="column columnW850">
		<div class="title">会员注销</div>
		</div>
		<div class="ada-wrap">

	<form action="cancelaccount_cancelAgreement.action" method="post">
		<div class="ht"></div>


			<table cellpadding="0" cellspacing="0" class="tbl">
				<tr>
					<td>
					<div class="MBcancel">
						<div class="maincont">
							<div class="content1">
								<h3 class="text-center">${COMPANY_FOR}支付销户证协议
								</h3>
								<p style="padding: top">在您注销您的${COMPANY_FOR}账户之前，请您充分阅读、理解并同意下列事项：</p>

								<div class="dvH"></div>

								<p>
									1、您所申请注销的${COMPANY_FOR}账户应当是您依照本协议的约定注册并由本公司提供给您本人的账户。您应当依照本公司规定的程序或网站上的提示进行${COMPANY_FOR}账户注销。
								</p>
								<p>2、您在${COMPANY_FOR}支付网站上申请注销${COMPANY_FOR}账户，必须使用了本公司提供的安全产品，在该安全产品环境下申请注销。</p>
								<p>
									3、${COMPANY_FOR}账户注销将导致本公司终止为您提供本服务，本协议约定的双方的权利义务终止（依本协议其他条款另行约定不得终止的或依其性质不能终止的除外），同时还可能对于该账户产生如下结果：
								</p>
								<p>A、账户内的积分都将作废；</p>
								<p>B、任何银行卡将不能适用该账户内的支付或提现服务。</p>
								<p>
									4、您申请注销的${COMPANY_FOR}账户应当处于正常状态，即您的${COMPANY_FOR}账户的账户信息和用户信息是最新、完整、正确的，且该账户可以使用所有${COMPANY_FOR}服务功能的状态。账户信息或用户信息过时、缺失、不正确的账户或被冻结的账户不能被申请注销。
								</p>
								<p>
									5、如果您的${COMPANY_FOR}账户有余额，则应先将余额转移出去后才能进行销户。否则，销户成功后，该余额将会作为${COMPANY_FOR}支付的沉淀资金。</p>
								<p>
									6、您申请注销的${COMPANY_FOR}账户应当不存在任何由于该账户被注销而导致的未了结的合同关系与其他基于该账户的存在而产生或维持的权利义务，及本公司认为注销该账户会由此产生未了结的权利义务而产生纠纷的情况。
								</p>
								<p>
									7、如果您的${COMPANY_FOR}账户在连续5年内没有任何账户操作行为或资金变动，且满足本协议规定的其他注销条件时，本公司有权依照本协议的规定或国家相关法律法规的要求主动进行注销，由此引发的一切后果由您本人承担。
								</p>
								<p>
									8、您同意，如您申请注销${COMPANY_FOR}账户时，本公司尚在或需要对您的有关交易或账户进行核查的，本公司有权继续冻结您的${COMPANY_FOR}账户或账户内全部或部分资金以便继续核查，并在必要时有权向监管或司法部门举报欺诈或非法行为。
								</p>
								<p>9、本协议未尽事宜，参照《${COMPANY_FOR}服务协议》执行。</p>

								<div class="dvH"></div>
								<h3>二、身份信息识别</h3>
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
								<h3>三、特别声明 </h3>
								<p>1、身份认证信息共享：</p>
								<p>
									为了使您享有便捷的服务，您经由其它网站向${COMPANY_FOR}支付提交认证申请即表示您同意${COMPANY_FOR}支付为您核对所提交的全部身份信息和银行账户信息，并同意${COMPANY_FOR}支付将是否通过认证的结果及相关身份信息（不包括您的银行账户信息）提供给该网站。
								</p>
								<p>2、认证资料的管理：</p>
								<p>
									您在认证时提交给${COMPANY_FOR}支付的认证资料，即不可撤销的授权由${COMPANY_FOR}支付保留。${COMPANY_FOR}支付承诺除法定或约定的事由外，不公开或编辑或透露您的认证资料及保存在${COMPANY_FOR}支付的非公开内容用于商业目的，但本条第1项规定以及以下情形除外：
								</p>
								<p>1) 您授权${COMPANY_FOR}支付透露的相关信息；</p>
								<p>2) ${COMPANY_FOR}支付向国家司法及行政机关提供</p>
								<p>3) ${COMPANY_FOR}支付向${COMPANY_FOR}支付关联企业提供；</p>
								<p>4) 第三方和${COMPANY_FOR}支付一起为用户提供服务时，该第三方向您提供服务所需的相关信息（不包括您的银行账户信息）。</p>
								<p>5) 基于解决您与第三方民事纠纷的需要，${COMPANY_FOR}支付有权向该第三方提供您的身份信息。</p>


								<div class="dvH"></div>

								<h3>四、第三方网站的链接</h3>
								<p>
									为实现身份信息审查，${COMPANY_FOR}支付网站(www.gdgwpay.com)上可能包含了指向第三方网站（如网上银行网站）的链接（以下简称“链接网站”）。“链接网站”非${COMPANY_FOR}支付控制，对于任何“链接网站”的内容，包含但不限于“链接网站”内含的任何链接，或“链接网站”的任何改变或更新，${COMPANY_FOR}支付均不予负责。自“链接网站”接收的网络传播或其它形式之传送，${COMPANY_FOR}支付不予负责。
								</p>



								<div class="dvH"></div>
								<h3>五、不得为非法或禁止的使用 </h3>
								<p>
									接受本协议全部的说明、条款、条件是您申请认证的先决条件。您声明并保证，您不得为任何非法或为本协议、条件及须知所禁止之目的进行认证申请。您不得以任何可能损害、使瘫痪、使过度负荷或损害其他网站或其他网站的服务或${COMPANY_FOR}支付或干扰他人对于${COMPANY_FOR}支付认证申请的使用等方式使用认证服务。您不得经由非${COMPANY_FOR}支付许可提供的任何方式取得或试图取得任何资料或信息。
								</p>

								<div class="dvH"></div>
								<h3>六、有关免责</h3>
								<p>您同意在下列情况时${COMPANY_FOR}支付无需承担任何责任：</p>
								<p>
									1、由于您将${COMPANY_FOR}支付账户密码告知他人或未保管好自己的密码或与他人共享${COMPANY_FOR}支付账户或任何其他非${COMPANY_FOR}支付的过错，导致您的个人资料泄露。
								</p>
								<p>
									2、任何由于黑客攻击、计算机病毒侵入或发作、电信部门技术调整导致之影响、因政府管制而造成的暂时性关闭、由于第三方原因(包括不可抗力，例如国际出口的主干线路及国际出口电信提供商一方出现故障、火灾、水灾、雷击、地震、洪水、台风、龙卷风、火山爆发、瘟疫和传染病流行、罢工、战争或暴力行为或类似事件等)及其他非因${COMPANY_FOR}支付过错而造成的认证信息泄露、丢失、被盗用或被篡改等。
								</p>
								<p>3、由于与${COMPANY_FOR}支付链接的其它网站（如网上银行等）所造成的银行账户信息泄露及由此而导致的任何法律争议和后果。</p>
								<p>
									4、任何${COMPANY_FOR}支付用户（包括未成年人用户）向${COMPANY_FOR}支付提供错误、不完整、不实信息等造成不能通过认证或遭受任何其他损失，概与${COMPANY_FOR}支付无关。
								</p>

								<div class="dvH"></div>
								<h3>七、协议关系</h3>
								<p>本协议构成《${COMPANY_FOR}支付服务协议》的有效组成部分；本协议未约定的内容，以《${COMPANY_FOR}支付服务协议》的约定为准。</p>
							</div>
						</div>
						</div>
						</td>

				</tr>
				<tr align="center">
					<td><input id='checkAgree' type="checkbox" class="agreement"
						name="consentAgreement" value="true" onclick="disabledbut();"/> 我同意支付销户协议</td>
				</tr>
				<tr><td style="height:5px;"></td></tr>
				 <tr align="center">
				 <td>
					<input id="btn_next" class="submitBtn nextBtn" type="submit" style="margin-left: 280px;"
						value="下一步" />   <a class="cancelBtn" style="float:left ; margin-left: 10px;"  href="javascript:history.back(-1)">
							&nbsp; 取 消</a></td>
				</tr>
			</table>
			<br style="clear: both;" />

	</form>
	<div class="clear"></div>
	</div>
	<div class="clear"></div>
	</div>



	<div class="clear"></div>
	</div>

	<div class="ht"></div>

	<jsp:include page="../../foot.jsp" />

</body>
</html>

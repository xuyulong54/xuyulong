var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
var bm=/^1[3|4|5|8][0-9]\d{4,8}$/;
var loginPwd1 = /.*?[^a-zA-Z\d]+.*?/;
var loginPwd2 = /.*?[a-zA-Z]+\.*?/;
var loginPwd3 = /.*?[\d]+.*?/;
var zzs=/^[1-9]\d*$/;

function validateMerchantForm(){
	//登录名
	if($("#loginName").val()==""){
		alertMsg.info("登录名不能为空,请输入登录名!");
		$("#loginName").focus();
		return false;
	}
	if(!emailReg.test($("#loginName").val())){
		alertMsg.info("登录名必须为邮箱格式!");
		$("#loginName").focus();
		return false;
	}
	//商户名称(签约名)
	if($("#fullName").val()==""){
		alertMsg.info("商户名称(签约名)不能为空,请输入商户名称(签约名)!");
		$("#fullName").focus();
		return false;
	}
	//商户简称
	if($("#shortName").val()==""){
		alertMsg.info("商户简称不能为空,请输入商户简称!");
		$("#shortName").focus();
		return false;
	}
	//营业执照号															
	if($("#licenseNo").val()==""){
		alertMsg.info("营业执照号不能为空,请输入15位数字营业执照号!");
		$("#licenseNo").focus();
		return false;
	}
	if($("#licenseNo").val().length !=15){
		alertMsg.info("营业执照号长度为15!");
		$("#licenseNo").focus();
		return false;
	}
	if(!zzs.exec(($("#licenseNo").val()))){
		alertMsg.info("请输入合法营业执照号!");
		$("#licenseNo").focus();
		return false;
	}
	//经营范围
	if($("#scope").val()==""){
		alertMsg.info("经营范围不能为空,请输入2-100个字经营范围!");
		$("#scope").focus();
		return false;
	}
	if($("#scope").val().length <2 || $("#scope").val().length >100){
		alertMsg.info("经营范围长度为2-100!");
		$("#scope").focus();
		return false;
	}
	//联系人
	if($("#busiContactName").val()==""){
		alertMsg.info("联系人不能为空,请输入联系人!");
		$("#busiContactName").focus();
		return false;
	}
	//联系人电话
	if($("#busiContactMobileNo").val()==""){
		alertMsg.info("联系人电话不能为空,请输入联系人电话!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	if(!bm.exec(($("#busiContactMobileNo").val()))){
		alertMsg.info("请输入合法联系人电话!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	//省份
	if($("#province").val()=="0"){
		alertMsg.info("请输入省份!");
		$("#province").focus();
		return false;
	}
	//城市
	if($("#city").val()=="0"){
		alertMsg.info("请输入城市!");
		$("#city").focus();
		return false;
	}
	//县(区)
	if($("#area").val()=="0"){
		alertMsg.info("请输入县(区)!");
		$("#area").focus();
		return false;
	}
	//企业地址
	if($("#address").val()==""){
		alertMsg.info("企业地址不能为空,请输入8-50个字!");
		$("#address").focus();
		return false;
	}
	if($("#address").val().length <8 ||  $("#address").val().length >50){
		alertMsg.info("企业地址长度为8-50!");
		$("#address").focus();
		return false;
	}
	//绑定手机
	if($("#bindMobileNo").val()==""){
		alertMsg.info("手机号码不能为空,请输入绑定手机号码!");
		$("#bindMobileNo").focus();
		return false;
	}
	if(!bm.exec(($("#bindMobileNo").val()))){
		alertMsg.info("请输入合法手机号码!");
		$("#bindMobileNo").focus();
		return false;
	}
	//商户费率
	if($("#rate").val()==""){
		alertMsg.info("商户费率不能为空,请输入商户费率!");
		$("#rate").focus();
		return false;
	}
	//MCC费率
	if($("#mccRate").val()==""){
		alertMsg.info("MCC费率不能为空,请输入MCC费率!");
		$("#mccRate").focus();
		return false;
	}
	//封顶金额
	if($("#fixedAmt").val()==""){
		alertMsg.info("封顶金额不能为空,请输入封顶金额!");
		$("#fixedAmt").focus();
		return false;
	}
	if($("#oneProfit").val()==""){
		alertMsg.info("返佣比例不能为空,请输入返佣比例!");
		$("#oneProfit").focus();
		return false;
	}
	if(parseInt($("#oneProfit").val()) <=0 ||  parseInt($("#oneProfit").val())>=100){
		alertMsg.info("返佣比例在0-100之间!");
		$("#oneProfit").focus();
		return false;
	}
	//商户类型
	if($("#merchantType").val()==""){
		alertMsg.info("请选择商户类型！");
		$("#merchantType").focus();
		return false;
	}
	return true;
}
//验证商户账户信息
function validateAccountForm(){
	//银行账户名
		if($("#bankAccountName").val()==""){
			alertMsg.info("银行账户名不能为空,请输入银行账户名!");
			$("#bankAccountName").focus();
			return false;
		}
		//银行账号
		if($("#bankAccountNo").val()==""){
			alertMsg.info("银行账号不能为空,请输入银行账号!");
			$("#bankAccountNo").focus();
			return false;
		}
		//账户类型
		if($("#publicList").val()==""){
			alertMsg.info("请选择账户类型!");
			$("#publicList").focus();
			return false;
		}
		//银行所在地省份
		if($("#accountProvince").val()=="请选择"){
			alertMsg.info("省份不能为空,请输入省份!");
			$("#accountProvince").focus();
			return false;
		}
		//银行所在地城市
		if($("#accountCity").val()=="请选择"){
			alertMsg.info("城市不能为空,请输入城市!");
			$("#accountCity").focus();
			return false;
		}
		//开户银行
		if($("#bankName").val()=="请选择"){
			alertMsg.info("开户银行不能为空,请输入开户银行!");
			$("#bankName").focus();
			return false;
		}
		//支行
		if($("#branchBankName").val()==""){
			alertMsg.info("支行不能为空,请输入支行!");
			$("#branchBankName").focus();
			return false;
		}
		return true;
	}
//结算方式
function validateSettTypeForm(){
	//结算方式
	if($("#status2").val()=="1"){
		//具体参数
		if($("input:checked").val()=="0"){
			alertMsg.info("具体参数不能为空,请输入具体参数!");
			$("input:checked").focus();
			return false;
		}
	}
	//风险预存期
	if($("#riskTimelimitDay").val()==""){
		alertMsg.info("风险预存期不能为空,请输入具体风险预存期!");
		$("#riskTimelimitDay").focus();
		return false;
	}
	//最低结算金额
	if($("#minSettAmount").val()==""){
		alertMsg.info("最低结算金额不能为空,请输入最低结算金额!");
		$("#minSettAmount").focus();
		return false;
	}
	
	return true;
}
//验证业务设置 
function validateBusinessTypeForm(){
	//开通功能
	if($("input:checked").val()=="0"){
		alertMsg.info("开通功能不能为空,请选择开通功能!");
		$("input:checked").focus();
		return false;
	}
	return true;
}


function validateMerchantEditForm(){
	//登录名
	if($("#loginName").val()==""){
		alertMsg.info("登录名不能为空,请输入登录名!");
		$("#loginName").focus();
		return false;
	}
	if(!emailReg.test($("#loginName").val())){
		alertMsg.info("登录名必须为邮箱格式!");
		$("#loginName").focus();
		return false;
	}
	//商户名称(签约名)
	if($("#fullName").val()==""){
		alertMsg.info("商户名称(签约名)不能为空,请输入商户名称(签约名)!");
		$("#fullName").focus();
		return false;
	}
	//商户简称
	if($("#shortName").val()==""){
		alertMsg.info("商户简称不能为空,请输入商户简称!");
		$("#shortName").focus();
		return false;
	}
	//营业执照号
	if($("#licenseNo").val()==""){
		alertMsg.info("营业执照号不能为空,请输入营业执照号!");
		$("#licenseNo").focus();
		return false;
	}
	if($("#licenseNo").val().length !=15){
		alertMsg.info("营业执照号长度为15!");
		$("#licenseNo").focus();
		return false;
	}
	if(!zzs.exec(($("#licenseNo").val()))){
		alertMsg.info("请输入合法营业执照号!");
		$("#licenseNo").focus();
		return false;
	}
	//经营范围
	if($("#scope").val()==""){
		alertMsg.info("经营范围不能为空,请输入2-100个字经营范围!");
		$("#scope").focus();
		return false;
	}
	if($("#scope").val().length <2 || $("#scope").val().length >100){
		alertMsg.info("经营范围长度为2-100!");
		$("#scope").focus();
		return false;
	}
	//联系人
	if($("#busiContactName").val()==""){
		alertMsg.info("联系人不能为空,请输入联系人!");
		$("#busiContactName").focus();
		return false;
	}
	//联系人电话
	if($("#busiContactMobileNo").val()==""){
		alertMsg.info("联系人电话不能为空,请输入联系人电话!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	if(!bm.exec(($("#busiContactMobileNo").val()))){
		alertMsg.info("请输入合法联系人电话!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	//省份
	if($("#province").val()=="0"){
		alertMsg.info("请输入省份!");
		$("#province").focus();
		return false;
	}
	//城市
	if($("#city").val()=="0"){
		alertMsg.info("请输入城市!");
		$("#city").focus();
		return false;
	}
	//县(区)
	if($("#area").val()=="0"){
		alertMsg.info("请输入县(区)!");
		$("#area").focus();
		return false;
	}
	//企业地址
	if($("#address").val()==""){
		alertMsg.info("企业地址不能为空,请输入2-50个字!");
		$("#address").focus();
		return false;
	}
	if($("#address").val().length <2 ||  $("#address").val().length >50){
		alertMsg.info("企业地址长度为8-50!");
		$("#address").focus();
		return false;
	}
	//预留信息
	if($("#greeting").val()==""){
		alertMsg.info("预留信息不能为空,请输入5-20个字预留信息!");
		$("#greeting").focus();
		return false;
	}
	//绑定手机
	if($("#bindMobileNo").val()==""){
		alertMsg.info("手机号码不能为空,请输入绑定手机号码!");
		$("#bindMobileNo").focus();
		return false;
	}
	if(!bm.exec(($("#bindMobileNo").val()))){
		alertMsg.info("请输入合法手机号码!");
		$("#bindMobileNo").focus();
		return false;
	}
	//商户费率d
	if($("#rate").val()=="" || $("#rate").val()>=100 || $("#rate").val()<=0){
		alertMsg.info("商户费率必须大于0小于1!");
		$("#rate").focus();
		return false;
	}
	//封顶金额
	if($("#fixedAmt").val()==""){
		alertMsg.info("封顶金额不能为空,请输入封顶金额!");
		$("#fixedAmt").focus();
		return false;
	}
	return true;
}
 
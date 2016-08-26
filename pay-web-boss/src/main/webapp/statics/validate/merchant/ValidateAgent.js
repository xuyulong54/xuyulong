
var emailReg = /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/;
var phone=/^1[3|4|5|8][0-9]\d{4,8}$/;
var loginPwd1 = /.*?[^a-zA-Z\d]+.*?/;
var loginPwd2 = /.*?[a-zA-Z]+\.*?/;
var loginPwd3 = /.*?[\d]+.*?/;
var zzs=/^[1-9]\d*$/;
var idcard=/^[1-9]([0-9]{14}|[0-9]{17})$/;

//添加代理商
function addMerchantValidate(){
	//绑定手机
	if(!validateStr("bindMobileNo","绑定手机")){return false;};
	if(!phone.exec(getIdValue("bindMobileNo"))){
		alertMsg.info("请输入合法手机号码!");
		$("#bindMobileNo").focus();
		return false;
	}
	//全称
	if(!validateStr("fullName","全称")){return false;};
	
	//简称
	if(!validateStr("shortName","简称")){return false;};
	
	//业务类型
	if(!validateStrSel("merchantTypeId","业务类型")){return false;};
	
	if(getIdValue("merchantTypeId")!=10){
		if(!validateStrSel("licenseNo","营业执照号")){return false;};
		//if(!validateStrSel("nationaltax","税务登记证号")){return false;};
	}
	
	//联系人
	if(!validateStr("busiContactName","联系人")){return false;};
	
	//联系人电话
	if(!validateStr("busiContactMobileNo","联系人电话")){return false;};
	if(!phone.exec(getIdValue("busiContactMobileNo"))){
		alertMsg.info("请输入合法联系人电话!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	
	//身份证
	if(!validateStr("cardNo","身份证")){return false;};
	if(!idcard.exec(($("#cardNo").val()))){
		alertMsg.info("请输入合法身份证号码!");
		$("#busiContactMobileNo").focus();
		return false;
	}
	
	//MCC
	if(!validateStr("mccma","MCC")){return false;};
	
	//费率
	if(!validateStr("rate","费率")){return false;};
	
	//封顶金额
	if(!validateStr("fixedAmt","封顶金额")){return false;};
	
	//经验范围
	if(!validateStr("scope","经验范围")){return false;};
	
	//企业地址
	if(!validateStrSel("province","企业地址（省）")){return false;};
	if(!validateStrSel("city","企业地址（市）")){return false;};
	if(!validateStr("address","企业地址")){return false;};
	
	
	//银行账户名
	if(!validateStr("bankAccountName","银行账户名")){return false;};

	//银行账号
	if(!validateStr("bankAccountNo","银行账号")){return false;};
	
	//开户银行
	if(!validateStrSel("bankName","开户银行")){return false;};
	
	//银行账户类型
	if(getIdValue("merchantTypeId")==10){
		if(!validateStrSel("privateBankAccountType","银行账户类型")){return false;};
	}else{
		if(!validateStrSel("publicBankAccountType","银行账户类型")){return false;};
	}
	
	
	
	//银行地址
	if(!validateStrSel("accountProvince","银行地址（省）")){return false;};
	if(!validateStrSel("accountCity","银行地址（市）")){return false;};
	if(!validateStr("bankAccountAddress","银行地址")){return false;};
	
	//风险预存期
	if(!validateStr("riskTimelimitDay","风险预存期")){return false;};
	//最低结算金额
	if(!validateStr("minSettAmount","最低结算金额")){return false;};
	 return true;
}



//添加代理商
function addAgentValidate(){

	//登录名
	if(!validateStr("loginName_agent","登录名")){return false;};
	if(!emailReg.test(getIdValue("loginName_agent"))){
		alertMsg.info("登录名必须为邮箱格式!");
		$("#loginName_agent").focus();
		return false;
	}
	//绑定手机
	if(!validateStr("bindMobileNo_agent","绑定手机")){return false;};
	if(!phone.exec(getIdValue("bindMobileNo_agent"))){
		alertMsg.info("请输入合法手机号码!");
		$("#bindMobileNo_agent").focus();
		return false;
	}
	//全称
	if(!validateStr("fullName_agent","全称")){return false;};
	
	//简称
	if(!validateStr("shortName_agent","简称")){return false;};
	
	//业务类型
	//if(!validateStrSel("merchantTypeId_agent","业务类型")){return false;};
	
	if(getIdValue("merchantTypeId")!=10){
		if(!validateStrSel("licenseNo_agent","营业执照号")){return false;};
		if(!validateStrSel("nationaltax_agent","税务登记证号")){return false;};
	}
	
	//联系人
	if(!validateStr("busiContactName_agent","联系人")){return false;};
	
	//联系人电话
	if(!validateStr("busiContactMobileNo_agent","联系人电话")){return false;};
	if(!phone.exec(getIdValue("busiContactMobileNo_agent"))){
		alertMsg.info("请输入合法联系人电话!");
		$("#busiContactMobileNo_agent").focus();
		return false;
	}
	
	//身份证
	if(!validateStr("cardNo_agent","身份证")){return false;};
	if(!idcard.exec(($("#cardNo_agent").val()))){
		alertMsg.info("请输入合法身份证号码!");
		$("#cardNo_agent").focus();
		return false;
	}
	
	
	
	//返拥比例
	if(!validateStr("rate_agent","返拥比例")){return false;};
	
	//保证金
	if(!validateStr("deposit_agent","保证金")){return false;};
	
	//经验范围
	if(!validateStr("scope_agent","经验范围")){return false;};
	
	//企业地址
	if(!validateStrSel("province","企业地址（省）")){return false;};
	if(!validateStrSel("city","企业地址（市）")){return false;};
	if(!validateStr("address_agent","企业地址")){return false;};
	
	
	//银行账户名
	if(!validateStr("bankAccountName_agent","银行账户名")){return false;};

	//银行账号
	if(!validateStr("bankAccountNo_agent","银行账号")){return false;};
	
	//开户银行
	if(!validateStrSel("bankName_agent","开户银行")){return false;};
	
	//银行账户类型
	if(getIdValue("merchantTypeId")==10){
		if(!validateStrSel("privateBankAccountType","银行账户类型")){return false;};
	}else{
		if(!validateStrSel("publicBankAccountType","银行账户类型")){return false;};
	}
	
	
	
	//银行地址
	if(!validateStrSel("accountProvince","银行地址（省）")){return false;};
	if(!validateStrSel("accountCity","银行地址（市）")){return false;};
	if(!validateStr("bankAccountAddress_agent","银行地址")){return false;};
	
	//风险预存期
	if(!validateStr("riskTimelimitDay_agent","风险预存期")){return false;};
	//最低结算金额
	if(!validateStr("minSettAmount_agent","最低结算金额")){return false;};
	
    return true;
	
}






//输入框
function validateStr(id,desc){
	if(isNull(getIdValue(id))){
		alertMsg.info(desc+"不能为空,请输入"+desc+"!");
		$("#"+id).focus();
		return false; 
	}
	return true;
}
//下拉框
function validateStrSel(id,desc){
	if(isNull(getIdValue(id))){
		alertMsg.info("请选择"+desc+"!");
		$("#"+id).focus();
		return false; 
	}
	return true;
}

function getIdValue(id){
  return  $("#"+id).val();
};

function isNull(temp){
 if(temp==null || temp==''){
  return true;
 }
 return false;
};

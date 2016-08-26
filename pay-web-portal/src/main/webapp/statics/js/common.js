function selectDate(str, obj) {
	var now = new Date(); // 获取当前时间
	var year = now.getFullYear(); // 获取当前日期的年份部分
	// if ($.browser.mozilla) { year = year + 1900; } //如果是火狐浏览器，则需要加上1900年
	// if ($.browser.opera) { year = year + 1900; } //如果是opera浏览器，则需要加上1900年
	// if ($.browser.safari) { year = year + 1900; } //如果是safari浏览器，则需要加上1900年
	// if (!$.browser.msie) { year = year + 1900;} //如果不是IE浏览器，则需要加上1900年
	var month = now.getMonth() + 1; // 获取当前日期的月份部分
	var day = now.getDate(); // 获取当前日期的天数部分
	// alert(year); //opera safari msie
	// 获取当前月份包含的天数
	var nowdays = new Date(year, month, 0).getDate();
	var lastMonth = month - 1;
	var lastMonthdays = new Date(year, lastMonth, 0).getDate();

	var startTime = "";
	var endTime = "";
	switch (str) {
	case "toDay":
		if (month < 10) {
			month = '0' + month;
		}
		if (day < 10) {
			day = '0' + day;
		}
		startTime = year + "-" + month + "-" + day;
		endTime = year + "-" + month + "-" + day;
		break;
	case "lastMonth":
		if (lastMonth == 0) {
			lastMonth = 12;
			year = year - 1;
		}
		if (lastMonth < 10) {
			lastMonth = '0' + lastMonth;
		}
		if (lastMonthdays < 10) {
			lastMonthdays = '0' + lastMonthdays;
		}
		startTime = year + "-" + lastMonth + "-01";
		endTime = year + "-" + lastMonth + "-" + lastMonthdays;
		break;
	case "currentMonth":
		if (month < 10) {
			month = '0' + month;
		}
		if (nowdays < 10) {
			nowdays = '0' + nowdays;
		}
		startTime = year + "-" + month + "-01";
		// endTime = year + "-" + month + "-" + day;
		endTime = year + "-" + month + "-" + nowdays;
		break;
	case "lastYear":
		startTime = (year - 1) + "-01-01";
		endTime = (year - 1) + "-12-31";
		break;
	case "currentYear":
		startTime = year + "-01-01";
		endTime = year + "-12-31";
		break;
	case "custom":
		
		break;
	default:
		if (month < 10) {
			month = '0' + month;
		}
		if (day < 10) {
			day = '0' + day;
		}
		startTime = year + "-" + month + "-" + day;
		endTime = year + "-" + month + "-" + day;
		break;		
	}
	// alert(endTime);
	// if (str != "all") {
	$("#beginDate").val(startTime);
	$("#endDate").val(endTime);
	// }
	// $("#td_Date a").removeClass(); //清空所有a标签的css
	// $(obj).addClass("Fcurrent"); //为当前选中的a标签增加css
}

// 获取两个日期相隔的天数
function daysBetween(beginDate, endDate) {
	var now1 = new Date(beginDate);
	var now2 = new Date(endDate);
	return (now2 - now1) / (1000 * 3600 * 24);
}
// 发送短信方法
var i = 5;// 获取次数
var ss = 60;// 显示秒数
var id;
// 手机号，登录名，用户类型，是否绑定，短信模板类型，发送短信ID，显示信息DIV的ID
function sendSms(phone, loginName, userType, bindingType, smsType, buttonId,
		phoneCodeMsg) {

	$.post("sms_getSms.action", {
		'phone' : phone,
		'loginName' : loginName,
		'type' : userType,
		'bindingType' : bindingType,
		'smsType' : smsType
	}, function(data) {
		var message = data[0].message;
		if (data[0].status == 1) {// 成功
			$("#" + phoneCodeMsg).html(message);
			ss = 60;
			$("#" + buttonId).attr('disabled', true);
      $("#" + buttonId).addClass('disable');
			id = window.setInterval("bb('" + buttonId + "','" + phoneCodeMsg
					+ "')", 1000);
		} else {
			$("#" + phoneCodeMsg).html(message);
		}
	}, "json");
}
function bb(buttonId, phoneCodeMsg) {
	if (i > 0) {
		ss--;
		if (ss < 0) {
			i--;
			$("#" + buttonId).val("重新获取");
			$("#" + buttonId).attr('disabled', false);
      $("#" + buttonId).removeClass('disable');
			window.clearInterval(id);
		} else {
			$("#" + buttonId).val(ss + "秒后重新获取");
		}
	} else {
		$("#" + phoneCodeMsg).html("您的操作过于频繁");
		$("#" + buttonId).hide();
	}
}

/**
 * 汉字转换拼音 全拼 简拼
 *
 * @author Hill
 * @param value
 *            传入要转换的字符串
 * @param fun
 *            传入结果处理方法,方法第一个参数为转换结果r,r.simple为简拼,r.full为全拼
 */
function toPinyin(value, fun) {
	if (value != null && value != '') {
		$.post("pinyin_getPinyin.action", {
			value : value
		}, function(res) {
			if (typeof (fun) == 'function') {
				fun.call(null, res);
			}
		}, 'json');
	}
}


//重新发送邮件
function resendEmail(userNo,userType,loginName,toMail,mailType) {
		$.post("resendemail_resendEmail.action", {
			'userNo' : userNo,
			'userType' : userType,
			'loginName' : loginName,
			'toMail' : toMail,
			'mailType' : mailType
		}, function(data) {
		}, "json");
	}

//判断未登录页面是属于哪个导航,创建激活导航状态。
function setPageType(FirstClassName, SeconClassName) {

    if(typeof(SeconClassName)=='undefined'){
    	$(FirstClassName).siblings().removeClass('active');
    	$(FirstClassName).addClass('active');
    }
    /*一级菜单状态*/
    else{
    $(FirstClassName).siblings().find('a').removeClass('active');
    $(FirstClassName).find('a').addClass('active');


    /*二级菜单状态*/
    $(SeconClassName).siblings().find('a').removeClass('active');
    $(SeconClassName).find('a').addClass('active');

    /*二级菜单显示和隐藏*/
    $(SeconClassName).parents("div").addClass('block');
    $(SeconClassName).parents("div").siblings().removeClass('block');



    /*滑动条状态*/
    $(FirstClassName).siblings().find('a').removeClass('current');
    $(FirstClassName).find('a').addClass('current');

    $(".nav-current").css('display', 'block');
    var itemW = $(".nav").find('.current').innerWidth(); //默认当前位置宽度
    var defLeftW = $(".nav").find('.current').position().left;  //默认当前位置Left值
    //添加默认下划线
    $(".nav-current").css({ width: itemW, left: defLeftW });

}
}

//导航滑动条
function navSlide() {
    var $navcur = $(".nav-current");
    var $nav = $(".nav");
    var current = ".current";
    var itemW = $nav.find(current).innerWidth();
    var defLeftW = $nav.find(current).position().left;
    //添加默认下划线
    $navcur.css({ width: itemW, left: defLeftW });
    //hover事件
    $nav.find("a").mousemove(function() {
        var index = $(this).index();
        var leftW = $(this).position().left;
        var currentW = $nav.find("a").eq(index).innerWidth();
        $navcur.stop().animate({
            left: leftW,
            width: currentW
        }, 300);

    })

}

//判断日期是否为空(日期ID)
function isEDate(date){
	var date=$('#'+date).val();
	if(date==''||date==null){
		return true;
	}
	else{
	return false;
	}
}

//开始日期是否大于结束日期(开始日期ID,结束日期ID)
function judeDate(beginDate,endDate){
	var begin=$('#'+beginDate).val();
	var end=$('#'+endDate).val();
	var now1 = new Date(begin);
	var now2 = new Date(end);
	var between=(now2 - now1) / (1000 * 3600 * 24);
	if(between<0){
		return true;

	}
	else{
		return false;
		}
}

//判断两日是否相隔大于n天。(开始日期ID,结束日期ID,相隔num天)
function dateApart(beginDate,endDate,num){
	var begin=$('#'+beginDate).val();
	var end=$('#'+endDate).val();
	var now1 = new Date(begin);
	var now2 = new Date(end);
	var between=(now2 - now1) / (1000 * 3600 * 24);
	if(between>num){
		return true;
	}
	else{
		return false;
		}
}

//获取当天时间
function getNowDate(){
	var now = new Date(); //获取当前时间
	var year = now.getFullYear(); //获取当前日期的年份部分
	var month = now.getMonth() + 1; //获取当前日期的月份部分
	var day = now.getDate(); //获取当前日期的天数部分
	if (month < 10) {
		month = '0' + month;
	}
	if (day < 10) {
		day = '0' + day;
	}
	var nowDay = year + "-" + month + "-" + day;
	return nowDay;
}

//获取addDayCount天后的日期
function getDateStr(addDayCount) {
    var dd = new Date();
    dd.setDate(dd.getDate()+addDayCount);//获取addDayCount天后的日期
    var y = dd.getFullYear();
    var m = dd.getMonth()+1; //获取当前月份
    if (m<10){
    	m = "0" + m;
    }
    var d = dd.getDate();
    if (d < 10){
    	d = "0" + d;
    }
    return y+"-"+m+"-"+d;
}

//给ID为id的赋值str
function setStr(id,str){
	$("#"+id).html(str);
}

//是id为id的设置为display:为inline-display
function setDisplay(id){
	$("#"+id).css('display','inline-block');
}

//居中弹出层
function centerMask(selector1,selector2){
		$(selector1).css('width',$(window).width()+'px');
		$(selector1).css('height',$(window).height()+'px');
		$(window).bind('resize',function(){
		$(selector1).css('width',$(window).width()+'px');
		$(selector1).css('height',$(window).height()+'px');
		});
		var l=($(window).width()-$(selector2).width())/2;
		var t=($(window).height()-$(selector2).height())/2;
		$(selector2).css('top',t+'px');
		$(selector2).css('left',l+'px');
		$(window).bind('resize',function(){
		var l=($(window).width()-$(selector2).width())/2;
		var t=($(window).height()-$(selector2).height())/2;
		$(selector2).css('top',t+'px');
		$(selector2).css('left',l+'px');
		document.documentElement.style.overflow = 'hidden';
		});
}
//弹出层显示
function showAlert(show_div,show_mask){
	$(show_div).show();
	$(show_mask).show();
}

//关闭弹出层
function hideAlert(show_div,show_mask){
        $(show_div).hide();
        $(show_mask).hide();
	    document.documentElement.style.overflow = 'auto';
}


//阅读协议的弹出层
/*------------弹出、关闭隐藏层---------------*/

//弹出隐藏层
function ShowDiv(show_div, bg_div) {

    document.getElementById(show_div).style.display = 'block';
    document.getElementById(bg_div).style.display = 'block';
    var bgdiv = document.getElementById(bg_div);
    bgdiv.style.width = document.body.scrollWidth;
    // bgdiv.style.height = $(document).height();
    $("#" + bg_div).height($(document).height());
};
//关闭弹出层
function CloseDiv(show_div, bg_div) {
    document.getElementById(show_div).style.display = 'none';
    document.getElementById(bg_div).style.display = 'none';
};
/*------------弹出、关闭隐藏层 结束---------------*/

/*------------密码格式进行校验------------------*/
function  validatePwd(pwd){
var msg="密码应为8-20位数字,字母和特殊符号组合";
 if(pwd==null||pwd==''){
    return msg;
 }
 if(pwd.length<8 || pwd.length>20){
 	return msg;
 }
 var pwdReg=/(?=.*[\d]+)(?=.*[a-zA-Z]+)(?=.*[^a-zA-Z0-9]+).{8,20}/;
 var pwdRegEn=/[a-zA-Z]+/;
 var pwdRegNum=/[0-9]+/;
 var pwdRegEnNum=/^[a-zA-Z0-9]+$/;
 //判断是否包含字母
 if(!pwdRegEn.test(pwd)){
 	return msg;
 }
 //判断是否包含数字
 if(!pwdRegNum.test(pwd)){
 	return msg;
 }
 //判断只包含数字和字母
 if(pwdRegEnNum.test(pwd)){
 	return msg;
 }
 return '';
}

//去除密码控件/CA控件产生的页面顶部间隙
$(function(){
  $('#versionObj').addClass('pst');
  $('#FakeCryptoAgent').addClass('pst');
})

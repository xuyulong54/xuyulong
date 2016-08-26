	$(document).ready(function() {

       //选择条件下拉框显示隐藏控制
       $('#timeType').change(function() {

       	var selectVal=$(this).find("option:selected").text();
           if (selectVal == '自定义') {
              $('#time').removeClass('none');
           } else {
			  $('#time').addClass('none');
           }
       })


       //查询结果表格样式控制
       $('.table-hover>tbody>tr:odd').addClass('split');

   })

//转换时间格式
  function parseISO8601(dateStringInRange) {
    var isoExp = /^\s*(\d{4})-(\d\d)-(\d\d)\s*$/,
        date = new Date(NaN), month,
        parts = isoExp.exec(dateStringInRange);

    if(parts) {
      month = +parts[2];
      date.setFullYear(parts[1], month - 1, parts[3]);
      if(month != date.getMonth() + 1) {
        date.setTime(NaN);
      }
    }
    return date;
  }



//日期选择
function selectDate(str){
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

	var startTime = $("beginDate").val();
	var endTime =  $("endDate").val();
	if(str==null||str==""){
		str="toDay";
	}
	switch (str) {
	case "toDay"://今天
		if (month < 10) {
			month = '0' + month;
		}
		if (day < 10) {
			day = '0' + day;
		}
		startTime = year + "-" + month + "-" + day;
		endTime = year + "-" + month + "-" + day;
		$("#timeType").val("toDay");
		$("#time").hide();
		break;
	case "lastMonth"://上月
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
		$("#timeType").val("lastMonth");
		$("#time").hide();
		break;
	case "currentMonth"://本月
		if (month < 10) {
			month = '0' + month;
		}
		if (nowdays < 10) {
			nowdays = '0' + nowdays;
		}
		startTime = year + "-" + month + "-01";
		// endTime = year + "-" + month + "-" + day;
		endTime = year + "-" + month + "-" + nowdays;
		$("#timeType").val("currentMonth");
		$("#time").hide();
		break;
	case "custom"://自定义
		$("#timeType").val("custom");
		$("#time").show();
		break;
	}
	$("#beginDate").val(startTime);
	$("#endDate").val(endTime);
}

//日期检查
function checkData(fromId) {
	var begin = $("#beginDate").val();
	var end = $("#endDate").val();

var begins=	parseISO8601(begin);
var ends=parseISO8601(end);


	if (begin == "" || begin == null) {
		$("#msgtr").show();
		$("#msg").html("开始日期不能为空");
		$("#msg").css('display', 'inline-block');
		$("#beginDate").focus();
		return;
	}
	if (end == "" || end == null) {
		$("#msgtr").show();
		$("#msg").html("结束日期不能为空");
		$("#msg").css('display', 'inline-block');
		$("#endDate").focus();
		return;
	}


	var betweendays = daysBetween(begins, ends);
	if (betweendays < 0) {
		$("#msgtr").show();
		$("#msg").html("结束日期不能小于开始日期");
		$("#msg").css('display', 'inline-block');
		return;
	}
	if (betweendays > 180) {
		$("#msgtr").show();
		$("#msg").html("相隔日期不能大于180天");
		$("#msg").css('display', 'inline-block');
		return;
	}
	var form = document.getElementById(fromId);
	form.submit();
}


function exportExcel(){
 	var begin = $("#beginDate").val();
	var end = $("#endDate").val();
	  if(begin != "" && begin != null&&end != "" && end != null){
	   var betweendays = daysBetween(begin,end);
	  	if(betweendays<0||betweendays>31){
	  	 $("#msg").show();
	 	 $("#msg").html("只能导出一个月之内的数据");
	  	return false;
	  	}
	  }
}





function selectDate(str, obj) {
    var now = new Date(); //获取当前时间
    var year = now.getFullYear(); //获取当前日期的年份部分
    //    if ($.browser.mozilla) { year = year + 1900; } //如果是火狐浏览器，则需要加上1900年
    //    if ($.browser.opera) { year = year + 1900; } //如果是opera浏览器，则需要加上1900年
    //    if ($.browser.safari) { year = year + 1900; } //如果是safari浏览器，则需要加上1900年
    //if (!$.browser.msie) { year = year + 1900;} //如果不是IE浏览器，则需要加上1900年
    var month = now.getMonth() + 1 +""; //获取当前日期的月份部分
    var day = now.getDate() +""; //获取当前日期的天数部分
    if(day.length==1){
    	day="0"+day;
    }
    //alert(year); //opera  safari msie
    //获取当前月份包含的天数
    var nowdays = new Date(year, month, 0).getDate();
    var lastMonth = month - 1+"";
    var lastMonthdays = new Date(year, lastMonth, 0).getDate() +"";
    if(month.length==1){
    	month="0"+month;
    }
    if(lastMonth.length==1){
    	lastMonth="0"+lastMonth;
    }
    if(lastMonthdays.length==1){
    	lastMonthdays="0"+lastMonthdays;
    }
    var startTime = "";
    var endTime = "";
    switch (str) {
        case "toDay":
        	startTime = year + "-" + month + "-" + day;
        	endTime = year + "-" + month + "-" + day;
            break;
        case "lastMonth":
            if (lastMonth == 0) { lastMonth = 12; year = year - 1; }
            startTime = year + "-" + lastMonth + "-01";
            endTime = year + "-" + lastMonth + "-" + lastMonthdays;
            break;
        case "currentMonth":
            startTime = year + "-" + month + "-01";
            //endTime = year + "-" + month + "-" + day;
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
    }
    //alert(endTime);
    //    if (str != "all") {
    $("#beginDate").val(startTime);
    $("#endDate").val(endTime);
    //}

    //$("#td_Date a").removeClass(); //清空所有a标签的css
    //$(obj).addClass("Fcurrent"); //为当前选中的a标签增加css
}

// 清空表单
function clearForm(myForm){
	$(':input','#'+myForm)  
	 .not(':button, :submit, :reset, :hidden')  
	 .val('')  
	 .removeAttr('checked')  
	 .removeAttr('selected');  
}








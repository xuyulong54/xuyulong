$(document).ready(function(){
	
	if (!isIE()){
		//如果是非IE浏览器，则调用此函数，为控件添加事件处理函数。
		addEventForPass();
	}
});
/**
 * 主要放密码控件
 */
function addEventForPass() {
    //获取对象
    var powerpass = document.getElementById("powerpass");
    //添加Password控件的回车事件，如果收到此事件，则触发OnPassEventReturn()函数
    addEvent(powerpass, "EventReturn", OnPassEventReturn);
    //添加Password控件的Tab事件，如果收到此事件，则触发OnPassEventTab()函数
    addEvent(powerpass, "EventTab", OnPassEventTab);

    //添加Password控件的密码强弱度事件，如果收到此事件，则触发OnEventDegree(arg1)函数
    addEvent(powerpass, "EventDegree", OnEventDegree);
}

function addEvent(obj, name, func) {
    obj.addEventListener(name, func, false);
}

function OnPassEventReturn() {
    //在收到Password控件上的回车事件时，将焦点放在id为login的标签上。
    //document.getElementById("randomCode").focus();
}

function OnPassEventTab() {
    //在收到Password控件上的Tab事件时，将焦点放在id为login的标签上。
    //document.getElementById("randomCode").focus();
}

 //处理非IE浏览器下控件的密码强弱度事件
function OnEventDegree(arg1) {
    var degree = "";
    if (arg1 == "W") {
        degree = "弱";
    } else if (arg1 == "M") {
        degree = "中";
    } else if (arg1 == "S") {
        degree = "强";
    }
    /* window.document.getElementById("errorMsg").innerHTML = "密码强度:" + degree; */
}
 function createOrder_check(){
		if(checkRadio()){
			ShowDiv('MyDiv','fade');//弹出掩藏层
			$("#info").submit();
		}
	}
function checkRadio(){
	var frp_check = false;
	$(":radio").each(function(i){
		if(this.checked){
			frp_check = true;
		}
	});
	if(!frp_check){
		alert('请选择支付渠道');
	}
	return frp_check;
}

//弹出隐藏层1
 function ShowDiv(show_div, bg_div) { 
     document.getElementById(show_div).style.display = 'block';
     document.getElementById(bg_div).style.display = 'block';
     var bgdiv = document.getElementById(bg_div);
     bgdiv.style.width = document.body.scrollWidth;
     // bgdiv.style.height = $(document).height();
     $("#" + bg_div).height($(document).height());
 };
 //关闭弹出层1
 function CloseDiv(show_div, bg_div) {
     document.getElementById(show_div).style.display = 'none';
     document.getElementById(bg_div).style.display = 'none';
 };
 function fun_iframeLoadSrc(id,src_str){
	 document.getElementById('id').src= src_str; // 这里指定要加载的页面
 }


//Cookie操作
function getCookie(name) {
   var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
	if(arr=document.cookie.match(reg)){
		var cookieValue = unescape(arr[2]);
		return cookieValue.replace(/(^")|("$)/g,"")
	}else{
		return null;
	}
}
function setCookie(name,value)
{
   var Days = 365;
   var exp  = new Date();    //new Date("December 31, 9998");
       exp.setTime(exp.getTime() + Days*24*60*60);
       document.cookie = name + "="+ escape (value) + ";expires=" + exp.toGMTString();
}

//选择最后一次使用的银行.
function selectLastBank(){
	var bank = getCookie('lastSelectedBank');
	
	if(bank!=null){
		$("#"+bank).attr("checked","checked");
		var bankId = $("input[name='frpCode']:checked").attr("id");
		$.get(webpath+"/page/payment/bankInfo/"+bankId+".html", function(data){ $("#bankInfo").html(data);});
	}
}
$(document).ready(function(){
	
	//选择最后一个选中的银行
	selectLastBank();
	
	$("input[name='frpCode']").click(function(){
		setCookie("lastSelectedBank",$("input[name='frpCode']:checked").attr("id"));
		var bankId = $("input[name='frpCode']:checked").attr("id");
		$("#bankInfo").empty();
		$.get(webpath+"/page/payment/bankInfo/"+bankId+".html", function(data){ $("#bankInfo").html(data);});
	});
	
	$("#frpPaySubmit").click(function() {
		createOrder_check();
	});
	
});



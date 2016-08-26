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


//弹出隐藏层2
 /*
 $(document).ready(function () {
     $('.forgetpwd').bind('click', function () {
         ShowDiv2('.popupBox');
     });
     $('.btnClost').bind('click', function () {
         CloseDiv2('.popupBox');
     });
     
     $('.close').bind('click', function () {
         CloseDiv2('.popupBox');
     });
     

     $('.test').bind('click', function () {
         CloseDiv2('.popupBox');

     });

     function ShowDiv2(show_div) {
         $('.popupMask').css('width', $(window).width() + 'px');
         $('.popupMask').css('height', $(window).height() + 'px');
         $(window).bind('resize', function () {
             $('.popupMask').css('width', $(window).width() + 'px');
             $('.popupMask').css('height', $(window).height() + 'px');
         });
         var l = ($(window).width() - $(show_div).width()) / 2;
         var t = ($(window).height() - $(show_div).height()) / 2;
         $(show_div).css('top', t + 'px');
         $(show_div).css('left', l + 'px');
         $(window).bind('resize', function () {
             var l = ($(window).width() - $(show_div).width()) / 2;
             var t = ($(window).height() - $(show_div).height()) / 2;
             $(show_div).css('top', t + 'px');
             $(show_div).css('left', l + 'px');
         });
         document.documentElement.style.overflow = 'hidden';
         $(show_div).show();
         $('.popupMask').show();
         $('#powerpass').hide();
     }
 });

 function CloseDiv2(show_div) {
     $(show_div).hide();
     $('.popupMask').hide();
     $('#powerpass').show();
     document.documentElement.style.overflow = 'auto';
 }

 */



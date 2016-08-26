function myAlert(str,w,h,confirmcall,cancelcall,confirmargument,cancelargument){
	var mask=$("<div class='alertMask'></div>").appendTo("body");
	var box=$("<div class='alertBox'></div>").appendTo('body');
	$('.alertMask').css('width',$(window).width()+'px');
	$('.alertMask').css('height',$(window).height()+'px');
	$(window).bind('resize',function(){
	$('.alertMask').css('width',$(window).width()+'px');
	$('.alertMask').css('height',$(window).height()+'px');
		});
	$('.alertBox').css('width',w+'px');
	$('.alertBox').css('height',h+'px');
	var boxhead=$("<h3 class='alertBoxheader'></h3>").appendTo(box);;
	var adaimg=$("<img class='alertImg' src='statics/themes/default/images/closebtnImg.png'/>").appendTo(boxhead);
	var pWrap=$("<div class='alertTextWrap'></div>").appendTo(box);
	var p=$("<p class='alertText'>"+str+"</p>").appendTo(pWrap);
	var butwrap=$("<div class='butWrap'></div>").appendTo(box);
	var but1=$("<input class='alertBtnYes alertBoxbutn' type='button' value='确定'/>").appendTo(butwrap);
	var but2=$("<input class='alertBtnNo alertBoxbutn' type='button' value='取消'/>").appendTo(butwrap);
	var l=($(window).width()-w)/2;
	var t=($(window).height()-h)/2;
	$('.alertBox').css('top',t+'px');
	$('.alertBox').css('left',l+'px');
	document.documentElement.style.overflow = 'hidden';
	$('.alertBtnNo').bind('click',function(){		
		$('.alertMask').hide();
		$('.alertBox').hide();
		cancelcall(cancelargument);
		document.documentElement.style.overflow = 'auto';
		});
	$('.alertImg').bind('click',function(){
		$('.alertMask').hide();
		$('.alertBox').hide();
		document.documentElement.style.overflow = 'auto';
		});
	$('.alertBtnYes').bind('click',function(){
		
		$('.alertMask').hide();
		$('.alertBox').hide();
	    confirmcall(confirmargument);
		document.documentElement.style.overflow = 'auto'; 
		});
	$(window).bind('resize',function(){
		var l=$(window).width()/2-w/2;
	    var t=$(window).height()/2-h/2;
	    $('.alertBox').css('top',t+'px');
	    $('.alertBox').css('left',l+'px');
		});

	}

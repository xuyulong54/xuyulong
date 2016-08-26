function customAlert(w,h,confirmcall,cancelcall,confirmargument,cancelargument){
	var mask=$("<div class='alertMask'></div>").appendTo("body");
	var box=$("<div class='alertBox1'></div>").appendTo('body');
	$('.alertMask').css('width',$(window).width()+'px');
	$('.alertMask').css('height',$(window).height()+'px');
	$(window).bind('resize',function(){
	$('.alertMask').css('width',$(window).width()+'px');
	$('.alertMask').css('height',$(window).height()+'px');
		});
	var boxhead=$("<h3 class='alertBoxheader'></h3>").appendTo(box);;
	var adaimg=$("<img class='alertImg' src='statics/themes/default/images/closebtnImg.png'/>").appendTo(boxhead);
	var pWrap=$("<div class='alertTextWrap'></div>").appendTo(box);
	var butwrap=$("<div class='butWrap'></div>").appendTo(box);
	var but1=$("<input class='alertBtnYes alertBoxbutn' type='button' value='确定'/>").appendTo(butwrap);
	var but2=$("<input class='alertBtnNo alertBoxbutn' type='button' value='取消'/>").appendTo(butwrap);
	var l=($(window).width()-w)/2;
	var t=($(window).height()-h)/2;
	$('.alertBox1').css('top',t+'px');
	$('.alertBox1').css('left',l+'px');
	document.documentElement.style.overflow = 'hidden'; 
	$(window).bind('resize',function(){
		var l=$(window).width()/2-w/2;
	    var t=$(window).height()/2-h/2;
	    $('.alertBox1').css('top',t+'px');
	    $('.alertBox1').css('left',l+'px');
		});
	$('.alertBox1').css('width',w+'px');
	$('.alertBox1').css('height',h+'px');
	$('.alertImg').bind('click',function(){
		$('.alertMask').hide();
		$('.alertBox1').hide();
		document.documentElement.style.overflow = 'auto';
		});
	$('.alertBtnYes').bind('click',function(){
		
		$('.alertMask').hide();
		$('.alertBox1').hide();
	    confirmcall(confirmargument);
		document.documentElement.style.overflow = 'auto'; 
		});
		$('.alertBtnNo').bind('click',function(){		
		$('.alertMask').hide();
		$('.alertBox1').hide();
		cancelcall(cancelargument);
		document.documentElement.style.overflow = 'auto';
		});
	}

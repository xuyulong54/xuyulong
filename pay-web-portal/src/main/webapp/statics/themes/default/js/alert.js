function myAlert(str,w,h,cancelcall,confirmcall,cancelparam,confirmparam){
	var mask=$("<div class='ada-mask'></div>").appendTo("body");
	var box=$("<div class='ada-box'></div>").appendTo('body');
	$('.ada-box').css('width',w+'px');
	$('.ada-box').css('height',h+'px');
	var adaimg=$("<img class='adaimg' src='statics/themes/default/images/ada-icon_03.gif'/>").appendTo(box);
	var p=$("<p class='ada-text'>"+str+"</p>").appendTo(box);
	var butwrap=$("<div class='butwrap'></div>").appendTo(box);
	var but1=$("<input class='ada-but1' type='button' value='确定'/>").appendTo(butwrap);
	var but2=$("<input class='ada-but2' type='button' value='取消'/>").appendTo(butwrap);
	var l=($(window).width()-w)/2;
	var t=($(window).height()-h)/2;
	$('.ada-box').css('top',t+'px');
	$('.ada-box').css('left',l+'px');
	document.documentElement.style.overflow = 'hidden';
	$('.ada-but2').bind('click',function(){
		
		$('.ada-mask').hide();
		$('.ada-box').hide();
		if(cancelcall!=null){
			cancelcall();
		}

		document.documentElement.style.overflow = 'auto';
		});
	$('.adaimg').bind('click',function(){
		$('.ada-mask').hide();
		$('.ada-box').hide();
		document.documentElement.style.overflow = 'auto';
		});
	$('.ada-but1').bind('click',function(){
		
		$('.ada-mask').hide();
		$('.ada-box').hide();
		confirmcall(confirmparam);
		document.documentElement.style.overflow = 'auto'; 
		});
	
	$(window).bind('resize',function(){
		var l=$(window).width()/2-180;
	    var t=$(window).height()/2-125;
	    $('.ada-box').css('top',t+'px');
	    $('.ada-box').css('left',l+'px');
		});

	 
	}

 
 function validate(){
	   var username = document.getElementById("username").value;
	     if(username=="" || username==null){
	    	 alert("用户名不能为空!");
	    	 if(username==""){
	    	 document.getElementById("username").focus();
	    	 }
	    	 return false;
	     }
	     return true;
}

// JavaScript Document
var num=0;

$(document).ready(function() {
	//如果银行数量少于12个，则隐藏按钮
	var lis=$(".bank>li").size();
	if(lis<=12){
		$("div[class='moreBank']").hide();
	}
	
	
    /*通栏导航*/
    $("#tag li").hover(function() { //鼠标进入显示
        if (!$(this).hasClass('current')) {
            bgImageUrl = $(this).next().css("backgroundImage");
            var boxWidth = $(".nav_item_box").outerWidth(true),
            boxHeight = $(this).find(".nav_item_box").outerHeight(true);
            $(".nav_transparent,.navFrame").width(boxWidth);
            $(".nav_transparent,.navFrame").height(boxHeight + 2);
            $(this).find(".nav_transparent,.nav_item_box,.navFrame").show();
            $(this).children("a").addClass("hover");
        }
    },
    function() { //鼠标离开隐藏
        if (!$(this).hasClass('current')) {
            $(this).find(".nav_transparent,.nav_item_box,.navFrame").hide();
            $(this).removeClass("hover");
        }
    });
    $("#tag li").click(function() {
		$(this).addClass("current").siblings().removeClass('current');
		var index = $(this).index("#tag li");
		$('.tagContext > li').eq(index).removeClass('divNone').siblings().addClass('divNone');
		$(this).next().addClass('li_none_back').siblings().removeClass('li_none_back');
		
    }); try {
        $("#tag li.current").next().addClass('li_none_back');
    } catch(e) {
        ///
    }
	//列表显示隐藏
	
	$('.more_li .tab,.more_li .more_icon').click(function(){
		if($(this).parent().hasClass('down')){
			$(this).parent().removeClass('down');
		}else{
			$(this).parent().addClass('down');
		}
		$(this).parent().siblings().removeClass('down');
	});
	
	//tab切换
	$(".moreBank").hover(function(){
			$(this).addClass("hover");
		},function(){
			$(this).removeClass("hover");
		}
	);
	$("div[class='moreBank']").click(function(){//更多银行
		num++;
		if(num%2!==0){
		  var liHeight=$(".bank>li").outerHeight(true);
			var lis=$(".bank>li").size();
			var cells=0;
			if(lis%4===0){
				  cells=lis/4;
				}else{
					   cells=(Math.floor(lis/4))+1;
					};
					
	  	$(".bank").animate({height:liHeight*cells},600,function(){
				  		$(".moreBank").children("em").html("&and;");
				});
		}else{
			  $(".bank").animate({height:"150"},600,function(){
				  $(".moreBank").children("em").html("&or;");
			  }); 
		}
	});
	$("#Bank>li").click(function(){
		$(":radio").next("label").removeClass("checked");
	 $(":radio:checked").next("label").addClass("checked");
	});
	$(".tab_list>span").click(function(){
		$(this).children(":radio").attr('checked','checked');
	});
	$(".name_list>li").click(function(){
		$(this).children(":radio").attr('checked','checked');
	});
	$("#Bank>li>label").hover(function(){//鼠标滑过标签效果
		  $(this).addClass("hover");
		},function(){$(this).removeClass("hover")})
	$("#tag>li:not('.current')").hover(function(){
		   $(this).addClass("hover");
		},function(){
				$(this).removeClass("hover");
			});
});
$(document).click(function(e){
	e.stopPropagation();
	var s1=(e.target.className).toString();		
	if(s1.substr(0,7)!='more_li'){  
		if($(e.target).parents('.more_li')[0]){ 
			
			var s2=($(e.target).parents('.more_li')[0].className).toString();
			if(s2.substr(0,7)!='more_li'){    
				$('.more_li').removeClass('down');			
			}
		}else{
			$('.more_li').removeClass('down'); 
		}
	}		
});
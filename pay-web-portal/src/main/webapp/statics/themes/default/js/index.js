$(document).ready(function() {
    $('#NavMenu1').hoverIntent(
		function() {
             $('.HF_Position1').addClass('open').slideDown(100);
		},
		function() {
		     $('.HF_Position1').removeClass('open').slideUp(200);
		}
	);

	$('#NavMenu2').hoverIntent(
		function() {
		     $('.HF_Position2').addClass('open').slideDown(100);
		},
		function() {
		    $('.HF_Position2').removeClass('open').slideUp(200);
		}
	);


	$('#NavMenu3').hoverIntent(
		function() {
		     $('.HF_Position3').addClass('open').slideDown(100);
		},
		function() {
		    $('.HF_Position3').removeClass('open').slideUp(200);
		}
	);

	$('#NavMenu4').hoverIntent(
		function() {
		    $('.HF_Position4').addClass('open').slideDown(100);
		},
		function() {
		    $('.HF_Position4').removeClass('open').slideUp(200);
		}
	);


	$('#NavMenu5').hoverIntent(
		function() {
            $('.HF_Position5').addClass('open').slideDown(100);
		},
		function() {
		    $('.HF_Position5').removeClass('open').slideUp(200);
		}
	);
	$('#NavMenu6').hoverIntent(
			function() {
	            $('.HF_Position6').addClass('open').slideDown(100);
			},
			function() {
			    $('.HF_Position6').removeClass('open').slideUp(200);
			}
		);



});

(function($) { $.fn.hoverIntent = function(f, g) { var cfg = { sensitivity: 7, interval: 100, timeout: 0 }; cfg = $.extend(cfg, g ? { over: f, out: g} : f); var cX, cY, pX, pY; var track = function(ev) { cX = ev.pageX; cY = ev.pageY; }; var compare = function(ev, ob) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); if ((Math.abs(pX - cX) + Math.abs(pY - cY)) < cfg.sensitivity) { $(ob).unbind("mousemove", track); ob.hoverIntent_s = 1; return cfg.over.apply(ob, [ev]); } else { pX = cX; pY = cY; ob.hoverIntent_t = setTimeout(function() { compare(ev, ob); }, cfg.interval); } }; var delay = function(ev, ob) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); ob.hoverIntent_s = 0; return cfg.out.apply(ob, [ev]); }; var handleHover = function(e) { var p = (e.type == "mouseover" ? e.fromElement : e.toElement) || e.relatedTarget; while (p && p != this) { try { p = p.parentNode; } catch (e) { p = this; } } if (p == this) { return false; } var ev = jQuery.extend({}, e); var ob = this; if (ob.hoverIntent_t) { ob.hoverIntent_t = clearTimeout(ob.hoverIntent_t); } if (e.type == "mouseover") { pX = ev.pageX; pY = ev.pageY; $(ob).bind("mousemove", track); if (ob.hoverIntent_s != 1) { ob.hoverIntent_t = setTimeout(function() { compare(ev, ob); }, cfg.interval); } } else { $(ob).unbind("mousemove", track); if (ob.hoverIntent_s == 1) { ob.hoverIntent_t = setTimeout(function() { delay(ev, ob); }, cfg.timeout); } } }; return this.mouseover(handleHover).mouseout(handleHover); }; })(jQuery);
//导航延迟触发

/*Tab 更换显示样式*/
function setTab(m, n) {
    var tli = document.getElementById("menu" + m).getElementsByTagName("li");
    var mli = document.getElementById("main" + m).getElementsByTagName("ul");
    for (i = 0; i < tli.length; i++) {
        tli[i].className = i == n ? "hover" : "";
        mli[i].style.display = i == n ? "block" : "none";
    }
}
/*Tab 更换显示样式 结束*/




/*----------最顶部菜单样式---------
sfHover = function() {
	var str1=document.getElementById("Menu_hover");
	if(str1!=null){
		str1.getElementsByTagName("li");
		 for (var i = 0; i < sfEls.length; i++) {
		        sfEls[i].onmouseover = function() {
		            this.className += " sfhover"
		        }
		        sfEls[i].onmouseout = function() {
		            this.className = this.className.replace(new RegExp(" sfhover\\b"), "")
		        }
		    }
	}
    
   
}
if (window.attachEvent) window.attachEvent("onload", sfHover);
*/
/*----------最顶部菜单样式  结束---------*/


/*----------登录框样式（用户名密码）---------*/
//sfHover = function() {
//    var sfEls = document.getElementById("input_content").getElementsByTagName("input");
//    for (var i = 0; i < sfEls.length; i++) {
//        sfEls[i].onmouseover = function() {
//            this.className += " sfhover"
//        }
//        sfEls[i].onmouseout = function() {
//            this.className = this.className.replace(new RegExp(" sfhover\\b"), "")
//        }
//    }
//}
//if (window.attachEvent) window.attachEvent("onload", sfHover);
/*----------登录框样式（用户名密码）  结束---------*/

/*----------登录框样式（验证码）---------*/
//sfHover = function() {
//    var sfEls = document.getElementById("code_input").getElementsByTagName("input");
//    for (var i = 0; i < sfEls.length; i++) {
//        sfEls[i].onmouseover = function() {
//            this.className += " sfhover"
//        }
//        sfEls[i].onmouseout = function() {
//            this.className = this.className.replace(new RegExp(" sfhover\\b"), "")
//        }
//    }
//}
//
//if (window.attachEvent) window.attachEvent("onload", sfHover);
/*----------登录框样式（验证码）  结束---------*/


        
        
        

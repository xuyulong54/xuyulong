   /*----------展开收回样式 ---------*/
 function showdiv(targetid) {
    var target = document.getElementById(targetid);
    if (target.style.display == "block") {
        document.getElementById('BtnZhankai').style.backgroundImage = "url('images/dev_btn.png')";
        target.style.display = "none";
    }
    else {
        target.style.display = "block";
        document.getElementById('BtnZhankai').style.backgroundImage = "url('images/back_btn.png')";
    }
}
/*----------展开收回样式 结束---------*/
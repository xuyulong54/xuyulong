/** 不要改动此文件 by laich ***/
var WIN_32_ACTIVEX_VERSION = 2004041000;                		// Windows系统下32位控件版本号，例如2.4.1.3版本号则为2004001003
var WIN_64_ACTIVEX_VERSION = 2004041000;                		// Windows系统下64位控件版本号，例如2.4.1.3版本号则为2004001003
var WIN_PLUGIN_VERSION = 2004041000;                    		// Windows系统下插件版本号，例如2.4.1.3版本号则为2004001003
var WIN_SETUP_PATH = "statics/cfca/setup/iSecurityGDMGC.exe";          		// Windows系统下安装程序下载路径
var WIN_32_CAB_PATH = "statics/cfca/setup/iSecurityGDMGC.ocx#version=2,4,41,0";
var WIN_64_CAB_PATH = "statics/cfca/setup/iSecurityGDMGC_x64.ocx#version=2,4,41,0";
var codeBaseFile = "";
var LocalObjVersion="";
var isInistall = false;

// 应用公钥
var _pk = "BgIAAACkAABSU0ExAAQAAAEAAQDf1OaPSVJyGjtZq3CQ/dcHKfm6Jzo1wlxo3nNIyWHMnSn/wY+hxpy3kGvEP+QROGlcZVz6oiwTyVoi6MQ7NtscU3fs2C/59A62Bb99ft73oGOXE7XvD1XpOFcHqMgXaPWimmNoFr26uHmBtOZ/RZRERJ7dRV+hlgKHhbkLcuDVjw==";

// 控件
var PassCtrlClsid = "clsid:6D65E832-2071-4499-A63A-954655D56444";
var EditCtrlClsid = "clsid:16E3E210-989E-4492-A9EE-DD2AB5B29356";
var UtilCtrlClsid = "clsid:30288CE1-78BB-4AAC-8227-29A34FCD6A26";
var CtlName = "POWERENTERGDMGC.PowerUtilityXGDMGCCtrl.1";

// 插件
var MIME = "application/x-vnd-sa-isecurity-gdmgc";
var PluginDescription = "SA-iSecurity Plug-in for GDMGC";

// 控件默认属性
function powerConfig(args) {
    var defaults = { 
       "width":268,
       "height":32,
       "maxLength":16,
       "minLength":6,
       "maskChar":"*",
       "backColor":"#FFFFFF",
       "textColor":"#0000FF",
       "borderColor":"#DDDDDD",
       "accepts":"[:graph:]+",
       "caption":"",
       "captionColor":"#87011f",
       "captionFont":"",
       "captionSize":0,
       "captionBold":"true",
       "lang":"zh_CN",
       "softKeyboard":"true"
    };
    for (var p in args)
       if (args[p] != null) defaults[p] = args[p];
    return defaults;
}

function writePluginObject(oid, clsid, cfg) {
    document.write('<object id="' + oid + '" type="' + clsid
       + '" width="' + cfg.width + '" height="' + cfg.height
       + '" style="width:' + cfg.width + 'px;height:' + cfg.height + 'px">');
    for (var name in cfg)
       document.write('<param name="' + name + '" value="' + cfg[name] + '">');
    document.write('</object>');
};

function writeObject(oid, clsid, cfg) {
    document.write('<object id="' + oid + '" codebase="'+codeBaseFile+'" classid="' + clsid     
           + '" width="' + cfg.width + '" height="' + cfg.height  + '">');
    for (var name in cfg)
       document.write('<param name="' + name + '" value="' + cfg[name] + '">');
    document.write('</object>');
};

function writeEditObject(oid, cfg) {
    if (!oid || typeof(oid) != "string") {
       alert("writePassObj Failed: oid are required!");
    } else {
    	setPEXSetupUrl(oid);
		if(isInistall)
		{
			if (isIE())
			{
				writeObject(oid, EditCtrlClsid, powerConfig(cfg));
			}
			else
			{
				writePluginObject(oid, MIME, powerConfig(cfg));
			}
		}
    }
};

function writePassObject(oid, cfg) {
    if (!oid || typeof(oid) != "string") {
       alert("writePassObj Failed: oid are required!");
    } else {
    	setPEXSetupUrl(oid);
		if(isInistall)
		{
			if (isIE())
			{
				writeObject(oid, PassCtrlClsid, powerConfig(cfg));
			}
			else
			{
				writePluginObject(oid, MIME, powerConfig(cfg));
			}
		}
    }
};

function writeUtilObject(oid, cfg) {
    if (!oid || typeof(oid) != "string") {
       alert("writePassObj Failed: oid are required!");
    } else {
       if (isIE())
       {
           if((navigator.platform == "Win64" || navigator.cpuClass == "x64"))
              codeBaseFile = WIN_64_CAB_PATH;
           else
              codeBaseFile = WIN_32_CAB_PATH;
           
           writeObject(oid, UtilCtrlClsid, powerConfig(cfg));
       }
       else
       {
           writePluginObject(oid, MIME, powerConfig(cfg));
       }
    }
};

//密码输入控件一致性检查。
//返回值为ture时，两个密码输入框输入内容一致，反之输入内容不一致。
function checkConsistency(id1, id2)
{
	try 
	{
		var powerobj1 = document.getElementById(id1);
		var powerobj2 = document.getElementById(id2);
		var nresult1 = powerobj1.verify();
		var nresult2 = powerobj2.verify();
		if(nresult1 < 0 || nresult2 < 0){
	       return false;
		}   
	   
		if(powerobj1.getMeasureValue() != powerobj2.getMeasureValue()){
			return false;
		}
	}
	catch(e)
	{
		return false;
	}
	return true;
}

/*
 * 获取密码控件密文
 * getPassInput(id, ts, spanId, massage)
 * id：控件id
 * ts：服务器时间戳
 * spanId：错误内容显示域
 * massage：错误提示种类描述
 */
function getPassInput(id, ts, spanId, massage)
{
    try
    {
       var powerobj = document.getElementById(id);
       powerobj.setTimestamp(ts);
       powerobj.publicKeyBlob(_pk); 
       var nresult = powerobj.verify();
       if(nresult < 0)
       {
           var error;
           if(nresult == -1)
           {
              error = "内容不能为空";
           }
           else if(nresult == -2)
           {
              error = "输入长度不足";
           }
           else if(nresult == -3)
           {
              error = "输入内容不合法";
           }
           else
           {
              error = powerobj.lastError(); 
           }
           PEGetElement(spanId).innerHTML = massage +error;
           return null;
       }
       
       value = powerobj.getValue();
       if(value=="")
       {
         PEGetElement(spanId).innerHTML= massage+powerobj.lastError(); 
         return null;
       }
       else
       {
           return value;
       }
    }
    catch(e)
    {
       PEGetElement(spanId).innerHTML= "控件尚未安装，请下载并安装控件！:"+e;
    }
    return null;
}

/*
 * 获取编辑控件密文
 * getEditInput(id, ts, spanId, massage)
 * id：控件id
 * ts：服务器时间戳
 * spanId：错误内容显示域
 * massage：错误提示种类描述
 */
function getEditInput(id, ts, spanId, massage) 
{
    try 
    {
       var powerobj = document.getElementById(id);
       powerobj.setTimestamp(ts);
       powerobj.publicKeyBlob(_pk);
       var nresult = powerobj.verify();
       if(nresult < 0)
       {          
           var error;
           if(nresult == -1)
           {
              error = "内容不能为空";
           }
           else if(nresult == -2)
           {
              error = "输入长度不足";
           }
           else if(nresult == -3)
           {
              error = "输入内容不合法";
           }
           else
           {
              error = powerobj.lastError(); 
           }
           PEGetElement(spanId).innerHTML = massage +error;
           return null;
       }   
              
       value = powerobj.getValue();
       if(value=="")
       {
           PEGetElement(spanId).innerHTML= massage+powerobj.lastError(); 
           return null;
       }
       else
       {
           return value;
       }
    }
    catch(e)
    {
       PEGetElement(spanId).innerHTML= "控件尚未安装，请下载并安装控件！";
    }
    return null;
}

/*
 * 获取工具控件密文
 * getMFMInput(id, ts, spanId, massage)
 * id：控件id
 * ts：服务器时间戳
 * spanId：错误内容显示域
 * massage：错误提示种类描述
 */
function getMFMInput(id, ts, spanId, massage) 
{
    try 
    {
       var powerobj = document.getElementById(id); 
       powerobj.setTimestamp(ts);
       powerobj.publicKeyBlob(_pk);
       value = powerobj.getMFM();
       if(value=="")
       {
           PEGetElement(spanId).innerHTML= massage + powerobj.lastError(); 
           return null;
       }
       else
       {
           return value;
       }
    }
    catch(e)
    {
       PEGetElement(spanId).innerHTML= massage + e.message;
    }
    return null;
}

function PEGetElement(id)
{
    return  window.document.getElementById(id);
}

function setPEXSetupUrl(oid)
{
    var DownloadPath = getDownLoadPath();
    var ObjVersion = getObjVersion();
    
    if(isRegisterediSecurity()==false){
       if((navigator.platform == "Win32") || 
          (navigator.platform == "Windows") || 
          (navigator.platform == "Win64")){
           document.write('<a href="'+DownloadPath+'" class="download_install">点击此处下载控件</a>');
       }else{
           document.write('<a href="#" class="download_install">暂不支持此系统</a>');
       }
       isInistall = false;
    }else{
       var LocalObjVersion = getLocalObjVersion();
       if(LocalObjVersion < ObjVersion){
           document.write('<a href="'+DownloadPath+'" class="download_install">点击此处更新控件</a>');
           isInistall = false;
       }else{
           isInistall = true;
       }
    }
}

function isRegisterediSecurity(){
	try{
		if (isIE()){
			new ActiveXObject(CtlName);
		}else{
			var powerEnterPlugin = navigator.plugins[PluginDescription];
			if(powerEnterPlugin == null)
				return false;
		}
	}catch(e){
		return false;   
	}
	return true;
}

function getDownLoadPath()
{
    return WIN_SETUP_PATH; 
}

function getObjVersion()
{
    if((navigator.platform == "Win64" || navigator.cpuClass == "x64")){
       if (isIE())
           return WIN_64_ACTIVEX_VERSION;         // Windows系统下64位控件版本
       else
           return WIN_PLUGIN_VERSION;             // Windows系统下插件版本
    }else if((navigator.platform == "Win32") || (navigator.platform == "Windows")){
       if (isIE())
           return WIN_32_ACTIVEX_VERSION;         // Windows系统下32位控件版本
       else
           return WIN_PLUGIN_VERSION;             // Windows系统下插件版本
    }
    return "";
}

writeUtilObject("versionObj",{"width":1,"height":1});

function getLocalObjVersion()
{
    if(LocalObjVersion == "")
    {
       LocalObjVersion = PEGetElement("versionObj").getVersion();
    }
    return LocalObjVersion;
}

function isIE()
{
	if (navigator.appName == 'Microsoft Internet Explorer' || navigator.userAgent.indexOf("Trident")>0)
		return true;
	else
		return false;
}

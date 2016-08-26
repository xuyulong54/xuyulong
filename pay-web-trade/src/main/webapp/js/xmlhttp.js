function send_request(callback, urladdress, isReturnData){      
        var xmlhttp = getXMLHttpRequest();
        xmlhttp.onreadystatechange = function(){
            	if (xmlhttp.readyState == 4) {//readystate 为4即数据传输结束
 				    try{
				    	if(xmlhttp.status == 200){
							if(isReturnData && isReturnData==true){
								callback(xmlhttp.responseText);
							}
						}else{
							callback("抱歉，没找到此页面:"+ urladdress +"");
						}
			        } catch(e){
			        	callback("抱歉，发送请求失败，请重试 " + e);
			        }
			   }
        }
        xmlhttp.open("GET", urladdress, true);
        xmlhttp.send(null);
}

function getXMLHttpRequest() {
        var xmlhttp;
		if (window.XMLHttpRequest) {
			try {
				xmlhttp = new XMLHttpRequest();
				xmlhttp.overrideMimeType("text/html;charset=UTF-8");//设定以UTF-8编码识别数据
			} catch (e) {}
		} else if (window.ActiveXObject) {
			try {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			} catch (e) {
				try {
					xmlhttp = new ActiveXObject("Msxml2.XMLHttp");
				} catch (e) {
					try {
						xmlhttp = new ActiveXObject("Msxml3.XMLHttp");
					} catch (e) {}
				}
			}
		}
        return xmlhttp;
}

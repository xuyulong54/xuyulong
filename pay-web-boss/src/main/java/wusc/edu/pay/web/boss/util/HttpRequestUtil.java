package wusc.edu.pay.web.boss.util;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import wusc.edu.pay.common.utils.httpclient.SimpleHttpParam;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpResult;
import wusc.edu.pay.common.utils.httpclient.SimpleHttpUtils;

/**
 * 
 * @时间 2013-11-14
 * @author Peter
 */
public class HttpRequestUtil {
	/**
	 * 该方法用来发送https/http请求
	 * @param URL  请求URL
	 * @return  请求返回的数据
	 */
	private static final Log log = LogFactory.getLog(HttpRequestUtil.class);
    public static String sendRequest(String Url) throws IOException {
    	if(Url != null){
    		SimpleHttpParam httpParam = new SimpleHttpParam(Url);
    		httpParam.setConnectTimeout(5000);
    		httpParam.setMethod("GET");
    		SimpleHttpResult response=SimpleHttpUtils.httpRequest(httpParam);
    		if (response != null && response.getStatusCode() == 200) {
    			return response.getContent();
    		}
    	}
    	return null;
    }
    
    /**
     * 用HTTPS POST请求发送xml
     * @param url 地址
     * @param xml xml报文
     * @param charSet 编码，null则默认为UTF-8
     * @return
     */
    public static String sendHttpsPostXml(String url,String xml,String charSet){
    	if(url != null){
    		SimpleHttpParam httpParam = new SimpleHttpParam(url);
    		httpParam.setConnectTimeout(5000);
    		httpParam.setMethod("POST");
    		httpParam.setPostData(xml);
    		if(charSet!=null){
    			httpParam.setCharSet(charSet);
    		}
    		SimpleHttpResult response=SimpleHttpUtils.httpRequest(httpParam);
    		if (response != null && response.getStatusCode() == 200) {
    			return response.getContent();
    		}
    	}
    	return null;
    }
    

}

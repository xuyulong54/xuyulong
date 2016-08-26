package wusc.edu.pay.core.bank.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * @时间 2013-11-14
 * @author Peter
 */
public class HttpsRequest {
	/**
	 * 该方法用来发送https请求
	 * @param URL  请求URL
	 * @return  请求返回的数据
	 */
	 private static final Log log = LogFactory.getLog(HttpsRequest.class);
    public static String sendHttpsRequest(String Url) throws IOException {
    	InputStream in = getInputStreamByHttp(Url);
    	 byte b[] = new byte[1024];   
         int len = 0;   
         int temp=0;          //所有读取的内容都使用temp接收   
         while((temp=in.read())!=-1){    //当没有读取完时，继续读取   
             b[len]=(byte)temp;   
             len++;   
         }   
         in.close();   
         log.info("sendHttpsRequest file end");
         return new String(b,0,len,"GBK");
    }
    
    /**
     * 通过HTTPS方式下载对账文件
     * @param filePath 下载文件存放路径
     * @param Url  请求的Url
     * @return
     * @throws IOException
     */
    public static boolean sendHttprequestForFileDownd(String filePath , String Url) throws IOException{
    	boolean flag = false;
    	InputStream in = getInputStreamByHttp(Url);
    	if(in != null){
    		BufferedInputStream inBuff = null;
            BufferedOutputStream outBuff = null;
                // 新建文件输入流并对它进行缓冲
                inBuff = new BufferedInputStream(in);
                // 新建文件输出流并对它进行缓冲
                outBuff = new BufferedOutputStream(new FileOutputStream(filePath));
                // 缓冲数组
                byte[] b = new byte[1024];
                int len;
                while ((len = inBuff.read(b)) != -1) {
                    outBuff.write(b, 0, len);
                }
                // 刷新此缓冲的输出流
                outBuff.flush();
                flag = true;
                outBuff.close();
                inBuff.close();
                in.close();
    	}
    	return flag;
    }
    
    
    @SuppressWarnings("finally")
	private static InputStream getInputStreamByHttp(String Url){
    	 URL url ;
    	 InputStream in = null;
         try {
             url = new URL(Url);
            log.info("sendHttpsRequest begin : " + Url);
             HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
             con.setRequestMethod("POST");//设置请求方法
             con.setConnectTimeout(3000);//设置请求超时时间，以毫秒为单位
             X509TrustManager xtm = new X509TrustManager() {
                 @Override
                 public X509Certificate[] getAcceptedIssuers() {
                     return null;
                 }
                 
                 @Override
                 public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                         throws CertificateException {
                 }
                 
                 @Override
                 public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                         throws CertificateException {
                 }
             };
             TrustManager[] tm = { xtm };
             SSLContext ctx = SSLContext.getInstance("TLS");
             ctx.init(null, tm, null);
             
             con.setSSLSocketFactory(ctx.getSocketFactory());
             con.setHostnameVerifier(new HostnameVerifier() {
                 @Override //验证主机名和服务器验证方案的匹配是可接受的。
                 public boolean verify(String arg0, SSLSession arg1) {
                     return true;
                 }
             });
             log.info("sendHttpsRequest end");
             in = con.getInputStream();
         }catch(Exception e){
        	 log.error("连接通讯异常", e);
         }finally{
             return in;
         }
    }
}

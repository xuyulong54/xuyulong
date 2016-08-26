package wusc.edu.pay.core.banklink.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;


/**
 * Socket 通讯
 * @author： Peter
 * @ClassName: SocketUtils.java
 * @Date： 2014-12-23 下午4:34:52 
 * @version：  V1.0
 */
public class SocketUtils {
	
	/**
	 * 通过socket通讯发送消息，并接受返回信息。
	 * 短连接，通讯一次后即关闭连接
	 * @param ip
	 * @param port
	 * @param msg
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public static String sendMsg(String ip , int port , String msg) throws UnknownHostException, IOException{
		
		String returnStr = "";
		Socket socekt = new Socket(ip,port);
		socekt.setSoTimeout(1000*60);//5分钟
		
		PrintWriter out = new PrintWriter(socekt.getOutputStream(),true);
		out.println(msg);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(socekt.getInputStream()));
		returnStr = in.readLine();
		
		out.close();
		
		in.close();
		
		socekt.close();
		
		return returnStr;
	}
	
}

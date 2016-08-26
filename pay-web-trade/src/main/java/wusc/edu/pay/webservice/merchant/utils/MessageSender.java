package wusc.edu.pay.webservice.merchant.utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * 发送短信
 * 
 * @author Peter
 * @date 2014-01-04
 * @version 1.0
 */
public class MessageSender {
	public static final String SendMessageID;
	public static final String SendMessageName;
	public static final String SendMessagePsw;
	// public static final String SendMessageUrl = "http://121.12.175.198:8180/service.asmx/SendMessage";

	private Logger logger = Logger.getLogger(MessageSender.class);

	private static String SendMessageUrl;
	static {
		SendMessageID = Context.SYSTEM_CONFIG.get("SendMessageID");
		SendMessageName = Context.SYSTEM_CONFIG.get("SendMessageName");
		SendMessagePsw = Context.SYSTEM_CONFIG.get("SendMessagePsw");
		SendMessageUrl = Context.SYSTEM_CONFIG.get("SendMessageUrl");
	}

	/**
	 * 发送短信
	 * 
	 * @param phone
	 *            手机号
	 * @param msgContent
	 *            发送内容
	 * @return
	 */
	public Map<String, Object> messageSender(String phone, String msgContent) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		try {
			String param = "?ID=" + SendMessageID + "&Name=" + SendMessageName + "&Psw=" + SendMessagePsw + "&Phone=" + phone + "&Message=" + msgContent + "&Timestamp=0";
			String msgUrl = SendMessageUrl + param;
			URL getUrl = new URL(msgUrl);
			HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
			connection.connect();
			paramMap = getMessageSenderReturn(connection.getInputStream());
			connection.disconnect();
		} catch (Exception e) {
			logger.error(e);
		}
		return paramMap;
	}

	/**
	 * 短信发送结果
	 * 
	 * @param is
	 * @return
	 */
	public Map<String, Object> getMessageSenderReturn(InputStream is) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		/*
		 * try { DocumentBuilderFactory factory = DocumentBuilderFactory .newInstance(); DocumentBuilder builder = factory.newDocumentBuilder(); Document document = builder.parse(is); NodeList
		 * nodeListFailPhone = document .getElementsByTagName("FailPhone"); Element elementFailPhone = (Element) nodeListFailPhone.item(0); paramMap.put(elementFailPhone.getNodeName(),
		 * elementFailPhone.getTextContent());
		 * 
		 * NodeList nodeListId = document.getElementsByTagName("Id"); Element elementId = (Element) nodeListId.item(0); paramMap.put(elementId.getNodeName(), elementId.getTextContent());
		 * 
		 * NodeList nodeListIdState = document.getElementsByTagName("State"); Element elementIdState = (Element) nodeListIdState.item(0); paramMap.put(elementIdState.getNodeName(),
		 * elementIdState.getTextContent()); } catch (Exception e) { e.printStackTrace(); }
		 */
		return paramMap;
	}

	public static void main(String[] args) {
	}
}

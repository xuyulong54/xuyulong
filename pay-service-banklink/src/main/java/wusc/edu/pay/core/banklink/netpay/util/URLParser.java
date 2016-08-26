package wusc.edu.pay.core.banklink.netpay.util;

import java.util.HashMap;
import java.util.Map;

/**
 * URL解析器
 * 
 * @author Administrator
 * 
 */
public class URLParser {
	/**
	 * 解析出url恳求的路径,包含页面
	 * 
	 * @param strURL
	 *            Url地址
	 * @return 请求Url的服务地址
	 */
	public static String UrlRequestService(String strURL) {
		String strPage = null;
		String[] arrSplit = null;

		// strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 0) {
			if (arrSplit.length > 1) {
				if (arrSplit[0] != null) {
					strPage = arrSplit[0];
				}
			}
		}

		return strPage;
	}

	/**
	 * 解析请求的参数字符串的键值对 如 "index.jsp?Action=del&id=123",解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            Url地址
	 * @return 请求Url的参数Map
	 */
	public static Map<String, Object> UrlRequestParam(String URL) {
		Map<String, Object> mapRequest = new HashMap<String, Object>();

		String[] arrSplit = null;

		String strUrlParam = TruncateUrlPage(URL);
		if (strUrlParam == null) {
			return mapRequest;
		}
		// 每个键值为一组
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit) {
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1) {
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

			} else {
				if (arrSplitEqual[0] != "") {
					// 只有参数没有值,不参加
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}

	/**
	 * 获取请求的参数字符串
	 * 
	 * @param strURL
	 *            Url地址
	 * @return 请求的参数字符串
	 */
	private static String TruncateUrlPage(String strURL) {
		String strAllParam = null;
		String[] arrSplit = null;

		// strURL = strURL.trim().toLowerCase();

		arrSplit = strURL.split("[?]");
		if (strURL.length() > 1) {
			if (arrSplit.length > 1) {
				if (arrSplit[1] != null) {
					strAllParam = arrSplit[1];
				}
			}
		}
		return strAllParam;
	}

}
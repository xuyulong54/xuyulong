package wusc.edu.pay.core.banklink.netpay.util;

import java.io.StringReader;
import java.util.LinkedHashMap;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * xml辅助类
 * 
 * @author Administrator
 * 
 */
public class XmlUtils {

	/**
	 * 获取Jdom解析的Document
	 */
	public static Document getDocument(String path) {
		try {
			SAXBuilder builder = new SAXBuilder();
			return builder.build(new StringReader(path));
		} catch (Exception e) {
			throw new RuntimeException("银行XML报文异常，请与银行联系确认", e);
		}
	}

	/**
	 * 解析xml文件
	 * 
	 * <pre>
	 * 解析xml文件
	 * element是待解析文件的元素，并解析该元素内部的所有子元素
	 * 将解析结果存入到parseSourceMap中
	 * </pre>
	 */
	@SuppressWarnings("unchecked")
	public static void parseXml(LinkedHashMap<String, String> parseSourceMap, Element element) {
		List<Element> elementList = element.getChildren();
		if (elementList != null && elementList.size() > 0) {
			for (Element e : elementList) {
				parseXml(parseSourceMap, e);
				String name = e.getName();
				String value = e.getTextTrim();
				if (name != null) {
					parseSourceMap.put(name, value);
				}
			}
		}
	}

	/**
	 * 替换字符串中的XML转义字符
	 */
	public static String xmlEscape(String strData) {
		if (strData == null) {
			return "";
		}
		strData = replaceString(strData, "&", "&amp;");
		strData = replaceString(strData, "<", "&lt;");
		strData = replaceString(strData, ">", "&gt;");
		strData = replaceString(strData, "'", "&apos;");
		strData = replaceString(strData, "\"", "&quot;");
		return strData;
	}

	/**
	 * 替换一个字符串中的某些指定字符
	 * 
	 * @param strData
	 *            String 原始字符串
	 * @param regex
	 *            String 要替换的字符串
	 * @param replacement
	 *            String 替代字符串
	 * @return String 替换后的字符串
	 */
	public static String replaceString(String strData, String regex, String replacement) {
		if (strData == null) {
			return null;
		}
		int index;
		index = strData.indexOf(regex);
		String strNew = "";
		if (index >= 0) {
			while (index >= 0) {
				strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			strNew += strData;
			return strNew;
		}
		return strData;
	}
}

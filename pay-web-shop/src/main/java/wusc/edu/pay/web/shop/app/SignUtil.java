package wusc.edu.pay.web.shop.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import wusc.edu.pay.common.config.PublicConfig;


public class SignUtil {

	public String sign(Map<String, Object> paramMap) throws UnsupportedEncodingException {

		// 按参数名称进行排序
		Properties properties = new Properties();
		for (Iterator<String> iter = paramMap.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String value = paramMap.get(name) == null ? "" : paramMap.get(name).toString();
			if (StringUtils.equalsIgnoreCase("hmac", name)) {
				continue;
			}
			properties.setProperty(name, StringUtils.trim(value));
		}
		List keys = new ArrayList(properties.keySet());
		Collections.sort(keys);
		
		
		StringBuffer hmacContent = new StringBuffer(""); // 签名内容
		StringBuffer html = new StringBuffer(""); // 表单内容
		String merchantKey = ""; // 商户秘钥
		String actionName = ""; // action名称
		
		for (int i = 0; i < keys.size(); i++) {
			String keyName = (String) keys.get(i);
			String value = properties.getProperty(keyName);
			System.out.println("==>" + keyName + "=" + value);

			if (StringUtils.isBlank(keyName) || StringUtils.isBlank(value) || StringUtils.equalsIgnoreCase("hmac", keyName)) {
				continue;
			} 
			if (StringUtils.equalsIgnoreCase("actionName", keyName)) {
				actionName = value; // 获取action地址，不参与签名
				continue;
			}
			if (StringUtils.equalsIgnoreCase("merchantKey", keyName)) {
				merchantKey = value; // 获取商户秘钥，不参与签名
				continue;
			}
			
			if("".equals(hmacContent.toString())){
				hmacContent.append(keyName).append("=").append(value);
			}else{
				// 第二个参数（有&开头）
				hmacContent.append("&").append(keyName).append("=").append(value);
			}
			
			// 拼装表单内容，并对表单中的值都进行URL编码
			html.append("<input type='hidden' name='").append(keyName).append("' value='").append(URLEncoder.encode(value, "utf-8")).append("'></br>");
		}
		
		// 表单头内容
		StringBuffer htmlFormHead = new StringBuffer("<form name='toForm' action='").append(PublicConfig.WEB_TRADE_URL).append(actionName).append(".action").append("' method='POST'>");

		// 签名源数据
		String hmacData = hmacContent.toString();
		System.out.println("==>hmacData:" + hmacData);
		
		// 签名值
		String hmac = DigestUtils.md5Hex(hmacData + merchantKey).toUpperCase();
		System.out.println("==>hmac:" + hmac);
		html.append("<input type='hidden' name='hmac' value='").append(hmac).append("'></br>");
		
		// 表单尾部内容
		html.append("</form><script>document.forms['toForm'].submit();</script>");//
		
		String formContent = htmlFormHead.toString() + html.toString();
		System.out.println("==>formContent:" + formContent);

		return formContent;
	}

}

package wusc.edu.pay.api.merchant;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import wusc.edu.pay.api.merchant.utils.Context;


public class RegistSign {
	
	public String sign(Map paramMap){
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		String merchantKey = "";
		Set keys = paramMap.keySet();
		for (Object key : keys) {
			try {
				parameters.put(key.toString(), new String((((String[])paramMap.get(key.toString()))[0]).getBytes("ISO-8859-1"),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		String actionUrl ="";
		Properties properties = new Properties();
			for (Iterator<String> iter = parameters.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String value = parameters.get(name).toString();
//				if (StringUtils.isBlank(name) || StringUtils.isBlank(value) || StringUtils.equalsIgnoreCase("hmac", name)) {
//					continue;
				if(StringUtils.equalsIgnoreCase("hmac", name)){
					continue;
				}else if(StringUtils.equalsIgnoreCase("actionName", name)){
					//获取action地址
					actionUrl = StringUtils.trim(value);
				}else{
					properties.setProperty(name, StringUtils.trim(value));
				}
			}

			StringBuffer content = new StringBuffer("");
			StringBuffer html = new StringBuffer("<form name='toSplit' action='").append(Context.WEB_TRADE_URL).append(actionUrl).append(".action").append("' method='POST'>");
			
			List<String> keyList = new ArrayList(properties.keySet());
			Collections.sort(keyList);
			for (int i = 0; i < keyList.size(); i++) {
				String tempKey = (String) keyList.get(i);
				String value = properties.getProperty(tempKey);
				if("merchantKey".equals(tempKey)){
					merchantKey = value;
				}else{
					if("".equals(content.toString())){
						content.append(tempKey).append("=").append(value);
					}else{
						content.append("&").append(tempKey).append("=").append(value);
					}
					
					html.append("<input type='hidden' name='").append(tempKey).append("' value='").append(value).append("'></br>");
				}
			}
			
			System.out.println("客户端签名数据：" + content.toString() + merchantKey);
			html.append("<input type='hidden' name='hmac' value='").append(DigestUtils.md5Hex(content.toString() + merchantKey).toUpperCase()).append("'></br>");
			html.append("</form><script>document.forms['toSplit'].submit();</script>");// 
		
		return html.toString();
	}

}

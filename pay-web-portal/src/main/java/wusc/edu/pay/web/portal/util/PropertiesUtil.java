package wusc.edu.pay.web.portal.util;

import java.io.IOException;
import java.util.Properties;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class PropertiesUtil {
	private static final Log log = LogFactory.getLog(PropertiesUtil.class);
	public static Properties getPropertiesValue(String fileName){
		Properties properties = new Properties();
		try {
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			log.error("读取配置文件" + fileName + "异常", e);
			return null;
		}
		return properties;
	}
	
	public static String newGUID() 
	{ 
	UUID uuid = UUID.randomUUID(); 
	return uuid.toString().replace("-", ""); 
	} 
	
	/*public static void main(String[] args) {
		Properties properties = getPropertiesValue("mail.properties");
		System.out.println(properties.get("mail.host"));
		System.out.println(newGUID());
		System.out.println(newGUID().length());
	}*/
}

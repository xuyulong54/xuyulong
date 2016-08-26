package wusc.edu.pay.webservice.merchant.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.webservice.merchant.exception.WebTradeBizException;


/**
 * 
 * @author Administrator
 * 
 */
public class BaseAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 8133815759396076721L;
	private static final Log logger = LogFactory.getLog(BaseAction.class);

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	/**
	 * 根据商户key及源字符串获取签名数据
	 * 
	 * @param merchantNo
	 * @param signStr
	 * @return
	 * @throws WebTradeBizException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String signData(String merchantNo, Map<String, Object> map) throws WebTradeBizException {

		MerchantOnline merchantOnline = null;
		if (!StringUtil.isBlank(merchantNo)) {
			merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);
		}
		if (merchantOnline == null) {
			throw WebTradeBizException.MERCHANT_IS_NOT_EXIST;
		}

		if (!merchantOnline.getStatus().equals(UserStatusEnum.ACTIVE.getValue())) {
			throw WebTradeBizException.PARENT_MERCHANT_IS_NOT_EXIST;
		}

		Properties properties = new Properties();
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String value = map.get(name) == null ? "" : map.get(name).toString();
			// if (StringUtils.isBlank(name) || StringUtils.isBlank(value) ||
			// StringUtils.equalsIgnoreCase("hmac", name)) {
			// continue;
			// }
			if (StringUtils.equalsIgnoreCase("hmac", name)) {
				continue;
			}
			properties.setProperty(name, StringUtils.trim(value));
		}

		StringBuffer content = new StringBuffer();
		List keys = new ArrayList(properties.keySet());
		Collections.sort(keys);
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
			String value = properties.getProperty(key);
			content.append((i == 0 ? "" : "&") + key + "=" + value);
		}

		logger.info("签名数据：" + content.toString());
		return DigestUtils.md5Hex(content.toString() + merchantOnline.getMerchantKey()).toUpperCase();
	}
}

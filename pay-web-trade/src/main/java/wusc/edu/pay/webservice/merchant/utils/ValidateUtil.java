/**
 * wusc.edu.pay.web.portal.util.ValidateUtil.java
 */
package wusc.edu.pay.webservice.merchant.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.string.StrUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserVerificationCode;
import wusc.edu.pay.facade.user.service.UserVerificationCodeFacade;


/**
 * <ul>
 * <li>Title:验证工具类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
public class ValidateUtil {
	
	private static final Log logger = LogFactory.getLog(ValidateUtil.class);
	
	@Autowired
	private UserVerificationCodeFacade userVerificationCodeFacade;

	/**
	 * 验证工具类
	 * 
	 * @param property
	 * @param propertyMsg
	 * @param isRequire
	 * @param minLength
	 * @param maxLength
	 * @return
	 */
	public static Map<String, String> lengthValidateMap(String property, String propertyMsg, boolean isRequire, int minLength, int maxLength) {
		Map<String, String> msgMap = new HashMap<String, String>();
		int propertyLenght = StrUtil.strLengthCn(property);
		if (isRequire && propertyLenght == 0) {
			msgMap.put(propertyMsg, "不能为空"); // 校验不能为空
		} else if (isRequire && minLength != 0 && propertyLenght < minLength) {
			msgMap.put(propertyMsg, "不能少于" + minLength + "个字符");// 必填情况下校验最少长度
		} else if (maxLength != 0 && propertyLenght > maxLength) {
			msgMap.put(propertyMsg, "不能多于" + maxLength + "个字符"); // 校验最大长度
		}
		return msgMap;
	}

	/**
	 * 短信验证码 验证
	 * 
	 * @param phoneCode
	 *        用户输入的验证码
	 * @param UserVerificationCode
	 *        用户验证码信息
	 * @return
	 */
	public static boolean isValidatePhoneCode(String phoneCode, UserVerificationCode verificationCode) {
		if (ValidateUtils.isEmpty(verificationCode)) {
			return false;
		}
		if (ValidateUtils.isEmpty(phoneCode)) {
			return false;
		}
		if (!phoneCode.equals(verificationCode.getVerificationCode())) {
			return false;
		}
		return true;
	}

	/**
	 * 密码格式校验
	 * 
	 * @param pwd
	 * @return
	 */
	public static String isValidatePwd(String pwd) {
		String msg = "密码应为8-20位数字,字母和特殊符号组合";
		if (ValidateUtils.isEmpty(pwd)) {
			return msg;
		}
		if (pwd.length() < 8 || pwd.length() > 20) {
			return msg;
		}
		// TODO:判断是字母和特殊符号组合
		return null;
	}

}

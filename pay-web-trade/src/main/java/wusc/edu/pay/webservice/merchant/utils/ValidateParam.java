package wusc.edu.pay.webservice.merchant.utils;

import org.apache.commons.lang3.StringUtils;

import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.webservice.merchant.exception.WebTradeBizException;


/**
 * 
 * @描述: 验证接口参数是否为空且长度是否正确.
 * @作者: Along.shen .
 * @创建时间:2014-11-19,下午2:34:44.
 * @版本:
 */
public class ValidateParam {

	/**
	 * 验证字符串 paramName <br/>
	 * 参数名 例如：p1_MerchantNo <br/>
	 * paramValue <br/>
	 * 参数值 <br/>
	 * paramMaxSize <br/>
	 * 参数值最大长度 allowNull <br/>
	 * 是否允许为空：true表示是可以为空 false 不能为空
	 */
	public static WebTradeBizException validateString(WebTradeBizException webTradeBizException, String paramName, String paramValue, int paramMaxSize, boolean allowNull) throws WebTradeBizException {
		if (webTradeBizException != null) {
			return webTradeBizException;
		}
		if (StringUtils.isBlank(paramValue) && !allowNull) {
			webTradeBizException = WebTradeBizException.GF_PARAMS_EXCEPTION;
			String msg = "传递参数有误：" + "[" + paramName + "为空]";
			webTradeBizException.setMsg(msg);
		} else if (paramValue.length() > paramMaxSize) {
			webTradeBizException = WebTradeBizException.GF_PARAMS_EXCEPTION;
			String msg = "传递参数有误：" + "[" + paramName + ":超出最大长度:" + paramMaxSize + "]";
			webTradeBizException.setMsg(msg);
		}
		return webTradeBizException;
	}

	/**
	 * 验证数字 paramName 参数名 例如：p1_MerchantNo paramValue 参数值 paramMaxSize 参数值最大长度
	 */
	public static WebTradeBizException validateDouble(WebTradeBizException webTradeBizException, String paramName, String paramValue) throws WebTradeBizException {
		if (webTradeBizException != null) {
			return webTradeBizException;
		}
		if (StringUtils.isBlank(paramValue)) {
			webTradeBizException = WebTradeBizException.GF_PARAMS_EXCEPTION;
			String msg = "传递参数有误：" + "[" + paramName + "为空]";
			webTradeBizException.setMsg(msg);
			return webTradeBizException;
		} else {
			if (!ValidateUtils.isDoubleAnd2decimals(paramValue)) {
				webTradeBizException = WebTradeBizException.GF_PARAMS_EXCEPTION;
				String msg = "传递参数有误：" + "[" + paramName + "=" + paramValue + ";不是正确的数字类型]";
				webTradeBizException.setMsg(msg);
				return webTradeBizException;
			}
		}
		return webTradeBizException;
	}

}

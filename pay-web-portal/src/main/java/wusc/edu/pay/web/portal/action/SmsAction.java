package wusc.edu.pay.web.portal.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.biz.SmsBiz;
import wusc.edu.pay.web.portal.util.PhoneCode;


/**
 * 手机短信Action
 * 
 * @author liliqiong
 * @date 2013-10-28
 * @version 1.0
 */
public final class SmsAction extends BaseAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7534758357650437285L;
	private static final Log LOG = LogFactory.getLog(SmsAction.class);
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private SmsBiz smsBiz;
	

	/**
	 * 获取手机短信验证码.
	 * @return msg.
	 */
	public void getSms() {
		String phone = getString("phone");// 手机号
		String loginName = getString("loginName");// 登录名
		String bindingType = getString("bindingType");// 绑定状态
		String smsType = getString("smsType");// 短信类型（不同短信类型对应不同的短信模板）
		String phoneCode = PhoneCode.getPhoneCode(); // 随机短信验证码
		
		// 验证参数
		String status = "1";// 成功状态
		String message = "验证码短信已发送";// 提示信息
		Map<String, String> msgMap = validatePhoneCodeCheck(phone, loginName, bindingType);
		if (!msgMap.isEmpty()) {
			status = "0";// 失败
			message = msgMap.get("errorMsg");
		} else {
			
			String smsTemplatePath = "template/sms/SmsCommonTemplate.vm";
			if (StringUtils.isNotBlank(smsType)){
				smsTemplatePath = "template/sms/" + smsType + ".vm";
			}
			
			Map<String, Object> paramModel = new HashMap<String, Object>();
			paramModel.put("phoneCode", phoneCode);
			
			String smsContent = "";
			try {
				smsContent = smsBiz.mergeSmsTemplate(smsTemplatePath, paramModel);
			} catch (Exception e) {
				LOG.error("短信模板解释异常", e);
			}
			
			smsBiz.sendSms(phone, smsContent);
			
			getSessionMap().put(BaseConsts.CURRENT_USER_PHONE_CODE, loginName + "&" + phoneCode);
		}
		if(PublicConfig.IS_DEV_STATUS){
			outPrint(this.getHttpResponse(), "[{\"status\":\"" + status + "\",\"phone\":\"" + phone + "\",\"msgContent\":\"" + phoneCode + "\",\"message\":\"" + message+ "(因为没短信接口:" + phoneCode + ")" + "\"}]");
		}else{
			outPrint(this.getHttpResponse(), "[{\"status\":\"" + status + "\",\"phone\":\"" + phone + "\",\"message\":\"" + message + "(因为没短信接口:" + phoneCode + ")" + "\"}]");
		}
		
	}

	/**
	 * 校验获取验证码参数.
	 * @param phone
	 * @param loginName
	 * @param bindingType
	 * @return
	 */
	private Map<String, String> validatePhoneCodeCheck(String phone, String loginName, String bindingType) {
		Map<String, String> msgMap = new HashMap<String, String>();
		
		if (ValidateUtils.isEmpty(phone)) {
			msgMap.put("errorMsg", "请输入接收短信验证码的手机号码");
			return msgMap;
		}else if (!ValidateUtils.isMobile(phone)) {
			msgMap.put("errorMsg", "请输入正确的手机号码");
			return msgMap;
		}
		
		// 绑定手机
		if ("binding".equals(bindingType)) {
			// 判断手机号是否与绑定手机一致
			UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
			if (ValidateUtils.isEmpty(userInfo) || !phone.equals(userInfo.getBindMobileNo())) {
				msgMap.put("errorMsg", "手机号码与绑定手机号码不一致");
			}
		}
		
		return msgMap;
	}

}

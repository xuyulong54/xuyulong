package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;


/***
 * 
 * @描述: 修改预留信息.
 * @作者: Lanzy.
 * @创建时间: 2014-6-5, 下午3:48:51 .
 * @版本: V1.0.
 * 
 */
public class EditReservationInfoAction extends BaseAction {
	private static final long serialVersionUID = 2828310271543417498L;

	/**
	 * 去修改商户预留信息
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Question:Geeting")
	public String editMerchantGreetingUI() {
		return "editMerchantGreetingUI";
	}

	/**
	 * 修改商户预留信息
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Question:Geeting")
	public String editMerchantGreeting() {

		String greeting = StringTools.stringToTrim(getString("greeting"));
		String tradePwd = super.getPwd("tradePwd");

		// 1.验证参数
		Map<String, String> msgMap = validateEditMerchantGreeting(greeting, tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改预留信息.");
			putData("greeting", greeting);
			pushData(msgMap);
			return "editMerchantGreetingUI";
		}

		// 2.修改预留信息
		this.setUserGreeting(greeting);
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改预留信息.");
		return "editMerchantGreeting";
	}

	public Map<String, String> validateEditMerchantGreeting(String greeting, String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(greeting) || greeting.length() < 5 || greeting.length() > 20) {
			msgMap.put(errorMsg, "预留信息请输入5-20个字符");
			return msgMap;
		}

		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtil.isEmpty(tradePwdMsg)) {
			msgMap.put(errorMsg, tradePwdMsg);
		}

		return msgMap;
	}
}

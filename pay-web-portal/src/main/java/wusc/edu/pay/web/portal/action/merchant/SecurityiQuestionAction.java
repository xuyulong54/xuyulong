package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.SecurityQuestionEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MerchantLogonAction;


/**
 * 商户：安全问题
 * 
 * @author liliqiong
 * @date 2013-11-4
 * @version 1.0
 */
public class SecurityiQuestionAction extends MerchantLogonAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserManagementFacade userManagementFacade;

	/**
	 * 去修改安全问题页面
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Question:Geeting")
	public String editSecurityiQuestionUI() {
		putData("questions", SecurityQuestionEnum.values());
		return "editSecurityiQuestionUI";
	}

	/**
	 * 修改安全问题
	 * 
	 * @return
	 */
	@Permission("SecurityCenter:Question:Geeting")
	public String editSecurityiQuestion() {
		String question = StringTools.stringToTrim(getString("question"));
		String answer = StringTools.stringToTrim(getString("answer"));
		String tradePwd = super.getPwd("tradePwd");
		// 1.验证参数
		Map<String, String> msgMap = validateEditSecurityiQuestion(question, answer, tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改安全问题.");
			putData("question", question);
			putData("answer", answer);
			pushData(msgMap);
			putData("questions", SecurityQuestionEnum.values());
			return "editSecurityiQuestionUI";
		}
		// 2.修改安全问题
		UserInfo userInfo = super.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		userInfo.setQuestion(question);
		userInfo.setAnswer(answer);
		userManagementFacade.updateUserInfo(userInfo);
		
		// 3.更新SESSION当前用户
		setCurrentUserInfo(userInfo);
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改安全问题.");
		return "editSecurityiQuestion";
	}

	public Map<String, String> validateEditSecurityiQuestion(String question, String answer, String tradePwd) {
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(answer) || answer.trim().length() < 2 || answer.trim().length() > 16) {
			msgMap.put(errorMsg, "安全保护问题答案在2~16个字之间");
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

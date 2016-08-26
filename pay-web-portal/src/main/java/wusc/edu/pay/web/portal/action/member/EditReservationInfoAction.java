package wusc.edu.pay.web.portal.action.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.base.MemberLogonAction;


/***
 * 
 * @描述: 修改预留信息.
 * @作者: Lanzy.
 * @创建时间: 2014-6-5, 下午3:38:51 .
 * @版本: V1.0.
 * 
 */
public class EditReservationInfoAction extends MemberLogonAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8055242617330835083L;
	@Autowired
	private UserManagementFacade userManagementFacade;

	/**
	 * 去 修改会员预留信息
	 * 
	 * @return
	 */
	public String editMemberGreetingUI() {
		return "editMemberGreetingUI";
	}

	/**
	 * 修改会员预留信息
	 * 
	 * @return
	 */
	public String editMemberGreeting() {
		String greeting = StringTools.stringToTrim(getString("greeting"));
		String tradePwd = super.getPwd("tradePwd");
		// 1.验证参数
		Map<String, String> msgMap = validateEditMemberGreeting(greeting, tradePwd);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "修改预留信息.");
			putData("greeting", greeting);
			pushData(msgMap);
			return "editMemberGreetingUI";
		}
		// 2.修改预留信息
		UserInfo userInfo = super.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		userInfo.setGreeting(greeting);
		userManagementFacade.updateUserInfo(userInfo);
		// 3.更新SESSION
		setCurrentUserInfo(userInfo);
		this.addUserLog(OpeStatusEnum.SUCCESS, "修改预留信息.");
		return "editMemberGreeting";
	}

	public Map<String, String> validateEditMemberGreeting(String greeting, String tradePwd) {
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

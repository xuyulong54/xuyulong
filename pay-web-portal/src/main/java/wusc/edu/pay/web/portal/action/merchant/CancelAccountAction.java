package wusc.edu.pay.web.portal.action.merchant;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.enums.UserStatusEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/**
 * @描述: .商户销户
 * @作者: Liliqiong.
 * @创建时间: 2014-4-24, 上午10:13:37 .
 * @版本: V1.0.
 * 
 */
@Scope("prototype")
public class CancelAccountAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 注销协议页面
	 * 
	 * @return
	 */
	public String cancelAgreementUI() {
		return "cancelAgreementUI";
	}

	/**
	 * 判断是否同意协议，进入注销说明页面
	 * 
	 * @return
	 */
	public String cancelAgreement() {
		// 用户信息
		UserInfo userInfo = userQueryFacade.getUserInfoByAccountNo(getCurrentUserInfo().getAccountNo());
		putData("currentUserInfo", userInfo);
		putData("showBindMobile", ValidateUtils.isEmpty(userInfo.getBindMobileNo()) ? "" : StringTools.phoneChange(userInfo.getBindMobileNo()));

		// 账户信息
		Account accountParam = this.getAccount();
		putData("availableBalance", AmountUtil.roundDown(accountParam.getAvailableBalance())); // 账户可用余额
		putData("unBalance", AmountUtil.roundDown(accountParam.getUnBalance())); // 账户不可用余额

		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		return "cancelAgreement";
	}

	/**
	 * 验证用户信息页面，提交注销申请
	 * 
	 * @return
	 */
	public String authenticate() {
		// 获取页面参数
		String cardNo = getString("cardNo");// 身份证号
		String requestDesc = getString("requestDesc");// 注销原因
		String phoneCode = getString("phoneCode");// 短信验证码
		String tradePwd = super.getPwd("tradePwd");// 支付密码

		// 验证页面参数
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo());
		Map<String, String> msgMap = validateAuthenticate(cardNo, requestDesc, phoneCode, tradePwd, userInfo);
		if (!msgMap.isEmpty()) {
			this.addUserLog(OpeStatusEnum.FAIL, "提交销户申请");
			pushData(msgMap);
			putData("cardNo", cardNo);
			putData("requestDesc", requestDesc);

			// 用户信息
			putData("currentUserInfo", userInfo);
			putData("showBindMobile", ValidateUtils.isEmpty(userInfo.getBindMobileNo()) ? "" : StringTools.phoneChange(userInfo.getBindMobileNo()));

			// 账户信息
			Account accountParam = this.getAccount();
			putData("availableBalance", AmountUtil.roundDown(accountParam.getAvailableBalance())); // 账户可用余额
			putData("unBalance", AmountUtil.roundDown(accountParam.getUnBalance())); // 账户不可用余额

			putData("PublicStatusEnum", PublicStatusEnum.toMap());
			return "cancelAgreement";
		}
		// 提交销户申请
		try {
			userAuditFacade.createUserAuditRecordClose(userInfo.getUserNo(), userInfo.getLoginName(), userInfo.getRealName(), requestDesc);
			this.addUserLog(OpeStatusEnum.SUCCESS, "提交销户申请");
			// 3.清除Session，以强制重新登录
			getSessionMap().clear();
		} catch (UserBizException e) {
			putData(BaseConsts.PAGE_ERROR_MSG, e.getMessage());
			return BaseConsts.ACTION_ERROR_ADD;
		}
		return "authenticate";
	}

	public Map<String, String> validateAuthenticate(String cardNo, String requestDesc, String phoneCode, String tradePwd, UserInfo userInfo) {
		if(getCurrentUserOperator().getType().intValue()!=UserOperatorTypeEnum.ADMIN.getValue()){
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：非管理员不可申请销户");
		}
		// 判断是否为激活状态
		if (userInfo.getStatus().intValue() != UserStatusEnum.ACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：不可销户，非激活状态用户");
		}
		// 判断是否实名认证
		if (userInfo.getIsRealNameAuth().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：不可销户，未实名认证");
		}
		// 判断是否绑定邮箱
		if (userInfo.getIsMobileAuth().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：不可销户，未绑定手机");
		}
		// 判断是否是管理员（非管理员不可销户）
		if (getCurrentUserOperator().getType().intValue() != UserOperatorTypeEnum.ADMIN.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数错误：不可销户，当前用户不是管理员");
		}
		Map<String, String> msgMap = new HashMap<String, String>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(cardNo) || !cardNo.toUpperCase().equals(getCurrentUserInfo().getCardNo().toUpperCase())) {
			msgMap.put(errorMsg, "请输入正确的身份证号");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(requestDesc)) {
			msgMap.put(errorMsg, "请输入注销原因");
			return msgMap;
		}
		if (!ValidateUtil.isValidatePhoneCode(userInfo.getLoginName(), phoneCode, getCurrentPhoneCode())) {
			msgMap.put(errorMsg, "请输入正确的验证码");
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

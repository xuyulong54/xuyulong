package wusc.edu.pay.web.portal.action;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.utils.WebUtil;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.bank.entity.CardBin;
import wusc.edu.pay.facade.bank.service.CardBinFacade;
import wusc.edu.pay.facade.boss.entity.GlobalSet;
import wusc.edu.pay.facade.boss.service.GlobalSetFacade;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.entity.UserTradePwd;
import wusc.edu.pay.facade.user.enums.UserOperatorTypeEnum;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.service.UserTradePwdFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.util.ValidateUtil;

import com.google.code.kaptcha.Constants;

/**
 * 页面异步验证
 * 
 * @author liliqiong
 * @date 2013-12-19
 * @version 1.0
 */
public class AjaxValidateAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	// private static final Log LOG =
	// LogFactory.getLog(AjaxValidateAction.class);

	@Autowired
	private AccountQueryFacade accountQueryFacade;
	/*
	 * @Autowired private SettlementFacade settlementFacade;
	 */
	@Autowired
	private GlobalSetFacade globalSetFacade;
	@Autowired
	private CardBinFacade cardBinFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private UserOperatorFacade userOperatorFacade;
	@Autowired
	private UserTradePwdFacade userTradePwdFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	private static final String STATE = "STATE";
	private static final String MSG = "MSG";

	/**
	 * 图片验证码是否正确
	 */
	public void randomCodeValidate() {
		String randomCode = StringTools.stringToTrim(getString("randomCode"));

		String kaptchaCode = (String) WebUtil.getSession(getHttpRequest(), Constants.KAPTCHA_SESSION_KEY);
		if (ValidateUtils.isEmpty(kaptchaCode) || !kaptchaCode.equalsIgnoreCase(randomCode)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "验证码错误");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户手机短信码是否正确
	 */
	public void phoneCodeValidate() {
		String loginName = ValidateUtils.isEmpty(getString("loginName")) ? getCurrentUserInfo().getLoginName() : getString("loginName");
		String phoneCode = getString("phoneCode");
		if (!ValidateUtil.isValidatePhoneCode(loginName, phoneCode, getCurrentPhoneCode())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "短信验证码错误");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户登录名是否正确(注册)
	 */
	public void userRegloginNameValidate() {
		String loginName = StringTools.stringToTrim(getString("loginName"));
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
		if (!ValidateUtils.isEmpty(userInfo)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "该用户名已存在");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * （登录后）验证支付密码
	 * 
	 * @param tradePwd
	 */
	public void validateTreadPwd(String tradePwd) {
		String msg = validateTradePwd(tradePwd);
		if (!ValidateUtils.isEmpty(msg)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, msg);
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户找回登录密码用户名验证
	 */
	public void lookforPwdloginNameValidate() {

		String loginName = StringTools.stringToTrim(getString("loginName"));
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
		if (!ValidateUtils.isEmpty(userOperator)) {
			if (userOperator.getStatus().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "该用戶为不可用状态");
			} else if (userOperator.getType().intValue() != UserOperatorTypeEnum.ADMIN.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "操作员不提供找回密码功能，请联系管理员重置密码");
			}
		}
		else if(ValidateUtils.isEmpty(userInfo)) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "该用户不存在");
			}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户登录密码是否正确 ： 找回密码，修改密码
	 */
	public void oldLoginPwdValidate() {
		String oldLoginPwd = StringTools.stringToTrim(getString("lgoldPwd"));
		String loginName = getString("loginName");
		if (ValidateUtils.isEmpty(loginName)) {
			loginName = getCurrentUserOperator().getLoginName();
		}
		String trueLoginPwd = userOperatorFacade.getUserOperatorByLoginName(loginName).getLoginPwd();
		if (!trueLoginPwd.equals(DigestUtils.sha1Hex(oldLoginPwd))) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "原密码不正确");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 商户原支付密码是否正确
	 */
	public void merchantOldTradePwdValidate() {
		String oldLoginPwd = StringTools.stringToTrim(getString("troldPwd"));
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
		if (!userTradePwd.getTradePwd().equals(DigestUtils.sha1Hex(oldLoginPwd))) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "原密码不正确");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

	}

	/**
	 * 会员原支付密码是否正确
	 */
	public void memberOldTradePwdValidate() {
		String oldLoginPwd = StringTools.stringToTrim(getString("troldPwd"));
		UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
		if (!userTradePwd.getTradePwd().equals(DigestUtils.sha1Hex(oldLoginPwd))) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "原密码不正确");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

	}

	/**
	 * 会员原绑定手机是否正确
	 */
	public void memberOldbingMobile() {
		String loginName = getString("loginName");
		String bingMobile = getString("bindingPhone");
		if (ValidateUtils.isEmpty(loginName)) {
			loginName = getCurrentUserInfo().getLoginName();
		}
		String oldTrueBindPhone = userQueryFacade.getUserInfoByLoginName(loginName).getBindMobileNo();
		if (!oldTrueBindPhone.equals(bingMobile)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "与绑定手机不一致");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 商户原绑定手机是否正确
	 */
	public void oldbindMobile() {
		String loginName = getString("loginName");
		String bingMobile = getString("bindingPhone");
		if (ValidateUtils.isEmpty(loginName)) {
			loginName = getCurrentUserInfo().getLoginName();
		}
		String trueBindPhone = userQueryFacade.getUserInfoByLoginName(loginName).getBindMobileNo();
		if (!trueBindPhone.equals(bingMobile)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "与绑定手机不一致");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 充值金额限制
	 */
	public void rechargeAmountValidate() {
		Double addAmount = getDouble("addAmount");
		Double limitAmount = Double.valueOf(PublicConfig.RECHARGE_LIMIT_AMOUNT);
		if (addAmount > limitAmount) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "充值金额不可大于" + limitAmount.toString() + "元");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 会员充值金额限制
	 */
	public void rechargeMemberAmountValidate() {
		GlobalSet globalSetMax = globalSetFacade.getBySetKey("MEMBER_SINGLE_RECHARGE_MAX_LIMIT");
		GlobalSet globalSetMin = globalSetFacade.getBySetKey("MEMBER_SINGLE_RECHARGE_MIN_LIMIT");
		Double addAmount = getDouble("addAmount");
		if (addAmount > Double.valueOf(globalSetMax.getSetContent())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "单笔充值金额不可大于" + globalSetMax.getSetContent() + "元");
		}
		if (addAmount < Double.valueOf(globalSetMin.getSetContent())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "单笔充值金额不可小于" + globalSetMin.getSetContent() + "元");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 结算金额限制
	 */
	// public void settAmountValidate() {
	// Double settAmount = getDouble("settAmount");
	// Account account =
	// accountQueryFacade.getAccountByAccountNo(this.getCurrentUserInfo().getAccountNo());
	// Settlement settlement =
	// settlementFacade.findByAccountNo(account.getAccountNo());
	// /*
	// * if (settAmount > settlementParam.getMaxSettAmount()) { struts = 1;
	// message = "结算金额不可大于最高结算金额"; }
	// */
	// if (settAmount < settlement.getMinSettAmount()) {
	// getOutputMsg().put(STATE, "FAIL");
	// getOutputMsg().put(MSG, "结算金额不可小于最低结算金额");
	// }
	// if (settAmount > account.getSettleAvailableSettAmount()) {
	// getOutputMsg().put(STATE, "FAIL");
	// getOutputMsg().put(MSG, "结算金额不可大于可结算金额");
	// }
	// outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	// }

	/**
	 * 提现金额限制
	 */
	public void withdrawAmountValidate() {
		Double withdrawAmount = getDouble("withdrawAmount");// 提现金额
		GlobalSet globalSet = globalSetFacade.getBySetKey("MEMBER_SINGLE_CASH_MIN_LIMIT");
		GlobalSet globalSetParam1 = globalSetFacade.getBySetKey("MEMBER_SINGLE_CASH_MAX_LIMIT");
		Account account = accountQueryFacade.getAccountByAccountNo(getCurrentUserInfo().getAccountNo());
		if (withdrawAmount > (account.getBalance() - account.getUnBalance())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "提现金额不可大于可用余额");
		} else if (Double.valueOf(globalSet.getSetContent()) > withdrawAmount) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "提现金额不可小于会员提现单笔最小限额");
		} else if (Double.valueOf(globalSetParam1.getSetContent()) < withdrawAmount) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "提现金额不可大于会员提现单笔最大限额");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 商户转账金额限制
	 */
	public void merchantTransferAmountValidate() {
		Double transferAmount = getDouble("transferAmount");// 转账金额

		tradeLimitFacade.validateTradeAmount(LimitTrxTypeEnum.ACCOUNT_TRANSFER, "", "", "", BigDecimal.valueOf(transferAmount), getCurrentUserInfo().getUserNo());

		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 会员转账金额限制
	 */
	public void memberTransferAmountValidate() {
		Double transferAmount = getDouble("transferAmount");// 转账金额
		GlobalSet globalSet = globalSetFacade.getBySetKey("MEMBER_SINGLE_TRANSFER_MIN_LIMIT");
		GlobalSet globalSetParam1 = globalSetFacade.getBySetKey("MEMBER_SINGLE_TRANSFER_MAX_LIMIT");
		Account account = accountQueryFacade.getAccountByAccountNo(this.getCurrentUserInfo().getAccountNo());
		if (transferAmount > (account.getBalance() - account.getUnBalance())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "转账金额不可大于可用余额");
		} else if (Double.valueOf(globalSet.getSetContent()) > transferAmount) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "转账金额不可小于会员转账单笔最小限额");
		} else if (Double.valueOf(globalSetParam1.getSetContent()) < transferAmount) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "转账金额不可大于会员转账单笔最大限额");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户登录密码不可与支付密码一致，且不可以原密码一致
	 */
	public void loginEqTradePwd() {
		String loginName = getString("loginName");
		String lgnewPwd1 = getString("lgnewPwd1");// 登录密码
		String trueTradePwd = "";
		String oldTrueLoginPwd = "";
		if (!ValidateUtils.isEmpty(loginName)) {
			UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
			UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(loginName);
			trueTradePwd = userTradePwd.getTradePwd();
			oldTrueLoginPwd = userOperator.getLoginPwd();
		} else {
			UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
			trueTradePwd = userTradePwd.getTradePwd();
			UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(getCurrentUserOperator().getLoginName());
			oldTrueLoginPwd = userOperator.getLoginPwd();
		}

		if (DigestUtils.sha1Hex(lgnewPwd1).equals(trueTradePwd)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "登录密码不可与支付密码一致");
		} else if (DigestUtils.sha1Hex(lgnewPwd1).equals(oldTrueLoginPwd)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "新密码不可与原密码一样");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 用户支付密码不可与登录密码一致，且不可以原密码一致
	 */
	public void tradeEqLoginPwd() {
		String loginName = getString("loginName");
		String trnewPwd1 = getString("trnewPwd1");// 支付
		String trueLoginPwd = "";
		String oldTrueLoginPwd = "";
		if (!ValidateUtils.isEmpty(loginName)) {
			UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
			trueLoginPwd = userOperator.getLoginPwd();
			UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(loginName);
			oldTrueLoginPwd = userTradePwd.getTradePwd();
		} else {
			UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(getCurrentUserOperator().getLoginName());
			trueLoginPwd = userOperator.getLoginPwd();
			UserTradePwd userTradePwd = userTradePwdFacade.getUserTradePwdByLoginName(getCurrentUserInfo().getLoginName());
			oldTrueLoginPwd = userTradePwd.getTradePwd();
		}
		if (DigestUtils.sha1Hex(trnewPwd1).equals(trueLoginPwd)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "登录密码不可与支付密码一致");
		} else if (DigestUtils.sha1Hex(trnewPwd1).equals(oldTrueLoginPwd)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "新密码不可与原密码一样");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 身份证号验证
	 */
	public void cardNoValidate() {
		String cradNo = getString("cardNo");
		if (!getCurrentUserInfo().getCardNo().toUpperCase().equals(cradNo.toUpperCase())) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "身份证号错误");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 身份证号是否被其他用户认证过
	 */
	public void memberRealCardNoValidate() {
		String cardNo = getString("cardNo");
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("cardNo", cardNo);
		paramMap.put("isRealNameAuth", PublicStatusEnum.ACTIVE.getValue());
		paramMap.put("userType", UserTypeEnum.CUSTOMER.getValue());
		pageBean = userQueryFacade.listUserInfoListPage(getPageParam(), paramMap);
		if (!ValidateUtils.isEmpty(pageBean) && pageBean.getTotalCount() >= 1) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "此身份证号已被其他会员认证");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 操作员角色名
	 */
	public void merchantOperatorRoleName() {
		// TODO:此处需判断是POS，还是在线商户
		// Long id = getLong("id");
		// Map<String, Object> map = new HashMap<String, Object>();
		// map.put("merchantId", getCurrentUserBySession().getUserId());
		// try {
		// map.put("roleName", java.net.URLDecoder.decode(getString("roleName"),
		// "UTF-8"));
		// } catch (UnsupportedEncodingException e) {
		// LOG.error("AjaxValidateAction merchantOperatorRoleName fail:", e);
		// }
		// List list = merchantRoleFacade.listModelByCondition(map);
		// if (list != null && list.size() > 0) {
		// if (ValidateUtils.isEmpty(id)) {
		// getOutputMsg().put(STATE, "FAIL");
		// getOutputMsg().put(MSG, "角色名重复");
		// } else {
		// for (int i = 0; i < list.size(); i++) {
		// MerchantRole merchantRole = (MerchantRole) list.get(i);
		// if (!merchantRole.getId().equals(id)) {
		// getOutputMsg().put(STATE, "FAIL");
		// getOutputMsg().put(MSG, "角色名重复");
		// }
		// }
		// }
		// }
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 商户转账，收款人验证
	 */
	public void merchantGetUserNameByAccount() {
		String targetLoginName = getString("payeeAccountNo");// 收款人登录名
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(targetLoginName);
		if (ValidateUtils.isEmpty(userInfo)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "转账的用户名不存在!");
		} else {
			Account account = accountQueryFacade.getAccountByUserNo(userInfo.getUserNo());
			if (userInfo.getUserType().intValue() == UserTypeEnum.CUSTOMER.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "商户不能转账给会员!");
			} else if (account.getStatus().intValue() != AccountStatusEnum.ACTIVE.getValue() && account.getStatus().intValue() != AccountStatusEnum.INACTIVE_FREEZE_DEBIT.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "收款人账户状态为" + AccountStatusEnum.getEnum(account.getStatus()).getDesc() + ",不可转账");
			} else if (userInfo.getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				getOutputMsg().put(MSG, "收款人未进行实名验证，存在风险!");
				getOutputMsg().put(STATE, "SUCC");
				getOutputMsg().put("FULLNAME", StringTools.PayeeNameChange(userInfo.getRealName()));
			}
		}
		/*// 看收款方是否开通收款权限
		try {
			tradeLimitFacade.validateBizFunctionAvailable(userInfo.getUserNo(), LimitTrxTypeEnum.ACCOUNT_TRANSFER);
		} catch (SwitchLimitException exception) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, exception.getMsg());
		}*/
		
		if (getOutputMsg().get("MSG") == null) {
			getOutputMsg().put("FULLNAME", StringTools.PayeeNameChange(userInfo.getRealName()));
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 会员转账，收款人验证
	 */
	public void memberInfoGetUserNameByAccount() {
		String targetLoginName = getString("payeeAccountNo");// 收款人登录名
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(targetLoginName);

		if (ValidateUtils.isEmpty(userInfo)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "转账的用户名不存在!");
		} else {
			Account account = accountQueryFacade.getAccountByUserNo(userInfo.getUserNo());
			if (account.getStatus().intValue() != AccountStatusEnum.ACTIVE.getValue() && account.getStatus().intValue() != AccountStatusEnum.INACTIVE_FREEZE_DEBIT.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "收款人账户状态为" + AccountStatusEnum.getEnum(account.getStatus()).getDesc() + ",不可转账");

			} else if (userInfo.getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				getOutputMsg().put(MSG, "收款人未进行实名验证，存在风险!");
				getOutputMsg().put(STATE, "SUCC");
				getOutputMsg().put("FULLNAME", StringTools.PayeeNameChange(userInfo.getRealName()));
			}
		}
		if (getOutputMsg().get("MSG") == null) {
			getOutputMsg().put("FULLNAME", StringTools.PayeeNameChange(userInfo.getRealName()));
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 绑定提现银行卡
	 */
	public void bindSettBankCard() {
		String bankAccountNo = getString("bankAccountNo");
		CardBin cardBin = cardBinFacade.getByCardBin(bankAccountNo.substring(0, 6), PublicStatusEnum.ACTIVE.getValue());
		if (ValidateUtils.isEmpty(cardBin) || cardBin.getCardKind().intValue() != BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue()
				|| cardBin.getStatus().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "请输入有效的借记银行卡号");
		} else {
			UserBankAccount userBankAccount = userBankAccountFacade.getByBankAccountNoAndUserNo(bankAccountNo, getCurrentUserInfo().getUserNo());
			if (!ValidateUtils.isEmpty(userBankAccount) && userBankAccount.getBankAccountType().intValue() == BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue()
					&& userBankAccount.getIsDelete().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
				getOutputMsg().put(STATE, "FAIL");
				getOutputMsg().put(MSG, "您已添加此银行卡，请勿重复添加。");
			} else {
				String bankName = cardBin.getBankName();
				getOutputMsg().put(STATE, "SUCC");
				getOutputMsg().put(MSG, cardBin.getBankCode().substring(0, 3));
				getOutputMsg().put("bankName", bankName);
			}
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

	}

	/**
	 * 操作员登录名
	 */
	public void operatorLoginNameValidate() {
		String loginName = getString("loginName");
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(loginName);
		if (!ValidateUtils.isEmpty(userOperator)) {
			getOutputMsg().put(STATE, "FAIL");
			getOutputMsg().put(MSG, "操作员登录名已存在，请重新输入");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
}
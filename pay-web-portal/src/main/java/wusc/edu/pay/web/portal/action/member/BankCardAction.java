package wusc.edu.pay.web.portal.action.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.CardTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.bank.entity.CardBin;
import wusc.edu.pay.facade.bank.service.CardBinFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankInfo;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.service.RemitBankAreaFacade;
import wusc.edu.pay.facade.remit.service.RemitBankInfoFacade;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.base.BaseConsts;
import wusc.edu.pay.web.portal.exceptions.PortalMerchantException;


/**
 * <ul>
 * <li>Title:提现银行卡绑定</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Liliqiong
 * @version 2014-6-10
 */
@Scope("prototype")
public class BankCardAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private static Log LOG = LogFactory.getLog(BankCardAction.class);

	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private CardBinFacade cardBinFacade;
	@Autowired
	private RemitBankInfoFacade remitBankInfoFacade;
	@Autowired
	private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private RemitBankAreaFacade remitBankAreaFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 进入银行卡管理页面
	 * 
	 * @return
	 */
	public String viewBankCard() {
		String userNo = getCurrentUserInfo().getUserNo();
		// 快捷支付银行卡
		List<UserBankAccount> listFastBankAccount = userBankAccountFacade.listFastBankAccountByMemberUserNo(userNo);
		// 绑定银行卡
		List<UserBankAccount> listSettlementBankAccount = userBankAccountFacade.listSettlementBankAccountByMemberUserNo(userNo);

		// 设置相关信息到页面
		putData("listFastBankAccount", listFastBankAccount);
		putData("listSettlementBankAccount", listSettlementBankAccount);
		putData("bankCardTypeEnumList", BankAccountTypeEnum.values());
		super.putEnums();
		return "viewBankCard";
	}

	/**
	 * 进入新增提现银行卡页面
	 * 
	 * @return
	 */
	public String addSettbankCardUI() {
		putData("bankCodeList", BankCode.toStringMap());
		List<RemitBankType> remitBankTypeList = remitBankTypeFacade.listActiveBank();
		putData("remitBankTypeList", remitBankTypeList);
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		putData("provinceList", remitBankAreaFacade.getProvince());
		putData("userInfo", userQueryFacade.getUserInfoByUserNo(getCurrentUserInfo().getUserNo()));
		super.putEnums();
		return "addSettbankCardUI";
	}

	/**
	 * 新增银行卡信息
	 * 
	 * @return
	 */
	public String addSettbankCard() {

		String bankAccountNo = StringTools.stringToTrim(getString("bankAccountNo"));
		String province = StringTools.stringToTrim(getString("province"));// 省
		String city = StringTools.stringToTrim(getString("city"));// 市
		String bankChannelNo = StringTools.stringToTrim(getString("bankChannelNo"));// 支行编号

		UserBankAccount userBankAccount = userBankAccountFacade.getByBankAccountNoAndUserNo(bankAccountNo, getCurrentUserInfo().getUserNo());
		// 验证，并获取卡BIN
		Map<String, Object> map = validateEditBank(bankAccountNo, province, city, userBankAccount);
		CardBin cardBin = (CardBin) map.remove("cardBin");
		if (map!=null&&!map.isEmpty()) {
			pushData(map);
			return BaseConsts.ACTION_ERROR_ADD;
		}
		if (ValidateUtils.isEmpty(userBankAccount)) {
			userBankAccount = new UserBankAccount();
		}
		// 封装参数，添加提现银行卡
		UserInfo userInfo = getCurrentUserInfo();
		userBankAccount.setLoginName(userInfo.getLoginName());// 用户登录名
		userBankAccount.setUserNo(userInfo.getUserNo());// 用户编号
		userBankAccount.setBankName(cardBin.getBankName());// 银行名称
		List<RemitBankType> bank = remitBankTypeFacade.listAll();
		String bankNo = cardBin.getBankCode().substring(0, 3);//银行行别代码
		for(RemitBankType bt : bank){
			if(StringUtil.equals(bankNo, bt.getTypeCode())){
				userBankAccount.setBankCode(bt.getBankCode());// 银行编码
			}
		}
		userBankAccount.setBankAccountName(userInfo.getRealName());// 开户名称(持卡人)
		userBankAccount.setBankAccountNo(bankAccountNo);// 开户账号
		userBankAccount.setBankAccountType(BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue());// 银行卡类型
		userBankAccount.setIsAuth(PublicStatusEnum.INACTIVE.getValue()); // (100:是,101:否,默认值为101)
		userBankAccount.setAuthTime(new Date());
		userBankAccount.setCardType(CardTypeEnum.ID_CARD.getValue());// 证件类型
		userBankAccount.setCardNo(ValidateUtils.isEmpty(userInfo.getCardNo()) ? "" : userInfo.getCardNo());// 证件号
		userBankAccount.setMobileNo(userInfo.getBindMobileNo());// 手机号
		userBankAccount.setIsDelete(PublicStatusEnum.INACTIVE.getValue());// 是否已删除(100:是,101:否,默认值为101)
		userBankAccount.setIsDefault(PublicStatusEnum.INACTIVE.getValue());// 是否默认(100:是,101:否,默认值为101)
		userBankAccount.setProvince(province);// 开户行所在省份
		userBankAccount.setCity(city);// 城市
		RemitBankInfo remitBankInfo = remitBankInfoFacade.getByBankChannelNo(bankChannelNo);
		userBankAccount.setBankAccountAddress(remitBankInfo.getBankName());// 开户行地址
		userBankAccount.setBankChannelNo(bankChannelNo);// 支行编号
		if (ValidateUtils.isEmpty(userBankAccount.getId())) {
			userBankAccountFacade.creatUserBankAccount(userBankAccount);
		} else {
			userBankAccountFacade.updateUserBankAccount(userBankAccount);
		}
		return viewBankCard();
	}

	/**
	 * 删除银行卡
	 */
	public void deleteBankCard() {
		Long id = getLong("id");// 银行卡号
		String tradePwd = super.getPwd("tradePwd");// 支付密码
		Map<String, Object> paraMap = validateDeleteBankCard(id, tradePwd);
		UserBankAccount userBankAccount = (UserBankAccount) paraMap.remove("userBankAccount");
		if (!ValidateUtils.isEmpty(paraMap)) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", paraMap.get(BaseConsts.PAGE_ERROR_MSG));
		} else {
			userBankAccount.setIsDelete(PublicStatusEnum.ACTIVE.getValue());
			userBankAccountFacade.updateUserBankAccount(userBankAccount);
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	// ************************************验证*********************************
	public Map<String, Object> validateEditBank(String bankAccountNo, String province, String city, UserBankAccount userBankAccount) {
		Map<String, Object> msgMap = new HashMap<String, Object>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		if (ValidateUtils.isEmpty(bankAccountNo) || bankAccountNo.length() < 8 || bankAccountNo.length() > 19) {
			msgMap.put(errorMsg, "请输入正确的银行卡号");
			return msgMap;
		}
		if (ValidateUtils.isEmpty(province) || ValidateUtils.isEmpty(city) || "0".equals(province) || "0".equals(city)) {
			msgMap.put(errorMsg, "请选择开户行地址");
			return msgMap;
		}
		if (getCurrentUserInfo().getIsRealNameAuth().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数丢失：未实名认证");
		}

		CardBin cardBin = cardBinFacade.getByCardBin(bankAccountNo.substring(0, 6), PublicStatusEnum.ACTIVE.getValue());
		if (ValidateUtils.isEmpty(cardBin) || cardBin.getCardKind().intValue() != BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue() || cardBin.getStatus().intValue() != PublicStatusEnum.ACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数丢失：银行卡号");
		}
		if (!ValidateUtils.isEmpty(userBankAccount) && userBankAccount.getBankAccountType().intValue() == BankAccountTypeEnum.PRIVATE_DEBIT_CARD.getValue() && userBankAccount.getIsDelete().intValue() == PublicStatusEnum.INACTIVE.getValue()) {
			throw new PortalMerchantException(PortalMerchantException.PARAM_IS_ERROR, "参数丢失：银行卡号不可重置绑定");
		}
		msgMap.put("cardBin", cardBin);
		return msgMap;
	}

	public Map<String, Object> validateDeleteBankCard(Long id, String tradePwd) {
		Map<String, Object> msgMap = new HashMap<String, Object>();
		String errorMsg = BaseConsts.PAGE_ERROR_MSG;
		UserBankAccount userBankAccount = userBankAccountFacade.getById(id);
		if (ValidateUtils.isEmpty(userBankAccount) || !userBankAccount.getUserNo().equals(getCurrentUserInfo().getUserNo())) {
			msgMap.put(errorMsg, "参数错误：未查到此绑定银行卡");
			return msgMap;
		}
		msgMap.put("userBankAccount", userBankAccount);
		// 支付密码合法验证
		String tradePwdMsg = validateTradePwd(tradePwd);
		if (!StringUtil.isEmpty(tradePwdMsg)) {
			msgMap.put(errorMsg, tradePwdMsg);
			return msgMap;
		}

		return msgMap;
	}

	/**
	 * @Title: 根据银行行别和区域查询银行信息
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void getBankInfoListByBankTypeCodeAndArea() {
		try {
			String bankTypeCode = getString("bankTypeCode");
			String province = getString("province");
			String city = getString("city");
			List<RemitBankInfo> remitBankInfoList = remitBankInfoFacade.listByBankTypeCodeAndArea(bankTypeCode, province, city);
			getOutputMsg().put("remitBankInfoList", remitBankInfoList);
		} catch (Exception e) {
			LOG.error("============= getBankInfoListByBankTypeCodeAndArea Exception", e);
			getOutputMsg().put("remitBankInfoList", null);
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

	public void getCityByProvince() {
		String province = getString("province");
		getOutputMsg().put("cityList", remitBankAreaFacade.getCityByProvince(province));
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));
	}

}

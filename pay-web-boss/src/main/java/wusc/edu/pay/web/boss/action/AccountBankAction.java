package wusc.edu.pay.web.boss.action;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.CardTypeEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/***
 * 
 * 类描述：企业结算账户Action
 * 
 * @author: blank
 * @date： 日期：2013-10-16 时间：下午9:25:58
 * @version 1.0
 */
public class AccountBankAction extends BossBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8307213986406912756L;
	private static Log log = LogFactory.getLog(AccountBankAction.class);
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	/**
	 * 进入账户的页面 .<br/>
	 * 
	 * @return addHelpUI .
	 */
	public String addAccountBankUI() {

		return "addAccountBankUI";
	}

	/**
	 * 添加账户
	 */
	public void addAccountBank() throws Exception {
		log.info("Enter addAccountBank method . ");
		try {
			UserBankAccount userBankAccount = new UserBankAccount();
			Long merchantId = getLong("merchantIdOne"); // 验证商户ID在银行账户表中存不存在
			log.info("商户id" + merchantId);
			log.info("merchantOnlineFacade" + merchantOnlineFacade);
			MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
			if (merchantId == null || merchant == null) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "商户信息为空");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}
			String loginName = getString("loginNameValue"); // 登录名
			// 通过账户编号查询该商户的结算银行账户是否存在
			UserBankAccount userBank = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(merchant.getMerchantNo());
			if (userBank != null) {
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "该账户已经存在银行账号信息!");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
				return;
			}
			
			String accountName = getString("bankAccountName"); // 开户名
			String bankAccountNo = getString("bankAccountNo"); // 开户账户
			Integer bankAccountType = getInteger("bankAccountType"); // 账户类型，对公还是对私
			if(bankAccountType == null){
				bankAccountType = getInteger("bankAccountType2");
			}
			String province = getString("provinceMerchantInfo");
			String city = getString("cityMerchantInfo");
			String bankName = getString("bankName"); // 行别
			String bankChannelName = getString("bankChannelName").split(",")[0]; // 开户行
			String bankChannelNo = getString("bankChannelNo"); //开户行号
			
			checkInputData();// 校验输入参数正确性
			
			String[] bankNames = bankName.split(","); // 银行名称
			userBankAccount.setLoginName(loginName);
			userBankAccount.setUserNo(merchant.getMerchantNo());
			userBankAccount.setBankAccountName(accountName);
			userBankAccount.setBankAccountNo(bankAccountNo);
			userBankAccount.setBankName(bankNames[1]); // 开户银行
			userBankAccount.setBankCode(bankNames[0]); // 银行编码
			userBankAccount.setBankAccountType(bankAccountType); // 银行账户类型
			userBankAccount.setBankAccountAddress(bankChannelName); //开户行
			userBankAccount.setProvince(province);
			userBankAccount.setCity(city);
			userBankAccount.setCardType(CardTypeEnum.ID_CARD.getValue());
			userBankAccount.setBankChannelNo(bankChannelNo); 
			
			userBankAccount.setIsDefault(PublicStatusEnum.ACTIVE.getValue());
			userBankAccount.setIsDelete(PublicStatusEnum.INACTIVE.getValue());
			userBankAccount.setIsAuth(PublicStatusEnum.INACTIVE.getValue());//是否已验证
			
			
			// 新增银行账户信息
			long bankAccountId = userBankAccountFacade.creatUserBankAccount(userBankAccount);
			if (bankAccountId > 0) {
				getOutputMsg().clear();
				getOutputMsg().remove("STATE");
				getOutputMsg().remove("MSG");
				super.logSave("添加账户.开户账号[" + userBankAccount.getBankAccountNo() + "]，开户名[" + accountName + "]");
				getOutputMsg().put("STATE", "SUCCESS");
				getOutputMsg().put("MSG", bankAccountId);
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

			} else {
				getOutputMsg().clear();
				getOutputMsg().remove("STATE");
				getOutputMsg().remove("MSG");
				super.logSaveError("添加企业银行账户.开户账号[" + userBankAccount.getBankAccountNo() + "]，开户名[" + accountName + "]");
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "0");
				outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "添加银行账户异常");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		}
	}

	/***
	 * 校验数据参数是否完整
	 */
	private void checkInputData() {
		String bankAccountName = getString("bankAccountName"); // 开户名
		String bankAccountNo = getString("bankAccountNo"); // 开户账户
		
		String province = getString("provinceMerchantInfo");
		String city = getString("cityMerchantInfo");
		String bankName = getString("bankName"); // 行别
		String bankChannelName = getString("bankChannelName"); // 开户行
		
		if("".equals(bankAccountName) || bankAccountName == null){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请输入银行账户名");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		
		if("".equals(bankAccountNo) || bankAccountNo == null){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请输入银行账号");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		
		if("".equals(province) || province == null || "".equals(city) || city == null ){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请选择银行所在地");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		
		if("".equals(bankName) || bankName == null){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请选择行别");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
		if("".equals(bankChannelName) || bankChannelName == null){
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "请选择开户行");
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
			return;
		}
	}

	/**
	 * 修改企业账户
	 */
	public String editAccountBank() throws Exception {
		Long merchantId = getLong("bankMerchantId");
		if (merchantId == null) {
			return operateError("商户信息为空");
		}
		// 查询账户ID
		MerchantOnline merchant = merchantOnlineFacade.getById(merchantId);
		if (merchant == null) {
			return operateError("商户信息为空");
		}
		
		UserInfo userInfo = userQueryFacade.getUserInfoByUserNo(merchant.getMerchantNo());
		
		// 通过商户编号查询账户是否存在
		UserBankAccount userBankAccount = userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(merchant.getMerchantNo());
		boolean isInsert = false; // 是否是新增
		if (userBankAccount == null) {
			isInsert = true;
			userBankAccount = new UserBankAccount();
			
			userBankAccount.setIsDefault(PublicStatusEnum.ACTIVE.getValue());
			userBankAccount.setIsDelete(PublicStatusEnum.INACTIVE.getValue());
			userBankAccount.setIsAuth(PublicStatusEnum.INACTIVE.getValue());//是否已验证
			userBankAccount.setLoginName(userInfo.getLoginName()); // 登录名
			userBankAccount.setUserNo(merchant.getMerchantNo());
			
		}
		
		String accountName = getString("bankAccountName"); // 开户行
		String bankAccountNo = getString("bankAccountNo"); // 开户行账号
		Integer bankAccountType = getInteger("bankAccountType"); // 账户类型，对公还是对私
		if(bankAccountType == null){
			bankAccountType = getInteger("bankAccountType2");
		}
		userBankAccount.setBankAccountNo(bankAccountNo);
		userBankAccount.setBankAccountName(accountName);
		String province = getString("provinceMerchantInfo");
		String city = getString("cityMerchantInfo");
		String bankName = getString("bankName"); // 行别
		String [] bankChannelNameArr = getString("bankChannelName").split(",");
		String bankChannelName = bankChannelNameArr[0]; // 开户行
		String bankChannelNo = getString("bankChannelNo"); //开户行号
		
		String[] bankNames = bankName.split(","); // 银行名称
		userBankAccount.setBankName(bankNames[1]); // 开户银行
		userBankAccount.setBankCode(bankNames[0]); // 银行编码
		userBankAccount.setBankAccountType(bankAccountType); // 银行账户类型
		userBankAccount.setBankAccountAddress(bankChannelName); //开户行
		userBankAccount.setProvince(province);
		userBankAccount.setCity(city);
		userBankAccount.setBankChannelNo(bankChannelNo); 
		
		long resultId = 0;
		if (isInsert) {
			resultId = userBankAccountFacade.creatUserBankAccount(userBankAccount);
			super.logSave("添加企业银行账户.开户账号[" + userBankAccount.getBankAccountNo() + "]，商户编号[" + userBankAccount.getUserNo() + "]");
			log.info("新增企业银行账户信息成功!");
		} else {
			resultId = merchantId;
			userBankAccountFacade.updateUserBankAccount(userBankAccount); // 修改企业银行账户信息
			super.logEdit("修改企业银行账户.开户账号[" + userBankAccount.getBankAccountNo() + "]，商户编号[" + userBankAccount.getUserNo() + "]");
			log.info("修改企业银行账户信息成功!==【" + userBankAccount.getId() + "】");
		}
		String ajaxRequest = getString("ajax");
		if ("".equals(ajaxRequest) || ajaxRequest == null) {
			return operateSuccess();
		} else {
			getOutputMsg().put("STATE", "SUCCESS");
			getOutputMsg().put("MSG", resultId);
			outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));

			return null;
		}
	}
}

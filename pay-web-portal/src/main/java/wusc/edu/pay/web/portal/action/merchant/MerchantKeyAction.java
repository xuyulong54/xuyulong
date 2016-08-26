package wusc.edu.pay.web.portal.action.merchant;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.OpeStatusEnum;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.rsa.RSAUtils;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.MerchantSecret;
import wusc.edu.pay.facade.user.enums.MerchantSignTypeEnum;
import wusc.edu.pay.facade.user.enums.MerchantTradeTypeEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.MerchantSecretFacade;
import wusc.edu.pay.web.portal.base.BaseAction;
import wusc.edu.pay.web.portal.util.ValidateUtil;


/***
 * 
 * @描述: 密钥管理.
 * @作者: Lanzy.
 * @创建时间: 2014-6-6, 下午3:08:47 .
 * @版本: V1.0.
 * 
 */
public class MerchantKeyAction extends BaseAction {
	private static final long serialVersionUID = 6696554456767330339L;

	// private static final Log LOG =
	// LogFactory.getLog(MerchantKeyAction.class);
	
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	
	@Autowired
	private MerchantSecretFacade MerchantSecretFacade;

	/**
	 * 去查看密钥页面
	 * 
	 * @return
	 */
	@Permission("CommonInfo:Key:View")
	public String showMerchantKeyUI() {
		putData("bean", getMerchantOnline());
		putData("user", getCurrentUserInfo());
		putData("bindMobileNoShow", ValidateUtils.isEmpty(getCurrentUserInfo().getBindMobileNo()) ? "" : StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
		putData("isMobileAuth", getCurrentUserInfo().getIsMobileAuth());
		putData("PublicStatusEnum", PublicStatusEnum.toMap());
		return "showMerchantKeyUI";
	}

	/**
	 * 查看密钥
	 */
	@Permission("CommonInfo:Key:View")
	public void showMerchantKey() {
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {
			getOutputMsg().put("STATE", "FAIL");
			getOutputMsg().put("MSG", "短信验证码错误");
		} else {
			MerchantOnline merchantOnline =  getMerchantOnline();
				getOutputMsg().put("merchantKey", getMerchantOnline().getMerchantKey());
				MerchantSecret merchantSecret = MerchantSecretFacade.getByMerchantNo(merchantOnline.getMerchantNo());
				if(merchantSecret != null){
					getOutputMsg().put("merchantPublicKey", merchantSecret.getMerchantPublicKey());
				}
			this.addUserLog(OpeStatusEnum.SUCCESS, "查看密钥");
		}
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}
	
	/**
	 * 修改商户密钥签名类型
	 * @return
	 */
	public String changeMerchantSignType(){
		
		putData("bindMobileNoShow", ValidateUtils.isEmpty(getCurrentUserInfo().getBindMobileNo()) ? "" : StringTools.phoneChange(getCurrentUserInfo().getBindMobileNo()));
		putData("bean", getMerchantOnline());
		putData("MerchantSignTypeEnum",MerchantSignTypeEnum.toMap());
		
		return "changeMerchantKeyTypeUI";
	}
	
	/**
	 * 修改签名类型
	 */
	public void changeSignTyepe(){
		
		String phoneCode = StringTools.stringToTrim(getString("phoneCode"));
		String merchantPubKey = StringTools.stringToTrim(getString("merchantPubKey"));
		String merchantKey =  StringTools.stringToTrim(getString("merchantKey"));
		String isRSA =  StringTools.stringToTrim(getString("isRSA"));
		
		MerchantOnline merchantOnline = getMerchantOnline();
		
			if (!ValidateUtil.isValidatePhoneCode(getCurrentUserInfo().getLoginName(), phoneCode, getCurrentPhoneCode())) {//短信验证码验证
				getOutputMsg().put("STATE", "FAIL");
				getOutputMsg().put("MSG", "短信验证码错误");
			}else{
				
				//判断商户私钥是否为空
				if(StringUtils.isBlank(merchantKey)){
					getOutputMsg().put("STATE", "FAIL");
					getOutputMsg().put("MSG", "商户密钥不能为空");
				}else if(merchantKey.getBytes().length < 10){
					getOutputMsg().put("STATE", "FAIL");
					getOutputMsg().put("MSG", "MD5签名密钥长度太短");
				}else if(merchantKey.getBytes().length > 200){
					getOutputMsg().put("STATE", "FAIL");
					getOutputMsg().put("MSG", "MD5签名密钥长度太长");
				}else{
					
					if(StringUtils.isNotBlank(isRSA) && "1".equals(isRSA)){//需要上传
						if(StringUtils.isBlank(merchantPubKey)){
							getOutputMsg().put("STATE", "FAIL");
							getOutputMsg().put("MSG", "RSA签名公钥不能为空");
						}else if(merchantPubKey.getBytes().length > 1000){
							getOutputMsg().put("STATE", "FAIL");
							getOutputMsg().put("MSG", "RSA签名公钥超长");
						}else{
							try {
								Map<String, Object> keyMap = merchantOnlineFacade.updateMerchantSignType(merchantKey, merchantPubKey, merchantOnline.getMerchantNo());
								
								String platFormPubKey = keyMap.get("publicKey").toString();//平台公钥
								String platFormPriKey = keyMap.get("privateKey").toString();//平台私钥
								getOutputMsg().put("platformPubKey",platFormPubKey);
								
								String sourceStr = "平台签名明文"+System.currentTimeMillis();
								String signStr = RSAUtils.sign(sourceStr.getBytes(), platFormPriKey);
								
								getOutputMsg().put("sourceStr",sourceStr);
								getOutputMsg().put("signStr",signStr);
								
							} catch (Exception e) {
								getOutputMsg().put("STATE", "FAIL");
								getOutputMsg().put("MSG", "上传失败");
							}
						}
					}else{//不需要上传
						merchantOnlineFacade.updateMerchantSignType(merchantKey, null, merchantOnline.getMerchantNo());
						getOutputMsg().put("platformPubKey","未上传商户RSA签名公钥，无法查看平台公钥");
						getOutputMsg().put("sourceStr","未上传商户RSA签名公钥，无法查看平台公钥");
						getOutputMsg().put("signStr","未上传商户RSA签名公钥，无法查看平台公钥");
					}
				}
			}
			
		outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
		
	}
	
	/**
	 * 查看平台公钥
	 * @return
	 * @throws Exception 
	 */
	public String showPlatPubKey() throws Exception{
		MerchantOnline merchantOnline = getMerchantOnline();
		
		MerchantSecret merchantSecret = MerchantSecretFacade.getByMerchantNo(merchantOnline.getMerchantNo());
			
		String platFormPubKey = "尚未上传商户RSA签名公钥，无法查看平台公钥";
		String platFormPriKey = "尚未上传商户RSA签名公钥，无法查看平台公钥";
		String sourceStr = "尚未上传商户RSA签名公钥，无法查看平台公钥";
		String signStr = "尚未上传商户RSA签名公钥，无法查看平台公钥";
		
		if(merchantSecret != null){
			platFormPubKey = merchantSecret.getPublicKey();//平台公钥
			platFormPriKey = merchantSecret.getPrivateKey();//平台私钥
			sourceStr = "平台签名明文"+System.currentTimeMillis();
			signStr = RSAUtils.sign(sourceStr.getBytes(), platFormPriKey);
			
		}
			
		putData("platformPubKey",platFormPubKey);
		putData("sourceStr",sourceStr);
		putData("signStr",signStr);
		
		return "showPlatPubUI";
	}
}

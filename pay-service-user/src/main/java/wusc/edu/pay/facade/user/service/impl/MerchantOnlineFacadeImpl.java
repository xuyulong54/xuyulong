package wusc.edu.pay.facade.user.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.rsa.RSAUtils;
import wusc.edu.pay.core.user.biz.MerchantOnlineBiz;
import wusc.edu.pay.core.user.biz.MerchantSecretBiz;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.MerchantSecret;
import wusc.edu.pay.facade.user.enums.MerchantSignTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

/**
 * 
 * @描述: 在线支付的商户Dubbo服务接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-22,下午4:48:16 .
 * @版本: 1.0 .
 */
@Component("merchantOnlineFacade")
public class MerchantOnlineFacadeImpl implements MerchantOnlineFacade {

	private Log logger = LogFactory.getLog(MerchantOnlineFacadeImpl.class);
	
	@Autowired
	private MerchantOnlineDao merchantOnlineDao; // 商户信息
	
	@Autowired
	private MerchantOnlineBiz merchantOnlineBiz;

	@Autowired
	private MerchantSecretBiz merchantSecretBiz;

	/**
	 * 根据商户编号获取在线支付商户信息.<br/>
	 * 
	 * @param merchantNo
	 *            商户编号.
	 * @return MerchantOnline.
	 */
	
	public MerchantOnline getMerchantOnlineByMerchantNo(String merchantNo) {
		return merchantOnlineDao.getByMerchantNo(merchantNo);
	}
	
	/**
	 * 根据身份证号获取在线支付商户信息.
	 * 
	 * @param cardNo
	 *            身份证号（唯一）.
	 * @return MerchantOnline.
	 */
	
	public MerchantOnline getMerchantOnlineByCardNo(String cardNo) {
		return merchantOnlineDao.getByCardNo(cardNo);
	}	
	
	/***
	 * 根据条件查询商户列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	public PageBean listMerchantListPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException {
		return merchantOnlineDao.listPage(pageParam, paramMap);
	}


	/***
	 * 修改在线商户信息
	 * @param merchantOnline
	 */
	public void update(MerchantOnline merchantOnline) {
		merchantOnlineDao.update(merchantOnline);
	}


	/***
	 * 根据商户ID查询商户信息
	 * @param merchantId
	 * @return
	 */
	public MerchantOnline getById(Long merchantId) {
		return merchantOnlineDao.getById(merchantId);
	}


	/***
	 * 根据商户全称查询商户信息
	 * @param fullName
	 * @return
	 */
	public MerchantOnline getByFullName(String fullName) {
		return merchantOnlineDao.getByFullName(fullName);
	}

	@Override
	public List<MerchantOnline> listBy(Map<String, Object> paramMap) {
		return merchantOnlineDao.listBy(paramMap);
	}
	
	@Override
	public Map<String, Object> countMerchant(
			List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser) {
		return merchantOnlineDao.countMerchant(listBlackUser, listFreezeUser);
	}
	
	
	
	/***
	 * 根据营业执照号查询商户信息
	 * @param licenseNo
	 * @return
	 */
	public MerchantOnline getMerchantByLicenseNo(String licenseNo) {
		return merchantOnlineDao.getMerchantByLicenseNo(licenseNo);
	}

	@Override
	public PageBean agentMerchantStautsSumm(PageParam pageParam, Map<String, Object> paramMap) {
		return merchantOnlineDao.agentMerchantStautsSumm(pageParam, paramMap);
	}

	/**
	 * 修改商户签名类型
	 * @param merchantKey 商户MD5签名密钥
	 * @param merchantPublicKey 商户公钥，目标签名类型为RSA是必传
	 * @param merchantNo 商户编号，必填
	 * @return Map<String , Object> 当目标签名类型为MD5时，返回商户私钥。当目标签名类型为RSA时，返回平台公钥及平台私钥
	 */
	@Override
	public Map<String, Object> updateMerchantSignType(String merchantKey,
			String merchantPublicKey, String merchantNo) throws UserBizException {
		return merchantOnlineBiz.updateMerchantSignType(merchantKey, merchantPublicKey, merchantNo);
	}

	
	/**
	 * 商户验签
	 * @param sourceStr 签名原文
	 * @param signStr 签名密文
	 * @param merchantSignTypeEnum 签名类型
	 * @param merchantNo 商户编号
	 * @return
	 */
	@Override
	public boolean merchantVerify(String sourceStr, String signStr,MerchantSignTypeEnum merchantSignTypeEnum, String merchantNo) {
		
		MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(merchantNo);
		if(merchantOnline == null){
			throw new UserBizException(UserBizException.MERCHANT_IS_NULL , "在线商户为空");
		}else{
			
			if(MerchantSignTypeEnum.MD5 == merchantSignTypeEnum){//MD5验证，查询在线表，得出密钥，进行签名及验签
				
				String merchantKey = merchantOnline.getMerchantKey();
				if(StringUtils.isBlank(merchantKey)){
					throw new UserBizException(UserBizException.MERCHANT_KEY_IS_NULL, "商户私钥为空" );
				}else{
					String platSignStr = DigestUtils.md5Hex(sourceStr + merchantKey).toUpperCase();
					if(platSignStr.equals(signStr)){
						return true;
					}else{
						return false;
					}
				}
				
			}else if(MerchantSignTypeEnum.RSA == merchantSignTypeEnum){//RSA验证，查询商户密钥表，得到商户公钥，进行签名及验签
				MerchantSecret merchantSecret = merchantSecretBiz.getByMerchantNo(merchantNo);
				if(merchantSecret == null){
					throw new UserBizException(UserBizException.MERCHANT_PUBLICKEY_IS_NULL,"商户公钥为空");
				}else{
					try {
						return RSAUtils.verify(sourceStr.getBytes(), merchantSecret.getMerchantPublicKey(), signStr);
					} catch (Exception e) {
						logger.error("验签异常",e);
						return false;
					}
				}
			}else{
				throw new UserBizException(UserBizException.MERCHANT_SIGNTYPE_ERR , "签名类型有误");
			}
		}
	}

	/**
	 * 商户签名
	 * @param sourceStr 签名原文
	 * @param merchantSignTypeEnum 签名类型
	 * @param merchantNo 商户编号
	 * @return
	 */
	@Override
	public String merchantSign(String sourceStr, MerchantSignTypeEnum merchantSignTypeEnum, String merchantNo) {
		MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(merchantNo);
		if(merchantOnline == null){
			throw new UserBizException(UserBizException.MERCHANT_IS_NULL , "在线商户为空");
		}else{
			if(MerchantSignTypeEnum.MD5 == merchantSignTypeEnum){//MD5验证，查询在线表，得出密钥，进行签名
				String merchantKey = merchantOnline.getMerchantKey();
				if(StringUtils.isBlank(merchantKey)){
					throw new UserBizException(UserBizException.MERCHANT_KEY_IS_NULL, "商户私钥为空" );
				}else{
					return DigestUtils.md5Hex(sourceStr + merchantKey).toUpperCase();
				}
			}else if(MerchantSignTypeEnum.RSA == merchantSignTypeEnum){//RSA验证，查询商户密钥表，得到商户公钥，进行签名
				MerchantSecret merchantSecret = merchantSecretBiz.getByMerchantNo(merchantNo);
				if(merchantSecret == null){
					throw new UserBizException(UserBizException.MERCHANT_PUBLICKEY_IS_NULL,"商户公钥为空");
				}else{
					try {
						return RSAUtils.sign(sourceStr.getBytes(), merchantSecret.getPrivateKey());
					} catch (Exception e) {
						return null;
					}
				}
			}else{
				throw new UserBizException(UserBizException.MERCHANT_SIGNTYPE_ERR , "签名类型有误");
			}
		}
	}
	
}

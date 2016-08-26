package wusc.edu.pay.core.user.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.rsa.RSAUtils;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.MerchantSecretDao;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.MerchantSecret;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 在线商户业务处理逻辑接口
 * @author： Peter
 * @ClassName: MerchantOnlineBiz.java
 * @Date： 2015-3-10 下午2:49:21 
 * @version：  V1.0
 */
@Component("merchantOnlineBiz")
public class MerchantOnlineBiz {
	
	private static final Log logger = LogFactory.getLog(MerchantOnlineBiz.class);
	
	@Autowired
	private MerchantOnlineDao merchantOnlineDao;
	
	@Autowired
	private MerchantSecretDao merchantSecretDao;
	/**
	 * 修改商户签名类型
	 * @param merchantKey 商户MD5签名密钥
	 * @param merchantPublicKey 商户公钥，目标签名类型为RSA是必传
	 * @param merchantNo 商户编号，必填
	 * @return Map<String , Object> 当目标签名类型为MD5时，返回商户私钥。当目标签名类型为RSA时，返回平台公钥及平台私钥
	 */
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> updateMerchantSignType(String merchantKey, String merchantPublicKey, String merchantNo){
			Map<String , Object> returnMap = new HashMap<String , Object>();//返回Map
		
			MerchantOnline merchantOnline = merchantOnlineDao.getByMerchantNo(merchantNo);//判断商户是否存在
			if(merchantOnline == null){
				throw new UserBizException(UserBizException.MERCHANT_IS_NULL, "商户不存在");
			}
			
			if(StringUtils.isBlank(merchantKey)){//商户密钥不能为空
				throw new UserBizException(UserBizException.MERCHANT_KEY_IS_NULL, "商户密钥为空");
			}
			
			//修改商户密钥
			merchantOnline.setMerchantKey(merchantKey);//设置商户密钥
			merchantOnlineDao.update(merchantOnline);//修改商户信息
			
			//判断商户公钥是否为空，如果不为空，则再生成平台公私钥
			if(StringUtils.isNotBlank(merchantPublicKey)){
				//修改商户公钥及平台公私钥，先判断商户密钥表内是否存在改商户的数据
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("merchantNo", merchantNo);
				MerchantSecret merchantSecret = merchantSecretDao.getBy(map);
				
				try {
					
					Map<String, Object> keyMap = RSAUtils.genKeyPair();
					String publicKey = keyMap.get(RSAUtils.PUBLIC_KEY_VALUE).toString();
					String privateKey = keyMap.get(RSAUtils.PRIVATE_KEY_VALUE).toString();
					
					if(merchantSecret == null){//不存在，重新创建一条
						merchantSecret = new MerchantSecret();
						merchantSecret.setMerchantNo(merchantNo);
						merchantSecret.setMerchantPublicKey(merchantPublicKey);
						merchantSecret.setPrivateKey(privateKey);
						merchantSecret.setPublicKey(publicKey);
						merchantSecret.setCreateTime(new Date());
						merchantSecretDao.insert(merchantSecret);
					}else{//存在，更新一条
						merchantSecret.setMerchantPublicKey(merchantPublicKey);
						merchantSecret.setPrivateKey(privateKey);
						merchantSecret.setPublicKey(publicKey);
						merchantSecretDao.update(merchantSecret);
					}
					
					returnMap.put("publicKey", publicKey);
					returnMap.put("privateKey", privateKey);
					
				} catch (Exception e) {
					logger.error("生成平台公私钥异常" , e);
					throw new UserBizException(UserBizException.GENERATE_PLATE_KEY_FAIL, "生成平台公私钥异常");
				}
			}
		return returnMap;
	}
}

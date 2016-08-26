package wusc.edu.pay.facade.user.service.impl; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.user.biz.MerchantSecretBiz;
import wusc.edu.pay.facade.user.entity.MerchantSecret;
import wusc.edu.pay.facade.user.service.MerchantSecretFacade;


/**
 *类描述：
 *@author: huangbin
 *@date： 日期：2014-1-6 时间：下午3:40:51
 *@version 1.0
 */
@Component("merchantSecretFacade")
public class MerchantSecretFacadeImpl implements MerchantSecretFacade {
	@Autowired
	private MerchantSecretBiz merchantSecretBiz;
	
	/***
	 * 保存商户秘钥信息
	 * @param secrt
	 * @return
	 */
	public long create(MerchantSecret secrt) {
		return merchantSecretBiz.create(secrt);
	}

	/***
	 * 根据商户编号查询商户秘钥信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantSecret getByMerchantNo(String merchantNo) {
		return merchantSecretBiz.getByMerchantNo(merchantNo);
	}
}
 
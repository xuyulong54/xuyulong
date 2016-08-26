package wusc.edu.pay.facade.user.service; 

import wusc.edu.pay.facade.user.entity.MerchantSecret;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

/**
 * 商户密钥.
 *@author: huangbin
 *@date： 日期：2014-1-6 时间：下午3:41:47
 *@version 1.0
 */
public interface MerchantSecretFacade {
	
	/***
	 * 保存商户秘钥信息
	 * @param secrt
	 * @return
	 */
	public long create(MerchantSecret secrt) throws UserBizException ;
	
	/***
	 * 根据商户编号查询商户秘钥信息
	 * @param merchantNo
	 * @return
	 */
	public MerchantSecret getByMerchantNo(String merchantNo) throws UserBizException ;
	
}
 
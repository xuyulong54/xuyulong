package wusc.edu.pay.facade.user.service;

import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;

public interface UserCacheFacade {

	
	/**
	 * 根据商户编号获取在线支付的商户信息.<br/>
	 * 先尝试从缓存中取，如果缓存中没有则从数据库中查询放存入缓存中并返回.<br/>
	 * 
	 * @param merchantNo
	 *            商户编号.
	 * @return MerchantOnline.
	 */
	MerchantOnline getMerchantOnlineByMerchantNoInCache(String merchantNo)  throws UserBizException;
	/**
	 * 根据用户编号从缓存中查找用户信息
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserInfo getUserInfoByUserNoInCache(String userNo) throws UserBizException;
	
}

package wusc.edu.pay.facade.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.constant.CacheConstant;
import wusc.edu.pay.common.utils.cache.redis.RedisUtils;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.core.user.dao.UserInfoDao;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserCacheFacade;



@Component("userCacheFacade")
public class UserCacheFacadeImpl implements UserCacheFacade{
	
	@Autowired
	private MerchantOnlineDao merchantOnlineDao;
	
	@Autowired
	private UserInfoDao userInfoDao; 

	/**
	 * 根据商户编号获取在线支付商户信息.<br/>
	 * 先尝试从缓存中取，如果缓存中没有则从数据库中查询放存入缓存中并返回.<br/>
	 * 
	 * @param merchantNo
	 *            商户编号.
	 * @return MerchantOnline.
	 */
	@Override
	public MerchantOnline getMerchantOnlineByMerchantNoInCache(String merchantNo)
			throws UserBizException {
		StringBuffer buffer = new StringBuffer(CacheConstant.MERCHANT_INFO);
		buffer.append(merchantNo);
		MerchantOnline entity = (MerchantOnline) RedisUtils.get(buffer.toString());
		if (entity == null) {
			entity = merchantOnlineDao.getByMerchantNo(merchantNo);
			if (entity == null) {
				return null;
			}
			RedisUtils.save(buffer.toString(), entity);
		}
		return entity;
	}

	/**
	 * 根据用户编号从缓存中查找用户信息
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	@Override
	public UserInfo getUserInfoByUserNoInCache(String userNo)
			throws UserBizException {
		StringBuffer buffer = new StringBuffer(CacheConstant.MERCHANT_INFO);
		buffer.append(userNo);
		UserInfo entity = (UserInfo) RedisUtils.get(buffer.toString());
		if (entity == null) {
			entity = userInfoDao.getUserInfoByUserNo(userNo);
			if (entity == null) {
				return null;
			}
			RedisUtils.save(buffer.toString(), entity);
		}
		return entity;
	}
}

package wusc.edu.pay.core.user.biz; 

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.user.dao.MerchantSecretDao;
import wusc.edu.pay.facade.user.entity.MerchantSecret;


/**
 *类描述：商户秘钥biz接口
 *@author: huangbin
 *@date： 日期：2014-1-6 时间：下午3:35:45
 *@version 1.0
 */
@Component("merchantSecretBiz")
public class MerchantSecretBiz {
	@Autowired
	private MerchantSecretDao merchantSecretDao;
	
	/***
	 * 保存商户秘钥信息
	 * @param secrt
	 * @return
	 */
	public long create(MerchantSecret secrt){
		return merchantSecretDao.insert(secrt);
	}
	
	/***
	 * 根据商户编号查询商户秘钥信息
	 * @param merchantNo 商户编号
	 * @return
	 */
	public MerchantSecret getByMerchantNo(String merchantNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		return merchantSecretDao.getBy(map);
	}
	
}
 
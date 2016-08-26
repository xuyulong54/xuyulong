package wusc.edu.pay.core.user.dao.impl; 

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.MerchantSecretDao;
import wusc.edu.pay.facade.user.entity.MerchantSecret;


/**
 *类描述：
 *@author: huangbin
 *@date： 日期：2014-1-6 时间：下午3:29:42
 *@version 1.0
 */
@Repository("merchantSecretDao")
public class MerchantSecretDaoImpl extends BaseDaoImpl<MerchantSecret> implements MerchantSecretDao{
	
}
 
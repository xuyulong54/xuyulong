/**
 * 功能描述：
 * @File : wusc.edu.pay.core.merchant.daoMerchantCaDao.java 
 * @date  2014-1-7下午2:06:24
 * Copyright (c) 2014, laichunhua@gzzyzz.com All Rights Reserved. 
 */
package wusc.edu.pay.core.user.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.user.entity.PortalCa;


/** 
 * ClassName: PortalCADao <br/> 
 * Function:  商户CA证书信息表<br/> 
 * date: 2014-1-7 下午2:06:24 <br/> 
 * @author laich
 */
public interface PortalCADao extends BaseDao<PortalCa>{

	/**
	 * listByUserIdAndType: 根据商户ID查询<br/> 
	 * @param userNo
	 * @return
	 */
	public List<PortalCa> listByUserIdAndType(String userNo,Long userId,Integer userType);

	public List<PortalCa> listByUserNo(String userNo);
}

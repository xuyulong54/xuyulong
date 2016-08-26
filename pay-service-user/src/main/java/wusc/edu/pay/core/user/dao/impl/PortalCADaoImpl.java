/**
 * 功能描述：
 * @File : wusc.edu.pay.core.merchant.dao.implMerchantCaDaoImpl.java 
 * @date  2014-1-7下午2:06:57
 * Copyright (c) 2014, laichunhua@gzzyzz.com All Rights Reserved. 
 */
package wusc.edu.pay.core.user.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.user.dao.PortalCADao;
import wusc.edu.pay.facade.user.entity.PortalCa;


/** 
 * ClassName: MerchantCaDaoImpl <br/> 
 * Function:  商户CA证书信息表<br/> 
 * date: 2014-1-7 下午2:06:57 <br/> 
 * @author laich
 */
@Repository("portalCADao")
public class PortalCADaoImpl extends BaseDaoImpl<PortalCa> implements PortalCADao {

	/**
	 * listByUserIdAndType: 根据用户ID查询，用户类型 <br/> 
	 * @param merchantId
	 * @return
	 */
	public List<PortalCa> listByUserIdAndType(String userNo,Long userId,Integer userType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userId", userId);
		map.put("userType", userType);
		map.put("userNo", userNo);
		return super.listBy(map);
	}

	@Override
	public List<PortalCa> listByUserNo(String userNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userNo", userNo);
		return super.listBy(map);
	}

	
}

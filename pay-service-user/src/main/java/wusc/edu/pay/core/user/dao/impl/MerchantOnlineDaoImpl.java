/**
 * wusc.edu.pay.facade.merchant.dao.impl.MerchantOnlineDaoImpl.java
 */
package wusc.edu.pay.core.user.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.user.dao.MerchantOnlineDao;
import wusc.edu.pay.facade.user.entity.MerchantOnline;


/**
 * @author System
 * 
 * @since 2014-05-27
 */
@Repository(value="merchantOnlineDao")
public class MerchantOnlineDaoImpl extends BaseDaoImpl<MerchantOnline> implements MerchantOnlineDao {

	/**
	 * 根据商户编号获取在线支付商户信息.
	 * @param merchantNo 商户编号.
	 * @return MerchantOnline.
	 */
	public MerchantOnline getByMerchantNo(String merchantNo){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("merchantNo", merchantNo);
		return super.getBy(map);
	}
	
	/**
	 * 根据身份证号获取在线支付商户信息.
	 * @param cardNo 身份证号（唯一）.
	 * @return MerchantOnline.
	 */
	public MerchantOnline getByCardNo(String cardNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("cardNo", cardNo);
		return super.getBy(map);
	}

	/***
	 * 根据商户全称查询商户信息
	 * @param fullName
	 * @return
	 */
	public MerchantOnline getByFullName(String fullName) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fullName", fullName);
		return super.getBy(map);
	}
	
	@Override
	public Map<String, Object> countMerchant(
			List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser) {
		List<String> blackList = new ArrayList<String>();
		for (Map<String, Object> map : listBlackUser) {
			blackList.add(map.get("USERNO").toString());
		}
		List<String> freezeList = new ArrayList<String>();
		for (Map<String, Object> map : listFreezeUser) {
			freezeList.add(map.get("USERNO").toString());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		if(freezeList.size()==0){
			map.put("sumFreezeList", freezeList);
		}else{
			map.put("sumFreezeList",
					super.getSqlSession().selectList(getStatement("sumFreezeList"),
							freezeList));
		}
		if(blackList.size()==0){
			map.put("sumBlackList", blackList); 
		}else{
			map.put("sumBlackList",
					super.getSqlSession().selectList(getStatement("sumBlackList"),
							blackList));
		}
		
		return map;
	}

	
	
	
	/***
	 * 根据营业执照号查询商户信息
	 * @param licenseNo
	 * @return
	 */
	public MerchantOnline getMerchantByLicenseNo(String licenseNo) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("licenseNo", licenseNo);
		return super.getBy(paramMap);
	}

	@Override
	public PageBean agentMerchantStautsSumm(PageParam pageParam, Map<String, Object> paramMap) {
		return listPage(pageParam, paramMap, "agentMerchantStautsSummReport");
	}
	
} 
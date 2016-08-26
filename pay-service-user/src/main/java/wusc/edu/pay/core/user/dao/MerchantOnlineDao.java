/**
 * wusc.edu.pay.facade.merchant.dao.MerchantOnlineDao.java
 */
package wusc.edu.pay.core.user.dao;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantOnline;


/**
 * 
 * @描述: 在线支付的商户信息表数据访问层接口.
 * @作者: WuShuicheng.
 * @创建: 2014-5-29,上午10:10:11
 * @版本: V1.0
 * 
 */
public interface MerchantOnlineDao extends BaseDao<MerchantOnline> {

	/**
	 * 根据商户编号获取在线支付商户信息.
	 * 
	 * @param merchantNo
	 *            商户编号.
	 * @return MerchantOnline.
	 */
	MerchantOnline getByMerchantNo(String merchantNo);

	/**
	 * 根据身份证号获取在线支付商户信息.
	 * 
	 * @param cardNo
	 *            身份证号（唯一）.
	 * @return MerchantOnline.
	 */
	MerchantOnline getByCardNo(String cardNo);

	/***
	 * 根据商户全称查询商户信息
	 * 
	 * @param fullName
	 * @return
	 */
	MerchantOnline getByFullName(String fullName);

	/**
	 * 查询黑名单和冻结的商户数量
	 * 
	 * @param listBlackUser
	 * @param listFreezeUser
	 * @return
	 */
	Map<String, Object> countMerchant(List<Map<String, Object>> listBlackUser, List<Map<String, Object>> listFreezeUser);

	/***
	 * 根据营业执照号查询商户信息
	 * 
	 * @param licenseNo
	 * @return
	 */
	MerchantOnline getMerchantByLicenseNo(String licenseNo);

	/**
	 * 代理商商户状态统计报表
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean agentMerchantStautsSumm(PageParam pageParam, Map<String, Object> paramMap);

}
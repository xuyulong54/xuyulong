package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.MerchantSignTypeEnum;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 
 * @描述: 在线支付的商户Dubbo服务接口.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-22,下午4:48:16 .
 * @版本: 1.0 .
 */
public interface MerchantOnlineFacade {

	// ///////////////// 在线支付商户信息 MerchantOnline ///////////////////////

	/**
	 * 根据商户编号查询商户信息.
	 * @param merchantNo 商户编号(也是用户编号).
	 * @return merchantOnline 在线支付商户信息.
	 */
	MerchantOnline getMerchantOnlineByMerchantNo(String merchantNo)  throws UserBizException;

	/**
	 * 根据身份证号查询商户信息.
	 * @param cardNo 身份证号（唯一）.
	 * @return merchantOnline 在线支付商户信息.
	 */
	MerchantOnline getMerchantOnlineByCardNo(String cardNo) throws UserBizException;

	/***
	 * 根据条件查询商户列表
	 * @param pageParam
	 * @param paramMap
	 * @return
	 * @throws UserBizException
	 */
	PageBean listMerchantListPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;
	
	/***
	 * 更新在线支付商户信息
	 * @param merchantOnline
	 */
	void update(MerchantOnline merchantOnline) throws UserBizException;
	
	/***
	 * 根据商户ID查询商户信息
	 * @param merchantId
	 * @return
	 */
	MerchantOnline getById(Long merchantId) throws UserBizException;


	/***
	 * 根据商户全称查询商户信息.
	 * @param fullName 商户全称.
	 * @return merchantOnline 在线支付商户信息.
	 */
	MerchantOnline getByFullName(String fullName) throws UserBizException;
	
	/**
	 * 查询商户数据
	 * @param paramMap
	 * @return
	 */
	List<MerchantOnline> listBy(Map<String, Object> paramMap) throws UserBizException;
	
	/**
	 * 查询黑名单和冻结的商户人数
	 * @param listBlackUser
	 * @param listFreezeUser
	 * @return
	 */
	Map<String, Object> countMerchant(List<Map<String, Object>> listBlackUser,
			List<Map<String, Object>> listFreezeUser) throws UserBizException;

	
	/***
	 * 根据营业执照号查询商户信息
	 * @param licenseNo
	 * @return
	 */
	MerchantOnline getMerchantByLicenseNo(String licenseNo);
	
	/**
	 * 代理商商户状态统计
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	PageBean agentMerchantStautsSumm(PageParam pageParam, Map<String, Object> paramMap);
	
	/**
	 * 修改商户签名类型
	 * @param merchantKey 商户MD5签名密钥
	 * @param merchantPublicKey 商户RSA签名公钥，目标签名类型为RSA是必传
	 * @param merchantNo 商户编号
	 * @return Map<String , Object> 返回平台RSA签名的公钥及私钥
	 */
	Map<String , Object> updateMerchantSignType(String merchantKey , String merchantPublicKey , String merchantNo);
	
	/**
	 * 商户验签
	 * @param sourceStr 签名原文
	 * @param signStr 签名密文
	 * @param merchantSignTypeEnum 签名类型
	 * @param merchantNo 商户编号
	 * @return
	 */
	boolean merchantVerify(String sourceStr , String signStr , MerchantSignTypeEnum merchantSignTypeEnum , String merchantNo);
	
	/**
	 * 商户签名
	 * @param sourceStr 签名原文
	 * @param merchantSignTypeEnum 签名类型
	 * @param merchantNo 商户编号
	 * @return
	 */
	String merchantSign(String sourceStr , MerchantSignTypeEnum merchantSignTypeEnum , String merchantNo);
}

package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantRole;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 商户角色对外接口
 * 
 * @author liliqiong
 * @date 2013-9-29
 * @version 1.0
 */
public interface MerchantRoleFacade {

	/**
	 * 查询所有角色
	 * 
	 * @return
	 */
	List<MerchantRole> listAllRole() throws UserBizException;

	/**
	 * 根据商户ID查找
	 * 
	 * @param merchantId
	 * @return
	 */
	List<MerchantRole> listRoleByUserNo(String userNo) throws UserBizException;

	/**
	 * 根据商户ID查找
	 * 
	 * @param merchantId
	 * @return
	 */
	MerchantRole getByUserNo(String userNo) throws UserBizException;

	void deleteById(long id) throws UserBizException;

	long create(MerchantRole entity) throws UserBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;

	long update(MerchantRole entity) throws UserBizException;

	MerchantRole getById(long id) throws UserBizException;
	
	/****
	 * 根据条件查询
	 * @param paramMap
	 * @return
	 */
	List<MerchantRole> listModelByCondition(Map<String, Object> paramMap) throws UserBizException;

}

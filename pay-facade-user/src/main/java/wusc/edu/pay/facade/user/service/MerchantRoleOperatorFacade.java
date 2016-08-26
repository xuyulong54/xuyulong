package wusc.edu.pay.facade.user.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.user.entity.MerchantRoleOperator;
import wusc.edu.pay.facade.user.exceptions.UserBizException;


/**
 * 角色与操作员关联对外接口
 * 
 * @author liliqiong
 * @date 2013-9-29
 * @version 1.0
 */
public interface MerchantRoleOperatorFacade {

	/**
	 * 根据操作员ID获得角色字符串
	 * 
	 * @param id
	 * @return
	 */
	String getRoleStrByOperatorId(Long id) throws UserBizException;

	long create(MerchantRoleOperator entity) throws UserBizException;

	PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) throws UserBizException;

	MerchantRoleOperator getById(long id) throws UserBizException;

	long update(MerchantRoleOperator entity) throws UserBizException;

	/**
	 * 根据角色ID查集合 getByRoleId: <br/>
	 * 
	 * @param roleId
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List listByRoleId(long roleId) throws UserBizException;
}

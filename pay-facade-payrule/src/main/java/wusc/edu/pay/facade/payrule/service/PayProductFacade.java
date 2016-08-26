package wusc.edu.pay.facade.payrule.service;

import java.util.List;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * ClassName: PayProductFacade 包括 PayProduct PayProductSwitch <br/>
 * Function: <br/>
 * date: 2014-6-26 下午6:10:35 <br/>
 * 
 * @author laich
 */
public interface PayProductFacade {

	/**
	 * 新增支付产品
	 * 
	 * @param payProductCode
	 * @param payProductName
	 * @param status
	 * @return
	 */
	public long createPayWayProduct(String payProductCode, String payProductName, Integer status) throws PayruleBizException;

	/**
	 * 分页查询支付产品
	 * 
	 * @param pageParam
	 * @param payProductCode
	 * @param payProductName
	 * @param status
	 * @return
	 */
	public PageBean queryPayProduct(PageParam pageParam, String payProductCode, String payProductName, Integer status)
			throws PayruleBizException;

	public PayProduct getPayProductById(Long id);

	/**
	 * 修改支付产品
	 * 
	 * @param id
	 * @param payProductCode
	 * @param payProductName
	 * @param status
	 */
	public void updatePayWayProduct(Long id, String payProductName, int status) throws PayruleBizException;

	public List<PayProductSwitch> findPayProductSwitchByRuleId(Long payRuleId);

	/***
	 * 查询所有有效的支付产品列表
	 * 
	 * @return
	 */
	public List<PayProduct> listAllProduct();

	/***
	 * 根据规则ID和产品编号查询支付产品开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	public PayProductSwitch getProductSwitchByRuleIdAndProductCode(Long ruleId, String productCode) throws PayruleBizException;;

	/***
	 * 支付规则取消绑定支付产品
	 * 
	 * @param productSwitch
	 */
	public void deteleProductSwitch(PayProductSwitch productSwitch) throws PayruleBizException;

	/***
	 * 查询该支付规则绑定了哪些支付产品
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> listByRuleId(Long ruleId);

	/**
	 * 根据支付产品编号查询支付产品
	 * 
	 * @param payProduct
	 * @return
	 */
	public PayProduct getPayProductByPayProductCode(String payProduct);

}

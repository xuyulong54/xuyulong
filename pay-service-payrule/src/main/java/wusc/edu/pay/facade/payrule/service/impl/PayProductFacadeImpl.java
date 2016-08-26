package wusc.edu.pay.facade.payrule.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.payrule.biz.PayWayProductBiz;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;


@Component("payProductFacade")
public class PayProductFacadeImpl implements PayProductFacade {

	@Autowired
	private PayWayProductBiz payWayProductBiz;

	public long createPayWayProduct(String payProductCode, String payProductName, Integer status) {
		return payWayProductBiz.createPayWayProduct(payProductCode, payProductName, status);
	}

	/**
	 * 分页查询支付产品
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryPayProduct(PageParam pageParam, String payProductCode, String payProductName, Integer status) {
		return payWayProductBiz.queryPayProduct(pageParam, payProductCode, payProductName, status);
	}

	/**
	 * 查出所有的支付产品
	 * 
	 * @return
	 */
	public List<PayProduct> findAllPayProduct() {
		return payWayProductBiz.findAllPayProduct();
	}

	public PayProduct getPayProductById(Long id) {
		return payWayProductBiz.getPayProductById(id);
	}

	public void updatePayWayProduct(Long id, String payProductName, int value) {
		payWayProductBiz.updatePayWayProduct(id, payProductName, value);
	}

	/**
	 * 根据规则 ID 查出 PayProductSwitch 集合
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> findPayProductSwitchByRuleId(Long payRuleId) {
		return payWayProductBiz.findPayProductSwitchByRuleId(payRuleId);
	}

	/***
	 * 查询所有有效的支付产品列表
	 * 
	 * @return
	 */
	public List<PayProduct> listAllProduct() {
		return payWayProductBiz.listAllProduct();
	}

	/***
	 * 根据规则ID和产品编号查询支付产品开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	public PayProductSwitch getProductSwitchByRuleIdAndProductCode(Long ruleId, String productCode) {
		return payWayProductBiz.getProductSwitchByRuleIdAndProductCode(ruleId, productCode);
	}

	/***
	 * 支付规则取消绑定支付产品
	 * 
	 * @param productSwitch
	 */
	public void deteleProductSwitch(PayProductSwitch productSwitch) {
		payWayProductBiz.deteleProductSwitch(productSwitch);
	}

	/***
	 * 查询该支付规则绑定了哪些支付产品
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> listByRuleId(Long ruleId) {
		return payWayProductBiz.listByRuleId(ruleId);
	}

	/**
	 * 根据支付产品编号查询支付产品
	 * 
	 * @param payProduct
	 * @return
	 */
	@Override
	public PayProduct getPayProductByPayProductCode(String payProductCode) {
		return payWayProductBiz.getPayProductByPayProductCode(payProductCode);
	}

}

package wusc.edu.pay.core.payrule.biz;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.payrule.dao.PayProductDao;
import wusc.edu.pay.core.payrule.dao.PayProductSwitchDao;
import wusc.edu.pay.core.payrule.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * ClassName: PayWayProductBiz 对应接口 PayWayProductFacade 包括 PayProduct PayProductSwitch操作 <br/>
 * Function: <br/>
 * date: 2014-6-27 上午9:39:26 <br/>
 * 
 * @author laich
 */
@Transactional(rollbackFor = Exception.class)
@Component("payWayProductBiz")
public class PayWayProductBiz {

	private static final Log log = LogFactory.getLog(PayRuleBiz.class);

	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;
	@Autowired
	private PayWaySwitchDao payWaySwitchDao; // 支付方式开关Dao

	/**
	 * 创建支付产品
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createPayWayProduct(String payProductCode, String payProductName, Integer status) {
		if (payProductDao.findByProductCode(payProductCode) != null) {
			throw new PayruleBizException(PayruleBizException.RULE_PRODUCTCODE_IS_EXIT, "该产品，支付编号已存在");
		}
		PayProduct entity = new PayProduct();
		entity.setPayProductCode(payProductCode);
		entity.setPayProductName(payProductName);
		entity.setStatus(status);
		log.info("新增支付产品 产品编号:" + payProductCode);
		return payProductDao.insert(entity);
	}

	/**
	 * 查出所有的支付产品
	 * 
	 * @return
	 */
	public List<PayProduct> findAllPayProduct() {
		return payProductDao.listBy(null);
	}

	/**
	 * 分页查询支付产品
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryPayProduct(PageParam pageParam, String payProductCode, String payProductName, Integer status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payProductName", payProductName);
		paramMap.put("payProductCode", payProductCode);
		paramMap.put("status", status);
		return payProductDao.listPage(pageParam, paramMap);
	}

	public PayProduct getPayProductById(Long id) {
		return payProductDao.getById(id);
	}

	/**
	 * 修改 PayProductSwitch 开关 同产品名字
	 * 
	 * @param id
	 * @param status
	 */
	public void updatePayWayProduct(Long id, String payProductName, int status) {
		PayProduct entity = payProductDao.getById(id);
		if (StringUtils.isNotEmpty(payProductName)) {
			entity.setPayProductName(payProductName);
		}
		if (StringUtils.isNotEmpty(status + "")) {
			entity.setStatus(status);
		}
		entity.setModifyTime(new Date());
		payProductDao.update(entity);
	}

	/**
	 * 根据规则 ID 查出 PayProductSwitch 集合
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> findPayProductSwitchByRuleId(Long payRuleId) {
		return payProductSwitchDao.findPayProductSwitchByRuleId(payRuleId);
	}

	/***
	 * 查询所有有效的支付产品列表
	 * 
	 * @return
	 */
	public List<PayProduct> listAllProduct() {
		return payProductDao.listAllProduct();
	}

	/***
	 * 根据规则ID和产品编号查询支付产品开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	public PayProductSwitch getProductSwitchByRuleIdAndProductCode(Long ruleId, String productCode) {
		return payProductSwitchDao.getProductSwitchByRuleIdAndProductCode(ruleId, productCode);
	}

	/***
	 * 支付规则取消绑定支付产品
	 * 
	 * @param productSwitch
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deteleProductSwitch(PayProductSwitch productSwitch) {
		// 首先删除支付产品开关表
		if (payProductSwitchDao.deleteById(productSwitch.getId()) > 0) {
			List<PayWaySwitch> payWaySwitchList = payWaySwitchDao.getByRuleIdAndProductCode(productSwitch.getPayRuleId(), productSwitch.getPayProductCode());
			// 循环删除支付方式开关表
			for (PayWaySwitch payWay : payWaySwitchList) {
				payWaySwitchDao.deleteById(payWay.getId());
			}
		}
	}

	/***
	 * 查询该支付规则绑定了哪些支付产品
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<PayProductSwitch> listByRuleId(Long ruleId) {
		return payProductSwitchDao.findPayProductSwitchByRuleId(ruleId);
	}

	/**
	 * 根据支付产品编号查询支付产品
	 * 
	 * @param payProduct
	 * @return
	 */
	public PayProduct getPayProductByPayProductCode(String payProductCode) {
		return payProductDao.findByProductCode(payProductCode);
	}
}

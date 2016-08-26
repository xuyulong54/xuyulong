package wusc.edu.pay.facade.payrule.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.payrule.biz.PayWayBiz;
import wusc.edu.pay.core.payrule.biz.PayWayProductBiz;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayBindingVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;


@Component("payWayFacade")
public class PayWayFacadeImpl implements PayWayFacade {

	@Autowired
	private PayWayBiz payWayBiz;
	@Autowired
	private PayWayProductBiz payWayProductBiz;

	public long createPayProductSwitch(PayProductSwitch entity) {
		return payWayBiz.createPayProductSwitch(entity);
	}

	/**
	 * 新增 支付产品 ,同时新增一条排序记录
	 * 
	 * @param entity
	 *            PayWay
	 * @param sort
	 *            排序可以为空
	 * @return
	 */
	public long createPayWay(String payWayCode, String payWayName, String payProductCode, String defaultPayInterface, Integer status,
			Integer sorts) {
		return payWayBiz.createPayWay(payWayCode, payWayName, payProductCode, defaultPayInterface, status, sorts);
	}

	public void updatePayWay(String payWayCode, String payWayName, long id, String defaultPayInterface, Integer status, Integer sorts) {
		payWayBiz.updatePayWay(payWayCode, payWayName, id, defaultPayInterface, status, sorts);
	}

	public void deletePayWay(Long id) {
		payWayBiz.deletePayWay(id);
	}

	public PayWay getPayWayById(Long id) {
		return payWayBiz.getPayWayById(id);
	}

	/**
	 * 修改 PayWaySwitch 开关
	 * 
	 * @param id
	 * @param status
	 */
	public void updatePayWaySwitch(Long id, Integer status) {
		payWayBiz.updatePayWaySwitch(id, status);
	}

	@Override
	public List<PayWay> findPayWayByPayProductCode(String payProductCode) {
		return payWayBiz.findPayWayByPayProductCode(payProductCode);
	}

	public PayWay getPayWayBypayWayCodeAndpayProductCode(String payWayCode, String payProductCode) {
		return payWayBiz.getPayWayBypayWayCodeAndpayProductCode(payWayCode, payProductCode);
	}

	/**
	 * 根据userNo 查出支付方式，同时去掉重复的数据 按支付产品最后修改时间
	 * 
	 * @param userNo
	 * @param busType
	 *            可为空，渠道业务类型
	 * @return
	 */
	public List<PayWayVo> findPayWayByUserNo(String userNo, String busType) {
		return payWayBiz.findPayWayByUserNo(userNo, busType);
	}

	/**
	 * 用于运营后 绑定支付方式 1,首先查出所有的FRP 列表，如果被这个支付产品使用则 在VO中isUse 设置为true
	 * 
	 * @param payProductCode
	 * @return
	 */
	public List<FrpSelectVo> queryFrpSelectVos(String payProductCode) {
		return payWayBiz.queryFrpSelectVos(payProductCode);
	}

	/***
	 * 根据支付规则ID查询支付产品 以及支付方式
	 * 
	 * @param ruleId
	 * @return
	 */
	public Map<String, List<PayWayBindingVo>> findPayWaySwitchByRuleId(Long ruleId) {
		// 查询该支付规则下绑定了多少个支付产品
		List<PayProductSwitch> payProductSwitchList = payWayProductBiz.listByRuleId(ruleId);
		Map<String, List<PayWayBindingVo>> payWayBindingMap = new HashMap<String, List<PayWayBindingVo>>();
		for (int i = 0; i < payProductSwitchList.size(); i++) {
			List<PayWayBindingVo> payWayBindingList = new ArrayList<PayWayBindingVo>();
			String productCode = payProductSwitchList.get(i).getPayProductCode(); // 支付产品编号
			// 查询支付产品下所有的支付方式
			List<PayWay> payWayList = payWayBiz.findPayWayByPayProductCode(productCode);
			for (PayWay model : payWayList) { // 循环支付方式，是否被规则选中
				String payWayCode = model.getPayWayCode();
				PayWayBindingVo payWayBindingVo = new PayWayBindingVo();
				payWayBindingVo.setPayProductCode(model.getPayProductCode()); // 支付产品编号
				payWayBindingVo.setPayRuleId(ruleId); // 支付规则ID
				payWayBindingVo.setPayWayCode(payWayCode); // 支付方式编号

				// 根据支付规则编号，支付产品编号，支付方式编号查询支付方式开关表 是否存在
				PayWaySwitch payWaySwitch = payWayBiz.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, productCode, payWayCode);
				if (payWaySwitch != null) {
					payWayBindingVo.setUse(true);
				} else {
					payWayBindingVo.setUse(false);
				}
				// 把封装好的VO添加进List
				payWayBindingList.add(payWayBindingVo);
			}
			payWayBindingMap.put(productCode, payWayBindingList);
		}
		return payWayBindingMap;
	}

	@Override
	public PageBean queryPayWay(PageParam pageParam, Map<String, Object> paramMap) {
		return payWayBiz.queryPayWay(pageParam, paramMap);
	}

	/***
	 * 根据条件查询支付方式开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 */
	public PayWaySwitch getPayWaySwitchByRuleIdProductCodepayWayCode(Long ruleId, String productCode, String payWayCode) {
		return payWayBiz.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, productCode, payWayCode);
	}

	/***
	 * 根据ID删除数据
	 * 
	 * @param payWaySwitchId
	 * @return
	 */
	public long deletePayWaySwitch(Long payWaySwitchId) {
		return payWayBiz.deletePayWaySwitch(payWaySwitchId);
	}

	/***
	 * 查询支付规则下绑定的支付产品和支付方式
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> findBindSwitchList(Long ruleId) {
		return payWayBiz.findBindSwitchList(ruleId);
	}

	/***
	 * 支付规则绑定支付产品，同时绑定支付方式
	 * 
	 * @param payWayCodes
	 * @return
	 */
	public long createPayWaySwitchAndPayProductSwitch(String payWayCodes) throws PayruleBizException {
		return payWayBiz.createPayWaySwitchAndPayProductSwitch(payWayCodes);
	}

	/***
	 * 批量删除支付规则下的支付产品和支付方式
	 * 
	 * @param payWayCodes
	 * @return
	 * @throws BossBizException
	 */
	public int deletePayWaySwitchAndPayProductSwitch(String payWayCodes) {
		return payWayBiz.deletePayWaySwitchAndPayProductSwitch(payWayCodes);
	}

	/***
	 * 新增支付规则下的支付产品和支付方式
	 * 
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 * @throws BossBizException
	 */
	public long createPayWaySwitch(Long ruleId, String productCode, String payWayCode) {
		return payWayBiz.createPayWaySwitch(ruleId, productCode, payWayCode);
	}

	/**
	 * 查询所有支付方式
	 */
	@Override
	public List<PayWay> listAllPayWay() {
		return payWayBiz.listAllPayWay();
	}

	/**
	 * 银行直连时使用 根据 用户编号与支付方式编号查询
	 * 
	 * @param merchantNo
	 * @param payWayCode
	 * @return
	 */
	public PayWay getPayWayBypayWayCode_merchantNo_payWayCode(String merchantNo, String payWayCode) {
		return payWayBiz.getPayWayBypayWayCode_merchantNo_payWayCode(merchantNo, payWayCode);
	}

}

package wusc.edu.pay.core.payrule.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import wusc.edu.pay.common.core.dao.BaseDaoImpl;
import wusc.edu.pay.core.payrule.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;


/**
 * ClassName: PayWaySwitchDaoImpl <br/>
 * Function: <br/>
 * date: 2014-6-27 上午9:24:49 <br/>
 * 
 * @author laich
 */
@Repository(value = "payWaySwitchDao")
public class PayWaySwitchDaoImpl extends BaseDaoImpl<PayWaySwitch> implements PayWaySwitchDao {

	/***
	 * 根据支付规则ID和支付产品编号查询支付方式列表
	 * 
	 * @param payRuleId
	 * @param payProductCode
	 * @return
	 */
	public List<PayWaySwitch> getByRuleIdAndProductCode(Long payRuleId, String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payRuleId", payRuleId);
		paramMap.put("payProductCode", payProductCode);
		return super.listBy(paramMap);
	}

	/***
	 * 根据支付规则编号，支付产品编号，支付方式编号查询支付方式开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 */
	public PayWaySwitch getPayWaySwitchByRuleIdProductCodepayWayCode(Long ruleId, String productCode, String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payRuleId", ruleId);
		paramMap.put("payProductCode", productCode);
		paramMap.put("payWayCode", payWayCode);
		return super.getBy(paramMap);
	}

	/***
	 * 查询支付规则下绑定的支付产品和支付方式
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> findBindSwitchList(Long ruleId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleId", ruleId);
		return super.getSessionTemplate().selectList(this.getStatement("findBindSwitchList"), paramMap);
	}

	/***
	 * 批量删除支付规则开关表
	 * 
	 * @param ruleId
	 * @param payProductCode
	 */
	public void deleteByRuleIdAndPayProductCode(Long ruleId, String payProductCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ruleId", ruleId);
		paramMap.put("payProductCode", payProductCode);
		super.getSessionTemplate().delete(this.getStatement("deleteByRuleIdAndPayProductCode"), paramMap);
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
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", userNo);
		paramMap.put("busType", busType);
		return super.getSessionTemplate().selectList(this.getStatement("findPayWayByUserNo"), paramMap);
	}

	public PayWayVo getPayWayBypayWayCode_merchantNo_payWayCode(String merchantNo, String payWayCode) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("userNo", merchantNo);
		paramMap.put("payWayCode", payWayCode);
		List<PayWayVo> payWayVoList = super.getSessionTemplate().selectList(this.getStatement("getPayWayByUserNoAndPayWayCode"), paramMap);
		if (payWayVoList != null && payWayVoList.size() > 0) {
			return payWayVoList.get(0);
		}
		return null;

	}
}

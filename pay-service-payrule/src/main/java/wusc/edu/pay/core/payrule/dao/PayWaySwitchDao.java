package wusc.edu.pay.core.payrule.dao;

import java.util.List;

import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;


public interface PayWaySwitchDao extends BaseDao<PayWaySwitch>{

	/***
	 * 根据支付规则ID和支付产品编号查询支付方式列表
	 * @param payRuleId
	 * @param payProductCode
	 * @return
	 */
	List<PayWaySwitch> getByRuleIdAndProductCode(Long payRuleId, String payProductCode);

	/***
	 * 根据支付规则编号，支付产品编号，支付方式编号查询支付方式开关表
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 */
	PayWaySwitch getPayWaySwitchByRuleIdProductCodepayWayCode(Long ruleId, String productCode, String payWayCode);

	/***
	 * 查询支付规则下绑定的支付产品和支付方式
	 * @param ruleId
	 * @return
	 */
	List<BindSwitchVo> findBindSwitchList(Long ruleId);

	/***
	 * 批量删除支付规则开关表
	 * @param ruleId
	 * @param payProductCode
	 */
	void deleteByRuleIdAndPayProductCode(Long ruleId, String payProductCode);
	
	/**
	 * 根据userNo 查出支付方式，同时去掉重复的数据 按支付产品最后修改时间
	 * @param userNo
	 * @param busType 可为空，渠道业务类型
	 * @return
	 */
	List<PayWayVo> findPayWayByUserNo(String userNo ,String busType);

	/**
	 * @param merchantNo
	 * @param payWayCode
	 * @return 
	 */
	PayWayVo getPayWayBypayWayCode_merchantNo_payWayCode(String merchantNo, String payWayCode);
}

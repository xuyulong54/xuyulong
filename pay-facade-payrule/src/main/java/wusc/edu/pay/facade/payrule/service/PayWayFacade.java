package wusc.edu.pay.facade.payrule.service;

import java.util.List;
import java.util.Map;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayBindingVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * ClassName: PayWayFacade 包括 PayWay PayProductSwitch PayWayPauseNotice PayWaySwitch 操作 <br/>
 * Function: <br/>
 * date: 2014-6-26 下午6:10:35 <br/>
 * 
 * @author laich
 */
public interface PayWayFacade {

	/***
	 * 新增支付产品开关，同时生成 PayWaySwitch 支付方式开关
	 * 
	 * @param ruleId
	 *            支付规则ID
	 * @param payProductCode
	 *            支付产品编号
	 */
	public long createPayProductSwitch(PayProductSwitch entity) throws PayruleBizException;

	/**
	 * 新增 支付产品 ,同时新增一条排序记录
	 * 
	 * @param payWayCode
	 * @param payWayName
	 * @param payProductCode
	 * @param defaultPayInterface
	 * @param status
	 * @param sorts
	 * @return
	 * @throws PayruleBizException
	 */
	public long createPayWay(String payWayCode, String payWayName, String payProductCode, String defaultPayInterface, Integer status, Integer sorts) throws PayruleBizException;

	/**
	 * 修改支付方式
	 * 
	 * @param payWayCode
	 * @param payWayName
	 * @param id
	 * @param defaultPayInterface
	 * @param status
	 * @param sorts
	 * @throws PayruleBizException
	 */
	public void updatePayWay(String payWayCode, String payWayName, long id, String defaultPayInterface, Integer status, Integer sorts) throws PayruleBizException;

	/**
	 * 删除支付方式
	 * 
	 * @param id
	 *            支付方式 ID
	 * @param payWayCode
	 *            支付方式编号
	 */
	public void deletePayWay(Long id) throws PayruleBizException;

	public PayWay getPayWayById(Long id);

	/**
	 * 修改 PayWaySwitch 开关
	 * 
	 * @param id
	 * @param status
	 */
	public void updatePayWaySwitch(Long id, Integer status) throws PayruleBizException;

	public PayWay getPayWayBypayWayCodeAndpayProductCode(String payWayCode, String payProductCode) throws PayruleBizException;;

	/**
	 * 根据userNo 查出支付方式，同时去掉重复的数据 按支付产品最后修改时间
	 * @param userNo
	 * @param busType 可为空，渠道业务类型
	 * @return
	 */
	public List<PayWayVo> findPayWayByUserNo(String userNo,String busType) throws PayruleBizException; ;

	/**
	 * 用于运营后 绑定支付方式  1,首先查出所有的FRP 列表，如果被这个支付产品使用则 在VO中isUse 设置为true
	 * @param payProductCode
	 * @return
	 */
	public List<FrpSelectVo> queryFrpSelectVos(String payProductCode) throws PayruleBizException;

	public List<PayWay> findPayWayByPayProductCode(String payProductCode) throws PayruleBizException;;

	/***
	 * 根据支付规则ID查询支付产品 以及支付方式
	 * 
	 * @param ruleId
	 * @return
	 */
	public Map<String, List<PayWayBindingVo>> findPayWaySwitchByRuleId(Long ruleId) throws PayruleBizException;;

	/**
	 * 分页查询
	 * 
	 * @param pageParam
	 * @param paramMap
	 * @return
	 */
	public PageBean queryPayWay(PageParam pageParam, Map<String, Object> paramMap);

	/***
	 * 根据条件查询支付方式开关表
	 * 
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 */
	public PayWaySwitch getPayWaySwitchByRuleIdProductCodepayWayCode(Long ruleId, String productCode, String payWayCode);

	/***
	 * 根据ID删除数据
	 * 
	 * @param payWaySwitchId
	 * @return
	 */
	public long deletePayWaySwitch(Long payWaySwitchId) throws PayruleBizException;

	/***
	 * 查询支付规则下绑定的支付产品和支付方式
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> findBindSwitchList(Long ruleId);

	/***
	 * 批量新增支付规则绑定支付产品，同时绑定支付方式
	 * 
	 * @param payWayCodes
	 * @return
	 */
	public long createPayWaySwitchAndPayProductSwitch(String payWayCodes) throws PayruleBizException;

	/***
	 * 批量删除支付规则下的支付产品和支付方式
	 * 
	 * @param payWayCodes
	 * @return
	 * @throws PayruleBizException
	 */
	public int deletePayWaySwitchAndPayProductSwitch(String payWayCodes) throws PayruleBizException;

	/**
	 * 查询所有支付方式
	 * 
	 * @return
	 */
	public List<PayWay> listAllPayWay();
	/***
	 * 新增支付规则下的支付产品和支付方式
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 * @throws PayruleBizException
	 */
	public long createPayWaySwitch(Long ruleId, String productCode, String payWayCode) throws PayruleBizException;

	/**
	 * 银行直连时使用 根据 用户编号与支付方式编号查询
	 * @param merchantNo
	 * @param payWayCode
	 * @return 
	 */
	public PayWay getPayWayBypayWayCode_merchantNo_payWayCode(String merchantNo, String payWayCode);

}

package wusc.edu.pay.core.payrule.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.payrule.dao.FrpDao;
import wusc.edu.pay.core.payrule.dao.PayProductDao;
import wusc.edu.pay.core.payrule.dao.PayProductSwitchDao;
import wusc.edu.pay.core.payrule.dao.PayWayDao;
import wusc.edu.pay.core.payrule.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayProductSwitch;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.entity.PayWaySwitch;
import wusc.edu.pay.facade.payrule.entity.vo.BindSwitchVo;
import wusc.edu.pay.facade.payrule.entity.vo.FrpSelectVo;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.enums.PayProductStatusEnum;
import wusc.edu.pay.facade.payrule.enums.PayTypeEnum;
import wusc.edu.pay.facade.payrule.enums.SwitchStatusEnum;
import wusc.edu.pay.facade.payrule.exceptions.PayruleBizException;


/**
 * ClassName: PayWayBiz 对应接口： PayWayFacade 包括 PayWay PayWaySort
 * PayWayPauseNotice PayProductSwitch PayWaySwitch 操作 <br/>
 * Function: <br/>
 * date: 2014-6-27 上午9:37:54 <br/>
 * 
 * @author laich
 */
@Transactional(rollbackFor = Exception.class)
@Component("payWayBiz")
public class PayWayBiz {

	private static final Log log = LogFactory.getLog(PayRuleBiz.class);

	@Autowired
	private PayWayDao payWayDao;
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;
	@Autowired
	private PayWaySwitchDao payWaySwitchDao;
	@Autowired
	private PayProductDao payProductDao;
	@Autowired
	private FrpDao frpDao;

	/**
	 * 新增支付产品开关，同时生成 PayWaySwitch 支付方式开关
	 * 
	 * @param entity
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createPayProductSwitch(PayProductSwitch entity) {
		if (null == entity || null == entity.getPayRuleId()) {// 该产品，支付规则不存在
			throw new PayruleBizException(PayruleBizException.RULE_RULE_IS_NULL, "该产品，支付规则不存在");
		}
		long payProductId = payProductSwitchDao.insert(entity);
		// 找出该支付产品下的支付方式
		List<PayWay> payWays = payWayDao.findPayWayByPayProductCode(entity.getPayProductCode());
		for (Iterator<PayWay> iterator = payWays.iterator(); iterator.hasNext();) {
			PayWay payWay = (PayWay) iterator.next();
			PayWaySwitch payWaySwitch = new PayWaySwitch();
			payWaySwitch.setPayProductCode(entity.getPayProductCode());
			payWaySwitch.setPayRuleId(entity.getPayRuleId());
			payWaySwitch.setPayWayCode(payWay.getPayWayCode());
			payWaySwitch.setStatus(SwitchStatusEnum.ON.getValue());// 默认是开的
			payWaySwitchDao.insert(payWaySwitch);
		}
		return payProductId;
	}

	/**
	 * 新增 支付方式，如果支付产品已被规则使用，则不能新增该支付产品的支付方式
	 * 
	 * @param entity
	 *            PayWay
	 * @param sort
	 *            排序可以为空
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createPayWay(String payWayCode, String payWayName, String payProductCode, String defaultPayInterface, Integer status,
			Integer sorts) {
		// 暂时注释支付产品只能绑定一个支付方式的限制
		// List<PayWay> list = payWayDao.findByPayWayCode(payWayCode);
		// if(list!=null && !list.isEmpty()){
		// log.info("支付产品编号:payProductCode ="+payProductCode
		// +" 不能创建支付方式编号："+payWayCode);
		// throw new
		// RuleBizException(RuleBizException.RULE_PAYWAYCODE_PAYWAY_IS_EXIT,"该支付方式已被使用，不能加入该产品");
		// }
		PayWay entity = new PayWay();
		entity.setPayWayCode(payWayCode);
		entity.setPayProductCode(payProductCode);
		entity.setPayWayName(payWayName);
		entity.setDefaultPayInterface(defaultPayInterface);
		entity.setStatus(PayProductStatusEnum.ACTIVITY.getValue());
		entity.setSorts(sorts);
		return payWayDao.insert(entity);
	}

	/**
	 * 删除支付方式 同时删除 排序中的数据
	 * 
	 * @param id
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void deletePayWay(Long id) {
		log.info("删除支付方式ID=" + id);
		payWayDao.deleteById(id);
	}

	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updatePayWay(String payWayCode, String payWayName, Long id, String defaultPayInterface, Integer status, Integer sorts) {
		PayWay entity = payWayDao.getById(id);
		entity.setPayWayName(payWayName);
		entity.setDefaultPayInterface(defaultPayInterface);
		entity.setSorts(sorts);
		entity.setModifyTime(new Date());
		payWayDao.update(entity);
	}

	public PayWay getPayWayById(Long id) {
		return payWayDao.getById(id);
	}

	/**
	 * 修改 PayWaySwitch 开关
	 * 
	 * @param id
	 * @param status
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void updatePayWaySwitch(Long id, Integer status) {
		PayWaySwitch entity = payWaySwitchDao.getById(id);
		entity.setStatus(status);
		payWaySwitchDao.update(entity);
	}

	public List<PayWay> findPayWayByPayProductCode(String payProductCode) {
		return payWayDao.findPayWayByPayProductCode(payProductCode);
	}

	public PayWay getPayWayBypayWayCodeAndpayProductCode(String payWayCode, String payProductCode) {
		PayWay payWay = payWayDao.getPayWayBypayWayCodeAndpayProductCode(payWayCode, payProductCode);
		if (payWay == null) {
			throw new PayruleBizException(PayruleBizException.RULE_PAYWAYCODE_IS_NOT_EXIT, "该支付方式不存在");
		}
		return payWay;
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
		List<PayWayVo> vos = payWaySwitchDao.findPayWayByUserNo(userNo, busType);

		Map<String, PayWayVo> map = new HashMap<String, PayWayVo>();
		List<PayWayVo> payWayVos = new ArrayList<PayWayVo>();
		// 去重复
		for (Iterator<PayWayVo> iterator = vos.iterator(); iterator.hasNext();) {
			PayWayVo vo = (PayWayVo) iterator.next();
			if (map.get(vo.getBankCode()) == null) {
				map.put(vo.getBankCode(), vo);
				// payWayVos.add(vo);
			}
		}

		// 排序

		// 四大行
		// key->bankCode
		// value->desc
		Map<String, String> topFour = BankCode.getTopFourMap();
		for (Object obj : topFour.keySet()) {
			String bankCode = (String) obj;
			if (map.containsKey(bankCode)) {
				payWayVos.add(map.get(bankCode));
				map.remove(bankCode);
			}
		}

		// 常用行
		Map<String, String> common = BankCode.getCommonMap();
		for (Object obj : common.keySet()) {
			String bankCode = (String) obj;
			if (map.containsKey(bankCode)) {
				payWayVos.add(map.get(bankCode));
				map.remove(bankCode);
			}
		}

		// 剩下行
		for (Object obj : map.keySet()) {
			payWayVos.add(map.get(obj));
		}

		return payWayVos;
	}

	public List<FrpSelectVo> findBindPayWayByPayProductCode(String payProductCode) {
		return payWayDao.findBindPayWayByPayProductCode(payProductCode);

	}

	/***
	 * 查询该支付产品所绑定的支付规则
	 * 
	 * @param ruleId
	 * @param productCode
	 * @return
	 */
	public List<PayWaySwitch> getByRuleIdAndProductCode(Long ruleId, String productCode) {
		return payWaySwitchDao.getByRuleIdAndProductCode(ruleId, productCode);
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
		return payWaySwitchDao.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, productCode, payWayCode);
	}

	public PageBean queryPayWay(PageParam pageParam, Map<String, Object> paramMap) {
		return payWayDao.listPage(pageParam, paramMap);
	}

	/***
	 * 根据ID删除数据
	 * 
	 * @param payWaySwitchId
	 * @return
	 */
	public long deletePayWaySwitch(Long payWaySwitchId) {
		return payWaySwitchDao.deleteById(payWaySwitchId);
	}

	/***
	 * 查询支付规则下绑定的支付产品和支付方式 return new ArrayList<T>(new LinkedHashSet<T>(list));
	 * 
	 * @param ruleId
	 * @return
	 */
	public List<BindSwitchVo> findBindSwitchList(Long ruleId) {
		// 查询所有的支付产品列表
		List<PayProduct> payProductList = payProductDao.listAllProduct();
		List<BindSwitchVo> returnSwitchList = new ArrayList<BindSwitchVo>();
		// 查询该支付角色绑定的支付产品
		for (PayProduct payProduct : payProductList) {
			BindSwitchVo bindSwitchVo = new BindSwitchVo();
			String payProductCode = payProduct.getPayProductCode(); // 支付产品编号
			String payProductName = payProduct.getPayProductName(); // 支付产品名称
			bindSwitchVo.setPayProductCode(payProductCode);
			bindSwitchVo.setPayProductName(payProductName);

			// 查询该支付产品是否被选中
			PayProductSwitch payProductSwitch = payProductSwitchDao.getProductSwitchByRuleIdAndProductCode(ruleId, payProductCode);
			// 判断支付产品是否被选中
			if (payProductSwitch != null) {
				bindSwitchVo.setIsSelectPayProductCode(true);
			} else {
				bindSwitchVo.setIsSelectPayProductCode(false);
			}
			// 查询支付产品下所有的支付方式
			List<PayWay> payWayList = payWayDao.findPayWayByPayProductCode(payProductCode);
			List<BindSwitchVo> bindSwitchVoList = new ArrayList<BindSwitchVo>();
			for (PayWay payWay : payWayList) {
				BindSwitchVo payWayBindVo = new BindSwitchVo();
				// 查询该支付方式是否被选中
				String payWayCode = payWay.getPayWayCode(); // 支付方式编号
				payWayBindVo.setPayWayCode(payWayCode);
				PayWaySwitch payWaySwitch = payWaySwitchDao
						.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, payProductCode, payWayCode);
				if (payWaySwitch != null) {
					payWayBindVo.setIsSelectPayWayCode(true);
				} else {
					payWayBindVo.setIsSelectPayWayCode(false);
				}
				bindSwitchVoList.add(payWayBindVo);
			}
			bindSwitchVo.setPayWayCodeList(bindSwitchVoList);
			returnSwitchList.add(bindSwitchVo);
		}
		return returnSwitchList;
	}

	/***
	 * 支付规则绑定支付产品，同时绑定支付方式
	 * 
	 * @param payWayArray
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createPayWaySwitchAndPayProductSwitch(String payWayCodes) {
		int num = payWayCodes.indexOf("#");
		Long ruleId = 0L;
		String payProductCode = "";
		if (num > 0) { // 选中多个支付方式
			String[] payWayArray = payWayCodes.split("#");
			for (int i = 0; i < payWayArray.length; i++) {
				String payWayTemp = payWayArray[i];
				String[] payWayTempArray = payWayTemp.split("-");
				ruleId = Long.valueOf(payWayTempArray[0]);
				payProductCode = payWayTempArray[1];
				String payWayCode = payWayTempArray[2];
				// 查询该数据是否在支付规则开关表中
				PayWaySwitch payWaySwitch = payWaySwitchDao
						.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, payProductCode, payWayCode);
				if (payWaySwitch == null) {
					payWaySwitch = new PayWaySwitch();
					payWaySwitch.setPayProductCode(payProductCode);
					payWaySwitch.setPayWayCode(payWayCode);
					payWaySwitch.setPayRuleId(ruleId);
					payWaySwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());

					payWaySwitchDao.insert(payWaySwitch);
				}
			}
		} else { // 选中一个支付方式
			String[] payCodeTemp = payWayCodes.split("-");
			ruleId = Long.valueOf(payCodeTemp[0]);
			payProductCode = payCodeTemp[1];
			String payWayCode = payCodeTemp[2];

			// 查询该数据是否在支付规则开关表中
			PayWaySwitch payWaySwitch = payWaySwitchDao.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, payProductCode, payWayCode);
			if (payWaySwitch == null) {
				payWaySwitch = new PayWaySwitch();
				payWaySwitch.setPayProductCode(payProductCode);
				payWaySwitch.setPayWayCode(payWayCode);
				payWaySwitch.setPayRuleId(ruleId);
				payWaySwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());

				payWaySwitchDao.insert(payWaySwitch);
			}
		}

		PayProductSwitch payProductSwitch = payProductSwitchDao.getProductSwitchByRuleIdAndProductCode(ruleId, payProductCode);
		if (payProductSwitch == null) {
			payProductSwitch = new PayProductSwitch();
			payProductSwitch.setPayProductCode(payProductCode);
			payProductSwitch.setPayRuleId(ruleId);
			payProductSwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());
			payProductSwitchDao.insert(payProductSwitch);
		}
		return 1;
	}

	/***
	 * 批量删除支付规则下的支付产品和支付方式
	 * 
	 * @param payWayCodes
	 * @return
	 * @throws PayruleBizException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public int deletePayWaySwitchAndPayProductSwitch(String payWayCodes) {
		String[] payCodeTemp = payWayCodes.split("-");
		Long ruleId = Long.valueOf(payCodeTemp[0]);
		String payProductCode = payCodeTemp[1];
		payWaySwitchDao.deleteByRuleIdAndPayProductCode(ruleId, payProductCode);

		// 新增支付规则与支付产品的绑定表
		PayProductSwitch payProductSwitch = payProductSwitchDao.getProductSwitchByRuleIdAndProductCode(ruleId, payProductCode);
		Long rowNum = payProductSwitchDao.deleteById(payProductSwitch.getId());

		return rowNum.intValue();
	}

	/***
	 * 新增支付规则下的支付产品和支付方式
	 * 
	 * @param ruleId
	 * @param productCode
	 * @param payWayCode
	 * @return
	 * @throws PayruleBizException
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public long createPayWaySwitch(Long ruleId, String productCode, String payWayCode) {
		// 查询该数据是否在支付规则开关表中
		PayWaySwitch payWaySwitch = payWaySwitchDao.getPayWaySwitchByRuleIdProductCodepayWayCode(ruleId, productCode, payWayCode);
		if (payWaySwitch != null) {
			throw new PayruleBizException(-1, "已经绑定了该支付方式，无需重复绑定！");
		}
		payWaySwitch = new PayWaySwitch();
		payWaySwitch.setPayProductCode(productCode);
		payWaySwitch.setPayWayCode(payWayCode);
		payWaySwitch.setPayRuleId(ruleId);
		payWaySwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());

		long payWayId = payWaySwitchDao.insert(payWaySwitch);

		// 新增支付产品开关表
		PayProductSwitch payProductSwitch = payProductSwitchDao.getProductSwitchByRuleIdAndProductCode(ruleId, productCode);
		if (payProductSwitch == null) {
			payProductSwitch = new PayProductSwitch();
			payProductSwitch.setPayProductCode(productCode);
			payProductSwitch.setPayRuleId(ruleId);
			payProductSwitch.setStatus(PayProductStatusEnum.ACTIVITY.getValue());
			payProductSwitchDao.insert(payProductSwitch);
		}
		return payWayId;
	}

	/**
	 * 查询所有支付方式
	 * 
	 * @return
	 */
	public List<PayWay> listAllPayWay() {
		return payWayDao.listAllPayWay();
	}

	/**
	 * 用于运营后 绑定支付方式 1,首先查出所有的FRP 列表，如果被这个支付产品使用则 在VO中isUse 设置为true
	 * 
	 * @param payProductCode
	 * @return
	 */
	public List<FrpSelectVo> queryFrpSelectVos(String payProductCode) {
		List<FrpSelectVo> frpSelectVos = this.findBindPayWayByPayProductCode(payProductCode);
		List<PayWay> payWayTypeNoCard = this.findPayWayByPayProductCode(payProductCode);
		List<Frp> frps = frpDao.listByPayType(PayTypeEnum.NO_CARD.getValue());
		for (Iterator<Frp> iterator = frps.iterator(); iterator.hasNext();) {
			Frp frp = (Frp) iterator.next();
			boolean isAdd = true;
			FrpSelectVo vo = new FrpSelectVo();
			vo.setBankCode(frp.getBankCode());
			vo.setFrpCode(frp.getFrpCode());
			vo.setPayType(frp.getPayType());
			vo.setBankName(frp.getBankName());
			vo.setStatus(frp.getStatus());
			EXIT: for (PayWay pw : payWayTypeNoCard) {
				if (pw.getPayWayCode().equals(frp.getFrpCode())) {
					vo.setIsUse(true);
					vo.setPayWayId(pw.getId());
					vo.setPayProductCode(pw.getPayProductCode());
					isAdd = false;
					frpSelectVos.add(vo);
					break EXIT;
				}
			}
			if (isAdd) {
				frpSelectVos.add(vo);
			}
		}
		return frpSelectVos;
	}

	/**
	 * @param merchantNo
	 * @param payWayCode
	 * @return
	 */
	public PayWay getPayWayBypayWayCode_merchantNo_payWayCode(String merchantNo, String payWayCode) {
		PayWayVo vo = payWaySwitchDao.getPayWayBypayWayCode_merchantNo_payWayCode(merchantNo, payWayCode);
		if (vo == null) {
			return null;
		} else {
			PayWay payWay = new PayWay();
			payWay.setPayProductCode(vo.getPayProductCode());
			payWay.setPayWayCode(vo.getPayWayCode());
			payWay.setPayWayName(vo.getPayWayName());
			payWay.setDefaultPayInterface(vo.getDefaultPayInterface());
			return payWay;
		}
	}
}

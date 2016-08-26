/**
 * wusc.edu.pay.core.limit.biz.SwitchLimitBiz.java
 */
package wusc.edu.pay.core.limit.biz;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao;
import wusc.edu.pay.core.limit.dao.MerchantCustomPayInterfaceDao;
import wusc.edu.pay.core.limit.dao.PayProductSwitchDao;
import wusc.edu.pay.core.limit.dao.PayWaySwitchDao;
import wusc.edu.pay.core.limit.dao.TradeLimitRouterDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.entity.MerchantCustomPayInterface;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.exptions.SwitchLimitException;


/**
 * 
 * <ul>
 * <li>Title:限制开关业务类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-9
 */
@Component("switchLimitBiz")
public class SwitchLimitBiz {

	// 业务功能
	@Autowired
	private BizFunctionSwitchDao bizFunctionSwitchDao;

	// 支付产品
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;

	// 支付方式
	@Autowired
	private PayWaySwitchDao payWaySwitchDao;

	// 交易限制
	@Autowired
	private TradeLimitRouterDao tradeLimitRouterDao;
	
	// 支付接口
	@Autowired
	private MerchantCustomPayInterfaceDao merchantCustomPayInterfaceDao;

	/**
	 * 判断商户是否开通业务功能
	 * 
	 * @param merchantNo
	 *            商户号
	 * @param bizFunction
	 *            业务功能
	 * @return 是否限制
	 */
	public boolean isBizFunctionAvailable(String merchantNo, String bizFunction) {
		// 根据商户号获取开关限制包ID
		Long switchLimitPackId = getSwitchLimitPackId(merchantNo);

		// 判断商户业务功能是否被限制
		BizFunctionSwitch bizFunctionSwitch = bizFunctionSwitchDao.getBizFunctionSwitchByPackIdAndBizFunction(switchLimitPackId, bizFunction);

		return (bizFunctionSwitch!=null);

	}

	/**
	 * 判断商户是否开通否支付产品
	 * 
	 * @param merchantNo
	 *            商户号
	 * @param bizFunction
	 *            业务功能
	 * @param payProduct
	 *            支付产品
	 * @return 是否限制
	 */
	public boolean isPayProductAvailable(String merchantNo, String bizFunction, String payProduct) {

		// 根据商户号获取开关限制包ID
		Long switchLimitPackId = getSwitchLimitPackId(merchantNo);

		// 根据开关限制包ID获取已开通支付产品列表
		PayProductSwitch payProductSwitch = payProductSwitchDao.getPayProductByPackIdAndProduct(switchLimitPackId, payProduct);

		return (payProductSwitch == null);
	}

	/**
	 * 判断商户是否开通某支付方式
	 * 
	 * @param merchantNo
	 *            商户号
	 * 
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @return 是否开通
	 */
	public boolean isPayWayAvailable(String merchantNo, String payProduct, String payWay) {
		// 根据商户号获取开关限制包ID
		Long switchLimitPackId = getSwitchLimitPackId(merchantNo);

		// 根据开关限制包ID获取已开通支付方式列表
		PayWaySwitch payWaySwitch = payWaySwitchDao.getPayWaySwitchByPackIdAndProductAndWay(switchLimitPackId, payProduct, payWay);

		return (payWaySwitch == null);
	}

	/**
	 * 根据商户号获取开关限制包ID
	 */
	private Long getSwitchLimitPackId(String merchantNo) {
		TradeLimitRouter tradeLimitRouterEntity = tradeLimitRouterDao.getTradeLimitRouter(merchantNo);
		if (tradeLimitRouterEntity == null) {
			throw new SwitchLimitException(SwitchLimitException.TRADE_LIMIT_NOT_EXIST, "商户未设置交易限制[merchantNo=" + merchantNo + "]");
		}

		Long switchLimitPackId = tradeLimitRouterEntity.getSwitchLimitPackId();
		if (switchLimitPackId == null) {
			throw new SwitchLimitException(SwitchLimitException.MERCHANT_SWITCH_LIMIT_NOT_EXIST, "商户未设置开关限制[merchantNo=" + merchantNo + ";tradeLimitRouterId=" + tradeLimitRouterEntity.getId() + "]");
		}

		return switchLimitPackId;
	}

	/**
	 * 判断商户是否设置某支付接口限制
	 * 
	 * @param merchantNo
	 *            商户号
	 * 
	 * @param payProduct
	 *            支付产品
	 * @param payWay
	 *            支付方式
	 * @return 
	 */
	public boolean isPayInterfaceAvailable(String merchantNo, String payWay,
			String payInterface) {
		// 根据商户号获取开关限制包ID
		MerchantCustomPayInterface merchantCustomPayInterface = merchantCustomPayInterfaceDao.getByMerchantNoAndWayAndInterface(merchantNo, payWay, payInterface);


		return !(merchantCustomPayInterface == null);
	}
	
	/**
	 * 该商户是否开通该业务
	 * @param merchantNo 商户编号
	 * @param limitTrxTypeEnum 业务功能
	 * @return true为开通该业务，false为没开通
	 * @throws SwitchLimitException
	 */
	public boolean isBizFunctionAvailable(String merchantNo,
			LimitTrxTypeEnum limitTrxTypeEnum) {
		//根据商户编号找到该商户所有的业务
		List<BizFunctionSwitch> bizFunctionSwitchList = bizFunctionSwitchDao.getBizFunctionSwitchByMerchantNo(merchantNo);
		boolean flag = false;
		//循环遍历，把传过来的limitTrxTypeEnum 和业务对比，看是否开通该业务。
		for (int i = 0; i < bizFunctionSwitchList.size(); i++) {
			BizFunctionSwitch bizFunctionSwitch = bizFunctionSwitchList.get(i);
			if (limitTrxTypeEnum.toString().equals(bizFunctionSwitch.getBizFunction())) {
				flag = true;
				break;
			}
		}	
		return flag;
	}

}

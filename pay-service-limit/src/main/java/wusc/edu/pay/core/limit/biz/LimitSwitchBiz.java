package wusc.edu.pay.core.limit.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.core.limit.dao.BizFunctionSwitchDao;
import wusc.edu.pay.core.limit.dao.LimitSwitchDao;
import wusc.edu.pay.core.limit.dao.PayProductSwitchDao;
import wusc.edu.pay.core.limit.dao.PayWaySwitchDao;
import wusc.edu.pay.facade.limit.entity.BizFunctionSwitch;
import wusc.edu.pay.facade.limit.entity.LimitSwitch;
import wusc.edu.pay.facade.limit.entity.PayProductSwitch;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;


/**
 * 限制开关属性展现业务biz
 * 
 * @author：zh
 */
@Component(value = "limitSwitchBiz")
public class LimitSwitchBiz {

	@Autowired
	private LimitSwitchDao limitSwitchDao;
	@Autowired
	private PayProductSwitchDao payProductSwitchDao;
	@Autowired
	private PayWaySwitchDao payWaySwitchDao;
	@Autowired
	private BizFunctionSwitchDao bizFunctionSwitchDao;
	
	/***
	 * 查询支付包下的业务功能
	 * 
	 * @param switchPackId
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LimitSwitch> findBizFunctionList(Long switchPackId){
		//获取所有的业务功能
		List list = LimitTrxTypeEnum.toList();
		List<LimitSwitch> listLimitSwitch = new ArrayList<LimitSwitch>();
		for(Object object : list){
			LimitSwitch bizFunction = new LimitSwitch();
			Map<String, String> map = (Map<String, String>)object; 
			String code = map.get("value");
			String name = map.get("desc");
			bizFunction.setBizFunctionCode(code);
			bizFunction.setBizFunctionName(name);
			
			//查询该业务功能是否被选中
			BizFunctionSwitch bizFunctionSwitch = bizFunctionSwitchDao.getBizFunctionSwitchByPackIdAndBizFunction(switchPackId, code);
			if (bizFunctionSwitch != null) {
				bizFunction.setIsSelectBizFunctionCode(true);
			} else {
				bizFunction.setIsSelectBizFunctionCode(false);
			}
			
			listLimitSwitch.add(bizFunction);
		}
		
		return listLimitSwitch;
	}

	/***
	 * 查询支付包下的支付产品和支付方式
	 * 
	 * @param switchPackId
	 * @return
	 */
	public List<LimitSwitch> findLimitSwitchList(Long switchPackId){
		// 查询所有的支付产品列表
		List<LimitSwitch> payProductList = limitSwitchDao.listAllProduct();
		
		for(LimitSwitch payProduct : payProductList){
			
			// 查询该支付产品是否被选中
			PayProductSwitch payProductSwitch = payProductSwitchDao.
					getPayProductByPackIdAndProduct(switchPackId, payProduct.getPayProductCode());
			// 判断支付产品是否被选中
			if (payProductSwitch != null) {
				payProduct.setIsSelectPayProductCode(true);
			} else {
				payProduct.setIsSelectPayProductCode(false);
			}
			
			// 查询支付产品下所有的支付方式
			List<LimitSwitch> payWayList = limitSwitchDao.findPayWayByPayProductCode(payProduct.getPayProductCode());
			for(LimitSwitch payWay : payWayList){
				// 查询该支付方式是否被选中
				PayWaySwitch payWaySwitch = payWaySwitchDao.
						getPayWaySwitchByPackIdAndProductAndWay(switchPackId, payProduct.getPayProductCode(), payWay.getPayWayCode());
				if (payWaySwitch != null) {
					payWay.setIsSelectPayWayCode(true);
				} else {
					payWay.setIsSelectPayWayCode(false);
				}
			}
			payProduct.setPayWayCodeList(payWayList);
		}
		
		return payProductList;
	}
	
	
	

}

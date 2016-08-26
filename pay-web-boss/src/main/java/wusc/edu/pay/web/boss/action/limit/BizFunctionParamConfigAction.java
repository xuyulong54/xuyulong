package wusc.edu.pay.web.boss.action.limit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.TrxTypeEnum;
import wusc.edu.pay.facade.limit.entity.BizFunctionParamConfig;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.enums.SwitchLimitStatusEnum;
import wusc.edu.pay.facade.limit.service.BizFunctionParamConfigFacade;
import wusc.edu.pay.facade.limit.service.SwitchLimitPackFacade;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * @描述: 业务功能参数配置ACTION.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-9, 下午9:43:41 .
 * @版本: V1.0 .
 */
public class BizFunctionParamConfigAction extends BossBaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private SwitchLimitPackFacade switchLimitPackFacade;
	@Autowired
	private PayProductFacade payProductFacade;
	@Autowired
	private FrpFacade frpFacade;
	@Autowired
	private BizFunctionParamConfigFacade bizFunctionParamConfigFacade;
	
	/**
	 * 查询业务功能参数配置
	 * @return
	 */
	public String listBizFunctionParamConfig(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		super.pageBean = bizFunctionParamConfigFacade.listPage(getPageParam(), paramMap);
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		this.putData("SwitchLimitStatusEnumList", SwitchLimitStatusEnum.toList());
		this.putData("trxTypeEnumList", TrxTypeEnum.toListForMerchantBusType());		
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);		
		return "bizFunctionParamConfigList";
	}
	
	/**
	 * 添加业务功能参数配置界面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String addBizFunctionParamConfigUI(){
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		this.putData("SwitchLimitStatusEnumList", SwitchLimitStatusEnum.toList());
		List<PayProduct> payProductList = payProductFacade.listAllProduct();
		this.putData("trxTypeEnumList", TrxTypeEnum.toListForMerchantBusType());		
		this.putData("payProductList", payProductList);
		List<Frp> frpList = frpFacade.listAll();
		this.putData("frpList", frpList);
		return "bizFunctionParamConfigAdd";
	}	
	
	/**
	 * 添加业务功能参数配置
	 * @return
	 */
	public String addBizFunctionParamConfig(){		
		BizFunctionParamConfig bizFunctionParamConfig = new BizFunctionParamConfig(); 
//		Long switchLimitPackId = getLong("switchLimitPackId");
//		String bizFunction = getString("bizFunction");
//		String payProduct = getString("payProduct");
//		String status = getString("status");
//		String payWay = getString("payWay");
		
		bizFunctionParamConfigFacade.saveBizFunctionParamConfig(bizFunctionParamConfig);
		this.logSave("添加业务功能参数配置：id="+bizFunctionParamConfig.getId());
		return this.operateSuccess("添加成功！");		
	}
	
	/**
	 * 修改业务功能参数配置界面
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String editBizFunctionParamConfigUI(){
		Long id = getLong("id");
		BizFunctionParamConfig BizFunctionParamConfig = bizFunctionParamConfigFacade.getById(id);
		this.pushData(BizFunctionParamConfig);
		List<Frp> frpList = frpFacade.listAll();
		this.putData("frpList", frpList);
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		this.putData("SwitchLimitStatusEnumList", SwitchLimitStatusEnum.toList());
		List<PayProduct> payProductList = payProductFacade.listAllProduct();
		this.putData("payProductList", payProductList);
		this.putData("trxTypeEnumList", TrxTypeEnum.toListForMerchantBusType());		
		return "BizFunctionParamConfigEdit";
	}
	/**
	 * 修改业务功能参数配置
	 * @return
	 */
	public String editBizFunctionParamConfig(){
		Long id = getLong("id");
		BizFunctionParamConfig bizFunctionParamConfig = bizFunctionParamConfigFacade.getById(id);
//		Long switchLimitPackId = getLong("switchLimitPackId");
//		String bizFunction = getString("bizFunction");
//		String payProduct = getString("payProduct");
//		String status = getString("status");
//		String payWay = getString("payWay");
		
		
		bizFunctionParamConfigFacade.updateBizFunctionParamConfig(bizFunctionParamConfig);	
		this.logEdit("修改业务功能参数配置：id="+bizFunctionParamConfig.getId());
		return this.operateSuccess("修改成功！");		
	}
	
	/**
	 * 删除业务功能参数配置
	 */
	public void deleteBizFunctionParamConfig(){
		try {
			Long id = getLong("id");
			bizFunctionParamConfigFacade.deleteBizFunctionParamConfig(id);
			this.getOutputMsg().put("STATE", "SUCCESS");
		} catch (Exception e) {
			this.getOutputMsg().put("STATE", "ERROR");
		}
		this.outPrint(this.getHttpResponse(),
				JSONObject.fromObject(this.getOutputMsg()));
		
	}

	
}

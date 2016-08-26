package wusc.edu.pay.web.boss.action.limit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.entity.PayWaySwitch;
import wusc.edu.pay.facade.limit.entity.SwitchLimitPack;
import wusc.edu.pay.facade.limit.entity.TradeLimitRouter;
import wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade;
import wusc.edu.pay.facade.limit.service.SwitchLimitPackFacade;
import wusc.edu.pay.facade.limit.service.TradeLimitRouterFacade;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 
 * @描述: 商户关联开关限制和额度限制的ACTION类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-10, 下午8:26:37 .
 * @版本: V1.0 .
 */
public class TradeLimitRouterAction extends BossBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private SwitchLimitPackFacade switchLimitPackFacade;
	@Autowired
	private TradeLimitRouterFacade tradeLimitRouterFacade;
	@Autowired
	private AmountLimitManagementFacade amountLimitManagementFacade;

	/**
	 * 支付产品
	 */
	@Autowired
	private PayProductFacade payProductFacade;

	/**
	 * 查询商户关联开关限制和额度限制
	 * 
	 * @return
	 */
	public String listTradeLimitRouter() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Long switchLimitPackId = getLong("switchLimitPackId");
		Long amountLimitPackId = getLong("amountLimitPackId");
		String merchantNo = getString("merchantNo");
		paramMap.put("switchLimitPackId", switchLimitPackId);
		paramMap.put("amountLimitPackId", amountLimitPackId);
		paramMap.put("merchantNo", merchantNo);
		super.pageBean = tradeLimitRouterFacade.listPage(getPageParam(), paramMap);
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		List<AmountLimitPack> amountLimitPackList = amountLimitManagementFacade.queryAmountLimitPackAll();
		this.putData("amountLimitPackList", amountLimitPackList);
		this.pushData(paramMap); // 回显查询条件
		this.pushData(pageBean);
		return "tradeLimitRouterList";
	}

	/**
	 * 添加商户关联开关限制和额度限制界面
	 * 
	 * @return
	 */
	public String addTradeLimitRouterUI() {
		Long id = getLong("id");
		String merchantNo = getString("merchantNo");
		if (id != null) {
			TradeLimitRouter TradeLimitRouter = tradeLimitRouterFacade.getById(id);
			this.pushData(TradeLimitRouter);
		}
		
		if(merchantNo !=null){
			TradeLimitRouter TradeLimitRouter = tradeLimitRouterFacade.getTradeLimitRouterByMerchantNo(merchantNo);
			this.pushData(TradeLimitRouter);
		}
		
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		List<AmountLimitPack> amountLimitPackList = amountLimitManagementFacade.queryAmountLimitPackAll();
		this.putData("amountLimitPackList", amountLimitPackList);
		this.putData("merchantNo",merchantNo==null?"":merchantNo);
		return "tradeLimitRouterAdd";
	}

	/**
	 * 添加商户关联开关限制和额度限制
	 * 
	 * @return
	 */
	public String addTradeLimitRouter() {
		TradeLimitRouter tradeLimitRouter  = new TradeLimitRouter();
		Long switchLimitPackId = getLong("switchLimitPackId");
		Long amountLimitPackId = getLong("amountLimitPackId");
		String merchantNo = getString("merchantNo");
		
		if(switchLimitPackId==null){
			return this.operateError("开关限制包不能为空！");
		}
		
		
		// 判断是否已经存在
		tradeLimitRouter = tradeLimitRouterFacade.getTradeLimitRouterByMerchantNo(merchantNo);

		if (tradeLimitRouter == null) {
			tradeLimitRouter = new TradeLimitRouter();
			tradeLimitRouter.setAmountLimitPackId(amountLimitPackId);
			tradeLimitRouter.setMerchantNo(merchantNo);
			tradeLimitRouter.setSwitchLimitPackId(switchLimitPackId);
			tradeLimitRouterFacade.saveTradeLimitRouter(tradeLimitRouter);
			
			this.logSave("添加商户限制成功，商户编号为：" + getString("merchantNo") + 
					"开关限制ID为：" + (getString("switchLimitPackId")==null?"未设置":getString("switchLimitPackId")) + 
					"金额限制ID为："+ (getString("amountLimitPackId")==null?"未设置":getString("amountLimitPackId")));
		} else {
			tradeLimitRouter.setAmountLimitPackId(amountLimitPackId);
			tradeLimitRouter.setSwitchLimitPackId(switchLimitPackId);
			tradeLimitRouterFacade.updateTradeLimitRouter(tradeLimitRouter);
			this.logEdit("修改商户限制成功，商户编号为：" + getString("merchantNo") + 
					"开关限制ID为：" + (getString("switchLimitPackId")==null?"未设置":getString("switchLimitPackId")) + 
					"金额限制ID为："+ (getString("amountLimitPackId")==null?"未设置":getString("amountLimitPackId")));
		}

		return this.operateSuccess("保存成功！");
	}

	/**
	 * 修改商户关联开关限制和额度限制界面
	 * 
	 * @return
	 */
	public String editTradeLimitRouterUI() {
		Long id = getLong("id");
		TradeLimitRouter TradeLimitRouter = tradeLimitRouterFacade.getById(id);
		this.pushData(TradeLimitRouter);
		List<SwitchLimitPack> switchLimitPacksList = switchLimitPackFacade.querySwitchLimitPackList();
		this.putData("switchLimitPacksList", switchLimitPacksList);
		List<AmountLimitPack> amountLimitPackList = amountLimitManagementFacade.queryAmountLimitPackAll();
		this.putData("amountLimitPackList", amountLimitPackList);
		return "tradeLimitRouterEdit";
	}

	/**
	 * 修改商户关联开关限制和额度限制
	 * 
	 * @return
	 */
	public String editTradeLimitRouter() {
		Long id = getLong("id");
		TradeLimitRouter tradeLimitRouter = tradeLimitRouterFacade.getById(id);
		Long switchLimitPackId = getLong("switchLimitPackId");
		Long amountLimitPackId = getLong("amountLimitPackId");
		String merchantNo = getString("merchantNo");
		tradeLimitRouter.setAmountLimitPackId(amountLimitPackId);
		tradeLimitRouter.setMerchantNo(merchantNo);
		tradeLimitRouter.setSwitchLimitPackId(switchLimitPackId);
		tradeLimitRouterFacade.updateTradeLimitRouter(tradeLimitRouter);
		this.logEdit("修改商户限制成功，商户编号为：" + getString("merchantNo") + 
				"开关限制ID为：" + (getString("switchLimitPackId")==null?"未设置":getString("switchLimitPackId")) + 
				"金额限制ID为："+ (getString("amountLimitPackId")==null?"未设置":getString("amountLimitPackId")));
		return this.operateSuccess("修改成功！");
	}

	/**
	 * 删除商户关联开关限制和额度限制
	 */
	public void deleteTradeLimitRouter() {
		try {
			Long id = getLong("id");
			tradeLimitRouterFacade.deleteTradeLimitRouter(id);
			this.logDelete("删除商户限制成功，商户编号为：" + getString("merchantNo") + 
					"开关限制ID为：" + (getString("switchLimitPackId")==null?"未设置":getString("switchLimitPackId")) + 
					"金额限制ID为："+ (getString("amountLimitPackId")==null?"未设置":getString("amountLimitPackId")));
			this.getOutputMsg().put("STATE", "SUCCESS");
		} catch (Exception e) {
			this.logDeleteError("删除商户限制成功，商户编号为：" + getString("merchantNo") + 
					"开关限制ID为：" + (getString("switchLimitPackId")==null?"未设置":getString("switchLimitPackId")) + 
					"金额限制ID为："+ (getString("amountLimitPackId")==null?"未设置":getString("amountLimitPackId")));
			this.getOutputMsg().put("STATE", "ERROR");
		}
		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(this.getOutputMsg()));

	}

	/**
	 * 根据开关限制包加载数据 1 业务方式 2 支付产品 3 支付方式
	 */
	@SuppressWarnings("unchecked")
	public void queryDataBySwitchLimitPackId() {

		Long switchLimitPackId = getLong("switchLimitPackId");

		Map<String, Object> resultMap = tradeLimitRouterFacade.queryDataBySwitchLimitPackId(switchLimitPackId);

		List<PayProduct> payProductList = payProductFacade.listAllProduct();

		List<PayProduct> payWayAndProductList = new ArrayList<PayProduct>();

		List<PayWaySwitch> payWayList = (List<PayWaySwitch>) resultMap.get("PayWayList");

		if (payProductList != null && payWayList != null) {

			for (int i = 0; i < payProductList.size(); i++) {

				for (int j = 0; j < payWayList.size(); j++) {
					if (payProductList.get(i).getPayProductCode().equals(payWayList.get(j).getPayProduct())) {

						payWayAndProductList.add(payProductList.get(i));
					}
				}
			}
		}

		resultMap.put("PayWayAndProductList", removeDuplicateWithOrder(payWayAndProductList));
		// 加载所有支付产品
		resultMap.put("PayProductAllList", payProductList);

		this.getOutputMsg().put("resultData", resultMap);

		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg(), this.getDefaultJsonConfig()));
	}

	/**
	 * 去除重复的List
	 * 
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List removeDuplicateWithOrder(List list) {

		Set set = new HashSet();
		List newList = new ArrayList();
		if (list != null) {
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				Object element = iter.next();
				if (set.add(element))
					newList.add(element);
			}
		}
		return newList;
	}

	/**
	 * 根据金额限制包加载数据
	 */
	public void queryDataByAmountLimitPackId() {

		Long amountLimitPackId = getLong("amountLimitPackId");

		List<AmountLimit> amountLimitList = amountLimitManagementFacade.queryAmountLimit(amountLimitPackId);

		List<PayProduct> payProductList = payProductFacade.listAllProduct();

		// 将支付产品编号，转换成支付产品名称
		if (amountLimitList != null) {
			
			for (AmountLimit limit : amountLimitList) {
				
				for (int i = 0; i < payProductList.size(); i++) {

					if (payProductList.get(i).getPayProductCode().equals(limit.getPayProduct())) {

						limit.setPayProduct(payProductList.get(i).getPayProductName());
					} 
				}
			}
		}

		this.getOutputMsg().put("AmountLimitList", amountLimitList);

		this.outPrint(this.getHttpResponse(), JSONObject.fromObject(getOutputMsg(), this.getDefaultJsonConfig()));

	}

}

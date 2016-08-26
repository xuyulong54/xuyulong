package wusc.edu.pay.web.boss.action.rule;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.enums.PayProductStatusEnum;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * ClassName: PayProductAction 支付产品 <br/>
 * Function: <br/>
 * date: 2014-6-27 下午2:07:03 <br/>
 * 
 * @author laich
 */
public class PayProductAction extends BossBaseAction {

	//private static final Log log = LogFactory.getLog(PayProductAction.class);
	private static final long serialVersionUID = -6389861250826167276L;
	@Autowired
	private PayProductFacade payProductFacede;

	@Permission("payrule:product:view")
	public String listPayProduct() {
		super.pageBean = payProductFacede.queryPayProduct(getPageParam(), getString("payProductCode"), getString("payProductName"), null);
		this.pushData(pageBean);
		// 回显查询条件
		this.putData("payProductCode", getString("payProductCode"));
		this.putData("payProductName", getString("payProductName"));
		this.putData("PayProductStatusEnum", PayProductStatusEnum.toMap());
		return "listPayProduct";
	}

	// 进入添加支付产品页面
	@Permission("payrule:product:add")
	public String addView() {
		this.putData("statusList", PayProductStatusEnum.values());
		return "addView";
	}

	// 进入修改页面要列出已添加的支付方式
	// 进入添加支付产品页面
	@Permission("payrule:product:edit")
	public String editView() {
		if (StringUtils.isEmpty(getString("id"))) {
			return super.operateError("参数为空!");
		}
		Long id = Long.parseLong(getString("id"));
		PayProduct payProduct = payProductFacede.getPayProductById(id);
		this.putData("statusList", PayProductStatusEnum.values());
		super.pushData(payProduct);
		return "editView";
	}

	// 保存支付产品
	public String savePayProduct() {
		
		String payProductCode = getString("payProductCode");
		String payProductName = getString("payProductName");
		if (StringUtils.isEmpty(payProductCode) || StringUtils.isEmpty(payProductName)) {
			return super.operateError("参数为空!");
		}
		PayProduct payProduct  = payProductFacede.getPayProductByPayProductCode(payProductCode);
		if(payProduct != null){
			return super.operateError("支付产品编号已存在!");
		}
		payProductFacede.createPayWayProduct(payProductCode, payProductName, PayProductStatusEnum.ACTIVITY.getValue());
		this.logSave("添加支付产品，产品名称："+payProductName);
		return super.operateSuccess();
	}

	// 保存编辑后的支付产品
	public String editPayProduct() {
		String payProductName = getString("payProductName");
		if (StringUtils.isEmpty(getString("id")) || StringUtils.isEmpty(payProductName)) {
			return super.operateError("参数为空!");
		}
		Long id = Long.parseLong(getString("id"));
		Integer status = getInteger("status");
		payProductFacede.updatePayWayProduct(id, payProductName, status);
		this.logEdit("修改支付产品，产品名称："+payProductName);
		return super.operateSuccess();
	}

	// 修改支付产中开关状态
	public String updateStatus() {
		Long id = Long.parseLong(getString("id"));
		String status_str = getString("status");
		Integer status = 0;
		if (status_str.equals(PayProductStatusEnum.ACTIVITY.getValue() + "")) {
			status = (PayProductStatusEnum.INACTIVITY.getValue());
		} else {
			status = (PayProductStatusEnum.ACTIVITY.getValue());
		}
		payProductFacede.updatePayWayProduct(id, null, status);
		this.logEdit("修改支付产中开关状态，产品ID："+id);
		return super.operateSuccess();
	}

	/**
	 * 支付产品查找带回
	 * 
	 * @return
	 */
	public String payProductLookupList() {
		super.pageBean = payProductFacede.queryPayProduct(getPageParam(), getString("payProductCode"), getString("payProductName"), null);
		this.pushData(pageBean);
		// 回显查询条件
		this.putData("payProductCode", getString("payProductCode"));
		this.putData("payProductName", getString("payProductName"));
		this.putData("PayProductStatusEnum", PayProductStatusEnum.toMap());
		return "listPayProductForLookup";
	}
}

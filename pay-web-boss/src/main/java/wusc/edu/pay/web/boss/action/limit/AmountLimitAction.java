/**
 * wusc.edu.pay.web.boss.action.limit.AmountLimitAction.java
 */
package wusc.edu.pay.web.boss.action.limit;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.limit.entity.AmountLimit;
import wusc.edu.pay.facade.limit.entity.AmountLimitPack;
import wusc.edu.pay.facade.limit.enums.CardKindEnum;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.enums.LimitTypeEnum;
import wusc.edu.pay.facade.limit.exceptions.LimitBizException;
import wusc.edu.pay.facade.limit.service.AmountLimitManagementFacade;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 
 * <ul>
 * <li>Title:金额限制控制器</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-7-10
 */
public class AmountLimitAction extends BossBaseAction implements ModelDriven<AmountLimit> {
	private static final Log log = LogFactory.getLog(AmountLimitAction.class);
	private static final long serialVersionUID = 4214036968949639449L;

	/**
	 * 金额限制
	 */
	@Autowired
	private AmountLimitManagementFacade amountLimitManagementFacade;

	/**
	 * 支付产品
	 */
	@Autowired
	private PayProductFacade payProductFacade;

	/**
	 * 支付方式
	 */
	@Autowired
	private PayWayFacade payWayFacade;

	/**
	 * 支付方式
	 */
	@Autowired
	private FrpFacade frpFacade;

	/**
	 * 金额限制
	 */
	private AmountLimit amountLimit = new AmountLimit();

	/**
	 * 查询金额限制列表
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String listAmountLimit() {
		super.pageBean = this.amountLimitManagementFacade.queryAmountLimitPage(getPageParam(), getParamMap_Utf8());
		pushData(pageBean);
		pushData(getParamMap_Utf8());

		// 金额限制包
		List<AmountLimitPack> amountLimitPackList = amountLimitManagementFacade.queryAmountLimitPackAll();
		this.putData("AmountLimitPackList", amountLimitPackList);

		// 业务功能
		this.putData("TrxTypeEnums", LimitTrxTypeEnum.toList());

		// 支付产品
		List<PayProduct> payProductList = payProductFacade.listAllProduct();
		this.putData("PayProductList", payProductList);

		// 支付方式
		List payWayList = frpFacade.listAll();
		this.putData("PayWayList", payWayList);

		// 支付卡种
		this.putData("CardTypeEnums", CardKindEnum.values());

		// 限制类型
		this.putData("LimitTypeEnums", LimitTypeEnum.values());
		return "amountLimitList";
	}

	/**
	 * 新增或者修改金额限制操作
	 */
	@SuppressWarnings("static-access")
	public String addOrEditAmountLimit() {
		try {
			String payProduct = getString("payProduct");
			String payWay = getString("payWay");
			if (payProduct == null && payWay != null) {
				return this.operateError("必须选择相应的支付产品");
			};
			String minAmount = getString("minAmount");
			String maxAmount = getString("maxAmount");
			String limitType = getString("limitType");
			
			ValidateUtils validateUtils = new ValidateUtils();
			
			if (!(validateUtils.isNumeric(maxAmount)||validateUtils.isDouble(maxAmount))) {
				return this.operateError("限额只能输入数字");
			}
			 
			//只有限制是单笔时才验证最小金额
			if (limitType.equals(LimitTypeEnum.SINGLE.toString())) {
				double min = Double.parseDouble(minAmount);
				double max = Double.parseDouble(maxAmount);
				if (min>max) {
					return this.operateError("最小限额不能大于最大限额");
				}				
				else if (!(validateUtils.isNumeric(minAmount)||validateUtils.isDouble(minAmount))) {
					return this.operateError("限额只能输入数字");
				}
			}
	
			if (getModel().getId() != null){
				AmountLimit entity = amountLimitManagementFacade.getAmountLimitById(getModel().getId());
				AmountLimit amountLimit = getModel();
				amountLimit.setVersion(entity.getVersion());
				this.amountLimitManagementFacade.updateAmountLimit(amountLimit);
				this.logEdit("更新金额限制，id="+getModel().getAmountLimitPackId());
			}
			else{
				this.amountLimitManagementFacade.addAmountLimit(getModel());
				this.logSave("增加金额限制，id="+getModel().getAmountLimitPackId());
			}
				
		} catch (LimitBizException e) {
			this.logSaveError("增加金额限制错误，id="+getModel().getAmountLimitPackId());
			return this.operateError("操作出现错误");
		}
		return this.operateSuccess();
	}

	/**
	 * 跳转到金额新增修改页面
	 * 
	 * @return
	 */
	public String toEditAmountLimit() {
		Long id = getLong("id");
		Long amountLimitPackId = getLong("amountLimitPackId");
		this.putData("amountLimitPackId", amountLimitPackId);
		if (id != null) {
			AmountLimit amountLimit = amountLimitManagementFacade.getAmountLimitById(id);
			this.pushData(amountLimit);
			String payProduct = amountLimit.getPayProduct();
			if (!("" .equals(payProduct) || payProduct == null)) {
				// 支付方式
				List<PayWay> payWayList = payWayFacade.findPayWayByPayProductCode(payProduct);
				this.putData("PayWayList", payWayList);
			}
			
		}

		// 金额限制包
		List<AmountLimitPack> amountLimitPackList = amountLimitManagementFacade.queryAmountLimitPackAll();
		this.putData("AmountLimitPackList", amountLimitPackList);

		// 业务功能
		this.putData("TrxTypeEnums", LimitTrxTypeEnum.toListForAmountLimit());
		this.putData("LimitTrxTypeEnum", LimitTrxTypeEnum.toMap());

		// 支付产品
		List<PayProduct> payProductList = payProductFacade.listAllProduct();
		this.putData("PayProductList", payProductList);

		// 支付卡种
		this.putData("CardTypeEnums", CardKindEnum.values());

		// 限制类型
		this.putData("LimitTypeEnums", LimitTypeEnum.values());

		return "amountLimitEdit";
	}

	/***
	 * 支付方式
	 */
	public void loadPayWay() {
		HttpServletResponse resp = null;
		try {
			resp = getHttpResponse();
			resp.setCharacterEncoding("utf-8");
			String payProduct = getString("payProduct");// 支付产品
			JSONArray json = null;
			List<PayWay> payWayList = payWayFacade.findPayWayByPayProductCode(payProduct);
			json = JSONArray.fromObject(payWayList);
			resp.getWriter().write(json.toString());

		} catch (Exception e) {
			log.error("loadPayInterface fail:", e);
		} finally {
			try {
				resp.getWriter().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public AmountLimit getModel() {
		return amountLimit;
	}
}
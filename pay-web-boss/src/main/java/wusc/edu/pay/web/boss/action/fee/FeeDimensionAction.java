package wusc.edu.pay.web.boss.action.fee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.facade.fee.vo.FeeDimensionBindVO;
import wusc.edu.pay.facade.payrule.entity.PayProduct;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.PayProductFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费维度action
 */
public class FeeDimensionAction extends BossBaseAction {

	private static final long serialVersionUID = -8349988507072446887L;
	@Autowired
	private FeeQueryFacade feeQueryFacade;
	@Autowired
	private FeeManagerFacade feeManagerFacade;
	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private PayProductFacade payProductFacade;

	/**
	 * 绑定支付产品List
	 * 
	 * @return
	 */
	@Permission("feenode:productswitch:add")
	public String bindPayProductList() {
		Long nodeId = getLong("feeNodeId");
		List<FeeDimensionBindVO> listFeeDimensionBindVOs = feeQueryFacade.queryFeeBindListByNodeId(nodeId);
		putData("listFeeDimensionBindVOs", listFeeDimensionBindVOs);
		putData("nodeId", nodeId);
		return "bindPayProductList";
	}

	/**
	 * 操作费率维度
	 */
	public void operateFeeDimension() {
		Long nodeId = getLong("nodeId");
		String payProductCode = getString("payProductCode");
		String payWayCode = getString("payWayCode");
		// 查询支付方式和支付产品
		PayProduct payProduct = payProductFacade.getPayProductByPayProductCode(payProductCode);
		PayWay payWay = payWayFacade.getPayWayBypayWayCodeAndpayProductCode(payWayCode, payProductCode);
		//
		FeeDimension feeDimension = feeQueryFacade.queryFeeDimension(nodeId, payProductCode, payWayCode);
		if (feeDimension == null) {
			feeDimension = new FeeDimension();
			feeDimension.setFeeNodeId(nodeId);
			feeDimension.setBankChannelCode(payWay.getDefaultPayInterface());
			feeDimension.setFrpCode(payWay.getPayWayCode());
			feeDimension.setPayProduct(payProduct.getPayProductCode());
			feeDimension.setPayProductName(payProduct.getPayProductName());
			feeDimension.setStatus(PublicStatusEnum.ACTIVE.getValue());
			feeManagerFacade.createFeeDimension(feeDimension);
			this.logSave("添加费率维度，支付产品名称：" + payProduct.getPayProductName());
		} else {
			Integer status = feeDimension.getStatus();
			if (PublicStatusEnum.ACTIVE.getValue() == status) {
				feeDimension.setStatus(PublicStatusEnum.INACTIVE.getValue());
			} else {
				feeDimension.setStatus(PublicStatusEnum.ACTIVE.getValue());
			}
			feeManagerFacade.updateFeeDimension(feeDimension);
			this.logEdit("修改费率维度，支付产品名称：" + payProduct.getPayProductName());
		}

	}

	/**
	 * 批量操作费率维度
	 */
	public void batchFeeDimension() {
		Long nodeId = getLong("nodeId");
		String payProductCode = getString("payProductCode");
		String payWayName = "";

		PayProduct payProduct = payProductFacade.getPayProductByPayProductCode(payProductCode);
		// 查出所有已经有的维度
		List<FeeDimension> listDimensions = feeQueryFacade.queryFeeDimensionList(nodeId, payProductCode);
		Boolean operateFlag = false;// 状态标志，false为选中操作，true为全部取消操作
		if (listDimensions != null) {// 如果记录不为空，则判断是选中还是取消
			Map<String, FeeDimension> payDimMapTemp = new HashMap<String, FeeDimension>();
			for (FeeDimension feeDimension : listDimensions) {
				payDimMapTemp.put(feeDimension.getFrpCode(), feeDimension);
				if (feeDimension.getStatus().equals(PublicStatusEnum.ACTIVE.getValue())) {// 如果有选中的，那就全部取消
					operateFlag = true;
				}
			}
			StringBuffer payProductName = new StringBuffer();
			if (operateFlag) {// 全部取消
				for (FeeDimension feeDimension : listDimensions) {
					feeDimension.setStatus(PublicStatusEnum.INACTIVE.getValue());
					feeManagerFacade.updateFeeDimension(feeDimension);
					payProductName.append(feeDimension.getPayProductName() + "，");
				}
				this.logEdit("修改费率维度状态（无效），支付产品名称：[" + payProductName + "]");
			} else {// 全部选中
				// 查询出该计费产品下的所有计费方式
				List<PayWay> listpPayWays = payWayFacade.findPayWayByPayProductCode(payProductCode);
				for (PayWay payWay : listpPayWays) {
					// 如果已经存在该维度，那么就更新
					String payWayCode = payWay.getPayWayCode();
					if (payDimMapTemp.get(payWayCode) != null) {
						FeeDimension dimension = payDimMapTemp.get(payWayCode);
						dimension.setStatus(PublicStatusEnum.ACTIVE.getValue());
						payProductName.append(dimension.getPayProductName() + "，");
						feeManagerFacade.updateFeeDimension(dimension);
					} else {// 如果不存在该维度，就新建
						FeeDimension feeDimension = new FeeDimension();
						feeDimension.setFeeNodeId(nodeId);
						// 把mcc类型的名称保存到借口编码里面去，保存之前做截取判断，避免过长保存出现问题
						payWayName = payWay.getDefaultPayInterface();
						if (payWayName.length() > 30) {
							payWayName = payWayName.substring(0, 30) + "...";
						}
						feeDimension.setBankChannelCode(payWayName);

						feeDimension.setFrpCode(payWay.getPayWayCode());
						feeDimension.setPayProduct(payProduct.getPayProductCode());
						feeDimension.setPayProductName(payProduct.getPayProductName());
						feeDimension.setStatus(PublicStatusEnum.ACTIVE.getValue());
						payProductName.append(payProduct.getPayProductName() + "，");
						feeManagerFacade.createFeeDimension(feeDimension);
					}
				}
				this.logEdit("添加/修改费率维度，支付产品名称：[" + payProductName + "]");
			}
		}
	}


	/**
	 * 分页查询维度(根據節點)
	 * 
	 * @return
	 */
	public String listDimension() {
		Long feeNodeId = getLong("feeNodeId");
		String payProductCode = getString("product.payProductCode");// 支付产品编号
		String payWayCode = getString("way.payWayCode");// 支付方式编号
		String payWayName = getString("way.payWayName");// 支付产品名称
		String payProductName = getString("product.payProductName");// 支付产品名称
		//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("payProduct", payProductCode);
		map.put("frpCode", payWayCode);
		map.put("feeNodeId", feeNodeId);
		super.pageBean = feeQueryFacade.queryFeeDimensionListPage(this.getPageParam(), map);
		FeeNode feeNode = feeQueryFacade.queryFeeNodeByNodeId(feeNodeId);
		this.pushData(pageBean);
		this.putData("feeNode", feeNode);
		// 回显
		this.putData("payProductCode", payProductCode);
		this.putData("payWayCode", payWayCode);
		this.putData("payProductName", payProductName);
		this.putData("payWayName", payWayName);
		return "listDimension";
	}

	/**
	 * 查詢所有計費維度
	 * 
	 * @return
	 */
	public String feeDimensionAllList() {
		Map<String, Object> map = new HashMap<String, Object>();
		String payProductCode = getString("payProductCode");// 支付产品编号
		String payWayCode = getString("payWayCode");// 支付方式编号
		String feeNodeName = getString("feeNodeName");
		map.put("payProduct", payProductCode);
		map.put("frpCode", payWayCode);
		map.put("feeNodeName", feeNodeName);
		map.put("status", PublicStatusEnum.ACTIVE.getValue());
		super.pageBean = feeQueryFacade.queryFeeDimensionVOListPage(this.getPageParam(), map);
		this.pushData(pageBean);
		// 回显
		putData("payProduct", payProductCode);
		putData("payWayCode", payWayCode);
		this.putData("CalculateFeeItemEnum", CalculateFeeItemEnum.toList());
		return "allListDimension";
	}

	/**
	 * 根据维度ID查找维度
	 * 
	 * @return
	 */
	public String dimensionLookupListById() {
		String dimensionIds = getString("dimensionIds");
		String[] ids = dimensionIds.trim().split("-");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dimensionIds", ids);
		map.put("status", PublicStatusEnum.ACTIVE.getValue());
		PageBean FeeDimensionVOList = feeQueryFacade.queryFeeDimensionVOListPage(getPageParam(), map);
		this.pushData(FeeDimensionVOList);
		return "dimensionLookupList";
	}

	/**
	 * 查找所有维度
	 * 
	 * @return
	 */
	public String dimensionLookupList() {
		Long feeNodeId = getLong("feeNodeId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("feeNodeId", feeNodeId);
		map.put("payProductName", getString("payProductName"));
		map.put("status", PublicStatusEnum.ACTIVE.getValue());
		super.pageBean = feeQueryFacade.queryFeeDimensionVOListPage(this.getPageParam(), map);
		this.pushData(pageBean);
		return "dimensionLookupList";
	}

	/**
	 * 绑定转账、提现、自助结算等其他产品UI
	 * 
	 * @return
	 */
	public String bindOtherProductUI() {
		Long feeNodeId = getLong("feeNodeId");
		String trxType = getString("trxType");
		List<FeeDimension> listDimension = feeQueryFacade.queryDimensionByNodeId(feeNodeId);
		this.putData("listFeeDimensionVO", listDimension);
		// if (Integer.parseInt(trxType) ==
		// TrxTypeEnum.ACCOUNT_TRANSFER.getValue()) {// 转账
		// this.putData("FeeOtherProductEnumName",
		// FeeOtherProductEnum.toListTransferName());
		// } else if (Integer.parseInt(trxType) == TrxTypeEnum.ATM.getValue())
		// {// 提现
		// this.putData("FeeOtherProductEnumName",
		// FeeOtherProductEnum.toListATMName());
		// } else if (Integer.parseInt(trxType) ==
		// TrxTypeEnum.SETTLEMENT.getValue()) {// 结算
		// this.putData("FeeOtherProductEnumName",
		// FeeOtherProductEnum.toListSettlementName());
		// }
		this.putData("feeNodeId", feeNodeId);
		this.putData("trxType", trxType);
		return "bindOtherProduct";
	}

	/**
	 * 绑定转账、提现、自助结算等其他产品--功能
	 * 
	 * @return
	 */
	public String bindOtherProduct() {
		Integer status = getInteger("status");
		Long dimensionId = getLong("dimensionId");
		if (dimensionId == null) {
			Long feeNodeId = getLong("feeNodeId");
			String payProductName = getString("productName");
			String payProduct = getString("productCode");
			// String frpCode = getString("frpCode");
			// String bankChannelCode = getString("bankChannelCode");
			FeeDimension feeDimension = new FeeDimension();
			feeDimension.setFeeNodeId(feeNodeId);
			feeDimension.setPayProduct(payProduct);
			feeDimension.setPayProductName(payProductName);
			feeDimension.setBankChannelCode(payProduct);
			feeDimension.setFrpCode(payProduct);
			feeDimension.setStatus(status);
			feeManagerFacade.createFeeDimension(feeDimension);
		} else {
			FeeDimension feeDimension = feeQueryFacade.queryFeeDimensionById(dimensionId);
			feeDimension.setStatus(status);
			feeManagerFacade.updateFeeDimension(feeDimension);
		}

		return this.operateSuccess();
	}

}

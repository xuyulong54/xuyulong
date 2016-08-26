package wusc.edu.pay.web.boss.action.fee;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.fee.entity.FeeDimension;
import wusc.edu.pay.facade.fee.entity.FeeNode;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeModelTypeEnum;
import wusc.edu.pay.facade.fee.service.FeeManagerFacade;
import wusc.edu.pay.facade.fee.service.FeeQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 计费节点
 */
public class FeeNodeAction extends BossBaseAction {

	private static final long serialVersionUID = -8349988507072446887L;

	@Autowired
	private FeeQueryFacade feeQueryFacade;
	@Autowired
	private FeeManagerFacade feeManagerFacade;

	/**
	 * 分页查询节点
	 * 
	 * @return
	 */
	@Permission("fee:node:view")
	public String listNode() {
		Integer nodeType = getInteger("nodeType");
		String feeNodeName = getString("feeNodeName");
		Integer calculateFeeItem = getInteger("calculateFeeItem");
		Map<String, Object> map = new HashMap<String, Object>(); 
		map.put("nodeType", nodeType);
		map.put("feeNodeName", feeNodeName);
		map.put("calculateFeeItem", calculateFeeItem);

		super.pageBean = feeQueryFacade.queryFeeNodeListPage(
				this.getPageParam(), map);
		this.pushData(pageBean);
		// CalculateFeeItemEnum计费项
		this.putData("CalculateFeeItemEnums", CalculateFeeItemEnum.toList());
		this.putData("payEnums", CalculateFeeItemEnum.toListForPay());
		this.putData("posFeeItem", CalculateFeeItemEnum.POS_ACQUIRING);
		this.putData("specialEnums", CalculateFeeItemEnum.toListForSpecial());
		this.putData("feeModelTypeEnums", FeeModelTypeEnum.toList());
		this.putData("proMap", map);
		return "listNode";
	}

	/**
	 * 添加节点UI
	 * 
	 * @return
	 */
	@Permission("fee:node:add")
	public String addNodeUI() {
		this.putData("feeModelTypeEnums", FeeModelTypeEnum.toList());
		this.putData("CalculateFeeItemEnums", CalculateFeeItemEnum.toList());
		return "addNodeUI";
	}

	/**
	 * 添加节点
	 * 
	 * @return
	 */
	@Permission("fee:node:add")
	public String addNode() {
		Integer nodeType = getInteger("feeNodeType");
		Integer calculateFeeItem = getInteger("calculateFeeItem");
		FeeNode node = new FeeNode();
		node.setNodeName(getString("feeNodeName"));
		node.setRemark(getString("desc"));
		node.setNodeType(nodeType);
		node.setCalculateFeeItem(calculateFeeItem);
		node.setStatus(PublicStatusEnum.ACTIVE.getValue());
		boolean flag = feeManagerFacade.checkUnique(node);
		if(!flag){
			return this.operateError("存在相同节点！");
		}
		if((CalculateFeeItemEnum.ATM_ACQUIRING.getValue() == calculateFeeItem) ||(CalculateFeeItemEnum.DEBIT_ACQUIRING.getValue() == calculateFeeItem) || (CalculateFeeItemEnum.SETTLEMENT_ACQUIRING.getValue() == calculateFeeItem) ||(CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_0.getValue() == calculateFeeItem) ||(CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_1.getValue() == calculateFeeItem) ||(CalculateFeeItemEnum.SETTLEMENT_ACQUIRING_T_5.getValue() == calculateFeeItem) || (CalculateFeeItemEnum.TRANSFER_ACQUIRING.getValue() == calculateFeeItem) || (CalculateFeeItemEnum.CASH_ACQUIRING.getValue() == calculateFeeItem) ){
			Long feeNodeId = feeManagerFacade.createFeeNode(node);
			FeeDimension feeDimension = new FeeDimension();
			feeDimension.setFeeNodeId(feeNodeId);
			feeDimension.setPayProduct(CalculateFeeItemEnum.getEnum(calculateFeeItem).name());
			feeDimension.setPayProductName(CalculateFeeItemEnum.getEnum(calculateFeeItem).getDesc());
			feeDimension.setBankChannelCode(CalculateFeeItemEnum.getEnum(calculateFeeItem).name());
			feeDimension.setFrpCode(CalculateFeeItemEnum.getEnum(calculateFeeItem).name());
			feeDimension.setStatus(PublicStatusEnum.ACTIVE.getValue());
			feeManagerFacade.createFeeDimension(feeDimension);
			this.logSave("创建计费维度，支付产品名称："+feeDimension.getPayProductName());
		}else{
			feeManagerFacade.createFeeNode(node);
			this.logSave("创建计费节点，节点名称："+node.getNodeName());
		}
		return this.operateSuccess();	
	}
}

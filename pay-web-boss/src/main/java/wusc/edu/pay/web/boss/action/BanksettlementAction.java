package wusc.edu.pay.web.boss.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.common.web.struts.ModelDrivenUtil;
import wusc.edu.pay.facade.bank.entity.BankSettlement;
import wusc.edu.pay.facade.bank.service.BankSettlementFacade;
import wusc.edu.pay.facade.boss.exceptions.BossBizException;
import wusc.edu.pay.web.boss.base.BossBaseAction;

import com.opensymphony.xwork2.ModelDriven;

/**
 * 设置银行结算信息
 * */
public class BanksettlementAction extends BossBaseAction implements ModelDriven<BankSettlement> {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private BankSettlementFacade bankSettlementFacade; 
	@Permission("bank:settlement:view")
	public String listBanksettlement() {
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("bankChannelCode", getString("bankChannelCode")); // 银行渠道名称
		super.pageBean = bankSettlementFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap); // 回显查询条件
		return "listBanksettlement";
	}
	
	/**
	 * 查看银行结算信息
	 * */
	@Permission("bank:settlement:view")
	public String viewBanksettlement(){
		long id = getLong("id");
		banksettlementParam = bankSettlementFacade.getById(id);
		if(banksettlementParam == null){
			return operateError("不存在该结算信息");
		}
		return "viewBanksettlement";
	}
	
	/**
	 * 跳转至设置银行结算信息页面
	 * */
	@Permission("bank:settlement:set")
	public String setBanksettlementUI(){
		//根据银行协议ID ， 银行渠道ID 确认银行结算信息表的唯一值
		Long bankAgreementId = getLong("bankAgreementID");
		String bankChannelCode = getString("bankChannelCode");
		this.putData("bankAgreementID", bankAgreementId);
		this.putData("bankChannelCode", bankChannelCode);
		banksettlementParam = bankSettlementFacade.getByBankBankChannelCode(bankChannelCode);
		if(banksettlementParam != null){//如果存在，将数据存放在session中，并跳转至银行结算信息修改页面
			return "editBanksettlementUI";
		}else{//如果不存在，跳转至银行结算信息新增页面
			return "addBanksettlementUI";
		}
	}
	
	/**
	 * 新增结算信息
	 * */
	@Permission("bank:settlement:set")
	public String addBanksettlement(){
		banksettlementParam = getModel();
		String errMsg = validateBanksettlement(banksettlementParam);
		if(errMsg != null && !"".equals(errMsg)){
			throw new BossBizException(errMsg);
		}
		long num = bankSettlementFacade.create(banksettlementParam);
		if(num == 0){
			super.logSaveError("新增结算信息失败.银行渠道编码["+banksettlementParam.getBankChannelCode()+"]");
		}else{
			super.logSave("新增结算信息.银行渠道编码["+banksettlementParam.getBankChannelCode()+"]");	
		}
		return operateSuccess();
	}
	
	/**
	 * 修改结算信息
	 * */
	@Permission("bank:settlement:set")
	public String editBanksettlement(){
		banksettlementParam = getModel();
		String errMsg = validateBanksettlement(banksettlementParam);
		if(errMsg != null && !"".equals(errMsg)){
			throw new BossBizException(errMsg);
		}
		long num = bankSettlementFacade.update(banksettlementParam);
		if(num == 0){
			super.logEditError("修改结算信息失败.银行渠道编码["+banksettlementParam.getBankChannelCode()+"]");
		}else{
			super.logEdit("修改结算信息.银行渠道编码["+banksettlementParam.getBankChannelCode()+"]");	
		}
		return operateSuccess();
	}

	/**
	 * 銀行結算信息校驗
	 * */

	private String validateBanksettlement(BankSettlement banksettlementParam) {
		StringBuffer errStr = new StringBuffer();
		String BankChannelCode = banksettlementParam.getBankChannelCode();		//银行渠道ID
		if(StringUtils.isEmpty(BankChannelCode)){
			errStr.append("银行渠道不能为空,<br/>");
		}
        /*
        long bankAgreementId = banksettlementParam.getBankAgreementId();		//银行协议ID
		if(bankAgreementId < 0){
			errStr.append("银行协议不能为空,<br/>");
		}*/
		int settleCycle = banksettlementParam.getSettleCycle ();	  //结算周期
		if(settleCycle <= 0){
			errStr.append("请选择正确的结算周期,<br/>");
		}
		/*String chargeAccount = banksettlementParam.getChargeAccount ();		//手续费账户
		if(!ValidateUtils.isNumeric(chargeAccount)){
			errStr.append("手续费账户输入不正确,<br/>");
		}
		String marginAccount = banksettlementParam.getMarginAccount ();		//保证金账户
		if(!ValidateUtils.isNumeric(marginAccount)){
				errStr.append("保证金账户输入不正确,<br/>");
		}*/
		int chargeDeductWay = banksettlementParam.getChargeDeductWay ();		//手续费扣收方式
		if(chargeDeductWay <= 0){
			errStr.append("请选择正确的手续费扣收方式,<br/>");
		}
		int chargeDeductCycle = banksettlementParam.getChargeDeductCycle ();	  //手续费扣收周期
		if(chargeDeductCycle <= 0){
			errStr.append("请选择正确的手续费扣收周期,<br/>");
		}
		int chargePayWay = banksettlementParam.getChargePayWay ();		//手续费支付方式
		if(chargePayWay <= 0){
			errStr.append("请选择正确的手续费支付方式,<br/>");
		}
		int refoundType = banksettlementParam.getRefoundType ();	  //退款方式
		if(refoundType <= 0){
			errStr.append("请选择正确的退款方式,<br/>");
		}
		int refoundDeductWay = banksettlementParam.getRefoundDeductWay ();	  //退款扣收方式
		if(refoundDeductWay <= 0){
			errStr.append("请选择正确的扣款方式,<br/>");
		}
		int refoundValidity = banksettlementParam.getRefoundValidity ();		//退款有效期
		if(refoundValidity < 0){
			errStr.append("退款有效期输入不正确,<br/>");
		}
		int isReturnCharge = banksettlementParam.getIsReturnCharge ();		//是否退回手续费
		if(isReturnCharge <= 0){
			errStr.append("请选择正确的是否回退手续费,<br/>");
		}
		int refundAccountTime = banksettlementParam.getRefundAccountTime ();		//退款到账时间
		if(refundAccountTime < 0){
			errStr.append("退款到账时间输入不正确,<br/>");
		}
		double refundLimit = banksettlementParam.getRefundLimit ();		//退款限额
		if(refundLimit < 0){
			if(refundLimit <= 0){
				errStr.append("退款限额不能小于零,<br/>");
			}
		}
		int isNonWorkdayAccount = banksettlementParam.getIsNonWorkdayAccount ();		//是否非工作日到账
		if(isNonWorkdayAccount <= 0){
			errStr.append("请选择正确的是否工作日到账,<br/>");
		}
//		String chargeomment = banksettlementParam.getChargeomment ();		//手续费描述
//		if(chargeomment.isEmpty()){
//			errStr.append("手续费描述不能为空,<br/>");
//		}
//		String refundChargeRule = banksettlementParam.getRefundChargeRule ();		//退款手续费规则
//		if(refundChargeRule.isEmpty()){
//			errStr.append("退款手续费规则不能为空,<br/>");
//		}
		return errStr.toString();
	}
	
	
	private BankSettlement banksettlementParam = new BankSettlement();
	@Override
	public BankSettlement getModel() {
		return (BankSettlement) ModelDrivenUtil.cleanModel(banksettlementParam);
	}

}

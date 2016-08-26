package wusc.edu.pay.web.boss.action;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.banklink.netpay.enums.BankTradeStatusEnum;
import wusc.edu.pay.facade.banklink.netpay.service.BankFacade;
import wusc.edu.pay.facade.banklink.netpay.vo.PaymentQueryResult;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


public class BankOrderQueryAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private BankFacade bankFacade;
	@Autowired
	private PaymentFacade paymentFacade;

	/**
	 * 跳转到银行订单查询页面
	 * */
	@Permission("bank:order:view")
	public String queryBankOrderUI() {
		return "queryBankOrder";
	}

	/**
	 * 根据银行订单号与交易类型查询
	 * */
	@Permission("bank:order:view")
	public String queryBankOrder() {
		String bankOrderNo = getString("bankOrderNo");
		if (bankOrderNo == null || "".equals(bankOrderNo.trim())) {
			return operateError("请输入订单号");
		} else {
			this.putData("isDisplay" , 1);
			PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(null, null,
					null, bankOrderNo, null);
			if (paymentRecord == null) {
				return operateError("该笔支付交易不存在");
			} else {

				PaymentQueryResult queryResult = bankFacade.query(paymentRecord.getPayInterfaceCode(), bankOrderNo,
						paymentRecord.getCreateTime());
				if(queryResult == null){
					return operateError("无法查询到该订单！");
				}
				this.putData("queryResult", queryResult);
				this.putData("paymentRecord", paymentRecord);
				
			}
		}
		
		this.putData("bankOrderNo" , bankOrderNo);
		this.putData("PaymentRecordStatusEnum" , PaymentRecordStatusEnum.toList());
		this.putData("BankTradeStatusEnum" , BankTradeStatusEnum.toList());
		this.putData("sucStatus", BankTradeStatusEnum.SUCCESS);
		this.putData("sucTradeStatus", PaymentRecordStatusEnum.SUCCESS.getValue());
		return "queryBankOrder";
	}
	
	/**
	 * 补发通知
	 * */
	public String notifyMerchant() {
		String bankOrderNo = getString("bankOrderNo");
		if (bankOrderNo == null || "".equals(bankOrderNo.trim())) {
			return operateError("银行订单号不可为空");
		}
		Double orderAccount = getDouble("orderAccount");
		if (orderAccount == null || orderAccount < 0) {
			return operateError("银行金额有误");
		}
		String bankTradeId = getString("bankTradeId");
		if (bankTradeId == null) {
			bankTradeId = "";
		}
		paymentFacade.completePayment(bankOrderNo, bankTradeId, orderAccount, PaymentRecordStatusEnum.SUCCESS);
		super.logSave("补发通知.银行订单号["+bankOrderNo+"]"+" 银行流水号["+bankTradeId+"]");
		return operateSuccess();
	}

}

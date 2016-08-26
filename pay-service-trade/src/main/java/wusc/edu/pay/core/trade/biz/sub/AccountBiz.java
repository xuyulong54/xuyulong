package wusc.edu.pay.core.trade.biz.sub;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.facade.account.enums.AccountFundDirectionEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.entity.SplitRecord;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.user.service.UserQueryFacade;

import com.alibaba.fastjson.JSONObject;

@Component("accountBiz")
public class AccountBiz {

	@Autowired
	private AccountTransactionFacade accountTransactionFacade;

	@Autowired
	private UserQueryFacade userQueryFacade;

	private static final Log log = LogFactory.getLog(AccountBiz.class);

	/**
	 * 创建分账
	 * 
	 * @param splitRecordList
	 */
	public List<AccountTransactionVo> splitCreateBill(List<SplitRecord> splitRecordList, PaymentOrder paymentOrder) {

		log.info("==>splitCreateBill");

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();

		for (int i = 0; i < splitRecordList.size(); i++) {

			// 主账户减款
			AccountTransactionVo dVo = new AccountTransactionVo();
			dVo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
			dVo.setUserNo(paymentOrder.getMerchantNo());
			dVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
			dVo.setRequestNo(paymentOrder.getSuccessTrxNo());
			dVo.setTradeType(AccountTradeTypeEnum.SPLIT);
			dVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());

			voList.add(dVo);

			// 子账户加款/冻结
			if (splitRecordList.get(i).getTrxModel() == TrxModelEnum.GUARANTEE.getValue()) {

				AccountTransactionVo cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				cVo.setRequestNo(paymentOrder.getSuccessTrxNo());
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				voList.add(cVo);

				cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.FROZEN);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setRequestNo(paymentOrder.getSuccessTrxNo());
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				cVo.setFrezonAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				voList.add(cVo);
			} else if (splitRecordList.get(i).getTrxModel() == TrxModelEnum.IMMEDIATELY.getValue()) {
				AccountTransactionVo cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				cVo.setRequestNo(paymentOrder.getSuccessTrxNo());
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				voList.add(cVo);
			}

		}

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		// 会计处理

		log.info("==>splitCreateBill<==");

		return voList;
	}

	/**
	 * 分账支付解冻
	 * 
	 * @param splitRecordList
	 */
	public List<AccountTransactionVo> splitConfirmPayment(List<SplitRecord> splitRecordList, PaymentOrder paymentOrder) {

		log.info("==>splitConfirmPayment");

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();

		for (int i = 0; i < splitRecordList.size(); i++) {

			AccountTransactionVo cVo = new AccountTransactionVo();
			cVo.setAccountFundDirection(AccountFundDirectionEnum.UNFROZEN);
			cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
			cVo.setRequestNo(paymentOrder.getSuccessTrxNo());
			cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
			cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
			cVo.setUnFrezonAmount(splitRecordList.get(i).getSplitAmount().doubleValue()); // 解冻

			voList.add(cVo);
		}

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		// 会计处理

		log.info("==>splitConfirmPayment<==");

		return voList;
	}

	/**
	 * 支付(包含分账)
	 * 
	 * @param splitRecordList
	 * @param paymentRecord
	 */
	public List<AccountTransactionVo> payment(List<SplitRecord> splitRecordList, PaymentRecord paymentRecord) {

		log.info("==>payment");

		if (splitRecordList == null) {
			splitRecordList = new ArrayList<SplitRecord>();
		}

		// 特殊处理_不做账务处理
		if (paymentRecord.getBizType() == TradeBizTypeEnum.POS_ACQUIRING.getValue() && paymentRecord.getPaymentType() == TradePaymentTypeEnum.POS_PAY_NOT_SETT.getValue()) {
			return new ArrayList<AccountTransactionVo>();
		}

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();

		if (TradeBizTypeEnum.RECHARGE.getValue() == paymentRecord.getBizType()) {
			String rechargeUserNo = paymentRecord.getPayerUserNo(); // 充值账户
			double rechargeFee = paymentRecord.getPayerFee().doubleValue(); // 充值手续费
			// 充值金额=支付金额-充值手续费
			double rechargeAmount = AmountUtil.sub(paymentRecord.getPayerPayAmount().doubleValue(), rechargeFee);

			// 会员充值
			AccountTransactionVo rechargeVo = new AccountTransactionVo();
			rechargeVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
			rechargeVo.setUserNo(rechargeUserNo);
			rechargeVo.setAmount(rechargeAmount);
			rechargeVo.setFee(rechargeFee);
			rechargeVo.setRequestNo(paymentRecord.getTrxNo());
			rechargeVo.setTradeType(AccountTradeTypeEnum.ACCOUNT_DEPOSIT);
			rechargeVo.setDesc(AccountTradeTypeEnum.ACCOUNT_DEPOSIT.getDesc());

			voList.add(rechargeVo);
			accountTransactionFacade.execute(voList);

			return voList;
		}

		// 代扣
		if (TradeBizTypeEnum.ACCOUNT_AGENCYDEBIT.getValue() == paymentRecord.getBizType()) {
			String receiverUserNo = paymentRecord.getMerchantNo(); // 商户账户
			double receiverFee = paymentRecord.getReceiverFee().doubleValue(); // 商户手续费
			// 入账金额=支付金额-手续费
			double inAmount = AmountUtil.sub(paymentRecord.getOrderFlag().doubleValue(), receiverFee);

			// 代扣
			AccountTransactionVo accountVo = new AccountTransactionVo();
			accountVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
			accountVo.setUserNo(receiverUserNo);
			accountVo.setAmount(inAmount);
			accountVo.setFee(receiverFee);
			accountVo.setRequestNo(paymentRecord.getTrxNo());
			accountVo.setTradeType(AccountTradeTypeEnum.ACCOUNT_AGENCYDEBIT);
			accountVo.setDesc(AccountTradeTypeEnum.ACCOUNT_AGENCYDEBIT.getDesc());

			voList.add(accountVo);
			accountTransactionFacade.execute(voList);

			return voList;
		}

		String receiverUserNo = paymentRecord.getMerchantNo();
		String payerUserNo = paymentRecord.getPayerUserNo();
		double receiverAmount = paymentRecord.getReceiverPostAmount().doubleValue();
		double payerAmount = paymentRecord.getPayerPayAmount().doubleValue();
		String requestNo = paymentRecord.getTrxNo();

		AccountTradeTypeEnum accountTradeTypeEnum = null;
		if (TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.ACCOUNT_BALANCE_PAY;
		} else if (TradePaymentTypeEnum.POS_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.POS_PAY;
		} else if (TradePaymentTypeEnum.NET_B2C_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.NET_B2C_PAY;
		} else if (TradePaymentTypeEnum.NET_B2B_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.NET_B2B_PAY;
		} else if (TradePaymentTypeEnum.FAST_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.FAST_PAY;
		} else if (TradePaymentTypeEnum.CASH_PAY.getValue() == paymentRecord.getPaymentType()) {
			accountTradeTypeEnum = AccountTradeTypeEnum.CASH_PAY;
		}

		if (StringUtil.isNotBlank(payerUserNo)) {
			if (accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.POS_PAY.getValue() || accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.NET_B2C_PAY.getValue()
					|| accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.NET_B2B_PAY.getValue() || accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.FAST_PAY.getValue()
					|| accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.CASH_PAY.getValue()) {
				// 付款人充值加款
				AccountTransactionVo creditVo1 = new AccountTransactionVo();
				creditVo1.setAccountFundDirection(AccountFundDirectionEnum.ADD);
				creditVo1.setUserNo(payerUserNo);
				creditVo1.setAmount(payerAmount);
				creditVo1.setRequestNo(requestNo);
				creditVo1.setTradeType(AccountTradeTypeEnum.ACCOUNT_DEPOSIT);
				creditVo1.setDesc(AccountTradeTypeEnum.ACCOUNT_DEPOSIT.getDesc());
				voList.add(creditVo1);
			}

			// 付款人支付减款
			AccountTransactionVo debitVo = new AccountTransactionVo();
			debitVo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
			debitVo.setUserNo(payerUserNo);
			debitVo.setAmount(payerAmount);
			debitVo.setFee(paymentRecord.getPayerFee().doubleValue());
			debitVo.setRequestNo(requestNo);
			debitVo.setTradeType(accountTradeTypeEnum);
			debitVo.setDesc(accountTradeTypeEnum.getDesc());
			voList.add(debitVo);
		}

		if (accountTradeTypeEnum.getValue() == AccountTradeTypeEnum.CASH_PAY.getValue()) {
			// 现金支付,商户不做加款
		} else {
			// 主账户加款
			AccountTransactionVo creditVo = new AccountTransactionVo();
			creditVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
			creditVo.setUserNo(receiverUserNo);
			creditVo.setAmount(receiverAmount);
			creditVo.setFee(paymentRecord.getReceiverFee().doubleValue());
			creditVo.setRequestNo(requestNo);
			creditVo.setTradeType(accountTradeTypeEnum);
			creditVo.setDesc(accountTradeTypeEnum.getDesc());
			creditVo.setRiskDay(paymentRecord.getRiskDay()); // 设置交易风险预存期
			voList.add(creditVo);
		}

		// 主账户减款，子账户加款
		for (int i = 0; i < splitRecordList.size(); i++) {

			AccountTransactionVo dVo = new AccountTransactionVo();
			dVo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
			dVo.setUserNo(receiverUserNo);
			dVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
			dVo.setRequestNo(requestNo);
			dVo.setTradeType(AccountTradeTypeEnum.SPLIT);
			dVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());

			voList.add(dVo);

			if (splitRecordList.get(i).getTrxModel() == TrxModelEnum.GUARANTEE.getValue()) {
				AccountTransactionVo cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				cVo.setRequestNo(requestNo);
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				voList.add(cVo);

				cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.FROZEN);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setFrezonAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				cVo.setRequestNo(requestNo);
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				voList.add(cVo);
			} else if (splitRecordList.get(i).getTrxModel() == TrxModelEnum.IMMEDIATELY.getValue()) {
				AccountTransactionVo cVo = new AccountTransactionVo();
				cVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
				cVo.setUserNo(splitRecordList.get(i).getSplitMerchantNo());
				cVo.setAmount(splitRecordList.get(i).getSplitAmount().doubleValue());
				cVo.setRequestNo(requestNo);
				cVo.setTradeType(AccountTradeTypeEnum.SPLIT);
				cVo.setDesc(AccountTradeTypeEnum.SPLIT.getDesc());
				voList.add(cVo);
			}

		}

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		// 会计处理

		log.info("==>payment<==");

		return voList;
	}

	/**
	 * 转账
	 * 
	 * @param transferRecord
	 */
	public List<AccountTransactionVo> transfer(TransferRecord transferRecord) {

		log.info("==>transfer");

		String creditUserNo = transferRecord.getSourceUserNo();
		String debitUserNo = transferRecord.getTargetUserNo();
		double amount = transferRecord.getAmount().doubleValue();
		String requestNo = transferRecord.getTrxNo();

		// 不能给自己转账
		if (debitUserNo.equals(creditUserNo)) {
			throw TradeBizException.ACCOUNT_CANNOT_TRANSFER_TO_MYSELF;
		}

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();
		// 借方(支付)
		AccountTransactionVo debitVo = new AccountTransactionVo();
		debitVo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
		debitVo.setUserNo(debitUserNo);
		debitVo.setAmount(amount);
		debitVo.setFee(transferRecord.getTargetFee().doubleValue());
		debitVo.setRequestNo(requestNo);
		debitVo.setTradeType(AccountTradeTypeEnum.ACCOUNT_TRANSFER);
		debitVo.setDesc(AccountTradeTypeEnum.ACCOUNT_TRANSFER.getDesc());
		voList.add(debitVo);

		// 贷方(收款)
		AccountTransactionVo creditVo = new AccountTransactionVo();
		creditVo.setAccountFundDirection(AccountFundDirectionEnum.ADD);
		creditVo.setUserNo(creditUserNo);
		creditVo.setAmount(amount);
		creditVo.setFee(transferRecord.getSourceFee().doubleValue());
		creditVo.setRequestNo(requestNo);
		creditVo.setTradeType(AccountTradeTypeEnum.ACCOUNT_TRANSFER);
		creditVo.setDesc(AccountTradeTypeEnum.ACCOUNT_TRANSFER.getDesc());
		voList.add(creditVo);

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		// 会计处理

		log.info("==>transfer<==");

		return voList;
	}



}

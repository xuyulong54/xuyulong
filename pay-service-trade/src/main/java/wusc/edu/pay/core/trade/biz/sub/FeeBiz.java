package wusc.edu.pay.core.trade.biz.sub;

import java.math.BigDecimal;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.fee.enums.CalculateFeeItemEnum;
import wusc.edu.pay.facade.fee.enums.FeeRoleTypeEnum;
import wusc.edu.pay.facade.fee.service.CalculateFeeFacade;
import wusc.edu.pay.facade.notify.util.NotifyUtil;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;

import com.alibaba.fastjson.JSONObject;

@Component("feeBiz")
public class FeeBiz {

	private static final Log log = LogFactory.getLog(FeeBiz.class);

	@Autowired
	private CalculateFeeFacade calculateFeeFacade;

	@Autowired
	private JmsTemplate notifyJmsTemplate;

	/**
	 * 支付_试算
	 * 
	 * @param paymentRecord
	 */
	public FeeCalculateResultDTO preCaculate_Pay(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		log.info("==>preCaculate_Pay MerchantOrderNo:" + paymentRecord.getMerchantOrderNo());

		String userNo = paymentRecord.getMerchantNo();
		int userType = EnumChangeUtils.getUserType(paymentRecord.getReceiverAccountType());
		int calFeeItem = 0;

		if (paymentRecord.getBizType() == TradeBizTypeEnum.ONLINE_ACQUIRING.getValue()) {
			if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.NET_B2C_PAY.getValue()
					|| paymentRecord.getPaymentType() == TradePaymentTypeEnum.NET_B2B_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.ONLINE_ACQUIRING.getValue();
			} else if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.FAST_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.FP_ACQUIRING.getValue();
			} else if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.VC_ACQUIRING.getValue();
			} else if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.CASH_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.CASH_ACQUIRING.getValue();
			}
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.POS_ACQUIRING.getValue()) {
			if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.CASH_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.CASH_ACQUIRING.getValue();
			} else {
				calFeeItem = CalculateFeeItemEnum.POS_ACQUIRING.getValue();
			}
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.RECHARGE.getValue()) {
			calFeeItem = CalculateFeeItemEnum.RECHARGE_ACQUIRING.getValue();
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.ACCOUNT_AGENCYDEBIT.getValue()) {
			calFeeItem = CalculateFeeItemEnum.DEBIT_ACQUIRING.getValue();
		} else if (paymentRecord.getBizType() == TradeBizTypeEnum.MOBILE_ACQUIRING.getValue()) {
			if (paymentRecord.getPaymentType() == TradePaymentTypeEnum.FAST_PAY.getValue()) {
				calFeeItem = CalculateFeeItemEnum.FP_ACQUIRING.getValue();
			}
		}

		String payProduct = paymentRecord.getPayProductCode();
		String frpCode = paymentRecord.getPayWayCode();
		double amount = paymentRecord.getOrderAmount().doubleValue();
		Date transferDate = paymentRecord.getCreateTime();
		String merchantName = paymentRecord.getMerchantName();
		String orderNo = paymentRecord.getMerchantOrderNo();
		String trxNo = paymentRecord.getTrxNo();

		log.info(String
				.format("==>userNo:%s, userType:%s, calFeeItem:%s, payProduct:%s, frpCode:%s, amount:%s, transferDate:%s, merchantName:%s, orderNo:%s, trxNo:%s",
						userNo, userType, calFeeItem, payProduct, frpCode, amount, transferDate, merchantName, orderNo, trxNo));

		FeeCalculateResultDTO dto = calculateFeeFacade.preCaculateFee(userNo, userType, calFeeItem, payProduct, frpCode, amount,
				transferDate, merchantName, orderNo, trxNo);

		log.info("==>preCaculateFee Result:" + JSONObject.toJSONString(dto));

		// 付款人承担
		if (dto.getRoleType().intValue() == FeeRoleTypeEnum.PAYER.getValue()) {
			// 设置付款方支付手续费
			paymentRecord.setPayerFee(BigDecimal.valueOf(dto.getPayFee()));
			paymentOrder.setPayerFee(paymentRecord.getPayerFee());
			// 设置实际支付金额
			paymentRecord.setPayerPayAmount(BigDecimal.valueOf(AmountUtil.add(dto.getPayFee(), amount)));
			paymentOrder.setPayerPayAmount(paymentRecord.getPayerPayAmount());

			// 设置收款方支付手续费
			paymentRecord.setReceiverFee(BigDecimal.ZERO);
			paymentOrder.setReceiverFee(BigDecimal.ZERO);
			// 设置收款方入账金额
			paymentRecord.setReceiverPostAmount(BigDecimal.valueOf(amount));
			paymentOrder.setReceiverPostAmount(BigDecimal.valueOf(amount));
		}
		// 款人承担
		else if (dto.getRoleType().intValue() == FeeRoleTypeEnum.PAYEE.getValue()) {
			// 设置付款方支付手续费
			paymentRecord.setPayerFee(BigDecimal.ZERO);
			paymentOrder.setPayerFee(BigDecimal.ZERO);
			// 设置实际支付金额
			paymentRecord.setPayerPayAmount(BigDecimal.valueOf(amount));
			paymentOrder.setPayerPayAmount(BigDecimal.valueOf(amount));

			// 设置收款方支付手续费
			paymentRecord.setReceiverFee(BigDecimal.valueOf(dto.getPayFee()));
			paymentOrder.setReceiverFee(paymentRecord.getReceiverFee());
			// 设置收款方入账金额
			paymentRecord.setReceiverPostAmount(BigDecimal.valueOf(AmountUtil.sub(amount, dto.getPayFee())));
			paymentOrder.setReceiverPostAmount(paymentRecord.getReceiverPostAmount());
		}
		// 平台承担
		else if (dto.getRoleType().intValue() == FeeRoleTypeEnum.PLATFORM.getValue()) {
			// 设置付款方支付手续费
			paymentRecord.setPayerFee(BigDecimal.ZERO);
			paymentOrder.setPayerFee(BigDecimal.ZERO);
			// 设置实际支付金额
			paymentRecord.setPayerPayAmount(BigDecimal.valueOf(amount));
			paymentOrder.setPayerPayAmount(BigDecimal.valueOf(amount));

			// 设置收款方支付手续费
			paymentRecord.setReceiverFee(BigDecimal.ZERO);
			paymentOrder.setReceiverFee(BigDecimal.ZERO);
			// 设置收款方入账金额
			paymentRecord.setReceiverPostAmount(BigDecimal.valueOf(amount));
			paymentOrder.setReceiverPostAmount(BigDecimal.valueOf(amount));
		}

		paymentRecord.setPlatIncome(paymentRecord.getPayerFee().add(paymentRecord.getReceiverFee()));

		log.info("==>preCaculate_Pay<==");

		return dto;
	}

	/**
	 * 会员余额转账_试算
	 * 
	 * @param paymentRecord
	 */
	public FeeCalculateResultDTO preCaculate_AccountTransfer(TransferRecord transferRecord) {

		return new FeeCalculateResultDTO();
	}

	/**
	 * 支付_计费
	 * 
	 * @param paymentRecord
	 */
	public void caculate(final FeeCalculateResultDTO dto) {

		if (dto == null) {
			return;
		}

		notifyJmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				return session.createTextMessage(NotifyUtil.formatFee(dto));
			}
		});

	}

	public static void main(String args[]) {
		double a = 0.01D;
		System.out.println(BigDecimal.valueOf(a));
	}
}

package wusc.edu.pay.gateway.action.recharge;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;
import wusc.edu.pay.gateway.exceptions.GateWayException;


/**
 * 
 * @描述: 充值支付成功 .
 * @作者: WuShuicheng.
 * @创建: 2014-7-17,下午5:22:20
 * @版本: V1.0
 * 
 */
@Scope("prototype")
public class RechargeBankPaySuccessAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private PaymentQueryFacade paymentQueryFacade;

	/**
	 * 会员充值支付成功提示.
	 * 
	 * @return
	 * @throws GateWayException
	 * @throws UnsupportedEncodingException
	 */
	public String paySuccess() throws GateWayException, UnsupportedEncodingException {

		// 为保证两个页面同一个会话,必须存session
		RechargeOrderVo vo = (RechargeOrderVo) getSessionMap().get("RechargeOrderVo");
		if (vo == null) {
			throw BizException.SESSION_IS_OUT_TIME; // 抛出会话超时异常
		}
		String merchantNo = vo.getMerchantNo();
		String merchantOrderNo = vo.getMerchantOrderNo();

		if (StringUtil.isBlank(merchantNo) || StringUtil.isBlank(merchantOrderNo)) {
			return "rechargePayFalse";
		}

		PaymentOrder order = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo, merchantOrderNo, null);
		if (order == null) {
			return "rechargePayFalse";
		}

		if (order.getStatus().intValue() != OrderStatusEnum.SUCCESS.getValue()) {
			return "rechargePayFalse";
		} else {
			PaymentRecord paymentRecord = paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(merchantNo, merchantOrderNo, null, null, 100);

			if (paymentRecord == null) {
				return "rechargePayFalse";
			}

			// this.putData("trxTypeEnums", TrxTypeEnum.toList());
			// this.putData("merchantName", paymentRecord.getMerchantName());
			// this.putData("productName", order.getProductName());
			this.putData("payAmount", paymentRecord.getPayerPayAmount());
			this.putData("orderNo", order.getMerchantOrderNo());
			this.putData("trxNo", paymentRecord.getTrxNo());
			this.putData("bankOrderNo", paymentRecord.getBankOrderNo());
			// this.putData("createTime", paymentRecord.getCreateTime());
			this.putData("paySuccessTime", paymentRecord.getPaySuccessTime());

			return "rechargePaySuccess";
		}
	}
}

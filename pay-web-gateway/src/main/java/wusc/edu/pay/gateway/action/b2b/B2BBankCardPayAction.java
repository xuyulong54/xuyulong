package wusc.edu.pay.gateway.action.b2b;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.gateway.action.GateWayAction;
import wusc.edu.pay.gateway.biz.BankBiz;
import wusc.edu.pay.gateway.biz.MerchantBiz;
import wusc.edu.pay.gateway.biz.OrderBiz;
import wusc.edu.pay.gateway.exceptions.GateWayException;


/**
 * b2b
 * 
 */
@Scope("prototype")
public class B2BBankCardPayAction extends GateWayAction {

	private static final long serialVersionUID = 3550648555487612073L;
	private static final Log log = LogFactory.getLog(B2BBankCardPayAction.class);

	@Autowired
	private BankBiz bankBiz;
	@Autowired
	private MerchantBiz merchantBiz;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	private String html;

	public String execute() {

		// 为保证两个页面同一个会话,必须存session
		PaymentOrderVo vo = super.getPaymentOrderVo();
		// 判断session是否过期--提示session过期异常而不是服务异常
		if (vo == null) {
			throw BizException.SESSION_IS_OUT_TIME;
		}
		// payProductCode 支付产品编号
		String payProductCode = "";
		String bankCode = "";//银行编号
		PayWay payWay = null;
		String frpCode = getString("frpCode");
		if (!StringUtils.isBlank(frpCode)) {
			payProductCode = getString(frpCode);
			vo.setPayWayCode(frpCode);
			//银行bankcode
			bankCode = frpCode.split("_")[0];
			payWay = payWayFacade.getPayWayBypayWayCodeAndpayProductCode(frpCode, payProductCode);
		}else{
			
			//银行直连
			payWay = payWayFacade.getPayWayBypayWayCode_merchantNo_payWayCode(vo.getMerchantNo(),vo.getPayWayCode());
			if(payWay ==null){
				throw new GateWayException(GateWayException.DIRECT_CONNECT_PAYWAYCODE_IS_ERROR,"直连接口渠道编号不正确");
			}
			frpCode = vo.getPayWayCode();
			payProductCode = payWay.getPayProductCode();
			bankCode = frpCode.split("_")[0];
		}
		
		/** 交易业务开关限制 ---end **/
		tradeLimitFacade.validateSwitchAvailable(vo.getMerchantNo(), LimitTrxTypeEnum.PAY, payProductCode, frpCode);
		/** 调用交易限制 ---start **/
		tradeLimitFacade.validateTradeAmount(LimitTrxTypeEnum.PAY, payProductCode, frpCode, null,
				new BigDecimal(String.valueOf(vo.getOrderAmount())), vo.getMerchantNo());
		// 生成支付记录实体
		PaymentRecordVo paymentRecord = new PaymentRecordVo();
		paymentRecord.setMerchantOrderNo(vo.getMerchantOrderNo());
		paymentRecord.setPayProductCode(payProductCode);
		paymentRecord.setPayWayCode(frpCode);
		paymentRecord.setOrderAmount(BigDecimal.valueOf(Double.valueOf(vo.getOrderAmount())));
		paymentRecord.setPayWayCode(vo.getPayWayCode());
		paymentRecord.setPayWayName(payWay.getPayWayName());
		paymentRecord.setMerchantNo(vo.getMerchantNo());
		paymentRecord.setProductName(vo.getProductName());
		paymentRecord.setProductDesc(vo.getCallbackPara());
		paymentRecord.setBizType(TradeBizTypeEnum.ONLINE_ACQUIRING.getValue());
		paymentRecord.setPaymentType(TradePaymentTypeEnum.NET_B2B_PAY.getValue());
		paymentRecord.setPayerUserNo("");// 付款方编号
		paymentRecord.setPayerName("");
		paymentRecord.setPayerAccountType(AccountTypeEnum.MERCHANT.getValue());
		paymentRecord.setOrderIp(vo.getOrderIp());
		paymentRecord.setOrderRefererUrl(vo.getOrderRefererUrl());
		paymentRecord.setCur(vo.getCur());
		// step.1 设置商户信息
		merchantBiz.setMerchantInfo(vo, paymentRecord);
		paymentRecord.setReceiverAccountType(AccountTypeEnum.MERCHANT.getValue());

		// 设置银行接口
		paymentRecord.setPayInterfaceCode(bankBiz.getSpareBankChannel(payWay, vo.getMerchantNo()));
		// 设置支付流水号
		orderBiz.setPaymentTrxNo(paymentRecord);
		// 设置银行订单号
		orderBiz.setBankOrderNo(paymentRecord);

		log.info("商户编号:" + paymentRecord.getMerchantNo() + " b2b关网支付,支付流水号:" + paymentRecord.getTrxNo() + " [支付产品编号:"
				+ paymentRecord.getPayProductCode() + "][支付方式 ：" + paymentRecord.getPayWayCode() + "] [银行接口:"
				+ paymentRecord.getPayInterfaceCode() + "]" + "[付款方手续费: " + paymentRecord.getPayerAmountFee() + "]");
		html = bankBiz.buildHtml(paymentRecord.getPayInterfaceCode(), paymentRecord.getPaymentType(), paymentRecord.getBankOrderNo(),
				new Date(), paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getTrxNo(), this.getIpAddr(), vo.getProductName(), bankCode);
		// 保存支付记录并且创建银行订单
		orderBiz.createPaymentRecord(paymentRecord);

		return "redirect";
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}
}

package wusc.edu.pay.gateway.action.recharge;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;
import wusc.edu.pay.gateway.action.b2b.B2BBankCardPayAction;
import wusc.edu.pay.gateway.biz.BankBiz;
import wusc.edu.pay.gateway.biz.OrderBiz;
import wusc.edu.pay.gateway.exceptions.GateWayException;


/**
 * 选择银行后
 * 
 * @author healy
 * 
 */
@Scope("prototype")
public class RechargeBankCardPayAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 3550648555487612073L;
	private static final Log log = LogFactory.getLog(B2BBankCardPayAction.class);
	@Autowired
	private BankBiz bankBiz;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private PayWayFacade payWayFacade;
	@Autowired
	private TradeLimitFacade tradeLimitFacade;

	private String html;

	public String execute() {

		// 为保证两个页面同一个会话,必须存session
		RechargeOrderVo vo = (RechargeOrderVo) getSessionMap().get("RechargeOrderVo");
		// 判断session是否过期--提示session过期异常而不是服务异常
		if (vo == null) {
			throw BizException.SESSION_IS_OUT_TIME;
		}
		String bankCode = "";//银行编号
		PayWay payWay = null;
		String payProductCode = ""; //  支付产品编号
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
		/** 交易业务开关限制 ,这一步在门户中做 ---end **/
		/** 调用交易限制 ---start **/
		tradeLimitFacade.validateTradeAmount(LimitTrxTypeEnum.ACCOUNT_DEPOSIT, payProductCode, frpCode, null, new BigDecimal(String.valueOf(vo.getOrderAmount())), vo.getPayerUserNo());
		// 生成支付记录实体
		PaymentRecordVo paymentRecord = new PaymentRecordVo();
		paymentRecord.setMerchantOrderNo(vo.getMerchantOrderNo());
		paymentRecord.setReceiverAccountType(AccountTypeEnum.MERCHANT.getValue());
		paymentRecord.setPayProductCode(payProductCode);
		paymentRecord.setPayWayCode(frpCode);
		paymentRecord.setPayWayName(payWay.getPayWayName());
		paymentRecord.setOrderAmount(new BigDecimal(vo.getOrderAmount()));
		paymentRecord.setPayWayCode(vo.getPayWayCode());
		paymentRecord.setMerchantNo(vo.getMerchantNo());
		paymentRecord.setBizType(TradeBizTypeEnum.RECHARGE.getValue());
		paymentRecord.setPaymentType(TradePaymentTypeEnum.NET_B2C_PAY.getValue());
		paymentRecord.setPayerUserNo(vo.getPayerUserNo());// 付款方编号
		paymentRecord.setPayerName(vo.getPayerName());

		paymentRecord.setPayerAccountType(vo.getPayerAccountType());
		paymentRecord.setMerchantName(vo.getMerchantName()); // 收款方用户名
		paymentRecord.setMerchantNo(vo.getMerchantNo());
		paymentRecord.setCur(vo.getCur());

		paymentRecord.setPayWayName(payWay.getPayWayName());
		// 设置支付流水号
		orderBiz.setPaymentTrxNo(paymentRecord);
		// 设置银行接口
		paymentRecord.setPayInterfaceCode(bankBiz.getSpareBankChannel(payWay, vo.getMerchantNo()));
		// 设置银行订单号
		orderBiz.setBankOrderNo(paymentRecord);
		
		log.info("商户编号:" + paymentRecord.getMerchantNo() + "充值关网支付,支付流水号:" + paymentRecord.getTrxNo() + " [支付产品编号:" + paymentRecord.getPayProductCode() + "][支付方式 ：" + paymentRecord.getPayWayCode() 
				+ "] [银行接口:" + paymentRecord.getPayInterfaceCode() + "]" + " 银行代码 bankCode:" + bankCode);

		html = bankBiz.buildHtml(paymentRecord.getPayInterfaceCode(), paymentRecord.getPaymentType(), paymentRecord.getBankOrderNo(), new Date(), paymentRecord.getOrderAmount().doubleValue(), paymentRecord.getTrxNo(), this.getIpAddr(), vo.getProductName(), bankCode);

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

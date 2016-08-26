package wusc.edu.pay.gateway.action.recharge;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.enums.CurrencyTypeEnum;
import wusc.edu.pay.common.web.struts.Struts2ActionSupport;
import wusc.edu.pay.facade.bank.enums.BankBusTypeEnum;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.facade.trade.enums.OrderFromTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;
import wusc.edu.pay.gateway.biz.UserBiz;


/**
 * 
 * @描述: 会员账户充值.
 * @作者: .
 * @创建: 2014-6-23,下午5:06:08
 * @版本: V1.0
 * 
 */
public class MemberRechargeAction extends Struts2ActionSupport {

	private static final long serialVersionUID = 1L;

	@Autowired
	private UserBiz userBiz;

	@Autowired
	private PaymentFacade paymentFacade;

	@Autowired
	private PayWayFacade payWayFacade;

	/**
	 * 会员账户充值接口.<br/>
	 * 
	 * @return
	 */
	public String initRecharge() {

		String p1_Amount = getString_UrlDecode_UTF8("p1_Amount"); // 充值金额
		String p2_LoginName = getString_UrlDecode_UTF8("p2_LoginName"); // 会员登录名
		String p3_ReturnUrl = getString_UrlDecode_UTF8("p3_ReturnUrl"); // 回调地址
		String p4_OrderNo = getString_UrlDecode_UTF8("p4_OrderNo"); // 订单号(平台内部商户的商户订单号)
		String hmac = getString_UrlDecode_UTF8("hmac");

		RechargeOrderVo vo = new RechargeOrderVo();

		String merchantNo = PublicConfig.RECHARGE_MERCHANTNO; // 充值商户编号(平台内部商户,用于收款)
		vo.setMerchantName("平台默认充值商户");
		vo.setMerchantNo(merchantNo);
		vo.setMerchantOrderNo(p4_OrderNo);
		vo.setOrderAmount(p1_Amount);
		vo.setCur(CurrencyTypeEnum.RMB.getValue());
		vo.setProductName("账户充值");

		vo.setOrderFrom(OrderFromTypeEnum.RECHARGE.getValue());

		vo.setReturnUrl(p3_ReturnUrl);

		// 设置客户端ip
		vo.setOrderIp(this.getIpAddr());
		// 设置客户端refererUrl
		vo.setOrderRefererUrl(this.getRefererUrl());

		// stp.1 检查参数
		vo.validateParam();

		// stp.2 验证充值账号 并验证签名
		userBiz.verifyUser(vo, p2_LoginName, hmac);

		// stp.4 创建RechargeOrder
		paymentFacade.createRechargePaymentOrder(vo);

		String backUrl = p3_ReturnUrl + "?orderNo=" + vo.getMerchantOrderNo() + "&merchantNo=" + vo.getMerchantNo();
		vo.setReturnUrl(backUrl);
		// 为保证两个页面同一个会话,必须存session
		getSessionMap().put("RechargeOrderVo", vo);

//		putData("RechargeOrderVo", vo); // 在页面显示要充值的金额

		List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo(vo.getMerchantNo(), BankBusTypeEnum.NET_B2C.toString());
		putData("payWays", payWays);

		return "rechargeGateway";
	}

}

package wusc.edu.pay.gateway.action.b2b;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.facade.bank.enums.BankBusTypeEnum;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.facade.trade.enums.OrderFromTypeEnum;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.gateway.action.GateWayAction;
import wusc.edu.pay.gateway.biz.OrderBiz;
import wusc.edu.pay.gateway.biz.VerifyHmacBiz;
import wusc.edu.pay.gateway.exceptions.GateWayException;


public class B2BPayAction extends GateWayAction {

	private static final long serialVersionUID = 363220743610057508L;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private VerifyHmacBiz verifyHmacBiz;
	@Autowired
	private PayWayFacade payWayFacade;


	public String initB2B() throws GateWayException, UnsupportedEncodingException {

		String p1_MerchantNo = getString_UrlDecode_UTF8("p1_MerchantNo"); // 得到商户编号
		String p2_OrderNo = getString_UrlDecode_UTF8("p2_OrderNo"); // 得到商户订单号
		String p3_Amount = getString_UrlDecode_UTF8("p3_Amount"); // 得到订单金额
		String p4_Cur = getString_UrlDecode_UTF8("p4_Cur"); // 交易币种（固定值CNY）数据库中保存的是int类型。
		String p5_ProductName = getString_UrlDecode_UTF8("p5_ProductName"); // 商品名称
		String p6_Mp = getString_UrlDecode_UTF8("p6_Mp"); // 回调消息
		String p7_ReturnUrl = getString_UrlDecode_UTF8("p7_ReturnUrl"); // 返回url
		String p8_NotifyUrl = getString_UrlDecode_UTF8("p8_NotifyUrl"); // 消息通知Url
		String p9_FrpCode = getString_UrlDecode_UTF8("p9_FrpCode"); // 银行编码
		String pa_OrderPeriod =getString_UrlDecode_UTF8("pa_OrderPeriod");
		String pb_PayerLoginName =getString_UrlDecode_UTF8("pb_PayerLoginName");
		String hmac = getString_UrlDecode_UTF8("hmac");

		PaymentOrderVo vo = new PaymentOrderVo();
		vo.setMerchantNo(p1_MerchantNo);
		vo.setMerchantOrderNo(p2_OrderNo);
		vo.setOrderAmount(p3_Amount);
		vo.setCur(Integer.parseInt(p4_Cur));
		vo.setProductName(p5_ProductName);
		vo.setOrderFrom(OrderFromTypeEnum.GATEWAY_B2B.getValue());
		vo.setCallbackPara(p6_Mp);
		vo.setReturnUrl(p7_ReturnUrl);
		vo.setNotifyUrl(p8_NotifyUrl);
		vo.setPayWayCode(p9_FrpCode);
		vo.setOrderIp(super.getIpAddr());
		vo.setOrderRefererUrl(super.getRefererUrl());
		vo.setOrderPeriod(Integer.valueOf(pa_OrderPeriod));
		vo.setPayerLoginName(pb_PayerLoginName);

		// stp.1 检查参数
		vo.validateParam();
		// stp.2 验证签名、商户状态
		verifyHmacBiz.verifyNetPay(vo, hmac);
		// stp.3 检查商户收款功能是否打开 在选择银行之后调用 否则得调用两次
		// merchantBiz.checkSwitchPay(vo);

		// stp.4 创建PaymentOrder
		orderBiz.createPaymentOrder(vo);
		super.setPaymentOrderVo(vo); // 每次提交订单过来，都放入Session中

		// 获取可用的银行列表(直连银行跳过此步骤)
		if (StringUtils.isEmpty(p9_FrpCode)) {
			putData("paymentOrderVo", vo);
			List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo(vo.getMerchantNo(), BankBusTypeEnum.NET_B2B.toString());
			putData("payWays", payWays);
			putData("pwdFind", PublicConfig.PROTAL_URL+"memberLookForLoginPwd_loginPwdCheckLoginNameUI.action");
			putData("registerUrl", PublicConfig.PROTAL_URL+"merchantRegister_checkLoginNameUI.action");
			return "b2bGateway";
		} else {
			return "redirect";
		}
	}

}

package wusc.edu.pay.gateway.action.b2c;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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


/**
 * 接收支付指令
 * 
 * @author healy
 * 
 */
public class InitPayAction extends GateWayAction {
	private final static Log log = LogFactory.getLog(InitPayAction.class);
	private static final long serialVersionUID = 1L;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private VerifyHmacBiz verifyHmacBiz;
	@Autowired
	private PayWayFacade payWayFacade;

	public String init() throws GateWayException, UnsupportedEncodingException {

		String p1_MerchantNo = getString_UrlDecode_UTF8("p1_MerchantNo"); // 得到商户编号
		String p2_OrderNo = getString_UrlDecode_UTF8("p2_OrderNo"); // 得到商户订单号
		String p3_Amount = getString_UrlDecode_UTF8("p3_Amount"); // 得到订单金额
		String p4_Cur = getString_UrlDecode_UTF8("p4_Cur"); // 交易币种（固定值CNY）数据库中保存的是int类型。
		String p5_ProductName = getString_UrlDecode_UTF8("p5_ProductName"); // 商品名称
		String p6_Mp = getString_UrlDecode_UTF8("p6_Mp"); // 回调消息
		String p7_ReturnUrl = getString_UrlDecode_UTF8("p7_ReturnUrl"); // 返回url
		String p8_NotifyUrl = getString_UrlDecode_UTF8("p8_NotifyUrl"); // 消息通知Url
		String p9_FrpCode = getString_UrlDecode_UTF8("p9_FrpCode"); // 银行编码
		String pa_OrderPeriod = getString_UrlDecode_UTF8("pa_OrderPeriod");
		String pb_PayerLoginName = getString_UrlDecode_UTF8("pb_PayerLoginName");
		String hmac = getString_UrlDecode_UTF8("hmac");

		log.info("p1_MerchantNo=" + p1_MerchantNo + " " + "p2_OrderNo" + p2_OrderNo + " " + "p3_Amount=" + p3_Amount + " " + "p4_Cur=" + p4_Cur + " " + "p5_ProductName=" + p5_ProductName + " " + "p6_Mp=" + p6_Mp + " " + "p7_ReturnUrl=" + p7_ReturnUrl + " " + "p8_NotifyUrl=" + p8_NotifyUrl+"p9_FrpCode="+p9_FrpCode);

		PaymentOrderVo vo = new PaymentOrderVo();
		vo.setMerchantNo(p1_MerchantNo);
		vo.setMerchantOrderNo(p2_OrderNo);
		vo.setOrderAmount(p3_Amount);
		vo.setCur(Integer.parseInt(p4_Cur));
		vo.setProductName(p5_ProductName);
		vo.setCallbackPara(p6_Mp);
		vo.setOrderFrom(OrderFromTypeEnum.GATEWAY_B2C.getValue());
		vo.setReturnUrl(p7_ReturnUrl);
		vo.setNotifyUrl(p8_NotifyUrl);
		vo.setPayWayCode(p9_FrpCode);
		vo.setOrderPeriod(StringUtils.isBlank(pa_OrderPeriod) ? null : Integer.valueOf(pa_OrderPeriod));
		vo.setPayerLoginName(pb_PayerLoginName);

		// 设置客户端ip
		vo.setOrderIp(this.getIpAddr());
		// 设置客户端refererUrl
		vo.setOrderRefererUrl(this.getRefererUrl());

		// stp.1 检查参数
		vo.validateParam();
		// stp.2 验证签名、商户状态
		verifyHmacBiz.verifyNetPay(vo, hmac);
		// stp.3 检查商户收款功能是否打开 在选择银行之后调用 否则得调用两次
		// merchantBiz.checkSwitchPay(vo);

		// stp.4 创建paymentOrder
		orderBiz.createPaymentOrder(vo);
		super.setPaymentOrderVo(vo); // 每次提交订单过来，都放入Session中

		// 获取可用的银行列表(直连银行跳过此步骤)
		if (StringUtils.isEmpty(p9_FrpCode)) {
			// 为保证两个页面同一个会话,必须存session
			List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo(vo.getMerchantNo(), BankBusTypeEnum.NET_B2C.toString());
			putData("payWays", payWays);
			putData("pwdFind", PublicConfig.PROTAL_URL + "memberLookForLoginPwd_loginPwdCheckLoginNameUI.action");
			putData("registerUrl", PublicConfig.PROTAL_URL + "merchantRegister_checkLoginNameUI.action");
			return "payGateway";
		} else {
			return "redirect";
		}
	}

	public static String getRandomString(int length) {

		if (length <= 0) {

			return "";

		}

		char[] randomChar = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',

		'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };

		Random random = new Random();

		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < length; i++) {

			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);

		}

		return stringBuffer.toString();

	}
	
	/**
	 * 
	 * @描述: 给重新选择银行卡支付使用.
	 * @作者: Along.shen .
	 * @创建时间:2015-1-12,上午11:18:20.
	 * @版本:
	 */
	public String getBankListPage(){
		PaymentOrderVo vo = (PaymentOrderVo) getSessionMap().get("PaymentOrderVo");
		List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo(vo.getMerchantNo(), BankBusTypeEnum.NET_B2C.toString());
		putData("payWays", payWays);
		putData("pwdFind", PublicConfig.PROTAL_URL + "memberLookForLoginPwd_loginPwdCheckLoginNameUI.action");
		putData("registerUrl", PublicConfig.PROTAL_URL + "merchantRegister_checkLoginNameUI.action");
		return "payGateway";
	}

}

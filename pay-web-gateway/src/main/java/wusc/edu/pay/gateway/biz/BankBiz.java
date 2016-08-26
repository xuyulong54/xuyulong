package wusc.edu.pay.gateway.biz;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.facade.banklink.netpay.service.BankFacade;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentParam;
import wusc.edu.pay.facade.banklink.netpay.vo.PrePaymentResult;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.payrule.entity.BankBranch;
import wusc.edu.pay.facade.payrule.entity.PayWay;
import wusc.edu.pay.facade.payrule.service.BankBranchFacade;
import wusc.edu.pay.gateway.exceptions.GateWayException;

import com.alibaba.fastjson.JSONObject;

@Component("bankBiz")
public class BankBiz {

	@Autowired
	private BankFacade bankFacade;
	@Autowired
	private TradeLimitFacade tradeLimitFacade;
	@Autowired
	private BankBranchFacade bankBranchFacade;

	private static final Log log = LogFactory.getLog(BankBiz.class);

	// 获取银行接口
	public String getSpareBankChannel(PayWay payWay, String userNo) throws GateWayException {
		log.info("支付关网获取银行接口方法 ，支付方式为：" + payWay.getPayWayCode());
		/** 调用接口限制 **/
		boolean interfaceIsPay = false;
		interfaceIsPay = tradeLimitFacade.validatePayInterfaceAvailable(userNo, payWay.getPayWayCode(), payWay.getDefaultPayInterface());
		if (!interfaceIsPay) {// 接口没有被限制
			return payWay.getDefaultPayInterface();
		} else {// 备用接口 //如果备也被限制则不能进行交易了
				// 获取备用银行渠道
			BankBranch bankBranch = bankBranchFacade.getByFrpCode(payWay.getPayWayCode());
			String spareBankChannelCode = "";// 设为备接口
			if (payWay.getDefaultPayInterface().equals(bankBranch.getDefaultBankChannelCode())) {
				spareBankChannelCode = bankBranch.getSpareBankChannelCode();
			} else {
				spareBankChannelCode = bankBranch.getDefaultBankChannelCode();
			}

			if (org.apache.commons.lang3.StringUtils.isEmpty(spareBankChannelCode)) {
				throw new GateWayException(GateWayException.GATEWAY_INTERFACE_IS_LIMIT);
			}
			interfaceIsPay = tradeLimitFacade.validatePayInterfaceAvailable(userNo, payWay.getPayWayCode(), spareBankChannelCode);
			if (interfaceIsPay) {// 备也被限制 此时不能交易，抛出异常
				throw new GateWayException(GateWayException.GATEWAY_INTERFACE_IS_LIMIT);
			}
			return spareBankChannelCode;
		}
	}

	/**
	 * 生成银行请求html
	 * 
	 * @param bankChannelCode
	 * @param trxType
	 * @param bankOrderNo
	 * @param orderDate
	 * @param payAmount
	 * @param trxNo
	 * @param ipStr
	 * @return
	 * @throws BizException
	 */
	public String buildHtml(String bankChannelCode, int trxType, String bankOrderNo, Date orderDate, double payAmount, String trxNo,
			String ipStr, String productName, String bankCode) throws BizException {
		PrePaymentParam prePaymentParam = new PrePaymentParam();
		prePaymentParam.setBankOrderNo(bankOrderNo);
		prePaymentParam.setOrderDate(orderDate);
		prePaymentParam.setPayAmount(BigDecimal.valueOf(Double.valueOf(payAmount)));
		prePaymentParam.setPayerIp(ipStr);
		prePaymentParam.setPayInterface(bankChannelCode);
		prePaymentParam.setPaymentTrxNo(trxNo);
		prePaymentParam.setProductName(productName);
		prePaymentParam.setBankCode(bankCode);

		log.debug("prePaymentParam:" + JSONObject.toJSONString(prePaymentParam));
		
		PrePaymentResult result = bankFacade.preparePay(prePaymentParam);

		String html = "<form action=\"" + result.getUrl() + "\"" + " id=\"toPay\" method=\"post\">";
		log.info("test==========="+result.getParamMap().toString());
		for (String key : result.getParamMap().keySet()) {
			html += "<input name=\"" + key + "\" value=\"" + result.getParamMap().get(key) + "\" type=\"hidden\" />";
		}
		html += "</form><script type=\"text/javascript\">document.getElementById(\"toPay\").submit();</script>";

		log.info("请求form:" + html);

		return html;
	}
}

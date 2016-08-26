package wusc.edu.pay.gateway.action.b2c;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import wusc.edu.pay.common.config.PublicConfig;
import wusc.edu.pay.common.constant.PublicStatus;
import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.utils.string.StringTools;
import wusc.edu.pay.common.utils.token.TokenProductFactory;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.enums.UserTypeEnum;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;
import wusc.edu.pay.gateway.action.GateWayAction;
import wusc.edu.pay.gateway.biz.MerchantBiz;
import wusc.edu.pay.gateway.biz.OrderBiz;
import wusc.edu.pay.gateway.exceptions.GateWayException;


/**
 * 余额支付action
 * 
 * @desc
 * @author shenjialong
 * @date 2014-7-16,上午11:32:08
 */
public class BalancePayAction extends GateWayAction {
	private static final long serialVersionUID = 1L;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private OrderBiz orderBiz;
	@Autowired
	private TradeLimitFacade tradeLimitFacade;
	@Autowired
	private MerchantBiz merchantBiz;

	private static final Log log = LogFactory.getLog(BalancePayAction.class);

	public String balancePay() throws Exception {
		String token = (String) getSessionMap().get("token");
		if (token == null) {
			super.putData("msg", "不能重复提交表单数据!");
			return super.loginPayment();// 跳到父级去区别Login还是 into
		} else {
			getSessionMap().remove("token");
		}
		log.info("进入账户余额支付密码验证");
		String trxPassWord = DigestUtils.sha1Hex((StringTools.stringToTrim(super.getPwd("tradePwd"))));
		// 验证账户密码是否有效
		String errorMsg = merchantBiz.validateAccountPassWord(super.getCurrentUserInfo().getLoginName(), trxPassWord);
		if (errorMsg != null) {
			super.putData("msg", errorMsg);
			// 确认订单信息
			return super.loginPayment();// 跳到父级去区别Login还是 into
		}

		// 进入下一个Action 为安全这里可以生成一个Token 在下一个Action中验证。
		String gateToken = TokenProductFactory.getInstallDES64().productToken("GATEPAY", super.getCurrentUserInfo().getUserNo());
		getSessionMap().put("token", gateToken);
		return "accountBalancePayComplete";
	}

	/**
	 * 账户余额完成
	 * 
	 * @return
	 * @throws GateWayException
	 * @throws IOException
	 * @throws ServletException
	 */
	public void accountBalancePayComplete() throws GateWayException, ServletException, IOException {
		log.info("进入账户余额支付");
		UserInfo userInfo = super.getCurrentUserInfo();
		PaymentOrderVo vo = super.getPaymentOrderVo();
		/** token 识别 */
		String token = (String) getSessionMap().get("token");
		log.info("token:" + token);
		if (StringUtils.isEmpty(token) || !TokenProductFactory.getInstallDES64().decrypt(token).contains(userInfo.getUserNo())) {
			throw BizException.TOKEN_IS_ILLICIT;
		}
		getSessionMap().remove("token");
		String orderNo = vo.getMerchantOrderNo();
		String merchantNo = vo.getMerchantNo();
		String merchantName = vo.getMerchantName();
		Double orderAmount = Double.valueOf(vo.getOrderAmount());
		String userNo = userInfo.getUserNo();
		Integer userType = userInfo.getUserType();
		String userName = userInfo.getRealName() == null ? "" : userInfo.getRealName();

		/** 调用交易限制 ---start **/
		tradeLimitFacade.validateTradeAmount(LimitTrxTypeEnum.PAY, PublicConfig.BALANCE_PAY, PublicConfig.BALANCE_PAY, null, new BigDecimal(String.valueOf(orderAmount)), merchantNo);
		tradeLimitFacade.validateSwitchAvailable(merchantNo, LimitTrxTypeEnum.PAY, PublicConfig.BALANCE_PAY, PublicConfig.BALANCE_PAY);
		/** 调用交易限制 ---end **/

		// 创建交易记录
		log.info("完成用户限制验证 用户编号:" + userNo + "订单号：" + orderNo + "开始创建交易记录");
		PaymentRecordVo paymentRecord = new PaymentRecordVo();
		paymentRecord.setMerchantName(merchantName);
		paymentRecord.setPayerName(userName);
		paymentRecord.setBizType(TradeBizTypeEnum.ONLINE_ACQUIRING.getValue());
		paymentRecord.setPaymentType(TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue());
		paymentRecord.setMerchantOrderNo(orderNo);
		paymentRecord.setMerchantNo(vo.getMerchantNo());
		paymentRecord.setOrderAmount(new BigDecimal(orderAmount.toString()));
		paymentRecord.setMerchantNo(merchantNo);
		paymentRecord.setProductName(vo.getProductName());
		paymentRecord.setProductDesc(vo.getCallbackPara());
		paymentRecord.setReceiverAccountType(AccountTypeEnum.MERCHANT.getValue());
		paymentRecord.setPayerUserNo(userNo);
		paymentRecord.setPayerAccountType(EnumChangeUtils.getAccountType(userType));// 将用户类型转换成战虎类型
		paymentRecord.setReturnUrl(vo.getReturnUrl());
		paymentRecord.setNotifyUrl(vo.getNotifyUrl());
		paymentRecord.setCur(vo.getCur());
		// 从system属性文件中加载出余额支付的产品编号和frpcode（默认的都是ACCOUNT_BALANCE_PAY）
		paymentRecord.setPayProductCode(PublicConfig.BALANCE_PAY);
		paymentRecord.setPayWayCode(PublicConfig.BALANCE_PAY);
		paymentRecord.setPayInterfaceCode(""); // step.4 设置银行渠道
		paymentRecord.setStatus(OrderStatusEnum.SUCCESS.getValue()); // step.7_设置订单状态->成功

		paymentRecord.setBankOrderNo("");
		orderBiz.setBankOrderNo(paymentRecord); // step.10 设置银行订单号
		orderBiz.setPaymentTrxNo(paymentRecord); // step.11 设置支付流水号
		paymentRecord.setOrderIp(this.getIpAddr());// step.12 获取客户端ip地址
		paymentRecord.setPayerAmountFee(new BigDecimal(0));// step.13
															// 设置付款方手续费(余额支付手续费设为0)
		
		log.info("支付处理前风控 验证通过  订单号：" + orderNo + "支付产品编号：" + paymentRecord.getPayProductCode() + "支付方式编号: " + paymentRecord.getPayWayCode());
		orderBiz.completeBalancePayment(paymentRecord);
		getHttpRequest().getRequestDispatcher("bankPaySuccess_paySuccess.action?merchantNo=" + paymentRecord.getMerchantNo() + "&orderNo=" + paymentRecord.getMerchantOrderNo() + "&oa=" + orderAmount).forward(getHttpRequest(), getHttpResponse());
	}

	public String balancePayErr() throws GateWayException, UnsupportedEncodingException {
		log.info("goException");
		String errCode = getString("errCode");
		putData("errCode", errCode);
		return "bizException";
	}

	/**
	 * 账户登录
	 * 
	 * @return
	 * @throws GateWayException
	 * @throws IOException
	 */
	public String validateLogin() throws GateWayException, IOException {
		UserInfo userInfo = super.getCurrentUserInfo();
		if (userInfo == null) {
			String trxPassWord = DigestUtils.sha1Hex(super.getPwd("tradePwd"));
			String loginName = getString("username");
			userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
			String backAction = "b2cLogin";
			// 判断是否有这个用户
			if (userInfo == null) {
				super.putData("msg", "登录名或密码不正确!");
				return backAction;
			}
			// 验证账户密码是否有效
			String errorMsg = merchantBiz.valiUserPassWord(loginName, trxPassWord, userInfo.getUserType());
			if (errorMsg != null) {
				super.putData("msg", errorMsg);
				return backAction;
			}
			// 如果是商户
			if (userInfo.getUserType() == UserTypeEnum.MERCHANT.getValue()) {
				if (super.getPaymentOrderVo() == null) {
					throw BizException.SESSION_IS_OUT_TIME;
				}
				String merchantNo = super.getPaymentOrderVo().getMerchantNo();
				if (userInfo.getUserNo().equals(merchantNo)) {
					super.putData("msg", "不能购买自己商城的商品!");
					return backAction;
				}
			}

			// 查询用户账户
			Account userAccount = accountQueryFacade.getAccountByUserNo(userInfo.getUserNo());
			if (userAccount == null) {
				super.putData("msg", "用户的账户不合法!");
				return backAction;
			}
			if (userAccount.getStatus() == PublicStatus.INACTIVE) {
				super.putData("msg", "该账户已被冻结，请联系客服!");
				return backAction;
			}
			// 设置用户
			super.setCurrentUserInfo(userInfo);
		}
		
		Double orderAmount = Double.valueOf(super.getPaymentOrderVo().getOrderAmount());
		// 确认订单信息
		confirmOrderInfo(orderAmount, userInfo.getLoginName(), userInfo);
		return "confirmPayment";
	}

	/**
	 * 获取预留信息
	 */
	public void queryObligateInfo() {
		// ToBankPayVo vo = (ToBankPayVo) getSessionMap().get("ToBankPayVo");
		// 验证商户是否存在白名单
		// merchantBiz.isCheckMerchantIps(vo.getMerchantNo());
		String loginName = getString("username");
		if (!StringUtil.isBlank(loginName)) {
			UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
			if (null != userInfo.getGreeting()) {
				getOutputMsg().put("MSG", userInfo.getGreeting());
			}
			getOutputMsg().put("STATE", "SUCCESS");
		}
		outPrint(getHttpResponse(), JSONObject.fromObject(getOutputMsg()));
	}

	/**
	 * 重先选择余额账户支付
	 */
	public void changeBalanceAccount() {
		this.setCurrentUserInfo(null);

		try {
			this.getHttpResponse().sendRedirect("gateway_loginPayment.action");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

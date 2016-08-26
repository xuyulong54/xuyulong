package wusc.edu.pay.webservice.merchant.biz;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.enums.BankCode;
import wusc.edu.pay.common.enums.PublicStatusEnum;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.validate.ValidateUtils;
import wusc.edu.pay.facade.account.enums.AccountTypeEnum;
import wusc.edu.pay.facade.bank.enums.BankBusTypeEnum;
import wusc.edu.pay.facade.bank.exceptions.BankBizException;
import wusc.edu.pay.facade.payrule.entity.Frp;
import wusc.edu.pay.facade.payrule.entity.vo.PayWayVo;
import wusc.edu.pay.facade.payrule.service.FrpFacade;
import wusc.edu.pay.facade.payrule.service.PayWayFacade;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.OrderFromTypeEnum;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.entity.UserOperator;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserAuditFacade;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserManagementFacade;
import wusc.edu.pay.facade.user.service.UserOperatorFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.webservice.merchant.enums.FastPayMessageEnum;

import com.alibaba.fastjson.JSONObject;

@Component("fastPayBiz")
public class FastPayBiz {
	private static final Log log = LogFactory.getLog(FastPayBiz.class);
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private PaymentFacade paymentFacade;
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private FrpFacade frpFacade;
	@Autowired
	private UserOperatorFacade userOperatorFacade;//
	@Autowired
	private UserManagementFacade userManagementFacade;
	@Autowired
	private UserBankAccountFacade userBankAccountFacade;
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private PayWayFacade payWayFacade;
	// @Autowired
	// private RemitBankTypeFacade remitBankTypeFacade;
	@Autowired
	private UserAuditFacade userAuditFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;

	public Map<String, Object> registerMember(Map<String, Object> paramMap, HttpSession session) {
		String p2_LoginName = String.valueOf(paramMap.get("r2_LoginName"));
		String p3_LoginPassword = String.valueOf(paramMap.remove("p3_LoginPassword"));// 将要返回的map中的密码去除
		String payPwd = String.valueOf(paramMap.remove("payPwd"));// 将要返回的map中的密码去除
		UserOperator userOperator = userOperatorFacade.getUserOperatorByLoginName(p2_LoginName.trim());
		if (userOperator != null) {
			paramMap.put("r3_Code", FastPayMessageEnum.FASTPAY_ERRPHONENUM.getValue() + "");
			paramMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_ERRPHONENUM.getDesc());
		} else {
			userManagementFacade.registerMember(p2_LoginName, DigestUtils.sha1Hex(p3_LoginPassword),
					DigestUtils.sha1Hex(payPwd), "", "", "", "", "", "", "", "", "");
			userOperator = userOperatorFacade.getUserOperatorByLoginName(p2_LoginName.trim());
			MemberInfo memberInfo = memberInfoFacade.getMemberByUserNo(userOperator.getUserNo());
			session.setAttribute("loginMember", userOperator);
			session.setAttribute("memberAccountNo", memberInfo.getAccountNo());
			paramMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue() + "");
			paramMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
		}
		return paramMap;
	}

	/**
	 * 获取银行卡列表
	 * 
	 * @param merchantNo
	 * */
	public Map<String, Object> listFastPayBank(Map<String, Object> paramMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo((String) paramMap
				.get("p1_MerchantNo"));
		if (merchantOnline != null) {
			// 为保证两个页面同一个会话,必须存session
			List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo((String) paramMap.get("p1_MerchantNo"),
					BankBusTypeEnum.FAST.toString());
			log.info("商户ID【" + merchantOnline.getId() + "】");
			int payWaysSize = payWays.size();
			if (payWays != null && payWaysSize != 0) {
				String bankTypes[] = new String[payWaysSize];
				Map<String, Object> tempParamMap = null;
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				for (int i = 0; i < payWaysSize; i++) {
					tempParamMap = new HashMap<String, Object>();
					String frpCode = payWays.get(i).getPayWayCode();
					bankTypes[i] = frpCode.substring(0, frpCode.indexOf("_"));
					String desc = BankCode.valueOf(bankTypes[i]).getDesc();
					tempParamMap.put(bankTypes[i], desc);

					// JSONObject jsons = new JSONObject();
					// jsons.putAll(tempParamMap);
					// JSONObject json = new JSONObject(tempMap);
					list.add(tempParamMap);

					// JSONObject json = new JSONObject(tempParamMap);
					// list.add(json.toString());
				}
				String listJson = JSONObject.toJSONString(list);
				log.info("获取银行列表：" + listJson);
				returnMap.put("r6_bankList", listJson);
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
			} else {
				log.info("查询不到商户业务类型设置");
				// 商户业务设置表为空
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_MERCHANTBUSERR.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_MERCHANTBUSERR.getDesc());
			}
		} else {
			log.info("商户不存在");
			// 商户不存在
			returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getValue());
			returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getDesc());
		}
		return returnMap;
	}

	/**
	 * 绑定银行卡
	 * 
	 * @param paramMap
	 * @param session
	 * @return
	 */
	public Map<String, Object> bindBankCard(Map<String, Object> paramMap, HttpSession session) {
		Map<String, Object> returnMap = new HashMap<String, Object>();

		String merchantNo = String.valueOf(paramMap.get("MerchantNo"));
		String orderNo = String.valueOf(paramMap.get("orderNo"));
		PaymentOrder paymentOrder = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo, orderNo,
				null);
		if (paymentOrder != null) {
			paramMap.put("orderAmount", paymentOrder.getOrderAmount());
		}

		String bankAccountNo = String.valueOf(paramMap.get("p16_BankAccountNo"));
		int isOpenBind = Integer.parseInt(String.valueOf(paramMap.get("p11_IsBindBankAccount")));
		UserOperator userOperator = (UserOperator) paramMap.get("loginMember");
		String loginName = userOperator.getLoginName();
		String memberAccountNo = String.valueOf(paramMap.get("memberAccountNo"));
		int bankAccountType = Integer.parseInt(String.valueOf(paramMap.get("p17_BankAccountType")));
		String mobileNo = String.valueOf(paramMap.get("p15_Mobileno"));
		String cardNo = String.valueOf(paramMap.get("p13_CardNo"));
		int cardType = Integer.parseInt(String.valueOf(paramMap.get("p12_CardType")));
		String frpCode = String.valueOf(paramMap.get("p9_FrpCode"));
		String realName = String.valueOf(paramMap.get("p14_RealName"));
		UserBankAccount userBankAccount = new UserBankAccount();
		userBankAccount.setIsAuth(PublicStatusEnum.ACTIVE.getValue());
		userBankAccount.setIsDelete(PublicStatusEnum.INACTIVE.getValue());
		userBankAccount.setIsDefault(PublicStatusEnum.INACTIVE.getValue());
		userBankAccount.setLoginName(loginName);
		userBankAccount.setUserNo(userOperator.getUserNo());
		RemitBankType remitBankType = null;// remitBankTypeFacade.getByBankCode(frpCode);
		// RemitBankType remitBankType =
		// remitBankTypeFacade.getByBankCode(frpCode);
		if (remitBankType != null) {
			userBankAccount.setBankName(remitBankType.getTypeName());
		} else {
			log.info(frpCode + ", 未知的银行渠道转换的银行");
			userBankAccount.setBankName("未知银行");
		}
		userBankAccount.setBankCode(frpCode);
		userBankAccount.setBankAccountName(realName);
		userBankAccount.setBankAccountNo(bankAccountNo);
		userBankAccount.setBankAccountType(bankAccountType);
		userBankAccount.setAuthTime(new Date());
		userBankAccount.setCardType(cardType);
		userBankAccount.setCardNo(cardNo);
		userBankAccount.setMobileNo(mobileNo);
		userBankAccount.setBankAccountAddress("");
		long creatNum = userBankAccountFacade.creatUserBankAccount(userBankAccount);
		// userBankAccountFacade.fastPayBindCard(bankAccountNo, bankAccountType,
		// loginName, memberAccountNo, mobileNo, cardNo, cardType, frpCode,
		// realName, isOpenBind);
		if (creatNum > 0) {
			returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue());
			returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
		} else {
			return returnMap;
		}
		UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(loginName);
		log.info(">>>>>>>>>" + JSONObject.toJSONString(userInfo) + "<<<<<<<<<<");
		if (userInfo != null && PublicStatusEnum.INACTIVE.getValue() == userInfo.getIsRealNameAuth().intValue()) {
			log.info("用户" + loginName + "去实名认证");
			long id = userAuditFacade.createUserAuditRecordRealName(cardNo, "", "", new Date(), userOperator, realName,
					"中国", "自由");
			userAuditFacade.auditPass_RealName(id, "", "", "");
		} else {
			log.info(userInfo + "为空，或者已经实名认证过");
		}
		// 调用银行接口，绑定银行卡
		log.info("银行卡绑定");// accountQueryFacade
		returnMap.put("orderNo", orderNo);
		if (isOpenBind != 1) {
			log.info("绑定并支付接口");
			List<UserBankAccount> list = userBankAccountFacade.listFastBankAccountByMemberUserNo(userOperator
					.getUserNo());
			if (list.size() > 0) {
				// 已绑定银行卡
				session.setAttribute("fastPayAccount", list);
			}
			returnMap.put("fastPayAccount", list);
		}
		return returnMap;
	}

	public Map<String, Object> creatFastPayOrder(Map<String, Object> paramMap) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String orderNo = (String) paramMap.get("p2_OrderNo");
		Double orderAmount = Double.parseDouble(String.valueOf(paramMap.get("p4_Amount")));
		String merchantNo = (String) paramMap.get("p1_MerchantNo");
		String orderTime = (String) paramMap.get("p3_OrderTime");
		MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);
		if (merchantOnline == null) {
			returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getValue());
			returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getDesc());
		} else {
			PaymentOrder paymentOrder = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo,
					orderNo, null);
			log.info("order is paymentOrderParam ： " + paymentOrder + ",orderNo:" + orderNo + ",merchantNo:"
					+ merchantNo);
			if (paymentOrder != null) {
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_ERRORDER.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_ERRORDER.getDesc());
			} else {
				// 创建快捷支付订单表
				PaymentOrderVo paymentOrderVo = new PaymentOrderVo();
				paymentOrderVo.setMerchantNo(merchantNo);
				paymentOrderVo.setMerchantName(merchantOnline.getFullName());
				paymentOrderVo.setMerchantOrderNo(orderNo);
				paymentOrderVo.setOrderAmount(orderAmount.toString());
				paymentOrderVo.setCur(Integer.parseInt(String.valueOf(paramMap.get("p5_Cur"))));
				paymentOrderVo.setProductName(String.valueOf(paramMap.get("p6_ProductName")));
				paymentOrderVo.setCallbackPara("");
				paymentOrderVo.setOrderFrom(OrderFromTypeEnum.MOBILE.getValue());
				paymentOrderVo.setReturnUrl("");
				paymentOrderVo.setNotifyUrl(String.valueOf(paramMap.get("p8_NotifyUrl")));
				paymentOrderVo.setPayWayCode("");
				paymentOrderVo.setOrderIp("");
				paymentOrderVo.setOrderRefererUrl("");
				paymentOrderVo.setTrxModel(TrxModelEnum.IMMEDIATELY.getValue());
				try {
					checkOrder(paymentOrderVo);
				} catch (UnsupportedEncodingException ue) {
					returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue());
					returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
				}

				paymentFacade.createPaymentOrder(paymentOrderVo);
				returnMap.put("MerchantName", merchantOnline.getFullName());
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
			}
		}
		return returnMap;
	}

	/**
	 * 返回枚举类
	 * 
	 * @param vo
	 * @throws UnsupportedEncodingException
	 */
	private FastPayMessageEnum checkOrder(PaymentOrderVo vo) throws UnsupportedEncodingException {

		if (!ValidateUtils.isDouble(vo.getOrderAmount())) {
			return FastPayMessageEnum.FASTPAY_AMOUNTERR;
		}

		// 订单金额不能小于0
		if (AmountUtil.bigger(0, Double.parseDouble(vo.getOrderAmount()))) {
			return FastPayMessageEnum.FASTPAY_AMOUNTERR;
		}

		// 检查商品名称长度
		if (StringUtils.isEmpty(vo.getProductName())) {
			return FastPayMessageEnum.FASTPAY_GOODSNAMEERR;
		}

		// 检查returnUrl
		if (!ValidateUtils.isURL(vo.getReturnUrl())) {
			return FastPayMessageEnum.FASTPAY_DATAERR;
		}

		// 检查notifyUrl
		if (!ValidateUtils.isURL(vo.getNotifyUrl())) {
			return FastPayMessageEnum.FASTPAY_DATAERR;
		}

		// 检查商户订单号
		if (StringUtils.isEmpty(vo.getMerchantNo())) {
			return FastPayMessageEnum.FASTPAY_ERRORDER;
		}

		if (StringUtils.length(vo.getMerchantNo()) > 30) {
			return FastPayMessageEnum.FASTPAY_DATAERR;
		}

		return null;
	}

	public Map<String, Object> bindCardAndPay(Map<String, Object> paramMap, HttpSession session) {
		Map<String, Object> encryptMap = bindBankCard(paramMap, session);
		if (FastPayMessageEnum.FASTPAY_SUCCESS.getValue() == (Integer) encryptMap.get("r3_Code")) {
			// 绑定订单成功
			encryptMap = fastPay(paramMap, session);
			encryptMap.put("r2_OrderNo", paramMap.get("orderNo"));
			encryptMap.put("r1_MerchantNo", paramMap.get("MerchantNo"));
		} else {
			encryptMap.put("r3_Code", FastPayMessageEnum.FASTPAY_BINDINGCARDFAIL.getValue());
			encryptMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_BINDINGCARDFAIL.getDesc());
		}
		return encryptMap;
	}

	/**
	 * 快捷支付
	 * */
	public Map<String, Object> fastPay(Map<String, Object> paramMap, HttpSession session) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		String frpCode = String.valueOf(paramMap.get("p9_FrpCode"));
		frpCode = frpCode + "_FAST";
		String orderNo = String.valueOf(paramMap.get("orderNo"));
		returnMap.put("orderNo", orderNo);
		log.info("order no is :" + orderNo);
		String merchantNo = String.valueOf(paramMap.get("MerchantNo"));
		log.info("查询到的商户编号【" + merchantNo + "】");
		MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);
		if (merchantOnline == null) {
			returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getValue());
			returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_NULLMERCHANT.getDesc());
		} else {

			PaymentOrder paymentOrder = paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo,
					orderNo, null);
			if (paymentOrder == null) {
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_NULLORDER.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_NULLORDER.getDesc());
				return returnMap;
			}
			paramMap.put("orderAmount", paymentOrder.getOrderAmount());

			UserOperator userOperator = (UserOperator) paramMap.get("loginMember");

			// 支付成功后，回写更新 paymentOrder.setPayWayCode(frpCode);
			PaymentRecord tempPaymentRecordParam = paymentQueryFacade
					.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(merchantNo, orderNo, null, null,
							null);
			if (tempPaymentRecordParam != null) {
				returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_DATAERR.getValue());
				returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_DATAERR.getDesc());
			} else {
				PaymentRecordVo paymentRecordVo = new PaymentRecordVo();
				paymentRecordVo.setMerchantOrderNo(orderNo);
				paymentRecordVo.setBankOrderNo(paymentFacade.buildBankOrderNo());
				paymentRecordVo.setTrxNo(paymentFacade.buildPaymentTrxNo());
				paymentRecordVo.setMerchantNo(merchantNo);
				paymentRecordVo.setMerchantName(merchantOnline.getFullName());
				paymentRecordVo.setReceiverAccountType(AccountTypeEnum.MERCHANT.getValue());// TODO
																							// 先使用在线账户类型，后期改成移动账户
				paymentRecordVo.setPayerUserNo(userOperator.getUserNo());
				paymentRecordVo.setPayerName(userOperator.getRealName());
				paymentRecordVo.setPayerAccountType(AccountTypeEnum.CUSTOMER.getValue());
				paymentRecordVo.setOrderIp("");// 移动支付没有
				paymentRecordVo.setOrderRefererUrl("");// 移动支付没有
				paymentRecordVo.setOrderAmount(paymentOrder.getOrderAmount());
				paymentRecordVo.setPayWayCode(frpCode);
				// 设置支付产品信息
				setProductInfo(paymentRecordVo);
				paymentRecordVo.setProductName(paymentOrder.getProductName());
				paymentRecordVo.setProductDesc(paymentOrder.getProductDesc());
				paymentRecordVo.setReturnUrl(paymentOrder.getReturnUrl());
				paymentRecordVo.setNotifyUrl(paymentOrder.getNotifyUrl());
				paymentRecordVo.setOrderFrom(OrderFromTypeEnum.MOBILE.getValue());
				paymentRecordVo.setCur(paymentOrder.getCur());
				paymentRecordVo.setStatus(PaymentRecordStatusEnum.CREATED.getValue());
				paymentRecordVo.setBizType(TradeBizTypeEnum.MOBILE_ACQUIRING.getValue());
				paymentRecordVo.setPaymentType(TradePaymentTypeEnum.FAST_PAY.getValue());
				paymentRecordVo.setTrxModel(TrxModelEnum.IMMEDIATELY.getValue());

				Frp frp = frpFacade.findByFrpCode(frpCode);
				if (frp == null) {
					returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_MERCHANTBUSERR.getValue());
					returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_MERCHANTBUSERR.getDesc());
				} else {
					// 保存支付记录并且创建银行订单
					savePaymentRecord_BankOrder(paymentRecordVo);
					try {
						String p11_IsBindBankAccount = (String) paramMap.get("p11_IsBindBankAccount");
						if (StringUtils.isBlank(p11_IsBindBankAccount)) {
							log.info("已绑定卡快捷支付接口");
						} else {
							if ("1".equals(p11_IsBindBankAccount)) {
								log.info("绑定银行卡并支付");
							} else {
								log.info("不绑定银行卡并支付");
							}
						}
						paymentFacade.completePayment(paymentRecordVo.getBankOrderNo(), "", paymentRecordVo
								.getOrderAmount().doubleValue(), PaymentRecordStatusEnum.SUCCESS);
						// 调用银行快捷支付接口
						returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_SUCCESS.getValue());
						returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_SUCCESS.getDesc());
					} catch (BankBizException e) {
						log.error("调用快捷接口异常", e);
						returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_PAYFAIL.getValue());
						returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_PAYFAIL.getDesc());
					} catch (Exception e) {
						log.error("系统异常", e);
						returnMap.put("r3_Code", FastPayMessageEnum.FASTPAY_OPERATETYPEERR.getValue());
						returnMap.put("r4_CodeMsg", FastPayMessageEnum.FASTPAY_OPERATETYPEERR.getDesc());
					}
				}
			}
		}
		return returnMap;
	}
	

	/**
	 * 设置支付产品信息
	 * 
	 * @param paymentRecordVo
	 */
	private void setProductInfo(PaymentRecordVo paymentRecordVo) {
		List<PayWayVo> payWays = payWayFacade.findPayWayByUserNo(paymentRecordVo.getMerchantNo(),
				BankBusTypeEnum.FAST.toString());
		for (int i = 0; i < payWays.size(); i++) {
			PayWayVo payWay = payWays.get(i);
			if (paymentRecordVo.getPayWayCode().equals(payWay.getPayWayCode())) {
				paymentRecordVo.setPayProductCode(payWay.getPayProductCode());
				paymentRecordVo.setPayProductName(payWay.getPayProductName());
				paymentRecordVo.setPayWayName(payWay.getPayWayName());
				paymentRecordVo.setPayInterfaceCode(payWay.getPayWayCode());
				paymentRecordVo.setPayInterfaceName("");
			}
		}
	}

	/**
	 * 保存支付记录（充值记录）并创建银行订单
	 * 
	 * @param paymentRecord
	 * @return
	 * @throws GateWayException
	 */
	private void savePaymentRecord_BankOrder(PaymentRecordVo paymentRecordVo) {
		String jsonStr = JSONObject.toJSONString(paymentRecordVo);
		System.out.println("jsonStr:" + jsonStr);
		paymentFacade.createPaymentRecord(paymentRecordVo);
		log.info("保存支付记录,银行订单成功.");
	}
}

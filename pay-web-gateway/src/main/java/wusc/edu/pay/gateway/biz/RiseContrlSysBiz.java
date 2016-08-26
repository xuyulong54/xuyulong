/**
 * wusc.edu.pay.gateway.biz.RiseContrlSysBiz.java
 */
package wusc.edu.pay.gateway.biz;

import org.springframework.stereotype.Component;

/**
 * <ul>
 * <li>Title: 风险控制业务类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-4-18
 */
@Component("riseContrlSysBiz")
public class RiseContrlSysBiz {
//
//	@Resource(name = "mntMerchantTradeWarnFacade")
//	private MntMerchantTradeWarnFacade riskMerchantFacade;
//	@Autowired
//	@Resource(name = "mntMemberTradeWarnFacade")
//	private MntMemberTradeWarnFacade riskMemberFacade;
//	@Autowired
//	private UserQueryFacade userQueryFacade;
//	@Autowired
//	private AccountQueryFacade accountQueryFacade;
//
//	private static final Log log = LogFactory.getLog(RiseContrlSysBiz.class);
//
//	/**
//	 * 使用余额支付的风控监控接口
//	 * 
//	 * @param salerId
//	 *            商户(卖家)ID
//	 * @param buyerId
//	 *            买家(会员、商户)ID
//	 * @param accountTypeEnum
//	 *            买家账户类型
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxNo
//	 *            交易流水号
//	 * @param trxTypeEnum
//	 *            业务类型
//	 * @throws GateWayException
//	 */
//	public void riskContrlBalancePay(String salerNo, String buyerNo, AccountTypeEnum accountTypeEnum, double orderAmount, String trxNo, TrxTypeEnum trxTypeEnum) throws GateWayException {
//		// 设置风控商户 (卖家) 检测实体
//		TradeMerchantParam riskSalerMerParams = setSalerRiskMerParams(salerNo, trxNo, orderAmount, trxTypeEnum, false);
//		// 判断是否触犯商户 (卖家) 风控规则
//		if (isCheckMerViolateRule(riskSalerMerParams, orderAmount)) {
//			// 判断买家 是商户还是会员
//			if (AccountTypeEnum.CUSTOMER.equals(accountTypeEnum)) {
//				TradeMemberParam riskMemParams = setBuyRiskMemParams(buyerNo, accountTypeEnum, trxNo, orderAmount, trxTypeEnum, true);
//				if (!isCheckMemViolateRule(riskMemParams, orderAmount))
//					throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("会员(买家)[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskMemParams.getMemberLoginNo(), trxNo, orderAmount));
//
//			} else if (AccountTypeEnum.MERCHANT.equals(accountTypeEnum)) {
//				TradeMerchantParam riskBuyerMerParams = setBuyRiskMerParams(buyerNo, trxNo, orderAmount, trxTypeEnum, true);
//				if (!isCheckMerViolateRule(riskBuyerMerParams, orderAmount))
//					throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("商户(买家)[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskBuyerMerParams.getMerchantNo(), trxNo, orderAmount));
//			}
//		} else {
//			throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("商户(卖家)[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskSalerMerParams.getMerchantNo(), trxNo, orderAmount));
//		}
//	}
//
//	/**
//	 * 使用B2C网银支付的风控监控接口
//	 * 
//	 * @param salerId
//	 *            商户(卖家) ID
//	 * @param trxNo
//	 *            交易流水号
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxTypeEnum
//	 *            业务类型
//	 */
//	public void riskContrlB2cPay(String salerNo,String trxNo, double orderAmount, TrxTypeEnum trxTypeEnum) {
//		// 设置风控商户 (卖家) 检测实体
//		TradeMerchantParam riskSalerMerParams = setSalerRiskMerParams(salerNo, trxNo, orderAmount, trxTypeEnum, false);
//		if (!isCheckMerViolateRule(riskSalerMerParams, orderAmount)) {
//			throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("商户(卖家)[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskSalerMerParams.getMerchantNo(), trxNo, orderAmount));
//		}
//	}
//	
//	/**
//	 * 使用B2B网银支付的风控监控接口
//	 * 
//	 * @param salerId
//	 *            商户(卖家) ID
//	 * @param trxNo
//	 *            交易流水号
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxTypeEnum
//	 *            业务类型
//	 */
//	public void riskContrlB2bPay(String salerNo,String trxNo, double orderAmount, TrxTypeEnum trxTypeEnum) {
//		// 设置风控商户 (卖家) 检测实体
//		TradeMerchantParam riskSalerMerParams = setSalerRiskMerParams(salerNo, trxNo, orderAmount, trxTypeEnum, false);
//		if (!isCheckMerViolateRule(riskSalerMerParams, orderAmount)) {
//			throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("商户(卖家)[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskSalerMerParams.getMerchantNo(), trxNo, orderAmount));
//		}
//	}
//	
//
//	/**
//	 * 使用账户充值的风控监控接口
//	 * 
//	 * @param rechargeObjId
//	 *            充值对象(商户、会员)ID
//	 * @param accountTypeEnum
//	 *            账户类型(商户、会员)
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxNo
//	 *            交易流水号
//	 * @param trxTypeEnum
//	 *            业务类型
//	 */
//	public void riskContrlAccountRecharge(String rechargeObjNo, AccountTypeEnum accountTypeEnum, double orderAmount, String trxNo, TrxTypeEnum trxTypeEnum) {
//		// 判断当前使用账户充值的是商户还是会员
//		if (AccountTypeEnum.CUSTOMER.equals(accountTypeEnum)) {
//			TradeMemberParam riskMemParams = setBuyRiskMemParams(rechargeObjNo, accountTypeEnum, trxNo, orderAmount, trxTypeEnum, false);
//			if (!isCheckMemViolateRule(riskMemParams, orderAmount))
//				throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("会员 [%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskMemParams.getMemberLoginNo(), trxNo, orderAmount));
//
//		} else if (AccountTypeEnum.MERCHANT.equals(accountTypeEnum)) {
//			TradeMerchantParam riskMerParams = setBuyRiskMerParams(rechargeObjNo, trxNo, orderAmount, trxTypeEnum, false);
//			if (!isCheckMerViolateRule(riskMerParams, orderAmount))
//				throw new GateWayException(GateWayException.GATEWAY_RCS_REFUND_TRADE, String.format("商户编号[%s] 交易流水号[%s] 交易金额[%s] 触发风控规则", riskMerParams.getMerchantNo(), trxNo, orderAmount));
//		}
//	}
//
//	/**
//	 * 设置风控系统的 商户检测实体
//	 * 
//	 * @param salerId
//	 *            商户(卖家) ID
//	 * @param trxNo
//	 *            交易流水号
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxTypeEnum
//	 *            交易类型 (充值、支付等业务)
//	 * @param isOutFund
//	 *            标识符 (1 表示出款 0 表示入款)
//	 * @return 商户检测实体
//	 */
//	private TradeMerchantParam setSalerRiskMerParams(String salerNo, String trxNo, double orderAmount, TrxTypeEnum trxTypeEnum, boolean isOutFund) {
//		// 查询商户( 卖家 )信息
//		UserInfo salerMerchant = userQueryFacade.getUserInfoByUserNo(salerNo);
//		TradeMerchantParam riskMerParams = new TradeMerchantParam();
//		riskMerParams.setMerchantNo(salerMerchant.getUserNo());
//		riskMerParams.setMerchantName(salerMerchant.getRealName());
//		riskMerParams.setEmail(salerMerchant.getBindEmail());
//		riskMerParams.setAccountType(AccountTypeEnum.MERCHANT.getValue());
//		riskMerParams.setAmount(orderAmount);
//		riskMerParams.setCreateTime(new Date());
//		riskMerParams.setLoginIp(SystemUtils.getCurrClientIp());
//		riskMerParams.setLoginTime(new Date());
//		riskMerParams.setPayIp(SystemUtils.getCurrClientIp());
//		riskMerParams.setPayTime(new Date());
//		riskMerParams.setIsOut(isOutFund ? 1 : 0);// 1--是出款 0---进款
//		riskMerParams.setTrxNo(trxNo);
//		riskMerParams.setTrxType(trxTypeEnum.getValue());
//		return riskMerParams;
//	}
//
//	/**
//	 * 设置风控系统的 商户检测实体
//	 * 
//	 * @param salerId
//	 *            商户(买家) ID
//	 * @param trxNo
//	 *            交易流水号
//	 * @param orderAmount
//	 *            交易金额
//	 * @param trxTypeEnum
//	 *            交易类型 (充值、支付等业务)
//	 * @param isOutFund
//	 *            标识符 (1 表示出款 0 表示入款)
//	 * @return 商户检测实体
//	 */
//	private TradeMerchantParam setBuyRiskMerParams(String salerNo, String trxNo, double orderAmount, TrxTypeEnum trxTypeEnum, boolean isOutFund) {
//		return setSalerRiskMerParams(salerNo, trxNo, orderAmount, trxTypeEnum, isOutFund);
//	}
//
//	/**
//	 * 设置风控系统的 会员检测实体
//	 * 
//	 * @param buyerId
//	 *            买家 (会员) ID
//	 * @param accountTypeEnum
//	 *            账户类型(会员、商户)
//	 * @param trxNo
//	 *            交易编号
//	 * @param orderAmount
//	 *            订单金额
//	 * @param trxTypeEnum
//	 *            交易类型
//	 * @param isOutFund
//	 *            标识符 (1 表示出款 0 表示入款)
//	 * @return 会员检测实体
//	 */
//	private TradeMemberParam setBuyRiskMemParams(String buyerNo, AccountTypeEnum accountTypeEnum, String trxNo, double orderAmount, TrxTypeEnum trxTypeEnum, boolean isOutFund) {
//		UserInfo member = userQueryFacade.getUserInfoByUserNo(buyerNo);
//		AccountSecurity entity = accountQueryFacade.getAccountSecurityByAccountNo(accountQueryFacade.getAccountByUserNo(buyerNo).getAccountNo());
//		TradeMemberParam buyRiskMember = new TradeMemberParam();
//		buyRiskMember.setMemberNo(member.getUserNo());
//		buyRiskMember.setMemberLoginNo(member.getLoginName());
//		buyRiskMember.setMemberName(member.getRealName());
//		buyRiskMember.setEmail(member.getBindEmail());
//		buyRiskMember.setRealNameAuth(entity.getRealNameCheckStatus());
//		buyRiskMember.setAccountType(accountTypeEnum.getValue());
//		buyRiskMember.setAmount(orderAmount);
//		buyRiskMember.setCreateTime(new Date());
//		buyRiskMember.setLoginIp(SystemUtils.getCurrClientIp());
//		buyRiskMember.setLoginTime(new Date());
//		buyRiskMember.setPayIp(SystemUtils.getCurrClientIp());
//		buyRiskMember.setPayTime(new Date());
//		buyRiskMember.setIsOut(isOutFund ? 1 : 0);// 1--是出款 0---进款
//		buyRiskMember.setTrxNo(trxNo);
//		buyRiskMember.setTrxType(trxTypeEnum.getValue());
//		return buyRiskMember;
//	}
//
//	/**
//	 * 判断商户是否触犯风控规则
//	 * 
//	 * @param riskMerParams
//	 *            商户检测实体
//	 * @param orderAmount
//	 *            交易金额 (只用于打印日志)
//	 * @return
//	 */
//	private boolean isCheckMerViolateRule(TradeMerchantParam riskMerParams, double orderAmount) {
//		// 是否触犯风控规则 默认未触犯
//		boolean isViolateRule = false;
//		isViolateRule = riskMerchantFacade.checkSingleTrade(riskMerParams);
//		if (isViolateRule) {
//			isViolateRule = riskMerchantFacade.checkTrade(riskMerParams);
//			if (isViolateRule) {
//				riskMerchantFacade.checkTradeWarn(riskMerParams);
//			} else {
//				log.info(String.format("商户编号 [%s] 交易金额 [%s] 触犯风控规则，具体规则风控系统。", riskMerParams.getMerchantNo(), orderAmount));
//			}
//		} else {
//			log.info(String.format("商户编号 [%s] 交易金额 [%s] 触犯单笔交易金额。", riskMerParams.getMerchantNo(), orderAmount));
//		}
//		return isViolateRule;
//	}
//
//	/**
//	 * 判断会员是否触犯风控规则
//	 * 
//	 * @param riskMemParams
//	 *            会员检测实体
//	 * @param orderAmount
//	 *            交易金额 (只用于打印日志)
//	 * @return
//	 */
//	private boolean isCheckMemViolateRule(TradeMemberParam riskMemParams, double orderAmount) {
//		// 是否触犯风控规则 默认未触犯
//		boolean isViolateRule = false;
//		isViolateRule = riskMemberFacade.checkSingleTrade(riskMemParams);
//		if (isViolateRule) {
//			isViolateRule = riskMemberFacade.checkTrade(riskMemParams);
//			if (isViolateRule) {
//				riskMemberFacade.checkTradeWarn(riskMemParams);
//			} else {
//				log.info(String.format("会员登录账号 [%s] 交易流水号[%s] 交易金额 [%s] 触犯风控规则。", riskMemParams.getMemberLoginNo(), riskMemParams.getAmount(), orderAmount));
//			}
//		} else {
//			log.info(String.format("会员登录账号 [%s] 交易流水号[%s] 交易金额 [%s] 触犯单笔交易限额的风控规则。", riskMemParams.getMemberLoginNo(), riskMemParams.getAmount(), orderAmount));
//		}
//		return isViolateRule;
//	}
}

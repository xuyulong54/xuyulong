/**
 * wusc.edu.pay.web.portal.base.BaseAction.java
 */
package wusc.edu.pay.web.portal.base;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.notify.entity.NotifyRecord;
import wusc.edu.pay.facade.notify.enums.NotifyStatusEnum;
import wusc.edu.pay.facade.notify.enums.NotifyTypeEnum;
import wusc.edu.pay.facade.notify.service.NotifyFacade;
import wusc.edu.pay.facade.settlement.entity.SettRule;
import wusc.edu.pay.facade.settlement.service.SettQueryFacade;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentFacade;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.service.TransferFacade;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.exceptions.UserBizException;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;

import com.alibaba.fastjson.JSONObject;

/**
 * <ul>
 * <li>Title: 控制器基类</li>
 * <li>Description:</li>
 * <li>Copyright: www.gzzyzz.com</li>
 * <li>Company:</li>
 * </ul>
 * 
 * @author Hill
 * @version 2014-5-29
 */
public class MerchantLogonAction extends BaseAction {

	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private SettQueryFacade settQueryFacade;
	@Autowired
	private PaymentFacade paymentFacade;
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private TransferFacade transferFacade;
	@Autowired
	private NotifyFacade notifyFacade;
	@Autowired
	private JmsTemplate notifyJmsTemplate; // 商户通知队列模板
	@Autowired
	private UserBankAccountFacade userBankAccountFacade; // 商户通知队列模板
	/*
	 * @Autowired private TradeLimitFacade tradeLimitFacade;
	 */

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3913863823025110156L;

	/**
	 * 根据用户编号获取用户信息.
	 * 
	 * @param userNo
	 *            用户编号.
	 * @return userInfo.
	 */
	public UserInfo getUserInfoByUserNo(String userNo) {
		return userQueryFacade.getUserInfoByUserNo(userNo);
	}

	/**
	 * 根据登录名获取用户信息.
	 * 
	 * @param loginName
	 *            登录名.
	 * @return userInfo.
	 */
	public UserInfo getUserInfoByLoginName(String loginName) {
		return userQueryFacade.getUserInfoByLoginName(loginName);
	}

	/**
	 * 根据账户编号获取账户信息.<br/>
	 * 
	 * @return Account.
	 */
	public Account getAccountByAccountNo(String accountNo) {
		return accountQueryFacade.getAccountByAccountNo(accountNo);
	}

	/**
	 * 查询当前用户账务历史
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param beginDate
	 * @param endDate
	 * @param fundDirection
	 * @param trxNo
	 * @param trxType
	 * @return
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, String beginDate, String endDate, String fundDirection,
			String requestNo, String trxType) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("fundDirection", fundDirection);
		paramMap.put("requestNo", requestNo);
		paramMap.put("trxType", trxType);
		paramMap.put("accountNo", getCurrentUserInfo().getAccountNo());

		return accountQueryFacade.queryAccountHistoryListPage(pageParam, paramMap);
	}

	/**
	 * 取当前商户的结算信息
	 * 
	 * @return
	 */
	// public Settlement getMerchantSettlementInfo() {
	// return
	// settlementFacade.findByAccountNo(this.getCurrentUserInfo().getAccountNo());
	// }

	/**
	 * 我的付款:查询支付订单
	 * 
	 * @param pageParam
	 * @param beginDate
	 * @param endDate
	 * @param merchantOrderNo
	 *            商户订单号.<br/>
	 * @param status
	 * @param trxType
	 * @return
	 */
	public PageBean payPaymentOrderListForPage(PageParam pageParam, String beginDate, String endDate, String merchantOrderNo,
			String status, String paymentType) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("notBizType", TradeBizTypeEnum.RECHARGE.getValue());
		paramMap.put("paymentType", paymentType);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		paramMap.put("beginOrderTime", beginDate);
		paramMap.put("endOrderTime", endDate);
		paramMap.put("status", status);
		paramMap.put("payerUserNo", this.getCurrentUserInfo().getUserNo());
		paramMap.put("isCount", 1);

		return paymentQueryFacade.queryPaymentOrderListForPage(pageParam, paramMap);
	}

	/**
	 * 我的收款,查询支付记录
	 * 
	 * @param pageParam
	 *            分页
	 * @param merchantOrderNo
	 *            商户订单号
	 * @param beginDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @param status
	 *            支付状态
	 * @param paymentTypes
	 *            交易类型
	 * @return
	 */
	public PageBean receivePaymentOrderListForPage(PageParam pageParam, String merchantOrderNo, String beginDate, String endDate,
			String status, String paymentType) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("paymentType", paymentType);
		paramMap.put("merchantOrderNo", merchantOrderNo);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("status", status);
		paramMap.put("merchantNo", this.getCurrentUserInfo().getUserNo());
		paramMap.put("isCount", 1);

		return paymentQueryFacade.queryPaymentOrderListForPage(pageParam, paramMap);
	}

	/**
	 * (商户收款)充值记录查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param merchantOrderNo
	 *            商户订单号
	 * @param trxNo
	 *            交易流水号
	 * @param status
	 *            支付状态
	 * @param beginDate
	 * @param endDate
	 * @return list.
	 */
	public PageBean queryRechargeOrderListPage(PageParam pageParam, String merchantOrderNo, Integer status, String beginDate,
			String endDate, String beginAmount, String endAmount) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("payerUserNo", this.getCurrentUserInfo().getUserNo()); // 用户
		paramMap.put("bizType", TradeBizTypeEnum.RECHARGE.getValue()); // 业务类型:充值
		paramMap.put("merchantOrderNo", merchantOrderNo); // 商户订单号
		paramMap.put("status", status);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("beginAmount", beginAmount);// 支付金额
		paramMap.put("endAmount", endAmount);
		paramMap.put("isCount", 1);
		return paymentQueryFacade.queryPaymentOrderListForPage(pageParam, paramMap);
	}

	/**
	 * 查询支付记录.
	 * 
	 * @param merchantNo
	 * @param orderNo
	 * @param trxNo
	 * @param bankOrderNo
	 * @param status
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(String merchantNo, String orderNo, String trxNo,
			String bankOrderNo, Integer status) {
		return paymentQueryFacade.getPaymentRecordByMerchantNo_orderNo_trxNo_bankOrderNo_status(merchantNo, orderNo, trxNo, bankOrderNo,
				status);
	}

	/**
	 * 根据商户订单号，商户编号(收款人编号)，订单状态 查询
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param status
	 * @return
	 */
	public PaymentOrder getPaymentOrderBy_merchantNo_orderNo_status(String merchantNo, String merchantOrderNo, Integer status) {
		return paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo, merchantOrderNo, status);
	}

	/**
	 * 交易撤销
	 * 
	 * @param trxNo
	 *            流水号
	 * @param merchantNo
	 *            商户编号
	 * @param cancelReason
	 *            取消原因
	 */
	public void cancelOrder(String trxNo, String merchantNo, String cancelReason) {
		paymentFacade.cancelPaymentOrder(merchantNo, trxNo, cancelReason);
	}

	/**
	 * 我的收款,转账记录查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param trxNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public PageBean receiveTransferRecordListForPage(PageParam pageParam, String trxNo, String beginDate, String endDate) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxNo", trxNo);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("sourceUserNo", this.getCurrentUserInfo().getUserNo());
		paramMap.put("isCount", 1);
		return transferFacade.queryTransferRecordListPage(pageParam, paramMap);
	}

	/**
	 * 我的付款,转账记录查询
	 * 
	 * @param pageParam
	 *            分页参数
	 * @param trxNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public PageBean payTransferRecordListForPage(PageParam pageParam, String trxNo, String beginDate, String endDate) {

		if (StringUtils.isEmpty(beginDate) || StringUtils.isEmpty(endDate)) {
			return new PageBean();
		}

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("trxNo", trxNo);
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("targetUserNo", this.getCurrentUserInfo().getUserNo());
		paramMap.put("isCount", 1);
		return transferFacade.queryTransferRecordListPage(pageParam, paramMap);
	}

	/**
	 * 转账.
	 * 
	 * @param transferTrxNo
	 *            转账流水号
	 * @param loginName
	 *            付款人账户
	 * @param payeeAccountNo
	 *            收款人帐户
	 * @param transferAmount
	 *            转账金额
	 * @param transferDesc
	 */
	public void completeTransfer(String transferTrxNo, String loginName, String payeeAccountNo, Double transferAmount, String transferDesc) {
		transferFacade.createTransfer(transferTrxNo, loginName, payeeAccountNo, transferAmount, transferDesc);
	}


	/**
	 * 我的收款：支付订单明细
	 * 
	 * @param merchantOrderNo
	 *            商户订单号
	 */
	public PaymentOrder receiveGetPaymentOrder(String merchantOrderNo) {
		return paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(this.getCurrentUserInfo().getUserNo(), merchantOrderNo, null);
	}

	/**
	 * 我的付款,支付记录明细
	 * 
	 * @param trxNo
	 */
	public PaymentOrder payGetPaymentOrder(String merchantOrderNo) {
		return paymentQueryFacade.getByTargetUserNo_merchantOrderNo(this.getCurrentUserInfo().getUserNo(), merchantOrderNo);
	}



	/**
	 * 我的付款,支付记录明细-订单信息
	 * 
	 * @param orderNo
	 * @param merchantNo
	 * @return
	 */
	public PaymentOrder payGetPaymentOrder(String orderNo, String merchantNo) {
		return paymentQueryFacade.getPaymentOrderBy_merchantNo_orderNo_status(merchantNo, orderNo, null);
	}

	/**
	 * 支付交易记录信息
	 * 
	 * @param trxNo
	 * @return
	 */
	public PaymentRecord payGetPaymentRecord(String trxNo) {
		return paymentQueryFacade.getPaymentRecordByMerchantNoAndTrxNo(this.getCurrentUserInfo().getUserNo(), trxNo);
	}

	/**
	 * 根据支付流水号查支付订单
	 * 
	 * @param trxNo
	 * @return
	 */
	public PaymentOrder getPaymentOrderBySuccessTrxNo(String trxNo) {
		return paymentQueryFacade.getPaymentOrderBySuccessTrxNo(trxNo);
	}

	/**
	 * 查询通知记录
	 * 
	 * @param merchantNo
	 *            流水号
	 * @param merchantOrderNo
	 *            商户订单号.
	 * @return NotifyRecord
	 */
	public NotifyRecord getNotifyByMerchantNoAndMerchantOrderNo(String merchantNo, String merchantOrderNo) {
		return notifyFacade.getNotifyByMerchantNoAndMerchantOrderNoAndNotifyType(merchantNo, merchantOrderNo,
				NotifyTypeEnum.MERCHANT.getValue());
	}

	/**
	 * 商户补单
	 * 
	 * @param param
	 *            通知参数.<br/>
	 * @return noNotifyTrxNoStr 不需要发送通知的交易流水号.
	 */
	public String replacementOrder(String params) {

		String paramArray[] = params.split("&"); // 转数组
		StringBuffer noNotifyOrderNoStr = new StringBuffer();// 不需要发送通知的商户订单号
		int paramLent = paramArray.length;
		for (int num = 1; num < paramLent; num++) {// 循环遍历需要通知的订单
			String param[] = paramArray[num].split(",");
			if (param[1].equals(OrderStatusEnum.SUCCESS.getValue() + "")) {
				// 支付成功的商户订单号
				String merchantOrderNo = param[0];
				final NotifyRecord notifyRecord = this.getNotifyByMerchantNoAndMerchantOrderNo(merchantOrderNo, String.valueOf(param[3]));// 根据流水号查询通知记录表
				if (notifyRecord != null && notifyRecord.getStatus() == NotifyStatusEnum.FAILED.getValue()) {

					notifyJmsTemplate.send(new MessageCreator() {
						public Message createMessage(Session session) throws JMSException {
							return session.createTextMessage(JSONObject.toJSONString(notifyRecord));
						}
					});

				} else {
					noNotifyOrderNoStr.append(merchantOrderNo + ',');
				}
			} else { // 支付失败的订单
				noNotifyOrderNoStr.append(param[0] + ",");
			}
		}
		return noNotifyOrderNoStr.toString();
	}

	/**
	 * 生产充值订单号
	 * 
	 */
	public String buildRechargeOrderNo() {
		return paymentFacade.buildPaymentOrderNo();
	}

	public String buildTransferTrxNo() {
		return paymentFacade.buildTransferTrxNo();
	}

	/**
	 * 根据商户编号和流水号查询支付记录
	 * 
	 * @param merchantNo
	 * @param trxNo
	 * @return
	 */
	public PaymentRecord getPaymentRecordByMerchantNoAndMerchantOrderNo(String merchantNo, String trxNo) {
		return paymentQueryFacade.getPaymentRecordByMerchantNoAndTrxNo(merchantNo, trxNo);
	}

	/**
	 * 每日汇总记录查询
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param batchNo
	 * @return pageBean
	 */
	public PageBean listSettDaily(String beginDate, String endDate, String batchNo,String endCollectDate) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("beginDate", beginDate);
		paramMap.put("endDate", endDate);
		paramMap.put("batchNo", batchNo);
		paramMap.put("endCollectDate", endCollectDate);
		paramMap.put("accountNo", this.getCurrentUserInfo().getAccountNo());
		pageBean = settQueryFacade.listPageSettDailyCollect(getPageParam(), paramMap);
		return pageBean;
	}

	/**
	 * 结算记录查询
	 * 
	 * @param beginDate
	 * @param endDate
	 * @param batchNo
	 * @return
	 */
	public PageBean listSettRecord(String beginDate, String endDate, String batchNo) {

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("accountNo", this.getCurrentUserInfo().getAccountNo());
		paramMap.put("beginTime", beginDate);
		paramMap.put("endTime", endDate);
		paramMap.put("batchNo", batchNo);
		pageBean = settQueryFacade.listPageSettRecord(getPageParam(), paramMap);
		return pageBean;
	}

	/**
	 * 根据账户编号获取单个结算规则
	 * 
	 * @param accountNo
	 *            结算账户编号
	 * @return
	 */
	public SettRule getSettRuleByAccountNo(String accountNo) {
		return settQueryFacade.getSettRuleByAccountNo(accountNo);
	}

	/**
	 * 根据商户编号获取结算银行卡信息
	 * 
	 * @param userNo
	 * @return
	 * @throws UserBizException
	 */
	public UserBankAccount getSettlementBankAccountByMerchantUserNo(String userNo) {
		return userBankAccountFacade.getSettlementBankAccountByMerchantUserNo(userNo);
	}

}

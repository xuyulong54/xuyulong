package wusc.edu.pay.core.trade.biz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.number.AmountUtil;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.trade.biz.sub.AccountBiz;
import wusc.edu.pay.core.trade.biz.sub.CostBiz;
import wusc.edu.pay.core.trade.biz.sub.FeeBiz;
import wusc.edu.pay.core.trade.biz.sub.LimtBiz;
import wusc.edu.pay.core.trade.biz.sub.MerchantNotifyBiz;
import wusc.edu.pay.core.trade.dao.PaymentOrderDao;
import wusc.edu.pay.core.trade.dao.PaymentRecordDao;
import wusc.edu.pay.core.trade.dao.SplitRecordDao;
import wusc.edu.pay.facade.cost.entity.CalCostOrder;
import wusc.edu.pay.facade.fee.dto.FeeCalculateResultDTO;
import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.trade.entity.PaymentOrder;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.entity.SplitRecord;
import wusc.edu.pay.facade.trade.enums.OrderEncryptTypeEnum;
import wusc.edu.pay.facade.trade.enums.OrderOperateEnum;
import wusc.edu.pay.facade.trade.enums.OrderStatusEnum;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.SplitStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradeBizTypeEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.enums.TrxModelEnum;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.trade.vo.PaymentOrderVo;
import wusc.edu.pay.facade.trade.vo.PaymentRecordVo;
import wusc.edu.pay.facade.trade.vo.RechargeOrderVo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.entity.UserBankAccount;
import wusc.edu.pay.facade.user.entity.UserInfo;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserBankAccountFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;


/**
 * 
 * @描述: 订单（创建、更新）业务层接口实现类.
 * @作者: WuShuicheng .
 * @创建时间: 2013-10-12,下午4:07:37 .
 * @版本: 1.0 .
 */
@Component("paymentBiz")
public class PaymentBiz {

	@Autowired
	private PaymentRecordDao paymentRecordDao;

	@Autowired
	private AccountBiz accountBiz;

	@Autowired
	private PaymentOrderDao paymentOrderDao;

	@Autowired
	private SplitRecordDao splitRecordDao;

	@Autowired
	private MerchantNotifyBiz merchantNotifyBiz;

	@Autowired
	private CostBiz costBiz;

	@Autowired
	private FeeBiz feeBiz;

	@Autowired
	private LimtBiz limtBiz;

	@Autowired
	private UserQueryFacade userQueryFacade;

	@Autowired
	private UserBankAccountFacade userBankAccountFacade;

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;

	/**
	 * 默认订单有效期，单位分钟
	 */
	public final int DEFAULT_ORDER_PERIOD = 60 * 24;

	private static final Log log = LogFactory.getLog(PaymentBiz.class);

	/**
	 * 余额支付成功处理
	 * 
	 * @param paymentRecord
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void successAccountBalancePay(PaymentRecordVo paymentRecordVo) {

		log.info("==>successAccountBalancePay");

		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setStatus(PaymentRecordStatusEnum.SUCCESS.getValue());
		paymentRecord.setCreateDate(new Date());
		paymentRecord.setCreateTime(new Date());
		paymentRecord.setCompleteTime(new Date());
		paymentRecord.setCompleteDate(new Date());
		paymentRecord.setPaySuccessDate(new Date());
		paymentRecord.setPaySuccessTime(new Date());
		paymentRecord.setModifyTime(new Date());
		paymentRecord.setOrderFlag(OrderOperateEnum.NORMAL.getValue());
		paymentRecord.setIsRefund(101);
		paymentRecord.setRefundTimes(0);
		paymentRecord.setBizType(TradeBizTypeEnum.ONLINE_ACQUIRING.getValue());
		paymentRecord.setPaymentType(TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue());

		paymentRecord.setBankOrderNo(paymentRecordVo.getBankOrderNo());
		paymentRecord.setCur(paymentRecordVo.getCur());
		paymentRecord.setMerchantName(paymentRecordVo.getMerchantName());
		paymentRecord.setMerchantNo(paymentRecordVo.getMerchantNo());
		paymentRecord.setMerchantOrderNo(paymentRecordVo.getMerchantOrderNo());
		paymentRecord.setNotifyUrl(paymentRecordVo.getNotifyUrl());
		paymentRecord.setOrderAmount(paymentRecordVo.getOrderAmount());
		paymentRecord.setOrderFrom(paymentRecordVo.getOrderFrom());
		paymentRecord.setOrderIp(paymentRecordVo.getOrderIp());
		paymentRecord.setOrderRefererUrl(paymentRecordVo.getOrderRefererUrl());
		paymentRecord.setPayerAccountType(paymentRecordVo.getPayerAccountType());
		paymentRecord.setPayerName(paymentRecordVo.getPayerName());
		paymentRecord.setPayerUserNo(paymentRecordVo.getPayerUserNo());
		paymentRecord.setPayInterfaceCode(paymentRecordVo.getPayInterfaceCode());
		paymentRecord.setPayInterfaceName(paymentRecordVo.getPayInterfaceName());
		paymentRecord.setPayProductCode(paymentRecordVo.getPayProductCode());
		paymentRecord.setPayProductName(paymentRecordVo.getPayProductName());
		paymentRecord.setPayWayCode(paymentRecordVo.getPayWayCode());
		paymentRecord.setPayWayName(paymentRecordVo.getPayWayName());
		paymentRecord.setProductDesc(paymentRecordVo.getProductDesc());
		paymentRecord.setProductName(paymentRecordVo.getProductName());
		paymentRecord.setReturnUrl(paymentRecordVo.getReturnUrl());
		paymentRecord.setTrxModel(paymentRecordVo.getTrxModel());
		paymentRecord.setTrxNo(paymentRecordVo.getTrxNo());
		paymentRecord.setReceiverAccountType(paymentRecordVo.getReceiverAccountType());
		paymentRecord.setBankTrxNo("");

		PaymentOrder paymentOrder = paymentOrderDao.getBy_merchantNo_orderNo_status(paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), null);
		if (paymentOrder == null) {
			throw TradeBizException.PAYMENT_ORDER_NOT_EXIST.print();
		}

		// 必须验证金额，请不要注释 by chenjianhua
		if (!AmountUtil.equal(paymentRecordVo.getOrderAmount().doubleValue(), paymentOrder.getOrderAmount().doubleValue())) {
			throw TradeBizException.REMOTE_AMOUNT_NOT_EQ_LOCAL_AMOUNT.print();
		}

		// 重复支付处理
		if (this.processRepeatPayment(paymentRecord, paymentOrder)) {
			throw TradeBizException.REPEAT_PAYMENT_IS_REFUND.print();
		}

		// 订单过期处理
		if (this.processExpiredPayment(paymentRecord, paymentOrder)) {
			throw TradeBizException.PAYMENT_ORDER_EXPIRED_IS_REFUND.print();
		}

		// 订单已处理完成
		if (!paymentOrder.getStatus().equals(OrderStatusEnum.CREATED.getValue())) {
			throw TradeBizException.PAYMENT_ORDER_IS_CANCELED.print();
		}

		paymentOrder.setPaymentType(TradePaymentTypeEnum.ACCOUNT_BALANCE_PAY.getValue());
		paymentOrder.setStatus(OrderStatusEnum.SUCCESS.getValue());
		paymentOrder.setCompleteTime(new Date());
		paymentOrder.setCompleteDate(new Date());
		paymentOrder.setPaySuccessDate(new Date());
		paymentOrder.setPaySuccessTime(new Date());
		paymentOrder.setModifyTime(new Date());
		paymentOrder.setPayerUserNo(paymentRecordVo.getPayerUserNo());
		paymentOrder.setPayerName(paymentRecordVo.getPayerName());
		paymentOrder.setPayProductCode(paymentRecordVo.getPayProductCode());
		paymentOrder.setPayProductName(paymentRecordVo.getPayProductName());
		paymentOrder.setPayWayCode(paymentRecordVo.getPayWayCode());
		paymentOrder.setPayWayName(paymentRecordVo.getPayWayName());
		paymentOrder.setSuccessTrxNo(paymentRecordVo.getTrxNo());

		// 如果为分账支付
		List<SplitRecord> splitRecordList = new ArrayList<SplitRecord>();
		if (paymentOrder.getTrxModel().equals(TrxModelEnum.SPLIT.getValue())) {

			splitRecordList = splitRecordDao.listBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus(paymentOrder.getMerchantNo(),
					paymentOrder.getMerchantOrderNo(), null, SplitStatusEnum.CREATED.getValue());

			for (int i = 0; i < splitRecordList.size(); i++) {
				if (splitRecordList.get(i).getTrxModel().equals(TrxModelEnum.IMMEDIATELY.getValue())) {
					splitRecordList.get(i).setSplitSuccessDate(new Date());
					splitRecordList.get(i).setSplitSuccessTime(new Date());
					splitRecordList.get(i).setModifyTime(new Date());
					splitRecordList.get(i).setSplitStatus(SplitStatusEnum.SUCCESS.getValue());
				}
			}
		}

		// step.1 手续费预算
		FeeCalculateResultDTO dto = feeBiz.preCaculate_Pay(paymentRecord, paymentOrder);

		// step.2 更新保存本地数据
		paymentOrderDao.update(paymentOrder);
		paymentRecordDao.insert(paymentRecord);
		splitRecordDao.update(splitRecordList);

		// step.3 更新账务
		accountBiz.payment(splitRecordList, paymentRecord);

		// step.4 商户通知（写队列）
		merchantNotifyBiz.notifyMerchant(paymentRecord, paymentOrder);

		// step.5 手续费计费（写队列）
		feeBiz.caculate(dto);

		// 累加交易金额 TODO chenjianhua-wushuicheng
		limtBiz.tradeAmountAddUp(LimitTrxTypeEnum.PAY, paymentRecordVo.getPayProductCode(), paymentRecordVo.getPayWayCode(), null,
				paymentOrder.getOrderAmount(), paymentOrder.getMerchantNo(), paymentOrder.getCompleteTime());

		log.info("==>successAccountBalancePay<==");
	}

	/**
	 * 创建支付订单
	 * 
	 * @param paymentOrder
	 */
	@Transactional(rollbackFor = Exception.class)
	public void createPaymentOrder(PaymentOrderVo paymentOrderVo, TradeBizTypeEnum bizType) {

		log.info("==>createPaymentOrder, bizType:" + bizType.name());

		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setStatus(OrderStatusEnum.CREATED.getValue());
		paymentOrder.setOrderFlag(OrderOperateEnum.NORMAL.getValue());
		paymentOrder.setBizType(bizType.getValue());

		paymentOrder.setBuyerleaveMessage(paymentOrderVo.getBuyerleaveMessage());
		paymentOrder.setCallbackPara(paymentOrderVo.getCallbackPara());
		paymentOrder.setConsigneeAddress(paymentOrderVo.getConsigneeAddress());
		paymentOrder.setConsigneeEmail(paymentOrderVo.getConsigneeEmail());
		paymentOrder.setConsigneeMobile(paymentOrderVo.getConsigneeMobile());
		paymentOrder.setConsigneeName(paymentOrderVo.getConsigneeName());
		paymentOrder.setConsigneePhone(paymentOrderVo.getConsigneePhone());
		paymentOrder.setConsigneePostCode(paymentOrderVo.getConsigneePostCode());
		paymentOrder.setCur(paymentOrderVo.getCur());
		paymentOrder.setInvoiceTitle(paymentOrderVo.getInvoiceTitle());
		paymentOrder.setMerchantName(paymentOrderVo.getMerchantName());
		paymentOrder.setMerchantNo(paymentOrderVo.getMerchantNo());
		paymentOrder.setMerchantOrderNo(paymentOrderVo.getMerchantOrderNo());
		paymentOrder.setNotifyUrl(paymentOrderVo.getNotifyUrl());
		paymentOrder.setOrderAmount(BigDecimal.valueOf(Double.valueOf(paymentOrderVo.getOrderAmount())));
		paymentOrder.setOrderDate(new Date());
		paymentOrder.setOrderTime(new Date());
		paymentOrder.setOrderEncryptType(paymentOrderVo.getOrderEncryptType());
		paymentOrder.setOrderFrom(paymentOrderVo.getOrderFrom());
		paymentOrder.setOrderIp(paymentOrderVo.getOrderIp());
		paymentOrder.setOrderOperatorLoginName(paymentOrderVo.getOrderOperatorLoginName());
		paymentOrder.setOrderOperatorRealName(paymentOrderVo.getOrderOperatorRealName());
		paymentOrder.setOrderRefererUrl(paymentOrderVo.getOrderRefererUrl());
		paymentOrder.setProductDesc(paymentOrderVo.getProductDesc());
		paymentOrder.setReturnUrl(paymentOrderVo.getReturnUrl());
		paymentOrder.setProductName(paymentOrderVo.getProductName());
		paymentOrder.setSplitDetail(paymentOrderVo.getSplitDetail());
		paymentOrder.setTrxModel(paymentOrderVo.getTrxModel());
		paymentOrder.setRiskDay(paymentOrderVo.getRiskDay());

		// 设置订单有效期
		if (paymentOrderVo.getOrderPeriod() == null || paymentOrderVo.getOrderPeriod() == 0) {
			paymentOrder.setOrderPeriod(this.DEFAULT_ORDER_PERIOD);
		} else {
			paymentOrder.setOrderPeriod(paymentOrderVo.getOrderPeriod());
		}

		// 未支付的订单允许再次支付
		PaymentOrder paymentOrder1 = paymentOrderDao.getBy_merchantNo_orderNo_status(paymentOrder.getMerchantNo(),
				paymentOrder.getMerchantOrderNo(), null);
		if (paymentOrder1 != null) {
			if (paymentOrder1.getStatus().equals(OrderStatusEnum.SUCCESS.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
			} else if (paymentOrder1.getStatus().equals(OrderStatusEnum.FAILED.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
			} else if (paymentOrder1.getStatus().equals(OrderStatusEnum.CANCELED.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_CANCELED.print();
			} else if (paymentOrder1.getStatus().equals(OrderStatusEnum.CREATED.getValue())) {
				// 判断金额一致
				if (!AmountUtil.equal(paymentOrder1.getOrderAmount().doubleValue(), paymentOrder.getOrderAmount().doubleValue())) {
					throw TradeBizException.PAYMENT_ORDER_IS_EXCEPTION.print();
				} else {
					return;
				}
			}
		}

		if (StringUtil.isNotBlank(paymentOrderVo.getPayerLoginName())) {

			UserInfo userInfo = userQueryFacade.getUserInfoByLoginName(paymentOrderVo.getPayerLoginName());
			if (userInfo == null) {
				throw TradeBizException.PAYER_USER_INFO_NOT_EXIST.print();
			}
			paymentOrder.setPayerUserNo(userInfo.getUserNo());
			paymentOrder.setPayerName(userInfo.getRealName());
			paymentOrder.setPayerUserType(userInfo.getUserType());
		}

		paymentOrderDao.insert(paymentOrder);

		log.info("==>createPaymentOrder<==");
	}

	/**
	 * 创建充值订单
	 * 
	 * @param rechargeOrderVo
	 */
	public void createRechargePaymentOrder(RechargeOrderVo rechargeOrderVo) {

		log.info("==>createRechargePaymentOrder");

		PaymentOrder paymentOrder = new PaymentOrder();
		paymentOrder.setStatus(OrderStatusEnum.CREATED.getValue());
		paymentOrder.setOrderFlag(OrderOperateEnum.NORMAL.getValue());
		paymentOrder.setBizType(TradeBizTypeEnum.RECHARGE.getValue());
		paymentOrder.setOrderDate(new Date());
		paymentOrder.setOrderTime(new Date());

		paymentOrder.setCur(rechargeOrderVo.getCur());
		paymentOrder.setMerchantName(rechargeOrderVo.getMerchantName());
		paymentOrder.setMerchantNo(rechargeOrderVo.getMerchantNo());
		paymentOrder.setMerchantOrderNo(rechargeOrderVo.getMerchantOrderNo());
		paymentOrder.setOrderAmount(new BigDecimal(rechargeOrderVo.getOrderAmount()));
		paymentOrder.setOrderEncryptType(rechargeOrderVo.getOrderEncryptType());
		paymentOrder.setOrderFrom(rechargeOrderVo.getOrderFrom());
		paymentOrder.setOrderIp(rechargeOrderVo.getOrderIp());
		paymentOrder.setOrderOperatorLoginName(rechargeOrderVo.getOrderOperatorLoginName());
		paymentOrder.setOrderOperatorRealName(rechargeOrderVo.getOrderOperatorRealName());
		paymentOrder.setOrderRefererUrl(rechargeOrderVo.getOrderRefererUrl());
		paymentOrder.setProductDesc(rechargeOrderVo.getProductDesc());
		paymentOrder.setReturnUrl(rechargeOrderVo.getReturnUrl());
		paymentOrder.setOrderEncryptType(OrderEncryptTypeEnum.HMAC.getValue());
		paymentOrder.setOrderTime(new Date());
		paymentOrder.setOrderPeriod(this.DEFAULT_ORDER_PERIOD);
		paymentOrder.setPayerUserNo(rechargeOrderVo.getPayerUserNo());
		paymentOrder.setPayerName(rechargeOrderVo.getPayerName());
		paymentOrder.setProductName(rechargeOrderVo.getProductName());
		paymentOrder.setPayerUserType(EnumChangeUtils.getUserType(rechargeOrderVo.getPayerAccountType()));

		// 未支付的订单允许再次支付
		PaymentOrder paymentOrderCheck = paymentOrderDao.getBy_merchantNo_orderNo_status(paymentOrder.getMerchantNo(),
				paymentOrder.getMerchantOrderNo(), null);
		if (paymentOrderCheck != null) {
			if (paymentOrderCheck.getStatus().equals(OrderStatusEnum.SUCCESS.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
			} else if (paymentOrderCheck.getStatus().equals(OrderStatusEnum.FAILED.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
			} else if (paymentOrderCheck.getStatus().equals(OrderStatusEnum.CANCELED.getValue())) {
				throw TradeBizException.PAYMENT_ORDER_IS_CANCELED.print();
			} else if (paymentOrderCheck.getStatus().equals(OrderStatusEnum.CREATED.getValue())) {
				// 判断金额一致
				if (!AmountUtil.equal(paymentOrderCheck.getOrderAmount().doubleValue(), paymentOrder.getOrderAmount().doubleValue())) {
					throw TradeBizException.PAYMENT_ORDER_IS_EXCEPTION.print();
				} else {
					return;
				}
			}
		}

		paymentOrderDao.insert(paymentOrder);

		log.info("==>createRechargePaymentOrder<==");
	}

	/**
	 * 创建支付记录
	 * 
	 * @param paymentRecord
	 */
	public void createPaymentRecord(PaymentRecordVo paymentRecordVo) {

		log.info("==>createPaymentRecord");

		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setStatus(PaymentRecordStatusEnum.CREATED.getValue());
		paymentRecord.setCreateDate(new Date());
		paymentRecord.setCreateTime(new Date());
		paymentRecord.setOrderFlag(OrderOperateEnum.NORMAL.getValue());
		paymentRecord.setIsRefund(101);
		paymentRecord.setRefundTimes(0);

		paymentRecord.setBankOrderNo(paymentRecordVo.getBankOrderNo());
		paymentRecord.setCur(paymentRecordVo.getCur());
		paymentRecord.setMerchantName(paymentRecordVo.getMerchantName());
		paymentRecord.setMerchantNo(paymentRecordVo.getMerchantNo());

		paymentRecord.setReceiverAccountType(paymentRecordVo.getReceiverAccountType());
		paymentRecord.setMerchantOrderNo(paymentRecordVo.getMerchantOrderNo());
		paymentRecord.setNotifyUrl(paymentRecordVo.getNotifyUrl());
		paymentRecord.setOrderAmount(paymentRecordVo.getOrderAmount());
		paymentRecord.setOrderFrom(paymentRecordVo.getOrderFrom());
		paymentRecord.setOrderIp(paymentRecordVo.getOrderIp());
		paymentRecord.setOrderRefererUrl(paymentRecordVo.getOrderRefererUrl());
		paymentRecord.setPayerAccountType(paymentRecordVo.getPayerAccountType());
		paymentRecord.setPayerName(paymentRecordVo.getPayerName());
		paymentRecord.setPayerUserNo(paymentRecordVo.getPayerUserNo());
		paymentRecord.setPayInterfaceCode(paymentRecordVo.getPayInterfaceCode());
		paymentRecord.setPayInterfaceName(paymentRecordVo.getPayInterfaceName());
		paymentRecord.setPayProductCode(paymentRecordVo.getPayProductCode());
		paymentRecord.setPayProductName(paymentRecordVo.getPayProductName());
		paymentRecord.setPayWayCode(paymentRecordVo.getPayWayCode());
		paymentRecord.setPayWayName(paymentRecordVo.getPayWayName());
		paymentRecord.setProductDesc(paymentRecordVo.getProductDesc());
		paymentRecord.setProductName(paymentRecordVo.getProductName());
		paymentRecord.setReturnUrl(paymentRecordVo.getReturnUrl());
		paymentRecord.setTrxModel(paymentRecordVo.getTrxModel());
		paymentRecord.setTrxNo(paymentRecordVo.getTrxNo());
		paymentRecord.setBizType(paymentRecordVo.getBizType());
		paymentRecord.setPaymentType(paymentRecordVo.getPaymentType());
		paymentRecord.setPayerPayAmount(paymentRecordVo.getOrderAmount().add(
				(paymentRecordVo.getPayerAmountFee() == null) ? new BigDecimal(0) : paymentRecordVo.getPayerAmountFee()));

		// 支付订单是否已处理
		PaymentOrder paymentOrder = paymentOrderDao.getBy_merchantNo_orderNo_status(paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), null);
		if (paymentOrder == null) {
			throw TradeBizException.PAYMENT_ORDER_NOT_EXIST.print();
		}

		paymentRecord.setReturnUrl(paymentOrder.getReturnUrl());
		paymentRecord.setNotifyUrl(paymentOrder.getNotifyUrl());
		paymentRecord.setProductName(paymentOrder.getProductName());
		paymentRecord.setOrderIp(paymentOrder.getOrderIp());
		paymentRecord.setOrderRefererUrl(paymentOrder.getOrderRefererUrl());
		paymentRecord.setOrderFrom(paymentOrder.getOrderFrom());
		paymentRecord.setRiskDay(paymentOrder.getRiskDay());

		if (paymentOrder.getStatus().equals(OrderStatusEnum.SUCCESS.getValue())) {
			throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
		} else if (paymentOrder.getStatus().equals(OrderStatusEnum.FAILED.getValue())) {
			throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
		} else if (paymentOrder.getStatus().equals(OrderStatusEnum.CANCELED.getValue())) {
			throw TradeBizException.PAYMENT_ORDER_IS_CANCELED.print();
		}

		// 订单是否过期
		if (this.checkOrderIsExpired(paymentOrder)) {
			throw TradeBizException.PAYMENT_ORDER_IS_EXPIRED.print();
		}

		paymentRecordDao.insert(paymentRecord);

		log.info("==>createPaymentRecord<==");
	}

	/**
	 * 撤销支付订单（支付记录不能撤消，因为有支付成功前订单撤消了，需要退款）
	 * 
	 * @param merchantNo
	 * @param merchantOrderNo
	 * @param cancelDesc
	 */
	public void cancelPaymentOrder(String merchantNo, String merchantOrderNo, String cancelReason) {

		log.info("==>cancelPaymentOrder");
		log.info(String.format("==>merchantNo:%s, merchantOrderNo:%s, cancelReason:%s", merchantNo, merchantOrderNo, cancelReason));

		PaymentOrder paymentOrder = paymentOrderDao.getBy_merchantNo_orderNo_status(merchantNo, merchantOrderNo,
				OrderStatusEnum.CREATED.getValue());
		if (paymentOrder == null) {
			throw TradeBizException.PAYMENT_ORDER_NOT_EXIST.print();
		}

		log.info("==>cancelPaymentOrder");
		log.info(String.format("==>merchantNo:%s, orderNo:%s", paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo()));

		paymentOrder.setStatus(OrderStatusEnum.CANCELED.getValue());
		paymentOrder.setCancelDate(new Date());
		paymentOrder.setCancelTime(new Date());
		paymentOrder.setCancelReason(cancelReason);
		paymentOrderDao.update(paymentOrder);

		log.info("==>cancelPaymentOrder<==");
	}

	/**
	 * 支付完成处理
	 * 
	 * @param bankOrderNo
	 *            银行订单号
	 * @param bankTrxNo
	 *            银行流水号
	 * @param amount
	 * @param status
	 * @return
	 */
	public PaymentRecord completePayment(String bankOrderNo, String bankTrxNo, double amount, PaymentRecordStatusEnum status) {

		log.info("==>completePayment");
		log.info(String.format("==>bankOrderNo:%s, bankTrxNo:%s, amount:%s, status:%s", bankOrderNo, bankTrxNo, amount, status.name()));

		// 注：加入行锁, 处理并发问题
		PaymentRecord paymentRecord = paymentRecordDao.getByBankOrderNo_IsPessimist(bankOrderNo, true);
		if (paymentRecord == null) {
			throw TradeBizException.PAYMENT_RECORD_NOT_EXIST.print();
		}

		// 如果支付记录已处理
		if (paymentRecord.getStatus().equals(PaymentRecordStatusEnum.SUCCESS.getValue())
				|| paymentRecord.getStatus().equals(PaymentRecordStatusEnum.FAILED.getValue())) {
			throw TradeBizException.PAYMENT_ORDER_IS_COMPLETED.print();
		}

		PaymentOrder paymentOrder = paymentOrderDao.getBy_merchantNo_orderNo_status(paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), null);
		if (paymentOrder == null) {
			throw TradeBizException.PAYMENT_ORDER_NOT_EXIST.print();
		}

		// 必须验证银行订单状态
		if (PaymentRecordStatusEnum.FAILED.equals(status)) {
			this.failPayment(paymentRecord, paymentOrder);
		} else if (PaymentRecordStatusEnum.SUCCESS.equals(status)) {

			// 必须验证金额，请不要注释 by chenjianhua
			if (!AmountUtil.equal(amount, paymentRecord.getPayerPayAmount().doubleValue())) {
				throw TradeBizException.REMOTE_AMOUNT_NOT_EQ_LOCAL_AMOUNT.print();
			}

			// 设置银行流水号
			paymentRecord.setBankTrxNo(bankTrxNo);

			// 重复支付处理
			if (this.processRepeatPayment(paymentRecord, paymentOrder)) {
				throw TradeBizException.REPEAT_PAYMENT_IS_REFUND.print();
			}

			// 订单在支付成功前,如果已撤消,处理
			if (this.processCancelPayment(paymentRecord, paymentOrder)) {
				throw TradeBizException.PAYMENT_ORDER_CANCELED_IS_REFUND.print();
			}

			// 订单过期处理
			if (this.processExpiredPayment(paymentRecord, paymentOrder)) {
				throw TradeBizException.PAYMENT_ORDER_EXPIRED_IS_REFUND.print();
			} else if (paymentOrder.getStatus().equals(OrderStatusEnum.CREATED.getValue())
					&& paymentRecord.getStatus().equals(PaymentRecordStatusEnum.CREATED.getValue())) {
				this.successPayment(paymentRecord, paymentOrder);
			}
		}

		log.info("==>completePayment<==");

		return paymentRecord;

	}

	/**
	 * 订单在支付成功前，撤消处理（不做财务处理，不通知商户，不计商户手续费，但是要计成本）by chenjianhua
	 * 
	 * @param paymentRecord
	 */
	public boolean processCancelPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		if (paymentOrder.getStatus() == OrderStatusEnum.CANCELED.getValue()) {

			log.info("==>processCancelPayment");
			log.info(String.format("==>merchantNo:%s, orderNo:%s, status:%s", paymentOrder.getMerchantNo(),
					paymentOrder.getMerchantOrderNo(), paymentOrder.getStatus()));

			// 异常支付成功处理
			this.exceptionSuccessPayment(paymentRecord, paymentOrder);

			log.info("==>processCancelPayment<==");

			return true;
		} else {
			return false;
		}
	}

	/**
	 * 订单已过期（不做财务处理，不通知商户，不计商户手续费，但是要计成本）by chenjianhua
	 * 
	 * @param paymentRecord
	 */
	public boolean processExpiredPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		if (this.checkOrderIsExpired(paymentOrder)) {

			log.info("==>processExpiredPayment");
			log.info(String.format("==>merchantNo:%s, orderNo:%s, orderPeriod:%s, expireTime:%s", paymentOrder.getMerchantNo(),
					paymentOrder.getMerchantOrderNo(), paymentOrder.getOrderPeriod(), paymentOrder.getExpireTime()));

			// 异常支付成功处理
			this.exceptionSuccessPayment(paymentRecord, paymentOrder);

			log.info("==>processExpiredPayment<==");

			return true;
		} else {
			return false;
		}

	}

	/**
	 * 重复支付处理，退款当前订单（不做财务处理，不通知商户，不计商户手续费，但是要计成本）by chenjianhua
	 * 
	 * @param paymentRecord
	 */
	public boolean processRepeatPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		PaymentRecord paymentRecord1 = paymentRecordDao.getBy_merchantNo_orderNo_trxNo_bankOrderNo_status(paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), null, null, OrderStatusEnum.SUCCESS.getValue());
		if (paymentRecord1 != null) {

			log.info("==>processRepeatPayment");
			log.info(String.format("==>merchantNo:%s, orderNo:%s", paymentOrder.getMerchantNo(), paymentOrder.getMerchantOrderNo()));

			// 异常支付成功处理
			this.exceptionSuccessPayment(paymentRecord, paymentOrder);

			log.info("==>processRepeatPayment<==");

			return true;
		} else {
			return false;
		}

	}

	/**
	 * 支付成功处理
	 * 
	 * @param paymentRecord
	 * @param paymentOrder
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void successPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		log.info("==>successPayment");
		log.info(String.format("==>merchantNo:%s, orderNo:%s, trxNo:%s, bankOrderNo:%s, orderAmount:%s", paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
				paymentRecord.getOrderAmount()));

		paymentRecord.setStatus(OrderStatusEnum.SUCCESS.getValue());
		paymentRecord.setCompleteTime(new Date());
		paymentRecord.setCompleteDate(new Date());
		paymentRecord.setPaySuccessDate(new Date());
		paymentRecord.setPaySuccessTime(new Date());
		paymentRecord.setModifyTime(new Date());

		paymentOrder.setPaymentType(paymentRecord.getPaymentType());
		paymentOrder.setStatus(OrderStatusEnum.SUCCESS.getValue());
		paymentOrder.setCompleteTime(new Date());
		paymentOrder.setCompleteDate(new Date());
		paymentOrder.setPaySuccessDate(new Date());
		paymentOrder.setPaySuccessTime(new Date());
		paymentOrder.setModifyTime(new Date());
		paymentOrder.setPayerName(paymentRecord.getPayerName());
		paymentOrder.setPayerUserNo(paymentRecord.getPayerUserNo());
		paymentOrder.setSuccessTrxNo(paymentRecord.getTrxNo()); // 设置成功支付流水号
		paymentOrder.setPayProductCode(paymentRecord.getPayProductCode());
		paymentOrder.setPayProductName(paymentRecord.getPayProductName());
		paymentOrder.setPayWayCode(paymentRecord.getPayWayCode());
		paymentOrder.setPayWayName(paymentRecord.getPayWayName());

		// 如果为分账支付
		List<SplitRecord> splitRecordList = new ArrayList<SplitRecord>();
		if (paymentOrder.getTrxModel().equals(TrxModelEnum.SPLIT.getValue())) {

			splitRecordList = splitRecordDao.listBy_merchantNo_merchantOrderNo_splitMerchantNo_splitStatus(paymentOrder.getMerchantNo(),
					paymentOrder.getMerchantOrderNo(), null, SplitStatusEnum.CREATED.getValue());

			for (int i = 0; i < splitRecordList.size(); i++) {
				if (splitRecordList.get(i).getTrxModel().equals(TrxModelEnum.IMMEDIATELY.getValue())) {
					splitRecordList.get(i).setSplitSuccessDate(new Date());
					splitRecordList.get(i).setSplitSuccessTime(new Date());
					splitRecordList.get(i).setModifyTime(new Date());
					splitRecordList.get(i).setSplitStatus(SplitStatusEnum.SUCCESS.getValue());
				}
			}
		}

		// step.1 成本试算
		CalCostOrder calCostOrder = costBiz.preCaculate_Pay(paymentRecord);

		// step.2 手续费试算
		FeeCalculateResultDTO dto = feeBiz.preCaculate_Pay(paymentRecord, paymentOrder);

		// step.3 更新本地数据
		paymentRecordDao.update(paymentRecord);
		paymentOrderDao.update(paymentOrder);
		splitRecordDao.update(splitRecordList);

		// step.4 更新账务
		accountBiz.payment(splitRecordList, paymentRecord);

		if (TradeBizTypeEnum.ONLINE_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.MOBILE_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.ICC_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.POS_ACQUIRING.getValue() == paymentRecord.getBizType()) {

			// step.5 商户通知（写队列）
			merchantNotifyBiz.notifyMerchant(paymentRecord, paymentOrder);

			// 累加交易金额 TODO chenjianhua-wushuicheng
			limtBiz.tradeAmountAddUp(LimitTrxTypeEnum.PAY, paymentRecord.getPayProductCode(), paymentRecord.getPayWayCode(), null,
					paymentRecord.getOrderAmount(), paymentOrder.getMerchantNo(), paymentOrder.getCompleteTime());
		}

		// step.6 成本计费（写队列）
		costBiz.caculate_Pay(calCostOrder);

		// step.7 手续费计费（写队列）
		feeBiz.caculate(dto);

		// step.8 风控成功消息

		// step.9 代理商分润

		log.info("==>successPayment<==");

	}

	/**
	 * 异常支付成功处理
	 * 
	 * @param paymentRecord
	 * @param paymentOrder
	 */
	@Transactional(rollbackFor = Exception.class)
	private void exceptionSuccessPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		log.info("==>exceptionSuccessPayment");
		log.info(String.format("==>merchantNo:%s, orderNo:%s, trxNo:%s, bankOrderNo:%s, orderAmount:%s", paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
				paymentRecord.getOrderAmount()));

		paymentRecord.setStatus(OrderStatusEnum.SUCCESS.getValue());
		paymentRecord.setCompleteTime(new Date());
		paymentRecord.setCompleteDate(new Date());
		paymentRecord.setPaySuccessDate(new Date());
		paymentRecord.setPaySuccessTime(new Date());
		paymentRecord.setModifyTime(new Date());
		// 把异常支付交易挂到平台充值商户上去
		paymentRecord.setMerchantNo("999000000000000");
		paymentRecord.setMerchantName("平台专用商户");

		// step.1 成本试算
		CalCostOrder calCostOrder = costBiz.preCaculate_Pay(paymentRecord);

		// step.2 手续费试算（异常支付特殊处理）
		paymentRecord.setPayerFee(BigDecimal.ZERO);
		paymentRecord.setPayerPayAmount(paymentRecord.getOrderAmount());
		paymentRecord.setReceiverFee(BigDecimal.ZERO);
		paymentRecord.setReceiverPostAmount(paymentRecord.getOrderAmount());
		paymentRecord.setPlatIncome(BigDecimal.ZERO);

		// step.3 更新本地数据,订单表不做更新！！
		paymentRecordDao.update(paymentRecord);

		// step.4 更新账务
		accountBiz.payment(null, paymentRecord);

		// step.5 成本计费（写队列）
		costBiz.caculate_Pay(calCostOrder);

		// step.6 风控成功消息

		log.info("==>exceptionSuccessPayment<==");
	}

	/**
	 * 支付失败处理
	 * 
	 * @param paymentRecord
	 * @param paymentOrder
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	public void failPayment(PaymentRecord paymentRecord, PaymentOrder paymentOrder) {

		log.info("==>failPayment");
		log.info(String.format("==>merchantNo:%s, orderNo:%s, trxNo:%s, bankOrderNo:%s, orderAmount:%s", paymentRecord.getMerchantNo(),
				paymentRecord.getMerchantOrderNo(), paymentRecord.getTrxNo(), paymentRecord.getBankOrderNo(),
				paymentRecord.getOrderAmount()));

		paymentRecord.setStatus(OrderStatusEnum.FAILED.getValue());
		paymentRecord.setCompleteTime(new Date());
		paymentRecord.setCompleteDate(new Date());
		paymentRecord.setModifyTime(new Date());

		paymentOrder.setPaymentType(paymentRecord.getPaymentType());
		paymentOrder.setStatus(OrderStatusEnum.FAILED.getValue());
		paymentOrder.setCompleteTime(new Date());
		paymentOrder.setCompleteDate(new Date());
		paymentOrder.setModifyTime(new Date());

		// 更新paymentRecord
		paymentRecordDao.update(paymentRecord);
		// 更新paymentOrder
		paymentOrderDao.update(paymentOrder);

		if (TradeBizTypeEnum.ONLINE_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.MOBILE_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.ICC_ACQUIRING.getValue() == paymentRecord.getBizType()
				|| TradeBizTypeEnum.POS_ACQUIRING.getValue() == paymentRecord.getBizType()) {

			// 通知队列
			merchantNotifyBiz.notifyMerchant(paymentRecord, paymentOrder);
		}

		log.info("==>failPayment<==");
	}

	/**
	 * 订单是否过期
	 * 
	 * @param paymentOrder
	 */
	private boolean checkOrderIsExpired(PaymentOrder paymentOrder) {

		if (paymentOrder.getOrderPeriod() != null) {

			// 当前系统时超过订单创建时间+订单有效期分钟，即为过期
			if (new Date().after(DateUtils.addMinute(paymentOrder.getCreateTime(), paymentOrder.getOrderPeriod()))) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 根据用户编号、银行卡账号，更改协议状态，及生成通知URL
	 * 
	 * @param userNo
	 * @param bankAccountNo
	 * @return
	 */
	public String completeBankAgreement(String userNo, String bankAccountNo, String contractNo, int status) {

		UserBankAccount userBankAccount = userBankAccountFacade.getByBankAccountNoAndUserNo(bankAccountNo, userNo);

		if (userBankAccount != null) {

			userBankAccount.setIsAuth(status);
			userBankAccount.setContractNo(contractNo);
			userBankAccountFacade.updateUserBankAccount(userBankAccount);
			String comments = userBankAccount.getRemark();
			String[] tempArr = comments.split("\\$");

			if (tempArr.length == 2) {

				String merchantNo = tempArr[1];
				String returnUrl = tempArr[0];

				MerchantOnline merchantOnline = merchantOnlineFacade.getMerchantOnlineByMerchantNo(merchantNo);

				StringBuffer signSrc = new StringBuffer();
				signSrc.append("r1_LoginName=").append(userBankAccount.getLoginName()).append("&");
				signSrc.append("r2_UserNo=").append(userBankAccount.getUserNo()).append("&");
				signSrc.append("r3_BankAccountNo=").append(userBankAccount.getBankAccountNo()).append("&");
				signSrc.append("ra_Code=").append(status).append("&");
				signSrc.append("rb_CodeMsg=");
				String hmac = DigestUtils.md5Hex(signSrc.toString() + merchantOnline.getMerchantKey());
				signSrc.append("&hmac=").append(hmac);

				return returnUrl + "?" + signSrc.toString();
			}
		}
		return null;
	}

}

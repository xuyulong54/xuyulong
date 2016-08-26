package wusc.edu.pay.web.boss.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import wusc.edu.pay.common.enums.MerchantBusTypeEnum;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.BeanUtils;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.common.utils.export.DataField;
import wusc.edu.pay.common.utils.export.ExportDataSource;
import wusc.edu.pay.common.utils.export.excel.ExcelDataExportor;
import wusc.edu.pay.common.utils.export.excel.MODE;
import wusc.edu.pay.common.web.annotation.Permission;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.cost.service.CalCostInterfaceFacade;
import wusc.edu.pay.facade.trade.entity.PaymentRecord;
import wusc.edu.pay.facade.trade.entity.TransferRecord;
import wusc.edu.pay.facade.trade.enums.PaymentRecordStatusEnum;
import wusc.edu.pay.facade.trade.enums.TradePaymentTypeEnum;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.trade.service.TransferFacade;
import wusc.edu.pay.facade.user.entity.MemberInfo;
import wusc.edu.pay.facade.user.entity.MerchantOnline;
import wusc.edu.pay.facade.user.enums.MemberStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.enums.MerchantTypeEnum;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.web.boss.base.BossBaseAction;


/**
 * 业务报表功能action
 * 
 * @desc
 * @author shenjialong
 * @date 2013-12-31,上午11:38:28
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Scope("prototype")
public class BordereauxAction extends BossBaseAction {
	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(BordereauxAction.class);
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private TransferFacade transferFacade;
	@Autowired
	private AccountQueryFacade accountQueryFacade;
	@Autowired
	private CalCostInterfaceFacade calCostInterfaceFacade;

	/**
	 * 商户 报表UI
	 */
	@Permission("bordereaux:merchant:view")
	public String merchantBordereauxUI() {
		this.putData("merchantStatusList", MerchantStatusEnum.toList());// 商户状态
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		return "merchantBordereauxList";
	}

	/**
	 * 商户报表
	 */
	@Permission("bordereaux:merchant:view")
	public String merchantBordereaux() {

		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", getInteger("status")); // 状态
		paramMap.put("beginDate", beginDate); // 提交开始时间
		paramMap.put("endDate", endDate); // 提交结束时间
		paramMap.put("isCount", 1); // 是否统计当前条件下的数据
		paramMap.put("shortName", getString("shortName"));
		paramMap.put("merchantNo", getString("merchantNo"));
		paramMap.put("signName", getString("signName"));
		paramMap.put("merchantType", getString("merchantType"));
		paramMap.put("merchantBusType", MerchantBusTypeEnum.ONLINE_MERCHANT.getValue()); // 查询在线商户数据
		super.pageBean = merchantOnlineFacade.listMerchantListPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("merchantStatusList", MerchantStatusEnum.toList());// 商户状态
		this.putData("merchantTypeList", MerchantTypeEnum.toList());// 商户类型
		return "merchantBordereauxList";
	}

	/**
	 * 导出商户报表
	 */
	public void merchantBordereauxExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename = "商户业务报表_" + sdf.format(new Date());
			this.getHttpResponse()
					.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[6];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("注册时间", "createTime");
			dataFields[2] = new DataField("商户编号", "merchantNo", 25);
			dataFields[3] = new DataField("商户类型", "merchantType");
			dataFields[4] = new DataField("商户简称", "shortName", 25);
			dataFields[5] = new DataField("商户状态", "status");

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String status = getString("status");
			final String merchantType = getString("merchantType");
			final String shortName = getString("shortName");
			final String merchantNo = getString("merchantNo");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				@Override
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
					map.put("status", status);
					map.put("merchantType", merchantType);
					map.put("shortName", shortName);
					map.put("merchantNo", merchantNo);
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = merchantOnlineFacade.listMerchantListPage(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							MerchantOnline ap = (MerchantOnline) list.get(i);
							MerchantOnline merchantOnline = BeanUtils.toBean(MerchantOnline.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("createTime", sdf1.format(merchantOnline.getCreateTime()));
							mapParam.put("merchantNo", merchantOnline.getMerchantNo());
							mapParam.put("merchantType", MerchantTypeEnum.getEnum(merchantOnline.getMerchantType()).getDesc());
							mapParam.put("shortName", merchantOnline.getShortName());
							mapParam.put("status", MerchantStatusEnum.getEnum(merchantOnline.getStatus()).getDesc());
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("导出商户报表数据");
		} catch (UnsupportedEncodingException e) {
			log.error("下载报表", e);
		} catch (IOException e) {
			log.error("IO", e);
		}
	}

	/**
	 * 会员报表UI
	 */
	@Permission("bordereaux:member:view")
	public String memberBordereauxUI() {
		this.putData("memberStatusList", MemberStatusEnum.toList()); // 会员状态查询
		return "memberBordereauxList";
	}

	/**
	 * 会员报表
	 */
	@Permission("bordereaux:member:view")
	public String memberBordereaux() {
		String startDate = getString("startDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (startDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(startDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((startDate != null && endDate == null) || (startDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		// 查询参数集合
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate); // 提交开始时间
		paramMap.put("endDate", endDate); // 提交结束时间
		paramMap.put("status", getString("status"));
		paramMap.put("realName", getString("realName"));// 真实姓名
		paramMap.put("isCount", 1); // 是否统计当前条件下的数据
		super.pageBean = memberInfoFacade.listPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("memberStatusList", MemberStatusEnum.toList()); // 会员状态查询
		return "memberBordereauxList";
	}

	/**
	 * 导出会员报表
	 */
	public void memberBordereauxExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename ="会员业务报表_" + sdf.format(new Date());
			this.getHttpResponse()
					.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[6];
			dataFields[0] = new DataField("序号", "no");
			dataFields[1] = new DataField("注册时间", "createTime");
			dataFields[2] = new DataField("会员编号", "memberNo", 25);
			dataFields[3] = new DataField("真实姓名", "realName");
			dataFields[4] = new DataField("身份证号", "cardNo", 20);
			dataFields[5] = new DataField("状态", "status");

			final String startDate = getString("startDate");
			final String endDate = getString("endDate");
			final String status = getString("status");
			final String realName = getString("realName");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				@Override
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("startDate", startDate);
					map.put("endDate", endDate);
					map.put("status", status);
					map.put("realName", realName);
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = memberInfoFacade.listPage(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							MemberInfo ap = (MemberInfo) list.get(i);
							MemberInfo memberInfo = BeanUtils.toBean(MemberInfo.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("createTime", sdf1.format(memberInfo.getCreateTime()));
							mapParam.put("memberNo", memberInfo.getMemberNo());
							mapParam.put("realName", memberInfo.getRealName());
							mapParam.put("cardNo", memberInfo.getCardNo());
							mapParam.put("status", MemberStatusEnum.getEnum(memberInfo.getStatus()).getDesc());
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("导出会员报表数据");
		} catch (UnsupportedEncodingException e) {
			log.error("下载报表", e);
		} catch (IOException e) {
			log.error("IO", e);
		}
	}

	/**
	 * 支付记录报表UI
	 * 
	 * @return
	 */
	@Permission("bordereaux:paymentrecord:view")
	public String paymentRecordBordereauxUI() {
		this.putData("TradePaymentTypeEnum", TradePaymentTypeEnum.toList());
		return "paymentRecordBordereauxList";
	}

	/**
	 * 支付记录报表
	 * 
	 * @return
	 */
	@Permission("bordereaux:paymentrecord:view")
	public String paymentRecordBordereaux() {
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String payInterfaceCode = getString("payInterfaceCode");
		paramMap.put("payInterfaceCode", payInterfaceCode);
		paramMap.put("beginDate", beginDate); // 提交开始时间
		paramMap.put("endDate", endDate); // 提交结束时间
		paramMap.put("isCount", 1); // 是否统计当前条件下的数据
		paramMap.put("status", PaymentRecordStatusEnum.SUCCESS.getValue());
		paramMap.put("merchantName", getString("merchantName"));// 商户名
		paramMap.put("paymentType", getInteger("paymentType"));// 交易类型
		super.pageBean = paymentQueryFacade.queryPaymentRecordListPage(getPageParam(), paramMap);
		this.putData("TradePaymentTypeEnum", TradePaymentTypeEnum.toList()); // 状态
		this.putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "paymentRecordBordereauxList";
	}

	/**
	 * 导出支付记录报表
	 */
	public void paymentRecordBordereauxExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			final SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
			String filename ="支付记录报表_" + sdf.format(new Date());
			this.getHttpResponse()
					.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[13];
			dataFields[0] = new DataField("序号", "no", 5);
			dataFields[1] = new DataField("支付时间", "payTime", 20);
			dataFields[2] = new DataField("商户全称", "merchantName", 30);
			dataFields[3] = new DataField("支付接口编号", "payInterfaceCode", 30);
			dataFields[4] = new DataField("交易类型", "paymentType");
			dataFields[5] = new DataField("交易流水号", "trxNo", 25);
			dataFields[6] = new DataField("商户订单号", "merchantOrderNo", 25);
			dataFields[7] = new DataField("交易状态", "status");
			dataFields[8] = new DataField("订单金额", "orderAmount");
			dataFields[9] = new DataField("支付金额", "payerPayAmount");
			dataFields[10] = new DataField("付款方手续费", "payerFee");
			dataFields[11] = new DataField("收款方手续费", "receiverFee");
			dataFields[12] = new DataField("平台收入", "platIncome");

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String paymentType = getString("paymentType");
			final String merchantName = getString("merchantName");
			final String payInterfaceCode = getString("payInterfaceCode");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				DecimalFormat df1 = new DecimalFormat("0.00");

				@Override
				public List getData() {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("beginDate", beginDate);
					map.put("endDate", endDate);
					map.put("paymentType", paymentType);
					map.put("merchantName", merchantName);
					map.put("payInterfaceCode", payInterfaceCode);
					map.put("isCount", 1); // 是否统计当前条件下的数据
					map.put("status", PaymentRecordStatusEnum.SUCCESS.getValue());
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = paymentQueryFacade.queryPaymentRecordListPage(pageParam, map);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							PaymentRecord ap = (PaymentRecord) list.get(i);
							PaymentRecord paymentRecord = BeanUtils.toBean(PaymentRecord.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("payTime",sdf1.format(paymentRecord.getPaySuccessDate()) + sdf2.format(paymentRecord.getPaySuccessTime()));
							mapParam.put("merchantName", paymentRecord.getMerchantName());
							mapParam.put("payInterfaceCode", paymentRecord.getPayInterfaceCode());
							mapParam.put("paymentType", TradePaymentTypeEnum.getEnum(paymentRecord.getPaymentType()).getDesc());
							mapParam.put("trxNo", paymentRecord.getTrxNo());
							mapParam.put("merchantOrderNo", paymentRecord.getMerchantOrderNo());
							mapParam.put("status", PaymentRecordStatusEnum.getEnum(paymentRecord.getStatus()).getDesc());
							mapParam.put("orderAmount", paymentRecord.getOrderAmount());
							mapParam.put("payerPayAmount", paymentRecord.getPayerPayAmount());
							mapParam.put("payerFee", "\t" + df1.format(paymentRecord.getPayerFee().intValue()));
							mapParam.put("receiverFee", "\t" + df1.format(paymentRecord.getReceiverFee().intValue()));
							mapParam.put("platIncome", "\t" + df1.format(paymentRecord.getPlatIncome().intValue()));
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("导出支付记录报表数据");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("下载报表", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("IO", e);
		}
	}

	/**
	 * 转账记录报表UI
	 * 
	 * @return
	 */
	@Permission("bordereaux:transferrecord:view")
	public String transferRecordBordereauxUI() {
		return "transferRecordBordereauxList";
	}

	/**
	 * 转账记录报表
	 * 
	 * @return
	 */
	@Permission("bordereaux:transferrecord:view")
	public String transferRecordBordereaux() {
		String beginDate = getString("beginDate");
		String endDate = getString("endDate");
		// 对时间进行校验
		if (beginDate != null && endDate != null) {
			// 取得两个日期之间的日数
			long a = DateUtils.daysBetween(DateUtils.toSqlTimestamp(beginDate), DateUtils.toSqlTimestamp(endDate));

			if (a < 0) {
				return this.operateError("开始时间不能大于结束时间");
			}
		} else if ((beginDate != null && endDate == null) || (beginDate == null && endDate != null)) {
			return this.operateError("不能只输入一个时间查询");
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("targetLoginName", getString("targetLoginName"));
		paramMap.put("sourceLoginName", getString("sourceLoginName"));
		paramMap.put("beginDate", beginDate); // 提交开始时间
		paramMap.put("endDate", endDate); // 提交结束时间
		paramMap.put("isCount", 1); // 是否统计当前条件下的数据
		super.pageBean = transferFacade.queryTransferRecordListPage(getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		this.putData("paymentRecordStatus", PaymentRecordStatusEnum.toList());
		return "transferRecordBordereauxList";
	}

	/**
	 * 导出转账记录报表
	 */
	public void transferRecordBordereauxExportExecl() {
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String filename ="转账记录报表_" + sdf.format(new Date());
			this.getHttpResponse().setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(filename + ".xls", "utf-8"));
			OutputStream os = getHttpResponse().getOutputStream();
			DataField[] dataFields = new DataField[9];
			dataFields[0] = new DataField("序号", "no", 5);
			dataFields[1] = new DataField("创建时间", "createTime", 20);
			dataFields[2] = new DataField("交易流水号", "trxNo", 23);
			dataFields[3] = new DataField("付款人账户", "targetLoginName", 25);
			dataFields[4] = new DataField("收款人账户", "sourceLoginName", 25);
			dataFields[5] = new DataField("订单金额", "amount");
			dataFields[6] = new DataField("手续费", "platFee");
			dataFields[7] = new DataField("转账说明", "remark", 30);
			dataFields[8] = new DataField("交易状态", "status", 10);

			final String beginDate = getString("beginDate");
			final String endDate = getString("endDate");
			final String targetLoginName = getString("targetLoginName");
			final String sourceLoginName = getString("sourceLoginName");

			new ExcelDataExportor<Object>(dataFields, new ExportDataSource<Object>() {
				DecimalFormat df1 = new DecimalFormat("0.00");

				public List getData() {
					Map<String, Object> paramMap = new HashMap<String, Object>();
					paramMap.put("targetLoginName", targetLoginName);
					paramMap.put("sourceLoginName", sourceLoginName);
					paramMap.put("beginDate", beginDate); // 提交开始时间
					paramMap.put("endDate", endDate); // 提交结束时间
					paramMap.put("isCount", 1); // 是否统计当前条件下的数据
					PageParam pageParam = new PageParam(1, exportMaxNumber);
					PageBean pageBean = transferFacade.queryTransferRecordListPage(pageParam, paramMap);

					List<Object> list = pageBean.getRecordList();
					List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
					if (!list.isEmpty()) {
						int temp = 0;
						for (int i = 0; i < list.size(); i++) {
							Map<String, Object> mapParam = new HashMap<String, Object>();
							TransferRecord ap = (TransferRecord) list.get(i);
							TransferRecord transferRecord = BeanUtils.toBean(TransferRecord.class, ap);
							mapParam.put("no", ++temp);
							mapParam.put("createTime", sdf1.format(transferRecord.getCreateTime()));
							mapParam.put("trxNo", transferRecord.getTrxNo());
							mapParam.put("targetLoginName", transferRecord.getTargetLoginName());
							mapParam.put("sourceLoginName", transferRecord.getSourceLoginName());
							mapParam.put("amount", "\t" + df1.format(transferRecord.getAmount()));
							mapParam.put("platFee", "\t" + df1.format(transferRecord.getPlatFee()));
							mapParam.put("remark", transferRecord.getRemark());
							mapParam.put("status", PaymentRecordStatusEnum.getEnum(transferRecord.getStatus()).getDesc());
							lists.add(mapParam);
						}
					}
					return lists;
				}
			}, os, MODE.EXCEL).export();
			super.logSave("转账记录报表数据");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			log.error("下载报表", e);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("IO", e);
		}
	}

	/**
	 * 结算记录报表UI
	 * 
	 * @return
	 */
	public String settlementRecordBordereauxUI() {

		return "";
	}



	/**
	 * 会员信息的查找带回，用于在其他依赖于会员信息的模块中选择会员信息进行关联.
	 * 
	 * @return memberLookupList .
	 */
	public String memberLookupList() {
		try {
			// 查询参数集合
			Map<String, Object> paramMap = new HashMap<String, Object>();
			String memberNo = getString("memberNo");
			paramMap.put("memberNo", memberNo);
			paramMap.put("status", 100);// 状态
			super.pageBean = memberInfoFacade.listPage(this.getPageParam(), paramMap);
			pushData(pageBean);
			putData("memberNo", memberNo);
			return "memberLookupList";
		} catch (Exception e) {
			log.error("== memberLookupList get data exception:", e);
			return operateError("获取商户信息失败");
		}
	}

	/**
	 * 计费接口查找带回
	 * 
	 * @return
	 */
	public String calCostInterfaceListLookupList() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("interfaceCode", getString("interfaceCode"));
		paramMap.put("interfaceName", getString("interfaceName"));
		super.pageBean = calCostInterfaceFacade.listPage(this.getPageParam(), paramMap);
		this.pushData(pageBean);
		this.pushData(paramMap);
		return "calCostInterfaceListLookupList";
	}
}

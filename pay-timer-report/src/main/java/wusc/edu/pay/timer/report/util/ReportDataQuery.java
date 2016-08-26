package wusc.edu.pay.timer.report.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.DateUtils;
import wusc.edu.pay.facade.bank.entity.BankChannel;
import wusc.edu.pay.facade.bank.service.BankAgreementFacade;
import wusc.edu.pay.facade.bank.service.BankChannelFacade;
import wusc.edu.pay.facade.cost.service.CalCostOrderFacade;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;
import wusc.edu.pay.facade.trade.exception.TradeBizException;
import wusc.edu.pay.facade.trade.service.PaymentQueryFacade;
import wusc.edu.pay.facade.user.enums.MerchantStatusEnum;
import wusc.edu.pay.facade.user.service.MemberInfoFacade;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;
import wusc.edu.pay.facade.user.service.UserQueryFacade;


/**
 * 
 * @描述: 报表源数据集中获取类.
 * @创建: 2014-5-7,上午9:37:11
 * @版本: V1.0
 * 
 */
@Component("reportDataQuery")
public class ReportDataQuery {

	/** 分页获取数据时的每页获取数据记录数 */
	private static final int NUM_PER_PAGE = ReportProperties.BATCH_GET_NUMBER;

	final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
	public final String beginTime = DateUtils.getYesterday();
	public final String endTime = DateUtils.getYesterday();
	@Autowired
	private PaymentQueryFacade paymentQueryFacade;
	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	@Autowired
	private BankChannelFacade bankChannelFacade;
	@Autowired
	private UserQueryFacade userQueryFacade;
	@Autowired
	private MemberInfoFacade memberInfoFacade;
	@Autowired
	private BankAgreementFacade bankAgreementFacade;
	@Autowired
	private RemitProcessFacade remitProcessFacade;
	@Autowired
	private CalCostOrderFacade calCostOrderFacade;
	
	/**
	 * 
	 * @return
	 */
	public List<Object> getPaymentRecordList(Map<String, Object> paramMap) throws TradeBizException {
		List<Object> paymentList = new ArrayList<Object>();
		try {
			PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
			PageBean paymentPageBean = paymentQueryFacade.queryPaymentRecordListPage(pageParam, paramMap);
			paymentList.addAll(paymentPageBean.getRecordList());
			int pageCount = paymentPageBean.getPageCount();// 总页数
			if (pageCount > 1) {
				for (int i = 2; i <= pageCount; i++) {
					pageParam.setPageNum(i);
					paymentPageBean = paymentQueryFacade.queryPaymentRecordListPage(pageParam, paramMap);
					paymentList.addAll(paymentPageBean.getRecordList());
				}
			}
		} catch (TradeBizException e) {
			e.printStackTrace();
			throw new TradeBizException();
		}
		
		return paymentList;
	}

	/**
	 * 获取在线商户全部数据
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Object> getOnlineMerchantList(Map<String, Object> paramMap) {
		List<Object> merchantList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		List<String> inStatus = new ArrayList<String>();
		inStatus.add(String.valueOf(MerchantStatusEnum.ACTIVE.getValue()));
		inStatus.add(String.valueOf(MerchantStatusEnum.INACTIVE.getValue()));
		paramMap.put("inStatus", inStatus);// 激活或者冻结状态的商户
		PageBean merchantPageBean = merchantOnlineFacade.listMerchantListPage(pageParam, paramMap);
		merchantList.addAll(merchantPageBean.getRecordList());
		int pageCount = merchantPageBean.getPageCount();// 总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				merchantPageBean = merchantOnlineFacade.listMerchantListPage(pageParam, paramMap);
				merchantList.addAll(merchantPageBean.getRecordList());
			}
		}
		return merchantList;
	}

	/**
	 * 获取user全部数据
	 */
	public List<Object> getUserInfoList(Map<String, Object> paramMap) {
		List<Object> userList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean userPageBean = userQueryFacade.listUserInfoListPage(pageParam, paramMap);
		userList.addAll(userPageBean.getRecordList());
		int pageCount = userPageBean.getPageCount();// 总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				userPageBean = userQueryFacade.listUserInfoListPage(pageParam, paramMap);
				userList.addAll(userPageBean.getRecordList());
			}
		}
		return userList;
	}

	/**
	 * 获取POS渠道信息
	 * 
	 * @return
	 */

/*	public List<Object> getChannelList(Map<String, Object> paramMap) {
		List<Object> posChannelList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean posChannelBean = channelFacade.listPageChannel(pageParam, paramMap);
		posChannelList.addAll(posChannelBean.getRecordList());
		int pageCount = posChannelBean.getPageCount();// 总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				posChannelBean = channelFacade.listPageChannel(pageParam, paramMap);
				posChannelList.addAll(posChannelBean.getRecordList());
			}
		}
		return posChannelList;
	}*/

	/**
	 * 获取POS账户信息
	 * 
	 * @return
	 */
/*	public List<Object> getPosAccountList(Map<String, Object> paramMap) {
		List<Object> posAccountList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean posAccountBean = posBankAccountFacade.listPageBankAccount(pageParam, paramMap);
		posAccountList.addAll(posAccountBean.getRecordList());
		int pageCount = posAccountBean.getPageCount();// 总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				posAccountBean = posBankAccountFacade.listPageBankAccount(pageParam, paramMap);
				posAccountList.addAll(posAccountBean.getRecordList());
			}
		}
		return posAccountList;
	}*/

	/**
	 * 获取会员信息
	 */
	public List<Object> getMemberList(Map<String, Object> paramMap) {
		List<Object> memberList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean memberPageBean = memberInfoFacade.listPage(pageParam, paramMap);
		memberList.addAll(memberPageBean.getRecordList());
		int pageCount = memberPageBean.getPageCount();// 获取总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				memberPageBean = memberInfoFacade.listPage(pageParam, paramMap);
				memberList.addAll(memberPageBean.getRecordList());
			}
		}
		return memberList;
	}

	public List<BankChannel> getBankchannelList(Map<String, Object> paramMap) {
		return bankChannelFacade.listBy(paramMap);
	}

	public List<Object> getBankAgreementList(Map<String, Object> paramMap) {
		List<Object> bankAgreementList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean bankAgreementPageBean = bankAgreementFacade.listPage(pageParam, paramMap);
		bankAgreementList.addAll(bankAgreementPageBean.getRecordList());
		int pageCount = bankAgreementPageBean.getPageCount();// 获取总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				bankAgreementPageBean = bankAgreementFacade.listPage(pageParam, paramMap);
				bankAgreementList.addAll(bankAgreementPageBean.getRecordList());
			}
		}
		return bankAgreementList;

	}

//	/**
//	 * 获取备付金银行下的所有账户
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	public List<Object> getReservesBankAccountList(String bringId) {
//		List<Object> adjustList = accountingAccountTitleNotesFacade.listAdjust(bringId);
//		return adjustList;
//
//	}
//
//	/**
//	 * 获取备付金银行数据.
//	 * 
//	 * @param paramMap
//	 * @return
//	 */
//	public List<Object> getReservesBankList(Map<String, Object> paramMap) {
//		List<Object> accountingCheckResultList = new ArrayList<Object>();
//		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
//		PageBean reservesBankPageBean = accountingAccountTitleDailyAccountFacade.listPage(pageParam,
//				paramMap);
//		accountingCheckResultList.addAll(reservesBankPageBean.getRecordList());
//		int pageCount = reservesBankPageBean.getPageCount();// 获取总页数
//		if (pageCount > 1) {
//			for (int i = 2; i <= pageCount; i++) {
//				pageParam.setPageNum(i);
//				reservesBankPageBean = accountingAccountTitleDailyAccountFacade
//						.listPage(pageParam, paramMap);
//				accountingCheckResultList.addAll(reservesBankPageBean.getRecordList());
//			}
//		}
//		return accountingCheckResultList;
//
//	}

	/**
	 * 获取所有的账户数据.
	 * 
	 * @param paramMap
	 * @return
	 */
	public static List<Object> getAccountList(Map<String, Object> paramMap) {
		List<Object> accountList = new ArrayList<Object>();
		// PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		// PageBean accountPageBean =
		// LoadFacade.accountQueryFacade.queryAccountListPage(pageParam,
		// paramMap);
		// accountList.addAll(accountPageBean.getRecordList());
		// int pageCount = accountPageBean.getPageCount();// 获取总页数
		// if (pageCount > 1) {
		// for (int i = 2; i <= pageCount; i++) {
		// pageParam.setPageNum(i);
		// accountPageBean =
		// LoadFacade.accountQueryFacade.queryAccountListPage(pageParam,
		// paramMap);
		// accountList.addAll(accountPageBean.getRecordList());
		// }
		// }
		return accountList;
	}

	/**
	 * 查询打款记录
	 * 
	 * @param paramMap
	 * @return
	 */
	public List<Object> getRemittanceRecordList(Map<String, Object> paramMap) {
		List<Object> remittanceRecordList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean accountPageBean = remitProcessFacade.listPage(pageParam, paramMap);
		remittanceRecordList.addAll(accountPageBean.getRecordList());
		int pageCount = accountPageBean.getPageCount();// 获取总页数
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				accountPageBean = remitProcessFacade.listPage(pageParam, paramMap);
				remittanceRecordList.addAll(accountPageBean.getRecordList());
			}
		}
		return remittanceRecordList;
	}

	
	//计费成本订单数据
	public List<Object> getCalCostOrderList(Map<String, Object> paramMap) {
		List<Object> calCostOrderList = new ArrayList<Object>();
		PageParam pageParam = new PageParam(1, NUM_PER_PAGE);
		PageBean calCostOrderPageBean = calCostOrderFacade.listPage(pageParam, paramMap);
		calCostOrderList.addAll(calCostOrderPageBean.getRecordList());
		int pageCount = calCostOrderPageBean.getPageCount();
		if (pageCount > 1) {
			for (int i = 2; i <= pageCount; i++) {
				pageParam.setPageNum(i);
				calCostOrderPageBean = calCostOrderFacade.listPage(pageParam, paramMap);
				calCostOrderList.addAll(calCostOrderPageBean.getRecordList());
			}
		}
		return calCostOrderList;
	}

}

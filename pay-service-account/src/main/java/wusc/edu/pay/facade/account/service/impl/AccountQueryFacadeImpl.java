package wusc.edu.pay.facade.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.common.utils.string.StringUtil;
import wusc.edu.pay.core.account.dao.AccountDao;
import wusc.edu.pay.core.account.dao.AccountHistoryDao;
import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.entity.AccountHistory;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.vo.DailyCollectAccountHistoryVo;


/**
 * 账户查询
 * 
 * @author healy
 * 
 */
@Component("accountQueryFacade")
public class AccountQueryFacadeImpl implements AccountQueryFacade {

	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountHistoryDao accountHistoryDao;

	/**
	 * 账户历史查询.
	 * 
	 * @param pageParam
	 *            分页参数.
	 * @param paramMap
	 *            查询参数.
	 * @return AccountHistoryList.
	 */
	public PageBean queryAccountHistoryListPage(PageParam pageParam, Map<String, Object> paramMap) {
		return accountHistoryDao.listPage(pageParam, paramMap);
	}

	/**
	 * 获取账户历史
	 * 
	 * @param accountNo
	 * @param requestNo
	 * @param trxType
	 * @return
	 */
	public AccountHistory getAccountHistoryByAccountNo_requestNo_trxType(String accountNo, String requestNo, Integer trxType) {
		return accountHistoryDao.getByAccountNo_requestNo_trxType(accountNo, requestNo, trxType);
	}

	/**
	 * 根据用户编号获取账户信息 .
	 * 
	 * @param userNo
	 *            用户编号.
	 * @return account 查询到的账户信息.
	 */
	public Account getAccountByUserNo(String userNo) {
		if (StringUtil.isBlank(userNo)) {
			return null;
		}
		return accountDao.getByUserNo_IsPessimist(userNo, false);
	}

	/**
	 * 根据账户编号查询账户信息.
	 * 
	 * @param accountNo
	 *            账户编号.
	 * @return account 查询到的账户信息.
	 */
	public Account getAccountByAccountNo(String accountNo) {
		return accountDao.getByAccountNo(accountNo);
	}

	/**
	 * 日汇总账户待结算金额 .
	 * 
	 * @param accountNo
	 *            账户编号.
	 * @param beginDate
	 *            汇总开始日期.
	 * @param endDate
	 *            汇总截止日期.
	 * @param fundDirection
	 *            资金变动方向(可传null).
	 * @return DailyCollectAccountHistoryVoList.
	 */
	public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo(String accountNo, String statDate, Integer riskDay,
			Integer fundDirection) {

		return accountHistoryDao.listDailyCollectAccountHistoryVo(accountNo, statDate, riskDay, fundDirection);

	}

	/**
	 * 日汇总账户待结算金额_针对单笔t+0结算
	 * 
	 * @param accountNo
	 * @param requestNo
	 */
	public List<DailyCollectAccountHistoryVo> listDailyCollectAccountHistoryVo_t0(String accountNo, String requestNo) {
		return accountHistoryDao.listDailyCollectAccountHistoryVo_t0(accountNo, requestNo);
	}
}

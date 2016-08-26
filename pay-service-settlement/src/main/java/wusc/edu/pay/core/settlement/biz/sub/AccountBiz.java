package wusc.edu.pay.core.settlement.biz.sub;

import java.math.BigDecimal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.account.entity.Account;
import wusc.edu.pay.facade.account.enums.AccountStatusEnum;
import wusc.edu.pay.facade.account.service.AccountQueryFacade;
import wusc.edu.pay.facade.account.service.AccountSettFacade;
import wusc.edu.pay.facade.settlement.DTO.LaunchSettleVo;
import wusc.edu.pay.facade.settlement.entity.SettRecord;
import wusc.edu.pay.facade.settlement.exception.SettBizException;
import wusc.edu.pay.facade.user.utils.EnumChangeUtils;


@Component("accountBiz")
public class AccountBiz {

	@Autowired
	private AccountQueryFacade accountQueryFacade;

	@Autowired
	private AccountSettFacade accountSettFacade;

	private static final Log log = LogFactory.getLog(AccountBiz.class);

	/** 得到账户余额 **/
	public BigDecimal getAccountBalance(LaunchSettleVo launchSettleVo, BigDecimal balance) {
		Account account = null;
		if (balance == null) {
			account = accountQueryFacade.getAccountByAccountNo(launchSettleVo.getAccountNo());
			if (account == null) {
				throw new SettBizException(SettBizException.ACCOUNT_IS_NULL, "账户编号为:" + launchSettleVo.getAccountNo() + "的结算账户不存在");
			}
			if (account.getStatus() != AccountStatusEnum.ACTIVE.getValue()) {
				throw new SettBizException(SettBizException.ACCOUNT_IS_NOT_ACTIVE, "账户编号为:" + launchSettleVo.getAccountNo() + "的结算账户状态不是激活状态");
			}
			balance = BigDecimal.valueOf(account.getAvailableBalance());
		}
		return balance;
	}

	/**
	 * 结算成功_T+0
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 */
	public void settSuccessT0(SettRecord settRecord, String trxNo) {

		// 账户处理
		accountSettFacade.settCreateT0(settRecord.getUserNo(), settRecord.getSettAmount().doubleValue(),settRecord.getRemitNo(), trxNo);
		
		log.info("==>settSuccessT0<==");
	}

	/**
	 * 结算成功
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 */
	public void settSuccess(SettRecord settRecord) {
		// 账户处理
		accountSettFacade.settSuccess(settRecord.getUserNo(), settRecord.getSettAmount().doubleValue(), settRecord.getRemitNo());

		log.info("==>settSuccessT0<==");
	}

	/**
	 * 结算汇总成功
	 * 
	 * @param userNo
	 * @param statDate
	 * @param riskDay
	 */
	public void settCollectSuccess(String userNo, String statDate, Integer riskDay) {
		// 账户处理
		accountSettFacade.settCollectSuccess(userNo, statDate, riskDay);
		
		log.info("==>settCollectSuccess<==");
	}

	/**
	 * 结算创建
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 * @param lastId
	 * @param atm 
	 */
	public void settCreate(SettRecord settRecord, long lastId) {
		// 账户处理
		accountSettFacade.settCreate(settRecord.getUserNo(), settRecord.getSettAmount().doubleValue(), settRecord.getRemitNo(), lastId);
	}

	/**
	 * 结算失败
	 * 
	 * @param userNo
	 * @param settAmount
	 * @param requestNo
	 */
	public void settFail(SettRecord settRecord) {
		// 账户处理
		accountSettFacade.settFail(settRecord.getUserNo(), settRecord.getSettAmount().doubleValue(), settRecord.getRemitNo());
	}

}

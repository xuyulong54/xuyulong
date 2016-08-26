package wusc.edu.pay.core.remit.biz.sub;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.facade.account.enums.AccountFundDirectionEnum;
import wusc.edu.pay.facade.account.enums.AccountTradeTypeEnum;
import wusc.edu.pay.facade.account.service.AccountTransactionFacade;
import wusc.edu.pay.facade.account.vo.AccountTransactionVo;
import wusc.edu.pay.facade.remit.entity.RemitBatch;

import com.alibaba.fastjson.JSONObject;

@Component("accountBiz")
public class AccountBiz {

	@Autowired
	private AccountTransactionFacade accountTransactionFacade;

	private static final Log log = LogFactory.getLog(AccountBiz.class);
	
	

	/**
	 * 打款记录创建_备付金账户处理
	 * 
	 * @param remitBatch
	 */
	public void remitCreate(List<RemitBatch> listRemitBatch) {

		log.info("==>remitCreate");

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();
		for (int i = 0; i < listRemitBatch.size(); i++) {
			AccountTransactionVo vo = new AccountTransactionVo();
			vo.setAccountFundDirection(AccountFundDirectionEnum.FROZEN);
			vo.setUserNo(listRemitBatch.get(i).getRemitBankAccountNo());
			vo.setFrezonAmount(listRemitBatch.get(i).getAcceptSucAmount().doubleValue()); // 受理成功金额
			vo.setRequestNo(listRemitBatch.get(i).getBatchNo());
			vo.setTradeType(AccountTradeTypeEnum.REMIT);
			vo.setDesc(AccountTradeTypeEnum.REMIT.getDesc());
			voList.add(vo);
		}

		accountTransactionFacade.execute(voList);

		// TODO 需要重新定义会计模板

		log.info("==>vo:" + JSONObject.toJSONString(voList));
		log.info("==>remitCreate<==");
	}

	/**
	 * 打款记录创建_备付金账户处理
	 * 
	 * @param remitBatch
	 */
	public void remitCreate(RemitBatch remitBatch) {

		log.info("==>remitCreate");

		AccountTransactionVo vo = new AccountTransactionVo();
		vo.setAccountFundDirection(AccountFundDirectionEnum.FROZEN);
		vo.setUserNo(remitBatch.getRemitBankAccountNo());
		vo.setFrezonAmount(remitBatch.getAcceptSucAmount().doubleValue()); // 受理成功金额
		vo.setRequestNo(remitBatch.getBatchNo());
		vo.setTradeType(AccountTradeTypeEnum.REMIT);
		vo.setDesc(AccountTradeTypeEnum.REMIT.getDesc());

		accountTransactionFacade.execute(vo);

		// TODO 需要重新定义会计模板

		log.info("==>vo:" + JSONObject.toJSONString(vo));
		log.info("==>remitCreate<==");
	}

	/**
	 * 打款成功_备付金账户处理
	 * 
	 * @param remitBatch
	 * @param remitChannelCode
	 * @return
	 */
	public void remitSuccess(RemitBatch remitBatch) {

		log.info("==>remitSuccess");

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();

		// 出款银行，虚拟账户解冻+减款
		AccountTransactionVo vo = new AccountTransactionVo();
		vo.setAccountFundDirection(AccountFundDirectionEnum.UNFROZEN);
		vo.setUserNo(remitBatch.getRemitBankAccountNo());
		vo.setUnFrezonAmount(remitBatch.getAcceptSucAmount().doubleValue()); // 受理成功金额
		vo.setRequestNo(remitBatch.getBatchNo());
		vo.setTradeType(AccountTradeTypeEnum.REMIT);
		vo.setDesc(AccountTradeTypeEnum.REMIT.getDesc());
		voList.add(vo);

		vo = new AccountTransactionVo();
		vo.setAccountFundDirection(AccountFundDirectionEnum.SUB);
		vo.setUserNo(remitBatch.getRemitBankAccountNo());
		vo.setAmount(remitBatch.getProcessSucAmount().doubleValue()); // 处理成功金额
		vo.setRequestNo(remitBatch.getBatchNo());
		vo.setTradeType(AccountTradeTypeEnum.REMIT);
		vo.setDesc(AccountTradeTypeEnum.REMIT.getDesc());
		voList.add(vo);

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		log.info("==>remitSuccess<==");

	}

	/**
	 * 打款失败_备付金账户处理
	 * 
	 * @param remitBatch
	 * @return
	 */
	public void remitFail(RemitBatch remitBatch) {

		log.info("==>remitFail");

		List<AccountTransactionVo> voList = new ArrayList<AccountTransactionVo>();

		AccountTransactionVo vo = new AccountTransactionVo();
		vo.setAccountFundDirection(AccountFundDirectionEnum.UNFROZEN);
		vo.setUserNo(remitBatch.getRemitBankAccountNo());
		vo.setUnFrezonAmount(remitBatch.getAcceptSucAmount().doubleValue()); // 成功受理金额
		vo.setRequestNo(remitBatch.getBatchNo());
		vo.setTradeType(AccountTradeTypeEnum.REMIT);
		vo.setDesc(AccountTradeTypeEnum.REMIT.getDesc());
		voList.add(vo);

		accountTransactionFacade.execute(voList);

		log.info("==>voList:" + JSONObject.toJSONString(voList));

		// TODO 需要重新定义会计模板

		log.info("==>remitFail<==");

	}

}

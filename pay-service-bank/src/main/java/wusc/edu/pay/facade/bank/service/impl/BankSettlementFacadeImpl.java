package wusc.edu.pay.facade.bank.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.exceptions.BizException;
import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.bank.biz.BankSettlementBiz;
import wusc.edu.pay.facade.bank.entity.BankSettlement;
import wusc.edu.pay.facade.bank.service.BankSettlementFacade;


@Component("bankSettlementFacade")
public class BankSettlementFacadeImpl implements BankSettlementFacade {
	
	@Autowired
	private BankSettlementBiz bankSettlementBiz;
	
	@Override
	public long create(BankSettlement bankSettlement) {
		return bankSettlementBiz.create(bankSettlement);
	}

	@Override
	public long update(BankSettlement bankSettlement) {
		return bankSettlementBiz.update(bankSettlement);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return bankSettlementBiz.listPage(pageParam, paramMap);
	}

	@Override
	public BankSettlement getById(long id) {
		return bankSettlementBiz.getById(id);
	}

	/**
	 * 获取银行渠道状态为可用(100)的银行结算信息，包括以下字段内容:<br/>
	 * "bankChannelCode:银行渠道编号"、<br/>
	 * "tradeGainCheckFileTime:业务对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "fundGainCheckFileTime:清算对账文件获取时间，如：1.15代表每天凌晨1点15分后获取对账文件"、<br/>
	 * "settleCycle:结算周期：T+X"
	 * @return list.
	 */
	@Override
	public List listAvailableBankSettlementInfo() {
		return bankSettlementBiz.listAvailableBankSettlementInfo();
	}

	/**
	 * 根据银行渠道编号获取银行结算信息.
	 * @param bankChannelCode 银行渠道编号.
	 * @return BankSettlement.
	 */
	@Override
	public BankSettlement getByBankBankChannelCode(String bankChannelCode) {
		return bankSettlementBiz.getByBankBankChannelCode(bankChannelCode);
	}

	/**
	 * 获取银行渠道可用状态下的银行账号
	 * @return
	 * @throws BizException
	 */
	@Override
	public List listAvailableBankAccount() throws BizException {
		return bankSettlementBiz.listAvailableBankAccount();
	}

}

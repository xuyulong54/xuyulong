package wusc.edu.pay.core.bank.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.core.biz.BaseBizImpl;
import wusc.edu.pay.common.core.dao.BaseDao;
import wusc.edu.pay.core.bank.dao.BankAgreementDao;
import wusc.edu.pay.facade.bank.entity.BankAgreement;


@Component("bankAgreementBiz")
public class BankAgreementBiz extends BaseBizImpl<BankAgreement> {

	@Autowired
	private BankAgreementDao bankAgreementDao;

	@Override
	protected BaseDao<BankAgreement> getDao() {
		return bankAgreementDao;
	}

}

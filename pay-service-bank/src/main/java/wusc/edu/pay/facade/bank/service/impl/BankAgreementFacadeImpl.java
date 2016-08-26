package wusc.edu.pay.facade.bank.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.core.bank.biz.BankAgreementBiz;
import wusc.edu.pay.facade.bank.entity.BankAgreement;
import wusc.edu.pay.facade.bank.service.BankAgreementFacade;


@Component("bankAgreementFacade")
public class BankAgreementFacadeImpl implements BankAgreementFacade {
	
	@Autowired
	private BankAgreementBiz bankAgreementBiz;

	@Override
	public long create(BankAgreement entity) {
		return bankAgreementBiz.create(entity);
	}

	@Override
	public long update(BankAgreement entity) {
		return bankAgreementBiz.update(entity);
	}

	@Override
	public PageBean listPage(PageParam pageParam, Map<String, Object> paramMap) {
		return bankAgreementBiz.listPage(pageParam, paramMap);
	}

	@Override
	public BankAgreement getById(long id) {
		BankAgreement bankAgreement = bankAgreementBiz.getById(id);
		return bankAgreement;
	} 
	
}

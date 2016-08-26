package wusc.edu.pay.test.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankAgreement;
import wusc.edu.pay.facade.bank.service.BankAgreementFacade;


public class TsBankAgreement extends TsBase {

	@Test
	public void create() throws Exception {
		BankAgreementFacade bankAgreementFacade = (BankAgreementFacade) context.getBean("bankAgreementFacade");
		// 创建银行协议
		BankAgreement bankAgreement = new BankAgreement();
		bankAgreement.setBankSequence("bankSequence");
		bankAgreement.setAgreementNo("bnxxxxxxx");
		bankAgreement.setAmountSystem(1.1);
		bankAgreement.setArea("asia");
		bankAgreement.setBankCode("ICBC");
		bankAgreement.setCardType("100");
		bankAgreement.setCity("广州");
		bankAgreement.setCommunicationAddress("江海大道中");
		bankAgreement.setCommunicationMode("HTTP");
		bankAgreement.setContractOANo("oano1111");
		bankAgreement.setCreateTime(new Date());
		bankAgreement.setInterfaceName("ICBC-TEST-INTERFACE");
		bankAgreement.setLinkMan("ZWS");
		bankAgreement.setLinkType(1);
		bankAgreement.setMerchantNo("888000000000000");
		bankAgreement.setMerchantType("B2B");
		bankAgreement.setOfflineTime(new Date());
		bankAgreement.setOnlineTime(new Date());
		bankAgreement.setProvince("广东");
		bankAgreement.setRemark("this is remark");

		bankAgreementFacade.create(bankAgreement);

		// 列表
		PageParam pageParam = new PageParam(1, 1);
		PageBean pageBean = bankAgreementFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pageBean.getRecordList();
		// 更新

		for (Object object : list) {
			BankAgreement agreement = (BankAgreement) object;
			agreement.setOfflineTime(new Date());
			agreement.setOfflineTime(new Date());
			agreement.setProvince("广东1");
			bankAgreementFacade.update(agreement);
		}
	}
}

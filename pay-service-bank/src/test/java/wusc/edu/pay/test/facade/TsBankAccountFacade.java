package wusc.edu.pay.test.facade;

import java.util.Date;

import org.junit.Test;

import wusc.edu.pay.facade.bank.entity.CardBin;
import wusc.edu.pay.facade.bank.service.CardBinFacade;


public class TsBankAccountFacade extends TsBase {

	@Test
	public void createCardBin() throws Exception {
		CardBinFacade cardBinFacade = (CardBinFacade) context.getBean("cardBinFacade");

		System.out.println(cardBinFacade);

		CardBin cardBin = new CardBin();
		cardBin.setBankCode("xxxxx");
		cardBin.setBankName("test card biun");
		cardBin.setCardBin("12345");
		cardBin.setCardKind(1);
		cardBin.setCardLength(20);
		cardBin.setCardName("中国卡");
		cardBin.setLastUpdateTime(new Date());
		cardBin.setLastUpdatorId(1L);
		cardBin.setLastUpdatorName("test last name");
		cardBin.setStatus(1);

		Long result = cardBinFacade.create(cardBin);

		System.out.println(result + "==============================================");

	}

}

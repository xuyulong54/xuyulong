package wusc.edu.pay.test.facade;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import wusc.edu.pay.common.page.PageBean;
import wusc.edu.pay.common.page.PageParam;
import wusc.edu.pay.facade.bank.entity.BankAccount;
import wusc.edu.pay.facade.bank.service.BankAccountFacade;


public class TsBankAccount extends TsBase {

	@Test
	public void createBankAccount() throws Exception {
		BankAccountFacade bankAccountFacade = (BankAccountFacade) context.getBean("bankAccountFacade");
		// 创建一个银行账户
		BankAccount bankAccount = new BankAccount();
		bankAccount.setAccountMngFee(20.0);
		bankAccount.setAccountNature(1);
		bankAccount.setAccountStatus(1);
		bankAccount.setAccountType(1);
		bankAccount.setBankAccount("1111111111111111");
		bankAccount.setBankNo("2222222222222222");
		bankAccount.setBigAmounttransferObligate("1+21=");
		bankAccount.setComments("test");
		bankAccount.setCooperationWay(1);
		bankAccount.setIsOnlineBank(1);
		bankAccount.setLinkMan("zws");
		bankAccount.setOnlineBankFee(2.2);
		bankAccount.setOnlineBankObligate("onlinebankobli");
		bankAccount.setOpenAccountObligate("openaccount");
		bankAccount.setOpenBank("openbank");
		bankAccount.setOpenBankAddress("openbankaddress");
		bankAccount.setOpendate(new Date());
		bankAccount.setOperator("operator");
		bankAccount.setPledgefDeposits(3.3);
		bankAccount.setProposer("proposeer");
		bankAccount.setReceiptForm(2);
		bankAccount.setReserveSeal("a");
		bankAccount.setUserName("username");
		Long result = bankAccountFacade.create(bankAccount);
		System.out.println("=====================================>" + result);
		// 分页查询
		PageParam pageParam = new PageParam(1, 1);
		PageBean pagebean = bankAccountFacade.listPage(pageParam, new HashMap<String, Object>());
		List<Object> list = pagebean.getRecordList();
		System.out.println(list.size() + "==================================");
		// 修改
		for (Object object : list) {
			BankAccount account = (BankAccount) object;
			account.setOpendate(new Date());
			account.setBankAccount("9999999");
			bankAccountFacade.update(account);
		}
	}

}

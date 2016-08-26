/**
 * com
 * className：Test.java
 * @version：1.0
 * date: 2014-9-19-下午2:07:50
 *   2014中益智正公司-版权所有
 */
package wusc.edu.pay.service.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.facade.remit.entity.BatchSettlRequestParam;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.enums.TradeSourcesEnum;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;


/**
 * className：TsLocalRemitTs
 * Function： 测试打款
 * date: 2014-9-19-上午11:50:47
 * @author laich
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/spring-context.xml"})
public class TsLocalRemitTs {

	@Autowired 
	private  RemitRequestFacade  remitRequestFacade;
	
	/**
	 * 
	 * @throws RemitBizException
	 * @throws SQLException 
	 * @since  1.0
	 */
	@Test
	public void testRemitRequest() throws RemitBizException, SQLException{
		BatchSettlRequestParam pb = new BatchSettlRequestParam();
		 List<SettlRequestParam> settReqeustList = new ArrayList();
		 pb.setTotalAmount(50d);
		 pb.setTotalNum(1);
		for(int i = 0 ; i < 1 ; i++){
			SettlRequestParam testMerchant = new SettlRequestParam();
			testMerchant.setTradeSource(TradeSourcesEnum.MEMBER_CASH.getValue());
			testMerchant.setTradeType(TradeSourcesEnum.MEMBER_CASH.getValue());
			testMerchant.setRequestNo(System.currentTimeMillis()+"") ;//打款请求号
			testMerchant.setIsUrgent(1) ;//是否加急
			testMerchant.setBankAccountName("测试2200003220");//收款人银行户名
			testMerchant.setBankAccountNo("600033029");//收款人银行账号
			testMerchant.setBankName("中国工商银行");//收款银行名称
			testMerchant.setBankChannelNo("102659000491");//收款银行行号
			testMerchant.setAmount(50d);//提现金额
			testMerchant.setUserNo("888000000000000");//商户编号
			testMerchant.setAccountNo("80080011000005350101");//会员账户编号
			testMerchant.setProvince("广东省");//收款账号省份
			testMerchant.setCity("广州市");//收款账号城市
			testMerchant.setBankAccountType(BankAccountTypeEnum.PUBLIC_ACCOUNTS.getValue());//银行账户类型
			settReqeustList.add(testMerchant);
		}
		
		pb.setSettReqeustList(settReqeustList);
		//remitRequestFacade.merchantAutoSettlement(pb);
	}
}

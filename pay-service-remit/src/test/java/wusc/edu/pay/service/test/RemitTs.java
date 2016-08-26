package wusc.edu.pay.service.test;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import wusc.edu.pay.common.enums.BankAccountTypeEnum;
import wusc.edu.pay.facade.remit.entity.BatchSettlRequestParam;
import wusc.edu.pay.facade.remit.entity.RemitBankType;
import wusc.edu.pay.facade.remit.entity.RemitBatch;
import wusc.edu.pay.facade.remit.entity.RemitProcess;
import wusc.edu.pay.facade.remit.entity.RemitRequest;
import wusc.edu.pay.facade.remit.entity.SettlRequestParam;
import wusc.edu.pay.facade.remit.exceptions.RemitBizException;
import wusc.edu.pay.facade.remit.service.RemitBankTypeFacade;
import wusc.edu.pay.facade.remit.service.RemitBatchFacade;
import wusc.edu.pay.facade.remit.service.RemitProcessFacade;
import wusc.edu.pay.facade.remit.service.RemitRequestFacade;


public class RemitTs extends TestCase {
	ClassPathXmlApplicationContext context;
	@Autowired
	RemitRequestFacade remitRequestFacade;
	@Autowired
	RemitProcessFacade remitProcessFacade;
	@Autowired
	RemitBatchFacade remitBatchFacade;
	@Autowired
	RemitBankTypeFacade remitBankTypeFacade;
	
	public void setUp() throws Exception {
		context = new ClassPathXmlApplicationContext(new String[] { "dubbo-consumer-remit-test.xml" });
		super.setUp();
		remitRequestFacade = (RemitRequestFacade)context.getBean("remitRequestFacade");
		remitProcessFacade = (RemitProcessFacade)context.getBean("remitProcessFacade");
		remitBatchFacade = (RemitBatchFacade)context.getBean("remitBatchFacade");
		remitBankTypeFacade = (RemitBankTypeFacade)context.getBean("remitBankTypeFacade");
	}
	/**
	 * 生成打款请求
	 */
	public void testCreatRequest(){
		RemitRequest remitRequest = new RemitRequest();
		remitRequest.setRequestNo("100022332");
		remitRequest.setFlowNo("23423423523");
		remitRequest.setTradeInitiator(1);
		remitRequest.setAccountType(100);
		remitRequest.setIsUrgent(100);
		remitRequest.setAccountName("李大伟");
		remitRequest.setAccountNo("622848993344002344");
		remitRequest.setBankChannelNo("12100332");
		remitRequest.setBankName("平安银行");
		remitRequest.setProvince("广东省");
		remitRequest.setCity("广州市");
		remitRequest.setCurrency("");
		remitRequest.setNotifyAddress("");
		remitRequest.setStatus(1);
		remitRequest.setAmount(new BigDecimal(10.0));
		remitRequest.setIsAutoProcess(100);
		remitRequest.setUserNo("20011233");
		remitRequest.setBusinessType(100);
		remitRequest.setCreator("100998732");
		remitRequest.setConfirm("");
		remitRequest.setConfirmDate(new Date());
		remitRequest.setCancelReason("");
		remitRequest.setBankRemark("");
		remitRequestFacade.create(remitRequest);
	}
	/**
	 * 修改请求纪录
	 */
	public void testUpdateRequest(){
		RemitRequest remitRequest = remitRequestFacade.getById(1);
		System.out.println(remitRequest);
		remitRequest.setBankRemark("测试数据来了");
		remitRequestFacade.update(remitRequest);
	}
	
	/**
	 * 创建处理记录实体
	 */
	public void testCreatProcess(){
		RemitProcess remitProcess = new RemitProcess();
		remitProcess.setRequestNo("1277777");
		remitProcess.setFlowNo("1277777");
		remitProcess.setChannelCode("PINGANBENK_EWR");
		remitProcess.setBatchNo("23423532423");
		remitProcess.setTradeInitiator(1);
		remitProcess.setAccountType(1);
		remitProcess.setAccountName("3243");
		remitProcess.setAccountNo("200993");
		remitProcess.setBankChannelNo("1300023");
		remitProcess.setBankName("平安银行");
		remitProcess.setProvince("广东省");
		remitProcess.setCity("广州市");
		remitProcess.setCurrency("RMB");
		remitProcess.setClearBankName("民生银行");
		remitProcess.setClearBankChannelNo("123434");
		remitProcess.setStatus(1);
		remitProcess.setIsAutoProcess(1);
		remitProcess.setIsUrgent(1);
		remitProcess.setAmount(new BigDecimal(32.0));
		remitProcess.setIsReconciled(2);
		remitProcess.setUserNo("3280432");
		remitProcess.setBusinessType(1);
		remitProcess.setCreator("34234");
		remitProcess.setCreateDate(new Date());
		remitProcess.setConfirm("");
		remitProcess.setConfirmDate(new Date());
		remitProcess.setReason("");
		remitProcess.setBankRemark("");
		remitProcessFacade.creat(remitProcess);
	}
	
	/**
	 * 修改处理记录
	 */
	public void testUpdateProcess(){
		RemitProcess remitProcess = remitProcessFacade.getById(47);
		System.out.println(remitProcess.getAccountName());
		remitProcess.setBankRemark("测试数据来了");
		remitProcess.setStatus(1);
		remitProcess.setOrderId("100000001");
		remitProcessFacade.update(remitProcess);
	}
	
	/**
	 * 创建批次实体
	 */
	public void testCreatBatch(){
		RemitBatch remitBatch = new RemitBatch();
		remitBatch.setBatchNo(System.currentTimeMillis()+"");
		remitBatch.setStatus(1);
		remitBatch.setTotalNum(12);
		remitBatch.setTotalAmount(new BigDecimal(123));
		remitBatch.setAcceptSucNum(12);
		remitBatch.setAcceptSucAmount(new BigDecimal(123));
		remitBatch.setAcceptFailNum(34);
		remitBatch.setAcceptFailAmount(new BigDecimal(153));
		remitBatch.setProcessSucNum(65);
		remitBatch.setProcessSucAmount(new BigDecimal(983));
		remitBatch.setProcessFailNum(43);
		remitBatch.setProcessFailAmount(new BigDecimal(543));
		remitBatch.setCreateDate(new Date());
		remitBatch.setAcceptDate(new Date());
		remitBatch.setProcessDate(new Date());
		remitBatchFacade.create(remitBatch);
	}
	
	/**
	 * 修改批处理实体
	 */
	public void testUpdateBatch(){
		RemitBatch remitBatch = remitBatchFacade.getById(1);
		System.out.println(remitBatch.getProcessDate());
		remitBatch.setProcessDate(new Date());
		remitBatchFacade.update(remitBatch);
	}
	
	/**
	 * 批量打款请求
	 * @throws SQLException 
	 * @throws RemitBizException 
	 */
	public void testRemitRequest() throws RemitBizException, SQLException{
		BatchSettlRequestParam pb = new BatchSettlRequestParam();
		 List<SettlRequestParam> settReqeustList = new ArrayList();
		 pb.setTotalAmount(543d);
		 pb.setTotalNum(1);
		for(int i = 0 ; i < 1 ; i++){
			SettlRequestParam testMerchant = new SettlRequestParam();
			testMerchant.setRequestNo(System.currentTimeMillis()+"") ;//打款请求号
			testMerchant.setIsUrgent(1) ;//是否加急
			testMerchant.setBankAccountName("测试2200003220");//收款人银行户名
			testMerchant.setBankAccountNo("600033029");//收款人银行账号
			testMerchant.setBankName("中国工商银行");//收款银行名称
			testMerchant.setBankChannelNo("102659000491");//收款银行行号
			testMerchant.setAmount(543d);//提现金额
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
	
	/**
	 * 根据in条件有效银行
	 */
	public void testBankTypeInSelect(){
		List<String> bankCodeList = new ArrayList<String>();
		bankCodeList.add("PINGANBANK");
		bankCodeList.add("CZBANK");
		List<RemitBankType> list = remitBankTypeFacade.listActiveBankByIn(bankCodeList);
		for(RemitBankType r : list){
			System.out.println(">>>>>>>>>>>>>>" + r.getBankCode() + "," + r.getTypeName());
		}
	}
	
	public void testBankTypeList(){
		remitBankTypeFacade.listAll();
	}
	
/*	public void testBatchUpdateToUnapprove(){
		remitRequestFacade.manualCheckRemitRequest(new String[]{"1","2","3"}, RemitRequestStatusEnum.UNAPPROVE.getValue());
	}*/
	
	public void testBatchInsertPro(){
		List<RemitProcess> lists = new ArrayList<RemitProcess>();
		for(int i = 0 ; i < 5 ; i++){
			RemitProcess remitProcess = new RemitProcess();
			remitProcess.setRequestNo(System.currentTimeMillis() + "");
			remitProcess.setFlowNo("1200003");
			remitProcess.setChannelCode("PINGANBENK_EWR");
			remitProcess.setBatchNo("23423532423");
			remitProcess.setTradeInitiator(1);
			remitProcess.setAccountType(1);
			remitProcess.setAccountName("3243");
			remitProcess.setAccountNo("200993");
			remitProcess.setBankChannelNo("1300023");
			remitProcess.setBankName("平安银行");
			remitProcess.setProvince("广东省");
			remitProcess.setCity("广州市");
			remitProcess.setCurrency("RMB");
			remitProcess.setClearBankName("民生银行");
			remitProcess.setClearBankChannelNo("123434");
			remitProcess.setStatus(1);
			remitProcess.setIsAutoProcess(1);
			remitProcess.setIsUrgent(1);
			remitProcess.setAmount(new BigDecimal(32.0));
			remitProcess.setIsReconciled(2);
			remitProcess.setUserNo("3280432");
			remitProcess.setBusinessType(1);
			remitProcess.setCreator("34234");
			remitProcess.setCreateDate(new Date());
			remitProcess.setConfirm("");
			remitProcess.setConfirmDate(new Date());
			remitProcess.setReason("");
			remitProcess.setBankRemark("");
			lists.add(remitProcess);
		}
		
	}
}

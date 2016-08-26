package wusc.edu.pay.limit.test;

import wusc.edu.pay.facade.limit.enums.LimitTrxTypeEnum;
import wusc.edu.pay.facade.limit.service.TradeLimitFacade;
/**
 * 
 * @描述: 开关限制测试类.
 * @作者: HuangRuixiang .
 * @创建时间: 2014-7-14, 上午11:05:16 .
 * @版本: V1.0 .
 */
public class SwitchValidateResultDTO extends BaseTestCase {
	
	private TradeLimitFacade tradeLimitFacade;
	
	public void setUp() {
		tradeLimitFacade = (TradeLimitFacade) applicationContext.getBean("tradeLimitFacade");
	}
	/**
	 * 测试开关限制测试
	 */
	public void testValidateSwitchAvailable(){//业务一定要传值，验证支付产品的话，支付方式可以为空，验证支付方式，支付产品也要带上.
		String merchantNo = "1210002390";
		String bizFunction = "PAY"; 
		String payProduct = "asdfasdfasdf";//B2C银行卡支付
		String payWay = null;//PINGANBANK_NET_B2C
		
		//余额支付  ACCOUNT_BALANCE_PAY 
		tradeLimitFacade.validateSwitchAvailable(merchantNo, LimitTrxTypeEnum.PAY, payProduct, payWay);
		
	}
	
	/**
	 * 测试业务是否开通
	 */
	public void testValidateBizFunctionAvailable(){
		String merchantNo = "1210002390";
		String bizFunction = "PAY"; 
	
//		tradeLimitFacade.validateBizFunctionAvailable(merchantNo, bizFunction);
		
	}
}

package test.facade.user;

import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import wusc.edu.pay.common.utils.rsa.RSAUtils;
import wusc.edu.pay.facade.user.enums.MerchantSignTypeEnum;
import wusc.edu.pay.facade.user.service.MerchantOnlineFacade;


/**
 * 服务消费者,测试类
 * 
 * @author healy
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-context.xml" })
public class TsUserFacade extends TestCase {/*

	@Autowired
	private MerchantOnlineFacade merchantOnlineFacade;
	
	@Test
	public void testMerchantMD5Sign(){
		String sourceStr = "ABCDEfw1321中国";
		
		String sign = merchantOnlineFacade.merchantSign(sourceStr, MerchantSignTypeEnum.MD5, "888100000001533");
		
		System.out.println(sign);
		
		boolean flag = merchantOnlineFacade.merchantVerify(sourceStr, sign, MerchantSignTypeEnum.MD5, "888100000001533");
		
		System.out.println(flag);
	}
	
	@Test
	public void testMerchantRSASign(){
		String sourceStr = "ABCDEfw1321中国";
		String merchantPriKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANLkPXw5KbfF7nhnIxQ1v5jSGTVpsm7QLukjspeFOd9Q+icrkWD1TZ3numPEuxDY8lq1If3q4Tr+Lsi1WroCSawFw9vQLsZ+TkYeLpvjUhxAFKFtraiqeg041TOj6wiC9IF0N40ZUdOK5cDrJmbjwZvasfLnsBh7huMPS5r1TwT3AgMBAAECgYEAideNKKjkT64b1K71Crym4uhQec0AR9XjE5e1n7wAu6peF0Qu3cT4WoGgHp3z92sV3ZwO/7yKQtjpE18WUgY0x/+O7CxyGDFCe+JKIXeHRtumZMZTIE1Yp1dyRUCC+/OukT/oZki936EOtkY0hLTA0vqW95FflT11gD+A9wFCMeECQQDvBWzi6kG3z/iaUFDGxRQKbHpTaHc7zzvHHSIwYS1+T4NozdvVmaBZZ55ZPXghGqZCfFQMPqRjmMlYkgMfZ20tAkEA4d9H0mJ96x+6LuXA1ZiWf9O0kaERlnzL8gg3x0z3ZsitWZi/n+uwfIpzPok5GQMjnY11f9HlInSzM9xuicJ5MwJAVj1g4iafe5DAI/1ih7VH29Lz9/AzRRMqgFcUpNwLLA3IXiIYdWMupdNrIMu68kbJQifVs21OptjTnO9hzQzmKQJBAJEI8HNVLwmlS8YFXXdKDYlzBUVlqYd6BvMY98gP0eS8AyO/A3zKuH6f4DRNpM48x8fJJ6O1wW4IhXFNn+TxbSUCQFSPcRR70Q6E2k/Sdie3GUxhYoHBjl59HUpxcCA02T0Z7zO1F4dvcsSRunAsG9OZpsqHembc3NwhXPVu5JO5K1U=";
		try {
			String sign = RSAUtils.sign(sourceStr.getBytes(), merchantPriKey);
			
			boolean flag = merchantOnlineFacade.merchantVerify(sourceStr, sign, MerchantSignTypeEnum.RSA, "888100000001533");
			System.out.println(flag);
			
			String platPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCHWgfx3g+bVSEtXzkGyN0eCDgNuL0tRNW0U85XVUuUJU4+zN7+rsJPtSyXitxpo6Mf2N1wAko27znZQiSuEeML6ZczNKSuN0TrGtYFbyfnR1JTz8uILlB+PiPWt+5rFQSXgyHglfXSw/34vo5ikNjgdpW0zthFYqPPBfpKzQMClQIDAQAB";
			String rsaSign = merchantOnlineFacade.merchantSign(sourceStr, MerchantSignTypeEnum.RSA, "888100000001533");
			boolean rsaFlag = RSAUtils.verify(sourceStr.getBytes(), platPublicKey, rsaSign);
			System.out.println(rsaFlag);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCreatRSAKey(){
		try {
			Map<String , Object> map =  RSAUtils.genKeyPair();
			String pubKey = String.valueOf(map.get(RSAUtils.PUBLIC_KEY_VALUE));
			String priKey = String.valueOf(map.get(RSAUtils.PRIVATE_KEY_VALUE));
			System.out.println(pubKey);
			System.out.println(priKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

*/}

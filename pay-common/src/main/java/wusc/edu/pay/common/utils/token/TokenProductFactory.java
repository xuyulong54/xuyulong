package wusc.edu.pay.common.utils.token;

/**
 * ClassName: TokenProductFactory <br/>
 * Function: 生成Token 类 ，用于不同平台间安全验证<br/>
 * date: 2014-8-5 下午8:25:58 <br/>
 * 
 * @author laich
 */
public class TokenProductFactory {
	
	 public final static String key="gzzyzz";
	 private static TokenBaseInter base64 = new TokenToolEncrypterBase64();
	 private static TokenBaseInter des64 = new TokenToolEncrypter();

	 public static TokenBaseInter getInstallBase64(){
		 return base64 ;
	 }
	 public static TokenBaseInter getInstallDES64(){
		 return des64 ;
	 }
	
	public static void main(String[] args) {
		String[] s={"gw","888000000000000@qq.com"};
		String ss=base64.productToken("GATEPAY","99900000000000099");
		System.out.println("加密后的值："+ss);
		System.out.println(base64.decrypt("R0FURVBBWS0xMjk5OTk5OTk5OTktMTQxMTUyNDUyMzc0Ny1nenp5eno_"));
		System.out.println(base64.decrypt("R0FURVBBWS0xMjk5OTk5OTk5OTktMTQxMTUyNDA1NDM3OC1nenp5eno="));
		String gg=base64.decrypt("iHyd03VRhAeTTDxLx4VPGt3iKgCo7i4FEoxz7gboUJRUBmPRBe9heJGF2TUe9MQp");
		System.out.println("解密后的值："+gg);
	}
}
